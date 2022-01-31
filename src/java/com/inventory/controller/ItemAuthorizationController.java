/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.inventory.controller;

import com.DBConnection.DBConnection;
import com.inventory.model.ItemAuthorizationModel;
import com.inventory.model.ItemNameModel;
import com.inventory.tableClasses.ItemAuthorization;
import com.inventory.tableClasses.ItemName;
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
        ItemNameModel model2 = new ItemNameModel();

        String search_item_name = "";
        String search_designation = "";
        String search_org_office = "";

        search_item_name = request.getParameter("search_item_name");
        search_designation = request.getParameter("search_designation");
        search_org_office = request.getParameter("search_org_office");

        HttpSession session = request.getSession();
        String loggedUser = "";
        loggedUser = session.getAttribute("user_role").toString();

        if (search_item_name == null) {
            search_item_name = "";
        }
        if (search_designation == null) {
            search_designation = "";
        }
        if (search_org_office == null) {
            search_org_office = "";
        }

        String logged_user_name = "";
        String logged_designation = "";
        String logged_org_name = "";
        String logged_org_office = "";
        int logged_org_office_id = 0;
        int logged_org_name_id = 0;
        int logged_key_person_id = 0;

        loggedUser = session.getAttribute("user_role").toString();

        if (session == null || session.getAttribute("logged_user_name") == null) {
            request.getRequestDispatcher("/").forward(request, response);
            return;
        } else {
            logged_user_name = session.getAttribute("logged_user_name").toString();
            logged_org_name = session.getAttribute("logged_org_name").toString();
            logged_designation = session.getAttribute("logged_designation").toString();
            logged_org_office = session.getAttribute("logged_org_office").toString();
            logged_org_name_id = Integer.parseInt(session.getAttribute("logged_org_name_id").toString());
            logged_org_office_id = Integer.parseInt(session.getAttribute("logged_org_office_id").toString());
            logged_key_person_id = Integer.parseInt(session.getAttribute("logged_key_person_id").toString());
            // office_embedded_dev = session.getAttribute("office_embedded_dev").toString();
        }

        try {
            model.setConnection(DBConnection.getConnectionForUtf(ctx));
            model2.setConnection(DBConnection.getConnectionForUtf(ctx));
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
                    if (JQstring.equals("getAllChild")) {
                        String item_names_id = request.getParameter("item_names_id");
                        list = model.getAllChild(item_names_id);
                    }

                    if (json != null) {

                        out.println(json);
                    } else {
                        Iterator<String> iter = list.iterator();
                        JSONObject gson = new JSONObject();
                        gson.put("list", list);
                        out.println(gson);
                    }
                    DBConnection.closeConncetion(model.getConnection());
                    return;
                }
            } catch (Exception e) {
                System.out.println("\n Error --ItemAuthorizationController get JQuery Parameters Part-" + e);
            }

            String task = request.getParameter("task");
            String task1 = request.getParameter("task1");
            if (task == null) {
                task = "";
            }
            if (task1 == null) {
                task1 = "";
            }

            if (task1.equals("viewMappedDesignation")) {
                String item_names_id = request.getParameter("item_names_id");
                String org_office = request.getParameter("org_office");
                List<ItemAuthorization> list = model.getMappedItemDesignation(item_names_id, org_office);
                request.setAttribute("list", list);

                request.getRequestDispatcher("item_authorization_designation").forward(request, response);
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
                String item_checkbox[] = request.getParameterValues("item_checkbox");
                String des_checkbox[] = request.getParameterValues("designation");
                bean.setDescription(request.getParameter("description").trim());
                bean.setOrg_office(request.getParameter("org_office").trim());
                bean.setQuantity(0);
                bean.setMonthly_limit(0);
                for (int i = 0; i < item_checkbox.length; i++) {
                    bean.setItem_name(item_checkbox[i]);
                    if (item_authorization_id == 0) {
                        model.insertRecord(bean, des_checkbox);
                    } else {
                        model.updateRecord(bean, item_authorization_id);
                    }
                }
            }

//            List<ItemAuthorization> list = model.showData(search_item_name, search_designation);
            List<ItemName> list = model.getItemsList(logged_designation, search_org_office,search_item_name);

            request.setAttribute("list", list);
            request.setAttribute("search_item_name", search_item_name);
            request.setAttribute("search_designation", search_designation);
            request.setAttribute("search_org_office", search_org_office);
            request.setAttribute("message", model.getMessage());
            request.setAttribute("msgBgColor", model.getMsgBgColor());
            request.setAttribute("loggedUser", loggedUser);
            DBConnection.closeConncetion(model.getConnection());
            DBConnection.closeConncetion(model2.getConnection());

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
