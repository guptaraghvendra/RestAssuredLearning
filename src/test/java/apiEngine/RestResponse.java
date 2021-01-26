package apiEngine;

import io.restassured.response.Response;

/**
 * @author
 * @author Last updated by : $
 * @version : $
 */

public class RestResponse<T> implements IRestResponse{
    public T data;
    public Response response;
    public Exception e;

    public RestResponse(Class<T> t, Response response) {
        this.response = response;

        try{
            this.data = t.newInstance();
        }catch(Exception e){
            throw new RuntimeException("There should be a default constructor in the Response POJO");
        }
    }

    public String getContent(){
        return response.getBody().asString();
    }

    public int getStatusCode(){
        return response.getStatusCode();
    }

    public boolean isSuccessful(){
        int code = response.getStatusCode();
        if(code == 200|| code == 201 || code == 202 || code == 203 || code == 204 || code == 205)
            return true;
        return false;
    }

    public String getStatusMessage() {
        return response.getStatusLine();
    }

    public Response getResponse() {
        return response;
    }

    @Override
    public T getBody() {
        try{
            data = (T)response.getBody().as(data.getClass());
        }catch(Exception e){
            this.e = e;
        }
        return data;
    }

    @Override
    public Exception getException(){
        return e;
    }
}
