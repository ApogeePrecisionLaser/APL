package com.dashboard.controller;

import com.DBConnection.DBConnection;
import com.dashboard.bean.Enquiry;
import com.dashboard.model.EnquiryModel;
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
import org.json.simple.JSONObject;


/**
 *
 * @author Komal
 */
public class SalesEnquiryController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        System.out.println("Starting application");
        response.setContentType("text/html");
        ServletContext ctx = getServletContext();
        EnquiryModel model = new EnquiryModel();

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

        try {
            model.setConnection(DBConnection.getConnectionForUtf(ctx));
        } catch (Exception e) {
            System.out.println("error in SalesEnquiryController setConnection() calling try block" + e);
        }

        request.setCharacterEncoding("UTF-8");
        response.setHeader("Content-Type", "text/plain; charset=UTF-8");
        System.out.println(model.getConnection());
        String task = request.getParameter("task");

        try {
            String JQstring = request.getParameter("action1");
            String q = request.getParameter("str");   // field own input           
            if (JQstring != null) {
                PrintWriter out = response.getWriter();
                List<String> list = null;
                if (JQstring.equals("getEnquirySource")) {
                    list = model.getEnquirySource(q);
                }
                if (JQstring.equals("getMarketingVertical")) {
                    list = model.getMarketingVertical(q);
                }
                if (JQstring.equals("getDistrict")) {
                    list = model.getDistrict(q, loggedUser, logged_key_person_id);
                }
                if (JQstring.equals("getCities")) {
                    list = model.getCities(q);
                }
                if (JQstring.equals("getStates")) {
                    list = model.getStates(q);
                }
                if (JQstring.equals("getCountry")) {
                    list = model.getCountry(q);
                }
                if (JQstring.equals("getStatus")) {
                    list = model.getStatus(q);
                }
                JSONObject gson = new JSONObject();
                gson.put("list", list);
                out.println(gson);
//                model.closeConnection();
                DBConnection.closeConncetion(model.getConnection());
                return;
            }
        } catch (Exception e) {
            System.out.println("\n Error --SalesEnquiryController get JQuery Parameters Part-" + e);
        }

        if (task == null) {
            task = "";
        }

        if (task.equals("sales_enquiry_list")) {
            String enquiry_source = request.getParameter("enquiry_source");
            String status = request.getParameter("status");
            if (enquiry_source == null) {
                enquiry_source = "";
            }
            if (status == null) {
                status = "";
            }

            ArrayList<Enquiry> list = model.getAllEnquiries(enquiry_source, status);
            request.setAttribute("list", list);
            request.setAttribute("enquiry_source", enquiry_source);
            request.setAttribute("status", status);
            DBConnection.closeConncetion(model.getConnection());

            request.getRequestDispatcher("sales_enquiry_list").forward(request, response);
        }
        if (task.equals("complaint_enquiry_list")) {
            String enquiry_source = request.getParameter("enquiry_source");
            String status = request.getParameter("status");
            if (enquiry_source == null) {
                enquiry_source = "";
            }
            if (status == null) {
                status = "";
            }
            ArrayList<Enquiry> list = model.getAllComplaints(enquiry_source, status);
            request.setAttribute("list", list);
            request.setAttribute("enquiry_source", enquiry_source);
            request.setAttribute("status", status);
            DBConnection.closeConncetion(model.getConnection());

            request.getRequestDispatcher("complaint_enquiry_list").forward(request, response);
        }

        if (task.equals("viewEnquiryDetails")) {
            String enquiry_table_id = request.getParameter("enquiry_table_id");
            ArrayList<Enquiry> list = model.getAllEnquiriesDetails(enquiry_table_id);
            request.setAttribute("list", list);
            request.setAttribute("enquiry_table_id", enquiry_table_id);
            DBConnection.closeConncetion(model.getConnection());

            request.getRequestDispatcher("sales_enquiry_details").forward(request, response);
        }
        if (task.equals("viewComplaintDetails")) {
            String enquiry_table_id = request.getParameter("enquiry_table_id");
            ArrayList<Enquiry> list = model.getAllComplaintDetails(enquiry_table_id);
            request.setAttribute("list", list);
            request.setAttribute("enquiry_table_id", enquiry_table_id);
            DBConnection.closeConncetion(model.getConnection());

            request.getRequestDispatcher("complaint_enquiry_details").forward(request, response);
        }
        if (task.equals("assignToSalesPerson")) {
            String state = request.getParameter("state");
            String city = request.getParameter("city");
            String enquiry_table_id = request.getParameter("enquiry_table_id");
            model.assignToSalesPerson(enquiry_table_id, state, city);
            String enquiry_source = request.getParameter("enquiry_source");
            String status = request.getParameter("status");
            if (enquiry_source == null) {
                enquiry_source = "";
            }
            if (status == null) {
                status = "";
            }

            ArrayList<Enquiry> list = model.getAllEnquiries(enquiry_source, status);
            request.setAttribute("list", list);
            DBConnection.closeConncetion(model.getConnection());

            request.getRequestDispatcher("sales_enquiry_list").forward(request, response);
        }
        if (task.equals("assignComplaintToSalesPerson")) {
            String state = request.getParameter("state");
            String city = request.getParameter("city");
            String complaint_table_id = request.getParameter("enquiry_table_id");
            model.assignComplaintToSalesPerson(complaint_table_id, state, city);
            String enquiry_source = request.getParameter("enquiry_source");
            String status = request.getParameter("status");
            if (enquiry_source == null) {
                enquiry_source = "";
            }
            if (status == null) {
                status = "";
            }

            ArrayList<Enquiry> list = model.getAllComplaints(enquiry_source, status);
            request.setAttribute("list", list);
            DBConnection.closeConncetion(model.getConnection());

            request.getRequestDispatcher("complaint_enquiry_list").forward(request, response);
        }

        if (task.equals("Submit")) {
            int enquiry_table_id;
            try {
                enquiry_table_id = Integer.parseInt(request.getParameter("enquiry_table_id"));
            } catch (Exception e) {
                enquiry_table_id = 0;
            }

            String enquiry_type = request.getParameter("enquiry_type");
            if (enquiry_type == null) {
                enquiry_type = "";
            }
            int count = 0;
            String district = request.getParameter("district").trim();
            if (district.equals("Others")) {
                count = 1;
            } else {
                count = model.checkDistrict(district);
            }
            if (count > 0) {
                Enquiry bean = new Enquiry();
                bean.setEnquiry_type(enquiry_type);
                bean.setEnquiry_table_id(enquiry_table_id);
                bean.setProduct_name(request.getParameter("product_name"));
                bean.setEnquiry_source(request.getParameter("enquiry_source"));
                bean.setMarketing_vertical_name(request.getParameter("marketing_vertical"));
                bean.setEnquiry_no(request.getParameter("enquiry_no"));
                bean.setSender_name(request.getParameter("sender_name"));
                bean.setSender_email(request.getParameter("sender_email"));
                bean.setSender_alternate_email(request.getParameter("sender_alternate_email"));
                bean.setSender_mob(request.getParameter("sender_mob"));
                bean.setSender_alternate_mob(request.getParameter("sender_alternate_mob"));
                bean.setSender_company_name(request.getParameter("sender_company_name"));
                bean.setEnquiry_address(request.getParameter("sender_address"));
                bean.setEnquiry_city(request.getParameter("sender_city"));
                bean.setEnquiry_state(request.getParameter("sender_state"));
                bean.setCountry(request.getParameter("sender_country"));
                bean.setEnquiry_message(request.getParameter("enquiry_message").trim());
                bean.setDescription(request.getParameter("district").trim());
                int rowsAffected = 0;
                if (enquiry_table_id == 0) {
                    rowsAffected = model.insertEnquiries(bean, enquiry_type, loggedUser, logged_key_person_id);
                    if (rowsAffected > 0) {
                        request.setAttribute("message", "Query Submitted..");
                        request.setAttribute("msgBgColor", "green");
                        request.getRequestDispatcher("CRMDashboardController").forward(request, response);
                    }
                }
            } else {
                request.setAttribute("message", "Please Select district from list.");
                request.setAttribute("msgBgColor", "red");
                request.setAttribute("user_role", loggedUser);
                request.getRequestDispatcher("sales_enquiry_form").forward(request, response);
            }
        }

        request.setAttribute("message", model.getMessage());
        request.setAttribute("msgBgColor", model.getMessageBGColor());
        request.setAttribute("user_role", loggedUser);
        DBConnection.closeConncetion(model.getConnection());

        request.getRequestDispatcher("sales_enquiry_form").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        doGet(request, response);
    }
}
