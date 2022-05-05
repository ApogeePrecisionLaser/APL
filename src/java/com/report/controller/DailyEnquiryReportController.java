
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.report.controller;

import com.DBConnection.DBConnection;
import com.general.model.GeneralModel;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Date;
import com.report.bean.DailyEnquiryReport;
import com.report.model.DailyEnquiryReportModel;

/**
 *
 * @author Komal
 */
public class DailyEnquiryReportController extends HttpServlet {

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
        String loggedUser = "";

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

        DailyEnquiryReportModel model = new DailyEnquiryReportModel();
        String active = "Y";
        String ac = "ACTIVE RECORDS";
        String msg = "";

        try {
            model.setConnection(DBConnection.getConnectionForUtf(ctx));
        } catch (Exception e) {
            System.out.println("error in DealersReportController setConnection() calling try block" + e);
        }
        String task = request.getParameter("task");
        String from_date = request.getParameter("from_date");
        String to_date = request.getParameter("to_date");

        if (task == null) {
            task = "";
        }
        if (from_date == null) {
            from_date = "";
        }
        if (to_date == null) {
            to_date = "";
        }

        try {
            if ((task.equals("View Report"))) {
                String jrxmlFilePath;
                List list = null;

                response.setContentType("application/pdf");
                response.setCharacterEncoding("UTF-8");
                ServletOutputStream servletOutputStream = response.getOutputStream();

                String type = request.getParameter("type");

                jrxmlFilePath = ctx.getRealPath("/DailyEnquiryReport.jrxml");
                list = model.getData(from_date, to_date, loggedUser, logged_key_person_id);
                byte[] reportInbytes = GeneralModel.generateRecordList(jrxmlFilePath, list);
                response.setContentLength(reportInbytes.length);
                servletOutputStream.write(reportInbytes, 0, reportInbytes.length);
                servletOutputStream.flush();
                servletOutputStream.close();
                return;

                // start Write a string into file and download
//                File output = new File("output.txt");
//                FileWriter writer = new FileWriter(output);
//
//                writer.write("This text was written with a FileWriter");
//                writer.flush();
//                writer.close();
//
//                FileInputStream inStream = new FileInputStream(output);
//
//                String relativePath = getServletContext().getRealPath("");
//                ServletContext context = getServletContext();
//
//                String mimeType = context.getMimeType("output.txt");
//                if (mimeType == null) {
//                    mimeType = "application/octet-stream";
//                }
//                response.setContentType(mimeType);
//                response.setContentLength((int) output.length());
//
//                String headerKey = "Content-Disposition";
//                String headerValue = String.format("attachment; filename=\"%s\"", output.getName());
//                response.setHeader(headerKey, headerValue);
//
//                OutputStream outStream = response.getOutputStream();
//                byte[] buffer = new byte[4096];
//                int bytesRead = -1;
//
//                while ((bytesRead = inStream.read(buffer)) != -1) {
//                    outStream.write(buffer, 0, bytesRead);
//                }
//                inStream.close();
//                outStream.close();
                // end Write a string into file and download
            }

            if ((task.equals("viewMultipleImagesReport"))) {
                String jrxmlFilePath;
                List list = null;

                response.setContentType("application/pdf");
                response.setCharacterEncoding("UTF-8");
                ServletOutputStream servletOutputStream = response.getOutputStream();
                jrxmlFilePath = ctx.getRealPath("/MultipleDynamicImages.jrxml");
                list = model.getDynamicImagesData();
                byte[] reportInbytes = GeneralModel.generateRecordList(jrxmlFilePath, list);
                response.setContentLength(reportInbytes.length);
                servletOutputStream.write(reportInbytes, 0, reportInbytes.length);
                servletOutputStream.flush();
                servletOutputStream.close();
                return;
            }

            List<DailyEnquiryReport> list = model.getData(from_date, to_date, loggedUser, logged_key_person_id);
            String current_date = "";
            Date date = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-dd");
            if (from_date.equals("")) {
                current_date = sdf.format(date);
            }
            if (!from_date.equals("") || !to_date.equals("")) {
                current_date = from_date + " to " + to_date;
            }

            request.setAttribute("list", list);
            request.setAttribute("from_date", from_date);
            request.setAttribute("current_date", current_date);
            request.setAttribute("to_date", to_date);
            request.getRequestDispatcher("enquiry_report").forward(request, response);
        } catch (Exception ex) {
            System.out.println("DealersReportController error: " + ex);
        }
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        doGet(request, response);
    }
}
