import com.fasterxml.jackson.databind.ObjectMapper;
import scala.concurrent.impl.FutureConvertersImpl;

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

    public LeagueHttpApiClient() {
        this.httpClient = HttpClient.newBuilder()
                .connectTimeout(Duration.ofMillis(Config.API_TIMEOUT))
                .build();
        this.apiKey = Config.API_KEY;
        this.baseUrl = Config.API_REGION_BASEURL;
        this.oldBaseUrl = Config.API_CLUSTER_BASEURL;
        this.MAPPER = new ObjectMapper();
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
            if(currentMatches.size() != 100) {
                break;
            }
        }
        return allMatchIds;
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
