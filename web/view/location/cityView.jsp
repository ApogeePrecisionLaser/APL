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
       <meta charset="utf-8">
        <title>Data Entry: City </title>
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
          
           $(function(){  
                 
              $("#tehsil").autocomplete({
           
            source: function (request, response) {
                        
                $.ajax({
                    url: "cityTypeCont",
                    dataType: "json",
                    data: {action1: "getTehsil"},
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
                $('#tehsil').val(ui.item.label); // display the selected text
                return false;
            }
        }); 
        
        
                   $("#searchCity").autocomplete({
           
            source: function (request, response) {
                        
                $.ajax({
                    url: "cityTypeCont",
                    dataType: "json",
                    data: {action1: "getCity"},
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
                $('#searchCity').val(ui.item.label); // display the selected text
                return false;
            }
        }); 
        
           });
           
           
           
            function setButton(id)
            {
                if (id == "delete")
                    document.getElementById('buttonClick').value = "delete";
                if (id == "saveAsNew")
                    document.getElementById('buttonClick').value = "saveAsNew";
                if (id == "save")
                    document.getElementById('buttonClick').value = "save";
            }
            
            
            function makeEditable(id)
            {
                debugger;

                if (id == "new")
                {
                    document.getElementById('edit').disabled = true;
                    document.getElementById('delete').disabled = true;
                    document.getElementById('saveAsNew').disabled = true;
                    document.getElementById('cityName').disabled = false;
                    document.getElementById('cityDescription').disabled = false;
                    document.getElementById('pin_code').disabled = false;
                    document.getElementById('std_code').disabled = false;
                    document.getElementById('tehsil').disabled = false;
                    document.getElementById('save').disabled = false;

                    document.getElementById('message').innerHTML = "";

                }
                if (id == "edit")
                {
                    document.getElementById('pin_code').disabled = false;
                    document.getElementById('std_code').disabled = false;
                    document.getElementById('cityName').disabled = false;
                    document.getElementById('tehsil').disabled = false;
                    document.getElementById('cityDescription').disabled = false;
                    document.getElementById('save').disabled = false;
                    document.getElementById('saveAsNew').disabled = false;
                    document.getElementById('delete').disabled = false;
                }

            }
            function varification()
            {
                var click = document.getElementById('buttonClick').value;
                if (click == "delete")
                {
                    var con = confirm("Do you want to delete this record")
                    return con;
                }
                if (click == "saveAsNew")
                {
                    var con = confirm("Do you want to save as this this record")
                    return con;
                }
                if ($.trim(document.getElementById('divisionName').value) == "")
                {
                    alert("Please enter division name")
                    document.getElementById('divisionName').value = "";
                    document.getElementById('divisionName').focus()
                    return false;
                }
                if ($.trim(document.getElementById('districtName').value) == "")
                {
                    alert("Please enter district name")
                    document.getElementById('districtName').value = "";
                    document.getElementById('districtName').focus()
                    return false;
                }
                if ($.trim(document.getElementById('cityName').value) == "")
                {
                    alert("Please enter city name")
                    document.getElementById('cityName').value = "";
                    document.getElementById('cityName').focus()
                    return false;
                }
//                if($.trim(document.getElementById('cityDescription').value)=="")
//                {
//                    alert("Please enter city description")
//                    document.getElementById('cityDescription').value="";
//                    document.getElementById('cityDescription').focus()
//                    return false;
//                }
                if (document.getElementById('cityId').value == "")
                {
                    addRow();
                    return false;
                }
                if (click == "save")
                {
                    var con = confirm("Do you want to update this record")
                    return con;
                }
                return true;
            }
            function addRow()
            {
                var table = document.getElementById('insetTable');
                var rowCount = table.rows.length;
                var row = table.insertRow(rowCount);

                var cell1 = row.insertCell(0);
                cell1.innerHTML = rowCount;
                var element1 = document.createElement("input");
                element1.name = "city_id";
                element1.id = "city_id" + rowCount;
                element1.type = "hidden";
                element1.value = 1;
                element1.size = 1;
                cell1.appendChild(element1);

                var element2 = document.createElement("input");
                element2.name = "cityCheckbox";
                element2.id = "cityCheckbox" + rowCount;
                element2.type = "checkbox";
                element2.checked = true;
                element2.setAttribute("onclick", 'singleCheck(' + rowCount + ')');
                cell1.appendChild(element2);
                ////////////////////////////////////
                var cell2 = row.insertCell(1);
                var element3 = document.createElement("input");
                element3.name = "divisionName";
                element3.id = "divisionName" + rowCount;
                element3.value = document.getElementById('divisionName').value;
                element3.size = 30;
                element3.className = "new_input";
                cell2.appendChild(element3);

                var cell3 = row.insertCell(2);
                var element4 = document.createElement("input");
                element4.name = "districtName";
                element4.id = "districtName" + rowCount;
                element4.value = document.getElementById('districtName').value;
                element4.size = 30;
                element4.className = "new_input";
                cell3.appendChild(element4);

                ///////////////////////////////////////////
                var cell4 = row.insertCell(3);
                var element5 = document.createElement("input");
                element5.name = "cityName";
                element5.id = "cityName" + rowCount;
                element5.value = document.getElementById('cityName').value;
                element5.size = 30;
                element5.className = "new_input";
                cell4.appendChild(element5);

                var cell5 = row.insertCell(4);
                var element6 = document.createElement("input");
                element6.name = "cityDescription";
                element6.id = "cityDescription" + rowCount;
                element6.value = document.getElementById('cityDescription').value;
                element6.size = 30;
                element6.className = "new_input";
                cell5.appendChild(element6);
                var height = (rowCount * 40) + 60;
                document.getElementById('autoCreatedTableDiv').style.visibility = "visible";
                document.getElementById("autoCreatedTableDiv").style.height = height + 'px';
            }
            function singleCheck(id)
            {
                if (document.getElementById('cityCheckbox' + id).checked == true)
                    document.getElementById('city_id' + id).value = 1;
                else
                    document.getElementById('city_id' + id).value = 0;
            }
            function deleteTable()
            {
                var table = document.getElementById('insetTable');
                var rowCount = table.rows.length;
                for (var i = 0; i < rowCount - 1; i++)
                    table.deleteRow(1);
                document.getElementById('autoCreatedTableDiv').style.visibility = "hidden";

            }
            function check()
            {
                var value = document.getElementById('uncheckAll').value;
                var length = document.getElementsByName('cityCheckbox').length;
                if (value == "UncheckAll")
                {
                    for (var checkbox = 0; checkbox < length; checkbox++)
                    {
                        document.getElementsByName('cityCheckbox')[checkbox].checked = false;
                        document.getElementsByName('city_id')[checkbox].value = 0;
                    }
                    document.getElementById('uncheckAll').value = "CheckAll";
                } else
                {
                    for (var checkbox = 0; checkbox < length; checkbox++)
                    {
                        document.getElementsByName('cityCheckbox')[checkbox].checked = true;
                        document.getElementsByName('city_id')[checkbox].value = 1;
                    }
                    document.getElementById('uncheckAll').value = "UncheckAll";
                }
            }
            function findfill(id)
            {
               debugger;
               // setDefault();
              //  document.getElementById(id).bgColor = "#d0dafd";
                document.getElementById('edit').disabled = false;
                document.getElementById('save').disabled = true;
                document.getElementById('cityId').value = document.getElementById(id + '1').innerHTML;
                document.getElementById('cityName').value = document.getElementById(id + '3').innerHTML;
                document.getElementById('pin_code').value = document.getElementById(id + '4').innerHTML;
                document.getElementById('std_code').value = document.getElementById(id + '5').innerHTML;
                document.getElementById('cityDescription').value = document.getElementById(id + '6').innerHTML;
                document.getElementById('tehsil').value = document.getElementById(id + '7').innerHTML;
                document.getElementById('message').innerHTML = "";
            }
            function setDefault()
            {
                for (var i = 1; i <= document.getElementById('noOfRowsTraversed').value; i++)
                    document.getElementById(i).bgColor = "";
            }
            function displayMapList(id)
            {
                var searchCity = document.getElementById('searchCity').value;
                var action;
                if (id == 'viewPdf')
                    action = "task=generateMapReport&searchCity=" + searchCity;
                else
                    action = "task=generateMapXlsReport&searchCity=" + searchCity;
                var url = "cityTypeCont?" + action;
                popup = popupWindow(url, "City_View_Report");
            }
            function popupWindow(url, windowName)
            {
                var windowfeatures = "left=50, top=50, width=1000, height=500, resizable=no, scrollbars=yes, location=0, menubar=no, status=no, dependent=yes";
                return window.open(url, windowName, windowfeatures)
            }
            
        </script>
        <style>
            .container-fluid
            {
                 max-width: 100%;

            }
            
            .form {
    max-width: 100%;
         }

        </style>
    </head>
    <body>
        <!--DWLayoutDefaultTable-->

        <div class="container-fluid">
            <table align="center" cellpadding="0" cellspacing="0"  class="main">            <!--DWLayoutDefaultTable-->
                <%@include file="/layout/header.jsp" %>
                 <div class="container  topbox" align="center">
                                 <h1 style="color: red;">City</h1>
                             </div>
              
        <br>
                <tr>
                
                </tr>
                <tr>
                    <%--   <td width="50" height="600" valign="top"><%@include file="/view/layout/Leftmenu.jsp" %></td></tr> --%>
                    <td>


                        <!--                            <tr><td>
                                                            <table>
                                                                <tr>
                                                                    <td align="center" class="header_table" width="800"></td>
                                                                    <td>
                   
                    </td>
                </tr>
            </table>
        </td></tr>-->
                        <div class="container">
                    <form action="cityTypeCont" method="post" class="form-group container-fluid">
                        <table class="table table-bordered table-hover  table-striped">
                            <tr>       <th>Enter city name:</th>
                                <td>   <input type="text" class="form-control"  name="searchCity" size="20" id="searchCity" value="${searchCity}"/></td>
                                <td>  <input class="btn btn-primary" type="submit" name="searchCityModel" value="Search"/></td>
                                <td>  <input class="btn btn-success" type="submit" name="task" value="Search All Records"/></td>
                                <td>  <input class="btn btn-danger" type="button" id="viewPdf" name="viewPdf" value="PDF" onclick="displayMapList(id);"/></td>
                                <td>  <input class="btn btn-info" type="button" id="viewXls" name="viewXls" value="Excel" onclick="displayMapList(id);"/></td>
                            </tr>      
                        </table>
                    </form>
                </div>

                <div class="container-fluid" >
                    <form action="cityTypeCont" method="post" class="form-group container-fluid">
                        <input type="hidden" name="searchCity" id="searchCity" value="${searchCity}"/>
                        <table class="table table-bordered table-hover  table-striped ">
                            <tr>
                                <th class="heading">S.No.</th>
                                <th class="heading">City Name</th>
                                <th class="heading">Pin Code</th>
                                <th class="heading">Std Code</th>
                                <th class="heading">City Description</th>
                                <th class="heading">Tehsil Name</th>

                            </tr>
                            <c:forEach var="cityBean" items="${requestScope['cityList']}" varStatus="loopcounter">
                                <tr id="${loopcounter.count}"onclick="findfill(id)">
                                    <td id="${loopcounter.count}1" style="display: none">${cityBean.cityId}</td>
                                    <td id="${loopcounter.count}2" align="center">${lowerLimit - noOfRowsTraversed + loopcounter.count}</td>
                                    <td class="new_input city_name" id="${loopcounter.count}3">${cityBean.cityName}</td>
                                    <td  id="${loopcounter.count}4">${cityBean.pin_code}</td>
                                    <td  id="${loopcounter.count}5">${cityBean.std_code}</td>
                                    <td class="new_input" id="${loopcounter.count}6">${cityBean.cityDescription}</td>
                                    <td class="new_input" id="${loopcounter.count}7">${cityBean.tehsilName}</td>
                                </tr>
                            </c:forEach>
                            <tr>
                                <td align='center' colspan="6">
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
                            <input type="hidden"  name="lowerLimit" value="${lowerLimit}">
                            <input type="hidden"  name="noOfRowsTraversed" value="${noOfRowsTraversed}">
                            <input  type="hidden" name="searchtypeofoccupation" value="${searchtypeofoccupation}">
                        </table>
                    </form>

                </div>

                <!--                                            <div class="row">
                                        <form method="post" action="cityTypeCont">
                                            <table class="table table-bordered table-hover table-responsive table-striped">
                                                <tr>
                                                    <th width="100" class="heading1">S. No</th>
                                                    <th class="heading1">Division Name</th>
                                                    <th class="heading1">District Name</th>
                                                    <th class="heading1">City Name</th>
                                                    <th class="heading1">City Description</th>
                                                    <th class="heading1">Tehsil Name</th>
                                                </tr>
                                            </table>
                                            <table class="reference" border="1" align="center">
                                                <tr>
                                                    <td><input  class="button"  id="uncheckAll" name="uncheckAll" type="button" value="UncheckAll" onclick="check()"/>
                                                        <input  class="button"  type="submit"  id="saveAllRecords" name="task" value="Save all records"/>
                                                        <input  class="button"  type="submit" name="cancel" value="Cancel" onclick="deleteTable()"/>
                                                    </td>
                                                </tr>
                                            </table>
                                        </form>
                                    
                                                            </div>-->


                <div class="col-12 col-sm pr-sm-0">
                    <form action="cityTypeCont" method="post"  name="form2" onsubmit="return varification()" class="form-group container-fluid">
                        <table class="table table-bordered table-hover  table-striped">
                            <c:if test="${not empty message}">
                                <tr id="message">
                                    <td colspan="2" bgcolor="${messageBGColor}"><b>Result: ${message}</b></td>
                                </tr>
                            </c:if>
                            <tr>
                                <td ><input style="display: none;" type="text" class="form-control" id="cityId" name="cityId" value="" readonly/><td>
                            </tr>
                            <tr>
                                <th >City Name</th>
                                
                                <td><input class="form-control" type="text"  id="cityName" name="cityName" size="40" disabled/></td>
                            </tr>
                            <tr>
                                <th>Pin Code</th>
                                <td><input class="form-control" type="text"  id="pin_code" name="pin_code" size="40" disabled/></td>
                            </tr>
                            <tr>
                                <th class="">Std Code</th>
                                <td><input class="form-control" type="text"  id="std_code" name="std_code" size="40" disabled/></td>
                            </tr>
                            <tr>
                                <th class="heading1">City Description</th>
                                <td><input class="form-control" type="text"  name="cityDescription" id="cityDescription" value="" size="40" disabled></td>
                            </tr>
                            <tr>
                                <th class="heading1">Tehsil Name</th>
                                <td><input class="form-control" type="text"  name="tehsil" id="tehsil" value="" size="40" disabled></td>
                            </tr>
                            <input type="hidden" name="buttonClick" id="buttonClick" value=""/>
                            <tr>
                                <td align='center' colspan="4">
                                    <input type="button" class="btn btn-primary" name="edit" id="edit" value="Edit" onclick="makeEditable(id)" disabled>
                                    <input type="submit" class="btn btn-success" name="task" id="save" value="Save" onclick="setStatus(id)" disabled>
                                    <input type="submit" class="btn btn-info" name="task" id="saveAsNew" value="Save AS New" onclick="setStatus(id)" disabled>
                                    <input type="reset" class=" btn btn-warning" name="new" id="new" value="New" onclick="makeEditable(id)" >
                                    <input type="submit" class="btn btn-success" name="task" id="delete" value="Delete" onclick="setStatus(id)" disabled>
                                </td>
                            </tr>

                            <%-- These hidden fields "lowerLimit", "noOfRowsTraversed", and "clickedButton" belong to form2 of table2. --%>
                            <input type="hidden" name="lowerLimit" value="${lowerLimit}">
                            <input type="hidden" name="noOfRowsTraversed" value="${noOfRowsTraversed}">
                            <input type="hidden" id="clickedButton" value="">

                        </table>
                    </form>
                </div>

         
            <%@include file="/layout/footer.jsp" %>
             </table>
        
        
    </body>
</html>