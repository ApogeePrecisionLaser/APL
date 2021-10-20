<%-- 
    Document   : device_oprtn_chartstc_map
    Created on : Jan 8, 2019, 4:44:35 PM
    Author     : jpss
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<link href="style/style1.css" type="text/css" rel="stylesheet" media="Screen"/>
<link href="style/style.css" type="text/css" rel="stylesheet" media="Screen"/>
<link href="style/Table_content.css" type="text/css" rel="stylesheet" media="Screen"/>
<script type="text/javascript" src="JS/jquery-1.4.2.min.js"></script>
<script type="text/javascript" src="JS/jquery.autocomplete.js"></script>


<script type="text/javascript" language="javascript">
    jQuery(function(){
        $("#searchCommandName").autocomplete("DeviceOprtnChartstcMapCont.do", {
            extraParams: {
                action1: function() { return "getCommandName"}
            }
        });
        $("#searchModelName").autocomplete("DeviceOprtnChartstcMapCont.do", {
            extraParams: {
                action1: function() { return "getModelName"}
            }
        });
        $("#searchDeviceType").autocomplete("DeviceOprtnChartstcMapCont.do", {
            extraParams: {
                action1: function() { return "getDeviceType"}
            }
        });
        $("#service_name").autocomplete("DeviceOprtnChartstcMapCont.do", {
                   extraParams: {
                       action1: function() { return "getServices" },
                       action2: function() { return  $("#manufacturer").val();},
                       action3: function() { return  $("#device_type").val();},
                       action4: function() { return  $("#model").val();}
                   }
               });
        $("#read_characteristics").autocomplete("DeviceOprtnChartstcMapCont.do", {
                   extraParams: {
                       action1: function() { return "getCharacteristics" },
                       action2: function() { return  $("#service_name").val();}                   
                   }
               });
        $("#write_characteristics").autocomplete("DeviceOprtnChartstcMapCont.do", {
                   extraParams: {
                       action1: function() { return "getCharacteristics" },
                       action2: function() { return  $("#service_name").val();}                   
                   }
               });
        $("#model").autocomplete("DeviceOprtnChartstcMapCont.do", {
            extraParams: {
                action1: function() { return "getModel"}
            }
        });
        $("#manufacturer").autocomplete("DeviceOprtnChartstcMapCont.do", {
            extraParams: {
                action1: function() { return "getManufacturer"}
            }
        });
        $("#device_type").autocomplete("DeviceOprtnChartstcMapCont.do", {
            extraParams: {
                action1: function() { return "getDeviceType"}
            }
        });
        $("#ble_operation_name").autocomplete("DeviceOprtnChartstcMapCont.do", {
            extraParams: {
                action1: function() { return "getBleOperationName"}
            }
        });

    });

    function setDefaultColor(noOfRowsTraversed, noOfColumns) {
        for(var i = 0; i < noOfRowsTraversed; i++) {
            for(var j = 1; j <= noOfColumns; j++) {
                document.getElementById("t1c" + (i * noOfColumns + j)).bgColor = "";     // set the default color.
            }
        }
    }
    function makeEditable(id) {

        document.getElementById("manufacturer").disabled = false;
        document.getElementById("device_type").disabled = false;
        document.getElementById("model").disabled = false;
        document.getElementById("service_name").disabled = false;
        document.getElementById("read_characteristics").disabled = false;
        document.getElementById("write_characteristics").disabled = false;
        document.getElementById("ble_operation_name").disabled = false;
        document.getElementById("order_no").disabled = false;
        document.getElementById("remark").disabled = false;

        document.getElementById("save").disabled = false;
//        document.getElementById("revise").disabled =false;
        document.getElementById("cancel").disabled =false;
        document.getElementById("save_As").disabled =false;
        //document.getElementById("save").disabled = true;
        if(id === 'new') {
        //    document.getElementById("created_date").disabled = true;
           // document.getElementById("active").value ='';
            document.getElementById("message").innerHTML = "";      // Remove message
            document.getElementById("manufacturer").focus();
            $("#message").html("");

            //document.getElementById("revise").disabled = true;
            document.getElementById("cancel").disabled = true;
            document.getElementById("save_As").disabled = true;
            document.getElementById("save").disabled = false;

            setDefaultColor(document.getElementById("noOfRowsTraversed").value, 3);
            document.getElementById("manufacturer").focus();

        }
        if(id === 'edit'){

            document.getElementById("save_As").disabled = false;
            document.getElementById("cancel").disabled = false;
        }
    }
    function setStatus(id) {
        if(id == 'save'){
            document.getElementById("clickedButton").value = "Save";
        }
        else if(id == 'save_As'){
            document.getElementById("clickedButton").value = "Save AS New";
        }
        else if(id == 'revise'){
            document.getElementById("clickedButton").value = "Revise";
        }
        else document.getElementById("clickedButton").value = "Delete";
    }
    function verify() {
        var result;
        if(document.getElementById("clickedButton").value == 'Save' || document.getElementById("clickedButton").value == 'Save AS New' || document.getElementById("clickedButton").value == 'Revise'||document.getElementById("clickedButton").value == 'Delete') {
            var division_name_m = document.getElementById("manufacturer").value;
            var a=document.getElementById("active").value;
            //    alert(a);
            if(myLeftTrim(division_name_m).length == 0) {

                // document.getElementById("message").innerHTML = "<td colspan='5' bgcolor='coral'><b>Organisation Type Name is required...</b></td>";
                $("#message").html("<td colspan='5' bgcolor='coral'><b>manufacturer is required...</b></td>");
                document.getElementById("manufacturer").focus();
                return false; // code to stop from submitting the form2.
            }

            if(document.getElementById("active").value =='Revised' || document.getElementById("active").value =='Cancelled')
            {
                $("#message").html("<td colspan='5' bgcolor='coral'><b>You can not perform any operation on revised and cancelled record...</b></td>");
                // document.getElementById("source_wattage"+i).focus();
                return false; // code to stop from submitting the form2.
            }
            if(result == false)    // if result has value false do nothing, so result will remain contain value false.
            {

            }
            else{ result = true;
            }

            if(document.getElementById("clickedButton").value == 'Save AS New'){
                result = confirm("Are you sure you want to save it as New record?")
                return result;
            }
        } else result = confirm("Are you sure you want to cancel this record?")
        return result;
    }
    function fillColumns(id) {
        debugger;
        var noOfRowsTraversed = document.getElementById("noOfRowsTraversed").value;
        var noOfColumns =11;
        var columnId = id;                              <%-- holds the id of the column being clicked, excluding the prefix t1c e.g. t1c3 (column 3 of table 1). --%>
        columnId = columnId.substring(3, id.length);    <%-- for e.g. suppose id is t1c3 we want characters after t1c i.e beginIndex = 3. --%>
        var lowerLimit, higherLimit;

        for(var i = 0; i < noOfRowsTraversed; i++) {
            lowerLimit = i * noOfColumns + 1;       // e.g. 11 = (1 * 10 + 1)
            higherLimit = (i + 1) * noOfColumns;    // e.g. 20 = ((1 + 1) * 10)

            if((columnId >= lowerLimit) && (columnId <= higherLimit)) break;
        }

        setDefaultColor(noOfRowsTraversed, noOfColumns);        // set default color of rows (i.e. of multiple coloumns).
        var t1id = "t1c";       // particular column id of table 1 e.g. t1c3.

        document.getElementById("device_characteristic_ble_map_id").value= document.getElementById(t1id + (lowerLimit + 0)).innerHTML;
        document.getElementById("manufacturer").value = document.getElementById(t1id +(lowerLimit+2)).innerHTML;
        document.getElementById("device_type").value = document.getElementById(t1id +(lowerLimit+3)).innerHTML;
        document.getElementById("model").value = document.getElementById(t1id +(lowerLimit+4)).innerHTML;
        document.getElementById("service_name").value = document.getElementById(t1id +(lowerLimit+5)).innerHTML;
        document.getElementById("read_characteristics").value = document.getElementById(t1id +(lowerLimit+6)).innerHTML;
        document.getElementById("write_characteristics").value = document.getElementById(t1id +(lowerLimit+7)).innerHTML;
        document.getElementById("ble_operation_name").value = document.getElementById(t1id +(lowerLimit+8)).innerHTML;
        document.getElementById("order_no").value = document.getElementById(t1id +(lowerLimit+9)).innerHTML;
        document.getElementById("remark").value = document.getElementById(t1id +(lowerLimit+10)).innerHTML;

        //       var b=  document.getElementById(t1id +(lowerLimit+8)).innerHTML;
        // alert(b);
        for(var i = 0; i < noOfColumns; i++) {
            document.getElementById(t1id + (lowerLimit + i)).bgColor = "#d0dafd";        // set the background color of clicked row to yellow.
        }
     //   makeEditable('');

        document.getElementById("edit").disabled = false;
        if(!document.getElementById("save").disabled)   // if save button is already enabled, then make edit, and cancel button enabled too.
        {
            document.getElementById("save_As").disabled = true;
            document.getElementById("cancel").disabled = false;
        }
        //  document.getElementById("message").innerHTML = "";      // Remove message
        $("#message").html("");
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
    var popupwin = null;
    function displayMapList(){
        var queryString = "task=generateMapReport" ;
        var url = "divisionCont?"+queryString;
        popupwin = openPopUp(url, "Mounting Type Map Details", 500, 1000);

    }

    function openPopUp(url, window_name, popup_height, popup_width) {
        var popup_top_pos = (screen.availHeight / 2) - (popup_height / 2);
        var popup_left_pos = (screen.availWidth / 2) - (popup_width / 2);
        var window_features = "left=" + popup_left_pos + ", top=" + popup_top_pos + ", width=" + popup_width + ", height=" + popup_height + ", resizable=yes, scrollbars=yes, location=0, menubar=no, status=no, dependent=yes";

        return window.open(url, window_name, window_features);
    }

</script>
<style>
a:hover{
  background-color: yellow;
}</style>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Rule Page</title>
        <meta charset="utf-8">

    </head>
    <body>
        <table align="center" cellpadding="0" cellspacing="0" class="main">
            <tr><td><%@include file="/layout/header.jsp" %></td></tr>
            <tr>
                <td><%@include file="/layout/menu.jsp" %> </td>
            </tr>
            <td>
                <DIV id="body" class="maindiv" align="center" >
                    <table width="100%" align="center">
                        <tr><td>
                                <table align="center">
                                    <tr>
                                        <td align="center" class="header_table" width="100%">Device Characterstic BLE Map</td>

                                    </tr>
                                </table>
                            </td></tr>
                        <tr>
                            <td> <div align="center">
                                    <form name="form0" method="POST" action="DeviceOprtnChartstcMapCont.do">
                                        <table align="center" class="heading1" width="600">
                                            <tr>
<!--                                                <td>Rule<input class="input" type="text" id="searchRule" name="searchRule" value="${searchRule}" size="20" ></td>-->
                                                <td>Manufacturer Name<input class="input" type="text" id="searchCommandName" name="searchCommandName" value="${searchCommandName}" size="20" ></td>
                                                <td>Device Type<input class="input" type="text" id="searchDeviceType" name="searchDeviceType" value="${searchDeviceType}" size="20" ></td>
                                                <td>Model<input class="input" type="text" id="searchModelName" name="searchModelName" value="${searchModelName}" size="20" ></td>                                               
                                                <td><input class="button" type="submit" name="task" id="searchIn" value="Search"></td>
                                                <td><input class="button" type="submit" name="task" id="showAllRecords" value="Show All Records"></td>
<!--                                                <td><input type="button" class="pdf_button" id="viewPdf" name="viewPdf" value="" onclick="displayMapList()"></td>-->
                                            </tr>
                                        </table>
                                    </form></div>
                            </td>
                        </tr>
                        <tr>
                            <td align="center">
                                <form name="form1" method="POST" action="DeviceOprtnChartstcMapCont.do">
                                    <DIV class="content_div">
                                        <table id="table1" width="600"  border="1"  align="center" class="content">
                                            <tr>
                                                <th class="heading">S.No.</th>
                                                <th class="heading">Manufacturer</th>
                                                <th class="heading">Device Type</th>
                                                <th class="heading">Model</th>
                                                <th class="heading">Service Name</th>
                                                <th class="heading">Characteristic Read</th>
                                                <th class="heading">Characteristic Write</th>
                                                <th class="heading">Ble Operation Name</th>
                                                <th class="heading">Order No</th>
                                                <th class="heading">Remark</th>
                                            </tr>
                                            <!---below is the code to show all values on jsp page fetched from trafficTypeList of TrafficController     --->
                                            <c:forEach var="divisionTypeBean" items="${requestScope['divisionTypeList']}"  varStatus="loopCounter">
                                                <tr  class="${loopCounter.index % 2 == 0 ? 'even': 'odd'}" >
                                                    <%--  <td id="t1c${IDGenerator.uniqueID}" onclick="fillColumns(id)" align="center">
                                                          <input type="hidden" id="status_type_id${loopCounter.count}" value="${statusTypeBean.status_type_id}">${lowerLimit - noOfRowsTraversed + loopCounter.count}
                                                          <td id="t1c${IDGenerator.uniqueID}" onclick="fillColumns(id)" align="center">${lowerLimit - noOfRowsTraversed + loopCounter.count}</td>
                                                      </td> --%>
                                                    <td id="t1c${IDGenerator.uniqueID}" style="display:none" onclick="fillColumns(id)">${divisionTypeBean.device_characteristic_ble_map_id}</td>
                                                    <td id="t1c${IDGenerator.uniqueID}" onclick="fillColumns(id)" align="center">${lowerLimit - noOfRowsTraversed + loopCounter.count}</td>
                                                    <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)" >${divisionTypeBean.manufacturer_name}</td>
                                                    <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)" >${divisionTypeBean.device_type}</td>
                                                    <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)" >${divisionTypeBean.model_name}</td>
                                                    <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)" >${divisionTypeBean.service_name}</td>
                                                    <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)" >${divisionTypeBean.read_characteristics_name}</td>
                                                    <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)" >${divisionTypeBean.write_characteristics_name}</td>
                                                    <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)" >${divisionTypeBean.ble_operation_name}</td>
                                                    <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)" >${divisionTypeBean.order_no}</td>
                                                    <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)" >${divisionTypeBean.remark}</td>

                                                </tr>
                                            </c:forEach>
                                            <tr>
                                                <td align='center' colspan="13">
                                                    <c:choose>
                                                        <c:when test="${showFirst eq 'false'}">
                                                            <input class="button" type='submit' name='buttonAction' value='First' disabled>
                                                        </c:when>
                                                        <c:otherwise>
                                                            <input class="button" type='submit' name='buttonAction' value='First'>
                                                        </c:otherwise>
                                                    </c:choose>
                                                    <c:choose>
                                                        <c:when test="${showPrevious == 'false'}">
                                                            <input class="button" type='submit' name='buttonAction' value='Previous' disabled>
                                                        </c:when>
                                                        <c:otherwise>
                                                            <input class="button" type='submit' name='buttonAction' value='Previous'>
                                                        </c:otherwise>
                                                    </c:choose>
                                                    <c:choose>
                                                        <c:when test="${showNext eq 'false'}">
                                                            <input class="button" type='submit' name='buttonAction' value='Next' disabled>
                                                        </c:when>
                                                        <c:otherwise>
                                                            <input class="button" type='submit' name='buttonAction' value='Next'>
                                                        </c:otherwise>
                                                    </c:choose>
                                                    <c:choose>
                                                        <c:when test="${showLast == 'false'}">
                                                            <input class="button" type='submit' name='buttonAction' value='Last' disabled>
                                                        </c:when>
                                                        <c:otherwise>
                                                            <input class="button" type='submit' name='buttonAction' value='Last'>
                                                        </c:otherwise>
                                                    </c:choose>
                                                </td>
                                            </tr>
                                            <!--- These hidden fields "lowerLimit", and "noOfRowsTraversed" belong to form1 of table1. -->
                                            <input type="hidden" name="lowerLimit" value="${lowerLimit}">
                                             <input type="hidden" name="manname" value="${manname}">
                                              <input type="hidden" name="devicetype" value="${devicetype}">
                                               <input type="hidden" name="modname" value="${modname}">
                                            <input type="hidden" id="noOfRowsTraversed" name="noOfRowsTraversed" value="${noOfRowsTraversed}">
                                            <input  type="hidden" id="searchCommandName" name="searchCommandName" value="${searchCommandName}" >
                                            <input type="hidden"  id="searchModelName" name="searchModelName" value="${searchModelName}" >
                                            <input type="hidden"  id="searchDeviceType" name="searchDeviceType" value="${searchDeviceType}" >
                                        </table></DIV>
                                </form>
                            </td>
                        </tr>

                        <tr>
                            <td align="center">
                                <div>
                                    <form name="form2" method="POST" action="DeviceOprtnChartstcMapCont.do" onsubmit="return verify()">
                                        <table id="table2"  class="content" border="0"  align="center" width="600">
                                            <tr id="message">
                                                <c:if test="${not empty message}">
                                                    <td colspan="2" bgcolor="${msgBgColor}"><b>Result: ${message}</b></td>
                                                </c:if>
                                            </tr>
                                            <tr>
                                                <th class="heading1">Manufacturer</th>
                                                <td><input class="input" type="text" id="manufacturer" name="manufacturer" value="" size="40" disabled></td>
                                            </tr>
                                            <tr>
                                                <th class="heading1">Device Type</th>
                                                <td><input class="input" type="text" id="device_type" name="device_type" value="" size="40" disabled>
                                                <input class="input" type="hidden" id="device_characteristic_ble_map_id" name="device_characteristic_ble_map_id" value="" ></td>
                                            </tr>
                                            <tr>
                                                <th class="heading1">Model</th>
                                                <td><input class="input" type="text" id="model" name="model" value="" size="40" disabled></td>
                                            </tr>
                                            <tr>
                                                <th class="heading1">Services</th>
                                                <td><input class="input" type="text" id="service_name" name="service_name" value="" size="40" disabled></td>
                                            </tr>
                                            <tr>
                                                <th class="heading1">Read Characteristics</th>
                                                <td><input class="input" type="text" id="read_characteristics" name="read_characteristics" value="" size="40" disabled></td>
                                            </tr>
                                            <tr>
                                                <th class="heading1"> Write Characteristics</th>
                                                <td><input class="input" type="text" id="write_characteristics" name="write_characteristics" value="" size="40" disabled></td>
                                            </tr>
                                            <tr>
                                                <th class="heading1">BLE Operation name</th>
                                                <td><input class="input" type="text" id="ble_operation_name" name="ble_operation_name" value="" size="40" disabled></td>
                                            </tr>
                                            <tr>
                                                <th class="heading1">Order No</th>
                                                <td><input class="input" type="text" id="order_no" name="order_no" value="" size="40" disabled></td>
                                            </tr>
                                            <tr>
                                                <th class="heading1">Remark</th>
                                                <td><input class="input" type="text" id="remark" name="remark" value="" size="40" disabled></td>
                                            </tr>

                                            <tr>
                                                <td align='center' colspan="2">
                                                    <input class="button" type="button" name="edit" id="edit" value="Edit" onclick="makeEditable(id)" disabled>
                                                    <%--       <input class="button" type="submit" name="task" id="revise" value="Revise" onclick="setStatus(id)" disabled>  --%>
                                                    <input class="button" type="submit" name="task" id="save" value="Save" onclick="setStatus(id)" disabled>
                                                    <input class="button" type="submit" name="task" id="save_As" value="Save AS New" onclick="setStatus(id)" disabled>
                                                    <input class="button" type="reset" name="new" id="new" value="New" onclick="makeEditable(id)">
                                                    <input class="button" type="submit" name="task" id="cancel" value="Cancel" onclick="setStatus(id)" disabled>

                                                </td>
                                            </tr>

                                            <%-- These hidden fields "lowerLimit", "noOfRowsTraversed", and "clickedButton" belong to form2 of table2. --%>
                                            <input type="hidden" name="active" id="active" value="">
                                            <input type="hidden" name="lowerLimit" value="${lowerLimit}">
                                            <input type="hidden" name="noOfRowsTraversed" value="${noOfRowsTraversed}">
                                            <input type="hidden" id="clickedButton" value="">
                                            <input type="hidden"  name="searchCommandName" value="${searchCommandName}" >
                                            <input type="hidden"  name="searchModelName" value="${searchModelName}" >
                                            <input type="hidden"  name="searchDeviceType" value="${searchDeviceType}" >
                                        </table>
                                    </form>
                                </div>
                            </td>
                        </tr>
                    </table>

                </DIV>
            </td>
            <tr><td><%@include file="/layout/footer.jsp" %></td> </tr>
        </table>
    </body>
</html>

