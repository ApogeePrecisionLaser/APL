

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */




/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.healthDepartment.organization.model;

import com.healthDepartment.organization.tableClasses.OrgOfficeHierarchyBean;
import com.lowagie.text.pdf.PdfWriter;
import com.healthDepartment.organization.tableClasses.Org_Office;
import com.healthDepartment.organization.tableClasses.OrganisationDesignationBean;
import com.healthDepartment.organization.tableClasses.OrganisationDesignationHierarchyBean;
import com.healthDepartment.util.KrutiDevToUnicodeConverter;
import com.healthDepartment.util.UnicodeToKrutiDevConverter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.JasperRunManager;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.JRPdfExporterContext;
import net.sf.jasperreports.engine.export.JRPdfExporterParameter;
import net.sf.jasperreports.engine.export.JRPdfExporterTagHelper;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.view.save.JRPdfSaveContributor;

/**
 *
 * @author Soft_Tech
 */
public class OrganisationDesignationHierarchyModel {

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
    
    
    public OrganisationDesignationBean getOfficeNamefromId(int i)
    {
     OrganisationDesignationBean bean=new OrganisationDesignationBean();
           String query1 = "SELECT * FROM organisation_designation_map WHERE organisation_designation_map_id =?  && active=? ";
          try {
            PreparedStatement pstmt = connection.prepareStatement(query1);
            pstmt.setInt(1, i);
            pstmt.setString(2, "Y");
            
            ResultSet rset = pstmt.executeQuery();

            while (rset.next())
            {
               int  orgid = rset.getInt("organisation_id");
               int desigid=rset.getInt("designation_id");
              
               String orgname=getOrgNameFromId(orgid);
               String designame=getDesigNameFromId(desigid);
               
               bean.setOrganisation(orgname);
               bean.setDesignation(designame);
               
            } 
            
          }
          catch( Exception ex)
          {
          
          }
    
    return bean;
    }
    
    
    
