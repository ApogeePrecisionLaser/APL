<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@include file="../layout/header.jsp" %>
<script type="text/javascript" language="javascript">
    $(function () {

        $("#stateName").autocomplete({

            source: function (request, response) {

                var random = $('#stateName').val();
                $.ajax({
                    url: "divisionTypeCont",
                    dataType: "json",
                    data: {action1: "getStateName", q: random},
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
                $('#stateName').val(ui.item.label); // display the selected text
                return false;
            }
        });

        $("#searchDivision").autocomplete({

            source: function (request, response) {
                var random = $('#searchDivision').val();
                $.ajax({
                    url: "divisionTypeCont",
                    dataType: "json",
                    data: {action1: "getDivision", q: random},
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
                $('#searchDivision').val(ui.item.label); // display the selected text
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
        document.getElementById('divisionName').disabled = false;
        document.getElementById('divisionDescription').disabled = false;
        document.getElementById('delete').disabled = false;
        document.getElementById('saveAsNew').disabled = false;
        document.getElementById('save').disabled = false;
        if (id == "new")
        {
            document.getElementById('edit').disabled = true;
            document.getElementById('delete').disabled = true;
            document.getElementById('saveAsNew').disabled = true;

            document.getElementById('stateName').disabled = false;
            document.getElementById('utName').disabled = false;
            document.getElementById('divisionName').disabled = false;
            document.getElementById('divisionDescription').disabled = false;
            document.getElementById('save').disabled = false;

            document.getElementById('message').innerHTML = "";

        }
        if (id == "edit")
        {

            document.getElementById('stateName').disabled = false;
            document.getElementById('utName').disabled = false;
            document.getElementById('divisionName').disabled = false;
            document.getElementById('divisionDescription').disabled = false;
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
        if (document.getElementById('zoneName').value == "")
        {
            alert("Please enter Zone name")
            document.getElementById('zoneName').focus()
            return false;
        }
        if ((document.getElementById('stateName').value == "") && (document.getElementById('utName').value = ""))
        {
            alert("Please enter State or UT name")
            document.getElementById('stateutName').focus()
            return false;
        }
        if (document.getElementById('divisionName').value == "")
        {
            alert("Please enter Division name")
            document.getElementById('divisionName').focus()
            return false;
        }
//                if(document.getElementById('divisionDescription').value=="")
//                {
//                    alert("Please enter Division description")
//                    document.getElementById('divisionDescription').focus()
//                    return false;
//                }
        if (document.getElementById('divisionId').value == "")
        {
            addRow();
            return false;
        }
        if (click == "saveAsNew")
        {
            var con = confirm("Do you want to save as this this record")
            return con;
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

        var table = document.getElementById('insertTable');
        var rowCount = table.rows.length;
        var row = table.insertRow(rowCount);

        var cell1 = row.insertCell(0);
        cell1.innerHTML = rowCount;
        var element1 = document.createElement("input");
        element1.name = "division_id";
        element1.id = "division_id" + rowCount;
        element1.type = "hidden";
        element1.value = 1;
        element1.size = 1;
        cell1.appendChild(element1);

        var element2 = document.createElement("input");
        element2.name = "divisionCheckbox";
        element2.id = "divisionCheckbox" + rowCount;
        element2.type = "checkbox";
        element2.checked = true;
        element2.setAttribute("onclick", 'singleCheck(' + rowCount + ')');
        cell1.appendChild(element2);

        var cell2 = row.insertCell(1);
        var element3 = document.createElement("input");
        element3.name = "zoneName";
        element3.id = "zoneName" + rowCount;
        element3.value = document.getElementById('zoneName').value;
        element3.size = 30;
        cell2.appendChild(element3);

        var cell3 = row.insertCell(2);
        var element4 = document.createElement("input");
        element4.name = "stateName";
        element4.id = "stateName" + rowCount;
        element4.value = document.getElementById('stateName').value;
        element4.size = 30;
        element4.className = "new_input";
        cell3.appendChild(element4);

        var cell4 = row.insertCell(3);
        var element5 = document.createElement("input");
        element5.name = "utName";
        element5.id = "utName" + rowCount;
        element5.value = document.getElementById('utName').value;
        element5.size = 30;
        element5.className = "new_input";
        cell4.appendChild(element5);


        var cell5 = row.insertCell(4);
        var element6 = document.createElement("input");
        element6.name = "divisionName";
        element6.id = "divisionName" + rowCount;
        element6.value = document.getElementById('divisionName').value;
        element6.size = 30;
        element6.className = "new_input";
        cell5.appendChild(element6);

        var cell6 = row.insertCell(5);
        var element7 = document.createElement("input");
        element7.name = "divisionDescription";
        element7.id = "divisionDescription" + rowCount;
        element7.value = document.getElementById('divisionDescription').value;
        element7.size = 30;
        element7.className = "new_input";
        cell6.appendChild(element7);

        var height = (rowCount * 40) + 60;
        document.getElementById('autoCreatedTableDiv').style.visibility = "visible";
        document.getElementById("autoCreatedTableDiv").style.height = height + 'px';
    }
    function singleCheck(id)
    {
        if (document.getElementById('divisionCheckbox' + id).checked == true)
            document.getElementById('division_id' + id).value = 1;
        else
            document.getElementById('division_id' + id).value = 0;
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
        var length = document.getElementsByName('divisionCheckbox').length;
        if (value == "UncheckAll")
        {
            for (var checkbox = 0; checkbox < length; checkbox++)
            {
                document.getElementsByName('divisionCheckbox')[checkbox].checked = false;
                document.getElementsByName('division_id')[checkbox].value = 0;
            }
            document.getElementById('uncheckAll').value = "CheckAll";
        } else
        {
            for (var checkbox = 0; checkbox < length; checkbox++)
            {
                document.getElementsByName('divisionCheckbox')[checkbox].checked = true;
                document.getElementsByName('division_id')[checkbox].value = 1;
            }
            document.getElementById('uncheckAll').value = "UncheckAll";
        }
    }
    function findfill(id)
    {
        setDefault();
        document.getElementById(id).bgColor = "#d0dafd";
        document.getElementById('edit').disabled = false;
        document.getElementById('delete').disabled = false;
        document.getElementById('save').disabled = true;
        document.getElementById('divisionId').value = document.getElementById(id + '1').innerHTML;
        document.getElementById('divisionName').value = document.getElementById(id + '3').innerHTML;
        document.getElementById('divisionDescription').value = document.getElementById(id + '4').innerHTML;
        document.getElementById('stateName').value = document.getElementById(id + '5').innerHTML;

        document.getElementById('message').innerHTML = "";
    }
    function setDefault()
    {
        for (var i = 1; i <= document.getElementById('noOfRowsTraversed').value; i++)
            document.getElementById(i).bgColor = "";
    }
    function displayMapList(id)
    {
        var searchDivision = document.getElementById('searchDivision').value;
        if (id == 'viewPdf')
            action = "task=generateMapReport&searchDivision=" + searchDivision;
        else
            action = "task=generateXlsMapReport&searchDivision=" + searchDivision;
        var url = "divisionTypeCont?" + action;
        popup = popupWindow(url, "Division_View_Report");
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
        <table align="center" cellpadding="0" cellspacing="0"  class="main">            <!--DWLayoutDefaultTable-->
            <div class="container  topbox" align="center">
                <h1 style="color: red;">Division</h1>
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
                    <div class="container">

                        <form action="divisionTypeCont" method="post">

                            <table class="table table-bordered table-hover  table-striped">
                                <tr>
                                    <th> Enter Division name:</th>
                                    <td> <input type="text" class="form-control" name="searchDivision" size="30" id="searchDivision" value="${searchDivision}"/></td>
                                    <td><input class="btn btn-primary" type="submit" name="searchDivisionModel" value="Search"/></td>
                                    <td><input class="btn btn-success" type="submit" name="task" value="Search All Records"/></td>
                                    <td><input class="btn btn-warning"  type="button" id="viewPdf" name="viewPdf" value="PDF" onclick="displayMapList(id);"/></td>
                                    <td><input class="btn btn-info" type="button" id="viewXls" name="viewExcell" value="Excel" onclick="displayMapList(id);"/></td>
                                </tr>
                            </table>
                        </form>
                    </div>
                </td>
            </tr>
            <tr>
                <td align="center">
                    <div class="container" >
                        <form action="divisionTypeCont" method="post" align="center">
                            <input type="hidden" name="searchDivision" id="searchDivision" value="${searchDivision}"/>
                            <table class="table table-bordered table-hover  table-striped" align="center">
                                <tr>
                                    <th class="heading">S.No.</th>
                                    <th class="heading" id="changeDivisionFont">Division Name</th>
                                    <th class="heading" id="changeRemarkFont">Division Description</th>
                                    <th class="heading" id="changeStateFont">State & UT Name</th>

                                </tr>
                                <c:forEach var="divisionBean" items="${requestScope['divisionList']}" varStatus="loopcounter">
                                    <tr id="${loopcounter.count}"onclick="findfill(id)">
                                        <td id="${loopcounter.count}1" style="display: none">${divisionBean.divisionId}</td>
                                        <td id="${loopcounter.count}2" align="center">${lowerLimit - noOfRowsTraversed + loopcounter.count}</td>
                                        <td id="${loopcounter.count}3" class="new_input division_name">${divisionBean.divisionName}</td>
                                        <td id="${loopcounter.count}4" class="new_input remark">${divisionBean.divisionDescription}</td>
                                        <td id="${loopcounter.count}5" class="new_input state_name">${divisionBean.stateName}</td>

                                    </tr>
                                </c:forEach>
                                <tr>
                                    <td align='center' colspan="6">
                                        <c:choose>
                                            <c:when test="${showFirst eq 'false'}">
                                                <input class="btn btn-primary" type='submit' name='task' value='First' >
                                            </c:when>
                                            <c:otherwise>
                                                <input class="btn btn-primary" type='submit' name='task' value='First'>
                                            </c:otherwise>
                                        </c:choose>
                                        <c:choose>
                                            <c:when test="${showPrevious == 'false'}">
                                                <input class="btn btn-success" type='submit' name='task' value='Previous' >
                                            </c:when>
                                            <c:otherwise>
                                                <input class="btn btn-success" type='submit' name='task' value='Previous'>
                                            </c:otherwise>
                                        </c:choose>
                                        <c:choose>
                                            <c:when test="${showNext eq 'false'}">
                                                <input class="btn btn-warning" type='submit' name='task' value='Next' >
                                            </c:when>
                                            <c:otherwise>
                                                <input class="btn btn-warning" type='submit' name='task' value='Next'>
                                            </c:otherwise>
                                        </c:choose>
                                        <c:choose>
                                            <c:when test="${showLast == 'false'}">
                                                <input class="btn btn-info" type='submit' name='task' value='Last' >
                                            </c:when>
                                            <c:otherwise>
                                                <input class="btn btn-info" type='submit' name='task' value='Last'>
                                            </c:otherwise>
                                        </c:choose>
                                    </td>
                                </tr>
                                <input type="hidden" name="lowerLimit" value="${lowerLimit}">
                                <input type="hidden" id="noOfRowsTraversed" name="noOfRowsTraversed" value="${noOfRowsTraversed}">
                            </table>
                        </form>  
                    </div>
                </td>
            </tr>
            <!--          <div class="row">
                             <div id="autoCreatedTableDiv" STYLE="overflow: auto; visibility: hidden;height: 0px" align="center">
                            <form method="post" action="divisionTypeCont">
                                <table id="insertTable" border="1" class="content" border="1"  align="center">
                                    <tr>
                                        <th width="100" class="heading1">S. No</th>
                                        <th class="heading1" width="100">Zone Name</th>
                                        <th class="heading1" width="100">State Name</th>
                                        <th class="heading1" width="100">UT Name</th>
                                        <th class="heading1" width="100">Division Name</th>
                                        <th class="heading1" width="100">Division Description</th>
                                    </tr>
                                </table>
                                <table class="reference" border="1" align="center">
                                    <tr>
                                        <td><input  class="button"  id="uncheckAll" name="uncheckAll" type="button" value="UncheckAll" onclick="check()"/>
                                            <input  class="button"  type="submit"  id="saveAllRecords" name="task" value="Save all records"/>
                                            <input  class="button"  type="submit" name="cancel" value="Cancel" onclick="deleteTable()"/>
                                        </td>
                                    </tr>
                                </table>
                            </form>
                        </div>
           
            </div>-->
            <tr>
                <td align="center">
                    <div class="container">
                        <form action="divisionTypeCont" method="post" name="form2" onsubmit="return varification()">
                            <table class="table table-bordered table-hover  table-striped">                   
                                <c:if test="${not empty message}">
                                    <tr id="message">
                                        <td class="heading1" colspan="2" bgcolor="${messageBGColor}"><b>Result: ${message}</b></td>
                                    </tr>
                                </c:if>
                                <tr>
                                    <td><input style="display: none;" type="text" id="divisionId" name="divisionId" value="" readonly/><td>
                                </tr>

                                <tr>
                                    <td class="heading1">State Name</td><td><input type="text" class="form-control" name="stateName" id="stateName" value="" size="40" disabled></td>
                                </tr>
                                <!--                            <tr>
                                                                <td class="heading1">UT Name</td><td><input type="text" class="form-control" name="utName" id="utName" value="" size="40" disabled></td>
                                                            </tr>-->
                                <tr>
                                    <td class="heading1" width="150">Division Name</td><td><input type="text" class="form-control" id="divisionName" name="divisionName" size="40" disabled/></td>
                                </tr>
                                <tr>
                                    <td class="heading1">Division Description</td><td><input type="text" class="form-control" name="divisionDescription" id="divisionDescription" value="" size="40" disabled></td>
                                </tr>
                                <input type="hidden" name="buttonClick" id="buttonClick" value=""/>
                                <tr>
                                    <td class="heading1" colspan="2" align="center" style="text-align: center;">
                                        <input class="btn btn-primary" type="button" name="edit" id="edit" value="Edit" onclick="makeEditable(id)" disabled/>
                                        <input class="btn btn-success"  type="submit" name="task" id="save" value="Save" onclick="setButton(id)" disabled/>
                                        <input class="btn btn-info" type="submit" name="task" id="saveAsNew" value="Save As New" onclick="setButton(id)" disabled/>
                                        <input class=" btn btn-warning"  type="reset" name="new" value="New" id="new" onclick="makeEditable(id)"/>                            
                                        <input class="btn btn-success"  type="submit" name="task" value="Delete" id="delete" onclick="setButton(id)" disabled/>
                                    </td>
                                </tr>
                            </table>
                        </form>         

                    </div>
                </td>
            </tr>



        </table>   
    </div>
    <%@include file="../layout/footer.jsp" %>
