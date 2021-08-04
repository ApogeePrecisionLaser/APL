/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.DBConnection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.servlet.ServletContext;

/**
 *
 * @author Shobha
 */
public class DBConnection {

    //private static BasicDataSource dataSource = null;

    public static Connection getConnection(ServletContext ctx) throws SQLException
    {
        Connection conn = null;
        try {
            Class.forName(ctx.getInitParameter("driverClass"));
            conn = (Connection) DriverManager.getConnection(ctx.getInitParameter("connectionString"), (String) ctx.getInitParameter("db_username"), (String) ctx.getInitParameter("db_password"));
        } catch (Exception e) {
            System.out.println("DBConncetion getConnection() Error: " + e);
        }
        return conn;
    }
   public static synchronized Connection getConnectionForUtf(ServletContext ctx) throws SQLException {
        Connection conn = null;
        try {
            Class.forName(ctx.getInitParameter("driverClass"));
            conn = (Connection) DriverManager.getConnection((String) ctx.getInitParameter("connectionString") + "?useUnicode=true&characterEncoding=UTF-8&character_set_results=utf8", (String) ctx.getInitParameter("db_username"), (String) ctx.getInitParameter("db_password"));
        } catch (Exception e) {
            System.out.println(" getConnectionForUtf() Error: " + e);
        }
        return conn;
    }
    public static void closeConncetion(Connection con) {

        try {
            con.close();
        } catch (Exception e) {
            System.out.println("DBConncetion closeConnection() Error: " + e);
        }

    }

}
