package Utils;

public final class LeagueEndpoints {

    public static String championRotation() {
        return "/lol/platform/v3/champion-rotations";
    }

    public static String accountByRiotId() {
        return "/riot/account/v1/accounts/by-riot-id/";
    }

    public static String matchesByUserId() {
        return "/lol/match/v5/matches/by-puuid/";
    }

    public static String matchByMatchId() {
        return "/lol/match/v5/matches/";
    }

    public static String rankByUserId() {
        return "/lol/league/v4/entries/by-puuid/";
    }

    public static String accountByUserId() {
        return "/riot/account/v1/accounts/by-puuid/";
    }
}
