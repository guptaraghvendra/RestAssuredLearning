package raghvendra.learning;

import io.restassured.RestAssured;
import io.restassured.internal.path.json.mapping.JsonObjectDeserializer;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import io.restassured.specification.RequestSpecification;
import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * @author
 * @author Last updated by : $
 * @version : $
 */

public class SimplePostTest {
    @Test
    public void simplePost(){
        RestAssured.baseURI = "https://jsonplaceholder.typicode.com/";
        RequestSpecification httpRequest = RestAssured.given();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("title","foo");
        jsonObject.put("body","bar");
        jsonObject.put("userId","1");
        Response response = httpRequest.body(jsonObject.toJSONString()).header("Content-type","application/json; charset=UTF-8").post("/posts");
        System.out.println("-----------------------------------------------");
        System.out.println("RESPONSE");
        System.out.println("-----------------------------------------------");
        response.prettyPrint();
        ResponseBody body = response.getBody();
        JsonPlaceHolderPostSuccessResponse responseBody = body.as(JsonPlaceHolderPostSuccessResponse.class);
        Assert.assertEquals(responseBody.title,"foo");
        Assert.assertEquals(responseBody.body,"bar");
        Assert.assertEquals(responseBody.userId,"1");
        Assert.assertEquals(responseBody.id,101);
    }
}
