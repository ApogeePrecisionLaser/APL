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

//        if (($('#message_level').text()) == "Result: Record saved successfully.") {
//            var last_indent_table_id=$('#last_indent_table_id').val();
//            var url = "IndentController?task=GetItems&ind_tab_id="+last_indent_table_id;
//            popupwin = openPopUp(url, "", 600, 1030);
//        }

    });
    $(document).ready(function () {
        $(document).on('keydown', '.myAutocompleteClass', function () {
            var id = this.id;
            var type;
            if (id.match("^status")) {
                type = "Status";
            } else if (id.match("^purpose")) {
                type = "Purpose";
            }

            var random = this.value;
            $('#' + id).autocomplete({
                source: function (request, response) {
                    $.ajax({
                        url: "IndentController",
                        dataType: "json",
                        data: {
                            action1: "getParameter",
                            str: random,
                            type: type
                        },
                        success: function (data) {
                            console.log(data);
                            response(data.list);
                        },
                        error: function (error) {
                            console.log(error.responseText);
                            response(error.responseText);
                        }
                    });
                },
                select: function (events, ui) {
                    console.log(ui);
                    $(this).val(ui.item.label); // display the selected text
                    return false;
                }
            });
        });
    });
    function enableFields(i) {
        document.getElementById("required_qty" + i).disabled = false;
        document.getElementById("expected_date_time" + i).disabled = false;
        document.getElementById("purpose" + i).disabled = false;
        //document.getElementById("status" + i).disabled = false;
    }

    $(function () {
        setTimeout(function () {
            $('#message').fadeOut('fast');
        }, 10000);
        $("#requested_by").autocomplete({
            source: function (request, response) {
                var random = document.getElementById("requested_by").value;
                $.ajax({
                    url: "IndentController",
                    dataType: "json",
                    data: {action1: "getRequestedByKeyPerson", str: random},
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
                $('#requested_by').val(ui.item.label);
                return false;
            }
        });
        $("#requested_to").autocomplete({
            source: function (request, response) {
                var random = document.getElementById("requested_to").value;
                var requested_by = document.getElementById("requested_by").value;
                $.ajax({
                    url: "IndentController",
                    dataType: "json",
                    data: {action1: "getRequestedToKeyPerson", str: random, requested_by: requested_by},
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
                $('#requested_to').val(ui.item.label);
                return false;
            }
        });
        $("#status").autocomplete({
            source: function (request, response) {
                var random = document.getElementById("status").value;
                $.ajax({
                    url: "IndentController",
                    dataType: "json",
                    data: {action1: "getStatus", str: random},
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
                $('#status').val(ui.item.label);
                return false;
            }
        });
    });
    function makeEditable(id) {
        document.getElementById("indent_no").disabled = false;
        document.getElementById("requested_by").disabled = false;
        document.getElementById("requested_to").disabled = false;
        document.getElementById("description").disabled = false;
//        document.getElementById("status").disabled = false;
//        for (i = 0; document.getElementById("item_name" + i) !== null; i++) {
//            document.getElementById("item_name" + i).disabled = false;
//        }


        document.getElementById("save").disabled = false;
        if (id === 'new') {
            $("#message").html("");
            document.getElementById("indent_table_id").value = "";
            // document.getElementById("indent_item_id").value = "";
            document.getElementById("indent_no").focus();
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
            var indent_no = document.getElementById("indent_no").value;
            if (myLeftTrim(indent_no).length === 0) {
                $("#message").html('<div class="col-md-12 text-center"><label style="color:red"><b>Indent No. is required...</b></label></div>');
                document.getElementById("indent_no").focus();
                return false;
            }
            if (result === false) {
            } else {
                result = true;
                var url = "IndentController?task=GetItems";
                popupwin = openPopUp(url, "", 600, 1030);
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


    function openPopUpForItems() {
        //  alert("ewed");
        var indent_no = $('#indent_no').val();
        var requested_by = $('#requested_by').val();
        var requested_to = $('#requested_to').val();
        var description = $('#description').val();
        var url = "IndentController?task=GetItems&indent_no=" + indent_no + "&requested_by=" + requested_by + "&requested_to=" + requested_to + "&description=" + description;
//        var url = "IndentController?task=GetItems";
        popupwin = openPopUp(url, "", 600, 1030);

    }

    function openPopUp(url, window_name, popup_height, popup_width) {
        var popup_top_pos = (screen.availHeight / 2) - (popup_height / 2);
        var popup_left_pos = (screen.availWidth / 2) - (popup_width / 2);
        var window_features = "left=" + popup_left_pos + ", top=" + popup_top_pos + ", width=" + popup_width + ", height=" + popup_height + ", resizable=yes, scrollbars=yes, location=0, menubar=no, status=no, dependent=yes";
        return window.open(url, window_name, window_features);
    }

//    function getData() {
//        var item_name = [];
//        var items = "";
//        $.ajax({
//            url: "IndentController",
//            dataType: "json",
//            data: {action1: "getItemsList"},
//            success: function (data) {
//
//                //  $('#items_div').show();
//                $('#item_list').empty();
//                console.log(data);
//                items = data.item_name;
//                for (var i = 0; i < items.length; i++) {
//                    item_name[i] = items[i]["item_name"];
//                    $('#item_list').append('<li class="mb-1" id="items"><div class="row"><div class="col-lg-2"><input type="checkbox" name="item_name" id="item_name' + i + '" disabled onclick=enableFields(' + i + ')> ' + item_name[i] + ' </div><div class="col-lg-1"><input style="width:50px;font-size:13px" type="text" name="required_qty" id="required_qty' + i + '" placeholder="Qty" disabled></div><div class="col-lg-2"><input style="font-size:13px" type="date" name="expected_date_time" id="expected_date_time' + i + '" placeholder="Expected Date Time" disabled></div><div class="col-lg-1"><input style="width:50px;font-size:13px" type="text" name="stock_qty" id="stock_qty' + i + '" placeholder="Qty" disabled></div><div class="col-lg-1"><input style="width:50px;font-size:13px" type="text" name="delivered_qty" id="delivered_qty' + i + '" placeholder="Qty" disabled></div><div class="col-lg-1"><input class="myAutocompleteClass" style="width:100px;font-size:13px" type="text" name="status' + i + '" id="status' + i + '" placeholder="Status" disabled ></div><div class="col-lg-1"><input style="font-size:13px" class="myAutocompleteClass" type="text" name="purpose' + i + '" id="purpose' + i + '" placeholder="Purpose" disabled></div></div></li>');
//                }
//            }
//        });
//    }


    if (!document.all) {
        document.captureEvents(Event.CLICK);
    }
    document.onclick = function () {
        if (popupwin !== null && !popupwin.closed) {
            popupwin.focus();
        }
    }

    function fillColumn(id, count) {
        $('#inventory_id').val(id);
        $('#org_office').val($("#" + count + '2').html());
        $('#item_name').val($("#" + count + '3').html());
        $('#item_code').val($("#" + count + '4').html());
        $('#key_person').val($("#" + count + '5').html());
        $('#inward_quantity').val($("#" + count + '6').html());
        $('#outward_quantity').val($("#" + count + '7').html());
        $('#date_time').val($("#" + count + '8').html());
        $('#reference_document_type').val($("#" + count + '9').html());
        $('#reference_document_id').val($("#" + count + '10').html());
        $('#description').val($("#" + count + '11').html());
        document.getElementById("edit").disabled = false;
        document.getElementById("delete").disabled = false;
    }





</script>


<section>
    <div class="container-fluid page_heading sectionPadding35">
        <h1>Indent</h1>
    </div>
</section>


<!--<section class="marginTop30">
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
                        <label>Item Name</label>
                        <input type="text" Placeholder="Item Name" name="search_item_name" id="search_item_name" value="${search_item_name}" class="form-control myInput searchInput1 w-100">
                    </div>
                </div>

                <div class="col-md-4">
                    <div class="form-group mb-md-0">
                        <label>Item Code</label>
                        <input type="text" Placeholder="Item Code" name="search_item_code" id="search_item_code" value="${search_item_code}" class="form-control myInput searchInput1 w-100">
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
</section>-->

<!--<section class="marginTop30 ">
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
                                <th>Item Name</th>
                                <th>Item Code</th>
                                <th>Key Person</th>
                                <th>Inward Quantity</th>
                                <th>Outward Quantity</th>
                                <th>Date Time</th>
                                <th>Reference Document Type</th>
                                <th>Reference Document Id</th>
                                <th>Description</th>
                            </tr>
                        </thead>
                        <tbody>
<c:forEach var="beanType" items="${requestScope['list']}"
           varStatus="loopCounter">
    <tr
        onclick="fillColumn('${beanType.inventory_id}', '${loopCounter.count }');">
        <td>${loopCounter.count }</td>               
        <td id="${loopCounter.count }2">${beanType.org_office}</td>   
        <td id="${loopCounter.count }3">${beanType.item_name}</td>
        <td id="${loopCounter.count }4">${beanType.item_code}</td>
        <td id="${loopCounter.count }5">${beanType.key_person}</td>                                               
        <td id="${loopCounter.count }6">${beanType.inward_quantity}</td> 
        <td id="${loopCounter.count }7">${beanType.outward_quantity}</td>
        <td id="${loopCounter.count }8">${beanType.date_time}</td> 
        <td id="${loopCounter.count }9">${beanType.reference_document_type}</td>
        <td id="${loopCounter.count }10">${beanType.reference_document_id}</td>
        <td id="${loopCounter.count }11">${beanType.description}</td>  

    </tr>
</c:forEach>
</tbody>
</table>    
</div>
</div>
</div>
</div>
</section>-->


<section class="marginTop30">
    <div class="container organizationBox">
        <div class="headBox">
            <h5 class="">Data Entry</h5>
        </div>
        <form name="form2" method="POST" action="IndentController" onsubmit="return verify()" >
            <div class="row mt-3">
                <div class="col-md-3">
                    <div class="">
                        <div class="form-group">
                            <label>Indent No.<span class="text-danger">*</span></label>
                            <input type="hidden" name="indent_table_id" id="indent_table_id" value="">
                            <!--<input class="form-control myInput" type="hidden" id="indent_item_id" name="indent_item_id" value="" disabled >-->
                            <input class="form-control myInput" type="text" id="indent_no" name="indent_no" value="" disabled >
                        </div>
                    </div>
                </div>

                <div class="col-md-3">
                    <div class="">
                        <div class="form-group">
                            <label>Requested By<span class="text-danger">*</span></label>
                            <input class="form-control myInput" type="text" id="requested_by" name="requested_by" value="${requested_by}" disabled onclick="alert('You cannot Change this person!............')">
                        </div>
                    </div>
                </div>

                <div class="col-md-3">
                    <div class="">
                        <div class="form-group">
                            <label>Requested To<span class="text-danger">*</span></label>
                            <input class="form-control myInput" type="text" id="requested_to" name="requested_to" value="${requested_to}" disabled onclick="alert('You cannot Change this person!............')">
                        </div>
                    </div>
                </div>

                <div class="col-md-12">
                    <div class="">
                        <div class="form-group">
                            <label>Description</label>
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
                            <label id="message_level" style="color:${msgBgColor}"><b>Result: ${message}</b></label>
                            <input type="text" name="last_indent_table_id" id="last_indent_table_id" value="${last_indent_table_id}">
                        </div>
                    </c:if>
                </div>
                <input type="hidden" id="clickedButton" value="">
                <div class="col-md-12 text-center">                       
                    <input type="reset" class="btn normalBtn" name="task" id="new" value="New" onclick="makeEditable(id)">
                    <input type="submit" class="btn normalBtn" name="task" id="save" value="Select Items" onclick="setStatus(id);openPopUpForItems()">
                </div>
            </div>
        </form>
    </div>
</section>

<%@include file="../layout/footer.jsp" %>

