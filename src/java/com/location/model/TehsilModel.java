/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.location.model;

import com.location.bean.TehsilBean;
import java.io.ByteArrayOutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.JasperRunManager;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRXlsExporter;

/**
 *
 * @author DELL
 */
public class TehsilModel {

    private static Connection connection;
    private String driver, url, user, password;
    private String message, messageBGColor;
//    private KrutiDevToUnicodeConverter krutiToUnicode = new KrutiDevToUnicodeConverter();
//    private UnicodeToKrutiDevConverter unicodeToKruti = new UnicodeToKrutiDevConverter();

    public byte[] generateMapReport(String jrxmlFilePath, List<TehsilBean> listAll) {
        byte[] reportInbytes = null;
        try {

            JRBeanCollectionDataSource beanColDataSource = new JRBeanCollectionDataSource(listAll);
            JasperReport compiledReport = JasperCompileManager.compileReport(jrxmlFilePath);
            reportInbytes = JasperRunManager.runReportToPdf(compiledReport, null, beanColDataSource);
        } catch (Exception e) {
            System.out.println("Error: in TehsilModel generateMapReport() JRException: " + e);
        }
        return reportInbytes;
    }

    public ByteArrayOutputStream generateTehsilXlsRecordList(String jrxmlFilePath, List list) {
        ByteArrayOutputStream bytArray = new ByteArrayOutputStream();
        //  HashMap mymap = new HashMap();
        try {
            JRBeanCollectionDataSource jrBean = new JRBeanCollectionDataSource(list);
            JasperReport compiledReport = JasperCompileManager.compileReport(jrxmlFilePath);
            JasperPrint jasperPrint = JasperFillManager.fillReport(compiledReport, null, jrBean);
            JRXlsExporter exporter = new JRXlsExporter();
            exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
            exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, bytArray);
            exporter.exportReport();
        } catch (Exception e) {
            System.out.println("tehsilStatusModel generatReport() JRException: " + e);
        }
        return bytArray;
    }

    public List<TehsilBean> showAllData(String tehsilName) {
//        tehsilName = krutiToUnicode.convert_to_unicode(tehsilName);
        tehsilName = tehsilName;
        ArrayList<TehsilBean> list = new ArrayList<TehsilBean>();
        String query = " select tehsil_name,tehsil_description from tehsil where "
                + " if('" + tehsilName + "'='',tehsil_name LIKE '%%',tehsil_name='" + tehsilName + "') ";

        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                TehsilBean tehsilBean = new TehsilBean();
//                tehsilBean.setTehsilName(unicodeToKruti.Convert_to_Kritidev_010(rset.getString("tehsil_name")));
//               tehsilBean.setTehsilDescription(unicodeToKruti.Convert_to_Kritidev_010(rset.getString("tehsil_description")));
                tehsilBean.setTehsilName(rset.getString("tehsil_name"));
                tehsilBean.setTehsilDescription(rset.getString("tehsil_description"));

                list.add(tehsilBean);
            }
        } catch (Exception e) {
            System.out.println("Error in ShowAllData --- tehsilModel : " + e);
        }

        return list;
    }

    public static int getDistrictId(String district_name) {
        int division_id = 0;
        String query = " select district_id from district where district_name= '" + district_name + "' ";
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            if (rset.next()) {
                division_id = rset.getInt("district_id");

            }
        } catch (Exception ex) {
            System.out.println("ERROR: " + ex);
        }
        return division_id;
    }

    public static int getTehsilId(String district_name) {
        int division_id = 0;
        String query = " select tehsil_id from tehsil where tehsil_name= '" + district_name + "' ";
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            if (rset.next()) {
                division_id = rset.getInt("tehsil_id");

            }
        } catch (Exception ex) {
            System.out.println("ERROR: " + ex);
        }
        return division_id;
    }

    public List<String> getDivision(String q) {
        List<String> list = new ArrayList<String>();
        String query = " SELECT division_name FROM division GROUP BY division_name ORDER BY division_name ";
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            int count = 0;
            q = q.trim();
            while (rset.next()) {    // move cursor from BOR to valid record.
//                String division_type = unicodeToKruti.Convert_to_Kritidev_010(rset.getString("division_name"));
                String division_type = rset.getString("division_name");
                if (division_type.toUpperCase().startsWith(q.toUpperCase())) {
                    list.add(division_type);
                    count++;
                }
            }
            if (count == 0) {
                list.add("No such tehsil exists.......");
            }
        } catch (Exception e) {
            System.out.println("getTehsil ERROR inside TehsilModel - " + e);
        }
        return list;
    }

    public List<String> getTehsil(String q) {
        List<String> list = new ArrayList<String>();
        String query = " SELECT tehsil_id, tehsil_name FROM tehsil GROUP BY tehsil_name ORDER BY tehsil_name ";
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            int count = 0;

            while (rset.next()) {    // move cursor from BOR to valid record.
//                String tehsil_type = unicodeToKruti.Convert_to_Kritidev_010(rset.getString("tehsil_name"));
                String tehsil_type = rset.getString("tehsil_name");

                if (tehsil_type.toUpperCase().startsWith(q.toUpperCase())) {
                    list.add(tehsil_type);
                    count++;
                }

            }
            if (count == 0) {
                list.add("No such tehsil exists.......");
            }
        } catch (Exception e) {
            System.out.println("getTehsil ERROR inside TehsilModel - " + e);
        }
        return list;
    }

    public List<String> getDistrict(String q) {
        if (q == null) {
            q = "";
        }
        List<String> list = new ArrayList<String>();
        String query = " SELECT district_id, district_name FROM district GROUP BY district_name ORDER BY district_name ";
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            int count = 0;
            q = q.trim();
            while (rset.next()) {
                String district_name = (rset.getString("district_name"));
                if (district_name.toUpperCase().startsWith(q.toUpperCase())) {
                    list.add(district_name);
                    count++;
                }
            }
            if (count == 0) {
                list.add("No such district_name  exists.");
            }
        } catch (Exception e) {
            System.out.println("getDistrict ERROR inside DistrictModel - " + e);
        }
        return list;
    }

    public List<String> getTehsilName(String q, String distName) {
        List<String> list = new ArrayList<String>();

        String query = " select tehsil_name FROM tehsil where tehsil.district_id=(select district.district_id from district where district_name='" + distName + "') GROUP BY tehsil_name ORDER BY tehsil_name ";
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            int count = 0;
            q = q.trim();
            while (rset.next()) {    // move cursor from BOR to valid record.
                String tehsil_type = rset.getString("tehsil_name");
                if (tehsil_type.toUpperCase().startsWith(q.toUpperCase())) {
                    list.add(tehsil_type);
                    count++;
                }
            }
            if (count == 0) {
                list.add("No such tehsil exists.......");
            }
        } catch (Exception e) {
            System.out.println("getTehsilName ERROR inside TehsilModel - " + e);
        }
        return list;
    }

    public List<String> getDistrict(String q, String diviName) {
        List<String> list = new ArrayList<String>();
//        diviName = krutiToUnicode.convert_to_unicode(diviName);
        diviName = diviName;
        String query = " SELECT district_name FROM district where district.division_id=(select division.division_id from division where division.division_name='" + diviName + "') GROUP BY district_name ORDER BY district_name ";
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            int count = 0;
            q = q.trim();
            while (rset.next()) {    // move cursor from BOR to valid record.
//                String district_type = unicodeToKruti.Convert_to_Kritidev_010(rset.getString("district_name"));
                String district_type = rset.getString("district_name");
                if (district_type.toUpperCase().startsWith(q.toUpperCase())) {
                    list.add(district_type);
                    count++;
                }
            }
            if (count == 0) {
                list.add("No such District exists.......");
            }
        } catch (Exception e) {
            System.out.println("getDistrict ERROR inside TehsilModel - " + e);
        }
        return list;
    }

    public void deleteRecord(int tehsilId) {
        PreparedStatement presta = null;
        try {
            presta = connection.prepareStatement(" delete from tehsil where tehsil_id=? ");
            presta.setInt(1, tehsilId);
            int i = presta.executeUpdate();
            if (i > 0) {
                message = "Record deleted successfully......";
                messageBGColor = "yellow";
            } else {
                message = "Record not deleted successfully......";
                messageBGColor = "red";
            }
        } catch (Exception e) {
            System.out.println("Error in deleting recordl ---- TehsilModel : " + e);
        }
    }

    public String getTehsilNameFromId(int id) {
        String name = null;

        String query = " SELECT tehsil_name FROM tehsil where tehsil_id=? and active='Y' ";
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setInt(1, id);
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                name = rset.getString(1);
            }

        } catch (SQLException ex) {
            Logger.getLogger(TehsilModel.class.getName()).log(Level.SEVERE, null, ex);
        }

        return name;
    }

    public ArrayList<TehsilBean> getAllRecords(int lowerLimit, int noOfRowsToDisplay, String searchTehsil) {
//        searchTehsil = krutiToUnicode.convert_to_unicode(searchTehsil);
        searchTehsil = searchTehsil;
        ArrayList<TehsilBean> list = new ArrayList<TehsilBean>();
        /*
        String query = " SELECT city_id, city_name, city_description,district_id,division_id "
                + " FROM city "
                + " WHERE IF('" + searchCity + "' = '', city_name LIKE '%%',city_name =?) "
                + " ORDER BY city_name "
                + " LIMIT " + lowerLimit + ", " + noOfRowsToDisplay;
         
         */
        String query = " SELECT tehsil_id,tehsil_name,tehsil_description,tehsil_id,district_name FROM tehsil ,district "
                + " where tehsil.district_id=district.district_id and tehsil.active='Y' and district.active='Y' "
                + " AND IF('" + searchTehsil + "'='',tehsil_name LIKE '%%',tehsil_name=?) "
                + " order by tehsil_name limit " + lowerLimit + "," + noOfRowsToDisplay;

        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, searchTehsil);
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                TehsilBean tehsilBean = new TehsilBean();
                tehsilBean.setTehsilId(rset.getInt(1));
