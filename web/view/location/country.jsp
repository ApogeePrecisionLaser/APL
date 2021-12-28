<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@include file="../layout/header.jsp" %>
<style type="text/css">
    /*            .new_input
                {
                    font-size: 16px;
                    font-family:"kruti Dev 010", Sans-Serif;
                    font-weight: 500;
                    background-color: white;
                }*/
</style>
<script type="text/javascript" language="javascript">
    jQuery(function () {
        $("#searchCountry").autocomplete("countryCont", {
            extraParams: {
                action1: function () {
                    return "getCountryList";
                }
            }
        });
        $("#searchCountry").result(function (event, data, formatted) {
            if (document.getElementById("searchCountry").value == 'No Such country Exists.') {
                document.getElementById("searchCountry").value = "";
            }
        });
        $("#searchCountryCode").autocomplete("countryCont", {
            extraParams: {
                action1: function () {
                    return "getSearchCountryCode"
                }
            }
        });
    });
    function makeEditable(id) {
        document.getElementById("country_id").disabled = false;
        document.getElementById("country_name").disabled = false;
        document.getElementById("country_discription").disabled = false;
        if (id == 'new') {
            $("#message").html("");
            document.getElementById("country_id").value = "";
            document.getElementById("edit").disabled = true;
            document.getElementById("delete").disabled = true;
            document.getElementById("save_As").disabled = true;
            document.getElementById("designation").focus();
            setDefaultColor(document.getElementById("noOfRowsTraversed").value, 4);
        }
        if (id == 'edit') {
            document.getElementById("save_As").disabled = false;
            document.getElementById("delete").disabled = false;
            document.getElementById("country_name").focus();
        }
        document.getElementById("save").disabled = false;
    }

    
    function setStatus(id) {
        if (id == 'save') {
            document.getElementById("clickedButton").value = "Save";
        } else if (id == 'save_As') {
            document.getElementById("clickedButton").value = "Save AS New";
        } else
            document.getElementById("clickedButton").value = "Delete";
    }
    function myLeftTrim(str) {
        var beginIndex = 0;
        for (var i = 0; i < str.length; i++) {
            if (str.charAt(i) == ' ')
                beginIndex++;
            else
                break;
        }
        return str.substring(beginIndex, str.length);
    }
    function verify() {
        var result;
        if (document.getElementById("clickedButton").value == 'Save' || document.getElementById("clickedButton").value == 'Save AS New') {
            var media_type = document.getElementById("country_name").value;
            var country_discription = document.getElementById("country_discription").value;
            if (myLeftTrim(media_type).length == 0) {
                $("#message").html("<td colspan='2' bgcolor='coral'><b>Designation is required...</b></td>");
                document.getElementById("country_name").focus();
                return  false; // code to stop from submitting the form2.
            }
            if (myLeftTrim(country_discription).length == 0) {
                $("#message").html("<td colspan='2' bgcolor='coral'><b>country_discription is required...</b></td>");
                document.getElementById("country_discription").focus();
                return  false; // code to stop from submitting the form2.
            }
            if (result == false)
            {// if result has value false do nothing, so result will remain contain value false.

            } else {
                result = true;

            }
            if (document.getElementById("clickedButton").value == 'Save AS New') {
                result = confirm("Are you sure you want to save it as New record?")
                return result;
            }
        } else
            result = confirm("Are you sure you want to delete this record?")
        return result;
    }
    function searchCountryList(id)
    {
        var searchCountryName = document.getElementById('searchCountryName').value;
        var searchCountryDiscription = document.getElementById('searchCountryDiscription').value;
        var action;
        if (id == 'viewPdf')
            action = "task=generateCountryNameReport&CountryName=" + searchCountryName + "&searchCountryDiscription=" + searchCountryDiscription;
        else
            action = "task=generateCountryNameXlsReport&searchCountryName=" + searchDesignation + "&searchCountryDiscription=" + searchCountryDiscription;
        var url = "countryCont?" + action;
        popup = popupWindow(url, "Country_View_Report");
    }
    function popupWindow(url, windowName)
    {
        var windowfeatures = "left=50, top=50, width=1000, height=500, resizable=no, scrollbars=yes, location=0, menubar=no, status=no, dependent=yes";
        return window.open(url, windowName, windowfeatures)
    }

    function fillColumn(id, count) {
        $('#country_id').val(id);
        $('#country_discription').val($("#" + count + '2').html());
        $('#country_name').val($("#" + count + '3').html());
        document.getElementById("edit").disabled = false;
        document.getElementById("delete").disabled = false;
    }
