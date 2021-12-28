<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@include file="/CRM Dashboard/CRM_header.jsp" %>



<div class="content-wrapper" id="contentWrapper">
    <section class="content-header">
        <div class="container-fluid">
            <div class="row mb-2">
                <div class="col-sm-6">
                    <h1>Complaint Enquiry Detail</h1>
                </div>
                <div class="col-sm-6">
                    <ol class="breadcrumb float-sm-right">
                        <li class="breadcrumb-item"><a href="#">Home</a></li>
                        <li class="breadcrumb-item active">Complaint Enquiry Detail</li>
                    </ol>
                </div>
            </div>
        </div><!-- /.container-fluid -->
    </section>


    <section class="content">
        <div class="row">
            <div class="col-md-12 px-3">
                <div class="card card-primary rounded-0 profileCard mt-3" style="">
                    <div class="card-body">
                        <div class="mt-1">
                            <c:forEach var="beanType" items="${requestScope['list']}"
                                       varStatus="loopCounter">
                                <div class="d-flex justify-content-between">
                                    <div class="text-right">
                                        <a href="SalesEnquiryController?task=complaint_enquiry_list" class="btn myThemeBtn text-right">Back</a>
                                    </div>
                                    <div class="text-right">
                                        <c:choose>
                                            <c:when test="${beanType.status =='Enquiry Generated'}">
                                                <a href="SalesEnquiryController?task=assignComplaintToSalesPerson&enquiry_table_id=${beanType.enquiry_table_id}&sales_person_name=${beanType.assigned_to}"
                                                   class="btn myThemeBtn text-right">Assign to Sale Person</a>                                        </c:when>
                                            <c:otherwise>
                                                <button class="btn btn-danger" disabled>${beanType.status}</button>
                                            </c:otherwise>
                                        </c:choose>

                                    </div>
                                </div>


                                <div class="row mt-4">
                                    <!--                                    <div class="col-md-4">
                                                                            <div>
                                                                                <p class="mb-0"><small>Enquiry Source:</small></p>
                                                                                <p><strong>${beanType.enquiry_source}</strong></p>
                                                                            </div>
                                                                        </div>
                                                                        <div class="col-md-4">
                                                                            <div>
                                                                                <p class="mb-0"><small>Marketing Vertical:</small></p>
                                                                                <p><strong>${beanType.marketing_vertical_name}</strong></p>
                                                                            </div>
                                                                        </div>-->
                                    <div class="col-md-4">
                                        <div>
                                            <p class="mb-0"><small>Enquiry No:</small></p>
                                            <p><strong>${beanType.enquiry_no}</strong></p>
                                        </div>
                                    </div>
                                    <div class="col-md-4">
                                        <div>
                                            <p class="mb-0"><small>Sender Name:</small></p>
                                            <p><strong>${beanType.sender_name}</strong></p>
                                        </div>
                                    </div>

                                    <div class="col-md-4">
                                        <div>
                                            <p class="mb-0"><small>Sender Email:</small></p>
                                            <p><strong>${beanType.sender_email}</strong></p>
                                        </div>
                                    </div>
                                    <div class="col-md-4">
                                        <div>
                                            <p class="mb-0"><small>Sender Mobile:</small></p>
                                            <p><strong>${beanType.sender_mob}</strong></p>
                                        </div>
                                    </div>
                                    <div class="col-md-4">
                                        <div>
                                            <p class="mb-0"><small>Sender Company Name:</small></p>
                                            <p><strong>${beanType.sender_company_name}</strong></p>
                                        </div>
                                    </div>
                                    <div class="col-md-4">
                                        <div>
                                            <p class="mb-0"><small>Sender Address:</small></p>
                                            <p><strong>${beanType.enquiry_address}</strong></p>
                                        </div>
                                    </div>
                                    <div class="col-md-4">
                                        <div>
                                            <p class="mb-0"><small>City:</small></p>
                                            <p><strong>${beanType.enquiry_city}</strong></p>
                                        </div>
                                    </div> 
                                    <div class="col-md-4">
                                        <div>
                                            <p class="mb-0"><small>District:</small></p>
                                            <p><strong>${beanType.description}</strong></p>
                                        </div>
                                    </div> 
                                    <div class="col-md-4">
                                        <div>
                                            <p class="mb-0"><small>State:</small></p>
                                            <p><strong>${beanType.enquiry_state}</strong></p>
                                        </div>
                                    </div> 
                                    <div class="col-md-4">
                                        <div>
                                            <p class="mb-0"><small>Country Code:</small></p>
                                            <p><strong>${beanType.country}</strong></p>
                                        </div>
                                    </div> 
                                    <div class="col-md-4">
                                        <div>
                                            <p class="mb-0"><small>Enquiry Date Time:</small></p>
                                            <p><strong>${beanType.enquiry_date_time}</strong></p>
                                        </div>
                                    </div> 
                                    <div class="col-md-12">
                                        <div>
                                            <p class="mb-0"><small>Enquiry Message:</small></p>
                                            <p><strong>${beanType.enquiry_message}</strong></p>
                                        </div>
                                    </div>
                                </c:forEach>
                            </div> 
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </section>
</div>

<%@include file="/CRM Dashboard/CRM_footer.jsp" %>
