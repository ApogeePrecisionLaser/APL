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
public class DemandNoteController extends HttpServlet {

    private File tmpDir;
    String org_office_name = "";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        Map<String, String> map = new HashMap<String, String>();

        System.out.println("Starting application");
        response.setContentType("text/html");
        ServletContext ctx = getServletContext();
        DemandNoteModel model = new DemandNoteModel();
        String logged_user_name = "";
        String logged_designation = "";
        String logged_org_name = "";
        String logged_org_office = "";
        int logged_org_office_id = 0;
        int logged_org_name_id = 0;
        int logged_key_person_id = 0;
        String loggedUser = "";
        int counting = 100000;
        String autogenerate_demand_note_no = "";
        
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

        String task = request.getParameter("task");
        if (task == null) {
            task = "";
        }
        if (task.equals("Search")) {
            String org_office = request.getParameter("org_office");

            if (org_office == null) {
                org_office = "";
            }
            task = "new_demand";
        }
        if (task.equals("new_demand")) {
            String org_office = request.getParameter("org_office");
            if (org_office == null) {
                org_office = "";
            }
            List<PurchaseOrdersBean> list = model.getNewOrderData(loggedUser, logged_key_person_id, org_office);

            List<PurchaseOrdersBean> cart_list = model.viewCart(logged_key_person_id, loggedUser);

            request.setAttribute("cart_count", cart_list.size());

            request.setAttribute("list", list);
            request.setAttribute("role", loggedUser);
            request.setAttribute("org_office", org_office);

            request.getRequestDispatcher("new_demand_note").forward(request, response);

        }
        if (task.equals("cart")) {
            List<PurchaseOrdersBean> list = model.viewCart(logged_key_person_id, loggedUser);

            request.setAttribute("cart_count", list.size());
            request.setAttribute("list", list);
            request.setAttribute("org_office_name", org_office_name);
            request.setAttribute("msg", "");
            request.setAttribute("msg_color", "");
            request.getRequestDispatcher("purchase_order_cart").forward(request, response);
        }

        request.setAttribute("message", model.getMessage());
        request.setAttribute("msgBgColor", model.getMessageBGColor());
        request.setAttribute("role", loggedUser);
        request.getRequestDispatcher("demand_notes").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        doGet(request, response);
    }
}
