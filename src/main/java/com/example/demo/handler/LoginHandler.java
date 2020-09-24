package com.example.demo.handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

import com.example.demo.dto.RegisterDTO;
import com.example.demo.dto.RegisterDetail;

public class LoginHandler extends SavedRequestAwareAuthenticationSuccessHandler {
	
	public LoginHandler(String defaultTargetUrl){
		setDefaultTargetUrl(defaultTargetUrl);
    }
    
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws ServletException, IOException {
        
    	RegisterDetail registerDetail = (RegisterDetail)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    	String auth = registerDetail.getAuth();
    	System.out.println("handler auth : " + auth);
    	
    	if(auth.equals("X")) {
        	System.out.println("인증 안됐을때 여기서 뭔가가 처리가 되야되는데...");
        }
    	
        HttpSession session = request.getSession();
        if (session != null) {
            String redirectUrl = (String) session.getAttribute("prevPage");
            if (redirectUrl != null) {
                session.removeAttribute("prevPage");
                getRedirectStrategy().sendRedirect(request, response, redirectUrl);
            } else {
                super.onAuthenticationSuccess(request, response, authentication);
            }
            
        } else {
            super.onAuthenticationSuccess(request, response, authentication);
        }
    }
}
