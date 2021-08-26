/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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

/**
 *
 * @author Vikrant
 */
public class AttendanceModel {

    private static Connection connection;

    private String message;
    private String msgBgColor;
    private final String COLOR_OK = "#a2a220";
    private final String COLOR_ERROR = "red";

    public void setConnection(Connection con) {
        try {
            connection = con;
        } catch (Exception e) {
            System.out.println("QtOohDefaultsModel setConnection() Error: " + e);
        }
    }

    public List<AttendanceBean> showData() {
        List<AttendanceBean> list = new ArrayList<AttendanceBean>();
        String query = " select kp.key_person_name,a.coming_time,a.going_time,a.latitude,a.longitude "
                + " from attendance a, key_person kp "
                + " where a.active='y' and kp.active='y' and a.key_person_id=kp.key_person_id ";
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            while (rset.next()) {
                AttendanceBean bean = new AttendanceBean();
                bean.setKp_name(rset.getString(1));
                bean.setComing_time(rset.getString(2));
                bean.setGoing_time(rset.getString(3));
                bean.setLatitude(rset.getString(4));
                bean.setLongitude(rset.getString(5));

                list.add(bean);
            }
        } catch (Exception e) {
            System.out.println("com.apogee.admin.AttendanceModel.showData() -" + e);
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
}
