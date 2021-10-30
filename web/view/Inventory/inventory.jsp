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



        $("#search_org_office").autocomplete({
            source: function (request, response) {
                var random = document.getElementById("search_org_office").value;
                $.ajax({
                    url: "InventoryController",
                    dataType: "json",
                    data: {action1: "getOrgOffice", str: random},
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

        $("#search_key_person").autocomplete({
            source: function (request, response) {
                var random = document.getElementById("search_key_person").value;
                var org_office = document.getElementById("search_org_office").value;
                $.ajax({
                    url: "InventoryController",
                    dataType: "json",
                    data: {action1: "getKeyPerson", str: random, str2: org_office},
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
                $('#search_key_person').val(ui.item.label);
                return false;
            }
        });

        $("#key_person").autocomplete({
            source: function (request, response) {
                var random = document.getElementById("key_person").value;
                var org_office = document.getElementById("org_office").value;

                $.ajax({
                    url: "InventoryController",
                    dataType: "json",
                    data: {action1: "getKeyPerson", str: random, str2: org_office},
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
                $('#key_person').val(ui.item.label);
                return false;
            }
        });



        $("#org_office").autocomplete({
            source: function (request, response) {
                var random = document.getElementById("org_office").value;
                $.ajax({
                    url: "InventoryController",
                    dataType: "json",
                    data: {action1: "getOrgOffice", str: random},
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

        $("#search_manufacturer").autocomplete({
            source: function (request, response) {
                var random = document.getElementById("search_manufacturer").value;
                $.ajax({
                    url: "InventoryController",
                    dataType: "json",
                    data: {action1: "getManufacturer", str: random},
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
                $('#search_manufacturer').val(ui.item.label);
                return false;
            }
        });



        $("#manufacturer_name").autocomplete({
            source: function (request, response) {
                var random = document.getElementById("manufacturer_name").value;
                $.ajax({
                    url: "InventoryController",
                    dataType: "json",
                    data: {action1: "getManufacturer", str: random},
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
                $('#manufacturer_name').val(ui.item.label);
                return false;
            }
        });

        $("#item_code").autocomplete({
            source: function (request, response) {
                var random = document.getElementById("item_code").value;
                var manufacturer = document.getElementById("manufacturer_name").value;
                var org_office = document.getElementById("org_office").value;
                $.ajax({
                    url: "InventoryController",
                    dataType: "json",
                    data: {action1: "getItemCode", str: random, manufacturer: manufacturer},
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
                $('#item_code').val(ui.item.label);
                return false;
            }
        });


        $("#search_model").autocomplete({
            source: function (request, response) {
                var random = document.getElementById("search_model").value;
                var manufacturer_name = document.getElementById("search_manufacturer").value;
                var item_code = document.getElementById("search_item_code").value;
                $.ajax({
                    url: "InventoryController",
                    dataType: "json",
                    data: {action1: "getModelName", str: random, manufacturer_name: manufacturer_name, item_code: item_code},
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
                $('#search_model').val(ui.item.label);
                return false;
            }
        });


        $("#model_name").autocomplete({
            source: function (request, response) {
                var random = document.getElementById("model_name").value;
                var manufacturer_name = document.getElementById("manufacturer_name").value;
                var item_code = document.getElementById("item_code").value;
                $.ajax({
                    url: "InventoryController",
                    dataType: "json",
                    data: {action1: "getModelName", str: random, manufacturer_name: manufacturer_name, item_code: item_code},
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
                $('#model_name').val(ui.item.label);
                return false;
            }
        });

        $("#search_item_code").autocomplete({
            source: function (request, response) {
                var random = document.getElementById("search_item_code").value;
                var manufacturer = document.getElementById("search_manufacturer").value;
                // var org_office = document.getElementById("search_org_office").value;
                $.ajax({
                    url: "InventoryController",
                    dataType: "json",
                    data: {action1: "getItemCode", str: random, manufacturer: manufacturer},
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
    });


    function makeEditable(id) {
        document.getElementById("org_office").disabled = false;
        document.getElementById("item_code").disabled = false;
        document.getElementById("key_person").disabled = false;
        document.getElementById("description").disabled = false;
        document.getElementById("manufacturer_name").disabled = false;
        document.getElementById("model_name").disabled = false;
        // document.getElementById("inward_quantity").disabled = false;
        //document.getElementById("outward_quantity").disabled = false;
        document.getElementById("date_time").disabled = false;
        // document.getElementById("reference_document_type").disabled = false;
        // document.getElementById("reference_document_id").disabled = false;
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
            var org_office = document.getElementById("org_office").value;
            var item_code = document.getElementById("item_code").value;
            var key_person = document.getElementById("key_person").value;
            var manufacturer_name = document.getElementById("manufacturer_name").value;
            var model_name = document.getElementById("model_name").value;
            var date_time = document.getElementById("date_time").value;
            var reference_document = document.getElementById("reference_document").value;

            if (myLeftTrim(org_office).length === 0) {
                $("#message").html('<div class="col-md-12 text-center"><label style="color:red"><b>Org Office is required...</b></label></div>');
                document.getElementById("org_office").focus();
                return false;
            }
            if (myLeftTrim(manufacturer_name).length === 0) {
                $("#message").html('<div class="col-md-12 text-center"><label style="color:red"><b>Manufacturer Name is required...</b></label></div>');
                document.getElementById("manufacturer_name").focus();
                return false;
            }
            if (myLeftTrim(model_name).length === 0) {
                $("#message").html('<div class="col-md-12 text-center"><label style="color:red"><b>Model Name is required...</b></label></div>');
                document.getElementById("model_name").focus();
                return false;
            }
            if (myLeftTrim(item_code).length === 0) {
                $("#message").html('<div class="col-md-12 text-center"><label style="color:red"><b>Item Code is required...</b></label></div>');
                document.getElementById("item_code").focus();
                return false;
            }
            if (myLeftTrim(key_person).length === 0) {
                $("#message").html('<div class="col-md-12 text-center"><label style="color:red"><b>Key Person is required...</b></label></div>');
                document.getElementById("key_person").focus();
                return false;
            }
            if (myLeftTrim(date_time).length === 0) {
                $("#message").html('<div class="col-md-12 text-center"><label style="color:red"><b>Date Time is required...</b></label></div>');
                document.getElementById("date_time").focus();
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

    function openPopUpForDetails(item_names_id) {
        popupwin = openPopUp("Item Details", 600, 1130, item_names_id);
    }

    function openPopUp(window_name, popup_height, popup_width, item_names_id) {
        var popup_top_pos = (screen.availHeight / 2) - (popup_height / 2);
        var popup_left_pos = (screen.availWidth / 2) - (popup_width / 2);
        var window_features = "left=" + popup_left_pos + ", top=" + popup_top_pos + ", width=" + popup_width + ", height=" + popup_height + ", resizable=no, scrollbars=yes, status=no, dialog=yes, dependent=yes";
        var url = "InventoryController?task=GetDetails&item_names_id=" + item_names_id;
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

    function fillColumn(id, count, popupval) {
        //  alert(popupval);
        if (popupval == '') {
            $('#inventory_id').val(id);
            $('#item_code').val($("#" + count + '2').text() + " - " + $("#" + count + '3').html());
            $('#org_office').val($("#" + count + '4').html());
            $('#manufacturer_name').val($("#" + count + '5').html());
            $('#model_name').val($("#" + count + '6').html());
            $('#key_person').val($("#" + count + '7').html());
            $('#date_time').val($("#" + count + '11').html());
            $('#description').val($("#" + count + '12').html());
            document.getElementById("edit").disabled = false;
            document.getElementById("delete").disabled = false;
        }

    }
</script>


<section>
    <div class="container-fluid page_heading sectionPadding35">
        <h1>Inventory</h1>
    </div>
</section>


<section class="marginTop30">
    <div class="container organizationBox">
        <div class="headBox">
            <h5 class="">Search Engine</h5>
        </div>
        <form name="form1" method="POST" action="InventoryController" onsubmit="return verifySearch();" >
            <div class="row mt-3">
                <div class="col-md-4">
                    <div class="form-group mb-md-0">
                        <label>Org Office</label>
                        <input type="text" name="search_org_office" id="search_org_office" value="${search_org_office}" Placeholder="Org Office" class="form-control myInput searchInput1 w-100" >
                    </div>
                </div>

                <div class="col-md-4">
                    <div class="form-group mb-md-0">
                        <label>Manufacturer</label>
                        <input type="text" name="search_manufacturer" id="search_manufacturer" value="${search_manufacturer}" Placeholder="Manufacturer" class="form-control myInput searchInput1 w-100" >
                    </div>
                </div>

                <div class="col-md-4">
                    <div class="form-group mb-md-0">
                        <label>Item Name - Code</label>
                        <input type="text" Placeholder="Item Name" name="search_item_code" id="search_item_code" value="${search_item_code}" class="form-control myInput searchInput1 w-100">
                    </div>
                </div>

                <div class="col-md-4">
                    <div class="form-group mb-md-0">
                        <label>Model</label>
                        <input type="text" name="search_model" id="search_model" value="${search_model}" Placeholder="Model" class="form-control myInput searchInput1 w-100" >
                    </div>
                </div>

                <div class="col-md-4">
                    <div class="form-group mb-md-0">
                        <label>Key Person</label>
                        <input type="text" Placeholder="Key Person" name="search_key_person" id="search_key_person" value="${search_key_person}" class="form-control myInput searchInput1 w-100">
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


<c:if test="${isSelectPriv eq 'Y'}">
    <section class="marginTop30">
        <div class="container organizationBox">
            <div class="headBox">
                <h5 class="">Items List</h5>
            </div>
            <div class="row mt-3 table_div">

                <table id="tree-table" class="table table-hover table-bordered" data-page-length='6'>

                    <tr>
                        <!--<th>S.No.</th>-->

                        <th>Item Name</th>
                        <th>Item Code</th>
                        <th>Org Office</th>
                        <th>Manufacturer Name</th>
                        <th>Model Name</th>
                        <th>Key Person</th>
                        <th>Stock Quantity</th>
                        <th>Inward Quantity</th>
                        <th>Outward Quantity</th>
                        <th>Reference Document Type</th>
                        <th>Reference Document Id</th>
                        <th>Date Time</th>
                        <th>Description</th>
                        <th></th>
                    </tr>
                    <tbody>
                        <c:forEach var="beanType" items="${requestScope['list']}"
                                   varStatus="loopCounter">
                            <!--openpopup-->

                            <tr onclick="fillColumn('${beanType.inventory_id}', '${loopCounter.count}', '${beanType.popupval}');"
                                data-id="${beanType.item_names_id}" data-parent="${beanType.parent_item_id}" data-level="${beanType.generation}">
                                <!--<td data-column="name">${loopCounter.count }</td>-->               

                                <td id="${loopCounter.count }2" data-column="name">${beanType.item_name}</td>
                                <td id="${loopCounter.count }3">${beanType.item_code}</td>
                                <td id="${loopCounter.count }4" >${beanType.org_office}</td> 
                                <td id="${loopCounter.count }5">${beanType.manufacturer_name}</td> 
                                <td id="${loopCounter.count }6">${beanType.model}</td>
                                <td id="${loopCounter.count }7">${beanType.key_person}</td>                                               
                                <td id="${loopCounter.count }8">${beanType.stock_quantity}</td>                                               
                                <td id="${loopCounter.count }9">${beanType.inward_quantity}</td> 
                                <td id="${loopCounter.count }10">${beanType.outward_quantity}</td>
                                <td id="${loopCounter.count }11">${beanType.reference_document_type}</td>
                                <td id="${loopCounter.count }12">${beanType.reference_document_id}</td>
                                <td id="${loopCounter.count }13">${beanType.date_time}</td> 
                                <td id="${loopCounter.count }14">${beanType.description}</td>  

                                <c:if test="${beanType.popupval=='openpopup'}">
                                    <td>
                                        <input type="button" name="openpopup" id="openpopup" class="btn btn-success" value="Show Details" onclick="openPopUpForDetails(${beanType.item_names_id})">
                                    </td>
                                </c:if>
                                <td></td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>

            </div>      
        </div>
    </section>
</c:if>


<!--<section class="marginTop30">
    <div class="container organizationBox">
        <div class="headBox">
            <h5 class="">Data Entry</h5>
        </div>
        <form name="form2" method="POST" action="InventoryController" onsubmit="return verify()" >
            <div class="row mt-3">
                <div class="col-md-3">
                    <div class="">
                        <div class="form-group">
                            <label>Org Office<span class="text-danger">*</span></label>
                            <input type="hidden" name="inventory_id" id="inventory_id" value="">
                            <input class="form-control myInput" type="hidden" id="item_name" name="item_name" value="" disabled >
                            <input class="form-control myInput" type="text" id="org_office" name="org_office" value="" disabled >
                        </div>
                    </div>
                </div>

                <div class="col-md-3">
                    <div class="">
                        <div class="form-group">
                            <label>Manufacturer<span class="text-danger">*</span></label>
                            <input class="form-control myInput" type="text" id="manufacturer_name" name="manufacturer_name" value="" disabled >
                        </div>
                    </div>
                </div>


                <div class="col-md-3">
                    <div class="">
                        <div class="form-group">
                            <label>Item Name - Code<span class="text-danger">*</span></label>

                            <input class="form-control myInput" type="text" id="item_code" name="item_code" value="" disabled >
                        </div>
                    </div>
                </div>

                <div class="col-md-3">
                    <div class="">
                        <div class="form-group">
                            <label>Model<span class="text-danger">*</span></label>

                            <input class="form-control myInput" type="text" id="model_name" name="model_name" value="" disabled >
                        </div>
                    </div>
                </div>

                <div class="col-md-3">
                    <div class="">
                        <div class="form-group">
                            <label>Key Person<span class="text-danger">*</span></label>

                            <input class="form-control myInput" type="text" id="key_person" name="key_person" value="" disabled >
                        </div>
                    </div>
                </div>

                <div class="col-md-3">
                    <div class="">
                        <div class="form-group">
                            <label>Date Time<span class="text-danger">*</span></label>
                            <input class="form-control myInput" type="date"  id="date_time" name="date_time" value="" disabled>
                        </div>
                    </div>
                </div>

                <div class="col-md-12">
                    <div class="">
                        <div class="form-group">
                            <label>Description</label>
                            <input class="form-control" type="text" id="description" name="description" size="60" value="" disabled >
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
<input type="button" class="btn normalBtn" name="task" id="edit" value="Edit" onclick="makeEditable(id)" disabled="">
<input type="submit" class="btn normalBtn" name="task" id="save" value="Save" onclick="setStatus(id)" disabled="">
<input type="reset" class="btn normalBtn" name="task" id="new" value="New" onclick="makeEditable(id)">
<input type="submit" class="btn normalBtn" name="task" id="delete" value="Delete" onclick="setStatus(id)" disabled="">
</div>
</div>
</form>
</div>
</section>-->


<%@include file="../layout/footer.jsp" %>
