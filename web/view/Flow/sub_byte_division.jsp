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
        <c:when test="${fn:length(sub_byte_divisionListById) > 0}">            
            <body>
            </c:when>        
            <c:otherwise>
            <body onload="addRow('dataTable',${sub_byte_division}, '${parameter_name}')">
            </c:otherwise>
        </c:choose>

        <table cellspacing="0" border="0" id="table0"  align="center" width="60%">
            <tr><td><%@include file="/layout/header.jsp" %></td></tr>
            <tr>
                <td><%@include file="/layout/menu.jsp" %> </td>
            </tr>
            <tr style="font-size:larger ;font-weight: 700;" align="center">
                <td>
                    "${parameter_name}" Byte Data Details
                </td>
            </tr>

            <tr id="message">
                <c:if test="${not empty message}">
                    <td colspan="8" bgcolor="${msgBgColor}"><b>Result: ${message}</b></td>
                </c:if>
            </tr>
            <tr>
                <td>
                    <form name="form1" id ="form1" action="SubByteDivisionController" method="post" >
                        <table id="dataTable" style="border-collapse: collapse;" border="1" width="100%" align="center">
                            <tbody>
                                <tr>
                                    <th class="heading">S.No.</th>
                                    <th class="heading">Byte Data Parameter</th> 
                                    <th class="heading">Parameter</th>       
                                    <c:choose>        
                                        <c:when test="${fn:length(sub_byte_divisionListById) > 0}">            
                                            <th class="heading" colspan="2" style="min-width:267px;">Sub-Division Selection Number</th>
                                        </c:when>        
                                        <c:otherwise>
                                            <th class="heading" >Sub-Division Selection Number</th>
                                        </c:otherwise>
                                    </c:choose>
                                    
                                    <th class="heading">Start Position</th>
                                    <th class="heading">No of Bit</th>
                                    <th class="heading">Remark</th>
<!--                                    <th class="heading">Action</th>-->
                                </tr>
                                <c:forEach var="list" items="${requestScope['sub_byte_divisionListById']}" varStatus="loopCounter">
                                    <tr>

                                        <td><input type="text" name="s_no${loopCounter.count}" id="s_no${loopCounter.count}" size="10" value="${loopCounter.count}" readonly>
                                            <input type="hidden" name="count" maxlength="8" size="5" id="count" value="${loopCounter.count}">
                                            <input type="hidden" name="sub_byte_division_id${loopCounter.count}" maxlength="8" size="20" id="sub_byte_division_id${loopCounter.count}" value="${list.sub_byte_division_id}"></td>
                                        <td><input type="text" name="byte_parameter_name${loopCounter.count}" maxlength="8" size="20" id="byte_parameter_name${loopCounter.count}" value="${list.parameter_name_byte}" ></td>
                                        <td><input type="text" name="parameter_name${loopCounter.count}" maxlength="20" size="20" id="parameter_name${loopCounter.count}" value="${list.parameter_name}" ></td>

                                        <td><input type="text" name="sub_division_no${loopCounter.count}" maxlength="50" size="5" id="sub_division_no${loopCounter.count}" value="${list.sub_division_no}" onkeyup="check(value)"></td>
                                        <td><a href="#" onclick="inputPopup('SubDivisionSelectionController', '${list.parameter_name}',${list.sub_division_no},${list.sub_byte_division_id});" id="input_button">View Sub Division Selection Value</a></td>
                                        <td><input type="text" name="start_pos${loopCounter.count}" maxlength="8" size="20" id="start_pos{loopCounter.count}" value="${list.start_pos}" ></td>

                                        <td><input type="text" name="no_of_bit${loopCounter.count}" maxlength="20" size="20" id="no_of_bit{loopCounter.count}" value="${list.no_of_bit}" ></td>

                                        <td><input type="text" name="remark${loopCounter.count}" maxlength="8" size="20" id="remark{loopCounter.count}" value="${list.remark}" ></td>

<!--                                        <td><input type="submit" class ="button" name="task"  value="update" ></td>-->

                                    </tr>

                                </c:forEach>



                            </tbody>
                        </table>
                        <input  type="hidden" name="sub_byte_division" value="${sub_byte_division}" >
                        <input  type="hidden" name="parameter_name" id="parameter_name1" value="${parameter_name}" >
                        <input  type="hidden" name="byte_data_id" id="byte_data_id" value="${byte_data_id}" >
                        <div style="padding-top: 10px;" align="center">
                            <input class="button" type="submit" id="save" name="task" value="Save">
                        </div>
                    </form>
                </td>
            </tr>
        </table>

        <script type="text/javascript" language="javascript">

            var sub_division_no_update_value;

            function autocompleteMethod(id, count) {
                debugger;
//              //  if (id === "parameter_name") {
//                    $("#parameter_name2" + count).autocomplete("SubByteDivision", {
//                        extraParams: {
//                            action1: function () {
//                                return "getParameter";
//                            }
//                        }
//                    });
//
//               // }
 $("#parameter_name2"+ count).autocomplete("SubByteDivisionController", {
            extraParams: {
                action1: function() { return "getParameter"}
            }
        });

            }
