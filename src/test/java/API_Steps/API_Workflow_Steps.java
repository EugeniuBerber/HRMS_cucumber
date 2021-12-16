package API_Steps;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.Assert;
import utils.API_Constants;
import utils.API_Payload_Constants;

import java.util.List;
import java.util.Map;
import java.util.Set;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;


public class API_Workflow_Steps {
    RequestSpecification request;
    Response response;
    public static String employee_id;

    @Given("a request is prepared for creating an employee")
    public void a_request_is_prepared_for_creating_an_employee() {
        request = given().header(API_Constants.Header_Content_Type, API_Constants.Content_Type)
                .header(API_Constants.Header_Authorization, GenerateTokenSteps.token).body(API_Payload_Constants.CreateEmployeePayload());
    }


    @When("a POST call is made to create an employee")
    public void a_post_call_is_made_to_create_an_employee() {
        response = request.when().post(API_Constants.CREATE_EMPLOYEE_URI);
    }

    @Then("the status code for creating the employee is {int}")
    public void the_status_code_for_creating_the_employee_is(Integer statusCode) {
        response.then().assertThat().statusCode(statusCode);
    }

    @Then("the employee created contains key {string} and value {string}")
    public void the_employee_created_contains_key_and_value(String message, String employeeCreatedValue) {
        response.then().assertThat().body(message, equalTo(employeeCreatedValue));
    }

    @Then("the employee id {string} is stored as a global var to be used for other calls")
    public void the_employee_id_is_stored_as_a_global_var_to_be_used_for_other_calls(String empID) {
        employee_id = response.jsonPath().getString(empID);
        System.out.println("Stored employee_id: " + employee_id);
    }

    @Given("a request is prepared to retrieve the created employee")
    public void a_request_is_prepared_to_retrieve_the_created_employee() {
        request = given().header(API_Constants.Header_Authorization, GenerateTokenSteps.token)
                .header(API_Constants.Header_Content_Type, API_Constants.Content_Type).queryParams("employee_id", employee_id);
    }

    @When("a GET call is made to retrieve the created employee")
    public void a_get_call_is_made_to_retrieve_the_created_employee() {
        response = request.when().get(API_Constants.GET_ONE_EMP_URI);
    }

    @Then("the status code for this employee is {int}")
    public void the_status_code_for_this_employee_is(Integer responseCode) {
        response.then().assertThat().statusCode(responseCode);
    }

    @Then("the retrieved employee ID {string} should match the globally stored employee id")
    public void the_retrieved_employee_id_should_match_the_globally_stored_employee_id(String empID_from_response) {
        String temp_empID = response.jsonPath().getString(empID_from_response); // response.jsonPath() targets just 1 key val
        Assert.assertEquals("ERROR", employee_id, temp_empID);
    }

    @Then("the retrieved data at {string} object matches the data used to create an employee with employee id {string}")
    public void the_retrieved_data_at_object_matches_the_data_used_to_create_an_employee_with_employee_id(String employeeObject, String responseEmpID, DataTable dataTable) {
        List<Map<String, String>> expectedData = dataTable.asMaps(String.class, String.class);
        Map<String, String> actualData = response.body().jsonPath().get(employeeObject); // response.body().jsonPath() targets the whole obj , all keys

        int index = 0;
        for (Map<String, String> map : expectedData) {
            Set<String> keys = map.keySet();
            for (String key : keys) {
                String expectedValue = map.get(key);
                String actualValue = actualData.get(key);
                Assert.assertEquals(expectedValue, actualValue);
            }
            index++;
        }

        String empID = response.body().jsonPath().getString(responseEmpID);
        Assert.assertEquals(empID, employee_id);
    }


    @Given("a request is prepared for creating an employee with dynamic data {string} {string} {string} {string} {string} {string} {string}")
    public void a_Request_IsPrepared_For_Creating_An_Employee_With_Dynamic_Data(String firstName, String lastName, String middleName,
                                                                       String gender, String birthday, String status, String jobTitle) {
        request = given().header(API_Constants.Header_Content_Type, API_Constants.Content_Type)
                .header(API_Constants.Header_Authorization, GenerateTokenSteps.token).body(API_Payload_Constants.payloadMoreDynamic(firstName,lastName,middleName,gender,birthday,status,jobTitle));
        System.out.println(request);
        response = request.when().post(API_Constants.CREATE_EMPLOYEE_URI);
        response.prettyPrint();
    }
}
