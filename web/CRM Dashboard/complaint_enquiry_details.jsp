<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@include file="/CRM Dashboard/CRM_header.jsp" %>

<div class="content-wrapper" id="contentWrapper">
    <section class="content-header">
        <div class="container-fluid">
            <div class="row mb-2 marginTop10">
                <div class="col-sm-6">
                    <h1>Complaint Enquiry Detail</h1>
                </div>
                <div class="col-sm-6">
                    <ol class="breadcrumb float-sm-right">
                        <li class="breadcrumb-item"><a href="CRMDashboardController">Home</a></li>
                        <li class="breadcrumb-item active">Complaint Enquiry Detail</li>
                    </ol>
                </div>
            </div>
        </div><!-- /.container-fluid -->
    </section>


    <section class="content">
        <div class="row">
            <div class="col-md-12 px-3">
                <div class="card card-primary rounded-0 profileCard mt-2 card-outline" style="">
                    <div class="card-body">
                        <div class="mt-1">
                            <c:forEach var="beanType" items="${requestScope['list']}"
                                       varStatus="loopCounter">
                                <div class="d-flex justify-content-between">
                                    <div class="text-right">
                                        <a href="SalesEnquiryController?task=complaint_enquiry_list" class="btn btnBack "><i class="fas fa-chevron-circle-left"></i></a>

                                    </div>
                                    <div class="text-right">
                                        <c:choose>
                                            <c:when test="${beanType.status =='Enquiry Generated'}">
                                                <a href="SalesEnquiryController?task=assignComplaintToSalesPerson&enquiry_table_id=${beanType.enquiry_table_id}&state=${beanType.enquiry_state}&city=${beanType.enquiry_city}" class="btn myBtnInfo fontFourteen" title="Assigned To SalesManager">Assign To SalesManager & Dealer</a>
                                            </c:when>

                                            <c:otherwise>

                                                <button class="btn myThemeBtn fontFourteen " disabled>${beanType.status}</button>
                                                <div><span class="text-danger fontFourteen">(${beanType.assigned_to})</span></div>                                            </c:otherwise>
                                        </c:choose>
                                    </div>
                                </div>


                                <div class="col-md-6 px-0">
                                    <div class="mt-3">
                                        <form class="myForm" action="DealersOrderController">
                                            <div class="row">                        
                                                <div class="col-md-6">
                                                    <!--<div class="dropdown multiDropDown">-->
                                                    <!--                                                        <button class="btn myThemeBtn dropdown-toggle " type="button" data-toggle="dropdown">Status
                                                                                                                <span class="caret"></span></button>-->
                                                    <input type="hidden" name="enquiry_table_id" id="enquiry_table_id" value="${enquiry_table_id}">
                                                    <select name="status" id="status" class="form-control mb-2" disabled="">
                                                        <c:if test="${beanType.status == 'Open'}">
                                                            <option value="Open" selected="">Open</option>
                                                        </c:if>
                                                        <c:if test="${beanType.status == 'Call'}">
                                                            <option value="Call" selected="">Call</option>
                                                        </c:if>
                                                        <c:if test="${beanType.status == 'Follow Up'}">
                                                            <option value="Follow Up" selected="">Follow Up</option>
                                                        </c:if>
                                                        <c:if test="${beanType.status == 'Sold'}">
                                                            <option value="Sold" selected="">Sold</option>
                                                        </c:if>
                                                        <c:if test="${beanType.status == 'UnSold'}">
                                                            <option value="UnSold" selected="">UnSold</option>
                                                        </c:if>
                                                        <c:if test="${beanType.status != 'Open' || beanType.status != 'Call'
                                                                      || beanType.status != 'Follow Up' ||   beanType.status != 'Sold' 
                                                                      || beanType.status != 'UnSold'}">
                                                              <option disabled="">Select</option>
                                                              <option value="Open">Open</option>
                                                              <option value="Call">Call</option>
                                                              <option value="Follow Up">Follow Up</option>
                                                              <option value="Sold">Sold</option>
                                                              <option value="UnSold">UnSold</option>
                                                        </c:if>

                                                        <c:if test="${beanType.status == 'Irrelevant' || beanType.status == 'Not Interested'
                                                                      || beanType.status == 'Purchased From Others'}">
                                                              <option value="UnSold" selected="">UnSold</option>

                                                        </c:if>

                                                    </select>

                                                </div>

                                                <c:if test="${beanType.status == 'Irrelevant' || beanType.status == 'Not Intrested'
                                                              || beanType.status == 'Purchased From Other'}">
                                                      <div class="col-md-6" id="unsold_status_div">
                                                          <select name="status2" id="status2" class="form-control mb-2" disabled="">
                                                              <c:if test="${beanType.status == 'Irrelevant' || beanType.status == 'Not Interested'
                                                                            || beanType.status == 'Purchased From Others'}">
                                                                    <option disabled="">Select</option>
                                                                    <option value="Irrelevant">Irrelevant</option>
                                                                    <option value="Not Interested">Not Interested</option>
                                                                    <option value="Purchased From Other">Purchased From Other</option>
                                                              </c:if>
                                                              <c:if test="${beanType.status == 'Irrelevant' }">

                                                                  <option value="Irrelevant" selected="">Irrelevant</option>
                                                              </c:if>
                                                              <c:if test="${beanType.status == 'Not Interested' }">

                                                                  <option value="Not Interested" selected="">Not Interested</option>
                                                              </c:if>
                                                              <c:if test="${beanType.status == 'Purchased From Others' }">

                                                                  <option value="Purchased From Others" selected="">Purchased From Others</option>
                                                              </c:if>

                                                          </select>
                                                      </div>
                                                </c:if>

                                                <div class="col-md-6" id="unsold_status_div" style="display: none">
                                                    <select name="status2" id="status2" class="form-control mb-2">
                                                        <option disabled="">Select</option>
                                                        <option value="Irrelevant">Irrelevant</option>
                                                        <option value="Not Interested">Not Interested</option>
                                                        <option value="Purchased From Others">Purchased From Others</option>
                                                    </select>
                                                </div>
                                            </div>

                                            <div class="row">
                                                <div class="col-md-6">
                                                    <div class="form-group">
                                                        <p class="mb-0"><small>Date:</small></p>
                                                        <input type="date" class="form-control rounded-0" name="date_time" value="${beanType.update_date_time}" disabled="">
                                                    </div>
                                                </div>
                                                <div class="col-md-6">
                                                    <div class="form-group">
                                                        <p class="mb-0"><small>Time:</small></p>
                                                        <input type="time" class="form-control rounded-0" name="time" value="${beanType.update_time}" disabled="">
                                                    </div>
                                                </div>
                                                <div class="col-md-12">
                                                    <div class="form-group">
                                                        <p class="mb-0"><small>Remark:</small></p>
                                                        <textarea class="form-control rounded-0" name="remark" disabled="">${beanType.remark}</textarea>
                                                    </div>
                                                </div>
                                                <!--                                                <div class="col-md-12">
                                                                                                    <div class="form-group mb-0">
                                                <c:choose>
                                                    <c:when test="${beanType.status == 'Assigned To Dealer' || beanType.status == 'Assigned To SalesManager' ||
                                                                    beanType.status == 'Sold' 
                                                                    || beanType.status == 'UnSold' ||  beanType.status == 'Irrelevant' || 
                                                                    beanType.status == 'Not Interested' ||  beanType.status == 'Purchased From Others'}">
                                                            <input type="submit" class="btn myThemeBtn" name="update_enquiry" value="Update" disabled="">
                                                    </c:when>
                                                    <c:otherwise>
                                                        <input type="submit" class="btn myThemeBtn" name="update_enquiry" value="Update" >
                                                    </c:otherwise>  
                                                </c:choose>

                                            </div>
                                        </div>-->
                                            </div>
                                        </form>    
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
                                            <p><strong><a href="mailTo:${beanType.sender_email}"><i class="far fa-envelope"></i> ${beanType.sender_email}</a></strong></p>
                                        </div>
                                    </div>
                                    <div class="col-md-4">
                                        <div>
                                            <p class="mb-0"><small>Sender Mobile:</small></p>
                                            <p><strong><a href="tel:+${beanType.sender_mob}"><i class="fas fa-mobile-alt"></i> ${beanType.sender_mob}</a></strong></p>
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
                                    <div class="col-md-4">
                                        <div>
                                            <p class="mb-0"><small>Product Name:</small></p>
                                            <p><strong>${beanType.product_name}</strong></p>
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
<script>

    $('#status').change(function () {
        var status = $('#status').val();
//        alert(status);
        if (status == 'UnSold') {
            $('#unsold_status_div').show();
        } else {
            $('#unsold_status_div').hide();
        }
    });
</script>