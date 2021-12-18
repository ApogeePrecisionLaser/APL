/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.inventory.controller;

import com.DBConnection.DBConnection;
import com.inventory.model.ItemAuthorizationModel;
import com.inventory.tableClasses.ItemAuthorization;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 *
 * @author Komal
 */
public class ItemAuthorizationController extends HttpServlet {

    private File tmpDir;

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        ServletContext ctx = getServletContext();
        request.setCharacterEncoding("UTF-8");
        response.setHeader("Content-Type", "text/plain; charset=UTF-8");
        ItemAuthorizationModel model = new ItemAuthorizationModel();

        String search_item_name = "";
        String search_designation = "";
       
        search_item_name = request.getParameter("search_item_name");
        search_designation = request.getParameter("search_designation");
        
        HttpSession session = request.getSession();
        String loggedUser="";
        loggedUser = session.getAttribute("user_role").toString();	


        if (search_item_name == null) {
            search_item_name = "";
        }
        if (search_designation == null) {
            search_designation = "";
        }
        

        try {
            model.setConnection(DBConnection.getConnectionForUtf(ctx));
        } catch (Exception e) {
            System.out.println("error in ItemAuthorizationController setConnection() calling try block" + e);
        }

        try {
            String requester = request.getParameter("requester");
            try {
                String JQstring = request.getParameter("action1");
                String q = request.getParameter("str");
                String str2 = request.getParameter("str2");
                String str3 = request.getParameter("str3");

                if (JQstring != null) {
                    PrintWriter out = response.getWriter();
                    List<String> list = null;
                    JSONObject json = null;
                   
                    if (JQstring.equals("getDesignation")) {
                        list = model.getDesignation(q);
                    }

                    if (JQstring.equals("getItemName")) {
                        list = model.getItemName(q);
                    }

                    
                    if (json != null) {

                        out.println(json);
                    } else {
                        Iterator<String> iter = list.iterator();
                        JSONObject gson = new JSONObject();
                        gson.put("list", list);
                        out.println(gson);
                    }
                    return;
                }
            } catch (Exception e) {
                System.out.println("\n Error --ItemAuthorizationController get JQuery Parameters Part-" + e);
            }

            String task = request.getParameter("task");
            if (task == null) {
                task = "";
            }
            if (task.equals("Delete")) {
                model.deleteRecord(Integer.parseInt(request.getParameter("item_authorization_id")));
            } else if (task.equals("Save") || task.equals("Save AS New") || task.equals("Save & Next")) {
                int item_authorization_id = 0;
                try {
                    item_authorization_id = Integer.parseInt(request.getParameter("item_authorization_id").trim());
                } catch (Exception e) {
                    item_authorization_id = 0;
                }

                if (task.equals("Save AS New")) {
                    item_authorization_id = 0;
                }

                ItemAuthorization bean = new ItemAuthorization();
                bean.setItem_authorization_id(item_authorization_id);
                bean.setItem_name(request.getParameter("item_name").trim());
                bean.setDesignation(request.getParameter("designation").trim());
                bean.setDescription(request.getParameter("description").trim());
                bean.setQuantity(Integer.parseInt(request.getParameter("quantity").trim()));
                bean.setMonthly_limit(Integer.parseInt(request.getParameter("monthly_limit").trim()));

                if (item_authorization_id == 0) {
                    model.insertRecord(bean);
                } else {
                    model.updateRecord(bean, item_authorization_id);
                }
            }

            List<ItemAuthorization> list = model.showData(search_item_name, search_designation);
            request.setAttribute("list", list);
            request.setAttribute("search_item_name", search_item_name);
            request.setAttribute("search_designation", search_designation);
            request.setAttribute("message", model.getMessage());
            request.setAttribute("msgBgColor", model.getMsgBgColor());
            request.setAttribute("loggedUser", loggedUser);
            model.closeConnection();

            request.getRequestDispatcher("item_authorization").forward(request, response);
        } catch (Exception ex) {
            System.out.println("ItemAuthorizationController error: " + ex);
        }
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        doGet(request, response);
    }
}
