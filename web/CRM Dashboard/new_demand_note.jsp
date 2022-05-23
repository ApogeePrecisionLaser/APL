<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@include file="/CRM Dashboard/CRM_header.jsp" %>
<style>
    .quantityWrap input{
        max-width: 80px;
        height: 25px;
    }
</style>

<div class="content-wrapper position-relative pt-3" id="contentWrapper">
    <section class="content">
        <div class="container-fluid">
            <div class="row">               
                <div class="col-sm-6">
                    <div class="d-flex">
                        <c:if test="${role=='Incharge'}">
                            <div>
                                <a href="DemandNoteController" class="btn btn-primary myNewLinkBtn">View All Demand Notes</a>
                            </div>
                        </c:if>
                        <c:if test="${not empty message}">
                            <c:if test="${msgBgColor=='green'}">
                                <div class="alert alert-success alert-dismissible myAlertBox"  id="msg" >
                                    <button type="button" class="close" data-dismiss="alert">&times;</button>
                                    <strong>Success!</strong> ${message}

                                </div>
                            </c:if>
                            <c:if test="${msgBgColor=='red'}">
                                <div class="alert alert-danger alert-dismissible myAlertBox" id="msg" >
                                    <button type="button" class="close" data-dismiss="alert">&times;</button>
                                    <strong>OOps!</strong> ${message}
                                </div>
                            </c:if>
                        </c:if>
                    </div>  
                </div>
            </div>
            
            
          
            

            <div class="container-fluid">
                <div class="row mt-3" >
                    <div class="col-md-12">
                        <div class="card card-primary card-outline">            
                            <div class="card-body">
                                <div>
                                    <div class="table-responsive" >
                                        <table class="table mainTable" id="tableHeight">
                                            <thead>
                                                <tr>
                                                    <th>Sr. No.</th>
                                                    <th>Product</th>
                                                    <th>Model</th>
                                                        <c:if test="${role=='Admin' || role=='Super Admin'}">
                                                        <th>Org Office</th>
                                                        </c:if>
                                                    <th>Min Qty</th>
                                                    <th>Available Qty</th>
                                                    <th>Quantity</th>
                                                    <!--<th>Action</th>-->
                                                </tr>
                                            </thead>
                                            <tbody>
                                                <c:forEach var="beanType" items="${requestScope['list']}"
                                                           varStatus="loopCounter">
                                                    <tr style="background-color: ${beanType.color}">
                                                        <td class="fontFourteen">${loopCounter.count}</td>
                                                        <td class="fontFourteen"  id="item_name${loopCounter.count}">${beanType.item_name}
                                                            <input type="hidden" class="form-control rounded-0 pl-1 pr-0"
                                                                   name="item_names_id" id="item_names_id${loopCounter.count}" value="${beanType.item_names_id}">
                                                        </td>
                                                        <td class="fontFourteen" id="model${loopCounter.count}"> ${beanType.model}
                                                            <input type="hidden" class="form-control rounded-0 pl-1 pr-0"
                                                                   name="model_id" id="model_id${loopCounter.count}" value="${beanType.model_id}">
                                                        </td>
                                                        <c:if test="${role=='Admin' || role=='Super Admin'}">
                                                            <td>${beanType.org_office_name}</td>
                                                        </c:if>
                                                        <td class="fontFourteen">${beanType.min_qty}</td>
                                                        <td class="fontFourteen">${beanType.stock_qty}</td>
                                                        <td class="fontFourteen">
                                                            <div class="quantityWrap">
                                                                <input type="number" class="form-control rounded-0 pl-1 pr-0"
                                                                       name="qty" id="qty${loopCounter.count}" onblur="checkCheckBoxes(${loopCounter.count})">

                                                                <input type="checkbox" style="display: none" name="product_check" id="product_check${loopCounter.count}" class="product_check" 
                                                                       value="${loopCounter.count}">
                                                            </div>
                                                        </td>
                                                        <!--                                                    <td class="fontFourteen d-flex">
                                                                                                                <div>
                                                        <c:choose>
                                                            <c:when test="${org_office=='' && (role=='Admin' || role=='Super Admin')}">
                                                                <a style="pointer-events: none;cursor: default;" onclick="addToDemandNote('${beanType.model_id}', '${beanType.model}', '${beanType.item_names_id}', ${loopCounter.count})" class="btn actionEdit" title="Add to Demand Note"> Add</a>
                                                            </c:when>
                                                            <c:otherwise>
                                                                <a onclick="addToDemandNote('${beanType.model_id}', '${beanType.model}', '${beanType.item_names_id}', ${loopCounter.count})" class="btn actionEdit" title="Add to Demand Note"> Add</a>
                                                            </c:otherwise>
                                                        </c:choose>
                                                    </div>
                                                </td>-->
                                                    </tr>
                                                </c:forEach>
                                            </tbody>
                                        </table>
                                    </div>
                                </div>
                                <div class="text-right mt-3">
                                    <button type="button" class="btn btn-primary myThemeBtn open-modal" data-toggle="modal" data-target="#myModal">Confirm</button>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
    </section>
</div>


