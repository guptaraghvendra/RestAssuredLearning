package raghvendra.learning;

import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * @author
 * @author Last updated by : $
 * @version : $
 */

public class SimpleGetTest {

    @Test
    public void checkStatusPositive() {
        RestAssured.baseURI = "https://api.themoviedb.org/3/movie";
        RequestSpecification httpRequest = RestAssured.given();
        Response response = httpRequest.request(Method.GET,"/550?api_key=ac8780e36db533b6a1396da8f1324e49");
        int status = response.getStatusCode();
        Assert.assertEquals(status,200,"200 returned");
    }
    @Test
    public void checkStatusNegative() {
        RestAssured.baseURI = "https://api.themoviedb.org/3/movie";
        RequestSpecification httpRequest = RestAssured.given();
        Response response = httpRequest.request(Method.GET,"/0?api_key=ac8780e36db533b6a1396da8f1324e49");
        int status = response.getStatusCode();
        Assert.assertEquals(status,404,"404 returned");
    }

    @Test
    public void getHeaders(){
        RestAssured.baseURI="https://api.themoviedb.org/3/movie";
        RequestSpecification httpRequest = RestAssured.given();
        Response response = httpRequest.request(Method.GET,"/0?api_key=ac8780e36db533b6a1396da8f1324e49");
        String contentType = response.getHeader("Content-Type");
        String contentEncoding = response.getHeader("Content-Encoding");
        System.out.println("Content-Type " + contentType + "Encoding type "+contentEncoding);
    }
    @Test
    public void validateMovieName(){
        RestAssured.baseURI = "https://api.themoviedb.org/3/movie";
        RequestSpecification httpRequest = RestAssured.given();
        Response response = httpRequest.request(Method.GET,"/550?api_key=ac8780e36db533b6a1396da8f1324e49");
        JsonPath jsonData = response.jsonPath();
        String movieTitle = jsonData.get("original_title");
        Assert.assertEquals(movieTitle,"Fight Club","Movie name is same");
    }
    @Test
    public void requestWithParameters(){
        RestAssured.baseURI = "https://api.themoviedb.org/3/movie";
        Response response = RestAssured.given().queryParam("api_key","ac8780e36db533b6a1396da8f1324e49").get("/550");
        JsonPath jsonResponse = response.jsonPath();
        System.out.println(response.getStatusCode());
        Assert.assertEquals(jsonResponse.get("original_title"),"Fight Club");

    }
}
