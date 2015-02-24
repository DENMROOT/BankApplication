package com.luxoft.bankapp.filters;

import org.h2.engine.Session;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.logging.Logger;

/**
 * Created by Denis Makarov on 12.02.2015.
 */
public class CheckLoggedFilter implements Filter {
    Logger logger = Logger.getLogger("CheckLoggedFilter");

    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        HttpSession session = ((HttpServletRequest) req).getSession();
        String clientName = (String) session.getAttribute("clientName");
        String path = ((HttpServletRequest) req).getRequestURI();
        HttpServletResponse response = (HttpServletResponse) resp;

        if (path.startsWith("/login") ||
                path.startsWith("/js") ||
                path.startsWith("/stylesheets") ||
                path.equals("/welcome") ||
                path.equals("/") ||
                clientName!=null) {
            //logger.info("Client: " + clientName + " passthrow");
            chain.doFilter(req, resp);
        }
        else {
            //logger.info("Client: " + clientName + " redirected" + "to login.html");
            response.sendRedirect("login.html");
        }
    }

    public void init(FilterConfig config) throws ServletException {

    }

}
