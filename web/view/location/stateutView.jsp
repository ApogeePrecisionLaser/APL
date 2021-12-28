<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@include file="../layout/header.jsp" %>
<script type="text/javascript" language="javascript">


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
            document.getElementById('countryName').disabled = false;
            document.getElementById('zoneName').disabled = false;
            document.getElementById('stateName').disabled = false;
            document.getElementById('utName').disabled = false;
            document.getElementById('stateutDescription').disabled = false;
            document.getElementById('save').disabled = false;
            document.getElementById('countryName').focus()
            document.getElementById('message').innerHTML = "";
        }
        if (id == "edit")
        {
            document.getElementById('countryName').disabled = false;
            document.getElementById('zoneName').disabled = false;
            document.getElementById('stateName').disabled = false;
            document.getElementById('utName').disabled = false;
            document.getElementById('stateutDescription').disabled = false;
            document.getElementById('save').disabled = false;
            document.getElementById('saveAsNew').disabled = false;
            document.getElementById('delete').disabled = false;
        }

    }


    function varification()
    {
        var click = document.getElementById('buttonClick').value;
        var zoneName = $.trim(document.getElementById('zoneName').value);
        var stateName = $.trim(document.getElementById('stateName').value);
        var utName = $.trim(document.getElementById('utName ').value);
        var stateutDescription = $.trim(document.getElementById('stateutDescription').value);
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

        if (zoneName == "")
        {
            alert("Please enter Zone name")
            document.getElementById('zoneName').value = "";
            document.getElementById('zoneName').focus()
            return false;
        }
        if ((stateName == "") && (utName == ""))
        {
            alert("Please enter State or UT Name")
            document.getElementById('stateName').value = "";
            document.getElementById('utName').value = "";
            document.getElementById('stateName').focus()
            return  false;
        }                      /*if(stateutDescription=="")
         {
         alert("Please enter Stateut description")
         document.getElementById('stateutDescription').value = "";
         document.getElementById('stateutDescription').focus()
         return false;
         }*/
        if (document.getElementById('stateutId').value == "")
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
        var language = document.getElementById("language");
        var table = document.getElementById('insertTable');
        var rowCount = table.rows.length;
        var row = table.insertRow(rowCount);
        var cell1 = row.insertCell(0);
        cell1.innerHTML = rowCount;
        var element1 = document.createElement("input");
        element1.name = "stateut_id";
        element1.id = "stateut_id" + rowCount;
        element1.type = "hidden";
        element1.value = 1;
        element1.size = 1;
        cell1.appendChild(element1);
        var element2 = document.createElement("input");
        element2.name = "stateutCheckbox";
        element2.id = "stateutCheckbox" + rowCount;
        element2.type = "checkbox";
        element2.checked = true;
        element2.setAttribute("onclick", 'singleCheck(' + rowCount + ')');
        cell1.appendChild(element2);
        var cell2 = row.insertCell(1);
        var element3 = document.createElement("input");
        element3.name = "countryName";
        element3.id = "countryName" + rowCount;
        element3.value = document.getElementById('countryName').value;
        element3.size = 30;
        cell2.appendChild(element3);
        var cell3 = row.insertCell(2);
        var element4 = document.createElement("input");
        element4.name = "zoneName";
        element4.id = "zoneName" + rowCount;
        element4.value = document.getElementById('zoneName').value;
        element4.size = 30;
        cell3.appendChild(element4);
        var cell4 = row.insertCell(3);
        var element5 = document.createElement("input");
        element5.name = "stateName";
        element5.id = "stateName" + rowCount;
        element5.value = document.getElementById('stateName').value;
        element5.size = 30;
        element5.className = "new_input";
        cell4.appendChild(element5);
        var cell5 = row.insertCell(4);
        var element6 = document.createElement("input");
        element6.name = "utName";
        element6.id = "utName" + rowCount;
        element6.value = document.getElementById(' u tName').value;
        element6.size = 30;
        element6.className = "new_input";
        cell5.appendChild(element6);
        var cell6 = row.insertCell(5);
        var element7 = document.createElement("input");
        element7.name = "stateutDescription";
        element7.id = "stateutDescription" + rowCount;
        element7.value = document.getElementById('stateutDescription').value;
        element7.size = 30;
        element7.className = "new_input";
        cell6.appendChild(element7);
        var height = (rowCount * 40) + 60;
        document.getElementById('autoCreatedTableDiv').style.visibility = "visible";
        document.getElementById("autoCreatedTableDiv").style.height = height + 'px';
    }

    function singleCheck(id)
    {
        if (document.getElementById('stateutCheckbox' + id).checked == true)
            document.getElementById('stateut_id' + id).value = 1;
        else
            document.getElementById('stateut_id' + id).value = 0;
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
        var length = document.getElementsByName('stateutCheckbox').length;
        if (value == "UncheckAll")
        {
            for (var checkbox = 0; checkbox < length; checkbox++)
            {
                document.getElementsByName('stateutCheckbox')[checkbox].checked = false;
                document.getElementsByName('stateut_id')[checkbox].value = 0;
            }
            document.getElementById('uncheckAll').value = "CheckAll";
        } else
        {
            for (var checkbox = 0; checkbox < length; checkbox++)
            {
                document.getElementsByName('stateutCheckbox')[checkbox].checked = true;
                document.getElementsByName('stateut_id')[checkbox].value = 1;
            }
            document.getElementById('uncheckAll').value = "UncheckAll";
        }
    }
    function findfill(id)
    {
        setDefault();
        document.getElementById(id).bgColor = "#d0dafd";
        document.getElementById('edit').disabled = false;
        document.getElementById('save').disabled = false;
        document.getElementById('stateutId').value = document.getElementById(id + '1').innerHTML;
        document.getElementById('stateName').value = document.getElementById(id + '3').innerHTML;
        document.getElementById('utName').value = document.getElementById(id + '4').innerHTML;
        document.getElementById('stateutDescription').value = document.getElementById(id + '5').innerHTML;
        document.getElementById('countryName').value = document.getElementById(id + '7').innerHTML;
        document.getElementById('zoneName').value = document.getElementById(id + '6').innerHTML;
        document.getElementById('message').innerHTML = "";
    }
    function setDefault()
    {
        for (var i = 1; i <= document.getElementById('noOfRowsTraversed').value; i++)
            document.getElementById(i).bgColor = "";
    }

    function displayMapList(id)
    {
        var action;
        var searchStateut = document.getElementById('searchStateut').value;
        if (id == 'viewPdf')
            action = "task=geerateMapReport&searchStateut=" + searchStateut;
        else
            action = "task=generateXlsapReport&searchStateut=" + searchStateut;
        var url = "stateutTypeCont?" + action;
        popup = popupWindow(url, "State & UT Report");
    }

    function popupWindow(url, windowName)
    {
        var windowfeatures = "left=50, top=50, width=1000, height=500, resizable=no, scrollbars=yes, location=0, menubar=no, status=no, dependent=yes";
        return window.open(url, windowName, windowfeatures)
    }
