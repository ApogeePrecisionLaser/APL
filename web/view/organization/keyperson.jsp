<%-- 
    Document   : organisation_type
    Created on : Dec 9, 2011, 2:46:00 PM
    Author     : Soft_Tech
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Data Entry: KeyPerson </title>
     <link rel="stylesheet" href="//code.jquery.com/ui/1.10.4/themes/smoothness/jquery-ui.css">

<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">

<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js" integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q" crossorigin="anonymous"></script>

<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js" integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl" crossorigin="anonymous"></script>


<script src="//code.jquery.com/jquery-1.10.2.js"></script>
<script src="//code.jquery.com/ui/1.10.4/jquery-ui.js"></script>


<script src="https://cdnjs.cloudflare.com/ajax/libs/moment.js/2.21.0/moment.min.js" type="text/javascript"></script>
<link rel="stylesheet" href="time/bootstrap-datetimepicker.min.css">
<script  type="text/javascript" src="time/moment.min.js"></script>
<script  type="text/javascript" src="time/bootstrap-datetimepicker.min.js"></script>
                  <style>
           body {
               background-color: #E7475E;
/*                background-image: linear-gradient(to right, #E7475E , #EC9BA7);*/
           }
           .boxeffext{
               background-color: white;
               box-shadow:-2px 3px 40px 16px rgba(0,0,0,0.47);
             
               margin-top: 6%;
           }
           .topbox
           {
               margin-top: 10%;
               text-align: center;
               color:white;
               font-weight: 900;

           }
           .topbox { 
   color: white; 
   font: bold 52px "Arial Black", Gadget, sans-serif;
   text-shadow: 1px 1px #fe4902, 
                2px 2px #fe4902, 
                3px 3px #fe4902;
   -webkit-transition: all 0.12s ease-out;
   -moz-transition:    all 0.12s ease-out;
   -ms-transition:     all 0.12s ease-out;
   -o-transition:      all 0.12s ease-out;
}
.topbox:hover {
   position: relative; 
   top: -3px; 
   left: -3px; 
   text-shadow: 1px 1px #fe4902, 
                2px 2px #fe4902, 
                3px 3px #fe4902, 
                4px 4px #fe4902, 
                5px 5px #fe4902, 
                6px 6px #fe4902;
}
           
           
           
           
           
           
           
           .maintr
           {
               
               padding-top: 50px;
           }
            
        </style>    
    </head>
    <body>
         <div class="container-fluid">
              <!--DWLayoutDefaultTable-->
            <%@include file="/layout/header.jsp" %>
                             <div class="container  topbox">
                                 <h1>KeyPerson</h1>
                             </div>
            

                                                <div class="container">
                                    <form name="form0" class="form-group row boxeffext" method="POST" action="personCount.do">
                                        <table  align="center"  class="table table-bordered table-striped" width="600">
                                            <tr>
                                             
                                                       <th>  Organisation Name</th>
                                                    <td><input class="form-control" type="text" id="searchOrg" name="searchOrg" value="${searchOrg}" size="20"></td>
                                                    <!--
                                                    <th> Employee Id </th>
                                                    <td>    <input class="form-control" type="text" id="searchEmpCode" name="searchEmpCode" value="${searchEmpCode}" size="20"></td>
                                                -->
                                           
                                           
                                                
                                            <th>  Person Name</th>
                                            <td><input class="form-control" type="text" id="searchKeyPerson" name="searchKeyPerson" value="${searchKeyPerson}"  size="20"></td>
<!--                                                <th>  Designation</th>
                                                <td><input class="form-control" type="text" id="searchDesignation" name="searchDesignation" value="${searchDesignation}" size="20">
                                                </td>-->
                                            <th>  Mobile Number</th>
                                                <td><input class="form-control" type="text" id="searchmobile" name="searchmobile" value="${searchmobile}" size="20">
                                                </td>
                                            </tr>
                                            <tr>
                                                  <th>Office Code</th>
                                                <td><input class="form-control" type="text" id="searchfamily" name="searchfamily" value="${searchfamily}" size="20">
                                                <td>
                                                    <input type="submit" class="btn btn-primary" name="task" id="search" value="Search"></td>
                                                <td>   <input type="submit" class="btn btn-success" name="task1" id="showAllRecords" value="Show All Records"></td>
                                                <td>    <input class="btn btn-danger" type="button" id="viewPdf" name="viewPdf" value="PDF" onclick="displayOrgnList(id)"></td>
                                                <td>   <input class="btn btn-warning" type="button" id="viewExcel" name="viewExcel" value="Excel" onclick="displayOrgnList(id)"></td>
                                                <input type="hidden" id="activee" name="active" value="${active}">
                                                <td>   <label id="org_msg">  </label></td>
                                            </tr>
                                        </table>
                                                
                                          <div class="panel-group container">
    <div class="panel panel-success">
