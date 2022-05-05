package com.location.model;

import com.location.bean.WardTypeBean;
import com.util.KrutiDevToUnicodeConverter;
import com.util.UnicodeToKrutiDevConverter;
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
 * @author Administrator
 */
public class WardModel {

    static private Connection connection;
    private String driverClass;
    private String connectionString;
    private String db_username;
    private String db_password;
    static private String message;
    static private String msgBgColor;
    private final String COLOR_OK = "yellow";
    private final String COLOR_ERROR = "red";
    UnicodeToKrutiDevConverter uk = new UnicodeToKrutiDevConverter();
    KrutiDevToUnicodeConverter ku = new KrutiDevToUnicodeConverter();

    public void setConnection(Connection con) {
        try {

            connection = con;
        } catch (Exception e) {
            System.out.println("CityModel setConnection() Error: " + e);
        }
    }

    public byte[] generateMapReport(String jrxmlFilePath, List<WardTypeBean> listAll) {
        byte[] reportInbytes = null;
        Connection c;
        //     HashMap mymap = new HashMap();
        try {

            JRBeanCollectionDataSource beanColDataSource = new JRBeanCollectionDataSource(listAll);
            JasperReport compiledReport = JasperCompileManager.compileReport(jrxmlFilePath);
            reportInbytes = JasperRunManager.runReportToPdf(compiledReport, null, beanColDataSource);
        } catch (Exception e) {
            System.out.println("Error: in WardModel generateMapReport() JRException: " + e);
        }
        return reportInbytes;
    }

