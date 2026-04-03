package serialization;

import io.restassured.RestAssured;
import org.testng.annotations.Test;
import pojo.maps.Location;
import pojo.maps.PlaceDetails;

import java.io.File;

import static io.restassured.RestAssured.given;
import static java.util.Arrays.asList;
import static org.hamcrest.Matchers.emptyCollectionOf;
import static org.hamcrest.Matchers.equalTo;

public class MapApi {
    @Test
    public void testMapAPI() {

        RestAssured.baseURI = "https://rahulshettyacademy.com";
        // Add place post request

        Location l = new Location();
        l.setLat(-38.383494);
        l.setLng(33.427362);

        PlaceDetails pl = new PlaceDetails();
        pl.setAccuracy(50);
        pl.setAddress("29, side layout, cohen 09");
        pl.setLanguage("French-IN");
        pl.setName("Frontline house");
        pl.setPhoneNumber("(+91) 983 893 3937");
        pl.setTypes(asList("shoe park", "shop"));
        pl.setLocation(l);
        pl.setWebsite("http://google.com");

        String addPlaceResponse = given().log().all().queryParam("key", "qaclick123")
                .header("content-type", "application/json")
                .body(pl)
                .when().post("/maps/api/place/add/json")
                .then().assertThat().statusCode(200)
                .assertThat().body("scope", equalTo("APP"))
                .log().body()
                .extract().response().asString();
        System.out.println(addPlaceResponse);
    }
}
