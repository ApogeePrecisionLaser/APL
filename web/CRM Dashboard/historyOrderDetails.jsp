<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@include file="/CRM Dashboard/CRM_header.jsp" %>



<div class="content-wrapper" id="contentWrapper">
    <section class="content-header">
        <div class="container-fluid">
            <div class="row mb-2">
                <div class="col-sm-6">
                    <div class="d-flex">
                        <div>
                            <a href="OrdersHistoryController" class="btn btn-primary myNewLinkBtn">Back</a>
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
                            <div class="table-responsive tableScrollWrap" >
                                <table class="table table-striped1 mainTable" id="mytable1" >
                                    <thead>
                                        <tr>
                                            <th>S.No.</th>
                                            <th>Image</th>
                                            <th>Name</th>
                                            <th>Category</th>
                                            <th>Req Qty</th>
                                            <th>Approved Qty</th>
                                            <th>Delivered Qty</th>
                                            <th>MRP Price</th>
                                            <th>Discount Percent</th>
                                            <th>Discount Price</th>
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
                                        <td>
                                            <img class="orderImg img-fluid${loopCounter.count}" 
                                                 src="" width="50px" height="55px">
                                        </td>
                                        <td>${beanType.model}</td>
                                        <td>${beanType.item_name}</td>
                                        <td>${beanType.required_qty}</td>
                                        <td>${beanType.approved_qty}</td>
                                        <td>${beanType.delivered_qty}</td>
                                        <td>${beanType.basic_price}</td>
                                        <td>${beanType.discount_percent}</td>
                                        <td>${beanType.discount_price}</td>                        
                                        </tr>
                                    </c:forEach>

                                    <!--                                        <tr>
                                                                                <td colspan="4"></td>
                                                                                <td class="font-weight-bold fontSeventeen">Discount</td>
                                                                                <td class="font-weight-bold fontSeventeen">?50,000</td>                    
                                                                            </tr>-->
                                    <tr class="darkBlueBg">
                                        <td colspan="6"></td>
                                        <td class="font-weight-bold fontSeventeen text-white py-3">Total Amount</td>
                                        <td class="font-weight-bold fontSeventeen text-white py-3">Rs. ${total_amount}</td>                    
                                        <td class="font-weight-bold fontSeventeen text-white py-3">Rs. ${total_discount_percent}</td>                    
                                        <td class="font-weight-bold fontSeventeen text-white py-3">Rs. ${total_discount_price}</td>                    
                                    </tr>                    
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

    $(function () {
        var count = $('#count').val();
        var total_price = 0;
        $('.counting').text(count);
        for (var j = 0; j < count; j++) {
            var image_path = $('#image_path' + (j + 1)).val();
            var image_name = $('#image_name' + (j + 1)).val();

            var image = image_path + image_name;
            if (image != "") {
                image = image.replace(/\\/g, "/");
            }
            $('.img-fluid' + (j + 1)).attr("src", "http://120.138.10.146:8080/APL/DealersOrderController?getImage=" + image + "");

        }


    });
</script>
