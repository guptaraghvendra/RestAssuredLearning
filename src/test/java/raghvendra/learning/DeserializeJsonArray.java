package raghvendra.learning;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.Test;

import java.util.List;

/**
 * @author
 * @author Last updated by : $
 * @version : $
 */

public class DeserializeJsonArray {
    @Test
    public void deserializeJsonToListArray(){
        RestAssured.baseURI = "https://bookstore.toolsqa.com/BookStore/v1";
        RequestSpecification request = RestAssured.given();
        Response response = request.get("/Books");
        JsonPath jsonPath = response.jsonPath();

        //List<AllBooksPOJO> allBooks = jsonPath.getList("books",AllBooksPOJO.class);
        AllBooksPOJO[] allBooks = response.jsonPath().getObject("books",AllBooksPOJO[].class);
        for(AllBooksPOJO book:allBooks){
            System.out.println("Book Name ="+book.getPublish_date());
        }
    }
}
