package rip.noloot.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import rip.noloot.bean.NoLootSessionBean;
import rip.noloot.exception.UnauthorizedUserException;
import rip.noloot.model.User;
import rip.noloot.service.BattlenetLoginService;

@RestController
@RequestMapping(value = "/api")
public class OauthController {

    @Autowired
    private NoLootSessionBean noLootSessionBean;
    @Autowired
    private BattlenetLoginService battlenetLoginService;

    /*
     * Battle.net OAuth ---------------------------------------------------------------------------------------------------
     */
    @RequestMapping(value = "battlenet-oauth-login")
    public void battlenetOAuthLogin(HttpServletResponse response,
                                    @RequestParam(value = "rememberMe", required = false) boolean rememberMe,
                                    @RequestParam(value = "prevPath", required = false) String prevPath) throws IOException {

        noLootSessionBean.setRememberMe(rememberMe);
        noLootSessionBean.setPrevPath(prevPath);

        String authenticationRequestUrl = battlenetLoginService.getAuthenticationRequestUrl();

        response.sendRedirect(authenticationRequestUrl);

    }

    @RequestMapping(value = "battlenet-oauth-verify")
    public ModelAndView battlenetOAuthVerify(HttpServletRequest request, HttpServletResponse response) {

        battlenetLoginService.verifyAuthenticationResponseAndRetrieveToken(request);

        User noLootUser = battlenetLoginService.getNoLootUser();

        boolean unauthorized = noLootUser == null;
        if (unauthorized) throw new UnauthorizedUserException(request);

        noLootSessionBean.setUser(noLootUser);

        boolean rememberMe = noLootSessionBean.isRememberMe();
        if (rememberMe) battlenetLoginService.createUserCookies(response);

        String prevPath = noLootSessionBean.getPrevPath();
        boolean hasPrevPath = prevPath != null;

        if (hasPrevPath) {
            noLootSessionBean.setPrevPath(null);
            return new ModelAndView("redirect:" + prevPath);
        } else {
            return new ModelAndView("redirect:/admin/maintenance");
        }

    }
    /* ------------------------------------------------------------------------------------------------------------------- */

}