</script>

<div class="container-fluid">


    <div class="container  topbox" align="center">
        <h1 style="color: red;">State</h1>
    </div>

    <br>
    <%--   <td width="50" height="600" valign="top"><%@include file="/view/layout/Leftmenu.jsp" %></td></tr> --%>

    <DIV class="container">
        <div class="row">

        </div>
        <div class="row">
            <form action="stateutTypeCont" method="post">
                <table class="table table-bordered table-hover  table-striped">

                    <tr> <th> Enter State & UT Name:</th>

                        <td> <input type="text" class="form-control" name="searchStateut" size="30" id="searchStateut" value="${searchStateut}"/></td>
                        <td> <input class="btn btn-primary" type="submit" name="searchStateutModel" value="Search"/></td>
                        <td><input class="btn btn-success" type="submit" name="task" value="Search All Records"/></td>
                        <td><input class="btn btn-warning" type="button" id="viewPdf" name="viewPdf" value="PDF" onclick="displayMapList(id);"/></td>
                        <td><input class="btn btn-info" type="button" id="viewXls" name="viewPdf" value="Excel" onclick="displayMapList(id);"/></td>
                    </tr>
                </table>
            </form>
        </div> 
        <div class="container-fluid">
            <form action="stateutTypeCont" method="post">
                <input type="hidden" name="searchStateut" id="searchStateut" value="${searchStateut}"/>
                <table class="table table-bordered table-hover  table-striped">
                    <tr>
                        <th class="heading">S.No.</th>
                        <th class="heading" id="changeStateFont">State Name</th>
                        <th class="heading">UT Name</th>
                        <th class="heading">State & UT Description</th>

                        <th class="heading">Country Name</th>
                    </tr>
                    <c:forEach var="stateutBean" items="${requestScope['stateutList']}" varStatus="loopcounter">
                        <tr id="${loopcounter.count}" onclick="findfill(id)">
                            <td id="${loopcounter.count}1" style="display: none">${stateutBean.stateutId}</td>
                            <td id="${loopcounter.count}2" align="center">${lowerLimit - noOfRowsTraversed + loopcounter.count}</td>
                            <td id="${loopcounter.count}3" class="new_input toggle_state state_name">${stateutBean.stateName}</td>
                            <td id="${loopcounter.count}4" class="new_input">${stateutBean.utName}</td>
                            <td id="${loopcounter.count}5" class="new_input">${stateutBean.stateutDescription}</td>

                            <td id="${loopcounter.count}7">${stateutBean.countryName}</td>
                        </tr>
                    </c:forEach>
                    <tr>
                        <td align='center' colspan="6">
                            <c:choose>
                                <c:when test="${showFirst eq 'false'}">
                                    <input class="btn btn-primary" type='submit' name='task' value='First' disabled>
                                </c:when>
                                <c:otherwise>
                                    <input class="btn btn-primary" type='submit' name='task' value='First'>
                                </c:otherwise>
                            </c:choose>
                            <c:choose>
                                <c:when test="${showPrevious == 'false'}">
                                    <input class="btn btn-success" type='submit' name='task' value='Previous' disabled>
                                </c:when>
                                <c:otherwise>
                                    <input class="btn btn-success" type='submit' name='task' value='Previous'>
                                </c:otherwise>
                            </c:choose>
                            <c:choose>
                                <c:when test="${showNext eq 'false'}">
                                    <input class="btn btn-warning" type='submit' name='task' value='Next' disabled>
                                </c:when>
                                <c:otherwise>
                                    <input class="btn btn-warning" type='submit' name='task' value='Next'>
                                </c:otherwise>
                            </c:choose>
                            <c:choose>
                                <c:when test="${showLast == 'false'}">
                                    <input class="btn btn-info" type='submit' name='task' value='Last' disabled>
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
        <!--                <div class="row">
                            <form method="post" action="stateutTypeCont">
                                <table class="table table-bordered table-hover table-responsive table-striped">
                                    <tr>
                                        <th width="100" class="heading1">S. No</th>
                                        <th class="heading1" width="100">Country Name</th>
                                        <th class="heading1" width="100">Zone Name</th>
                                        <th class="heading1" width="100">State Name</th>
                                        <th class="heading1" width="100">UT Name</th>
                                        <th class="heading1" width="100">State/UT Description</th>
                                    </tr>
                                </table>
                                <table class="table table-bordered table-hover table-responsive table-striped">
                                    <tr>
                                        <td><input  class="btn btn-primary"  id="uncheckAll" name="uncheckAll" type="button" value="UncheckAll" onclick="check()"/>
                                            <input  class="btn btn-success"  type="submit"  id="saveAllRecords" name="task" value="Save all records"/>
                                            <input  class="btn btn-info"  type="submit" name="cancel" value="Cancel" onclick="deleteTable()"/>
                                        </td>
                                    </tr>
                                </table>
                            </form>
                        </div>-->

        <div class="container-fluid">
            <form action="stateutTypeCont" method="post" name="form2" onsubmit="return varification()">
                <table class="table table-bordered table-hover  table-striped">
                    <c:if test="${not empty message}">
                        <tr id="message">
                            <td colspan="2" bgcolor="${messageBGColor}"><b>Result: ${message}</b></td>
                        </tr>
                    </c:if>


                    <tr>
                        <td><input style="display: none;" class="form-control"  type="text" id="stateutId" name="stateutId" value="" readonly/><td>
                    </tr>
                    <tr>
                        <th>Country Name</th><td><input  type="text" name="countryName" class="form-control" id="countryName" value="India" size="40" readonly></td>
                    </tr>

                    <tr>
                        <th>State Name</th><td><input type="text" class="form-control" id="stateName" name="stateName" size="40" /></td>
                    </tr>
                    <tr>
                        <th>UT Name</th><td><input type="text" class="form-control" id="utName" name="utName" size="40" /></td>
                    </tr>

                    <tr>
                        <th>State or UT Description</th><td><input type="text" class="form-control" name="stateutDescription" id="stateutDescription" value="" size="40" ></td>
                    </tr>
                    <input type="hidden" name="buttonClick" id="buttonClick" value=""/>
                    <tr>
                        <td align='center' colspan="4">
                            <input class="btn btn-primary"  type="button" name="edit" id="edit" value="Edit" onclick="makeEditable(id)" />
                            <input class="btn btn-success"  type="submit" name="task" id="save" value="Save" onclick="setButton(id)" />
                            <input class="btn btn-info" type="submit" name="task" id="saveAsNew" value="Save As New" onclick="setButton(id)" />
                            <input class=" btn btn-warning"  type="reset" name="new" value="New" id="new" onclick="makeEditable(id)"/>                            
                            <input class="btn btn-success"  type="submit" name="task" value="Delete" id="delete" onclick="setButton(id)" />
                        </td>
                    </tr>
                </table>
            </form>         
        </div>

    </div>
</div>
<%@include file="../layout/footer.jsp" %>