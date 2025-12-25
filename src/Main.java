import com.fasterxml.jackson.databind.JsonNode;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
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
    String summonerTagLine = summoner[1];
    String accountInformation = leagueHttpApiClient.getAccountInformation(summonerName, summonerTagLine);
    System.out.println(accountInformation);
    JsonNode accountInformationJson = JsonUtil.parse(accountInformation);
    String UserId = accountInformationJson.get("puuid").asText();
    System.out.println(UserId);
    List<String> matchIds = leagueHttpApiClient.getAllMatchIds(UserId);
    System.out.println(matchIds.size());
}
