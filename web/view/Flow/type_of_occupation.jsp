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
        <title>Data Entry: Organization Type </title>
        <link rel="shortcut icon" href="/imageslayout/ssadvt_logo.ico">
<!--        <link href="style/style1.css" type="text/css" rel="stylesheet" media="Screen"/>-->
<!--        <link href="style/Table_content.css" type="text/css" rel="stylesheet" media="Screen"/>-->
        <script type="text/javascript" src="JS/jquery-1.4.2.min.js"></script>
        <script type="text/javascript" src="JS/jquery.autocomplete.js"></script>
        <script type="text/javascript">

            jQuery(function(){
        $("#searchtypeofoccupation").autocomplete("TypeOfOccupationController", {
            extraParams: {
                action1: function() { return "getSearchOccupation"}
            }
        });
    });
    function fillColumns(id)
     {
       
        var noOfRowsTraversed = document.getElementById("noOfRowsTraversed").value;
        var noOfColumns = 4;
        var columnId = id;                              <%-- holds the id of the column being clicked, excluding the prefix t1c e.g. t1c3 (column 3 of table 1). --%>
        columnId = columnId.substring(3, id.length);    <%-- for e.g. suppose id is t1c3 we want characters after t1c i.e beginIndex = 3. --%>
        var lowerLimit, higherLimit;
        for(var i = 0; i < noOfRowsTraversed; i++)
        {
            lowerLimit = i * noOfColumns + 1;       // e.g. 11 = (1 * 10 + 1)
            higherLimit = (i + 1) * noOfColumns;    // e.g. 20 = ((1 + 1) * 10)

            if((columnId>= lowerLimit) && (columnId <= higherLimit)) break;
        }

        setDefaultColor(noOfRowsTraversed, noOfColumns);        // set default color of rows (i.e. of multiple coloumns).
        var t1id = "t1c";       // particular column id of table 1 e.g. t1c3.

     document.getElementById("type_of_occupation_id").value=document.getElementById(t1id + (lowerLimit + 0)).innerHTML;
     document.getElementById("name").value=document.getElementById(t1id + (lowerLimit + 2)).innerHTML;
     document.getElementById("description").value=document.getElementById(t1id + (lowerLimit + 3)).innerHTML;

        for(var i = 0; i < noOfColumns; i++) {
            document.getElementById(t1id + (lowerLimit + i)).bgColor = "#d0dafd";
        }

        document.getElementById("edit").disabled = false;
        if(!document.getElementById("save").disabled)
        {
            document.getElementById("save_as_new").disabled = true;
            document.getElementById("delete").disabled = false;
            dodument.getElementById("save").disabled=true;

        }

        $("#message").html("");
    }
          function setDefaultColor(noOfRowsTraversed, noOfColumns) {

        for(var i = 0; i < noOfRowsTraversed; i++) {

            for(var j = 1; j <= noOfColumns; j++) {

                document.getElementById("t1c" + (i * noOfColumns + j)).bgColor = "";
        }
        }}
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
        var clickedButton = document.getElementById("clickedButton").value;
        if(clickedButton == 'save' || clickedButton == 'Save AS New') {

                      var shift_type = document.getElementById("shift_type").value;
                                if(myLeftTrim(shift_type).length == 0) {
                                    $("#message").html("<td colspan='6' bgcolor='coral'><b> Shift Type is required...</b></td>");
                                    document.getElementById("shift_type").focus();
                                    return false;
                                }
                        var starting_time_hour = document.getElementById("starting_time_hour").value;
                                if(myLeftTrim(starting_time_hour).length == 0) {

                                    $("#message").html("<td colspan='6' bgcolor='coral'><b> Starting Time Hour is required...</b></td>");
                                    document.getElementById("starting_time_hour").focus();
                                    return false;
                                }
                        var starting_time_min = document.getElementById("starting_time_min").value;
                                if(myLeftTrim(starting_time_min).length == 0) {

                                    $("#message").html("<td colspan='6' bgcolor='coral'><b> Starting Time Min is required...</b></td>");
                                    document.getElementById("starting_time_min").focus();
                                    return false;
                                }

                             var ending_time_hour = document.getElementById("ending_time_hour").value;
                                if(myLeftTrim(ending_time_hour).length == 0) {

                                    $("#message").html("<td colspan='6' bgcolor='coral'><b> Ending Time Hour is required...</b></td>");
                                    document.getElementById("ending_time_hour").focus();
                                    return false;
                                }
                               var ending_time_min = document.getElementById("ending_time_min").value;
                                if(myLeftTrim(ending_time_min).length == 0) {

                                    $("#message").html("<td colspan='6' bgcolor='coral'><b> Ending Time Min is required...</b></td>");
                                    document.getElementById("ending_time_min").focus();
                                    return false;
                                }
        return result;

        }
    }

     function makeEditable(id) {
        document.getElementById("name").disabled = false;
        document.getElementById("description").disabled = false;
  

        document.getElementById("save").disabled = true;

        if(id == 'new') {
           $("#message").html("");
            document.getElementById("edit").disabled = true;
            document.getElementById("delete").disabled = true;
            document.getElementById("save_as").disabled =true;
             document.getElementById("save").disabled =false;
             document.getElementById("type_of_occupation_id").value=0;




        }
        if(id == 'edit'){
               $("#message").html("");
                document.getElementById("save_as").disabled = false;
                document.getElementById("delete").disabled = false;
                 document.getElementById("save").disabled = false;

            }

        }



