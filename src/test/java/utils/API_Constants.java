package utils;

import io.restassured.RestAssured;

public class API_Constants {
    public static final String BaseURI = RestAssured.baseURI = "http://hrm.syntaxtechs.net/syntaxapi/api";
    public static final String CREATE_USER_URI = BaseURI + "/createUser.php";
    public static final String GENERATE_TOKEN_URI = BaseURI + "/generateToken.php";
    public static final String GET_ALL_EMP_URI = BaseURI + "/getAllEmployees.php";
    public static final String GET_ONE_EMP_URI = BaseURI + "/getOneEmployee.php";
    public static final String GET_JOB_TITLE_URI = BaseURI + "/jobTitle.php";
    public static final String GET_EMPLOYMENT_STATUS_URI = BaseURI + "/employeementStatus.php";
    public static final String CREATE_EMPLOYEE_URI = BaseURI + "/createEmployee.php";
    public static final String UPDATE_EMPLOYEE_URI = BaseURI + "/updateEmployee.php";
    public static final String DELETE_EMPLOYEE_URI = BaseURI + "/deleteEmployee.php";
    public static final String UPDATE_PARTIAL_EMPLOYEE_DETAILS_URI = BaseURI + "/updatePartialEmplyeesDetails.php";
    public static final String Header_Content_Type = "Content-Type";
    public static final String Content_Type = "application/json";
    public static final String Header_Authorization = "Authorization";






/*
public class APIConstants {
    public static final String BaseURI = RestAssured.baseURI = "http://hrm.syntaxtechs.net/syntaxapi/api";
    public static final String GENERATE_TOKEN_URI = BaseURI + "/generateToken.php";
    public static final String CREATE_EMPLOYEE_URI = BaseURI + "/createEmployee.php";
    public static final String GET_ONE_EMPLOYEE_URI = BaseURI + "/getOneEmployee.php";
    public static final String UPDATE_EMPLOYEE_URI = BaseURI + "/updateEmployee.php";
    public static final String GET_ALL_EMPLOYEE_URI = BaseURI + "/getAllEmployees.php";
    public static final String DELETE_EMPLOYEE_URI = BaseURI + "/deleteEmployee.php";
    public static final String Header_Content_Type = "Content-Type";
    public static final String Content_Type = "application/json";
    public static final String Header_Authorization = "Authorization";
}
 */


}