<!--      <div class="panel">Panel with panel-default class</div>-->
      <div class="panel-body">
          <table align="center"  class="table table-bordered table-striped" width="600" class="table table-bordered table-responsive">
              <tr>  
<!--                  <td align="center"><input class="btn btn-success" type="submit"  name="task" id="active" value="ACTIVE RECORDS"></td>
                  <td align="center"><input class="btn btn-info" type="submit" name="task" id="inactive" value="INACTIVE RECORDS"></td>
                  <td align="center"><input class="btn btn-primary" type="submit" name="task" id="all" value="ALL RECORDS"></td>-->
             <td>  <select class="ui dropdown form-control" name="task2" id="hiera" >
                                                                  <option value="ACTIVE RECORDS">ACTIVE RECORDS</option>
                                                                  <option value="INACTIVE RECORDS">INACTIVE RECORDS</option>
                                                               <option value="ALL RECORDS">ALL RECORDS</option>
                                                                      
                                                         </select></td>  
                                                            <td><input type="submit" class="btn btn-primary" id="hiera" name="search_org" value="SEARCH RECORDS" onclick="setStatus(id)"></td>
      </tr>
          </table>
      </div>
    </div>  
                           </div> 
                                                
                                                
                                                
                                    </form>
                                                </div>
                              
                                       
                                                <DIV class="container">
                                    <form name="form1" method="POST" action="personCount.do" class="boxeffext">
                                        
                                            <table id="table1" width="600"  border="1"  align="center" class="table table-bordered table-striped boxeffext">
                                              
                                            </table>
                                    </form>
                                            </DIV>
                     
                                            
                                    <div class="container">
                                        <form name="form2" method="POST" class="form-group row boxeffext" action="personCount.do" encType="multipart/form-data"  onsubmit="return verify()">
                                            <table id="table2"  class="table table-bordered table-striped" border="0"  align="center" style="width: 100%">
                                                <tr id="message">
                                                        <td colspan="2" bgcolor="${msgBgColor}"><b>Result: ${message}</b></td>
                                                </tr>
                                                <tr>
                                                     <th class="heading1">Data Entry</th>
                                                     <td>
                                                         <strong>Default Value</strong>&nbsp;&nbsp;<input type="checkbox" class="checkbox-inline" id="default" name="default" onclick="setDefaultValues(id)"></td>
                                                     
                                                </tr>
                                                <tr>


                                                <input type="hidden" id="key_person_id" name="key_person_id" value="">
                                                <input type="hidden" id="general_image_details_id" name="general_image_details_id" value="" size="28"   />
                                                <input type="hidden" id="i" name="i" value=${i}>
                                                
                                                <th class="heading1" >Office Code</th>
                                                <td><input class="form-control" type="text" id="office_code" name="office_code"  size="20" disabled></td>
                                                <th class="heading1" >Office Name</th>
                                                <td><input class="form-control" type="text" id="org_office_name" name="org_office_name"  size="28"  disabled></td>
                                                <th class="heading1" >Employee Id</th>
                                                <td><input class="form-control" type="text" id="employeeId" name="employeeId" value=""  size="28" disabled></td>

                                                </tr>
                                                <tr>
                                                    <th class="heading1" >Salutation </th>
                                                    <td><select class="dropdown-toggle form-control" id="salutation" name="salutation" value=""  disabled>
                                                            <option>---Select--- </option>
                                                            <option style="text-align: center">Mr.</option>
                                                            <option style="text-align: center">Ms.</option>
                                                            <option style="text-align: center">Mrs.</option>
                                                        </select>
                                                    </td>

                                                    <th class="heading1" >Person Name</th>
                                                    <td><input class="form-control" type="text" id="key_person_name" name="key_person_name" value=""  size="30" disabled></td>
                                                    <th class="heading1" >Password</th>
                                                    <td><input class="form-control" type="password" id="password" name="password" value=""  size="30" disabled></td>
                                                </tr>
                                                <tr>
                                                    <th class="heading1" >Father's Name</th>
                                                    <td><input class="form-control" type="text" id="father_name" name="father_name" value=""  size="30" disabled></td>
                                                <th class="heading1" >Blood Group</th>
                                                     <td>  <select class="ui dropdown form-control" name="blood" id="blood" disabled>
                                                              <option>---Select--- </option>
                                                                  <option value="A+">A+</option>
                                                                  <option value="A-">A-</option>
                                                               <option value="B+">B+</option>
                                                                      <option value="B-">B-</option>
                                                                      <option value="O+">O+</option>
                                                                  <option value="O-">O-</option>
                                                               <option value="AB+">AB+</option>
                                                                      <option value="AB-">AB-</option>
                                                         </select></td>
