<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@include file="../layout/header.jsp" %>
<script type="text/javascript">
    $(function () {

        $("#cityName").autocomplete({

            source: function (request, response) {
                var random = $('#cityName').val();
                $.ajax({
                    url: "zoneTypeCont",
                    dataType: "json",
                    data: {action1: "getCity", q: random},
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
                $('#cityName').val(ui.item.label); // display the selected text
                return false;
            }
        });


        $("#searchZone").autocomplete({

            source: function (request, response) {
                var random = $('#searchZone').val();

                $.ajax({
                    url: "zoneTypeCont",
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
                $('#searchZone').val(ui.item.label); // display the selected text
                return false;
            }
        });


        $("#searchZone_no").autocomplete({

            source: function (request, response) {
                var random = $('#searchZone_no').val();

                $.ajax({
                    url: "zoneTypeCont",
                    dataType: "json",
                    data: {action1: "getZone_no", q: random},
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
                $('#searchZone_no').val(ui.item.label); // display the selected text
                return false;
            }
        });


    });

    function setButton(id)
    {
        if (id == "delete")
            document.getElementById('buttonClick').value = "delete";
        if (id == "saveAsNew")
            document.getElementById('buttonClick').value = "saveAsNew";
        if (id == "save")
            document.getElementById('buttonClick').value = "save";
    }
    function makeEditable(id)
    {
        if (id == "new")
        {
            document.getElementById('edit').disabled = true;
            document.getElementById('delete').disabled = true;
            document.getElementById('saveAsNew').disabled = true;
            document.getElementById('zoneName').disabled = false;
            document.getElementById('zoneDescription').disabled = false;
            document.getElementById('zone_no').disabled = false;
            document.getElementById('cityName').disabled = false;
            document.getElementById('save').disabled = false;
            document.getElementById('zoneName').focus();
            document.getElementById('message').innerHTML = "";

        }
        if (id == "edit")
        {
            document.getElementById('zoneName').disabled = false;
            document.getElementById('zone_no').disabled = false;
            document.getElementById('zoneDescription').disabled = false;
            document.getElementById('cityName').disabled = false;
            document.getElementById('save').disabled = false;
            document.getElementById('saveAsNew').disabled = false;
            document.getElementById('delete').disabled = false;
        }

    }
    function varification()
    {
        var click = document.getElementById('buttonClick').value;

        if ($.trim(document.getElementById('zoneName').value) == "")
        {
            document.getElementById('zoneName').value = "";
            alert("Please enter Zone name")
            document.getElementById('zoneName').focus()
            return false;
        }
//                if($.trim(document.getElementById('zoneDescription').value)=="")
//                {
//                    alert("Please enter Zone description")
//                      document.getElementById('zoneDescription').value="";
//                    document.getElementById('zoneDescription').focus()
//                    return false;
//                }
        if (document.getElementById('zoneId').value == "")
        {
            addRow();
            return false;
        }
        if (click == "saveAsNew")
        {
            var con = confirm("Do you want to save as this this record")
            return con;
        } else if (click == "save")
        {
            var con = confirm("Do you want to update this record")
            return con;
        } else if (click == "delete")
        {
            var con = confirm("Do you want to delete this record")
            return con;
        }

        return true;
    }
    function addRow()
    {

        var table = document.getElementById('insertTable');
        var rowCount = table.rows.length;
        var row = table.insertRow(rowCount);

        var cell1 = row.insertCell(0);
        cell1.innerHTML = rowCount;
        var element1 = document.createElement("input");
        element1.name = "zone_id";
        element1.id = "zone_id" + rowCount;
        element1.type = "hidden";
        element1.value = 1;
        element1.size = 1;
        cell1.appendChild(element1);

        var element2 = document.createElement("input");
        element2.name = "zoneCheckbox";
        element2.id = "zoneCheckbox" + rowCount;
        element2.type = "checkbox";
        element2.checked = true;
        element2.setAttribute("onclick", 'singleCheck(' + rowCount + ')');
        cell1.appendChild(element2);

        var cell2 = row.insertCell(1);
        var element3 = document.createElement("input");
        element3.name = "CityName";
        element3.id = "CityName" + rowCount;
        element3.value = document.getElementById('CityName').value;
        element3.size = 30;
        element3.className = "new_input";
        cell2.appendChild(element3);

        var cell3 = row.insertCell(2);
        var element4 = document.createElement("input");
        element4.name = "zoneName";
        element4.id = "zoneName" + rowCount;
        element4.value = document.getElementById('zoneName').value;
        element4.size = 30;
        element4.className = "new_input";
        cell3.appendChild(element4);


        var cell4 = row.insertCell(3);
        var element5 = document.createElement("input");
        element5.name = "zoneDescription";
        element5.id = "zoneDescription" + rowCount;
        element5.value = document.getElementById('zoneDescription').value;
        element5.size = 30;
        element5.className = "new_input";
        cell4.appendChild(element5);


        var height = (rowCount * 40) + 60;
        document.getElementById('autoCreatedTableDiv').style.visibility = "visible";
        document.getElementById("autoCreatedTableDiv").style.height = height + 'px';
    }
    function singleCheck(id)
    {
        if (document.getElementById('zoneCheckbox' + id).checked == true)
            document.getElementById('zone_id' + id).value = 1;
        else
            document.getElementById('zone_id' + id).value = 0;
    }
    function deleteTable()
    {
        var table = document.getElementById('insertTable');
        var rowCount = table.rows.length;
        for (var i = 0; i < rowCount - 1; i++)
            table.deleteRow(1);
        document.getElementById('autoCreatedTableDiv').style.visibility = "hidden";

    }
    function check()
    {
        var value = document.getElementById('uncheckAll').value;
        var length = document.getElementsByName('zoneCheckbox').length;
        if (value == "UncheckAll")
        {
            for (var checkbox = 0; checkbox < length; checkbox++)
            {
                document.getElementsByName('zoneCheckbox')[checkbox].checked = false;
                document.getElementsByName('zone_id')[checkbox].value = 0;
            }
            document.getElementById('uncheckAll').value = "CheckAll";
        } else
        {
            for (var checkbox = 0; checkbox < length; checkbox++)
            {
                document.getElementsByName('zoneCheckbox')[checkbox].checked = true;
                document.getElementsByName('zone_id')[checkbox].value = 1;
            }
            document.getElementById('uncheckAll').value = "UncheckAll";
        }
    }
    function findfill(id)
    {
        // setDefault();
        document.getElementById(id).bgColor = "#d0dafd";
        document.getElementById('edit').disabled = false;
        document.getElementById('save').disabled = true;
        document.getElementById('zoneId').value = document.getElementById(id + '1').innerHTML;
        document.getElementById('zoneName').value = document.getElementById(id + '3').innerHTML;
        document.getElementById('zoneDescription').value = document.getElementById(id + '4').innerHTML;
        document.getElementById('zone_no').value = document.getElementById(id + '5').innerHTML;
        document.getElementById('cityName').value = document.getElementById(id + '6').innerHTML;
        document.getElementById('message').innerHTML = "";
    }
    function setDefault()
    {
        for (var i = 1; i <= document.getElementById('noOfRowsTraversed').value; i++)
            document.getElementById(i).bgColor = "";
    }
    function displayMapList(id)
    {
        var searchZone = document.getElementById('searchZone').value;
        var action;
        if (id == 'viewPdf')
            action = "task=generateMapReport&searchZone=" + searchZone;
        else
            action = "task=generateXlsMapReport&searchZone=" + searchZone;
        var url = "zoneTypeCont?" + action;
        popup = popupWindow(url, "Zone new Report");

    }
    function popupWindow(url, windowName)
    {
        var windowfeatures = "left=50, top=50, width=1000, height=500, resizable=no, scrollbars=yes, location=0, menubar=no, status=no, dependent=yes";
        return window.open(url, windowName, windowfeatures)
    }
</script>

<style>


</style>

</head>
<body>


    <div class="container-fluid">
        <div class="container  topbox" align="center">
            <h1 style="color: red;">Zone</h1>
        </div>

        <br>


        <div class="container">
            <!--                   <div class="row">
</div>-->
            <div class="container">

                <form action="zoneTypeCont" method="post">
                    <table class="table table-bordered table-hover  table-striped"><tr>
                            <th class="heading1"> Zone Name:</th>
                            <td><input type="text" class="form-control" name="searchZone" size="20" id="searchZone"  value="${searchZone}"/></td>
                            <th class="heading1"> Zone No:</th>
                            <td><input type="text" class="form-control" name="searchZone_no" size="20" id="searchZone_no"  value="${searchZone_no}"/></td>
                            <td><input class="btn btn-primary" type="submit" name="searchZoneModel" value="Search"/></td>
                            <td> <input class="btn btn-success" type="submit" name="task" value="Search All Records"/></td>
                            <td> <input class="btn btn-danger" type="button" id="viewPdf" name="viewPdf" value="PDF" onclick="displayMapList(id);"/></td>
                            <td><input class="btn btn-info" type="button" id="viewXls" name="viewXls" value="Excel" onclick="displayMapList(id);"/></td>
                        </tr>    </table>
                </form>

            </div>

            <div class="container">
                <form action="zoneTypeCont" method="post">
                    <input type="hidden" name="searchZone" id="searchZone" value="${searchZone}"/>
                    <input type="hidden" name="searchZone_no" id="searchZone_no" value="${searchZone_no}"/>
                    <table class="table table-bordered table-hover  table-striped">
                        <tr>
                            <th class="heading">S.No.</th>
                            <th class="heading">Zone Name</th>
                            <th class="heading">Zone Description</th>
                            <th class="heading">Zone No</th>
                            <th class="heading">City</th>

                        </tr>
                        <c:forEach var="zoneBean" items="${requestScope['zoneList']}" varStatus="loopcounter">
                            <tr id="${loopcounter.count}"onclick="findfill(id)">
                                <td id="${loopcounter.count}1" style="display: none">${zoneBean.zoneId}</td>
                                <td id="${loopcounter.count}2" align="center">${lowerLimit - noOfRowsTraversed + loopcounter.count}</td>
                                <td class="new_input" id="${loopcounter.count}3">${zoneBean.zoneName}</td>
                                <td class="new_input" id="${loopcounter.count}4">${zoneBean.zoneDescription}</td>
                                <td class="new_input" id="${loopcounter.count}5">${zoneBean.zone_no}</td>
                                <td class="new_input" id="${loopcounter.count}6">${zoneBean.cityName}</td>
                            </tr>
                        </c:forEach>
                        <tr>
                            <td align='center' colspan="6">
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
                    </table>
                </form>
            </div>
            <div class="container">

                <form name="form2" method="POST"  action="zoneTypeCont" onsubmit="return verify()">
                    <table class="table table-bordered table-hover  table-striped">
                        <tr id="message">
                            <c:if test="${not empty message}">
                                <td colspan="2" bgcolor="${msgBgColor}"><b>Result: ${message}</b></td>
                            </c:if>
                        </tr>
                        <tr>
                            <td><input style="display: none;" type="text" class="form-control" id="zoneId" name="zoneId" value="" readonly/><td>
                        </tr>
                        <tr>
                            <td><input style="display: none;" type="text" class="form-control" id="cityId" name="zoneId" value="" readonly/><td>
                        </tr>

                        <tr>
                            <th class="heading1">City Name</th><td><input type="text" class="form-control" name="cityName" id="cityName" value="" size="40"></td>
                        </tr>


                        <tr>
                            <th class="heading1">Zone Name</th><td><input type="text" class="form-control" name="zoneName" id="zoneName" value="" size="40"></td>
                        </tr>
                        <tr>
                            <th class="heading1" width="210px;">Zone No</th><td><input type="text" class="form-control" name="zone_no" id="zone_no" value="" size="40" ></td>
                        </tr>
                        <tr>
                            <th class="heading1">Zone Description</th><td><input type="text" class="form-control" name="zoneDescription" id="zoneDescription" value="" size="40" ></td>
                        </tr>
                        <input type="hidden" name="buttonClick" id="buttonClick" value=""/>
                        <tr>
                            <td align='center' colspan="2">
                                <input type="button" class="btn btn-primary" name="edit" id="edit" value="Edit" onclick="makeEditable(id)">
                                <input type="submit" class="btn btn-success" name="task" id="save" value="Save" onclick="setStatus(id)" >
                                <input type="submit" class="btn btn-info" name="task" id="save_As" value="Save AS New" onclick="setStatus(id)">
                                <input type="reset" class=" btn btn-warning" name="new" id="new" value="New" onclick="makeEditable(id)" >
                                <input type="submit" class="btn btn-success" name="task" id="delete" value="Delete" onclick="setStatus(id)" >
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


    </div>

    <%@include file="../layout/footer.jsp" %>
