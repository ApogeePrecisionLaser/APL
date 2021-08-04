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
        <title>Data Entry: Organization Office Type </title>
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
<!--<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">-->
<!--  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>-->
        <script type="text/javascript" language="javascript">
            $(function () {  
                 
              $("#searchOrgOfficeType").autocomplete({
                    
            source: function (request, response) {
                var random = document.getElementById("searchOrgOfficeType").value;
                $.ajax({
                    url: "orgOfficeTypeCont.do",
                    dataType: "json",
                    data: {action1: "getOrganisationOfficeType",str:random},
                    success: function (data) {

                        console.log(data);
                        response(data.list);
                    }, error: function (error) {
                        console.log(error.responseText);
                        response(error.responseText);
                    }
                });
            },
            select: function (events, ui) {
                console.log(ui);
                $('#searchOrgOfficeType').val(ui.item.label); // display the selected text
                return false;
            }
        });
    });
            function makeEditable(id) {
                document.getElementById("office_type").disabled = false;
                //document.getElementById("office_code").disabled = false;
                document.getElementById("description").disabled = false;
                if(id == 'new') {
                    // document.getElementById("message").innerHTML = "";      // Remove message
                    $("#message").html("");
                    document.getElementById("office_type_id").value = "";
                    document.getElementById("save").disabled = false;
                    document.getElementById("edit").disabled = true;
                    document.getElementById("delete").disabled = true;
                    document.getElementById("save_As").disabled = true;
                    setDefaultColor(document.getElementById("noOfRowsTraversed").value, 4);
                    document.getElementById("office_code").focus();
                }
                if(id == 'edit') {
                    document.getElementById("save_As").disabled = false;
                    document.getElementById("delete").disabled = false;
                }
                document.getElementById("save").disabled = false;
            }
            function setDefaultColor(noOfRowsTraversed, noOfColumns) {
                for(var i = 0; i < noOfRowsTraversed; i++) {
                    for(var j = 1; j <= noOfColumns; j++) {
                        document.getElementById("t1c" + (i * noOfColumns + j)).bgColor = "";     // set the default color.
                    }
                }
            }
            function fillColumns(id) {
                var noOfRowsTraversed = document.getElementById("noOfRowsTraversed").value;
                var noOfColumns = 3;
                var columnId = id;                              <%-- holds the id of the column being clicked, excluding the prefix t1c e.g. t1c3 (column 3 of table 1). --%>
                columnId = columnId.substring(3, id.length);    <%-- for e.g. suppose id is t1c3 we want characters after t1c i.e beginIndex = 3. --%>
                var lowerLimit, higherLimit, rowNo = 0;
                for(var i = 0; i < noOfRowsTraversed; i++) {
                    lowerLimit = i * noOfColumns + 1;       // e.g. 11 = (1 * 10 + 1)
                    higherLimit = (i + 1) * noOfColumns;    // e.g. 20 = ((1 + 1) * 10)
                    rowNo++;
                    if((columnId >= lowerLimit) && (columnId <= higherLimit)) break;
                }
                setDefaultColor(noOfRowsTraversed, noOfColumns);        // set default color of rows (i.e. of multiple coloumns).
                var t1id = "t1c";       // particular column id of table 1 e.g. t1c3.
                for(var i = 0; i < noOfColumns; i++) {
                    // set the background color of clicked/selected row to yellow.
                    document.getElementById(t1id + (lowerLimit + i)).bgColor = "";
                }
                // Now get clicked row data, and set these into the below edit table.
                document.getElementById("office_type_id").value = document.getElementById("office_type_id" + rowNo).value;
                document.getElementById("office_type").value = document.getElementById(t1id + (lowerLimit + 1)).innerHTML;
                document.getElementById("description").value = document.getElementById(t1id + (lowerLimit + 2)).innerHTML;
                for(var i = 0; i < noOfColumns; i++) {
                    document.getElementById(t1id + (lowerLimit + i)).bgColor = "#d0dafd";        // set the background color of clicked row to yellow.
                }
                document.getElementById("edit").disabled = false;
                if(!document.getElementById("save").disabled)   // if save button is already enabled, then make edit, and delete button enabled too.
                {
                    document.getElementById("delete").disabled = false;
                    document.getElementById("save_As").disabled = true;
                }
                //  document.getElementById("message").innerHTML = "";      // Remove message
                $("#message").html("");
            }
            function setStatus(id) {
                if(id == 'save') {
                    document.getElementById("clickedButton").value = "Save";
                }
                else if(id == 'save_As'){
                    document.getElementById("clickedButton").value = "Save AS New";
                }
                else {
                    document.getElementById("clickedButton").value = "Delete";;
                }
            }
            function myLeftTrim(str) {
                var beginIndex = 0;
                for(var i = 0; i < str.length; i++) {
                    if(str.charAt(i) == ' ')
                        beginIndex++;
                    else break;
                }
                return str.substring(beginIndex, str.length);
            }
            function verify() {
                var result;
                if(document.getElementById("clickedButton").value == 'Save' || document.getElementById("clickedButton").value == 'Save AS New') {
                    var office_type = document.getElementById("office_type").value;
                   
                    if(myLeftTrim(office_type).length == 0) {
                        //  document.getElementById("message").innerHTML = "<td colspan='5' bgcolor='coral'><b>Organisation Office Type is required...</b></td>";
                        $("#message").html("<td colspan='5' bgcolor='coral'><b>Organisation Office Type is required...</b></td>");
                        document.getElementById("office_type").focus();
                        return false; // code to stop from submitting the form2.
                    }
                    if(result == false) {
                        // if result has value false do nothing, so result will remain contain value false.
                    } else {
                        result = true;
                    }

                    if(document.getElementById("clickedButton").value == 'Save AS New'){
                        result = confirm("Are you sure you want to save it as New record?")
                        return result;
                    }
                } else {
                    result = confirm("Are you sure you want to delete this record?");
                }
                return result;
            }
             function orgOfficeReport(id)
            {
                var searchOfficeType = document.getElementById('searchOrgOfficeType').value;
                                var active = document.getElementById("activee").value;
                var action;
                if(id=='viewPdf')
                {
                 action="task=generateOrgOfficeReport&searchOrgOfficeType="+searchOfficeType+"&active="+active;
               
                }
               else
               {
                action="task=generateOrgOfficeXlsReport&searchOrgOfficeType="+searchOfficeType+"&active="+active;
            }
            var url="orgOfficeTypeCont.do?"+action;
                popup = popupWindow(url,"Org_Office_View_Report");
            }
            function popupWindow(url,windowName)
            {
                var windowfeatures= "left=50, top=50, width=1000, height=500, resizable=no, scrollbars=yes, location=0, menubar=no, status=no, dependent=yes";
                return window.open(url,windowName,windowfeatures)
            }
            
            
            function  myrequest()
            {
                var active = document.getElementById("active").value;
                 var inactive = document.getElementById("inactive").value; 
                     var all = document.getElementById("all").value; 
                     
                     
                
            }
            
        </script>
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
                                 <h1>Org Office Type</h1>
                             </div>
                       

            
                                                <div class="container">
                          
                                    <form name="form0" class="form-group row boxeffext" method="POST" action="orgOfficeTypeCont.do" >
                                        <table  align="center"  class="table table-bordered table-striped" width="600" class="table table-bordered table-responsive">
                                            <tr>
                                                <th>Office Type</th>
                                                <td><input class="form-control" type="text" id="searchOrgOfficeType" name="searchOrgOfficeType" value="${searchOrgOfficeType}" size="30" ></td>
                                                <td><input class="btn btn-primary" type="submit" name="task" id="searchIn" value="Search"></td>
                                                <td><input class="btn btn-success" type="submit" name="task" id="showAllRecords" value="Show All Records">
                                                <td> <input class="btn btn-warning" type="button" id="viewPdf" name="viewPdf" value="PDF" onclick="orgOfficeReport(id);"/></td>
                                                <td> <input class="btn btn-info" type="button" id="viewXls" name="viewXls" value="Excel" onclick="orgOfficeReport(id);"/>
                                                  <input type="hidden" id="activee" name="active" value="${active}">
                                                </td>

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
             <td>  <select class="ui dropdown form-control" name="task" id="hiera" >
                                                              <option>${ac}(CURRENTLY)</option>
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
                                    <form name="form1" method="POST" action="orgOfficeTypeCont.do" class="boxeffext ">
                                        
                                            <table id="table1" width="600"  border="1"  align="center" class="table table-bordered table-striped">
                                                <tr>
                                                    <th class="heading">S.No.</th>
                                                    <th class="heading">Office Type</th>
                                                    <th class="heading">Description</th>
                                                </tr>
                                                <c:forEach var="orgOfficeType" items="${requestScope['orgOfficeTypeList']}" varStatus="loopCounter">
                                                    <tr  class="${loopCounter.index % 2 == 0 ? 'even': 'odd'}" >
                                                                <td id="t1c${IDGenerator.uniqueID}" onclick="fillColumns(id)" align="center">
                                                                    <input type="hidden" id="office_type_id${loopCounter.count}" value="${orgOfficeType.office_type_id}">${lowerLimit - noOfRowsTraversed + loopCounter.count}
                                                                </td>
                                                                <td id="t1c${IDGenerator.uniqueID}" class="new_input" onclick="fillColumns(id)" >${orgOfficeType.office_type}</td>
                                                                <td id="t1c${IDGenerator.uniqueID}" class="new_input" onclick="fillColumns(id)" >${orgOfficeType.description}</td>
                                                    </tr>
                                                </c:forEach>
                                                <tr>
                                                    <td align='center' colspan="4">
                                                        <c:choose>
                                                            <c:when test="${showFirst eq 'false'}">
                                                                <input class="btn btn-primary" type='submit' name='buttonAction' value='First' disabled>
                                                                  <input type="hidden" id="activee" name="active" value="${active}">
                                                          
                                                            </c:when>
                                                            <c:otherwise>
                                                                <input class="btn btn-primary" type='submit' name='buttonAction' value='First'>
                                                                  <input type="hidden" id="activee" name="active" value="${active}">
                                                            </c:otherwise>
                                                        </c:choose>
                                                        <c:choose>
                                                            <c:when test="${showPrevious == 'false'}">
                                                                <input class="btn btn-success" type='submit' name='buttonAction' value='Previous' disabled>
                                                                 <input type="hidden" id="activee" name="active" value="${active}">
                                                            </c:when>
                                                            <c:otherwise>
                                                                <input class="btn btn-success" type='submit' name='buttonAction' value='Previous'>
                                                                 <input type="hidden" id="activee" name="active" value="${active}">
                                                            </c:otherwise>
                                                        </c:choose>
                                                        <c:choose>
                                                            <c:when test="${showNext eq 'false'}">
                                                                <input class="btn btn-warning" type='submit' name='buttonAction' value='Next' disabled>
                                                                 <input type="hidden" id="activee" name="active" value="${active}">
                                                            </c:when>
                                                            <c:otherwise>
                                                                <input class="btn btn-warning" type='submit' name='buttonAction' value='Next'>
                                                                 <input type="hidden" id="activee" name="active" value="${active}">
                                                            </c:otherwise>
                                                        </c:choose>
                                                        <c:choose>
                                                            <c:when test="${showLast == 'false'}">
                                                                <input class="btn btn-info" type='submit' name='buttonAction' value='Last' disabled>
                                                                 <input type="hidden" id="activee" name="active" value="${active}">
                                                            </c:when>
                                                            <c:otherwise>
                                                                <input class="btn btn-info" type='submit' name='buttonAction' value='Last'>
                                                                 <input type="hidden" id="activee" name="active" value="${active}">
                                                            </c:otherwise>
                                                        </c:choose>
                                                    </td>
                                                </tr>
                                                <%-- These hidden fields "lowerLimit", and "noOfRowsTraversed" belong to form1 of table1. --%>
                                                <input type="hidden" name="lowerLimit" value="${lowerLimit}">
                                                <input type="hidden" id="noOfRowsTraversed" name="noOfRowsTraversed" value="${noOfRowsTraversed}">
                                                <input  type="hidden" id="searchOrgOfficeType" name="searchOrgOfficeType" value="${searchOrgOfficeType}" >
                                                <input  type="hidden" id="searchOrgOfficeCode" name="searchOrgOfficeCode" value="${searchOrgOfficeCode}" >
                                            </table>
                                    </form>
                       </DIV>
                                            
                                    <div class="container">
                                        <form name="form2" method="POST" class="form-group row boxeffext" action="orgOfficeTypeCont.do" onsubmit="return verify()" >
                                            <table id="table2"  class="table table-bordered table-striped" border="0"  align="center" width="500" >
                                                <tr id="message">
                                                    <c:if test="${not empty message}">
                                                        <td colspan="2" bgcolor="${msgBgColor}"><b>Result: ${message}</b></td>
                                                    </c:if>
                                                </tr>
                                              
                                                <tr>


                                                    <th class="heading1">Office Type</th>
                                                    <td>
                                                        <input type="hidden" id="office_type_id" name="office_type_id" value="" >
                                                        <input class="form-control" type="text" id="office_type" name="office_type" size="30" value="" disabled>
                                                    </td>
                                                </tr>
                                                <tr>

                                                    <th class="heading1">Description</th><td><input class="form-control" type="text" id="description" name="description" size="30" value="" disabled></td>
                                                </tr>
                                                <tr>
                                                    <td align='center' colspan="4">
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
                                                <input  type="hidden" id="searchOrgOfficeType" name="searchOrgOfficeType" value="${searchOrgOfficeType}" >
                                                <input type="hidden" id="clickedButton" value="">
                                            </table>
                                        </form>
                                    </div>
                          
                                                

                    </DIV>
    
          <%@include file="/layout/footer.jsp" %>
    
    </body>
</html>