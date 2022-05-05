package com.purchase.module;

import com.DBConnection.DBConnection;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.File;
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
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;


/**
 *
 * @author komal
 */
public class ApprovePurchaseOrdersController extends HttpServlet {

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
            System.out.println("error in ApprovePurchaseOrdersController setConnection() calling try block" + e);
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
                
                if (JQstring.equals("getVendor")) {
                    list = model.getVendorName(q);
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
            System.out.println("\n Error --ApprovePurchaseOrdersController get JQuery Parameters Part-" + e);
        }
        List items = null;
        Iterator itr = null;
        DiskFileItemFactory fileItemFactory = new DiskFileItemFactory(); //Set the size threshold, above which content will be stored on disk.
        fileItemFactory.setSizeThreshold(1 * 1024 * 1024); //1 MB Set the temporary directory to store the uploaded files of size above threshold.
        fileItemFactory.setRepository(tmpDir);
        ServletFileUpload uploadHandler = new ServletFileUpload(fileItemFactory);

        String task = request.getParameter("task");
        if (task == null) {
            task = "";
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
            request.getRequestDispatcher("approve_purchase_order_detail").forward(request, response);
        }
        if ((task.equals("Confirm")) || (task.equals("Denied All"))) {
            PrintWriter out = response.getWriter();

            String purchase_order_id_arr[] = request.getParameterValues("purchase_order_id");

            for (int i = 0; i < purchase_order_id_arr.length; i++) {
                try {
                    int purchase_order_id = Integer.parseInt(purchase_order_id_arr[i]);
                    String status = request.getParameter("status" + purchase_order_id);
                    if (status.equals("Select")) {
                        status = "Denied";
                    }
                    PurchaseOrdersBean bean = new PurchaseOrdersBean();
                    bean.setStatus(status);

                    String message = model.approvePurchaseOrder(bean, purchase_order_id, i, purchase_order_id_arr.length);
                } catch (SQLException ex) {
                    Logger.getLogger(ApprovePurchaseOrdersController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        
        List<PurchaseOrdersBean> list = model.getAllPendingOrders(logged_key_person_id, loggedUser);
        request.setAttribute("list", list);
        request.setAttribute("message", model.getMessage());
        request.setAttribute("msgBgColor", model.getMessageBGColor());
        request.setAttribute("role", loggedUser);
        request.getRequestDispatcher("approve_purchase_orders").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        doGet(request, response);
    }
}