    public List<OrganisationDesignationHierarchyBean> showData(int lowerLimit, int noOfRowsToDisplay, String serial_no,String designation) {
        PreparedStatement pstmt;
        String query;
        List<OrganisationDesignationHierarchyBean> list = new ArrayList<OrganisationDesignationHierarchyBean>();
        try {
       
            // Use DESC or ASC for descending or ascending order respectively of fetched data.

            query = " SELECT * from organisation_designation_hierarchy where organisation_designation_hierarchy.active=?  "
                    + "AND IF ('" + serial_no + "' = '' , serial_no LIKE '%%',serial_no= ?)";
                   
            pstmt = connection.prepareStatement(query);
            pstmt.setString(1,"Y");
             pstmt.setString(2, serial_no);
             //  pstmt.setString(3, designation);
            ResultSet rset = pstmt.executeQuery();
              
            while (rset.next()) {
                OrganisationDesignationHierarchyBean organisation = new OrganisationDesignationHierarchyBean();
                 int id=rset.getInt("organisation_designation_hierarchy_id");
                 int offid=rset.getInt("organisation_designation_map_id");
                 
                 OrganisationDesignationBean orgbean= getOfficeNamefromId( offid) ;
                 organisation.setOfficename(orgbean.getOrganisation());
                 organisation.setDesignation(orgbean.getDesignation());
                         
                 int parentid=rset.getInt("parent_id");
                 String parentoffice="",office="";
                 if(parentid == 0)
                 {
                 
                 }
                 else
                 {
                     OrganisationDesignationBean orgbean2= getOfficeNamefromId( parentid) ;
                 
                     organisation.setParentdesignationname(orgbean2.getDesignation());
                     organisation.setParentofficename(orgbean2.getOrganisation());
                 }
                 
                organisation.setId(id);
                 
//                 organisation.setParentofficename(parentoffice);
                 organisation.setSerialnumber(rset.getString("serial_no"));
                 
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
      String query1 = "SELECT org_office_id FROM org_office WHERE serial_no =?  && active=? ";
          try {
            PreparedStatement pstmt = connection.prepareStatement(query1);
            pstmt.setString(1, number);
            pstmt.setString(2, "Y");
            
            ResultSet rset = pstmt.executeQuery();

            while (rset.next())
            {
                id = rset.getInt("org_office_id");

            }
            
          }
          catch( Exception ex)
          {
          
          }
      
    return id;
    }
    
    
    public int insertRecord(Org_Office orgOffice) {
        String superstatus=orgOffice.getSuperp();
        String serialnumber=orgOffice.getSerialnumber();
        int key=0;
        String query = "INSERT INTO "
                + "org_office( org_office_name,org_office_code,office_type_id, address_line1, "
                + "address_line2, address_line3, city_id, email_id1, email_id2, mobile_no1, mobile_no2, "
                + "landline_no1, landline_no2, landline_no3,revision_no,active,remark,organisation_id,serial_no,super) "
                + "VALUES( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?,?,?,?,?,?,?)";
        int rowsAffected = 0;
        try {
            PreparedStatement pstmt = connection.prepareStatement(query,Statement.RETURN_GENERATED_KEYS);
         //   pstmt.setInt(1, orgOffice.getOrganisation_id());
            pstmt.setString(1, (orgOffice.getOrg_office_name()));
            pstmt.setString(2,orgOffice.getOrg_office_code());
            pstmt.setInt(3, orgOffice.getOffice_type_id());
            pstmt.setString(4, (orgOffice.getAddress_line1()));
            pstmt.setString(5, (orgOffice.getAddress_line2()));
            pstmt.setString(6, (orgOffice.getAddress_line3()));
            pstmt.setInt(7, orgOffice.getCity_id());
            pstmt.setString(8, orgOffice.getEmail_id1());
            pstmt.setString(9, orgOffice.getEmail_id2());
            pstmt.setString(10, orgOffice.getMobile_no1());
            pstmt.setString(11, orgOffice.getMobile_no2());
            pstmt.setString(12, orgOffice.getLandline_no1());
            pstmt.setString(13, orgOffice.getLandline_no2());
            pstmt.setString(14, orgOffice.getLandline_no3());
            pstmt.setInt(15, orgOffice.getRevision_no());
            pstmt.setString(16, "Y");
            pstmt.setString(17, "ok");
            pstmt.setInt(18, orgOffice.getOrganisation_id());
            
            pstmt.setString(19,serialnumber );
            pstmt.setString(20,superstatus);
          
            
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
                           
                if(superstatus.equals("yes"))
                {    
                    String query2 = "INSERT INTO  org_office_hierarchy(org_office_id,parent_id,active) values(?,?,?) ";
                    int rowsAffected2 = 0;
                     try {
                     PreparedStatement pstmt2 = connection.prepareStatement(query2);
                     
                        pstmt2.setInt(1, key);
                        pstmt2.setString(3, "Y");
                         if(serialnumber.contains("."))
                         {
                            //set for super child
                             int lst=serialnumber.lastIndexOf(".");
			     String parentsrno=serialnumber.substring(0,lst);
                             int parentid=getIdOfParent(parentsrno);
                             pstmt2.setInt(2,parentid);
                         }
                         else
                         {
                            //set for super parent
                             pstmt2.setInt(2,0);
                             
                         }
                         
                         rowsAffected2 = pstmt2.executeUpdate();
                         
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
                    
                    String query2 = "INSERT INTO  org_office_hierarchy(org_office_id,parent_id,active) values(?,?,?) ";
                    int rowsAffected2 = 0;
                     try {
                     PreparedStatement pstmt2 = connection.prepareStatement(query2);
                     
                        pstmt2.setInt(1, key);
                        pstmt2.setString(3, "Y");
                      
                            //set for super child
                             int lst=serialnumber.lastIndexOf(".");
			     String parentsrno=serialnumber.substring(0,lst);
                             int parentid=getIdOfParent(parentsrno);
                             pstmt2.setInt(2,parentid);
                        
                            //set for super parent
                          
                             
                        
                         
                         rowsAffected2 = pstmt2.executeUpdate();
                         
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
            
            
            
        } else {
            message = "Cannot save the record, some error.";
            msgBgColor = COLOR_ERROR;
        }
        return rowsAffected;
    }

    public int updateRecord(Org_Office orgOffice,int org_office_id) {
          int revision=OrgOfficeModel.getRevisionno(orgOffice,org_office_id);
         int updateRowsAffected = 0;
           boolean status=false;
        String query1 = "SELECT max(revision_no) revision_no FROM org_office WHERE org_office_id = "+org_office_id+"  && active=? ";
         String query2 = "UPDATE org_office SET active=? WHERE org_office_id=? and revision_no=?";
        String query3 = "INSERT INTO "
                + "org_office(org_office_id,org_office_name,org_office_code,office_type_id, address_line1, "
                + "address_line2, address_line3, city_id, email_id1, email_id2, mobile_no1, mobile_no2, "
                + "landline_no1, landline_no2, landline_no3,revision_no,active,remark) "
                + "VALUES(?,?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?,?,?,?)";
        int rowsAffected = 0;
        try {
             PreparedStatement pstmt = connection.prepareStatement(query1);
//           pstmt.setInt(1,organisation_type_id);
           pstmt.setString(1, "Y");
           
           
           
           ResultSet rs = pstmt.executeQuery();
                if(rs.next()){
                PreparedStatement pstm = connection.prepareStatement(query2);
               
                 pstm.setString(1,"n");
               
                 pstm.setInt(2,org_office_id);
                 pstm.setInt(3,revision);
                  updateRowsAffected = pstm.executeUpdate();
             if(updateRowsAffected >= 1){
                   revision = rs.getInt("revision_no")+1;
                    PreparedStatement psmt = (PreparedStatement) connection.prepareStatement(query3);
                     pstmt.setInt(1, orgOffice.getOrganisation_id());
                   pstmt.setString(2, (orgOffice.getOrg_office_name()));
                     pstmt.setString(3,orgOffice.getOrg_office_code());
                       pstmt.setInt(4, orgOffice.getOffice_type_id());
                   pstmt.setString(5, (orgOffice.getAddress_line1()));
            pstmt.setString(6, (orgOffice.getAddress_line2()));
            pstmt.setString(7, (orgOffice.getAddress_line3()));
            pstmt.setInt(8, orgOffice.getCity_id());
            pstmt.setString(9, orgOffice.getEmail_id1());
            pstmt.setString(10, orgOffice.getEmail_id2());
            pstmt.setString(11, orgOffice.getMobile_no1());
            pstmt.setString(12, orgOffice.getMobile_no2());
            pstmt.setString(13, orgOffice.getLandline_no1());
            pstmt.setString(14, orgOffice.getLandline_no2());
            pstmt.setString(15, orgOffice.getLandline_no3());
                    psmt.setInt(16,revision);
                    psmt.setString(17,"Y");
                    psmt.setString(18,"OK");
                    rowsAffected = psmt.executeUpdate();
                   if(rowsAffected > 0)
                   status=true;
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

    public static int getRevisionno(Org_Office orgOffice,int org_office_id) {
        int revision=0;
        try {

            String query = " SELECT max(revision_no) as revision_no FROM organisation_type WHERE org_office_id ="+org_office_id+"  && active='Y';";

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
        String query = "DELETE FROM org_office WHERE org_office_id = "+org_office_id;
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

    public List<String> getSerialNo(String q) {
        List<String> list = new ArrayList<String>();
        String query = "SELECT org.serial_no FROM organisation_designation_map AS org ORDER BY serial_no ";
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            int count = 0;
          q=q.trim();
            while (rset.next()) {    // move cursor from BOR to valid record.
                String organisation_name = (rset.getString("serial_no"));
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

    public int getOrganisation_id(String organisation_name) {
        String query = "SELECT organisation_id FROM organisation_name WHERE organisation_name = ? ";
        int organisation_id = 0;
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, organisation_name);
            ResultSet rset = pstmt.executeQuery();
            rset.next();    // move cursor from BOR to valid record.
            organisation_id = rset.getInt("organisation_id");
        } catch (Exception e) {
            System.out.println("Error: OrganisationMapModel--" + e);
        }
        return organisation_id;
    }

   

        public List<String> OrgOfficeType(String q) {
        List<String> list = new ArrayList<String>();
        //String query = "SELECT org.office_type FROM org_office_type AS org WHERE org.org_office_code = ? ORDER BY office_type ";
        String query = "SELECT office_type from org_office_type";
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
           // pstmt.setString(1, office_code);
            ResultSet rset = pstmt.executeQuery();
            int count = 0;
           
            while (rset.next()) {    // move cursor from BOR to valid record.
                String office_type = (rset.getString("office_type"));
                
                    list.add(office_type);
                    count++;
             
            }
            if (count == 0) {
                list.add("No Such Office Name Exists.");
            }
            } catch (Exception e) {
            System.out.println("Error:OrganisationMapModel--office code()-- " + e);
             }
            return list;
          }

    public List<String> getDesignationSearch(String q,String office_code) {
        List<String> list = new ArrayList<String>();
        String query = "SELECT d.designation FROM designation AS d,organisation_designation_hierarchy AS odh,organisation_designation_map odm"
                + " where d.active='Y' and odm.active='Y' and odh.active='Y' and odm.organisation_designation_map_id=odh.organisation_designation_map_id  "
                + "AND IF ('" + office_code + "' = '' , serial_no LIKE '%%',serial_no= ?)";
                
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            int count = 0;
       
            while (rset.next()) {    // move cursor from BOR to valid record.
                String org_office =(rset.getString("designation"));
               
                    list.add(org_office);
                    count++;
                
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

    public List<String> getStateName(String q) {
        List<String> list = new ArrayList<String>();
        String query = "SELECT state_name FROM state ORDER BY state_name ";
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            int count = 0;
            q = q.trim();
            while (rset.next()) {    // move cursor from BOR to valid record.
                String state_name = unicodeToKruti.Convert_to_Kritidev_010(rset.getString("state_name"));
                if (state_name.toUpperCase().startsWith(q.toUpperCase())) {
                    list.add(state_name);
                    count++;
                }
            }
            if (count == 0) {
                list.add("No such State Name exists.");
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
