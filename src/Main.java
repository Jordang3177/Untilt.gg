import Entities.HourlyWinRateEntity;
import Entities.MatchEntity;
import Entities.PlayerDataEntity;
import Enums.Region;
import HttpClients.LeagueHttpApiClient;
import Utils.BestHourUtil;
import Utils.GrabUserInput;
import Utils.JsonUtil;
import Utils.RegionUtil;
import com.fasterxml.jackson.databind.JsonNode;

void main() throws IOException, InterruptedException {
    GrabUserInput grabUserInput = new GrabUserInput();
    String summonerId = grabUserInput.grabInputForSummoner();
    if(!summonerId.contains("#")) {
        System.out.println("Please provide a proper SummonerId that includes the # in the Name");
        throw new IOException();
    }
    System.out.println(summonerId);
    Region region = grabUserInput.promptForRegion();
    RegionUtil regionUtil = new RegionUtil();
    String regionBaseUrl = regionUtil.getRegionUrl(region);
    String clusterBaseUrl = regionUtil.getClusterUrl(region);
    LeagueHttpApiClient leagueHttpApiClient = new LeagueHttpApiClient(regionBaseUrl, clusterBaseUrl);
    String[] summoner = summonerId.split("#");
    String summonerName = summoner[0];
    summonerName = summonerName.replaceAll("\\s+","");
    String summonerTagLine = summoner[1];
    String accountInformation = leagueHttpApiClient.getAccountInformation(summonerName, summonerTagLine);
    Thread.sleep(1300);
    System.out.println(accountInformation);
    JsonNode accountInformationJson = JsonUtil.parse(accountInformation);
    String UserId = accountInformationJson.get("puuid").asText();
    String GameName = accountInformationJson.get("gameName").asText();
    String TagLine = accountInformationJson.get("tagLine").asText();
    System.out.println(UserId);
    List<String> matchIds = leagueHttpApiClient.getAllMatchIds(UserId);
    Thread.sleep(1300);
    PlayerDataEntity currentPlayerDataEntity = leagueHttpApiClient.getPlayerEntityByPuuid(UserId, GameName, TagLine, region, matchIds);
    System.out.println(matchIds.size());
    System.out.println(matchIds);
    List<MatchEntity> allMatchInfo = leagueHttpApiClient.getAllMatches(currentPlayerDataEntity.matchesSet);
    System.out.println(allMatchInfo.size());
    Thread.sleep(1300);
    BestHourUtil bestHourUtil = new BestHourUtil();
    ZoneId testZone = ZoneId.of("America/Los_Angeles");
    HourlyWinRateEntity hourlyWinRateEntity = bestHourUtil.buildHourlyWinRates(UserId, allMatchInfo, testZone);
    hourlyWinRateEntity.debugPrint(summonerId);
}
