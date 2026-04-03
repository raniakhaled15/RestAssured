package deserialization;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pojo.instructorCourses.Instructor;

import static commons.utils.rawToJson;
import static io.restassured.RestAssured.given;

public class GetCourses {
    String accessToken = "";
    @BeforeMethod
    public void beforeMethod() {
        RestAssured.baseURI = "https://rahulshettyacademy.com/";
    }
    @Test
    public void GenerateToken() {

        String response = given().formParams("client_id","692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com",
        "client_secret","erZOWM9g3UtwNRj340YYaK_W",
        "scope","trust",
        "grant_type","client_credentials")
                .when().post("oauthapi/oauth2/resourceOwner/token")
                .then().assertThat().statusCode(200)
                .extract().response().asString();
        JsonPath jsonPath = rawToJson(response);
        accessToken = jsonPath.getString("access_token");
        System.out.println("Access Token: " + accessToken);
    }
    @Test
    public void GetCoursesDetails() {

        Instructor response = given().queryParam("access_token", accessToken)
                .when().get("oauthapi/getCourseDetails")
                .then().assertThat().statusCode(401)
                .log().all()
                .extract().response().as(Instructor.class);
        System.out.println(response.getCourses().getWebAutomation().get(0).getCourseTitle());
        System.out.println(response.getCourses().getWebAutomation().get(1).getPrice());
        int numberOfCourses = response.getCourses().getApi().size();
        System.out.println("Number of API Courses: " + numberOfCourses);

        for (int i = 0; i < numberOfCourses; i++) {
            String title = response.getCourses().getApi().get(i).getCourseTitle();
            String price = response.getCourses().getApi().get(i).getPrice();
            if(title.equals("SoapUI Webservices testing")){
                Assert.assertEquals(price,"40");
                break;
            }
        }
    }
}
