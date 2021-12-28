/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.inventory.controller;

import com.DBConnection.DBConnection;
import com.inventory.model.ItemTypeModel;
import com.inventory.tableClasses.ItemType;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
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
public class ItemTypeController extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        ServletContext ctx = getServletContext();
        HttpSession session = request.getSession();
        System.err.println("----------------------- item controller -----------------------------");
        request.setCharacterEncoding("UTF-8");
        response.setHeader("Content-Type", "text/plain; charset=UTF-8");
        ItemTypeModel model = new ItemTypeModel();
        String active = "Y";
        String ac = "ACTIVE RECORDS";
        String active1 = request.getParameter("active");
        String loggedUser = "";
        loggedUser = session.getAttribute("user_role").toString();

        try {
            String driverClass = session.getAttribute("driverClass").toString();
            String connectionString = session.getAttribute("connectionString").toString();
            String myDbUserName = session.getAttribute("myDbUserName").toString();
            String myDbUserpass = session.getAttribute("myDbUserPass").toString();

            Connection con = DriverManager.getConnection(connectionString, myDbUserName, myDbUserpass);
            model.setConnection(con);
        } catch (Exception e) {
            System.out.println("error in ItemTypeController setConnection() calling try block" + e);
        }
        try {
            String searchItemType = "";
            try {
                String JQstring = request.getParameter("action1");
                String q = request.getParameter("str");
                if (JQstring != null) {
                    PrintWriter out = response.getWriter();
                    List<String> list = null;
                    if (JQstring.equals("getItemType")) {
                        list = model.getItemType(q);
                    }
                    JSONObject gson = new JSONObject();
                    gson.put("list", list);
                    out.println(gson);
                    DBConnection.closeConncetion(model.getConnection());
                    return;
                }
            } catch (Exception e) {
                System.out.println("\n Error --ItemTypeController get JQuery Parameters Part-" + e);
            }
            searchItemType = request.getParameter("searchItemType");
            try {
                if (searchItemType == null) {
                    searchItemType = "";
                }
            } catch (Exception e) {
            }
            String search = request.getParameter("searchItemType");
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
                model.deleteRecord(Integer.parseInt(request.getParameter("item_type_id")));  // Pretty sure that office_type_id will be available.
            } else if (task.equals("Save") || task.equals("Save AS New")) {
                int item_type_id = 0;
                try {
                    item_type_id = Integer.parseInt(request.getParameter("item_type_id"));
                } catch (Exception e) {
                    item_type_id = 0;
                }
                if (task.equals("Save AS New")) {
                    //office_type_id = 0;
                }
                ItemType itemType = new ItemType();
                itemType.setItem_type_id(item_type_id);
                itemType.setItem_type(request.getParameter("item_type_name").trim());
                itemType.setDescription(request.getParameter("description").trim());

                if (item_type_id == 0) {
                    model.insertRecord(itemType);
                } else {
                    model.updateRecord(itemType, item_type_id);
                }
            }

            // Logic to show data in the table.
            List<ItemType> list = model.showData(searchItemType, active);
            request.setAttribute("list", list);
            request.setAttribute("searchItemType", searchItemType);
            request.setAttribute("message", model.getMessage());
            request.setAttribute("msgBgColor", model.getMsgBgColor());
            request.setAttribute("loggedUser", loggedUser);

            DBConnection.closeConncetion(model.getConnection());
            request.getRequestDispatcher("item_type").forward(request, response);
        } catch (Exception ex) {
            System.out.println("ItemTypeController error: " + ex);
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