//       jQuery(function(id, count){
//        $("#parameter_name"+ count).autocomplete("SubByteDivision", {
//            extraParams: {
//                action1: function() { return "getParameter"}
//            }
//        });
////        $("#searchRule").autocomplete("ruleCont.do", {
////            extraParams: {
////                action1: function() { return "getRule"}
////            }
////        });       
//
//    });

            function update1(sub_byte_division_id)
            {

                // var selection_value_no = document.getElementById('selection_update_value').value;
                var sub_division_no = sub_division_no_update_value;
                //alert(selection_update_value);
                var bitwise = document.getElementById('bitwise').value;
                var byte_id = document.getElementById('bit_id').value;
                var queryString = "task=update&sub_byte_division_id=" + sub_byte_division_id + "&sub_division_no=" + sub_division_no + "&bitwise=" + bitwise + "&byte_id=" + byte_id;
                var url = "SubByteDivision?" + queryString;
                //alert(url);
                window.open(url);
            }

//            function check(sub_division_no){                
//              sub_division_no_update_value = sub_division_no;               
//            }



            function addRow(tableID, sub_byte_division, parameter_name_byte) {
                debugger;

                $("#message").html("");
                var table = document.getElementById(tableID);

                for (var i = 1; i <= sub_byte_division; i++) {
                    var row = table.insertRow(i);


                    var cell1 = row.insertCell(0);
                    var element1 = document.createElement("input");
                    element1.type = "text";
                    element1.id = "s_no" + i;
                    element1.name = "s_no" + i;
                    element1.value = i;
                    element1.size = 5;
                    element1.readonly = true;
                    cell1.appendChild(element1);

                    var cell2 = row.insertCell(1);
                    var element2 = document.createElement("input");
                    element2.type = "text";
                    element2.name = "byte_parameter_name" + i;
                    element2.id = "byte_parameter_name" + i;
                    element2.size = 20;
                    element2.maxLength = 2;
                    element2.value = parameter_name_byte;
                    cell2.appendChild(element2);

                    var cell3 = row.insertCell(2);
                    var element2 = document.createElement("input");
                    element2.type = "text";
                    element2.name = "parameter_name2" + i;
                    element2.id = "parameter_name2" + i;
                    element2.size = 20;
                    element2.maxLength = 20;
                    element2.value = "";
               
                    element2.setAttribute("onkeyup", 'autocompleteMethod("parameter_name2",' + i + ')');
                    cell3.appendChild(element2);

                    var cell4 = row.insertCell(3);
                    var element2 = document.createElement("input");
                    element2.type = "text";
                    element2.name = "sub_division_no" + i;
                    element2.id = "sub_division_no" + i;
                    element2.size =20;
                    element2.maxLength = 50;
                    element2.value = "";
                    cell4.appendChild(element2);

                    var cell5 = row.insertCell(4);
                    var element2 = document.createElement("input");
                    element2.type = "text";
                    element2.name = "start_pos" + i;
                    element2.id = "start_pos" + i;
                    element2.size = 20;
                    element2.maxLength = 8;
                    element2.value = "";
                    cell5.appendChild(element2);


                    var cell6 = row.insertCell(5);
                    var element2 = document.createElement("input");
                    element2.type = "text";
                    element2.name = "no_of_bit" + i;
                    element2.id = "no_of_bit" + i;
                    element2.size = 20;
                    element2.maxLength = 8;
                    element2.value = "";
                    cell6.appendChild(element2);

                    var cell7 = row.insertCell(6);
                    var element2 = document.createElement("input");
                    element2.type = "text";
                    element2.name = "remark" + i;
                    element2.id = "remark" + i;
                    element2.size = 20;
                    element2.maxLength = 8;
                    element2.value = "";
                    cell7.appendChild(element2);

                }


            }
            function inputPopup(url, parameter, sub_division_no, sub_byte_division_id) {
                debugger;
                var popup_height = 580;
                var popup_width = 900;
                var popup_top_pos = (screen.availHeight / 2) - (popup_height / 2);
                var popup_left_pos = (screen.availWidth / 2) - (popup_width / 2);
                url = url + "?parameter_name=" + parameter + "&sub_division_no=" + sub_division_no + "&sub_byte_division_id=" + sub_byte_division_id;
                //alert(url);
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






        </script>
    <style>
        a:hover{
            background-color: yellow;
        }</style>

</body>
</html>
