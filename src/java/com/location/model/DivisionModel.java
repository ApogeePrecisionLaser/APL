package com.location.model;

import com.location.bean.DivisionBean;
import com.util.KrutiDevToUnicodeConverter;
import com.util.UnicodeToKrutiDevConverter;
import java.io.ByteArrayOutputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import net.sf.jasperreports.engine.JRExporterParameter;
//import org.omg.PortableServer.REQUEST_PROCESSING_POLICY_ID;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.JasperRunManager;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRXlsExporter;

public class DivisionModel {

    private static Connection connection;
    private String driver, url, user, password;
    private String message, messageBGColor;
    private KrutiDevToUnicodeConverter krutiToUnicode = new KrutiDevToUnicodeConverter();
    private UnicodeToKrutiDevConverter unicodeToKruti = new UnicodeToKrutiDevConverter();

    public byte[] generateMapReport(String jrxmlFilePath, List<DivisionBean> listAll) {
        byte[] reportInbytes = null;
        try {

            JRBeanCollectionDataSource beanColDataSource = new JRBeanCollectionDataSource(listAll);
            JasperReport compiledReport = JasperCompileManager.compileReport(jrxmlFilePath);
            reportInbytes = JasperRunManager.runReportToPdf(compiledReport, null, beanColDataSource);
        } catch (Exception e) {
            System.out.println("Error: in DivisionModel generateMapReport() JRException: " + e);
        }
        return reportInbytes;
    }

    public ByteArrayOutputStream generateDivisionXlsRecordList(String jrxmlFilePath, List list) {
        ByteArrayOutputStream byteArray = new ByteArrayOutputStream();
        //  HashMap mymap = new HashMap();
        try {
            JRBeanCollectionDataSource jrBean = new JRBeanCollectionDataSource(list);
            JasperReport compiledReport = JasperCompileManager.compileReport(jrxmlFilePath);
            JasperPrint jasperPrint = JasperFillManager.fillReport(compiledReport, null, jrBean);
            JRXlsExporter exporter = new JRXlsExporter();
            exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
            exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, byteArray);
            exporter.exportReport();
        } catch (Exception e) {
            System.out.println("DivisionStatusModel generatReport() JRException: " + e);
        }
        return byteArray;
    }

    public List<DivisionBean> showAllData(String divisionName) {
        divisionName = krutiToUnicode.convert_to_unicode(divisionName);
        ArrayList<DivisionBean> list = new ArrayList<DivisionBean>();
        String query = "select division_name,division_description,state_name,ut_name,zone_name from division,state,zone where division.active='Y' and division.state_id=state.state_id and division.zone_id=zone.zone_id and if('" + divisionName + "'='',division_name LIKE '%%',division_name='" + divisionName + "')";

        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            ResultSet rset = pstmt.executeQuery();

            while (rset.next()) {
                DivisionBean divisionBean = new DivisionBean();
                divisionBean.setDivisionName(rset.getString("division_name"));
                divisionBean.setDivisionDescription(rset.getString("division_description"));
                divisionBean.setStateName(rset.getString("state_name"));
                divisionBean.setUtName(rset.getString("ut_name"));
                divisionBean.setZoneName(rset.getString("zone_name"));

                list.add(divisionBean);
            }
        } catch (Exception e) {
            System.out.println("Error in ShowAllData --- DivisionModel : " + e);
        }

        return list;
    }

    public List<String> getDivName(String q, String stateName, String utName) {
        List<String> list = new ArrayList<String>();
        String query = null;
        if (stateName.equals("")) {
            query = " SELECT division_name FROM division where division.state_id=(select state.state_id from state where state.ut_name='" + utName + "') GROUP BY division_name ORDER BY division_name ";
        } else {
            query = " SELECT division_name FROM division where division.state_id=(select state.state_id from state where state.state_name='" + stateName + "') GROUP BY division_name ORDER BY division_name ";
        }
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            int count = 0;
            q = q.trim();
            while (rset.next()) {    // move cursor from BOR to valid record.
                String division_type = rset.getString("division_name");
                if (division_type.toUpperCase().startsWith(q.toUpperCase())) {
                    list.add(division_type);
                    count++;
                }
            }
            if (count == 0) {
                list.add("No such Division exists.......");
            }
        } catch (Exception e) {
            System.out.println("getDivName ERROR inside DivisionModel - " + e);
        }
        return list;
    }

    public List<String> getZone(String q) {
        List<String> list = new ArrayList<String>();
        String query = " SELECT zone_name FROM zone GROUP BY zone_name ORDER BY zone_name ";
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            int count = 0;
            q = q.trim();
            while (rset.next()) {    // move cursor from BOR to valid record.
                String zone_type = unicodeToKruti.Convert_to_Kritidev_010(rset.getString("zone_name"));
                if (zone_type.toUpperCase().startsWith(q.toUpperCase())) {
                    list.add(zone_type);
                    count++;
                }
            }
            if (count == 0) {
                list.add("No such Zone exists.......");
            }
        } catch (Exception e) {
            System.out.println("getZone ERROR inside DivisionModel - " + e);
        }
        return list;
    }

    public List<String> getDivision(String q) {
        List<String> list = new ArrayList<String>();
        String query = " SELECT division_name FROM division GROUP BY division_name ORDER BY division_name ";
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
            System.out.println("getDivision ERROR inside DivisionModel - " + e);
        }
        return list;
    }

    public List<String> getStateName(String q) {
        List<String> list = new ArrayList<String>();
        String query = " SELECT state_name FROM state  GROUP BY state_name ORDER BY  state_name ";
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            int count = 0;
            q = q.trim();
            while (rset.next()) {    // move cursor from BOR to valid record.
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
            System.out.println("getStateutName ERROR inside DivisionModel - " + e);
        }
        return list;
    }

    public List<String> getUtName(String q, String zoneName) {
        List<String> list = new ArrayList<String>();
        String query = "select ut_name from state where state.zone_id=(select zone.zone_id from zone where zone_name='" + zoneName + "') ";
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            int count = 0;
            q = q.trim();
            while (rset.next()) {    // move cursor from BOR to valid record.
                String stateut_type = unicodeToKruti.Convert_to_Kritidev_010(rset.getString(1));
                if (stateut_type != null) {
                    if (stateut_type.toUpperCase().startsWith(q.toUpperCase())) {
                        list.add(stateut_type);
                        count++;
                    }
                }
            }
            if (count == 0) {
                list.add("No such UT exists.......");
            }
        } catch (Exception e) {
            System.out.println("getUtName ERROR inside DivisionModel - " + e);
        }
        return list;
    }
