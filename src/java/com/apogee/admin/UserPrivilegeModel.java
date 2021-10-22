/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.apogee.admin;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.servlet.ServletContext;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.JasperRunManager;
import org.apache.commons.io.FileUtils;

/**
 *
 * @author Vikrant
 */
public class UserPrivilegeModel {

    private Connection connection;
    private String message;
    private String msgBgColor;
    private final String COLOR_OK = "Green";
    private final String COLOR_ERROR = "red";
    private String messaged;
    private String msgBgColored;

    public void setConnection(Connection con) {
        try {
            connection = con;
        } catch (Exception e) {
            System.out.println("EStatusTypeModel setConnection() Error: " + e);
        }
    }

    public List<UserPrivilegeBean> showData(String u_urlSearch, String role_nameSearch) {
        List<UserPrivilegeBean> list = new ArrayList<UserPrivilegeBean>();
//        String query = "select"
//                + " ur.role_name, u.u_url,"
//                + " p.privilege_type,"
//                + " up.privilege, up.active, up.remark, up.u_url_privilege_id"
//                + " FROM"
//                + " user_roles as ur, u_url as u, url_detail as ud, u_url_privilege as up, u_role_url_privilege as urup, privilege p "
//                + " WHERE"
//                + " ur.user_role_id = urup.user_role_id AND"
//                + " u.u_url_id = urup.u_url_id AND"
//                + " u.u_url_id = ud.url_id AND"
//                + " ud.url_detail_id = up.url_detail_id AND"
//                + " urup.u_role_url_privilege_id = up.u_role_url_privilege_id"
//                + " AND IF('" + role_nameSearch + "'='', ur.role_name LIKE '%%', ur.role_name='" + role_nameSearch + "')"
//                + " AND IF('" + u_urlSearch + "'='', u.u_url LIKE '%%', u.u_url='" + u_urlSearch + "') "
//                + " and p.privilege_id=ud.privilege_id "
//                + " ORDER BY ur.priority , up.u_url_privilege_id";

//        String query = " select distinct p.privilege_id,p.privilege_type,p.privilege_type_id,p.privilege_type_value,"
//                + " uu.u_url_id,uu.u_url,uup.privilege,ur.role_name,uup.u_url_privilege_id,p.remark "
//                + " from privilege p, u_url uu, url_detail ud, user_roles ur, u_role_url_privilege urup,u_url_privilege uup "
//                + " where p.privilege_id=ud.privilege_id and uu.u_url_id=ud.url_id and p.privilege_id=uup.privilege_id "
//                + " AND IF('" + role_nameSearch + "'='', ur.role_name LIKE '%%', ur.role_name='') "
//                + " AND IF('"+ u_urlSearch+"'='', uu.u_url LIKE '%%', uu.u_url='') "
//                + " order by ur.priority;";
        String query = " select ur.role_name,uu.u_url,uup.privilege,uup.u_url_privilege_id, p.privilege_type,p.remark "
                + " from url_detail ud, u_url_privilege uup, u_url uu, u_role_url_privilege urup, user_roles ur, privilege p "
                + " where ud.url_detail_id=uup.url_detail_id and uu.u_url_id=ud.url_id and urup.u_url_id=ud.url_id "
                + " and ur.user_role_id=urup.user_role_id and p.privilege_id=ud.privilege_id "
                + " and urup.u_role_url_privilege_id=uup.u_role_url_privilege_id "
                + " AND IF('" + role_nameSearch + "'='', ur.role_name LIKE '%%', ur.role_name='" + role_nameSearch + "') "
                + " AND IF('" + u_urlSearch + "'='', uu.u_url LIKE '%%', uu.u_url='" + u_urlSearch + "') "
                + " order by uup.u_url_privilege_id ";
                //+ " order by ur.priority ";
        try {
            System.err.println("user privilege query -" + query);
            PreparedStatement pstmt = connection.prepareStatement(query);
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                UserPrivilegeBean userUrlPrivilege = new UserPrivilegeBean();
                userUrlPrivilege.setU_url_privilege_id(rset.getInt("u_url_privilege_id"));
                userUrlPrivilege.setU_url(rset.getString("u_url"));
                userUrlPrivilege.setRole_name(rset.getString("role_name"));
                userUrlPrivilege.setPrivilege_type(rset.getString("privilege_type"));
                userUrlPrivilege.setPrivilege(rset.getString("privilege"));
//                //userUrlPrivilege.setActive(rset.getString("active"));
                userUrlPrivilege.setRemark(rset.getString("remark"));
                list.add(userUrlPrivilege);
            }
        } catch (Exception e) {
            System.out.println("Error: inside show data" + e);
        }
        return list;
    }

    public int insertRecord(UserPrivilegeBean userUrlPrivilege) {
        int duplicate = 0;
        int success = 0;
        int rowsAffected = 0;
        ArrayList<String> failedRoles = new ArrayList<String>();
        PreparedStatement pstmt = null;
        String[] role_blocks = userUrlPrivilege.getRole_nameM();
        String[] u_url_privilege_id = userUrlPrivilege.getU_url_privilege_idM();
        String query = "INSERT INTO u_url_privilege"
                + " (u_role_url_privilege_id, url_detail_id, privilege, active, remark) values (?,?,?,?,?)";
        try {
            for (int i = 0; i < u_url_privilege_id.length; i++) {
                if (userUrlPrivilege.getU_url_privilege_idM()[i].equals("1")) {
                    String u_url = userUrlPrivilege.getU_urlM()[i];
                    String privilege_type = userUrlPrivilege.getPrivilege_typeM()[i];
                    String a = role_blocks[i];
                    String[] roles = a.split(",");
                    for (int j = 0; j < roles.length; j++) {
                        if (roles[j].equals("")) {
                            continue;
                        } else {
                            int role_id = getU_Role_Url_Privilege_ID(roles[j], u_url);
                            if (role_id == 0) {
                                failedRoles.add(roles[j]);
                                rowsAffected = -1;   // incase if user tries to insert url which is not present in RoleUrl Map DB
                            } else {
                                try {
                                    pstmt = connection.prepareStatement(query);
                                    pstmt.setInt(1, role_id);
                                    pstmt.setInt(2, getUrl_Detail_ID(u_url, privilege_type));
                                    pstmt.setString(3, userUrlPrivilege.getPrivilegeM()[i]);
                                    pstmt.setString(4, userUrlPrivilege.getActiveM()[i]);
                                    pstmt.setString(5, userUrlPrivilege.getRemarkM()[i]);
                                    rowsAffected = pstmt.executeUpdate();
                                    success++;
                                } catch (Exception e) {
                                    System.out.println("No Error, Unique Key Constraint for RoleID:" + getU_Role_Url_Privilege_ID(roles[j], u_url) + "& Url ID is" + getUrl_Detail_ID(u_url, privilege_type) + " is :" + e);
                                    duplicate++;
                                }
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            rowsAffected = 0;  //-----------
            System.out.println("Error insertRecord role url privilege:" + e);
        }
        if (rowsAffected > 0 || duplicate > 0) {
            if (failedRoles.isEmpty()) {
                message = "No. of Record Sucessfully Saved are " + success + " & No. of Already Existing Entries are " + duplicate;
            } else {
                message = "No. of Record Sucessfully Saved are " + success + " & No. of Already Existing Entries are " + duplicate + " & Roles Failed To Save are " + failedRoles;
            }
            msgBgColor = COLOR_OK;
        } else {
            if (rowsAffected == -1) {
                message = "Cannot save the record, First Insert into Role Url Privilege Map";
            } else {
                message = "Cannot save the record, some error.";
            }
            msgBgColor = COLOR_ERROR;
        }
        return rowsAffected;
    }

    public int updatePrivilegeInBulk(UserPrivilegeBean userUrlPrivilege) {
        String query = "UPDATE u_url_privilege SET privilege = ? WHERE u_url_privilege_id = ? ";
        String[] url_PrivID = userUrlPrivilege.getUrl_privID();
        String[] priv = userUrlPrivilege.getPrivilegeM();
        int rowsAffected = 0;
        int success = 0;
        try {
            for (int i = 0; i < url_PrivID.length; i++) {
                if (!(url_PrivID[i].equals("0"))) {
                    PreparedStatement stmt = connection.prepareStatement(query);
                    stmt.setString(1, priv[i]);
                    stmt.setInt(2, Integer.parseInt(url_PrivID[i]));
                    rowsAffected = stmt.executeUpdate();
                    success = success + rowsAffected;
                }
            }
        } catch (Exception e) {
            System.out.println("Error Inside updatePrivilegeInBulk is:" + e);
        }
        if (rowsAffected > 0) {
            messaged = success + " Records Updated Successfully";
            msgBgColored = COLOR_OK;
        } else {
            messaged = "Cannot update the record, some error.";
            msgBgColored = COLOR_ERROR;
        }
        return rowsAffected;
    }

    public int updateRecord(UserPrivilegeBean bean) {

        int rowsAffected = 0;
        if (!bean.getPrivilege_type().equals("Full")) {

            int url_privilege_id = 0;
            String qry = " select uup.u_url_privilege_id "
                    + " from url_detail ud, u_url_privilege uup, u_url uu, u_role_url_privilege urup, user_roles ur, privilege p "
                    + " where ud.url_detail_id=uup.url_detail_id and uu.u_url_id=ud.url_id and urup.u_url_id=ud.url_id "
                    + " and ur.user_role_id=urup.user_role_id and p.privilege_id=ud.privilege_id and "
                    + " urup.u_role_url_privilege_id=uup.u_role_url_privilege_id "
                    + " and uu.u_url=? and ur.role_name=? and p.privilege_type=? ";

            String query = "UPDATE u_url_privilege SET privilege =?, remark=? "
                    + " WHERE u_url_privilege_id=?";

            try {

                PreparedStatement pst = connection.prepareStatement(qry);
                pst.setString(1, bean.getU_url());
                pst.setString(2, bean.getRole_name());
                pst.setString(3, bean.getPrivilege_type());
                ResultSet rstt = pst.executeQuery();
                while (rstt.next()) {
                    url_privilege_id = rstt.getInt(1);
                }
                if (url_privilege_id > 0) {
                    PreparedStatement psmt = connection.prepareStatement(query);
                    psmt.setString(1, bean.getPrivilege());
                    psmt.setString(2, bean.getRemark());
                    psmt.setInt(3, url_privilege_id);
                    rowsAffected = psmt.executeUpdate();
                } else {
                    message = "Cannot update the record, some error.";
                    msgBgColor = COLOR_ERROR;
                }

            } catch (Exception e) {
                System.out.println("Error updateRecord user role url privileges:" + e);
            }
            if (rowsAffected > 0) {
                message = "Record updated successfully.";
                msgBgColor = COLOR_OK;
            } else {
                message = "Cannot update the record, some error.";
                msgBgColor = COLOR_ERROR;
            }

        } else {
            int url_privilege_id = 0;
            String qry = " select uup.u_url_privilege_id "
                    + " from url_detail ud, u_url_privilege uup, u_url uu, u_role_url_privilege urup, user_roles ur, privilege p "
                    + " where ud.url_detail_id=uup.url_detail_id and uu.u_url_id=ud.url_id and urup.u_url_id=ud.url_id "
                    + " and ur.user_role_id=urup.user_role_id and p.privilege_id=ud.privilege_id and "
                    + " urup.u_role_url_privilege_id=uup.u_role_url_privilege_id "
                    + " and uu.u_url=? and ur.role_name=? ";

            String query = "UPDATE u_url_privilege SET privilege =?, remark=? "
                    + " WHERE u_url_privilege_id=?";

            try {

                PreparedStatement pst = connection.prepareStatement(qry);
                pst.setString(1, bean.getU_url());
                pst.setString(2, bean.getRole_name());
                //pst.setString(3, bean.getPrivilege_type());
                ResultSet rstt = pst.executeQuery();
                while (rstt.next()) {
                    url_privilege_id = rstt.getInt(1);

                    if (url_privilege_id > 0) {
                        PreparedStatement psmt = connection.prepareStatement(query);
                        psmt.setString(1, bean.getPrivilege());
                        psmt.setString(2, bean.getRemark());
                        psmt.setInt(3, url_privilege_id);
                        rowsAffected = psmt.executeUpdate();
                    }

                }

            } catch (Exception e) {
                System.out.println("Error updateRecord user role url privileges:" + e);
            }
            if (rowsAffected > 0) {
                message = "Record updated successfully.";
                msgBgColor = COLOR_OK;
            } else {
                message = "Cannot update the record, some error.";
                msgBgColor = COLOR_ERROR;
            }

        }

        return rowsAffected;
    }

    public int deleteRecord(int u_url_privilege_id) {
        String query = "DELETE FROM u_url_privilege WHERE u_url_privilege_id=" + u_url_privilege_id;
        int rowsAffected = 0;
        try {
            rowsAffected = connection.prepareStatement(query).executeUpdate();
        } catch (Exception e) {
            System.out.println("Error: inside delete : " + e);
        }
        if (rowsAffected > 0) {
            message = "Record deleted successfully.";
            msgBgColor = COLOR_OK;
        } else {
            message = "Record could not be deleted due to some error (ie. u_url_privilege)";
            msgBgColor = COLOR_ERROR;
        }
        return rowsAffected;
    }

    public List<String> getRoleName(String q) {
        List<String> list = new ArrayList<String>();
        String query = "SELECT role_name FROM user_roles GROUP BY role_name ORDER BY role_name ";
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            int count = 0;
            q = q.trim();
            while (rset.next()) {    // move cursor from BOR to valid record.
                String role_name = rset.getString("role_name");
                if (role_name.toUpperCase().startsWith(q.toUpperCase())) {
                    list.add(role_name);
                    count++;
                }
            }
            if (count == 0) {
                list.add("No Such Role Exists.");
            }
        } catch (Exception e) {
            System.out.println("getU_url ERROR inside user role url map - " + e);
        }
        return list;
    }

    public List<String> getPrivilegeType(String q, String url) {
        String qq = null;
        List<String> list = new ArrayList<String>();
        String query = "SELECT privilege_type FROM url_Detail WHERE url_id=(SELECT u_url_id FROM u_url WHERE u_url=?) GROUP BY privilege_type ORDER BY privilege_type";
        try {
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setString(1, url);
            ResultSet rs = stmt.executeQuery();
            int count = 0;
            qq = q.trim();
            while (rs.next()) {
                String u_url = rs.getString("privilege_type");
                if (u_url.toUpperCase().startsWith(qq.toUpperCase())) {
                    list.add(u_url);
                    count++;
                }
            }
            if (count == 0) {
                list.add("No Such Privilege Type Exists.");
            }
        } catch (Exception e) {
            System.out.println("getU_url ERROR inside user role url map - " + e);
        }
        return list;
    }

    public List<String> getU_Url(String q) {
        String qq = null;
        List<String> list = new ArrayList<String>();
        String query = "SELECT u_url FROM u_url uu, u_role_url_privilege urp WHERE urp.u_url_id = uu.u_url_id  GROUP BY  u_url ORDER BY u_url ";
        try {
            PreparedStatement stmt = connection.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();
            int count = 0;
            qq = q.trim();
            while (rs.next()) {
                String u_url = rs.getString("u_url");
                if (u_url.toUpperCase().startsWith(qq.toUpperCase())) {
                    list.add(u_url);
                    count++;
                }
            }
            if (count == 0) {
                list.add("No Such Url Type Exists.");
            }
        } catch (Exception e) {
            System.out.println("getU_url ERROR inside user role url map - " + e);
        }
        return list;
    }

    public int getRoleUrlPrivilegeID(String role_name, String u_url) {
        String query = "SELECT u_role_url_privilege_id "
                + " FROM u_role_url_privilege AS a, user_roles AS b, u_url AS c"
                + " WHERE a.user_role_id = b.user_role_id "
                + " AND a.u_url_id = c.u_url_id "
                + " AND b.role_name='" + role_name + "' "
                + " AND c.u_url ='" + u_url + "'";
        int u_role_url_privilege_id = 0;
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                u_role_url_privilege_id = rs.getInt("u_role_url_privilege_id");
            }
        } catch (Exception e) {
            System.out.println("Exception occured while getting the role url privilege id :");
        }

        return u_role_url_privilege_id;
    }

    public int getUrl_Detail_ID(String url, String privilege_type) {
        int id = 0;
        PreparedStatement pstmt = null;
        String query = "SELECT url_detail_id FROM url_detail WHERE "
                + " privilege_type =?"
                + " AND url_id=(SELECT u_url_id from u_url WHERE u_url=?)";
        try {
            pstmt = connection.prepareStatement(query);
            pstmt.setString(1, privilege_type);
            pstmt.setString(2, url);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                id = rs.getInt("url_detail_id");
            }
        } catch (Exception e) {
            System.out.println("Error occured inside url_detail id of userUrlPrivilege is :" + e);
        }
        System.out.println("url detail id: " + id);
        return id;

    }

    public int getU_Role_Url_Privilege_ID(String role, String url) throws Exception {
        int id = 0;
        PreparedStatement pstmt = null;
        String query = "SELECT u_role_url_privilege_id FROM u_role_url_privilege WHERE "
                + " user_role_id =(SELECT user_role_id FROM user_roles WHERE role_name=?)"
                + " AND u_url_id=(SELECT u_url_id from u_url WHERE u_url=?)";
        pstmt = connection.prepareStatement(query);
        pstmt.setString(1, role);
        pstmt.setString(2, url);
        ResultSet rs = pstmt.executeQuery();
        while (rs.next()) {
            id = rs.getInt("u_role_url_privilege_id");
        }
        System.out.println("priviledge id: " + id);
        return id;
    }

    public byte[] generateUrlUserPrivilege(String jrxmlFilePath, String role_nameSearch, String u_urlSearch) {
        byte[] reportInbytes = null;
        HashMap mymap = new HashMap();
        mymap.put("role_name", role_nameSearch);
        mymap.put("u_url", u_urlSearch);
        try {
            JasperReport compiledReport = JasperCompileManager.compileReport(jrxmlFilePath);
            reportInbytes = JasperRunManager.runReportToPdf(compiledReport, mymap, connection);
        } catch (Exception e) {
            System.out.println("Error: in ComplaintHistoryReportController generatComplaintHistoryReport() JRException: " + e);
        }
        return reportInbytes;
    }

    public String getMessaged() {
        return messaged;
    }

    public void setMessaged(String messaged) {
        this.messaged = messaged;
    }

    public String getMsgBgColored() {
        return msgBgColored;
    }

    public void setMsgBgColored(String msgBgColored) {
        this.msgBgColored = msgBgColored;
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
            System.out.println("UserRoleModel closeConnection() Error: " + e);
        }
    }
    
    
    
    
    public void insertData(){
        PreparedStatement psmt;
        ResultSet rst;
        int count=0;
        String query="";
        try{
            connection.setAutoCommit(false);
            for(int k=1;k<=96;k++){
                for(int l=1;l<=120;l++){
                    query=" insert into apl_test.u_url_privilege(url_detail_id,u_role_url_privilege_id) values(?,?) ";
                    psmt=connection.prepareStatement(query);
                    psmt.setInt(1, l);
                    psmt.setInt(2, k);
                    count=psmt.executeUpdate();
                    if(count>0){
                        connection.commit();
                    }
                }
            }
        }catch(Exception e){
            System.out.println("com.apogee.admin.UserPrivilegeModel.insertData() -"+e);
        }
    }
    
    
    
    
    
    
    
}
