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
}
