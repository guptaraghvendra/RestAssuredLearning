package apiEngine.model.requests;

import apiEngine.model.ISBN;

/**
 * @author
 * @author Last updated by : $
 * @version : $
 */

public class RemoveBookRequest {
    public String userId;
    public ISBN isbn;
    public RemoveBookRequest(String userId,ISBN isbn){
        this.userId=userId;
        //this.isbn=isbn.toString();
        this.isbn = isbn;
    }
}
