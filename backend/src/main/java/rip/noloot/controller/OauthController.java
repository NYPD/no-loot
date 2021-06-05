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
import rip.noloot.domain.NoLootUser;
import rip.noloot.exception.UnauthorizedUserException;
import rip.noloot.service.BattlenetLoginService;

@RestController
@RequestMapping(value = "/login")
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

        this.noLootSessionBean.setRememberMe(rememberMe);
        this.noLootSessionBean.setPrevPath(prevPath);

        String authenticationRequestUrl = this.battlenetLoginService.getAuthenticationRequestUrl();

        response.sendRedirect(authenticationRequestUrl);

    }

    @RequestMapping(value = "battlenet-oauth-verify")
    public ModelAndView battlenetOAuthVerify(HttpServletRequest request, HttpServletResponse response) {

        this.battlenetLoginService.verifyAuthenticationResponseAndRetrieveToken(request);

        NoLootUser noLootUser = this.battlenetLoginService.getNoLootUser();

        boolean unauthorized = noLootUser == null || !noLootUser.isActive();
        if (unauthorized) throw new UnauthorizedUserException(request);

        this.noLootSessionBean.setNoLootUser(noLootUser);

        boolean rememberMe = this.noLootSessionBean.isRememberMe();
        if (rememberMe) this.battlenetLoginService.createUserCookies(response);

        String prevPath = this.noLootSessionBean.getPrevPath();
        boolean hasPrevPath = prevPath != null;

        if (hasPrevPath) {
            this.noLootSessionBean.setPrevPath(null);
            return new ModelAndView("redirect:" + prevPath);
        } else {
            return new ModelAndView("redirect:/admin/maintenance");
        }

    }
    /* ------------------------------------------------------------------------------------------------------------------- */

}
