<%@taglib prefix="myfn" uri="http://MyCustomTagFunctions" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@include file="../layout/header.jsp" %>
<script src="//code.jquery.com/jquery-1.10.2.js"></script>
<script src="//code.jquery.com/ui/1.10.4/jquery-ui.js"></script>
<link rel="stylesheet" type="text/css" href="collapsetable/css/bootstrap.min.css">
<!--<script src="collapsetable/js/jquery-3.1.1.min.js"></script>-->
<!--<script src="collapsetable/js/bootstrap.min.js"></script>-->
<script src="collapsetable/js/javascript.js"></script>

<style>
    .selected_row {
        font-weight: bolder;
        color: blue;
        border: 3px solid black;
    }


    .treegrid-indent {
        width: 0px;
        height: 16px;
        display: inline-block;
        position: relative;
    }

    .treegrid-expander {
        width: 0px;
        height: 16px;
        display: inline-block;
        position: relative;
        left:-17px;
        cursor: pointer;
    }
    label{
        font-size: 13px;
    }
    table{
        font-size:13px
    }
    .ui-widget{
        font-size: 1.4em;
    }
    .table_div{
        height:300px;
        overflow-y: scroll;
        position:relative;
    }
    .table_div{
        height:300px;
        overflow-y: scroll;
        position:relative;
    }
    .scrollable {
        overflow: auto;
        height: 60px;
        border: 1px solid #332A7C;
        font-size: 13px;
    }
    .scrollable2 {
        overflow: auto;
        height: 100px;
        border: 1px solid #332A7C;
        font-size: 13px;
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

        $("#item_name").autocomplete({
            source: function (request, response) {
                var random = document.getElementById("item_name").value;
                $.ajax({
                    url: "VendorItemMapController",
                    dataType: "json",
                    data: {action1: "getItemName", str: random},
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
                $('#item_name').val(ui.item.label);
                return false;
            }
        });

        $("#search_item_name").autocomplete({
            source: function (request, response) {
                var random = document.getElementById("search_item_name").value;
                $.ajax({
                    url: "VendorItemMapController",
                    dataType: "json",
                    data: {action1: "getItemName", str: random},
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
                $('#search_item_name').val(ui.item.label);
                return false;
            }
        });

        $("#org_office").autocomplete({
            source: function (request, response) {
                if ($('input[type="checkbox"]:checked').length == 0) {
                    alert("Please Select any one of the item!...");
                    return  false;
                }
                var random = document.getElementById("org_office").value;
                var search_org_office = "";
                //  alert("jfhg");
                $.ajax({
                    url: "VendorItemMapController",
                    dataType: "json",
                    data: {action1: "getOrgOffice", str: random, search_org_office: search_org_office},
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
                $('#org_office').val(ui.item.label);
                return false;
            }
        });

        $("#search_org_office").autocomplete({
            source: function (request, response) {
                var random = document.getElementById("search_org_office").value;
                var search_org_office = "";
                //  alert("jfhg");
                $.ajax({
                    url: "VendorItemMapController",
                    dataType: "json",
                    data: {action1: "getOrgOffice", str: random, search_org_office: search_org_office},
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
                $('#search_org_office').val(ui.item.label);
                return false;
            }
        });
    });



    function checkAllCheckBoxes() {
        var checkboxes = document.getElementsByClassName("des_checkboxes");
        var val = null;
        for (var i = 0; i < checkboxes.length; i++) {
            if ($('#checkAllDes').prop("checked") == true) {
                $('.des_checkboxes').attr("checked", true);
            } else {
                $('.des_checkboxes').attr("checked", false);
            }
        }
    }

    function checkAll() {
        var checkboxes = document.getElementsByClassName("messageCheckbox");
        var val = null;
        for (var i = 0; i < checkboxes.length; i++) {
            if ($('#checkall').prop("checked") == true) {
                $('.messageCheckbox').attr("checked", true);
            } else {
                $('.messageCheckbox').attr("checked", false);
            }
        }
    }

    function checkUncheck(item_names_id) {
        if ($('#pchcheck' + item_names_id).prop("checked") == true) {
            $.ajax({
                url: "VendorItemMapController",
                dataType: "json",
                data: {action1: "getAllChild", item_names_id: item_names_id},
                success: function (data) {
                    console.log(data);
                    console.log(data.list);
                    for (var i = 0; i < data.list.length; i++) {
                        $('#pchcheck' + data.list[i]).attr("checked", true);
                    }
                }
            });
        } else {
            $.ajax({
                url: "VendorItemMapController",
                dataType: "json",
                data: {action1: "getAllChild", item_names_id: item_names_id},
                success: function (data) {
                    console.log(data);
                    console.log(data.list);
                    for (var i = 0; i < data.list.length; i++) {
                        $('#pchcheck' + data.list[i]).attr("checked", false);
                    }
                }
            });
        }
    }

    function makeEditable(id) {
//        document.getElementById("item_name").disabled = false;
//        document.getElementById("quantity").disabled = false;
        document.getElementById("description").disabled = false;
//        document.getElementById("monthly_limit").disabled = false;

        document.getElementById("save").disabled = false;
        if (id === 'new') {
            $("#message").html("");
            document.getElementById("item_authorization_id").value = "";
            document.getElementById("item_name").focus();
        }
        if (id == 'edit') {
            document.getElementById("save_As").disabled = true;
            document.getElementById("delete").disabled = false;
        }
    }

    function setStatus(id) {
        if (id === 'save') {
            document.getElementById("clickedButton").value = "Save";
        } else {
            document.getElementById("clickedButton").value = "Delete";
        }
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

    function verify() {
        var result;
        if (document.getElementById("clickedButton").value === 'Save' || document.getElementById("clickedButton").value === 'Save AS New') {
            var item_name = document.getElementById("item_name").value;
            var designation = document.getElementById("designation").value;
            var quantity = document.getElementById("quantity").value;
            var monthly_limit = document.getElementById("monthly_limit").value;
            if (myLeftTrim(item_name).length === 0) {
                $("#message").html('<div class="col-md-12 text-center"><label style="color:red"><b>Item Name is required...</b></label></div>');
                document.getElementById("item_name").focus();
                return false;
            }
            if (myLeftTrim(designation).length === 0) {
                $("#message").html('<div class="col-md-12 text-center"><label style="color:red"><b>Designation is required...</b></label></div>');
                document.getElementById("designation").focus();
                return false;
            }
            if (myLeftTrim(quantity).length === 0) {
                $("#message").html('<div class="col-md-12 text-center"><label style="color:red"><b>Quantity is required...</b></label></div>');
                document.getElementById("quantity").focus();
                return false;
            }
            if (myLeftTrim(monthly_limit).length === 0) {
                $("#message").html('<div class="col-md-12 text-center"><label style="color:red"><b>Monthly limit is required...</b></label></div>');
                document.getElementById("monthly_limit").focus();
                return false;
            }
            if (result === false) {
            } else {
                result = true;
            }
            if (document.getElementById("clickedButton").value === 'Save AS New') {
                result = confirm("Are you sure you want to save it as New record?")
                return result;
            }
        } else {
            result = confirm("Are you sure you want to delete this record?");
        }
        return result;
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

    function fillColumn(id, count) {
        $('#vendor_item_map_id').val(id);
        $('#item_name').val($("#" + count + '2').html());
        $('#description').val($("#" + count + '6').html());
        document.getElementById("edit").disabled = false;
        document.getElementById("delete").disabled = false;
    }


</script>


<section>
    <div class="container-fluid page_heading sectionPadding35">
        <h1>Vendor Item Map</h1>
    </div>
</section>


<section class="marginTop30">
    <div class="container organizationBox">
        <div class="headBox">
            <h5 class="">Search Engine</h5>
        </div>
        <form name="form1" method="POST" action="VendorItemMapController" onsubmit="return verifySearch();" >
            <div class="row mt-3">
                <div class="col-md-4">
                    <div class="form-group mb-md-0">
                        <label>Item Name</label>
                        <input type="text" Placeholder="Item Name" name="search_item_name" id="search_item_name" value="${search_item_name}" class="form-control myInput searchInput1 w-100">
                    </div>
                </div>
                <div class="col-md-4">
                    <div class="form-group mb-md-0">
                        <label>Vendor</label>
                        <input type="text" Placeholder="Org Office" name="search_org_office" id="search_org_office" value="${search_org_office}" class="form-control myInput searchInput1 w-100">
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


<form name="form2" method="POST" action="VendorItemMapController" onsubmit="return verify()" >
    <section class="marginTop30">
        <div class="container organizationBox">
            <div class="headBox">
                <h5 class="">Items List</h5>
            </div>
            <div class="row mt-3 table_div">

                <table id="tree-table" class="table table-hover table-bordered" data-page-length='6'>

                    <tr>
                        <th><input type="checkbox" onchange="checkAll()" name="chk[]" id="checkall" /></th>
                        <th>Item Name</th>
                        <th >Org Office</th>
                    </tr>
                    <tbody>
                        <c:forEach var="beanType" items="${requestScope['list']}"
                                   varStatus="loopCounter">

                            <tr data-id="${beanType.item_names_id}" data-parent="${beanType.parent_item_id}" data-level="${beanType.generation}">
                                <td>
                                    <input type="checkbox" class="messageCheckbox" onchange="checkUncheck('${beanType.item_names_id}')" 
                                           id="pchcheck${beanType.item_names_id}" name="item_checkbox" value="${beanType.item_name}">
                                </td>
                                <td id="${beanType.item_names_id }" data-column="name">${beanType.item_name}</td>
                                <td>
                                    <c:if test="${beanType.org_office!=''}">
                                        <div class="scrollable">
                                            <c:forTokens items="${beanType.org_office}" delims="," var="mySplit">
                                                <li>  
                                                    <u style="color:blue">
                                                        <a style="color:blue" value="${mySplit}">${mySplit}</a>
                                                    </u>
                                                    </br>
                                                </li>
                                            </c:forTokens>
                                        </div>
                                    </c:if>
                                </td>

                            </tr>

                        </c:forEach>
                    </tbody>
                </table>

            </div>      
        </div>
    </section>

    <section class="marginTop30">
        <div class="container organizationBox">
            <div class="headBox">
                <h5 class="">Data Entry</h5>
            </div>

            <div class="row mt-3">
                <div class="col-md-3">
                    <div class="">
                        <div class="form-group">
                            <label>Vendor<span class="text-danger">*</span></label>
                            <input type="hidden" name="vendor_item_map_id" id="vendor_item_map_id" value="">

                            <input class="form-control myInput" type="text" id="org_office" name="org_office" 
                                   value="${org_office}">
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
                    <c:if test="${myfn:isContainPrivileges2(loggedUser,'VendorItemMapController','update') eq 'True'}">
                        <!--<input type="button" class="btn normalBtn" name="task" id="edit" value="Edit" onclick="makeEditable(id)" disabled="">-->
                    </c:if>

                    <c:if test="${myfn:isContainPrivileges2(loggedUser,'VendorItemMapController','insert') eq 'True'}">
                        <input type="submit" class="btn normalBtn" name="task" id="save" value="Save" onclick="setStatus(id)">
                    </c:if>

                    <c:if test="${myfn:isContainPrivileges2(loggedUser,'VendorItemMapController','insert') eq 'True'}">
                        <!--<input type="reset" class="btn normalBtn" name="task" id="new" value="New" onclick="makeEditable(id)">-->
                    </c:if>

                    <c:if test="${myfn:isContainPrivileges2(loggedUser,'VendorItemMapController','delete') eq 'True'}">
                        <input type="submit" class="btn normalBtn" name="task" id="delete" value="Delete" onclick="setStatus(id)">
                    </c:if>
                </div>
            </div>
        </div>
    </section>
</form>

<%@include file="../layout/footer.jsp" %>
