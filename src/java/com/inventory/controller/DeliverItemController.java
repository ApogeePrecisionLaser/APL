/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.inventory.controller;

import com.inventory.controller.*;
import com.DBConnection.DBConnection;
import com.apl.order.model.DeliverOrderItemModel;
import com.inventory.model.DeliverItemModel;
import com.inventory.tableClasses.DeliverItem;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author Komal
 */
@MultipartConfig
public class DeliverItemController extends HttpServlet {

    private File tmpDir;

    private String extractfileName(Part part) {
        String content = part.getHeader("content-disposition");
        String[] items = content.split(";");
        for (String s : items) {
            if (s.trim().startsWith("filename")) {

                return s.substring(s.indexOf("=") + 2, s.length() - 1);
            }

        }

        return null;
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        ServletContext ctx = getServletContext();
        request.setCharacterEncoding("UTF-8");
        response.setHeader("Content-Type", "text/plain; charset=UTF-8");
        String logged_user_name = "";
        String logged_designation = "";
        String logged_org_name = "";
        String logged_org_office = "";
        int logged_org_office_id = 0;
        int logged_org_name_id = 0;
        int logged_key_person_id = 0;
        String office_admin = "";
        int last_indent_table_id = 0;
        int counting = 100;
        String indent_no = "";
        String requested_by = "";
        String requested_to = "";
        String description = "";
        String message_split[] = null;
        int delivery_indent_table_id = 0;

        HttpSession session = request.getSession();
        if (session == null || session.getAttribute("logged_user_name") == null) {
            request.getRequestDispatcher("/").forward(request, response);
            return;
        } else {
            logged_user_name = session.getAttribute("logged_user_name").toString();
            logged_org_name = session.getAttribute("logged_org_name").toString();
            logged_designation = session.getAttribute("logged_designation").toString();
            logged_org_office = session.getAttribute("logged_org_office").toString();
            logged_org_name_id = Integer.parseInt(session.getAttribute("logged_org_name_id").toString());
            logged_org_office_id = Integer.parseInt(session.getAttribute("logged_org_office_id").toString());
            logged_key_person_id = Integer.parseInt(session.getAttribute("logged_key_person_id").toString());
            office_admin = session.getAttribute("office_admin").toString();
        }

        DeliverItemModel model = new DeliverItemModel();

        String search_item_name = "";

        search_item_name = request.getParameter("search_item_name");

        try {
            model.setConnection(DBConnection.getConnectionForUtf(ctx));
        } catch (Exception e) {
            System.out.println("error in DeliverItemController setConnection() calling try block" + e);
        }

        try {
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
                        if (type.equals("Status")) {
                            list = model.getStatus(q);
                        }
                    }
                    if (JQstring.equals("getStatus")) {
                        list = model.getStatus(q);
                    }

                    if (JQstring.equals("getRequestedByKeyPerson")) {
                        list = model.getRequestedByKeyPerson(q);
                    }

                    if (JQstring.equals("getRequestedToKeyPerson")) {
                        String requested_by_person = request.getParameter("requested_by");
                        list = model.getRequestedToKeyPerson(q, requested_by_person);
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
                System.out.println("\n Error --DeliverItemController get JQuery Parameters Part-" + e);
            }

            String task = request.getParameter("task");

            if (task == null) {
                task = "";
            }

            if (task.equals("generateDeliveryReport")) {
                String counter = request.getParameter("counter");
                String delivered_qty = request.getParameter("delivered_qty");
                String delivery_challan_date = request.getParameter("delivery_challan_date");
                String indent_num = request.getParameter("indent_no");
                String delivery_challan_no = request.getParameter("delivery_challan_no");
                String item_name_report = request.getParameter("item_name");
                
                List listAll = null;
                String jrxmlFilePath;
                response.setContentType("application/pdf");
                ServletOutputStream servletOutputStream = response.getOutputStream();

//                jrxmlFilePath = ctx.getRealPath("/IndentChallan.jrxml");
                jrxmlFilePath = ctx.getRealPath("/DeliveryChallan.jrxml");

                listAll = model.showReportData(logged_user_name, office_admin, indent_num, delivery_challan_date, delivery_challan_no, item_name_report);
                // listAll = tubeWellSurveyModel.showData(-1, -1,Pole,IvrsNo,"",FileNo,PageNo,Date,"",meterFunctional,feeder,typeOfConnection,dateTo,searchStatus,feeder_ivrs_search);
                byte[] reportInbytes = model.generateMapReport(jrxmlFilePath, listAll);
                response.setContentLength(reportInbytes.length);
                response.addHeader("Content-disposition", "attachment; filename=" + indent_num);

                servletOutputStream.write(reportInbytes, 0, reportInbytes.length);
                servletOutputStream.flush();
                servletOutputStream.close();
                return;
            }
            if (task.equals("GetIndentItems")) {
                // List<ItemName> list = null;
                //  String indent_no = request.getParameter("indent_no");
                int indent_table_id = Integer.parseInt(request.getParameter("indent_table_id").trim());

                List<DeliverItem> indent_items_list = model.getIndentItems(indent_table_id);
                request.setAttribute("indent_items_list", indent_items_list);
                request.getRequestDispatcher("deliveredIndentItemList").forward(request, response);
                return;
            }

            if (task.equals("Delete")) {
                // model.deleteRecord(Integer.parseInt(request.getParameter("indent_table_id")));
            } else if ((task.equals("Upload Challan & Deliver Items"))) {

                String savepath = "C:\\ssadvt_repository\\DeliveryChallanPdf" + File.separator;
                File file = new File(savepath);
                Part part = request.getPart("up");
                String filename = extractfileName(part);
                part.write(savepath + File.separator + filename);
                String image_path = savepath.concat(filename);
                String indent_item_id_arr[] = request.getParameterValues("indent_item_id");
                int indent_table_id = Integer.parseInt(request.getParameter("indent_table_id").trim());
                String delivery_challan_no = request.getParameter("delivery_challan_no");
                String delivery_challan_date = request.getParameter("delivery_challan_date");
                String decsription = request.getParameter("description");

                String message = "";
                for (int i = 0; i < indent_item_id_arr.length; i++) {
                    int indent_item_id = Integer.parseInt(indent_item_id_arr[i]);
                    String status_item = request.getParameter("item_status" + indent_item_id);
                    String item_name = request.getParameter("item_name" + indent_item_id);
                    String model_name = request.getParameter("model" + indent_item_id);

                    String requested_by_id = request.getParameter("requested_by");
                    String requsted_to = request.getParameter("requested_to");
                    int delivered_qty = Integer.parseInt(request.getParameter("delivered_qty" + indent_item_id).trim());

                    DeliverItem bean = new DeliverItem();
                    bean.setDelivery_challan_no(delivery_challan_no);
                    bean.setChallan_date(delivery_challan_date);
                    bean.setItem_status(status_item);
                    bean.setDelivered_qty(delivered_qty);
                    bean.setItem_name(item_name);
                    bean.setModel(model_name);
                    bean.setRequested_by_id(Integer.parseInt(requested_by_id));
                    bean.setDescription(description);
                    bean.setIndent_table_id(indent_table_id);
                    bean.setIndent_item_id(indent_item_id);
                    bean.setImage_path(image_path);
                    if (!status_item.equals("Denied")) {
                        message = model.updateRecord(bean, indent_item_id, indent_table_id, task, logged_org_office, logged_user_name);
                    }
                }

                message_split = message.split("&");
                delivery_indent_table_id = indent_table_id;

                request.setAttribute("delivery_message", message_split[0]);
                request.setAttribute("delivery_status", message_split[1]);
                request.setAttribute("delivery_indent_table_id", delivery_indent_table_id);
                request.getRequestDispatcher("deliveryChallan").forward(request, response);
                return;
            }
            //counting = model.getCounting();
            String autogenerate_indent_no = "Indent_" + counting;

//            List<DeliverItem> list = model.showData(logged_designation);
            List<DeliverItem> list = model.showIndents(logged_designation);
            request.setAttribute("list", list);
            request.setAttribute("autogenerate_indent_no", autogenerate_indent_no);
            request.setAttribute("requested_by", logged_user_name);
            request.setAttribute("requested_to", office_admin);
            request.setAttribute("message", model.getMessage());
            request.setAttribute("msgBgColor", model.getMsgBgColor());

            model.closeConnection();

            request.getRequestDispatcher("deliver_item").forward(request, response);

        } catch (Exception ex) {
            System.out.println("DeliverItemController error: " + ex);
        }
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        doGet(request, response);
    }
}
