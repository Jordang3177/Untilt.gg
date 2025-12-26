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
    public static final String API_REGION_BASEURL = PROPS.getProperty("API_REGION_BASEURL");
    public static final String API_CLUSTER_BASEURL = PROPS.getProperty("API_CLUSTER_BASEURL");
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
