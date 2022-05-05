/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.organization.model;

import com.organization.tableClasses.Org_Office;
import com.organization.tableClasses.OrganisationDesignationBean;
import java.io.ByteArrayOutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
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
 * @author Dell
 */
/**
 *
 * @author Soft_Tech
 */
public class OrganisationTypeDesignationModel {

    private static Connection connection;
    private String message;
    private String msgBgColor;
    private final String COLOR_OK = "lightyellow";
    private final String COLOR_ERROR = "red";
    private static List<OrganisationDesignationBean> list1 = new ArrayList<OrganisationDesignationBean>();
    static int count = 0;
    static int prev_parent_id = 0;
    static int off_id = 0;

    public void setConnection(Connection con) {
        try {

            connection = con;
        } catch (Exception e) {
            System.out.println("OrgOfficeModel setConnection() Error: " + e);
        }
    }

    public int getNoOfRows() {
        int noOfRows = 0;
        try {
            ResultSet rset = connection.prepareStatement(" select count(*) from org_office ").executeQuery();
            rset.next();    // move cursor from BOR to valid record.
            noOfRows = Integer.parseInt(rset.getString(1));
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
        return noOfRows;
    }

    public int getNoOfRows(String org_name, String designation, String getgeneration, String active, String searchhierarchy) {
        int noOfRowNUM = 0;
        int org_id = getOrganisation_id(org_name);

        String id = "";

        if (org_id != 0) {
            id = String.valueOf(org_id);
        } else {
            id = "";
        }
        list1.clear();
        int org_idd = OrganisationTypeDesignationModel.getDesgnid(searchhierarchy);
        count = 0;
        int count = 0;
        int o_id = 0;
        if (searchhierarchy.isEmpty()) {
            count = 0;
        } else {
            count++;
        }
        // Use DESC or ASC for descending or ascending order respectively of fetched data.
        if (count > 0) {

            list1 = OrganisationTypeDesignationModel.showHierarchyParentData(0, 50, org_idd, org_name, designation, getgeneration, active, searchhierarchy, o_id);
            noOfRowNUM = list1.size();
            return noOfRowNUM;
        }
        try {
            org_name = (org_name);

//            String query = "  SELECT COUNT(*) as count "
//                    + " FROM  org_office AS o LEFT JOIN org_office_type AS oft ON oft.office_type_id = o.office_type_id , "
//                    + " city AS c, organisation_name AS org "
//                    + " WHERE  org.organisation_id=o.organisation_id "
//                    // + " AND o.city_id = c.city_id "
//                    //  + " AND c.state_id = s.state_id "
//                    + " AND IF ('" + org_name + "' = '' , organisation_name LIKE '%%',organisation_name= ?) "
//                    //+ " AND IF (" + office_code_search + " = '' , o.org_office_code LIKE '%%',o.org_office_code LIKE  "+office_code_search+".%' OR o.org_office_code like ?) "
//                    + " AND IF('" + office_code_search + "'='' ,o.org_office_code LIKE '%%',o.org_office_code LIKE '" + office_code_search + ".%' OR o.org_office_code like ?)"
//                    + " AND IF ('" + office_name_search + "' = '' , o.org_office_name LIKE '%%',o.org_office_name = ?) "
//                    + " ORDER BY org.organisation_name,o.org_office_name ";
            String query = " select od.id,od.is_super_child,o.org_type_name,d.designation, "
                    + " od.parent_designation_id,od.generation "
                    //+ " (select a.designation from designation a,organisation_designation b where a.designation_id=b.organisation_designation_map_id_2 "
                    //+ " and a.active='Y')as desigantion_map_2 "
                    + " from designation_organisation_type_map od, organisation_type o, designation d "
                    + " where od.organisation_type_id=o.organisation_type_id and o.active='Y' "
                    + " and d.designation_id=od.designation_id and d.active='Y'  "
                    + " AND IF ('" + active + "' = '' , od.active LIKE '%%',od.active= ?) "
                    + " AND IF ('" + org_name + "' = '' , o.org_type_name LIKE '%%',o.org_type_name= ?) "
                    + " AND IF ('" + getgeneration + "' = '' , od.generation LIKE '%%',od.generation= ?) "
                    //+ " or d.designation_id=od.organisation_designation_map_id_2 and d.active='Y' "                                       
                    + "  order by od.id ";
            PreparedStatement psmt = connection.prepareStatement(query);
            psmt.setString(1, active);
            psmt.setString(2, org_name);
            psmt.setString(3, getgeneration);
            ResultSet rset = psmt.executeQuery();
            while (rset.next()) {// move cursor from BOR to valid record.
                noOfRowNUM++;
            }
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
        return noOfRowNUM;
    }

    public List<OrganisationDesignationBean> showPDF(String org_name, String designation, String getgeneration, String active, String searchhierarchy) {
        PreparedStatement pstmt;
        String query;
        List<OrganisationDesignationBean> list = new ArrayList<OrganisationDesignationBean>();

        list1.clear();
        int org_id = OrganisationTypeDesignationModel.getDesgnid(searchhierarchy);
        count = 0;
        int count = 0;
        int o_id = 0;
        if (searchhierarchy.isEmpty()) {
            count = 0;
        } else {
            count++;
        }
        // Use DESC or ASC for descending or ascending order respectively of fetched data.
        if (count > 0) {

            list1 = OrganisationTypeDesignationModel.showHierarchyParentData(0, 50, org_id, org_name, designation, getgeneration, active, searchhierarchy, o_id);
            return list1;
        }

        try {
//            org_name = krutiToUnicode.convert_to_unicode(org_name);
//            office_name_search = krutiToUnicode.convert_to_unicode(office_name_search);
            // Use DESC or ASC for descending or ascending order respectively of fetched data.

//            query = " select odm.organisation_designation_map_id, o.organisation_name,d.designation,odm.serial_no,odm.super,odm.parent_designation from organisation_name o ,designation d,organisation_designation_map odm\n" +
//                    " where o.organisation_id=odm.organisation_id and d.designation_id=odm.designation_id and o.active='Y' and d.active='Y' and odm.active='Y'"
//                   
//                 
//                    + " AND IF ('" + org_name + "' = '' , organisation_name LIKE '%%',organisation_name= ?) "
//                     + " AND IF ('" + designation + "' = '' , designation LIKE '%%',designation= ?) "
////                    //+ " AND IF (" + office_code_search + " = '' , o.org_office_code LIKE '%%',o.org_office_code LIKE  "+office_code_search+".%' OR o.org_office_code like ?) "
////                    + " AND IF('" + office_code_search + "'='' ,o.org_office_code LIKE '%%',o.org_office_code LIKE '"+office_code_search+".%' OR o.org_office_code like ?)"
////                    + " AND IF ('" + office_name_search+ "' = '' , o.org_office_name LIKE '%%',o.org_office_name = ?) "
//
//                    + " LIMIT " + lowerLimit + ", " + noOfRowsToDisplay;
            query = " select od.id,od.is_super_child,o.org_type_name,d.designation, "
                    + " od.parent_designation_id,od.generation "
                    //+ " (select a.designation from designation a,organisation_designation b where a.designation_id=b.organisation_designation_map_id_2 "
                    //+ " and a.active='Y')as desigantion_map_2 "
                    + " from designation_organisation_type_map od, organisation_type o, designation d "
                    + " where od.organisation_type_id=o.organisation_type_id and o.active='Y' "
                    + " and d.designation_id=od.designation_id and d.active='Y'  "
                    + " AND IF ('" + active + "' = '' , od.active LIKE '%%',od.active= ?) "
                    + " AND IF ('" + org_name + "' = '' , o.org_type_name LIKE '%%',o.org_type_name= ?) "
                    + " AND IF ('" + getgeneration + "' = '' , od.generation LIKE '%%',od.generation= ?) "
                    //+ " or d.designation_id=od.organisation_designation_map_id_2 and d.active='Y' "                                       
                    + "  order by od.id ";

            System.out.println("query show all data -" + query);

            pstmt = connection.prepareStatement(query);
            pstmt.setString(1, active);
            pstmt.setString(2, org_name);
            pstmt.setString(3, getgeneration);
//            pstmt.setString(3, office_name_search);
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {

                int id = rset.getInt("id");
//                int desigid=rset.getInt("designation_id");                    
//                int orgid=rset.getInt("organisation_id");
                //String serialno = rset.getString("serial_no");
                String superp = rset.getString("is_super_child");
                String designame = rset.getString("designation");
                //String desigName2=rset.getString("desigantion_map_2");
                String orgname = rset.getString("org_type_name");;
                String desigName2 = rset.getString("parent_designation_id");
                String generation = String.valueOf(rset.getInt("generation"));

                String mapId2 = getDesignation_name(desigName2);

                OrganisationDesignationBean organisation = new OrganisationDesignationBean();
                organisation.setId(id);
                organisation.setOrganisation(orgname);
                organisation.setDesignation(designame);
                //organisation.setSerialnumber(serialno);
                organisation.setSuperp(superp);
                organisation.setP_designation(mapId2);
                organisation.setGeneration(generation);
                list.add(organisation);
            }
        } catch (Exception e) {
            System.out.println("Error:--organisation--- showData--" + e);
        }
        return list;
    }

    public static int getParentOrgid(int org_id) {
        String query = " SELECT parent_designation_id FROM designation_organisation_type_map WHERE  designation_id=? and active=? ";
        int organisation_id = 0;
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setInt(1, org_id);

            pstmt.setString(2, "Y");
            ResultSet rset = pstmt.executeQuery();
            rset.next();    // move cursor from BOR to valid record.
            organisation_id = rset.getInt("parent_designation_id");
        } catch (Exception e) {
            System.out.println("Error: OrganisationMapModel--" + e);
        }
        return organisation_id;
    }

    public static int getcheckorgid(int org_id) {
        String query = " SELECT designation_id FROM designation_organisation_type_map WHERE  parent_designation_id=? and active=? ";
        int organisation_id = 0;
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setInt(1, org_id);

            pstmt.setString(2, "Y");
            ResultSet rset = pstmt.executeQuery();
            rset.next();    // move cursor from BOR to valid record.
            organisation_id = rset.getInt("designation_id");
        } catch (Exception e) {
            System.out.println("Error: OrganisationMapModel--" + e);
        }
        return organisation_id;
    }

