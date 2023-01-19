package com.example.securitytest.service;

import com.example.securitytest.api.ITokenValidator;
import org.springframework.stereotype.Service;

@Service
public class TokenValidatorService implements ITokenValidator {

    @Override
    public boolean validateToken(String token) {
        System.out.println("TOKEN VALIDATION STARTED");
        if(token == null || token.isEmpty()){
            System.out.println("TOKEN INVALID");
            return false;
        }
        System.out.println("TOKEN VALID");
        return true;
    }

}
