/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.organization.model;

import com.organization.tableClasses.OrgOfficeType;
import java.io.ByteArrayOutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
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
 * @author komal
 */
public class OrgOfficeTypeModel {

    private static Connection connection;

    private String message;
    private String msgBgColor;
    private final String COLOR_OK = "#a2a220";
    private final String COLOR_ERROR = "red";

    public void setConnection(Connection con) {
        try {

            connection = con;
        } catch (Exception e) {
            System.out.println("OrgOfficeTypeModel setConnection() Error: " + e);
        }
    }

    public List<OrgOfficeType> showAllData(String searchOrgOfficeType, String active) {
        List<OrgOfficeType> list = new ArrayList<OrgOfficeType>();

        // Use DESC or ASC for descending or ascending order respectively of fetched data.
//        String query = " SELECT office_type_id, office_type,  description FROM org_office_type where  "
//                + "  IF('" + active + "'='' ,active LIKE '%%',active = ?)  AND "
//                + "  IF('" + searchOrgOfficeType + "'='' ,office_type LIKE '%%',office_type = ?)  ";
        String query = " SELECT office_type_id, office_type,  description FROM org_office_type where active='Y' ";

        if (!searchOrgOfficeType.equals("") && searchOrgOfficeType != null) {
            query += " and office_type='" + searchOrgOfficeType + "' ";
        }

        try {
            searchOrgOfficeType = (searchOrgOfficeType);
            PreparedStatement pstmt = connection.prepareStatement(query);
//            pstmt.setString(1, active);
//            pstmt.setString(2, searchOrgOfficeType);
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                OrgOfficeType orgOfficeType = new OrgOfficeType();
                orgOfficeType.setOffice_type_id(rset.getInt("office_type_id"));
                orgOfficeType.setOffice_type((rset.getString("office_type")));
                orgOfficeType.setDescription((rset.getString("description")));
                list.add(orgOfficeType);
            }
        } catch (Exception e) {
            System.out.println("OrgOfficeTypeModel showAllData() Error: " + e);
        }
        return list;
    }

    public List<OrgOfficeType> showData(String searchOrgOfficeType, String active) {
        List<OrgOfficeType> list = new ArrayList<OrgOfficeType>();

        // Use DESC or ASC for descending or ascending order respectively of fetched data.
        String query = " SELECT office_type_id, office_type,  description FROM org_office_type where active='Y' ";

        if (!searchOrgOfficeType.equals("") && searchOrgOfficeType != null) {
            query += " and office_type='" + searchOrgOfficeType + "' ";
        }

        try {
            searchOrgOfficeType = (searchOrgOfficeType);
            PreparedStatement pstmt = connection.prepareStatement(query);
//            pstmt.setString(1, active);
//            pstmt.setString(2, searchOrgOfficeType);
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                OrgOfficeType orgOfficeType = new OrgOfficeType();
                orgOfficeType.setOffice_type_id(rset.getInt("office_type_id"));
                orgOfficeType.setOffice_type((rset.getString("office_type")));
                orgOfficeType.setDescription((rset.getString("description")));
                list.add(orgOfficeType);
            }
        } catch (Exception e) {
            System.out.println("OrgOfficeTypeModel showData() Error: " + e);
        }
        return list;
    }

    public int insertRecord(OrgOfficeType org_office_type) {
        String query = "INSERT INTO org_office_type(office_type, description,revision_no,active,remark) VALUES(?,?,?,?,?) ";
        int rowsAffected = 0;
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, (org_office_type.getOffice_type()));
            pstmt.setString(2, (org_office_type.getDescription()));
            pstmt.setInt(3, org_office_type.getRevision_no());
            pstmt.setString(4, "Y");
            pstmt.setString(5, "OK");

            rowsAffected = pstmt.executeUpdate();
        } catch (Exception e) {
            System.out.println("OrgOfficeTypeModel insertRecord() Error: " + e);
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

    public int updateRecord(OrgOfficeType org_office_type, int office_type_id) {
        int revision = OrgOfficeTypeModel.getRevisionno(org_office_type, office_type_id);
        int updateRowsAffected = 0;
        boolean status = false;
        String query1 = "SELECT max(revision_no) revision_no FROM org_office_type WHERE office_type_id = " + office_type_id + "  && active=? ";
        String query2 = "UPDATE org_office_type SET active =? WHERE office_type_id =? and revision_no=? ";
        String query3 = "INSERT INTO org_office_type(office_type_id,office_type,description,revision_no,active,remark) VALUES(?,?,?,?,?,?)";
        int rowsAffected = 0;
        try {
            PreparedStatement pstmt = connection.prepareStatement(query1);
//           pstmt.setInt(1,organisation_type_id);
            pstmt.setString(1, "Y");
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                PreparedStatement pstm = connection.prepareStatement(query2);

                pstm.setString(1, "n");

                pstm.setInt(2, office_type_id);
                pstm.setInt(3, revision);
                updateRowsAffected = pstm.executeUpdate();
                if (updateRowsAffected >= 1) {
                    revision = rs.getInt("revision_no") + 1;
                    PreparedStatement psmt = (PreparedStatement) connection.prepareStatement(query3);
                    psmt.setInt(1, (office_type_id));
                    psmt.setString(2, org_office_type.getOffice_type());
                    psmt.setString(3, org_office_type.getDescription());
                    psmt.setInt(4, revision);
                    psmt.setString(5, "Y");
                    psmt.setString(6, "OK");
                    rowsAffected = psmt.executeUpdate();
                    if (rowsAffected > 0) {
                        status = true;
                    } else {
                        status = false;
                    }
                }

            }
        } catch (Exception e) {
            System.out.println("OrgOfficeTypeModel updateRecord() Error: " + e);
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

    public static int getRevisionno(OrgOfficeType bean, int office_type_id) {
        int revision = 0;
        try {

            String query = " SELECT max(revision_no) as revision_no FROM org_office_type WHERE office_type_id =" + office_type_id + "  && active='Y';";

            PreparedStatement pstmt = (PreparedStatement) connection.prepareStatement(query);

            ResultSet rset = pstmt.executeQuery();

            while (rset.next()) {
                revision = rset.getInt("revision_no");

            }
        } catch (Exception e) {
            System.out.println("OrgOfficeTypeModel getRevisionno() Error: " + e);

        }
        return revision;
    }

    public int deleteRecord(int office_type_id) {
        String query = "DELETE FROM org_office_type WHERE active='Y' and office_type_id = " + office_type_id;
        int rowsAffected = 0;
        try {
            rowsAffected = connection.prepareStatement(query).executeUpdate();
        } catch (Exception e) {
            System.out.println("OrgOfficeTypeModel deleteRecord() Error: " + e);
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

    public List<String> getOrgOfficeType(String q) {
        List<String> list = new ArrayList<String>();
        String query = " SELECT office_type_id, office_type FROM org_office_type o where o.active='Y' ORDER BY office_type ";
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            int count = 0;
            q = q.trim();
            while (rset.next()) {    // move cursor from BOR to valid record.
                String office_type = (rset.getString("office_type"));
                if (office_type.toUpperCase().startsWith(q.toUpperCase())) {
                    list.add(office_type);
                    count++;
                }
            }
            if (count == 0) {
                list.add("No such Organisation Office Type exists.");
            }
        } catch (Exception e) {
            System.out.println("OrgOfficeTypeModel getOrgOfficeType ERROR - " + e);
        }
        return list;
    }

    public byte[] orgOfficeReport(String jrxmlFilePath, List listAll) {
        byte[] reportInbytes = null;
        try {

            JRBeanCollectionDataSource beanColDataSource = new JRBeanCollectionDataSource(listAll);
            JasperReport compiledReport = JasperCompileManager.compileReport(jrxmlFilePath);
            reportInbytes = JasperRunManager.runReportToPdf(compiledReport, null, beanColDataSource);
        } catch (Exception e) {
            System.out.println("Error: in OrgOfficeTypeModel orgOfficeReport() JRException: " + e);
        }
        return reportInbytes;
    }

    public ByteArrayOutputStream orgOfficeXlsRecordList(String jrxmlFilePath, List list) {
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
            System.out.println("OrgOfficeTypeModel orgOfficeXlsRecordList() JRException: " + e);
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
            System.out.println("OrgOfficeTypeModel closeConnection() Error: " + e);
        }
    }
}