<div class="modal" id="myModal">
    <div class="modal-dialog modal-lg">
        <div class="modal-content">
            <div class="modal-header rounded-0 bg-voilet py-2">
                <h4 class="modal-title text-white" style="font-size: 22px;">Demand Draft</h4>
                <button type="button" class="close text-white" data-dismiss="modal">&times;</button>
            </div>
            <div class="modal-body">
                <form action="DemandNoteController" method="post">
                    <div>
                        <div class="table-responsive" >
                            <table class="table mainTable">
                                <thead>
                                    <tr>
                                        <th>Sr. No.</th>
                                        <th>Product</th>
                                        <th>Model</th> 
                                        <th>Qty</th>
                                        <th>Action</th>
                                    </tr>
                                </thead>
                                <tbody id="test-body">
                                    <!--                                <tr id="row0" class="test-body-row">
                                                                        <td class="fontFourteen">1</td>
                                                                        <td class="fontFourteen">Receiver</td>
                                                                        <td class="fontFourteen">CB-200</td>
                                                                        <td class="fontFourteen">10</td>
                                                                        <td><a onclick="return confirm('Are you sure you want to delete this item?');" href="#" class="btn far fa-trash-alt actionDelete" title="Delete Message"></a></td>
                                                                    </tr>-->
                                </tbody>
                            </table>
                        </div>
                        <div class="text-right mt-1">
                            <input type="submit" class="btn btn-success myThemeBtn" name="task" value="Submit">
                        </div>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>

<%@include file="/CRM Dashboard/CRM_footer.jsp" %>

<script>
    var row = 1;
    $(document).on("click", "#add-row", function () {
        var new_row = '<tr id="row' + row + '"><td class="pl-0"><input name="pr_name" id="pr_name' + row + '" type="text" class="form-control rounded-0 myAutocompleteClass" /><input name="inventory_id' + row + '" id="inventory_id' + row + '" type="hidden" class="form-control rounded-0 " /></td><td class="pl-0"><input name="pr_modelName' + row + '" id="pr_modelName' + row + '" type="text" onblur="getData(this.value, this.id)" class="form-control rounded-0 myAutocompleteClass" /></td><td class="pl-0"><input name="pr_model' + row + '" id="pr_model' + row + '" type="text" class="form-control rounded-0" /></td><td class="pl-0"><input name="pr_stock_qty' + row + '" id="pr_stock_qty' + row + '" type="text" class="form-control rounded-0" disabled=""/></td><td class="pl-0"><input name="pr_qty' + row + '" id="pr_qty' + row + '" type="text" class="form-control rounded-0" required=""/></td><td class="pl-0"><button class="delete-row btn btn-sm rounded-0 btn-danger"><i class="far fa-trash-alt"></i></button></td></tr>';
        $('#test-body').append(new_row);
        row++;
        return false;
    });
    $(document).on("click", ".delete-row", function () {
        if (row > 1) {
            $(this).closest('tr').remove();
            row--;
        }
        return false;
    });

    $("#org_office").autocomplete({
        source: function (request, response) {
            var random = $('#org_office').val();
            $.ajax({
                url: "PurchaseOrdersController",
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

    function addToDemandNote(model_id, model, item_names_id, count) {
        var qty = $('#qty' + count).val();
        var org_office_name = $('#org_office_name').val();

        if (qty == '') {
            alert("Please Enter Quantity!.");
            return  false;
        }
        if (qty <= 0) {
            alert("Please Enter valid qty!.");
            return  false;
        }

        $.ajax({
            url: "DemandNoteController",
            dataType: "json",
            data: {task: "addToDemandNote", model_id: model_id, model: model, item_names_id: item_names_id, qty: qty, org_office_name: org_office_name},
            success: function (data) {
                console.log(data);
                if (data.success_msg != '') {
                    $('.counting').text(data.list);
                    $('#msg_success').text(data.success_msg);
                    $('#msg_success').show();
                    $('#msg_danger').hide();
                    setTimeout(function () {
                        $('#msg_success').fadeOut('fast');
                    }, 2000);
                } else {
                    $('.myAlertBox').hide();
                    $('#msg_danger').text(data.error_msg);
                    $('#msg_danger').show();
                    $('#msg_success').hide();
                    setTimeout(function () {
                        $('#msg_danger').fadeOut('fast');
                    }, 2000);
                }
//                window.location.reload();
            }
        });
    }

    $(document).on("click", ".open-modal", function () {
        var length = 0;

        $('#test-body').empty();
        var rows = 0;
        $('input.product_check:checkbox:checked').each(function () {
            var sThisVal = $(this).val();
            var item_name = $('#item_name' + sThisVal).text();
            var model_name = $('#model' + sThisVal).text();
            var qty = $('#qty' + sThisVal).val();
            var item_names_id = $('#item_names_id' + sThisVal).val();
            var model_id = $('#model_id' + sThisVal).val();

            $.ajax({
                url: "InventoryController",
                dataType: "json",
                data: {action1: "getVendorName", item_name: item_name},
                success: function (data) {
                    console.log(data);
                    $('#myModal').modal('show');
                    $('#test-body').append('<tr id="row' + rows + '"><td>' + (rows + 1) + '</td><td class="pl-0"><input name="pr_item_names_id" id="pr_item_names_id' + rows + '" type="hidden" class="form-control rounded-0 myAutocompleteClass" value="' + item_names_id + '" />' + item_name + '</td><td class="pl-0"><input name="pr_model_id" id="pr_model_id' + rows + '" type="hidden" class="form-control rounded-0" value="' + model_id + '"/>' + model_name + '</td><td class="pl-0"><input name="pr_qty" id="pr_qty' + rows + '" type="hidden" value=' + qty + ' class="form-control rounded-0" required="" />' + qty + '</td><td class="pl-0"><button class="delete-row btn btn-sm rounded-0 btn-danger"><i class="fa fa-trash"></i></button></td></tr>');
                    rows++;
                }
            });
            row++;
        });

        return false;
    });


    function checkCheckBoxes(count) {
        var qty = $('#qty' + count).val();
        if (qty != '') {
            $('#product_check' + count).attr('checked', true);
        } else {
            $('#product_check' + count).attr('checked', false);
        }
    }
    
    
    
    $('#tableHeight').DataTable( {
        scrollY:        '50vh',
        scrollCollapse: true,
        paging:         false
    } );
</script>


