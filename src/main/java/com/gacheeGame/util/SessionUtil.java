package com.gacheeGame.util;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

@Component
public class SessionUtil
{
    public static User getUser()
    {
        return (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
