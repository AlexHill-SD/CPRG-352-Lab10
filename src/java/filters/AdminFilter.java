/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package filters;

import dataaccess.UserDB;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.StringWriter;
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
 * @author BritishWaldo
 */
public class AdminFilter implements Filter
{
    @Override
    public void init(FilterConfig filterConfig) throws ServletException
    {
        
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException
    {
        //before dofilter = request
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpSession httpSession = httpRequest.getSession();
        
        String email = (String) httpSession.getAttribute("email");
        
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        
        //if not admin then send to notes page
        //if user isn't even logged in they don't reach this point.
        if (new UserDB().get(email).getRole().getRoleId() != 1)
        {
            //printout for debugging purposes (infinite redirects)
            System.out.println(email + " is NOT an admin, redirecting to notes page");
            httpResponse.sendRedirect("notes");
            return;
        }
        
        chain.doFilter(request, response);
    }

    @Override
    public void destroy()
    {
        
    }
}
