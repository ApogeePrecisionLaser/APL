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
        QuotationModel quotationModel = new QuotationModel();
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
            quotationModel.setConnection(DBConnection.getConnectionForUtf(ctx));
        } catch (Exception e) {
            System.out.println("error in DemandNoteController setConnection() calling try block" + e);
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
            System.out.println("\n Error --DemandNoteController get JQuery Parameters Part-" + e);
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

            List<PurchaseOrdersBean> list = model.getNewOrderData(loggedUser, logged_key_person_id, logged_org_office);

            List<PurchaseOrdersBean> cart_list = model.viewCart(logged_key_person_id, loggedUser);

            request.setAttribute("cart_count", cart_list.size());

            request.setAttribute("list", list);
            request.setAttribute("role", loggedUser);
            request.setAttribute("org_office", logged_org_office);

            request.getRequestDispatcher("new_demand_note").forward(request, response);

        }

        if (task.equals("Submit")) {
            counting = model.getCounting();
            autogenerate_demand_note_no = "DNO" + counting;
            int demand_note_id = 0;
            try {
                demand_note_id = Integer.parseInt(request.getParameter("demand_note_id").trim());
            } catch (Exception e) {
                demand_note_id = 0;
            }

            String item_names_id[] = request.getParameterValues("pr_item_names_id");
            String model_id[] = request.getParameterValues("pr_model_id");
            String qty[] = request.getParameterValues("pr_qty");
            for (int i = 0; i < item_names_id.length; i++) {
                PurchaseOrdersBean bean = new PurchaseOrdersBean();
                bean.setDemand_note_no(autogenerate_demand_note_no);
                bean.setDemand_note_id(demand_note_id);
                bean.setItem_names_id(Integer.parseInt(item_names_id[i]));
                bean.setModel_id(Integer.parseInt(model_id[i]));
                bean.setQty(qty[i]);
                if (demand_note_id == 0) {
                    try {
                        model.insertRecord(bean, logged_org_office_id, loggedUser, logged_key_person_id);
                    } catch (SQLException ex) {
                        Logger.getLogger(DemandNoteController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        }

        if (task.equals("viewDetails")) {
            String order_no = request.getParameter("order_no");
            List<PurchaseOrdersBean> detail = model.getOrderDetail(order_no, logged_key_person_id, loggedUser);

            int total_qty = 0;
            int total_approved_qty = 0;
            List<String> status_list = new ArrayList<>();
            List<String> pending = new ArrayList<>();
            pending.add("Pending");
            for (int i = 0; i < detail.size(); i++) {
                total_qty = total_qty + Integer.parseInt(detail.get(i).getQty());
                total_approved_qty = total_approved_qty + detail.get(i).getApproved_qty();
                status_list.add(detail.get(i).getStatus());
            }

            String button = "";
            if (status_list.containsAll(pending)) {
                button = "Enable";
            } else {
                button = "Disabled";
            }
            request.setAttribute("detail", detail);
            request.setAttribute("count", detail.size());
            request.setAttribute("order_no", order_no);
            request.setAttribute("total_qty", total_qty);
            request.setAttribute("total_approved_qty", total_approved_qty);
            request.setAttribute("role", loggedUser);
            request.setAttribute("button", button);
            request.getRequestDispatcher("demand_note_detail").forward(request, response);
        }

        if ((task.equals("Confirm")) || (task.equals("Denied All"))) {
            PrintWriter out = response.getWriter();

            String demand_note_id_arr[] = request.getParameterValues("demand_note_id");
            String approved_qty_arr[] = request.getParameterValues("approved_qty");

            for (int i = 0; i < demand_note_id_arr.length; i++) {
                try {
                    int demand_note_id = Integer.parseInt(demand_note_id_arr[i]);
                    String status = request.getParameter("status" + demand_note_id);
                    int approved_qty = Integer.parseInt(approved_qty_arr[i]);
                    if (status.equals("Select")) {
                        status = "Denied";
                    }
                    PurchaseOrdersBean bean = new PurchaseOrdersBean();
                    bean.setStatus(status);
                    bean.setApproved_qty(approved_qty);

                    String message = model.approveDemandNote(bean, demand_note_id, i, demand_note_id_arr.length);
                } catch (SQLException ex) {
                    Logger.getLogger(DemandNoteController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }

        if (task.equals("convertToQuotation")) {
            String demand_note_no = request.getParameter("demand_note_no");
            counting = quotationModel.getCounting();
            String rfq_no = "RFQ" + counting;
            int quotation_id = 0;
            try {
                quotation_id = Integer.parseInt(request.getParameter("quotation_id").trim());
            } catch (Exception e) {
                quotation_id = 0;
            }
            List<PurchaseOrdersBean> list = model.getOrderDetail(demand_note_no, logged_key_person_id, loggedUser);

            for (int i = 0; i < list.size(); i++) {
//                String vendor_name = request.getParameter("vendor_name");
                Date date = new Date();
                SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss a");
                String cur_date = sdf.format(date);
                String description = "";

                int item_names_id = list.get(i).getItem_names_id();
                int model_id = list.get(i).getModel_id();
                int qty = list.get(i).getApproved_qty();
                String scheduled_date = cur_date;
                PurchaseOrdersBean bean = new PurchaseOrdersBean();
                bean.setQuotation_no(rfq_no);
                bean.setQuotation_id(quotation_id);
                bean.setItem_names_id(item_names_id);
                bean.setModel_id(model_id);
                bean.setQty(String.valueOf(qty));
                bean.setScheduled_date(scheduled_date);
                String vendor_name = model.getVendor(item_names_id);
                bean.setVendor_id(model.getOrgOfficeId(vendor_name));
                bean.setDescription(description);
                bean.setDate_time(cur_date);

                if (quotation_id == 0) {
                    try {
                        if (loggedUser.equals("Admin") || loggedUser.equals("Super Admin")) {
                            logged_org_office_id = model.getOrgOfficeId(list.get(i).getCustomer_office_name());
                            logged_key_person_id = list.get(i).getKey_person_id();
                        }
                        if (qty != 0) {
                            quotationModel.insertRecord(bean, logged_org_office_id, loggedUser, logged_key_person_id);
                        }
                    } catch (SQLException ex) {
                        Logger.getLogger(DemandNoteController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
            List<PurchaseOrdersBean> list1 = quotationModel.getAllQuotations(logged_key_person_id, loggedUser);
            request.setAttribute("list", list1);
            request.getRequestDispatcher("QuotationController").forward(request, response);

        }
        List<PurchaseOrdersBean> list = model.getAllExistingDemandNotes(logged_key_person_id, loggedUser);
        request.setAttribute("list", list);
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
