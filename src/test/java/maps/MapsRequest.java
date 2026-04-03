package maps;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.File;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import static payloads.MapsPayloads.*;

public class MapsRequest {
    private static final Logger log = LoggerFactory.getLogger(MapsRequest.class);
    String newAddress = "address";
    @Test
    public void testMapAPI() {

        File jsonFile = new File("G:\\t\\route course\\API Testing\\RestAssured_01\\src\\test\\resources\\addPlace.json");
        RestAssured.baseURI = "https://rahulshettyacademy.com/";
        // Add place post request
        String addPlaceResponse = given().log().all().queryParam("key", "qaclick123")
                .header("content-type", "application/json")
                .body(jsonFile)
                .when().post("maps/api/place/add/json")
                .then().assertThat().statusCode(200)
                .assertThat().body("scope", equalTo("APP"))
                .extract().response().asString();
        System.out.println(addPlaceResponse);

        // Extract place_id from the response
        JsonPath jsonPath = new JsonPath(addPlaceResponse);
        String placeID = jsonPath.getString("place_id");
        System.out.println("Place ID: " + placeID);


        // Update place put request
        given().queryParam("key","qaclick123")
                .queryParam("place_id", placeID)
                .header("content-type","application/json")
                .body(updatePlaceRequestBody(placeID,newAddress))
                .when().put("maps/api/place/update/json")
                .then().assertThat().statusCode(200)
                .log().all();

        // Get place to verify the update
        String getPlaceResponse = given().queryParam("key","qaclick123")
                .queryParam("place_id", placeID)
                .header("content-type","application/json")
                .when().get("maps/api/place/get/json")
                .then().assertThat().statusCode(200)
                .body("address", equalTo(newAddress))
                .extract().response().asString();
        System.out.println(getPlaceResponse);
        jsonPath = new JsonPath(getPlaceResponse);
        String actualAddress = jsonPath.getString("address");
        Assert.assertEquals(actualAddress, newAddress,"addresses don't match");

        // Delete place request
//        given().queryParam("key","qaclick123")
//                .header("content-type","application/json")
//                .body(deletePlaceRequestBody(placeID))
//                .when().delete("maps/api/place/delete/json")
//                .then().assertThat().statusCode(200)
//                .log().all();

    }
}
