<%@taglib prefix="myfn" uri="http://MyCustomTagFunctions" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
    table.dataTable {      
        border-collapse: collapse;
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
        $("#item_type").autocomplete({
            source: function (request, response) {
                var random = document.getElementById("item_type").value;
                $.ajax({
                    url: "ItemNameController",
                    dataType: "json",
                    data: {action1: "getItemType", str: random},
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
                $('#item_type').val(ui.item.label);
                return false;
            }
        });
        $("#search_item_type").autocomplete({
            source: function (request, response) {
                var random = document.getElementById("search_item_type").value;
                $.ajax({
                    url: "ItemNameController",
                    dataType: "json",
                    data: {action1: "getItemType", str: random},
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
                $('#search_item_type').val(ui.item.label);
                return false;
            }
        });
        $("#search_item_code").autocomplete({
            source: function (request, response) {
                var item_type = document.getElementById("search_item_type").value;
                var item_name = document.getElementById("search_item_name").value;
                var random = document.getElementById("search_item_code").value;
                $.ajax({
                    url: "ItemNameController",
                    dataType: "json",
                    data: {action1: "getItemCode", str: random, str2: item_type, str3: item_name},
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
                $('#search_item_code').val(ui.item.label);
                return false;
            }
        });
        $("#search_item_name").autocomplete({
            source: function (request, response) {
                var item_type = document.getElementById("search_item_type").value;
                var random = document.getElementById("search_item_name").value;
                $.ajax({
                    url: "ItemNameController",
                    dataType: "json",
                    data: {action1: "getItemName", str: random, str2: item_type},
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
        $("#parent_item").autocomplete({
            source: function (request, response) {
                //var item_type = document.getElementById("item_type").value;
                var random = document.getElementById("parent_item").value;
                $.ajax({
                    url: "ItemNameController",
                    dataType: "json",
                    data: {action1: "getParentItemName", str: random},
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
                $('#parent_item').val(ui.item.label);
                return false;
            }
        });
        $("#search_generation").autocomplete({
            source: function (request, response) {
                //var item_type = document.getElementById("item_type").value;
                var random = document.getElementById("search_generation").value;
                $.ajax({
                    url: "ItemNameController",
                    dataType: "json",
                    data: {action1: "getGeneration", str: random},
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
                $('#search_generation').val(ui.item.label);
                return false;
            }
        });
        $("#search_super_child").autocomplete({
            source: function (request, response) {
                //var item_type = document.getElementById("item_type").value;
                var random = document.getElementById("search_super_child").value;
                $.ajax({
                    url: "ItemNameController",
                    dataType: "json",
                    data: {action1: "getSuperChild", str: random},
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
                $('#search_super_child').val(ui.item.label);
                return false;
            }
        });
//        $('#item_name').blur(function () {
//            var min = 10000;
//            var max = 900000;
//            var item_name=$('#item_name').val();
//            var num = Math.floor(Math.random() * min) + max;
//            var auto_item_code = "APL_ITEM_"+item_name +"_"+ num;
//            $('#item_code').val(auto_item_code);
//        });
    });
    function makeEditable(id) {
        document.getElementById("item_type").disabled = false;
        document.getElementById("item_name").disabled = false;
        //  document.getElementById("item_code").disabled = false;
        document.getElementById("quantity").disabled = false;
        document.getElementById("prefix").disabled = false;
        document.getElementById("description").disabled = false;
        document.getElementById("parent_item").disabled = false;
        document.getElementById("supern").disabled = false;
        document.getElementById("supery").disabled = false;
        document.getElementById("save").disabled = false;
        if (id === 'new') {
            $("#message").html("");
            document.getElementById("item_name_id").value = "";
            document.getElementById("item_type").focus();
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
            var item_type = document.getElementById("item_type").value;
            var item_name = document.getElementById("item_name").value;
            var quantity = document.getElementById("quantity").value;
            var prefix = document.getElementById("prefix").value;
            if (myLeftTrim(item_type).length === 0) {
                $("#message").html('<div class="col-md-12 text-center"><label style="color:red"><b>Item Type is required...</b></label></div>');
                document.getElementById("item_type").focus();
                return false;
            }
            if (myLeftTrim(item_name).length === 0) {
                $("#message").html('<div class="col-md-12 text-center"><label style="color:red"><b>Item Name is required...</b></label></div>');
                document.getElementById("item_name").focus();
                return false;
            }
            if (myLeftTrim(prefix).length === 0) {
                $("#message").html('<div class="col-md-12 text-center"><label style="color:red"><b>Prefix is required...</b></label></div>');
                document.getElementById("prefix").focus();
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
//    function viewImages(id, img) {
//        popupwin = openPopUp("Show Image List", 1000, 1000, id);
//    }
    function openPopUp(window_name, popup_height, popup_width, id) {
        var popup_top_pos = (screen.availHeight / 2) - (popup_height / 2);
        var popup_left_pos = (screen.availWidth / 2) - (popup_width / 2);
        var window_features = "left=" + popup_left_pos + ", top=" + popup_top_pos + ", width=" + popup_width + ", height=" + popup_height + ", resizable=no, scrollbars=yes, status=no, dialog=yes, dependent=yes";
        // var queryString = "task1=getImageList&item_names_id=" + id;
        // var url = "ItemImagesController?" + queryString;
        return window.open(url, window_name, window_features);
    }
    if (!document.all) {
        document.captureEvents(Event.CLICK);
    }
    document.onclick = function () {
        if (popupwin !== null && !popupwin.closed) {
            popupwin.focus();
        }
    }
    function fillColumn(id, count) {
        $('#item_name_id').val(id);
        $('#item_name').val($("#" + count + '2').text());
        $('#prefix').val($("#" + count + '3').html());
        $('#item_type').val($("#" + count + '5').html());
        $('#quantity').val($("#" + count + '6').html());
        if (($("#" + count + '9').text()) != "") {
            $('#parent_item').val($("#" + count + '9').html() + " - " + $("#" + count + '11').html());
        } else {
            $('#parent_item').val($("#" + count + '9').html());
        }
        var super_child = $("#" + count + '10').html();
        if (super_child == 'Y') {
            $('#supery').attr('checked', true);
        } else {
            $('#supern').attr('checked', true);
        }
        $('#description').val($("#" + count + '8').html());
        document.getElementById("edit").disabled = false;
        document.getElementById("delete").disabled = false;
    }
    function showQuantity() {
        var is_super_child = $("input[name='super']:checked").val();
        if (is_super_child == 'Y') {
            $('#quantity_div').show();
        } else {
            $('#quantity_div').hide();
        }
    }
    function getOrgChartData(item_name) {
        window.open("ItemNameController?org_chart=Org Chart&item_name=" + item_name);
    }
</script>


<section>
    <div class="container-fluid page_heading sectionPadding35">
        <h1>Item Name</h1>
    </div>
</section>


<section class="marginTop30">
    <div class="container organizationBox">
        <div class="headBox">
            <h5 class="">Search Engine</h5>
        </div>
        <form name="form1" method="POST" action="ItemNameController" onsubmit="return verifySearch();" >
            <div class="row mt-3">
                <div class="col-md-4">
                    <div class="form-group mb-md-0">
                        <label>Item Type</label>
                        <input type="text" Placeholder="Item Type" name="search_item_type" id="search_item_type" value="${search_item_type}" class="form-control myInput searchInput1 w-100">
                    </div>
                </div>

                <div class="col-md-4">
                    <div class="form-group mb-md-0">
                        <label>Item Name</label>
                        <input type="text" name="search_item_name" id="search_item_name" value="${search_item_name}" Placeholder="Item Name" class="form-control myInput searchInput1 w-100" >
                    </div>
                </div>
                <div class="col-md-4">
                    <div class="form-group mb-md-0">
                        <label>Item Code</label>
                        <input type="text" Placeholder="Item Code"  name="search_item_code" id="search_item_code" value="${search_item_code}" class="form-control myInput searchInput1 w-100">
                    </div>
                </div>

                <div class="col-md-4">
                    <div class="form-group mb-md-0">
                        <label>Is Super Child</label>
                        <input type="text" Placeholder="Is Super Child"  name="search_super_child" id="search_super_child" value="${search_super_child}" class="form-control myInput searchInput1 w-100">
                    </div>
                </div>

                <div class="col-md-4">
                    <div class="form-group mb-md-0">
                        <label>Generation</label>
                        <input type="text" Placeholder="Generation"  name="search_generation" id="search_generation" value="${search_generation}" class="form-control myInput searchInput1 w-100">
                    </div>
                </div>
            </div>
            <hr>
            <div class="row">
                <div class="col-md-12 text-center">
                    <input type="submit" class="btn formBtn" id="hiera" name="search_item" value="SEARCH RECORDS" onclick="setStatus(id)">
                    <!--<input type="submit" class="btn formBtn" id="org_chart" name="org_chart" value="Org Chart">-->
                </div>
            </div>
        </form>
    </div>
</section>


<c:if test="${isSelectPriv eq 'Y'}">  
    <section class="marginTop30 ">
        <div class="container organizationBox">
            <div class="headBox">
                <h5 class="">Search List</h5>
            </div>
            <table id="tree-table" class="table table-hover table-bordered" data-page-length='6'>
                <tbody>
                <th>Item Name</th>
                <th>Prefix</th>
                <th>Item Code</th>
                <th>Item Type</th>
                <th>Quantity</th>
                <th>Generation</th>
                <th>Description</th>
                <th style="display:none"></th>
                <th style="display:none"></th>
                <th style="display:none"></th>
                <th style="display:none"></th>


                <c:forEach var="beanType" items="${requestScope['list']}"
                           varStatus="loopCounter">
                    <tr data-id="${beanType.item_names_id}" data-parent="${beanType.parent_item_id}" data-level="${beanType.generation}"
                        onclick="fillColumn('${beanType.item_names_id}', '${loopCounter.count }');">
                        <td id="${loopCounter.count }2" data-column="name">${beanType.item_name}</td>
                        <td id="${loopCounter.count }3">${beanType.prefix}</td>
                        <td id="${loopCounter.count }4">${beanType.item_code}</td>                                               
                        <td id="${loopCounter.count }5">${beanType.item_type}</td>                                               
                        <td id="${loopCounter.count }6">${beanType.quantity}</td> 
                        <td id="${loopCounter.count }7">${beanType.generation}</td>
                        <td id="${loopCounter.count }8">${beanType.description}</td> 
                        <td id="${loopCounter.count }9" style="display:none">${beanType.parent_item}</td>
                        <td id="${loopCounter.count }10" style="display:none">${beanType.superp}</td>
                        <td id="${loopCounter.count }11" style="display:none">${beanType.parent_item_code}</td>
                        <td id="${loopCounter.count }12">
                            <input type="submit" class="btn formBtn" id="org_chart" name="org_chart" value="Org Chart" onclick="getOrgChartData('${beanType.item_name}')">
                        </td>

                    </tr>
                </c:forEach>


                </tbody>
            </table>
        </div>          
    </section>
</c:if>

<section class="marginTop30">
    <div class="container organizationBox">
        <div class="headBox">
            <h5 class="">Data Entry</h5>
        </div>
        <form name="form2" method="POST" action="ItemNameController" onsubmit="return verify()" enctype="multipart/form-data">
            <div class="row mt-3">
                <div class="col-md-3">
                    <div class="">
                        <div class="form-group">
                            <label>Item Type<span class="text-danger">*</span></label>
                            <input class="form-control myInput" type="hidden" id="item_name_id" name="item_name_id" value="" >
                            <input type="hidden" id="item_image_details_id" name="item_image_details_id" value="" size="28"   />
                            <input class="form-control myInput" type="text" id="item_type" name="item_type" value="" disabled >
                        </div>
                    </div>
                </div>
                <div class="col-md-3">
                    <div class="">
                        <div class="form-group">
                            <label>Item Name<span class="text-danger">*</span></label>
                            <input class="form-control myInput" type="text" id="item_name" name="item_name" size="60" value="" disabled >
                        </div>
                    </div>
                </div>

                <div class="col-md-3">
                    <div class="">
                        <div class="form-group">
                            <label>Prefix<span class="text-danger">*</span></label>
                            <input class="form-control myInput" type="text" id="prefix" name="prefix" size="60" value="" disabled >
                        </div>
                    </div>
                </div>
            </div>


            <div class="row mt-3">
                <div class="col-md-3">
                    <div class="">
                        <div class="form-group">
                            <label>Parent Item - Code<span class="text-danger"></span></label>                            
                            <input class="form-control myInput" type="text" id="parent_item" name="parent_item"  value="" disabled>
                            <input class="form-control myInput" type="text" id="generation" name="generation" value="" size="45" disabled hidden>
                        </div>
                    </div>
                </div>

                <div class="col-md-3">
                    <div class="form-group mb-1">
                        <label class="" for="email">Is super child<span class="text-danger">*</span></label>
                    </div>
                    <div class="form-group form-check mb-0 d-inline mr-2 pl-0">
                        <label class="form-check-label">
                            <input type="radio" id="supery" name="super" value="Y" disabled onclick="showQuantity()"> Yes
                        </label>
                    </div>
                    <div class="form-group form-check d-inline pl-0">
                        <label class="form-check-label">
                            <input type="radio" id="supern" name="super" value="N" disabled onclick="showQuantity()"> No
                        </label>
                    </div>
                </div>

                <div class="col-md-3" style="display:none" id="quantity_div">
                    <div class="">
                        <div class="form-group">
                            <label>Quantity<span class="text-danger">*</span></label>
                            <input class="form-control myInput" type="text" id="quantity" name="quantity" value="" disabled>
                        </div>
                    </div>
                </div>

                <div class="col-md-12">
                    <div class="">
                        <div class="form-group">
                            <label>Description</label>
                            <!--<input class="form-control" type="text" id="description" name="description" size="60" value="" disabled >-->
                            <textarea class="form-control myTextArea" id="description" name="description" name="description" disabled></textarea>
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
                    <c:if test="${myfn:isContainPrivileges2(loggedUser,'ItemNameController','update') eq 'True'}">
                    <input type="button" class="btn normalBtn" name="task" id="edit" value="Edit" onclick="makeEditable(id)" disabled="">
                    </c:if>
                    
                    <c:if test="${myfn:isContainPrivileges2(loggedUser,'ItemNameController','insert') eq 'True'}">
                    <input type="submit" class="btn normalBtn" name="task" id="save" value="Save" onclick="setStatus(id)" disabled="">
                    </c:if>
                    
                    <c:if test="${myfn:isContainPrivileges2(loggedUser,'ItemNameController','insert') eq 'True'}">
                    <input type="reset" class="btn normalBtn" name="task" id="new" value="New" onclick="makeEditable(id)">
                    </c:if>
                    
                    <c:if test="${myfn:isContainPrivileges2(loggedUser,'ItemNameController','delete') eq 'True'}">
                    <input type="submit" class="btn normalBtn" name="task" id="delete" value="Delete" onclick="setStatus(id)" disabled="">
                    </c:if>
                </div>
            </div>
        </form>
    </div>
</section>

<%@include file="../layout/footer.jsp" %>
