<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@include file="../layout/header.jsp" %>
<script src="//code.jquery.com/jquery-1.10.2.js"></script>
<script src="//code.jquery.com/ui/1.10.4/jquery-ui.js"></script>

<script type="text/javascript" language="javascript">
          
           $(function(){  
                 
              $("#tehsil").autocomplete({
           
            source: function (request, response) {
                        
                $.ajax({
                    url: "cityTypeCont",
                    dataType: "json",
                    data: {action1: "getTehsil"},
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
                $('#tehsil').val(ui.item.label); // display the selected text
                return false;
            }
        }); 
        
        
                   $("#searchCity").autocomplete({
           
            source: function (request, response) {
                        
                $.ajax({
                    url: "cityTypeCont",
                    dataType: "json",
                    data: {action1: "getCity"},
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
                $('#searchCity').val(ui.item.label); // display the selected text
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
                    //document.getElementById('saveAsNew').disabled = true;
                    document.getElementById('cityName').disabled = false;
                    document.getElementById('cityDescription').disabled = false;
                    document.getElementById('pin_code').disabled = false;
                    document.getElementById('std_code').disabled = false;
                    document.getElementById('tehsil').disabled = false;
                    document.getElementById('save').disabled = false;

                    document.getElementById('message').innerHTML = "";

                }
                if (id == "edit")
                {
                    document.getElementById('pin_code').disabled = false;
                    document.getElementById('std_code').disabled = false;
                    document.getElementById('cityName').disabled = false;
                    document.getElementById('tehsil').disabled = false;
                    document.getElementById('cityDescription').disabled = false;
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
                if ($.trim(document.getElementById('cityName').value) == "")
                {
                    alert("Please enter city name")
                    document.getElementById('cityName').value = "";
                    document.getElementById('cityName').focus()
                    return false;
                }
//                if($.trim(document.getElementById('cityDescription').value)=="")
//                {
//                    alert("Please enter city description")
//                    document.getElementById('cityDescription').value="";
//                    document.getElementById('cityDescription').focus()
//                    return false;
//                }
                if (document.getElementById('cityId').value == "")
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
                element1.name = "city_id";
                element1.id = "city_id" + rowCount;
                element1.type = "hidden";
                element1.value = 1;
                element1.size = 1;
                cell1.appendChild(element1);

                var element2 = document.createElement("input");
                element2.name = "cityCheckbox";
                element2.id = "cityCheckbox" + rowCount;
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
                element5.name = "cityName";
                element5.id = "cityName" + rowCount;
                element5.value = document.getElementById('cityName').value;
                element5.size = 30;
                element5.className = "new_input";
                cell4.appendChild(element5);

                var cell5 = row.insertCell(4);
                var element6 = document.createElement("input");
                element6.name = "cityDescription";
                element6.id = "cityDescription" + rowCount;
                element6.value = document.getElementById('cityDescription').value;
                element6.size = 30;
                element6.className = "new_input";
                cell5.appendChild(element6);
                var height = (rowCount * 40) + 60;
                document.getElementById('autoCreatedTableDiv').style.visibility = "visible";
                document.getElementById("autoCreatedTableDiv").style.height = height + 'px';
            }
            function singleCheck(id)
            {
                if (document.getElementById('cityCheckbox' + id).checked == true)
                    document.getElementById('city_id' + id).value = 1;
                else
                    document.getElementById('city_id' + id).value = 0;
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
                var length = document.getElementsByName('cityCheckbox').length;
                if (value == "UncheckAll")
                {
                    for (var checkbox = 0; checkbox < length; checkbox++)
                    {
                        document.getElementsByName('cityCheckbox')[checkbox].checked = false;
                        document.getElementsByName('city_id')[checkbox].value = 0;
                    }
                    document.getElementById('uncheckAll').value = "CheckAll";
                } else
                {
                    for (var checkbox = 0; checkbox < length; checkbox++)
                    {
                        document.getElementsByName('cityCheckbox')[checkbox].checked = true;
                        document.getElementsByName('city_id')[checkbox].value = 1;
                    }
                    document.getElementById('uncheckAll').value = "UncheckAll";
                }
            }
            function findfill(id)
            {
               debugger;
               // setDefault();
              //  document.getElementById(id).bgColor = "#d0dafd";
                document.getElementById('edit').disabled = false;
                document.getElementById('save').disabled = true;
                document.getElementById('cityId').value = document.getElementById(id + '1').innerHTML;
                document.getElementById('cityName').value = document.getElementById(id + '3').innerHTML;
                document.getElementById('pin_code').value = document.getElementById(id + '4').innerHTML;
                document.getElementById('std_code').value = document.getElementById(id + '5').innerHTML;
                document.getElementById('cityDescription').value = document.getElementById(id + '6').innerHTML;
                document.getElementById('tehsil').value = document.getElementById(id + '7').innerHTML;
                document.getElementById('message').innerHTML = "";
            }
            function setDefault()
            {
                for (var i = 1; i <= document.getElementById('noOfRowsTraversed').value; i++)
                    document.getElementById(i).bgColor = "";
            }
            function displayMapList(id)
            {
                var searchCity = document.getElementById('searchCity').value;
                var action;
                if (id == 'viewPdf')
                    action = "task=generateMapReport&searchCity=" + searchCity;
                else
                    action = "task=generateMapXlsReport&searchCity=" + searchCity;
                var url = "cityTypeCont?" + action;
                popup = popupWindow(url, "City_View_Report");
            }
            function popupWindow(url, windowName)
            {
                var windowfeatures = "left=50, top=50, width=1000, height=500, resizable=no, scrollbars=yes, location=0, menubar=no, status=no, dependent=yes";
                return window.open(url, windowName, windowfeatures)
            }
            
        </script>




<section>
    <div class="container-fluid page_heading sectionPadding35">
        <h1>City Name</h1>
    </div>
</section>



<section class="marginTop30">
    <div class="container organizationBox">
        <div class="headBox">
            <h5 class="">Search Engine</h5>
        </div>
        <form name="form1" method="POST" action="OrganizationNameController" onsubmit="return verifySearch();" >
            <div class="row mt-3">

                <div class="col-md-12">
                    <div class="form-group mb-md-0">
                        <label>City Name</label>
                        <input type="text"  id="searchCity" name="searchCity" value="" Placeholder="City Name" class="form-control myInput searchInput1 w-100" >
                    </div>
                </div>

            </div>

            <hr>
            <div class="row">
                <div class="col-md-12 text-center">
                    <input type="submit" class="btn formBtn" id="hiera" name="search_org" value="SEARCH RECORDS" onclick="setStatus(id)">
                </div>
            </div>
        </form>
    </div>
</section>

<section class="marginTop30 ">
    <div class="container organizationBox">
        <div class="headBox">
            <h5 class="">Search List</h5>
        </div>
        <div class="row mt-3 myTable">
            <div class="col-md-12">
                <div class="table-responsive">
                    <table class="table table-striped table-bordered" id="mytable" style="width:100%">
                        <thead>
                            <tr>
                                <th>S.No.</th>
                                <th>City Name</th>
                                <th>Pin Code</th>
                                <th>Std Code</th>
                                <th>City Description</th>
                                <th>Tehsil Name</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="beanType" items="${requestScope['cityList']}"
                                       varStatus="loopCounter">
                                <tr
                                    onclick="fillColumn();">
                                    <td>${loopCounter.count }</td>
                                    <td id="${loopCounter.count }2">${beanType.cityName}</td>
                                    <td id="${loopCounter.count }3">${beanType.pin_code}</td>
                                    <td id="${loopCounter.count }4">${beanType.std_code}</td>
                                    <td id="${loopCounter.count }5">${beanType.cityDescription}</td>                                                
                                    <td id="${loopCounter.count }5">${beanType.tehsilName}</td>                                                
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>

    </div>
</section>




<section class="marginTop30">
    <div class="container organizationBox">
        <div class="headBox">
            <h5 class="">Data Entry</h5>
        </div>
        <form name="form2" method="POST" action="CityController" onsubmit="return verify()">
            <div class="row mt-3">
                <div class="col-md-4">
                    <div class="">
                        <div class="form-group">
                            <label>City Name<span class="text-danger">*</span></label>
                            <input class="form-control myInput" type="hidden" id="city_id" name="city_id" value="" >
                            <input class="form-control myInput" type="text" id="cityName" name="cityName" value="" disabled >
                        </div>
                    </div>
                </div>
                <div class="col-md-4">
                    <div class="">
                        <div class="form-group">
                            <label>Pin Code<span class="text-danger">*</span></label>
                            <input class="form-control myInput" type="text" id="pin_code" name="pin_code" size="60" value="" disabled >
                        </div>
                    </div>
                </div>
                <div class="col-md-4">
                    <div class="">
                        <div class="form-group">
                            <label>STD Code<span class="text-danger">*</span></label>
                            <input class="form-control myInput" type="text" id="std_code" name="std_code" value="" disabled>
                        </div>
                    </div>
                </div>
                <div class="col-md-4">
                    <div class="">
                        <div class="form-group">
                            <label>Tehsil Name<span class="text-danger">*</span></label>
                            <input class="form-control myInput" type="text" id="tehsil" name="tehsil" value="" disabled>
                        </div>
                    </div>
                </div>
                <div class="col-md-12">
                    <div class="">
                        <div class="form-group">
                            <label>Description</label>
                            <!--<input class="form-control" type="text" id="description" name="description" size="60" value="" disabled >-->
                            <textarea class="form-control myTextArea"  id="cityDescription" name="cityDescription" disabled></textarea>
                        </div>
                    </div>
                </div>
            </div>      
            <hr>
            <div class="row">
                <div id="message">
                    <c:if test="${not empty message}">
                        <div class="col-md-12 text-center">
                            <label style="color:${msgBgColor}"><b>Result: ${message}</b></label>
                        </div>
                    </c:if>
                </div>
                <div class="col-md-12 text-center">                       
                    <input type="button" class="btn normalBtn" name="edit" id="edit" value="Edit" onclick="makeEditable(id)" >
                    <input type="submit" class="btn normalBtn" name="task" id="save" value="Save" onclick="setStatus(id)">
                    <input type="reset" class="btn normalBtn" name="new" id="new" value="New" onclick="makeEditable(id)" >
                    <input type="submit" class="btn normalBtn" name="task" id="delete" value="Delete" onclick="setStatus(id)" >
                </div>
            </div>
        </form>
    </div>
</section>



<%@include file="../layout/footer.jsp" %>

