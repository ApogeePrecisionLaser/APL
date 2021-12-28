/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.location.model;

import com.location.bean.CountryBean;
import java.io.ByteArrayOutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
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
public class CountryModel {

    private Connection connection;
    private String message;
    private String msgBgColor;
    private final String COLOR_OK = "lightyellow";
    private final String COLOR_ERROR = "red";
//    private KrutiDevToUnicodeConverter krutiToUnicode = new KrutiDevToUnicodeConverter();
//    private UnicodeToKrutiDevConverter unicodeToKruti = new UnicodeToKrutiDevConverter();

    public void setConnection(Connection con) {
        try {
            connection = con;
        } catch (Exception e) {
            System.out.println("CountryModel setConnection() Error: " + e);
        }
    }

    public int getNoOfRows(String searchCountryName, String searchCountryDiscription) {
        int noOfRows = 0;

        try {
            String query = "select count(*) from country "
                    + "WHERE if('" + searchCountryName + "' = '', country_name like '%%',  country_name = '" + searchCountryName + "')"
                    + " AND IF('" + searchCountryDiscription + "'='' ,country_discription LIKE '%%',country_discription LIKE '" + searchCountryDiscription + ".%' OR country_discription like ?)";

            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, searchCountryDiscription);
            ResultSet rset = pstmt.executeQuery();
            rset.next();    // move cursor from BOR to valid record.
            noOfRows = Integer.parseInt(rset.getString(1));
        } catch (Exception e) {
            System.out.println("countryModel Error: " + e);
        }
        return noOfRows;
    }

    public List<CountryBean> showAllData(String searchCountryName, String searchCountryDiscription) {
        List<CountryBean> list = new ArrayList<CountryBean>();
        String query = "SELECT * FROM country "
                + "WHERE if('" + searchCountryName + "' = '', country_name like '%%', country_name = '" + searchCountryName + "')"
                + " AND IF('" + searchCountryDiscription + "'='' ,country_discription LIKE '%%',country_discription LIKE '" + searchCountryDiscription + ".%' OR country_discription like ?)"
                + "ORDER BY country_name ";
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, searchCountryDiscription);
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                CountryBean media = new CountryBean();
                media.setCountry_id(rset.getInt("country_id"));
                media.setCountry_name(rset.getString("country_name"));

                media.setCountry_discription(rset.getString("country_discription"));
                list.add(media);
            }
        } catch (Exception e) {
            System.out.println("designationModel Error: " + e);
        }
        return list;

    }

    public List<CountryBean> showData(int lowerLimit, int noOfRowsToDisplay, String searchCountryName, String searchCountryDiscription) {
        List<CountryBean> list = new ArrayList<CountryBean>();
        searchCountryName = searchCountryName;
        // Use DESC or ASC for descending or ascending order respectively of fetched data.
        String query = "SELECT country_id,country_name,country_discription FROM country "
                + "WHERE if('" + searchCountryName + "' = '', country_name like '%%', country_name = '" + searchCountryName + "')"
                + " AND IF('" + searchCountryDiscription + "'='' ,country_discription LIKE '%%',country_discription LIKE '" + searchCountryDiscription + ".%' OR country_discription like ?)"
                + "ORDER BY country_discription "
                + "LIMIT " + lowerLimit + ", " + noOfRowsToDisplay;
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, searchCountryDiscription);
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                CountryBean media = new CountryBean();
                media.setCountry_id(rset.getInt("country_id"));
                media.setCountry_name(rset.getString("country_name"));

                media.setCountry_discription(rset.getString("country_discription"));
                list.add(media);
            }
        } catch (Exception e) {
            System.out.println("designationModel Error: " + e);
        }
        return list;
    }

    public int insertRecord(CountryBean country) {
        String query = "INSERT INTO country(country_name, country_discription,revision_no,active,remark ) VALUES(?,?,?,?,?)";
        int rowsAffected = 0;
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
//            pstmt.setString(1, krutiToUnicode.convert_to_unicode(designation.getDesignation()));
//            pstmt.setString(2, krutiToUnicode.convert_to_unicode(designation.getDescription()));
            pstmt.setString(1, country.getCountry_name());
            pstmt.setString(2, country.getCountry_discription());
            pstmt.setInt(3, country.getRevision_no());
            pstmt.setString(4, "Y");
            pstmt.setString(5, "");
            rowsAffected = pstmt.executeUpdate();
        } catch (Exception e) {
            System.out.println("designationModel Error: " + e);
        }
        if (rowsAffected > 0) {
            message = "Record saved successfully.";
            msgBgColor = COLOR_OK;
        } else {
            message = "Cannot save the record, some error.";
            msgBgColor = COLOR_ERROR;
        }
        return rowsAffected;
    }

    public int updateRecord(CountryBean country) {
        String query = "UPDATE country SET country_name=?, country_discription=?  WHERE country_id=?";
        int rowsAffected = 0;
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
//            pstmt.setString(1, krutiToUnicode.convert_to_unicode(designation.getDesignation()));
//            pstmt.setString(2, krutiToUnicode.convert_to_unicode(designation.getDescription()));
            pstmt.setString(1, country.getCountry_name());
            pstmt.setString(2, country.getCountry_discription());
            pstmt.setInt(3, country.getCountry_id());
            rowsAffected = pstmt.executeUpdate();
        } catch (Exception e) {
            System.out.println("countryModel Error: " + e);
        }
        if (rowsAffected > 0) {
            message = "Record updated successfully.";
            msgBgColor = COLOR_OK;
        } else {
            message = "Cannot update the record, some error.";
            msgBgColor = COLOR_ERROR;
        }
        return rowsAffected;
    }

    public int deleteRecord(int country_id) {
        String query = "DELETE FROM country WHERE country_id = " + country_id;
        int rowsAffected = 0;
        try {
            rowsAffected = connection.prepareStatement(query).executeUpdate();
        } catch (Exception e) {
            System.out.println("countryModel Error: " + e);
        }
        if (rowsAffected > 0) {
            message = "Record deleted successfully.";
            msgBgColor = COLOR_OK;
        } else {
            message = "Cannot delete the record, some error.";
            msgBgColor = COLOR_ERROR;
        }
        return rowsAffected;
    }

    public List<String> getCountryList(String q) {
        List<String> list = new ArrayList<String>();
        q = q.trim();
        int count = 0;
        String query = " SELECT dcountry_name FROM country ORDER BY country_name ";
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                String country_name = rset.getString("country_name");
                if (country_name.toUpperCase().startsWith(q.toUpperCase())) {
                    list.add(country_name);
                    count++;
                }
            }
            if (count == 0) {
                list.add("No such Country exists.");
            }
        } catch (Exception e) {
            System.out.println("Error:countryModel --getCountrylist-- " + e);
        }
        return list;
    }

    public List<String> getCountryDiscription(String q) {
        List<String> list = new ArrayList<String>();
        q = q.trim();
        int count = 0;
        String query = " SELECT country_discription FROM country ORDER BY country_discription ";
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                String country_discription = rset.getString("country_discription");
                if (country_discription.toUpperCase().startsWith(q.toUpperCase())) {
                    list.add(country_discription);
                    count++;
                }
            }
            if (count == 0) {
                list.add("No such Country exists.");
            }
        } catch (Exception e) {
            System.out.println("Error:countryModel --getCountrylist-- " + e);
        }
        return list;
    }

    public byte[] generateCountryReport(String jrxmlFilePath, List listAll) {
        byte[] reportInbytes = null;
        try {

            JRBeanCollectionDataSource beanColDataSource = new JRBeanCollectionDataSource(listAll);
            JasperReport compiledReport = JasperCompileManager.compileReport(jrxmlFilePath);
            reportInbytes = JasperRunManager.runReportToPdf(compiledReport, null, beanColDataSource);
        } catch (Exception e) {
            System.out.println("Error: in CityModel generateDesignationReport() JRException: " + e);
        }
        return reportInbytes;
    }

    public ByteArrayOutputStream generateDesignationXlsRecordList(String jrxmlFilePath, List list) {
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
            System.out.println("CountryStatusModel generateCountryXlsRecordList() JRException: " + e);
        }
        return bytArray;
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
            System.out.println("countryModel closeConnection() Error: " + e);
        }
    }

    public Connection getConnection() {
        return connection;
    }

}
