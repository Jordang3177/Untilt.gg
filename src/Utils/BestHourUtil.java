package Utils;

import Entities.HourlyWinRateEntity;
import Entities.MatchEntity;
import Entities.PlayerEntity;
import Entities.TimeZoneEntity;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

public class BestHourUtil {

    public BestHourUtil() {}

    public HourlyWinRateEntity buildHourlyWinRates(String playerUUID, List<MatchEntity> matches, ZoneId playerZone) {
        int[] wins = new int[24];
        int[] games = new int[24];
        HourlyWinRateEntity hourlyWinRateEntity = new HourlyWinRateEntity();

        for (MatchEntity currentMatchEntity : matches) {
            int hour = resolveHourForPlayerZone(playerZone, currentMatchEntity.timeZoneEntity);

            games[hour]++;
            for(PlayerEntity currentPlayerEntity : currentMatchEntity.winners) {
                if(currentPlayerEntity.riotPlayerId.equals(playerUUID)) {
                    wins[hour]++;
                    hourlyWinRateEntity.playerEntity = currentPlayerEntity;
                }
            }
        }

        for (int h = 0; h < 24; h++) {
            double normalized = (double) wins[h] / games[h];
            double percentage = normalized * 100;
            setHour(hourlyWinRateEntity, h, percentage, wins, games);
        }

        return hourlyWinRateEntity;
    }

    private void setHour(HourlyWinRateEntity hourlyWinRateEntity, int hour, double value, int[] wins, int[] games) {
        switch (hour) {
            case 0 -> { hourlyWinRateEntity.h00Percent = value; hourlyWinRateEntity.h00Wins = wins[hour]; hourlyWinRateEntity.h00Games = games[hour]; }
            case 1 -> { hourlyWinRateEntity.h01Percent = value; hourlyWinRateEntity.h01Wins = wins[hour]; hourlyWinRateEntity.h01Games = games[hour]; }
            case 2 -> { hourlyWinRateEntity.h02Percent = value; hourlyWinRateEntity.h02Wins = wins[hour]; hourlyWinRateEntity.h02Games = games[hour]; }
            case 3 -> { hourlyWinRateEntity.h03Percent = value; hourlyWinRateEntity.h03Wins = wins[hour]; hourlyWinRateEntity.h03Games = games[hour]; }
            case 4 -> { hourlyWinRateEntity.h04Percent = value; hourlyWinRateEntity.h04Wins = wins[hour]; hourlyWinRateEntity.h04Games = games[hour]; }
            case 5 -> { hourlyWinRateEntity.h05Percent = value; hourlyWinRateEntity.h05Wins = wins[hour]; hourlyWinRateEntity.h05Games = games[hour]; }
            case 6 -> { hourlyWinRateEntity.h06Percent = value; hourlyWinRateEntity.h06Wins = wins[hour]; hourlyWinRateEntity.h06Games = games[hour]; }
            case 7 -> { hourlyWinRateEntity.h07Percent = value; hourlyWinRateEntity.h07Wins = wins[hour]; hourlyWinRateEntity.h07Games = games[hour]; }
            case 8 -> { hourlyWinRateEntity.h08Percent = value; hourlyWinRateEntity.h08Wins = wins[hour]; hourlyWinRateEntity.h08Games = games[hour]; }
            case 9 -> { hourlyWinRateEntity.h09Percent = value; hourlyWinRateEntity.h09Wins = wins[hour]; hourlyWinRateEntity.h09Games = games[hour]; }
            case 10 -> { hourlyWinRateEntity.h10Percent = value; hourlyWinRateEntity.h10Wins = wins[hour]; hourlyWinRateEntity.h10Games = games[hour]; }
            case 11 -> { hourlyWinRateEntity.h11Percent = value; hourlyWinRateEntity.h11Wins = wins[hour]; hourlyWinRateEntity.h11Games = games[hour]; }
            case 12 -> { hourlyWinRateEntity.h12Percent = value; hourlyWinRateEntity.h12Wins = wins[hour]; hourlyWinRateEntity.h12Games = games[hour]; }
            case 13 -> { hourlyWinRateEntity.h13Percent = value; hourlyWinRateEntity.h13Wins = wins[hour]; hourlyWinRateEntity.h13Games = games[hour]; }
            case 14 -> { hourlyWinRateEntity.h14Percent = value; hourlyWinRateEntity.h14Wins = wins[hour]; hourlyWinRateEntity.h14Games = games[hour]; }
            case 15 -> { hourlyWinRateEntity.h15Percent = value; hourlyWinRateEntity.h15Wins = wins[hour]; hourlyWinRateEntity.h15Games = games[hour]; }
            case 16 -> { hourlyWinRateEntity.h16Percent = value; hourlyWinRateEntity.h16Wins = wins[hour]; hourlyWinRateEntity.h16Games = games[hour]; }
            case 17 -> { hourlyWinRateEntity.h17Percent = value; hourlyWinRateEntity.h17Wins = wins[hour]; hourlyWinRateEntity.h17Games = games[hour]; }
            case 18 -> { hourlyWinRateEntity.h18Percent = value; hourlyWinRateEntity.h18Wins = wins[hour]; hourlyWinRateEntity.h18Games = games[hour]; }
            case 19 -> { hourlyWinRateEntity.h19Percent = value; hourlyWinRateEntity.h19Wins = wins[hour]; hourlyWinRateEntity.h19Games = games[hour]; }
            case 20 -> { hourlyWinRateEntity.h20Percent = value; hourlyWinRateEntity.h20Wins = wins[hour]; hourlyWinRateEntity.h20Games = games[hour]; }
            case 21 -> { hourlyWinRateEntity.h21Percent = value; hourlyWinRateEntity.h21Wins = wins[hour]; hourlyWinRateEntity.h21Games = games[hour]; }
            case 22 -> { hourlyWinRateEntity.h22Percent = value; hourlyWinRateEntity.h22Wins = wins[hour]; hourlyWinRateEntity.h22Games = games[hour]; }
            case 23 -> { hourlyWinRateEntity.h23Percent = value; hourlyWinRateEntity.h23Wins = wins[hour]; hourlyWinRateEntity.h23Games = games[hour]; }
        }
    }

