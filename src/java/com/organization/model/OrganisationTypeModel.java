/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.organization.model;

import com.organization.tableClasses.OrganisationType;
import java.io.ByteArrayOutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
 * @author komal
 */
public class OrganisationTypeModel {

    private static Connection connection;

    private String message;
    private String msgBgColor;
    private final String COLOR_OK = "#a2a220";
    private final String COLOR_ERROR = "red";
    private static List<OrganisationType> list1 = new ArrayList<OrganisationType>();
    static int off_id = 0;
    private static int count = 0;

    public void setConnection(Connection con) {
        try {

            connection = con;
        } catch (Exception e) {
            System.out.println("OrganisationTypeModel setConnection() Error: " + e);
        }
    }

//    public List<OrganisationType> showAllData(String searchOrgType, String searchgeneration) {
//        List<OrganisationType> list = new ArrayList<OrganisationType>();
//        count = 0;
//        list1.clear();
//        searchOrgType = (searchOrgType);
//      //  int org_id = OrganisationTypeModel.getOrgid(searchhierarchy);
//        int count = 0;
//        int o_id = 0;
////        if (searchhierarchy.isEmpty()) {
////            count = 0;
////        } else {
////            count++;
////        }
//        // Use DESC or ASC for descending or ascending order respectively of fetched data.
//        if (count > 0) {
//
//            list = OrganisationTypeModel.showHierarchyParentData(org_id, searchOrgType, searchgeneration, active, searchhierarchy, o_id);
//            return list;
//        }
//        // Use DESC or ASC for descending or ascending order respectively of fetched data.
////        String query = " SELECT organisation_type_id, org_type_name, description,parent_org_id,super,generation "
////                + " FROM organisation_type where "
////                + "  IF('" + active + "' = '', active LIKE '%%',active =?) and "
////                + "  IF('" + searchOrgType + "' = '', org_type_name LIKE '%%',org_type_name =?) and "
////                + "  IF('" + searchgeneration + "' = '', generation LIKE '%%',generation =?) "
////                + " ORDER BY generation,org_type_name asc ";
//
//        String query = " SELECT organisation_type_id, org_type_name, description,parent_org_id,super,generation "
//                + " FROM organisation_type  where active='Y' ";
//
//        if (!searchOrgType.equals("") && searchOrgType != null) {
//            query += " and org_type_name='" + searchOrgType + "' ";
//        }
//        if (!searchgeneration.equals("") && searchgeneration != null) {
//            query += " and generation='" + searchgeneration + "' ";
//        }
//        query += " ORDER BY generation,org_type_name asc ";
//
//        try {
//            PreparedStatement pstmt = connection.prepareStatement(query);
//            pstmt.setString(1, active);
//            pstmt.setString(2, searchOrgType);
//            pstmt.setString(3, searchgeneration);
//            ResultSet rset = pstmt.executeQuery();
//            while (rset.next()) {
//                OrganisationType organisationType = new OrganisationType();
//                organisationType.setOrganisation_type_id(rset.getInt("organisation_type_id"));
//                organisationType.setOrg_type_name((rset.getString("org_type_name")));
//                organisationType.setDescription((rset.getString("description")));
//                String p_ot_id = rset.getString("parent_org_id");
//                organisationType.setP_ot(getParentOrgname(Integer.parseInt(p_ot_id)));
//
//                organisationType.setSupper(rset.getString("super"));
//                organisationType.setGeneration(rset.getInt("generation"));
//                list.add(organisationType);
//            }
//        } catch (Exception e) {
//            System.out.println(" showAllData Error: " + e);
//        }
//        return list;
//    }
    public List<OrganisationType> showData(String searchOrgType, String searchgeneration) {
        List<OrganisationType> list = new ArrayList<OrganisationType>();
        list1.clear();
        count = 0;
        searchOrgType = (searchOrgType);

        //   int org_id = OrganisationTypeModel.getOrgid(searchhierarchy);
//        int count = 0;
//        int o_id = 0;
//        if (searchhierarchy.isEmpty()) {
//            count = 0;
//        } else {
//            count++;
//        }
        // Use DESC or ASC for descending or ascending order respectively of fetched data.
//        if (count > 0) {
//
//            list1 = OrganisationTypeModel.showHierarchyParentData(org_id, searchOrgType, searchgeneration, o_id);
//            return list1;
//        }
        String query = "SELECT organisation_type_id, org_type_name, description,parent_org_id,super,generation "
                + " FROM organisation_type  where active='Y' ";

        if (!searchOrgType.equals("") && searchOrgType != null) {
            query += " and org_type_name='" + searchOrgType + "' ";
        }
        if (!searchgeneration.equals("") && searchgeneration != null) {
            query += " and generation='" + searchgeneration + "' ";
        }
        query += " ORDER BY generation,org_type_name asc ";

        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
//            pstmt.setString(1, active);
//            pstmt.setString(2, searchOrgType);
//            pstmt.setString(3, searchgeneration);
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                OrganisationType organisationType = new OrganisationType();
                organisationType.setOrganisation_type_id(rset.getInt("organisation_type_id"));
                organisationType.setOrg_type_name((rset.getString("org_type_name")));
                organisationType.setDescription((rset.getString("description")));
                String p_ot_id = rset.getString("parent_org_id");
                organisationType.setP_ot(getParentOrgname(Integer.parseInt(p_ot_id)));

                organisationType.setSupper(rset.getString("super"));
                organisationType.setGeneration(rset.getInt("generation"));
                list.add(organisationType);

            }
        } catch (Exception e) {
            System.out.println("OrganisationTypeModel showData Error: " + e);
        }
        return list;
    }

    //show hierarchy organisation wise
