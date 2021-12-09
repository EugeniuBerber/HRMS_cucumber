package API;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class HardCodedExamplesOfAPI {
    //Rest Assured consider baseurl as baseuri
    String baseURI = RestAssured.baseURI = "http://hrm.syntaxtechs.net/syntaxapi/api";
    String token = "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpYXQiOjE2Mzg5MjU5ODgsImlzcyI6ImxvY2FsaG9zdCIsImV4cCI6MTYzODk2OTE4OCwidXNlcklkIjoiMzIyNiJ9.QrqPJLS-qKG5Ag2zqKrFaiNOTzHpWz3bFOp02bMlGUA";
    String employee_id;

    @Test
    public void sampleTest() {
        //Given
        RequestSpecification prepareRequest = given().header("Authorization", token)
                .header("Content-Type", "application/json").queryParam("employee_id", "25241A");

        //When hitting the endpoint
        Response response = prepareRequest.when().get("/getOneEmployee.php");
        System.out.println(response.asString());

        //Then - result/assertion
        response.then().assertThat().statusCode(200);
    }

    @Test
    public void createEmployee() {
        //Given
        RequestSpecification requestSpecification = given().header("Authorization", token)
                .header("Content-Type", "application/json").body(
                        "{\n" +
                                "  \"emp_firstname\": \"Santa\",\n" +
                                "  \"emp_lastname\": \"Claus\",\n" +
                                "  \"emp_middle_name\": \"Fanta\",\n" +
                                "  \"emp_gender\": \"M\",\n" +
                                "  \"emp_birthday\": \"2021-12-08\",\n" +
                                "  \"emp_status\": \"Active\",\n" +
                                "  \"emp_job_title\": \"Postman\"\n" +
                                "}"
                );
        //When
        Response response = requestSpecification.when().post("/createEmployee.php");
        response.prettyPrint(); // same as sout (data.asString());
        //jsonPath() we will use this to get specific information from the json object
        employee_id = response.jsonPath().getString("Employee.employee_id");
        System.out.println(employee_id);
        //Then
        response.then().assertThat().statusCode(201);
        response.then().assertThat().body("Employee.emp_firstname", equalTo("Santa")); // import equalTo() from CoreMatchers 'import static org.hamcrest.Matchers.*;' lib
        response.then().body("Employee.emp_middle_name", equalTo("Fanta"));
        response.then().assertThat().body("Message", equalTo("Employee Created"));
        response.then().assertThat().header("Server", "Apache/2.4.39 (Win64) PHP/7.2.18"); // this was taken from Postman Response Header for assertion
    }


}
