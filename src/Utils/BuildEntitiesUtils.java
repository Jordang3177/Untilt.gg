package Utils;

import Entities.Info.*;
import Entities.MatchEntity;
import Entities.PlayerEntity;
import Entities.TimeZoneEntity;
import com.fasterxml.jackson.databind.JsonNode;

import java.time.Instant;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

public class BuildEntitiesUtils {

    public BuildEntitiesUtils() {}

    public MatchEntity buildEntity(JsonNode match) {
        MatchEntity currentMatchEntity = new MatchEntity();
        String matchId = match.get("metadata").get("matchId").asText();
        currentMatchEntity.id = matchId;
        currentMatchEntity.matchId = matchId;
        currentMatchEntity.gameCreationTimeStamp = match.get("info").get("gameCreation").asLong();
        currentMatchEntity.timeZoneEntity = buildTimeZoneEntity(currentMatchEntity.gameCreationTimeStamp);
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

    private TimeZoneEntity buildTimeZoneEntity(long gameCreationTimeStamp) {
        Instant instant = Instant.ofEpochMilli(gameCreationTimeStamp);
        TimeZoneEntity currentTimeZoneEntity = new TimeZoneEntity();
        currentTimeZoneEntity.etcGmtPlus12 = instant.atZone(ZoneId.of("Etc/GMT+12")).toLocalDateTime().toString();
        currentTimeZoneEntity.pacificPagoPago = instant.atZone(ZoneId.of("Pacific/Pago_Pago")).toLocalDateTime().toString();
        currentTimeZoneEntity.pacificHonolulu= instant.atZone(ZoneId.of("Pacific/Honolulu")).toLocalDateTime().toString();
        currentTimeZoneEntity.americaAnchorage = instant.atZone(ZoneId.of("America/Anchorage")).toLocalDateTime().toString();
        currentTimeZoneEntity.americaLosAngeles = instant.atZone(ZoneId.of("America/Los_Angeles")).toLocalDateTime().toString();
        currentTimeZoneEntity.americaDenver = instant.atZone(ZoneId.of("America/Denver")).toLocalDateTime().toString();
        currentTimeZoneEntity.americaChicago = instant.atZone(ZoneId.of("America/Chicago")).toLocalDateTime().toString();
        currentTimeZoneEntity.americaNewYork = instant.atZone(ZoneId.of("America/New_York")).toLocalDateTime().toString();
        currentTimeZoneEntity.americaHalifax = instant.atZone(ZoneId.of("America/Halifax")).toLocalDateTime().toString();
        currentTimeZoneEntity.americaStJohns = instant.atZone(ZoneId.of("America/St_Johns")).toLocalDateTime().toString();
        currentTimeZoneEntity.americaBuenosAires = instant.atZone(ZoneId.of("America/Argentina/Buenos_Aires")).toLocalDateTime().toString();
        currentTimeZoneEntity.atlanticSouthGeorgia = instant.atZone(ZoneId.of("Atlantic/South_Georgia")).toLocalDateTime().toString();
        currentTimeZoneEntity.atlanticAzores = instant.atZone(ZoneId.of("Atlantic/Azores")).toLocalDateTime().toString();
        currentTimeZoneEntity.europeLondon = instant.atZone(ZoneId.of("Europe/London")).toLocalDateTime().toString();
        currentTimeZoneEntity.europeParis = instant.atZone(ZoneId.of("Europe/Paris")).toLocalDateTime().toString();
        currentTimeZoneEntity.africaCairo = instant.atZone(ZoneId.of("Africa/Cairo")).toLocalDateTime().toString();
        currentTimeZoneEntity.europeMoscow = instant.atZone(ZoneId.of("Europe/Moscow")).toLocalDateTime().toString();
        currentTimeZoneEntity.asiaTehran = instant.atZone(ZoneId.of("Asia/Tehran")).toLocalDateTime().toString();
        currentTimeZoneEntity.asiaDubai = instant.atZone(ZoneId.of("Asia/Dubai")).toLocalDateTime().toString();
        currentTimeZoneEntity.asiaKabul = instant.atZone(ZoneId.of("Asia/Kabul")).toLocalDateTime().toString();
        currentTimeZoneEntity.asiaKarachi = instant.atZone(ZoneId.of("Asia/Karachi")).toLocalDateTime().toString();
        currentTimeZoneEntity.asiaKolkata = instant.atZone(ZoneId.of("Asia/Kolkata")).toLocalDateTime().toString();
        currentTimeZoneEntity.asiaKathmandu = instant.atZone(ZoneId.of("Asia/Kathmandu")).toLocalDateTime().toString();
        currentTimeZoneEntity.asiaDhaka = instant.atZone(ZoneId.of("Asia/Dhaka")).toLocalDateTime().toString();
        currentTimeZoneEntity.asiaYangon = instant.atZone(ZoneId.of("Asia/Yangon")).toLocalDateTime().toString();
        currentTimeZoneEntity.asiaBangkok = instant.atZone(ZoneId.of("Asia/Bangkok")).toLocalDateTime().toString();
        currentTimeZoneEntity.asiaShanghai = instant.atZone(ZoneId.of("Asia/Shanghai")).toLocalDateTime().toString();
        currentTimeZoneEntity.asiaTokyo = instant.atZone(ZoneId.of("Asia/Tokyo")).toLocalDateTime().toString();
        currentTimeZoneEntity.australiaAdelaide = instant.atZone(ZoneId.of("Australia/Adelaide")).toLocalDateTime().toString();
        currentTimeZoneEntity.australiaSydney = instant.atZone(ZoneId.of("Australia/Sydney")).toLocalDateTime().toString();
        currentTimeZoneEntity.australiaLordHowe = instant.atZone(ZoneId.of("Australia/Lord_Howe")).toLocalDateTime().toString();
        currentTimeZoneEntity.pacificNoumea = instant.atZone(ZoneId.of("Pacific/Noumea")).toLocalDateTime().toString();
        currentTimeZoneEntity.pacificAuckland = instant.atZone(ZoneId.of("Pacific/Auckland")).toLocalDateTime().toString();
        currentTimeZoneEntity.pacificChatham = instant.atZone(ZoneId.of("Pacific/Chatham")).toLocalDateTime().toString();
        currentTimeZoneEntity.pacificTongatapu = instant.atZone(ZoneId.of("Pacific/Tongatapu")).toLocalDateTime().toString();
        currentTimeZoneEntity.pacificKiritimati = instant.atZone(ZoneId.of("Pacific/Kiritimati")).toLocalDateTime().toString();
        return currentTimeZoneEntity;
    }
}
