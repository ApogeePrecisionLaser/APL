package com.purchase.module;

import com.DBConnection.DBConnection;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;



/**
 *
 * @author komal
 */
public class PurchaseOrdersController extends HttpServlet {

    private File tmpDir;
    String org_office_name = "";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        Map<String, String> map = new HashMap<String, String>();

        System.out.println("Starting application");
        response.setContentType("text/html");
        ServletContext ctx = getServletContext();
        PurchaseOrdersModel model = new PurchaseOrdersModel();
        String logged_user_name = "";
        String logged_designation = "";
        String logged_org_name = "";
        String logged_org_office = "";
        int logged_org_office_id = 0;
        int logged_org_name_id = 0;
        int logged_key_person_id = 0;
        String loggedUser = "";
        int counting = 100000;
        String autogenerate_order_no = "";

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
            System.out.println("error in PurchaseOrdersController setConnection() calling try block" + e);
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

                if (JQstring.equals("getParameter")) {
                    String type = request.getParameter("type");
                    if (type.equals("vendor")) {
                        list = model.getVendorName(q);
                    }

                }
                if (JQstring.equals("getData")) {
                    String vendor_name = request.getParameter("vendor_name");
                    String org_office = request.getParameter("org_office");

                    JSONObject obj1 = new JSONObject();
                    JSONArray arrayObj = new JSONArray();

                    arrayObj = model.getData(loggedUser, logged_key_person_id, vendor_name, org_office);

                    obj1.put("list", arrayObj);
                    out.print(obj1);
                    return;
                }
                if (JQstring.equals("getVendor")) {
                    list = model.getVendorName(q);
                }
                if (JQstring.equals("getOrgOffice")) {
                    list = model.getOrgOffice(q);
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
            System.out.println("\n Error --PurchaseOrdersController get JQuery Parameters Part-" + e);
        }
        List items = null;
        Iterator itr = null;
        DiskFileItemFactory fileItemFactory = new DiskFileItemFactory(); //Set the size threshold, above which content will be stored on disk.
        fileItemFactory.setSizeThreshold(1 * 1024 * 1024); //1 MB Set the temporary directory to store the uploaded files of size above threshold.
        fileItemFactory.setRepository(tmpDir);
        ServletFileUpload uploadHandler = new ServletFileUpload(fileItemFactory);
        try {
            items = uploadHandler.parseRequest(request);
            itr = items.iterator();
            while (itr.hasNext()) {
                FileItem item = (FileItem) itr.next();
                if (item.isFormField()) {
                    System.out.println("File Name = " + item.getFieldName() + ", Value = " + item.getString() + "\n");//(getString())its for form field
                    map.put(item.getFieldName(), item.getString("UTF-8"));
                } else {
                    System.out.println("File Name = " + item.getFieldName() + ", Value = " + item.getName());//it is (getName()) for file related things
                    if (item.getName() == null || item.getName().isEmpty()) {
                        map.put(item.getFieldName(), "");
                    } else {
                        String image_name = item.getName();
                        image_name = image_name.substring(0, image_name.length());
                        // System.out.println(image_name);
                        map.put(item.getFieldName(), item.getName());
                    }
                }
            }
            itr = null;
            itr = items.iterator();
        } catch (Exception ex) {
            System.out.println("Error encountered while uploading file" + ex);
        }
        String task = request.getParameter("task");
        if (task == null) {
            task = "";
        }

//        if (task.equals("Submit")) {
//            String order_no = request.getParameter("order_no");
//            String vendor_name = request.getParameter("vendor_name");
//            int purchase_order_id = 0;
//            try {
//                purchase_order_id = Integer.parseInt(request.getParameter("purchase_order_id").trim());
//            } catch (Exception e) {
//                purchase_order_id = 0;
//            }
//            String item_name[] = request.getParameterValues("pr_name");
//            for (int i = 0; i < item_name.length; i++) {
//                String model_name = request.getParameter("pr_modelName" + i);
//                String qty = request.getParameter("pr_qty" + i);
////                String vendor = request.getParameter("pr_vendor" + i);
//                String inventory_id = request.getParameter("inventory_id" + i);
//
//                PurchaseOrdersBean bean = new PurchaseOrdersBean();
//                bean.setOrder_no(order_no);
//                bean.setPurchase_order_id(purchase_order_id);
//                bean.setItem_name(item_name[i]);
//                bean.setModel(model_name);
//                bean.setQty(qty);
//                bean.setVendor(vendor_name);
//                bean.setInventory_id(Integer.parseInt(inventory_id.trim()));
//
//                if (purchase_order_id == 0) {
//                    try {
//                        model.insertRecord(bean);
//                    } catch (SQLException ex) {
//                        Logger.getLogger(PurchaseOrdersController.class.getName()).log(Level.SEVERE, null, ex);
//                    }
//                }
//            }
//        }
        if (task.equals("Send")) {
            counting = model.getCounting();
            autogenerate_order_no = "PO" + counting;
            int purchase_order_id = 0;
            try {
                purchase_order_id = Integer.parseInt(request.getParameter("purchase_order_id").trim());
            } catch (Exception e) {
                purchase_order_id = 0;
            }

            String item_names_id[] = request.getParameterValues("item_names_id");
            String model_id[] = request.getParameterValues("model_id");
            String qty[] = request.getParameterValues("qty");
            String price[] = request.getParameterValues("price");
            String vendor_id[] = request.getParameterValues("vendor_id");
            for (int i = 0; i < item_names_id.length; i++) {
//                String inventory_id = request.getParameter("inventory_id" + i);

                PurchaseOrdersBean bean = new PurchaseOrdersBean();
                bean.setOrder_no(autogenerate_order_no);
                bean.setPurchase_order_id(purchase_order_id);
                bean.setItem_names_id(Integer.parseInt(item_names_id[i]));
                bean.setModel_id(Integer.parseInt(model_id[i]));
                bean.setQty(qty[i]);
                bean.setPrice(price[i]);
                bean.setVendor_id(Integer.parseInt(vendor_id[i]));
//                bean.setInventory_id(Integer.parseInt(inventory_id.trim()));

                if (purchase_order_id == 0) {
                    try {
//                        if (loggedUser.equals("Admin") || loggedUser.equals("Super Admin")) {
//                            logged_org_office_id = model.getOrgOfficeId(org_office_name);
//                        }
                        String org_office_id = request.getParameter("org_office_id");
                        if (org_office_id == null) {
                            org_office_id = "";
                        }
                        logged_org_office_id = Integer.parseInt(org_office_id);
                        model.insertRecord(bean, logged_org_office_id, loggedUser, logged_key_person_id);
                    } catch (SQLException ex) {
                        Logger.getLogger(PurchaseOrdersController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        }
        if (task.equals("mapToVendor")) {
            PrintWriter out = response.getWriter();
            String vendor = request.getParameter("vendor");
            String item_names_id = request.getParameter("item_names_id");
            String message = model.mapToVendor(item_names_id, vendor);
            JSONObject gson = new JSONObject();
            gson.put("message", message);
            out.println(gson);
            DBConnection.closeConncetion(model.getConnection());

            return;
        }

        if (task.equals("Search")) {
            String type = request.getParameter("type");
            String org_office = request.getParameter("org_office");
            if (type == null) {
                type = "";
            }
            if (org_office == null) {
                org_office = "";
            }
            task = "new order";
        }
        if (task.equals("new order")) {
            String type = request.getParameter("type");
            String org_office = request.getParameter("org_office");
            if (type == null) {
                type = "";
            }
            if (org_office == null) {
                org_office = "";
            }
            if (type.equals("vendor")) {
                List<PurchaseOrdersBean> cart_list = model.viewCart(logged_key_person_id, loggedUser);
                request.setAttribute("cart_count", cart_list.size());
                request.setAttribute("type", type);
                request.setAttribute("role", loggedUser);
                request.setAttribute("org_office", org_office);
                request.getRequestDispatcher("new_purchase_order_by_vendor").forward(request, response);
            } else {
                List<PurchaseOrdersBean> list = model.getNewOrderData(loggedUser, logged_key_person_id, org_office);

                List<PurchaseOrdersBean> cart_list = model.viewCart(logged_key_person_id, loggedUser);

                request.setAttribute("cart_count", cart_list.size());

                request.setAttribute("type", type);
                request.setAttribute("list", list);
                request.setAttribute("role", loggedUser);
                request.setAttribute("org_office", org_office);

                request.getRequestDispatcher("new_purchase_order").forward(request, response);
            }
        }
        if (task.equals("cart")) {
            String type = request.getParameter("type");
            if (type == null) {
                type = "";
            }
              counting = model.getCounting();
            autogenerate_order_no = "PO" + counting;
            List<PurchaseOrdersBean> list = model.viewCart(logged_key_person_id, loggedUser);

            request.setAttribute("cart_count", list.size());
            request.setAttribute("type", type);
            request.setAttribute("list", list);
            request.setAttribute("org_office_name", org_office_name);
            request.setAttribute("msg", "");
            request.setAttribute("msg_color", "");
            request.getRequestDispatcher("purchase_order_cart").forward(request, response);
        }

        if (task.equals("AddToCart")) {
            try {
                org_office_name = request.getParameter("org_office_name");
                String model_id = request.getParameter("model_id");
                String model_name = request.getParameter("model");
                String vendor_name = request.getParameter("vendor_name");
                String item_names_id = request.getParameter("item_names_id");
                String qty = request.getParameter("qty");
                String rate = request.getParameter("rate");
                if (qty == null) {
                    qty = "";
                }

                if (qty.equals("")) {
                    qty = "1";
                }
                String price = "";
                price = String.valueOf(Integer.parseInt(qty) * Integer.parseInt(rate));
                PurchaseOrdersBean bean = new PurchaseOrdersBean();
                bean.setModel_id(Integer.parseInt(model_id));
                bean.setModel(model_name);
                bean.setVendor(vendor_name);
                bean.setItem_names_id(Integer.parseInt(item_names_id));
                bean.setQty(qty);
                bean.setPrice(price);

                int previous_quantity = model.getCurrentQuantity(item_names_id, model_id, vendor_name, logged_key_person_id);

                if (loggedUser.equals("Incharge")) {
                    org_office_name = logged_org_office;
                }
                int rowsaffected = model.addToCart(bean, logged_key_person_id, org_office_name);

                int current_quantity = model.getCurrentQuantity(item_names_id, model_id, vendor_name, logged_key_person_id);
                request.setAttribute("message", model.getMessage());
                request.setAttribute("msgBgColor", model.getMessageBGColor());

                ArrayList<PurchaseOrdersBean> cart_list = model.viewCart(logged_key_person_id, loggedUser);

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

                    gson.put("success_msg", success_msg);
                    gson.put("error_msg", error_msg);

                    out.println(gson);
                }

                DBConnection.closeConncetion(model.getConnection());

                return;
            } catch (SQLException ex) {
                Logger.getLogger(PurchaseOrdersController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        if (task.equals("cancel_order")) {
            String vendor_id = request.getParameter("vendor_id");
            String org_office_id = request.getParameter("org_office_id");
            String msg = "";
            String msg_color = "";
            int rowsAffected = model.cancelOrder(vendor_id, org_office_id);
            if (rowsAffected > 0) {
                msg = "Order Has been cancelled succesfully!...";
                msg_color = "green";
            } else {
                msg = "Something Went Wrong!...";
                msg_color = "red";
            }
            String type = request.getParameter("type");
            if (type == null) {
                type = "";
            }
            List<PurchaseOrdersBean> list = model.viewCart(logged_key_person_id, loggedUser);

            request.setAttribute("cart_count", list.size());
            request.setAttribute("type", type);
            request.setAttribute("list", list);
            request.setAttribute("msg", msg);
            request.setAttribute("msg_color", msg_color);
            request.getRequestDispatcher("purchase_order_cart").forward(request, response);
        }
        if (task.equals("remove_item")) {
            String vendor_id = request.getParameter("vendor_id");
            String item_names_id = request.getParameter("item_names_id");
            String model_id = request.getParameter("model_id");
            String org_office_id = request.getParameter("org_office_id");

            String msg = "";
            String msg_color = "";
            int rowsAffected = model.removeItem(vendor_id, item_names_id, model_id, org_office_id);
            if (rowsAffected > 0) {
                msg = "Item Has been removed from order succesfully!...";
                msg_color = "green";
            } else {
                msg = "Something Went Wrong!...";
                msg_color = "red";
            }
            List<PurchaseOrdersBean> list = model.viewCartItems(logged_key_person_id, loggedUser, vendor_id, org_office_id);

            request.setAttribute("list", list);
            request.getRequestDispatcher("purchase_order_placed_item").forward(request, response);

        }
        if (task.equals("purchase_order_placed_item")) {
            String vendor_id = request.getParameter("vendor_id");
            String org_office_id = request.getParameter("org_office_id");
            if (vendor_id == null) {
                vendor_id = "";
            }
            List<PurchaseOrdersBean> list = model.viewCartItems(logged_key_person_id, loggedUser, vendor_id, org_office_id);

            request.setAttribute("list", list);
            request.setAttribute("org_office_id", org_office_id);
            request.getRequestDispatcher("purchase_order_placed_item").forward(request, response);
        }
        
        if (task.equals("viewDetails")) {
            String order_no = request.getParameter("order_no");
            List<PurchaseOrdersBean> detail = model.getOrderDetail(order_no);

            float total_qty = 0;
            float total_price = 0;

            for (int i = 0; i < detail.size(); i++) {
                total_qty = total_qty + Float.parseFloat(detail.get(i).getQty());
                total_price = total_price + Float.parseFloat(detail.get(i).getPrice());
            }

            request.setAttribute("detail", detail);
            request.setAttribute("count", detail.size());
            request.setAttribute("order_no", order_no);
            request.setAttribute("total_qty", total_qty);
            request.setAttribute("total_price", total_price);
            request.getRequestDispatcher("purchase_order_detail").forward(request, response);
        }

        if (task.equals("viewPdf")) {
            try {
                String order_no = request.getParameter("order_no");

                String logo_path = ctx.getRealPath("/CRM Dashboard/assets2/img/product/logo1.jpg");
                
                String FILE = "Downloads/" + order_no + ".pdf";
                Document document = new Document(PageSize.A3);
                PdfWriter.getInstance(document, new FileOutputStream(FILE));
                PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(FILE));
                document.open();
                model.addMetaData(document);
                model.addContent(document, logo_path, order_no);
                document.close();
                File downloadFile = new File(FILE);
                FileInputStream inStream = new FileInputStream(downloadFile);

                String relativePath = getServletContext().getRealPath("");
                ServletContext context = getServletContext();

                String mimeType = context.getMimeType(FILE);
                if (mimeType == null) {
                    mimeType = "application/octet-stream";
                }
                response.setContentType(mimeType);
                response.setContentLength((int) downloadFile.length());

                String headerKey = "Content-Disposition";
                String headerValue = String.format("attachment; filename=\"%s\"", downloadFile.getName());
                response.setHeader(headerKey, headerValue);

                OutputStream outStream = response.getOutputStream();
                byte[] buffer = new byte[4096];
                int bytesRead = -1;

                while ((bytesRead = inStream.read(buffer)) != -1) {
                    outStream.write(buffer, 0, bytesRead);
                }
                inStream.close();
                outStream.close();
            } catch (DocumentException ex) {
                Logger.getLogger(PurchaseOrdersController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        List<PurchaseOrdersBean> list = model.getAllExistingOrders(logged_key_person_id, loggedUser);
        request.setAttribute("list", list);
        request.setAttribute("message", model.getMessage());
        request.setAttribute("msgBgColor", model.getMessageBGColor());
        request.setAttribute("role", loggedUser);
        request.getRequestDispatcher("existing_orders").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        doGet(request, response);
    }
}