</script>
</head>
<body>
    <div class="container-fluid">
        <table align="center" cellpadding="0" cellspacing="0"  class="main">            <!--DWLayoutDefaultTable-->
            <div class="container  topbox" align="center">
                <h1 style="color: red;">Country</h1>
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
                    <div class="container-fluid">

                        <form name="form0" class="form-group row" method="POST" action="countryCont">
                            <table  align="center"  class="heading1" width="1100" class="table table-bordered table-hover  table-striped">
                                <tr>
                                    <th>Country Description</th>
                                    <td><input class="form-control" type="text" id="searchCountryDiscription" name="searchCountryDiscription" value="${searchCountryDiscription}" size="200" ></td>
                                    <th>Country Name</th>
                                    <td><input class="form-control" type="text" id="searchCountryName" name="searchCountryName" value="${searchCountryName}" size="200" ></td>
                                    <td><input class="btn btn-primary" type="submit" name="task" id="searchInCountry" value="Search"></td>
                                    <td><input class="btn btn-success" type="submit" name="task" id="showAllRecords" value="Show All Records"></td>

                                    <td><input class="btn btn-danger" type="button" id="viewPdf" name="viewPdf" value="PDF" onclick="searchCountryList(id);"/></td>
                                    <td><input class="btn btn-info" type="button" id="viewXls" name="viewXls" value="Excel" onclick="searchCountryList(id);"/>
                                    </td>

                                </tr>
                            </table>
                        </form>
                    </div>
                </td>
            </tr>
            <tr>
                <td align="center">
                    <form name="form1" method="POST" action="countryCont">
                        <DIV class="table-hover">
                            <table id="table1" width="600"  border="1"  align="center" class="table table-bordered table-hover  table-striped">
                                <tr>
                                    <th class="heading">S.No.</th>
                                    <th class="heading" >Country Description</th>
                                    <th class="heading" >Country Name</th>

                                </tr>
                                <c:forEach var="media" items="${requestScope['mediaList']}" varStatus="loopCounter">
                                    <tr  class="${loopCounter.index % 2 == 0 ? 'even': 'odd'}" onclick="fillColumn('${media.country_id}', '${loopCounter.count }');">
                                        <td  align="center">
                                            ${loopCounter.count }
                                        </td>
                                        <td id="${loopCounter.count }2" >${media.country_discription}</td>
<!--                                               <td id="t1c${IDGenerator.uniqueID}" onclick="fillColumns(id)" >${media.remark}</td>-->
                                        <td id="${loopCounter.count }3" class="new_input"  >${media.country_name}</td>                                                    
                                    </tr>
                                </c:forEach>
                                <tr>
                                    <td align='center' colspan="4">
                                        <c:choose>
                                            <c:when test="${showFirst eq 'false'}">
                                                <input class="btn btn-primary" type='submit' name='buttonAction' value='First' >
                                            </c:when>
                                            <c:otherwise>
                                                <input class="btn btn-primary" type='submit' name='buttonAction' value='First'>
                                            </c:otherwise>
                                        </c:choose>
                                        <c:choose>
                                            <c:when test="${showPrevious == 'false'}">
                                                <input class="btn btn-success" type='submit' name='buttonAction' value='Previous' >
                                            </c:when>
                                            <c:otherwise>
                                                <input class="btn btn-success" type='submit' name='buttonAction' value='Previous'>
                                            </c:otherwise>
                                        </c:choose>
                                        <c:choose>
                                            <c:when test="${showNext eq 'false'}">
                                                <input class="btn btn-warning" type='submit' name='buttonAction' value='Next' >
                                            </c:when>
                                            <c:otherwise>
                                                <input class="btn btn-warning" type='submit' name='buttonAction' value='Next'>
                                            </c:otherwise>
                                        </c:choose>
                                        <c:choose>
                                            <c:when test="${showLast == 'false'}">
                                                <input class="btn btn-info" type='submit' name='buttonAction' value='Last' >
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
                                <input  type="hidden" name="searchCountryName" value="${searchCountryName}"  >
                                <input  type="hidden" name="searchCountryDiscription" value="${searchCountryDiscription}">
                            </table></DIV>
                    </form>
                </td>
            </tr>

            <tr>
                <td align="center">
                    <div>
                        <form name="form2" method="POST" class="form-group row" action="countryCont" onsubmit="return verify()">
                            <table id="table2"  class="table table-bordered table-hover  table-striped" border="0"  align="center" width="600">
                                <tr id="message">
                                    <c:if test="${not empty message}">
                                        <td colspan="2" bgcolor="${msgBgColor}"><b>Result: ${message}</b></td>
                                    </c:if>
                                </tr>
                                <tr>
                                <input type="hidden" id="country_id" name="country_id" value="">
                                <th class="heading1" >Country Description</th><td><input class="form-control" type="text" id="country_discription" name="country_discription" value="" size="45" disabled></td>
                                </tr>
                                <tr>
                                    <th class="heading1" >Country Name</th>
                                    <td>

                                        <input class="form-control" type="text" id="country_name" name="country_name" value=""  size="45" disabled>
                                    </td>
                                </tr>
                                <!--                                <tr>
                                                                    <th class="heading1" >Remark</th>
                                                                    <td>
                                
                                                                        <input class="form-control" type="text" id="remark" name="remark" value=""  size="45" disabled>
                                                                    </td>
                                                                </tr>-->

                                <tr>
                                    <td align='center' colspan="2">
                                        <input type="button" class="btn btn-primary" name="edit" id="edit" value="Edit" onclick="makeEditable(id)" >
                                        <input type="submit" class="btn btn-success" name="task" id="save" value="Save" onclick="setStatus(id)" >
                                        <input type="submit" class="btn btn-info" name="task" id="save_As" value="Save AS New" onclick="setStatus(id)" >
                                        <input type="reset" class=" btn btn-warning" name="new" id="new" value="New" onclick="makeEditable(id)" >
                                        <input type="submit" class="btn btn-success" name="task" id="delete" value="Delete" onclick="setStatus(id)">
                                    </td>
                                </tr>
                                <%-- These hidden fields "lowerLimit", "noOfRowsTraversed", and "clickedButton" belong to form2 of table2. --%>
                                <input type="hidden" name="lowerLimit" value="${lowerLimit}">
                                <input type="hidden" name="noOfRowsTraversed" value="${noOfRowsTraversed}">
                                <input type="hidden" id="clickedButton" value="">
                                <input  type="hidden" name="searchDesignation" value="${searchDesignation}"  >
                            </table>
                        </form>
                    </div>
                </td>
            </tr>
        </table>

    </DIV>
</td></tr>
<tr><td>
        <%@include file="../layout/footer.jsp" %>