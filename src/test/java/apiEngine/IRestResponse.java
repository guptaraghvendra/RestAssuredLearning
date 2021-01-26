package apiEngine;

import io.restassured.response.Response;

/**
 * @author
 * @author Last updated by : $
 * @version : $
 */

public interface IRestResponse<T> {
    public T getBody();
    public String getContent();
    public int getStatusCode();
    public String getStatusMessage();
    public boolean isSuccessful();
    public Response getResponse();
    public Exception getException();

}
