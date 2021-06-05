package rip.noloot.service;

import java.math.BigInteger;
import java.security.SecureRandom;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import rip.noloot.domain.NoLootUser;
import rip.noloot.exception.InvalidStateTokenException;

public interface Oauth2LoginService {

    /**
     * Creates the authentication request String URL needed to send to the API servers.
     * 
     * @return The authentication request String URL
     */
    public String getAuthenticationRequestUrl();

    /**
     * 
     * Verifies the response from the API server and if valid, retrieves the token needed to make API calls. If the response
     * is invalid due a miss matching state token a {@link InvalidStateTokenException} is thrown.
     * <p>
     * The token is stored in session to be used to make any future API calls
     * 
     * @param request - The HttpServletRequest from the API server
     * @throws InvalidStateTokenException
     */
    public void verifyAuthenticationResponseAndRetrieveToken(HttpServletRequest request) throws InvalidStateTokenException;

    /**
     * Return the corresponding app user using the unique API's user id. If no user is found a new user will be created,
     * persisted, then returned.
     * <p>
     * If available, sets the URL API profile picture for the user
     * 
     * @return {@link User}
     */
    public NoLootUser getNoLootUser();

    /**
     * Creates API specific cookies to be able to authenticate the user again without having them login again and sets them
     * in the {@link HttpServletResponse}
     * 
     * @param response - The {@link HttpServletResponse} to set cookies into
     */
    public void createUserCookies(HttpServletResponse response);

    /**
     * Should re-authenticate the user with the given API and set any needed data in the session. If re-authentication can't
     * be done redirect the user to wherever the authentication process begins (post login click) and try to authenticate the
     * user again seamlessly
     * 
     * @param response
     */
    public void reAuthenticateUser(HttpServletRequest request, HttpServletResponse response);

    /**
     * Helper method that return a new random state token to use for authorization for Oauth.
     * 
     * @return {@link String} state token
     */
    default String getStateToken() {
        return new BigInteger(130, new SecureRandom()).toString(32);
    }

}