<!--                                                <th class="heading1" >Relation</th>
                                                    <td><input class="form-control" type="text" id="relation" name="relation" value=""  size="30" disabled></td>-->
                                                </tr>
                                                <tr>
                                                    <th class="heading1" >Date Of Birth</th>
                                                    <td><input class="form-control" type="date" id="date_of_birth" name="date_of_birth" value=""  size="5" disabled></td>
                                                    <th class="heading1" >Designation</th>
                                                    <td><input class="form-control" type="text" id="designation" name="designation" value=""  size="30" disabled></td>
                                                    
                                                </tr>
                                                <tr>
                                                    <th class="heading1" >Address Line1</th>
                                                    <td><input class="form-control" type="text" id="address_line1" name="address_line1" value=""  size="28"  disabled></td>
                                                    <th class="heading1" >Address Line2</th>
                                                    <td><input class="form-control" type="text" id="address_line2" name="address_line2" value=""   size="25" disabled></td>
                                                    <th class="heading1" >Address Line3</th>
                                                    <td><input class="form-control" type="text" id="address_line3" name="address_line3" value=""  size="30"  disabled></td>
                                                </tr>
                                                
                                                
                                                 
                                                <tr class="exp">
                                                    
                                                     <th class="heading1" >Emergency Contact Name</th>
                                                    <td><input class="form-control" type="text" id="emergency_name" name="emergency_name" value="" size="28"  disabled></td> 
                                               <th class="heading1" >Emergency Contact Number</th>
                                                    <td><input class="form-control" type="text" id="emergency_number" name="emergency_number" value="" size="28"  disabled>
                                                
                                                    </td> 
                                                    
                                                    
                                                    
                                                </tr>
                                                <tr>
                                                <td class="container1" colspan="6">
    <button class="add_form_field btn btn-info" id="emerg">Add Emergency Name and Contact Fields &nbsp; 
      <span style="font-size:16px; font-weight:bold;">+ </span>
    </button>
                                                 

            </td>
            
            </tr>
                                                 
                                           

                                                                                         
                                                
                                                 <tr>
                                                     <th class="heading1" >ID Type</th>
                                                     <td>  <select class="ui dropdown form-control" name="id_type" id="id_type" disabled>
                                                              <option>---Select--- </option>
                                                                  <c:forEach var="id_list"  items="${requestScope['id_list']}">
                                                     
                                                    <option value="${id_list}">
                                                       <c:out value="${id_list}"/>
                                                    </option>
                                                         </c:forEach>
                                                         </select></td>
                                                    
                                                    <th class="heading1" >ID No.</th>
                                                    <td><input class="form-control" type="text" id="id_no" name="id_no" value=""  size="30"  disabled></td>
                                                 
                                                </tr>
                                                
                                                 <tr>
                                                    <th class="heading1">Gender</th>
                                                    <td>
                                                      
