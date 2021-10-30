/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.apogee.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.util.Collection;
import java.util.Iterator;

/**
 *
 * @author Vikrant
 */
public class MyCustomTagFunctions {

    public static boolean isContainPrivileges(Collection<?> coll, Object key) {
        boolean isFound = false;
      //  System.err.println("---------------------------apl custom tag functions calling---------------------------");
        try {
            UrlPrivileges bean = null;
            Iterator<UrlPrivileges> iterator = (Iterator<UrlPrivileges>) coll.iterator();
            while (iterator.hasNext()) {
                bean = new UrlPrivileges();
                bean = iterator.next();
                if (bean.getPrivilegeTypeId().equals(key)) {
                    isFound = true;
                    break;
                }
            }
        } catch (Exception e) {
            System.out.println("Bugg : MyCustomTagFunctions isContainPrivileges()- " + e);
        }
        return isFound;
    }

    public static boolean isContainPrivileges2(String user, String url, String qry) {
        boolean isFound = false;
        int id = 0;
      //  System.err.println("---------------------------apl custom tag functions calling---------------------------");
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/apl", "root", "CXKyE2ZpT%HjbP!4c$");

            String query = " select p.privilege_id,p.privilege_type,p.privilege_type_id,p.privilege_type_value,  "
                    + " uu.u_url_id,uu.u_url,uup.privilege  \n"
                    + " from url_detail ud, u_url_privilege uup, u_url uu, u_role_url_privilege urup, user_roles ur, privilege p  \n"
                    + " where ud.url_detail_id=uup.url_detail_id and uu.u_url_id=ud.url_id and urup.u_url_id=ud.url_id  \n"
                    + " and ur.user_role_id=urup.user_role_id and p.privilege_id=ud.privilege_id   "
                    + " and urup.u_role_url_privilege_id=uup.u_role_url_privilege_id \n"
                    + " and  uu.u_url='"+url+"' and ur.role_name = '"+user+"' and p.privilege_type_id='"+qry+"'"
                    + " and uup.privilege='Y' ";
            ResultSet rst = con.prepareStatement(query).executeQuery();
            while (rst.next()) {
                id = rst.getInt(1);
            }
            if(id>0){
                isFound=true;
            }

        } catch (Exception e) {
            System.out.println("Bugg : MyCustomTagFunctions isContainPrivileges()- " + e);
        }
        return isFound;
    }

}
