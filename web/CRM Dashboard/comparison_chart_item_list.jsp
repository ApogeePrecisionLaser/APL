<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@include file="/CRM Dashboard/CRM_header.jsp" %>


<style>
    .borderRight{
        border-right: 1px solid #ccc;
    }
</style>
<div class="content-wrapper" id="contentWrapper">
    <section class="content-header mt-2">
        <div class="container-fluid">
            <div class="row mb-2 ">
                <div class="col-sm-6">
                </div>
                <div class="col-sm-6">
                    <ol class="breadcrumb float-sm-right">
                        <li class="breadcrumb-item"><a href="CRMDashboardController">Dashboard</a></li>
                        <li class="breadcrumb-item active">Quotation Comparison Chart</li>
                    </ol>
                </div>
            </div>
        </div><!-- /.container-fluid -->
    </section>

    <section class="content">
        <div class="row">
            <div class="col-md-12">
                <div class="card card-primary card-outline">            
                    <div class="card-body pt-2">
                        <!--                        <div class="container-fluid">
                                                    <div class="row">
                                                        <div class="col-md-3">
                                                            <div>
                                                                <p class="fontFourteen mb-0"><b>QCC No :</b> ${autogenerate_comparative_charts_no}</p>
                                                                <p class="fontFourteen mb-0"><b>Date :</b> ${cur_date}</p>
                                                            </div>
                                                        </div>
                                                        <div class="col-md-6">
                                                            <div class="text-center">
                                                                <h4>Quotation Comparison Chart</h4>
                                                            </div>
                                                        </div>
                                                        <div class="col-md-3">
                                                            <div class="text-right">
                                                                <a href="QuotationController?task=new_quotation&type=vendor" class="text-right">Back to Quotation Requsition</a>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>-->




                        <div class="table-responsive tableScrollWrap mt-2">
                            <table class="table table-hover table-striped" id="mytable1">
                                <thead>
                                    <tr>
                                        <th>S.No.</th> 
                                            <c:if test="${role=='Admin' || role=='Super Admin'}">
                                            <th>Org Office</th> 
                                            </c:if>

                                        <th>Product</th> 
                                        <th>Model</th> 
                                        <th>Qty</th> 
                                        <th>Action</th> 
                                    </tr>             
                                </thead>
                                <tbody>
                                    <c:forEach var="beanType" items="${requestScope['list']}"
                                               varStatus="loopCounter">
                                        <tr>
                                            <td class="fontFourteen borderRight">${loopCounter.count}</td>
                                            <c:if test="${role=='Admin' || role=='Super Admin'}">
                                                <td class="fontFourteen borderRight">${beanType.org_office_name}</td>
                                            </c:if>
                                            <td class="fontFourteen borderRight">${beanType.item_name}</td>
                                            <td class="fontFourteen borderRight">${beanType.model}</td>     
                                            <td class="fontFourteen borderRight">${beanType.qty}</td>     
                                            <td class="fontFourteen borderRight">
                                                <c:choose>
                                                    <c:when test="${beanType.status=='Converted To Order'}"> 
                                                        <a class="btn btn-primary myOtherBtn" style="pointer-events: none">
                                                            ${beanType.status}
                                                        </a>    
                                                    </c:when>
                                                    <c:otherwise>
                                                        <a class="btn btn-primary myOtherBtn" href="ComparisonChartController?task=viewComparativeChart&item_name=${beanType.item_name}&model=${beanType.model}">
                                                            View Comparison Chart
                                                        </a>        
                                                    </c:otherwise>
                                                </c:choose>
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
    </section>
</div>

<%@include file="/CRM Dashboard/CRM_footer.jsp" %>
