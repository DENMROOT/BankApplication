package com.luxoft.bankapp.listeners; /**
 * Created by Denis Makarov on 13.02.2015.
 */

import com.luxoft.bankapp.service.services.BankService;
import com.luxoft.bankapp.service.services.BankServiceImpl;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import javax.servlet.http.HttpSessionBindingEvent;

public class SessionListener implements ServletContextListener,
        HttpSessionListener, HttpSessionAttributeListener {

    // Public constructor is required by servlet spec
    public SessionListener() {
    }

    // -------------------------------------------------------
    // ServletContextListener implementation
    // -------------------------------------------------------
    public void contextInitialized(ServletContextEvent sce) {
      /* This method is called when the servlet context is
         initialized(when the Web application is deployed). 
         You can initialize servlet context related data here.
      */

    }

    public void contextDestroyed(ServletContextEvent sce) {
      /* This method is invoked when the Servlet Context 
         (the Web application) is undeployed or 
         Application Server shuts down.
      */
    }

    // -------------------------------------------------------
    // HttpSessionListener implementation
    // -------------------------------------------------------
    public void sessionCreated(HttpSessionEvent se) {
      /* Session is created. */
        final ServletContext context = se.getSession().getServletContext();
        synchronized (SessionListener.class) {
            Integer clientsConnected = (Integer) context.getAttribute("clientsConnected");
            if (clientsConnected==null) {
                clientsConnected=1;
            } else {
                clientsConnected++;
            }
            context.setAttribute("clientsConnected", clientsConnected);
        }
    }

    public void sessionDestroyed(HttpSessionEvent se) {
      /* Session is destroyed. */
        final ServletContext context = se.getSession().getServletContext();
        synchronized (SessionListener.class) {
            Integer clientsConnected = (Integer) context.getAttribute("clientsConnected");
            if (clientsConnected!=null) {
                clientsConnected--;
            }
            context.setAttribute("clientsConnected", clientsConnected);
        }
    }

    // -------------------------------------------------------
    // HttpSessionAttributeListener implementation
    // -------------------------------------------------------

    public void attributeAdded(HttpSessionBindingEvent sbe) {
      /* This method is called when an attribute 
         is added to a session.
      */
    }

    public void attributeRemoved(HttpSessionBindingEvent sbe) {
      /* This method is called when an attribute
         is removed from a session.
      */
    }

    public void attributeReplaced(HttpSessionBindingEvent sbe) {
      /* This method is invoked when an attibute
         is replaced in a session.
      */
    }
}