//    public void updateRecord(String zoneName,String stateName,String utName,String divisionId,String divisionName,String divisionDescription)
//    {       
//      PreparedStatement prestaZone=null;
//      PreparedStatement prestaState=null;
//      PreparedStatement prestaDivision=null;
//      PreparedStatement presta = null;
//      int rowAffected=0;
//      int rowNotAffected=0;     
//            try
//            {
//                zoneName = zoneName;
//                stateName = krutiToUnicode.convert_to_unicode(stateName);
//                utName = krutiToUnicode.convert_to_unicode(utName);
//                divisionName = krutiToUnicode.convert_to_unicode(divisionName);
//                divisionDescription = krutiToUnicode.convert_to_unicode(divisionDescription);
//
//                String zoneQuery = "select count(*) from zone where zone_name='"+zoneName+"'";
//                prestaZone = connection.prepareStatement(zoneQuery);
//                ResultSet resultZone = prestaZone.executeQuery();
//                resultZone.next();
//                int zoneNo = resultZone.getInt(1);
//                if(zoneNo==1)
//                {
//                    String stateQuery = "select count(*) from state where if ('"+stateName+"'='',ut_name='"+utName+"',state_name='"+stateName+"')";
//                    prestaState = connection.prepareStatement(stateQuery);
//                    ResultSet resultState = prestaState.executeQuery();
//                    resultState.next();
//                    int stateNo = resultState.getInt(1);
//                    if(stateNo==1)
//                    {
//                        String divisionQuery = "select count(*) from division where division_name='"+divisionName+"'";
//                        prestaDivision = connection.prepareStatement(divisionQuery);
//                        ResultSet resultDivision = prestaDivision.executeQuery();
//                        resultDivision.next();
//                        int divisionNo = resultDivision.getInt(1);
//                       
//                       
//                            String query="update division set division_name='"+divisionName+"',division_description='"+divisionDescription+"',"
//                                        + "state_id=(select state_id from state where if('"+stateName+"'='',ut_name='"+utName+"',state_name='"+stateName+"')),"
//                                        + "zone_id=(select zone_id from zone where zone_name='"+zoneName+"')"
//                                        + "where division_id="+Integer.parseInt(divisionId);
//                           presta = connection.prepareStatement(query);
//                           int i = presta.executeUpdate();
//                       
//                           if(i>0)
//                            {
//                                message=i+" Record updated successfully......";
//                                messageBGColor="yellow";
//                            }
//                          else
//                            {
//                                message="Record not updated successfully......";
//                                messageBGColor="red";
//                            }                        
//                                           
//                    }
//                    else
//                    {
//                        message ="State name does not exist: ";
//                        messageBGColor="red";
//                    }
//                }else
//                {
//                 message ="Zone name does not exist: ";
//                 messageBGColor="red";
//                }
//            }catch(Exception e)
//            {
//                message="Record not updated successfully";
//                messageBGColor="red";
//                System.out.println("Error in updateRecord DivisionModel : "+e);
//            }      
//     
//}

    public void deleteRecord(int divisionId) {
        PreparedStatement presta = null;
        try {
            presta = connection.prepareStatement("delete from division where division_id=?");
            presta.setInt(1, divisionId);
            int i = presta.executeUpdate();
            if (i > 0) {
                message = "Record deleted successfully......";
                messageBGColor = "yellow";
            } else {
                message = "Record not deleted successfully......";
                messageBGColor = "red";
            }
        } catch (Exception e) {
            message = "Record cann't be delted";
            messageBGColor = "red";
            System.out.println("Error in deleting recordl ---- DivisionModel - " + e);
        }
    }

    public ArrayList<DivisionBean> getAllRecords(int lowerLimit, int noOfRowsToDisplay, String searchDivision) {
        ArrayList<DivisionBean> list = new ArrayList<DivisionBean>();
        searchDivision = searchDivision;
        String query = "SELECT division_id,division_name,division_description,state_name,ut_name "
                + "FROM division,state "
                + "WHERE division.active='Y' and IF('" + searchDivision + "'='',division_name LIKE '%%',division_name=?) and division.state_id=state.state_id "
                + "order by division_name limit " + lowerLimit + "," + noOfRowsToDisplay;
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, searchDivision);
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                DivisionBean divisionBean = new DivisionBean();
                divisionBean.setDivisionId(rset.getInt(1));
                divisionBean.setDivisionName(rset.getString(2));
                divisionBean.setDivisionDescription(rset.getString(3));
                String uname = rset.getString(5);
                if (uname == null || uname.equals("")) {
                    divisionBean.setStateName(rset.getString(4));
                } else {
                    divisionBean.setStateName(rset.getString(4));//+"("+rset.getString(5)+")"
                }
                divisionBean.setZoneName(rset.getString(5));

                list.add(divisionBean);
            }
        } catch (Exception e) {
            System.out.println("Error in getAllRecrod in DivisionModel " + e);
        }
        return list;
    }

    public int getTotalRowsInTable(String searchDivision) {
        searchDivision = krutiToUnicode.convert_to_unicode(searchDivision);
        String query = " SELECT Count(*) "
                + " FROM division "
                + " WHERE IF('" + searchDivision + "' = '', division_name LIKE '%%',division_name =?) "
                + " ORDER BY division_name ";
        int noOfRows = 0;
        try {
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setString(1, searchDivision);
            ResultSet rs = stmt.executeQuery();
            rs.next();
            noOfRows = Integer.parseInt(rs.getString(1));
        } catch (Exception e) {
            System.out.println("Error inside getNoOfRows DivisionModel" + e);
        }
        System.out.println("No of Rows in Table for search is****....." + noOfRows);
        return noOfRows;
    }

