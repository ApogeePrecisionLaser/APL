<%@taglib prefix="myfn" uri="http://MyCustomTagFunctions" %>
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
    .not_in_stock{
        background-color: #d587c8;
        color:white;
    }
    .approved{
        background-color: #2e6da4;
        color:white; 
    }
    .PaymentPending{
        background-color: #dc3545;
        color:white; 
    }
    .PaymentSuccess{
        background-color: #5cb85c;
        color:white; 
    }
    .delivered{
        background-color: #5cb85c;
        color:white; 
    }
    .denied{
        background-color: #dc3545;
        color:white; 
    }
    .pending{
        background-color: #ffc107;
        color:white; 
    }
    .delivery_challan_generated{
        background-color: #df7d35;
        color:white; 
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

        $(".datepicker").datepicker({minDate: new Date()});
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
                        url: "OrderController",
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
        }
        );
    });

    function makeEditable(id) {
        document.getElementById("indent_no").disabled = false;
        document.getElementById("requested_by").disabled = false;
        document.getElementById("requested_to").disabled = false;
        document.getElementById("description").disabled = false;
        document.getElementById("payment").disabled = false;
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
    function displayReading() {


        var queryString = "task=generateDeliveryReport&survey_id=" + 1;
        var url = "OrderController?" + queryString;
        popupwin = openPopUp(url, "Mounting Type Map Details", 500, 1000);

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
                //  var url = "OrderController?task=GetItems";
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


    function openPopUpForItems() {
        console.log(JSON.stringify(json));
        var json_data_string = JSON.stringify(json);
        var encodedStringBtoA = btoa(json_data_string);
        console.log(encodedStringBtoA);
        var url = "OrderController?task=GetItems&encodedStringBtoA=" + encodedStringBtoA;
        popupwin = openPopUp(url, "", 600, 1030);
    }

    function openPopUp(url, window_name, popup_height, popup_width) {
        var popup_top_pos = (screen.availHeight / 2) - (popup_height / 2);
        var popup_left_pos = (screen.availWidth / 2) - (popup_width / 2);
        var window_features = "left=" + popup_left_pos + ", top=" + popup_top_pos + ", width=" + popup_width + ", height=" + popup_height + ", resizable=yes, scrollbars=yes, location=0, menubar=no, status=no, dependent=yes";
        return window.open(url, window_name, window_features);
    }



    function openPopUpForIndentItems(indent_table_id) {
      //  alert("resv"+indent_table_id);
        var url = "OrderController?task=GetIndentItems&indent_table_id=" + indent_table_id;
        popupwin = openPopUp(url, "", 600, 1030);
    }


    if (!document.all) {
        document.captureEvents(Event.CLICK);
    }
    document.onclick = function () {
        if (popupwin !== null && !popupwin.closed) {
            popupwin.focus();
        }
    }

    var json;
    $(function () {
        //alert("fe");
        var String_data = $('#String_data').val();
        //alert("string_data --" + String_data);
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
                    tabCell.innerHTML = '<input type="text"  name="checked_id" id="checked_id' + i + '"  value="' + json[i][col[j]] + '" required style="width:35px">';
                }
                if (j == 1) {
                    tabCell.innerHTML = '<input type="text"  name="item_name' + i + '" id="item_name' + i + '"  value="' + json[i][col[j]] + '" required>';
                }
                if (j == 2) {
                    tabCell.innerHTML = '<input type="text"  name="model' + i + '" id="model' + i + '"  value="' + json[i][col[j]] + '" required>';
                }
                if (j == 3) {
                    tabCell.innerHTML = '<input type="text"  name="req_qty' + i + '" id="req_qty' + i + '"  value="' + json[i][col[j]] + '" required style="width:35px">';
                }
                if (j == 4) {
                    tabCell.innerHTML = '<input type="text"  class="myAutocompleteClass" name="purpose' + i + '" id="purpose' + i + '" required value="' + json[i][col[j]] + '">';
                }
                if (j == 5) {
                    tabCell.innerHTML = '<input type="text" class="datepicker"  name="expected_date_time' + i + '" id="expected_date_time' + i + '" required  value="' + json[i][col[j]] + '"  >';

                }
//                else {
//                    tabCell.innerHTML = json[i][col[j]];
//                }
            }
        }
        var divContainer = document.getElementById("showData");
        divContainer.innerHTML = "";
        divContainer.appendChild(table);
    });


    function showData() {
        //alert("fe");
        var String_data = $('#String_data').val();
        //alert("string_data --" + String_data);
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
                    tabCell.innerHTML = '<input type="text"  name="checked_id" id="checked_id' + i + '"  value="' + json[i][col[j]] + '" required style="width:35px">';
                }
                if (j == 1) {
                    tabCell.innerHTML = '<input type="text"  name="item_name' + i + '" id="item_name' + i + '"  value="' + json[i][col[j]] + '" required>';
                }
                if (j == 2) {
                    tabCell.innerHTML = '<input type="text"  name="model' + i + '" id="model' + i + '"  value="' + json[i][col[j]] + '" required>';
                }
                if (j == 3) {
                    tabCell.innerHTML = '<input type="text"  name="req_qty' + i + '" id="req_qty' + i + '"  value="' + json[i][col[j]] + '" required style="width:35px">';
                }
                if (j == 4) {
                    tabCell.innerHTML = '<input type="text"  class="myAutocompleteClass" name="purpose' + i + '" id="purpose' + i + '" required value="' + json[i][col[j]] + '">';
                }
                if (j == 5) {
                    tabCell.innerHTML = '<input type="text" class="datepicker"  name="expected_date_time' + i + '" id="expected_date_time' + i + '" required  value="' + json[i][col[j]] + '"  >';

                }
            }
        }
        var divContainer = document.getElementById("showData");
        divContainer.innerHTML = "";
        divContainer.appendChild(table);
    }

    $(function () {
        var dataLength = "";
        setInterval(function () {
            var String_data = $('#String_data').val();
            //alert("string_data --" + String_data);
            var dataLength2 = "";
            var dataLength2 = String_data.length;
            dataLength = String_data.length;
            //alert("data leng -"+dataLength+" data len 22 --"+dataLength2);
            if (dataLength > dataLength2 || dataLength > 0) {//alert(121);
                showData();
            } else {
                //alert(2321);
            }
        }, 2000);
    });

    function searchIndentStatusWise(status) {
        var url = "OrderController?action1=searchIndentStatusWise&status=" + status;
        window.open(url, "_self");
    }

