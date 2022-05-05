package com.dashboard.model;

import com.dashboard.bean.EventBean;
import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;
import java.text.SimpleDateFormat;
import org.apache.commons.fileupload.FileItem;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;


/**
 *
 * @author Komal
 */
public class EventModel {

    static private Connection connection;
    private String driver, url, user, password;
    static private String message, messageBGColor = "#a2a220";
    static private final String COLOR_OK = "green";
    static private final String COLOR_ERROR = "red";

    public void setConnection(Connection con) {
        try {

            connection = con;
        } catch (Exception e) {
            System.out.println("EventModel setConnection() Error: " + e);
        }
    }

    public int insertEvent(EventBean bean, Iterator itr) throws SQLException {
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
            String query = " INSERT INTO "
                    + " events( status,title, description, "
                    + " document_path, document_name,revision_no,active,remark,date_time) "
                    + " VALUES( ?, ?, ?, ?, ?, ?, ?, ?, ?) ";
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
            pstmt.setString(2, bean.getTitle());
            pstmt.setString(3, bean.getDescription());
            if (msg.equals("Image Uploaded Successfully.")) {
                pstmt.setString(4, "C:/ssadvt_repository/APL/events_doc");
                pstmt.setString(5, imageName);
            } else {
                pstmt.setString(4, "");
                pstmt.setString(5, "");
            }

            pstmt.setInt(6, bean.getRevision_no());
            pstmt.setString(7, "Y");
            pstmt.setString(8, "ok");
            pstmt.setString(9, date_time);
            rowsAffected = pstmt.executeUpdate();
            ResultSet rs = pstmt.getGeneratedKeys();
            if (rs != null && rs.next()) {
                key = rs.getInt(1);
            }
        } catch (Exception e) {
            System.out.println("Error: EventModel---insertEvent" + e);
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

    public String WriteImage(EventBean key, Iterator itr, String destination, String imageName, String fieldName) {
        int count = 0;
//        String msg="";
        try {
            while (itr.hasNext()) {
                FileItem item = (FileItem) itr.next();
                makeDirectory(destination);
                try {

                    if (!item.isFormField()) {
                        if (item.getFieldName().equals(fieldName)) {
                            File file = new File("C:/ssadvt_repository/APL/events_doc/" + imageName);
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
                    System.out.println("EventModel WirteImage error: " + e);
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
        dirPathName = "C:/ssadvt_repository/APL/events_doc";
        File directory = new File(dirPathName);
        if (!directory.exists()) {
            try {
                result = directory.mkdirs();
            } catch (Exception e) {
                System.out.println("EventModel makeDirectory Error - " + e);
            }
        }
        return result;
    }

    public static ArrayList<EventBean> getAllEvents(int logged_key_person_id, String user_role) {
        ArrayList<EventBean> list = new ArrayList<EventBean>();
        String query = "";

        if (user_role.equals("Dealer")) {
            query = " select * from events ev,dealer_events de where ev.active='Y' and de.active='Y'  "
                    + " and de.events_id=ev.events_id and de.dealer_id='" + logged_key_person_id + "' "
                    + " order by ev.events_id desc  ";
        } else {
            query = " select * from events ev where ev.active='Y' "
                    + " order by ev.events_id desc  ";
        }

        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                EventBean bean = new EventBean();
                bean.setEvents_id(rset.getInt("events_id"));
                bean.setTitle(rset.getString("title"));
                bean.setStatus(rset.getString("status"));
                bean.setDescription(rset.getString("description"));
                bean.setDocument_path(rset.getString("document_path"));
                bean.setDocument_name(rset.getString("document_name"));
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
            System.out.println("Error in EventModel getAllEvents -- " + e);
        }
        return list;
    }

    public static ArrayList<EventBean> getEventDetail(String events_id) {
        ArrayList<EventBean> list = new ArrayList<EventBean>();
        String query = " select * from events ev where ev.active='Y' and events_id='" + events_id + "' "
                + " order by events_id desc  ";
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                EventBean bean = new EventBean();
                bean.setEvents_id(rset.getInt("events_id"));
                bean.setTitle(rset.getString("title"));
                bean.setStatus(rset.getString("status"));
                bean.setDescription(rset.getString("description"));
                bean.setDocument_path(rset.getString("document_path"));
                bean.setDocument_name(rset.getString("document_name"));
                String date_time = rset.getString("date_time");

                SimpleDateFormat dateFormatter = new SimpleDateFormat("dd-MMM-yyyy hh:mm:ss a");
                Date date = new Date();
                String currentDateString = dateFormatter.format(date);
                Date currentDate = dateFormatter.parse(currentDateString);
                String pastTimeInSecond = date_time;
                Date pastDate = dateFormatter.parse(pastTimeInSecond);
                String time_ago = timeAgo(currentDate, pastDate);
                bean.setTime_ago(time_ago);
                bean.setDate_time(date_time);

                list.add(bean);
            }
        } catch (Exception e) {
            System.out.println("Error in EventModel getEventDetail -- " + e);
        }
        return list;
    }

    public static ArrayList<EventBean> getDealersEvents(String events_id, int logged_key_person_id, String user_role) {
        ArrayList<EventBean> list = new ArrayList<EventBean>();
        String query = " select kp.key_person_name,kp.key_person_id,oo.org_office_id,oo.org_office_name "
                + " from key_person kp,dealer_events de,org_office oo where de.active='Y' and kp.active='Y' and oo.active='Y' "
                + " and de.events_id='" + events_id + "' and kp.key_person_id=de.dealer_id and oo.org_office_id=kp.org_office_id ";
        if (user_role.equals("Dealer")) {
            query += " de.dealer_id='" + logged_key_person_id + "' ";
        }
        query += " order by events_id desc  ";
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                EventBean bean = new EventBean();
                bean.setKey_person(rset.getString("key_person_name"));
                bean.setOrg_office(rset.getString("org_office_name"));
                bean.setOrg_office_id(rset.getInt("org_office_id"));
                bean.setKey_person_id(rset.getInt("key_person_id"));
                list.add(bean);
            }
        } catch (Exception e) {
            System.out.println("Error in EventModel getDealersEvents() -- " + e);
        }
        return list;
    }

    public static int getRevisionno(String events_id) {
        int revision = 0;
        try {

            String query = " SELECT max(revision_no) as revision_no FROM events where "
                    + " events_id='" + events_id + "' and active='Y' ";

            PreparedStatement pstmt = (PreparedStatement) connection.prepareStatement(query);

            ResultSet rset = pstmt.executeQuery();

            while (rset.next()) {
                revision = rset.getInt("revision_no");

            }
        } catch (Exception e) {
            System.err.println("EventModel getRevisionno() error--------" + e);
        }
        return revision;
    }

    public int deleteEvent(String events_id) throws SQLException {
        int rowsAffected = 0;
        int updateRowsAffected = 0;
        int updateRowsAffected1 = 0;
        int count = 0;

        int revision = getRevisionno(events_id);

        try {
            String query1 = " SELECT max(revision_no) as revision_no FROM events WHERE events_id='" + events_id + "' "
                    + " and active='Y' ";

            String query_update = " UPDATE events SET active =? WHERE events_id=? "
                    + " and revision_no=? ";
            String query_update_item = " UPDATE events SET active =? WHERE events_id=? ";

            PreparedStatement pstmt = connection.prepareStatement(query1);

            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                revision = rs.getInt("revision_no");

                PreparedStatement pstm = connection.prepareStatement(query_update);
                pstm.setString(1, "N");
                pstm.setString(2, events_id);
                pstm.setInt(3, revision);
                updateRowsAffected = pstm.executeUpdate();
            }
            if (updateRowsAffected1 > 0) {
                message = "Record saved successfully.";
                messageBGColor = COLOR_OK;
            } else {
                message = "Cannot save the record, some error.";
                messageBGColor = COLOR_ERROR;

            }
        } catch (Exception e) {
            System.err.println("EventModel deleteEvent() Exception---" + e);
        }

        return updateRowsAffected1;
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

    public static ArrayList<EventBean> getAllDealers(String events_id) {
        ArrayList<EventBean> list = new ArrayList<EventBean>();
        String query = " select kp.key_person_id,kp.key_person_name,oo.org_office_name,kp.mobile_no1,kp.email_id1,ct.city_name "
                + " from org_office oo,city ct,key_person kp "
                + " where oo.active='Y' and ct.active='Y' and kp.active='Y' and kp.org_office_id=oo.org_office_id "
                + " and oo.city_id=ct.city_id and oo.office_type_id=3 "
                + " order by oo.org_office_name  ";
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                EventBean bean = new EventBean();
                bean.setOrg_office(rset.getString("org_office_name"));
                bean.setKey_person(rset.getString("key_person_name"));
                bean.setKey_person_id(rset.getInt("key_person_id"));
                bean.setKey_person_mobile(rset.getString("mobile_no1"));
                bean.setKey_person_email(rset.getString("email_id1"));
                bean.setCity(rset.getString("city_name"));

                String query2 = " select count(*) as count "
                        + " from key_person kp,dealer_events de,org_office oo where de.active='Y' and kp.active='Y' and oo.active='Y' "
                        + " and de.dealer_id='" + rset.getInt("key_person_id") + "' and de.events_id='" + events_id + "' "
                        + " and kp.key_person_id=de.dealer_id and oo.org_office_id=kp.org_office_id "
                        + " order by oo.org_office_name   ";
                PreparedStatement pstmt2 = connection.prepareStatement(query2);
                ResultSet rset2 = pstmt2.executeQuery();
                int count = 0;
                while (rset2.next()) {
                    count = rset2.getInt("count");
                }
                if (count > 0) {
                    bean.setChecked("yes");
                } else {
                    bean.setChecked("no");
                }

                list.add(bean);
            }
        } catch (Exception e) {
            System.out.println("Error in EventModel getAllDealers -- " + e);
        }
        return list;
    }

    public int sendEventsToDealer(String events_id, String checked_dealer[]) throws SQLException {
        String query = " INSERT INTO dealer_events(dealer_id,events_id, "
                + " active,remark,date_time,revision_no,description) "
                + " VALUES(?,?,?,?,?,?,?) ";
        int rowsAffected = 0;

        int count = 0;
        java.util.Date date = new java.util.Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss a");
        String date_time = sdf.format(date);

        try {
            connection.setAutoCommit(false);
            for (int i = 0; i < checked_dealer.length; i++) {
                String dealer_id = checked_dealer[i];

                String exist_query = " select count(*) as count from dealer_events where active='Y' "
                        + "  and events_id='" + events_id + "'  and dealer_id='" + dealer_id + "' ";
                PreparedStatement pstmt1 = connection.prepareStatement(exist_query);
                ResultSet rst = pstmt1.executeQuery();
                while (rst.next()) {
                    count = rst.getInt("count");

                }
                if (count > 0) {
                    message = "Event is already sent to Dealer!..";
                    messageBGColor = COLOR_ERROR;
                } else {
                    PreparedStatement pstmt = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
                    pstmt.setString(1, dealer_id);
                    pstmt.setString(2, events_id);
                    pstmt.setString(3, "Y");
                    pstmt.setString(4, "OK");
                    pstmt.setString(5, date_time);
                    pstmt.setInt(6, 0);
                    pstmt.setString(7, "");
                    rowsAffected = pstmt.executeUpdate();
                }
            }

        } catch (Exception e) {
            System.out.println("EventModel sendEventsToDealer() Error: " + e);
        }
        if (rowsAffected > 0) {
            message = "Record saved successfully.";
            messageBGColor = COLOR_OK;
            connection.commit();

        } else {
            message = "Cannot save the record, some error.";
            messageBGColor = COLOR_ERROR;
            connection.rollback();

        }
        if (count > 0) {
            message = "Indent No. Already Exists!..";
            messageBGColor = COLOR_ERROR;
        }

        return rowsAffected;
    }

    public void closeConnection() {
        try {
            connection.close();
        } catch (Exception e) {
            System.out.println("EventModel closeConnection: " + e);
        }
    }

    public void setConnection() {
        try {
            Class.forName(driver);
            connection = DriverManager.getConnection(url, user, password);
        } catch (Exception e) {
            System.out.println("EventModel setConnection error: " + e);
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
