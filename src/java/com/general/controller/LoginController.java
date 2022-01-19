
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.general.controller;

import com.DBConnection.DBConnection;
import com.dashboard.bean.DealerItemMap;
import com.dashboard.bean.DealersOrder;
import com.dashboard.bean.Enquiry;
import com.dashboard.bean.Help;
import com.dashboard.bean.Profile;
import com.dashboard.model.DealerItemMapModel;
import com.dashboard.model.DealersOrderModel;
import com.dashboard.model.EnquiryModel;
import com.dashboard.model.HelpModel;
import com.dashboard.model.ProfileModel;
import com.general.model.LoginModel;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
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
        LoginModel model = new LoginModel();
        DealersOrderModel dealersOrderModel = new DealersOrderModel();
        ProfileModel profileModel = new ProfileModel();
        EnquiryModel enquiryModel = new EnquiryModel();
        HelpModel helpModel = new HelpModel();

        model.setDriverClass(ctx.getInitParameter("driverClass"));
        model.setConnectionString(ctx.getInitParameter("connectionString"));

        profileModel.setConnection(DBConnection.getConnectionForUtf(ctx));
        dealersOrderModel.setConnection(DBConnection.getConnectionForUtf(ctx));
        enquiryModel.setConnection(DBConnection.getConnectionForUtf(ctx));
        helpModel.setConnection(DBConnection.getConnectionForUtf(ctx));

        HttpSession session = request.getSession();
        String task = request.getParameter("task");
        if (task == null || task.isEmpty()) {
            task = "";
        }

