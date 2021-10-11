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

//    $(document).ready(function () {
//        $('#mytable tbody').on('click', 'tr', function () {
//            if ($(this).hasClass('selected_row')) {
//                $(this).removeClass('selected_row');
//            } else {
//                $("#mytable").DataTable().$(
//                        'tr.selected_row').removeClass(
//                        'selected_row');
//                $(this).addClass('selected_row');
//            }
//        });
//    });

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
                        url: "ApproveIndentController",
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

    function approveIndent(indent_table_id, indent_item_id) {
        var status = $('#status' + indent_item_id).val();
        if (status == "") {
            alert("Please Select status");
            $('#status' + indent_item_id).focus();
            return false;
        }
        var approved_qty = $('#approved_qty' + indent_item_id).val();
        // alert(status);
        $.ajax({
            url: "ApproveIndentController",
            dataType: "json",
            data: {
                task: "approveIndent",
                indent_table_id: indent_table_id,
                indent_item_id: indent_item_id,
                status: status,
                approved_qty: approved_qty
            },
            success: function (response) {
                console.log(response.message);
                window.location.reload();
            }
        });
    }


</script>


<section>
    <div class="container-fluid page_heading sectionPadding35">
        <h1>Approve Indent</h1>
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
                                <td style="display:none"></td>
                                <td style="display:none"></td>
                                <th>Indent No.</th>
                                <th>Requested By</th>
                                <th>Date Time</th>
                                <th>Item Name</th>
                                <th>Required Qty</th>
                                <th>Approved Qty</th>
                                <th>Status</th>
                                <th>Purpose</th>
                                <th>Expected Date</th>
                                <th>Description</th>
                                <th></th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="beanType" items="${requestScope['list']}"
                                       varStatus="loopCounter">
                                <tr>
                                    <td>${loopCounter.count }</td>
                                    <td style="display:none"><input type="hidden" name="indent_table_id" id="indent_table_id" value="${beanType.indent_table_id}"></td>
                                    <td style="display:none"><input type="hidden" name="indent_item_id" id="indent_item_id" value="${beanType.indent_item_id}"></td>
                                    <td id="${loopCounter.count }">${beanType.indent_no}</td>
                                    <td id="${loopCounter.count }">${beanType.requested_by}</td>
                                    <td id="${loopCounter.count }">${beanType.date_time}</td>
                                    <td id="${loopCounter.count }">${beanType.item_name}</td>
                                    <td id="${loopCounter.count }">${beanType.required_qty}</td>
                                    <c:choose>
                                        <c:when test="${beanType.status =='Confirmed'}">
                                            <td id="${loopCounter.count }"><input type="text" disabled="" name="approved_qty" id="approved_qty${beanType.indent_item_id}" value="${beanType.required_qty}"></td>
                                            <td id="${loopCounter.count }"><input type="text"  disabled="" name="status" placeholder="select status" class="myAutocompleteClass" id="status${beanType.indent_item_id}" value="${beanType.status}"></td>
                                            </c:when>
                                            <c:when test="${beanType.status =='Denied'}">
                                            <td id="${loopCounter.count }"><input type="text" disabled="" name="approved_qty" id="approved_qty${beanType.indent_item_id}" value="${beanType.required_qty}"></td>
                                            <td id="${loopCounter.count }"><input type="text"  disabled="" name="status" placeholder="select status" class="myAutocompleteClass" id="status${beanType.indent_item_id}" value="${beanType.status}"></td>
                                            </c:when>
                                            <c:when test="${beanType.status =='Delivered'}">
                                            <td id="${loopCounter.count }"><input type="text" disabled="" name="approved_qty" id="approved_qty${beanType.indent_item_id}" value="${beanType.required_qty}"></td>
                                            <td id="${loopCounter.count }"><input type="text"  disabled="" name="status" placeholder="select status" class="myAutocompleteClass" id="status${beanType.indent_item_id}" value="${beanType.status}"></td>
                                            </c:when>
                                            <c:when test="${beanType.status =='Not In Stock'}">
                                            <td id="${loopCounter.count }"><input type="text" disabled="" name="approved_qty" id="approved_qty${beanType.indent_item_id}" value="${beanType.required_qty}"></td>
                                            <td id="${loopCounter.count }"><input type="text"  disabled="" name="status" placeholder="select status" class="myAutocompleteClass" id="status${beanType.indent_item_id}" value="${beanType.status}"></td>
                                            </c:when>
                                            <c:otherwise>
                                            <td id="${loopCounter.count }"><input type="text" name="approved_qty" id="approved_qty${beanType.indent_item_id}" value="${beanType.required_qty}"></td>
                                            <td id="${loopCounter.count }"><input type="text" name="status" class="myAutocompleteClass" placeholder="select status" id="status${beanType.indent_item_id}" value=""></td>
                                            </c:otherwise>
                                        </c:choose>

                                    <td id="${loopCounter.count }">${beanType.purpose}</td>
                                    <td id="${loopCounter.count }">${beanType.expected_date_time}</td>
                                    <td id="${loopCounter.count }">${beanType.description}</td>  

                                    <c:choose>
                                        <c:when test="${beanType.status =='Request Sent'}">
                                            <td id="${loopCounter.count }">
                                                <input type="button" class="btn btn-info" value="Approve/Denied Indent" 
                                                       onclick="approveIndent(${beanType.indent_table_id},${beanType.indent_item_id})">
                                            </td>  
                                        </c:when>
                                        <c:when test="${beanType.status =='Delivered'}">
                                            <td id="${loopCounter.count }"><a primary href="" style="pointer-events: none;background-color: #28a745;color:white" class="btn">${beanType.status}</a></td>
                                            </c:when>

                                        <c:when test="${beanType.status =='Not In Stock'}">
                                            <td id="${loopCounter.count }"><a style="pointer-events: none;background-color: #d587c8;color:white" href="" class="btn">${beanType.status}</a>
                                            </td>
                                        </c:when>
                                        <c:when test="${beanType.status =='Confirmed'}">
                                            <td id="${loopCounter.count }"><a style="pointer-events: none;background-color: #2e6da4;color:white" href="" class="btn">Approved</a></td>  
                                        </c:when> 
                                        <c:when test="${beanType.status =='Denied'}">
                                            <td id="${loopCounter.count }"><a style="pointer-events: none;background-color: #dc3545;color:white" href="" class="btn">Denied</a></td>  
                                        </c:when>
                                        <c:otherwise>
                                            <td id="${loopCounter.count }"></td>  
                                        </c:otherwise>
                                    </c:choose>
                                </tr>

                            </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>
</section>


<%@include file="../layout/footer.jsp" %>

