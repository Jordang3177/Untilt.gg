import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonUtil {
    private static final ObjectMapper MAPPER = new ObjectMapper();

    public static JsonNode parse(String json) {
        try {
            return MAPPER.readTree(json);
        }
        catch (Exception e) {
            throw new RuntimeException("Invalid JSON", e);
        }
    }
}
