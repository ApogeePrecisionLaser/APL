<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@include file="../layout/header.jsp" %>
<script type="text/javascript" language="javascript">

    $(function () {

        $("#searchTehsil").autocomplete({

            source: function (request, response) {
                var random = $('#searchTehsil').val();
                $.ajax({
                    url: "tehsilTypeCont",
                    dataType: "json",
                    data: {action1: "getTehsil", q: random},
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
                $('#searchTehsil').val(ui.item.label); // display the selected text
                return false;
            }
        });



        $("#district").autocomplete({
            source: function (request, response) {
                var random = $('#district').val();
                $.ajax({
                    url: "tehsilTypeCont",
                    dataType: "json",
                    data: {action1: "getDistrict", q: random},
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
                $('#district').val(ui.item.label); // display the selected text
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
        debugger;

        if (id == "new")
        {
            document.getElementById('edit').disabled = true;
            document.getElementById('delete').disabled = true;
            document.getElementById('saveAsNew').disabled = true;
            document.getElementById('tehsilName').disabled = false;
            document.getElementById('tehsilDescription').disabled = false;
            document.getElementById('district').disabled = false;
            document.getElementById('save').disabled = false;

            document.getElementById('message').innerHTML = "";

        }
        if (id == "edit")
        {

            document.getElementById('tehsilName').disabled = false;
            document.getElementById('district').disabled = false;
            document.getElementById('tehsilDescription').disabled = false;
            document.getElementById('save').disabled = false;
            document.getElementById('saveAsNew').disabled = false;
            document.getElementById('delete').disabled = false;
        }

    }
    function varification()
    {
        var click = document.getElementById('buttonClick').value;
        if (click == "delete")
        {
            var con = confirm("Do you want to delete this record")
            return con;
        }
        if (click == "saveAsNew")
        {
            var con = confirm("Do you want to save as this this record")
            return con;
        }
        if ($.trim(document.getElementById('divisionName').value) == "")
        {
            alert("Please enter division name")
            document.getElementById('divisionName').value = "";
            document.getElementById('divisionName').focus()
            return false;
        }
        if ($.trim(document.getElementById('districtName').value) == "")
        {
            alert("Please enter district name")
            document.getElementById('districtName').value = "";
            document.getElementById('districtName').focus()
            return false;
        }
        if ($.trim(document.getElementById('tehsilName').value) == "")
        {
            alert("Please enter tehsil name")
            document.getElementById('tehsilName').value = "";
            document.getElementById('tehsilName').focus()
            return false;
        }
//                if($.trim(document.getElementById('cityDescription').value)=="")
//                {
//                    alert("Please enter city description")
//                    document.getElementById('cityDescription').value="";
//                    document.getElementById('cityDescription').focus()
//                    return false;
//                }
        if (document.getElementById('tehsilId').value == "")
        {
            addRow();
            return false;
        }
        if (click == "save")
        {
            var con = confirm("Do you want to update this record")
            return con;
        }
        return true;
    }
    function addRow()
    {
        var table = document.getElementById('insetTable');
        var rowCount = table.rows.length;
        var row = table.insertRow(rowCount);

        var cell1 = row.insertCell(0);
        cell1.innerHTML = rowCount;
        var element1 = document.createElement("input");
        element1.name = "tehsil_id";
        element1.id = "tehsil_id" + rowCount;
        element1.type = "hidden";
        element1.value = 1;
        element1.size = 1;
        cell1.appendChild(element1);

        var element2 = document.createElement("input");
        element2.name = "tehsilCheckbox";
        element2.id = "tehsilCheckbox" + rowCount;
        element2.type = "checkbox";
        element2.checked = true;
        element2.setAttribute("onclick", 'singleCheck(' + rowCount + ')');
        cell1.appendChild(element2);
        ////////////////////////////////////
        var cell2 = row.insertCell(1);
        var element3 = document.createElement("input");
        element3.name = "divisionName";
        element3.id = "divisionName" + rowCount;
        element3.value = document.getElementById('divisionName').value;
        element3.size = 30;
        element3.className = "new_input";
        cell2.appendChild(element3);

        var cell3 = row.insertCell(2);
        var element4 = document.createElement("input");
        element4.name = "districtName";
        element4.id = "districtName" + rowCount;
        element4.value = document.getElementById('districtName').value;
        element4.size = 30;
        element4.className = "new_input";
        cell3.appendChild(element4);

        ///////////////////////////////////////////
        var cell4 = row.insertCell(3);
        var element5 = document.createElement("input");
        element5.name = "tehsilName";
        element5.id = "tehsilName" + rowCount;
        element5.value = document.getElementById('tehsilName').value;
        element5.size = 30;
        element5.className = "new_input";
        cell4.appendChild(element5);

        var cell5 = row.insertCell(4);
        var element6 = document.createElement("input");
        element6.name = "tehsilDescription";
        element6.id = "tehsilDescription" + rowCount;
        element6.value = document.getElementById('tehsilDescription').value;
        element6.size = 30;
        element6.className = "new_input";
        cell5.appendChild(element6);
        var height = (rowCount * 40) + 60;
        document.getElementById('autoCreatedTableDiv').style.visibility = "visible";
        document.getElementById("autoCreatedTableDiv").style.height = height + 'px';
    }
    function singleCheck(id)
    {
        if (document.getElementById('tehsilCheckbox' + id).checked == true)
            document.getElementById('tehsil_id' + id).value = 1;
        else
            document.getElementById('tehsil_id' + id).value = 0;
    }
    function deleteTable()
    {
        var table = document.getElementById('insetTable');
        var rowCount = table.rows.length;
        for (var i = 0; i < rowCount - 1; i++)
            table.deleteRow(1);
        document.getElementById('autoCreatedTableDiv').style.visibility = "hidden";

    }
    function check()
    {
        var value = document.getElementById('uncheckAll').value;
        var length = document.getElementsByName('tehsilCheckbox').length;
        if (value == "UncheckAll")
        {
            for (var checkbox = 0; checkbox < length; checkbox++)
            {
                document.getElementsByName('tehsilCheckbox')[checkbox].checked = false;
                document.getElementsByName('tehsil_id')[checkbox].value = 0;
            }
            document.getElementById('uncheckAll').value = "CheckAll";
        } else
        {
            for (var checkbox = 0; checkbox < length; checkbox++)
            {
                document.getElementsByName('tehsilCheckbox')[checkbox].checked = true;
                document.getElementsByName('tehsil_id')[checkbox].value = 1;
            }
            document.getElementById('uncheckAll').value = "UncheckAll";
        }
    }
    function findfill(id)
    {
        // setDefault();
        document.getElementById(id).bgColor = "#d0dafd";
        document.getElementById('edit').disabled = false;
        document.getElementById('delete').disabled = false;
        document.getElementById('save').disabled = true;
        document.getElementById('tehsilId').value = document.getElementById(id + '1').innerHTML;
        document.getElementById('tehsilName').value = document.getElementById(id + '3').innerHTML;

        document.getElementById('tehsilDescription').value = document.getElementById(id + '4').innerHTML;
        document.getElementById('district').value = document.getElementById(id + '5').innerHTML;
        document.getElementById('message').innerHTML = "";
    }
    function setDefault()
    {
        for (var i = 1; i <= document.getElementById('noOfRowsTraversed').value; i++)
            document.getElementById(i).bgColor = "";
    }
    function displayMapList(id)
    {
        var searchTehsil = document.getElementById('searchTehsil').value;
        var action;
        if (id == 'viewPdf')
            action = "task=generateMapReport&searchTehsil=" + searchTehsil;
        else
            action = "task=generateMapXlsReport&searchTehsil=" + searchTehsil;
        var url = "tehsilTypeCont?" + action;
        popup = popupWindow(url, "Tehsil_View_Report");
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
    <!--DWLayoutDefaultTable-->

    <div class="container-fluid">

        <div class="container  topbox" align="center">
            <h1 style="color: red;">Tehsil</h1>
        </div>

        <br>

        <%--   <td width="50" height="600" valign="top"><%@include file="/view/layout/Leftmenu.jsp" %></td></tr> --%>

        <DIV id="body" class="container">
            <div class="row">
                <!--                    <th>Tehsil</th>-->
            </div>
            <div class="container">
                <form action="tehsilTypeCont" method="post" class= "form-group container-fluid">
                    <table class="table table-bordered table-hover  table-striped">
                        <tr>       <th class>Enter Tehsil name:</th>
                            <td>   <input type="text" class="form-control"  name="searchTehsil" size="20" id="searchTehsil" value="${searchTehsil}"/></td>
                            <td>  <input class="btn btn-primary" type="submit" name="searchTehsilModel" value="Search"/></td>
                            <td>  <input class="btn btn-success" type="submit" name="task" value="Search All Records"/></td>
                            <td>  <input class="btn btn-danger" type="button" id="viewPdf" name="viewPdf" value="PDF" onclick="displayMapList(id);"/></td>
                            <td>  <input class="btn btn-info" type="button" id="viewXls" name="viewXls" value="Excel" onclick="displayMapList(id);"/></td>
                        </tr>      </table>
                </form>
            </div>

            <div class="container-fluid">
                <form action="tehsilTypeCont" method="post" class= "form-group container-fluid">
                    <input type="hidden" name="searchTehsil" id="searchTehsil" value="${searchTehsil}"/>
                    <table class="table table-bordered table-hover  table-striped ">
                        <tr>
                            <th class="heading">S.No.</th>
                            <th class="heading">Tehsil Name</th>

                            <th class="heading">Tehsil Description</th>
                            <th class="heading">District Name</th>

                        </tr>
                        <c:forEach var="tehsilBean" items="${requestScope['tehsilList']}" varStatus="loopcounter">
                            <tr id="${loopcounter.count}"onclick="findfill(id)">
                                <td id="${loopcounter.count}1" style="display: none">${tehsilBean.tehsilId}</td>
                                <td id="${loopcounter.count}2" align="center">${lowerLimit - noOfRowsTraversed + loopcounter.count}</td>
                                <td class="new_input tehsil_name" id="${loopcounter.count}3">${tehsilBean.tehsilName}</td>

                                <td class="new_input" id="${loopcounter.count}4">${tehsilBean.tehsilDescription}</td>
                                <td class="new_input" id="${loopcounter.count}5">${tehsilBean.districtName}</td>
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
                <form action="tehsilTypeCont" method="post"  name="form2" onsubmit="return varification()" class= "form-group container-fluid">
                    <table class="table table-bordered table-hover  table-striped">
                        <c:if test="${not empty message}">
                            <tr id="message">
                                <td colspan="2" bgcolor="${messageBGColor}"><b>Result: ${message}</b></td>
                            </tr>
                        </c:if>
                        <tr>
                            <td ><input style="display: none;" type="text" class="form-control" id="tehsilId" name="tehsilId" value="" readonly/><td>
                        </tr>
                        <tr>
                            <th >Tehsil Name</th>
                            <td><input type="text" class="form-control" id="tehsilName" name="tehsilName" size="40" disabled/></td>
                        </tr>

                        <tr>
                            <th class="heading1">Tehsil Description</th>
                            <td><input type="text" class="form-control" name="tehsilDescription" id="tehsilDescription" value="" size="40" disabled></td>
                        </tr>
                        <tr>
                            <th class="heading1">District Name</th>
                            <td><input type="text" class="form-control" name="district" id="district" value="" size="40" disabled></td>
                        </tr>
                        <input type="hidden" name="buttonClick" id="buttonClick" value=""/>
                        <tr>
                            <td align='center' colspan="4">
                                <input type="button" class="btn btn-primary" name="edit" id="edit" value="Edit" onclick="makeEditable(id)" disabled>
                                <input type="submit" class="btn btn-success" name="task" id="save" value="Save" onclick="setStatus(id)" disabled>
                                <input type="submit" class="btn btn-info" name="task" id="saveAsNew" value="Save AS New" onclick="setStatus(id)" disabled>
                                <input type="reset" class=" btn btn-warning" name="new" id="new" value="New" onclick="makeEditable(id)" >
                                <input type="submit" class="btn btn-success" name="task" id="delete" value="Delete" onclick="setStatus(id)" disabled>
                            </td>
                        </tr>

                        <%-- These hidden fields "lowerLimit", "noOfRowsTraversed", and "clickedButton" belong to form2 of table2. --%>
                        <input type="hidden" name="lowerLimit" value="${lowerLimit}">
                        <input type="hidden" name="noOfRowsTraversed" value="${noOfRowsTraversed}">
                        <input type="hidden" id="clickedButton" value="">

                    </table>
                </form>
            </div>

        </DIV>
    </div>
    <%@include file="../layout/footer.jsp" %>