//    public static List<OrganisationType> showHierarchyData(int org_id, String searchOrgType, String searchgeneration, String active, String searchhierarchy, int o_id) {
//        int id = 0;
//        searchOrgType = (searchOrgType);
//        //  int org_id  = OrganisationTypeModel.getOrgid(searchhierarchy);
//        String p_idd = "";
//
////       if(o_id>0)
////       {
////        org_id=o_id;
////       }
////        if(p_id==0)
////        {
////           p_idd = String.valueOf(org_id);
////        }
////        else
////        {
////          p_idd =String.valueOf(p_id);
////        
////        }
//        // Use DESC or ASC for descending or ascending order respectively of fetched data.
////        String query = " SELECT organisation_type_id, org_type_name, description,parent_org_id,super,generation "
////                + " FROM organisation_type where  parent_org_id=" + org_id + " and "
////                + " IF('" + active + "' = '', active LIKE '%%',active =?)  "
////                //  + "  IF('" + searchhierarchy + "' = '', org_type_name LIKE '%%',org_type_name =?) "
////                + " ORDER BY generation asc ";
//        String query = " SELECT organisation_type_id, org_type_name, description,parent_org_id,super,generation "
//                + " FROM organisation_type  where active='Y' and parent_org_id=" + org_id + " ORDER BY generation asc ";
//
//        try {
//            PreparedStatement pstmt = connection.prepareStatement(query);
//
//            //  pstmt.setString(1, active);
//            //   pstmt.setString(1, searchhierarchy);
//            ResultSet rset = pstmt.executeQuery();
//            while (rset.next()) {
//                OrganisationType organisationType = new OrganisationType();
//                id = rset.getInt("organisation_type_id");
//                organisationType.setOrganisation_type_id(rset.getInt("organisation_type_id"));
//                o_id = OrganisationTypeModel.getParentOrgid(id);
//                off_id = OrganisationTypeModel.getcheckorgid(id);
//                if (o_id == 0) {
//                    break;
//                }
//
//                organisationType.setOrg_type_name((rset.getString("org_type_name")));
//                organisationType.setDescription((rset.getString("description")));
//                String p_ot_id = rset.getString("parent_org_id");
//                organisationType.setP_ot(getParentOrgname(Integer.parseInt(p_ot_id)));
//
//                organisationType.setSupper(rset.getString("super"));
//                organisationType.setGeneration(rset.getInt("generation"));
//                list1.add(organisationType);
//                if (off_id != 0) {
//                    showHierarchyData2(id, searchOrgType, searchgeneration, active, searchhierarchy, o_id);
//                }
//
//            }
//        } catch (Exception e) {
//            System.out.println("showHierarchyData Error: " + e);
//        }
//        if (o_id != 0) {
//            //  showHierarchyData(lowerLimit,noOfRowsToDisplay,id,searchOrgType,searchgeneration,active,searchhierarchy,o_id);
//        }
//        //   }
//        return list1;
//    }
    //show hierarchy Parent wise