//public void insertRecord(String[] zoneName,String[] stateName,String[] utName, String[] division_id,String[] divisionName,String[] divisionDescription)
//{
//      PreparedStatement prestaZone=null;
//      PreparedStatement prestaState=null;
//      PreparedStatement prestaDivision=null;
//      PreparedStatement presta = null;
//      int rowAffected=0;
//      int rowNotAffected=0;
//      for(int i=0;i<division_id.length;i++)
//      {
//        if(division_id[i].equals("1"))
//        {
//            try
//            {
//                String zone_name = zoneName[i];
//                String state_name = krutiToUnicode.convert_to_unicode(stateName[i]);
//                String ut_name = krutiToUnicode.convert_to_unicode(utName[i]);
//                String division_name = krutiToUnicode.convert_to_unicode(divisionName[i]);
//                String division_description = krutiToUnicode.convert_to_unicode(divisionDescription[i]);
//
//                String zoneQuery = "select count(*) from zone where zone_name='"+zone_name+"'";
//                prestaZone = connection.prepareStatement(zoneQuery);
//                ResultSet resultZone = prestaZone.executeQuery();
//                resultZone.next();
//                int zoneNo = resultZone.getInt(1);
//                if(zoneNo==1)
//                {
//                    String stateQuery = "select count(*) from state where if ('"+state_name+"'='',ut_name='"+ut_name+"',state_name='"+state_name+"')";
//                    prestaState = connection.prepareStatement(stateQuery);
//                    ResultSet resultState = prestaState.executeQuery();
//                    resultState.next();
//                    int stateNo = resultState.getInt(1);
//                    if(stateNo==1)
//                    {
//                        String divisionQuery = "select count(*) from division where division_name='"+division_name+"'";
//                        prestaDivision = connection.prepareStatement(divisionQuery);
//                        ResultSet resultDivision = prestaDivision.executeQuery();
//                        resultDivision.next();
//                        int divisionNo = resultDivision.getInt(1);
//                        if(divisionNo==1)
//                            rowNotAffected++;
//                        else
//                        {
//                            String query = "insert into division(division_name,division_description,state_id,zone_id) values('"+division_name.trim()+"','"+division_description.trim()+"',(select state_id from state where if('"+state_name+"'='',ut_name='"+ut_name+"','"+state_name+"'=state_name)),(select zone.zone_id from zone where zone.zone_name='"+zone_name+"'))";
//                            presta = connection.prepareStatement(query);
//                            presta.executeUpdate();
//                            rowAffected++;
//                        }
//                    }
//                    else
//                        rowNotAffected++;
//                }
//                else
//                    rowNotAffected++;          
//            }catch(Exception e)
//            {
//                message="Record not inserted successfully";
//                messageBGColor="red";
//                System.out.println("Error in insertRecord DivisionModel : "+e);
//            }
//         }
//      }
//      if(rowAffected>0)
//      {
//        message=rowAffected+" Record inserted sucessfully";
//        messageBGColor="yellow";
//      }
//      if(rowNotAffected>0)
//      {
//        message=message+" ("+rowNotAffected+" Record not inserted (Only Division name should unique)";
//        messageBGColor="red";
//      }
//}
    public static int getStateID(String state_name) {
        state_name = (state_name);
        String query = "SELECT state_id FROM state WHERE state_name = ?";
        int id = 0;
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, state_name);
            ResultSet rset = pstmt.executeQuery();
            rset.next();    // move cursor from BOR to valid record.
            id = rset.getInt("state_id");
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
        return id;
    }

    public int insertRecord(DivisionBean bean) {

        String query = "INSERT INTO division( division_name, division_description,state_id,revision_no,active)"
                + "VALUES(?, ?,?,?,?)";
        int rowsAffected = 0;
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, bean.getDivisionName());
            pstmt.setString(2, bean.getDivisionDescription());
            pstmt.setInt(3, bean.getStateId());
            pstmt.setInt(4, bean.getRevision_no());
            pstmt.setString(5, "Y");

            rowsAffected = pstmt.executeUpdate();
        } catch (Exception e) {
            System.out.println("Error: OrganisationSubTypeModel- " + e);
        }
        if (rowsAffected > 0) {
            message = "Record saved successfully.";
//            msgBgColor = COLOR_OK;
        } else {
            message = "Cannot save the record, some error.";
//            msgBgColor = COLOR_ERROR;
        }
        return rowsAffected;
    }

    public int updateRecord(DivisionBean bean, int division_id) {
        int revision = DivisionModel.getRevisionno(bean, division_id);
        int updateRowsAffected = 0;
        boolean status = false;
        String query1 = "SELECT max(revision_no) revision_no FROM division WHERE division_id = " + division_id + "  && active=? ";
        String query2 = "UPDATE division SET active=? WHERE division_id=? and revision_no=?";
        String query3 = "INSERT INTO division(division_id ,division_name, division_description,state_id,revision_no,active)"
                + "VALUES(?, ?,?,?,?,?)";
        int rowsAffected = 0;
        try {
            PreparedStatement pstmt = connection.prepareStatement(query1);
//           pstmt.setInt(1,organisation_type_id);
            pstmt.setString(1, "Y");

            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                PreparedStatement pstm = connection.prepareStatement(query2);

                pstm.setString(1, "n");

                pstm.setInt(2, division_id);
                pstm.setInt(3, revision);
                updateRowsAffected = pstm.executeUpdate();
                if (updateRowsAffected >= 1) {
                    revision = rs.getInt("revision_no") + 1;
                    PreparedStatement psmt = (PreparedStatement) connection.prepareStatement(query3);
                    psmt.setInt(1, (division_id));
                    psmt.setString(2, (bean.getDivisionName()));
                    psmt.setString(3, (bean.getDivisionDescription()));
                    psmt.setInt(4, (bean.getStateId()));
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

    public static int getRevisionno(DivisionBean bean, int division_id) {
        int revision = 0;
        try {

            String query = " SELECT max(revision_no) as revision_no FROM division WHERE division_id =" + division_id + "  && active='Y';";

            PreparedStatement pstmt = (PreparedStatement) connection.prepareStatement(query);

            ResultSet rset = pstmt.executeQuery();

            while (rset.next()) {
                revision = rset.getInt("revision_no");

            }
        } catch (Exception e) {
        }
        return revision;
    }

    public int getStateId(String state_name, String ut_name) {
        int state_id = 0;
        String query = "select state_id from state where state_name= '" + state_name + "' and ut_name='" + ut_name + "'";
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            if (rset.next()) {
                state_id = rset.getInt("state_id");

            }
        } catch (Exception ex) {
            System.out.println("ERROR: " + ex);
        }
        return state_id;
    }
//      public int insertRecord(DivisionBean Bean) {
//
//        String query = "INSERT INTO division(division_name, division_description, state_id ) VALUES(?,?,?)";
//        int rowsAffected = 0;
//        try {
//            PreparedStatement pstmt = connection.prepareStatement(query);
////            pstmt.setString(1, krutiToUnicode.convert_to_unicode(designation.getDesignation()));
////            pstmt.setString(2, krutiToUnicode.convert_to_unicode(designation.getDescription()));
//            pstmt.setString(1, Bean.getDivisionName());
//            pstmt.setString(2, Bean.getDivisionDescription());
////            pstmt.setString(3, Bean.getStateutDescription());
//            pstmt.setInt(3, Bean.getStateId());
//            rowsAffected = pstmt.executeUpdate();
//        } catch (Exception e) {
//            System.out.println("Model Error: " + e);
//        }
//        if (rowsAffected > 0) {
//            message = "Record saved successfully.";
//
//        } else {
//            message = "Cannot save the record, some error.";
//
//        }
//        return rowsAffected;
//    }

//    public int updateRecord(DivisionBean Bean) {
//        String query = "UPDATE state SET division_name=?,division_description=?  WHERE division_id=?";
//        int rowsAffected = 0;
//        try {
//            PreparedStatement pstmt = connection.prepareStatement(query);
////            pstmt.setString(1, krutiToUnicode.convert_to_unicode(designation.getDesignation()));
////            pstmt.setString(2, krutiToUnicode.convert_to_unicode(designation.getDescription()));
//            pstmt.setString(1, Bean.getDivisionDescription());
//            pstmt.setString(2, Bean.getDivisionDescription());
////            pstmt.setString(3, Bean.getStateutDescription());
//            pstmt.setInt(3, Bean.getStateId());
//            rowsAffected = pstmt.executeUpdate();
//        } catch (Exception e) {
//            System.out.println("countryModel Error: " + e);
//        }
//        if (rowsAffected > 0) {
//            message = "Record updated successfully.";
//
//        } else {
//            message = "Cannot update the record, some error.";
//
//        }
//        return rowsAffected;
//    }
    public void closeConnection() {
        try {
            connection.close();
        } catch (Exception e) {
            System.out.println("DivisionModel closeConnection: " + e);
        }
    }

    public void setConnection(Connection con) {
        try {

            connection = con;
        } catch (Exception e) {
            System.out.println("CityModel setConnection() Error: " + e);
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
