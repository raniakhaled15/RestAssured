package Library;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static Library.LibraryPayloads.addBookRequestBody;
import static commons.utils.rawToJson;
import static io.restassured.RestAssured.given;

public class AddBook {

    @Test(dataProvider =  "bookData")
    public void addBookRequest(String bookName, String aisle) {
        RestAssured.baseURI = "https://rahulshettyacademy.com/";
        String response = given().log().all()
                .body(addBookRequestBody(bookName, aisle))
                .when().post("Library/Addbook.php")
                .then().assertThat().statusCode(200)
                .log().body().extract().response().asString();
        JsonPath  jsonPath = rawToJson(response);
        String bookID = jsonPath.getString("ID");
        System.out.println("Book ID: " + bookID);

    }
    @DataProvider(name = "bookData")
    public Object[][] getBookData() {
        return new Object[][] {
                {"Java","012"},
                {"Python","013"},
                {"Cs","014"},
                {"JS","015"}
        };
    }
}
