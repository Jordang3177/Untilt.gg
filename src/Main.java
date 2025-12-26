import Entities.MatchEntity;
import HttpClients.LeagueHttpApiClient;
import Utils.GrabUserInput;
import Utils.JsonUtil;
import com.fasterxml.jackson.databind.JsonNode;

void main() throws IOException, InterruptedException {
    GrabUserInput grabUserInput = new GrabUserInput();
    String summonerId = grabUserInput.grabInput();
    if(!summonerId.contains("#")) {
        System.out.println("Please provide a proper SummonerId that includes the # in the Name");
        throw new IOException();
    }
    System.out.println(summonerId);
    LeagueHttpApiClient leagueHttpApiClient = new LeagueHttpApiClient();
    String[] summoner = summonerId.split("#");
    String summonerName = summoner[0];
    summonerName = summonerName.replaceAll("\\s+","");
    String summonerTagLine = summoner[1];
    String accountInformation = leagueHttpApiClient.getAccountInformation(summonerName, summonerTagLine);
    Thread.sleep(1300);
    System.out.println(accountInformation);
    JsonNode accountInformationJson = JsonUtil.parse(accountInformation);
    String UserId = accountInformationJson.get("puuid").asText();
    System.out.println(UserId);
    List<String> matchIds = leagueHttpApiClient.getAllMatchIds(UserId);
    Thread.sleep(1300);
    System.out.println(matchIds.size());
    System.out.println(matchIds);
    List<MatchEntity> allMatchInfo = leagueHttpApiClient.getAllMatches(matchIds);
    System.out.println(allMatchInfo.size());
    Thread.sleep(1300);
}
