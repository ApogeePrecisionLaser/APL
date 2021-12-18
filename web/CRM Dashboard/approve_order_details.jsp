<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@include file="/CRM Dashboard/CRM_header.jsp" %>



<div class="content-wrapper" id="contentWrapper">
    <section class="content-header">
        <div class="container-fluid">
            <div class="row mb-2">
                <div class="col-sm-6">
                    <div class="d-flex">
                        <div>
                            <a href="ApproveOrdersController" class="btn btn-primary myNewLinkBtn">Back</a>
                        </div>
                    </div>  
                </div>
                <div class="col-sm-6">
                    <ol class="breadcrumb float-sm-right">
                        <li class="breadcrumb-item"><a href="#">Dashboard</a></li>
                        <li class="breadcrumb-item active">Order Detail</li>
                    </ol>
                </div>
            </div>
        </div>
    </section>
    <section class="content mt-0">
        <div class="container-fluid">              
            <div class="row mt-0">
                <div class="col-md-12">
                    <div class="card card-primary card-outline">
                        <div class="card-body">
                            <form action="ApproveOrdersController" method="POST">
                                <div class="table-responsive tableScrollWrap" >
                                    <table class="table table-striped1 mainTable" id="mytable1" >
                                        <thead>
                                            <tr>
                                                <th>S.No.</th>
                                                <th>Image</th>
                                                <th>Name</th>
                                                <th>Category</th>
                                                <th>Req Qty</th>
                                                <th>Stock Qty</th>
                                                <th>Approved Qty</th>
                                                <th>MRP Price</th>
                                                <th></th>
                                                <!--<th>Discount Price</th>-->
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <c:forEach var="beanType" items="${requestScope['list']}"
                                                       varStatus="loopCounter">
                                                <tr>
                                                    <td>${loopCounter.count}</td>
                                            <input type="hidden" name="image_path" id="image_path${loopCounter.count}" value="${beanType.image_path}">
                                            <input type="hidden" name="image_name" id="image_name${loopCounter.count}" value="${beanType.image_name}">
                                            <input type="hidden" name="count" id="count" value="${count}">
                                            <input type="hidden" name="order_table_id" id="order_table_id" value="${beanType.order_table_id}">
                                            <input type="hidden" name="order_item_id" id="order_item_id" value="${beanType.order_item_id}">
                                            <input type="hidden" name="item_status" id="item_status" value="${beanType.item_status}">
                                            <input type="hidden" name="order_status" id="order_status" value="${beanType.order_status}">
                                            <td>
                                                <img class="orderImg img-fluid${loopCounter.count}" 
                                                     src="" width="50px" height="55px">
                                            </td>

                                            <td>${beanType.model}</td>
                                            <td>${beanType.item_name}</td>
                                            <td>${beanType.required_qty}</td>
                                            <td>${beanType.stock_quantity}</td>

                                            <td>

                                                <c:if test="${beanType.order_status=='Pending'}">
                                                    <input type="text" name="approved_qty${beanType.order_item_id}" value="${beanType.approved_qty}">

                                                </c:if>

                                                <c:if test="${beanType.order_status!='Pending'}">
                                                    <input type="text" name="approved_qty${beanType.order_item_id}"  disabled="" value="${beanType.approved_qty}">
                                                    <!--<input type="text" name="approved_qty${beanType.order_item_id}" hidden value="${beanType.approved_qty}">-->

                                                </c:if>
                                            </td>
                                            
                                            <td>${beanType.basic_price}</td>
                                            <td>
                                                <select class="btn btn-primary ml-3" style="width:100px" value="${beanType.item_status}"
                                                        name="item_status${beanType.order_item_id}">
                                                    <!--                                                    <option  class="btn btn-primary">Select</option>-->
                                                    <c:if test="${beanType.item_status=='Approved'}">
                                                        <option  class="btn btn-primary" value="${beanType.item_status}">Approved</option>
                                                    </c:if>
                                                    <c:if test="${beanType.item_status=='Denied'}">
                                                        <option  class="btn btn-primary" value="${beanType.item_status}"> Denied</option>
                                                    </c:if>
                                                    <c:if test="${beanType.item_status=='Pending'}">
                                                        <option  class="btn btn-primary">Select</option>
                                                        <option  class="btn btn-primary" value="Approved">Approved</option>
                                                        <option  class="btn btn-primary" value="Denied">Denied</option>

                                                    </c:if>
                                                </select>
                                            </td>
                                            </tr>
                                        </c:forEach>

                                        <tr class="darkBlueBg">
                                            <td colspan="6"></td>
                                            <td class="font-weight-bold fontSeventeen text-white py-3">Total Amount</td>
                                            <td class="font-weight-bold fontSeventeen text-white py-3">Rs. ${total_amount}</td>   
                                            <td class="font-weight-bold fontSeventeen text-white py-3">
                                                <input type="submit" name="task"  id="approved" value="Approve">
                                                <input type="submit" name="task"  id="denied" value="Denied">
                                            </td>   
                                        </tr>                    
                                        </tbody>
                                    </table>
                                </div>
                            </form>
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

    $(function () {
        var count = $('#count').val();
        var order_status = $('#order_status').val();
        if (order_status == 'Pending') {
            $('#approved').attr('disabled', false);
            $('#denied').attr('disabled', false);
        } else {
            $('#approved').attr('disabled', true);
            $('#denied').attr('disabled', true);

        }
        var total_price = 0;
        $('.counting').text(count);
        for (var j = 0; j < count; j++) {
            var image_path = $('#image_path' + (j + 1)).val();
            var image_name = $('#image_name' + (j + 1)).val();

            var image = image_path + image_name;
            if (image != "") {
                image = image.replace(/\\/g, "/");
            }
            $('.img-fluid' + (j + 1)).attr("src", "http://localhost:8080/APL/DealersOrderController?getImage=" + image + "");

        }


    });
</script>