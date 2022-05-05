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
                <div class="col-md-7">
                    <div class="d-flex">
                        <div class="alert alert-success alert-dismissible myAlertBox mb-0" style="display:none"  id="msg_success">
                            <button type="button" class="close" data-dismiss="alert">&times;</button>
                            <strong>Success!</strong> New Demand Note created successfully.
                        </div>
                        <div class="alert alert-danger alert-dismissible myAlertBox mb-0" style="display:none" id="msg_danger">
                            <button type="button" class="close" data-dismiss="alert">&times;</button>
                            <strong>Oops!</strong> Something went wrong.
                        </div>
                    </div>
                </div>
                <div class="col-md-5">
                    <div class="d-flex">                        
                        <div class="cartCountWrap d-flex ml-auto">
                            <a href="DemandNoteController?task=cart" >
                                <div><i class="fas fa-cart-plus"></i></div>
                                <div class="counting">${cart_count}</div>
                            </a>
                        </div>
                    </div>                    
                </div>
                <c:if test="${role=='Admin' || role=='Super Admin'}">
                    <div class="mt-3" style="width: 400px">
                        <form action="DemandNoteController" method="post" class="d-flex">
                            <input type="text" name="org_office" id="org_office" placeholder="Select Org Office" class="form-control rounded-0" value="${org_office}">
                            <input type="hidden" name="org_office" id="org_office_name" value="${org_office}">
                            <input type="submit" name="task" value="Search" class="btn btn-primary myNewLinkBtn">
                        </form>
                    </div> 
                </c:if>
            </div>
        </div>

        <div class="container-fluid">
            <div class="row mt-3" >
                <div class="col-md-12">
                    <div class="card card-primary card-outline">            
                        <div class="card-body">
                            <div>
                                <div class="table-responsive" >
                                    <table class="table mainTable" id="mytable">
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
                                                <th>Action</th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <c:forEach var="beanType" items="${requestScope['list']}"
                                                       varStatus="loopCounter">
                                                <tr style="background-color: ${beanType.color}">
                                                    <td class="fontFourteen">${loopCounter.count}</td>
                                                    <td class="fontFourteen">${beanType.item_name}</td>
                                                    <td class="fontFourteen">${beanType.model}</td>
                                                    <c:if test="${role=='Admin' || role=='Super Admin'}">
                                                        <td>${beanType.org_office_name}</td>
                                                    </c:if>
                                                    <td class="fontFourteen">${beanType.min_qty}</td>
                                                    <td class="fontFourteen">${beanType.stock_qty}</td>
                                                    <td class="fontFourteen">
                                                        <div class="quantityWrap">
                                                            <input type="number" class="form-control rounded-0 pl-1 pr-0" onblur="calculatePrice(${loopCounter.count})"
                                                                   name="qty" id="qty${loopCounter.count}">
                                                        </div>
                                                    </td>                                                    
                                                    <td class="fontFourteen d-flex">
                                                        <div>
                                                            <c:choose>
                                                                <c:when test="${org_office=='' && (role=='Admin' || role=='Super Admin')}">
                                                                    <a style="pointer-events: none;cursor: default;" onclick="addTocart('${beanType.model_id}', '${beanType.model}', '${beanType.item_names_id}', '${beanType.vendor}')" class="btn actionEdit" title="Add to Demand Note"> Add</a>
                                                                </c:when>
                                                                <c:otherwise>
                                                                    <a onclick="addToDemandNote('${beanType.model_id}', '${beanType.model}', '${beanType.item_names_id}', '${beanType.vendor}', ${loopCounter.count})" class="btn actionEdit" title="Add to Demand Note"> Add</a>
                                                                </c:otherwise>
                                                            </c:choose>
                                                        </div>
                                                    </td> 
                                                </tr>
                                            </c:forEach>
                                        </tbody>
                                    </table>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </section>
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
</script>