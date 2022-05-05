/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.website.controller;

import com.DBConnection.DBConnection;
import com.website.model.ContactUsModel;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.util.List;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 *
 * @author komal
 */
public class ContactUsController extends HttpServlet {

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
        ServletContext ctx = getServletContext();
        ContactUsModel model = new ContactUsModel();
        //PrintWriter out = response.getWriter();

        try {
            model.setConnection(DBConnection.getConnectionForUtf(ctx));
        } catch (Exception e) {
            System.out.println("error in ContactUsController setConnection() calling try block" + e);
        }
        try {
            String task = request.getParameter("task");
            if (task == null) {
                task = "";
            }
            if (task.equals("GetCordinates4")) {
                System.err.println("GetCordinates4-----------------");
                String longi = request.getParameter("longitude");
                String latti = request.getParameter("latitude");
                if (longi == null || longi.equals("undefined")) {
                    longi = "0";
                }
                if (latti == null || latti.equals("undefined")) {
                    latti = "0";
                }
                request.setAttribute("longi", longi);
                request.setAttribute("latti", latti);
                System.out.println(latti + "," + longi);
                request.getRequestDispatcher("getCordinate4").forward(request, response);
                return;
            }
            
            String JQstring = request.getParameter("action1");
            String latitude = request.getParameter("latitude");
            String longitude = request.getParameter("longitude");

            if (JQstring != null) {
                PrintWriter out = response.getWriter();
                List<String> list = null;
                if (JQstring.equals("getDealersList")) {
                    JSONObject obj1 = new JSONObject();
                    JSONArray arrayObj = new JSONArray();

                    arrayObj = model.getDealersList(latitude,longitude);

                    obj1.put("dealer_name", arrayObj);
                    out.print(obj1);
                    return;
                }
            }

            model.closeConnection();
            request.getRequestDispatcher("contact").forward(request, response);
        } catch (Exception ex) {
            System.out.println("ContactUsController error: " + ex);

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
