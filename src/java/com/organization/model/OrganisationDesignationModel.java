/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.healthDepartment.organization.model;

import com.healthDepartment.organization.tableClasses.Org_Office;
import com.healthDepartment.organization.tableClasses.OrganisationDesignationBean;
import com.healthDepartment.util.KrutiDevToUnicodeConverter;
import com.healthDepartment.util.UnicodeToKrutiDevConverter;
import java.io.ByteArrayOutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
import net.sf.jasperreports.engine.export.JRXlsExporter;

/**
 *
 * @author Dell
 */


/**
 *
 * @author Soft_Tech
 */
public class OrganisationDesignationModel {

    private static Connection connection;
    private String message;
    private String msgBgColor;
    private final String COLOR_OK = "lightyellow";
    private final String COLOR_ERROR = "red";
    private KrutiDevToUnicodeConverter krutiToUnicode = new KrutiDevToUnicodeConverter();
    private UnicodeToKrutiDevConverter unicodeToKruti = new UnicodeToKrutiDevConverter();

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
            ResultSet rset = connection.prepareStatement("select count(*) from org_office").executeQuery();
            rset.next();    // move cursor from BOR to valid record.
            noOfRows = Integer.parseInt(rset.getString(1));
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
        return noOfRows;
    }

    public int getNoOfRows(String org_name, String office_code_search, String office_name_search) {
        int noOfRowNUM = 0;
        // int org_id = getOrganisation_id(org_name);
        try {
            org_name = krutiToUnicode.convert_to_unicode(org_name);
            office_name_search = krutiToUnicode.convert_to_unicode(office_name_search);
            String query = "  SELECT COUNT(*) as count "
                    + " FROM  org_office AS o LEFT JOIN org_office_type AS oft ON oft.office_type_id = o.office_type_id , "
                    + " city AS c, organisation_name AS org "
                    + " WHERE  org.organisation_id=o.organisation_id "
                   // + " AND o.city_id = c.city_id "
                  //  + " AND c.state_id = s.state_id "
                     + " AND IF ('" + org_name + "' = '' , organisation_name LIKE '%%',organisation_name= ?) "
                    //+ " AND IF (" + office_code_search + " = '' , o.org_office_code LIKE '%%',o.org_office_code LIKE  "+office_code_search+".%' OR o.org_office_code like ?) "
                    + " AND IF('" + office_code_search + "'='' ,o.org_office_code LIKE '%%',o.org_office_code LIKE '"+office_code_search+".%' OR o.org_office_code like ?)"
                    + " AND IF ('" + office_name_search+ "' = '' , o.org_office_name LIKE '%%',o.org_office_name = ?) "
                    + " ORDER BY org.organisation_name,o.org_office_name ";
            PreparedStatement psmt = connection.prepareStatement(query);
            psmt.setString(1, org_name);
            psmt.setString(2, office_code_search);
            psmt.setString(3, office_name_search);
            ResultSet rset = psmt.executeQuery();
            rset.next();    // move cursor from BOR to valid record.
            noOfRowNUM = Integer.parseInt(rset.getString("count"));
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
        return noOfRowNUM;
    }

    public List<OrganisationDesignationBean> showData(int lowerLimit, int noOfRowsToDisplay,String org_name,String designation) {
        PreparedStatement pstmt;
        String query;
        List<OrganisationDesignationBean> list = new ArrayList<OrganisationDesignationBean>();
        try {
//            org_name = krutiToUnicode.convert_to_unicode(org_name);
//            office_name_search = krutiToUnicode.convert_to_unicode(office_name_search);
            // Use DESC or ASC for descending or ascending order respectively of fetched data.

            query = " select odm.organisation_designation_map_id, o.organisation_name,d.designation,odm.serial_no,odm.super from organisation_name o ,designation d,organisation_designation_map odm\n" +
                    " where o.organisation_id=odm.organisation_id and d.designation_id=odm.designation_id and o.active='Y' and d.active='Y' and odm.active='Y'"
                   
                 
                    + " AND IF ('" + org_name + "' = '' , organisation_name LIKE '%%',organisation_name= ?) "
                     + " AND IF ('" + designation + "' = '' , designation LIKE '%%',designation= ?) "
//                    //+ " AND IF (" + office_code_search + " = '' , o.org_office_code LIKE '%%',o.org_office_code LIKE  "+office_code_search+".%' OR o.org_office_code like ?) "
//                    + " AND IF('" + office_code_search + "'='' ,o.org_office_code LIKE '%%',o.org_office_code LIKE '"+office_code_search+".%' OR o.org_office_code like ?)"
//                    + " AND IF ('" + office_name_search+ "' = '' , o.org_office_name LIKE '%%',o.org_office_name = ?) "

                    + " LIMIT " + lowerLimit + ", " + noOfRowsToDisplay;
            pstmt = connection.prepareStatement(query);
           pstmt.setString(1, org_name);
          pstmt.setString(2, designation);
//            pstmt.setString(3, office_name_search);
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                
                int id=rset.getInt("organisation_designation_map_id");
//                int desigid=rset.getInt("designation_id");                    
//                int orgid=rset.getInt("organisation_id");
                String serialno=rset.getString("serial_no");
                String superp=rset.getString("super");
                String designame=rset.getString("designation");;
               // String orgname=getOrgNameFromId(orgid);
                 String orgname=rset.getString("organisation_name");;
                OrganisationDesignationBean organisation = new OrganisationDesignationBean();
                
                organisation.setId(id);
                organisation.setOrganisation(orgname);
                organisation.setDesignation(designame);
                organisation.setSerialnumber(serialno);
                organisation.setSuperp(superp);
                
                list.add(organisation);
            }
        } catch (Exception e) {
            System.out.println("Error:--organisation--- showData--" + e);
        }
        return list;
    }

    public int getIdOfParent(String number)
    {
    int id=0;
      String query1 = "SELECT organisation_designation_map_id FROM organisation_designation_map WHERE serial_no =?  && active=? ";
          try {
            PreparedStatement pstmt = connection.prepareStatement(query1);
            pstmt.setString(1, number);
            pstmt.setString(2, "Y");
            
            ResultSet rset = pstmt.executeQuery();

            while (rset.next())
            {
                id = rset.getInt("organisation_designation_map_id");

            }
            
          }
          catch( Exception ex)
          {
          
          }
      
    return id;
    }
    
    public String getDesigNameFromId(int id)
    {
    String name="";
      String query1 = "SELECT designation FROM designation WHERE designation_id =?  && active=? ";
          try {
            PreparedStatement pstmt = connection.prepareStatement(query1);
            pstmt.setInt(1, id);
            pstmt.setString(2, "Y");
            
            ResultSet rset = pstmt.executeQuery();

            while (rset.next())
            {
                name = rset.getString("designation");

            }
            
          }
          catch( Exception ex)
          {
          
          }
      
    return name;
    }
     public String getOrgNameFromId(int id)
    {
    String name="";
      String query1 = "SELECT organisation_name FROM organisation_name WHERE organisation_id =?  && active=? ";
          try {
            PreparedStatement pstmt = connection.prepareStatement(query1);
            pstmt.setInt(1, id);
            pstmt.setString(2, "Y");
            
            ResultSet rset = pstmt.executeQuery();

            while (rset.next())
            {
                name = rset.getString("organisation_name");

            }
            
          }
          catch( Exception ex)
          {
          
          }
      
    return name;
    }
    
    public int insertRecord(OrganisationDesignationBean orgOffice) {
        String superstatus=orgOffice.getSuperp();
        if(superstatus.equals(""))
        {
             superstatus = "yes";
        }
        String serialnumber=orgOffice.getSerialnumber();
        int orgid=getOrganisation_id(orgOffice.getOrganisation());
        int desigid=getDesignation_id(orgOffice.getDesignation());
        int key=0;
        String query = "INSERT INTO "
                + "organisation_designation_map( organisation_id,designation_id,serial_no, super,revision,active) "
        
                + "VALUES( ?, ?, ?, ?,?,?)";
        int rowsAffected = 0;
        try {
            PreparedStatement pstmt = connection.prepareStatement(query,Statement.RETURN_GENERATED_KEYS);
         //   pstmt.setInt(1, orgOffice.getOrganisation_id());
         
            pstmt.setInt(1,orgid);
            pstmt.setInt(2,desigid);
            pstmt.setString(3,serialnumber );
            pstmt.setString(4,superstatus);
             pstmt.setString(5,"0");
                pstmt.setString(6,"Y");
            
            rowsAffected = pstmt.executeUpdate();
            ResultSet rs = pstmt.getGeneratedKeys();
             if (rs != null && rs.next()) {
               key = rs.getInt(1);
              }
            
        } catch (Exception e) {
            System.out.println("Error: organisation---insertRecord" + e);
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

    public int updateRecord(OrganisationDesignationBean orgOffice,int org_office_id) {
          int revision=OrganisationDesignationModel.getRevisionno(orgOffice,org_office_id);
          int orgid=getOrganisation_id(orgOffice.getOrganisation());
           int desigid=getDesignation_id(orgOffice.getDesignation());
           String oldserial,oldsuper;
                  String newserial=orgOffice.getSerialnumber();
                  String newsuper=orgOffice.getSuperp();
                   String superstatus=orgOffice.getSuperp();
        String serialnumber=orgOffice.getSerialnumber();
        int key=orgOffice.getId();
         int updateRowsAffected = 0;
           boolean status=false;
        String query1 = "SELECT max(revision),serial_no,super  FROM organisation_designation_map WHERE organisation_designation_map_id = "+org_office_id+"  && active=? ";
         String query2 = "UPDATE organisation_designation_map SET active=? WHERE organisation_designation_map_id=? and revision=?";
        String query3 = "INSERT INTO "
                + "organisation_designation_map(organisation_designation_map_id,organisation_id,designation_id,serial_no, super,revision,active) "
        
                + "VALUES(?,?,?,?,?,?,?)";
        int rowsAffected = 0;
        try {
             PreparedStatement pstmt = connection.prepareStatement(query1);
//           pstmt.setInt(1,organisation_type_id);
           pstmt.setString(1, "Y");
           
           
           
           ResultSet rs = pstmt.executeQuery();
                if(rs.next()){
                PreparedStatement pstm = connection.prepareStatement(query2);
               revision = rs.getInt("max(revision)");
                oldserial=rs.getString("serial_no");
                oldsuper=rs.getString("super");
                 pstm.setString(1,"n");
               
                 pstm.setInt(2,org_office_id);
                 pstm.setInt(3,revision);
                  updateRowsAffected = pstm.executeUpdate();
             if(updateRowsAffected >= 1){
                   revision = rs.getInt("max(revision)")+1;
                    PreparedStatement psmt = (PreparedStatement) connection.prepareStatement(query3);
                     psmt.setInt(1,orgOffice.getId());
                    
                      psmt.setInt(2,orgid);
                  
                     psmt.setInt(3,desigid);
                     
                       psmt.setString(4,serialnumber);
                   psmt.setString(5,superstatus);
            psmt.setInt(6, revision);
            psmt.setString(7, "Y");
          
                    rowsAffected = psmt.executeUpdate();
                   if(rowsAffected > 0)
                    {
                        status=true;
                       
                        //delete record
                        
                        if(!(oldserial.equals(newserial) && oldsuper.equals(newsuper) ))
                        {
                              String querydelete = "delete from organisation_designation_hierarchy where serial_no=? and active=? ";    
                          
                              PreparedStatement pstmt4 = connection.prepareStatement(querydelete);
                              pstmt4.setString(1, oldserial);
                              pstmt4.setString(2, "Y");
                              
                              int deletedrows = pstmt4.executeUpdate();
                              
                              if(deletedrows > 0)
                                  
                              {
                              
                                 
                                 
                                   if(superstatus.equals("yes"))
                {    
                    String queryinsert = "INSERT INTO  organisation_designation_hierarchy(organisation_designation_map_id,parent_id,active,serial_no) values(?,?,?,?) ";
                    int rowsAffected2 = 0;
                     try {
                     PreparedStatement pstmt6 = connection.prepareStatement(queryinsert);
                     
                        pstmt6.setInt(1, key);
                        pstmt6.setString(3, "Y");
                        pstmt6.setString(4, serialnumber);
                         if(serialnumber.contains("."))
                         {
                            //set for super child
                             int lst=serialnumber.lastIndexOf(".");
			     String parentsrno=serialnumber.substring(0,lst);
                             int parentid=getIdOfParent(parentsrno);
                             pstmt6.setInt(2,parentid);
                         }
                         else
                         {
                            //set for super parent
                             pstmt6.setInt(2,0);
                             
                         }
                         
                         rowsAffected2 = pstmt6.executeUpdate();
                         
                         if (rowsAffected2 > 0) {
            message = "Record saved successfully.";
            msgBgColor = COLOR_OK;
                         }
                         else {
            message = "Cannot save the record, some error.";
            msgBgColor = COLOR_ERROR;
        }
                         
                     }
                     catch(Exception e)
                     {
                         System.out.println(e);
                     }
                         
                }
            
                else
                {
                
                  //code for non super
                    
                    String queryinsert1 = "INSERT INTO  organisation_designation_hierarchy(organisation_designation_map_id,parent_id,active,serial_no) values(?,?,?,?)  ";
                    int rowsAffected2 = 0;
                     try {
                     PreparedStatement pstmt7 = connection.prepareStatement(queryinsert1);
                     
                        pstmt7.setInt(1, key);
                        pstmt7.setString(3, "Y");
                        pstmt7.setString(4, serialnumber);
                      
                            //set for super child
                             int lst=serialnumber.lastIndexOf(".");
			     String parentsrno=serialnumber.substring(0,lst);
                             int parentid=getIdOfParent(parentsrno);
                             pstmt7.setInt(2,parentid);
                        
                            //set for super parent
                          
                             
                        
                         
                         rowsAffected2 = pstmt7.executeUpdate();
                         
                         if (rowsAffected2 > 0) {
            message = "Record saved successfully.";
            msgBgColor = COLOR_OK;
                         }
                         else {
            message = "Cannot save the record, some error.";
            msgBgColor = COLOR_ERROR;
        }
                         
                     }
                     catch(Exception e)
                     {
                         System.out.println(e);
                     }
                    
                    
                    
                    
                }
                                  
                                  
                                  
                                  
                                  
                                  
                                  
                              
                              
                              
                              }
                        }
                        
                        
                        
                        
                        
                        
                        
                        
                        
                        
                        
                        
                        
                        
                   }
                  
                else 
                  status=false;
             }
                 
                 
                }
        } catch (Exception e) {
            System.out.println("Error: updateRecord---updateRecord" + e);
        }
        if (rowsAffected > 0) {
            message = "Record updated successfully.";
            msgBgColor = COLOR_OK;
        } else {
            message = "Cannot update the record, some error.";
            msgBgColor = COLOR_ERROR;
        }
        return rowsAffected;
    }

    public static int getRevisionno(OrganisationDesignationBean orgOffice,int org_office_id) {
        int revision=0;
        try {

            String query = " SELECT max(revision_no) as revision_no FROM organisation_designation_map WHERE organisation_designation_map_id ="+org_office_id+"  && active='Y';";

            PreparedStatement pstmt = (PreparedStatement) connection.prepareStatement(query);
           
            
           
            ResultSet rset = pstmt.executeQuery();

            while (rset.next())
            {
                revision = rset.getInt("revision_no");

            }
        } catch (Exception e) {
        }
        return revision;
    }
    public int deleteRecord(int org_office_id) {
        String query = "DELETE FROM organisation_designation_map WHERE organisation_designation_map_id = "+org_office_id;
        int rowsAffected = 0;
        try {
            rowsAffected = connection.prepareStatement(query).executeUpdate();
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
        String query = "SELECT org.organisation_name FROM organisation_name  AS org where org.active='Y' ORDER BY organisation_name ";
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            int count = 0;
          q=q.trim();
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
        String query = "SELECT distinct org.organisation_name FROM organisation_name AS org,organisation_designation_map odm where org.organisation_id = odm.organisation_id and odm.active='Y' and org.active='Y' ";
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            int count = 0;
           q=q.trim();
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
    
      
          public List<String> searchDesignation(String q,String code) {
       int id = getDesgn_id(code);
        int id1 = getDesgn_idd(id);
        List<String> list = new ArrayList<String>();
        String query = "SELECT distinct designation FROM designation,organisation_designation_map where organisation_designation_map.designation_id='"+id1+"' and organisation_designation_map.designation_id=designation.designation_id and  organisation_designation_map.active='Y' and designation.active='Y' ";
                       
                try {           
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            int count = 0;
           
            while (rset.next()) {    // move cursor from BOR to valid record.
                String state_name =(rset.getString("designation"));
         
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
        String query = "SELECT organisation_id FROM organisation_name WHERE organisation_name = ? and active=?";
        int organisation_id = 0;
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, organisation_name);
            pstmt.setString(2,"Y");
            ResultSet rset = pstmt.executeQuery();
            rset.next();    // move cursor from BOR to valid record.
            organisation_id = rset.getInt("organisation_id");
        } catch (Exception e) {
            System.out.println("Error: OrganisationMapModel--" + e);
        }
        return organisation_id;
    }
   public int getDesignation_id(String organisation_name) {
        String query = "SELECT designation_id FROM designation WHERE designation = ? and active=? ";
        int organisation_id = 0;
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, organisation_name);
            pstmt.setString(2,"Y");
            ResultSet rset = pstmt.executeQuery();
            rset.next();    // move cursor from BOR to valid record.
            organisation_id = rset.getInt("designation_id");
        } catch (Exception e) {
            System.out.println("Error: OrganisationMapModel--" + e);
        }
        return organisation_id;
    }
   

        public List<String> OrgOfficeType(String q) {
        List<String> list = new ArrayList<String>();
        //String query = "SELECT org.office_type FROM org_office_type AS org WHERE org.org_office_code = ? ORDER BY office_type ";
        String query = "SELECT organisation_name from organisation_name where organisation_name.active='Y' order by organisation_name";
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
           // pstmt.setString(1, office_code);
            ResultSet rset = pstmt.executeQuery();
            int count = 0;
           q=q.trim();
            while (rset.next()) {    // move cursor from BOR to valid record.
                String office_type = (rset.getString("organisation_name"));
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

    public List<String> getOrgOfficeNameSearch(String q,String office_code) {
        List<String> list = new ArrayList<String>();
        String query = "SELECT o.org_office_name FROM org_office AS o  "
                + " WHERE IF('"+ office_code +"'='', o.org_office_code LIKE '%%' , o.org_office_code='"+ office_code +"') "
                + "ORDER BY o.org_office_name";
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            int count = 0;
            q = q.trim();
            while (rset.next()) {    // move cursor from BOR to valid record.
                String org_office = unicodeToKruti.Convert_to_Kritidev_010(rset.getString("org_office_name"));
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
        String query = "SELECT oo.org_office_code FROM org_office as oo GROUP BY oo.org_office_code";
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
        String query = "SELECT office_type_id FROM org_office_type WHERE  office_type = ? ";
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
        String query = "SELECT city_id FROM city WHERE city_name = ?";
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
        String query = "SELECT organisation_id FROM organisation_name WHERE organisation_name = ?";
        int city_id = 0;
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, office);
            ResultSet rset = pstmt.executeQuery();
            rset.next();    // move cursor from BOR to valid record.
            city_id = rset.getInt("organisation_id");
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
        return city_id;
    }
    
    public int getDesgn_idd(int office) {
        String query = "SELECT designation_id FROM organisation_designation_map WHERE organisation_id = ?";
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
    

    public List<String> getCityName(String q) {
        List<String> list = new ArrayList<String>();
//        String query = "SELECT city_name FROM city AS c ,state AS s WHERE c.state_id=s.state_id AND s.state_name=? "
//                + "  ORDER BY city_name";
        String query = "SELECT city_name FROM city AS c ORDER BY city_name ";
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
        //    pstmt.setString(1, krutiToUnicode.convert_to_unicode(state_name));
            
            ResultSet rset = pstmt.executeQuery();
            int count = 0;
            q = q.trim();
            while (rset.next()) {    // move cursor from BOR to valid record.
                String AdvertiseName = unicodeToKruti.Convert_to_Kritidev_010(rset.getString("city_name"));
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
        String query = "SELECT designation FROM designation where designation.active='Y' ";
        try {               
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            int count = 0;
           q=q.trim();
            while (rset.next()) {    // move cursor from BOR to valid record.
                String state_name =(rset.getString("designation"));
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
