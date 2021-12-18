package com.dashboard.controller;

import com.location.model.CityModel;
import com.location.bean.CityBean;
import com.DBConnection.DBConnection;
import com.dashboard.bean.DealersOrder;
import com.dashboard.model.DealersOrderModel;
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
            System.out.println("\n Error --ItemNameController get JQuery Parameters Part-" + e);
        }

        request.setCharacterEncoding("UTF-8");
        response.setHeader("Content-Type", "text/plain; charset=UTF-8");
        System.out.println(model.getConnection());
        String task = request.getParameter("task");

        String order_no = request.getParameter("order_no");
        if (order_no == null) {
            order_no = "";
        }
        if (!order_no.equals("")) {
            request.setAttribute("order_no", order_no);
            request.getRequestDispatcher("thank_you").forward(request, response);
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
        if (task.equals("viewAll") || !search_model.equals("")) {
            String item_name = request.getParameter("item_name");
            ArrayList<DealersOrder> list = model.getAllModel(logged_designation, item_name, search_model);

            ArrayList<DealersOrder> cart_list = model.viewCart(logged_key_person_id);
            request.setAttribute("cart_count", cart_list.size());

            request.setAttribute("list", list);
            request.setAttribute("item_name", item_name);
            request.setAttribute("search_model", search_model);
            request.setAttribute("count", list.size());
            model.closeConnection();
            request.getRequestDispatcher("CRM_category").forward(request, response);
        }
        if (task.equals("viewDetail")) {
            String model_id = request.getParameter("model_id");
            ArrayList<DealersOrder> list = model.getAllDetails(model_id, logged_org_office_id);
            ArrayList<DealersOrder> list2 = new ArrayList<>();
            ArrayList<DealersOrder> list3 = new ArrayList<>();
            list2 = model.getAllImages(list);
            list3 = model.getAllSimilarProducts(model_id);

            ArrayList<DealersOrder> cart_list = model.viewCart(logged_key_person_id);
            request.setAttribute("cart_count", cart_list.size());

            request.setAttribute("list", list);
            request.setAttribute("list2", list2);
            request.setAttribute("list3", list3);
            request.setAttribute("count", list.size());
            request.setAttribute("count2", list2.size());
            request.setAttribute("count3", list3.size());
            model.closeConnection();
            request.getRequestDispatcher("product_details").forward(request, response);
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
                model.addToCart(bean, logged_key_person_id);
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
                    String message = "";
                    if (cart_list.size() > 0) {
                        message = "Product Successfully Added to Cart...";
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

                    gson.put("msg", message);
                    gson.put("current_quantity", current_quantity);

                    out.println(gson);
                }

                model.closeConnection();
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
                    }
                    gson.put("msg", message);
                    gson.put("current_quantity", current_quantity);
                    out.println(gson);
                }

                model.closeConnection();
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
                    }
                    gson.put("msg", message);
                    out.println(gson);
                }

                model.closeConnection();
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

            ArrayList<DealersOrder> list1 = model.getAllItems(logged_designation, search_item);
            ArrayList<DealersOrder> list2 = new ArrayList<>();
            list2 = model.getAllModels(logged_designation, list1);
            request.setAttribute("list2", list2);
            request.setAttribute("count2", list2.size());
            model.closeConnection();
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
            autogenerate_order_no = "Order_" + counting;
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
            model.closeConnection();

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
        ArrayList<DealersOrder> list1 = model.getAllItems(logged_designation, search_item);
        ArrayList<DealersOrder> list2 = new ArrayList<>();
        list2 = model.getAllModels(logged_designation, list1);

        ArrayList<DealersOrder> cart_list = model.viewCart(logged_key_person_id);
        request.setAttribute("cart_count", cart_list.size());

        request.setAttribute("message", model.getMessage());
        request.setAttribute("msgBgColor", model.getMessageBGColor());
        request.setAttribute("search_item", search_item);
        request.setAttribute("list1", list1);
        request.setAttribute("list2", list2);
        request.setAttribute("count", list2.size());

        model.closeConnection();
        request.getRequestDispatcher("dealers_order").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        doGet(request, response);
    }
}
