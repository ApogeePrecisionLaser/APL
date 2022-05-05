<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@include file="/CRM Dashboard/CRM_header.jsp" %>
<div class="content-wrapper position-relative pt-3" id="contentWrapper">
    <section class="content">
        <div class="container-fluid">
            <div class="d-flex position-relative">
                <div>
                    <a href="PurchaseOrdersController?task=cart" class="btn btn-primary myNewLinkBtn">Back</a>
                </div>
            </div>        
            <div class="row mt-3">
                <div class="col-md-12">
                    <div class="card card-primary card-outline">            
                        <div class="card-body">
                            <form action="PurchaseOrdersController" method="post">
                                <div>
                                    <div class="table-responsive" >
                                        <table class="table mainTable" id="mytable1">
                                            <thead>
                                                <tr>
                                                    <th>Sr. No.</th>
                                                    <th>Product</th>
                                                    <th>Model</th>
                                                    <th>Quantity</th>
                                                    <!--<th>Unit</th>-->                                    
                                                    <th>Price (<small><i class="fas fa-rupee-sign curruncyIcon"></i></small>)</th>
                                                    <!--<th>Total</th>-->
                                                    <th>Action</th>
                                                </tr>
                                            </thead>
                                            <tbody>
                                                <c:forEach var="beanType" items="${requestScope['list']}"
                                                           varStatus="loopCounter">
                                                    <tr>
                                                        <td class="fontFourteen">${loopCounter.count}</td>
                                                        <td class="fontFourteen">${beanType.item_name}
                                                            <input type="hidden" name="item_names_id" value="${beanType.item_names_id}">
                                                            <input type="hidden" name="model_id" value="${beanType.model_id}">
                                                            <input type="hidden" name="qty" value="${beanType.qty}">
                                                            <input type="hidden" name="price" value="${beanType.price}">
                                                            <input type="hidden" name="vendor_id" value="${beanType.vendor_id}">
                                                            <input type="hidden" name="org_office_id" value="${org_office_id}">
                                                        </td>
                                                        <td class="fontFourteen">${beanType.model}</td>
                                                        <td class="fontFourteen">${beanType.qty}</td>
                                                        <!--<td class="fontFourteen">Pcs</td>-->
                                                        <td class="fontFourteen">${beanType.price}</td>
                                                        <!--<td class="fontFourteen">?1,80,000</td>-->
                                                        <td class="fontFourteen d-flex">
                                                            <div>
                                                                <a href="PurchaseOrdersController?task=remove_item&vendor_id=${beanType.vendor_id}&item_names_id=${beanType.item_names_id}&model_id=${beanType.model_id}&org_office_id=${org_office_id}" onclick="return confirm('Are you sure you want to remove this item?');" class="btn actionDelete" title="Add to Cart"> Remove</a>
                                                            </div>
                                                        </td> 
                                                    </tr>
                                                </c:forEach>
                                            </tbody>
                                        </table>
                                    </div>
                                    <div class="text-right">
                                        <input type="submit" class="btn btn-primary myNewLinkBtn text-right" value="Send" name="task">
                                    </div>
                                </div>
                            </form>
                        </div>
                    </div>
                </div>

            </div>
        </div>
    </section>
</div>





<%@include file="/CRM Dashboard/CRM_footer.jsp" %>

<script>
    // $(window).load(function() {
    //   $('#loading').hide();
    // });
</script>


<script>
    $(window).on('load', function () {
        $('#loading').hide();
    })
</script>