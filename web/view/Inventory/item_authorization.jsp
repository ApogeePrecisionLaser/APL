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
                    url: "ItemAuthorizationController",
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

        $("#designation").autocomplete({
            source: function (request, response) {
                var random = document.getElementById("designation").value;
                $.ajax({
                    url: "ItemAuthorizationController",
                    dataType: "json",
                    data: {action1: "getDesignation", str: random},
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
                $('#designation').val(ui.item.label);
                return false;
            }
        });

        $("#search_item_name").autocomplete({
            source: function (request, response) {
                var random = document.getElementById("search_item_name").value;
                $.ajax({
                    url: "ItemAuthorizationController",
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

        $("#search_designation").autocomplete({
            source: function (request, response) {
                var random = document.getElementById("search_designation").value;
                $.ajax({
                    url: "ItemAuthorizationController",
                    dataType: "json",
                    data: {action1: "getDesignation", str: random},
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
                $('#search_designation').val(ui.item.label);
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
                    url: "InventoryBasicController",
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
                    url: "InventoryBasicController",
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

    function getDesignation() {
        var random = "";
        var org_office = document.getElementById("org_office").value;
        $('#des_list').empty();
//        if (org_office == '') {
//            alert("Please select org office!..");
//            return  false;
//        }
        $.ajax({
            url: "ItemNameController",
            dataType: "json",
            data: {action1: "getDesignation", str: random, org_office: org_office},
            success: function (data) {
                console.log(data);
//                response(data.list);
//                alert(data.list.length);
                console.log(data.list.length);
                if (data.list.length > 1) {
                    $('#des_list').append('<input name="designation"  type="checkbox" id="checkAllDes" value="All" onchange="checkAllCheckBoxes()" > All');
                }
                for (var i = 0; i < data.list.length; i++) {
                    var designation = data.list[i];
                    var myArray = designation.split("-");
                    designation = myArray[0];
                    var designation_id = myArray[1];

                    $('#des_list').append(' <li><input name="designation" type="checkbox" onchange="getKeyPerson(' + designation_id + ');" id="des_check' + designation_id + '" value="' + designation + '"> ' + designation + '<input name="designation_id" type="checkbox" value="' + designation_id + '" hidden id="designation_id' + designation_id + '"></li>');
                }

//                $('#designation').html('<input type="checkbox" name="designation_check">' + );
            }
        });
    }



    function getKeyPerson(designation_id) {
        var org_office = $('#org_office').val();
//        alert(designation_id);

        var checkAll = document.getElementById("checkAllDes");
        var random = "";
        if ($('#des_check' + designation_id).prop("checked") == true) {
            var designation = $('#des_check' + designation_id).val();
            $('#designation_id' + designation_id).attr("checked", "true");
//            alert(designation);
//            $('#key_person_div').append('<div class="col-md-3" id="key_person_col' + designation_id + '"><div class=""><div class="form-group"><label>' + designation + '<span class="text-danger">*</span></label><div class="scrollable" id="key_person_list"><li><input name="key_person" type="checkbox"  id="key_person_check' + key_person_id + '" value="' + designation + '"> ' + designation + '</li></div></div></div></div>');
            $.ajax({
                url: "ItemNameController",
                dataType: "json",
                data: {action1: "getKeyPerson", str: random, org_office: org_office, designation_id: designation_id},
                success: function (data) {
                    $('#key_person_div').show();
                    $('#key_person_div').append('<div class="col-md-3" id="key_person_col' + designation_id + '"><div class=""><div class="form-group"><label>' + designation + '<span class="text-danger">*</span></label><div class="scrollable" id="key_person_list' + designation_id + '"></div></div></div></div>');

                    for (var i = 0; i < data.list.length; i++) {
                        var key_person = data.list[i];
                        var myArray = key_person.split("-");
                        key_person = myArray[0];
                        var key_person_id = myArray[1];
                        $('#key_person_list' + designation_id).append('<li><input name="key_person" type="checkbox"  id="key_person_check' + key_person_id + '" value="' + key_person + '"> ' + key_person + '</li>');

//                        $('#des_list').append(' <li><input name="designation" type="checkbox" onchange="getKeyPerson(' + designation_id + ');" id="des_check' + designation_id + '" value="' + designation + '"> ' + designation + '</li>');
                    }

                }
            });


        } else {
            $('#key_person_div').hide();
            $('#key_person_col' + designation_id).remove();
            $('#designation_id' + designation_id).removeAttr("checked");

        }
    }

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


//        var checkboxes = document.getElementsByTagName('input');
//        var val = null;
//        for (var i = 0; i < checkboxes.length; i++) {
//            if (checkboxes[i].type == 'checkbox') {
//                if (val === null)
//                    val = checkboxes[i].checked;
//                checkboxes[i].checked = val;
//            }
//        }

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
                url: "ItemAuthorizationController",
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
                url: "ItemAuthorizationController",
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


    function viewMappedDesignation(item_names_id, org_office) {
        $('#designation_div' + item_names_id).empty();
        $.ajax({
            url: "ItemAuthorizationController",
            dataType: "json",
            data: {action1: "viewMappedDesignation", item_names_id: item_names_id, org_office: org_office},
            success: function (data) {
                console.log(data);
                console.log(data.list);
                if (data.list.length > 0) {
                    $('#designation_div' + item_names_id).show();
                } else {
                    $('#designation_div' + item_names_id).hide();
                }
                for (var i = 0; i < data.list.length; i++) {

                    $('#designation_div' + item_names_id).append('<li><a value="' + data.list[i] + '">' + data.list[i] + '</a></li>');
                }
            }
        });

//        popupwin = openPopUp("Show Mapped Designation", 1000, 1000, item_names_id, org_office);
    }

//    function openPopUp(window_name, popup_height, popup_width, item_names_id, org_office) {
//        var popup_top_pos = (screen.availHeight / 2) - (popup_height / 2);
//        var popup_left_pos = (screen.availWidth / 2) - (popup_width / 2);
//        var window_features = "left=" + popup_left_pos + ", top=" + popup_top_pos + ", width=" + popup_width + ", height=" + popup_height + ", resizable=no, scrollbars=yes, status=no, dialog=yes, dependent=yes";
//        var queryString = "task1=viewMappedDesignation&item_names_id=" + item_names_id + "&org_office=" + org_office;
//        var url = "ItemAuthorizationController?" + queryString;
//        return window.open(url, window_name, window_features);
//    }

//    if (!document.all) {
//        document.captureEvents(Event.CLICK);
//    }
//    document.onclick = function () {
//        if (popupwin !== null && !popupwin.closed) {
//            popupwin.focus();
//        }
//    }

    function fillColumn(id, count) {
        $('#item_authorization_id').val(id);
        $('#item_name').val($("#" + count + '2').html());
        $('#designation').val($("#" + count + '3').html());
        $('#quantity').val($("#" + count + '4').html());
        $('#monthly_limit').val($("#" + count + '5').html());
        $('#description').val($("#" + count + '6').html());
        document.getElementById("edit").disabled = false;
        document.getElementById("delete").disabled = false;
    }


</script>


<section>
    <div class="container-fluid page_heading sectionPadding35">
        <h1>Item Authorization</h1>
    </div>
</section>


<section class="marginTop30">
    <div class="container organizationBox">
        <div class="headBox">
            <h5 class="">Search Engine</h5>
        </div>
        <form name="form1" method="POST" action="ItemAuthorizationController" onsubmit="return verifySearch();" >
            <div class="row mt-3">
                <div class="col-md-4">
                    <div class="form-group mb-md-0">
                        <label>Item Name</label>
                        <input type="text" Placeholder="Item Name" name="search_item_name" id="search_item_name" value="${search_item_name}" class="form-control myInput searchInput1 w-100">
                    </div>
                </div>
                <div class="col-md-4">
                    <div class="form-group mb-md-0">
                        <label>Org Office</label>
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


<form name="form2" method="POST" action="ItemAuthorizationController" onsubmit="return verify()" >
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
                        <th >Designation & Key Person</th>
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
                                                        <a style="color:blue" onclick="viewMappedDesignation('${beanType.item_names_id }', '${mySplit}')" value="${mySplit}">${mySplit}</a>
                                                    </u>
                                                    </br>
                                                </li>
                                            </c:forTokens>
                                        </div>
                                    </c:if>
                                </td>
                                <td id="des_${beanType.item_names_id}">
                                    <div class="scrollable" id="designation_div${beanType.item_names_id}" style="display:none"> 

                                    </div>
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
                            <label>Org Office<span class="text-danger">*</span></label>
                            <input type="hidden" name="item_authorization_id" id="item_authorization_id" value="">

                            <input class="form-control myInput" type="text" id="org_office" name="org_office" 
                                   value="${org_office}" onblur="getDesignation()">
                        </div>
                    </div>
                </div>

                <div class="col-md-3">
                    <div class="">
                        <div class="form-group">
                            <label>Designation<span class="text-danger">*</span></label>
                            <div class="scrollable2" id="des_list">

                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="row" id="key_person_div" style="display:none" >


            </div>


            <!--                <div class="col-md-3">
                                <div class="">
                                    <div class="form-group">
                                        <label>Quantity<span class="text-danger">*</span></label>
                                        <input class="form-control myInput" type="text" id="quantity" name="quantity" value="" disabled>
                                    </div>
                                </div>
                            </div>
            
                            <div class="col-md-3">
                                <div class="">
                                    <div class="form-group">
                                        <label>Monthly Limit<span class="text-danger">*</span></label>
                                        <input class="form-control myInput" type="text" id="monthly_limit" name="monthly_limit" value="" disabled>
                                    </div>
                                </div>
                            </div>-->
            <div class="row">
                <div class="col-md-12">
                    <div class="">
                        <div class="form-group">
                            <label>Description</label>
                            <!--<input class="form-control" type="text" id="description" name="description" size="60" value="" disabled >-->
                            <textarea class="form-control myTextArea" id="description" name="description" name="description" ></textarea>
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
                    <c:if test="${myfn:isContainPrivileges2(loggedUser,'ItemAuthorizationController','update') eq 'True'}">
                        <!--<input type="button" class="btn normalBtn" name="task" id="edit" value="Edit" onclick="makeEditable(id)" disabled="">-->
                    </c:if>

                    <c:if test="${myfn:isContainPrivileges2(loggedUser,'ItemAuthorizationController','insert') eq 'True'}">
                        <input type="submit" class="btn normalBtn" name="task" id="save" value="Save" onclick="setStatus(id)">
                    </c:if>

                    <c:if test="${myfn:isContainPrivileges2(loggedUser,'ItemAuthorizationController','insert') eq 'True'}">
                        <!--<input type="reset" class="btn normalBtn" name="task" id="new" value="New" onclick="makeEditable(id)">-->
                    </c:if>

                    <c:if test="${myfn:isContainPrivileges2(loggedUser,'ItemAuthorizationController','delete') eq 'True'}">
                        <input type="submit" class="btn normalBtn" name="task" id="delete" value="Delete" onclick="setStatus(id)">
                    </c:if>
                </div>
            </div>
        </div>
    </section>
</form>

<%@include file="../layout/footer.jsp" %>
