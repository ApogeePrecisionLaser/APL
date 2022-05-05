<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>

<%@include file="/CRM Dashboard/CRM_header.jsp" %>
<div class="content-wrapper" id="contentWrapper">
    <section class="content-header mt-2">
        <div class="container-fluid">
            <div class="row mb-2 ">
                <div class="col-sm-6">
                    <div class="d-flex">
                        <div>
                            <a href="EventController?task=add_event" class="btn btn-primary myNewLinkBtn fontFourteen">Create News</a>
                        </div>
                        <!--                        <div class="">
                                                    <div class="alert alert-success alert-dismissible myAlertBox">
                                                        <button type="button" class="close" data-dismiss="alert">&times;</button>
                                                        <strong>Success!</strong>
                                                    </div>
                                                </div>-->
                    </div>  
                </div>
                <div class="col-sm-6">
                    <ol class="breadcrumb float-sm-right">
                        <li class="breadcrumb-item"><a href="CRMDashboardController">Dashboard</a></li>
                        <li class="breadcrumb-item active">News List
                            <input type="hidden" name="count" id="count" value="${count}">
                        </li>
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
                        <div class="table-responsive mailbox-messages tableScrollWrap">
                            <table class="table table-hover table-striped" id="mytable">
                                <thead>
                                    <tr>
                                        <th class="pl-2">Sr. No.</th>
                                        <th class="pl-2">Img</th>
                                        <th class="pl-2">Title</th>
                                        <th class="pl-2">Status</th>
                                        <th class="pl-2">Time</th>
                                        <th class="pl-2">Action</th>
                                    </tr>
                                </thead>
                                <tbody>

                                    <c:forEach var="beanType" items="${requestScope['event_list']}"
                                               varStatus="loopCounter">
                                        <tr>
                                            <td class="fontFourteen">${loopCounter.count}</td>
                                            <td class="fontFourteen">
                                                <input type="hidden" name="document_name" id="document_name${loopCounter.count}" 
                                                       value="${beanType.document_name}">
                                                <img src="" class="event_image${loopCounter.count}" style="width: 40px;height: 20px;object-fit: cover;">

                                            </td>
                                            <td class="fontFourteen">${beanType.title}</td>
                                            <td class="fontFourteen"><i class="statusApprove py-0">Active</td>
                                            <td class="fontFourteen">${beanType.time_ago}</td>
                                            <td>
                                                <a href="EventController?task=event_detail&events_id=${beanType.events_id}" class="btn far fa-eye actionEdit" title="See Message"></a>
                                                <a onclick=" return confirm('Are You Sure You want to delete!');" href="EventController?task=deleteEvent&events_id=${beanType.events_id}" class="btn far fa-trash-alt actionDelete" title="Delete Message"></a>
                                                <a href="EventController?task=dealers_list&events_id=${beanType.events_id}" class="btn actionView text-white" title="Send To Dealer"><i class="fas fa-share"></i></a>
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
<script>
    $(document).ready(function () {
        var count = $('#count').val();
        for (var i = 0; i < count; i++) {
            var document_name = $('#document_name' + (i + 1)).val();
            $('.event_image' + (i + 1)).attr("src", "http://" + IMAGE_URL + "/APL/EventController?task=viewImage&document_name=" + document_name);

        }
    })
</script>