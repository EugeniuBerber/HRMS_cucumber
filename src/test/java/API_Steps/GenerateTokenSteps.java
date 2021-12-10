package API_Steps;

import io.cucumber.java.en.Given;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.Test;

import static io.restassured.RestAssured.given;

public class GenerateTokenSteps {
    public static String token;
    String baseURI = RestAssured.baseURI = "http://hrm.syntaxtechs.net/syntaxapi/api";

    @Given("a JWT is generated")
    public void a_jwt_is_generated() {
        RequestSpecification generateTokenRequest = given().header("Content-Type", "application/json")
                .body("{\n" +
                        "  \"email\": \"babushka1@gmail.com\",\n" +
                        "  \"password\": \"stringnotstring\"\n" +
                        "}");
        Response generateTokenResponse = generateTokenRequest.when().post("/generateToken.php");
        generateTokenResponse.prettyPrint();
        System.out.println("------------------------------------------------------------------------------------------------------");
        generateTokenResponse.prettyPeek();

        token = "Bearer " + generateTokenResponse.jsonPath().getString("token");
        System.out.println(token);
    }

}
