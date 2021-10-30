/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.inventory.controller;

import com.DBConnection.DBConnection;
import com.inventory.model.IndentModel;
import com.inventory.tableClasses.Indent;
import com.inventory.tableClasses.ItemName;
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
public class IndentController extends HttpServlet {

    private File tmpDir;
    List<String> import_item_name_arr = new ArrayList<String>();

    List<String> import_purpose_arr = new ArrayList<String>();
    List<String> import_expected_date_time_arr = new ArrayList<String>();
    List<Integer> import_req_qty_arr = new ArrayList<Integer>();
    List<Indent> imported_list = new ArrayList<Indent>();
    String import_item_name = "";
    String import_purpose = "";
    String import_expected_date_time = "";
    int import_req_qty = 0;

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
        String search_by_date = "";

        HttpSession session = request.getSession();
        String loggedUser = "";
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
            office_admin = session.getAttribute("office_admin").toString();
        }

        IndentModel model = new IndentModel();

        String search_item_name = "";
        search_by_date = request.getParameter("search_by_date");

        if (search_item_name == null) {
            search_item_name = "";
        }

        if (search_by_date == null) {
            search_by_date = "";
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

            if (task == null) {
                task = "";
            }
            String encodedStringBtoA = request.getParameter("encodedStringBtoA");
            if (encodedStringBtoA == null) {
                encodedStringBtoA = "";
            }
            byte[] decodedBytes = Base64.getDecoder().decode(encodedStringBtoA);
            String decodedString = new String(decodedBytes);
            String checkedValue = "";
            int req_qty = 0;
            String purpose = "";
            String item_name = "";
            String expected_date_time = "";
            if (task.equals("GetItems")) {
                List<ItemName> list = null;
                if (!decodedString.equals("[]")) {
                    JSONArray jsonArr = new JSONArray(decodedString);

                    for (int i = 0; i < jsonArr.length(); i++) {
                        JSONObject jsonObj = jsonArr.getJSONObject(i);

                        Iterator<String> keys = jsonObj.keys();

                        while (keys.hasNext()) {
                            String key = keys.next();
                            checkedValue = (String) jsonObj.get("checkedValue");
                            req_qty = (int) jsonObj.get("req_qty");
                            purpose = (String) jsonObj.get("purpose");
                            item_name = (String) jsonObj.get("item_name");
                            expected_date_time = (String) jsonObj.get("expected_date_time");
                        }
                        System.out.println(jsonObj);
                        list = model.getItemsList(logged_designation, checkedValue, req_qty, purpose, item_name, expected_date_time);

                    }

                } else {
                    list = model.getItemsList(logged_designation, checkedValue, req_qty, purpose, item_name, expected_date_time);
                }

                request.setAttribute("list", list);
                request.getRequestDispatcher("items_list").forward(request, response);
                return;
            }

            if (task.equals("GetIndentItems")) {
                List<ItemName> list = null;
                //  String indent_no = request.getParameter("indent_no");
                int indent_table_id = Integer.parseInt(request.getParameter("indent_table_id").trim());

                List<Indent> indent_items_list = model.getIndentItems(indent_table_id);
                request.setAttribute("indent_items_list", indent_items_list);
                request.getRequestDispatcher("showIndentItemList").forward(request, response);
                return;
            }

            if (task.equals("Delete")) {
                // model.deleteRecord(Integer.parseInt(request.getParameter("indent_table_id")));
            } else if (task.equals("Send Indent") || task.equals("Save AS New") || task.equals("Save & Next")) {
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
                bean.setIndent_no(request.getParameter("indent_no"));
                bean.setRequested_by(request.getParameter("requested_by"));
                bean.setRequested_to(request.getParameter("requested_to"));
                bean.setDescription(request.getParameter("description"));

                String[] checked_id = request.getParameterValues("checked_id");
                for (int i = 0; i < checked_id.length; i++) {
                    String checked_item = checked_id[i];
                    if (!checked_item.equals("")) {
                        bean.setIndent_item_id(indent_item_id);
                        bean.setItem_name(request.getParameter("item_name" + i + ""));
                        bean.setPurpose(request.getParameter("purpose" + i + ""));
                        bean.setRequired_qty(Integer.parseInt(request.getParameter("req_qty" + i + "")));
                        bean.setExpected_date_time(request.getParameter("expected_date_time" + i + ""));
                        if (indent_table_id == 0) {
                            model.insertRecord(bean, logged_user_name, office_admin, i);
                        }
                    }
                }

            }

            counting = model.getCounting();
            String autogenerate_indent_no = "Indent_" + counting;
            String status = "";
            String searchIndentStatusWise = request.getParameter("action1");
            if (searchIndentStatusWise == null) {
                searchIndentStatusWise = "";
            }
            if (searchIndentStatusWise.equals("searchIndentStatusWise")) {
                status = request.getParameter("status");
            }

//            List<Indent> list = model.showData(logged_user_name, office_admin);
            List<Indent> list = model.showData(logged_user_name, office_admin, status, search_by_date);
            List<Indent> status_list = model.getStatus();

            request.setAttribute("list", list);
            request.setAttribute("status_list", status_list);
            request.setAttribute("autogenerate_indent_no", autogenerate_indent_no);
            request.setAttribute("requested_by", logged_user_name);
            request.setAttribute("requested_to", office_admin);
            request.setAttribute("message", model.getMessage());
            request.setAttribute("msgBgColor", model.getMsgBgColor());
            request.setAttribute("loggedUser", loggedUser);

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
