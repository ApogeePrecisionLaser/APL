/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.webservice.controller;

import com.webservice.model.SendMail;
import java.sql.Connection;
import javax.servlet.ServletContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import org.codehaus.jettison.json.JSONException;
//import org.json.simple.JSONArray;
//import org.json.simple.JSONObject;
import com.DBConnection.DBConnection;
import com.general.model.GeneralModel;
import com.report.bean.DailyEnquiryReport;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimerTask;
import javax.net.ssl.HttpsURLConnection;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.ws.rs.GET;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;
import java.util.*;
import javax.servlet.ServletContext;
import com.report.model.DailyEnquiryReportModel;


/**
 *
 * @author komal
 */
public class EnquiryReportSchedular implements ServletContextListener {

    int scheduler_count = 0;
    String path = "";
    Connection connection = null;
    @Context
    ServletContext servletContext;

    @Override
    public void contextDestroyed(ServletContextEvent arg0) {

    }

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {

        ServletContext servletContext = servletContextEvent.getServletContext();
        path = servletContext.getRealPath("/DailyEnquiryReport.jrxml");
        try {
            DailyEnquiryReportModel.setConnection(DBConnection.getConnectionForUtf(servletContext));
        } catch (Exception e) {
            System.out.println("error in DealersReportController setConnection() calling try block" + e);
        }
        try {
            // create the timer and timer task objects
            Timer timer = new Timer();
            MyTimerTask task = new MyTimerTask(); //this class implements Callable.

            // get a calendar to initialize the start time
            Calendar calendar = Calendar.getInstance();
            Date startTime = calendar.getTime();

//            Date date = new Date();
//            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//            SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
//            String time = "10:30:00";
//            String date_time = sdf.format(date) + " " + time;
//            Date start_time = sdf1.parse(date_time);
            // schedule the task to run hourly            
            timer.scheduleAtFixedRate(task, startTime, 1000 * 1 * 60);
            // save our timer for later use
            servletContext.setAttribute("timer", timer);
        } catch (Exception e) {
            servletContext.log("Problem initializing the task that was to run hourly: " + e.getMessage());
        }
    }

    class MyTimerTask extends TimerTask {

        public String sendEnquiryReport() throws JSONException, IOException {
            String result = "";

            System.err.println("hello----");

            String jrxmlFilePath;
            Date date = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-dd");
            String from_date = sdf.format(date);
            String to_date = sdf.format(date);
            List<DailyEnquiryReport> list = null;
            List<String> salesperson_list = new ArrayList<>();
            salesperson_list.add("167");
            salesperson_list.add("168");
            salesperson_list.add("169");
            salesperson_list.add("95");
            salesperson_list.add("172");
            salesperson_list.add("89");//admin
            String role = "";
            if (salesperson_list.size() > 0) {
                for (int i = 0; i < salesperson_list.size(); i++) {
                    if (salesperson_list.get(i).equals("89")) {
                        role = "Admin";
                        list = DailyEnquiryReportModel.getData(from_date, to_date, role, 168);
                    } else {
                        role = "Sales";
                        list = DailyEnquiryReportModel.getData(from_date, to_date, role, Integer.parseInt(salesperson_list.get(i)));
                    }

                    String current_date = list.get(0).getCurrent_date();
                    String count_of_total_query_of_current_date = list.get(0).getTotal_query_of_current_date();
                    String count_of_total_query_till_date = list.get(0).getTotal_query_till_date();
                    String count_of_open_query_of_current_date = list.get(0).getOpen_query_of_current_date();
                    String count_of_open_query_till_date = list.get(0).getOpen_query_till_date();
                    String count_of_closed_query_of_current_date = list.get(0).getClosed_query_of_current_date();
                    String count_of_closed_query_till_date = list.get(0).getClosed_query_till_date();
                    String count_of_sold_query_of_current_date = list.get(0).getSold_query_of_current_date();
                    String count_of_sold_query_till_date = list.get(0).getSold_query_till_date();

                    byte[] reportInbytes = GeneralModel.generateRecordList(path, list);
                    if (Integer.parseInt(list.get(0).getTotal_query_of_current_date()) > 0) {
                        String mail_id = DailyEnquiryReportModel.getMailId(salesperson_list.get(i));
                        String name = DailyEnquiryReportModel.getPersonName(salesperson_list.get(i));
                        writeBytesToFile("ssadvt_repository\\APL\\Report\\sm.pdf", reportInbytes, mail_id, role, name, current_date, count_of_total_query_of_current_date,
                                count_of_total_query_till_date, count_of_open_query_of_current_date, count_of_open_query_till_date, count_of_closed_query_of_current_date,
                                count_of_closed_query_till_date, count_of_sold_query_of_current_date, count_of_sold_query_till_date);
                    }
                }
            }
            return result;
        }

        public void writeBytesToFile(String fileOutput, byte[] bytes, String mail_id, String role, String name, String current_date1, String count_of_total_query_of_current_date,
                String count_of_total_query_till_date, String count_of_open_query_of_current_date, String count_of_open_query_till_date, String count_of_closed_query_of_current_date,
                String count_of_closed_query_till_date, String count_of_sold_query_of_current_date, String count_of_sold_query_till_date)
                throws IOException {

            System.err.println("under write bytres");
            Date date = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String new_date = sdf.format(date);

            String current_date = new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime());
            String folder_path = "C:\\ssadvt_repository\\APL\\Report\\";
            File file = new File(folder_path);
            if (!file.exists()) {
                file.mkdirs();
            }
            if (role.equals("Admin")) {
                name = "Mr. Arun Kumar Gupta(Admin)";
            }
            fileOutput = folder_path + name + current_date + " (Enquiry report).pdf";

            try (FileOutputStream fos = new FileOutputStream(fileOutput)) {
                fos.write(bytes);

                if (role.equals("Admin")) {
                    name = "Mr. Arun Kumar Gupta";
                    mail_id = "arun@apogeeleveller.com";
                }
                SendMail mailC = new SendMail();
                String msg = mailC.sentMail(fileOutput, mail_id, role, current_date, name, current_date, count_of_total_query_of_current_date,
                        count_of_total_query_till_date, count_of_open_query_of_current_date, count_of_open_query_till_date, count_of_closed_query_of_current_date,
                        count_of_closed_query_till_date, count_of_sold_query_of_current_date, count_of_sold_query_till_date);
            }
        }

        @Override
        public void run() {
            try {
                Date date = new Date();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

                SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                SimpleDateFormat sdf2 = new SimpleDateFormat("hh:mm");
                String current_time = sdf2.format(date);
                String time = "05:42";
                if (time.equals(current_time)) {
                    String result = sendEnquiryReport();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