</script>


<section>
    <div class="container-fluid page_heading sectionPadding35">
        <h1>Order Form</h1>
    </div>
</section>


<section>
    <div class="container organizationBox">
        <div class="headBox" style="background-color: #6D9FBD">
            <c:forEach var="beanType" items="${requestScope['status_list']}"
                       varStatus="loopCounter">
                <label style="color:white;margin-left: 20px">${beanType.status}</label>
                <input type="checkbox" name="search_status" id="search_status" value="${beanType.status}" 
                       onclick="searchIndentStatusWise('${beanType.status}')"> 

            </c:forEach>
            <label style="color:white;margin-left: 20px">All</label>
            <input type="checkbox" name="search_status" id="search_status" value="All" 
                   onclick="searchIndentStatusWise('All')"> 
        </div>
        <form name="form3" method="POST" action="OrderController" >
            <div class="row mt-3 myTable">
                <input type="date" style="height:38px" placeholder="Search.." name="search_by_date">
                <button type="submit" class="btn normalBtn" name="submit_search">Search</button>
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
            <div class="row mt-3 myTable">
                <div class="col-md-12">
                    <div class="table-responsive verticleScroll">
                        <table class="table table-striped table-bordered" id="mytable" style="width:100%" data-page-length='6'>
                            <thead>
                                <tr>
                                    <th>S.No.</th>
                                    <th>Order No.</th>
                                    <th>Requested To</th>
                                    <th>Date Time</th>

                                    <th>Description</th>
                                    <th>Status</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach var="beanType" items="${requestScope['list']}"
                                           varStatus="loopCounter">
                                    <tr
                                        onclick="openPopUpForIndentItems('${beanType.indent_table_id}');">
                                        <td>${loopCounter.count }</td>
                                        <td id="${loopCounter.count }">${beanType.indent_no}</td>
                                        <td id="${loopCounter.count }">${beanType.requested_to}</td>
                                        <td id="${loopCounter.count }">${beanType.date_time}</td>
                                        <td id="${loopCounter.count }">${beanType.description}</td>      
                                        <c:choose>
                                            <c:when test="${beanType.status =='Delivered'}">
                                                <td id="${loopCounter.count }" class="delivered"><b>${beanType.status}</b>
                                                </td>
                                            </c:when>

                                            <c:when test="${beanType.status =='Delivery Challan Generated'}">
                                                <td id="${loopCounter.count }" class="delivery_challan_generated"><b>${beanType.status}</b>
                                                </td>
                                            </c:when>

                                            <c:when test="${beanType.status =='Less Stock'}">
                                                <td id="${loopCounter.count }" class="not_in_stock"><b>${beanType.status}</b>
                                                </td>
                                            </c:when>
                                            <c:when test="${beanType.status =='Approved'}">
                                                <td id="${loopCounter.count }" class="approved"><b>Approved</b>
                                                </td>
                                            </c:when>
                                            <c:when test="${beanType.status =='Pending'}">   
                                                <td id="${loopCounter.count }" class="pending"><b>${beanType.status}</b>
                                                </td>
                                            </c:when>
                                            <c:when test="${beanType.status =='Payment Pending'}">
                                                <td id="${loopCounter.count }" class="PaymentPending"><b>${beanType.status}</b>
                                                </td>
                                            </c:when>
                                            <c:when test="${beanType.status =='Payment Success'}">
                                                <td id="${loopCounter.count }" class="PaymentSuccess"><b>${beanType.status}</b>
                                                </td>
                                            </c:when>
                                            <c:when test="${beanType.status =='Denied'}">
                                                <td id="${loopCounter.count }" class="denied"><b>${beanType.status}</b>
                                                </td>
                                            </c:when>
                                            <c:otherwise>
                                                <td id="${loopCounter.count }">${beanType.status}</td>
                                            </c:otherwise>
                                        </c:choose>
                                        <!--     <c:choose>
                                            <c:when test="${(beanType.status == 'Payment Pending')}">
    
                                                <td id="${loopCounter.count }"><input type="button" id="status${beanType.indent_item_id}" class="selected" name="item_status${beanType.indent_item_id}" value="MakePayment">
                                                </td>
                                            </c:when>
    
                                        </c:choose>
                                        <c:choose>
                                            <c:when test="${(beanType.status == 'Payment Success')}">
    
                                                <td id="${loopCounter.count }"><input type="button" class="selected"  id="status${beanType.indent_item_id}"   name="item_status${beanType.indent_item_id}" value="Track Order">
                                                </td>
                                            </c:when>
    
                                        </c:choose>-->

                                    </tr>

                                </c:forEach>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
        </div>
    </section>
