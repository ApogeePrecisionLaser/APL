package com.dashboard.controller;

import com.location.model.CityModel;
import com.location.bean.CityBean;
import com.DBConnection.DBConnection;
import com.dashboard.bean.DealersOrder;
import com.dashboard.bean.Enquiry;
import com.dashboard.model.DealersOrderModel;
import java.io.ByteArrayOutputStream;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.json.simple.JSONObject;

public class ApproveOrdersController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String logged_user_name = "";
        String logged_designation = "";
        String logged_org_name = "";
        String logged_org_office = "";
        int logged_org_office_id = 0;
        int logged_org_name_id = 0;
        int logged_key_person_id = 0;
        String office_admin = "";
        String search_by_date = "";
        int last_indent_table_id = 0;
        int counting = 100;
        String indent_no = "";
        String requested_by = "";
        String requested_to = "";
        String description = "";
        String loggedUser = "";

        HttpSession session = request.getSession();
        if (session == null || session.getAttribute("logged_user_name") == null) {
            request.getRequestDispatcher("/").forward(request, response);
            return;
        } else {
            loggedUser = session.getAttribute("user_role").toString();
            logged_user_name = session.getAttribute("logged_user_name").toString();
            logged_org_name = session.getAttribute("logged_org_name").toString();
            logged_designation = session.getAttribute("logged_designation").toString();
            logged_org_office = session.getAttribute("logged_org_office").toString();
            logged_org_name_id = Integer.parseInt(session.getAttribute("logged_org_name_id").toString());
            logged_org_office_id = Integer.parseInt(session.getAttribute("logged_org_office_id").toString());
            logged_key_person_id = Integer.parseInt(session.getAttribute("logged_key_person_id").toString());
        }
        System.out.println("Starting application");
        response.setContentType("text/html");
        ServletContext ctx = getServletContext();
        DealersOrderModel model = new DealersOrderModel();

        try {
            model.setConnection(DBConnection.getConnectionForUtf(ctx));
        } catch (Exception e) {
            System.out.println("error in CityController setConnection() calling try block" + e);
        }
        request.setCharacterEncoding("UTF-8");
        response.setHeader("Content-Type", "text/plain; charset=UTF-8");
        System.out.println(model.getConnection());

        try {
            String JQstring = request.getParameter("action1");
            String q = request.getParameter("str");   // field own input           
            if (JQstring != null) {
                PrintWriter out = response.getWriter();
                List<String> list = null;
                if (JQstring.equals("getDealersStateWise")) {
                    String district = request.getParameter("district");
                    String city = request.getParameter("city");
                    String state = request.getParameter("state");
                    if (city == null) {
                        city = "";
                    }
                    if (state == null) {
                        state = "";
                    }
                    if (district == null) {
                        district = "";
                    }
                    list = model.getDealersStateWise(q, district, city, state);
                }

                JSONObject gson = new JSONObject();
                gson.put("list", list);
                out.println(gson);
                DBConnection.closeConncetion(model.getConnection());
                return;
            }
        } catch (Exception e) {
            System.out.println("\n Error --SalesEnquiryController get JQuery Parameters Part-" + e);
        }

        String task = request.getParameter("task");
        if (task == null) {
            task = "";
        }

        if (task.equals("sales_enquiry_list")) {
            ArrayList<Enquiry> list = model.getAllEnquiries(loggedUser, logged_key_person_id);
            request.setAttribute("list", list);
            DBConnection.closeConncetion(model.getConnection());

            request.getRequestDispatcher("salesperson_sales_enquiry_list").forward(request, response);
        }
        if (task.equals("complaint_enquiry_list")) {
            ArrayList<Enquiry> list = model.getAllComplaints(loggedUser, logged_key_person_id);
            request.setAttribute("list", list);
            DBConnection.closeConncetion(model.getConnection());

            request.getRequestDispatcher("salesperson_complaint_enquiry_list").forward(request, response);
        }

        if (task.equals("viewEnquiryDetails")) {
            String enquiry_table_id = request.getParameter("enquiry_table_id");
            ArrayList<Enquiry> list = model.getAllEnquiriesDetails(enquiry_table_id);
            request.setAttribute("list", list);
            DBConnection.closeConncetion(model.getConnection());

            request.getRequestDispatcher("salesperson_sales_enquiry_details").forward(request, response);
        }
        if (task.equals("viewComplaintDetails")) {
            String enquiry_table_id = request.getParameter("enquiry_table_id");
            ArrayList<Enquiry> list = model.getAllComplaintDetails(enquiry_table_id);
            request.setAttribute("list", list);
            DBConnection.closeConncetion(model.getConnection());

            request.getRequestDispatcher("salesperson_complaint_enquiry_details").forward(request, response);
        }

        if (task.equals("assignToDealer")) {
            PrintWriter out = response.getWriter();

            String dealer_name = request.getParameter("dealer_name");
            String enquiry_table_id = request.getParameter("enquiry_table_id");
            String message = model.assignToDealer(enquiry_table_id, dealer_name);
            JSONObject gson = new JSONObject();
            gson.put("message", message);
            out.println(gson);
            DBConnection.closeConncetion(model.getConnection());

            return;
        }
        if (task.equals("assignComplaintToDealer")) {
            PrintWriter out = response.getWriter();

            String dealer_name = request.getParameter("dealer_name");
            String enquiry_table_id = request.getParameter("enquiry_table_id");
            String message = model.assignComplaintToDealer(enquiry_table_id, dealer_name);
            JSONObject gson = new JSONObject();
            gson.put("message", message);
            out.println(gson);
            DBConnection.closeConncetion(model.getConnection());

            return;
        }

        if (task.equals("viewOrderDetails")) {
            String order_table_id = request.getParameter("order_table_id");
            String order_no = model.getOrderNo(order_table_id);

            ArrayList<DealersOrder> list = model.getAllOrderItems(order_table_id);

            float total_amount = 0;
            float total_discount_price = 0;
            float total_discount_percent = 0;
            float total_approved_price = 0;

            for (int i = 0; i < list.size(); i++) {
                total_amount = total_amount + Float.parseFloat(list.get(i).getBasic_price());
                total_discount_price = total_discount_price + Float.parseFloat(list.get(i).getDiscount_price());
//                total_discount_percent = total_discount_percent + Float.parseFloat(list.get(i).getDiscount_percent());
                total_approved_price = total_approved_price + Float.parseFloat(list.get(i).getApproved_price());
            }

            DBConnection.closeConncetion(model.getConnection());

            request.setAttribute("total_amount", total_amount);
            request.setAttribute("order_no", order_no);
            request.setAttribute("total_discount_price", total_discount_price);
            request.setAttribute("total_approved_price", total_approved_price);
            request.setAttribute("total_discount_percent", (String.format("%.2f", (((total_approved_price - total_discount_price) / total_approved_price) * 100))));
            request.setAttribute("list", list);
            request.setAttribute("count", list.size());
            request.getRequestDispatcher("approve_order_details").forward(request, response);

        } else if ((task.equals("Approve")) || (task.equals("Denied"))) {
            PrintWriter out = response.getWriter();
            int order_table_id = Integer.parseInt(request.getParameter("order_table_id").trim());
            String order_status = request.getParameter("order_status");
            if (order_status.equals("Pending")) {
                order_status = task;
            }

            // int indent_item_id = 0;
            int approved_qty = 0;
            int approved_price = 0;
            String item_status = "";
            String discounted_price = "";
            String discounted_percent = "";
            String order_item_id_arr[] = request.getParameterValues("order_item_id");

            for (int i = 0; i < order_item_id_arr.length; i++) {

                int order_item_id = Integer.parseInt(order_item_id_arr[i]);
                item_status = request.getParameter("item_status" + order_item_id);
                approved_qty = Integer.parseInt(request.getParameter("approved_qty" + order_item_id).trim());
                approved_price = (int) Float.parseFloat(request.getParameter("approved_price" + order_item_id).trim());
                discounted_price = request.getParameter("discounted_price" + order_item_id).trim();
                discounted_percent = request.getParameter("discounted_percent" + order_item_id).trim();
                DealersOrder bean = new DealersOrder();
                bean.setStatus(order_status);
                bean.setItem_status(item_status);
                bean.setApproved_qty(String.valueOf(approved_qty));
                bean.setApproved_price(String.valueOf(approved_price));
                bean.setDiscount_percent(String.valueOf(discounted_percent));
                bean.setDiscount_price(String.valueOf(discounted_price));
                String message = model.approveOrder(bean, order_item_id, order_table_id, i);

            }

            ArrayList<DealersOrder> list = model.getAllOrders(logged_user_name, loggedUser);

            DBConnection.closeConncetion(model.getConnection());

            request.setAttribute("list", list);
            request.setAttribute("count", list.size());
            request.getRequestDispatcher("salesperson_approve_order").forward(request, response);
            return;
        }

        ArrayList<DealersOrder> list = model.getAllOrders(logged_user_name, loggedUser);
        request.setAttribute("message", model.getMessage());
        request.setAttribute("msgBgColor", model.getMessageBGColor());
        request.setAttribute("list", list);
        DBConnection.closeConncetion(model.getConnection());

        request.getRequestDispatcher("salesperson_approve_order").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        doGet(request, response);
    }
}