//                tehsilBean.setTehsilName(unicodeToKruti.Convert_to_Kritidev_010(rset.getString(2)));
                tehsilBean.setTehsilName(rset.getString(2));

                tehsilBean.setTehsilDescription(rset.getString(3));
                String tehsil = getTehsilNameFromId(rset.getInt(4));
                tehsilBean.setDistrictName(rset.getString(5));
                list.add(tehsilBean);
            }
        } catch (Exception e) {
            System.out.println("Error in getAllRecrod -- TehsilModel : " + e);
        }
        return list;
    }

    public int getTotalRowsInTable(String searchTehsil) {
//        searchTehsil = krutiToUnicode.convert_to_unicode(searchTehsil);
        searchTehsil = searchTehsil;
        String query = " SELECT Count(*) "
                + " FROM tehsil "
                + " WHERE IF('" + searchTehsil + "' = '', tehsil_name LIKE '%%',tehsil_name =?) "
                + " ORDER BY tehsil_name ";
        int noOfRows = 0;
        try {
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setString(1, searchTehsil);
            ResultSet rs = stmt.executeQuery();
            rs.next();
            noOfRows = Integer.parseInt(rs.getString(1));
        } catch (Exception e) {
            System.out.println("Error inside getNoOfRows TehsilModel" + e);
        }
        System.out.println("No of Rows in Table for search is****....." + noOfRows);
        return noOfRows;
    }

    public int getDistrictIdFromName(String name) {
        int id = 0;

        String query = " SELECT district_id FROM district where district_name=? and active='Y' ";
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, name);
            ResultSet rset = pstmt.executeQuery();
            rset.next();
            id = rset.getInt(1);

        } catch (SQLException ex) {
            System.out.println("Exception:" + ex);
        }

        return id;
    }

    public void insertRecord(TehsilBean bean) {
        int rowAffected = 0;
        try {
            String query = " insert into tehsil(tehsil_name,tehsil_description,district_id,revision_no,active) values(?,?,?,?,?) ";
            PreparedStatement ps = (PreparedStatement) connection.prepareStatement(query);
            ps.setString(1, bean.getTehsilName());
            ps.setString(2, bean.getTehsilDescription());
            ps.setInt(3, bean.getDistrictId());
            ps.setInt(4, bean.getRevision_no());
            ps.setString(5, "Y");

            // int id=getDistrictIdFromName(bean.getDistrictName());
            rowAffected = ps.executeUpdate();
        } catch (Exception e) {
            System.out.println("Error in insertRecord in TehsilModel : " + e);
        }
        if (rowAffected > 0) {
            message = rowAffected + " Record inserted successfully";

        }
    }

    public int updateRecord(TehsilBean Bean, int tehsil_id) {
        int revision = TehsilModel.getRevisionno(Bean, tehsil_id);
        int updateRowsAffected = 0;
        boolean status = false;
        String query1 = " SELECT max(revision_no) revision_no FROM tehsil WHERE tehsil_id = " + tehsil_id + "  && active=? ";
        String query2 = " UPDATE tehsil SET active=? WHERE tehsil_id=? and revision_no=? ";
        String query3 = " INSERT INTO tehsil(tehsil_id,tehsil_name,tehsil_description,district_id,revision_no,active) "
                + " VALUES(?,?,?,?,?,?) ";
        int rowsAffected = 0;
        try {
            PreparedStatement pstmt = connection.prepareStatement(query1);
//           pstmt.setInt(1,organisation_type_id);
            pstmt.setString(1, "Y");

            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                PreparedStatement pstm = connection.prepareStatement(query2);

                pstm.setString(1, "n");

                pstm.setInt(2, tehsil_id);
                pstm.setInt(3, revision);
                updateRowsAffected = pstm.executeUpdate();
                if (updateRowsAffected >= 1) {
                    revision = rs.getInt("revision_no") + 1;
                    PreparedStatement psmt = (PreparedStatement) connection.prepareStatement(query3);
                    psmt.setInt(1, (tehsil_id));
                    psmt.setString(2, Bean.getTehsilName());
                    psmt.setString(3, Bean.getTehsilDescription());
                    psmt.setInt(4, (Bean.getDistrictId()));
                    psmt.setInt(5, revision);

                    psmt.setString(6, "Y");

                    rowsAffected = psmt.executeUpdate();
                    if (rowsAffected > 0) {
                        status = true;
                    } else {
                        status = false;
                    }
                }

            }
        } catch (Exception e) {
            System.out.println("Error:OrganisationSubTypeModel-" + e);
        }
        if (rowsAffected > 0) {
            message = "Record updated successfully.";

        } else {
            message = "Cannot update the record, some error.";
        }
        return rowsAffected;
    }

    public static int getRevisionno(TehsilBean bean, int tehsil_id) {
        int revision = 0;
        try {

            String query = " SELECT max(revision_no) as revision_no FROM tehsil WHERE tehsil_id =" + tehsil_id + "  && active='Y' ";

            PreparedStatement pstmt = (PreparedStatement) connection.prepareStatement(query);

            ResultSet rset = pstmt.executeQuery();

            while (rset.next()) {
                revision = rset.getInt("revision_no");

            }
        } catch (Exception e) {
        }
        return revision;
    }

    public void closeConnection() {
        try {
            connection.close();
        } catch (Exception e) {
            System.out.println("TehsilModel closeConnection: " + e);
        }
    }

    public void setConnection(Connection con) {
        try {

            connection = con;
        } catch (Exception e) {
            System.out.println("TehsilModel setConnection() Error: " + e);
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
