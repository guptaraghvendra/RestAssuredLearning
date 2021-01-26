package apiEngine.model.requests;

import apiEngine.model.ISBN;

import java.util.ArrayList;
import java.util.List;

/**
 * @author
 * @author Last updated by : $
 * @version : $
 */

public class AddBooksRequest {
    public String userId;
    public List<ISBN> collectionsOfISBNs;


    public AddBooksRequest(String userId, ISBN isbn) {
        this.userId = userId;
        collectionsOfISBNs = new ArrayList<ISBN>();
        collectionsOfISBNs.add(isbn);
    }
}