function setStatus(id) {

        if(id == 'save'){

            document.getElementById("clickedButton").value = "save";
        }
        else if(id == 'save_as'){
            document.getElementById("clickedButton").value = "Save AS New";
        }
        else if(id == 'delete'){
            document.getElementById("clickedButton").value = "Delete";
        }

        else
            {}
    }

 function getlist(){
                var searchtypeofoccupation = $("#searchtypeofoccupation").val();
                var queryString = "task=generateHSReport&searchtypeofoccupation="+searchtypeofoccupation;
                var url = "TypeOfOccupationController?" + queryString;
                popupwin = openPopUp(url, "List", 600, 900);
            }
            function getCity(){
                var searchtypeofoccupation = $("#searchtypeofoccupation").val();
              var queryString = "task=generateReport&searchtypeofoccupation="+searchtypeofoccupation;
                var url = "TypeOfOccupationController?" + queryString;
                popupwin = openPopUp(url, "List", 600, 900);
            }
  function openPopUp(url, window_name, popup_height, popup_width) {
                var popup_top_pos = (screen.availHeight / 2) - (popup_height / 2);
                var popup_left_pos = (screen.availWidth / 2) - (popup_width / 2);
                var window_features = "left=" + popup_left_pos + ", top=" + popup_top_pos + ", width=" + popup_width + ", height=" + popup_height + ", resizable=no, scrollbars=yes, active=no, dialog=yes, dependent=yes";
                return window.open(url, window_name, window_features);
            }


            
</script>
    </head>
    <body>
       
       <div class="container-fluid">
        <table align="center" cellpadding="0" cellspacing="0"  class="main">            <!--DWLayoutDefaultTable-->
           <%@include file="/layout/header.jsp" %>
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
                          
                                     <form name="form0" action="TypeOfOccupationController">
                             <table align="center" class="table table-bordered table-responsive" width="700">
                                 <tr> <th>Type Of Occupation</th>
                   <td><input class="form-control" type="text" id="searchtypeofoccupation" name="searchtypeofoccupation" value="${searchtypeofoccupation}" size="20" ></td>
                   <td><input class="btn btn-primary" type="submit" name="task" id="searchIn" value="Search"></td>
                   <td><input class="btn btn-success" type="submit" name="task" id="showAllRecords" value="Show All Records"></td>
                   <td><input type="button" class=" btn btn-warning" id="viewPdf" name="viewPdf" value="PDF" onclick="getlist()"></td>
                   <td><input type="button" class="btn btn-info"  id="viewXls" name="viewXls" value="Excel" onclick="getCity()"></td>
                        </tr>
                             </table>
                         </form>
                                </td>
                            </tr>
                            <tr>
                                <td align="center">
                                   <form name="form1" action="TypeOfOccupationController">
                                        <DIV class="table-hover">
                                          <TABLE BORDER="1" align="center" cellpadding="5%" width="52%" id="table1" class="table table-bordered table-responsive">
                                                &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                                <tr>
                                                      
                <TH class="heading">S.no&nbsp;</TH>
                <TH class="heading">Type Of Occupation&nbsp;</TH>
                <TH class="heading">Description&nbsp;</TH>
            
                                                </tr>
                                             <c:forEach var="type_of_occupation" items="${requestScope['list']}" varStatus="loopCounter">
            <TR class="${loopCounter.index % 2 == 0 ? 'even': 'odd'}" class="bcolor">
            <tr>
            <td id="t1c${IDGenerator.uniqueID}" style="display:none" onclick="fillColumns(id)"> ${type_of_occupation.type_of_occupation_id}</td>
            <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)" align="center"> ${lowerLimit + loopCounter.count- noOfRowsTraversed }</td>
            <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)">${type_of_occupation.name}</td>
            <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)">${type_of_occupation.description}</td>
          

            </tr>
            </TR>
            </c:forEach>
                                                <tr>
                                                    <td align='center' colspan="5">
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
                                            </table></DIV>
                                    </form>
                                </td>
                            </tr>

                            <tr>
                                <td align="center">
                                    <div>
                                        <form name="form2" method="POST" class="form-group row" action="TypeOfOccupationController" onsubmit="return verify()">
                                            <table id="table2"  class="table table-bordered table-responsive" border="0"  align="center" width="600">
                                                <tr id="message">
                                                    <c:if test="${not empty message}">
                                                        <td colspan="2" bgcolor="${msgBgColor}"><b>Result: ${message}</b></td>
                                                    </c:if>
                                                </tr>
                                                <tr><input class="input" type="hidden" id="type_of_occupation_id" name="type_of_occupation_id" value="" ></tr>
                              <tr>

                                 <th class="heading1"> Occupation Name </th>
                                 <td><input type="text" class="form-control" id="name" size="12" name="name" value="" disabled></td>

                              </tr>
                             <tr>

                                 <th class="heading1"> Description</th>
                                 <td><input type="text" class="form-control"  id="description" size="12" name="description" value="" disabled></td>

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
                                                <%-- These hidden fields "lowerLimit", "noOfRowsTraversed", and "clickedButton" belong to form2 of table2. --%>
                                                <input type="hidden" name="lowerLimit" value="${lowerLimit}">
                                                <input type="hidden" name="noOfRowsTraversed" value="${noOfRowsTraversed}">
                                                <input type="hidden" id="clickedButton" value="">
                                             <input  type="hidden" name="searchtypeofoccupation" value="${searchtypeofoccupation}">
                                            </table>
                                        </form>
                                    </div>
                                </td>
                            </tr>
                        </table>

                    </DIV>
                </td></tr>
            <tr><td><%@include file="/layout/footer.jsp" %></td> </tr>
        </table>
    </body>
</html>