package fr.webmarket.backend.model;

/**
 * User: walien
 * Date: 25/04/13
 * Time: 21:03
 */
public class ResponseWrapper {

    private boolean status;

    public boolean getStatus() {
        return status;
    }

    public ResponseWrapper setStatus(boolean status) {
        this.status = status;
        return this;
    }
}
