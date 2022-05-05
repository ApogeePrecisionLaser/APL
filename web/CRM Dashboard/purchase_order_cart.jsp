<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@include file="/CRM Dashboard/CRM_header.jsp" %>

<style>
    .mainTable.table td {
        font-size: 13.25px;
        padding: 9px 8px;
        vertical-align: middle;
    }
    .table td, .table th {
        padding: 0.75rem;
        vertical-align: top;
        background-color: #f2f2f2;
        border-top: 12px solid #fff;
    }
</style>

<div class="content-wrapper position-relative pt-3" id="contentWrapper">
    <section class="content">
        <div class="container-fluid">
            <div class="d-flex position-relative">
                <div class="col-md-7">
                    <c:choose>
                        <c:when test="${type=='vendor'}">
                            <a href="PurchaseOrdersController?task=new order&type=vendor" class="btn btn-primary myNewLinkBtn">Back</a>
                        </c:when>
                        <c:otherwise>
                            <a href="PurchaseOrdersController?task=new order" class="btn btn-primary myNewLinkBtn">Back</a>
                        </c:otherwise>
                    </c:choose>
                </div>
                <div class="col-md-5">
                    <div class="d-flex">                        
                        <div class="cartCountWrap d-flex ml-auto">
                            <a href="PurchaseOrdersController?task=cart&type=${type}" >
                                <div><i class="fas fa-cart-plus"></i></div>
                                <div class="counting">${cart_count}</div>
                            </a>
                        </div>
                    </div>                    
                </div>
                <c:if test="${msg!=''}">
                    <div class="">
                        <c:if test="${msg_color=='green'}">
                            <div class="alert alert-success alert-dismissible myAlertBox mb-0" >
                                <button type="button" class="close" data-dismiss="alert">&times;</button>
                                <strong>Success!</strong>${msg}
                            </div>
                        </c:if>
                        <c:if test="${msg_color=='red'}">
                            <div class="alert alert-danger alert-dismissible myAlertBox mb-0" >
                                <button type="button" class="close" data-dismiss="alert">&times;</button>
                                <strong>Oops!</strong>${msg}
                            </div>
                        </c:if>
                    </div>

                </c:if>

            </div>        
            <div class="row mt-3">
                <div class="col-md-12">
                    <div class="card card-primary card-outline">            
                        <div class="card-body">
                            <div>
                                <div class="table-responsive" >
                                    <table class="table mainTable" id="mytable">
                                        <tbody>
                                            <c:forEach var="bean" items="${requestScope['list']}"
                                                       varStatus="loopCounter">
                                                <tr>
                                                    <td class="fontFourteen">Org Office : <b>${bean.org_office_name}</b></td>
                                                    <!--<td class="fontFourteen">VendorID : <b>${bean.org_office_code}</b></td>-->
                                                    <td class="fontFourteen">Vendor: <b>${bean.vendor}</b></td>
                                                    <td class="fontFourteen">Total Product: <b>${bean.qty}</b></td>
                                                    <td class="fontFourteen">Total Payable INR : <b>${bean.price}</b></td>
                                                    <td class="fontFourteen d-flex">
                                                        <div>
                                                            <a href="PurchaseOrdersController?task=purchase_order_placed_item&vendor_id=${bean.vendor_id}&org_office_id=${bean.org_office_id}" class="btn actionEdit" title="Add to Cart"> Place Order</a>
                                                            <a href="PurchaseOrdersController?task=cancel_order&vendor_id=${bean.vendor_id}&org_office_id=${bean.org_office_id}" onclick="return confirm('Are you sure you want to cancel this order?');" class="btn actionDelete" title="Add to Cart"> Cancel Order</a>
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