    public ByteArrayOutputStream generateZoneXlsRecordList(String jrxmlFilePath, List list) {
        ByteArrayOutputStream bytArray = new ByteArrayOutputStream();
        try {
            JRBeanCollectionDataSource jrBean = new JRBeanCollectionDataSource(list);
            JasperReport compiledReport = JasperCompileManager.compileReport(jrxmlFilePath);
            JasperPrint jasperPrint = JasperFillManager.fillReport(compiledReport, null, jrBean);
            JRXlsExporter exporter = new JRXlsExporter();
            exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
            exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, bytArray);
            exporter.exportReport();
        } catch (Exception e) {
            System.out.println("CityStatusModel generatReport() JRException: " + e);
        }
        return bytArray;
    }

    public int insertRecord(WardTypeBean wardTypeBean) {
        String query = " INSERT INTO ward(ward_name,ward_no,remark,zone_id,active,revision_no) VALUES (?,?,?,?,?,?) ";
        int rowsAffected = 0;
        try {
            java.sql.PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, wardTypeBean.getWard_name());
            pstmt.setString(2, wardTypeBean.getWard_no());
            pstmt.setString(3, wardTypeBean.getRemark());
            pstmt.setInt(6, wardTypeBean.getWard_rev_no());
            pstmt.setString(5, "Y");
            int zone_id = getZoneId(wardTypeBean.getZone_m());
            pstmt.setInt(4, zone_id);
            rowsAffected = pstmt.executeUpdate();
        } catch (Exception e) {
            System.out.println("Error while inserting record...." + e);
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

    public int updateRecord(WardTypeBean bean, int ward_id) {
        int revision = WardModel.getRevisionno(bean, ward_id);
        int updateRowsAffected = 0;
        boolean status = false;
        String query1 = " SELECT max(revision_no) revision_no FROM ward WHERE ward_id = " + ward_id + "  && active=? ";
        String query2 = " UPDATE ward SET active=? WHERE ward_id=? and revision_no=? ";
        String query3 = " INSERT INTO ward(ward_id,ward_name,ward_no,remark,zone_id,active,revision_no) VALUES( ?,?, ?, ?,?,?,?) ";
        int rowsAffected = 0;
        try {
            PreparedStatement pstmt = connection.prepareStatement(query1);
//           pstmt.setInt(1,organisation_type_id);
            pstmt.setString(1, "Y");

            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                PreparedStatement pstm = connection.prepareStatement(query2);

                pstm.setString(1, "n");

                pstm.setInt(2, ward_id);
                pstm.setInt(3, revision);
                updateRowsAffected = pstm.executeUpdate();
                if (updateRowsAffected >= 1) {
                    revision = rs.getInt("revision_no") + 1;
                    PreparedStatement psmt = (PreparedStatement) connection.prepareStatement(query3);
                    psmt.setInt(1, (ward_id));
                    psmt.setString(2, bean.getWard_name());
                    psmt.setString(3, bean.getWard_no());
                    psmt.setString(4, bean.getRemark());
                    psmt.setInt(7, revision);
                    psmt.setString(6, "Y");
                    int zone_id = getZoneId(bean.getZone_m());
                    psmt.setInt(5, zone_id);

                    rowsAffected = psmt.executeUpdate();
                    if (rowsAffected > 0) {
                        status = true;
                    } else {
                        status = false;
                    }
                }

            }
        } catch (Exception e) {
            System.out.println("designationModel Error: " + e);
        }
        if (rowsAffected > 0) {
            message = "Record updated successfully.";

        } else {
            message = "Cannot update the record, some error.";

        }
        return rowsAffected;
    }

    public static int getRevisionno(WardTypeBean bean, int zone_id) {
        int revision = 0;
        try {

            String query = " SELECT max(revision_no) as revision_no FROM ward WHERE ward_id =" + zone_id + "  && active='Y' ";

            PreparedStatement pstmt = (PreparedStatement) connection.prepareStatement(query);

            ResultSet rset = pstmt.executeQuery();

            while (rset.next()) {
                revision = rset.getInt("revision_no");

            }
        } catch (Exception e) {
        }
        return revision;
    }

    public List<String> getWardType(String q) {
        List<String> list = new ArrayList<String>();
        String query = " SELECT ward_id, ward_name FROM ward GROUP BY ward_name ORDER BY ward_name ";
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();

            int count = 0;
            q = q.trim();
            while (rset.next()) {    // move cursor from BOR to valid record.
//                String district_type = unicodeToKruti.Convert_to_Kritidev_010(rset.getString("district_name"));
                String ward_name = uk.Convert_to_Kritidev_010(rset.getString("ward_name"));
                if (ward_name.toUpperCase().startsWith(q.toUpperCase())) {
                    list.add(ward_name);
                    count++;
                }
            }
            if (count == 0) {
                list.add("No such ward_name exists.......");
            }
        } catch (Exception e) {
            System.out.println("getWardType ERROR inside WardModel - " + e);
        }
        return list;
    }

    static public int getZoneId(String zone) {
        zone = zone;
        String query = " select zone_id from zone where zone_name='" + zone + "' ";
        int zone_id = 0;
        try {
            PreparedStatement stmt = connection.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                zone_id = rs.getInt("zone_id");
            }
        } catch (Exception e) {
            System.out.println("Error inside getCircleId division type model" + e);
        }

        return zone_id;
    }

    public List<String> getZoneName(String q) {
        List<String> list = new ArrayList<String>();
        String query = " SELECT zone_name FROM zone GROUP BY zone_name ORDER BY zone_name ";
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            int count = 0;
            q = q.trim();
            while (rset.next()) {    // move cursor from BOR to valid record.
//                String district_type = unicodeToKruti.Convert_to_Kritidev_010(rset.getString("district_name"));
                String zone_name = rset.getString("zone_name");
                if (zone_name.toUpperCase().startsWith(q.toUpperCase())) {
                    list.add(zone_name);
                    count++;
                }
            }
            if (count == 0) {
                list.add("No such zone_name exists.......");
            }
        } catch (Exception e) {
            System.out.println("getZoneName ERROR inside WardModel - " + e);
        }
        return list;
    }

    public List<String> getWardNo(String q) {
        List<String> list = new ArrayList<String>();
        String query = " SELECT ward_no FROM ward GROUP BY ward_no ORDER BY ward_no ";
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            int count = 0;

            while (rset.next()) {    // move cursor from BOR to valid record.
                String zone_name_m = (rset.getString("ward_no"));

                list.add(zone_name_m);
                count++;

            }
            if (count == 0) {
                list.add("No such Fuse exists.");
            }
        } catch (Exception e) {
            System.out.println("getZoneName ERROR inside WardModel - " + e);
        }
        return list;
    }

    public int getNoOfRows(String searchWardType, String zone) {
        searchWardType = ku.convert_to_unicode(searchWardType);
        zone = ku.convert_to_unicode(zone);
        String query = " SELECT Count(*)  FROM ward as w,zone as z "
                + "  where w.zone_id=z.zone_id "
                + " and IF('" + searchWardType + "'='',w.ward_name LIKE '%%',w.ward_name=?) "
                + " AND IF('" + zone + "'='',z.zone_name LIKE '%%',z.zone_name=?) ";
        int noOfRows = 0;
        try {
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setString(1, searchWardType);
            stmt.setString(2, zone);
            ResultSet rs = stmt.executeQuery();
            rs.next();
            noOfRows = Integer.parseInt(rs.getString(1));
        } catch (Exception e) {
            System.out.println("Error inside getNoOfRows fuse type model" + e);
        }
        System.out.println("No of Rows in Table for search is" + noOfRows);
        return noOfRows;
    }

    public List<WardTypeBean> showData(int lowerLimit, int noOfRowsToDisplay, String searchWardType, String searchWardno) //
    {
        List<WardTypeBean> list = new ArrayList<WardTypeBean>();
        searchWardType = (searchWardType);
        searchWardno = (searchWardno);
        String query = " select w.ward_id,w.ward_name,w.ward_no,w.remark,z.zone_name from ward as w,zone as z where w.zone_id=z.zone_id "
                + " and IF('" + searchWardType + "'='',w.ward_name LIKE '%%',w.ward_name=?) "
                + " AND IF('" + searchWardno + "'='',w.ward_no LIKE '%%',w.ward_no=?) order by ward_no"
                + " LIMIT " + lowerLimit + ", " + noOfRowsToDisplay;
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, searchWardType);
            pstmt.setString(2, searchWardno);
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                WardTypeBean wardType = new WardTypeBean();
                wardType.setWard_id(rset.getInt("ward_id"));
                wardType.setWard_name(rset.getString("ward_name"));
                wardType.setWard_no(rset.getString("ward_no"));
                wardType.setRemark(rset.getString("remark"));
                // wardType.setWard_rev_no(rset.getInt("ward_rev_no_m"));
                wardType.setZone_m(rset.getString("zone_name"));
                list.add(wardType);
            }
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
        return list;
    }

    public List<WardTypeBean> showAllData(String searchWardType, String zone) {
        List<WardTypeBean> listAll = new ArrayList<WardTypeBean>();

        String query = "  select z.zone_name,w.ward_name,w.ward_no,w.description from ward as w,zone as z where w.zone_id=z.zone_id "
                + " AND IF('" + searchWardType + "' = '',w.ward_name  LIKE '%%',w.ward_name =?) "
                + " AND IF('" + zone + "'='',z.zone_name LIKE '%%',z.zone_name=?) "
                + " order by zone_name,ward_no ";
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, searchWardType);
            pstmt.setString(2, zone);
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                WardTypeBean wardType = new WardTypeBean();

                wardType.setWard_name(uk.Convert_to_Kritidev_010(rset.getString("ward_name")));
                wardType.setZone_m(uk.Convert_to_Kritidev_010(rset.getString("zone_name")));
                wardType.setRemark(rset.getString("description"));
                wardType.setWard_no(rset.getString("ward_no"));

                listAll.add(wardType);
            }
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
        return listAll;
    }

    public int deleteRecord(int ward_id_m) {
        String query = " DELETE FROM ward WHERE ward_id=" + ward_id_m;
        int rowsAffected = 0;
        try {
            rowsAffected = connection.prepareStatement(query).executeUpdate();
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
        if (rowsAffected > 0) {
            message = "Record deleted successfully......";
            msgBgColor = COLOR_OK;
        } else {
            message = "Error Record cannot be deleted.....";
            msgBgColor = COLOR_ERROR;
        }
        return rowsAffected;
    }

    public void closeConnection() {
        try {
            connection.close();
        } catch (Exception e) {
            System.out.println("Error inside closeConnection TrafficTypeModel:" + e);
        }
    }

    public String getMessage() {
        return message;
    }

    public String getMsgBgColor() {
        return msgBgColor;
    }

    public Connection getConnection() {
        return connection;
    }

    public String getConnectionString() {
        return connectionString;
    }

    public void setConnectionString(String connectionString) {
        this.connectionString = connectionString;
    }

    public String getDb_password() {
        return db_password;
    }

    public void setDb_password(String db_password) {
        this.db_password = db_password;
    }

    public String getDb_username() {
        return db_username;
    }

    public void setDb_username(String db_username) {
        this.db_username = db_username;
    }

    public String getDriverClass() {
        return driverClass;
    }

    public void setDriverClass(String driverClass) {
        this.driverClass = driverClass;
    }
}
