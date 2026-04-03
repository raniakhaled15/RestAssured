import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static payloads.MapsPayloads.deletePlaceRequestBody;

public class Task1 {
    @Test
        public void deletePlaceRequest() {
    // Delete place request
    given().queryParam("key", "qaclick123")
            .header("content-type", "application/json")
            .body(deletePlaceRequestBody("3a71bc3e3c62fa79e66028231035c8a9"))
            .when().delete("https://rahulshettyacademy.com/maps/api/place/delete/json")
            .then().assertThat().statusCode(200)
            .log().all();
}
}
