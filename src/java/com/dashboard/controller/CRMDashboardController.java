package com.dashboard.controller;

import com.location.model.CityModel;
import com.location.bean.CityBean;
import com.DBConnection.DBConnection;
import com.dashboard.bean.DealersOrder;
import com.dashboard.bean.Enquiry;
import com.dashboard.bean.Profile;
import com.dashboard.model.DealersOrderModel;
import com.dashboard.model.EnquiryModel;
import com.dashboard.model.ProfileModel;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class CRMDashboardController extends HttpServlet {

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
        ProfileModel profileModel = new ProfileModel();
        EnquiryModel enquiryModel = new EnquiryModel();

        try {
            model.setConnection(DBConnection.getConnectionForUtf(ctx));
            profileModel.setConnection(DBConnection.getConnectionForUtf(ctx));
            enquiryModel.setConnection(DBConnection.getConnectionForUtf(ctx));

        } catch (Exception e) {
            System.out.println("error in CityController setConnection() calling try block" + e);
        }
        request.setCharacterEncoding("UTF-8");
        response.setHeader("Content-Type", "text/plain; charset=UTF-8");
        System.out.println(model.getConnection());

        try {
            String task = request.getParameter("task");
            if (task == null) {
                task = "";
            }

            if (task.equals("viewImage")) {
                String destinationPath = "";
                String type = request.getParameter("type");
                if (type == null) {
                    type = "";
                }
                String key_person_id = request.getParameter("key_person_id");
                if (key_person_id == null) {
                    key_person_id = "";
                }
                if (!key_person_id.equals("")) {
                    logged_key_person_id = Integer.parseInt(key_person_id);
                }
                destinationPath = model.getImagePath(String.valueOf(logged_key_person_id), type);
                if (destinationPath.isEmpty()) {
                    destinationPath = "C:\\ssadvt_repository\\APL\\key_person\\no_image.png";
                }

                File f = new File(destinationPath);
                FileInputStream fis = null;
                if (!f.exists()) {
                    destinationPath = "C:\\ssadvt_repository\\APL\\key_person\\no_image.png";
                    f = new File(destinationPath);
                }
                fis = new FileInputStream(f);
                if (destinationPath.contains("pdf")) {
                    response.setContentType("pdf");
                } else {
                    response.setContentType("image/jpeg");
                }

                BufferedInputStream bis = new BufferedInputStream(new FileInputStream(f));
                response.setContentLength(fis.available());
                ServletOutputStream os = response.getOutputStream();
                BufferedOutputStream out = new BufferedOutputStream(os);
                int ch = 0;
                while ((ch = bis.read()) != -1) {
                    out.write(ch);
                }
                bis.close();
                fis.close();
                out.close();
                os.close();
                response.flushBuffer();
                DBConnection.closeConncetion(model.getConnection());
                DBConnection.closeConncetion(profileModel.getConnection());
                DBConnection.closeConncetion(enquiryModel.getConnection());

                return;
            }

            if (task.equals("getDealerLocation")) {
                JSONObject obj1 = new JSONObject();
                JSONArray arrayObj = new JSONArray();
                arrayObj = model.getDealerLocation(logged_org_office, logged_user_name, loggedUser);
                obj1.put("dealers", arrayObj);
                PrintWriter out = response.getWriter();
                out.print(obj1);
                DBConnection.closeConncetion(model.getConnection());
                DBConnection.closeConncetion(profileModel.getConnection());
                DBConnection.closeConncetion(enquiryModel.getConnection());
                return;
            }

        } catch (Exception e) {
            System.out.println("errorr -" + e);
            return;
        }

        if (session.getAttribute("user_role").equals("Admin")) {
            ArrayList<DealersOrder> total_orders_list = model.getAllHistoryOrders(logged_user_name, loggedUser);
            List<Profile> dealers_list = profileModel.getAllDealers();
            ArrayList<Enquiry> total_enquiries_list = enquiryModel.getAllEnquiries();
            ArrayList<Enquiry> total_complaint_list = enquiryModel.getAllComplaints();
            ArrayList<DealersOrder> dashboard_pending_orders = model.getAllDashboardOrders(logged_user_name, session.getAttribute("user_role").toString());
            List<Profile> latest_dealers = profileModel.getAllLatestDealers();

            request.setAttribute("dashboard_pending_orders", dashboard_pending_orders);
            request.setAttribute("latest_dealers", latest_dealers);
            request.setAttribute("total_orders", total_orders_list.size());
            request.setAttribute("total_dealers", dealers_list.size());
            request.setAttribute("sales_enquiries", total_enquiries_list.size());
            request.setAttribute("complaint_enquiries", total_complaint_list.size());
            DBConnection.closeConncetion(model.getConnection());
            DBConnection.closeConncetion(profileModel.getConnection());
            DBConnection.closeConncetion(enquiryModel.getConnection());
            request.setAttribute("total_notification", ((total_enquiries_list.size()) + (total_complaint_list.size()) + 3));

            request.getRequestDispatcher("admin_dashboard").forward(request, response);

        }
        if (session.getAttribute("user_role").equals("Dealer")) {
            ArrayList<DealersOrder> pending_orders_list = model.getAllOrders(logged_user_name, session.getAttribute("user_role").toString());
            ArrayList<Enquiry> sales_enquiry_list = model.getAllEnquiriesForDealer(logged_key_person_id);
            ArrayList<Enquiry> complaint_enquiry_list = model.getAllComplaintForDealer(logged_key_person_id);

            ArrayList<DealersOrder> dashboard_pending_orders = model.getAllDashboardOrders(logged_user_name, session.getAttribute("user_role").toString());

            request.setAttribute("dashboard_pending_orders", dashboard_pending_orders);
            request.setAttribute("sales_enquiries", sales_enquiry_list.size());
            request.setAttribute("complaint_enquiries", complaint_enquiry_list.size());
            request.setAttribute("pending_orders", pending_orders_list.size());
            request.setAttribute("total_notification", ((sales_enquiry_list.size()) + (complaint_enquiry_list.size()) + 3));
            DBConnection.closeConncetion(model.getConnection());
            DBConnection.closeConncetion(profileModel.getConnection());
            DBConnection.closeConncetion(enquiryModel.getConnection());
            request.getRequestDispatcher("CRMDashboard").forward(request, response);

        }
        if (session.getAttribute("user_role").equals("Sales")) {
            ArrayList<DealersOrder> pending_orders_list = model.getAllPendingOrders(logged_user_name, session.getAttribute("user_role").toString(), "Pending");
            ArrayList<DealersOrder> approved_orders_list = model.getAllPendingOrders(logged_user_name, session.getAttribute("user_role").toString(), "Approved");
            ArrayList<DealersOrder> denied_orders_list = model.getAllPendingOrders(logged_user_name, session.getAttribute("user_role").toString(), "Denied");
            ArrayList<Enquiry> sales_enquiry_list = model.getAllEnquiries(session.getAttribute("user_role").toString(), logged_key_person_id);
            ArrayList<Enquiry> complaint_enquiry_list = model.getAllComplaints(session.getAttribute("user_role").toString(), logged_key_person_id);

            request.setAttribute("sales_enquiries", sales_enquiry_list.size());
            request.setAttribute("complaint_enquiries", complaint_enquiry_list.size());
            request.setAttribute("pending_orders", pending_orders_list.size());
            request.setAttribute("approved_orders", approved_orders_list.size());
            request.setAttribute("denied_orders", denied_orders_list.size());
            request.setAttribute("total_notification", ((sales_enquiry_list.size()) + (complaint_enquiry_list.size()) + 3));
            DBConnection.closeConncetion(model.getConnection());
            DBConnection.closeConncetion(profileModel.getConnection());
            DBConnection.closeConncetion(enquiryModel.getConnection());
            request.getRequestDispatcher("salesperson_dashboard").forward(request, response);

        }
//        else {
//            ArrayList<DealersOrder> pending_orders_list = model.getAllOrders(logged_user_name, loggedUser);
//
//            request.setAttribute("pending_orders", pending_orders_list.size());
//            DBConnection.closeConncetion(model.getConnection());
//            DBConnection.closeConncetion(profileModel.getConnection());
//            DBConnection.closeConncetion(enquiryModel.getConnection());
//            request.getRequestDispatcher("CRMDashboard").forward(request, response);
//        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        doGet(request, response);
    }
}
