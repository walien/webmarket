package fr.webmarket.backend.rest.auth;

import fr.webmarket.backend.model.User;

/**
 * User: walien
 * Date: 18/04/13
 * Time: 23:45
 */
public class AuthResult {

    private User user;
    private boolean isAuth;
    private String sessionID;

    public AuthResult() {
    }

    public AuthResult(User user, boolean auth, String sessionID) {
        this.user = user;
        isAuth = auth;
        this.sessionID = sessionID;
    }

    public User getUser() {
        return user;
    }

    public AuthResult setUser(User user) {
        this.user = user;
        return this;
    }

    public boolean isAuth() {
        return isAuth;
    }

    public AuthResult setAuth(boolean auth) {
        isAuth = auth;
        return this;
    }

    public String getSessionID() {
        return sessionID;
    }

    public AuthResult setSessionID(String sessionID) {
        this.sessionID = sessionID;
        return this;
    }

    @Override
    public String toString() {
        return "AuthResult{" +
                "user=" + user +
                ", isAuth=" + isAuth +
                ", sessionID='" + sessionID + '\'' +
                '}';
    }
}
