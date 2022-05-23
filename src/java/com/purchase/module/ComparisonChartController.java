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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
public class ComparisonChartController extends HttpServlet {

    private File tmpDir;
    String org_office_name = "";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        Map<String, String> map = new HashMap<String, String>();

        System.out.println("Starting application");
        response.setContentType("text/html");
        ServletContext ctx = getServletContext();
        ComparisonChartModel model = new ComparisonChartModel();
        PurchaseOrdersModel purchaseOrdersModel = new PurchaseOrdersModel();
        String logged_user_name = "";
        String logged_designation = "";
        String logged_org_name = "";
        String logged_org_office = "";
        int logged_org_office_id = 0;
        int logged_org_name_id = 0;
        int logged_key_person_id = 0;
        String loggedUser = "";
        int counting = 100000;
        String autogenerate_comparative_charts_no = "";
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
            purchaseOrdersModel.setConnection(DBConnection.getConnectionForUtf(ctx));
        } catch (Exception e) {
            System.out.println("error in ComparisonChartController setConnection() calling try block" + e);
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

//                if (JQstring.equals("getOrgOffice")) {
//                    list = model.getOrgOffice(q);
//                }
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
            System.out.println("\n Error --QuotationController get JQuery Parameters Part-" + e);
        }

        String task = request.getParameter("task");
        if (task == null) {
            task = "";
        }

        if (task.equals("viewComparativeChart")) {
            counting = model.getCounting();
            autogenerate_comparative_charts_no = "QCC" + counting;

            Date date = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            String cur_date = sdf.format(date);
            String item_name = request.getParameter("item_name");
            String model_name = request.getParameter("model");
            List<PurchaseOrdersBean> list2 = model.getAllQuotationDetail(logged_key_person_id, loggedUser, logged_org_office_id, item_name, model_name);
            request.setAttribute("list2", list2);
            request.setAttribute("cur_date", cur_date);
            request.setAttribute("autogenerate_comparative_charts_no", autogenerate_comparative_charts_no);
            request.getRequestDispatcher("comparison_chart").forward(request, response);
        }
        if (task.equals("confirmOrder")) {
            String quotation_id = request.getParameter("quotation_id");
            counting = purchaseOrdersModel.getCounting();
            autogenerate_order_no = "PO" + counting;
            int purchase_order_id = 0;
            try {
                purchase_order_id = Integer.parseInt(request.getParameter("purchase_order_id").trim());
            } catch (Exception e) {
                purchase_order_id = 0;
            }
            List<PurchaseOrdersBean> detail = model.getQuotationDetail(logged_key_person_id, loggedUser, logged_org_office_id, quotation_id);

            PurchaseOrdersBean bean = new PurchaseOrdersBean();
            bean.setOrder_no(autogenerate_order_no);
            bean.setPurchase_order_id(purchase_order_id);
            bean.setItem_names_id(detail.get(0).getItem_names_id());
            bean.setModel_id(detail.get(0).getModel_id());
            bean.setQty(detail.get(0).getQty());
            bean.setPrice(detail.get(0).getPrice());
            bean.setLead_time(detail.get(0).getLead_time());
            bean.setVendor_id(detail.get(0).getVendor_id());
            bean.setQuotation_id(Integer.parseInt(quotation_id));

            if (purchase_order_id == 0) {
                try {
                    model.insertRecord(bean, Integer.parseInt(detail.get(0).getOrg_office_id()), loggedUser, logged_key_person_id
                    );
                } catch (SQLException ex) {
                    Logger.getLogger(PurchaseOrdersController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }

        List<PurchaseOrdersBean> list = model.getAllQuotations(logged_key_person_id, loggedUser, logged_org_office_id);
        request.setAttribute("list", list);
        request.setAttribute("message", model.getMessage());
        request.setAttribute("msgBgColor", model.getMessageBGColor());
        request.setAttribute("role", loggedUser);

        request.getRequestDispatcher("comparison_chart_item_list").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        doGet(request, response);
    }
}
