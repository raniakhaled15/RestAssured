package commons;

import io.restassured.path.json.JsonPath;
import io.restassured.path.json.config.JsonPathConfig;

public class utils {
    // Takes raw json response as a string and converts it into a jsonPath object for querying.
    public static JsonPath rawToJson(String response) {
    JsonPath jsonPath = new JsonPath(response);
    return jsonPath;
    }
}
