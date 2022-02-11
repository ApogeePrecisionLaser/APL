package com.dashboard.controller;

import com.location.model.CityModel;
import com.location.bean.CityBean;
import com.DBConnection.DBConnection;
import com.dashboard.bean.DealersOrder;
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

public class OrdersHistoryController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        int lowerLimit, noOfRowsTraversed, noOfRowsToDisplay = 15, noOfRowsInTable;

        String logged_user_name = "";
        String logged_designation = "";
        String logged_org_name = "";
        String logged_org_office = "";
        int logged_org_office_id = 0;
        int logged_org_name_id = 0;
        int logged_key_person_id = 0;

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

        String task = request.getParameter("task");
        if (task == null) {
            task = "";
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
                total_discount_percent = total_discount_percent + Float.parseFloat(list.get(i).getDiscount_percent());
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
            request.getRequestDispatcher("historyOrderDetails").forward(request, response);

        }

        ArrayList<DealersOrder> list = model.getAllHistoryOrders(logged_user_name, loggedUser);
        request.setAttribute("message", model.getMessage());
        request.setAttribute("msgBgColor", model.getMessageBGColor());
        request.setAttribute("list", list);
        request.setAttribute("user_role", loggedUser);
        DBConnection.closeConncetion(model.getConnection());

        request.getRequestDispatcher("order_history").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        doGet(request, response);
    }
}