//    public static List<OrganisationType> showHierarchyParentData(int org_id, String searchOrgType, String searchgeneration, String active, String searchhierarchy, int o_id) {
//        int id = 0;
//        searchOrgType = (searchOrgType);
//        //     int org_id  = OrganisationTypeModel.getOrgid(searchhierarchy);
//        String p_idd = "";
//
////       if(o_id==0)
////       {
////        org_id=o_id;
////        
////       }
////        if(p_id==0)
////        {
////           p_idd = String.valueOf(org_id);
////        }
////        else
////        {
////          p_idd =String.valueOf(p_id);
////        
////        }
//        // Use DESC or ASC for descending or ascending order respectively of fetched data.
////        String query = " SELECT organisation_type_id, org_type_name, description,parent_org_id,super,generation "
////                + " FROM organisation_type where "
////                  + "  IF('" + active + "' = '', active LIKE '%%',active =?) and "
////                + "  IF('" + searchOrgType + "' = '', org_type_name LIKE '%%',org_type_name =?) and "
////                 + "  IF('" + searchgeneration + "' = '', generation LIKE '%%',generation =?) "
////                + " ORDER BY org_type_name "
////                + " LIMIT " + lowerLimit + ", " + noOfRowsToDisplay;
////                while(p_id>0)
////                {
//        String query = " SELECT organisation_type_id, org_type_name, description,parent_org_id,super,generation "
//                + " FROM organisation_type  where active='Y' and organisation_type_id=" + org_id + " ORDER BY generation asc ";
//
//        try {
//            PreparedStatement pstmt = connection.prepareStatement(query);
//
////            pstmt.setString(1, active);
////            pstmt.setInt(2, org_id);
////                pstmt.setInt(3, o_id);
//            ResultSet rset = pstmt.executeQuery();
//            // System.out.println("hierarchy org type -" + pstmt);
//            while (rset.next()) {
//                OrganisationType organisationType = new OrganisationType();
//                id = rset.getInt("organisation_type_id");
//                organisationType.setOrganisation_type_id(rset.getInt("organisation_type_id"));
//                o_id = OrganisationTypeModel.getParentOrgid(id);
//                off_id = OrganisationTypeModel.getcheckorgid(id);
//
//                organisationType.setOrg_type_name((rset.getString("org_type_name")));
//                organisationType.setDescription((rset.getString("description")));
//                String p_ot_id = rset.getString("parent_org_id");
//                organisationType.setP_ot(getParentOrgname(Integer.parseInt(p_ot_id)));
//
//                organisationType.setSupper(rset.getString("super"));
//                organisationType.setGeneration(rset.getInt("generation"));
//                list1.add(organisationType);
//                count++;
//
//            }
//        } catch (Exception e) {
//            System.out.println("showHierarchyParentData Error: " + e);
//        }
//
//        if (count > 1) {
//            showHierarchyData2(id, searchOrgType, searchgeneration, active, searchhierarchy, o_id);
//        }
//
//        showHierarchyData(id, searchOrgType, searchgeneration, active, searchhierarchy, o_id);
//
//        //   }
//        return list1;
//    }
//    public static List<OrganisationType> showHierarchyData2(int org_id, String searchOrgType, String searchgeneration, String active, String searchhierarchy, int o_id) {
//        int id = 0;
//
//        String p_idd = "";
//
////            
////        String query = " SELECT organisation_type_id, org_type_name, description,parent_org_id,super,generation "
////                + " FROM organisation_type where  parent_org_id=" + org_id + " and "
////                + " IF('" + active + "' = '', active LIKE '%%',active =?)  "
////                //  + "  IF('" + searchhierarchy + "' = '', org_type_name LIKE '%%',org_type_name =?) "
////                + " ORDER BY generation asc ";
//        String query = " SELECT organisation_type_id, org_type_name, description,parent_org_id,super,generation "
//                + " FROM organisation_type where active='Y' and parent_org_id=" + org_id + " ORDER BY generation asc ";
//        try {
//            PreparedStatement pstmt = connection.prepareStatement(query);
//
//            // pstmt.setString(1, active);
//            //   pstmt.setString(1, searchhierarchy);
//            ResultSet rset = pstmt.executeQuery();
//            while (rset.next()) {
////                OrganisationType organisationType = new OrganisationType();
////                int id =rset.getInt("organisation_type_id");
////                organisationType.setOrganisation_type_id(rset.getInt("organisation_type_id"));
////                o_id =OrganisationTypeModel.getParentOrgid(id);
////               
////               
////                
////                organisationType.setOrg_type_name((rset.getString("org_type_name")));
////                organisationType.setDescription((rset.getString("description")));
////                String p_ot_id=rset.getString("parent_org_id");
////                organisationType.setP_ot(getParentOrgname(Integer.parseInt(p_ot_id)));
////                              
////                  organisationType.setSupper(rset.getString("super"));
////                  organisationType.setGeneration(rset.getInt("generation"));
////                   list1.add(organisationType);
//
//                OrganisationType organisationType = new OrganisationType();
//                id = rset.getInt("organisation_type_id");
//                organisationType.setOrganisation_type_id(rset.getInt("organisation_type_id"));
//                o_id = OrganisationTypeModel.getParentOrgid(id);
//                off_id = OrganisationTypeModel.getcheckorgid(id);
//                if (id == 0) {
//                    break;
//                }
//
//                organisationType.setOrg_type_name((rset.getString("org_type_name")));
//                organisationType.setDescription((rset.getString("description")));
//                String p_ot_id = rset.getString("parent_org_id");
//                organisationType.setP_ot(getParentOrgname(Integer.parseInt(p_ot_id)));
//
//                organisationType.setSupper(rset.getString("super"));
//                organisationType.setGeneration(rset.getInt("generation"));
//                list1.add(organisationType);
//
//                if (off_id != 0) {
//
//                    showHierarchyData2(id, searchOrgType, searchgeneration, active, searchhierarchy, o_id);
//                }
//            }
//        } catch (Exception e) {
//            System.out.println("showHierarchyData2 Error: " + e);
//        }
//
//        if (off_id != 0) {
//            showHierarchyData2(id, searchOrgType, searchgeneration, active, searchhierarchy, o_id);
//        }
//        // }
//        return list1;
//    }
    public static int getcheckorgid(int org_id) {
        String query = "SELECT organisation_type_id FROM organisation_type WHERE  parent_org_id=? and active=? ";
        int organisation_id = 0;
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            //  pstmt.setInt(1, org_id);

            // pstmt.setString(2, "Y");
            ResultSet rset = pstmt.executeQuery();
            rset.next();    // move cursor from BOR to valid record.
            organisation_id = rset.getInt("organisation_type_id");
        } catch (Exception e) {
            System.out.println("OrganisationTypeModel Error: getcheckorgid--" + e);
        }
        return organisation_id;
    }

    public static int getOrgid(String org_id) {
        String query = "SELECT organisation_type_id FROM organisation_type WHERE active='Y' ";

        if (!org_id.equals("") && org_id != null) {
            query += " and org_type_name='" + org_id + "' ";
        }

        int organisation_id = 0;
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            // pstmt.setString(1, org_id);

            // pstmt.setString(2, "Y");
            ResultSet rset = pstmt.executeQuery();
            rset.next();    // move cursor from BOR to valid record.
            organisation_id = rset.getInt("organisation_type_id");
        } catch (Exception e) {
            System.out.println("OrganisationTypeModel Error: getOrgid--" + e);
        }
        return organisation_id;
    }

    public static int getParentOrgid(int org_id) {
        String query = "SELECT parent_org_id FROM organisation_type WHERE  organisation_type_id=? and active=? ";
        int organisation_id = 0;
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            // pstmt.setInt(1, org_id);

            // pstmt.setString(2, "Y");
            ResultSet rset = pstmt.executeQuery();
            rset.next();    // move cursor from BOR to valid record.
            organisation_id = rset.getInt("parent_org_id");
        } catch (Exception e) {
            System.out.println("OrganisationTypeModel Error: getParentOrgid--" + e);
        }
        return organisation_id;
    }

    public static String getParentOrgname(int org_id) {
        String query = "SELECT org_type_name FROM organisation_type WHERE  organisation_type_id='" + org_id + "' and active='Y' ";

        String organisation_id = "";
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            // pstmt.setInt(1, org_id);

            // pstmt.setString(2, "Y");
            ResultSet rset = pstmt.executeQuery();
            rset.next();    // move cursor from BOR to valid record.
            organisation_id = rset.getString("org_type_name");
        } catch (Exception e) {
            System.out.println("OrganisationTypeModel Error: getParentOrgname--" + e);
        }
        return organisation_id;
    }

    public int getParentGeneration(int org_id) {
        String query = "SELECT * FROM organisation_type WHERE  organisation_type_id=? and active=? ";
        int organisation_id = 0;
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            // pstmt.setInt(1, org_id);

            // pstmt.setString(2, "Y");
            ResultSet rset = pstmt.executeQuery();
            rset.next();    // move cursor from BOR to valid record.
            organisation_id = rset.getInt("generation");
        } catch (Exception e) {
            System.out.println("OrganisationTypeModel Error: getParentGeneration--" + e);
        }
        return organisation_id;
    }

    public int insertRecord(OrganisationType organisationType) {
        int rowsAffected = 0;
        String parent_org_name = organisationType.getP_ot();
        String is_child = organisationType.getSupper();
        int count = 0;
        if (is_child != null) {
            if (is_child.equals("yes") || is_child.equals("Yes") || is_child.equals("YES") || is_child.equals("Y") || is_child.equals("y")) {
                is_child = "Y";
            } else {
                is_child = "N";
            }
        }

        int org_type_id = organisationType.getOrganisation_type_id();
        int p_org_type_id = (getParentOrgTypeid(parent_org_name));
        int generation = 0;
        if (p_org_type_id == 0) {
            generation = 1;
        } else {

            generation = getParentGeneration(p_org_type_id) + 1;

        }
        if (org_type_id != 0) {
            if (org_type_id == p_org_type_id) {
                message = "Sorry! Parent-Child cannot be same!";
                msgBgColor = COLOR_ERROR;
                return rowsAffected;
            }
        }
        // to check if parent exist or not
        String qry2 = "select count(*) from organisation_type where organisation_type_id='" + org_type_id + "'  "
                + "  and active='Y' ";
        try {
            PreparedStatement pst1 = connection.prepareStatement(qry2);
            // System.out.println("query for check -" + pst1);
            ResultSet rst1 = pst1.executeQuery();
            while (rst1.next()) {
                count = rst1.getInt(1);
            }
        } catch (Exception e) {
            System.out.println("error in insertRecord OrganisationTypeModel -" + e);
        }
        if (count > 0) {
            message = "Cannot save the record, already mapped!";
            msgBgColor = COLOR_ERROR;
            return rowsAffected;
        }

        //
        String query1 = "select count(*) "
                + " from organisation_type where organisation_type_id='" + org_type_id + "'  and"
                + " parent_org_id='" + p_org_type_id + "' and active='Y' ";

        try {
            PreparedStatement pst = connection.prepareStatement(query1);
            // System.out.println("query for check -" + pst);
            ResultSet rst = pst.executeQuery();
            while (rst.next()) {
                count = rst.getInt(1);
            }
        } catch (Exception e) {
            System.out.println("error in insertRecord OrganisationTypeModel -" + e);
        }
        if (count > 0) {
            message = "Cannot save the record, already mapped!";
            msgBgColor = COLOR_ERROR;
            return rowsAffected;
        }

        String query = "INSERT INTO organisation_type(org_type_name,description,revision_no,active,remark,super,parent_org_id,generation) VALUES(?,?,?,?,?,?,?,?)";
        //  int rowsAffected = 0;
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, (organisationType.getOrg_type_name()));
            pstmt.setString(2, (organisationType.getDescription()));
            pstmt.setInt(3, organisationType.getRevision_no());
            pstmt.setString(4, "Y");
            pstmt.setString(5, "OK");
            pstmt.setString(6, is_child);
            pstmt.setInt(7, p_org_type_id);
            pstmt.setInt(8, generation);

            rowsAffected = pstmt.executeUpdate();
        } catch (Exception e) {
            System.out.println("OrganisationTypeModel insertRecord Error: " + e);
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

    public int getOrgtype_id(String organisation_name) {
        String query = "SELECT organisation_type_id FROM organisation_type WHERE org_type_name = ? and active=? ";
        int organisation_id = 0;
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            //  pstmt.setString(1, organisation_name);
            //  pstmt.setString(2, "Y");
            ResultSet rset = pstmt.executeQuery();
            rset.next();    // move cursor from BOR to valid record.
            organisation_id = rset.getInt("organisation_type_id");
        } catch (Exception e) {
            System.out.println("OrganisationTypeModel Error: getOrgtype_id--" + e);
        }
        return organisation_id;
    }

    public int updateRecord(OrganisationType organisationType, int organisation_type_id) {
        int rowsAffected = 0;
        int count = 0;
        int revision = OrganisationTypeModel.getRevisionno(organisationType, organisation_type_id);
        int org_type_id = getOrgtype_id(organisationType.getOrg_type_name());
        String parent_org_name = organisationType.getP_ot();
        int updateRowsAffected = 0;
        int Orgid2 = 0;

        String is_child = "", active = "", prev_is_child = "";
        int prev_mapId1 = 0;
        is_child = organisationType.getSupper().trim();
        if (is_child != null) {
            if (is_child.equals("yes") || is_child.equals("Yes") || is_child.equals("YES") || is_child.equals("Y") || is_child.equals("y")) {
                is_child = "Y";
            } else {
                is_child = "N";
            }
        }

        org_type_id = organisationType.getOrganisation_type_id();
        int p_org_type_id = (getParentOrgTypeid(parent_org_name));
        int generation = 0;
        if (p_org_type_id == 0) {
            generation = 1;
        } else {

            generation = getParentGeneration(p_org_type_id) + 1;

        }
        if (org_type_id != 0) {
            if (org_type_id == p_org_type_id) {
                message = "Sorry! Parent-Child cannot be same!";
                msgBgColor = COLOR_ERROR;
                return rowsAffected;
            }
        }

        // to check if child present or not for update logic
        String qry3 = "select count(*),organisation_type_id from organisation_type where "
                + " organisation_type_id='" + org_type_id + "' "
                //+ " organisation_id='" + orgid + "' and organisation_designation_map_id_2='"+prev_mapId1+"' "
                + " and active='Y' ";
        try {
            PreparedStatement pst2 = connection.prepareStatement(qry3);
            // System.out.println("query for check -" + pst2);
            ResultSet rst2 = pst2.executeQuery();
            while (rst2.next()) {
                count = rst2.getInt(1);
                prev_mapId1 = rst2.getInt(2);

            }
        } catch (Exception e) {
            System.out.println("error in insertRecord OrganisationTypeModel -" + e);
        }
        if (prev_mapId1 != org_type_id) {
            int c = 0;
            String query3 = "select count(*),organisation_type_id from organisation_type where "
                    //+ " organisation_designation_id='" + org_desig_id + "' and organisation_id='" + orgid + "'  "
                    + " organisation_type_id='" + org_type_id + "' and org_type_id='" + prev_mapId1 + "' "
                    + " and active='Y' ";
            try {
                PreparedStatement pst3 = connection.prepareStatement(query3);
                //  System.out.println("query for check -" + pst3);
                ResultSet rst3 = pst3.executeQuery();
                while (rst3.next()) {
                    c = rst3.getInt(1);
                    prev_mapId1 = rst3.getInt(2);

                }
            } catch (Exception e) {
                System.out.println("error in insertRecord OrganisationTypeModel -" + e);
            }

            if (c > 0) {
                message = "Cannot save the record, already mapped!";
                msgBgColor = COLOR_ERROR;
                return rowsAffected;
            }
        }

        // to check if child exist or not for duplicate entry of child
        String qry2 = "select count(*) from organisation_type where   "
                + " organisation_type_id='" + org_type_id + "' and active='Y' ";
        try {
            PreparedStatement pst1 = connection.prepareStatement(qry2);
            //  System.out.println("query for check -" + pst1);
            ResultSet rst1 = pst1.executeQuery();
            while (rst1.next()) {
                count = rst1.getInt(1);
            }
        } catch (Exception e) {
            System.out.println("error in insertRecord OrganisationTypeModel -" + e);
        }
        if (count > 1) {
            message = "Cannot save the record, already existed!";
            msgBgColor = COLOR_ERROR;
            return rowsAffected;
        }
        boolean status = false;
        // to check child - parent mapping
        //int count = 0;
        String qry = "select count(*) from organisation_type "
                + " where organisation_type_id='" + org_type_id + "' and "
                + " parent_org_id='" + p_org_type_id + "' and active='Y' ";

        String qry1 = "select super from organisation_type where organisation_type_id='" + org_type_id + "'  "
                + " and active='Y' ";

        //
        //String query1 = "SELECT max(revision),serial_no,super  FROM organisation_designation_map WHERE organisation_designation_map_id = " + org_office_id + "  && active=? ";
        String query1 = "SELECT max(revision_no),super FROM organisation_type WHERE organisation_type_id = " + org_type_id + "  && active='Y' ";
        //String query2 = "UPDATE organisation_designation_map SET active=? WHERE organisation_designation_map_id=? and revision=?";
        String query2 = "UPDATE organisation_type SET active=? WHERE organisation_type_id=? and revision_no=?";

//        String query3 = "INSERT INTO "
//                + "organisation_designation_map(organisation_designation_map_id,organisation_id,designation_id,serial_no, super,revision,active,parent_designation) "
//                + "VALUES(?,?,?,?,?,?,?,?)";
        String query3 = "INSERT INTO organisation_type(organisation_type_id,org_type_name,description,revision_no,active,remark,parent_org_id,"
                + "super,generation) VALUES(?,?,?,?,?,?,?,?,?)";

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
                    String oldsuper = rs.getString("super");

                    PreparedStatement pstm = connection.prepareStatement(query2);
                    pstm.setString(1, "N");

                    pstm.setInt(2, org_type_id);
                    pstm.setInt(3, revision);
                    updateRowsAffected = pstm.executeUpdate();
                    if (updateRowsAffected >= 1) {
                        revision = rs.getInt("max(revision_no)") + 1;
                        PreparedStatement psmt = (PreparedStatement) connection.prepareStatement(query3);
                        psmt.setInt(1, (organisation_type_id));
                        psmt.setString(2, (organisationType.getOrg_type_name()));
                        psmt.setString(3, (organisationType.getDescription()));
                        psmt.setInt(4, revision);
                        psmt.setString(5, "Y");
                        psmt.setString(6, "OK");
                        psmt.setInt(7, p_org_type_id);
                        psmt.setString(8, is_child);
                        psmt.setInt(9, generation);
                        //pstmt.setString(9, "0");
                        //  System.out.println("insert query -" + psmt);
                        rowsAffected = psmt.executeUpdate();
                        if (rowsAffected > 0) {
                            status = true;

                            updateallRecorf(org_type_id);

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
            System.out.println("Error: OrganisationTypeModel---updateRecord" + e);
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

        String querynw = "select * from organisation_type where organisation_type_id=?  and active=?  order by generation";
        PreparedStatement psmtnw = (PreparedStatement) connection.prepareStatement(querynw);
        psmtnw.setInt(1, id);

        psmtnw.setString(2, "Y");

        ResultSet rstnw = psmtnw.executeQuery();
        while (rstnw.next()) {
            int generation = 0;
            int selfdesig = rstnw.getInt("organisation_type_id");
            int desig = rstnw.getInt("parent_org_id");

            if (desig == 0) {

                generation = 1;
            } else {
                generation = getParentGeneration(desig) + 1;

            }

            String querynw2 = "update organisation_type SET generation=?  where  organisation_type_id=? and active=?  order by generation";
            PreparedStatement psmtnw2 = (PreparedStatement) connection.prepareStatement(querynw2);
            psmtnw2.setInt(1, generation);
            ;
            psmtnw2.setInt(2, selfdesig);
            psmtnw2.setString(3, "Y");

            int n = psmtnw2.executeUpdate();

        }

        return status;
    }

//    public int deleteRecord(int organisation_type_id) {
//        String query = "DELETE FROM organisation_type WHERE organisation_type_id=" + organisation_type_id;
//        int rowsAffected = 0;
//        try {
//            rowsAffected = connection.prepareStatement(query).executeUpdate();
//        } catch (Exception e) {
//            System.out.println("Error: " + e);
//        }
//        if (rowsAffected > 0) {
//            message = "Record deleted successfully.";
//            msgBgColor = COLOR_OK;
//        } else {
//            message = "Cannot delete the record, some error.";
//            msgBgColor = COLOR_ERROR;
//        }
//        return rowsAffected;
//    }
    public int deleteRecord(int organisation_type_id) throws SQLException {

        int org_id = 0, org_map1 = 0, org_map2 = 0, count = 0;
        int rowsAffected = 0;
        PreparedStatement psmt;
        ResultSet rst;
        String query = "select organisation_type_id,parent_org_id "
                + " from organisation_type where organisation_type_id='" + organisation_type_id + "' "
                + " and active='Y' ";

        try {
            psmt = connection.prepareStatement(query);
            rst = psmt.executeQuery();
            while (rst.next()) {
                org_id = rst.getInt(1);

                org_map2 = rst.getInt(2);
            }

        } catch (Exception e) {
            System.out.println("OrganisationTypeModel Error deleteRecord: " + e);
        } finally {
            psmt = null;
            rst = null;
            rowsAffected = 0;
            query = null;
        }
        query = "select count(*) from organisation_type where "
                + "  parent_org_id='" + org_id + "' and active='Y' ";
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
        query = "DELETE FROM organisation_type WHERE organisation_type_id = '" + organisation_type_id + "' and "
                + " active='Y' ";
        try {
            psmt = connection.prepareStatement(query);
            rowsAffected = psmt.executeUpdate();

        } catch (Exception e) {
            System.out.println("OrganisationTypeModel Error: " + e);
        }
        if (rowsAffected > 0) {

            message = "Record deleted successfully.";
            msgBgColor = COLOR_OK;
        } else {

            message = "Cannot delete the record, some error.";
            msgBgColor = COLOR_ERROR;
        }
        if (count > 0) {
            message = "Sorry!, cannot delete because child already mapped as Parent!";
            msgBgColor = COLOR_ERROR;

        }
        return rowsAffected;
    }

    public static int getRevisionno(OrganisationType bean, int organisation_type_id) {
        int revision = 0;
        try {

            String query = " SELECT max(revision_no) as revision_no FROM organisation_type WHERE organisation_type_id =" + organisation_type_id + "  && active='Y';";

            PreparedStatement pstmt = (PreparedStatement) connection.prepareStatement(query);

            ResultSet rset = pstmt.executeQuery();

            while (rset.next()) {
                revision = rset.getInt("revision_no");

            }
        } catch (Exception e) {
            System.err.println("OrganisationTypeModel getRevisionno error--------" + e);
        }
        return revision;
    }

    public List<String> getOrgType(String q) {
        List<String> list = new ArrayList<String>();
        String query = " SELECT distinct organisation_type_id, org_type_name FROM organisation_type o ORDER BY org_type_name ";
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            int count = 0;
            q = q.trim();
            while (rset.next()) {    // move cursor from BOR to valid record.
                String org_type_name = (rset.getString("org_type_name"));
                if (org_type_name.toUpperCase().startsWith(q.toUpperCase())) {
                    list.add(org_type_name);
                    count++;
                }
            }
            if (count == 0) {
                list.add("No such Organisation Type exists.");
            }
        } catch (Exception e) {
            System.out.println("OrganisationTypeModel getOrgType ERROR - " + e);
        }
        return list;
    }

    public List<String> getHierarchsearch(String q) {
        List<String> list = new ArrayList<String>();
        String query = " SELECT distinct organisation_type_id, org_type_name FROM organisation_type o ORDER BY org_type_name ";
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            int count = 0;
            q = q.trim();
            while (rset.next()) {    // move cursor from BOR to valid record.
                String org_type_name = (rset.getString("org_type_name"));
                if (org_type_name.toUpperCase().startsWith(q.toUpperCase())) {

                    list.add(org_type_name);
                    count++;
                }
            }
            if (count == 0) {
                list.add("No such Organisation Type exists.");
            }
        } catch (Exception e) {
            System.out.println("OrganisationTypeModel getHierarchsearch ERROR - " + e);
        }
        return list;
    }

    public List<String> getGeneration(String q) {
        List<String> list = new ArrayList<String>();
        String query = "SELECT distinct org.generation FROM organisation_type  AS org   where org.active='Y'  ORDER BY generation ";
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
            System.out.println("OrganisationTypeModel Error:getGeneration-- " + e);
        }
        return list;
    }

    public String getDesgn_id(String office) {
        String query = "SELECT parent_org_id FROM organisation_type WHERE org_type_name = ?";
        String city_id = "";
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            // pstmt.setString(1, office);
            ResultSet rset = pstmt.executeQuery();
            rset.next();    // move cursor from BOR to valid record.
            city_id = rset.getString("parent_org_id");
            if (city_id.equals("0")) {
                city_id = "";
            }
        } catch (Exception e) {
            System.out.println("OrganisationTypeModel getDesgn_id Error: " + e);
        }
        return city_id;
    }

    public List<String> getParentOrgType(String q, String org_type, String supper, String edit, String generationS) {

        List<String> list = new ArrayList<String>();
        String id = getDesgn_id(org_type);
        String query = " SELECT org_type_name,generation from organisation_type where active='Y' and super='N' and "
                + " IF('" + id + "' = '', organisation_type_id LIKE '%%',organisation_type_id ='" + id + "')  ";
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            int count = 0;
            q = q.trim();
            while (rset.next()) {    // move cursor from BOR to valid record.
                String org_type_name = (rset.getString("org_type_name"));
                if (edit.equals("true")) {

                    int generation = (rset.getInt("generation"));
                    int generationP = Integer.parseInt(generationS);
                    if ((org_type_name.toUpperCase().startsWith(q.toUpperCase())) && (generationP >= generation)) {

                        list.add(org_type_name);
                        count++;
                    }
                } else {

                    if (org_type_name.toUpperCase().startsWith(q.toUpperCase())) {
                        list.add(org_type_name);
                        count++;
                    }

                }

            }
            if (count == 0) {
                list.add("No such Organisation Type exists.");
            }
        } catch (Exception e) {
            System.out.println("OrganisationTypeModel getParentOrgType ERROR - " + e);
        }
        return list;
    }

    public int getParentOrgTypeid(String p_org) {

        int org_type_name = 0;

        String query = " SELECT  organisation_type_id FROM organisation_type o  where org_type_name='" + p_org + "'";
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            int count = 0;

            while (rset.next()) {    // move cursor from BOR to valid record.
                org_type_name = (rset.getInt("organisation_type_id"));

                count++;

            }
            if (count == 0) {
                System.out.println("No parent org");
            }
        } catch (Exception e) {
            System.out.println("OrganisationTypeModel getParentOrgTypeid ERROR - " + e);
        }
        return org_type_name;
    }

    public byte[] generateSiteList(String jrxmlFilePath, List list) {
        byte[] reportInbytes = null;
        HashMap mymap = new HashMap();
        try {
            JRBeanCollectionDataSource jrBean = new JRBeanCollectionDataSource(list);
            JasperReport compiledReport = JasperCompileManager.compileReport(jrxmlFilePath);
            reportInbytes = JasperRunManager.runReportToPdf(compiledReport, null, jrBean);
        } catch (Exception e) {
            System.out.println("Error: in OrganisationTypeModel generateSiteList() JRException: " + e);
        }
        return reportInbytes;
    }

    public ByteArrayOutputStream generateOrginisationXlsRecordList(String jrxmlFilePath, List list) {
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
            System.out.println("OrganisationTypeModel generateSiteList() JRException: " + e);
        }
        return bytArray;
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
            System.out.println("OrganisationTypeModel closeConnection() Error: " + e);
        }
    }
}