<!--                                                        <input class="form-control" type="text" id="super" name="super" value="" size="45" disabled>-->
                                             <input type="radio" id="genderm" name="gender" value="M" disabled>
                                                      <label for="Yes">Male</label><br>
                                                      <input type="radio" id="genderf" name="gender" value="F" disabled>
                                                      <label for="No">Female</label><br>
                                                       
                                                   </td>>
                                                   
                                                </tr>
                                                
                                                <tr>                                                    
                                                    <th class="heading1" >City</th>
                                                    <td><input class="form-control" type="text" id="city_name" name="city_name"  size="25" value="" disabled></td>
                                                     <th class="heading1" >Mobile No. 1st</th>
                                                     <td><input class="form-control" type="text" id="mobile_no1" name="mobile_no1" value=""  size="30" onkeyup="myFun(id)" maxlength="10"  disabled></td>
                                                    <th class="heading1" >Mobile No. 2nd</th>
                                                    <td><input class="form-control" type="text" id="mobile_no2" name="mobile_no2" value=""  size="25" maxlength="10" disabled></td>
                                                </tr>
                                                <tr>                                                    
                                                    <th class="heading1" >LandLine No. 1st</th>
                                                    <td><input class="form-control" type="text" id="landline_no1" name="landline_no1" value=""  size="25"  disabled></td>
                                                    <th class="heading1" >LandLine No. 2nd</th>
                                                    <td ><input class="form-control" type="text" id="landline_no2" name="landline_no2" value=""  size="30"   disabled></td>
                                                    
                                                </tr>
                                                <tr>
                                                    <th class="heading1" >Email ID 1st</th>
                                                    <td><input class="form-control" type="text" id="email_id1" name="email_id1" value=""  size="30"  disabled></td>
                                                    <th class="heading1" >Email ID 2nd</th>
                                                    <td><input class="form-control" type="text" id="email_id2" name="email_id2" value=""  size="25"  disabled></td>
                                                    
                                                     <tr>
                                    <th class="heading1">Latitude</th><td><input class="form-control" type="text" id="latitude" name="latitude" value="" size="20" disabled></td>
                                    <th class="heading1">Longitude</th><td><input  class="form-control" type="text" id="longitude" name="longitude" value="" size="20" disabled><input style="margin-left:18px;" class="btn btn-info" type="button" id="get_cordinate" value="Get Cordinate" onclick="openMapForCord()" disabled></td>
                                </tr>
                                                    
                                                    
                                <tr>    
                                                    
                                                    <th class="heading1">Select Photo </th>
                                                    <td>
                                                        <input type="file" id="design_name" name="design_name" value="" size="35"  onchange="readURL(this);" disabled  />
                                                        <input type="hidden" id="img" name="img" value="">
                                                    </td>
                                                    <th class="heading1">Select ID OR DL </th>
                                                    <td>
                                                        <input type="file" id="id_proof" name="id_proof" value="" size="30"  onchange="readURL(this);"   disabled/>
                                                        <input type="hidden" id="id_img" name="img" value="">
                                                    </td>
                                                </tr>
                                                <tr>
                                                     <input type="hidden" name="familyoffice" id="familyoffice" value="${noOfRowsTraversed}">
                                                <input type="hidden" name="relation" id="relation" value="${noOfRowsTraversed}">
                                                <input type="hidden" name="familydesignation" id="familydesignation" value="${noOfRowsTraversed}">
                                                </tr>
                                                <tr>
                                                    <td align='center' colspan="10">
                                                        <input type="button" class="btn btn-primary" name="edit" id="edit" value="Edit" onclick="makeEditable(id)" disabled>
                                                    <input type="submit" class="btn btn-success" name="task" id="save" value="Save" onclick="setStatus(id)" disabled>
                                                    <input type="submit" class="btn btn-info" name="task" id="save_As" value="Save AS New" onclick="setStatus(id)" disabled>
                                                    <input type="reset" class=" btn btn-warning" name="new" id="new" value="New" onclick="makeEditable(id)" >
                                                    <input type="submit" class="btn btn-success" name="task" id="delete" value="Delete" onclick="setStatus(id)" disabled>
                                                    </td>
                                                </tr>
                                                <%-- These hidden fields "lowerLimit", "noOfRowsTraversed", and "clickedButton" belong to form2 of table2. --%>
                                                <input type="hidden" name="lowerLimit" value="${lowerLimit}">
                                                <input type="hidden" name="noOfRowsTraversed" value="${noOfRowsTraversed}">
                                               
                                                <input type="hidden" id="clickedButton" value="">
                                                <input  type="hidden" id="searchOrgType" name="searchOrgType" value="${searchOrgType}" >
                                            </table>
                                        </form>
                                    </div>
                           

                    </DIV>
                
                 <%@include file="/layout/footer.jsp" %>
        
    </body>
</html>