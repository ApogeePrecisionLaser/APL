/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.inventory.controller;

import com.DBConnection.DBConnection;
import com.inventory.model.ManufacturerModel;
import com.inventory.tableClasses.Manufacturer;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.util.List;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 *
 * @author Komal
 */
public class ManufacturerController extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        ServletContext ctx = getServletContext();
        request.setCharacterEncoding("UTF-8");
        response.setHeader("Content-Type", "text/plain; charset=UTF-8");
        ManufacturerModel model = new ManufacturerModel();
        String active = "Y";
        String ac = "ACTIVE RECORDS";
        String active1 = request.getParameter("active");
        HttpSession session = request.getSession();
        if (session == null || session.getAttribute("logged_user_name") == null) {
            request.getRequestDispatcher("/").forward(request, response);
            return;
        }
        String loggedUser = "";
        loggedUser = session.getAttribute("user_role").toString();
        try {
            model.setConnection(DBConnection.getConnectionForUtf(ctx));
        } catch (Exception e) {
            System.out.println("error in ManufacturerController setConnection() calling try block" + e);
        }
        try {
            String searchManufacturer = "";
            try {
                String JQstring = request.getParameter("action1");
                String q = request.getParameter("str");
                if (JQstring != null) {
                    PrintWriter out = response.getWriter();
                    List<String> list = null;
                    if (JQstring.equals("getManufacturer")) {
                        list = model.getManufacturer(q);
                    }
                    JSONObject gson = new JSONObject();
                    gson.put("list", list);
                    out.println(gson);
                    DBConnection.closeConncetion(model.getConnection());
                    return;
                }
            } catch (Exception e) {
                System.out.println("\n Error --ManufacturerController get JQuery Parameters Part-" + e);
            }
            searchManufacturer = request.getParameter("searchManufacturer");
            try {
                if (searchManufacturer == null) {
                    searchManufacturer = "";
                }
            } catch (Exception e) {
            }
            String search = request.getParameter("searchManufacturer");
            if (search == null) {
                search = "";
            }

            String task = request.getParameter("task");
            if (task == null) {
                task = "";
            }

            if (task.equals("ACTIVE RECORDS")) {
                active = "Y";
                ac = "ACTIVE RECORDS";

            } else if (task.equals("INACTIVE RECORDS")) {
                active = "n";
                ac = "INACTIVE RECORDS";
            } else if (task.equals("ALL RECORDS")) {
                active = "";
                ac = "ALL RECORDS";
            }

            if (task.equals("Delete")) {
                model.deleteRecord(Integer.parseInt(request.getParameter("manufacturer_id")));  // Pretty sure that office_type_id will be available.
            } else if (task.equals("Save") || task.equals("Save AS New")) {
                int manufacturer_id;
                try {
                    manufacturer_id = Integer.parseInt(request.getParameter("manufacturer_id"));
                } catch (Exception e) {
                    manufacturer_id = 0;
                }
                if (task.equals("Save AS New")) {
                    //office_type_id = 0;
                }

                Manufacturer bean = new Manufacturer();
                bean.setManufacturer_id(manufacturer_id);
                bean.setManufacturer_name(request.getParameter("manufacturer_name").trim());
                bean.setDescription(request.getParameter("description").trim());

                if (manufacturer_id == 0) {
                    model.insertRecord(bean);
                } else {
                    model.updateRecord(bean, manufacturer_id);
                }
            }

            List<Manufacturer> list = model.showData(searchManufacturer, active);
            request.setAttribute("list", list);
            request.setAttribute("searchManufacturer", searchManufacturer);
            request.setAttribute("message", model.getMessage());
            request.setAttribute("msgBgColor", model.getMsgBgColor());
            request.setAttribute("loggedUser", loggedUser);

            DBConnection.closeConncetion(model.getConnection());
            request.getRequestDispatcher("manufacturer").forward(request, response);
        } catch (Exception ex) {
            System.out.println("ManufacturerController error: " + ex);
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
