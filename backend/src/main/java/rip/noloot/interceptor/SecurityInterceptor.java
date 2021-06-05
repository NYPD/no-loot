package rip.noloot.interceptor;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;

import rip.noloot.api.battlenet.response.TokenResponse;
import rip.noloot.bean.BattlenetSessionBean;
import rip.noloot.service.BattlenetLoginService;

/**
 * Security intercepter that checks to see if current user is allowed to continue with the request. If there is no current
 * user in the session it checks and see if the user has any cookies created from previous sessions. If so, it attempts to
 * use the appropriate third party API to re authenticate the user. If no cookies are found, the user is redirected to the
 * login page.
 * 
 * @author NYPD
 *
 */
public class SecurityInterceptor implements HandlerInterceptor {

    private static final String LOGIN_PATH = "/login";

    private final BattlenetSessionBean battlenetSessionBean;
    private final BattlenetLoginService battlenetLoginService;

    public SecurityInterceptor(BattlenetSessionBean battlenetSessionBean, BattlenetLoginService battlenetLoginService) {
        this.battlenetSessionBean = battlenetSessionBean;
        this.battlenetLoginService = battlenetLoginService;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        //If we have a token response in session, continue
        TokenResponse tokenResponse = this.battlenetSessionBean.getTokenResponse();
        if (tokenResponse != null) return true;

        //If the request is for some reason null, just redirect back to the login page
        if (request == null) {
            response.sendRedirect(LOGIN_PATH);
            return false;
        }

        Cookie[] cookies = request.getCookies();

        //If there is no cookies to save this request, just redirect back to the login page
        if (cookies == null) {
            response.sendRedirect(LOGIN_PATH);
            return false;
        }

        //Attempt to get the token response cookie if it exists and if it does re-authenticate the user with it
        for (Cookie cookie : cookies) {

            boolean isBattlenettokenCookie = BattlenetLoginService.BATTLENET_TOKEN_COOKIE_NAME.equals(cookie.getName());
            if (isBattlenettokenCookie) {
                this.battlenetLoginService.reAuthenticateUser(request, response);
                return true;
            }

        }

        //If the particular cookie can not be found, the user has to login again
        response.sendRedirect(LOGIN_PATH);
        return false;

    }

}