    public static int getDesgnid(String org_id) {
        String query = " SELECT designation_id FROM designation WHERE  designation=? and active=? ";
        int organisation_id = 0;
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, org_id);

            pstmt.setString(2, "Y");
            ResultSet rset = pstmt.executeQuery();
            rset.next();    // move cursor from BOR to valid record.
            organisation_id = rset.getInt("designation_id");
        } catch (Exception e) {
            System.out.println("Error: OrganisationMapModel--" + e);
        }
        return organisation_id;
    }

    public List<OrganisationDesignationBean> showData(int lowerLimit, int noOfRowsToDisplay, String org_name, String designation, String getgeneration, String active, String searchhierarchy) {
        PreparedStatement pstmt;
        String query;
        List<OrganisationDesignationBean> list = new ArrayList<OrganisationDesignationBean>();

        list1.clear();
        int org_id = OrganisationTypeDesignationModel.getDesgnid(searchhierarchy);
        count = 0;
        int count = 0;
        int o_id = 0;
        if (searchhierarchy.isEmpty()) {
            count = 0;
        } else {
            count++;
        }
        // Use DESC or ASC for descending or ascending order respectively of fetched data.
        if (count > 0) {

            list1 = OrganisationTypeDesignationModel.showHierarchyParentData(lowerLimit, noOfRowsToDisplay, org_id, org_name, designation, getgeneration, active, searchhierarchy, o_id);
            return list1;
        }

        try {
//            org_name = krutiToUnicode.convert_to_unicode(org_name);
//            office_name_search = krutiToUnicode.convert_to_unicode(office_name_search);
            // Use DESC or ASC for descending or ascending order respectively of fetched data.

//            query = " select odm.organisation_designation_map_id, o.organisation_name,d.designation,odm.serial_no,odm.super,odm.parent_designation from organisation_name o ,designation d,organisation_designation_map odm\n" +
//                    " where o.organisation_id=odm.organisation_id and d.designation_id=odm.designation_id and o.active='Y' and d.active='Y' and odm.active='Y'"
//                   
//                 
//                    + " AND IF ('" + org_name + "' = '' , organisation_name LIKE '%%',organisation_name= ?) "
//                     + " AND IF ('" + designation + "' = '' , designation LIKE '%%',designation= ?) "
////                    //+ " AND IF (" + office_code_search + " = '' , o.org_office_code LIKE '%%',o.org_office_code LIKE  "+office_code_search+".%' OR o.org_office_code like ?) "
////                    + " AND IF('" + office_code_search + "'='' ,o.org_office_code LIKE '%%',o.org_office_code LIKE '"+office_code_search+".%' OR o.org_office_code like ?)"
////                    + " AND IF ('" + office_name_search+ "' = '' , o.org_office_name LIKE '%%',o.org_office_name = ?) "
//
//                    + " LIMIT " + lowerLimit + ", " + noOfRowsToDisplay;
            query = " select od.id,od.is_super_child,o.org_type_name,d.designation, "
                    + " od.parent_designation_id,od.generation "
                    //+ " (select a.designation from designation a,organisation_designation b where a.designation_id=b.organisation_designation_map_id_2 "
                    //+ " and a.active='Y')as desigantion_map_2 "
                    + " from designation_organisation_type_map od, organisation_type o, designation d "
                    + " where od.organisation_type_id=o.organisation_type_id and o.active='Y' "
                    + " and d.designation_id=od.designation_id and d.active='Y'  "
                    + " AND IF ('" + active + "' = '' , od.active LIKE '%%',od.active= ?) "
                    + " AND IF ('" + org_name + "' = '' , o.org_type_name LIKE '%%',o.org_type_name= ?) "
                    + " AND IF ('" + getgeneration + "' = '' , od.generation LIKE '%%',od.generation= ?) "
                    //+ " or d.designation_id=od.organisation_designation_map_id_2 and d.active='Y' "                                       
                    + "  order by od.id LIMIT " + lowerLimit + ", " + noOfRowsToDisplay;

            System.out.println("query show all data -" + query);

            pstmt = connection.prepareStatement(query);
            pstmt.setString(1, active);
            pstmt.setString(2, org_name);
            pstmt.setString(3, getgeneration);
//            pstmt.setString(3, office_name_search);
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {

                int id = rset.getInt("id");
//                int desigid=rset.getInt("designation_id");                    
//                int orgid=rset.getInt("organisation_id");
                //String serialno = rset.getString("serial_no");
                String superp = rset.getString("is_super_child");
                String designame = rset.getString("designation");
                //String desigName2=rset.getString("desigantion_map_2");
                String orgname = rset.getString("org_type_name");;
                String desigName2 = rset.getString("parent_designation_id");
                String generation = String.valueOf(rset.getInt("generation"));

                String mapId2 = getDesignation_name(desigName2);

                OrganisationDesignationBean organisation = new OrganisationDesignationBean();
                organisation.setId(id);
                organisation.setOrganisation(orgname);
                organisation.setDesignation(designame);
                //organisation.setSerialnumber(serialno);
                organisation.setSuperp(superp);
                organisation.setP_designation(mapId2);
                organisation.setGeneration(generation);
                list.add(organisation);
            }
        } catch (Exception e) {
            System.out.println("Error:--organisation--- showData--" + e);
        }
        return list;
    }

    public static List<OrganisationDesignationBean> showHierarchyParentData(int lowerLimit, int noOfRowsToDisplay, int org_id, String org_name, String designation, String getgeneration, String active, String searchhierarchy, int o_id) {
        int id = 0;

        //    int org_id  = OrgOfficeModel.getOrgid(searchhierarchy);
        String p_idd = "";

//            String query = " SELECT organisation_type_id, org_type_name, description,parent_org_id,super,generation "
//                              + " FROM organisation_type where   "
//                        + " IF('" + active + "' = '', active LIKE '%%',active =?) and "
//                             + " IF('" + org_id + "' = '', organisation_type_id LIKE '%%',organisation_type_id =?)   "
////                           + "  IF('" + o_id + "' = '', parent_org_id LIKE '%%',parent_org_id =?) "
//                              + " ORDER BY generation asc "
//                              + " LIMIT " + lowerLimit + ", " + noOfRowsToDisplay;
        String query = " select od.id,od.is_super_child,o.org_type_name,d.designation, "
                + " od.parent_designation_id,od.generation,od.designation_id "
                //+ " (select a.designation from designation a,organisation_designation b where a.designation_id=b.organisation_designation_map_id_2 "
                //+ " and a.active='Y')as desigantion_map_2 "
                + " from designation_organisation_type_map od, organisation_type o, designation d "
                + " where od.organisation_type_id=o.organisation_type_id and o.active='Y' "
                + " and d.designation_id=od.designation_id and d.active='Y'  "
                + " AND IF ('" + active + "' = '' , od.active LIKE '%%',od.active= ?) "
                + " AND IF ('" + org_name + "' = '' , o.org_type_name LIKE '%%',o.org_type_name= ?) "
                + " AND IF ('" + getgeneration + "' = '' , od.generation LIKE '%%',od.generation= ?) "
                + " AND IF('" + org_id + "' = '', od.designation_id LIKE '%%',od.designation_id =?) order by generation  "
                + " LIMIT " + lowerLimit + ", " + noOfRowsToDisplay;

        try {
            PreparedStatement pstmt = connection.prepareStatement(query);

            pstmt.setString(1, active);
            pstmt.setString(2, org_name);
            pstmt.setString(3, getgeneration);
            pstmt.setInt(4, org_id);
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {

//                int id =rset.getInt("organisation_type_id");
//                organisationType.setOrganisation_type_id(rset.getInt("organisation_type_id"));
//                o_id =OrganisationTypeModel.getParentOrgid(id);
//               
//               
//                
//                organisationType.setOrg_type_name((rset.getString("org_type_name")));
//                organisationType.setDescription((rset.getString("description")));
//                String p_ot_id=rset.getString("parent_org_id");
//                organisationType.setP_ot(getParentOrgname(Integer.parseInt(p_ot_id)));
//                              
//                  organisationType.setSupper(rset.getString("super"));
//                  organisationType.setGeneration(rset.getInt("generation"));
//                   list1.add(organisationType);
                int desigid = rset.getInt("designation_id");

//                int orgid=rset.getInt("organisation_id");
                //String serialno = rset.getString("serial_no");
                String superp = rset.getString("is_super_child");
                String designame = rset.getString("designation");
                //String desigName2=rset.getString("desigantion_map_2");
                String orgname = rset.getString("org_type_name");;
                String desigName2 = rset.getString("parent_designation_id");
                String generation = String.valueOf(rset.getInt("generation"));

                String mapId2 = getDesignation_name(desigName2);
                o_id = OrganisationTypeDesignationModel.getParentOrgid(org_id);
                off_id = OrganisationTypeDesignationModel.getcheckorgid(org_id);
                OrganisationDesignationBean organisation = new OrganisationDesignationBean();
                organisation.setId(id);
                organisation.setOrganisation(orgname);
                organisation.setDesignation(designame);
                //organisation.setSerialnumber(serialno);
                organisation.setSuperp(superp);
                organisation.setP_designation(mapId2);
                organisation.setGeneration(generation);

                list1.add(organisation);
                count++;
            }
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
        if (count > 1) {
            showHierarchyData2(lowerLimit, noOfRowsToDisplay, o_id, org_name, designation, getgeneration, active, searchhierarchy, o_id);
        }

        showHierarchyData(lowerLimit, noOfRowsToDisplay, org_id, org_name, designation, getgeneration, active, searchhierarchy, o_id);

        //   }
        return list1;
    }

    public static List<OrganisationDesignationBean> showHierarchyData(int lowerLimit, int noOfRowsToDisplay, int org_id, String org_name, String designation, String getgeneration, String active, String searchhierarchy, int o_id) {
        int id = 0;

        //   int org_id  = OrgOfficeModel.getOrgid(searchhierarchy);
        String p_idd = "";

        List<Integer> idList = new ArrayList<Integer>();

//            String query = " SELECT organisation_type_id, org_type_name, description,parent_org_id,super,generation "
//                              + " FROM organisation_type where  organisation_type_id="+org_id+" and "
//                       
//                             + " IF('" + active + "' = '', active LIKE '%%',active =?)  "
//                           //  + "  IF('" + searchhierarchy + "' = '', org_type_name LIKE '%%',org_type_name =?) "
//                              + " ORDER BY generation asc "
//                              + " LIMIT " + lowerLimit + ", " + noOfRowsToDisplay;
//            
        String query = " select od.id,od.is_super_child,o.org_type_name,d.designation, "
                + " od.parent_designation_id,od.generation,od.designation_id "
                //+ " (select a.designation from designation a,organisation_designation b where a.designation_id=b.organisation_designation_map_id_2 "
                //+ " and a.active='Y')as desigantion_map_2 "
                + " from designation_organisation_type_map od, organisation_type o, designation d "
                + " where od.organisation_type_id=o.organisation_type_id and o.active='Y' "
                + " and d.designation_id=od.designation_id and d.active='Y'  "
                + " AND IF ('" + active + "' = '' , od.active LIKE '%%',od.active= ?) "
                + " AND IF ('" + org_name + "' = '' , o.org_type_name LIKE '%%',o.org_type_name= ?) "
                + " AND IF ('" + getgeneration + "' = '' , od.generation LIKE '%%',od.generation= ?) "
                + " AND IF('" + org_id + "' = '', od.parent_designation_id LIKE '%%',od.parent_designation_id =?) order by generation  "
                + " LIMIT " + lowerLimit + ", " + noOfRowsToDisplay;

        try {
            PreparedStatement pstmt = connection.prepareStatement(query);

            pstmt.setString(1, active);
            pstmt.setString(2, org_name);
            pstmt.setString(3, getgeneration);
            pstmt.setInt(4, org_id);
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {

//                int id =rset.getInt("organisation_type_id");
//                organisationType.setOrganisation_type_id(rset.getInt("organisation_type_id"));
//                o_id =OrganisationTypeModel.getParentOrgid(id);
//               
//               
//                
//                organisationType.setOrg_type_name((rset.getString("org_type_name")));
//                organisationType.setDescription((rset.getString("description")));
//                String p_ot_id=rset.getString("parent_org_id");
//                organisationType.setP_ot(getParentOrgname(Integer.parseInt(p_ot_id)));
//                              
//                  organisationType.setSupper(rset.getString("super"));
//                  organisationType.setGeneration(rset.getInt("generation"));
//                   list1.add(organisationType);
                int desigid = rset.getInt("designation_id");
//                int orgid=rset.getInt("organisation_id");
                //String serialno = rset.getString("serial_no");
                String superp = rset.getString("is_super_child");
                String designame = rset.getString("designation");
                //String desigName2=rset.getString("desigantion_map_2");
                String orgname = rset.getString("org_type_name");;
                String desigName2 = rset.getString("parent_designation_id");
                String generation = String.valueOf(rset.getInt("generation"));

                String mapId2 = getDesignation_name(desigName2);
                o_id = OrganisationTypeDesignationModel.getParentOrgid(desigid);
                off_id = OrganisationTypeDesignationModel.getcheckorgid(desigid);
                if (o_id == 0) {
                    break;

                }
                OrganisationDesignationBean organisation = new OrganisationDesignationBean();
                organisation.setId(id);
                organisation.setOrganisation(orgname);
                organisation.setDesignation(designame);
                //organisation.setSerialnumber(serialno);
                organisation.setSuperp(superp);
                organisation.setP_designation(mapId2);
                organisation.setGeneration(generation);

                list1.add(organisation);
                if (off_id != 0) {
                    showHierarchyData2(lowerLimit, noOfRowsToDisplay, desigid, org_name, designation, getgeneration, active, searchhierarchy, o_id);
                }
            }

        } catch (Exception e) {
            System.out.println("Error: " + e);
        }

        if (o_id != 0) {
            //  showHierarchyData(lowerLimit,noOfRowsToDisplay,id,org_name,office_code_search,office_name_search,mobile,generation,active,searchhierarchy,o_id);
        }
//}
        return list1;
    }

    public static List<OrganisationDesignationBean> showHierarchyData2(int lowerLimit, int noOfRowsToDisplay, int org_id, String org_name, String designation, String getgeneration, String active, String searchhierarchy, int o_id) {
        int id = 0;

        //   int org_id  = OrgOfficeModel.getOrgid(searchhierarchy);
        String p_idd = "";
        int desigid = 0;

//            String query = " SELECT organisation_type_id, org_type_name, description,parent_org_id,super,generation "
//                              + " FROM organisation_type where  organisation_type_id="+org_id+" and "
//                       
//                             + " IF('" + active + "' = '', active LIKE '%%',active =?)  "
//                           //  + "  IF('" + searchhierarchy + "' = '', org_type_name LIKE '%%',org_type_name =?) "
//                              + " ORDER BY generation asc "
//                              + " LIMIT " + lowerLimit + ", " + noOfRowsToDisplay;
//            
        String query = " select od.id,od.is_super_child,o.org_type_name,d.designation, "
                + " od.parent_designation_id,od.generation,od.designation_id "
                //+ " (select a.designation from designation a,organisation_designation b where a.designation_id=b.organisation_designation_map_id_2 "
                //+ " and a.active='Y')as desigantion_map_2 "
                + " from designation_organisation_type_map od, organisation_type o, designation d "
                + " where od.organisation_type_id=o.organisation_type_id and o.active='Y' "
                + " and d.designation_id=od.designation_id and d.active='Y'  "
                + " AND IF ('" + active + "' = '' , od.active LIKE '%%',od.active= ?) "
                + " AND IF ('" + org_name + "' = '' , o.org_type_name LIKE '%%',o.org_type_name= ?) "
                + " AND IF ('" + getgeneration + "' = '' , od.generation LIKE '%%',od.generation= ?) "
                + " AND IF('" + org_id + "' = '', od.parent_designation_id LIKE '%%',od.parent_designation_id =?) order by generation  "
                + "  LIMIT " + lowerLimit + ", " + noOfRowsToDisplay;

        try {
            PreparedStatement pstmt = connection.prepareStatement(query);

            pstmt.setString(1, active);
            pstmt.setString(2, org_name);
            pstmt.setString(3, getgeneration);
            pstmt.setInt(4, org_id);
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {

//                int id =rset.getInt("organisation_type_id");
//                organisationType.setOrganisation_type_id(rset.getInt("organisation_type_id"));
//                o_id =OrganisationTypeModel.getParentOrgid(id);
//               
//               
//                
//                organisationType.setOrg_type_name((rset.getString("org_type_name")));
//                organisationType.setDescription((rset.getString("description")));
//                String p_ot_id=rset.getString("parent_org_id");
//                organisationType.setP_ot(getParentOrgname(Integer.parseInt(p_ot_id)));
//                              
//                  organisationType.setSupper(rset.getString("super"));
//                  organisationType.setGeneration(rset.getInt("generation"));
//                   list1.add(organisationType);
                desigid = rset.getInt("designation_id");
//                int orgid=rset.getInt("organisation_id");
                //String serialno = rset.getString("serial_no");
                String superp = rset.getString("is_super_child");
                String designame = rset.getString("designation");
                //String desigName2=rset.getString("desigantion_map_2");
                String orgname = rset.getString("org_type_name");;
                String desigName2 = rset.getString("parent_designation_id");
                String generation = String.valueOf(rset.getInt("generation"));

                String mapId2 = getDesignation_name(desigName2);
                o_id = OrganisationTypeDesignationModel.getParentOrgid(desigid);
                off_id = OrganisationTypeDesignationModel.getcheckorgid(desigid);
                OrganisationDesignationBean organisation = new OrganisationDesignationBean();
                organisation.setId(id);
                organisation.setOrganisation(orgname);
                organisation.setDesignation(designame);
                //organisation.setSerialnumber(serialno);
                organisation.setSuperp(superp);
                organisation.setP_designation(mapId2);
                organisation.setGeneration(generation);

                list1.add(organisation);

                if (off_id != 0) {
                    showHierarchyData2(lowerLimit, noOfRowsToDisplay, desigid, org_name, designation, generation, active, searchhierarchy, o_id);
                }

//                if(off_id!=0 )
//                {
//                    count++;
//                  showHierarchyParentData(lowerLimit,noOfRowsToDisplay,off_id,org_name,office_code_search,office_name_search,mobile,generation,active,searchhierarchy,o_id);
//                }
                if (off_id != 0) {
                    showHierarchyData2(lowerLimit, noOfRowsToDisplay, desigid, org_name, designation, getgeneration, active, searchhierarchy, o_id);
                }
            }
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }

        if (off_id != 0) {
            showHierarchyData2(lowerLimit, noOfRowsToDisplay, desigid, org_name, designation, getgeneration, active, searchhierarchy, o_id);
        }
        // }
        return list1;
    }

    public int getIdOfParent(String number) {
        int id = 0;
        String query1 = " SELECT organisation_designation_map_id FROM organisation_designation_map WHERE serial_no =?  && active=? ";
        try {
            PreparedStatement pstmt = connection.prepareStatement(query1);
            pstmt.setString(1, number);
            pstmt.setString(2, "Y");

            ResultSet rset = pstmt.executeQuery();

            while (rset.next()) {
                id = rset.getInt("organisation_designation_map_id");

            }

        } catch (Exception ex) {

        }

        return id;
    }

    public String getDesigNameFromId(int id) {
        String name = "";
        String query1 = " SELECT designation FROM designation WHERE designation_id =?  && active=? ";
        try {
            PreparedStatement pstmt = connection.prepareStatement(query1);
            pstmt.setInt(1, id);
            pstmt.setString(2, "Y");

            ResultSet rset = pstmt.executeQuery();

            while (rset.next()) {
                name = rset.getString("designation");

            }

        } catch (Exception ex) {

        }

        return name;
    }

    public String getOrgNameFromId(int id) {
        String name = "";
        String query1 = " SELECT organisation_name FROM organisation_name WHERE organisation_id =?  && active=? ";
        try {
            PreparedStatement pstmt = connection.prepareStatement(query1);
            pstmt.setInt(1, id);
            pstmt.setString(2, "Y");

            ResultSet rset = pstmt.executeQuery();

            while (rset.next()) {
                name = rset.getString("organisation_name");

            }

        } catch (Exception ex) {

        }

        return name;
    }

    // edited by Vikrant
    public int insertRecord(OrganisationDesignationBean bean) {

        String is_child = "", active = "";
        int rowsAffected = 0;
        int count = 0;
        is_child = bean.getSuperp().trim();
        active = bean.getIsActive();
        if (is_child != null) {
            if (is_child.equals("yes") || is_child.equals("Yes") || is_child.equals("YES") || is_child.equals("Y") || is_child.equals("y")) {
                is_child = "Y";
            } else {
                is_child = "N";
            }
        }
        int orgid = getOrganisation_id(bean.getOrganisation());
        int desigid1 = getDesignation_id(bean.getDesignation());
        int desigid2 = getDesignation_id(bean.getP_designation());
        int generation = 0;
        if (desigid2 == 0) {
            generation = 1;
        } else {
            generation = getParentGeneration(orgid, desigid2) + 1;

        }
        if (desigid1 == desigid2) {
            message = "Sorry! Parent-Child cannot be same!";
            msgBgColor = COLOR_ERROR;
            return rowsAffected;
        }

        // to check if parent exist or not
        String qry2 = " select count(*) from designation_organisation_type_map where organisation_type_id='" + orgid + "' and "
                + " designation_id='" + desigid1 + "' and active='Y' ";
        try {
            PreparedStatement pst1 = connection.prepareStatement(qry2);
            System.out.println("query for check -" + pst1);
            ResultSet rst1 = pst1.executeQuery();
            while (rst1.next()) {
                count = rst1.getInt(1);
            }
        } catch (Exception e) {
            System.out.println("error in insertRecord model -" + e);
        }
        if (count > 0) {
            message = "Cannot save the record, already mapped!";
            msgBgColor = COLOR_ERROR;
            return rowsAffected;
        }

        //
        String query1 = " select count(*) "
                + " from designation_organisation_type_map where organisation_type_id='" + orgid + "' "
                + " and designation_id='" + desigid2 + "' and "
                + " parent_designation_id='" + desigid1 + "' and active='Y' ";

        try {
            PreparedStatement pst = connection.prepareStatement(query1);
            System.out.println("query for check -" + pst);
            ResultSet rst = pst.executeQuery();
            while (rst.next()) {
                count = rst.getInt(1);
            }
        } catch (Exception e) {
            System.out.println("error in insertRecord model -" + e);
        }
        if (count > 0) {
            message = "Cannot save the record, already mapped!";
            msgBgColor = COLOR_ERROR;
            return rowsAffected;
        }

        String query = " insert into designation_organisation_type_map(organisation_type_id,designation_id, "
                + " parent_designation_id,is_super_child,active,revision_no,remark,created_by,created_at,generation) "
                + " values (?,?,?,?,?,?,?,?,now(),?) ";

        //int rowsAffected = 0;
        try {
            java.sql.PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setInt(1, orgid);
            pstmt.setInt(2, desigid1);
            pstmt.setInt(3, desigid2);
            pstmt.setString(4, is_child);
            pstmt.setString(5, "Y");
            pstmt.setString(6, "0");
            pstmt.setString(7, bean.getRemark());
            pstmt.setString(8, "Shubham");
            pstmt.setInt(9, generation);
            //pstmt.setString(9, "0");

            System.out.println("insert query -" + pstmt);

            rowsAffected = pstmt.executeUpdate();
        } catch (Exception e) {
            System.out.println("Error while inserting record in insertRecord...." + e);
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

//    public int insertRecord(OrganisationDesignationBean orgOffice) {
//        String superstatus = orgOffice.getSuperp();
//        String parent_desg = orgOffice.getP_designation();
//        if (superstatus.equals("") || parent_desg.equals("")) {
//            superstatus = "yes";
//        } else {
//            superstatus = "no";
//        }
//        String serialnumber = orgOffice.getSerialnumber();
//        int orgid = getOrganisation_id(orgOffice.getOrganisation());
//        int desigid = getDesignation_id(orgOffice.getDesignation());
//        int key = 0;
////        String query = "INSERT INTO "
////                + "organisation_designation_map( organisation_id,designation_id,serial_no, super,revision,active,parent_designation) "
////        
////                + "VALUES( ?, ?, ?, ?,?,?,?)";
//
//        String query = "insert into organisation_designation(organisation_id,organisation_designation_map_id_1,"
//                + " organisation_designation_map_id_2,is_super_child,active,revision_no,remark,created_by,created_at) "
//                + " values(?,?,?,?,?,?,?,?,now()) ";
//
//        int rowsAffected = 0;
//        try {
//            PreparedStatement pstmt = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
//
//            pstmt.setInt(1, orgid);
//            pstmt.setInt(2, desigid);
//            pstmt.setString(3, serialnumber);
//            pstmt.setString(4, superstatus);
//            pstmt.setString(5, "0");
//            pstmt.setString(6, "Y");
//            pstmt.setString(7, parent_desg);
//
//            rowsAffected = pstmt.executeUpdate();
//            ResultSet rs = pstmt.getGeneratedKeys();
//            if (rs != null && rs.next()) {
//                key = rs.getInt(1);
//            }
//
//        } catch (Exception e) {
//            System.out.println("Error: organisation---insertRecord" + e);
//        }
//        if (rowsAffected > 0) {
//            message = "Record saved successfully.";
//            msgBgColor = COLOR_OK;
//
//            //insert record
//            if (superstatus.equals("yes")) {
//                String query2 = "INSERT INTO  organisation_designation_hierarchy(organisation_designation_map_id,parent_id,active,serial_no) values(?,?,?,?) ";
//                int rowsAffected2 = 0;
//                try {
//                    PreparedStatement pstmt2 = connection.prepareStatement(query2);
//
//                    pstmt2.setInt(1, key);
//                    pstmt2.setString(3, "Y");
//                    pstmt2.setString(4, serialnumber);
//
//                    if (serialnumber.contains(".")) {
//                        //set for super child
//                        int lst = serialnumber.lastIndexOf(".");
//                        String parentsrno = serialnumber.substring(0, lst);
//                        int parentid = getIdOfParent(parentsrno);
//                        pstmt2.setInt(2, parentid);
//                    } else {
//                        //set for super parent
//                        pstmt2.setInt(2, 0);
//
//                    }
//
//                    rowsAffected2 = pstmt2.executeUpdate();
//
//                    if (rowsAffected2 > 0) {
//                        message = "Record saved successfully.";
//                        msgBgColor = COLOR_OK;
//                    } else {
//                        message = "Cannot save the record, some error.";
//                        msgBgColor = COLOR_ERROR;
//                    }
//
//                } catch (Exception e) {
//                    System.out.println(e);
//                }
//
//            } else {
//
//                //code for non super
//                String query2 = "INSERT INTO  organisation_designation_hierarchy(organisation_designation_map_id,parent_id,active,serial_no) values(?,?,?,?,?) ";
//                int rowsAffected2 = 0;
//                try {
//                    PreparedStatement pstmt2 = connection.prepareStatement(query2);
//
//                    pstmt2.setInt(1, key);
//                    pstmt2.setString(3, "Y");
//                    pstmt2.setString(4, serialnumber);
//
//                    //set for super child
//                    int lst = serialnumber.lastIndexOf(".");
//                    String parentsrno = serialnumber.substring(0, lst);
//                    int parentid = getIdOfParent(parentsrno);
//                    pstmt2.setInt(2, parentid);
//
//                    //set for super parent
//                    rowsAffected2 = pstmt2.executeUpdate();
//
//                    if (rowsAffected2 > 0) {
//                        message = "Record saved successfully.";
//                        msgBgColor = COLOR_OK;
//                    } else {
//                        message = "Cannot save the record, some error.";
//                        msgBgColor = COLOR_ERROR;
//                    }
//
//                } catch (Exception e) {
//                    System.out.println(e);
//                }
//
//            }
//            //insert finished
//
//        } else {
//            message = "Cannot save the record, some error.";
//            msgBgColor = COLOR_ERROR;
//        }
//        return rowsAffected;
//    }
    public int updateRecord(OrganisationDesignationBean orgOffice, int org_office_id) throws SQLException {
        int revision = OrganisationTypeDesignationModel.getRevisionno(orgOffice, org_office_id);
        //int orgid = getOrganisation_id(orgOffice.getOrganisation());
        int desigid = getDesignation_id(orgOffice.getDesignation());
        String oldserial, oldsuper;
        String newserial = orgOffice.getSerialnumber();
        String newsuper = orgOffice.getSuperp();
        String superstatus = orgOffice.getSuperp();
        String serialnumber = orgOffice.getSerialnumber();
        int key = orgOffice.getId();
        int updateRowsAffected = 0;
        int rowsAffected = 0;
        int count = 0;

        String is_child = "", active = "", prev_is_child = "";
        int prev_mapId1 = 0;
        is_child = orgOffice.getSuperp().trim();
        if (is_child != null) {
            if (is_child.equals("yes") || is_child.equals("Yes") || is_child.equals("YES") || is_child.equals("Y") || is_child.equals("y")) {
                is_child = "Y";
            } else {
                is_child = "N";
            }
        }
        int org_desig_id = orgOffice.getId();
        int orgid = getOrganisation_id(orgOffice.getOrganisation());
        int desigid1 = getDesignation_id(orgOffice.getDesignation());
        int desigid2 = getDesignation_id(orgOffice.getP_designation());
        int generation = 0;
        if (desigid2 == 0) {
            generation = 1;
        } else {

            generation = getParentGeneration(orgid, desigid2) + 1;

        }

        if (desigid1 == desigid2) {
            message = "Sorry! Parent-Child cannot be same!";
            msgBgColor = COLOR_ERROR;
            return rowsAffected;
        }

        // to check if child present or not for update logic
        String qry3 = " select count(*),designation_id from designation_organisation_type_map where "
                + " id='" + org_desig_id + "' and organisation_type_id='" + orgid + "'  "
                //+ " organisation_id='" + orgid + "' and organisation_designation_map_id_2='"+prev_mapId1+"' "
                + " and active='Y' ";
        try {
            PreparedStatement pst2 = connection.prepareStatement(qry3);
            System.out.println("query for check -" + pst2);
            ResultSet rst2 = pst2.executeQuery();
            while (rst2.next()) {
                count = rst2.getInt(1);
                prev_mapId1 = rst2.getInt(2);

            }
        } catch (Exception e) {
            System.out.println("error in insertRecord model -" + e);
        }
        if (prev_mapId1 != desigid) {
            int c = 0;
            String query3 = " select count(*),designation_id from designation_organisation_type_map where "
                    //+ " organisation_designation_id='" + org_desig_id + "' and organisation_id='" + orgid + "'  "
                    + " organisation_type_id='" + orgid + "' and parent_designation_id='" + prev_mapId1 + "' "
                    + " and active='Y' ";
            try {
                PreparedStatement pst3 = connection.prepareStatement(query3);
                System.out.println("query for check -" + pst3);
                ResultSet rst3 = pst3.executeQuery();
                while (rst3.next()) {
                    c = rst3.getInt(1);
                    prev_mapId1 = rst3.getInt(2);

                }
            } catch (Exception e) {
                System.out.println("error in insertRecord model -" + e);
            }

            if (c > 0) {
                message = "Cannot save the record, already mapped!";
                msgBgColor = COLOR_ERROR;
                return rowsAffected;
            }
        }

        // to check if child exist or not for duplicate entry of child
        String qry2 = " select count(*) from designation_organisation_type_map where organisation_type_id='" + orgid + "' and "
                + " designation_id='" + desigid1 + "' and active='Y' ";
        try {
            PreparedStatement pst1 = connection.prepareStatement(qry2);
            System.out.println("query for check -" + pst1);
            ResultSet rst1 = pst1.executeQuery();
            while (rst1.next()) {
                count = rst1.getInt(1);
            }
        } catch (Exception e) {
            System.out.println("error in insertRecord model -" + e);
        }
        if (count > 1) {
            message = "Cannot save the record, already existed!";
            msgBgColor = COLOR_ERROR;
            return rowsAffected;
        }

        boolean status = false;

        // to check child - parent mapping
        //int count = 0;
        String qry = " select count(*) from designation_organisation_type_map "
                + " where organisation_type_id='" + orgid + "' and "
                + " parent_designation_id='" + desigid1 + "' and active='Y' ";

        String qry1 = " select is_super_child from designation_organisation_type_map where id='" + org_desig_id + "'  "
                + " and active='Y' ";

        //
        //String query1 = "SELECT max(revision),serial_no,super  FROM organisation_designation_map WHERE organisation_designation_map_id = " + org_office_id + "  && active=? ";
        String query1 = " SELECT max(revision_no),is_super_child FROM designation_organisation_type_map WHERE id = " + org_office_id + "  && active='Y' ";
        //String query2 = "UPDATE organisation_designation_map SET active=? WHERE organisation_designation_map_id=? and revision=?";
        String query2 = " UPDATE designation_organisation_type_map SET active=? WHERE id=? and revision_no=? ";

//        String query3 = "INSERT INTO "
//                + "organisation_designation_map(organisation_designation_map_id,organisation_id,designation_id,serial_no, super,revision,active,parent_designation) "
//                + "VALUES(?,?,?,?,?,?,?,?)";
        String query3 = " insert into designation_organisation_type_map(organisation_type_id,designation_id, "
                + " parent_designation_id,is_super_child,active,revision_no,remark,"
                + " created_by,created_at,id,generation) "
                + " values (?,?,?,?,?,?,?,?,now(),?,?) ";

        //int rowsAffected = 0;
        try {
            connection.setAutoCommit(false);

            // for child-parent mapping
            PreparedStatement pst1 = connection.prepareStatement(qry1);
            ResultSet rst1 = pst1.executeQuery();
            while (rst1.next()) {
                prev_is_child = rst1.getString(1);
            }
            if (!is_child.equals(prev_is_child)) {

                PreparedStatement pst = connection.prepareStatement(qry);
                ResultSet rst = pst.executeQuery();
                if (rst.next()) {
                    count = rst.getInt(1);
                }

            }

            if (count <= 1) {

                PreparedStatement pstmt = connection.prepareStatement(query1);
//           pstmt.setInt(1,organisation_type_id);
                //pstmt.setString(1, "Y");

                ResultSet rs = pstmt.executeQuery();
                if (rs.next()) {
                    revision = rs.getInt("max(revision_no)");
                    oldsuper = rs.getString("is_super_child");

                    PreparedStatement pstm = connection.prepareStatement(query2);
                    pstm.setString(1, "N");

                    pstm.setInt(2, org_office_id);
                    pstm.setInt(3, revision);
                    updateRowsAffected = pstm.executeUpdate();
                    if (updateRowsAffected >= 1) {
                        revision = rs.getInt("max(revision_no)") + 1;
                        PreparedStatement psmt = (PreparedStatement) connection.prepareStatement(query3);

                        psmt.setInt(1, orgid);
                        System.out.println("querrrrry --" + pstmt);
                        psmt.setInt(2, desigid1);
                        psmt.setInt(3, desigid2);
                        psmt.setString(4, is_child);
                        psmt.setString(5, "Y");
                        psmt.setInt(6, revision);
                        psmt.setString(7, orgOffice.getRemark());
                        psmt.setString(8, "Shubham");
                        psmt.setInt(9, org_office_id);
                        psmt.setInt(10, generation);

                        //pstmt.setString(9, "0");
                        System.out.println("insert query -" + psmt);
                        rowsAffected = psmt.executeUpdate();
                        if (rowsAffected > 0) {
                            status = true;

                            updateallRecorf(orgid);

                            message = "Record updated successfully.";
                            msgBgColor = COLOR_OK;
                            connection.commit();
                            //delete record
                        } else {
                            status = false;
                            message = "Cannot update the record, some error.";
                            msgBgColor = COLOR_ERROR;
                            connection.rollback();
                        }
                    }

                }

            } else {
                message = "Key already mapped with some child!";
                msgBgColor = COLOR_ERROR;
            }

        } catch (Exception e) {
            System.out.println("Error: updateRecord---updateRecord" + e);
        } finally {

        }

//        if (rowsAffected > 0) {
//            message = "Record updated successfully.";
//            msgBgColor = COLOR_OK;
//        } else {
//            message = "Cannot update the record, some error.";
//            msgBgColor = COLOR_ERROR;
//        }
        return rowsAffected;
    }

    public Boolean updateallRecorf(int id) throws SQLException {
        Boolean status = false;

        String querynw = " select * from designation_organisation_type_map where organisation_type_id=?  and active=?  order by generation ";
        PreparedStatement psmtnw = (PreparedStatement) connection.prepareStatement(querynw);
        psmtnw.setInt(1, id);

        psmtnw.setString(2, "Y");

        ResultSet rstnw = psmtnw.executeQuery();
        while (rstnw.next()) {
            int generation = 0;
            int selfdesig = rstnw.getInt("designation_id");
            int desig = rstnw.getInt("parent_designation_id");

            if (desig == 0) {

                generation = 1;
            } else {
                generation = getParentGeneration(id, desig) + 1;

            }

            String querynw2 = " update designation_organisation_type_map SET generation=?  where organisation_type_id=?  and designation_id=? and active=?  order by generation ";
            PreparedStatement psmtnw2 = (PreparedStatement) connection.prepareStatement(querynw2);
            psmtnw2.setInt(1, generation);
            psmtnw2.setInt(2, id);
            psmtnw2.setInt(3, selfdesig);
            psmtnw2.setString(4, "Y");

            int n = psmtnw2.executeUpdate();

        }

        return status;
    }

    public static int getRevisionno(OrganisationDesignationBean orgOffice, int org_office_id) {
        int revision = 0;
        try {

            String query = " SELECT max(revision_no) as revision_no FROM designation_organisation_type_map WHERE id =" + org_office_id + "  && active='Y' ";

            PreparedStatement pstmt = (PreparedStatement) connection.prepareStatement(query);

            ResultSet rset = pstmt.executeQuery();

            while (rset.next()) {
                revision = rset.getInt("revision_no");

            }
        } catch (Exception e) {
        }
        return revision;
    }

    public int deleteRecord(int org_office_id) throws SQLException {

        int org_id = 0, org_map1 = 0, org_map2 = 0, count = 0;
        int rowsAffected = 0;
        PreparedStatement psmt;
        ResultSet rst;
        String query = " select organisation_type_id,designation_id,parent_designation_id "
                + " from designation_organisation_type_map where id='" + org_office_id + "' "
                + " and active='Y' ";
        try {
            psmt = connection.prepareStatement(query);
            rst = psmt.executeQuery();
            while (rst.next()) {
                org_id = rst.getInt(1);
                org_map1 = rst.getInt(2);
                org_map2 = rst.getInt(3);
            }

        } catch (Exception e) {
            System.out.println("Error: " + e);
        } finally {
            psmt = null;
            rst = null;
            rowsAffected = 0;
            query = null;
        }
        query = " select count(*) from designation_organisation_type_map where "
                + " organisation_type_id='" + org_id + "' and parent_designation_id='" + org_map1 + "' and active='Y' ";
        try {
            psmt = connection.prepareStatement(query);
            rst = psmt.executeQuery();
            while (rst.next()) {
                count = rst.getInt(1);
            }

        } catch (Exception e) {
            System.out.println("Error: " + e);
        } finally {
            psmt = null;
            rst = null;
            rowsAffected = 0;
            query = null;
        }

        if (count > 0) {
            message = "Sorry!, cannot delete because child already mapped as Parent!";
            msgBgColor = COLOR_ERROR;

            return rowsAffected;
        }

//        if (rowsAffected > 0) {
//            message = "Record deleted successfully.";
//            msgBgColor = COLOR_OK;
//        } else {
//            message = "Cannot delete the record, some error.";
//            msgBgColor = COLOR_ERROR;
//        }
        query = " DELETE FROM designation_organisation_type_map WHERE id = '" + org_office_id + "' and "
                + " active='Y' ";
        try {
            psmt = connection.prepareStatement(query);
            rowsAffected = psmt.executeUpdate();

        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
        if (rowsAffected > 0) {

            message = "Record deleted successfully.";
            msgBgColor = COLOR_OK;
        } else {

            message = "Cannot delete the record, some error.";
            msgBgColor = COLOR_ERROR;
        }
        return rowsAffected;
    }

    public List<String> getOrganisation_Name(String q) {
        List<String> list = new ArrayList<String>();
        String query = " SELECT org.organisation_name FROM organisation_name AS org ORDER BY organisation_name ";
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            int count = 0;
            q = q.trim();
            while (rset.next()) {    // move cursor from BOR to valid record.
                String organisation_name = (rset.getString("organisation_name"));
                if (organisation_name.toUpperCase().startsWith(q.toUpperCase())) {
                    list.add(organisation_name);
                    count++;
                }
            }
            if (count == 0) {
                list.add("No such Organisation Type exists.");
            }
        } catch (Exception e) {
            System.out.println("Error:OrganisationMapModel--getOrganationNameList()-- " + e);
        }
        return list;
    }

    public List<String> searchOrganisation_Name(String q) {
        List<String> list = new ArrayList<String>();
        String query = " SELECT distinct org.org_type_name FROM organisation_type AS org,designation_organisation_type_map odm where org.organisation_type_id = odm.organisation_type_id and odm.active='Y' and org.active='Y' ";
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            int count = 0;
            q = q.trim();
            while (rset.next()) {    // move cursor from BOR to valid record.
                String organisation_name = (rset.getString("org_type_name"));
                if (organisation_name.toUpperCase().startsWith(q.toUpperCase())) {
                    list.add(organisation_name);
                    count++;
                }
            }
            if (count == 0) {
                list.add("No such Organisation Type exists.");
            }
        } catch (Exception e) {
            System.out.println("Error:OrganisationMapModel--getOrganationNameList()-- " + e);
        }
        return list;
    }

    public List<String> searchDesignation(String q, String code) {
        String query = "";
        int id = getDesgn_id(code);
        int id1 = getDesgn_idd(id);
        List<String> list = new ArrayList<String>();
        if (q.equals(null) || q.equals("")) {
            query = " SELECT distinct designation FROM designation where designation.active='Y' ";
        } else {
            query = " SELECT distinct designation FROM designation,organisation_designation_map where designation.designation_id=organisation_designation_map.organisation_designation_map_id and organisation_designation_map.active='Y' ";
        }
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            int count = 0;

            while (rset.next()) {    // move cursor from BOR to valid record.
                String state_name = (rset.getString("designation"));

                list.add(state_name);
                count++;

            }
            if (count == 0) {
                list.add("No such Designation exists.");
            }
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
        return list;
    }

    public int getOrganisation_id(String organisation_name) {
        String query = " SELECT organisation_type_id FROM organisation_type WHERE org_type_name = ? and active=? ";
        int organisation_id = 0;
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, organisation_name);
            pstmt.setString(2, "Y");
            ResultSet rset = pstmt.executeQuery();
            rset.next();    // move cursor from BOR to valid record.
            organisation_id = rset.getInt("organisation_type_id");
        } catch (Exception e) {
            System.out.println("Error: OrganisationMapModel--" + e);
        }
        return organisation_id;
    }

    public int getParentGeneration(int org_id, int desig_id) {
        String query = " SELECT * FROM designation_organisation_type_map WHERE organisation_type_id = ? and designation_id=? and active=? ";
        int organisation_id = 0;
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setInt(1, org_id);
            pstmt.setInt(2, desig_id);
            pstmt.setString(3, "Y");
            System.out.println("generation query --" + pstmt);
            ResultSet rset = pstmt.executeQuery();
            rset.next();    // move cursor from BOR to valid record.
            organisation_id = rset.getInt("generation");
        } catch (Exception e) {
            System.out.println("Error: OrganisationMapModel--" + e);
        }
        return organisation_id;
    }

    public int getDesignation_id(String organisation_name) {
        String query = " SELECT designation_id FROM designation WHERE designation = ? and active=? ";
        int organisation_id = 0;
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, organisation_name);
            pstmt.setString(2, "Y");
            ResultSet rset = pstmt.executeQuery();
            rset.next();    // move cursor from BOR to valid record.
            organisation_id = rset.getInt("designation_id");
        } catch (Exception e) {
            System.out.println("Error: OrganisationMapModel--" + e);
        }
        return organisation_id;
    }

    public static String getDesignation_name(String designation_id) {
        String query = " SELECT designation FROM designation WHERE designation_id = ? and active='Y' ";
        String designation_name = "";
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, designation_id);
            //pstmt.setString(2, "Y");
            ResultSet rset = pstmt.executeQuery();
            rset.next();    // move cursor from BOR to valid record.
            designation_name = rset.getString("designation");
        } catch (Exception e) {
            System.out.println("Error: OrganisationMapModel--" + e);
        }
        return designation_name;
    }

    public List<String> OrgOfficeType(String q) {
        List<String> list = new ArrayList<String>();
        //String query = "SELECT org.office_type FROM org_office_type AS org WHERE org.org_office_code = ? ORDER BY office_type ";
        String query = " SELECT org_type_name from organisation_type where organisation_type.active='Y' order by org_type_name ";
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            // pstmt.setString(1, office_code);
            ResultSet rset = pstmt.executeQuery();
            int count = 0;
            q = q.trim();
            while (rset.next()) {    // move cursor from BOR to valid record.
                String office_type = (rset.getString("org_type_name"));
                if (office_type.toUpperCase().startsWith(q.toUpperCase())) {
                    list.add(office_type);
                    count++;
                }
            }
            if (count == 0) {
                list.add("No Such Organisation Name Exists.");
            }
        } catch (Exception e) {
            System.out.println("Error:OrganisationMapModel--office code()-- " + e);
        }
        return list;
    }

    public List<String> getGeneration(String q) {
        List<String> list = new ArrayList<String>();
        String query = " SELECT distinct org.generation FROM designation_organisation_type_map  AS org   where org.active='Y'  ORDER BY generation ";
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            int count = 0;
            q = q.trim();
            while (rset.next()) {    // move cursor from BOR to valid record.
                String organisation_name = (rset.getString("generation"));
                if (organisation_name.toUpperCase().startsWith(q.toUpperCase())) {
                    list.add(organisation_name);
                    count++;
                }
            }
            if (count == 0) {
                list.add("No such Generation exists.");
            }
        } catch (Exception e) {
            System.out.println("Error:OrganisationMapModel--getOrganationNameList()-- " + e);
        }
        return list;
    }

    public List<String> getOrgOfficeNameSearch(String q, String office_code) {
        List<String> list = new ArrayList<String>();
        String query = " SELECT o.org_office_name FROM org_office AS o  "
                + " WHERE IF('" + office_code + "'='', o.org_office_code LIKE '%%' , o.org_office_code='" + office_code + "') "
                + " ORDER BY o.org_office_name ";
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            int count = 0;
            q = q.trim();
            while (rset.next()) {    // move cursor from BOR to valid record.
                String org_office = rset.getString("org_office_name");
                if (org_office.toUpperCase().startsWith(q.toUpperCase())) {
                    list.add(org_office);
                    count++;
                }
            }
            if (count == 0) {
                list.add("No Such Office Code Exists.");
            }
        } catch (Exception e) {
            System.out.println("Error:OrganisationMapModel--office code()-- " + e);
        }
        return list;
    }

    public List<String> getOrgOfficeCodeSearch(String q) {
        List<String> list = new ArrayList<String>();
        String query = " SELECT oo.org_office_code FROM org_office as oo GROUP BY oo.org_office_code ";
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            int count = 0;
            q = q.trim();
            while (rset.next()) {    // move cursor from BOR to valid record.
                String org_office_code = rset.getString("org_office_code");
                {
                    list.add(org_office_code);
                    count++;
                }
            }
            if (count == 0) {
                list.add("No Such Office Code Exists.");
            }
        } catch (Exception e) {
            System.out.println("Error:OrganisationMapModel--office code()-- " + e);
        }
        return list;
    }

    public int getOrgOfficeType_id(String office_type) {
        String query = " SELECT office_type_id FROM org_office_type WHERE  office_type = ? ";
        int organisation_id = 0;
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            //  pstmt.setString(1, office_code);
            pstmt.setString(1, office_type);
            ResultSet rset = pstmt.executeQuery();
            rset.next();    // move cursor from BOR to valid record.
            organisation_id = rset.getInt("office_type_id");
        } catch (Exception e) {
            System.out.println("Error: OrganisationMapModel--" + e);
        }
        return organisation_id;
    }

    public int getCity_id(String city_name) {
        String query = " SELECT city_id FROM city WHERE city_name = ? ";
        int city_id = 0;
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, city_name);
            ResultSet rset = pstmt.executeQuery();
            rset.next();    // move cursor from BOR to valid record.
            city_id = rset.getInt("city_id");
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
        return city_id;
    }

    public int getDesgn_id(String office) {
        String query = " SELECT organisation_type_id FROM organisation_type WHERE org_type_name = ? ";
        int city_id = 0;
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, office);
            ResultSet rset = pstmt.executeQuery();
            rset.next();    // move cursor from BOR to valid record.
            city_id = rset.getInt("organisation_type_id");
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
        return city_id;
    }

    public int getDesgn_idd(int office) {
        String query = " SELECT designation_id FROM organisation_designation_map WHERE organisation_id = ? ";
        int city_id = 0;
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setInt(1, office);
            ResultSet rset = pstmt.executeQuery();
            rset.next();    // move cursor from BOR to valid record.
            city_id = rset.getInt("designation_id");
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
        return city_id;
    }

    public byte[] generateSiteList(String jrxmlFilePath, List listAll) {
        byte[] reportInbytes = null;
        HashMap mymap = new HashMap();
        try {
            JRBeanCollectionDataSource beanColDataSource = new JRBeanCollectionDataSource(listAll);
            JasperReport compiledReport = JasperCompileManager.compileReport(jrxmlFilePath);
            reportInbytes = JasperRunManager.runReportToPdf(compiledReport, null, beanColDataSource);
        } catch (Exception e) {
            System.out.println("Error: in OrganisationNameModel generatReport() JRException: " + e);
        }
        return reportInbytes;
    }

    public List<String> getHierarchy(String q) {
        List<String> list = new ArrayList<String>();
//        String query = "SELECT city_name FROM city AS c ,state AS s WHERE c.state_id=s.state_id AND s.state_name=? "
//                + "  ORDER BY city_name";
        String query = " SELECT designation FROM designation AS d,designation_organisation_type_map as od where d.designation_id=od.designation_id and d.active='Y' and od.active='Y' ORDER BY d.designation ";
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            //    pstmt.setString(1, krutiToUnicode.convert_to_unicode(state_name));

            ResultSet rset = pstmt.executeQuery();
            int count = 0;
            q = q.trim();
            while (rset.next()) {    // move cursor from BOR to valid record.
                String AdvertiseName = (rset.getString("designation"));
                if (AdvertiseName.toUpperCase().startsWith(q.toUpperCase())) {
                    list.add(AdvertiseName);
                    count++;
                }
            }
            if (count == 0) {
                list.add("No such Designation Name exists.");
            }
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
        return list;
    }

    public List<String> getCityName(String q) {
        List<String> list = new ArrayList<String>();
//        String query = "SELECT city_name FROM city AS c ,state AS s WHERE c.state_id=s.state_id AND s.state_name=? "
//                + "  ORDER BY city_name";
        String query = " SELECT city_name FROM city AS c ORDER BY city_name ";
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            //    pstmt.setString(1, krutiToUnicode.convert_to_unicode(state_name));

            ResultSet rset = pstmt.executeQuery();
            int count = 0;
            q = q.trim();
            while (rset.next()) {    // move cursor from BOR to valid record.
                String AdvertiseName = rset.getString("city_name");
                if (AdvertiseName.toUpperCase().startsWith(q.toUpperCase())) {
                    list.add(AdvertiseName);
                    count++;
                }
            }
            if (count == 0) {
                list.add("No such City Name exists.");
            }
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
        return list;
    }

    public List<String> getStateName(String q, String code) {
        //  int id = getDesgn_id(code);
        // int id1 = getDesgn_idd(id);
        List<String> list = new ArrayList<String>();
        String query = " SELECT designation FROM designation where designation.active='Y' ";
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            int count = 0;
            q = q.trim();
            while (rset.next()) {    // move cursor from BOR to valid record.
                String state_name = (rset.getString("designation"));
                if (state_name.toUpperCase().startsWith(q.toUpperCase())) {
                    list.add(state_name);
                    count++;
                }
            }
            if (count == 0) {
                list.add("No such Designation exists.");
            }
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
        return list;
    }

    public List<String> getParentdesignation(String q, String code, String edit, String generationS, String currdesig) {
        int id = getDesgn_id(code);
        // int id1 = getDesgn_idd(id);
        List<String> list = new ArrayList<String>();
//        String query = " SELECT designation FROM designation,organisation_designation_map where designation.designation_id=organisation_designation_map.designation_id and "
//                + " organisation_designation_map.organisation_id='" + id + "' and "
//                + " organisation_designation_map.active='Y' and designation.active='Y'";

        String query = " SELECT * FROM designation d,designation_organisation_type_map o where "
                + " d.designation_id=o.designation_id "
                + " and  o.organisation_type_id='" + id + "' and  o.active='Y' and d.active='Y' and o.is_super_child='N' ";

        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            System.out.println("get paernt -" + query);
            int count = 0;
            q = q.trim();
            while (rset.next()) {    // move cursor from BOR to valid record.
                String state_name = (rset.getString("designation"));
                if (edit.equals("true")) {
                    int generation = (rset.getInt("generation"));
                    int generationP = Integer.parseInt(generationS);
                    if ((state_name.toUpperCase().startsWith(q.toUpperCase())) && (generationP >= generation)) {
                        if (!(state_name.equals(currdesig))) {
                            list.add(state_name);
                        }
                        count++;
                    }

                } else {
                    if (state_name.toUpperCase().startsWith(q.toUpperCase())) {
                        list.add(state_name);
                        count++;
                    }

                }

            }
            if (count == 0) {
                list.add("No such Designation exists.");
            }
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
        return list;
    }

    public byte[] generateOrganisationOfficeList(String jrxmlFilePath, int organisation_id) {
        byte[] reportInbytes = null;
        ByteArrayOutputStream byteArray = new ByteArrayOutputStream();
        HashMap mymap = new HashMap();
        mymap.put("org_id", organisation_id);
        Connection con = connection;
        try {
            JasperReport compiledReport = JasperCompileManager.compileReport(jrxmlFilePath);
            reportInbytes = JasperRunManager.runReportToPdf(compiledReport, mymap, con);
//            JasperPrint jasperPrint = JasperFillManager.fillReport(compiledReport, mymap, con);
//            JRPdfExporter export = new JRPdfExporter();
//            JRPdfSaveContributor pdf = new JRPdfSaveContributor(Locale.ENGLISH, null);
//            pdf.save(jasperPrint, new File("E:\\NetBeansProjects\\TrafficPoliceNew/Office_report.pdf"));////////////
//            export.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
//            export.setParameter(JRExporterParameter.OUTPUT_STREAM, byteArray);
//            export.exportReport();
        } catch (Exception e) {
            System.out.println("Error: in OrgOfficeModel generatReport() JRException: " + e);
        }
        return reportInbytes;
    }

    public ByteArrayOutputStream generateOrganisationOfficeExcelList(String jrxmlFilePath, int organisation_id) {
        ByteArrayOutputStream reportInbytes = new ByteArrayOutputStream();
        HashMap mymap = new HashMap();
        mymap.put("org_id", organisation_id);
        Connection con = connection;
        try {
            JasperReport compiledReport = JasperCompileManager.compileReport(jrxmlFilePath);
            //reportInbytes = JasperRunManager.runReportToPdf(compiledReport, mymap, con);
            JasperPrint jasperPrint = JasperFillManager.fillReport(compiledReport, mymap, con);
            JRXlsExporter exporter = new JRXlsExporter();
            exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
            exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, reportInbytes);
            exporter.exportReport();
        } catch (Exception e) {
            System.out.println("Error: in OrgOfficeModel generatReport() JRException: " + e);
        }
        return reportInbytes;
    }

    public byte[] generateofficeAddressList(String jrxmlFilePath, int org_office_id) {
        byte[] reportInbytes = null;
        HashMap mymap = new HashMap();
        mymap.put("org_office_id", org_office_id);
        Connection con = connection;
        try {
            JasperReport compiledReport = JasperCompileManager.compileReport(jrxmlFilePath);
            reportInbytes = JasperRunManager.runReportToPdf(compiledReport, mymap, con);
        } catch (Exception e) {
            System.out.println("Error: in OrgOfficeModel generatReport() JRException: " + e);
        }
        return reportInbytes;
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
            System.out.println("OrgOfficeModel closeConnection() Error: " + e);
        }
    }
}
