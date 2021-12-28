package com.apogee.admin;

import com.organization.tableClasses.OrganisationName;
import java.io.ByteArrayOutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.JasperRunManager;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import org.json.simple.JSONObject;
import com.DBConnection.DBConnection;
import static java.lang.Math.PI;
import java.text.DecimalFormat;
import java.text.NumberFormat;

/**
 *
 * @author Vikrant
 */
public class AttendanceModel {

    private static Connection connection;     
    private Connection connection2;     
    private String driver, url, user, password;

    private String message;
    private String msgBgColor;
    private final String COLOR_OK = "#a2a220";
    private final String COLOR_ERROR = "red";

    public void setConnection(Connection con) {
        try {
            connection = con;
        } catch (Exception e) {
            System.out.println("com.apogee.admin.AttendanceModel.setConnection()-"+e);
        }
    }
    

    public List<AttendanceBean> showData(String key_person, String date) {
        List<AttendanceBean> list = new ArrayList<AttendanceBean>();
        NumberFormat df = new DecimalFormat("###.###");
        String query = " select kp.key_person_name,date_format(a.coming_time,'%r') as coming_time, "
                + " date_format(a.going_time,'%r') as going_time,a.latitude,a.longitude,date(coming_time) as date, "
                + " 111.045 * DEGREES(ACOS(COS(RADIANS(28.6126243)) "
                + " * COS(RADIANS(a.latitude)) "
                + " * COS(RADIANS(a.longitude) - RADIANS(77.3776654)) "
                + " + SIN(RADIANS(28.6126243)) "
                + " * SIN(RADIANS(a.latitude)))) "
                + " AS distance_in_km, kp.mobile_no1 as mobile_no "
                + " from attendance a, key_person kp "
                + " where a.active='y' and kp.active='y' and a.key_person_id=kp.key_person_id ";
        if (date.equals("")) {
            query += " and date(a.created_at)=curdate() ";
        } else {
            query += " and date(a.created_at)='" + date + "' ";
        }
        if(!key_person.equals("")){
         query+=" and kp.key_person_name='"+key_person+"' ";   
        }
        try {
            System.err.println("query -"+query);
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            while (rset.next()) {
                AttendanceBean bean = new AttendanceBean();
                bean.setKp_name(rset.getString(1));
                bean.setComing_time(rset.getString(2));
                bean.setGoing_time(rset.getString(3));
                bean.setLatitude(rset.getString(4));
                bean.setLongitude(rset.getString(5));
                bean.setDate(rset.getString(6));
                bean.setContact(rset.getString(8));

                double obj = Double.valueOf(rset.getString(7));
                String vall = df.format(obj);
                bean.setDistance_between(vall);

                list.add(bean);
            }
        } catch (Exception e) {
            System.out.println("com.apogee.admin.AttendanceModel.showData() -" + e);
        }
        return list;
    }

    public List<String> getKeyPerson(String q) {
        List<String> list = new ArrayList<String>();
        String query = " select kp.key_person_name from key_person kp, org_office oo where kp.active='y' and oo.active='y' "
                + " and oo.org_office_id=kp.org_office_id and oo.org_office_name='apogee LLP' ";
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            int count = 0;
            q = q.trim();
            while (rset.next()) {    // move cursor from BOR to valid record.
                String name = (rset.getString(1));
                if (name.toUpperCase().startsWith(q.toUpperCase())) {
                    list.add(name);
                    count++;
                }
            }
            if (count == 0) {
                list.add("No such key person exists.......");
            }
        } catch (Exception e) {
            System.out.println("com.apogee.admin.AttendanceModel.getKeyPerson()-" + e);
        }
        return list;
    }

    public String getMessage() {
        return message;
    }

    public String getMsgBgColor() {
        return msgBgColor;
    }

    public void closeConnection() {
        try {
            connection.close();
        } catch (Exception e) {
            System.out.println("com.apogee.admin.AttendanceModel.closeConnection() -" + e);
        }
    }

    /**
     * @return the driver
     */
    public String getDriver() {
        return driver;
    }

    /**
     * @param driver the driver to set
     */
    public void setDriver(String driver) {
        this.driver = driver;
    }

    /**
     * @return the url
     */
    public String getUrl() {
        return url;
    }

    /**
     * @param url the url to set
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * @return the user
     */
    public String getUser() {
        return user;
    }

    /**
     * @param user the user to set
     */
    public void setUser(String user) {
        this.user = user;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

   public void setConnection2() {
        try {
            Class.forName(driver);
            connection2 = DriverManager.getConnection(url, user, password);
        } catch (Exception e) {
            System.out.println("CityModel setConnection error: " + e);
        }
    }

    public Connection getConnection2() {
        return connection2;
    }
}