package com.sample;

import com.ibm.mfp.server.registration.external.model.AuthenticatedUser;
import com.ibm.mfp.server.security.external.checks.impl.SecurityCheckWithUserAuthentication;

import java.util.HashMap;
import java.util.Map;

public class UserAuthSecurityCheck extends SecurityCheckWithUserAuthentication {
    private String userId, displayName;
    private String errorMsg;

    @Override
    protected AuthenticatedUser createUser() {
        return new AuthenticatedUser(userId, displayName, this.getName());
    }

    @Override
    protected boolean validateCredentials(Map<String, Object> credentials) {
        if(credentials!=null && credentials.containsKey("username") && credentials.containsKey("password")){
            String username = credentials.get("username").toString();
            String password = credentials.get("password").toString();
            if(username.equals(password)) {
                userId = username;
                displayName = username;
                return true;
            }
            else {
                errorMsg = "Wrong Credentials";
            }
        }
        else{
            errorMsg = "Credentials not set properly";
        }
        return false;
    }

    @Override
    protected Map<String, Object> createChallenge() {
        HashMap challenge = new HashMap();
        challenge.put("errorMsg", errorMsg);
        challenge.put("remainingAttempts", remainingAttempts);
        return challenge;
    }
}
