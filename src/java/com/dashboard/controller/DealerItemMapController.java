/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dashboard.controller;

import com.DBConnection.DBConnection;
import com.dashboard.bean.DealerItemMap;
import com.dashboard.bean.DealersOrder;
import com.dashboard.model.DealerItemMapModel;
import com.dashboard.model.DealersOrderModel;
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
public class DealerItemMapController extends HttpServlet {

    private File tmpDir;

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        ServletContext ctx = getServletContext();
        request.setCharacterEncoding("UTF-8");
        response.setHeader("Content-Type", "text/plain; charset=UTF-8");
        DealerItemMapModel model = new DealerItemMapModel();

//        String search_item_name = "";
//        String search_designation = "";
//
//        search_item_name = request.getParameter("search_item_name");
//        search_designation = request.getParameter("search_designation");
        HttpSession session = request.getSession();
        String loggedUser = "";
        loggedUser = session.getAttribute("user_role").toString();

//        if (search_item_name == null) {
//            search_item_name = "";
//        }
//        if (search_designation == null) {
//            search_designation = "";
//        }
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
                if (JQstring != null) {
                    PrintWriter out = response.getWriter();
                    List<String> list = null;
                    JSONObject json = null;

                    if (JQstring.equals("getItemName")) {
                        String org_office_id = request.getParameter("org_office_id");
                        if (org_office_id == null) {
                            org_office_id = "";
                        }
                        list = model.getItemName(q, org_office_id);
                    }
                    if (JQstring.equals("getModel")) {
                        String item = request.getParameter("item");
                        if (item == null) {
                            item = "";
                        }
                        list = model.getModel(q, item);
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
            if (task == null) {
                task = "";
            }
            String org_office_id = request.getParameter("org_office_id");
            if (org_office_id == null) {
                org_office_id = "";
            }
            String org_office_name = model.getOrgOfficeName(org_office_id);

            if (task.equals("mapWithDealer")) {
                int dealer_item_map_id = 0;
                try {
                    dealer_item_map_id = Integer.parseInt(request.getParameter("dealer_item_map_id").trim());
                } catch (Exception e) {
                    dealer_item_map_id = 0;
                }

                DealerItemMap bean = new DealerItemMap();
                bean.setDealer_item_map_id(dealer_item_map_id);
                bean.setItem_name(request.getParameter("item_name").trim());
                bean.setModel(request.getParameter("model_name").trim());
                bean.setModel_id(request.getParameter("model_id").trim());
//                bean.setDescription(request.getParameter("description").trim());
                int rowsAffected = 0;
                if (dealer_item_map_id == 0) {
                    rowsAffected = model.insertRecord(bean, org_office_id);
                }
                JSONObject json = null;
                PrintWriter out = response.getWriter();
                List<String> list = null;
                if (json != null) {
                    out.println(json);
                } else {
                    JSONObject gson = new JSONObject();
                    String message = "";
                    if (rowsAffected > 0) {
                        message = "Item mapped with dealer...";
                    } else {
                        message = "Something Went Wrong...";
                    }

                    gson.put("msg", message);

                    out.println(gson);
                }
                DBConnection.closeConncetion(model.getConnection());
                return;

            }
            if (task.equals("deleteMapping")) {
                String dealer_item_map_id = request.getParameter("dealer_item_map_id").trim();
                model.deleteMapping(dealer_item_map_id);
            }

//            List<DealerItemMap> list = model.showData(org_office_id);
//            request.setAttribute("list", list);
////            request.setAttribute("search_item_name", search_item_name);
//            request.setAttribute("org_office_name", org_office_name);
//            request.setAttribute("org_office_id", org_office_id);
//            request.setAttribute("message", model.getMessage());
//            request.setAttribute("msgBgColor", model.getMsgBgColor());
//            request.setAttribute("loggedUser", loggedUser);
            ArrayList<DealerItemMap> list1 = model.getAllItems(org_office_id);
            ArrayList<DealerItemMap> list2 = new ArrayList<>();
            list2 = model.getAllModels(org_office_id, list1);

            // request.setAttribute("message", DealersOrderModel.getMessage());
//            request.setAttribute("msgBgColor", DealersOrderModel.getMessageBGColor());
            request.setAttribute("search_item", "");
            request.setAttribute("list1", list1);
            request.setAttribute("list2", list2);
            request.setAttribute("org_office_name", org_office_name);
            request.setAttribute("org_office_id", org_office_id);
            request.setAttribute("count", list2.size());
            DBConnection.closeConncetion(model.getConnection());

            request.getRequestDispatcher("dealer_item_map").forward(request, response);
        } catch (Exception ex) {
            System.out.println("ItemAuthorizationController error: " + ex);
        }
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        doGet(request, response);
    }
}
