package com.dashboard.controller;

import com.location.model.CityModel;
import com.location.bean.CityBean;
import com.DBConnection.DBConnection;
import com.dashboard.bean.DealersOrder;
import com.dashboard.bean.Enquiry;
import com.dashboard.bean.Profile;
import com.dashboard.model.DealersOrderModel;
import com.dashboard.model.ProfileModel;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;

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
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class DealersOrderController extends HttpServlet {

    int quantity = 1;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        int lowerLimit, noOfRowsTraversed, noOfRowsToDisplay = 15, noOfRowsInTable;

        System.out.println("Starting application");
        response.setContentType("text/html");
        ServletContext ctx = getServletContext();
        DealersOrderModel model = new DealersOrderModel();
        ProfileModel profileModel = new ProfileModel();

        String logged_user_name = "";
        String logged_designation = "";
        String logged_org_name = "";
        String logged_org_office = "";
        int logged_org_office_id = 0;
        int logged_org_name_id = 0;
        int logged_key_person_id = 0;
        int counting = 100000;
        String loggedUser = "";
        String search_item = "";
        String autogenerate_order_no = "";

        search_item = request.getParameter("search_item");
        if (search_item == null) {
            search_item = "";
        }
        String search_model = request.getParameter("search_model");
        if (search_model == null) {
            search_model = "";
        }

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
            //  office_admin = session.getAttribute("office_admin").toString();
        }

        try {
            model.setConnection(DBConnection.getConnectionForUtf(ctx));
            profileModel.setConnection(DBConnection.getConnectionForUtf(ctx));
        } catch (Exception e) {
            System.out.println("error in DealersOrderController setConnection() calling try block" + e);
        }

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
                if (JQstring.equals("getItemName")) {
                    list = model.getItemName(q, logged_designation);
                }
                if (JQstring.equals("getModelName")) {
                    String item_name = request.getParameter("item_name");
                    list = model.getModelName(q, logged_designation, item_name);
                }
                if (JQstring.equals("getPaymentMode")) {
                    list = model.getPaymentMode(q);
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
            System.out.println("\n Error --ItemNameController get JQuery Parameters Part-" + e);
        }

        request.setCharacterEncoding("UTF-8");
        response.setHeader("Content-Type", "text/plain; charset=UTF-8");
        System.out.println(model.getConnection());
        String task = request.getParameter("task");

        String order_no = request.getParameter("order_no");
        String pending_approval = request.getParameter("pending_approval");
        if (order_no == null) {
            order_no = "";
        }
        if (pending_approval == null) {
            pending_approval = "";
        }
        if (pending_approval.equals("approval_pending")) {
            request.setAttribute("order_no", order_no);
            request.getRequestDispatcher("pendingApproval").forward(request, response);
        }

        try {
            //----- This is only for Vendor key Person JQuery
            String JQstring = request.getParameter("action1");
            String q = request.getParameter("str");   // field own input           

        } catch (Exception e) {
            System.out.println("\n Error --DealersOrderController get JQuery Parameters Part-" + e);
        }
