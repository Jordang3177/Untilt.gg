package Utils;

import Enums.Region;

public class RegionUtil {
    public RegionUtil() {}

    public String getRegionUrl(Region region) {
        switch(region) {
            case NA1 -> { return Config.API_NA1_REGION_BASEURL; }
            case BR1 -> { return Config.API_BR1_REGION_BASEURL; }
            case LA1 -> { return Config.API_LA1_REGION_BASEURL; }
            case LA2 -> { return Config.API_LA2_REGION_BASEURL; }
            case EUW1 -> { return Config.API_EUW1_REGION_BASEURL; }
            case EUN1 -> { return Config.API_EUN1_REGION_BASEURL; }
            case TR1 -> { return Config.API_TR1_REGION_BASEURL; }
            case RU -> { return Config.API_RU_REGION_BASEURL; }
            case KR -> { return Config.API_KR_REGION_BASEURL; }
            case JP1 -> { return Config.API_JP1_REGION_BASEURL; }
            default -> throw new IllegalArgumentException("Region Provided is not yet implemented for the System");
        }
    }

    public String getClusterUrl(Region region) {
        switch(region) {
            case NA1, BR1, LA1, LA2 -> { return Config.API_AMERICAS_CLUSTER_BASEURL; }
            case EUW1, EUN1, TR1, RU -> { return Config.API_EUROPE_CLUSTER_BASEURL; }
            case KR, JP1 -> { return Config.API_ASIA_CLUSTER_BASEURL; }
            default -> throw new IllegalArgumentException("Current Region is not yet implemented for the System");
        }
    }
}
