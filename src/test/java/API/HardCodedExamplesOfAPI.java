package API;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class HardCodedExamplesOfAPI {
    //Rest Assured consider baseurl as baseuri
    String baseURI = RestAssured.baseURI = "http://hrm.syntaxtechs.net/syntaxapi/api";
    String token = "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpYXQiOjE2MzkwMTA5MDcsImlzcyI6ImxvY2FsaG9zdCIsImV4cCI6MTYzOTA1NDEwNywidXNlcklkIjoiMzIwOSJ9.oEDRurrM3ZRkAMa8QVKNQ-av_YqtOCc69615XD0k20I";
    static String employee_id;

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
    public void a_createEmployee() {
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
        System.out.println("A response");
        response.prettyPrint(); // same as sout (data.asString());
        //jsonPath() we will use this to get specific information from the json object
        employee_id = response.jsonPath().getString("Employee.employee_id");
        System.out.println("This ID was created : "+employee_id);
        //Then
        response.then().assertThat().statusCode(201);
        response.then().assertThat().body("Employee.emp_firstname", equalTo("Santa")); // import equalTo() from CoreMatchers 'import static org.hamcrest.Matchers.*;' lib
        response.then().body("Employee.emp_middle_name", equalTo("Fanta"));
        response.then().assertThat().body("Message", equalTo("Employee Created"));
        response.then().assertThat().header("Server", "Apache/2.4.39 (Win64) PHP/7.2.18"); // this was taken from Postman Response Header for assertion
    }

    @Test
    public void b_getCreatedEmployee() {
        RequestSpecification preparedRequest = given().header("Authorization", token)
                .header("Content-Type", "application/json").queryParam("employee_id",employee_id);
        Response response = preparedRequest.when().get("/getOneEmployee.php");
        System.out.println("B response");
        response.prettyPrint();
        String empID = response.body().jsonPath().getString("employee.employee_id");
        System.out.println(empID);

        boolean comparingEmpID = empID.contentEquals(employee_id);
        Assert.assertTrue(comparingEmpID);

        String firstName = response.jsonPath().getString("employee.emp_firstname");
        Assert.assertTrue(firstName.contentEquals("Santa"));

        String lastName = response.jsonPath().getString("employee.emp_lastname");
        Assert.assertTrue(lastName.contentEquals("Claus"));

    }
    @Test
    public void c_updatedCreatedEmployee() {
        RequestSpecification preparedRequest = given().header("Authoriazation", token)
                .header("Content-Type", "application/json").body(
                        "{\n" +
                        "  \"employee_id\": \"" + employee_id + "\",\n" +
                        "  \"emp_firstname\": \"Santa\",\n" +
                        "  \"emp_lastname\": \"Claus\",\n" +
                        "  \"emp_middle_name\": \"Banana\",\n" +
                        "  \"emp_gender\": \"M\",\n" +
                        "  \"emp_birthday\": \"1982-12-09\",\n" +
                        "  \"emp_status\": \"Active\",\n" +
                        "  \"emp_job_title\": \"Worker\"\n" +
                        "}");
        Response response = preparedRequest.when().put("/updateEmployee.php");
    }
}
