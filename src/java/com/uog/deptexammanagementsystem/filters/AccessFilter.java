package com.uog.deptexammanagementsystem.filters;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
import com.uog.deptexammanagementsystem.user.LoginBean;
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

/**
 *
 * @author arahman
 */
//@WebFilter({"/addStudent.xhtml", "/UpdateStudent.xhtml", "/addRoom.xhtml", "/UpdateRoom.xhtml"})
public class AccessFilter implements Filter {

    LoginBean loginBean;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest req = (HttpServletRequest) request;
        /// loginBean=(LoginBean) req.getSession().getAttribute("currentSessionUser");
        loginBean = (LoginBean) req.getAttribute("loginBean");
//        Enumeration en=req.getAttributeNames();
//        while(en.hasMoreElements()){
//            System.out.println("Next attribute");
//            System.out.println(en.nextElement().toString());
//        }
        if (loginBean != null && loginBean.getUser() != null) {
            // User is logged in, so just continue request.
            HttpSession session = req.getSession(true);
            session.setAttribute("loginBean", loginBean);
            HttpServletResponse res = (HttpServletResponse) response;
            res.sendRedirect(req.getContextPath() + "/addStudent.xhtml");
            chain.doFilter(request, response);
        } else {
//            System.out.println("Login Bean is Null: "+loginBean==null);
//            System.out.println("User is Null: "+loginBean.getUser());
            // User is not logged in, so redirect to index.
            HttpServletResponse res = (HttpServletResponse) response;
            System.out.println("Redirecting to : " + req.getContextPath() + "/Login.xhtml");
            res.sendRedirect(req.getContextPath() + "/Login.xhtml");
        }
    }

    // You need to override init() and destroy() as well, but they can be kept empty.
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void destroy() {

    }
}
