package Utils;

import java.util.Properties;
import java.io.FileInputStream;
import java.io.IOException;

public final class Config {
    private static final Properties PROPS = new Properties();

    static {
        try (FileInputStream fileInputStream = new FileInputStream("config.properties")) {
            PROPS.load(fileInputStream);
        }
        catch (IOException e) {
            throw new RuntimeException("Failed to load config.properties File");
        }
    }

    private Config() {}

    public static final int API_TIMEOUT = Integer.parseInt(PROPS.getProperty("API_TIMEOUT"));
    public static final String API_KEY = PROPS.getProperty("API_KEY");
    public static final String API_NA1_REGION_BASEURL = PROPS.getProperty("API_NA1_REGION_BASEURL");
    public static final String API_BR1_REGION_BASEURL = PROPS.getProperty("API_BR1_REGION_BASEURL");
    public static final String API_LA1_REGION_BASEURL = PROPS.getProperty("API_LA1_REGION_BASEURL");
    public static final String API_LA2_REGION_BASEURL = PROPS.getProperty("API_LA2_REGION_BASEURL");
    public static final String API_EUW1_REGION_BASEURL = PROPS.getProperty("API_EUW1_REGION_BASEURL");
    public static final String API_EUN1_REGION_BASEURL = PROPS.getProperty("API_EUN1_REGION_BASEURL");
    public static final String API_TR1_REGION_BASEURL = PROPS.getProperty("API_TR1_REGION_BASEURL");
    public static final String API_RU_REGION_BASEURL = PROPS.getProperty("API_RU_REGION_BASEURL");
    public static final String API_KR_REGION_BASEURL = PROPS.getProperty("API_KR_REGION_BASEURL");
    public static final String API_JP1_REGION_BASEURL = PROPS.getProperty("API_JP1_REGION_BASEURL");
    public static final String API_AMERICAS_CLUSTER_BASEURL = PROPS.getProperty("API_AMERICAS_CLUSTER_BASEURL");
    public static final String API_EUROPE_CLUSTER_BASEURL = PROPS.getProperty("API_EUROPE_CLUSTER_BASEURL");
    public static final String API_ASIA_CLUSTER_BASEURL = PROPS.getProperty("API_ASIA_CLUSTER_BASEURL");
    public static final String AZURE_COSMOS_DB_URI = PROPS.getProperty("AZURE_COSMOS_DB_URI");
    public static final String AZURE_COSMOS_DB_PRIMARY_KEY_READ_WRITE = PROPS.getProperty("AZURE_COSMOS_DB_PRIMARY_KEY_READ_WRITE");
    public static final String AZURE_COSMOS_DB_SECONDARY_KEY_READ_WRITE = PROPS.getProperty("AZURE_COSMOS_DB_SECONDARY_KEY_READ_WRITE");
    public static final String AZURE_COSMOS_DB_PRIMARY_CONNECTION_STRING_READ_WRITE = PROPS.getProperty("AZURE_COSMOS_DB_PRIMARY_CONNECTION_STRING_READ_WRITE");
    public static final String AZURE_COSMOS_DB_SECONDARY_CONNECTION_STRING_READ_WRITE = PROPS.getProperty("AZURE_COSMOS_DB_SECONDARY_CONNECTION_STRING_READ_WRITE");
    public static final String AZURE_COSMOS_DB_PRIMARY_KEY_READ_ONLY = PROPS.getProperty("AZURE_COSMOS_DB_PRIMARY_KEY_READ_ONLY");
    public static final String AZURE_COSMOS_DB_SECONDARY_KEY_READ_ONLY = PROPS.getProperty("AZURE_COSMOS_DB_SECONDARY_KEY_READ_ONLY");
    public static final String AZURE_COSMOS_DB_PRIMARY_CONNECTION_STRING_READ_ONLY = PROPS.getProperty("AZURE_COSMOS_DB_PRIMARY_CONNECTION_STRING_READ_ONLY");
    public static final String AZURE_COSMOS_DB_SECONDARY_CONNECTION_STRING_READ_ONLY = PROPS.getProperty("AZURE_COSMOS_DB_SECONDARY_CONNECTION_STRING_READ_ONLY");
}
