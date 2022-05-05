package com.dashboard.controller;

import com.DBConnection.DBConnection;
import com.dashboard.bean.DealersOrder;
import com.dashboard.bean.Enquiry;
import com.dashboard.bean.EventBean;
import com.dashboard.bean.Help;
import com.dashboard.bean.Profile;
import com.dashboard.model.DealersOrderModel;
import com.dashboard.model.EnquiryModel;
import com.dashboard.model.EventModel;
import com.dashboard.model.HelpModel;
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

/**
 *
 * @author Komal
 */
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
        HelpModel helpModel = new HelpModel();
        EventModel eventModel = new EventModel();

        try {
            model.setConnection(DBConnection.getConnectionForUtf(ctx));
            profileModel.setConnection(DBConnection.getConnectionForUtf(ctx));
            enquiryModel.setConnection(DBConnection.getConnectionForUtf(ctx));
            helpModel.setConnection(DBConnection.getConnectionForUtf(ctx));
            eventModel.setConnection(DBConnection.getConnectionForUtf(ctx));

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
            if (task.equals("getData")) {
                if (session.getAttribute("user_role").equals("Admin")) {
                    ArrayList<Enquiry> pending_enquiries_list = enquiryModel.getAllPendingEnquiries("", "Pending");
                    ArrayList<Enquiry> pending_complaint_list = enquiryModel.getAllPendingComplaints("", "Pending");
                    ArrayList<EventBean> pending_news = eventModel.getAllEvents(logged_key_person_id, loggedUser);

                    String last_time_of_enquiry = "";
                    String last_time_of_complaint = "";
                    for (int j = 0; j < 1; j++) {
                        if (pending_enquiries_list.size() > 0) {
                            last_time_of_enquiry = pending_enquiries_list.get(j).getEnquiry_date_time().toString();
                        }
                        if (pending_complaint_list.size() > 0) {
                            last_time_of_complaint = pending_complaint_list.get(j).getEnquiry_date_time().toString();
                        }
                    }

                    JSONObject obj1 = new JSONObject();
                    obj1.put("pending_sales_enquiries", pending_enquiries_list.size());
                    obj1.put("pending_complaint_enquiries", pending_complaint_list.size());
                    obj1.put("last_time_of_complaint", last_time_of_complaint);
                    obj1.put("last_time_of_enquiry", last_time_of_enquiry);
                    obj1.put("pending_news", pending_news.size());
                    obj1.put("total_notification", ((pending_enquiries_list.size()) + (pending_complaint_list.size())));
                    PrintWriter out = response.getWriter();
                    out.print(obj1);

                    DBConnection.closeConncetion(enquiryModel.getConnection());
                    DBConnection.closeConncetion(eventModel.getConnection());
                    return;

                }
                if (session.getAttribute("user_role").equals("Dealer")) {
                    ArrayList<Enquiry> pending_sales_enquiry_list = model.getPendingEnquiriesForDealer(logged_key_person_id);
                    ArrayList<Enquiry> pending_complaint_enquiry_list = model.getPendingComplaintForDealer(logged_key_person_id);
                    ArrayList<EventBean> pending_news = model.getEvents(logged_key_person_id, loggedUser);

                    String last_time_of_enquiry = "";
                    String last_time_of_complaint = "";
                    String last_time_of_news = "";
                    for (int j = 0; j < 1; j++) {
                        if (pending_sales_enquiry_list.size() > 0) {
                            last_time_of_enquiry = pending_sales_enquiry_list.get(j).getEnquiry_date_time().toString();
                        }
                        if (pending_complaint_enquiry_list.size() > 0) {
                            last_time_of_complaint = pending_complaint_enquiry_list.get(j).getEnquiry_date_time().toString();
                        }
                        if (pending_news.size() > 0) {
                            last_time_of_news = pending_news.get(j).getDate_time().toString();
                        }
                    }

                    JSONObject obj1 = new JSONObject();
                    obj1.put("pending_sales_enquiries", pending_sales_enquiry_list.size());
                    obj1.put("pending_complaint_enquiries", pending_complaint_enquiry_list.size());
                    obj1.put("pending_news", pending_news.size());
                    obj1.put("last_time_of_complaint", last_time_of_complaint);
                    obj1.put("last_time_of_enquiry", last_time_of_enquiry);
                    obj1.put("last_time_of_news", last_time_of_news);
                    obj1.put("total_notification", ((pending_sales_enquiry_list.size()) + (pending_complaint_enquiry_list.size())
                            + (pending_news.size())));
                    PrintWriter out = response.getWriter();
                    out.print(obj1);

                    DBConnection.closeConncetion(model.getConnection());
                    return;
                }
                if (session.getAttribute("user_role").equals("Sales")) {
                    ArrayList<Enquiry> pending_sales_enquiry_list = model.getAllPendingEnquiries(session.getAttribute("user_role").toString(), logged_key_person_id, "", "Pending");
                    ArrayList<Enquiry> pending_complaint_enquiry_list = model.getAllPendingComplaints(session.getAttribute("user_role").toString(), logged_key_person_id, "", "Pending");

                    String last_time_of_enquiry = "";
                    String last_time_of_complaint = "";

                    for (int j = 0; j < 1; j++) {
                        if (pending_sales_enquiry_list.size() > 0) {
                            last_time_of_enquiry = pending_sales_enquiry_list.get(j).getEnquiry_date_time().toString();
                        }
                        if (pending_complaint_enquiry_list.size() > 0) {
                            last_time_of_complaint = pending_complaint_enquiry_list.get(j).getEnquiry_date_time().toString();
                        }
                    }
                    JSONObject obj1 = new JSONObject();
                    obj1.put("pending_sales_enquiries", pending_sales_enquiry_list.size());
                    obj1.put("pending_complaint_enquiries", pending_complaint_enquiry_list.size());
                    obj1.put("last_time_of_complaint", last_time_of_complaint);
                    obj1.put("last_time_of_enquiry", last_time_of_enquiry);
                    obj1.put("total_notification", ((pending_sales_enquiry_list.size()) + (pending_complaint_enquiry_list.size())));
                    PrintWriter out = response.getWriter();
                    out.print(obj1);

                    DBConnection.closeConncetion(model.getConnection());
                    return;
                }
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

                return;
            }

            if (task.equals("getAllSalesEnquiries")) {
                // List<DashboardReportsBean> list = model.getOverheadtankWaterdata();
                model.setConnection(DBConnection.getConnectionForUtf(ctx));
                String sales_enquiry_source = request.getParameter("sales_enquiry_source");
                if (sales_enquiry_source == null) {
                    sales_enquiry_source = "";
                }

                JSONObject obj1 = new JSONObject();
                JSONArray arrayObj = new JSONArray();

                arrayObj = model.getAllSalesEnquiries(sales_enquiry_source);

                obj1.put("sales_enquiries", arrayObj);
                PrintWriter out = response.getWriter();
                out.print(obj1);
                DBConnection.closeConncetion(model.getConnection());

                //System.err.println("water_data obj**************"+obj1.toString());
                return;

            }

            if (task.equals("getAllComplaintEnquiries")) {
                // List<DashboardReportsBean> list = model.getOverheadtankWaterdata();
                model.setConnection(DBConnection.getConnectionForUtf(ctx));

                JSONObject obj1 = new JSONObject();
                JSONArray arrayObj = new JSONArray();

                arrayObj = model.getAllComplaintEnquiries();

                obj1.put("complaint_enquiries", arrayObj);
                PrintWriter out = response.getWriter();
                out.print(obj1);
                DBConnection.closeConncetion(model.getConnection());

                //System.err.println("water_data obj**************"+obj1.toString());
                return;

            }

        } catch (Exception e) {
            System.out.println("errorr -" + e);
            return;
        }

        if (session.getAttribute("user_role").equals("Admin")) {
            ArrayList<DealersOrder> total_orders_list = model.getAllHistoryOrders(logged_user_name, loggedUser);
            List<Profile> dealers_list = profileModel.getAllDealers();
            ArrayList<Enquiry> total_enquiries_list = enquiryModel.getAllPendingEnquiries("", "Total");
            ArrayList<Enquiry> total_complaint_list = enquiryModel.getAllPendingComplaints("", "Total");

            ArrayList<Enquiry> pending_enquiries_list = enquiryModel.getAllPendingEnquiries("", "Pending");
            ArrayList<Enquiry> pending_complaint_list = enquiryModel.getAllPendingComplaints("", "Pending");
            ArrayList<DealersOrder> dashboard_pending_orders = model.getAllDashboardOrders(logged_user_name, session.getAttribute("user_role").toString());
            List<Profile> latest_dealers = profileModel.getAllLatestDealers();
            List<Help> supportMessages = helpModel.getAllSupportMessages();

            ArrayList<DealersOrder> allModels = model.getAllLatestItems(String.valueOf(logged_org_office_id));
//            ArrayList<DealerItemMap> allModels = new ArrayList<>();
//            allModels = dealerItemMapModel.getAllModels(String.valueOf(logged_org_office_id), allItems);
            ArrayList<EventBean> pending_news = eventModel.getAllEvents(logged_key_person_id, loggedUser);

            String last_time_of_enquiry = "";
            String last_time_of_complaint = "";
            for (int j = 0; j < 1; j++) {
                if (pending_enquiries_list.size() > 0) {
                    last_time_of_enquiry = pending_enquiries_list.get(j).getEnquiry_date_time().toString();
                }
                if (pending_complaint_list.size() > 0) {
                    last_time_of_complaint = pending_complaint_list.get(j).getEnquiry_date_time().toString();
                }
            }
            request.setAttribute("email", session.getAttribute("log_email").toString());
            request.setAttribute("password", session.getAttribute("password").toString());
            request.setAttribute("allProducts", allModels.size());
            request.setAttribute("allModels", allModels);
            request.setAttribute("dashboard_pending_orders", dashboard_pending_orders);
            request.setAttribute("dashboard_pending_orders_count", dashboard_pending_orders.size());
            request.setAttribute("supportMessages", supportMessages.size());
            request.setAttribute("latest_dealers", latest_dealers);
            request.setAttribute("total_orders", total_orders_list.size());
            request.setAttribute("total_dealers", dealers_list.size());
            request.setAttribute("sales_enquiries", total_enquiries_list.size());
            request.setAttribute("complaint_enquiries", total_complaint_list.size());
            request.setAttribute("pending_sales_enquiries", pending_enquiries_list.size());
            request.setAttribute("pending_complaint_enquiries", pending_complaint_list.size());
            request.setAttribute("pending_news", pending_news.size());
            request.setAttribute("last_time_of_complaint", last_time_of_complaint);
            request.setAttribute("last_time_of_enquiry", last_time_of_enquiry);

            DBConnection.closeConncetion(model.getConnection());
            DBConnection.closeConncetion(profileModel.getConnection());
            DBConnection.closeConncetion(enquiryModel.getConnection());
            DBConnection.closeConncetion(helpModel.getConnection());
            DBConnection.closeConncetion(eventModel.getConnection());
            request.setAttribute("total_notification", ((pending_enquiries_list.size()) + (pending_complaint_list.size())));

            request.getRequestDispatcher("admin_dashboard").forward(request, response);

        }
        if (session.getAttribute("user_role").equals("Dealer")) {
            ArrayList<DealersOrder> pending_orders_list = model.getAllOrders(logged_user_name, session.getAttribute("user_role").toString());
            ArrayList<Enquiry> sales_enquiry_list = model.getAllEnquiriesForDealer(logged_key_person_id);
            ArrayList<Enquiry> complaint_enquiry_list = model.getAllComplaintForDealer(logged_key_person_id);

            ArrayList<Enquiry> pending_sales_enquiry_list = model.getPendingEnquiriesForDealer(logged_key_person_id);
            ArrayList<Enquiry> pending_complaint_enquiry_list = model.getPendingComplaintForDealer(logged_key_person_id);

            ArrayList<DealersOrder> dashboard_pending_orders = model.getAllDashboardOrders(logged_user_name, session.getAttribute("user_role").toString());
            ArrayList<EventBean> pending_news = model.getEvents(logged_key_person_id, loggedUser);

            String last_time_of_enquiry = "";
            String last_time_of_complaint = "";
            String last_time_of_news = "";
            for (int j = 0; j < 1; j++) {
                if (pending_sales_enquiry_list.size() > 0) {
                    last_time_of_enquiry = pending_sales_enquiry_list.get(j).getEnquiry_date_time().toString();
                }
                if (pending_complaint_enquiry_list.size() > 0) {
                    last_time_of_complaint = pending_complaint_enquiry_list.get(j).getEnquiry_date_time().toString();
                }
                if (pending_news.size() > 0) {
                    last_time_of_news = pending_news.get(j).getDate_time().toString();
                }
            }
            request.setAttribute("email", session.getAttribute("log_email").toString());
            request.setAttribute("password", session.getAttribute("password").toString());
            request.setAttribute("last_time_of_enquiry", last_time_of_enquiry);
            request.setAttribute("last_time_of_complaint", last_time_of_complaint);
            request.setAttribute("last_time_of_news", last_time_of_news);
            request.setAttribute("dashboard_pending_orders", dashboard_pending_orders);
            request.setAttribute("dashboard_pending_orders_count", dashboard_pending_orders.size());
            request.setAttribute("pending_news_list", pending_news);
            request.setAttribute("sales_enquiries", sales_enquiry_list.size());
            request.setAttribute("complaint_enquiries", complaint_enquiry_list.size());
            request.setAttribute("pending_sales_enquiries", pending_sales_enquiry_list.size());
            request.setAttribute("pending_complaint_enquiries", pending_complaint_enquiry_list.size());
            request.setAttribute("pending_news", pending_news.size());
            request.setAttribute("pending_orders", pending_orders_list.size());
            request.setAttribute("total_notification", ((pending_sales_enquiry_list.size()) + (pending_complaint_enquiry_list.size())
                    + (pending_news.size())));
            DBConnection.closeConncetion(model.getConnection());
            request.getRequestDispatcher("CRMDashboard").forward(request, response);

        }
        if (session.getAttribute("user_role").equals("Sales")) {
            ArrayList<DealersOrder> pending_orders_list = model.getAllApprovedOrders(logged_user_name, session.getAttribute("user_role").toString(), "Pending");
            ArrayList<DealersOrder> approved_orders_list = model.getAllApprovedOrders(logged_user_name, session.getAttribute("user_role").toString(), "Approved");
            ArrayList<DealersOrder> denied_orders_list = model.getAllApprovedOrders(logged_user_name, session.getAttribute("user_role").toString(), "Denied");
            ArrayList<Enquiry> sales_enquiry_list = model.getAllEnquiries(session.getAttribute("user_role").toString(), logged_key_person_id, "", "");
            ArrayList<Enquiry> complaint_enquiry_list = model.getAllComplaints(session.getAttribute("user_role").toString(), logged_key_person_id, "", "");

            ArrayList<Enquiry> pending_sales_enquiry_list = model.getAllPendingEnquiries(session.getAttribute("user_role").toString(), logged_key_person_id, "", "Pending");
            ArrayList<Enquiry> pending_complaint_enquiry_list = model.getAllPendingComplaints(session.getAttribute("user_role").toString(), logged_key_person_id, "", "Pending");

            String last_time_of_enquiry = "";
            String last_time_of_complaint = "";

            for (int j = 0; j < 1; j++) {
                if (pending_sales_enquiry_list.size() > 0) {
                    last_time_of_enquiry = pending_sales_enquiry_list.get(j).getEnquiry_date_time().toString();
                }
                if (pending_complaint_enquiry_list.size() > 0) {
                    last_time_of_complaint = pending_complaint_enquiry_list.get(j).getEnquiry_date_time().toString();
                }
            }
            request.setAttribute("email", session.getAttribute("log_email").toString());
            request.setAttribute("password", session.getAttribute("password").toString());
            request.setAttribute("last_time_of_enquiry", last_time_of_enquiry);
            request.setAttribute("last_time_of_complaint", last_time_of_complaint);
            request.setAttribute("sales_enquiries", sales_enquiry_list.size());
            request.setAttribute("complaint_enquiries", complaint_enquiry_list.size());
            request.setAttribute("pending_sales_enquiries", pending_sales_enquiry_list.size());
            request.setAttribute("pending_complaint_enquiries", pending_complaint_enquiry_list.size());
            request.setAttribute("pending_orders", pending_orders_list.size());
            request.setAttribute("approved_orders", approved_orders_list.size());
            request.setAttribute("denied_orders", denied_orders_list.size());
            request.setAttribute("total_notification", ((pending_sales_enquiry_list.size()) + (pending_complaint_enquiry_list.size())));
            DBConnection.closeConncetion(model.getConnection());
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
