<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<%@include file="/CRM Dashboard/CRM_header.jsp" %>

<div class="content-wrapper" id="contentWrapper">
    <section class="content-header">
        <div class="container-fluid">
            <div class="row mb-2 marginTop10">
                <div class="col-sm-6">
                    <div class="d-flex">
                        <div class="mr-2 backBtnWrap">
                            <a href="PendingOrdersController" class="btn btnBack "><i class="fas fa-chevron-circle-left"></i></a>
                        </div>
                        <div>
                            <h6 class="mt-2">Order ID : ${order_no}</h6>
                        </div>
                    </div>  
                </div>
                <div class="col-sm-6">
                    <ol class="breadcrumb float-sm-right">
                        <li class="breadcrumb-item"><a href="CRMDashboardController">Dashboard</a></li>
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
                            <div class="table-responsive tableScrollWrap noWrapTable" >
                                <table class="table table-striped1 mainTable mb-0" id="mytable1" >
                                    <thead>
                                        <tr>
                                            <th>S.No.</th>
                                            <th>Image</th>
                                            <th>Name</th>
                                            <th>Rate (<i class="fas fa-rupee-sign fontTen"></i>)</th>
                                            <th>Req Qty</th>
                                            <!--<th>Approved Qty</th>-->
                                            <th>Price (<i class="fas fa-rupee-sign fontTen"></i>)</th>
                                            <!--<th>Approved (<i class="fas fa-rupee-sign fontTen"></i>)</th>-->
                                            <th>Discount (%)</th>
                                            <th>Discounted Price (<i class="fas fa-rupee-sign fontTen"></i>)</th>
                                            <th>Status</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <c:forEach var="beanType" items="${requestScope['list']}"
                                                   varStatus="loopCounter">
                                            <tr>
                                                <td>
                                                    ${loopCounter.count}

                                                    <input type="hidden" name="image_path" id="image_path${loopCounter.count}" value="${beanType.image_path}">
                                                    <input type="hidden" name="image_name" id="image_name${loopCounter.count}" value="${beanType.image_name}">
                                                    <input type="hidden" name="count" id="count" value="${count}">
                                                </td>
                                                <td>
                                                    <img class="orderImg img-fluid${loopCounter.count}" 
                                                         src="" width="50px" height="55px">
                                                </td>
                                                <td>
                                                    <p class="mb-0">${beanType.model}</p>
                                                    <small>(${beanType.item_name})</small>
                                                </td>
                                                <td id="rate${loopCounter.count}">${beanType.basic_price / beanType.required_qty}</td>
                                                <td>${beanType.required_qty}</td>
                                                <c:if test="${beanType.item_status=='Denied'}">
                                                    <td>
                                                        <fmt:formatNumber type = "number"  maxFractionDigits = "3" 
                                                                          value =  "0" />
                                                    </td>
                                                </c:if>
                                                <c:if test="${beanType.item_status!='Denied'}">
                                                    <td id="price${loopCounter.count}">${beanType.basic_price}</td>
                                                </c:if>
                                                <td>${beanType.discount_percent}</td>
                                                <td id="discount_price${loopCounter.count}">
                                                    ${beanType.discount_price}
                                                </td>      
                                                <c:if test="${beanType.item_status=='Approved'}">
                                                    <td  class="fontFourteen">
                                                        <i class="statusApprove">${beanType.item_status}</i>
                                                    </td>
                                                </c:if>
                                                <c:if test="${beanType.item_status=='Denied'}">
                                                    <td  class="fontFourteen">
                                                        <i class="statusDisapprove">${beanType.item_status}</i>
                                                    </td>
                                                </c:if>
                                                <c:if test="${beanType.item_status=='Pending'}">
                                                    <td  class="fontFourteen">
                                                        <i class="statusPending">${beanType.item_status}</i>
                                                    </td>               
                                                </c:if>
                                                <c:if test="${beanType.item_status=='Payment Done'}">
                                                    <td  class="fontFourteen">
                                                        <i class="statusPaymentDone">${beanType.item_status}</i>
                                                    </td>
                                                </c:if>
                                            </tr>
                                        </c:forEach>

                                        <!--                                        <tr>
                                                                                    <td colspan="4"></td>
                                                                                    <td class="font-weight-bold fontSeventeen">Discount</td>
                                                                                    <td class="font-weight-bold fontSeventeen">?50,000</td>                    
                                                                                </tr>-->
                                        <tr class="darkBlueBg">
                                            <td colspan="4"></td>
                                            <td class="totalValue text-white py-2">Total Amount</td>
                                            <td class="totalValue text-white py-2" id="total_amount">${total_amount}</td>                    
                                            <td class="totalValue text-white py-2" >${total_discount_percent}</td>                    
                                            <td class="totalValue text-white py-2" id="total_discount_price">${total_discount_price}</td>
                                            <td></td>
                                        </tr>                    
                                    </tbody>
                                </table>
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
            $('.img-fluid' + (j + 1)).attr("src", "http://" + IMAGE_URL + "/APL/DealersOrderController?getImage=" + image + "");
            
            var rate = $('#rate' + (j + 1)).text();
            var price = $('#price' + (j + 1)).text();
            var discount_price = $('#discount_price' + (j + 1)).text();
            var rate1 = convertToCommaSeperate(rate);
            var price1 = convertToCommaSeperate(price);
            var discount_price1 = convertToCommaSeperate(discount_price);
            $('#rate' + (j + 1)).text(rate1);
            $('#price' + (j + 1)).text(price1);
            $('#discount_price' + (j + 1)).text(discount_price1);
        }

        var total_amount = $('#total_amount').text();
        var total_discount_price = $('#total_discount_price').text();
        var total_amount1 = convertToCommaSeperate(total_amount);
        var total_discount_price1 = convertToCommaSeperate(total_discount_price);
        $('#total_amount').text(total_amount1);
        $('#total_discount_price').text(total_discount_price1);
    });

    function convertToCommaSeperate(x) {
        x = x.toString();
        var afterPoint = '';
        if (x.indexOf('.') > 0)
            afterPoint = x.substring(x.indexOf('.'), x.length);
        x = Math.floor(x);
        x = x.toString();
        var lastThree = x.substring(x.length - 3);
        var otherNumbers = x.substring(0, x.length - 3);
        if (otherNumbers != '')
            lastThree = ',' + lastThree;
        var res = otherNumbers.replace(/\B(?=(\d{2})+(?!\d))/g, ",") + lastThree + afterPoint;

        return res;
    }

</script>
