package com.location.model;

import com.location.bean.DistrictBean;
import com.util.KrutiDevToUnicodeConverter;
import com.util.UnicodeToKrutiDevConverter;
import java.io.ByteArrayOutputStream;
import java.sql.*;
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

public class DistrictModel {

    private static Connection connection;
    private String driver, url, user, password;
    private String message, messageBGColor;
    private KrutiDevToUnicodeConverter krutiToUnicode = new KrutiDevToUnicodeConverter();
    private UnicodeToKrutiDevConverter unicodeToKruti = new UnicodeToKrutiDevConverter();

    public byte[] generateMapReport(String jrxmlFilePath, List<DistrictBean> listAll) {
        byte[] reportInbytes = null;
        try {

            JRBeanCollectionDataSource beanColDataSource = new JRBeanCollectionDataSource(listAll);
            JasperReport compiledReport = JasperCompileManager.compileReport(jrxmlFilePath);
            reportInbytes = JasperRunManager.runReportToPdf(compiledReport, null, beanColDataSource);
        } catch (Exception e) {
            System.out.println("Error: in CityModel generateMapReport() JRException: " + e);
        }
        return reportInbytes;
    }

    public ByteArrayOutputStream generateDistrictXlsRecordList(String jrxmlFilePath, List list) {
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
            System.out.println("DistrictStatusModel generatReport() JRException: " + e);
        }
        return bytArray;
    }

    public List<DistrictBean> showAllData(String districtName) {
        districtName = krutiToUnicode.convert_to_unicode(districtName);
        ArrayList<DistrictBean> list = new ArrayList<DistrictBean>();
        String query = " select district_name,district_description,division_name,state_name,ut_name from district,division,state where district.division_id=division.division_id and district.state_id=state.state_id and if('" + districtName + "'='',district_name LIKE '%%',district_name='" + districtName + "') ";

        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                DistrictBean districtBean = new DistrictBean();
                districtBean.setDistrictName(rset.getString("district_name"));
                districtBean.setDistrictDescription(rset.getString("district_description"));
                districtBean.setDivisionName(rset.getString("division_name"));
                districtBean.setStateName(rset.getString("state_name"));
                districtBean.setUtName(rset.getString("ut_name"));

                list.add(districtBean);
            }
        } catch (Exception e) {
            System.out.println("Error in ShowAllData --- DistrictModel : " + e);
        }

        return list;
    }

    public List<String> getDist(String q, String divName) {
        List<String> list = new ArrayList<String>();
        String query = " select district.district_name from district where district.division_id=(select division.division_id from division where division.division_name='" + divName + "') group by district_name order by district_name ";
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            int count = 0;
            q = q.trim();
            while (rset.next()) {    // move cursor from BOR to valid record.
                String district_type = unicodeToKruti.Convert_to_Kritidev_010(rset.getString("district_name"));
                if (district_type.toUpperCase().startsWith(q.toUpperCase())) {
                    list.add(district_type);
                    count++;
                }
            }
            if (count == 0) {
                list.add("No such District exists.......");
            }
        } catch (Exception e) {
            System.out.println("getDist ERROR inside DistrictModel - " + e);
        }
        return list;
    }

    public List<String> getState(String q) {
        List<String> list = new ArrayList<String>();
        String query = " SELECT state_name FROM state GROUP BY state_name ORDER BY state_name ";
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            int count = 0;
            q = q.trim();
            while (rset.next()) {    // move cursor from BOR to valid record.
//                String district_type = unicodeToKruti.Convert_to_Kritidev_010(rset.getString("district_name"));
                String state_name = unicodeToKruti.Convert_to_Kritidev_010(rset.getString("state_name"));
                if (state_name.toUpperCase().startsWith(q.toUpperCase())) {
                    list.add(state_name);
                    count++;
                }
            }
            if (count == 0) {
                list.add("No such state_name exists.......");
            }
        } catch (Exception e) {
            System.out.println("getState ERROR inside DistrictModel - " + e);
        }
        return list;
    }

    public List<String> getUt(String q) {
        List<String> list = new ArrayList<String>();
        String query = " SELECT ut_name FROM state GROUP BY ut_name ORDER BY ut_name ";
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            int count = 0;
            q = q.trim();
            while (rset.next()) {    // move cursor from BOR to valid record.
                String stateut_type = unicodeToKruti.Convert_to_Kritidev_010(rset.getString("ut_name"));
                if (stateut_type.toUpperCase().startsWith(q.toUpperCase())) {
                    list.add(stateut_type);
                    count++;
                }
            }
            if (count == 0) {
                list.add("No such Ut exists.......");
            }
        } catch (Exception e) {
            System.out.println("getUt ERROR inside DistrictModel - " + e);
        }
        return list;
    }

    public List<String> getDistrict(String q) {
        List<String> list = new ArrayList<String>();
        String query = " SELECT district_id, district_name FROM district GROUP BY district_name ORDER BY district_name ";
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            int count = 0;
            q = q.trim();
            while (rset.next()) {    // move cursor from BOR to valid record.
                String district_name = unicodeToKruti.Convert_to_Kritidev_010(rset.getString("district_name"));
                if (district_name.toUpperCase().startsWith(q.toUpperCase())) {
                    list.add(district_name);
                    count++;
                }
            }
            if (count == 0) {
                list.add("No such district_name exists.......");
            }
        } catch (Exception e) {
            System.out.println("getDistrict ERROR inside DistrictModel - " + e);
        }
        return list;
    }

    public List<String> getDivision(String q, String stateName) {
        List<String> list = new ArrayList<String>();
        stateName = (stateName);

//        if(stateName==null||stateName.equals(""))
        String query = " select division_name from division where division.state_id IN (select state.state_id from state where state.state_name='" + stateName + "') ";
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            int count = 0;
            q = q.trim();
            while (rset.next()) {    // move cursor from BOR to valid record.
                String division_name = unicodeToKruti.Convert_to_Kritidev_010(rset.getString("division_name"));
                if (division_name.toUpperCase().startsWith(q.toUpperCase())) {
                    list.add(division_name);
                    count++;
                }
            }
            if (count == 0) {
                list.add("No such division_name exists.......");
            }
        } catch (Exception e) {
            System.out.println("getDivision ERROR inside DistrictModel - " + e);
        }
        return list;
    }

    public static int getDivisionID(String division_name) {
        int division_id = 0;
        String query = " (select division_id from division where division_name='" + division_name + "') ";
        try {
            PreparedStatement pst = connection.prepareStatement(query);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                division_id = rs.getInt("division_id");
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return division_id;
    }

//    public void updateRecord(String stateName, String utName, String divisionName, String districtId, String districtName, String districtDescription) {
//        PreparedStatement prestaState = null;
//        PreparedStatement prestaDivision = null;
//        PreparedStatement prestaDistrict = null;
//        PreparedStatement presta = null;
//        String query;
//        int rowAffected = 0;
//        try {
//            stateName = krutiToUnicode.convert_to_unicode(stateName);
//            utName = krutiToUnicode.convert_to_unicode(utName);
//            divisionName = krutiToUnicode.convert_to_unicode(divisionName);
//            districtName = krutiToUnicode.convert_to_unicode(districtName);
//            districtDescription = krutiToUnicode.convert_to_unicode(districtDescription);
//            String stateQuery = "select count(*) from state where if ('" + stateName + "'=' ',ut_name='" + utName + "',state_name='" + stateName + "')";
//            prestaState = connection.prepareStatement(stateQuery);
//            ResultSet resultState = prestaState.executeQuery();
//            resultState.next();
//            int stateNo = resultState.getInt(1);
//            if (stateNo == 1) {
//                String divisionQuery = "select count(*) from division where division_name='" + divisionName + "'";
//                prestaDivision = connection.prepareStatement(divisionQuery);
//                ResultSet resultDivision = prestaDivision.executeQuery();
//                resultDivision.next();
//                int divisionNo = resultDivision.getInt(1);
//                if (divisionNo == 1) {
//                    String districtQuery = "select count(*) from district where district_name='" + districtName + "'";
//                    prestaDistrict = connection.prepareStatement(districtQuery);
//                    ResultSet resultDistrict = prestaDistrict.executeQuery();
//                    resultDistrict.next();
//                    int districtNo = resultDistrict.getInt(1);
//                    query = "update district set district_name='" + districtName + "',district_description='" + districtDescription + "',"
//                            + "district.division_id=(select division.division_id from division where '" + divisionName + "'=division.division_name),"
//                            + "district.state_id=(select state.state_id from state where if('" + stateName + "'='',ut_name='" + utName + "',state_name='" + stateName + "')) "
//                            + "where district.district_id=" + Integer.parseInt(districtId);
//                    presta = connection.prepareStatement(query);
//                    rowAffected = presta.executeUpdate();
//                    if (rowAffected > 0) {
//                        message = "Record updated successfully....";
//                        messageBGColor = "yellow";
//                    } else {
//                        message = "Record not updated successfully";
//                        messageBGColor = "red";
//                    }
//
//                } else {
//                    message = "Division doesn't exists";
//                    messageBGColor = "red";
//                }
//            } else {
//                message = "State doesn't exists";
//                messageBGColor = "red";
//            }
//        } catch (Exception e) {
//            message = "Record cann't be updated this record";
//            messageBGColor = "red";
//            System.out.println("Error in updateRecord in DistrictModel: " + e);
//        }
//    }
    public void deleteRecord(int districtId) {
        PreparedStatement presta = null;
        try {
            presta = connection.prepareStatement(" delete from district where district_id=? ");
            presta.setInt(1, districtId);
            int i = presta.executeUpdate();
            if (i > 0) {
                message = "Record deleted successfully......";
                messageBGColor = "yellow";
            } else {
                message = "Record not deleted successfully......";
                messageBGColor = "red";
            }
        } catch (Exception e) {
            message = "Record cann't be deleted";
            messageBGColor = "red";
            System.out.println("Error in deleteRecord : DistrictModel : " + e);
        }
    }

    public ArrayList<DistrictBean> getAllRecords(int lowerLimit, int noOfRowsToDisplay, String searchDistrict) {
        searchDistrict = (searchDistrict);
        ArrayList<DistrictBean> list = new ArrayList<DistrictBean>();

        String query = " SELECT district_id, district_name,district_description,division_name,state_name,ut_name FROM district,division,state WHERE district.active='Y' and division.active='Y' and state.active='Y' and district.division_id=division.division_id  and division.state_id=state.state_id and division.state_id=state.state_id and IF('" + searchDistrict + "'='',district_name LIKE '%%',district_name=?) order by district_name limit " + lowerLimit + "," + noOfRowsToDisplay;
//         String query = "SELECT district_id, district_name,district_description,division_name,state_name,ut_name FROM district,division,state WHERE district.division_id=division.division_id and district.state_id=state.state_id and IF('" + searchDistrict + "'='',district_name LIKE '%%',district_name=?) order by district_name limit " + lowerLimit + "," + noOfRowsToDisplay;

        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, searchDistrict);
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                DistrictBean districtBean = new DistrictBean();
                districtBean.setDistrictId(rset.getInt("district_id"));
                districtBean.setDistrictName(rset.getString("district_name"));
                districtBean.setDistrictDescription(rset.getString("district_description"));
                districtBean.setDivisionName(rset.getString("division_name"));
                districtBean.setUtName(rset.getString("ut_name"));
                districtBean.setStateName(rset.getString("state_name"));
//                if (utname == null || utname.equals("")) {
//                    districtBean.setStateName(stateName);
//                } else {
//                    districtBean.setStateName(stateName);//+"("+rset.getString("ut_name")+")"
//                }
                list.add(districtBean);
            }
        } catch (Exception e) {
            System.out.println("Error in getAllRecrod in DistrictModel " + e);
        }
        return list;
    }

    public int getTotalRowsInTable(String searchDistrict) {
        searchDistrict = (searchDistrict);
        String query = " SELECT Count(*) "
                + " FROM district "
                + " WHERE IF('" + searchDistrict + "' = '', district_name LIKE '%%',district_name =?) "
                + " ORDER BY district_name ";
        int noOfRows = 0;
        try {
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setString(1, searchDistrict);
            ResultSet rs = stmt.executeQuery();
            rs.next();
            noOfRows = Integer.parseInt(rs.getString(1));
        } catch (Exception e) {
            System.out.println("Error inside getNoOfRows DistrictModel" + e);
        }
        System.out.println("No of Rows in Table for search is****....." + noOfRows);
        return noOfRows;
    }

