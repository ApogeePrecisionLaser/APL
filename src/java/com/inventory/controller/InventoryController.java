/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.inventory.controller;

import com.DBConnection.DBConnection;
import com.inventory.model.InventoryModel;
import com.inventory.tableClasses.Inventory;
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
public class InventoryController extends HttpServlet {

    private File tmpDir;

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        ServletContext ctx = getServletContext();
        request.setCharacterEncoding("UTF-8");
        response.setHeader("Content-Type", "text/plain; charset=UTF-8");
        InventoryModel model = new InventoryModel();

        String search_item_name = "";
        String search_item_code = "";
        String search_org_office = "";
        String search_key_person = "";
        search_item_name = request.getParameter("search_item_name");
        search_item_code = request.getParameter("search_item_code");
        search_org_office = request.getParameter("search_org_office");
        search_key_person = request.getParameter("search_key_person");

        if (search_item_name == null) {
            search_item_name = "";
        }
        if (search_item_code == null) {
            search_item_code = "";
        }
        if (search_org_office == null) {
            search_org_office = "";
        }
        if (search_key_person == null) {
            search_key_person = "";
        }

        try {
            model.setConnection(DBConnection.getConnectionForUtf(ctx));
        } catch (Exception e) {
            System.out.println("error in InventoryController setConnection() calling try block" + e);
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
                    if (JQstring.equals("getItemName")) {
                        list = model.getItemName(q, str2);
                    }
                    if (JQstring.equals("getItemCode")) {
                        list = model.getItemCode(q, str2);
                    }
                    if (JQstring.equals("getOrgOffice")) {
                        list = model.getOrgOffice(q);
                    }
                    if (JQstring.equals("getKeyPerson")) {
                        list = model.getKeyPerson(q, str2);
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
                System.out.println("\n Error --InventoryController get JQuery Parameters Part-" + e);
            }

            String task = request.getParameter("task");
            if (task == null) {
                task = "";
            }
            if (task.equals("Delete")) {
                model.deleteRecord(Integer.parseInt(request.getParameter("inventory_id")));
            } else if (task.equals("Save") || task.equals("Save AS New") || task.equals("Save & Next")) {
                int inventory_id = 0;
                try {
                    inventory_id = Integer.parseInt(request.getParameter("inventory_id").trim());
                } catch (Exception e) {
                    inventory_id = 0;
                }

                if (task.equals("Save AS New")) {
                    inventory_id = 0;
                }

                Inventory bean = new Inventory();
                bean.setInventory_id(inventory_id);
                // bean.setInventory_basic_id(Integer.parseInt(request.getParameter("inventory_basic_id").trim()));
//                bean.setItem_name(request.getParameter("item_name").trim());
                bean.setItem_code(request.getParameter("item_code").trim());
                bean.setOrg_office(request.getParameter("org_office").trim());
                bean.setKey_person(request.getParameter("key_person").trim());
                bean.setDescription(request.getParameter("description").trim());
                bean.setInward_quantity(Integer.parseInt(request.getParameter("inward_quantity").trim()));
                bean.setOutward_quantity(Integer.parseInt(request.getParameter("outward_quantity").trim()));
                bean.setDate_time(request.getParameter("date_time").trim());
                bean.setReference_document_type(request.getParameter("reference_document_type").trim());
                bean.setReference_document_id(request.getParameter("reference_document_id").trim());

                if (inventory_id == 0) {
                    model.insertRecord(bean);
                } else {
                    model.updateRecord(bean, inventory_id);
                }
            }

            List<Inventory> list = model.showData(search_item_name, search_org_office, search_key_person, search_item_code);
            request.setAttribute("list", list);
            request.setAttribute("search_item_name", search_item_name);
            request.setAttribute("search_org_office", search_org_office);
            request.setAttribute("search_key_person", search_key_person);
            request.setAttribute("search_item_code", search_item_code);
            request.setAttribute("message", model.getMessage());
            request.setAttribute("msgBgColor", model.getMsgBgColor());
            model.closeConnection();

            request.getRequestDispatcher("inventory").forward(request, response);
        } catch (Exception ex) {
            System.out.println("InventoryController error: " + ex);
        }
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        doGet(request, response);
    }
}
