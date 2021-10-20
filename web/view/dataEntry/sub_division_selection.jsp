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
        <c:when test="${fn:length(subDivisionSelectionListById) > 0}">            
            <body>
        </c:when>        
        <c:otherwise>
        <body onload="addRow('dataTable',${sub_division_no}, '${parameter_name}')">
        </c:otherwise>
    </c:choose>

        <table cellspacing="0" border="0" id="table0"  align="center" width="50%">
            
            <tr style="font-size:larger ;font-weight: 700;" align="center">
                <td>
                    "${parameter_name}" Sub Division Selection Values Details
                </td>
            </tr>
            
            <tr id="message">
                <c:if test="${not empty message}">
                    <td colspan="8" bgcolor="${msgBgColor}"><b>Result: ${message}</b></td>
                </c:if>
            </tr>
            <tr>
                <td>
                    <form name="form1" id ="form1" action="SubDivisionSelectionController" method="post" >
                        <table id="dataTable" style="border-collapse: collapse;" border="1" width="100%" align="center">
                            <tbody>
                                <tr>
                                    <th class="heading">S.No.</th>
                                    <th class="heading">Parameter</th> 
                                    <th class="heading">Display Value</th>                                    
                                    <th class="heading">Bit Value</th>
                                      
                                </tr>
                                
                                <c:forEach var="list" items="${requestScope['subDivisionSelectionListById']}" varStatus="loopCounter">
                                    <tr>
                                        <td><input type="text" name="s_no${loopCounter.count}" id="s_no${loopCounter.count}" size="5" value="${loopCounter.count}" readonly>
                                            <input type="hidden" name="sub_division_selection_id${loopCounter.count}" maxlength="8" size="8" id="sub_division_selection_id${loopCounter.count}" value="${list.sub_division_selection_id}"></td>
                                         <td><input type="text" name="parameter_name${loopCounter.count}" maxlength="50" size="20" id="parameter_name${loopCounter.count}" value="${parameter_name}" readonly></td>
                                     
                                        <td><input type="text" name="display_value${loopCounter.count}" maxlength="50" size="20" id="display_value${loopCounter.count}" value="${list.display_value}"></td>
                                  
                                        <td><input type="text" name="bit_value${loopCounter.count}" maxlength="8" size="8" id="bit_value${loopCounter.count}" value="${list.bit_value}"></td>
                                      
                                    </tr>
                                </c:forEach>

                            <input  type="hidden" name="sub_division_no" value="${sub_division_no}">
                            <input  type="hidden" name="parameter_name" value="${parameter_name}">
                            <input  type="hidden" name="sub_byte_division_id" value="${sub_byte_division_id}">
                            
                            </tbody>
                        </table>
                            <div style="padding-top: 10px;" align="center">
                            <input class="button" type="submit" id="save" name="task" value="Save">
                        </div>
                    </form>
                </td>
            </tr>
        </table>
        <script type="text/javascript" language="javascript">



            function autocompleteMethod(id, count) {
                debugger;
                if (id === "parameter") {
                    $("#parameter" + count).autocomplete("SelectionValueController", {
                        extraParams: {
                            action1: function () {
                                return "getParameter";
                            }
                        }
                    });
                } 

            }


            function addRow(tableID, sub_division_no,parameter_name) {
                debugger;

                $("#message").html("");
                var table = document.getElementById(tableID);

                //  alert(rowCount);
                for (var i = 1; i <= sub_division_no ; i++) {
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
                    element2.name = "parameter" + i;
                    element2.id = "parameter" + i;
                    element2.size = 20;
                    element2.maxLength = 50;
                    element2.value = parameter_name;
                    cell2.appendChild(element2);
                    
                 var cell2 = row.insertCell(2);
                    var element2 = document.createElement("input");
                    element2.type = "text";
                    element2.name = "display_value" + i;
                    element2.id = "display_value" + i;
                    element2.size = 20;
                    element2.maxLength = 50;
                    element2.value = "";
                    cell2.appendChild(element2);
                    
                    var cell3 = row.insertCell(3);
                    var element2 = document.createElement("input");
                    element2.type = "text";
                    element2.name = "bit_value" + i;
                    element2.id = "bit_value" + i;
                    element2.size = 20;
                    element2.maxLength = 50;
                    element2.value = "";
                    cell3.appendChild(element2);

                    
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