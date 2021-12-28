<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@include file="../layout/header.jsp" %>
<script type="text/javascript" language="javascript">
    $(function () {

        $("#zone").autocomplete({

            source: function (request, response) {
                var random = $('#zone').val();
                $.ajax({
                    url: "cityLocationCont",
                    dataType: "json",
                    data: {action1: "getZone", q: random},
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
                $('#zone').val(ui.item.label); // display the selected text
                return false;
            }
        });


        $("#ward").autocomplete({

            source: function (request, response) {
                var zone = document.getElementById("zone").value;
                var random = $('#ward').val();

                $.ajax({
                    url: "cityLocationCont",
                    dataType: "json",
                    data: {action1: "getWardName",
                        action2: zone, q: random},
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
                $('#ward').val(ui.item.label); // display the selected text
                return false;
            }
        });


        $("#area").autocomplete({

            source: function (request, response) {
                var zone = document.getElementById("zone").value;
                var ward = document.getElementById("ward").value;
                var random = $('#area').val();

                $.ajax({
                    url: "cityLocationCont",
                    dataType: "json",
                    data: {action1: "getAreaName",
                        action2: ward,
                        action3: zone, q: random},
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
                $('#area').val(ui.item.label); // display the selected text
                return false;
            }
        });





    });


    function makeEditable(id) {
        document.getElementById("city_name").disabled = false;
        document.getElementById("zone").disabled = false;
        document.getElementById("ward").disabled = false;
        document.getElementById("area").disabled = false;
        document.getElementById("remark").disabled = false;
        document.getElementById("latitude").disabled = false;
        document.getElementById("longitude").disabled = false;
        document.getElementById("location_no").disabled = false;
        document.getElementById("save").disabled = false;

        if (id == 'new') {

            //document.getElementById("message").innerHTML = "";      // Remove message
            $("#message").html("");
            document.getElementById("city_location_id").value = "";
            document.getElementById("edit").disabled = true;
            document.getElementById("delete").disabled = true;
            document.getElementById("save_As").disabled = true;
            setDefaultColor(document.getElementById("noOfRowsTraversed").value, 9);
            document.getElementById("city_name").focus();

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
        } else
            document.getElementById("clickedButton").value = "Delete";
    }



    function setDefaultColor(noOfRowsTraversed, noOfColumns) {
        for (var i = 0; i < noOfRowsTraversed; i++) {
            for (var j = 1; j <= noOfColumns; j++) {
                document.getElementById("t1c" + (i * noOfColumns + j)).bgColor = "";     // set the default color.
            }
        }
    }

    function fillColumns(id)
    {

        var noOfRowsTraversed = document.getElementById("noOfRowsTraversed").value;
        var noOfColumns = 10;

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

        document.getElementById("city_location_id").value = document.getElementById(t1id + (lowerLimit + 0)).innerHTML;
        document.getElementById("zone").value = document.getElementById(t1id + (lowerLimit + 2)).innerHTML;
        document.getElementById("ward").value = document.getElementById(t1id + (lowerLimit + 3)).innerHTML;
        document.getElementById("area").value = document.getElementById(t1id + (lowerLimit + 4)).innerHTML;
        document.getElementById("city_name").value = document.getElementById(t1id + (lowerLimit + 5)).innerHTML;
        document.getElementById("location_no").value = document.getElementById(t1id + (lowerLimit + 6)).innerHTML;
        document.getElementById("remark").value = document.getElementById(t1id + (lowerLimit + 7)).innerHTML;
        document.getElementById("latitude").value = document.getElementById(t1id + (lowerLimit + 8)).innerHTML;
        document.getElementById("longitude").value = document.getElementById(t1id + (lowerLimit + 9)).innerHTML;

        for (var i = 0; i < noOfColumns; i++) {
            document.getElementById(t1id + (lowerLimit + i)).bgColor = "#d0dafd";        // set the background color of clicked row to yellow.
        }
        document.getElementById("edit").disabled = false;
        document.getElementById("delete").disabled = false;
        if (!document.getElementById("save").disabled)   // if save button is already enabled, then make edit, and delete button enabled too.
        {
            document.getElementById("save_As").disabled = true;
            document.getElementById("delete").disabled = false;
        }
        //document.getElementById("message").innerHTML = "";      // Remove message
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
    function displayMapList(id) {
        var queryString;
        var report_for = "status_type";
        var searchCityName = $("#searchCityName").val();
        var searchWardType = $("#searchWardType").val();
        var searchZone = $("#searchZone").val();
        var searchArea = $("#searchArea").val();

        if (id == "viewPdf")
            queryString = "task=generateMapReport";
        else
            queryString = "task=generateExcelReport";
        queryString = queryString + "&searchWardType=" + searchWardType + "&searchZone=" + searchZone + "&searchArea=" + searchArea + "&searchCityName=" + searchCityName;
        var url = "cityLocationCont?" + queryString;
        popupwin = openPopUp(url, "State Type Map Details", 500, 1000);
    }

    function openPopUp(url, window_name, popup_height, popup_width) {
        var popup_top_pos = (screen.availHeight / 2) - (popup_height / 2);
        var popup_left_pos = (screen.availWidth / 2) - (popup_width / 2);
        var window_features = "left=" + popup_left_pos + ", top=" + popup_top_pos + ", width=" + popup_width + ", height=" + popup_height + ", resizable=yes, scrollbars=yes, location=0, menubar=no, status=no, dependent=yes";

        return window.open(url, window_name, window_features);
    }

</script>



<div class="container-fluid"

     <div class="container  topbox" align="center">
        <h1 style="color: red;">City Location</h1>
    </div>

    <br>

    <div class="container">  
        <div class="row">

        </div>
        <div class="container">

            <form name="form0" method="get" action="cityLocationCont">
                <table class="table table-bordered table-hover table-responsive table-striped">
                    <tr>
                        <th> Zone Name</th>
                        <td><input type="text" class="form-control" name="searchZone" size="10" id="searchZone"  value="${searchZone}"/></td>
                        <th>Zone No</th>
                        <td><input type="text" class="form-control" name="searchZone_no" size="5" id="searchZone_no"  value="${searchZone_no}"/></td>
                        <th> Ward Name</th>
                        <td>  <input class="form-control" type="text" id="searchWardType" name="searchWardType" value="${searchWardType}" size="10" ></td>
                        <th> Ward No</th>
                        <td><input class="form-control" type="text" id="searchWardNo" name="searchWardNo" value="${searchWardNo}" size="5" ></td>
                    </tr>
                    <tr>
                        <th> Area Name</th>
                        <td><input  type="text" class="form-control" name="searchArea" size="10" id="searchArea" value="${searchArea}" size="10"></td>
                        <th> Area No</th>
                        <td><input  type="text" class="form-control" name="searchAreaNo" size="5" id="searchAreaNo" value="${searchAreaNo}" size="10"></td>
                        <th>City Location</th>
                        <td><input class="form-control" type="text" id="searchCityName" name="searchCityName" value="${searchCityName}" size="15" ></td>
                        <th>City Location No</th>
                        <td><input class="form-control" type="text" id="searchCityNo" name="searchCityNo" value="${searchCityNo}" size="15" ></td>
                    </tr>
                    <tr>

                        <td colspan="8" align="center"><input class="btn btn-primary" type="submit" name="task" id="searchIn" value="Search">
                            <input class="btn btn-success" type="submit" name="task" id="showAllRecords" value="Show All Records">
                            <input type="button" class="btn btn-danger" id="viewPdf" name="viewPdf" value="PDF" onclick="displayMapList(id)">
                            <input type="button" class="btn btn-info" id="viewExcel" name="viewExcel" value="Excel" onclick="displayMapList(id)"></td>
                    </tr>

                </table>
            </form>

        </div>
        <div class="row">
            <form name="form1" method="get" action="cityLocationCont">

                <table class="table table-bordered table-hover table-responsive table-striped">
                    <tr>
                        <th class="heading">S.No.</th>                                              
                        <th class="heading">Zone</th>
                        <th class="heading">Ward</th>
                        <th class="heading">Area</th>
                        <th class="heading">City Location</th>
                        <th class="heading">City Location No</th>
                        <th  class="heading">Remark</th>
                        <th class="heading">Latitude</th>
                        <th class="heading">Longitude</th>
                    </tr>
                    <!---below is the code to show all values on jsp page fetched from taxTypeList of TaxController     --->
                    <c:forEach var="cityLocationBean" items="${requestScope['cityLocationList']}"  varStatus="loopCounter">
                        <tr  class="${loopCounter.index % 2 == 0 ? 'even': 'odd'}" >
                            <%--  <td id="t1c${IDGenerator.uniqueID}" onclick="fillColumns(id)" align="center">
                                  <input type="hidden" id="status_type_id${loopCounter.count}" value="${statusTypeBean.status_type_id}">${lowerLimit - noOfRowsTraversed + loopCounter.count}
                                  <td id="t1c${IDGenerator.uniqueID}" onclick="fillColumns(id)" align="center">${lowerLimit - noOfRowsTraversed + loopCounter.count}</td>
                              </td> --%>

                            <td id="t1c${IDGenerator.uniqueID}" style="display:none" onclick="fillColumns(id)">${cityLocationBean.city_location_id}</td>
                            <td id="t1c${IDGenerator.uniqueID}" onclick="fillColumns(id)" align="center">${lowerLimit - noOfRowsTraversed + loopCounter.count}</td>
                            <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)" >${cityLocationBean.zone}</td>
                            <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)" >${cityLocationBean.ward}</td>
                            <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)" >${cityLocationBean.area}</td>
                            <td id="t1c${IDGenerator.uniqueID}" class="new_input" onclick="fillColumns(id)">${cityLocationBean.city}</td>
                            <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)">${cityLocationBean.location_no}</td>
                            <td id="t1c${IDGenerator.uniqueID}"   onclick="fillColumns(id)">${cityLocationBean.remark}</td>                                                   
                            <td id="t1c${IDGenerator.uniqueID}" onclick="fillColumns(id)">${cityLocationBean.latitude}</td>
                            <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)">${cityLocationBean.longitude}</td>
                        </tr>
                    </c:forEach>
                    <tr>
                        <td align='center' colspan="9">
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
                    <input  type="hidden" id="searchStatusType" name="searchStatusType" value="${searchStatusType}" >
                    <input  type="hidden" name="searchWardType" value="${searchWardType}">
                    <input  type="hidden" name="searchCityName" value="${searchCityName}">
                    <input  type="hidden" name="searchZone" value="${searchZone}">
                    <input  type="hidden" name="searchWardType" value="${searchWardNo}">
                    <input  type="hidden" name="searchCityName" value="${searchCityNo}">
                    <input  type="hidden" name="searchCityName" value="${searchCity}">
                    <input  type="hidden" name="searchCityName" value="${searchAreaNo}">
                    <input  type="hidden" name="searchZone" value="${searchZone_no}">
                </table>
            </form>
        </div>
        <div class="container">


            <form name="form2" method="get" action="cityLocationCont" >
                <table id="table2"  class="table table-bordered table-hover  table-striped">
                    <tr id="message">
                        <c:if test="${not empty message}">
                            <td colspan="2" bgcolor="${msgBgColor}"><b>Result: ${message}</b></td>
                        </c:if>
                    </tr>
                    <tr> <th class="heading1">Zone</th><td><input class="form-control" type="text" id="zone" name="zone" value="" size="20" disabled></td>
                        <th class="heading1">Ward</th><td><input class="form-control" type="text" id="ward" name="ward" value="" size="20" disabled></td></tr>
                    <tr>
                        <th class="heading1">Area</th><td><input class="form-control" type="text" id="area" name="area" value="" size="20" disabled></td>
                        <th class="heading1">City Location</th>
                        <td>
                            <input class="form-control" type="hidden" id="city_location_id" name="city_location_id" value="" >
                            <input class="form-control" type="text" id="city_name" name="city_name" value="" size="25" disabled>
                        </td>

                    </tr>
                    <tr>
                        <th class="heading1">Latitude</th><td><input class="form-control" type="text" id="latitude" name="latitude" value="" size="20" disabled/></td>
                        <th class="heading1">Longitude</th><td><input class="form-control" type="text" id="longitude" name="longitude" value="" size="20" disabled/></td>
                    </tr>
                    <tr>
                        <th class="heading1">Location No</th><td><input class="form-control" type="text" id="location_no" name="location_no" value="" size="20" disabled></td>
                        <th class="heading1">Remark</th><td><input class="form-control" type="text" id="remark" name="remark" value="" size="20" disabled></td>
                    </tr>
                    <tr>
                        <td align='center' colspan="4">
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
                    <input  type="hidden" name="searchWardType" value="${searchWardType}">
                    <input  type="hidden" name="searchCityName" value="${searchCityName}">
                    <input  type="hidden" name="searchZone" value="${searchZone}">
                    <input  type="hidden" name="searchWardType" value="${searchWardNo}">
                    <input  type="hidden" name="searchCityName" value="${searchCityNo}">
                    <input  type="hidden" name="searchCityName" value="${searchCity}">
                    <input  type="hidden" name="searchCityName" value="${searchAreaNo}">
                    <input  type="hidden" name="searchZone" value="${searchZone_no}">
                </table>
            </form>
        </div>

    </div>
    <%@include file="../layout/footer.jsp" %>

</div>
