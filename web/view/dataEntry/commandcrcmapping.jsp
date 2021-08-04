<%-- 
    Document   : commandcrcmapping
    Created on : 12 Feb, 2020, 3:25:10 PM
    Author     : DELL
--%>

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

  <link type="text/css" href="style/menu.css" rel="stylesheet"/>
<script type="text/javascript" language="javascript">
 

jQuery(function () {
        $("#searchCommand").autocomplete("CommandCrcMapController", {
            extraParams: {
                action1: function () {
                    return "getCommand"
                }
            }
        });
        
         $("#Command").autocomplete("CommandCrcMapController", {
            extraParams: {
                action1: function () {
                    return "getCommand"
                }
            }
        });
        
         $("#searchCrctype").autocomplete("CommandCrcMapController", {
            extraParams: {
                action1: function () {
                    return "getCrcType"
                }
            }
        });
        
         $("#crctype").autocomplete("CommandCrcMapController", {
            extraParams: {
                action1: function () {
                    return "getCrcType"
                }
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
        debugger;
       
        document.getElementById("Command").disabled = false;
        document.getElementById("crctype").disabled = false;   
        document.getElementById("remark").disabled = false; 
        document.getElementById("save").disabled = false;
//        document.getElementById("revise").disabled =false;
        document.getElementById("cancel").disabled =false;
        document.getElementById("save_As").disabled =false;
        //document.getElementById("save").disabled = true;
        if(id === 'new') {
       
            document.getElementById("message").innerHTML = "";      // Remove message
            document.getElementById("Command").focus();
            $("#message").html("");
            document.getElementById("cancel").disabled = true;
            document.getElementById("save_As").disabled = true;
            document.getElementById("save").disabled = false;

            setDefaultColor(document.getElementById("noOfRowsTraversed").value, 3);
            document.getElementById("Command").focus();

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
        var noOfColumns = 5;
        var columnId = id;
        <%-- holds the id of the column being clicked, excluding the prefix t1c e.g. t1c3 (column 3 of table 1). --%>
        columnId = columnId.substring(3, id.length);
        <%-- for e.g. suppose id is t1c3 we want characters after t1c i.e beginIndex = 3. --%>
        var lowerLimit, higherLimit;

        for (var i = 0; i < noOfRowsTraversed; i++) {
            lowerLimit = i * noOfColumns + 1;       // e.g. 11 = (1 * 10 + 1)
            higherLimit = (i + 1) * noOfColumns;    // e.g. 20 = ((1 + 1) * 10)

            if ((columnId >= lowerLimit) && (columnId <= higherLimit))
                break;
        }

        setDefaultColor(noOfRowsTraversed, noOfColumns);        
        var t1id = "t1c";        
        document.getElementById("cmd_crcmap_id").value = document.getElementById(t1id + (lowerLimit + 0)).innerHTML;
        document.getElementById("Command").value = document.getElementById(t1id + (lowerLimit + 2)).innerHTML;
        document.getElementById("crctype").value = document.getElementById(t1id + (lowerLimit + 3)).innerHTML;
         document.getElementById("remark").value = document.getElementById(t1id + (lowerLimit + 4)).innerHTML;
//           document.getElementById("is_super_child").value = document.getElementById(t1id + (lowerLimit + 5)).innerHTML;

        //       var b=  document.getElementById(t1id +(lowerLimit+8)).innerHTML;
        // alert(b);
        for (var i = 0; i < noOfColumns; i++) {
            document.getElementById(t1id + (lowerLimit + i)).bgColor = "#d0dafd";        // set the background color of clicked row to yellow.
        }
        //   makeEditable('');

        document.getElementById("edit").disabled = false;
        if (!document.getElementById("save").disabled)   // if save button is already enabled, then make edit, and cancel button enabled too.
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
    
    function expandDetails(type) {
        if(type === "finished") {
            var row = document.getElementsByClassName("master");
            console.log(row);
            var i;
            for (i = 0; i < row.length; i++) {
                row[i].style.display = "";
            }
        } else if(type === "module"){
            var row = document.getElementsByClassName("masterModule");
            console.log(row);
            var i;
            for (i = 0; i < row.length; i++) {
                row[i].style.display = "";
            }
        }
        
    }
    
    function goToDevice() {
        window.location.href = "DeviceCont.do";
    }

</script>
<style>
a:hover{
  background-color: yellow;
}</style>
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
                                        <td align="center" class="header_table" width="100%">Response Crc Map</td>

                                    </tr>
                                </table>
                            </td></tr>
                        <tr>
                            <td> <div align="center">
                                    <form name="form0" method="POST" action="CommandCrcMapController">
                                        <table align="center" class="heading1" width="600">
                                            
                                            <tr>
                                              
                                                <td>Command<input class="input" type="text" id="searchCommand" name="searchCommand" value="${searchCommand}" size="20" ></td>
                                                <td>Crc Type<input class="input" type="text" id="searchCrctype" name="searchCrctype" value="${searchCrctype}" size="20" ></td>
<!--                                                                                          -->
                                                <td><input class="button" type="submit" name="task" id="searchIn" value="Search"></td>
                                                <td><input class="button" type="submit" name="task" id="showAllRecords" value="Show All Records"></td>
 
                                            </tr>
                                        </table>
                                    </form></div>
                            </td>
                        </tr>
                        <tr>
                            <td align="center">
                                <form name="form1" method="POST" action="CommandCrcMapController">
                                    <DIV class="content_div">
                                        <table id="table1" width="600"  border="1"  align="center" class="content">
                                            
                                            <tr>
                                                <th class="heading">S.No.</th>
                                                <th class="heading">Command Name</th>
                                                <th class="heading">CRC Type</th>
                                                <th class="heading">Remark</th>
                                            </tr>
                                            <!---below is the code to show all values on jsp page fetched from trafficTypeList of TrafficController     --->
                                            <c:forEach var="crcmap" items="${requestScope['cmdmapList']}"  varStatus="loopCounter">
                                                <tr  class="${loopCounter.index % 2 == 0 ? 'even': 'odd'}" >
                                                    
                                                   <td id="t1c${IDGenerator.uniqueID}" style="display:none" onclick="fillColumns(id)">${crcmap.command_crc_map_id}</td>
                                                    <td id="t1c${IDGenerator.uniqueID}" onclick="fillColumns(id)" align="center">${lowerLimit - noOfRowsTraversed + loopCounter.count}</td>
                                                    <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)" >${crcmap.short_hand}</td>
                                                    <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)" >${crcmap.crc_type}</td>
                                                    
                                                    <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)" >${crcmap.remark}</td> 

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
                                            <input type="hidden" id="noOfRowsTraversed" name="noOfRowsTraversed" value="${noOfRowsTraversed}">
                                            <input  type="hidden" id="searchCommand" name="searchCommand" value="${searchCommand}" >
                      
                                            <input type="hidden"  id="searchCrctype" name="searchCrctype" value="${searchCrctype}" >
                                        </table></DIV>
                                </form>
                            </td>
                        </tr>

                        <tr>
                            <td align="center">
                                <div>
                                    <form name="form2" method="POST" action="CommandCrcMapController" onsubmit="return verify()">
                                        <table id="table2"  class="content" border="0"  align="center" width="600">
                                            <tr id="message">
                                                <c:if test="${not empty message}">
                                                    <td colspan="2" bgcolor="${msgBgColor}"><b>Result: ${message}</b></td>
                                                </c:if>
                                            </tr>
                                             <tr>
                                                <th class="heading1">Command</th>
                                                <td><input class="input" type="text" id="Command" name="Command" value="" size="40" disabled></td>
                                                <td><input class="input" type="hidden" id="cmd_crcmap_id" name="cmd_crcmap_id" value="" ></td>
                                            </tr>
                                            <tr>
                                                <th class="heading1">CrcType</th>
                                                <td><input class="input" type="text" id="crctype" name="crctype" value="" size="40" disabled></td>
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
                                            <input type="hidden"  name="searchCommand" value="${searchCommand}" >
 
                                            <input type="hidden"  name="searchCrctype" value="${searchCrctype}" >
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

