Feature: Syntax HRMS API workflow

  Background:
    Given a JWT is generated

  @APIWorkflow
  Scenario: Creating an employee
    Given a request is prepared for creating an employee
    When a POST call is made to create an employee
    Then the status code for creating the employee is 201
    And the employee created contains key "Message" and value "Employee Created"
    And the employee id "Employee.employee_id" is stored as a global var to be used for other calls
  @APIWorkflow
  Scenario: Retrieving created employee
    Given a request is prepared to retrieve the created employee
    When a GET call is made to retrieve the created employee
    Then the status code for this employee is 200
    And the retrieved employee ID "employee.employee_id" should match the globally stored employee id
    And the retrieved data at "employee" object matches the data used to create an employee with employee id "employee.employee_id"
    |emp_firstname|emp_middle_name|emp_lastname|emp_birthday|emp_gender|emp_job_title|emp_status|
    |SANTA        |MOROZ          |CLAUS       |1925-12-27  |Male         |Gifter       |Active    |
    @DynamicAPI
    Scenario: Create dynamic scenario
      Given a request is prepared for creating an employee with dynamic data "Sneguro4ka" "Morozovna" "Dedovna" "F" "1955-12-27" "Active" "Helper"
