package API;

import static io.restassured.RestAssured.*;

import API_Steps.GenerateTokenSteps;
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


public class test {
    //rest assured considers based url as base uri
    //base uri - hrm.syntaxtechs.net/syntaxapi/api
    String baseURI = RestAssured.baseURI = "http://hrm.syntaxtechs.net/syntaxapi/api";
    String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpYXQiOjE2MzkwMDMwMjYsImlzcyI6ImxvY2FsaG9zdCIsImV4cCI6MTYzOTA0NjIyNiwidXNlcklkIjoiMzIyOCJ9.sqr3rt-qrS_tbGGsJKBxCvg_ka70kQvUTSktCPQl3Bs";
    static String employee_id;


    @Test
    public void sampleTest() {


        RequestSpecification prepareRequest = given().header("Authorization", token)
                .header("Content-Type", "application/json").queryParam("employee_id", "25570A");

//when part - hitting the end point
        Response response = prepareRequest.when().get("/getOneEmployee.php");


        System.out.println(response.asString());
        //pretty print------->same role as  System.out.println
        response.prettyPrint();



        response.then().assertThat().statusCode(200);
        //verifying that header "Content-Type" is "application/json"
        response.then().assertThat().header("Content-Type", "application/json");
//etc...
    }

    @Test
    public void aCreateEmployee() {
        //prepareRequest to create an employee
        RequestSpecification preaperedRequest = given().header("Authorization", token)
                .header("Content-Type", "application/json").body("{\n" +
                        "  \"emp_firstname\": \"FirstName\",\n" +
                        "  \"emp_lastname\": \"Lastname\",\n" +
                        "  \"emp_middle_name\": \"N/A\",\n" +
                        "  \"emp_gender\": \"M\",\n" +
                        "  \"emp_birthday\": \"1989-12-04\",\n" +
                        "  \"emp_status\": \"Employee \",\n" +
                        "  \"emp_job_title\": \"API Tester\"\n" +
                        "}");

//when ----------> making a call to create an employee
        Response response = preaperedRequest.when().post("/createEmployee.php");

        //to print a response
        response.prettyPrint();
        //this pretty print does the same job as syso. // sout(response.asString());


        employee_id = response.jsonPath().getString("Employee.employee_id");

        //to print employee_id in console
        System.out.println(employee_id);

        //then

        response.then().assertThat().statusCode(201);

        response.then().assertThat().body("Employee.emp_firstname", equalTo("FirstName"));

        response.then().assertThat().body("Message", equalTo("Employee Created"));

        response.then().assertThat().header("Server", "Apache/2.4.39 (Win64) PHP/7.2.18");

    }

    @Test
    public void bGetCreatedEmployee() {

        RequestSpecification preaperedRequest = given().header("Authorization", token)
                .header("Content-Type", "application/json").queryParam("employee_id", employee_id);

        Response response = preaperedRequest.when().get("/getOneEmployee.php");
        response.prettyPrint();
        String empId = response.body().jsonPath().getString("employee.employee_id");

        boolean comparingEmpId = empId.contentEquals(employee_id);
        Assert.assertTrue(comparingEmpId);

        String firstName = response.jsonPath().getString("employee.emp_firstname");
        Assert.assertTrue(firstName.contentEquals("FirstName"));

        String lastName = response.jsonPath().getString("employee.emp_lastname");
        Assert.assertTrue(lastName.contentEquals("Lastname"));



    }
    @Test
    public void cUpdateCreatedEmployee() {
        RequestSpecification preaperedRequest = given().header("Authorization", token)
                .header("Content-Type", "application/json").body("{\n" +
                        "\"employee_id\":\"" + employee_id + "\",\n" +
                        "  \"emp_firstname\": \"Sohail\",\n" +
                        "  \"emp_lastname\": \"Abassi\",\n" +
                        "  \"emp_middle_name\": \"TheBest\",\n" +
                        "  \"emp_gender\": \"F\",\n" +
                        "  \"emp_birthday\": \"1988-12-04\",\n" +
                        "  \"emp_status\": \"Employee \",\n" +
                        "  \"emp_job_title\": \"Cloud Consultant\"\n" +
                        "}");

        Response response = preaperedRequest.when().put("/updateEmployee.php");
        response.prettyPrint();
    }

}