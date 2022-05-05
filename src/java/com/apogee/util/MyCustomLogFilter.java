
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.apogee.util;

/**
 *
 * @author Vikrant
 */
import com.DBConnection.DBConnection;
import com.apogee.util.UrlPrivileges;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class MyCustomLogFilter implements Filter {

    private FilterConfig filterConfig = null;
    private ArrayList<String> urlList;
    private Connection connection;
    private String isSelect;
    private String isSelect2;
    private boolean isRedirection = false;

    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) {
        try {

            //  System.err.println("---------------------------------- Log Filter ---------------------------------------------");
            HttpServletRequest request = (HttpServletRequest) req;
            HttpServletResponse response = (HttpServletResponse) res;
            ServletContext ctx = getFilterConfig().getServletContext();
            int userId = 0;
            String url = request.getServletPath().replace("/", "");
            request.getQueryString();
            request.getRequestURL();
            //HttpSession session = request.getSession(false);
            HttpSession session = request.getSession();
            try {
                try {//                    System.out.println("urlExtention-" + url);
                    //String urlExtention = url.substring((url.lastIndexOf('.') + 1), url.length());
                    //if (urlExtention.equals("do")) {
                    isSelect = "Y";
                    isRedirection = false;
                    try {
                        //if (!(null == session || session.getAttribute("user_id") == null)) {
                        connection = DBConnection.getConnection(ctx, session);
                        List<UrlPrivileges> privilegeList = getUrlPrivileges(url, (String) session.getAttribute("user_role"));
                        req.setAttribute("privilegeList", privilegeList);
                        req.setAttribute("isSelectPriv", isSelect2);
                        DBConnection.closeConncetion(connection);
                        //}
                    } catch (Exception e) {
                        //session.invalidate();
                        response.sendRedirect("/");
                        return;
                    }
                    if (isRedirection) {
                        response.sendRedirect("UserNoPrivilegeController");
                        return;
                    }
                    // }

                } catch (Exception e) {
                    System.out.println("Error Inner Try LogFiletr" + e);
                }
                // System.out.println("req---" + req);
                chain.doFilter(req, res);
            } catch (Exception e) {
                //session.invalidate();
                response.sendRedirect("/");
                System.out.println("Error LogFiletr" + e);
                return;
            }
        } catch (Exception ex) {
            // System.out.println("ERROR -LogFilter-doFilter " + ex);
        }
    }

    public void init(FilterConfig filterConfig) {
        this.filterConfig = filterConfig;
    }

    public FilterConfig getFilterConfig() {
        return filterConfig;
    }

    public void setFilterConfig(FilterConfig filterConfig) {
        this.filterConfig = filterConfig;
    }

    public void destroy() {
    }

    public List<UrlPrivileges> getUrlPrivileges(String url, String role) {
        List<UrlPrivileges> privilegeList = new ArrayList();
        isSelect = "N";
        String privilegeTypeId[];
        String privilegeTypeValue[];

//        String query = " SELECT ut.u_url, ur.role_name, up.u_url_privilege_id, up.privilege,up.active, "
//                + " p.privilege_type, p.privilege_type_id, p.privilege_type_value, "
//                + " urtp.u_role_url_privilege_id, urtp.user_role_id, urtp.u_url_id "
//                + " FROM u_role_url_privilege AS urtp , u_url AS ut, user_roles AS ur, url_detail as ud, u_url_privilege as up,privilege p "
//                + " WHERE urtp.u_url_id = ut.u_url_id AND urtp.user_role_id = ur.user_role_id "
//                + " AND urtp.u_role_url_privilege_id = up.u_role_url_privilege_id and up.url_detail_id = ud.url_detail_id "
//                + " AND ud.url_id = ut.u_url_id and p.privilege_id=ud.privilege_id "
//                + " AND ur.role_name = ? AND ut.u_url = ? ORDER BY p.privilege_type DESC ";
//        String query = " SELECT distinct ut.u_url, ur.role_name, up.privilege,up.active,  p.privilege_type, p.privilege_type_id, p.privilege_id, "
//                + " p.privilege_type_value,  urtp.u_role_url_privilege_id, urtp.user_role_id, urtp.u_url_id "
//                + " FROM u_role_url_privilege AS urtp , u_url AS ut, user_roles AS ur, url_detail as ud, u_url_privilege as up,privilege p "
//                + " WHERE urtp.u_url_id = ut.u_url_id AND urtp.user_role_id = ur.user_role_id "
//                + " AND ud.url_id = ut.u_url_id and p.privilege_id=ud.privilege_id "
//                + " AND ur.role_name = ? AND ut.u_url = ? ORDER BY p.privilege_id ";
//        String query = " select distinct p.privilege_id,p.privilege_type,p.privilege_type_id,p.privilege_type_value, "
//                + " uu.u_url_id,uu.u_url,uup.privilege "
//                + " from privilege p, u_url uu, url_detail ud, user_roles ur, u_role_url_privilege urup,u_url_privilege uup "
//                + " where p.privilege_id=ud.privilege_id and uu.u_url_id=ud.url_id and p.privilege_id=uup.privilege_id and "
//                + " uu.u_url=? and ur.role_name = ? ";
        String query = " select p.privilege_id,p.privilege_type,p.privilege_type_id,p.privilege_type_value,  uu.u_url_id,uu.u_url,uup.privilege "
                + " from url_detail ud, u_url_privilege uup, u_url uu, u_role_url_privilege urup, user_roles ur, privilege p "
                + " where ud.url_detail_id=uup.url_detail_id and uu.u_url_id=ud.url_id and urup.u_url_id=ud.url_id "
                + " and ur.user_role_id=urup.user_role_id and p.privilege_id=ud.privilege_id  "
                + " and urup.u_role_url_privilege_id=uup.u_role_url_privilege_id and "
                + " uu.u_url=? and ur.role_name = ? ";
        try {
            String privilege = "";
            PreparedStatement pst = connection.prepareStatement(query);
            pst.setString(1, url);
            pst.setString(2, role);
            ResultSet rst = pst.executeQuery();
            while (rst.next()) {
                privilege = rst.getString("privilege");
                //if (privilege.equals("Y")) {
                //if (rst.getString("privilege_type").equals("Select")) {
                if (rst.getString("privilege_type").equals("Full")) {
                    isSelect = rst.getString("privilege");
                }

                if (rst.getString("privilege_type").equals("Select")) {
                    isSelect2 = rst.getString("privilege");
                }

//                    privilegeTypeId = rst.getString("privilege_type_id").split("&&");
//                    privilegeTypeValue = rst.getString("privilege_type_value").split("&&");
//                    for (int i = 0; i < privilegeTypeId.length; i++) {
                if (isSelect.equals("N")) {  // In order to hide Data Entry form fully
                    continue;
                } else {
                    UrlPrivileges urlPriv = new UrlPrivileges();
                    urlPriv.setPrivilegeType(rst.getString("privilege_type"));
                    urlPriv.setPrivilegeTypeId(rst.getString(3));
                    urlPriv.setPrivilegeTypeValue(rst.getString(4));
                    urlPriv.setPrivilege(rst.getString("privilege"));
                    privilegeList.add(urlPriv);
                }
                //}
                //}
            }
            if (!privilege.isEmpty() && (privilegeList.size() < 1 && isSelect.equals("N"))) {
                isRedirection = true;
            }

//            if (isSelect.equals("N")) {
//                isRedirection = true;
//            }
        } catch (Exception e) {
            System.out.println("Error Filter getUrlPrivileges:- " + e);
        }

        return privilegeList;
    }
}
