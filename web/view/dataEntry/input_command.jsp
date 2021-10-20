<%-- 
    Document   : input_command
    Created on : 24 Sep, 2019, 1:19:28 PM
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
<script type="text/javascript" src="http://code.jquery.com/jquery.min.js"></script>
    <script type="text/javascript" src="project.js"></script>
    </head>
    <c:choose>        
        <c:when test="${fn:length(inputListById) > 0}">            
            <body>
        </c:when>        
        <c:otherwise>
        <body onload="addRow('dataTable',${input_no}, '${command}')">
        </c:otherwise>
    </c:choose>

        <table cellspacing="0" border="0" id="table0"  align="center" width="500">
            <tr style="font-size:larger ;font-weight: 700;" align="center">
                <td>
                    "${command}" Selection Details
                </td>
            </tr>
            
            <tr id="message">
                <c:if test="${not empty message}">
                    <td colspan="8" bgcolor="${msgBgColor}"><b>Result: ${message}</b></td>
                </c:if>
            </tr>
            <tr>
                <td>
                    <form name="form1" id ="form1" action="InputCont.do" method="post" >
                        <table id="dataTable" style="border-collapse: collapse;" border="1" width="100%" align="center">
                            <tbody>
                                <tr>
                                    <th class="heading">S.No.</th>
                                    <th class="heading">Command</th>
                                    <th class="heading">Parameter</th>       
                                    <th class="heading">Parameter Type</th>
                                    <th class="heading">Remark</th>
                                </tr>
                                <c:forEach var="list" items="${requestScope['inputListById']}" varStatus="loopCounter">
                                    <tr>
                                        <td><input type="text" name="s_no${loopCounter.count}" id="s_no${loopCounter.count}" size="5" value="${loopCounter.count}" readonly>
                                            <input type="hidden" name="selection_id${loopCounter.count}" maxlength="8" size="20" id="selection_id${loopCounter.count}" value="${list.input_id}"></td>
                                        <td><input type="text" name="command_name${loopCounter.count}" maxlength="50" size="20" id="command_name${loopCounter.count}" value="${list.command_name}" readonly></td>
                                        <td><input type="text" name="parameter${loopCounter.count}" maxlength="8" size="20" id="parameter${loopCounter.count}" value="${list.parameter}" onkeyup="autocompleteMethod('parameter',${loopCounter.count})"></td>
                                        <td><input type="text" name="parameter_type${loopCounter.count}" maxlength="8" size="20" id="parameter_type${loopCounter.count}" value="${list.parameter_type}" onkeyup="autocompleteMethod('parameter_type',${loopCounter.count})"></td>
                                        <td><input type="text" name="remark{loopCounter.count}" maxlength="8" size="20" id="remark{loopCounter.count}" value="${list.remark}" ></td>
                                    </tr>
                                </c:forEach>

                            <input  type="hidden" name="input_no" id="input_no" value="${input_no}" >
                            <input  type="hidden" name="command" value="${command}" >
                            <input  type="hidden" name="command_id" value="${command_id}" >
                            <input  type="hidden" name="row_no" id="row_no" value="${row_no}" >
                            <div id="savebutton"> <input class="button" type="submit" id="save" name="task" value="Save" onclick="disableButton()" ></div>
                                                                   
                            </tbody>
                        </table>

                    </form>
                </td>
            </tr>
        </table>
        <script type="text/javascript" language="javascript">
  
    function disableButton(){
     var no=document.getElementById("input_no").value;
       var rowno=document.getElementById("row_no").value;
      // alert(no+"vvvvvvvvvvvv"+rowno);
       if(rowno>=no){
           	document.getElementById("save").disabled = true;
       }
   
	}



            function autocompleteMethod(id, count) {
                debugger;
                if (id === "command_name") {
                    $("#command_name" + count).autocomplete("SelectionCont.do", {
                        extraParams: {
                            action1: function () {
                                return "getCommand";
                            }
                        }
                    });
                } else if (id === "parameter") {
                    $("#parameter" + count).autocomplete("SelectionCont.do", {
                        extraParams: {
                            action1: function () {
                                return "getParameter";
                            }
                        }
                    });

                } else if (id === "parameter_type") {
                    $("#parameter_type" + count).autocomplete("SelectionCont.do", {
                        extraParams: {
                            action1: function () {
                                return "getParameterType";
                            }
                        }
                    });
                }

            }


            function addRow(tableID, input_no, command) {
                debugger;
                

                $("#message").html("");
                var table = document.getElementById(tableID);

                 
                for (var i = 1; i <= input_no; i++) {
                    var row = table.insertRow(i);


                    var cell1 = row.insertCell(0);
                    var element1 = document.createElement("input");
                    element1.type = "text";
                    element1.id = "s_no" + i;
                    element1.name = "s_no" + i;
                    element1.value = i;
                    element1.size = 5;
                    cell1.appendChild(element1);

                    var cell2 = row.insertCell(1);
                    var element2 = document.createElement("input");
                    element2.type = "text";
                    element2.name = "command_name" + i;
                    element2.id = "command_name" + i;
                    element2.size = 20;
                    element2.maxLength = 2;
                    element2.value = command;
                    element2.setAttribute("onkeyup", 'autocompleteMethod("command_name",' + i + ')');
                    cell2.appendChild(element2);

                    var cell3 = row.insertCell(2);
                    var element2 = document.createElement("input");
                    element2.type = "text";
                    element2.name = "parameter" + i;
                    element2.id = "parameter" + i;
                    element2.size = 20;
                    element2.maxLength = 8;
                    element2.value = "";
                    element2.setAttribute("onkeyup", 'autocompleteMethod("parameter",' + i + ')');
                    cell3.appendChild(element2);

                    

                    var cell4 = row.insertCell(3);
                    var element2 = document.createElement("input");
                    element2.type = "text";
                    element2.name = "parameter_type" + i;
                    element2.id = "parameter_type" + i;
                    element2.size = 20;
                    element2.maxLength = 8;
                    element2.value = "";
                    element2.setAttribute("onkeyup", 'autocompleteMethod("parameter_type",' + i + ')');
                    cell4.appendChild(element2);

                    var cell5 = row.insertCell(4);
                    var element2 = document.createElement("input");
                    element2.type = "text";
                    element2.name = "remark" + i;
                    element2.id = "remark" + i;
                    element2.size = 20;
                    element2.maxLength = 8;
                    element2.value = "";
                    cell5.appendChild(element2);

                }


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
