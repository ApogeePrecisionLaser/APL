<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>

<%@include file="/CRM Dashboard/CRM_header.jsp" %>
<div class="content-wrapper" id="contentWrapper">
    <section class="content-header">
        <div class="container-fluid">
            <div class="row mb-2">
                <div class="col-sm-6">
                    <h1>News Detail</h1>
                </div>
                <div class="col-sm-6">
                    <ol class="breadcrumb float-sm-right">
                        <li class="breadcrumb-item"><a href="CRMDashboardController">Dashboard</a></li>
                        <li class="breadcrumb-item active">News Detail</li>
                    </ol>
                </div>
            </div>
        </div><!-- /.container-fluid -->
    </section>

    <section class="content">
        <div class="row">
            <div class="col-md-12">          
                <div class="card card-primary card-outline">            
                    <div class="card-body">
                        <div class="row">
                            <div class="col-md-12">
                                <input type="hidden" name="document_name" id="document_name" value="${document_name}">
                                <img src="" class="event_image" style="max-height:300px;">
                            </div>
                        </div>
                        <div class="row mt-3">
                            <div class="col-md-6">
                                <div class="text-left mb-2">
                                    <c:choose>
                                        <c:when test="${user_role=='Dealer'}">
                                            <a href="NotificationController" class="btn myThemeBtn text-right">Back</a>
                                        </c:when>
                                        <c:otherwise>
                                            <a href="EventController" class="btn myThemeBtn text-right">Back</a>

                                        </c:otherwise>
                                    </c:choose>
                                </div>
                            </div>
                            <div class="col-md-6">
                                <div>                      
                                    <div class="text-right mt-1">
                                        <a href="#">${date_time}</a>
                                    </div>                  
                                </div>
                            </div>
                        </div>                           
                        <div class="blogBox">                
                            <div class="mt-2">
                                <h2>${title}</h2>
                                <p>${description}</p>
                            </div>
                            <div class="mt-3 eventAssignedDealer">
                                <c:if test="${count==0}">
                                    <p class="fontFourteen text-danger">Note:- This Event is not sent to any dealer yet.</p>

                                </c:if>
                                <c:forEach var="beanType" items="${requestScope['dealer_events']}"
                                           varStatus="loopCounter">
                                    <a href="DealersController?task=viewDealerDetails&org_office_id=${beanType.org_office_id}&key_person_id=${beanType.key_person_id}">
                                        ${beanType.org_office}</a>
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
<script src="CRM Dashboard/assets2/js/myJS.js" type="text/javascript"></script>

<script>
    $(document).ready(function () {
        var document_name = $('#document_name').val();
        $('.event_image').attr("src", "http://" + IMAGE_URL + "/APL/EventController?task=viewImage&document_name=" + document_name);
    })
</script>
