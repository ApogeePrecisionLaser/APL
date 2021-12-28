<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@include file="../layout/header.jsp" %>
<script type="text/javascript" language="javascript">
    $(function () {

        $("#zone_name_m").autocomplete({
            source: function (request, response) {
                var random = $('#zone_name_m').val();
                $.ajax({
                    url: "wardTypeCont",
                    dataType: "json",
                    data: {action1: "getZoneName", q: random},
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
                $('#zone_name_m').val(ui.item.label); // display the selected text
                return false;
            }
        });


        $("#searchWardType").autocomplete({

            source: function (request, response) {
                var random = $('#searchWardType').val();

                $.ajax({
                    url: "wardTypeCont",
                    dataType: "json",
                    data: {action1: "getWardName", q: random},
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
                $('#searchWardType').val(ui.item.label); // display the selected text
                return false;
            }
        });

        $("#searchWardNo").autocomplete({

            source: function (request, response) {

                var random = $('#searchWardNo').val();
                $.ajax({
                    url: "wardTypeCont",
                    dataType: "json",
                    data: {action1: "getWardNo", q: random},
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
                $('#searchWardNo').val(ui.item.label); // display the selected text
                return false;
            }
        });

    });

    function setDefaultColor(noOfRowsTraversed, noOfColumns) {
        for (var i = 0; i < noOfRowsTraversed; i++) {
            for (var j = 1; j <= noOfColumns; j++) {
                document.getElementById("t1c" + (i * noOfColumns + j)).bgColor = "";     // set the default color.
            }
        }
    }
    function makeEditable(id) {

        document.getElementById("ward_no").disabled = false;
        document.getElementById("ward_name").disabled = false;
        document.getElementById("zone_name_m").disabled = false;
        document.getElementById("remark").disabled = false;
        document.getElementById("save").disabled = false;
        document.getElementById("delete").disabled = false;
        /* if(document.getElementById("active").value == "N" )
         {
         document.getElementById("ward_no").disabled = true;
         document.getElementById("city_name").disabled = true;
         document.getElementById("remark").disabled = true;
         }*/

        //   document.getElementById("revise").disabled =false;
        document.getElementById("cancel").disabled = true;
        document.getElementById("save_As").disabled = true;
        //document.getElementById("save").disabled = true;
        if (id == 'new')
        {
            document.getElementById("created_date").disabled = true;
            //    document.getElementById("active").value ='';
            //document.getElementById("message").innerHTML = "";      // Remove message
            $("#message").html("");

            //document.getElementById("revise").disabled = true;
            document.getElementById("cancel").disabled = true;
            document.getElementById("save_As").disabled = true;
            document.getElementById("save").disabled = false;




            setDefaultColor(document.getElementById("noOfRowsTraversed").value, 6);
            document.getElementById("ward_no").focus();

        }
        if (id == 'edit') {
            document.getElementById("save_As").disabled = false;
            document.getElementById("delete").disabled = false;

        }
    }
    function setStatus(id) {
        if (id == 'save') {
            document.getElementById("clickedButton").value = "Save";
        } else if (id == 'save_As') {
            document.getElementById("clickedButton").value = "Save AS New";
        } else if (id == 'revise') {
            document.getElementById("clickedButton").value = "Revise";
        } else
            document.getElementById("clickedButton").value = "Delete";
    }

    function fillColumns(id) {
        var noOfRowsTraversed = document.getElementById("noOfRowsTraversed").value;
        var noOfColumns = 6;
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

        setDefaultColor(noOfRowsTraversed, noOfColumns);        // set default color of rows (i.e. of multiple coloumns).
        var t1id = "t1c";       // particular column id of table 1 e.g. t1c3.

        document.getElementById("ward_id_m").value = document.getElementById(t1id + (lowerLimit + 0)).innerHTML;
        document.getElementById("zone_name_m").value = document.getElementById(t1id + (lowerLimit + 2)).innerHTML;
        document.getElementById("ward_name").value = document.getElementById(t1id + (lowerLimit + 3)).innerHTML;
        document.getElementById("ward_no").value = document.getElementById(t1id + (lowerLimit + 4)).innerHTML;

        //  document.getElementById("created_date").value = document.getElementById(t1id +(lowerLimit+8)).innerHTML;
        document.getElementById("remark").value = document.getElementById(t1id + (lowerLimit + 5)).innerHTML;
        // document.getElementById("active").value = document.getElementById(t1id +(lowerLimit+8)).innerHTML;
        //       var b=  document.getElementById(t1id +(lowerLimit+8)).innerHTML;
        // alert(b);
        for (var i = 0; i < noOfColumns; i++) {
            document.getElementById(t1id + (lowerLimit + i)).bgColor = "#d0dafd";        // set the background color of clicked row to yellow.
        }
        //makeEditable('');

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
        for (var i = 0; i < str.length; i++) {
            if (str.charAt(i) == ' ')
                beginIndex++;
            else
                break;
        }
        return str.substring(beginIndex, str.length);
    }
    var popupwin = null;
    function getPdf() {
        var searchWardType = $("#searchWardType").val();
        var searchZone = $("#searchZone").val();
        var queryString = "task=generateMapReport&searchWardType=" + searchWardType + "&searchZone=" + searchZone;
        var url = "wardTypeCont?" + queryString;
        popupwin = openPopUp(url, "Mounting Type Map Details", 500, 1000);

    }
    function getExcel() {
        var searchWardType = $("#searchWardType").val();
        var searchZone = $("#searchZone").val();
        var queryString = "task=generateReport&searchWardType=" + searchWardType + "&searchZone=" + searchZone;
        var url = "wardTypeCont?" + queryString;
        popupwin = openPopUp(url, "List", 600, 900);
    }
    function openPopUp(url, window_name, popup_height, popup_width) {
        var popup_top_pos = (screen.availHeight / 2) - (popup_height / 2);
        var popup_left_pos = (screen.availWidth / 2) - (popup_width / 2);
        var window_features = "left=" + popup_left_pos + ", top=" + popup_top_pos + ", width=" + popup_width + ", height=" + popup_height + ", resizable=yes, scrollbars=yes, location=0, menubar=no, status=no, dependent=yes";

        return window.open(url, window_name, window_features);
    }

</script>
<style>


</style>

<div class="container-fluid">
    <div class="container  topbox" align="center">
        <h1 style="color: red;">Ward</h1>
    </div>

    <br>
    <div class="container">
        <div class="row">

        </div>
        <div class="container">
            <form name="form0" method="POST" action="wardTypeCont">
                <table class="table table-bordered table-hover  table-striped">
                    <tr>
                        <!--                                <th>Zone Name</th>
                                                        <td> <input type="text" class="form-control" name="searchZone" size="20" id="searchZone"  value="${searchZone}"/></td>-->

                        <th> Ward Name </th>
                        <td><input class="form-control" type="text" id="searchWardType" name="searchWardType" value="${searchWardType}" size="20" ></td>
                        <th>Ward No</th>
                        <td><input class="form-control" type="text" id="searchWardNo" name="searchWardNo" value="${searchWardNo}" size="20" ></td>
                    </tr>
                    <tr>
                        <td colspan="12" align="center">  <input class="btn btn-primary" type="submit" name="task" id="searchIn" value="Search">
                            <input class="btn btn-success" type="submit" name="task" id="showAllRecords" value="Show All Records">
                            <input type="button" class="btn btn-warning" id="viewPdf" name="viewPdf" value="PDF" onclick="getPdf()">
                            <input type="button" class="btn btn-info"  id="viewXls" name="viewXls" value="Excel" onclick="getExcel()"></td>
                    </tr>
                </table>
            </form>
        </div>
        <div class="container">
            <form action="wardTypeCont" method="post">
                <input type="hidden" name="searchZone" id="searchZone" value="${searchZone}"/>
                <input type="hidden" name="searchZone_no" id="searchZone_no" value="${searchZone_no}"/>
                <table class="table table-bordered table-hover  table-striped">
                    <tr>
                        <th class="heading">S.No.</th>
                        <th class="heading">Zone Name</th>
                        <th class="heading">Ward Name</th>
                        <th class="heading">Ward No</th>
                        <th class="heading">Remark</th>

                    </tr>
                    <!---below is the code to show all values on jsp page fetched from trafficTypeList of TrafficController     --->
                    <c:forEach var="wardTypeBean" items="${requestScope['wardTypeList']}"  varStatus="loopCounter">
                        <tr  class="${loopCounter.index % 2 == 0 ? 'even': 'odd'}" >
                            <%--  <td id="t1c${IDGenerator.uniqueID}" onclick="fillColumns(id)" align="center">
                                  <input type="hidden" id="status_type_id${loopCounter.count}" value="${statusTypeBean.status_type_id}">${lowerLimit - noOfRowsTraversed + loopCounter.count}
                                  <td id="t1c${IDGenerator.uniqueID}" onclick="fillColumns(id)" align="center">${lowerLimit - noOfRowsTraversed + loopCounter.count}</td>
                              </td> --%>
                            <td id="t1c${IDGenerator.uniqueID}" style="display:none" onclick="fillColumns(id)">${wardTypeBean.ward_id}</td>
                            <td id="t1c${IDGenerator.uniqueID}" onclick="fillColumns(id)" align="center">${lowerLimit - noOfRowsTraversed + loopCounter.count}</td>
                            <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)" >${wardTypeBean.zone_m}</td>
                            <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)" >${wardTypeBean.ward_name}</td>
                            <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)" >${wardTypeBean.ward_no}</td>
                            <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)" >${wardTypeBean.remark}</td>

                        </tr>
                    </c:forEach>
                    <tr>
                        <td align='center' colspan="8">
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
                    <input  type="hidden" id="searchWardType" name="searchWardType" value="${searchWardType}" >
                    <input  type="hidden" id="searchWardNo" name="searchWardNo" value="${searchWardNo}" >
                </table>
            </form>
        </div>
        <div class="container">
            <form name="form2" method="POST"  action="wardTypeCont" onsubmit="return verify()">
                <table class="table table-bordered table-hover  table-striped">
                    <tr id="message">
                        <c:if test="${not empty message}">
                            <td colspan="2" bgcolor="${msgBgColor}"><b>Result: ${message}</b></td>
                        </c:if>
                    </tr>
                    <tr>
                        <th class="heading1">Zone Name</th>
                        <td>
                            <input class="input" type="hidden" id="ward_id_m" name="ward_id_m" value="" >
                            <input class="form-control" type="text" id="zone_name_m" name="zone_name_m" value="" size="40" disabled>
                        </td>
                    </tr>
                    <tr>
                        <th class="heading1">Ward Name</th>
                        <td><input class="form-control" type="text" id="ward_name" name="ward_name" value="" size="40" disabled></td>
                    </tr>
                    <tr>
                        <th class="heading1">Ward No</th>
                        <td><input class="form-control" type="text" id="ward_no" name="ward_no" value="" size="40" disabled></td>
                    </tr>


                    <tr>
                        <th class="heading1">Remark</th><td><input class="form-control" type="text" id="remark" name="remark" value="" size="40" disabled></td>
                    </tr>
                    <%-- <tr style="display:none">
                         <th class="heading1">Active</th>
                         <td>
                             <select name="active" id="active" disabled>
                                     <option>Y</option>
                                     <option>N</option>
                             </select>
                         </td>

                                            </tr> --%>
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
    </div>
    <%@include file="../layout/footer.jsp" %>
</div>
