<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@include file="/CRM Dashboard/CRM_header.jsp" %>

<style>
    .salesManAppInputWrap{
        display: flex;
        white-space: nowrap;
    }
    .salesManAppInputWrap a{
        border-radius: 0;
    }
    .salesManAppInputWrap input{
        margin-right: 5px;
    }
</style>

<div class="content-wrapper position-relative" id="contentWrapper">
    <section class="content-header">
        <div class="container-fluid mt-2">
            <div class="row mb-2">
                <div class="col-sm-6">
                    <div class="d-flex">
                        <div>
                            <a href="QuotationController?task=new_quotation&type=vendor" class="btn btn-primary myNewLinkBtn">Request For Quotation</a>
                        </div>
                        <!--                        <div class="">
                                                    <div class="alert alert-success alert-dismissible myAlertBox">
                                                        <button type="button" class="close" data-dismiss="alert">&times;</button>
                                                        <strong>Success!</strong> New order create successfully.
                                                    </div>
                                                </div>-->

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
                <div class="col-sm-6">
                    <ol class="breadcrumb float-sm-right">
                        <c:choose>
                            <c:when test="${role=='Incharge' || role=='Super Admin'}">
                                <li class="breadcrumb-item"><a href="dashboard">Dashboard</a>
                                </li>
                            </c:when>
                            <c:otherwise>
                                <li class="breadcrumb-item"><a href="CRMDashboardController">Dashboard</a>
                                </li>
                            </c:otherwise>
                        </c:choose>

                        <li class="breadcrumb-item active">All Quotations</li>
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
                                <div class="table-responsive tableScrollWrap" >
                                    <table class="table table-striped1 mainTable" id="mytable" >
                                        <thead>
                                            <tr>
                                                <th class="fontFourteen">Sr. No.</th>
                                                    <c:if test="${role=='Admin' || role=='Super Admin'}">
                                                    <th class="fontFourteen">Office Name
                                                    </th>

                                                </c:if>
                                                <c:if test="${role=='Admin' || role=='Super Admin'}">
                                                    <th>Customer
                                                    </th>
                                                </c:if>
                                                <th class="fontFourteen">Vendor Name</th>

                                                <th class="fontFourteen">Quotation No</th>

                                                <!--<th>Vendor Mobile No</th>-->
                                                <!--<th class="fontFourteen">Price (<small><i class="fas fa-rupee-sign curruncyIcon"></i></small>)</th>-->
                                                <th class="fontFourteen">Time</th>
                                                <th class="fontFourteen">Status</th>
                                                <!--<th class="fontFourteen">Quotation</th>-->
                                                <th class="fontFourteen">Action</th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <c:forEach var="beanType" items="${requestScope['list']}"
                                                       varStatus="loopCounter">
                                                <tr>
                                                    <td> ${loopCounter.count} </td>
                                                    <c:if test="${role=='Admin' || role=='Super Admin'}">
                                                        <td class="fontFourteen">${beanType.org_office_name}</td>
                                                        <td class="fontFourteen">${beanType.customer_name}</td>
                                                    </c:if>
                                                    <td class="fontFourteen">${beanType.vendor}</td>


                                                    <!--
                                                    -->


                                                    <td class="fontFourteen">${beanType.quotation_no}</td>
                                                    <td class="fontFourteen">${beanType.time_ago}</td>
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
                                                    <td>
                                                        <a href="QuotationController?task=viewDetails&quotation_no=${beanType.quotation_no}" class="btn far fa-eye actionEdit" title="View Quotation Detail"></a>
                                                        <a class="btn actionEdit"  target="_blank" title="View PDF" id="download" href="QuotationController?task=viewPdF&&mail=no&quotation_no=${beanType.quotation_no}">
                                                            <i class="fas fa-download"></i>
                                                        </a>       
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
    $(function () {
        setTimeout(function () {
            $('.myAlertBox').fadeOut('fast');
        }, 3000);

        setTimeout(function () {
            $('.alert-danger').fadeOut('fast');
        }, 4000);
    });

    var popupwin = null;
    function viewPdf(order_no) {
        var queryString = "task=viewPdf&order_no=" + order_no;
        var url = "PurchaseOrdersController?" + queryString;
        popupwin = openPopUp(url, "Order Pdf", 500, 1000);
    }

    function openPopUp(url, window_name, popup_height, popup_width) {
        var popup_top_pos = (screen.availHeight / 2) - (popup_height / 2);
        var popup_left_pos = (screen.availWidth / 2) - (popup_width / 2);
        var window_features = "left=" + popup_left_pos + ", top=" + popup_top_pos + ", width=" + popup_width + ", height=" + popup_height + ", resizable=yes, scrollbars=yes, location=0, menubar=no, status=no, dependent=yes";
        return window.open(url, window_name, window_features);
    }
</script>