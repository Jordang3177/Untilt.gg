package HttpClients;

import Entities.*;
import Enums.Region;
import Utils.BuildEntitiesUtils;
import Utils.Config;
import Utils.Containers.PlayerDataContainer;
import Utils.LeagueEndpoints;
import Utils.Containers.MatchContainer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.*;

public class LeagueHttpApiClient {
    private final HttpClient httpClient;
    private final String apiKey;
    private final String regionBaseUrl;
    private final String clusterBaseUrl;
    private final ObjectMapper MAPPER;
    private final MatchContainer matchContainer;
    private final PlayerDataContainer playerDataContainer;
    private final BuildEntitiesUtils buildEntitiesUtils;

    public LeagueHttpApiClient(String regionBaseUrl, String clusterBaseUrl) {
        this.httpClient = HttpClient.newBuilder()
                .connectTimeout(Duration.ofMillis(Config.API_TIMEOUT))
                .build();
        this.apiKey = Config.API_KEY;
        this.regionBaseUrl = regionBaseUrl;
        this.clusterBaseUrl = clusterBaseUrl;
        this.MAPPER = new ObjectMapper();
        this.matchContainer = new MatchContainer();
        this.playerDataContainer = new PlayerDataContainer();
        this.buildEntitiesUtils = new BuildEntitiesUtils();
    }

    private HttpRequest.Builder regionBaseRequest(String path) {
        return HttpRequest.newBuilder()
                .uri(URI.create(regionBaseUrl + path))
                .timeout(Duration.ofMillis(Config.API_TIMEOUT))
                .header("X-Riot-Token", apiKey)
                .header("Content-Type", "application/json");
    }

    private HttpRequest.Builder clusterBaseRequest(String path) {
        return HttpRequest.newBuilder()
                .uri(URI.create(clusterBaseUrl + path))
                .timeout(Duration.ofMillis(Config.API_TIMEOUT))
                .header("X-Riot-Token", apiKey)
                .header("Content-Type", "application/json");
    }

    public String get(String path) throws IOException, InterruptedException {
        HttpRequest request = regionBaseRequest(path).GET().build();
        System.out.println(request.uri());

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        validateResponse(response);
        return response.body();
    }

    public String getAccountInformation(String summonerName, String summonerTagLine) throws IOException, InterruptedException {
        HttpRequest request = clusterBaseRequest(LeagueEndpoints.accountByRiotId() + summonerName + "/" + summonerTagLine).GET().build();
        System.out.println(request.uri());

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        validateResponse(response);
        return response.body();
    }

    public List<String> getMatchIds(String userId, String type, String start, String count) throws IOException, InterruptedException {
        Map<String, String> queryParams = Map.of("type", type, "start", start, "count", count);
        URI getMatchIdsURI = uriBuilder(LeagueEndpoints.matchesByUserId() + userId + "/ids", queryParams);
        HttpRequest request = clusterBaseRequest(getMatchIdsURI.toString()).GET().build();
        System.out.println(request.uri());

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        validateResponse(response);
        return MAPPER.readValue(response.body(), new com.fasterxml.jackson.core.type.TypeReference<List<String>>() {});
    }

    public List<String> getAllMatchIds(String userId) throws IOException, InterruptedException {
        List<String> allMatchIds = new ArrayList<>();
        for(int i = 0; i < 100; i++) {
            List<String> currentMatches = getMatchIds(userId, "ranked", String.valueOf(i * 100), String.valueOf(100));
            allMatchIds.addAll(currentMatches);
            Thread.sleep(1300);
            if(currentMatches.size() != 100) {
                break;
            }
        }
        return allMatchIds;
    }

    public MatchEntity getMatchInfo(String matchId) throws IOException, InterruptedException {
        MatchEntity existingMatchEntity = matchContainer.getByMatchId(matchId);
        if(existingMatchEntity != null) {
            System.out.println("Match already exists in the DB Returning the retrieved MatchEntity");
            return existingMatchEntity;
        }

        HttpRequest request = clusterBaseRequest(LeagueEndpoints.matchByMatchId() + matchId).GET().build();

        System.out.println(request.uri());

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        validateResponse(response);
        JsonNode match = MAPPER.readValue(response.body(), new com.fasterxml.jackson.core.type.TypeReference<JsonNode>() {});
        MatchEntity currentMatchEntity = buildEntitiesUtils.buildEntity(match);
        System.out.println("Attempting to save Current MatchEntity to DB with MatchId: " + currentMatchEntity.id);
        matchContainer.save(currentMatchEntity);
        Thread.sleep(1300);
        return currentMatchEntity;
    }

