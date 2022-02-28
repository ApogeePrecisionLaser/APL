<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<%@include file="/CRM Dashboard/CRM_header.jsp" %>

<div class="content-wrapper" id="contentWrapper">
    <section class="content-header">
        <div class="container-fluid">
            <div class="row mb-2">
                <div class="col-sm-6">
                    <div class="d-flex">
                        <!--                        <div>
                                                    <a href="products.php" class="btn btn-primary myNewLinkBtn">Create Order</a>
                                                </div>-->
                        <div class="position-relative">
                            <div class="alert alert-success alert-dismissible myAlertBox" style="display:none">
                                <button type="button" class="close" data-dismiss="alert">&times;</button>
                                <strong>Success!</strong> Indicates a successful or positive action.
                            </div>
                        </div>
                    </div>  
                </div>
                <div class="col-sm-6">
                    <ol class="breadcrumb float-sm-right">
                        <li class="breadcrumb-item"><a href="CRMDashboardController">Dashboard</a></li>
                        <li class="breadcrumb-item active">Approved Order</li>
                    </ol>
                </div>
            </div>
        </div><!-- /.container-fluid -->
    </section>
    <section class="content">
        <div class="container-fluid">              
            <div class="row mt-0">
                <div class="col-md-12">
                    <div class="card card-primary card-outline">            
                        <div class="card-body">
                            <div>
                                <div class="table-responsive tableScrollWrap noWrapTable" >
                                    <table class="table table-striped1 mainTable" id="mytable" >
                                        <thead>
                                            <tr>
                                                <th class="fontFourteen">S.No.</th>
                                                <th class="fontFourteen">Order No</th>
                                                <th class="fontFourteen">Requested By</th>
                                                <th class="fontFourteen">Status</th>
                                                <th class="fontFourteen">Price</th>
                                                <th class="fontFourteen">Date</th>
                                                <th class="fontFourteen">Action</th>
                                            </tr>
                                        </thead>
                                        <c:forEach var="beanType" items="${requestScope['list']}"
                                                   varStatus="loopCounter">
                                            <tr>
                                                <td class="fontFourteen">${loopCounter.count}</td>
                                                <td class="fontFourteen">${beanType.order_no}</td>
                                                <td class="fontFourteen">${beanType.requested_by}</td>

                                                <c:choose>  
                                                    <c:when test="${beanType.status == 'Pending'}">  
                                                        <td class="fontFourteen"><i class="statusPending">Order Placed</i></td>
                                                    </c:when>   
                                                    <c:when test="${beanType.status == 'Approved'}">  
                                                        <td class="fontFourteen"><i class="statusApprove">Order Confirmed</i></td>
                                                    </c:when>  
                                                    <c:otherwise>  
                                                        <td class="fontFourteen"><i class="statusDisapprove">${beanType.status}</i></td>
                                                        </c:otherwise>  
                                                    </c:choose>  

                                                <td class="fontFourteen"><i class="fas fa-rupee-sign fontTen"></i>  
                                                    <fmt:formatNumber type = "number"  maxFractionDigits = "3"  value =  "${beanType.basic_price}" />
                                                </td>
                                                <td class="fontFourteen">${beanType.date_time}</td>

                                                <td class="fontFourteen d-flex">
                                                    <div>
                                                        <a href="ApproveOrdersController?task=viewOrderDetails&order_table_id=${beanType.order_table_id}" class="btn far fa-eye actionEdit" title="View Order Detail"></a>
                                                        <!--<a onclick="return confirm('Are you sure you want to cancel this order?');" href="#" class="btn far fa-times-circle actionDelete" title="Cancel Order"></a>-->
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
