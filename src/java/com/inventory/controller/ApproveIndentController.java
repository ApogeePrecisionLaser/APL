/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.inventory.controller;

import com.DBConnection.DBConnection;
import com.inventory.model.ApproveIndentModel;
import com.inventory.tableClasses.ApproveIndent;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author Komal
 */
public class ApproveIndentController extends HttpServlet {

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
        int counting = 100;
        String message_split[] = null;
        int final_indent_table_id = 0;
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

        ApproveIndentModel model = new ApproveIndentModel();

        String search_item_name = "";

        search_item_name = request.getParameter("search_item_name");
        search_by_date = request.getParameter("search_by_date");

        if (search_by_date == null) {
            search_by_date = "";
        }

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
                    DBConnection.closeConncetion(model.getConnection());
                    return;
                }
            } catch (Exception e) {
                System.out.println("\n Error --ApproveIndentController get JQuery Parameters Part-" + e);
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
                List<ApproveIndent> indent_items_list = model.getIndentItems(indent_table_id, logged_org_office,loggedUser);
                request.setAttribute("indent_items_list", indent_items_list);
                request.setAttribute("indent_status", indent_status);
                request.getRequestDispatcher("approveIndentItemList").forward(request, response);
                DBConnection.closeConncetion(model.getConnection());
                return;
            }
            if (task.equals("Delete")) {
                // model.deleteRecord(Integer.parseInt(request.getParameter("indent_table_id")));
            } else if ((task.equals("Approve")) || (task.equals("Denied"))) {
                PrintWriter out = response.getWriter();
                int indent_table_id = Integer.parseInt(request.getParameter("indent_table_id").trim());
                String indent_status = request.getParameter("status");
                if (indent_status.equals("Pending")) {
                    indent_status = task;
                }

                // int indent_item_id = 0;
                int approved_qty = 0;
                String item_status = "";
                String indent_item_id_arr[] = request.getParameterValues("indent_item_id");

                for (int i = 0; i < indent_item_id_arr.length; i++) {

                    int indent_item_id = Integer.parseInt(indent_item_id_arr[i]);
                    item_status = request.getParameter("item_status" + indent_item_id);
                    approved_qty = Integer.parseInt(request.getParameter("approved_qty" + indent_item_id).trim());
                    ApproveIndent bean = new ApproveIndent();
                    bean.setStatus(indent_status);
                    bean.setItem_status(item_status);
                    bean.setApproved_qty(approved_qty);
                    String message = model.updateRecord(bean, indent_item_id, indent_table_id);
                    message_split = message.split("&");
                    final_indent_table_id = indent_table_id;
                }

                request.setAttribute("final_message", message_split[0]);
                request.setAttribute("final_status", message_split[1]);
                request.setAttribute("final_indent_table_id", final_indent_table_id);
                request.getRequestDispatcher("approveIndentItemList").forward(request, response);
                DBConnection.closeConncetion(model.getConnection());
                return;
            }

            //counting = model.getCounting();
            String autogenerate_indent_no = "Indent_" + counting;
            String status = "";
            String searchIndentStatusWise = request.getParameter("action1");
            if (searchIndentStatusWise == null) {
                searchIndentStatusWise = "";
            }
            if (searchIndentStatusWise.equals("searchIndentStatusWise")) {
                status = request.getParameter("status");
            }
            List<ApproveIndent> list = model.showIndents(logged_user_name, status, search_by_date);
            List<ApproveIndent> status_list = model.getStatus();

            request.setAttribute("list", list);
            request.setAttribute("status_list", status_list);
            request.setAttribute("autogenerate_indent_no", autogenerate_indent_no);
            request.setAttribute("requested_by", logged_user_name);
            request.setAttribute("requested_to", office_admin);
            request.setAttribute("message", model.getMessage());
            request.setAttribute("msgBgColor", model.getMsgBgColor());
            request.setAttribute("loggedUser", loggedUser);

            DBConnection.closeConncetion(model.getConnection());

            request.getRequestDispatcher("approve_indent").forward(request, response);

        } catch (Exception ex) {
            System.out.println("ApproveIndentController error: " + ex);
        }
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        doGet(request, response);
    }
}
