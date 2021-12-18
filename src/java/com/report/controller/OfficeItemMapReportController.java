/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.report.controller;

import com.DBConnection.DBConnection;
import com.general.model.GeneralModel;
import com.inventory.model.InventoryBasicModel;
import com.inventory.model.ItemNameModel;
import com.inventory.tableClasses.InventoryBasic;
import com.report.bean.OfficeItemMapReport;
import com.report.model.OfficeItemMapReportModel;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
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
 * @author Komal
 */
public class OfficeItemMapReportController extends HttpServlet {

    private File tmpDir;

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        ServletContext ctx = getServletContext();
        request.setCharacterEncoding("UTF-8");
        HttpSession session = request.getSession();
        response.setHeader("Content-Type", "text/plain; charset=UTF-8");
        OfficeItemMapReportModel model = new OfficeItemMapReportModel();
        ItemNameModel model2 = new ItemNameModel();
        String loggedUser = "";

        String search_item_name = "";
        String search_org_office = "";
        String search_item_code = "";
        String search_manufacturer = "";
        String search_model = "";
        String search_key_person = "";

        search_org_office = request.getParameter("search_org_office");
        search_item_code = request.getParameter("search_item_code");
        search_manufacturer = request.getParameter("search_manufacturer");
        search_model = request.getParameter("search_model");
        search_key_person = request.getParameter("search_key_person");

        if (search_item_name == null) {
            search_item_name = "";
        }
        if (search_org_office == null) {
            search_org_office = "";
        }
        if (search_item_code == null) {
            search_item_code = "";
        }
        if (search_manufacturer == null) {
            search_manufacturer = "";
        }
        if (search_model == null) {
            search_model = "";
        }
        if (search_key_person == null) {
            search_key_person = "";
        }
        if (!search_item_code.equals("")) {
            String search_item_code_arr[] = search_item_code.split(" - ");
            search_item_name = search_item_code_arr[0];
            search_item_code = search_item_code_arr[1];
        }
        try {
            loggedUser = session.getAttribute("user_role").toString();
            model.setConnection(DBConnection.getConnectionForUtf(ctx));
            model2.setConnection(DBConnection.getConnectionForUtf(ctx));
        } catch (Exception e) {
            System.out.println("error in OfficeItemMapReportController setConnection() calling try block" + e);
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

                    if (JQstring.equals("getManufacturer")) {
                        list = model.getManufacturer(q);
                    }

                    if (JQstring.equals("getItemCode")) {
                        String manufacturer = request.getParameter("manufacturer");
                        list = model.getItemCode(q, manufacturer);
                    }

                    if (JQstring.equals("getModelName")) {
                        String manufacturer_name = request.getParameter("manufacturer_name");
                        String item_code = request.getParameter("item_code");
                        list = model.getModelName(q, manufacturer_name, item_code);
                    }
                    if (JQstring.equals("getOrgOffice")) {
                        list = model.getOrgOffice(q);
                    }
                    if (JQstring.equals("getLeadTime")) {
                        String model_name = request.getParameter("model_name");
                        list = model.getLeadTime(model_name);
                    }

                    if (JQstring.equals("getKeyPerson")) {
                        list = model.getKeyPerson(q, str2);
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
                System.out.println("\n Error --OfficeItemMapReportController get JQuery Parameters Part-" + e);
            }

            String task = request.getParameter("task");
            if (task == null) {
                task = "";
            }

            if (task.equals("viewPdf")) {
                String jrxmlFilePath;
                List list = null;

                response.setContentType("application/pdf");
                response.setCharacterEncoding("UTF-8");
                ServletOutputStream servletOutputStream = response.getOutputStream();

                String type = request.getParameter("type");
                String q = "";
//                search_org_office = request.getParameter("search_org_office");
//                search_item_code = request.getParameter("search_item_code");
//                search_manufacturer = request.getParameter("search_manufacturer");
//                search_model = request.getParameter("search_model");

                jrxmlFilePath = ctx.getRealPath("/view/report/OfficeItemMapJasperReport.jrxml");
                list = model.showData(search_item_name, search_org_office, search_manufacturer, search_item_code, search_model, search_key_person);
                byte[] reportInbytes = GeneralModel.generateRecordList(jrxmlFilePath, list);
                response.setContentLength(reportInbytes.length);
                servletOutputStream.write(reportInbytes, 0, reportInbytes.length);
                servletOutputStream.flush();
                servletOutputStream.close();

                return;

            }

            List<OfficeItemMapReport> list = model.showData(search_item_name, search_org_office, search_manufacturer, search_item_code, search_model, search_key_person);
            request.setAttribute("list", list);
            if (!search_item_code.equals("")) {
                request.setAttribute("search_item_code", search_item_name + " - " + search_item_code);
            }

            request.setAttribute("search_org_office", search_org_office);
            request.setAttribute("search_manufacturer", search_manufacturer);
            request.setAttribute("search_model", search_model);
            request.setAttribute("search_key_person", search_key_person);
            request.setAttribute("message", model.getMessage());
            request.setAttribute("loggedUser", loggedUser);

            request.setAttribute("msgBgColor", model.getMsgBgColor());
            model.closeConnection();

            request.getRequestDispatcher("officeItemMapReport").forward(request, response);
        } catch (Exception ex) {
            System.out.println("OfficeItemMapReportController error: " + ex);
        }
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        doGet(request, response);
    }
}
