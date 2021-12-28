/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.apogee.admin;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Vikrant
 */
public class UserNoPrivilegeController extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setAttribute("message", "Sorry You Have No Privileges For This Page !!!.. ");
        request.setAttribute("msgBgColor", "red"); //        System.out.println("Going to JSp noww*************************");
        request.getRequestDispatcher("user_no_privilege").forward(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request, response);
    }
}