    public List<MatchEntity> getAllMatches(Set<String> matches) throws IOException, InterruptedException {
        List<MatchEntity> allMatches = new ArrayList<>();
        int counter = 0;
        for(String matchId : matches) {
            System.out.println("Remaining Requests : " + (matches.size() - counter));
            MatchEntity currentMatchInfo = getMatchInfo(matchId);
            allMatches.add(currentMatchInfo);
            counter++;
        }
        return allMatches;
    }

    public PlayerDataEntity getPlayerEntityByPuuid(String puuid, String gameName, String tagLine, Region region, List<String> matches) throws InterruptedException, IOException {
        PlayerDataEntity existingPlayerDataEntity = playerDataContainer.getByPlayerId(gameName + "_" + tagLine + "_" + region.toString());
        if(existingPlayerDataEntity != null) {
            boolean modified = false;
            for(String match : matches) {
                if(!existingPlayerDataEntity.matchesSet.contains(match)) {
                    existingPlayerDataEntity.matchesSet.add(match);
                    modified = true;
                }
            }
            if(modified) {
                playerDataContainer.replace(existingPlayerDataEntity);
                System.out.println("PlayerData has been modified so Updating the PlayerData in the DB for Player with Id: " + existingPlayerDataEntity.id);
            }
            else {
                System.out.println("PlayerData was not modified, no updates needed");
            }
            System.out.println("PlayerData already exists for given Player Id");
            return existingPlayerDataEntity;
        }

        HttpRequest request = regionBaseRequest(LeagueEndpoints.rankByUserId() + puuid).GET().build();

        System.out.println(request.uri());

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        validateResponse(response);
        JsonNode playerDataEntity = MAPPER.readValue(response.body(), new com.fasterxml.jackson.core.type.TypeReference<JsonNode>() {});
        String tier = null;
        String rank = null;
        if(playerDataEntity.isArray()) {
            for(JsonNode entry : playerDataEntity) {
                String queueType = entry.path("queueType").asText();
                if("RANKED_SOLO_5x5".equals(queueType)) {
                    tier = entry.path("tier").asText();
                    rank = entry.path("rank").asText();
                }
            }
        }
        PlayerDataEntity currentPlayerDataEntity = new PlayerDataEntity();
        if(tier.equals("CHALLENGER") || tier.equals("GRANDMASTER") || tier.equals("MASTER")) {
            currentPlayerDataEntity.rank = tier;
        }
        else {
            currentPlayerDataEntity.rank = tier + " " + rank;
        }
        currentPlayerDataEntity.id = gameName + "_" + tagLine + "_" + region;
        currentPlayerDataEntity.puuid = puuid;
        currentPlayerDataEntity.gameName = gameName;
        currentPlayerDataEntity.tagLine = tagLine;
        currentPlayerDataEntity.region = region.toString();
        currentPlayerDataEntity.matchesSet = new HashSet<>(matches);
        System.out.println("Attempting to save Current PlayerDataEntity to DB with PlayerId: " + currentPlayerDataEntity.id);
        playerDataContainer.save(currentPlayerDataEntity);
        Thread.sleep(1300);
        return currentPlayerDataEntity;
    }

    private void validateResponse(HttpResponse<?> response) {
        int status = response.statusCode();
        if (status < 200 || status >= 300) {
            throw new RuntimeException("API call failed: " + status + " - " + response.body());
        }
    }

    private URI uriBuilder(String baseUrl, Map<String, String> queryParams) {
        if(queryParams == null || queryParams.isEmpty()) {
            return URI.create(baseUrl);
        }

        StringJoiner joiner = new StringJoiner("&");

        for(var entry : queryParams.entrySet()) {
            joiner.add(
                    URLEncoder.encode(entry.getKey(), StandardCharsets.UTF_8)
                            + "=" +
                    URLEncoder.encode(entry.getValue(), StandardCharsets.UTF_8)
            );
        }

        return URI.create(baseUrl + "?" + joiner);
    }
}
