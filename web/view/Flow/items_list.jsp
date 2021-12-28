<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
<link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/1.10.25/css/jquery.dataTables.min.css">
<link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/buttons/1.7.1/css/buttons.dataTables.min.css">
<link rel="stylesheet" type="text/css" href="assets/css/jquery-ui.css">
<link rel="stylesheet" type="text/css" href="assets/css/style.css">

<script src="//code.jquery.com/jquery-1.10.2.js"></script>
<script src="//code.jquery.com/ui/1.10.4/jquery-ui.js"></script>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script src="//code.jquery.com/jquery-1.10.2.js"></script>
<script src="//code.jquery.com/ui/1.10.4/jquery-ui.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/select2@4.1.0-rc.0/dist/js/select2.min.js"></script>
<script src="assets/JS/jquery-ui.js"></script>
<script src="assets/JS/moment.min.js"></script>
<script src="https://cdn.datatables.net/1.10.25/js/jquery.dataTables.min.js"></script>
<script src="https://cdn.datatables.net/buttons/1.7.1/js/dataTables.buttons.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/jszip/3.1.3/jszip.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/pdfmake/0.1.53/pdfmake.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/pdfmake/0.1.53/vfs_fonts.js"></script>
<script src="https://cdn.datatables.net/buttons/1.7.1/js/buttons.html5.min.js"></script>
<script src="https://cdn.datatables.net/buttons/1.7.1/js/buttons.print.min.js"></script>

<link rel="stylesheet" type="text/css" href="collapsetable/css/bootstrap.min.css">
<script src="collapsetable/js/javascript.js"></script>
<script src="https://cdn.datatables.net/buttons/1.7.1/js/buttons.colVis.min.js"></script>
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
        }
        );
    });
    function enableFields(i) {
        document.getElementById("required_qty" + i).disabled = false;
        document.getElementById("expected_date_time" + i).disabled = false;
        document.getElementById("purpose" + i).disabled = false;
    }



    function closeSelf() {
        var prevVal = opener.document.getElementById("String_data").value;
        var checkedValue = null;
        var inputElements = document.getElementsByClassName('messageCheckbox');
        var data = "";
        for (var i = 0; i < inputElements.length; i++) {
            if (inputElements[i].checked) {
                checkedValue = inputElements[i].value;
                var item_name = $('#item_name' + checkedValue + '').val();
                var req_qty = $('#required_qty' + checkedValue + '').val();
                var purpose = $('#purpose' + checkedValue + '').val();
                var expected_date_time = $('#expected_date_time' + checkedValue + '').val();

                data += '{"checkedValue":"' + checkedValue + '","item_name":"' + item_name + '", "req_qty":' + req_qty + ', "purpose":"' + purpose + '","expected_date_time":"' + expected_date_time + '"},';

            } else {
                opener.document.getElementById("String_data").value = "";
            }
        }
        if (prevVal != "") {
            data = prevVal + data;
        }
        opener.document.getElementById("String_data").value = data;

        opener.location.reload();
        window.close();
    }



</script>



<section class="marginTop30">
    <div class="container organizationBox">
        <div class="headBox">
            <h5 class="">Items List</h5>
        </div>
        <form name="form2"  >
            <div class="row mt-3">

                <table id="tree-table" class="table table-hover table-bordered" data-page-length='6'>
                    <tbody>
                    <th>Item Name</th>
                    <th></th>
                    <th></th>
                    <th></th>
                    <th></th>

                    <c:forEach var="beanType" items="${requestScope['list']}"
                               varStatus="loopCounter">
                        <c:choose>
                            <c:when test="${beanType.superp =='Y'}">
                                <tr data-id="${beanType.item_names_id}" data-parent="${beanType.parent_item_id}" data-level="${beanType.generation}">

                                    <td id="${beanType.item_names_id}" data-column="name">${beanType.item_name}</td>

                                <input type="hidden" name="item_name${beanType.item_names_id}" id="item_name${beanType.item_names_id}" value="${beanType.item_name}" disabled>

                                <c:choose>
                                    <c:when test="${beanType.checkedValue ==beanType.item_names_id}">
                                        <td> <input type="checkbox" class="Checkbox" value="${beanType.item_names_id}" name="checkbox${beanType.item_names_id}" id="checkbox${beanType.item_names_id}" 
                                                    onclick="enableFields(${beanType.item_names_id})" checked="" disabled=""></td>
                                        <td><input style="width:100px;font-size:13px" type="text" value="${beanType.checked_req_qty}" name="required_qty${beanType.item_names_id}" id="required_qty${beanType.item_names_id}" placeholder="Qty" disabled></td>
                                        <td><input style="width:100px;font-size:13px" type="date" value="${beanType.checked_expected_date_time}" name="expected_date_time${beanType.item_names_id}" id="expected_date_time${beanType.item_names_id}" placeholder="Qty" disabled></td>
                                        <td><input style="width:100px;font-size:13px" value="${beanType.checked_purpose}" class="myAutocompleteClass" type="text" name="purpose${beanType.item_names_id}" id="purpose${beanType.item_names_id}" placeholder="Purpose" disabled></td>
                                        </c:when>
                                        <c:otherwise>
                                        <td> <input type="checkbox" class="messageCheckbox" value="${beanType.item_names_id}" name="checkbox${beanType.item_names_id}" id="checkbox${beanType.item_names_id}" 
                                                    onclick="enableFields(${beanType.item_names_id})"></td>
                                        <td><input style="width:100px;font-size:13px" type="text" value="" name="required_qty${beanType.item_names_id}" id="required_qty${beanType.item_names_id}" placeholder="Qty" disabled></td>
                                        <td><input style="width:100px;font-size:13px" class="datepicker" type="text" value="" name="expected_date_time${beanType.item_names_id}" id="expected_date_time${beanType.item_names_id}" placeholder="Expected Date" disabled></td>
                                        <td><input style="width:100px;font-size:13px" value="" class="myAutocompleteClass" type="text" name="purpose${beanType.item_names_id}" id="purpose${beanType.item_names_id}" placeholder="Purpose" disabled></td>
                                        </c:otherwise>
                                    </c:choose>

                                </tr>
                            </c:when>

                            <c:otherwise>
                                <tr data-id="${beanType.item_names_id}" data-parent="${beanType.parent_item_id}" data-level="${beanType.generation}">
                                    <td id="${beanType.item_names_id }" data-column="name">${beanType.item_name}</td>
                                    <td></td>
                                    <td></td>
                                    <td></td>
                                    <td></td>


                                </tr>
                            </c:otherwise>
                        </c:choose>

                    </c:forEach>
                    </tbody>
                </table>

            </div>      
            <hr>
            <div class="row">
                <input type="hidden" id="clickedButton" value="">
                <div class="col-md-12 text-center">                       
                    <input type="button" class="btn normalBtn" onclick="closeSelf();" name="task" id="save" value="Import to Indent">
                </div>
            </div>
        </form>
    </div>
</section>