    private int resolveHourForPlayerZone(ZoneId playerZone, TimeZoneEntity timeZoneEntity) {
        String zoneId = playerZone.getId();

        String localDateTimeString = switch (zoneId) {
            case "Etc/GMT+12" -> timeZoneEntity.etcGmtPlus12;
            case "Pacific/Pago_Pago" -> timeZoneEntity.pacificPagoPago;
            case "Pacific/Honolulu" -> timeZoneEntity.pacificHonolulu;
            case "America/Anchorage" -> timeZoneEntity.americaAnchorage;
            case "America/Los_Angeles" -> timeZoneEntity.americaLosAngeles;
            case "America/Denver" -> timeZoneEntity.americaDenver;
            case "America/Chicago" -> timeZoneEntity.americaChicago;
            case "America/New_York" -> timeZoneEntity.americaNewYork;
            case "America/Halifax" -> timeZoneEntity.americaHalifax;
            case "America/St_Johns" -> timeZoneEntity.americaStJohns;
            case "America/Argentina/Buenos_Aires" -> timeZoneEntity.americaBuenosAires;
            case "Atlantic/South_Georgia" -> timeZoneEntity.atlanticSouthGeorgia;
            case "Atlantic/Azores" -> timeZoneEntity.atlanticAzores;
            case "Europe/London" -> timeZoneEntity.europeLondon;
            case "Europe/Paris" -> timeZoneEntity.europeParis;
            case "Africa/Cairo" -> timeZoneEntity.africaCairo;
            case "Europe/Moscow" -> timeZoneEntity.europeMoscow;
            case "Asia/Tehran" -> timeZoneEntity.asiaTehran;
            case "Asia/Dubai" -> timeZoneEntity.asiaDubai;
            case "Asia/Kabul" -> timeZoneEntity.asiaKabul;
            case "Asia/Karachi" -> timeZoneEntity.asiaKarachi;
            case "Asia/Kolkata" -> timeZoneEntity.asiaKolkata;
            case "Asia/Kathmandu" -> timeZoneEntity.asiaKathmandu;
            case "Asia/Dhaka" -> timeZoneEntity.asiaDhaka;
            case "Asia/Yangon" -> timeZoneEntity.asiaYangon;
            case "Asia/Bangkok" -> timeZoneEntity.asiaBangkok;
            case "Asia/Shanghai" -> timeZoneEntity.asiaShanghai;
            case "Asia/Tokyo" -> timeZoneEntity.asiaTokyo;
            case "Australia/Adelaide" -> timeZoneEntity.australiaAdelaide;
            case "Australia/Sydney" -> timeZoneEntity.australiaSydney;
            case "Australia/Lord_Howe" -> timeZoneEntity.australiaLordHowe;
            case "Pacific/Noumea" -> timeZoneEntity.pacificNoumea;
            case "Pacific/Auckland" -> timeZoneEntity.pacificAuckland;
            case "Pacific/Chatham" -> timeZoneEntity.pacificChatham;
            case "Pacific/Tongatapu" -> timeZoneEntity.pacificTongatapu;
            case "Pacific/Kiritimati" -> timeZoneEntity.pacificKiritimati;
            default -> throw new IllegalArgumentException("Unsupported ZoneId: " + zoneId);
        };

        if (localDateTimeString == null)
        {
            throw new IllegalStateException("No datetime stored for zone: " + zoneId);
        }

        return LocalDateTime.parse(localDateTimeString).getHour();
    }
}
