package Courses;

import io.restassured.path.json.JsonPath;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.Test;

import static commons.utils.rawToJson;
import static payloads.CoursesPayload.getCoursesDetails;

public class complexJson {
    private static final Logger log = LoggerFactory.getLogger(complexJson.class);

    @Test
    public void complexJsonTest() {
        JsonPath jsonPath = rawToJson(getCoursesDetails());

        // print number of courses
        int coursesCount = jsonPath.getInt("courses.size()");
//        System.out.println("coursesCount: " + coursesCount);
        Assert.assertEquals(coursesCount, 3,"coursesCount don't match");

        // print purchase amount
        int purchaseAmount = jsonPath.getInt("dashboard.purchaseAmount");
        System.out.println("Purchases " + purchaseAmount);

        // print first course title
        String firstCourseTitle = "Selenium Python";
        String title = jsonPath.getString("courses[0].title");
        Assert.assertEquals(title, firstCourseTitle, "Titles don't match");

        // print all course titles and their respective prices. loop through courses array
        for (int i = 0; i < coursesCount; i++) {
            String courseTitle = jsonPath.getString("courses[" + i + "].title");
            int CoursePrice = jsonPath.getInt("courses[" + i + "].price");
            System.out.println("Course Num "+i + 1 + " : " + courseTitle + " " + CoursePrice);
        }

        // print no of copies sold by RPA Course
        for (int i = 0; i < coursesCount; i++) {
            String courseTitle = jsonPath.getString("courses[" + i + "].title");
            if(courseTitle.equals("RPA")) {
                int numOfCopies = jsonPath.getInt("courses["+ i +"].copies");
                System.out.println("Num of copies "+ numOfCopies);
            }
        }

        // Verify if Sum of all Course prices matches with Purchase Amount
        int totalPrice = 0;
        for (int i = 0; i < coursesCount; i++) {
            int coursePrice = jsonPath.getInt("courses[" + i + "].price");
            int copies = jsonPath.getInt("courses[" + i + "].copies");
            totalPrice += coursePrice * copies;
        }
        Assert.assertEquals(totalPrice, purchaseAmount, "purchase Amount don't match");
    }
}
