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



        $("#search_org_office").autocomplete({
            source: function (request, response) {
                var random = document.getElementById("search_org_office").value;
                $.ajax({
                    url: "InventoryBasicController",
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


        $("#org_office").autocomplete({
            source: function (request, response) {
                var random = document.getElementById("org_office").value;
                $.ajax({
                    url: "InventoryBasicController",
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
                    url: "InventoryBasicController",
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
                    url: "InventoryBasicController",
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
                    url: "InventoryBasicController",
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
                    url: "InventoryBasicController",
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
                    url: "InventoryBasicController",
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
                $.ajax({
                    url: "InventoryBasicController",
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
        document.getElementById("item_code").disabled = false;
        document.getElementById("org_office").disabled = false;
        document.getElementById("manufacturer_name").disabled = false;
        document.getElementById("model_name").disabled = false;
        document.getElementById("min_quantity").disabled = false;
        document.getElementById("description").disabled = false;
        document.getElementById("daily_req").disabled = false;
        document.getElementById("opening_balance").disabled = false;
        document.getElementById("save").disabled = false;
        if (id === 'new') {
            $("#message").html("");
            document.getElementById("inventory_basic_id").value = "";
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
            var item_code = document.getElementById("item_code").value;
            var min_quantity = document.getElementById("min_quantity").value;
            var daily_req = document.getElementById("daily_req").value;
            var opening_balance = document.getElementById("opening_balance").value;
            var org_office = document.getElementById("org_office").value;
            var manufacturer_name = document.getElementById("manufacturer_name").value;
            var model_name = document.getElementById("model_name").value;

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
            if (myLeftTrim(min_quantity).length === 0) {
                $("#message").html('<div class="col-md-12 text-center"><label style="color:red"><b>Minimum Quantity is required...</b></label></div>');
                document.getElementById("min_quantity").focus();
                return false;
            }
            if (myLeftTrim(daily_req).length === 0) {
                $("#message").html('<div class="col-md-12 text-center"><label style="color:red"><b>Daily Requirement is required...</b></label></div>');
                document.getElementById("daily_req").focus();
                return false;
            }
            if (myLeftTrim(opening_balance).length === 0) {
                $("#message").html('<div class="col-md-12 text-center"><label style="color:red"><b>Opening Balance is required...</b></label></div>');
                document.getElementById("opening_balance").focus();
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



    function openPopUp(window_name, popup_height, popup_width, id) {
        var popup_top_pos = (screen.availHeight / 2) - (popup_height / 2);
        var popup_left_pos = (screen.availWidth / 2) - (popup_width / 2);
        var window_features = "left=" + popup_left_pos + ", top=" + popup_top_pos + ", width=" + popup_width + ", height=" + popup_height + ", resizable=no, scrollbars=yes, status=no, dialog=yes, dependent=yes";

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
        $('#inventory_basic_id').val(id);
        $('#org_office').val($("#" + count + '2').html());
        $('#manufacturer_name').val($("#" + count + '3').html());
//        $('#item_name').val($("#" + count + '4').html());
        $('#item_code').val($("#" + count + '4').html()+ " - " + $("#" + count + '5').html());
        $('#model_name').val($("#" + count + '6').html());
        $('#min_quantity').val($("#" + count + '7').html());
        $('#daily_req').val($("#" + count + '8').html());
        $('#opening_balance').val($("#" + count + '9').html());
        $('#description').val($("#" + count + '10').html());
        document.getElementById("edit").disabled = false;
        document.getElementById("delete").disabled = false;
    }
</script>


<section>
    <div class="container-fluid page_heading sectionPadding35">
        <h1>Inventory Basic</h1>
    </div>
</section>


<section class="marginTop30">
    <div class="container organizationBox">
        <div class="headBox">
            <h5 class="">Search Engine</h5>
        </div>
        <form name="form1" method="POST" action="InventoryBasicController" onsubmit="return verifySearch();" >
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

<!--                <div class="col-md-4">
                    <div class="form-group mb-md-0">
                        <label>Item Name - Code</label>
                        <input type="text" Placeholder="Item Name" name="search_item_name" id="search_item_name" value="${search_item_name}" class="form-control myInput searchInput1 w-100">
                    </div>
                </div>-->

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
                                <th>S.No.</th>
                                <th>Org Office</th>
                                <th>Manufacturer Name</th>
                                <th>Item Name</th>
                                <th>Item Code</th>
                                <th>Model Name</th>
                                <th>Minimum Quantity</th>
                                <th>Daily Requirement</th>
                                <th>Opening Balance</th>
                                <th>Description</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="beanType" items="${requestScope['list']}"
                                       varStatus="loopCounter">
                                <tr
                                    onclick="fillColumn('${beanType.inventory_basic_id}', '${loopCounter.count }');">
                                    <td>${loopCounter.count }</td>               
                                    <td id="${loopCounter.count }2">${beanType.org_office}</td>   
                                    <td id="${loopCounter.count }3">${beanType.manufacturer_name}</td>   
                                    <td id="${loopCounter.count }4">${beanType.item_name}</td>
                                    <td id="${loopCounter.count }5">${beanType.item_code}</td>
                                    <td id="${loopCounter.count }6">${beanType.model}</td>
                                    <td id="${loopCounter.count }7">${beanType.min_quantity}</td>                                               
                                    <td id="${loopCounter.count }8">${beanType.daily_req}</td> 
                                    <td id="${loopCounter.count }9">${beanType.opening_balance}</td>
                                    <td id="${loopCounter.count }10">${beanType.description}</td>     
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
        <form name="form2" method="POST" action="InventoryBasicController" onsubmit="return verify()" >
            <div class="row mt-3">
                <div class="col-md-3">
                    <div class="">
                        <div class="form-group">
                            <label>Org Office<span class="text-danger">*</span></label>
                            <input class="form-control myInput" type="hidden" id="inventory_basic_id" name="inventory_basic_id" value="" >
                            <input class="form-control myInput" type="hidden" id="item_name" name="item_name" value="" disabled >
                            <input class="form-control myInput" type="text" id="org_office" name="org_office" size="60" value="" disabled >
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
                            <label>Minimum Quantity<span class="text-danger">*</span></label>
                            <input class="form-control myInput" type="text" id="min_quantity" name="min_quantity" value="" disabled>
                        </div>
                    </div>
                </div>

                <div class="col-md-3">
                    <div class="">
                        <div class="form-group">
                            <label>Daily Requirement<span class="text-danger">*</span></label>
                            <input class="form-control myInput" type="text" id="daily_req" name="daily_req" value="" disabled>
                        </div>
                    </div>
                </div>

                <div class="col-md-3">
                    <div class="">
                        <div class="form-group">
                            <label>Opening Balance<span class="text-danger">*</span></label>
                            <input class="form-control myInput" type="text" id="opening_balance" name="opening_balance" value="" disabled>
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
                    <input type="button" class="btn normalBtn" name="task" id="edit" value="Edit" onclick="makeEditable(id)" disabled="">
                    <input type="submit" class="btn normalBtn" name="task" id="save" value="Save" onclick="setStatus(id)" disabled="">
                    <input type="reset" class="btn normalBtn" name="task" id="new" value="New" onclick="makeEditable(id)">
                    <input type="submit" class="btn normalBtn" name="task" id="delete" value="Delete" onclick="setStatus(id)" disabled="">
                </div>
            </div>
        </form>
    </div>
</section>

<%@include file="../layout/footer.jsp" %>

