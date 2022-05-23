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
public class QuotationController extends HttpServlet {

    private File tmpDir;
    String org_office_name = "";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        Map<String, String> map = new HashMap<String, String>();

        System.out.println("Starting application");
        response.setContentType("text/html");
        ServletContext ctx = getServletContext();
        QuotationModel model = new QuotationModel();
        DemandNoteModel demandNoteModel = new DemandNoteModel();
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
            demandNoteModel.setConnection(DBConnection.getConnectionForUtf(ctx));
        } catch (Exception e) {
            System.out.println("error in QuotationController setConnection() calling try block" + e);
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

                if (JQstring.equals("getOrgOffice")) {
                    list = model.getOrgOffice(q);
                }
                if (JQstring.equals("getVendor")) {
                    list = model.getVendorName(q);
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

        if (task.equals("Save")) {
            int quotation_id = 0;
            try {
                quotation_id = Integer.parseInt(request.getParameter("quotation_id").trim());
            } catch (Exception e) {
                quotation_id = 0;
            }
            String org_office = request.getParameter("org_office");
            String type = request.getParameter("type");
            String vendor_name = request.getParameter("vendor_name");
            String current_date = request.getParameter("current_date");
            String rfq_no = request.getParameter("rfq_no");
            String description = request.getParameter("description");

            String checkitem[] = request.getParameterValues("checkitem");
            for (int i = 0; i < checkitem.length; i++) {
                String item_names_id = request.getParameter("item_names_id" + checkitem[i]);
                String model_id = request.getParameter("model_id" + checkitem[i]);
                String qty = request.getParameter("qty" + checkitem[i]);
                String scheduled_date = request.getParameter("scheduled_date" + checkitem[i]);
                String scheduled_date_arr[] = scheduled_date.split("/");
                String month = "";
                if (scheduled_date_arr[0].equals("01")) {
                    month = "Jan";
                }
                if (scheduled_date_arr[0].equals("02")) {
                    month = "Feb";
                }
                if (scheduled_date_arr[0].equals("03")) {
                    month = "Mar";
                }
                if (scheduled_date_arr[0].equals("04")) {
                    month = "Apr";
                }
                if (scheduled_date_arr[0].equals("05")) {
                    month = "May";
                }
                if (scheduled_date_arr[0].equals("06")) {
                    month = "Jun";
                }
                if (scheduled_date_arr[0].equals("07")) {
                    month = "Jul";
                }
                if (scheduled_date_arr[0].equals("08")) {
                    month = "Aug";
                }
                if (scheduled_date_arr[0].equals("09")) {
                    month = "Sep";
                }
                if (scheduled_date_arr[0].equals("10")) {
                    month = "Oct";
                }
                if (scheduled_date_arr[0].equals("11")) {
                    month = "Nov";
                }
                if (scheduled_date_arr[0].equals("12")) {
                    month = "Dec";
                }

                scheduled_date = scheduled_date_arr[1] + "-" + month + "-" + scheduled_date_arr[2] + " 00:00:00 PM";

                Date date = new Date();
                SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss a");
                String time = sdf.format(date);
                if (i == 0) {
                    String cur_date_arr[] = current_date.split("/");
                    current_date = cur_date_arr[1] + "-" + month + "-" + cur_date_arr[2] + " " + time;
                }

                PurchaseOrdersBean bean = new PurchaseOrdersBean();
                bean.setQuotation_no(rfq_no);
                bean.setQuotation_id(quotation_id);
                bean.setItem_names_id(Integer.parseInt(item_names_id));
                bean.setModel_id(Integer.parseInt(model_id));
                bean.setQty(qty);
                bean.setScheduled_date(scheduled_date);
                if (!type.equals("vendor")) {
                    vendor_name = demandNoteModel.getVendor(Integer.parseInt(item_names_id));
                }

                bean.setVendor_id(model.getOrgOfficeId(vendor_name));
                bean.setDescription(description);
                bean.setDate_time(current_date);

                if (quotation_id == 0) {
                    try {
                        if (loggedUser.equals("Admin") || loggedUser.equals("Super Admin")) {
                            logged_org_office_id = model.getOrgOfficeId(org_office);
                        }
                        model.insertRecord(bean, logged_org_office_id, loggedUser, logged_key_person_id);
                    } catch (SQLException ex) {
                        Logger.getLogger(QuotationController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        }

        if (task.equals("Update")) {
            String quotation_no = request.getParameter("quotation_no");
            String item_names_id_arr[] = request.getParameterValues("item_names_id");
            String model_id_arr[] = request.getParameterValues("model_id");
            String rate_arr[] = request.getParameterValues("rate");
            String lead_time_arr[] = request.getParameterValues("lead_time");

            PurchaseOrdersBean bean = new PurchaseOrdersBean();
            bean.setQuotation_no(quotation_no);

            for (int i = 0; i < item_names_id_arr.length; i++) {
                bean.setItem_names_id(Integer.parseInt(item_names_id_arr[i]));
                bean.setModel_id(Integer.parseInt(model_id_arr[i]));
                bean.setRate(rate_arr[i]);
                bean.setLead_time(Integer.parseInt(lead_time_arr[i]));
                if (!rate_arr[i].equals("0")) {
                    model.updateQuotation(bean, logged_key_person_id, loggedUser);
                }
            }
        }
        counting = model.getCounting();
        String rfq = "RFQ" + counting;

        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        String cur_date = sdf.format(date);

        if (task.equals("Search")) {
            String type = request.getParameter("type");
            String org_office = request.getParameter("org_office");
            if (type == null) {
                type = "";
            }
            if (org_office == null) {
                org_office = "";
            }
            task = "new_quotation";
        }
        if (task.equals("new_quotation")) {
            String type = request.getParameter("type");
            if (type == null) {
                type = "";
            }
            String org_office = request.getParameter("org_office");
            if (org_office == null) {
                org_office = "";
            }

            if (type.equals("vendor")) {
                request.setAttribute("type", type);
                request.setAttribute("message", model.getMessage());
                request.setAttribute("msgBgColor", model.getMessageBGColor());
                request.setAttribute("role", loggedUser);
                request.setAttribute("rfq", rfq);
                request.setAttribute("cur_date", cur_date);
                request.setAttribute("org_office", org_office);
                request.getRequestDispatcher("request_for_quotation").forward(request, response);
            } else {
                List<PurchaseOrdersBean> list = model.getNewOrderData(loggedUser, logged_key_person_id, org_office);
                request.setAttribute("type", type);
                request.setAttribute("message", model.getMessage());
                request.setAttribute("msgBgColor", model.getMessageBGColor());
                request.setAttribute("role", loggedUser);
                request.setAttribute("rfq", rfq);
                request.setAttribute("cur_date", cur_date);
                request.setAttribute("org_office", org_office);
                request.setAttribute("list", list);

                request.getRequestDispatcher("request_for_quotation_by_product").forward(request, response);
            }
        }

        if (task.equals("viewDetails")) {
            String quotation_no = request.getParameter("quotation_no");
            List<PurchaseOrdersBean> detail = model.getQuotationDetail(quotation_no, logged_key_person_id, loggedUser);
            int total_qty = 0;
            int total_rate = 0;
            int total_price = 0;
            List<String> status_list = new ArrayList<>();
            List<String> pending = new ArrayList<>();
            pending.add("Pending");

            for (int i = 0; i < detail.size(); i++) {
                total_qty = total_qty + Integer.parseInt(detail.get(i).getQty());
                total_rate = total_rate + Integer.parseInt(detail.get(i).getRate());
                total_price = total_price + (Integer.parseInt(detail.get(i).getRate()) * Integer.parseInt(detail.get(i).getQty()));
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
            request.setAttribute("quotation_no", quotation_no);
            request.setAttribute("total_qty", total_qty);
            request.setAttribute("total_rate", total_rate);
            request.setAttribute("total_price", total_price);
            request.setAttribute("role", loggedUser);
            request.setAttribute("button", button);
            request.getRequestDispatcher("quotation_detail").forward(request, response);
        }

        if (task.equals("viewPdF")) {
            try {
                String quotation_no = request.getParameter("quotation_no");
                String mail = request.getParameter("mail");
                List<PurchaseOrdersBean> detail = model.getQuotationDetail(quotation_no, logged_key_person_id, loggedUser);
                int total_qty = 0;

                for (int i = 0; i < detail.size(); i++) {
                    total_qty = total_qty + Integer.parseInt(detail.get(i).getQty());
                }
                String logo_path = ctx.getRealPath("/CRM Dashboard/assets2/img/product/logo1.jpg");

                String FILE = "Downloads/" + quotation_no + ".pdf";
                Document document = new Document(PageSize.A3);
                PdfWriter.getInstance(document, new FileOutputStream(FILE));
                PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(FILE));
                document.open();
                model.addMetaData(document);
                model.addContent(document, logo_path, quotation_no, logged_key_person_id, loggedUser);
                document.close();

                if (!mail.equals("yes")) {
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
                }
                if (mail.equals("yes")) {
                    String result = model.sentMail(FILE);
                    request.setAttribute("message", model.getMessage());
                    request.setAttribute("msgBgColor", model.getMessageBGColor());
                }
            } catch (DocumentException ex) {
                Logger.getLogger(QuotationController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        if (task.equals("discardQuotation")) {
            String quotation_no = request.getParameter("quotation_no");
            int rowsAffected = model.discardQuotation(quotation_no);
        }
        List<PurchaseOrdersBean> list = model.getAllQuotations(logged_key_person_id, loggedUser);
        request.setAttribute("list", list);
        request.setAttribute("message", model.getMessage());
        request.setAttribute("msgBgColor", model.getMessageBGColor());
        request.setAttribute("role", loggedUser);
        request.getRequestDispatcher("quotations").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        doGet(request, response);
    }
}
