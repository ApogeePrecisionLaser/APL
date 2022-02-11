<%-- 
    Document   : user_privilege
    Created on : 23 Sep, 2021, 11:03:41 AM
    Author     : Vikrant
--%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@include file="../layout/header.jsp" %>
<script src="//code.jquery.com/jquery-1.10.2.js"></script>
<script src="//code.jquery.com/ui/1.10.4/jquery-ui.js"></script>
<style>
    .selected_row {
        font-weight: bolder;
        color: blue;
        border: 3px solid black;
    }
    table.dataTable {      
        border-collapse: collapse;
    }
</style>
<script>

    $(document).ready(function () {
        $('#mytable tbody').on('click', 'tr', function () {
            if ($(this).hasClass('selected_row')) {
                $(this).removeClass('selected_row');
            } else {
                $("#mytable").DataTable().$(
                        'tr.selected_row').removeClass(
                        'selected_row');
                $(this).addClass('selected_row');
            }
        });
    });


    $(function () {
        setTimeout(function () {
            $('#message').fadeOut('fast');
        }, 10000);



        $("#role_nameSearch").autocomplete({
            source: function (request, response) {
                var random = document.getElementById("role_nameSearch").value;
                $.ajax({
                    url: "UserPrivilegeController",
                    dataType: "json",
                    data: {action1: "getRoleName", str: random},
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
                $('#role_nameSearch').val(ui.item.label);
                return false;
            }
        });
                
        $("#u_urlSearch").autocomplete({
            source: function (request, response) {
                var random = document.getElementById("u_urlSearch").value;
                $.ajax({
                    url: "UserPrivilegeController",
                    dataType: "json",
                    data: {action1: "getUrl", str: random},
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
                $('#u_urlSearch').val(ui.item.label);
                return false;
            }
        });

    });


    function makeEditable(id) {
        document.getElementById("org_office").disabled = false;
        document.getElementById("item_code").disabled = false;
        document.getElementById("key_person").disabled = false;
        document.getElementById("description").disabled = false;
        document.getElementById("inward_quantity").disabled = false;
        document.getElementById("outward_quantity").disabled = false;
        document.getElementById("date_time").disabled = false;
        document.getElementById("reference_document_type").disabled = false;
        document.getElementById("reference_document_id").disabled = false;
        document.getElementById("save").disabled = false;
        if (id === 'new') {
            $("#message").html("");
            document.getElementById("inventory_id").value = "";
            document.getElementById("org_office").focus();
        }
        if (id == 'edit') {
            document.getElementById("save_As").disabled = true;
            document.getElementById("delete").disabled = false;
        }
    }


    function setStatus(id) {
        if (id == 'save') {
            document.getElementById("clickedButton").value = "Save";
        } else if (id == 'update') {
            document.getElementById("clickedButton").value = "Update";
        } else if (id == 'privilege_update') {
            document.getElementById("clickedButton").value = "PrivilegeUpdate";
        } else
            document.getElementById("clickedButton").value = "Delete";
    }
    function myLeftTrim(str) {
        var beginIndex = 0;
        for (var i = 0; i < str.length; i++) {
            if (str.charAt(i) === ' ')
                beginIndex++;
            else
                break;
        }
        return str.substring(beginIndex, str.length);
    }
    
    function verifySearch() {
        var result;
        if (document.getElementById("clickedButton").value === 'SEARCH') {
            var org_name = document.getElementById("org_name").value;
            if (myLeftTrim(org_name).length === 0) {
                document.getElementById("org_msg").innerHTML = "<b>Organization Name is required...</b>";
                document.getElementById("org_name").focus();
                return false; // code to stop from submitting the form2.
            }
        }
    }

    function singleCheckUncheck(id) {

        //  alert(document.getElementById('insertTable').rows.length);
        if ($('#check_org_department' + id).is(':checked')) {
            $("#u_url_privilege_id" + id).val("1");
        } else {
            $("#u_url_privilege_id" + id).val("0");
        }
        if ($('form[name="form2"] input[type=checkbox]:checked').size() == 0) {

            $("#addAllRecords").attr("disabled", "disabled");
        } else {

            $("#addAllRecords").removeAttr("disabled");
        }
    }

    function addRow(u_url, privilege_type, privilege, active, remark) {

        var a = '';
        var b = '';
        var c = '';
        var d = '';
        var e = '';

        if ($('#Admin').is(':checked')) {
            a = document.getElementById("Admin").value;
        }
        if ($('#Clerk').is(':checked')) {
            b = document.getElementById("Clerk").value;
        }
        if ($('#Manager').is(':checked')) {
            c = document.getElementById("Manager").value;
        }
        if ($('#Guest').is(':checked')) {
            d = document.getElementById("Guest").value;
        }
        if ($('#Super_Admin').is(':checked')) {

            e = document.getElementById("Super_Admin").value;
        }
        var table = document.getElementById('insertTable');
        var rowCount = table.rows.length;                // alert(rowCount);
        var row = table.insertRow(rowCount);
        var cell1 = row.insertCell(0);
        cell1.innerHTML = rowCount;
        var element1 = document.createElement("input");
        element1.type = "hidden";
        element1.name = "u_url_privilege_id";
        element1.id = "u_url_privilege_id" + rowCount;
        element1.size = 1;
        element1.value = 1;
        element1.readOnly = false;
        cell1.appendChild(element1);
        var element1 = document.createElement("input");
        element1.type = "checkbox";
        element1.name = "check_org_department";
        element1.id = "check_org_department" + rowCount;                //element1.size = 1;
        element1.value = "YesModify";
        element1.checked = true;
        element1.setAttribute('onclick', 'singleCheckUncheck(' + rowCount + ')');
        cell1.appendChild(element1);

        var cell2 = row.insertCell(1);
        var element2 = document.createElement("input");
        element2.type = "text";
        element2.name = "role_name";
        element2.id = "role_name" + rowCount;
        element2.size = 30;
        element2.value = a + "," + b + "," + c + "," + d + "," + e;
        element2.readOnly = true;
        cell2.appendChild(element2);

        var cell3 = row.insertCell(2);
        var element3 = document.createElement("input");
        element3.type = "text";
        element3.name = "u_url";
        element3.id = "u_url" + rowCount;
        element3.size = 20;
        element3.value = u_url;
        element3.readOnly = true;
        cell3.appendChild(element3);

        var cell4 = row.insertCell(3);
        var element4 = document.createElement("input");
        element4.type = "text";
        element4.name = "privilege_type";
        element4.id = "privilege_type" + rowCount;
        element4.size = 15;
        element4.value = privilege_type;
        element4.readOnly = true;
        cell4.appendChild(element4);

        var cell5 = row.insertCell(4);
        var element5 = document.createElement("input");
        element5.type = "text";
        element5.name = "privilege";
        element5.id = "privilege" + rowCount;
        element5.size = 7;
        element5.value = privilege;
        element5.readOnly = true;
        cell5.appendChild(element5);

        var cell6 = row.insertCell(5);
        var element6 = document.createElement("input");
        element6.type = "text";
        element6.name = "remark";
        element6.id = "remark" + rowCount;
        element6.size = 15;
        element6.value = remark;
        element6.readOnly = true;
        cell6.appendChild(element6);

        var cell7 = row.insertCell(6);
        var element7 = document.createElement("input");
        element7.type = "hidden";
        element7.name = "active";
        element7.id = "active" + rowCount;
        element7.size = 5;
        element7.value = active;
        element7.readOnly = true;
        cell7.appendChild(element7);

        var height = (rowCount * 40) + 60;
        document.getElementById("autoCreateTableDiv").style.visibility = "visible";
        document.getElementById("autoCreateTableDiv").style.height = height + 'px';
        if (rowCount == 1) {
            $('#checkUncheckAll').attr('hidden', true);
        } else {
            $('#checkUncheckAll').attr('hidden', false);
        }
        resetFields();
    }

    function resetFields() {
        document.getElementById("privilege_type").value = '';
        document.getElementById("privilege_type").focus();
        document.getElementById("remark").value = '';
    }
    function deleteRowWithoutResetForm() {
        try {
            var table = document.getElementById('insertTable');
            var rowCount = table.rows.length;
            for (var i = 1; i < rowCount; i++) {
                table.deleteRow(1);
            }
            document.getElementById("autoCreateTableDiv").style.visibility = "visible";
            document.getElementById("autoCreateTableDiv").style.height = '0px';
        } catch (e) {
            alert(e);
        }
    }

    function setUrlDefault() {
        document.getElementById('u_url').value = '';
    }
    function setUrlSearchDefault() {
        document.getElementById('u_urlSearch').value = '';
    }

    function ComplaintReportPdf() {
        var role_nameSearch = document.getElementById("role_nameSearch").value;
        var u_urlSearch = document.getElementById("u_urlSearch").value;
        var queryString = "task=UrlPrivilegeReport&role_nameSearch=" + role_nameSearch + "&u_urlSearch=" + u_urlSearch;
        var url = "userUrlPrivilegeCont.do?" + queryString;
        popupwin = openPopUp(url, "Complaint Report", 500, 750);
    }

    function openPopUp(url, window_name, popup_height, popup_width) {
        var popup_top_pos = (screen.availHeight / 2) - (popup_height / 2);
        var popup_left_pos = (screen.availWidth / 2) - (popup_width / 2);
        var window_features = "left=" + popup_left_pos + ", top=" + popup_top_pos + ", width=" + popup_width + ", height=" + popup_height + ", resizable=no, scrollbars=yes, status=no, dialog=yes, dependent=yes";
        return window.open(url, window_name, window_features);
    }
    function checkUncheckAll(id) {
        var table = document.getElementById('insertTable');
        var rowCount = table.rows.length;
        if (id == 'Check All') {
            $('input[name=check_org_department]').attr("checked", true);
            for (var i = 1; i < rowCount; i++) {
                $("#department_id" + i).val("1");
            }
            $('#checkUncheckAll').val('Uncheck All');
            $("#addAllRecords").removeAttr("disabled");
        } else {
            $('input[name=check_org_department]').attr("checked", false);
            $('#checkUncheckAll').val('Check All');
            $("#addAllRecords").attr("disabled", "disabled");
            for (var i = 1; i < rowCount; i++) {
                $("#department_id" + i).val("2");
            }
        }
    }

    function fillColumn(id, count) {//alert("idd --"+id+" count -"+count);
        document.getElementById("Admin").checked = false;
        document.getElementById("Clerk").checked = false;
        document.getElementById("Manager").checked = false;
        document.getElementById("Guest").checked = false;
        document.getElementById("Super_Admin").checked = false;
        document.getElementById("Dealer").checked = false;
        document.getElementById("Employee").checked = false;
        document.getElementById("Sales").checked = false;
        document.getElementById("Incharge").checked = false;

        var role_name = $("#" + count + '2').html();
        if (role_name === 'Super Admin') {
            role_name = 'Super_Admin';
        }
        //alert(role_name);
        document.getElementById(role_name).checked = true;

        //alert($("#privilege" + count).val());
        //alert($.trim(document.getElementById("privilege" + count).value));

        $('#u_url').val($("#" + count + '3').html());
        $('#privilege_type').val($("#" + count + '4').html());
        $('#privilege').val($("#privilege" + count).val());
        $('#remarl').val($("#" + count + '6').html());

        $('#u_url').attr('disabled', false);
        $('#privilege_type').attr('disabled', false);
        $('#privilege').attr('disabled', false);
        $('#remark').attr('disabled', false);

        document.getElementById("edit").disabled = false;
        document.getElementById("delete").disabled = false;
        document.getElementById("save").disabled = false;
        $('#main_id').val(id);
        
    }

</script>


<section>
    <div class="container-fluid page_heading sectionPadding35">
        <h1>User Privilege</h1>
    </div>
</section>


<section class="marginTop30">
    <div class="container organizationBox">
        <div class="headBox">
            <h5 class="">Search Engine</h5>
        </div>
        <form name="form1" method="POST" action="UserPrivilegeController">
            <div class="row mt-3">
                <div class="col-md-4">
                    <div class="form-group mb-md-0">
                        <label>Role Name</label>
                        <input type="text" name="role_nameSearch" id="role_nameSearch" value="${role_nameSearch}" Placeholder="Role Name" class="form-control myInput searchInput1 w-100" >
                    </div>
                </div>

                <div class="col-md-4">
                    <div class="form-group mb-md-0">
                        <label>User URL</label>
                        <input type="text" Placeholder="User URL" name="u_urlSearch" id="u_urlSearch" value="${u_urlSearch}" class="form-control myInput searchInput1 w-100">
                    </div>
                </div>

            </div>
            <hr>
            <div class="row">
                <div class="col-md-12 text-center">
                    <input type="submit" class="btn formBtn" id="hiera" name="search_item" value="SEARCH RECORDS" onclick="setStatus(id)">
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
                <div class="table-responsive verticleScroll">
                    <table class="table table-striped table-bordered" id="mytable" style="width:100%" data-page-length='6'>
                        <thead>
                            <tr>
                                <th class="heading">S.No.</th>
                                <th class="heading">Role name</th>
                                <th class="heading">User Url</th>
                                <th class="heading">Privilege Type</th>
                                <th class="heading">Privilege</th> 
                                <th class="heading">Remark</th>
                            </tr>
                        </thead>
                        <tbody>                            
                            <c:forEach var="url_privilegeBean" items="${requestScope['url_privilegeList']}"  varStatus="loopCounter">
                                <tr  
                                    onclick="fillColumn('${url_privilegeBean.u_url_privilege_id}', '${loopCounter.count }');">
                                    <td>${loopCounter.count }</td>                                    
                                    <td id="${loopCounter.count }2">${url_privilegeBean.role_name}</td>
                                    <td id="${loopCounter.count }3">${url_privilegeBean.u_url}</td>
                                    <td id="${loopCounter.count }4">${url_privilegeBean.privilege_type}</td>
                                    <td id="${loopCounter.count }5">
                                        <select id="privilege${loopCounter.count}" name="privilegeBulk" style="width:70px">
                                            <option value="Y" ${url_privilegeBean.privilege == 'Y' ? 'selected' : ''}>Yes</option>
                                            <option value="N" ${url_privilegeBean.privilege == 'N' ? 'selected' : ''}>No</option>
                                        </select>
                                    </td>  
                                    <td id="${loopCounter.count }6">${url_privilegeBean.remark}</td>
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
        <form name="form2" method="POST" action="UserPrivilegeController" onsubmit="return verify()" >
            <div class="row mt-3">
                <div class="col-md-3">
                    <div class="">
                        <div class="form-group">
                            <label>Role Name<span class="text-danger">*</span></label>
                            <td>
                                <input type="checkbox" id="Admin" name="role" value="Admin" checked >Admin<br>
                                <input type="checkbox" id="Clerk" name="role" value="Clerk" checked >Clerk<br>
                                <input type="checkbox" id="Manager" name="role" value="Manager" checked >Manager<br>
                                <input type="checkbox" id="Guest" name="role" value="Guest" checked >Guest<br>
                                <input type="checkbox" id="Super_Admin" name="role" value="Super Admin" checked >Super Admin<br>
                                <input type="checkbox" id="Dealer" name="role" value="Dealer" checked >Dealer<br>
                                <input type="checkbox" id="Employee" name="role" value="Employee" checked >Employee<br>
                                <input type="checkbox" id="Sales" name="role" value="Sales" checked >Sales<br>
                                <input type="checkbox" id="Incharge" name="role" value="Incharge" checked >Incharge<br>

                            </td>
                        </div>
                    </div>
                </div>

                <div class="col-md-3">
                    <div class="">
                        <div class="form-group">
                            <label>User URL<span class="text-danger">*</span></label>
                            <input class="form-control myInput" type="text" id="u_url" name="u_url" value="" disabled >
                        </div>
                    </div>
                </div>

                <div class="col-md-3">
                    <div class="">
                        <div class="form-group">
                            <label>Privilege Type<span class="text-danger">*</span></label>
                            <input class="form-control myInput" type="text" id="privilege_type" name="privilege_type" value="" disabled >
                        </div>
                    </div>
                </div>

                <div class="col-md-3">
                    <div class="">
                        <div class="form-group">
                            <label>Privilege<span class="text-danger">*</span></label>
                            <select name="privilege" id="privilege" disabled>
                                <option value="Y">Yes</option>
                                <option value="N">No</option>
                            </select>
                        </div>
                    </div>
                </div>

                <div class="col-md-3">
                    <div class="">
                        <div class="form-group">
                            <label>Remark<span class="text-danger">*</span></label>
                            <input class="form-control myInput" type="text" id="remark" name="remark" value="" disabled >
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
                <input type="hidden" id="clickedButton" value="">
                <div class="col-md-12 text-center">                       
                    <input type="button" class="btn normalBtn" name="task" id="edit" value="Edit" onclick="makeEditable(id)" disabled="">
                    <input type="submit" class="btn normalBtn" name="task" id="save" value="Save" onclick="setStatus(id)" disabled="">
                    <input type="reset" class="btn normalBtn" name="task" id="new" value="New" onclick="makeEditable(id)">
                    <input type="submit" class="btn normalBtn" name="task" id="delete" value="Delete" onclick="setStatus(id)" disabled="">
                    <input type="hidden" name="main_id" id="main_id" value="">
                </div>
            </div>
        </form>
    </div>
</section>

<%@include file="../layout/footer.jsp" %>