//        String model_id = request.getParameter("model_id");
//        String model_name = request.getParameter("model_name");
//        String basic_price = request.getParameter("basic_price");
//        if (model_id == null) {
//            model_id = "";
//        }
//        if (model_name == null) {
//            model_name = "";
//        }
//        if (basic_price == null) {
//            basic_price = "";
//        }
        if (task == null) {
            task = "";
        }

        String update_enquiry = request.getParameter("update_enquiry");

        if (update_enquiry == null) {
            update_enquiry = "";
        }
        String update_complaint = request.getParameter("update_complaint");

        if (update_complaint == null) {
            update_complaint = "";
        }
        if (task.equals("viewAll") || !search_model.equals("")) {
            String item_name = request.getParameter("item_name");
            ArrayList<DealersOrder> list = model.getAllModel(logged_org_office_id, item_name, search_model);

            ArrayList<DealersOrder> cart_list = model.viewCart(logged_key_person_id);
            request.setAttribute("cart_count", cart_list.size());

            request.setAttribute("list", list);
            request.setAttribute("item_name", item_name);
            request.setAttribute("search_model", search_model);
            request.setAttribute("count", list.size());
            DBConnection.closeConncetion(model.getConnection());

            request.getRequestDispatcher("CRM_category").forward(request, response);
        }
        if (task.equals("viewDetail")) {
            String model_id = request.getParameter("model_id");
            ArrayList<DealersOrder> list = model.getAllDetails(model_id, logged_org_office_id);
            ArrayList<DealersOrder> list2 = new ArrayList<>();
            ArrayList<DealersOrder> list3 = new ArrayList<>();
            list2 = model.getAllImages(list);
            list3 = model.getAllSimilarProducts(model_id, logged_org_office_id);

            ArrayList<DealersOrder> cart_list = model.viewCart(logged_key_person_id);
            request.setAttribute("cart_count", cart_list.size());

            request.setAttribute("list", list);
            request.setAttribute("list2", list2);
            request.setAttribute("list3", list3);
            request.setAttribute("count", list.size());
            request.setAttribute("count2", list2.size());
            request.setAttribute("count3", list3.size());
            DBConnection.closeConncetion(model.getConnection());

            request.getRequestDispatcher("product_details").forward(request, response);
        }

        if (update_enquiry.equals("Update")) {
            String status = request.getParameter("status");
            String enquiry_table_id = request.getParameter("enquiry_table_id");
//            String status2 = "";
            if (status == null) {
                status = "";
            }
            if (status.equals("UnSold")) {
                status = request.getParameter("status2");
            }

            String date_time = request.getParameter("date_time");
            String remark = request.getParameter("remark");

            try {
                model.updateEnquiryStatus(status, date_time, remark, enquiry_table_id);
            } catch (SQLException ex) {
                Logger.getLogger(ApproveOrdersController.class.getName()).log(Level.SEVERE, null, ex);
            }
            String enquiry_source = request.getParameter("enquiry_source");

//            request.getRequestDispatcher("salesperson_sales_enquiry_details").forward(request, response);
            ArrayList<Enquiry> list = model.getAllEnquiriesDetails(enquiry_table_id);
            request.setAttribute("list", list);
            request.setAttribute("enquiry_table_id", enquiry_table_id);
            request.setAttribute("status", status);
            request.setAttribute("date_time", date_time);
            request.setAttribute("remark", remark);
            DBConnection.closeConncetion(model.getConnection());

            request.getRequestDispatcher("dealer_sales_enquiry_details").forward(request, response);

        }

        if (update_complaint.equals("Update")) {
            String status = request.getParameter("status");
            String enquiry_table_id = request.getParameter("enquiry_table_id");
//            String status2 = "";
            if (status == null) {
                status = "";
            }
            if (status.equals("UnSold")) {
                status = request.getParameter("status2");
            }

            String date_time = request.getParameter("date_time");
            String remark = request.getParameter("remark");

            try {
                model.updateComplaintEnquiryStatus(status, date_time, remark, enquiry_table_id);
            } catch (SQLException ex) {
                Logger.getLogger(ApproveOrdersController.class.getName()).log(Level.SEVERE, null, ex);
            }
            String enquiry_source = request.getParameter("enquiry_source");

//            request.getRequestDispatcher("salesperson_sales_enquiry_details").forward(request, response);
            ArrayList<Enquiry> list = model.getAllComplaintDetails(enquiry_table_id);
            request.setAttribute("list", list);
            request.setAttribute("enquiry_table_id", enquiry_table_id);
            request.setAttribute("status", status);
            request.setAttribute("date_time", date_time);
            request.setAttribute("remark", remark);
            DBConnection.closeConncetion(model.getConnection());

            request.getRequestDispatcher("dealer_complaint_enquiry_details").forward(request, response);

        }

        if (task.equals("AddToCart")) {
            String model_id = request.getParameter("model_id");
            String model_name = request.getParameter("model_name");
            String basic_price = request.getParameter("basic_price");
            String qty = request.getParameter("qty");
            if (qty == null) {
                qty = "";
            }

            if (qty.equals("")) {
                qty = "1";
            }
            DealersOrder bean = new DealersOrder();
            bean.setModel_id(Integer.parseInt(model_id));
            bean.setModel(model_name);
            bean.setBasic_price(basic_price);
            bean.setQuantity(Integer.parseInt(qty));

            try {
                int previous_quantity = model.getCurrentQuantity(model_id, logged_key_person_id);
                int rowsaffected = model.addToCart(bean, logged_key_person_id);
                int current_quantity = model.getCurrentQuantity(model_id, logged_key_person_id);

                request.setAttribute("message", model.getMessage());
                request.setAttribute("msgBgColor", model.getMessageBGColor());

                ArrayList<DealersOrder> lastAddedProduct = new ArrayList<>();
                ArrayList<DealersOrder> cart_list = model.viewCart(logged_key_person_id);
                if (previous_quantity == 0) {
                    lastAddedProduct = model.getLastAddedProduct(logged_key_person_id);

                }

                JSONObject json = null;
                PrintWriter out = response.getWriter();
                List<String> list = null;
                if (json != null) {
                    out.println(json);
                } else {
                    JSONObject gson = new JSONObject();
                    gson.put("list", cart_list.size());
                    String success_msg = "";
                    String error_msg = "";

                    if (rowsaffected > 0) {
                        success_msg = "Product Successfully Added to Cart...";
                    } else {
                        error_msg = "Something Went Wrong!...";
                    }
                    if (lastAddedProduct.size() > 0) {
                        gson.put("model", lastAddedProduct.get(0).getModel());
                        gson.put("item_name", lastAddedProduct.get(0).getItem_name());
                        gson.put("quantity", lastAddedProduct.get(0).getQuantity());
                        gson.put("basic_price", lastAddedProduct.get(0).getBasic_price());
                        gson.put("image_path", lastAddedProduct.get(0).getImage_path());
                        gson.put("image_name", lastAddedProduct.get(0).getImage_name());
                        gson.put("model_id", lastAddedProduct.get(0).getModel_id());
                        gson.put("cart_table_id", lastAddedProduct.get(0).getCart_table_id());
                    } else {
                        gson.put("model", "");
                        gson.put("item_name", "");
                        gson.put("quantity", "");
                        gson.put("basic_price", "");
                        gson.put("image_path", "");
                        gson.put("image_name", "");
                        gson.put("model_id", "");
                        gson.put("cart_table_id", "");
                    }

                    gson.put("success_msg", success_msg);
                    gson.put("error_msg", error_msg);
                    gson.put("current_quantity", current_quantity);

                    out.println(gson);
                }

                DBConnection.closeConncetion(model.getConnection());

                return;
//                request.getRequestDispatcher("dealers_order").forward(request, response);
            } catch (SQLException ex) {
                Logger.getLogger(DealersOrderController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        if (task.equals("removeFromcart")) {
            String model_id = request.getParameter("model_id");
            String model_name = request.getParameter("model_name");
            String basic_price = request.getParameter("basic_price");
            String qty = request.getParameter("qty");
            if (qty == null) {
                qty = "";
            }

            if (qty.equals("")) {
                qty = "1";
            }
            DealersOrder bean = new DealersOrder();
            bean.setModel_id(Integer.parseInt(model_id));
            bean.setModel(model_name);
            bean.setBasic_price(basic_price);
            bean.setQuantity(Integer.parseInt(qty));

            try {
                model.removeFromcart(bean, logged_key_person_id);

                int current_quantity = model.getCurrentQuantity(model_id, logged_key_person_id);
                request.setAttribute("message", model.getMessage());
                request.setAttribute("msgBgColor", model.getMessageBGColor());

                ArrayList<DealersOrder> cart_list = model.viewCart(logged_key_person_id);
                JSONObject json = null;
                PrintWriter out = response.getWriter();
                List<String> list = null;
                if (json != null) {
                    out.println(json);
                } else {
                    JSONObject gson = new JSONObject();
                    gson.put("list", cart_list.size());
                    String message = "";
                    if (cart_list.size() > 0) {
                        message = "Product Successfully removed from Cart...";
                    } else {
                        message = "Something Went Wrong!...";
                    }
                    gson.put("msg", message);
                    gson.put("current_quantity", current_quantity);
                    out.println(gson);
                }

                DBConnection.closeConncetion(model.getConnection());

                return;
//                request.getRequestDispatcher("dealers_order").forward(request, response);
            } catch (SQLException ex) {
                Logger.getLogger(DealersOrderController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        if (task.equals("removeAllFromcart")) {
            String model_id = request.getParameter("model_id");
            String model_name = request.getParameter("model_name");
            String basic_price = request.getParameter("basic_price");
            String qty = request.getParameter("qty");
            if (qty == null) {
                qty = "";
            }

            if (qty.equals("")) {
                qty = "1";
            }
            DealersOrder bean = new DealersOrder();
            bean.setModel_id(Integer.parseInt(model_id));
            bean.setModel(model_name);
            bean.setBasic_price(basic_price);
            bean.setQuantity(Integer.parseInt(qty));

            try {
                model.removeAllFromcart(bean, logged_key_person_id);
                request.setAttribute("message", model.getMessage());
                request.setAttribute("msgBgColor", model.getMessageBGColor());

                ArrayList<DealersOrder> cart_list = model.viewCart(logged_key_person_id);
                JSONObject json = null;
                PrintWriter out = response.getWriter();
                List<String> list = null;
                if (json != null) {
                    out.println(json);
                } else {
                    JSONObject gson = new JSONObject();
                    gson.put("list", cart_list.size());
                    String message = "";
                    if (cart_list.size() > 0) {
                        message = "Product Successfully removed from Cart...";
                    } else if (cart_list.size() == 0) {
                        message = "Product Successfully removed from Cart...";
                    } else {
                        message = "Something Went Wrong!...";
                    }
                    gson.put("msg", message);
                    out.println(gson);
                }

                DBConnection.closeConncetion(model.getConnection());

                return;
//                request.getRequestDispatcher("dealers_order").forward(request, response);
            } catch (SQLException ex) {
                Logger.getLogger(DealersOrderController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        if (task.equals("viewCart")) {

            ArrayList<DealersOrder> list = model.viewCart(logged_key_person_id);
            request.setAttribute("list", list);
            request.setAttribute("count", list.size());

            ArrayList<DealersOrder> list1 = model.getAllItems(logged_org_office_id, search_item);
            ArrayList<DealersOrder> list2 = new ArrayList<>();
            list2 = model.getAllModels(logged_org_office_id, list1);
            request.setAttribute("list2", list2);
            request.setAttribute("count2", list2.size());
            DBConnection.closeConncetion(model.getConnection());

            request.getRequestDispatcher("cart_view").forward(request, response);
        }

        if (task.equals("completeOrder")) {
            int rowsAffected = 0;
            String model_id[] = request.getParameterValues("model_id[]");
            String basic_price[] = request.getParameterValues("basic_price[]");
            String qty[] = request.getParameterValues("qty[]");
            String rate[] = request.getParameterValues("rate[]");
            String subtotal = request.getParameter("subtotal");
            String delivery_charge = request.getParameter("delivery_charge");
            String coupon_discount = request.getParameter("coupon_discount");
            String total_amount = request.getParameter("total_amount");
            String i = "0";
            counting = model.getCounting();
            autogenerate_order_no = "APL" + counting;
            int order_table_id = 0;
            int order_item_id = 0;
            for (int k = 0; k < model_id.length; k++) {
                String req_to = model.getRequestedToKeyPersonorder("", logged_user_name);
                DealersOrder bean = new DealersOrder();
                bean.setOrder_table_id(order_table_id);
                bean.setOrder_no(autogenerate_order_no);
                bean.setRequested_by(logged_user_name);
                bean.setRequested_to(req_to);
                bean.setOrder_item_id(order_item_id);
                bean.setModel_id(Integer.parseInt(model_id[k]));
                bean.setBasic_price(basic_price[k]);
                bean.setRequired_qty(qty[k]);
                if (order_table_id == 0) {
                    try {
                        rowsAffected = model.insertRecord(bean, logged_user_name, k, logged_key_person_id);
                    } catch (SQLException ex) {
                        Logger.getLogger(DealersOrderController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }

            JSONObject json = null;
            PrintWriter out = response.getWriter();
            List<String> list = null;
            if (json != null) {
                out.println(json);
            } else {
                JSONObject gson = new JSONObject();
                if (rowsAffected > 0) {
                    gson.put("msg", "thank_you");
                    gson.put("order_no", autogenerate_order_no);
                } else {
                    gson.put("msg", "error");
                    gson.put("order_no", "");

                }
                out.println(gson);
                DBConnection.closeConncetion(model.getConnection());

                return;
            }
        }

        if (task.equals("Complete Checkout")) {
            int rowsAffected = 0;
            String subtotal = request.getParameter("subtotal");
            String delivery_charge = request.getParameter("delivery_charge");
            String coupon_discount = request.getParameter("coupon_discount");
            String total_amount = request.getParameter("total_amount");
            String office_name = request.getParameter("office_name");
            String email = request.getParameter("email");
            String mobile = request.getParameter("mobile");
            String payment_mode = request.getParameter("payment_mode");
            String transaction_no = request.getParameter("transaction_no");
            String amount = request.getParameter("amount");
            String billing_add = request.getParameter("billing_add");
            String shipping_add = request.getParameter("shipping_add");
            String deliver_order_no = request.getParameter("order_no");

            if (shipping_add == null) {
                shipping_add = "";
            }
            if (shipping_add.equals("")) {
                shipping_add = billing_add;
            }
            int order_checkout_id = 0;

            String req_to = model.getRequestedToKeyPersonorder("", logged_user_name);
            DealersOrder bean = new DealersOrder();
            bean.setOrder_checkout_id(order_checkout_id);
            bean.setOrder_no(deliver_order_no);
            bean.setRequested_by(logged_user_name);
            bean.setRequested_to(req_to);
            bean.setOrg_office(office_name);
            bean.setEmail(email);
            bean.setMobile_no(mobile);
            bean.setPayment_type(payment_mode);
            bean.setTransaction_no(transaction_no);
            bean.setTotal_amount(subtotal);
            bean.setBilling_address(billing_add);
            bean.setShipping_address(shipping_add);
            bean.setDiscount_percent(coupon_discount);
            bean.setDelivery_charge(delivery_charge);
            bean.setDiscount_price(amount);

            if (order_checkout_id == 0) {
                try {
                    rowsAffected = model.orderCheckout(bean, logged_user_name, logged_key_person_id);
                } catch (SQLException ex) {
                    Logger.getLogger(DealersOrderController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            if (rowsAffected > 0) {
                request.setAttribute("order_no", deliver_order_no);
                request.getRequestDispatcher("thank_you").forward(request, response);
            } else {
                request.getRequestDispatcher("error").forward(request, response);

            }

//            JSONObject json = null;
//            PrintWriter out = response.getWriter();
//            List<String> list = null;
//            if (json != null) {
//                out.println(json);
//            } else {
//                JSONObject gson = new JSONObject();
//                if (rowsAffected > 0) {
//                    gson.put("msg", "thank_you");
//                    gson.put("order_no", deliver_order_no);
//                } else {
//                    gson.put("msg", "error");
//                    gson.put("order_no", "");
//
//                }
//                out.println(gson);
//                DBConnection.closeConncetion(model.getConnection());
//
//                return;
//            }
        }

        if (task.equals("sales_enquiry_list")) {
            ArrayList<Enquiry> list = model.getAllEnquiriesForDealer(logged_key_person_id);
            request.setAttribute("list", list);
            DBConnection.closeConncetion(model.getConnection());

            request.getRequestDispatcher("dealer_sales_enquiry_list").forward(request, response);
        }
        if (task.equals("complaint_enquiry_list")) {
            ArrayList<Enquiry> list = model.getAllComplaintForDealer(logged_key_person_id);
            request.setAttribute("list", list);
            DBConnection.closeConncetion(model.getConnection());

            request.getRequestDispatcher("dealer_complaint_enquiry_list").forward(request, response);
        }

        if (task.equals("viewEnquiryDetails")) {
            String enquiry_table_id = request.getParameter("enquiry_table_id");
            ArrayList<Enquiry> list = model.getAllEnquiriesDetails(enquiry_table_id);
            request.setAttribute("list", list);
            request.setAttribute("enquiry_table_id", enquiry_table_id);
            DBConnection.closeConncetion(model.getConnection());

            request.getRequestDispatcher("dealer_sales_enquiry_details").forward(request, response);
        }
        if (task.equals("viewComplaintDetails")) {
            String enquiry_table_id = request.getParameter("enquiry_table_id");
            ArrayList<Enquiry> list = model.getAllComplaintDetails(enquiry_table_id);
            request.setAttribute("list", list);
            request.setAttribute("enquiry_table_id", enquiry_table_id);
            DBConnection.closeConncetion(model.getConnection());

            request.getRequestDispatcher("dealer_complaint_enquiry_details").forward(request, response);
        }

        if (task.equals("checkout")) {
            String order_table_id = request.getParameter("order_table_id");
            String order_no_for_order = model.getOrderNo(order_table_id);

            List<Profile> dealer_list = profileModel.getAllDetails(logged_user_name, logged_org_office);
            String email = dealer_list.get(0).getEmail_id1().toString();
            String mobile1 = dealer_list.get(0).getMobile_no1().toString();
            String city = dealer_list.get(0).getCity_name().toString();
            String address_line1 = dealer_list.get(0).getAddress_line1().toString();
            String address_line2 = dealer_list.get(0).getAddress_line2().toString();
            String address_line3 = dealer_list.get(0).getAddress_line3().toString();

            ArrayList<DealersOrder> order_list = model.getAllOrderItems(order_table_id);
            float total_amount = 0;
            float total_discount_price = 0;
            float total_discount_percent = 0;
            float total_approved_price = 0;

            for (int i = 0; i < order_list.size(); i++) {
                total_amount = total_amount + Float.parseFloat(order_list.get(i).getBasic_price());
                total_discount_price = total_discount_price + Float.parseFloat(order_list.get(i).getDiscount_price());
                total_discount_percent = total_discount_percent + Float.parseFloat(order_list.get(i).getDiscount_percent());
                total_approved_price = total_approved_price + Float.parseFloat(order_list.get(i).getApproved_price());
            }

            ArrayList<DealersOrder> list = model.viewCart(logged_key_person_id);
            ArrayList<DealersOrder> list1 = model.getAllItems(logged_org_office_id, search_item);
            ArrayList<DealersOrder> list2 = new ArrayList<>();
            list2 = model.getAllModels(logged_org_office_id, list1);

            request.setAttribute("list", list);
            request.setAttribute("order_no", order_no_for_order);
            request.setAttribute("count", list.size());
            request.setAttribute("list2", list2);
            request.setAttribute("count2", list2.size());
            request.setAttribute("total_amount", total_amount);
            request.setAttribute("total_discount_price", total_discount_price);
            request.setAttribute("total_approved_price", total_approved_price);
            request.setAttribute("total_discount_percent", (String.format("%.2f", (((total_approved_price - total_discount_price) / total_approved_price) * 100))));

            request.setAttribute("logged_user_name", logged_user_name);
            request.setAttribute("logged_org_office", logged_org_office);
            request.setAttribute("email", email);
            request.setAttribute("mobile1", mobile1);
            request.setAttribute("city", city);
            request.setAttribute("address_line1", address_line1);
            request.setAttribute("address_line2", address_line2);
            request.setAttribute("address_line3", address_line3);
//            request.setAttribute("logged_email", logged_org_office);
            DBConnection.closeConncetion(model.getConnection());
            DBConnection.closeConncetion(profileModel.getConnection());

            request.getRequestDispatcher("checkout").forward(request, response);
        }

        if (task.equals("changeStatus")) {
            String message = "";
            String enquiry_status = request.getParameter("enquiry_status");
            String enquiry_table_id = request.getParameter("enquiry_table_id");
            try {
                message = model.changeEnquiryStatus(enquiry_status, enquiry_table_id);
            } catch (SQLException ex) {
                Logger.getLogger(DealersOrderController.class.getName()).log(Level.SEVERE, null, ex);
            }

            JSONObject json = null;
            PrintWriter out = response.getWriter();
            List<String> list = null;
            if (json != null) {
                out.println(json);
            } else {
                JSONObject gson = new JSONObject();

                if (message.equals("Enquiry Passed")) {
                    gson.put("msg", message);
                }
                if (message.equals("Enquiry Failed")) {
                    gson.put("msg", message);
                }
                out.println(gson);
                DBConnection.closeConncetion(model.getConnection());

                return;
            }
        }

        if (task.equals("changeComplaintStatus")) {
            String message = "";
            String enquiry_status = request.getParameter("enquiry_status");
            String enquiry_table_id = request.getParameter("enquiry_table_id");
            try {
                message = model.changeComplaintEnquiryStatus(enquiry_status, enquiry_table_id);
            } catch (SQLException ex) {
                Logger.getLogger(DealersOrderController.class.getName()).log(Level.SEVERE, null, ex);
            }

            JSONObject json = null;
            PrintWriter out = response.getWriter();
            List<String> list = null;
            if (json != null) {
                out.println(json);
            } else {
                JSONObject gson = new JSONObject();

                if (message.equals("Enquiry Passed")) {
                    gson.put("msg", message);
                }
                if (message.equals("Enquiry Failed")) {
                    gson.put("msg", message);
                }
                out.println(gson);
                DBConnection.closeConncetion(model.getConnection());

                return;
            }
        }

        String getImage = request.getParameter("getImage");
        try {
            if (getImage == null) {
                getImage = "";
            } else {
                String destinationPath = getImage;
                File f = new File(destinationPath);
                FileInputStream fis = null;
                if (!f.exists()) {
                    destinationPath = "C:\\ssadvt_repository\\health_department\\general\\no_image.png";
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
                return;
            }

        } catch (Exception e) {
            System.out.println("errorr -" + e);
            return;
        }

        ArrayList<DealersOrder> list1 = model.getAllItems(logged_org_office_id, search_item);
        ArrayList<DealersOrder> list2 = new ArrayList<>();
        list2 = model.getAllModels(logged_org_office_id, list1);

        ArrayList<DealersOrder> cart_list = model.viewCart(logged_key_person_id);
        request.setAttribute("cart_count", cart_list.size());

        request.setAttribute("message", model.getMessage());
        request.setAttribute("msgBgColor", model.getMessageBGColor());
        request.setAttribute("search_item", search_item);
        request.setAttribute("list1", list1);
        request.setAttribute("list2", list2);
        request.setAttribute("count", list2.size());

        DBConnection.closeConncetion(model.getConnection());
        DBConnection.closeConncetion(profileModel.getConnection());

        request.getRequestDispatcher("dealers_order").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        doGet(request, response);
    }
}
