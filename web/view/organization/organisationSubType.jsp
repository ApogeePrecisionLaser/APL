<%--
    Document   : dataEntry_lighting
    Created on : Dec 9, 2011, 12:36:33 PM
    Author     : Soft_Tech
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Data Entry: Organization Sub Type </title>
             <link rel="shortcut icon" href="/imageslayout/ssadvt_logo.ico">
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
        <script type="text/javascript" language="javascript">
//            jQuery(function(){              
//                $("#org_type_name").autocomplete("organisationSubTypeCont.do", {
//                    extraParams: {
//                        param1: function() { return "getOrgTypeName"}
//                    }
//                });
//                $("#searchOrgType").autocomplete("organisationSubTypeCont.do", {
//                    extraParams: {
//                        param1: function() { return "getOrgTypeName"}
//                    }
//                });
//                $("#searchOrgSubType").autocomplete("organisationSubTypeCont.do", {
//                    extraParams: {
//                        param1: function() { return "getOrgSubTypeName"}
//                    }
//                });
//            });
             $(function () {  
                 
              $("#org_type_name").autocomplete({
                    
            source: function (request, response) {
                var random = document.getElementById("org_type_name").value;
                $.ajax({
                    url: "organisationSubTypeCont.do",
                    dataType: "json",
                    data: {action1: "getOrgTypeName",str:random},
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
                $('#org_type_name').val(ui.item.label); // display the selected text
                return false;
            }
        });
        
        
         $("#searchOrgType").autocomplete({
                     
            source: function (request, response) {
                var random = document.getElementById("searchOrgType").value;
                $.ajax({
                    url: "organisationSubTypeCont.do",
                    dataType: "json",
                    data: {action1: "getOrgTypeName",str:random},
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
                $('#searchOrgType').val(ui.item.label); // display the selected text
                return false;
            }
        });
        
        
        
         $("#searchOrgSubType").autocomplete({
                    
            source: function (request, response) {
                var org_type = document.getElementById("searchOrgType").value;
                 var random = document.getElementById("searchOrgSubType").value;
                $.ajax({
                    url: "organisationSubTypeCont.do",
                    dataType: "json",
                    data: {action1: "searchOrgSubTypeName",
                           action2: org_type,str:random},
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
                $('#searchOrgSubType').val(ui.item.label); // display the selected text
                return false;
            }
        });
        
        
   });       
           
    
    
    
    function makeEditable(id) {
                document.getElementById("org_type_name").disabled = false;
                document.getElementById("organisation_sub_type_name").disabled = false;
                if(id == 'new') {
                    document.getElementById("org_type_name").focus();
                    // document.getElementById("message").innerHTML = "";      // Remove message
                    $("#message").html("");
                    document.getElementById("organisation_sub_type_id").value = "";
                    document.getElementById("edit").disabled = true;
                    document.getElementById("delete").disabled = true;
                    document.getElementById("save_As").disabled = true;
                    setDefaultColor(document.getElementById("noOfRowsTraversed").value, 3);
                }
                if(id == 'edit'){
                    document.getElementById("save_As").disabled = false;
                    document.getElementById("org_type_name").focus();
                    document.getElementById("delete").disabled = false;
                }
                document.getElementById("save").disabled = false;
            };
            function setDefaultColor(noOfRowsTraversed, noOfColumns) {
                for(var i = 0; i < noOfRowsTraversed; i++) {
                    for(var j = 1; j <= noOfColumns; j++) {
                        document.getElementById("t1c" + (i * noOfColumns + j)).bgColor = "";     // set the default color.
                    }
                }
            };
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

                document.getElementById("organisation_sub_type_id").value= document.getElementById("organisation_sub_type_id" + rowNo).value;
                document.getElementById("org_type_name").value = document.getElementById(t1id +(lowerLimit+1)).innerHTML;
                document.getElementById("organisation_sub_type_name").value = document.getElementById(t1id +(lowerLimit+2)).innerHTML;
                for(var i = 0; i < noOfColumns; i++) {
                    document.getElementById(t1id + (lowerLimit + i)).bgColor = "#d0dafd";        // set the background color of clicked row to yellow.
                }
                document.getElementById("edit").disabled = false;
                if(!document.getElementById("save").disabled)   // if save button is already enabled, then make edit, and delete button enabled too.
                {
                    document.getElementById("save_As").disabled = true;
                    document.getElementById("delete").disabled = false;
                }
                // document.getElementById("message").innerHTML = "";      // Remove message
                $("#message").html("");
            };
            function setStatus(id) {
                if(id == 'save'){
                    document.getElementById("clickedButton").value = "Save";
                }
                else if(id == 'save_As'){
                    document.getElementById("clickedButton").value = "Save AS New";
                }
                else document.getElementById("clickedButton").value = "Delete";;
            };
            function myLeftTrim(str) {
                var beginIndex = 0;
                for(var i = 0; i < str.length; i++) {
                    if(str.charAt(i) == ' ')
                        beginIndex++;
                    else break;
                }
                return str.substring(beginIndex, str.length);
            };
            function verify() {
                var result;                
                if(document.getElementById("clickedButton").value == 'Save' || document.getElementById("clickedButton").value == 'Save AS New') {
                    var organisation_sub_type_name = document.getElementById("org_type_name").value;
                    var org_type_name = document.getElementById("organisation_sub_type_name").value;
                    if(myLeftTrim(organisation_sub_type_name).length == 0) {
                        // document.getElementById("message").innerHTML = "<td colspan='5' bgcolor='coral'><b>Organisation Sub Type Name is required...</b></td>";
                        $("#message").html("<td colspan='5' bgcolor='coral'><b>Organisation Sub Type Name is required...</b></td>");
                        document.getElementById("org_type_name").focus();
                        return false;  // code to stop from submitting the form2.
                    }
                    if(myLeftTrim(org_type_name).length == 0) {
                        // document.getElementById("message").innerHTML = "<td colspan='5' bgcolor='coral'><b>Organisation Name is required...</b></td>";
                        $("#message").html("<td colspan='5' bgcolor='coral'><b>Organisation Name is required...</b></td>");
                        alert("Organization Type Name is required");
                        document.getElementById("organisation_sub_type_name").focus();
                        return false;  // code to stop from submitting the form2.
                    }
                    if(result == false)
                    {// if result has value false do nothing, so result will remain contain value false.
                    }
                    else{ result = true;
                    }
                    if(document.getElementById("clickedButton").value == 'Save AS New'){
                        result = confirm("Are you sure you want to save it as New record?")
                        return result;
                    }
                } else result = confirm("Are you sure you want to delete this record?")
                return result;
            };
            function displayOrgSubTypeList(id)
            {
              var searchOrgType = document.getElementById('searchOrgType').value;
              var searchOrgSubType = document.getElementById('searchOrgSubType').value;             
                var action;
                if(id=='viewPdf')
                 action="task=generateOrgReport&searchOrgType="+searchOrgType+"&searchOrgSubType="+searchOrgSubType;
               else
                action="task=generateOrgXlsReport&searchOrgType="+searchOrgType+"&searchOrgSubType="+searchOrgSubType;
                var url="organisationSubTypeCont.do?"+action;
                popup = popupWindow(url,"Org_Subtype_Report");
            };
            function popupWindow(url,windowName)
            {
                var windowfeatures= "left=50, top=50, width=1000, height=500, resizable=no, scrollbars=yes, location=0, menubar=no, status=no, dependent=yes";
                return window.open(url,windowName,windowfeatures)
            };
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
                                 <h1>Organisation SubType</h1>
                             </div>

           
                                                <div class="container">
                          
                                                    <form name="form1" class="form-group row boxeffext" method="POST" action="organisationSubTypeCont.do" >
                                                    <table  align="center"  class="table table-bordered table-striped"  width="600">
                                                        <tr>
                                                            <th> Organization Type </th>
                                                            <td><input class="form-control" type="text" id="searchOrgType" name="searchOrgType" value="${searchOrgType}" size="30" ></td>
                                                            <th> Organization Sub Type </th>
                                                            <td><input class="form-control" type="text" id="searchOrgSubType" name="searchOrgSubType" value="${searchOrgSubType}" size="30" ></td>
                                                            <td><input class="btn btn-primary" type="submit" name="task" id="searchInOrgSubType" value="Search"></td>
                                                            <td><input class="btn btn-success" type="submit" name="task" id="showAllRecords" value="Show All Records"></td>
                                                       
<!--                                                        <td colspan="5" align="center">-->
<td>   <input class="btn btn-danger" type="button" id="viewPdf" name="viewPdf" value="PDF" onclick="displayOrgSubTypeList(id);"/></td>
<td>    <input class="btn btn-info" type="button" id="viewXls" name="viewXls" value="Excel" onclick="displayOrgSubTypeList(id);"/>
                                                           
                                                    </td>
                                                        </tr>
                                                    </table>
                                                    </form>
                                                </div>
                                       
                                                       <DIV class="container">     
                                                    <form name="form1" method="POST" action="organisationSubTypeCont.do" class="boxeffext ">
                                                    
                                                        <table id="table1"  border="1" align="center" width="600"  class="table table-bordered table-striped">
                                                             <tr>
                                                                <th class="heading">S.No.</th>
                                                                <th class="heading">Organization Type Name</th>
                                                                <th class="heading">Organization Sub Type Name</th>
                                                            </tr>
                                                            <c:forEach var="organisationSubType" items="${requestScope['orgSubTypeList']}"  varStatus="loopCounter">
                                                                <tr  class="${loopCounter.index % 2 == 0 ? 'even': 'odd'}" >
                                                                    <td id="t1c${IDGenerator.uniqueID}" onclick="fillColumns(id)" align="center">
                                                                        <input type="hidden" id="organisation_sub_type_id${loopCounter.count}" value="${organisationSubType.organisation_sub_type_id}">${lowerLimit - noOfRowsTraversed + loopCounter.count}
                                                                    </td>
                                                                    <td id="t1c${IDGenerator.uniqueID}" class="new_input" onclick="fillColumns(id)">${organisationSubType.org_type_name}</td>
                                                                    <td id="t1c${IDGenerator.uniqueID}" class="new_input" onclick="fillColumns(id)">${organisationSubType.organisation_sub_type_name}</td>
                                                                </tr>
                                                            </c:forEach>
                                                            <tr>
                                                                <td align='center' colspan="3">
                                                                    <c:choose>
                                                                        <c:when test="${showFirst eq 'false'}">
                                                                            <input class="btn btn-primary" type='submit' name='buttonAction' value='First' disabled>
                                                                        </c:when>
                                                                        <c:otherwise>
                                                                            <input class="btn btn-primary" type='submit' name='buttonAction' value='First'>
                                                                        </c:otherwise>
                                                                    </c:choose>
                                                                    <c:choose>
                                                                        <c:when test="${showPrevious == 'false'}">
                                                                            <input class="btn btn-success" type='submit' name='buttonAction' value='Previous' disabled>
                                                                        </c:when>
                                                                        <c:otherwise>
                                                                            <input class="btn btn-success" type='submit' name='buttonAction' value='Previous'>
                                                                        </c:otherwise>
                                                                    </c:choose>
                                                                    <c:choose>
                                                                        <c:when test="${showNext eq 'false'}">
                                                                            <input class="btn btn-warning" type='submit' name='buttonAction' value='Next' disabled>
                                                                        </c:when>
                                                                        <c:otherwise>
                                                                            <input class="btn btn-warning" type='submit' name='buttonAction' value='Next'>
                                                                        </c:otherwise>
                                                                    </c:choose>
                                                                    <c:choose>
                                                                        <c:when test="${showLast == 'false'}">
                                                                            <input class="btn btn-info" type='submit' name='buttonAction' value='Last' disabled>
                                                                        </c:when>
                                                                        <c:otherwise>
                                                                            <input class="btn btn-info" type='submit' name='buttonAction' value='Last'>
                                                                        </c:otherwise>
                                                                    </c:choose>
                                                                </td>
                                                            </tr>
                                                            <%-- These hidden fields "lowerLimit", and "noOfRowsTraversed" belong to form1 of table1. --%>
                                                            <input type="hidden" name="lowerLimit" value="${lowerLimit}">
                                                            <input type="hidden" id="noOfRowsTraversed" name="noOfRowsTraversed" value="${noOfRowsTraversed}">
                                                            <input  type="hidden" id="searchOrgType" name="searchOrgType" value="${searchOrgType}"  >
                                                            <input  type="hidden"  name="searchOrgSubType" value="${searchOrgSubType}" >
                                                        </table>
                                                   
                                                        </form>
                                         </DIV>
                                                        
                                    <DIV class="container">
                                        <form name="form2" method="POST" class="form-group row boxeffext" action="organisationSubTypeCont.do" onsubmit="return verify()" >
                                             <table id="table2" border="0" align="center" class="table table-bordered table-striped" width="650">

                                                <tr id="message">
                                                    <c:if test="${not empty message}">
                                                        <td colspan="2" bgcolor="${msgBgColor}"><b>Result: ${message}</b></td>
                                                    </c:if>
                                                </tr>

                                                <tr>
                                                    <th class="heading1" >Organization Type Name</th>
                                                    <td>
                                                        <input class="form-control" type="hidden" id="organisation_sub_type_id" name="organisation_sub_type_id" value="" >
                                                        <input class="form-control" type="text" id="org_type_name" name="org_type_name" value="" size="45" disabled>
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <th class="heading1">Organization Sub Type Name</th><td><input class="form-control" type="text" id="organisation_sub_type_name" name="organisation_sub_type_name" value="" size="45" disabled></td>
                                                </tr>
                                                <tr>
                                                    <td align='center' colspan="2">
                                                        <input type="button" class="btn btn-primary" name="edit" id="edit" value="Edit" onclick="makeEditable(id)" disabled>
                                                    <input type="submit" class="btn btn-success" name="task" id="save" value="Save" onclick="setStatus(id)" disabled>
                                                    <input type="submit" class="btn btn-info" name="task" id="save_As" value="Save AS New" onclick="setStatus(id)" disabled>
                                                    <input type="reset" class=" btn btn-warning" name="new" id="new" value="New" onclick="makeEditable(id)" >
                                                    <input type="submit" class="btn btn-success" name="task" id="delete" value="Delete" onclick="setStatus(id)" disabled>
                                                    </td>
                                                </tr>
                                                <input  type="hidden" id="searchOrgType" name="searchOrgType" value="${searchOrgType}"  >
                                                <input  type="hidden"  name="searchOrgSubType" value="${searchOrgSubType}" >
                                                <%-- These hidden fields "lowerLimit", "noOfRowsTraversed", and "clickedButton" belong to form2 of table2. --%>
                                                <input type="hidden" name="lowerLimit" value="${lowerLimit}">
                                                <input type="hidden" name="noOfRowsTraversed" value="${noOfRowsTraversed}">
                                                <input type="hidden" id="clickedButton" value="">

                                            </table>
                                        </form>
                                   
          
      
        </div>
                                                </div>
                                                 <%@include file="/layout/footer.jsp" %>
        </body>
</html>

