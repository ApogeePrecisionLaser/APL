package com.inventory.model;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
//import com.organization.tableClasses.AllinOne;
import java.io.ByteArrayOutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
import org.json.simple.JSONObject;
import com.DBConnection.DBConnection;
import com.inventory.tableClasses.ItemName;
import static com.organization.model.KeypersonModel.getRevisionnoForImage;
import com.organization.tableClasses.KeyPerson;
import java.io.File;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import org.apache.commons.fileupload.FileItem;
import org.json.simple.JSONArray;

/**
 *
 * @author Vikrant
 */
public class ItemNameModel {

    private static Connection connection;

    private String message;
    private String msgBgColor;
    private final String COLOR_OK = "#a2a220";
    private final String COLOR_ERROR = "red";
    int item_id = 0;

    public void setConnection(Connection con) {
        try {

            connection = con;
        } catch (Exception e) {
            System.out.println("ItemNameModel setConnection() Error: " + e);
        }
    }

    public List<ItemName> showData(String searchItemName, String searchItemType, String itemCode) {
        List<ItemName> list = new ArrayList<ItemName>();

        if (searchItemName == null) {
            searchItemName = "";
        }
        if (searchItemType == null) {
            searchItemType = "";
        }
        if (itemCode == null) {
            itemCode = "";
        }

        String query = "select itn.item_names_id,itn.item_name,itn.description,itn.item_code,itt.item_type,itn.quantity "
                + " from item_names itn, item_type itt where itt.item_type_id=itn.item_type_id ";
        if (!searchItemName.equals("") && searchItemName != null) {
            query += " and itn.item_name='" + searchItemName + "' ";
        }
        if (!searchItemType.equals("") && searchItemType != null) {
            query += " and itt.item_type='" + searchItemType + "' ";
        }
        if (!itemCode.equals("") && itemCode != null) {
            query += " and itn.item_code='" + itemCode + "' ";
        }
        query += "  and itn.active='Y' and itt.active='y' order by itn.item_names_id desc ";

        try {
            System.out.println("query----- -" + query);
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            System.err.println("connectipn---------" + connection);
            while (rset.next()) {
                ItemName bean = new ItemName();
                bean.setItem_names_id(rset.getInt("item_names_id"));
                bean.setItem_name((rset.getString("item_name")));
                bean.setDescription(rset.getString("description"));
                bean.setItem_code(rset.getString("item_code"));
                bean.setItem_type(rset.getString("item_type"));
                bean.setQuantity(rset.getInt("quantity"));
                list.add(bean);
            }
        } catch (Exception e) {
            System.out.println("Error: ItemNameModel showdata-" + e);
        }
        return list;
    }

