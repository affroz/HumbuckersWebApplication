package com.humbuckers.filters;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import com.humbuckers.dto.UsersDTO;

public class WebFilter implements Filter {



    @Override
    public void destroy() {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpSession session = req.getSession(false);
        UsersDTO login = null;
        ((HttpServletResponse) response).setHeader("Pragma", "no-cache"); 
        ((HttpServletResponse) response).setHeader("Cache-Control", "no-cache, no-store, must-revalidate, max-age=2592000"); 
        if (session != null) {
            login = (UsersDTO) session.getAttribute("LOGIN_USER");
        }
        boolean isLoggedIn = (login != null);
        if (req.getRequestURI().contains("javax.faces.resource")) {
            chain.doFilter(request, response);
            return;
        }
        if (req.getRequestURI().equals(req.getContextPath() + "/login.xhtml")) {
            if (isLoggedIn) {
                HttpServletResponse res = (HttpServletResponse) response;
                res.sendRedirect(req.getContextPath() + "pages/dashbaord.xhtml");
            } else {
                chain.doFilter(request, response);
            }
        } else {
            if (isLoggedIn) {
                chain.doFilter(request, response);
            } else {
                HttpServletResponse res = (HttpServletResponse) response;
                res.sendRedirect(req.getContextPath() + "/login.xhtml");
            }
        }
        
   
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
    }

}
