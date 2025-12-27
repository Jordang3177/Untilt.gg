package Enums;

public enum Region {
    NA1("americas"),
    BR1("americas"),
    LA1("americas"),
    LA2("americas"),
    EUW1("europe"),
    EUN1("europe"),
    TR1("europe"),
    RU("europe"),
    KR("asia"),
    JP1("asia");

    private final String routing;

    Region(String routing) {
        this.routing = routing;
    }

    public String getRouting() {
        return routing;
    }
}
