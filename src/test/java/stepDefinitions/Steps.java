package stepDefinitions;

import apiEngine.Endpoints;
import apiEngine.model.Book;
import apiEngine.model.requests.AddBooksRequest;
import apiEngine.model.requests.AuthorizationRequest;
import apiEngine.model.ISBN;
import apiEngine.model.requests.RemoveBookRequest;
import apiEngine.model.response.Token;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.Assert;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author
 * @author Last updated by : $
 * @version : $
 */

public class Steps {
    private static final String userID="f41d053f-b01b-42ce-8ed8-86ebf61c0d9d";
    private static final String userName = "Raghav1";
    private static final String password = "P@55word";



    private static Token token;
    private static Response response;
    private static Book bookISBN;


    @Given("^I am an authorized user$")
    public void i_am_an_authorized_user() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        AuthorizationRequest username_password = new AuthorizationRequest(userName,password);
        response = Endpoints.authenticateUser(username_password);
        token = response.getBody().as(Token.class);

    }

    @Given("^A list of books are available$")
    public void a_list_of_books_are_available() throws Throwable {
        // Write code here that turns the phrase above into concrete actions

        List<Map<String,String>> books = request.get("/BookStore/v1/Books").jsonPath().getList("books");
        bookISBN=books.get(0).get("isbn");
        //bookISBN1=books.get(1).get("isbn");

        System.out.println("Total Books="+books.size());
        System.out.println("Book ISBN store="+bookISBN);
    }

    @When("^I add a book to my reading list$")
    public void i_add_a_book_to_my_reading_list() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        RestAssured.baseURI=URI;
        RequestSpecification request = RestAssured.given();

        Map<String,String> head = new HashMap<String,String>();
        head.put("accept","application/json");
        //head.put("authorization","Basic UmFnaGF2OlBANTV3b3Jk");
        head.put("Authorization","Bearer "+token);
        head.put("Content-Type","application/json");
        request.headers(head);

        ISBN isbn = new ISBN(bookISBN);
        AddBooksRequest addBook = new AddBooksRequest(userID,isbn);
        request.body(addBook);//.log().all();
        response = request.post("/BookStore/v1/Books");
        System.out.println("Book Added");

    }

    @Then("^the book is added$")
    public void the_book_is_added() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        //Assert.assertEquals(response.statusCode(),400);
        System.out.println("books added");
    }

    @When("^I remove a book from my reading list$")
    public void i_remove_a_book_from_my_reading_list() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        RestAssured.baseURI=URI;
        RequestSpecification request = RestAssured.given();

        Map<String,String> head = new HashMap<String,String>();
        head.put("accept","application/json");
        head.put("Authorization","Bearer "+token);
        head.put("Content-Type","application/json");
        request.headers(head);

        ISBN isbn = new ISBN(bookISBN);
        RemoveBookRequest removeBook = new RemoveBookRequest(userID,isbn);
        request.body(removeBook);
        response = request.delete("/BookStore/v1/Book");
        System.out.println("Delete Message = "+response.prettyPrint());
    }

    @Then("^the book is removed$")
    public void the_book_is_removed() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        RestAssured.baseURI=URI;
        RequestSpecification request = RestAssured.given();
        request.header("accept","application/json").header("Authorization","Bearer "+token);
        response = request.get("/Account/v1/User/"+userID);
        System.out.println("User Details ="+response.prettyPrint());

        JsonPath jsonResponse = response.jsonPath();
        List<Map<String,String>> listOfBooks = jsonResponse.getList("books");
        Assert.assertTrue(listOfBooks.size()==0);
    }

}