    public int insertRecord(ItemName item_name, Iterator itr, String image_name, String destination, int image_count) throws SQLException {
        String query = "INSERT INTO item_names(item_name,item_type_id,description,"
                + " revision_no,active,remark,item_code,quantity) VALUES(?,?,?,?,?,?,?,?) ";
        int rowsAffected = 0;
//        int item_id = 0;

        String query2 = " select count(*) as count from item_names where item_code='" + item_name.getItem_code() + "' ";
        String query3 = "INSERT INTO item_image_details (image_name, image_path, date_time, description,item_names_id,revision_no,active,remark) "
                + " VALUES(?,?,?,?,?,?,?,?)";
        try {
            PreparedStatement pstmt = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
//            pstmt.setInt(1,(organisation_name.getOrganisation_id()));
            pstmt.setString(1, (item_name.getItem_name()));
            pstmt.setInt(2, (item_name.getItem_type_id()));
            pstmt.setString(3, (item_name.getDescription()));
            pstmt.setInt(4, item_name.getRevision_no());
            pstmt.setString(5, "Y");
            pstmt.setString(6, "OK");
            pstmt.setString(7, item_name.getItem_code());
            pstmt.setInt(8, item_name.getQuantity());

            ResultSet rset = connection.prepareStatement(query2).executeQuery();
            int count = 0;
            while (rset.next()) {
                count = rset.getInt("count");
            }
            if (count > 0) {
                message = "Item Code Already Exists.";
                msgBgColor = COLOR_ERROR;
            } else {
                rowsAffected = pstmt.executeUpdate();
                ResultSet rs = pstmt.getGeneratedKeys();
                //if (rs.next()) {
                while (rs.next()) {
                    item_id = rs.getInt(1);
                }
            }

            rowsAffected = insertImageRecord(rowsAffected, item_name, itr, destination, item_id, image_name, image_count);

        } catch (Exception e) {
            System.out.println("ItemNameModel insertRecord() Error: " + e);
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

    public int insertImageRecord(int rowsAffected, ItemName item_name, Iterator itr, String destination, int item_id, String image_name, int image_count) {
        String img_message = "";
        DateFormat dateFormat1 = new SimpleDateFormat("dd.MMMMM.yyyy");
        DateFormat dateFormat = new SimpleDateFormat("dd.MMMMM.yyyy/ hh:mm:ss aaa");
        Date date = new Date();
        String middleName = "";
        String current_date = dateFormat.format(date);
        String query3 = "INSERT INTO item_image_details (image_name, image_path, date_time, description,item_names_id,revision_no,active,remark) "
                + " VALUES(?,?,?,?,?,?,?,?)";
        String imageName = "";
        try {

            if ((!item_name.getImage_path().isEmpty())) {
                if ((!item_name.getImage_path().isEmpty())) {
                    String tempExt = image_name;
                    if (!tempExt.isEmpty()) {

                        String fieldName = "";
                        if (tempExt.equals(image_name)) {
                            String item_names = item_name.getItem_name().replaceAll("[^a-zA-Z0-9]", "_");
                            middleName = "img_Item_" + item_names + "_" + image_count;
                            destination = "C:\\ssadvt_repository\\APL\\item\\" + item_names + "\\";
                            fieldName = "item_image";
                        }

                        int index = tempExt.lastIndexOf(".");
                        int index1 = tempExt.length();
                        String Extention = tempExt.substring(index + 1, index1);
                        tempExt = "." + Extention;
                        imageName = middleName + tempExt;

//                        imageName = imageName.replaceAll("[-+^]/*", "_");
                        imageName = imageName.replaceAll("[^a-zA-Z0-9]", "_");

                        WirteImage(item_name, itr, destination, imageName, fieldName);
                    }
                    PreparedStatement pstmt1 = connection.prepareStatement(query3);
                    pstmt1.setString(1, imageName);
                    pstmt1.setString(2, destination);
                    pstmt1.setString(3, current_date);
                    pstmt1.setString(4, "this image is for site");
                    pstmt1.setInt(5, item_id);
                    pstmt1.setString(6, "0");
                    pstmt1.setString(7, "Y");
                    pstmt1.setString(8, "ok");
                    rowsAffected = pstmt1.executeUpdate();
                    pstmt1.close();
                }
            }
        } catch (Exception e) {
            System.err.println("exception-----" + e);
        }

        return rowsAffected;
    }

    public String getImagePath(String item_image_details_id) {
        List<String> list = new ArrayList<String>();
        String img_name = "";

        String destination_path = "";
        String query = "SELECT item_image_details_id,image_name, image_path "
                + " FROM item_image_details iid "
                + " where iid.item_image_details_id=" + item_image_details_id + " ORDER BY item_image_details_id ";

        try {
            ResultSet rs = connection.prepareStatement(query).executeQuery();
            while (rs.next()) {
                img_name = rs.getString("image_name");
                destination_path = rs.getString("image_path");
                destination_path = destination_path + img_name;

            }

        } catch (Exception ex) {
            System.out.println("ERROR: in getImagePath in ItemNameModel : " + ex);
        }
        return destination_path;
    }

    public List<ItemName> getImageList(String item_names_id) {
        List<ItemName> list = new ArrayList<ItemName>();

        String data = "";
        String img_name = "";

        String destination_path = "";
        String query = "SELECT item_image_details_id,image_name, image_path,item_names_id "
                + " FROM item_image_details iid "
                + " where iid.item_names_id=" + item_names_id + " and active='Y'  ORDER BY item_image_details_id ";

        try {
            ResultSet rs = connection.prepareStatement(query).executeQuery();
            while (rs.next()) {
                ItemName bean = new ItemName();
                int item_image_details_id = rs.getInt("item_image_details_id");
                img_name = rs.getString("image_name");
                destination_path = rs.getString("image_path");
                destination_path = destination_path + img_name;
                bean.setItem_names_id(Integer.parseInt(item_names_id));
                bean.setItem_image_details_id(item_image_details_id);
                bean.setImage_name(img_name);
                bean.setDestination_path(destination_path);
                list.add(bean);
            }

        } catch (Exception e) {
            System.err.println("Exception in getSourcewater---------" + e);
        }
        return list;
    }

    public int updateRecord(ItemName item_name, Iterator itr, int item_names_id, String image_name, String destination, int image_count) {
        int revision = ItemNameModel.getRevisionno(item_name, item_names_id);
        int updateRowsAffected = 0;
        boolean status = false;
        int item_id = 0;
        DateFormat dateFormat1 = new SimpleDateFormat("dd.MMMMM.yyyy");
        DateFormat dateFormat = new SimpleDateFormat("dd.MMMMM.yyyy/ hh:mm:ss aaa");
        Date date = new Date();
        String current_date = dateFormat.format(date);
        String query1 = "SELECT max(revision_no) revision_no FROM item_names WHERE item_names_id = " + item_names_id + "  && active='Y' ";
        String query2 = "UPDATE item_names SET active=? WHERE item_names_id=? and revision_no=?";
        String query3 = "INSERT INTO item_names(item_names_id,item_name,item_type_id,description,"
                + " revision_no,active,remark,item_code,quantity) VALUES(?,?,?,?,?,?,?,?,?)";

        String query4 = " select count(*) from item_image_details where item_names_id='" + item_names_id + "' ";

        String query5 = " INSERT INTO item_image_details (image_name, image_path, date_time, description,item_names_id,"
                + "revision_no,active,remark) "
                + " VALUES(?,?,?,?,?,?,?,?)";
        int rowsAffected = 0;
        try {
            connection.setAutoCommit(false);
            PreparedStatement pstmt = connection.prepareStatement(query1);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                PreparedStatement pstm = connection.prepareStatement(query2);

                pstm.setString(1, "n");

                pstm.setInt(2, item_names_id);
                pstm.setInt(3, revision);
                updateRowsAffected = pstm.executeUpdate();
                if (updateRowsAffected >= 1) {
                    revision = rs.getInt("revision_no") + 1;
                    PreparedStatement psmt = (PreparedStatement) connection.prepareStatement(query3);
                    psmt.setInt(1, (item_name.getItem_names_id()));
                    psmt.setString(2, (item_name.getItem_name()));
                    psmt.setInt(3, (item_name.getItem_type_id()));
                    psmt.setString(4, (item_name.getDescription()));
                    psmt.setInt(5, revision);
                    psmt.setString(6, "Y");
                    psmt.setString(7, "OK");
                    psmt.setString(8, item_name.getItem_code());
                    psmt.setInt(9, item_name.getQuantity());
                    rowsAffected = psmt.executeUpdate();

                    if (rowsAffected > 0) {
                        status = true;
                        item_id = item_names_id;
                    } else {
                        status = false;
                    }
                    rowsAffected = insertImageRecord(rowsAffected, item_name, itr, destination, item_id, image_name, image_count);
                    // rowsAffected = updateImageRecord(rowsAffected, item_name, itr, destination, item_id, image_name, image_count, item_names_id);
//                    if (rowsAffected > 0 && (!item_name.getImage_path().isEmpty())) {
//                       // String[] image_name = {item_name.getImage_path()};
//                        String revision_no = getRevisionnoForImage(item_name, item_names_id);
//                        for (int i = 0; i < image_name.length; i++) {
//                           // String tempExt = image_name[i];
//                            String image_uploaded_for = "";
//                            if (!tempExt.isEmpty()) {
//                                String middleName = "";
//
//                                String fieldName = "";
//                                String update_image_query = "";
//
//                                //if (tempExt.equals(image_name[0])) {
//                                    middleName = "img_Item_";
//                                    destination = "C:\\ssadvt_repository\\APL\\item\\";
//                                    fieldName = "item_image";
//
//                                    update_image_query = " UPDATE item_image_details SET active=? "
//                                            + "WHERE item_names_id=? and revision_no=? ";
//                                }
//
//                                PreparedStatement pstmt1 = (PreparedStatement) connection.prepareStatement(query4);
//
//                                ResultSet rset = pstmt1.executeQuery();
//                                if (rset.next()) {
//                                    System.err.print("UPDATE--------------");
//
//                                    pstmt1 = connection.prepareStatement(update_image_query);
//                                    pstmt1.setString(1, "N");
//                                    pstmt1.setInt(2, item_names_id);
//                                    pstmt1.setString(3, revision_no);
//                                    int rowsAffectedImage = pstmt1.executeUpdate();
//                                    if (rowsAffectedImage >= 1) {
//                                        revision_no = revision_no + 1;
//                                    }
//                                }
//                                int index = tempExt.lastIndexOf(".");
//                                int index1 = tempExt.length();
//                                String Extention = tempExt.substring(index + 1, index1);
//                                tempExt = "." + Extention;
//                                String imageName = middleName + item_id + tempExt;
//                                item_name.setImage_name(imageName);
//                                //        rowsAffected = insertImageRecord(KeyPerson key,imageName, image_uploaded_for, current_date, kp_id);
////                                if (rowsAffected > 0) {
////                                    WirteImage(item_name, itr, destination, imageName, fieldName);
////                                }
//                            }
//                            pstmt = connection.prepareStatement(query5);
//                            pstmt.setString(1, item_name.getImage_name());
//                            pstmt.setString(2, destination);
//                            pstmt.setString(3, current_date);
//                            pstmt.setString(4, "this image is for site");
//                            pstmt.setInt(5, item_id);
//                            pstmt.setString(6, revision_no);
//                            pstmt.setString(7, "Y");
//                            pstmt.setString(8, "ok");
//                            rowsAffected = pstmt.executeUpdate();
//
//                        }
//                        pstmt.close();
//
//                    }
                    if (rowsAffected > 0) {
                        status = true;
                        message = "Record updated successfully.";
                        msgBgColor = COLOR_OK;
                        connection.commit();
                    } else {
                        status = false;
                        message = "Cannot update the record, some error.";
                        msgBgColor = COLOR_ERROR;
                        connection.rollback();
                    }
                }

            }
        } catch (Exception e) {
            System.out.println("ItemNameModel updateRecord() Error: " + e);
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

    public int getImageCount(int item_name_id) {
        int image_count = 0;
        String image_name = "";

        try {

            String query = " SELECT image_name FROM item_image_details"
                    + " WHERE item_names_id =" + item_name_id + " order by item_image_details_id desc limit 1 ";

            PreparedStatement pstmt = (PreparedStatement) connection.prepareStatement(query);
            ResultSet rset = pstmt.executeQuery();

            while (rset.next()) {
                image_name = rset.getString("image_name");
                String img_name_arr[] = image_name.split(".");
                int indexOfDecimal = image_name.indexOf(".");
                String img_count_arr[] = image_name.substring(0, indexOfDecimal).split("_");
                image_count = Integer.parseInt(img_count_arr[3]);
            }
        } catch (Exception e) {
            System.err.println("Exception-----" + e);
        }
        return image_count;
    }

    public void WirteImage(ItemName key, Iterator itr, String destination, String imageName, String fieldName) {
        int count = 0;

        try {
            while (itr.hasNext()) {
                FileItem item = (FileItem) itr.next();
                makeDirectory(destination);
                try {
                    if (!item.isFormField()) {
                        if (item.getFieldName().equals(fieldName)) {
                            File file = new File(destination + imageName);
                            String image = item.getName();
                            if (image.isEmpty() || image.equals(destination)) {
                            } else {
                                item.write(file);
                                message = "Image Uploaded Successfully.";
                                //  updateImageName(imageName,id);
                                count++;
                            }
                            break;
                        }
                    }
                } catch (Exception e) {
                    System.out.println("Image upload error: " + e);
                }
            }
            //}
        } catch (Exception ex) {
        }
    }

    public boolean makeDirectory(String dirPathName) {
        boolean result = false;
        System.out.println("dirPathName---" + dirPathName);
        //dirPathName = "C:/ssadvt/sor/organisation" ;
        File directory = new File(dirPathName);
        if (!directory.exists()) {
            try {
                result = directory.mkdirs();
            } catch (Exception e) {
                System.out.println("Error - " + e);
            }
        }
        return result;
    }

    public static String getRevisionnoForImage(ItemName key, int item_names_id) {
        String revision = "";
        try {

            String query = " SELECT max(revision_no) as revision_no FROM item_image_details"
                    + " WHERE item_names_id =" + item_names_id + "  && active='Y';";

            PreparedStatement pstmt = (PreparedStatement) connection.prepareStatement(query);

            ResultSet rset = pstmt.executeQuery();

            while (rset.next()) {
                revision = rset.getString("revision_no");

            }
        } catch (Exception e) {
        }
        return revision;
    }

    public static int getRevisionno(ItemName itemName, int item_names_id) {
        int revision = 0;
        try {

            String query = " SELECT max(revision_no) as revision_no FROM item_names WHERE item_names_id =" + item_names_id + "  && active='Y' ";

            PreparedStatement pstmt = (PreparedStatement) connection.prepareStatement(query);

            ResultSet rset = pstmt.executeQuery();

            while (rset.next()) {
                revision = rset.getInt("revision_no");

            }
        } catch (Exception e) {
        }
        return revision;
    }

    public int deleteRecord(int item_names_id) {
        //  int id = getOrganisationID(organisation_id);
        String query = "DELETE FROM item_names WHERE item_names_id = " + item_names_id;
        int rowsAffected = 0;
        try {
            rowsAffected = connection.prepareStatement(query).executeUpdate();
        } catch (Exception e) {
            System.out.println("ItemNameModel deleteRecord() Error: " + e);
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

    public int deleteImageRecord(String item_image_detail_id) throws SQLException {
        int rowsAffected = 0;
        ResultSet rst;
        int count = 0;
        int updateRowsAffected = 0;

        String query2 = "UPDATE item_image_details SET active=? WHERE item_image_details_id=? ";

        try {
            PreparedStatement pstm = connection.prepareStatement(query2);
            pstm.setString(1, "n");
            pstm.setString(2, item_image_detail_id);
            rowsAffected = pstm.executeUpdate();
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

    public static int getImageRevisionno(ItemName itemName, int item_names_id) {
        int revision = 0;
        try {

            String query = " SELECT max(revision_no) as revision_no FROM item_names WHERE item_names_id =" + item_names_id + "  && active='Y' ";

            PreparedStatement pstmt = (PreparedStatement) connection.prepareStatement(query);

            ResultSet rset = pstmt.executeQuery();

            while (rset.next()) {
                revision = rset.getInt("revision_no");

            }
        } catch (Exception e) {
        }
        return revision;
    }

//     public int deleteImageRecord(String item_image_detail_id) {
//         
//        String query = "DELETE FROM item_image_details WHERE item_image_details_id = " + item_image_detail_id;
//        int rowsAffected = 0;
//        try {
//            rowsAffected = connection.prepareStatement(query).executeUpdate();
//        } catch (Exception e) {
//            System.out.println("ItemNameModel deleteRecord() Error: " + e);
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
    public int getItemNamesId(String item_name) {

        String query = "SELECT item_names_id FROM item_name WHERE item_name = ?";
        int id = 0;
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, item_name);
            ResultSet rset = pstmt.executeQuery();
            rset.next();    // move cursor from BOR to valid record.
            id = rset.getInt("item_names_id");
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
        return id;
    }

    public int getItemTypeID(String item_type) {
        String query = "SELECT item_type_id FROM item_type WHERE item_type = ?";
        int id = 0;
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, item_type);
            ResultSet rset = pstmt.executeQuery();
            rset.next();    // move cursor from BOR to valid record.
            id = rset.getInt("item_type_id");
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
        return id;
    }

    public String getItemTypeName(int item_type) {
        String query = "SELECT item_type FROM item_type WHERE item_type_id = ? and active='Y'";
        String name = "";
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setInt(1, item_type);
            ResultSet rset = pstmt.executeQuery();
            rset.next();    // move cursor from BOR to valid record.
            name = rset.getString("item_type");
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
        return name;
    }

    public String getItemName(int item_name_id) {
        String query = "SELECT item_name FROM item_names WHERE item_names_id = ? and active='Y'";
        String name = "";
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setInt(1, item_name_id);
            ResultSet rset = pstmt.executeQuery();
            rset.next();    // move cursor from BOR to valid record.
            name = rset.getString("item_name");
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
        return name;
    }

    public List<String> getItemType(String q) {
        List<String> list = new ArrayList<String>();
        String query = "SELECT item_type FROM item_type where active='Y' ORDER BY item_type";
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            int count = 0;
            q = q.trim();
            while (rset.next()) {    // move cursor from BOR to valid record.
                String item_type = (rset.getString("item_type"));
                if (item_type.toUpperCase().startsWith(q.toUpperCase())) {
                    list.add(item_type);
                    count++;
                }
                if (count == 0) {
                    list.add("No such item_type exists.......");
                }
            }
        } catch (Exception e) {
            System.out.println("Error:ItemNameModel--getItemType()-- " + e);
        }
        return list;
    }

    public List<String> getItemName(String q, String item_type) {
        List<String> list = new ArrayList<String>();
        String query = "SELECT itn.item_name FROM item_names itn,item_type itt where itn.item_type_id=itt.item_type_id and"
                + " itn.active='Y' and itt.active='Y'  ";
        if (!item_type.equals("") && item_type != null) {
            query += " and itt.item_type='" + item_type + "' ";
        }
        query += " group by itn.item_name ORDER BY itn.item_name ";
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            int count = 0;
            q = q.trim();
            while (rset.next()) {    // move cursor from BOR to valid record.
                String item_name = (rset.getString("item_name"));
                if (item_name.toUpperCase().startsWith(q.toUpperCase())) {
                    list.add(item_name);
                    count++;
                }
                if (count == 0) {
                    list.add("No such item_name exists.......");
                }
            }
        } catch (Exception e) {
            System.out.println("Error:ItemNameModel--getItemName()-- " + e);
        }
        return list;
    }

    public List<String> getItemCode(String q, String item_type, String item_name) {
        List<String> list = new ArrayList<String>();
        String query = "SELECT itn.item_code FROM item_names itn,item_type itt where itn.item_type_id=itt.item_type_id and"
                + " itn.active='Y' and itt.active='Y'  ";
        if (!item_type.equals("") && item_type != null) {
            query += " and itt.item_type='" + item_type + "' ";
        }
        if (!item_name.equals("") && item_name != null) {
            query += " and itn.item_name='" + item_name + "' ";
        }
        query += " group by itn.item_name ORDER BY itn.item_name ";
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            int count = 0;
            q = q.trim();
            while (rset.next()) {    // move cursor from BOR to valid record.
                String item_code = (rset.getString("item_code"));
                if (item_code.toUpperCase().startsWith(q.toUpperCase())) {
                    list.add(item_code);
                    count++;
                }
                if (count == 0) {
                    list.add("No such item_code exists.......");
                }
            }
        } catch (Exception e) {
            System.out.println("Error:ItemNameModel--getItemCode()-- " + e);
        }
        return list;
    }

    public String getDestination_Path(String image_uploaded_for) {
        String destination_path = "";
        String query = " SELECT destination_path FROM image_destination id, image_uploaded_for  iuf "
                + " WHERE id.image_uploaded_for_id = iuf.image_uploaded_for_id "
                + " AND iuf.image_uploaded_for = '" + image_uploaded_for + "' ";//traffic_police
        try {
            ResultSet rs = connection.prepareStatement(query).executeQuery();
            if (rs.next()) {
                destination_path = rs.getString("destination_path");
            }
        } catch (Exception ex) {
            System.out.println("ERROR: in getDestination_Path in ItemNameModel : " + ex);
        }
        return destination_path;
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
            System.out.println("OrganisationNameModel closeConnection() Error: " + e);
        }
    }
}
