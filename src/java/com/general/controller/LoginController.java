/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.general.controller;

import com.DBConnection.DBConnection;
import com.general.model.LoginModel;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Komal
 */
public class LoginController extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        ServletContext ctx = getServletContext();
        LoginModel model = new LoginModel();
        model.setDriverClass(ctx.getInitParameter("driverClass"));
        model.setConnectionString(ctx.getInitParameter("connectionString"));
        HttpSession session = request.getSession();
        String task = request.getParameter("task");
        if (task == null || task.isEmpty()) {
            task = "";
        }
        
        
//        try {
//            model.setConnection((Connection) DBConnection.getConnectionForUtf(ctx));
//        } catch (Exception e) {
//            System.out.print(e);
//        }     

        try {
            System.out.println("conn -" + model);

            if (task.equals("login")) {
                String user_name = request.getParameter("user_name");
                String password = request.getParameter("password");
                
              model.setUserFullDetail(user_name, password);
                
                int count = model.checkLogin(user_name, password);

                session.setAttribute("log_user", user_name);
                session.setAttribute("driverClass", ctx.getInitParameter("driverClass"));
                session.setAttribute("connectionString", ctx.getInitParameter("connectionString"));
                session.setAttribute("myDbUserName", model.getMyDbUserName());
                session.setAttribute("myDbUserPass", model.getMyDbUserPass());
                session.setAttribute("user_role", model.getMyRoleName());

                String designation = "";

                session.setAttribute("user_name", user_name);
                session.setAttribute("password", password);
                
                session.setAttribute("mode", "data");

                designation = model.getDesignation(user_name, password);
                if (designation.equals("")) {
                    designation = "normal_user";
                }

                int logged_key_person_id = model.getKeyPersonId(user_name, password);
                int logged_org_name_id = model.getOrgNameId(user_name, password);
                int logged_org_office_id = model.getOrgOfficeId(user_name, password);
                String logged_org_name = model.getOrgName(user_name, password);
                String logged_org_office = model.getOrgOffice(user_name, password);
                String office_admin = model.getOfficeAdmin(user_name, password, logged_org_office_id);

                if (count > 0) {
                    session.setAttribute("logged_user_name", user_name);
                    session.setAttribute("logged_designation", designation);
                    session.setAttribute("logged_org_name_id", logged_org_name_id);
                    session.setAttribute("logged_org_office_id", logged_org_office_id);
                    session.setAttribute("logged_org_name", logged_org_name);
                    session.setAttribute("logged_org_office", logged_org_office);
                    session.setAttribute("logged_key_person_id", logged_key_person_id);
                    session.setAttribute("office_admin", office_admin);
                    request.getRequestDispatcher("dashboard").forward(request, response);
                } else {
                    request.setAttribute("message", "Credentials mis-match!");
                    request.getRequestDispatcher("/").forward(request, response);
                }

//                if (!designation.equals("")) {
//
//                    if (designation.equals("Supervisor1")) {
//                        request.setAttribute("designation", designation);
//                    } else if (designation.equals("Clerk")) {
//                        request.setAttribute("designation", designation);
//                    } else if (designation.equals("RWA")) {
//                        request.setAttribute("designation", designation);
//                    } else if (designation.equals("पब्लिक")) {
//                        request.setAttribute("designation", designation);
//                    }
//                    //   request.getRequestDispatcher("index").forward(request, response);
//                } else {
//                    session.invalidate();
//                    request.setAttribute("message", "UserName And Password Not Correct");
//                    request.getRequestDispatcher("beforeLoginHomeView").forward(request, response);
//                }
            }
            if (task.equals("logout")) {
                session.invalidate();
                request.getRequestDispatcher("/").forward(request, response);
            }
            //request.getRequestDispatcher("index").forward(request, response);
        } catch (Exception e) {
            System.out.println(e);
        }

    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
