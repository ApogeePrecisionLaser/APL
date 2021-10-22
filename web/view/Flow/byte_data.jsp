<%-- 
    Document   : selection_command
    Created on : 19 Sep, 2019, 4:17:59 PM
    Author     : apogee
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">


<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" >
        <title>JSP Page</title>
        <link rel="stylesheet" type="text/css" href="style/style.css" />
        <link rel="stylesheet" type="text/css" href="style/Table_content.css" />
        <script type="text/javascript" src="JS/jquery-1.4.2.min.js"></script>
        <script type="text/javascript" src="JS/jquery.autocomplete.js"></script>
        <script type="text/javascript" src="JS/jquery-ui.min.js"></script>

    </head>
    <c:choose>        
        <c:when test="${fn:length(databyteListById) > 0}">            
            <body>
        </c:when>        
        <c:otherwise>
            <body onload="addRow('dataTable',${bitwise}, '${command}')">
        </c:otherwise>
    </c:choose>

        <table cellspacing="0" border="0" id="table0"  align="center" width="60%">
            <tr><td><%@include file="/layout/header.jsp" %></td></tr>
            <tr>
                <td><%@include file="/layout/menu.jsp" %> </td>
            </tr>
            <tr style="font-size:larger ;font-weight: 700;" align="center">
                <td>
                    "${command}" Byte Details
                </td>
            </tr>

            <tr id="message">
                <c:if test="${not empty message}">
                    <td colspan="8" bgcolor="${msgBgColor}"><b>Result: ${message}</b></td>
                </c:if>
            </tr>
            <tr>
                <td>
                    <form name="form1" id ="form1" action="ByteDataController" method="post" >
                        <table id="dataTable" style="border-collapse: collapse;" border="1" width="100%" align="center">
                            <tbody>
                                <tr>
                                    <th class="heading">S.No.</th>
                                    <th class="heading">Command</th>
                                    <th class="heading">Parameter Name</th>     
                                    <c:choose> 
                                        <c:when test="${fn:length(databyteListById) > 0}">            
                                            <th class="heading" colspan="2" style="min-width:200px;">Sub Byte Division</th>
                                        </c:when>        
                                        <c:otherwise>
                                            <th class="heading" >Sub Byte Division</th>
                                        </c:otherwise>
                                    </c:choose>
                                    

                                    <th class="heading">Remark</th>
<!--                                    <th class="heading">Action</th>-->
                                </tr>
                                <c:forEach var="list" items="${requestScope['databyteListById']}" varStatus="loopCounter">
                                    <tr>
                                        <td><input type="text" name="s_no${loopCounter.count}" id="s_no${loopCounter.count}" size="5" value="${loopCounter.count}" readonly>
                                            <input type="hidden" name="byte_data_id${loopCounter.count}" maxlength="8" size="5" id="byte_data_id${loopCounter.count}" value="${list.byte_data_id}">
                                            <input type="hidden" name="count" maxlength="8" size="5" id="count" value="${loopCounter.count}"></td>
                                        <td><input type="text" name="command_name${loopCounter.count}" maxlength="50" size="80" id="command_name${loopCounter.count}" value="${list.command_name}" readonly></td>
                                        <td><input type="text" name="parameter_name${loopCounter.count}" maxlength="8" size="20" id="parameter_name${loopCounter.count}" value="${list.parameter_name}"></td>

                                        <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)" >
                                            <input type="text" name="sub_byte_division${loopCounter.count}" maxlength="8" size="5" id="sub_byte_division${loopCounter.count}" value="${list.sub_byte_division}"></td> 
                                        <td><a href="#" onclick="bits('${list.parameter_name}',${list.sub_byte_division},${list.byte_data_id});">(sub byte division)</a>
                                        </td>
                                        
                                        <td><input type="text" name="remark${loopCounter.count}" maxlength="8" size="20" id="remark{loopCounter.count}" value="${list.remark}" ></td>

