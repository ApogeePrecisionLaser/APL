<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@include file="/CRM Dashboard/CRM_header.jsp" %>

<div class="content-wrapper" id="contentWrapper">
    <section class="content-header">
        <div class="container-fluid mt-2">
            <div class="row mb-2">
                <div class="col-sm-6">
                    <div class="d-flex">
                        <div class="mr-2 backBtnWrap">
                            <a href="QuotationController" class="btn btn-primary myNewLinkBtn">Back</a>
                        </div>
                        <div>
                            <h6 class="mt-2">Quotation No : ${quotation_no}</h6>
                        </div>

                    </div>  
                </div>
                <div class="col-sm-6">
                    <ol class="breadcrumb float-sm-right">
                        <li class="breadcrumb-item"><a href="CRMDashboardController">Dashboard</a></li>
                        <li class="breadcrumb-item active">Quotation Detail
                        </li>
                    </ol>
                </div>
            </div>
        </div>
    </section>

    <section class="content-header">
        <div class="container-fluid mt-2">
            <div class="row mb-2">
                <div class="col-md-6">
                    <div>
                        <c:if test="${button=='Enable'}">
                            <a href="QuotationController?task=viewPdF&mail=yes&quotation_no=${quotation_no}" target="_blank"
                               class="btn btn-primary myOtherSuccessBtn  fontFourteen">Send RFQ by Email</a>
                        </c:if>

                        <a href="QuotationController?task=viewPdF&&mail=no&quotation_no=${quotation_no}" target="_blank"
                           class="btn btn-primary myOtherSuccessBtn  fontFourteen">Print RFQ</a>


                        <!-- <c:if test="${button=='Disabled'}">
                            <a href="#" class="btn btn-primary myOtherBtn fontFourteen">Confirm Order</a>
                        </c:if>-->

                        <a href="QuotationController?task=discardQuotation&quotation_no=${quotation_no}"
                           class="btn btn-primary myOtherBtn fontFourteen">Discard</a>
                    </div>
                </div>
            </div>
        </div>
    </section>

    <section class="content mt-2">
        <form action="QuotationController" method="post">
            <div class="container-fluid">              
                <div class="row mt-0">
                    <div class="col-md-12">
                        <div class="card card-primary card-outline">
                            <div class="card-body">
                                <div class="table-responsive tableScrollWrap" >
                                    <table class="table table-striped1 mainTable" id="mytable1" >
                                        <thead>
                                            <tr>
                                                <th>Sr. No.</th>
                                                <th>Product Img</th>
                                                <th>Product Name</th>
                                                <th>Model Name</th>
                                                <!--<th>Model/ Part No.</th>-->
                                                <th>Quantity</th>
                                                <th>Rate (<small><i class="fas fa-rupee-sign curruncyIcon"></i></small>)</th>
                                                <th>Price (<small><i class="fas fa-rupee-sign curruncyIcon"></i></small>)</th>
                                                <th>Lead Time(Days)</th>
                                                <th>Status</th>
                                            </tr>
                                        </thead>
                                        <tbody>

                                            <!-- 
                                           
                                            -->
                                            <c:forEach var="beanType" items="${requestScope['detail']}"
                                                       varStatus="loopCounter">
                                                <tr>
                                                    <td>${loopCounter.count}</td>
                                                    <td>
                                                        <input type="hidden" name="image_path" id="image_path${loopCounter.count}" value="${beanType.image_path}">
                                                        <input type="hidden" name="image_name" id="image_name${loopCounter.count}" value="${beanType.image_name}">
                                                        <input type="hidden" name="count" id="count" value="${count}">
                                                        <input type="hidden" name="quotation_no" id="quotation_no" value="${quotation_no}">
                                                        <img class="orderImg img-fluid${loopCounter.count}" src="">
                                                    </td>
                                                    <td>${beanType.item_name}
                                                        <input type="hidden" name="item_names_id" id="item_names_id" value="${beanType.item_names_id}" >
                                                    </td>
                                                    <td>${beanType.model}
                                                        <input type="hidden" name="model_id" value="${beanType.model_id}" id="model_id" >
                                                    </td>
                                                    <!--<td>${beanType.model_no}</td>-->
                                                    <td>${beanType.qty}</td>
                                                    <td class="font-weight-bold">
                                                        <input type="text" name="rate" disabled="" id="rate${loopCounter.count}" 
                                                               style="width: 70px" class="disabled_input" value="${beanType.rate}"
                                                               onblur="calculatePrice(${beanType.qty}, this.value,${loopCounter.count})">
                                                    </td>                        
                                                    <td class="font-weight-bold" id="price_text${loopCounter.count}">
                                                        ${beanType.qty * beanType.rate}
                                                        <input type="hidden" name="price" 
                                                               id="price${loopCounter.count}" value="${beanType.qty * beanType.rate}">
                                                    </td>                        
                                                    <td>
                                                        <input type="text" name="lead_time" id="lead_time"
                                                               value="${beanType.lead_time}" disabled="" style="width: 40px"
                                                               class="disabled_input" >
                                                    </td>                        
                                                    <c:choose>
                                                        <c:when test="${beanType.status=='Pending'}">
                                                            <td class="fontFourteen"><i class="statusPending">${beanType.status}</i> </td>
                                                        </c:when>
                                                        <c:when test="${beanType.status=='Approved'}">
                                                            <td class="fontFourteen"><i class="statusApprove">${beanType.status}</i> </td>
                                                        </c:when>
                                                        <c:when test="${beanType.status=='Denied'}">
                                                            <td class="fontFourteen"><i class="statusDisapprove">${beanType.status}</i> </td>
                                                        </c:when>
                                                        <c:when test="${beanType.status=='Received Quotation'}">
                                                            <td class="fontFourteen"><i class="statusReceivedQuotation">${beanType.status}</i> </td>
                                                        </c:when>
                                                        <c:otherwise>
                                                            <td class="fontFourteen"><i class="">${beanType.status}</i> </td>
                                                        </c:otherwise>
                                                    </c:choose>
                                                </tr>
                                            </c:forEach>

                                            <tr class="darkBlueBg">
                                                <td colspan="3"></td>
                                                <td class="totalValue text-white py-2">Total Qty</td>
                                                <td class="totalValue text-white py-2">${total_qty}</td>                    
                                                <td class="totalValue text-white py-2" id="total_rate">${total_rate}</td>                    
                                                <td class="totalValue text-white py-2" id="total_price">${total_price}</td>                    
                                                <td class="totalValue text-white py-2"></td>                    
                                                <td class="totalValue text-white py-2">
                                                    <input type="button" name="edit" value="Edit" class="btn btn-primary myOtherBtn" 
                                                           onclick="enableAll()">

                                                    <input type="submit" name="task" id="update" value="Update" disabled="" 
                                                           class="btn btn-success myOtherSuccessBtn ">
                                                </td>                    
                                            </tr>  
                                        </tbody>
                                    </table>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </form>
    </section>
