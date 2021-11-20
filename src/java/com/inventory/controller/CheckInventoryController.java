/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.inventory.controller;

import com.DBConnection.DBConnection;
import com.inventory.model.CheckInventoryModel;
import com.inventory.tableClasses.CheckInventory;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
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
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author Komal
 */
public class CheckInventoryController extends HttpServlet {

    private File tmpDir;

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        ServletContext ctx = getServletContext();
        request.setCharacterEncoding("UTF-8");
        response.setHeader("Content-Type", "text/plain; charset=UTF-8");
        String logged_user_name = "";
        String logged_designation = "";
        String logged_org_name = "";
        String logged_org_office = "";
        int logged_org_office_id = 0;
        int logged_org_name_id = 0;
        int logged_key_person_id = 0;
        String office_admin = "";
        int last_indent_table_id = 0;
        int counting = 100;
        String indent_no = "";
        String requested_by = "";
        String requested_to = "";
        String description = "";
        String message_split[] = null;
        String user_role = "";
        String search_by_date = "";

        int final_indent_table_id = 0;
        HttpSession session = request.getSession();
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
            office_admin = session.getAttribute("office_admin").toString();
            user_role = session.getAttribute("user_role").toString();
        }

        CheckInventoryModel model = new CheckInventoryModel();

        String search_item_name = "";

        search_item_name = request.getParameter("search_item_name");
        search_by_date = request.getParameter("search_by_date");

        if (search_by_date == null) {
            search_by_date = "";
        }

        try {
            model.setConnection(DBConnection.getConnectionForUtf(ctx));
        } catch (Exception e) {
            System.out.println("error in CheckInventoryController setConnection() calling try block" + e);
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

                    if (JQstring.equals("getParameter")) {
                        String type = request.getParameter("type");
                        if (type.equals("Status")) {
                            list = model.getStatus(q);
                        }
                    }
                    if (JQstring.equals("getStatus")) {
                        list = model.getStatus(q);
                    }

                    if (JQstring.equals("getRequestedByKeyPerson")) {
                        list = model.getRequestedByKeyPerson(q);
                    }

                    if (JQstring.equals("getRequestedToKeyPerson")) {
                        String requested_by_person = request.getParameter("requested_by");
                        list = model.getRequestedToKeyPerson(q, requested_by_person);
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
                System.out.println("\n Error --CheckInventoryController get JQuery Parameters Part-" + e);
            }

            String task = request.getParameter("task");

            if (task == null) {
                task = "";
            }
            if (task.equals("GetIndentItems")) {
                // List<ItemName> list = null;
                //  String indent_no = request.getParameter("indent_no");
                int indent_table_id = Integer.parseInt(request.getParameter("indent_table_id").trim());
                String indent_status = request.getParameter("indent_status");
                List<CheckInventory> indent_items_list = model.getIndentItems(indent_table_id, logged_key_person_id);
                request.setAttribute("indent_items_list", indent_items_list);
                request.setAttribute("indent_status", indent_status);
                request.getRequestDispatcher("checkInventoryItemList").forward(request, response);
                return;
            }

            if (task.equals("Delete")) {
                // model.deleteRecord(Integer.parseInt(request.getParameter("indent_table_id")));
            } else if (task.equals("Generate Delivery Challan")) {
                PrintWriter out = response.getWriter();
                String message = "";
                int indent_table_id = 0;
                String indent_item_id_arr[] = request.getParameterValues("indent_item_id");
                for (int i = 0; i < indent_item_id_arr.length; i++) {
                    int indent_item_id = Integer.parseInt(indent_item_id_arr[i]);
                    String status_item = request.getParameter("item_status" + indent_item_id);
                    String item_name = request.getParameter("item_name" + indent_item_id);
                    indent_table_id = Integer.parseInt(request.getParameter("indent_table_id").trim());
                    String requested_by_id = request.getParameter("requested_by");
                    String requsted_to = request.getParameter("requested_to");
                    int delivered_qty = Integer.parseInt(request.getParameter("deliver_qty" + indent_item_id).trim());

                    //  System.err.println("delivered_qty----"+delivered_qty);
                    CheckInventory bean = new CheckInventory();
                    bean.setItem_status(status_item);
                    bean.setDelivered_qty(delivered_qty);
                    bean.setItem_name(item_name);
                    bean.setRequested_by_id(Integer.parseInt(requested_by_id));
                    message = model.updateRecord(bean, indent_item_id, indent_table_id, task, logged_org_office, logged_user_name);

                }
                message_split = message.split("&");
                final_indent_table_id = indent_table_id;

                request.setAttribute("final_message", message_split[0]);
                request.setAttribute("final_status", message_split[1]);
                request.setAttribute("final_indent_table_id", final_indent_table_id);
                request.setAttribute("task", task);
                request.getRequestDispatcher("checkInventoryItemList").forward(request, response);
                return;
            } else if (task.equals("GenerateDeliveryChallan")) {
                int indent_table_id = Integer.parseInt(request.getParameter("final_indent_table_id").trim());

                String indent_nos = model.getIndentNo(indent_table_id);
                counting = model.getCounting();
                String autogenerate_delivery_challan_no = "DC_" + counting;
                Date date = new Date();
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/YYYY");
                String delivery_challan_date = sdf.format(date);
                List<CheckInventory> deliverey_challan_items_list = model.getIndentItemsForDeliveryChallan(indent_table_id, logged_key_person_id);
                request.setAttribute("deliverey_challan_items_list", deliverey_challan_items_list);

                request.setAttribute("delivery_challan_date", delivery_challan_date);
                request.setAttribute("indent_nos", indent_nos);
                request.setAttribute("autogenerate_delivery_challan_no", autogenerate_delivery_challan_no);
                request.getRequestDispatcher("deliveryChallan").forward(request, response);
                return;
            }

            String status = "";
            String searchIndentStatusWise = request.getParameter("action1");
            if (searchIndentStatusWise == null) {
                searchIndentStatusWise = "";
            }
            if (searchIndentStatusWise.equals("searchIndentStatusWise")) {
                status = request.getParameter("status");
            }

            List<CheckInventory> list = model.showIndents(logged_designation, status, user_role, search_by_date);

            List<CheckInventory> status_list = model.getStatus();

            request.setAttribute("list", list);
            request.setAttribute("status_list", status_list);
            request.setAttribute("requested_by", logged_user_name);
            request.setAttribute("requested_to", office_admin);
            request.setAttribute("message", model.getMessage());
            request.setAttribute("msgBgColor", model.getMsgBgColor());

            model.closeConnection();

            request.getRequestDispatcher("check_inventory").forward(request, response);

        } catch (Exception ex) {
            System.out.println("CheckInventoryController error: " + ex);
        }
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        doGet(request, response);
    }
}
