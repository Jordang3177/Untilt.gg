package Utils;

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

    public static void printAllValues(JsonNode node) {
        if(node.isValueNode()) {
            System.out.println(node.asText());
            return;
        }
        if(node.isObject()) {
            node.fields().forEachRemaining(entry -> {
                System.out.println(entry.getKey());
                printAllValues(entry.getValue());
            });
        }
        else if(node.isArray()) {
            for(JsonNode element : node) {
                printAllValues(element);
            }
        }
    }
}
