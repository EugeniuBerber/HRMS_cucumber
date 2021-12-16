package utils;

import org.json.JSONObject;

public class API_Payload_Constants {
    public static String CreateEmployeePayload() {
        return "{\n" +
                "  \"emp_firstname\": \"SANTA\",\n" +
                "  \"emp_lastname\": \"CLAUS\",\n" +
                "  \"emp_middle_name\": \"MOROZ\",\n" +
                "  \"emp_gender\": \"M\",\n" +
                "  \"emp_birthday\": \"1925-12-27\",\n" +
                "  \"emp_status\": \"Active\",\n" +
                "  \"emp_job_title\": \"Gifter\"\n" +
                "}";
    }

    public static String createEmployeeBody() {
        JSONObject obj = new JSONObject();
        obj.put("emp_firstname", "SANTA");
        obj.put("emp_lastname", "CLAUS");
        obj.put("emp_middle_name", "MOROZ");
        obj.put("emp_gender", "M");
        obj.put("emp_birthday", "1925-12-27");
        obj.put("emp_status", "Active");
        obj.put("emp_job_title", "Gifter");

        return obj.toString();
    }

    public static String payloadMoreDynamic(String emp_firstname, String emp_lastname, String emp_middle_name,
                                            String emp_gender, String emp_birthday, String emp_status, String job_title) {
        JSONObject obj = new JSONObject();
        obj.put("emp_firstname", emp_firstname);
        obj.put("emp_lastname", emp_lastname);
        obj.put("emp_middle_name", emp_middle_name);
        obj.put("emp_gender", emp_gender);
        obj.put("emp_birthday", emp_birthday);
        obj.put("emp_status", emp_status);
        obj.put("emp_job_title", job_title);

        return obj.toString();
    }
}

