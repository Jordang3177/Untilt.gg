package HttpClients;

import Entities.Info.*;
import Entities.MatchEntity;
import Entities.PlayerEntity;
import Utils.Config;
import Utils.LeagueEndpoints;
import Utils.MatchContainer;
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
    private final String baseUrl;
    private final String oldBaseUrl;
    private final ObjectMapper MAPPER;
    private final MatchContainer matchContainer;

    public LeagueHttpApiClient() {
        this.httpClient = HttpClient.newBuilder()
                .connectTimeout(Duration.ofMillis(Config.API_TIMEOUT))
                .build();
        this.apiKey = Config.API_KEY;
        this.baseUrl = Config.API_REGION_BASEURL;
        this.oldBaseUrl = Config.API_CLUSTER_BASEURL;
        this.MAPPER = new ObjectMapper();
        this.matchContainer = new MatchContainer();
    }

    private HttpRequest.Builder regionBaseRequest(String path) {
        return HttpRequest.newBuilder()
                .uri(URI.create(baseUrl + path))
                .timeout(Duration.ofMillis(Config.API_TIMEOUT))
                .header("X-Riot-Token", apiKey)
                .header("Content-Type", "application/json");
    }

    private HttpRequest.Builder clusterBaseRequest(String path) {
        return HttpRequest.newBuilder()
                .uri(URI.create(oldBaseUrl + path))
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
        MatchEntity currentMatchEntity = buildEntity(match);
        System.out.println("Attempting to save Current MatchEntity to DB with MatchId: " + currentMatchEntity.id);
        matchContainer.save(currentMatchEntity);
        Thread.sleep(1300);
        return currentMatchEntity;
    }

    public List<MatchEntity> getAllMatches(List<String> matches) throws IOException, InterruptedException {
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

    private MatchEntity buildEntity(JsonNode match) {
        MatchEntity currentMatchEntity = new MatchEntity();
        String matchId = match.get("metadata").get("matchId").asText();
        currentMatchEntity.id = matchId;
        currentMatchEntity.matchId = matchId;
        currentMatchEntity.gameCreationTimeStamp = match.get("info").get("gameCreation").asLong();
        List<SummonerInfoEntity> listOfSummonersInfo = new ArrayList<>();
        List<PlayerEntity> winners = new ArrayList<>();
        JsonNode participantsNode = match.get("info").get("participants");
        if(participantsNode != null && participantsNode.isArray()) {
            for(JsonNode participant : participantsNode) {
                listOfSummonersInfo.add(buildSummonerInfoEntity(participant));
                if(participant.has("win") && participant.get("win").asBoolean()) {
                    winners.add(buildPlayerEntity(participant));
                }
            }
        }
        currentMatchEntity.summonersInfo = listOfSummonersInfo;
        currentMatchEntity.winners = winners;
        return currentMatchEntity;
    }

    private SummonerInfoEntity buildSummonerInfoEntity(JsonNode summonerInfo) {
        SummonerInfoEntity currentSummonerInfoEntity = new SummonerInfoEntity();
        currentSummonerInfoEntity.damageInfoEntity = buildDamageInfoEntity(summonerInfo);
        currentSummonerInfoEntity.kdaInfoEntity = buildKDAInfoEntity(summonerInfo);
        currentSummonerInfoEntity.mapInfoEntity = buildMapInfoEntity(summonerInfo);
        currentSummonerInfoEntity.characterInfoEntity = buildCharacterInfoEntity(summonerInfo);
        currentSummonerInfoEntity.playerEntity = buildPlayerEntity(summonerInfo);
        if (summonerInfo.has("teamPosition")) {
            String lanePosition = summonerInfo.get("teamPosition").asText();
            if(lanePosition.equals("UTILITY")) {
                lanePosition = "SUPPORT";
            }
            currentSummonerInfoEntity.lane = lanePosition;
        }
        else currentSummonerInfoEntity.lane = "";
        currentSummonerInfoEntity.wonGame = summonerInfo.has("win") && summonerInfo.get("win").asBoolean();
        return currentSummonerInfoEntity;
    }

    private CharacterInfoEntity buildCharacterInfoEntity(JsonNode summonerInfo) {
        CharacterInfoEntity currentCharacterInfoEntity = new CharacterInfoEntity();
        currentCharacterInfoEntity.ChampionExperience = summonerInfo.has("champExperience") ? summonerInfo.get("champExperience").asInt() : 0;
        currentCharacterInfoEntity.ChampionLevel = summonerInfo.has("champLevel") ? summonerInfo.get("champLevel").asInt() : 0;
        currentCharacterInfoEntity.ChampionName = summonerInfo.has("championName") ? summonerInfo.get("championName").asText() : "";
        currentCharacterInfoEntity.GoldEarned = summonerInfo.has("goldEarned") ? summonerInfo.get("goldEarned").asInt() : 0;
        currentCharacterInfoEntity.GoldSpent = summonerInfo.has("goldSpent") ? summonerInfo.get("goldSpent").asInt() : 0;
        currentCharacterInfoEntity.Item0 = summonerInfo.has("item0") ? summonerInfo.get("item0").asInt() : 0;
        currentCharacterInfoEntity.Item1 = summonerInfo.has("item1") ? summonerInfo.get("item1").asInt() : 0;
        currentCharacterInfoEntity.Item2 = summonerInfo.has("item2") ? summonerInfo.get("item2").asInt() : 0;
        currentCharacterInfoEntity.Item3 = summonerInfo.has("item3") ? summonerInfo.get("item3").asInt() : 0;
        currentCharacterInfoEntity.Item4 = summonerInfo.has("item4") ? summonerInfo.get("item4").asInt() : 0;
        currentCharacterInfoEntity.Item5 = summonerInfo.has("item5") ? summonerInfo.get("item5").asInt() : 0;
        currentCharacterInfoEntity.Item6 = summonerInfo.has("item6") ? summonerInfo.get("item6").asInt() : 0;
        currentCharacterInfoEntity.JungleMonstersKilled = summonerInfo.has("neutralMinionsKilled") ? summonerInfo.get("neutralMinionsKilled").asInt() : 0;
        currentCharacterInfoEntity.TotalAllyJungleMonstersKilled = summonerInfo.has("totalAllyJungleMinionsKilled") ? summonerInfo.get("totalAllyJungleMinionsKilled").asInt() : 0;
        currentCharacterInfoEntity.TotalEnemyJungleMonstersKilled = summonerInfo.has("totalEnemyJungleMinionsKilled") ? summonerInfo.get("totalEnemyJungleMinionsKilled").asInt() : 0;
        currentCharacterInfoEntity.TotalMinionsKilled = summonerInfo.has("totalMinionsKilled") ? summonerInfo.get("totalMinionsKilled").asInt() : 0;
        return currentCharacterInfoEntity;
    }

    private DamageInfoEntity buildDamageInfoEntity(JsonNode summonerInfo) {
        DamageInfoEntity currentDamageInfoEntity = new DamageInfoEntity();
        currentDamageInfoEntity.DamageDealtToBuildings = summonerInfo.has("damageDealtToBuildings") ? summonerInfo.get("damageDealtToBuildings").asInt() : 0;
        currentDamageInfoEntity.DamageDealtToEpicMonsters = summonerInfo.has("damageDealtToEpicMonsters") ? summonerInfo.get("damageDealtToEpicMonsters").asInt() : 0;
        currentDamageInfoEntity.DamageDealtToObjectives = summonerInfo.has("damageDealtToObjectives") ? summonerInfo.get("damageDealtToObjectives").asInt() : 0;
        currentDamageInfoEntity.DamageDealtToTurrets = summonerInfo.has("damageDealtToTurrets") ? summonerInfo.get("damageDealtToTurrets").asInt() : 0;
        currentDamageInfoEntity.DamageSelfMitigated = summonerInfo.has("damageSelfMitigated") ? summonerInfo.get("damageSelfMitigated").asInt() : 0;
        currentDamageInfoEntity.MagicDamageDealtToChampions = summonerInfo.has("magicDamageDealtToChampions") ? summonerInfo.get("magicDamageDealtToChampions").asInt() : 0;
        currentDamageInfoEntity.MagicDamageTaken = summonerInfo.has("magicDamageTaken") ? summonerInfo.get("magicDamageTaken").asInt() : 0;
        currentDamageInfoEntity.PhysicalDamageDealtToChampions = summonerInfo.has("physicalDamageDealtToChampions") ? summonerInfo.get("physicalDamageDealtToChampions").asInt() : 0;
        currentDamageInfoEntity.PhysicalDamageTaken = summonerInfo.has("physicalDamageTaken") ? summonerInfo.get("physicalDamageTaken").asInt() : 0;
        currentDamageInfoEntity.TotalDamageDealtToChampions = summonerInfo.has("totalDamageDealtToChampions") ? summonerInfo.get("totalDamageDealtToChampions").asInt() : 0;
        currentDamageInfoEntity.TotalDamageShieldedOnTeammates = summonerInfo.has("totalDamageShieldedOnTeammates") ? summonerInfo.get("totalDamageShieldedOnTeammates").asInt() : 0;
        currentDamageInfoEntity.TotalDamageTaken = summonerInfo.has("totalDamageTaken") ? summonerInfo.get("totalDamageTaken").asInt() : 0;
        currentDamageInfoEntity.TotalHeal = summonerInfo.has("totalHeal") ? summonerInfo.get("totalHeal").asInt() : 0;
        currentDamageInfoEntity.TotalHealOnTeammates = summonerInfo.has("totalHealsOnTeammates") ? summonerInfo.get("totalHealsOnTeammates").asInt() : 0;
        currentDamageInfoEntity.TrueDamageDealtToChampions = summonerInfo.has("trueDamageDealtToChampions") ? summonerInfo.get("trueDamageDealtToChampions").asInt() : 0;
        currentDamageInfoEntity.TrueDamageTaken = summonerInfo.has("trueDamageTaken") ? summonerInfo.get("trueDamageTaken").asInt() : 0;
        return currentDamageInfoEntity;
    }

    private KDAInfoEntity buildKDAInfoEntity(JsonNode summonerInfo) {
        KDAInfoEntity currentKDAInfoEntity = new KDAInfoEntity();
        currentKDAInfoEntity.Assists = summonerInfo.has("assists") ? summonerInfo.get("assists").asInt() : 0;
        currentKDAInfoEntity.Deaths = summonerInfo.has("deaths") ? summonerInfo.get("deaths").asInt() : 0;
        currentKDAInfoEntity.Kills = summonerInfo.has("kills") ? summonerInfo.get("kills").asInt() : 0;
        currentKDAInfoEntity.DoubleKills = summonerInfo.has("doubleKills") ? summonerInfo.get("doubleKills").asInt() : 0;
        currentKDAInfoEntity.TripleKills = summonerInfo.has("tripleKills") ? summonerInfo.get("tripleKills").asInt() : 0;
        currentKDAInfoEntity.QuadraKills = summonerInfo.has("quadraKills") ? summonerInfo.get("quadraKills").asInt() : 0;
        currentKDAInfoEntity.PentaKills = summonerInfo.has("pentaKills") ? summonerInfo.get("pentaKills").asInt() : 0;
        currentKDAInfoEntity.LargestKillingSpree = summonerInfo.has("largestKillingSpree") ? summonerInfo.get("largestKillingSpree").asInt() : 0;
        currentKDAInfoEntity.LargestMultiKill = summonerInfo.has("largestMultiKill") ? summonerInfo.get("largestMultiKill").asInt() : 0;
        currentKDAInfoEntity.FirstBloodAssist = summonerInfo.has("firstBloodAssist") && summonerInfo.get("firstBloodAssist").asBoolean();
        currentKDAInfoEntity.FirstBloodKill = summonerInfo.has("firstBloodKill") && summonerInfo.get("firstBloodKill").asBoolean();
        return currentKDAInfoEntity;
    }

    private MapInfoEntity buildMapInfoEntity(JsonNode summonerInfo) {
        MapInfoEntity currentMapInfoEntity = new MapInfoEntity();
        currentMapInfoEntity.BaronKills = summonerInfo.has("baronKills") ? summonerInfo.get("baronKills").asInt() : 0;
        currentMapInfoEntity.DragonKills = summonerInfo.has("dragonKills") ? summonerInfo.get("dragonKills").asInt() : 0;
        currentMapInfoEntity.FirstTowerAssist = summonerInfo.has("firstTowerAssist") && summonerInfo.get("firstTowerAssist").asBoolean();
        currentMapInfoEntity.FirstTowerKill = summonerInfo.has("firstTowerKill") && summonerInfo.get("firstTowerKill").asBoolean();
        currentMapInfoEntity.InhibitorKills = summonerInfo.has("inhibitorKills") ? summonerInfo.get("inhibitorKills").asInt() : 0;
        currentMapInfoEntity.InhibitorTakedowns = summonerInfo.has("inhibitorTakedowns") ? summonerInfo.get("inhibitorTakedowns").asInt() : 0;
        currentMapInfoEntity.InhibitorsLost = summonerInfo.has("inhibitorsLost") ? summonerInfo.get("inhibitorsLost").asInt() : 0;
        currentMapInfoEntity.ObjectivesStolen = summonerInfo.has("objectivesStolen") ? summonerInfo.get("objectivesStolen").asInt() : 0;
        currentMapInfoEntity.ObjectivesStolenAssists = summonerInfo.has("objectivesStolenAssists") ? summonerInfo.get("objectivesStolenAssists").asInt() : 0;
        currentMapInfoEntity.TurretKills = summonerInfo.has("turretKills") ? summonerInfo.get("turretKills").asInt() : 0;
        currentMapInfoEntity.TurretTakedowns = summonerInfo.has("turretTakedowns") ? summonerInfo.get("turretTakedowns").asInt() : 0;
        currentMapInfoEntity.TurretsLost = summonerInfo.has("turretsLost") ? summonerInfo.get("turretsLost").asInt() : 0;
        currentMapInfoEntity.VisionScore = summonerInfo.has("visionScore") ? summonerInfo.get("visionScore").asInt() : 0;
        currentMapInfoEntity.ControlWardsPurchased = summonerInfo.has("visionWardsBoughtInGame") ? summonerInfo.get("visionWardsBoughtInGame").asInt() : 0;
        currentMapInfoEntity.WardsKilled = summonerInfo.has("wardsKilled") ? summonerInfo.get("wardsKilled").asInt() : 0;
        currentMapInfoEntity.WardsPlaced = summonerInfo.has("wardsPlaced") ? summonerInfo.get("wardsPlaced").asInt() : 0;
        return currentMapInfoEntity;
    }

    private PlayerEntity buildPlayerEntity(JsonNode summonerInfo) {
        PlayerEntity currentPlayerEntity = new PlayerEntity();
        currentPlayerEntity.riotPlayerId = summonerInfo.has("puuid") ? summonerInfo.get("puuid").asText() : "";
        currentPlayerEntity.riotIdGameName = summonerInfo.has("riotIdGameName") ? summonerInfo.get("riotIdGameName").asText() : "";
        currentPlayerEntity.riotIdTagLine = summonerInfo.has("riotIdTagline") ? summonerInfo.get("riotIdTagline").asText() : "";
        return currentPlayerEntity;
    }
}
