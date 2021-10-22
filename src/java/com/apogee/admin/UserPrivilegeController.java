/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.apogee.admin;

import com.DBConnection.DBConnection;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Iterator;
import java.util.List;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.json.JSONObject;

/**
 *
 * @author Vikrant
 */
public class UserPrivilegeController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        ServletContext ctx = getServletContext();

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user_name") == null) {
            response.sendRedirect("login.jsp");
            return;
        }
        
        if (!session.getAttribute("myDbUserName").equals("super_admin")) {
            response.sendRedirect("UserNoPrivilegeController");
            return;
        }
        
        UserPrivilegeModel model = new UserPrivilegeModel();
        UserPrivilegeBean bean = new UserPrivilegeBean();
        try {
            String driverClass = session.getAttribute("driverClass").toString();
            String connectionString = session.getAttribute("connectionString").toString();
            String myDbUserName = session.getAttribute("myDbUserName").toString();
            String myDbUserpass = session.getAttribute("myDbUserPass").toString();

            Connection con = DriverManager.getConnection(connectionString, myDbUserName, myDbUserpass);
            model.setConnection(con);
        } catch (Exception e) {
            System.out.println("error in Url Privilege Controller setConnection() calling try block" + e);
        }

        String task = request.getParameter("task");
        if (task == null) {
            task = "";
        }
        if (task.equals("Privilege Update")) {
            bean.setUrl_privID(request.getParameterValues("url_privID"));
            bean.setPrivilegeM(request.getParameterValues("privilegeBulk"));
            model.updatePrivilegeInBulk(bean);
        }

        if (task.equals("Save")) {
            bean.setU_url(request.getParameter("u_url"));
            bean.setRole_name(request.getParameter("role"));
            bean.setPrivilege_type(request.getParameter("privilege_type"));
            bean.setPrivilege(request.getParameter("privilege"));
            bean.setRemark(request.getParameter("remark"));
            model.updateRecord(bean);
        }

//         // Start of Auto Completer code
        String u_urlSearch = "";
        String role_nameSearch = "";
        try {
            //----- This is only for Vendor key Person JQuery
            String JQstring = request.getParameter("action1");
            String q = request.getParameter("str");   // field own input
            if (JQstring != null) {
                PrintWriter out = response.getWriter();
                List<String> list = null;
                if (JQstring.equals("getRoleName")) {
                    list = model.getRoleName(q);
                }
                if (JQstring.equals("getUrl")) {
                    list = model.getU_Url(q);
                }                

                JSONObject gson = new JSONObject();
                gson.put("list", list);
                //System.out.println("gson -" + gson);
                out.println(gson);
                return;
            }
        } catch (Exception e) {
            System.out.println("\n Error --ClientPersonMapController get JQuery Parameters Part-" + e);
        }
//        // End of Auto Completer code
        u_urlSearch = request.getParameter("u_urlSearch");
        role_nameSearch = request.getParameter("role_nameSearch");

        try {
            if (u_urlSearch == null || u_urlSearch.isEmpty()) {
                u_urlSearch = "";

            }
            if (role_nameSearch == null || role_nameSearch.isEmpty()) {
                role_nameSearch = "";
            }
        } catch (Exception e) {
            System.out.println("Exception inside null or empty test of search" + e);
        }
        // Start of Search Table
        if (task.equals("UrlPrivilegeReport")) {
            String jrxmlFilePath;
            response.setHeader("Content-Type", "text/plain; charset=UTF-8");
            response.setContentType("application/pdf");
            ServletOutputStream servletOutputStream = response.getOutputStream();
            jrxmlFilePath = ctx.getRealPath("/report/UserUrlPrivilegeReport.jrxml");
            byte[] reportInbytes = model.generateUrlUserPrivilege(jrxmlFilePath, role_nameSearch, u_urlSearch);
            response.setContentLength(reportInbytes.length);
            servletOutputStream.write(reportInbytes, 0, reportInbytes.length);
            servletOutputStream.flush();
            servletOutputStream.close();
            model.closeConnection();
            return;

        }

        // Logic to show data in the table.
        //model.insertData();
        List<UserPrivilegeBean> url_privilegeList = model.showData(u_urlSearch, role_nameSearch);
        request.setAttribute("url_privilegeList", url_privilegeList);

        // End of Search Table
        System.out.println("color is :" + model.getMsgBgColor());
        //request.setAttribute("IDGenerator", new UniqueIDGenerator());
        request.setAttribute("u_urlSearch", u_urlSearch);
        request.setAttribute("role_nameSearch", role_nameSearch);
        request.setAttribute("message", model.getMessage());
        request.setAttribute("messaged", model.getMessaged());
        request.setAttribute("msgBgColor", model.getMsgBgColor()); //        System.out.println("Going to JSp noww*************************");
        request.setAttribute("msgBgColored", model.getMsgBgColored()); //        System.out.println("Going to JSp noww*************************");
        request.getRequestDispatcher("user_privilege").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}