</div>

<%@include file="/CRM Dashboard/CRM_footer.jsp" %>

<script>
    $(function () {
        var count = $('#count').val();
        for (var j = 0; j < count; j++) {
            var image_path = $('#image_path' + (j + 1)).val();
            var image_name = $('#image_name' + (j + 1)).val();
//            alert(image_path);
//            alert(image_name);
            var image = image_path + image_name;
            if (image != "") {
                image = image.replace(/\\/g, "/");
            }
            $('.img-fluid' + (j + 1)).attr("src", "http://" + IMAGE_URL + "/APL/DealersOrderController?getImage=" + image + "");
        }
    });

    function enableAll() {
        $('.disabled_input').attr('disabled', false);
        $('#update').attr('disabled', false);
    }

    function calculatePrice(qty, rate, count) {
        $('#price_text' + count).text(qty * rate);
        $('#price' + count).val(qty * rate);
        var item_count = $('#count').val();
        var total_rate = 0;
        var total_price = 0;
        for (var i = 0; i < item_count; i++) {
            total_rate = total_rate + (parseInt(($('#rate' + (i + 1)).val())));
            total_price = total_price + (parseInt(($('#price_text' + (i + 1)).text())));
        }
        $('#total_rate').text(total_rate);
        $('#total_price').text(total_price);
    }
</script>
