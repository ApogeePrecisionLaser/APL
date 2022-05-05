
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.general.controller;

import com.DBConnection.DBConnection;
import com.dashboard.bean.DealersOrder;
import com.dashboard.bean.Enquiry;
import com.dashboard.bean.EventBean;
import com.dashboard.bean.Help;
import com.dashboard.bean.Profile;
import com.dashboard.model.DealerItemMapModel;
import com.dashboard.model.DealersOrderModel;
import com.dashboard.model.EnquiryModel;
import com.dashboard.model.EventModel;
import com.dashboard.model.HelpModel;
import com.dashboard.model.ProfileModel;
import com.general.model.LoginModel;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Komal
 */
public class LoginController extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        response.setContentType("text/html;charset=UTF-8");
        ServletContext ctx = getServletContext();
        HttpSession session = request.getSession();

        if (session == null) {
//            session.invalidate();
            request.getRequestDispatcher("/").forward(request, response);
        } else {
            LoginModel model = new LoginModel();
            DealersOrderModel dealersOrderModel = new DealersOrderModel();
            ProfileModel profileModel = new ProfileModel();
            EnquiryModel enquiryModel = new EnquiryModel();
            HelpModel helpModel = new HelpModel();
            EventModel eventModel = new EventModel();

            model.setDriverClass(ctx.getInitParameter("driverClass"));
            model.setConnectionString(ctx.getInitParameter("connectionString"));

            model.setConnection(DBConnection.getConnectionForUtf(ctx));
            profileModel.setConnection(DBConnection.getConnectionForUtf(ctx));
            dealersOrderModel.setConnection(DBConnection.getConnectionForUtf(ctx));
            enquiryModel.setConnection(DBConnection.getConnectionForUtf(ctx));
            helpModel.setConnection(DBConnection.getConnectionForUtf(ctx));
            eventModel.setConnection(DBConnection.getConnectionForUtf(ctx));
            String task = request.getParameter("task");
            int count = 0;
            if (task == null || task.isEmpty()) {
                task = "";
            }
            String user_name = "";
            int logged_key_person_id = 0;
            int user_id = 0;
            int logged_org_name_id = 0;
            int logged_org_office_id = 0;
            String logged_org_name = "";
            String logged_org_office = "";
            String mobile = "";
            String office_admin = "";
            String email = "";
            String password = "";
            int token_count = 0;
            String user_token = "";
            String android_url = "";
            try {
                System.out.println("conn -" + model);

                if (task.equals("getToken")) {
                    user_token = request.getParameter("user_token");
                    email = request.getParameter("email");
                    password = request.getParameter("password");
                    System.err.println("user_token------" + user_token);
                    token_count = model.getTokenCount(email, password, user_token);
                    if (token_count == 0) {
                        int rowsaffected = model.saveToken(user_token, email, password);
                    }
                    request.setAttribute("email", email);
                    request.setAttribute("password", password);
                    if (session.getAttribute("log_email") == null) {
                        task = "login";
                    }
                }

                if (task.equals("openNotificationUrl")) {
                    email = request.getParameter("email");
                    password = request.getParameter("password");
                    android_url = request.getParameter("android_url");
                    String enquiry_table_id = request.getParameter("enquiry_table_id");
                    String android_url_arr[] = android_url.split("=");
                    String enquiry_type = android_url_arr[1];
                    if (session.getAttribute("log_email") != null) {
                        if ((session.getAttribute("user_role").toString()).equals("Dealer")) {
                            if ((email.equals(session.getAttribute("log_email").toString())) && (password.equals(session.getAttribute("password").toString()))) {
                                request.getRequestDispatcher("DealersOrderController?task=" + enquiry_type + "&enquiry_table_id=" + enquiry_table_id).forward(request, response);
                            }
                        } else {
                            if ((email.equals(session.getAttribute("log_email").toString())) && (password.equals(session.getAttribute("password").toString()))) {
                                request.getRequestDispatcher("ApproveOrdersController?task=" + enquiry_type + "&enquiry_table_id=" + enquiry_table_id).forward(request, response);
                            }
                        }
                    } else {
                        task = "login";
                    }
                }

                if (task.equals("login")) {
                    email = request.getParameter("email");

                    user_name = model.getUserName(email);
                    password = request.getParameter("password");
                    if (password == null) {
                        password = "";
                    }
                    user_token = request.getParameter("user_token");
                    if (user_token == null) {
                        user_token = "";
                    }
                    if (email.equals("") && password.equals("")) {
                        request.setAttribute("user_token", user_token);
                        request.getRequestDispatcher("/").forward(request, response);
                    }
                    count = model.checkLogin(user_name, password);

                    model.setUserFullDetail(user_name, password);
                    session.setAttribute("log_user", user_name);
                    session.setAttribute("log_email", email);
                    session.setAttribute("driverClass", ctx.getInitParameter("driverClass"));
                    session.setAttribute("connectionString", ctx.getInitParameter("connectionString"));
                    session.setAttribute("myDbUserName", model.getMyDbUserName());
                    session.setAttribute("myDbUserPass", model.getMyDbUserPass());
                    session.setAttribute("user_role", model.getMyRoleName());

                    String designation = "";
                    session.setAttribute("user_name", user_name);
                    session.setAttribute("password", password);

                    session.setAttribute("mode", "data");

                    designation = model.getDesignation(user_name, password);
                    if (designation.equals("")) {
                        designation = "normal_user";
                    }

                    logged_key_person_id = model.getKeyPersonId(user_name, password);
                    user_id = model.getUserId(user_name, password);
                    logged_org_name_id = model.getOrgNameId(user_name, password);
                    logged_org_office_id = model.getOrgOfficeId(user_name, password);
                    logged_org_name = model.getOrgName(user_name, password);
                    logged_org_office = model.getOrgOffice(user_name, password);
                    mobile = model.getMobile(user_name, password);
                    office_admin = model.getOfficeAdmin(user_name, password, logged_org_office_id, designation);

                    if (count > 0) {
                        session.setAttribute("logged_user_name", user_name);
                        session.setAttribute("logged_designation", designation);
                        session.setAttribute("logged_org_name_id", logged_org_name_id);
                        session.setAttribute("logged_org_office_id", logged_org_office_id);
                        session.setAttribute("logged_org_name", logged_org_name);
                        session.setAttribute("logged_org_office", logged_org_office);
                        session.setAttribute("logged_key_person_id", logged_key_person_id);
                        session.setAttribute("office_admin", office_admin);
                        session.setAttribute("log_mobile", mobile);
                        session.setAttribute("user_id", user_id);

                        if (!user_token.equals("")) {
                            token_count = model.getTokenCount(email, password, user_token);
                            if (token_count == 0) {
                                int rowsaffected = model.saveToken(user_token, email, password);
                            }
                        }

                        // session.setAttribute("office_embedded_dev", office_embedded_dev);
                        if (user_token.equals("") && android_url.equals("")) {
                            int user_count = model.getCount(logged_key_person_id);

                            if (user_count == 1) {
                                int updaterows = model.updateUser(logged_key_person_id, user_name, password, user_id);
                                request.setAttribute("mobile", mobile);
                                request.getRequestDispatcher("recovery_password").forward(request, response);
                            } else {
                                String result = model.sendOTP(mobile, session.getAttribute("user_id").toString());

                                request.setAttribute("message", "OTP has been sent successfully on " + mobile);
                                request.setAttribute("msgBgColor", "green");
                                request.setAttribute("mobile", mobile);
                                request.setAttribute("type", "login");
                                request.setAttribute("otp", result);
                                request.setAttribute("email", email);
                                request.setAttribute("password", password);
                                request.setAttribute("user_token", user_token);

                                request.getRequestDispatcher("verify_mobile").forward(request, response);

//                            if (result.equals("Success")) {
//                                request.setAttribute("message", "OTP has been sent successfully on " + mobile);
//                                request.setAttribute("msgBgColor", "green");
//                                request.setAttribute("mobile", mobile);
//                                request.setAttribute("type", "login");
//                                request.getRequestDispatcher("verify_mobile").forward(request, response);
//                            } else {
//                                request.setAttribute("mobile", mobile);
//                                request.setAttribute("message", "OTP has not been sent.Please Resend!..");
//                                request.setAttribute("msgBgColor", "red");
//                                request.setAttribute("type", "login");
//                                request.getRequestDispatcher("verify_mobile").forward(request, response);
//                            }
                            }
                        }
                        if (!android_url.equals("")) {
                            String enquiry_table_id = request.getParameter("enquiry_table_id");
                            String android_url_arr[] = android_url.split("=");
                            String enquiry_type = android_url_arr[1];
                            if ((session.getAttribute("user_role").toString()).equals("Dealer")) {
                                if ((email.equals(session.getAttribute("log_email").toString())) && (password.equals(session.getAttribute("password").toString()))) {
                                    request.getRequestDispatcher("DealersOrderController?task=" + enquiry_type + "&enquiry_table_id=" + enquiry_table_id).forward(request, response);
                                }
                            } else {
                                if ((email.equals(session.getAttribute("log_email").toString())) && (password.equals(session.getAttribute("password").toString()))) {
                                    request.getRequestDispatcher("ApproveOrdersController?task=" + enquiry_type + "&enquiry_table_id=" + enquiry_table_id).forward(request, response);
                                }
                            }

                        }

                    } else {
                        request.setAttribute("message", "Credentials mis-match!");
                        request.setAttribute("msgBgColor", "red");
                        request.getRequestDispatcher("/").forward(request, response);
                    }
                }

                if (task.equals("VerifyOTP")) {
                    String otp = request.getParameter("otp");
                    String type = request.getParameter("verify_type");
                    int i = 1;
                    if (otp == null || otp.isEmpty()) {
                        System.out.println("enter the mobile number");
                    } else {
                        String mob = request.getParameter("mobile");
                        String users_id = model.getUserId(mob);
                        String verify_OTP = model.verifyOTP(otp, users_id);
                        System.out.println(verify_OTP);

                        request.setAttribute("mobile_no", mob);

                        if (type.equals("login")) {
                            if (verify_OTP.equals("success")) {
//                                if (session.getAttribute("user_role").toString().equals("Super Admin")) {
//                                    request.getRequestDispatcher("/view/dashboard.jsp").forward(request, response);
//                                } else if (session.getAttribute("user_role").toString().equals("Admin")
//                                        || session.getAttribute("user_role").toString().equals("Sales")
//                                        || session.getAttribute("user_role").toString().equals("Dealer")) {
//                                    request.getRequestDispatcher("CRMDashboardController").forward(request, response);
//                                } else {
//                                    request.getRequestDispatcher("/view/dashboard.jsp").forward(request, response);
//                                }

                                if (session.getAttribute("user_role").equals("Admin")) {
                                    ArrayList<DealersOrder> total_orders_list = dealersOrderModel.getAllHistoryOrders(session.getAttribute("logged_user_name").toString(), session.getAttribute("user_role").toString());
                                    List<Profile> dealers_list = profileModel.getAllDealers();
                                    ArrayList<Enquiry> total_enquiries_list = enquiryModel.getAllPendingEnquiries("", "Total");
                                    ArrayList<Enquiry> total_complaint_list = enquiryModel.getAllPendingComplaints("", "Total");

                                    ArrayList<Enquiry> pending_enquiries_list = enquiryModel.getAllPendingEnquiries("", "Pending");
                                    ArrayList<Enquiry> pending_complaint_list = enquiryModel.getAllPendingComplaints("", "Pending");

                                    ArrayList<DealersOrder> dashboard_pending_orders = dealersOrderModel.getAllDashboardOrders(session.getAttribute("logged_user_name").toString(), session.getAttribute("user_role").toString());
                                    List<Profile> latest_dealers = profileModel.getAllLatestDealers();
                                    List<Help> supportMessages = helpModel.getAllSupportMessages();

                                    ArrayList<EventBean> pending_news = eventModel.getAllEvents(Integer.parseInt(session.getAttribute("logged_key_person_id").toString()), session.getAttribute("user_role").toString());

                                    String last_time_of_enquiry = "";
                                    String last_time_of_complaint = "";
                                    ArrayList<DealersOrder> allModels = dealersOrderModel.getAllLatestItems(String.valueOf(session.getAttribute("logged_org_office_id").toString()));
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
                                    session.setAttribute("supportMessages", supportMessages.size());
                                    request.setAttribute("latest_dealers", latest_dealers);
                                    session.setAttribute("sales_enquiries", total_enquiries_list.size());
                                    session.setAttribute("pending_sales_enquiries", pending_enquiries_list.size());
                                    session.setAttribute("total_dealers", dealers_list.size());
                                    session.setAttribute("complaint_enquiries", total_complaint_list.size());
                                    session.setAttribute("pending_complaint_enquiries", pending_complaint_list.size());
                                    session.setAttribute("total_orders", total_orders_list.size());
                                    session.setAttribute("pending_news", pending_news.size());
                                    session.setAttribute("last_time_of_complaint", last_time_of_complaint);
                                    session.setAttribute("last_time_of_enquiry", last_time_of_enquiry);
                                    session.setAttribute("total_notification", ((pending_enquiries_list.size()) + (pending_complaint_list.size())));

                                    DBConnection.closeConncetion(model.getConnection());
                                    DBConnection.closeConncetion(profileModel.getConnection());
                                    DBConnection.closeConncetion(enquiryModel.getConnection());
                                    DBConnection.closeConncetion(helpModel.getConnection());
                                    DBConnection.closeConncetion(eventModel.getConnection());
                                    request.getRequestDispatcher("admin_dashboard").forward(request, response);
                                }
                                if (session.getAttribute("user_role").equals("Dealer")) {
                                    ArrayList<DealersOrder> pending_orders_list = dealersOrderModel.getAllOrders(session.getAttribute("logged_user_name").toString(), session.getAttribute("user_role").toString());
                                    ArrayList<Enquiry> sales_enquiry_list = dealersOrderModel.getAllEnquiriesForDealer(Integer.parseInt(session.getAttribute("logged_key_person_id").toString()));
                                    ArrayList<Enquiry> complaint_enquiry_list = dealersOrderModel.getAllComplaintForDealer(Integer.parseInt(session.getAttribute("logged_key_person_id").toString()));
                                    ArrayList<DealersOrder> dashboard_pending_orders = dealersOrderModel.getAllDashboardOrders(session.getAttribute("logged_user_name").toString(), session.getAttribute("user_role").toString());

                                    ArrayList<Enquiry> pending_sales_enquiry_list = dealersOrderModel.getPendingEnquiriesForDealer(Integer.parseInt(session.getAttribute("logged_key_person_id").toString()));
                                    ArrayList<Enquiry> pending_complaint_enquiry_list = dealersOrderModel.getPendingComplaintForDealer(Integer.parseInt(session.getAttribute("logged_key_person_id").toString()));
                                    ArrayList<EventBean> pending_news = dealersOrderModel.getEvents(Integer.parseInt(session.getAttribute("logged_key_person_id").toString()), session.getAttribute("user_role").toString());

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
                                    session.setAttribute("last_time_of_enquiry", last_time_of_enquiry);
                                    session.setAttribute("last_time_of_complaint", last_time_of_complaint);
                                    session.setAttribute("last_time_of_news", last_time_of_news);
                                    request.setAttribute("dashboard_pending_orders", dashboard_pending_orders);
                                    request.setAttribute("dashboard_pending_orders_count", dashboard_pending_orders.size());
                                    request.setAttribute("pending_news_list", pending_news);
                                    session.setAttribute("sales_enquiries", sales_enquiry_list.size());
                                    session.setAttribute("complaint_enquiries", complaint_enquiry_list.size());
                                    session.setAttribute("pending_sales_enquiries", pending_sales_enquiry_list.size());
                                    session.setAttribute("pending_complaint_enquiries", pending_complaint_enquiry_list.size());
                                    session.setAttribute("pending_news", pending_news.size());
                                    session.setAttribute("pending_orders", pending_orders_list.size());
                                    session.setAttribute("total_notification", ((pending_sales_enquiry_list.size()) + (pending_complaint_enquiry_list.size())
                                            + (pending_news.size())));

                                    DBConnection.closeConncetion(dealersOrderModel.getConnection());
                                    request.getRequestDispatcher("CRMDashboard").forward(request, response);
                                }
                                if (session.getAttribute("user_role").equals("Sales")) {
                                    ArrayList<DealersOrder> pending_orders_list = dealersOrderModel.getAllApprovedOrders(session.getAttribute("logged_user_name").toString(), session.getAttribute("user_role").toString(), "Pending");
                                    ArrayList<DealersOrder> approved_orders_list = dealersOrderModel.getAllApprovedOrders(session.getAttribute("logged_user_name").toString(), session.getAttribute("user_role").toString(), "Approved");
                                    ArrayList<DealersOrder> denied_orders_list = dealersOrderModel.getAllApprovedOrders(session.getAttribute("logged_user_name").toString(), session.getAttribute("user_role").toString(), "Denied");
                                    ArrayList<Enquiry> sales_enquiry_list = dealersOrderModel.getAllEnquiries(session.getAttribute("user_role").toString(), Integer.parseInt(session.getAttribute("logged_key_person_id").toString()), "", "");
                                    ArrayList<Enquiry> complaint_enquiry_list = dealersOrderModel.getAllComplaints(session.getAttribute("user_role").toString(), Integer.parseInt(session.getAttribute("logged_key_person_id").toString()), "", "");

                                    ArrayList<Enquiry> pending_sales_enquiry_list = dealersOrderModel.getAllPendingEnquiries(session.getAttribute("user_role").toString(), Integer.parseInt(session.getAttribute("logged_key_person_id").toString()), "", "Pending");
                                    ArrayList<Enquiry> pending_complaint_enquiry_list = dealersOrderModel.getAllPendingComplaints(session.getAttribute("user_role").toString(), Integer.parseInt(session.getAttribute("logged_key_person_id").toString()), "", "Pending");

                                    String last_time_of_enquiry = "";
                                    String last_time_of_complaint = "";

                                    for (int j = 0; j < 1; j++) {
                                        if (sales_enquiry_list.size() > 0) {
                                            last_time_of_enquiry = pending_sales_enquiry_list.get(j).getEnquiry_date_time().toString();
                                        }
                                        if (complaint_enquiry_list.size() > 0) {
                                            last_time_of_complaint = pending_complaint_enquiry_list.get(j).getEnquiry_date_time().toString();
                                        }
                                    }
                                    request.setAttribute("email", session.getAttribute("log_email").toString());
                                    request.setAttribute("password", session.getAttribute("password").toString());
                                    session.setAttribute("last_time_of_enquiry", last_time_of_enquiry);
                                    session.setAttribute("last_time_of_complaint", last_time_of_complaint);
                                    session.setAttribute("sales_enquiries", sales_enquiry_list.size());
                                    session.setAttribute("complaint_enquiries", complaint_enquiry_list.size());
                                    session.setAttribute("pending_sales_enquiries", pending_sales_enquiry_list.size());
                                    session.setAttribute("pending_complaint_enquiries", pending_complaint_enquiry_list.size());
                                    session.setAttribute("pending_orders", pending_orders_list.size());
                                    session.setAttribute("approved_orders", approved_orders_list.size());
                                    session.setAttribute("denied_orders", denied_orders_list.size());
                                    session.setAttribute("total_notification", ((pending_sales_enquiry_list.size()) + (pending_complaint_enquiry_list.size())));
                                    DBConnection.closeConncetion(dealersOrderModel.getConnection());
                                    request.getRequestDispatcher("salesperson_dashboard").forward(request, response);
                                } else {
                                    request.getRequestDispatcher("dashboard").forward(request, response);
                                }

                            } else if (verify_OTP.equals("OTP Expired. Please resend the OTP.")) {
                                request.setAttribute("message", verify_OTP);
                                request.setAttribute("msgBgColor", "red");
                                request.setAttribute("mobile", mob);
                                request.getRequestDispatcher("verify_mobile").forward(request, response);
                            } else {
                                request.setAttribute("message", "OTP has not been Verified Please Enter Correct OTP or resend");
                                request.setAttribute("msgBgColor", "red");
                                request.setAttribute("mobile", mob);
                                request.getRequestDispatcher("verify_mobile").forward(request, response);
                            }
                        }
                        if (type.equals("new password")) {
                            request.setAttribute("mobile", mob);
                            request.getRequestDispatcher("recovery_password").forward(request, response);
                        }
                    }
                }

                if (task.equals("Resend")) {
                    String mobile_no = request.getParameter("mobile");
                    int mobile_count = model.verifyMobile(mobile_no);
                    String users_id = model.getUserId(mobile_no);

                    if (mobile_count > 0) {
                        String result = model.sendOTP(mobile_no, users_id);
//                        if (result.equals("Success")) {
//                            request.setAttribute("message", "OTP has been sent successfully on " + mobile_no);
//                            request.setAttribute("msgBgColor", "green");
//                            request.setAttribute("mobile", mobile_no);
//                            request.setAttribute("type", "new password");
//                            request.getRequestDispatcher("verify_mobile").forward(request, response);
//                        } else {
//                            request.setAttribute("mobile", mobile_no);
//                            request.setAttribute("message", "");
//                            request.setAttribute("msgBgColor", "red");
//                            request.setAttribute("type", "new password");
//                            request.getRequestDispatcher("verify_mobile").forward(request, response);
//                        }

                        request.setAttribute("message", "OTP has been sent successfully on " + mobile_no);
                        request.setAttribute("msgBgColor", "green");
                        request.setAttribute("mobile", mobile_no);
                        request.setAttribute("type", "login");
                        request.setAttribute("otp", result);
                        request.getRequestDispatcher("verify_mobile").forward(request, response);

                    }
                }

                if (task.equals("Request New Password")) {
                    String mobile_no = request.getParameter("mobile");
                    int mobile_count = model.verifyMobile(mobile_no);
                    String users_id = model.getUserId(mobile_no);
                    if (mobile_count > 0) {
                        String result = model.sendOTP(mobile_no, users_id);
//                        if (result.equals("Success")) {
//                            request.setAttribute("message", "OTP has been sent successfully on " + mobile_no);
//                            request.setAttribute("msgBgColor", "green");
//                            request.setAttribute("mobile", mobile_no);
//                            request.setAttribute("type", "new password");
//                            request.getRequestDispatcher("verify_mobile").forward(request, response);
//                        } else {
//                            request.setAttribute("mobile", mobile_no);
//                            request.setAttribute("message", "");
//                            request.setAttribute("msgBgColor", "red");
//                            request.setAttribute("type", "new password");
//                            request.getRequestDispatcher("verify_mobile").forward(request, response);
//                        }

                        request.setAttribute("message", "OTP has been sent successfully on " + mobile_no);
                        request.setAttribute("msgBgColor", "green");
                        request.setAttribute("mobile", mobile_no);
                        request.setAttribute("type", "new password");
                        request.setAttribute("otp", result);
                        request.getRequestDispatcher("verify_mobile").forward(request, response);

                    } else {
                        request.setAttribute("message", "Mobile No. does Not Exists..");
                        request.setAttribute("msgBgColor", "red");
                        request.getRequestDispatcher("forgot_password").forward(request, response);

                    }
                }

                if (task.equals("Update Password")) {
                    String newpassword = request.getParameter("new_password");
                    String mobile_no = request.getParameter("mobile");
                    int update_rows = model.updatePassword(mobile_no, newpassword);
                    if (update_rows > 0) {
                        request.getRequestDispatcher("/").forward(request, response);
                    }
                }

                if (task.equals("logout")) {
                    session.invalidate();
                    request.getRequestDispatcher("/").forward(request, response);
                    session.removeAttribute("log_user");
                }

//                if (task.equals("")) {
//                    session.invalidate();
//                    request.getRequestDispatcher("/").forward(request, response);
//                    session.removeAttribute("log_user");
//                }
                if (task.equals("") && session.getAttribute("user_role") == null) {
                    request.getRequestDispatcher("login.jsp").forward(request, response);
                }
                if (session.getAttribute("user_role").toString().equals("Super Admin")) {
                    request.getRequestDispatcher("/view/dashboard.jsp").forward(request, response);
                } else if (session.getAttribute("user_role").toString().equals("Admin")
                        || session.getAttribute("user_role").toString().equals("Sales")
                        || session.getAttribute("user_role").toString().equals("Dealer")) {
                    request.getRequestDispatcher("CRMDashboardController").forward(request, response);
                } else {
                    request.getRequestDispatcher("/view/dashboard.jsp").forward(request, response);
                }

            } catch (Exception e) {
                System.out.println(e);
            }
        }
    }

// <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);

        } catch (SQLException ex) {
            Logger.getLogger(LoginController.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);

        } catch (SQLException ex) {
            Logger.getLogger(LoginController.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
