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
        document.getElementById("select").disabled = false;
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
                //  var url = "IndentController?task=GetItems";
                //   popupwin = openPopUp(url, "", 600, 1030);
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
        console.log(JSON.stringify(json));
        var json_data_string = JSON.stringify(json);
        var encodedStringBtoA = btoa(json_data_string);
        // alert("deocde data -" + encodedStringBtoA);
        console.log(encodedStringBtoA);

        var url = "IndentController?task=GetItems&encodedStringBtoA=" + encodedStringBtoA;

        popupwin = openPopUp(url, "", 600, 1030);
    }

    function openPopUp(url, window_name, popup_height, popup_width) {
        var popup_top_pos = (screen.availHeight / 2) - (popup_height / 2);
        var popup_left_pos = (screen.availWidth / 2) - (popup_width / 2);
        var window_features = "left=" + popup_left_pos + ", top=" + popup_top_pos + ", width=" + popup_width + ", height=" + popup_height + ", resizable=yes, scrollbars=yes, location=0, menubar=no, status=no, dependent=yes";
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

    var json;
    $(function () {
        var String_data = $('#String_data').val();
        var last_ch = String_data.charAt(String_data.length - 1);
        if (last_ch == ",") {
            String_data = String_data.substring(0, String_data.length - 1);
        }
        String_data = '[' + String_data + ']';
        json = $.parseJSON(String_data);
        var col = [];

        for (var i = 0; i < json.length; i++) {
            for (var key in json[i]) {
                if (col.indexOf(key) === -1) {
                    col.push(key);
                }
            }
        }

        var table = document.createElement("table");
        table.setAttribute("id", "tree-table");
        table.setAttribute("class", "table table-hover table-bordered");

        var tr = table.insertRow(-1);

        for (var i = 0; i < col.length; i++) {
            var th = document.createElement("th");
            th.innerHTML = col[i];
            tr.appendChild(th);
        }

        for (var i = 0; i < json.length; i++) {

            tr = table.insertRow(-1);
            tr.setAttribute("id", i + 1);

            for (var j = 0; j < col.length; j++) {
                var tabCell = tr.insertCell(-1);
                if (j == 0) {
                    tabCell.innerHTML = '<input type="text"  name="checked_id" id="checked_id' + i + '"  value="' + json[i][col[j]] + '">';

                }
                if (j == 1) {
                    tabCell.innerHTML = '<input type="text"  name="item_name' + i + '" id="item_name' + i + '"  value="' + json[i][col[j]] + '">';
                }
                if (j == 2) {
                    tabCell.innerHTML = '<input type="text"  name="req_qty' + i + '" id="req_qty' + i + '"  value="' + json[i][col[j]] + '">';
                }
                if (j == 3) {
                    tabCell.innerHTML = '<input type="text"  name="purpose' + i + '" id="purpose' + i + '"  value="' + json[i][col[j]] + '">';
                }
                if (j == 4) {
                    tabCell.innerHTML = '<input type="text"  name="expected_date_time' + i + '" id="expected_date_time' + i + '"  value="' + json[i][col[j]] + '">';


                }
//                else {
//                    tabCell.innerHTML = json[i][col[j]];
//                }
            }
        }
        var divContainer = document.getElementById("showData");
        divContainer.innerHTML = "";
        divContainer.appendChild(table);

        // alert(json.length);
    });


</script>


<section>
    <div class="container-fluid page_heading sectionPadding35">
        <h1>Indent</h1>
    </div>
</section>
<!--<input type="button" onclick="CreateTableFromJSON()" value="Create Table From JSON" />
<p id="showData"></p>-->




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
                                <th>Indent No.</th>
                                <th>Requested To</th>
                                <th>Date Time</th>
                                <th>Status</th>
                                <th>Item Name</th>
                                <th>Required Qty</th>
                                <th>Approved Qty</th>
                                <th>Purpose</th>
                                <th>Expected Date</th>
                                <th>Description</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="beanType" items="${requestScope['list']}"
                                       varStatus="loopCounter">
                                <tr
                                    onclick="fillColumn('${beanType.indent_table_id}', '${loopCounter.count }');">
                                    <td>${loopCounter.count }</td>
                                    <td id="${loopCounter.count }">${beanType.indent_no}</td>
                                    <td id="${loopCounter.count }">${beanType.requested_to}</td>
                                    <td id="${loopCounter.count }">${beanType.date_time}</td>
                                    <c:choose>
                                        <c:when test="${beanType.status =='Delivered'}">
                                            <td id="${loopCounter.count }" style="background-color: #28a745;color:white"><b>${beanType.status}</b>
                                            </td>
                                        </c:when>

                                        <c:when test="${beanType.status =='Not In Stock'}">
                                            <td id="${loopCounter.count }" style="background-color: #d587c8;color:white"><b>${beanType.status}</b>
                                            </td>
                                        </c:when>
                                        <c:when test="${beanType.status =='Confirmed'}">
                                            <td id="${loopCounter.count }" style="background-color: #2e6da4;;color:white"><b>Approved</b>
                                            </td>
                                        </c:when>
                                        <c:when test="${beanType.status =='Confirmation Awaited'}">
                                            <td id="${loopCounter.count }" style="background-color: #ffc107;color:white"><b>${beanType.status}</b>
                                            </td>
                                        </c:when>
                                        <c:when test="${beanType.status =='Denied'}">
                                            <td id="${loopCounter.count }" style="background-color: #dc3545;color:white"><b>${beanType.status}</b>
                                            </td>
                                        </c:when>
                                        <c:otherwise>
                                            <td id="${loopCounter.count }">${beanType.status}</td>
                                        </c:otherwise>
                                    </c:choose>





                                    <td id="${loopCounter.count }">${beanType.item_name}</td>
                                    <td id="${loopCounter.count }">${beanType.required_qty}</td>
                                    <td id="${loopCounter.count }">${beanType.approved_qty}</td>
                                    <td id="${loopCounter.count }">${beanType.purpose}</td>
                                    <td id="${loopCounter.count }">${beanType.expected_date_time}</td>
                                    <td id="${loopCounter.count }">${beanType.description}</td>                                               
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
        <form name="form2" method="POST" action="IndentController" onsubmit="return verify()" >
            <div class="row mt-3">
                <div class="col-md-3">
                    <div class="">
                        <div class="form-group">
                            <label>Indent No.<span class="text-danger">*</span></label>
                            <input type="hidden" name="indent_table_id" id="indent_table_id" value="">
                            <input type="hidden" name="indent_item_id" id="indent_item_id" value="">
                            <input type="hidden" name="String_data" id="String_data" value="">
                            <input class="form-control myInput" type="text" id="indent_no" name="indent_no" value="${autogenerate_indent_no}" disabled onclick="alert('You cannot Change AutoGenerate Indent No!............')" >
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

                <div class="col-md-12" id="showData">
                    <!--                    <table id="tree-table" class="table table-hover table-bordered" data-page-length='6'>
                                            <tbody>
                                                <tr>
                                                    <th>Items</th>
                                                    <th></th>
                                                    <th></th>
                                                    <th></th>
                                                    <th></th>
                                                </tr>
                                                <tr></tr>
                    
                    
                                            </tbody>
                                        </table>-->

                </div>


                <div class="col-md-12">
                    <div class="">
                        <div class="form-group">
                            <label>Description</label>
                            <textarea class="form-control myTextArea" id="description" name="description" name="description" disabled>${description}</textarea>
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
                    <input type="button" class="btn normalBtn" name="task" id="select" value="Select Items" onclick="setStatus(id);openPopUpForItems()" disabled="">
                    <input type="submit" class="btn normalBtn" name="task" id="save" value="Send Indent" disabled="" onclick="setStatus(id);">
                </div>
            </div>
        </form>
    </div>
</section>

<%@include file="../layout/footer.jsp" %>

