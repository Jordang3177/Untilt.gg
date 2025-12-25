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
}
