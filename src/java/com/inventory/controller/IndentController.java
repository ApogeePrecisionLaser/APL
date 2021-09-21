/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.inventory.controller;

import com.DBConnection.DBConnection;
import com.inventory.model.IndentModel;
import com.inventory.model.InventoryModel;
import com.inventory.tableClasses.Indent;
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
public class IndentController extends HttpServlet {

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

        IndentModel model = new IndentModel();

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
            System.out.println("error in IndentController setConnection() calling try block" + e);
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
                    if (JQstring.equals("getItemsList")) {
                        JSONObject obj1 = new JSONObject();
                        JSONArray arrayObj = new JSONArray();
                        arrayObj = model.getItemsList(logged_designation);
                        obj1.put("item_name", arrayObj);
                        out.print(obj1);
                        return;
                    }
                    if (JQstring.equals("getParameter")) {
                        String type = request.getParameter("type");
                        if (type.equals("Status")) {
                            list = model.getStatus(q);
                        } else if (type.equals("Purpose")) {
                            list = model.getPurpose(q);
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
                System.out.println("\n Error --InventoryController get JQuery Parameters Part-" + e);
            }

            String task = request.getParameter("task");
            indent_no = request.getParameter("indent_no");
            requested_by = request.getParameter("requested_by");
            requested_to = request.getParameter("requested_to");
            description = request.getParameter("description");

            if (task == null) {
                task = "";
            }
            if (task.equals("GetItems")) {
                request.setAttribute("indent_no", indent_no);
                request.setAttribute("requested_by", requested_by);
                request.setAttribute("requested_to", requested_to);
                request.setAttribute("description", description);

                request.getRequestDispatcher("items_list").forward(request, response);
                return;
            }

            if (task.equals("Delete")) {
                model.deleteRecord(Integer.parseInt(request.getParameter("indent_table_id")));
            } else if (task.equals("Send Request") || task.equals("Save AS New") || task.equals("Save & Next")) {
                int indent_table_id = 0;
                int indent_item_id = 0;
                try {
                    indent_table_id = Integer.parseInt(request.getParameter("indent_table_id").trim());
                    indent_item_id = Integer.parseInt(request.getParameter("indent_item_id").trim());
                } catch (Exception e) {
                    indent_table_id = 0;
                    indent_item_id = 0;
                }

                if (task.equals("Save AS New")) {
                    indent_table_id = 0;
                    indent_item_id = 0;
                }

                Indent bean = new Indent();
                bean.setIndent_table_id(indent_table_id);
                bean.setIndent_no(indent_no);
                bean.setRequested_by(requested_by);
                bean.setRequested_to(requested_to);
                bean.setDescription(description);

                String[] names = request.getParameterValues("item_name");
                for (int i = 0; i < names.length; i++) {
                    String item_name = names[i];
                    if (!item_name.equals("")) {
                        bean.setIndent_item_id(indent_item_id);
                        bean.setItem_name(item_name);
                        bean.setPurpose(request.getParameter("purpose" + i + ""));
                        bean.setRequired_qty(Integer.parseInt(request.getParameter("required_qty" + i + "").trim()));
                        bean.setExpected_date_time(request.getParameter("expected_date_time" + i + ""));
                        if (indent_table_id == 0) {
                            model.insertRecord(bean, logged_user_name, office_admin, i);
                        }
                    }
                }

//                else {
//                    model.updateRecord(bean, indent_table_id);
//                }
            }
//else if (task.equals("Send Request")) {
//                int indent_item_id = 0;
//                try {
//                    indent_item_id = Integer.parseInt(request.getParameter("indent_item_id").trim());
//                } catch (Exception e) {
//                    indent_item_id = 0;
//                }
//
//                Indent bean = new Indent();
//                bean.setIndent_table_id(Integer.parseInt(request.getParameter("indent_tab_id").trim()));
//                bean.setIndent_item_id(indent_item_id);
//                bean.setItem_name(request.getParameter("item_name"));
//                bean.setPurpose(request.getParameter("purpose"));
//                bean.setRequired_qty(Integer.parseInt(request.getParameter("required_qty").trim()));
//                bean.setExpected_date_time(request.getParameter("expected_date_time"));
//
//                if (indent_item_id == 0) {
//                    model.insertItemRecord(bean);
//                }
//            }

            List<Inventory> list = model.showData(search_item_name, search_org_office, search_key_person, search_item_code);
            request.setAttribute("list", list);
            request.setAttribute("search_item_name", search_item_name);
            request.setAttribute("search_org_office", search_org_office);
            request.setAttribute("search_key_person", search_key_person);
            request.setAttribute("search_item_code", search_item_code);
            request.setAttribute("requested_by", logged_user_name);
            request.setAttribute("requested_to", office_admin);
//            request.setAttribute("last_indent_table_id", last_indent_table_id);
            request.setAttribute("message", model.getMessage());
            request.setAttribute("msgBgColor", model.getMsgBgColor());
            model.closeConnection();

            request.getRequestDispatcher("indent").forward(request, response);
        } catch (Exception ex) {
            System.out.println("IndentController error: " + ex);
        }
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        doGet(request, response);
    }
}