</c:if>






<section class="marginTop30">
    <div class="container organizationBox">
        <div class="headBox">
            <h5 class="">Data Entry</h5>
        </div>
        <form name="form2" method="POST" action="OrderController" onsubmit="return verify()" >
            <div class="row mt-3">
                <div class="col-md-3">
                    <div class="">
                        <div class="form-group">
                            <label>Order No.<span class="text-danger">*</span></label>

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

                </div>


                <div class="col-md-12">
                    <div class="">
                        <div class="form-group">
                            <label>Payment Status</label>
                            <select id="payment" name="payment" required>
                                <option value="">Select</option>
                                <option value="advancedpayment">Advanced Payment</option>
                                <option value="credit">Credit</option>
                                <option value="part">Parts</option>
                            </select>
                            <!--                            <input class="form-control myTextArea" id="payment" name="payment"  disabled>${description}</input>
                            -->
                        </div>
                    </div>
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
                    <c:if test="${myfn:isContainPrivileges2(loggedUser,'OrderController','insert') eq 'True'}">                    
                        <input type="reset" class="btn normalBtn" name="task" id="new" value="New" onclick="makeEditable(id)">
                    </c:if>
                    <c:if test="${isSelectPriv eq 'Y'}">
                        <input type="button" class="btn normalBtn" name="task" id="select" value="Select Items" onclick="setStatus(id);
                                openPopUpForItems()" disabled=""></c:if>
                    <c:if test="${myfn:isContainPrivileges2(loggedUser,'OrderController','insert') eq 'True'}">
                        <input type="submit" class="btn normalBtn" name="task" id="save" value="Send Order" disabled="" onclick="setStatus(id);">
                    </c:if>
                </div>
            </div>
        </form>
    </div>
</section>

<%@include file="../layout/footer.jsp" %>