//    public void insertRecord(String[] stateName, String[] utName, String[] divisionName, String[] district_id, String[] districtName, String[] districtDescription) {
//        PreparedStatement prestaState = null;
//        PreparedStatement prestaDivision = null;
//        PreparedStatement prestaDistrict = null;
//        PreparedStatement presta = null;
//        int rowAffected = 0;
//        int rowNotAffected = 0;
//        for (int i = 0; i < district_id.length; i++) {
//            if (district_id[i].equals("1")) {
//                try {
//                    String state_name = krutiToUnicode.convert_to_unicode(stateName[i]);
//                    String ut_name = krutiToUnicode.convert_to_unicode(utName[i]);
//                    String division_name = krutiToUnicode.convert_to_unicode(divisionName[i]);
//                    String district_name = krutiToUnicode.convert_to_unicode(districtName[i]);
//                    String district_description = krutiToUnicode.convert_to_unicode(districtDescription[i]);
//                    String stateQuery = "select count(*) from state where if ('" + state_name + "'=' ',ut_name='" + ut_name + "',state_name='" + state_name + "')";
//                    prestaState = connection.prepareStatement(stateQuery);
//                    ResultSet resultState = prestaState.executeQuery();
//                    resultState.next();
//                    int stateNo = resultState.getInt(1);
//                    if (stateNo == 1) {
//                        String divisionQuery = "select count(*) from division where division_name='" + division_name + "'";
//                        prestaDivision = connection.prepareStatement(divisionQuery);
//                        ResultSet resultDivision = prestaDivision.executeQuery();
//                        resultDivision.next();
//                        int divisionNo = resultDivision.getInt(1);
//                        if (divisionNo == 1) {
//                            String districtQuery = "select count(*) from district where district_name='" + district_name + "'";
//                            prestaDistrict = connection.prepareStatement(districtQuery);
//                            ResultSet resultDistrict = prestaDistrict.executeQuery();
//                            resultDistrict.next();
//                            int districtNo = resultDistrict.getInt(1);
//                            if (districtNo == 1) {
//                                rowNotAffected++;
//                            } else {
//                                String query = "insert into district(district_name,district_description,division_id,state_id) values('" + district_name.trim() + "','" + district_description.trim() + "',(select division.division_id from division where division.division_name='" + division_name.trim() + "'),(select state.state_id from state where if('" + state_name + "' =' ',ut_name='" + ut_name + "',state_name='" + state_name + "')))";
//                                presta = connection.prepareStatement(query);
//                                presta.executeUpdate();
//                                rowAffected++;
//                            }
//                        } else {
//                            rowNotAffected++;
//                        }
//                    } else {
//                        rowNotAffected++;
//                    }
//                } catch (Exception e) {
//                    message = "Record not inserted successfully";
//                    messageBGColor = "red";
//                    System.out.println("Error in insertRecord DistrictModel : " + e);
//                }
//            }
//        }
//        if (rowAffected > 0) {
//            message = rowAffected + " Records are inserted";
//            messageBGColor = "yellow";
//        }
//        if (rowNotAffected > 0) {
//            message = message + " (" + rowNotAffected + " Records are not inserted (Only District name should unique)";
//            messageBGColor = "red";
//        }
//    }
    public static int getStateID(String state_name) {
        state_name = (state_name);
        String query = " SELECT state_id FROM state WHERE state_name = ? ";
        int id = 0;
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, state_name);
            ResultSet rset = pstmt.executeQuery();
            if (rset.next()) {    // move cursor from BOR to valid record.
                id = rset.getInt("state_id");
            }
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
        return id;
    }

    public int getDivisionId(String division_name) {
        int division_id = 0;
        String query = " select division_id from division where division_name= '" + division_name + "' ";
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            if (rset.next()) {
                division_id = rset.getInt("division_id");

            }
        } catch (Exception ex) {
            System.out.println("ERROR: " + ex);
        }
        return division_id;
    }

    public int insertRecord(DistrictBean Bean) {

        String query = " INSERT INTO district(district_name, district_description, division_id,revision_no,active ) VALUES(?,?,?,?,?) ";
        int rowsAffected = 0;
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
//            pstmt.setString(1, krutiToUnicode.convert_to_unicode(designation.getDesignation()));
//            pstmt.setString(2, krutiToUnicode.convert_to_unicode(designation.getDescription()));
            pstmt.setString(1, Bean.getDistrictName());
            pstmt.setString(2, Bean.getDistrictDescription());
            pstmt.setInt(3, Bean.getDivisionId());
            pstmt.setInt(4, Bean.getRevision_no());
            pstmt.setString(5, "Y");
            rowsAffected = pstmt.executeUpdate();
        } catch (Exception e) {
            System.out.println("Model Error: " + e);
        }
        if (rowsAffected > 0) {
            message = "Record saved successfully.";

        } else {
            message = "Cannot save the record, some error.";

        }
        return rowsAffected;
    }

    public int updateRecord(DistrictBean Bean, int district_id) {
        int revision = DistrictModel.getRevisionno(Bean, district_id);
        int updateRowsAffected = 0;
        boolean status = false;
        String query1 = " SELECT max(revision_no) revision_no FROM district WHERE district_id = " + district_id + "  && active=? ";
        String query2 = " UPDATE district SET active=? WHERE district_id=? and revision_no=? ";
        String query3 = " INSERT INTO district(district_id,division_id,district_name,district_description,revision_no,active) "
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

                pstm.setInt(2, district_id);
                pstm.setInt(3, revision);
                updateRowsAffected = pstm.executeUpdate();
                if (updateRowsAffected >= 1) {
                    revision = rs.getInt("revision_no") + 1;
                    PreparedStatement psmt = (PreparedStatement) connection.prepareStatement(query3);
                    psmt.setInt(1, (district_id));
                    psmt.setInt(2, Bean.getDivisionId());
                    psmt.setString(3, Bean.getDistrictName());
                    psmt.setString(4, (Bean.getDistrictDescription()));
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

    public static int getRevisionno(DistrictBean bean, int district_id) {
        int revision = 0;
        try {

            String query = " SELECT max(revision_no) as revision_no FROM district WHERE district_id =" + district_id + "  && active='Y' ";

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
            System.out.println("DistrictModel closeConnection: " + e);
        }
    }

    public void setConnection(Connection con) {
        try {

            connection = con;
        } catch (Exception e) {
            System.out.println("DistrictModel setConnection() Error: " + e);
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
