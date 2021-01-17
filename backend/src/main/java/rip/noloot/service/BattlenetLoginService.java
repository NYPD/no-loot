package rip.noloot.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Service;

import rip.noloot.annotation.BattlenetLogin;
import rip.noloot.model.User;

@Service
@BattlenetLogin
public class BattlenetLoginService implements Oauth2LoginService {

    @Override
    public String getAuthenticationRequestUrl() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void verifyAuthenticationResponse(HttpServletRequest request) {
        // TODO Auto-generated method stub

    }

    @Override
    public User getNoLootUser() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void createUserCookies(HttpServletResponse response) {
        // TODO Auto-generated method stub

    }

    @Override
    public void reAuthenticateUser(HttpServletRequest request, HttpServletResponse response) {
        // TODO Auto-generated method stub

    }

}
