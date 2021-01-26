package apiTests;

import io.restassured.RestAssured;
import io.restassured.http.Header;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.json.simple.JSONObject;
import org.testng.Assert;
import raghvendra.learning.AllBooksPOJO;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author
 * @author Last updated by : $
 * @version : $
 */

public class E2E_Tests {
    public static void main(String[] args) {
        String username = "TOOLSQA-Test";
        String password = "Test@123";
        String baseURI = "https://bookstore.toolsqa.com";

        RestAssured.baseURI = baseURI;
        RequestSpecification request = RestAssured.given();
        request.header("Content-Type", "application/json");

        Map<String,String> login = new HashMap<String,String>();
        login.put("userName",username);
        login.put("password",password);
        request.body(login);

        Response response = request.post("/Account/v1/GenerateToken");
        JsonPath jsonResponse = response.jsonPath();

        Assert.assertEquals(response.getStatusCode(),200);
        System.out.println("Status Code = 200");
        Assert.assertTrue(response.asString().contains("token"));
        System.out.println("Token Present");

        String token = jsonResponse.get("token");
        System.out.println("Token = "+token);

        response = request.get("/BookStore/v1/Books");
        Assert.assertEquals(response.getStatusCode(),200);
        System.out.println("All books status = 200");

        List<AllBooksPOJO> allBooks = response.jsonPath().getList("books",AllBooksPOJO.class);
        Assert.assertTrue(allBooks.size()>0);
        System.out.println("Allbooks size greater than 0");

        final String bookISBNToRent = allBooks.get(0).getIsbn();
        System.out.println("Book ISBN="+bookISBNToRent);

        request.header("Authorization","Bearer "+token);
        request.header("Content-Type","application/json");

        JSONObject body=new JSONObject();
        body.put("userId","123456");
        body.put("collectionOfIsbns",Arrays.asList(new HashMap<String,Object>(){
            {
                put("isbn",bookISBNToRent);
            }
        }));

        request.body(body.toJSONString()).post("/BookStore/v1/Books");
        Assert.assertEquals(response.getStatusCode(),200);
        System.out.println("Book added with Auth");


        //Step - 5
        // Get User
        request.header("Authorization", "Bearer " + token)
                .header("Content-Type", "application/json");

        response = request.get("/Account/v1/User/123456");
        Assert.assertEquals(200, response.getStatusCode());

        String respon = response.asString();
        List<Map<String, String>> booksOfUser = JsonPath.from(respon).get("books");
        Assert.assertEquals(0, booksOfUser.size());
    }
}