//        try {
//            model.setConnection((Connection) DBConnection.getConnectionForUtf(ctx));
//        } catch (Exception e) {
//            System.out.print(e);
//        }     
        try {
            System.out.println("conn -" + model);

            if (task.equals("login")) {
                String mobile = request.getParameter("mobile");
                String email = request.getParameter("email");
                if (mobile == null) {
                    mobile = "";
                }
                if (email == null) {
                    email = "";
                }

                // String user_name = request.getParameter("user_name");
                String user_name = "";
                user_name = model.getUserName(mobile, email);
//                user_name="jpss";
                String password = request.getParameter("password");

                model.setUserFullDetail(user_name, password);

                int count = model.checkLogin(user_name, password);

                session.setAttribute("log_user", user_name);
                session.setAttribute("log_mobile", mobile);
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

                int logged_key_person_id = model.getKeyPersonId(user_name, password);
                int logged_org_name_id = model.getOrgNameId(user_name, password);
                int logged_org_office_id = model.getOrgOfficeId(user_name, password);
                String logged_org_name = model.getOrgName(user_name, password);
                String logged_org_office = model.getOrgOffice(user_name, password);
                String office_admin = model.getOfficeAdmin(user_name, password, logged_org_office_id, designation);
                //  String office_embedded_dev = model.getOfficeEmbeddedDeveloper(user_name, password, logged_org_office_id);

                if (count > 0) {
                    session.setAttribute("logged_user_name", user_name);
                    session.setAttribute("logged_designation", designation);
                    session.setAttribute("logged_org_name_id", logged_org_name_id);
                    session.setAttribute("logged_org_office_id", logged_org_office_id);
                    session.setAttribute("logged_org_name", logged_org_name);
                    session.setAttribute("logged_org_office", logged_org_office);
                    session.setAttribute("logged_key_person_id", logged_key_person_id);
                    session.setAttribute("office_admin", office_admin);
                    // session.setAttribute("office_embedded_dev", office_embedded_dev);

                    if (session.getAttribute("user_role").equals("Admin")) {
                        ArrayList<DealersOrder> total_orders_list = dealersOrderModel.getAllHistoryOrders(user_name, session.getAttribute("user_role").toString());
                        List<Profile> dealers_list = profileModel.getAllDealers();
                        ArrayList<Enquiry> total_enquiries_list = enquiryModel.getAllEnquiries("", "");
                        ArrayList<Enquiry> total_complaint_list = enquiryModel.getAllComplaints("","");
                        ArrayList<DealersOrder> dashboard_pending_orders = dealersOrderModel.getAllDashboardOrders(user_name, session.getAttribute("user_role").toString());
                        List<Profile> latest_dealers = profileModel.getAllLatestDealers();
                        List<Help> supportMessages = helpModel.getAllSupportMessages();

                        ArrayList<DealersOrder> allModels = dealersOrderModel.getAllLatestItems(String.valueOf(logged_org_office_id));

                        request.setAttribute("allProducts", allModels.size());
                        request.setAttribute("allModels", allModels);
                        request.setAttribute("dashboard_pending_orders", dashboard_pending_orders);
                        request.setAttribute("supportMessages", supportMessages.size());
                        request.setAttribute("latest_dealers", latest_dealers);
                        session.setAttribute("sales_enquiries", total_enquiries_list.size());
                        session.setAttribute("total_dealers", dealers_list.size());
                        session.setAttribute("complaint_enquiries", total_complaint_list.size());
                        session.setAttribute("total_orders", total_orders_list.size());
                        session.setAttribute("total_notification", ((total_enquiries_list.size()) + (total_complaint_list.size())));

                        DBConnection.closeConncetion(model.getConnection());
                        DBConnection.closeConncetion(profileModel.getConnection());
                        DBConnection.closeConncetion(enquiryModel.getConnection());
                        DBConnection.closeConncetion(helpModel.getConnection());
                        request.getRequestDispatcher("admin_dashboard").forward(request, response);
                    }
                    if (session.getAttribute("user_role").equals("Dealer")) {
                        ArrayList<DealersOrder> pending_orders_list = dealersOrderModel.getAllOrders(user_name, session.getAttribute("user_role").toString());
                        ArrayList<Enquiry> sales_enquiry_list = dealersOrderModel.getAllEnquiriesForDealer(logged_key_person_id);
                        ArrayList<Enquiry> complaint_enquiry_list = dealersOrderModel.getAllComplaintForDealer(logged_key_person_id);
                        ArrayList<DealersOrder> dashboard_pending_orders = dealersOrderModel.getAllDashboardOrders(user_name, session.getAttribute("user_role").toString());

                        request.setAttribute("dashboard_pending_orders", dashboard_pending_orders);
                        session.setAttribute("sales_enquiries", sales_enquiry_list.size());
                        session.setAttribute("complaint_enquiries", complaint_enquiry_list.size());
                        session.setAttribute("pending_orders", pending_orders_list.size());
                        session.setAttribute("total_notification", ((sales_enquiry_list.size()) + (complaint_enquiry_list.size())));

                        DBConnection.closeConncetion(model.getConnection());
                        DBConnection.closeConncetion(profileModel.getConnection());
                        DBConnection.closeConncetion(enquiryModel.getConnection());
                        request.getRequestDispatcher("CRMDashboard").forward(request, response);
                    }
                    if (session.getAttribute("user_role").equals("Sales")) {
                        ArrayList<DealersOrder> pending_orders_list = dealersOrderModel.getAllPendingOrders(user_name, session.getAttribute("user_role").toString(), "Pending");
                        ArrayList<DealersOrder> approved_orders_list = dealersOrderModel.getAllPendingOrders(user_name, session.getAttribute("user_role").toString(), "Approved");
                        ArrayList<DealersOrder> denied_orders_list = dealersOrderModel.getAllPendingOrders(user_name, session.getAttribute("user_role").toString(), "Denied");
                        ArrayList<Enquiry> sales_enquiry_list = dealersOrderModel.getAllEnquiries(session.getAttribute("user_role").toString(), logged_key_person_id);
                        ArrayList<Enquiry> complaint_enquiry_list = dealersOrderModel.getAllComplaints(session.getAttribute("user_role").toString(), logged_key_person_id);

                        session.setAttribute("sales_enquiries", sales_enquiry_list.size());
                        session.setAttribute("complaint_enquiries", complaint_enquiry_list.size());
                        session.setAttribute("pending_orders", pending_orders_list.size());
                        session.setAttribute("approved_orders", approved_orders_list.size());
                        session.setAttribute("total_notification", ((sales_enquiry_list.size()) + (complaint_enquiry_list.size())));

                        DBConnection.closeConncetion(model.getConnection());
                        DBConnection.closeConncetion(profileModel.getConnection());
                        DBConnection.closeConncetion(enquiryModel.getConnection());
                        request.getRequestDispatcher("salesperson_dashboard").forward(request, response);

                    } else {
                        request.getRequestDispatcher("dashboard").forward(request, response);
                    }
//                    request.getRequestDispatcher("dashboard").forward(request, response);
                } else {
                    request.setAttribute("message", "Credentials mis-match!");
                    request.setAttribute("msgBgColor", "red");
                    request.getRequestDispatcher("/").forward(request, response);
                }

//                if (!designation.equals("")) {
//
//                    if (designation.equals("Supervisor1")) {
//                        request.setAttribute("designation", designation);
//                    } else if (designation.equals("Clerk")) {
//                        request.setAttribute("designation", designation);
//                    } else if (designation.equals("RWA")) {
//                        request.setAttribute("designation", designation);
//                    } else if (designation.equals("पब्लिक")) {
//                        request.setAttribute("designation", designation);
//                    }
//                    //   request.getRequestDispatcher("index").forward(request, response);
//                } else {
//                    session.invalidate();
//                    request.setAttribute("message", "UserName And Password Not Correct");
//                    request.getRequestDispatcher("beforeLoginHomeView").forward(request, response);
//                }
            }
            if (task.equals("logout")) {
                session.invalidate();
                request.getRequestDispatcher("/").forward(request, response);
            }
            if (session.getAttribute("log_user").toString().equals("")) {
                request.getRequestDispatcher("login.jsp").forward(request, response);
            }
            if (!session.getAttribute("log_user").toString().equals("")) {
                request.getRequestDispatcher("CRMDashboardController").forward(request, response);
            }

        } catch (Exception e) {
            System.out.println(e);
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
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
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
