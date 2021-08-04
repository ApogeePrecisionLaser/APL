/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.organization.controller;

import com.organization.model.OrganisationNameModel;
import com.DBConnection.DBConnection;
import com.organization.tableClasses.OrganisationNameBean;
import java.io.ByteArrayOutputStream;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.json.simple.JSONObject;

/**
 *
 * @author Vikrant
 */
public class OrganisationNameController extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        int lowerLimit, noOfRowsTraversed, noOfRowsToDisplay = 10, noOfRowsInTable;
        ServletContext ctx = getServletContext();
        OrganisationNameModel model = new OrganisationNameModel();
        model.setDriverClass(ctx.getInitParameter("driverClass"));
        model.setConnectionString(ctx.getInitParameter("connectionString"));
        model.setDb_username(ctx.getInitParameter("db_user_name"));
        model.setDb_password(ctx.getInitParameter("db_user_password"));
        model.setConnection();
        String task = request.getParameter("task");
        if (task == null) {
            task = "";
        }
        try {

            OrganisationNameBean bean = new OrganisationNameBean();
            bean.setOrganisation_type(request.getParameter("organization_type"));
            bean.setOrganisation_name(request.getParameter("organization_name"));
            bean.setOrganisation_code(request.getParameter("organization_code"));
            bean.setDescription(request.getParameter("description"));
            bean.setRemark(request.getParameter("remark"));

            if (task.equals("Save")) {
                int data = model.saveData(bean);
            }

            List<OrganisationNameBean> list = model.showData();
            request.setAttribute("list", list);
            
            System.err.println("message ----"+model.getMessage());
            request.setAttribute("message", model.getMessage());
            request.setAttribute("msgBgColor", model.getMsgBgColor());
            request.getRequestDispatcher("organization_name").forward(request, response);
        } catch (Exception ex) {
            System.out.println("OrganisationNameController error: " + ex);
        }
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        doGet(request, response);
    }
}