<!--                                        <td><input type="submit" class ="button" name="task"  value="update"></td>-->

                                    </tr>

                                </c:forEach>


                            </tbody>
                        </table>
                        <input  type="hidden" name="bitwise2" id="bitwise2" value="${bitwise}" >
                        <input  type="hidden" name="command_name2" id="command_name2" value="${command}" >
                        <input  type="hidden" name="command_id2" id="command_id" value="${command_id}" >
                        <div style="padding-top: 10px;" align="center">
                            <input class="button" type="submit" id="save" name="task" value="Save">
                        </div>

                    </form>
                </td>
            </tr>
        </table>




        <script type="text/javascript" language="javascript">

            var sub_byte_division_update_value;

            function autocompleteMethod(id, count) {
                debugger;
                if (id === "command_name") {
                    $("#command_name" + count).autocomplete("ByteDataController", {
                        extraParams: {
                            action1: function () {
                                return "getCommand";
                            }
                        }
                    });
                    debugger;
                } else if (id === "parameter_name") {
                    $("#parameter_name" + count).autocomplete("ByteDataController", {
                        extraParams: {
                            action1: function () {
                                return "getParameter_name";
                            }
                        }
                    });

                }

            }
                jQuery(function(id, count){
        $("#parameter_name"+ count).autocomplete("ByteDataController", {
            extraParams: {
                action1: function() { return "getParameter_name"}
            }
        });
//        $("#searchRule").autocomplete("ruleCont.do", {
//            extraParams: {
//                action1: function() { return "getRule"}
//            }
//        });       

    });

            function addRow(tableID, bitwise, command_name) {
                debugger;

                $("#message").html("");
                var table = document.getElementById(tableID);

                for (var i = 1; i <= bitwise; i++) {
                    var row = table.insertRow(i);


                    var cell1 = row.insertCell(0);
                    var element1 = document.createElement("input");
                    element1.type = "text";
                    element1.id = "s_no" + i;
                    element1.name = "s_no" + i;
                    element1.value = i;
                    element1.size = 3;
                    cell1.appendChild(element1);

                    var cell2 = row.insertCell(1);
                    var element2 = document.createElement("input");
                    element2.type = "text";
                    element2.name = "command_name" + i;
                    element2.id = "command_name" + i;
                    element2.size = 80;
                    element2.maxLength = 2;
                    element2.value = command_name;
                    //element2.setAttribute("onkeyup", 'autocompleteMethod("command_name",' + i + ')');
                    cell2.appendChild(element2);

                    var cell3 = row.insertCell(2);
                    var element2 = document.createElement("input");
                    element2.type = "text";
                    element2.name = "parameter_name" + i;
                    element2.id = "parameter_name" + i;
                    element2.size = 20;
                    element2.maxLength = 8;
                    element2.value = "";
                    element2.setAttribute("onkeyup", 'autocompleteMethod("parameter_name",' + i + ')');
                    cell3.appendChild(element2);

                    var cell4 = row.insertCell(3);
                    var element2 = document.createElement("input");
                    element2.type = "text";
                    element2.name = "sub_byte_division" + i;
                    element2.id = "sub_byte_division" + i;
                    element2.size = 5;
                    element2.maxLength = 50;
                    element2.value = "";
                    cell4.appendChild(element2);

                    var cell5 = row.insertCell(4);
                    var element2 = document.createElement("input");
                    element2.type = "text";
                    element2.name = "remark" + i;
                    element2.id = "remark" + i;
                    element2.size = 5;
                    element2.maxLength = 8;
                    element2.value = "";
                    cell5.appendChild(element2);

                }


            }
            function inputPopup(url, parameter_name, sub_byte_division, byte_data_id) {
                debugger;
                var popup_height = 580;
                var popup_width = 900;
                var popup_top_pos = (screen.availHeight / 2) - (popup_height / 2);
                var popup_left_pos = (screen.availWidth / 2) - (popup_width / 2);
                url = url + "?parameter_name=" + parameter_name + "&sub_byte_division=" + sub_byte_division + "&byte_data_id=" + byte_data_id;
                alert(url);
                var window_features = "left=" + popup_left_pos + ", top=" + popup_top_pos + ", width=" + popup_width + ", height=" + popup_height + ", resizable=no, scrollbars=yes, status=no, dialog=yes, dependent=yes";
                popupWindow = window.open(url, 'Selection Window', window_features);
            }
            function deleteRow(tableID) {
                try {
                    // alert(tableID);
                    var table = document.getElementById(tableID);
                    var rowCount = table.rows.length;
                    var delete_palan_row = 0;
                    for (var i = 0; i < rowCount; i++) {
                        var row = table.rows[i];
                        //  alert(row);
                        var chkbox = row.cells[0].childNodes[0];
                        //     alert(chkbox);
                        if (null != chkbox && true == chkbox.checked || k < rowCount) {
                            // alert(i);
                            var k = i;
                            for (k = i; k < rowCount; k++) {
                                table.deleteRow(i);
                                delete_palan_row++;
                                //  alert(delete_palan_row);
                                // --rowCount;
                                //setSequential(--rowCount, i);
                                // i--;
                                document.getElementById("no_of_plans").value = document.getElementById("no_of_plans").value - 1;
                            }
                        }
                    }
                    // alert("fdfdfd");
                    //document.getElementById("no_of_plans").value =(parseInt(rowCount)-parseInt(delete_palan_row));
                } catch (e) {
                    // alert(e);
                }
            }

            function verify() {
                //        var data = {
                //            command_name: document.getElementById(command_name),
                //            parameter:document.getElementById(parameter),
                //            parameter_value:document.getElementById(parameter_value),
                //            parametere_type:document.getElementById(parametere_type),
                //            remark:document.getElementById(remark)
                //        };
                debugger;
                var form = document.getElementById("form1");
                var dat = form.data;


            }





            function bits(parameter_name, sub_byte_division, byte_data_id) {
                debugger;
                document.getElementById("parameter_name").value = parameter_name;
                document.getElementById("sub_byte_division").value = sub_byte_division;
                document.getElementById("byte_data_id").value = byte_data_id;
                document.forms['redirectBits'].submit();

            }
        </script>
    <style>
        a:hover{
            background-color: yellow;
        }</style>
    <form name="redirectBits" method="post" action="SubByteDivisionController" target="_blank">
        <input type="hidden" id="parameter_name" name="parameter_name" value="">
        <input type="hidden" id="sub_byte_division" name="sub_byte_division" value="">
        <input type="hidden" id="byte_data_id" name="byte_data_id" value="">
    </form>
</body>
</html>