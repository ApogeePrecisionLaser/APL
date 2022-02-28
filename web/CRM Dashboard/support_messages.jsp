<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@include file="/CRM Dashboard/CRM_header.jsp" %>



<div class="content-wrapper" id="contentWrapper">
    <section class="content-header">
        <div class="container-fluid">
            <div class="row mb-2 marginTop10">
                <div class="col-sm-6">
                    <h1>Support Messages</h1>
                </div>
                <div class="col-sm-6">
                    <ol class="breadcrumb float-sm-right">
                        <li class="breadcrumb-item"><a href="CRMDashboardController">Dashboard</a></li>
                        <li class="breadcrumb-item active">Support Messages</li>
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
                            <table class="table table-hover table-striped" id="mytable">
                                <thead>
                                    <tr>
                                        <th class="pl-2">Sl. No.</th>
                                        <th class="pl-2">Name</th>
                                        <th class="pl-2">Contact</th>
                                        <th class="pl-2">Title</th>
                                        <!--<th class="pl-2">Description</th>-->
                                        <th class="pl-2">Status</th>
                                        <th class="pl-2">Time</th>
                                        <th class="pl-2">Action</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach var="beanType" items="${requestScope['supportMessages']}"
                                               varStatus="loopCounter">
                                        <tr>
                                            <td class="fontFourteen">${loopCounter.count}
                                                <input type="hidden" name="message${loopCounter.count}" id="message${loopCounter.count}" value="${beanType.message}">
                                                <input type="hidden" name="subject${loopCounter.count}" id="subject${loopCounter.count}" value="${beanType.subject}">
                                            </td>
                                            <td class="fontFourteen">${beanType.dealer_name}</td>
                                            <td class="fontFourteen"><a href="tel:+91- ${beanType.contact_no}"><i class="fas fa-mobile-alt"></i> +91- ${beanType.contact_no}</a></td>
                                            <td class="fontFourteen">

                                                ${beanType.subject}

                                            </td>
                                            <!--<td class="fontFourteen" style="max-width: 250px;">It is a long established fact that a reader will be distracted. </td>-->
                                            <td class=""><i class="statusPending fontThirteen">${beanType.status}</i></td>
                                            <td class="fontFourteen">${beanType.time_ago}</td>
                                            <td>
                                                <a  class="btn far fa-eye actionEdit" title="See Message" data-toggle="modal" data-target="#myPopModal" onclick="showMessage('${loopCounter.count}')"></a>
                                                <!--<a onclick="return confirm('Are you sure you want to delete this message?');" href="#" class="btn far fa-trash-alt actionDelete" title="Delete Message"></a>-->
                                                <input type="hidden" name="document_name${loopCounter.count}" id="document_name${loopCounter.count}" value="${beanType.document_name}">

                                                <c:if test="${beanType.document_name !=''}">
                                                    <a class="btn actionEdit"  title="View Document" id="download" 
                                                       href="SupportMessagesController?task1=downloadFile&document_name=${beanType.document_name}" >
                                                        <i class="fas fa-download"></i>
                                                    </a>
                                                </c:if>
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
                    <h4 class="modal-title"></h4>
                </div>
                <button type="button" class="close text-white" data-dismiss="modal">&times;</button>
            </div>
            <div class="modal-body">
                <p class="fontFourteen messagebox"></p>
            </div>
        </div>
    </div>
</div>


<%@include file="/CRM Dashboard/CRM_footer.jsp" %>
<script>
    function showMessage(count) {
        $('.messagebox').html($('#message' + count).val());
        $('.modal-title').html($('#subject' + count).val());
    }

</script>