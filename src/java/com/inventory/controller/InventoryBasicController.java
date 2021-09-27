/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.inventory.controller;

import com.DBConnection.DBConnection;
import com.inventory.model.InventoryBasicModel;
import com.inventory.tableClasses.InventoryBasic;
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
public class InventoryBasicController extends HttpServlet {

    private File tmpDir;

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        ServletContext ctx = getServletContext();
        request.setCharacterEncoding("UTF-8");
        response.setHeader("Content-Type", "text/plain; charset=UTF-8");
        InventoryBasicModel model = new InventoryBasicModel();

        String search_item_name = "";
        String search_org_office = "";
        String search_item_code = "";
        search_item_name = request.getParameter("search_item_name");
        search_org_office = request.getParameter("search_org_office");
        search_item_code = request.getParameter("search_item_code");

        if (search_item_name == null) {
            search_item_name = "";
        }
        if (search_org_office == null) {
            search_org_office = "";
        }
        if (search_item_code == null) {
            search_item_code = "";
        }

        try {
            model.setConnection(DBConnection.getConnectionForUtf(ctx));
        } catch (Exception e) {
            System.out.println("error in ItemNameController setConnection() calling try block" + e);
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
                        list = model.getItemName(q);
                    }
                     if (JQstring.equals("getItemCode")) {
                        list = model.getItemCode(q);
                    }
                    if (JQstring.equals("getOrgOffice")) {
                        list = model.getOrgOffice(q);
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
                System.out.println("\n Error --InventoryBasicController get JQuery Parameters Part-" + e);
            }

            String task = request.getParameter("task");
            if (task == null) {
                task = "";
            }
            if (task.equals("Delete")) {
                model.deleteRecord(Integer.parseInt(request.getParameter("inventory_basic_id")));
            } else if (task.equals("Save") || task.equals("Save AS New") || task.equals("Save & Next")) {
                int inventory_basic_id = 0;
                try {

                    inventory_basic_id = Integer.parseInt(request.getParameter("inventory_basic_id").trim());
                } catch (Exception e) {
                    inventory_basic_id = 0;
                }

                if (task.equals("Save AS New")) {
                    inventory_basic_id = 0;
                }

                InventoryBasic bean = new InventoryBasic();
                bean.setInventory_basic_id(inventory_basic_id);
               // bean.setItem_name(request.getParameter("item_name").trim());
                bean.setItem_code(request.getParameter("item_code").trim());
                bean.setOrg_office(request.getParameter("org_office").trim());
                bean.setDescription(request.getParameter("description").trim());
                bean.setMin_quantity(Integer.parseInt(request.getParameter("min_quantity").trim()));
                bean.setDaily_req(Integer.parseInt(request.getParameter("daily_req").trim()));
                bean.setOpening_balance(request.getParameter("opening_balance").trim());

                if (inventory_basic_id == 0) {
                    model.insertRecord(bean);
                } else {
                    model.updateRecord(bean, inventory_basic_id);
                }
            }

            List<InventoryBasic> list = model.showData(search_item_name, search_org_office,search_item_code);
            request.setAttribute("list", list);
            request.setAttribute("search_item_name", search_item_name);
            request.setAttribute("search_item_code", search_item_code);
            request.setAttribute("search_org_office", search_org_office);
            request.setAttribute("message", model.getMessage());
            request.setAttribute("msgBgColor", model.getMsgBgColor());
            model.closeConnection();

            request.getRequestDispatcher("inventory_basic").forward(request, response);
        } catch (Exception ex) {
            System.out.println("InventoryBasicController error: " + ex);
        }
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        doGet(request, response);
    }
}
