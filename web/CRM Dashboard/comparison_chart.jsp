<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@include file="/CRM Dashboard/CRM_header.jsp" %>


<style>
    .borderRight{
        border-right: 1px solid #ccc;
    }
    /*.borderRight{
      border-right: 1px solid #999;
    }*/
</style>
<div class="content-wrapper" id="contentWrapper">
    <section class="content-header mt-2">
        <div class="container-fluid">
            <div class="row mb-2 ">
                <div class="col-sm-6">
                    <div class="d-flex">
                        <div>
                            <a href="ComparisonChartController" class="btn btn-primary myNewLinkBtn fontFourteen">Back</a>
                        </div>
                    </div>   
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
                        <div class="container-fluid">
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
                        </div>
                        <div class="table-responsive tableScrollWrap mt-2">
                            <table class="table table-hover table-striped" id="mytable1">
                                <thead>
                                    <tr>
                                        <th class="fontFourteen borderRight" colspan="5">Supplier</th>
                                            <c:forEach var="beanType2" items="${requestScope['list2']}"
                                                       varStatus="loopCounter2">
                                            <th class="fontFourteen borderRight text-center" colspan="3">${beanType2.vendor}</th>
                                            </c:forEach>
                                    </tr>                    
                                </thead>
                                <tbody>
                                    <tr>
                                        <th class="fontFourteen borderRight">S#</th>
                                        <th class="fontFourteen borderRight">Product</th>
                                        <th class="fontFourteen borderRight">Model</th>
                                        <th class="fontFourteen borderRight">UOM</th>
                                        <th class="fontFourteen borderRight">QTY</th>
                                            <c:forEach var="beanType2" items="${requestScope['list2']}"
                                                       varStatus="loopCounter">
                                            <th class="fontFourteen borderRight">Lead Time(Days)</th>
                                            <th class="fontFourteen borderRight">Rate (<small><i class="fas fa-rupee-sign curruncyIcon"></i></small>)</th>
                                            <th class="fontFourteen borderRight">Amount(<small><i class="fas fa-rupee-sign curruncyIcon"></i></small>)</th>
                                                </c:forEach>
                                    </tr>
                                    <tr>       
                                        <c:forEach var="beanType" items="${requestScope['list2']}"
                                                   varStatus="loopCounter">
                                            <c:if test="${loopCounter.count==1}">
                                                <td class="fontFourteen borderRight">${loopCounter.count}</td>         
                                                <td class="fontFourteen borderRight">${beanType.item_name}</td>
                                                <td class="fontFourteen borderRight">${beanType.model}</td>
                                                <td class="fontFourteen borderRight">Unit(s)</td>
                                            </c:if>
                                        </c:forEach>
                                        <c:forEach var="beanType2" items="${requestScope['list2']}"
                                                   varStatus="loopCounter2">
                                            <c:if test="${loopCounter2.count==1}">
                                                <td class="fontFourteen borderRight">${beanType2.qty}</td>
                                            </c:if>
                                            <td class="fontFourteen borderRight">${beanType2.lead_time}</td>
                                            <td class="fontFourteen borderRight">${beanType2.rate}</td>
                                            <td class="fontFourteen borderRight">${beanType2.price}</td>
                                        </c:forEach>
                                    </tr>

                                    <tr>       
                                        <th class="fontFourteen borderRight" colspan="5">Total</th> 
                                            <c:forEach var="beanType2" items="${requestScope['list2']}"
                                                       varStatus="loopCounter2">
                                            <td class="fontFourteen borderRight" colspan="2">
                                                <a href="ComparisonChartController?task=confirmOrder&quotation_id=${beanType2.quotation_id}" class="fontFourteen btn btn-info rounded-0 py-0 px-2">Confirm Order</a>
                                            </td>
                                            <th class="fontFourteen borderRight ">${beanType2.price}</th>
                                            </c:forEach>
                                    </tr>  

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
