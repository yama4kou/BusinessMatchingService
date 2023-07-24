package com.starsoft1.bms.config;

import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class CustomAuthenticationFailureHandler   implements AuthenticationFailureHandler {
 
    @Override
    public void onAuthenticationFailure(
      HttpServletRequest request,
      HttpServletResponse response,
      AuthenticationException exception) 
      throws IOException, ServletException {
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        
        HttpSession session = request.getSession();
        session.setAttribute("email", request.getParameter("email"));

        response.sendRedirect("/login?error=true");
    }
}