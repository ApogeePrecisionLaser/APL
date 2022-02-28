package com.dashboard.model;

import com.dashboard.bean.Help;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.servlet.http.HttpSession;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.JasperRunManager;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import org.apache.commons.fileupload.FileItem;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class HelpModel {

    static private Connection connection;
    private String driver, url, user, password;
    static private String message, messageBGColor = "#a2a220";
    static private final String COLOR_OK = "green";
    static private final String COLOR_ERROR = "red";

    public void setConnection(Connection con) {
        try {

            connection = con;
        } catch (Exception e) {
            System.out.println("DealersOrderModel setConnection() Error: " + e);
        }
    }

    public int insertMessage(Help bean, Iterator itr) throws SQLException {
        int rowsAffected = 0;
        int count = 0;
        int key = 0;
        String msg = "";
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM HH-mm-ss");
        SimpleDateFormat sdf1 = new SimpleDateFormat("dd-MMM-yyyy hh:mm:ss a");
        String current_date = sdf.format(date);
        String date_time = sdf1.format(date);
        try {
            String query = "INSERT INTO "
                    + "dealer_help_messages( status,subject,dealer_id, message, "
                    + "document_path, document_name,revision_no,active,remark,description,date_time) "
                    + "VALUES( ?, ?, ?, ?, ?, ?, ?, ?, ?,?,?)";
            String[] image_name = {bean.getDocument_path()};
            String imageName = "";
            for (int i = 0; i < image_name.length; i++) {
                String tempExt = image_name[i];
                int index = tempExt.lastIndexOf(".");
                int index1 = tempExt.length();
                String fieldName = "document_name";
                String Extention = tempExt.substring(index + 1, index1);
                tempExt = "." + Extention;
                imageName = bean.getDealer_id() + "_" + current_date + tempExt;
                bean.setDocument_name(imageName);
                msg = WriteImage(bean, itr, "msg_doc", imageName, fieldName);
                if (msg == null) {
                    msg = "";
                }
            }
            PreparedStatement pstmt = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, "Pending");
            pstmt.setString(2, bean.getSubject());
            pstmt.setInt(3, bean.getDealer_id());
            pstmt.setString(4, bean.getMessage());
            if (msg.equals("Image Uploaded Successfully.")) {
                pstmt.setString(5, "C:/ssadvt_repository/APL/help_doc");
                pstmt.setString(6, imageName);
            } else {
                pstmt.setString(5, "");
                pstmt.setString(6, "");
            }

            pstmt.setInt(7, bean.getRevision_no());
            pstmt.setString(8, "Y");
            pstmt.setString(9, "ok");
            pstmt.setString(10, "");
            pstmt.setString(11, date_time);
            rowsAffected = pstmt.executeUpdate();
            ResultSet rs = pstmt.getGeneratedKeys();
            if (rs != null && rs.next()) {
                key = rs.getInt(1);
            }
        } catch (Exception e) {
            System.out.println("Error: ProfileModel---insertRecord" + e);
        }

        if (rowsAffected
                > 0) {
            message = "Message Sent successfully.";
            messageBGColor = COLOR_OK;

        } else {
            message = "Cannot send message, some error.";
            messageBGColor = COLOR_ERROR;
        }
        return rowsAffected;
    }

    public String WriteImage(Help key, Iterator itr, String destination, String imageName, String fieldName) {
        int count = 0;
//        String msg="";
        try {
            while (itr.hasNext()) {
                FileItem item = (FileItem) itr.next();
                makeDirectory(destination);
                try {

                    if (!item.isFormField()) {
                        if (item.getFieldName().equals(fieldName)) {
                            File file = new File("C:/ssadvt_repository/APL/help_doc/" + imageName);
                            String image = item.getName();
                            if (image.isEmpty() || image.equals("C:/ssadvt_repository/APL/")) {
                            } else {
                                item.write(file);
                                message = "Image Uploaded Successfully.";
                                //  updateImageName(imageName,id);
                                count++;
                            }
                            break;
                        }
                    }
                } catch (Exception e) {
                    System.out.println("ProfileModel WirteImage error: " + e);
                }
            }
            //}
        } catch (Exception ex) {
        }
        return message;
    }

    public boolean makeDirectory(String dirPathName) {
        boolean result = false;
        // System.out.println("dirPathName---" + dirPathName);
        dirPathName = "C:/ssadvt_repository/APL/help_doc";
        File directory = new File(dirPathName);
        if (!directory.exists()) {
            try {
                result = directory.mkdirs();
            } catch (Exception e) {
                System.out.println("ProfileModel makeDirectory Error - " + e);
            }
        }
        return result;
    }

    public static ArrayList<Help> getAllSupportMessages() {
        ArrayList<Help> list = new ArrayList<Help>();
        String query = " select * from dealer_help_messages dhm,key_person kp where dhm.active='Y' and kp.active='Y' "
                + "  and dhm.dealer_id=kp.key_person_id order by dealer_help_messages_id desc  ";
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                Help bean = new Help();
                bean.setSubject(rset.getString("subject"));
                bean.setStatus(rset.getString("status"));
                bean.setMessage(rset.getString("message"));
                bean.setDocument_path(rset.getString("document_path"));
                bean.setDocument_name(rset.getString("document_name"));
                bean.setDealer_name(rset.getString("key_person_name"));
                bean.setContact_no(rset.getString("mobile_no1"));
                String date_time = rset.getString("date_time"); 

                SimpleDateFormat dateFormatter = new SimpleDateFormat("dd-MMM-yyyy hh:mm:ss a");
                Date date = new Date();
                String currentDateString = dateFormatter.format(date);
                Date currentDate = dateFormatter.parse(currentDateString);
                String pastTimeInSecond = date_time;
                Date pastDate = dateFormatter.parse(pastTimeInSecond);
                String time_ago = timeAgo(currentDate, pastDate);
                bean.setTime_ago(time_ago);

                list.add(bean);
            }
        } catch (Exception e) {
            System.out.println("Error in ProfileModel getData -- " + e);
        }
        return list;
    }

    public static String timeAgo(Date currentDate, Date pastDate) {
        long milliSecPerMinute = 60 * 1000; //Milliseconds Per Minute
        long milliSecPerHour = milliSecPerMinute * 60; //Milliseconds Per Hour
        long milliSecPerDay = milliSecPerHour * 24; //Milliseconds Per Day
        long milliSecPerMonth = milliSecPerDay * 30; //Milliseconds Per Month
        long milliSecPerYear = milliSecPerDay * 365; //Milliseconds Per Year
        //Difference in Milliseconds between two dates
        long msExpired = currentDate.getTime() - pastDate.getTime();
        //Second or Seconds ago calculation
        if (msExpired < milliSecPerMinute) {
            if (Math.round(msExpired / 1000) == 1) {
                return String.valueOf(Math.round(msExpired / 1000)) + " second ago";
            } else {
                return String.valueOf(Math.round(msExpired / 1000) + " seconds ago");
            }
        } //Minute or Minutes ago calculation
        else if (msExpired < milliSecPerHour) {
            if (Math.round(msExpired / milliSecPerMinute) == 1) {
                return String.valueOf(Math.round(msExpired / milliSecPerMinute)) + " minute ago";
            } else {
                return String.valueOf(Math.round(msExpired / milliSecPerMinute)) + " minutes ago";
            }
        } //Hour or Hours ago calculation
        else if (msExpired < milliSecPerDay) {
            if (Math.round(msExpired / milliSecPerHour) == 1) {
                return String.valueOf(Math.round(msExpired / milliSecPerHour)) + " hour ago";
            } else {
                return String.valueOf(Math.round(msExpired / milliSecPerHour)) + " hours ago";
            }
        } //Day or Days ago calculation
        else if (msExpired < milliSecPerMonth) {
            if (Math.round(msExpired / milliSecPerDay) == 1) {
                return String.valueOf(Math.round(msExpired / milliSecPerDay)) + " day ago";
            } else {
                return String.valueOf(Math.round(msExpired / milliSecPerDay)) + " days ago";
            }
        } //Month or Months ago calculation 
        else if (msExpired < milliSecPerYear) {
            if (Math.round(msExpired / milliSecPerMonth) == 1) {
                return String.valueOf(Math.round(msExpired / milliSecPerMonth)) + "  month ago";
            } else {
                return String.valueOf(Math.round(msExpired / milliSecPerMonth)) + "  months ago";
            }
        } //Year or Years ago calculation 
        else {
            if (Math.round(msExpired / milliSecPerYear) == 1) {
                return String.valueOf(Math.round(msExpired / milliSecPerYear)) + " year ago";
            } else {
                return String.valueOf(Math.round(msExpired / milliSecPerYear)) + " years ago";
            }
        }
    }

    public void closeConnection() {
        try {
            connection.close();
        } catch (Exception e) {
            System.out.println("DealersOrderModel closeConnection: " + e);
        }
    }

    public void setConnection() {
        try {
            Class.forName(driver);
            connection = DriverManager.getConnection(url, user, password);
        } catch (Exception e) {
            System.out.println("DealersOrderModel setConnection error: " + e);
        }
    }

    public Connection getConnection() {
        return connection;
    }

    public void setDriver(String driver) {
        this.driver = driver;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDriver() {
        return driver;
    }

    public String getUrl() {
        return url;
    }

    public String getUser() {
        return user;
    }

    public String getPassword() {
        return password;
    }

    public String getMessage() {
        return message;
    }

    public String getMessageBGColor() {
        return messageBGColor;
    }

}
