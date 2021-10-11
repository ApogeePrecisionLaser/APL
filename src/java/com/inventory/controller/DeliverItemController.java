/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.inventory.controller;

import com.DBConnection.DBConnection;
import com.inventory.model.DeliverItemModel;
import com.inventory.tableClasses.DeliverItem;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Base64;
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
public class DeliverItemController extends HttpServlet {
    
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
        }

        DeliverItemModel model = new DeliverItemModel();

        String search_item_name = "";

        search_item_name = request.getParameter("search_item_name");

        try {
            model.setConnection(DBConnection.getConnectionForUtf(ctx));
        } catch (Exception e) {
            System.out.println("error in ApproveIndentController setConnection() calling try block" + e);
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
                System.out.println("\n Error --ApproveIndentController get JQuery Parameters Part-" + e);
            }

            String task = request.getParameter("task");

            if (task == null) {
                task = "";
            }

            if (task.equals("Delete")) {
                // model.deleteRecord(Integer.parseInt(request.getParameter("indent_table_id")));
            } else if (task.equals("deliverItem")) {
                PrintWriter out = response.getWriter();
                int indent_table_id = Integer.parseInt(request.getParameter("indent_table_id").trim());
                int indent_item_id = Integer.parseInt(request.getParameter("indent_item_id").trim());
                String status = request.getParameter("status");
                int delivered_qty = Integer.parseInt(request.getParameter("delivered_qty").trim());

                DeliverItem bean = new DeliverItem();
                bean.setStatus(status);
                bean.setDelivered_qty(delivered_qty);
                String message = model.updateRecord(bean, indent_table_id, indent_item_id);
                JSONObject json = new JSONObject();
                json.put("message", message);
                out.println(json);
                return;
            }

            //counting = model.getCounting();
            String autogenerate_indent_no = "Indent_" + counting;

            List<DeliverItem> list = model.showData(logged_designation);
            request.setAttribute("list", list);
            request.setAttribute("autogenerate_indent_no", autogenerate_indent_no);
            request.setAttribute("requested_by", logged_user_name);
            request.setAttribute("requested_to", office_admin);
            request.setAttribute("message", model.getMessage());
            request.setAttribute("msgBgColor", model.getMsgBgColor());

            model.closeConnection();

            request.getRequestDispatcher("deliver_item").forward(request, response);

        } catch (Exception ex) {
            System.out.println("ApproveIndentController error: " + ex);
        }
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        doGet(request, response);
    }
}
