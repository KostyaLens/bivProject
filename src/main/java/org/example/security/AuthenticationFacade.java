package org.example.security;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationFacade{
    public String getCurrentUserName(){
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }
}
