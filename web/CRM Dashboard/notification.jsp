<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@include file="/CRM Dashboard/CRM_header.jsp" %>



<div class="content-wrapper" id="contentWrapper">
    <section class="content-header">
        <div class="container-fluid">
            <div class="row mb-2 marginTop10">
                <div class="col-sm-6">
                    <h1>News</h1>
                </div>
                <div class="col-sm-6">
                    <ol class="breadcrumb float-sm-right">
                        <li class="breadcrumb-item"><a href="CRMDashboardController">Dashboard</a></li>
                        <li class="breadcrumb-item active">News</li>
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
                        <div class="table-responsive mailbox-messages tableScrollWrap noWrapTable">
                            <input type="hidden" name="count" id="count" value="${count}">

                            <table class="table table-hover table-striped" id="mytable">
                                <thead>
                                    <tr>
                                        <th class="pl-2">Sl. No.</th>
                                        <th class="pl-2">Name</th>
                                        <th class="pl-2">Title</th>
                                        <!--<th class="pl-2">Description</th>-->
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
                                                <a href="NotificationController?task=event_detail&events_id=${beanType.events_id}" class="btn far fa-eye actionEdit" title="See Message"></a>
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


<div class="modal myPopup" id="myPopModal">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header rounded-0">
                <div>
                    <h4 class="modal-title">Kundan Pandey</h4>
                </div>
                <button type="button" class="close text-white" data-dismiss="modal">&times;</button>
            </div>
            <div class="modal-body">
                <p class="fontFourteen">Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.</p>
            </div>
        </div>
    </div>
</div>


<%@include file="/CRM Dashboard/CRM_footer.jsp" %>
<script>

    var modal = document.getElementById('id01');
    window.onclick = function (event) {
        if (event.target == modal) {
            modal.style.display = "none";
        }
    }
</script>
<script>
    $(document).ready(function () {
        var count = $('#count').val();
        for (var i = 0; i < count; i++) {
            var document_name = $('#document_name' + (i + 1)).val();
            $('.event_image' + (i + 1)).attr("src", "http://" + IMAGE_URL + "/APL/NotificationController?task=viewImage&document_name=" + document_name);

        }
    })
</script>