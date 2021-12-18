package com.location.model;

import com.location.bean.CityBean;
import java.io.ByteArrayOutputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
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

public class CityModel {

    static private Connection connection;
    private String driver, url, user, password;
    private String message, messageBGColor = "#a2a220";

    public void setConnection(Connection con) {
        try {

            connection = con;
        } catch (Exception e) {
            System.out.println("CityModel setConnection() Error: " + e);
        }
    }

    public List<String> getCity(String q) {
        List<String> list = new ArrayList<String>();
        String query = " SELECT city_id, city_name FROM city GROUP BY city_name ORDER BY city_name ";
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            int count = 0;
            while (rset.next()) {    // move cursor from BOR to valid record.

                q = q.trim();
                while (rset.next()) {
                    String city_name = (rset.getString("city_name"));
                    if (city_name.toUpperCase().startsWith(q.toUpperCase())) {
                        list.add(city_name);
                        count++;
                    }

                }

            }
            if (count == 0) {
                list.add("No such city exists.......");
            }
        } catch (Exception e) {
            System.out.println("getCity ERROR inside CityModel - " + e);
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

                list.add(tehsil_type);
                count++;

            }
            if (count == 0) {
                list.add("No such tehsil exists.......");
            }
        } catch (Exception e) {
            System.out.println("getTehsil ERROR inside CityModel - " + e);
        }
        return list;
    }

    public List<String> getCityName(String q, String distName) {
        List<String> list = new ArrayList<String>();

        String query = "select city_name FROM city where city.district_id=(select district.district_id from district where district_name='" + distName + "') GROUP BY city_name ORDER BY city_name";
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            int count = 0;
            q = q.trim();
            while (rset.next()) {    // move cursor from BOR to valid record.
                String city_type = rset.getString("city_name");
                if (city_type.toUpperCase().startsWith(q.toUpperCase())) {
                    list.add(city_type);
                    count++;
                }
            }
            if (count == 0) {
                list.add("No such city exists.......");
            }
        } catch (Exception e) {
            System.out.println("getCityName ERROR inside CityModel - " + e);
        }
        return list;
    }

    public List<String> getDistrict(String q, String diviName) {
        List<String> list = new ArrayList<String>();
        diviName = diviName;
        String query = " SELECT district_name FROM district where district.division_id=(select division.division_id from division where division.division_name='" + diviName + "') GROUP BY district_name ORDER BY district_name ";
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            int count = 0;
            q = q.trim();
            while (rset.next()) {    // move cursor from BOR to valid record.
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
            System.out.println("getDistrict ERROR inside CityModel - " + e);
        }
        return list;
    }

    public void deleteRecord(int cityId) {
        PreparedStatement presta = null;
        try {
            presta = connection.prepareStatement("delete from city where city_id=?");
            presta.setInt(1, cityId);
            int i = presta.executeUpdate();
            if (i > 0) {
                message = "Record deleted successfully......";
                messageBGColor = "#a2a220";
            } else {
                message = "Record not deleted successfully......";
                messageBGColor = "red";
            }
        } catch (Exception e) {
            System.out.println("Error in deleting record ---- CityModel : " + e);
        }
    }

    public String getTehsilNameFromId(int id) {
        String name = null;

        String query = "SELECT tehsil_name FROM tehsil where tehsil_id=? and active='Y'";
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setInt(1, id);
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                name = rset.getString(1);
            }

        } catch (SQLException ex) {
            Logger.getLogger(CityModel.class.getName()).log(Level.SEVERE, null, ex);
        }

        return name;
    }

    public ArrayList<CityBean> getAllRecords(String searchCity) {
        searchCity = (searchCity);
        ArrayList<CityBean> list = new ArrayList<CityBean>();
        String query = "SELECT city_id,city_name,pin_code,std_code,city_description,tehsil_id FROM city "
                + " WHERE  active='Y' ";

        if (!searchCity.equals("") && searchCity != null) {
            query += " and city_name='" + searchCity + "' ";
        }
        query += " order by city_id desc ";

        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                CityBean CityBean = new CityBean();
                CityBean.setCityId(rset.getInt(1));
                CityBean.setCityName(rset.getString(2));
                CityBean.setPin_code(rset.getInt(3));
                CityBean.setStd_code(rset.getInt(4));
                CityBean.setCityDescription(rset.getString(5));
                String tehsil = getTehsilNameFromId(rset.getInt(6));
                CityBean.setTehsilName(tehsil);
                list.add(CityBean);
            }
        } catch (Exception e) {
            System.out.println("Error in getAllRecrod -- CityModel : " + e);
        }
        return list;
    }

    public int getTehsilIdFromName(String name) {
        int id = 0;

        String query = "SELECT tehsil_id FROM tehsil where tehsil_name=? and active='Y'";
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, name);
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                id = rset.getInt(1);
            }

        } catch (SQLException ex) {
            Logger.getLogger(CityModel.class.getName()).log(Level.SEVERE, null, ex);
        }

        return id;
    }

    public static int getCityId(String district_name) {
        int division_id = 0;
        String query = "select city_id from city where city_name= '" + district_name + "' ";
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            if (rset.next()) {
                division_id = rset.getInt("city_id");

            } else {
                division_id = 0;
            }
        } catch (Exception ex) {
            System.out.println("Error in getCityId -- CityModel : " + ex);
        }
        return division_id;
    }

    public void insertRecord(CityBean bean) {
        int rowAffected = 0;
        try {
            String query = "insert into city(city_name,city_description,pin_code,std_code,tehsil_id,revision_no,active,created_by,remark) values(?,?,?,?,?,0,'Y','tk','default')";
            PreparedStatement ps = (PreparedStatement) connection.prepareStatement(query);
            ps.setString(1, bean.getCityName());
            ps.setString(2, bean.getCityDescription());
            ps.setInt(3, bean.getPin_code());
            ps.setInt(4, bean.getStd_code());

            int id = getTehsilIdFromName(bean.getTehsilName());
            ps.setInt(5, id);

            rowAffected = ps.executeUpdate();
        } catch (Exception e) {
            System.out.println("Error in insertRecord in CityModel : " + e);
        }
        if (rowAffected > 0) {
            message = rowAffected + " Record inserted successfully";
            messageBGColor = "#a2a220";
        }
    }

    public int updateRecord(CityBean Bean, int city_id) {
        int revision = CityModel.getRevisionno(Bean, city_id);
        int updateRowsAffected = 0;
        boolean status = false;
        String query1 = "SELECT max(revision_no) revision_no FROM city WHERE city_id = " + city_id + "  && active=? ";
        String query2 = "UPDATE city SET active=? WHERE city_id=? and revision_no=?";
        String query3 = "INSERT INTO city(city_id,city_name,city_description,pin_code,std_code,tehsil_id,revision_no,active,created_by,remark)"
                + "VALUES(?,?,?,?,?,?,?,'Y','tk','default')";
        int rowsAffected = 0;
        try {
            PreparedStatement pstmt = connection.prepareStatement(query1);
//           pstmt.setInt(1,organisation_type_id);
            pstmt.setString(1, "Y");

            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                revision = rs.getInt("revision_no");
                PreparedStatement pstm = connection.prepareStatement(query2);

                pstm.setString(1, "n");

                pstm.setInt(2, city_id);
                pstm.setInt(3, revision);
                updateRowsAffected = pstm.executeUpdate();
                if (updateRowsAffected >= 1) {
                    revision = rs.getInt("revision_no") + 1;
                    PreparedStatement psmt = (PreparedStatement) connection.prepareStatement(query3);
                    psmt.setInt(1, (city_id));

                    psmt.setString(2, Bean.getCityName());
                    psmt.setString(3, Bean.getCityDescription());
                    psmt.setInt(4, Bean.getPin_code());
                    psmt.setInt(5, (Bean.getStd_code()));

                    int id = getTehsilIdFromName(Bean.getTehsilName());
                    psmt.setInt(6, id);
                    psmt.setInt(7, revision);
                    rowsAffected = psmt.executeUpdate();
                    if (rowsAffected > 0) {
                        status = true;
                    } else {
                        status = false;
                    }
                }

            }
        } catch (Exception e) {
            System.out.println("Error:CityModel updateRecord-" + e);
        }
        if (rowsAffected > 0) {
            message = "Record updated successfully.";

        } else {
            message = "Cannot update the record, some error.";
        }
        return rowsAffected;
    }

    public static int getRevisionno(CityBean bean, int city_id) {
        int revision = 0;
        try {

            String query = " SELECT max(revision_no) as revision_no FROM city WHERE tehsil_id =" + city_id + "  && active='Y';";

            PreparedStatement pstmt = (PreparedStatement) connection.prepareStatement(query);

            ResultSet rset = pstmt.executeQuery();

            while (rset.next()) {
                revision = rset.getInt("revision_no");

            }
        } catch (Exception e) {
            System.out.println("Error:CityModel getRevisionno-" + e);

        }
        return revision;
    }

    public void closeConnection() {
        try {
            connection.close();
        } catch (Exception e) {
            System.out.println("CityModel closeConnection: " + e);
        }
    }

    public void setConnection() {
        try {
            Class.forName(driver);
            connection = DriverManager.getConnection(url, user, password);
        } catch (Exception e) {
            System.out.println("CityModel setConnection error: " + e);
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
