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
                                    <tr>
                                        <td class="fontFourteen">1</td>
                                        <td class="fontFourteen"><a href="">Kundan Pandey</a></td>
                                        <td class="fontFourteen">Why do we use it?</td>
                                        <!--<td class="fontFourteen" style="max-width: 250px;">It is a long established fact that a reader will be distracted. </td>-->
                                        <td class=""><i class="statusDisapprove fontThirteen">Unread</i></td>
                                        <td class="fontFourteen">5 mins ago</td>
                                        <td>
                                            <a href="notificationDetail.php" class="btn far fa-eye actionEdit" title="See Message"></a>
                                            <a onclick="return confirm('Are you sure you want to delete this message?');" href="#" class="btn far fa-trash-alt actionDelete" title="Delete Message"></a>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td class="fontFourteen">1</td>
                                        <td class="fontFourteen"><a href="">Kundan Pandey</a></td>
                                        <td class="fontFourteen">Why do we use it?</td>
                                        <!--<td class="fontFourteen" style="max-width: 250px;">It is a long established fact that a reader will be distracted. </td>-->
                                        <td class=""><i class="statusDisapprove fontThirteen">Unread</i></td>
                                        <td class="fontFourteen">5 mins ago</td>
                                        <td>
                                            <a href="notificationDetail.php" class="btn far fa-eye actionEdit" title="See Message"></a>
                                            <a onclick="return confirm('Are you sure you want to delete this message?');" href="#" class="btn far fa-trash-alt actionDelete" title="Delete Message"></a>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td class="fontFourteen">1</td>
                                        <td class="fontFourteen"><a href="">Kundan Pandey</a></td>
                                        <td class="fontFourteen">Why do we use it?</td>
                                        <!--<td class="fontFourteen" style="max-width: 250px;">It is a long established fact that a reader will be distracted. </td>-->
                                        <td class=""><i class="statusDisapprove fontThirteen">Unread</i></td>
                                        <td class="fontFourteen">5 mins ago</td>
                                        <td>
                                            <a href="notificationDetail.php" class="btn far fa-eye actionEdit" title="See Message"></a>
                                            <a onclick="return confirm('Are you sure you want to delete this message?');" href="#" class="btn far fa-trash-alt actionDelete" title="Delete Message"></a>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td class="fontFourteen">1</td>
                                        <td class="fontFourteen"><a href="">Kundan Pandey</a></td>
                                        <td class="fontFourteen">Why do we use it?</td>
                                        <!--<td class="fontFourteen" style="max-width: 250px;">It is a long established fact that a reader will be distracted. </td>-->
                                        <td class=""><i class="statusDisapprove fontThirteen">Unread</i></td>
                                        <td class="fontFourteen">5 mins ago</td>
                                        <td>
                                            <a href="notificationDetail.php" class="btn far fa-eye actionEdit" title="See Message"></a>
                                            <a onclick="return confirm('Are you sure you want to delete this message?');" href="#" class="btn far fa-trash-alt actionDelete" title="Delete Message"></a>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td class="fontFourteen">1</td>
                                        <td class="fontFourteen"><a href="">Kundan Pandey</a></td>
                                        <td class="fontFourteen">Why do we use it?</td>
                                        <!--<td class="fontFourteen" style="max-width: 250px;">It is a long established fact that a reader will be distracted. </td>-->
                                        <td class=""><i class="statusDisapprove fontThirteen">Unread</i></td>
                                        <td class="fontFourteen">5 mins ago</td>
                                        <td>
                                            <a href="notificationDetail.php" class="btn far fa-eye actionEdit" title="See Message"></a>
                                            <a onclick="return confirm('Are you sure you want to delete this message?');" href="#" class="btn far fa-trash-alt actionDelete" title="Delete Message"></a>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td class="fontFourteen">1</td>
                                        <td class="fontFourteen"><a href="">Kundan Pandey</a></td>
                                        <td class="fontFourteen">Why do we use it?</td>
                                        <!--<td class="fontFourteen" style="max-width: 250px;">It is a long established fact that a reader will be distracted. </td>-->
                                        <td class=""><i class="statusDisapprove fontThirteen">Unread</i></td>
                                        <td class="fontFourteen">5 mins ago</td>
                                        <td>
                                            <a href="notificationDetail.php" class="btn far fa-eye actionEdit" title="See Message"></a>
                                            <a onclick="return confirm('Are you sure you want to delete this message?');" href="#" class="btn far fa-trash-alt actionDelete" title="Delete Message"></a>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td class="fontFourteen">1</td>
                                        <td class="fontFourteen"><a href="">Kundan Pandey</a></td>
                                        <td class="fontFourteen">Why do we use it?</td>
                                        <!--<td class="fontFourteen" style="max-width: 250px;">It is a long established fact that a reader will be distracted. </td>-->
                                        <td class=""><i class="statusDisapprove fontThirteen">Unread</i></td>
                                        <td class="fontFourteen">5 mins ago</td>
                                        <td>
                                            <a href="notificationDetail.php" class="btn far fa-eye actionEdit" title="See Message"></a>
                                            <a onclick="return confirm('Are you sure you want to delete this message?');" href="#" class="btn far fa-trash-alt actionDelete" title="Delete Message"></a>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td class="fontFourteen">1</td>
                                        <td class="fontFourteen"><a href="">Kundan Pandey</a></td>
                                        <td class="fontFourteen">Why do we use it?</td>
                                        <!--<td class="fontFourteen" style="max-width: 250px;">It is a long established fact that a reader will be distracted. </td>-->
                                        <td class=""><i class="statusDisapprove fontThirteen">Unread</i></td>
                                        <td class="fontFourteen">5 mins ago</td>
                                        <td>
                                            <a href="notificationDetail.php" class="btn far fa-eye actionEdit" title="See Message"></a>
                                            <a onclick="return confirm('Are you sure you want to delete this message?');" href="#" class="btn far fa-trash-alt actionDelete" title="Delete Message"></a>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td class="fontFourteen">1</td>
                                        <td class="fontFourteen"><a href="">Kundan Pandey</a></td>
                                        <td class="fontFourteen">Why do we use it?</td>
                                        <!--<td class="fontFourteen" style="max-width: 250px;">It is a long established fact that a reader will be distracted. </td>-->
                                        <td class=""><i class="statusDisapprove fontThirteen">Unread</i></td>
                                        <td class="fontFourteen">5 mins ago</td>
                                        <td>
                                            <a href="notificationDetail.php" class="btn far fa-eye actionEdit" title="See Message"></a>
                                            <a onclick="return confirm('Are you sure you want to delete this message?');" href="#" class="btn far fa-trash-alt actionDelete" title="Delete Message"></a>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td class="fontFourteen">1</td>
                                        <td class="fontFourteen"><a href="">Kundan Pandey</a></td>
                                        <td class="fontFourteen">Why do we use it?</td>
                                        <!--<td class="fontFourteen" style="max-width: 250px;">It is a long established fact that a reader will be distracted. </td>-->
                                        <td class=""><i class="statusDisapprove fontThirteen">Unread</i></td>
                                        <td class="fontFourteen">5 mins ago</td>
                                        <td>
                                            <a href="notificationDetail.php" class="btn far fa-eye actionEdit" title="See Message"></a>
                                            <a onclick="return confirm('Are you sure you want to delete this message?');" href="#" class="btn far fa-trash-alt actionDelete" title="Delete Message"></a>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td class="fontFourteen">1</td>
                                        <td class="fontFourteen"><a href="">Kundan Pandey</a></td>
                                        <td class="fontFourteen">Why do we use it?</td>
                                        <!--<td class="fontFourteen" style="max-width: 250px;">It is a long established fact that a reader will be distracted. </td>-->
                                        <td class=""><i class="statusDisapprove fontThirteen">Unread</i></td>
                                        <td class="fontFourteen">5 mins ago</td>
                                        <td>
                                            <a href="notificationDetail.php" class="btn far fa-eye actionEdit" title="See Message"></a>
                                            <a onclick="return confirm('Are you sure you want to delete this message?');" href="#" class="btn far fa-trash-alt actionDelete" title="Delete Message"></a>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td class="fontFourteen">1</td>
                                        <td class="fontFourteen"><a href="">Kundan Pandey</a></td>
                                        <td class="fontFourteen">Why do we use it?</td>
                                        <!--<td class="fontFourteen" style="max-width: 250px;">It is a long established fact that a reader will be distracted. </td>-->
                                        <td class=""><i class="statusDisapprove fontThirteen">Unread</i></td>
                                        <td class="fontFourteen">5 mins ago</td>
                                        <td>
                                            <a href="notificationDetail.php" class="btn far fa-eye actionEdit" title="See Message"></a>
                                            <a onclick="return confirm('Are you sure you want to delete this message?');" href="#" class="btn far fa-trash-alt actionDelete" title="Delete Message"></a>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td class="fontFourteen">1</td>
                                        <td class="fontFourteen"><a href="">Kundan Pandey</a></td>
                                        <td class="fontFourteen">Why do we use it?</td>
                                        <!--<td class="fontFourteen" style="max-width: 250px;">It is a long established fact that a reader will be distracted. </td>-->
                                        <td class=""><i class="statusDisapprove fontThirteen">Unread</i></td>
                                        <td class="fontFourteen">5 mins ago</td>
                                        <td>
                                            <a href="notificationDetail.php" class="btn far fa-eye actionEdit" title="See Message"></a>
                                            <a onclick="return confirm('Are you sure you want to delete this message?');" href="#" class="btn far fa-trash-alt actionDelete" title="Delete Message"></a>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td class="fontFourteen">1</td>
                                        <td class="fontFourteen"><a href="">Kundan Pandey</a></td>
                                        <td class="fontFourteen">Why do we use it?</td>
                                        <!--<td class="fontFourteen" style="max-width: 250px;">It is a long established fact that a reader will be distracted. </td>-->
                                        <td class=""><i class="statusDisapprove fontThirteen">Unread</i></td>
                                        <td class="fontFourteen">5 mins ago</td>
                                        <td>
                                            <a href="notificationDetail.php" class="btn far fa-eye actionEdit" title="See Message"></a>
                                            <a onclick="return confirm('Are you sure you want to delete this message?');" href="#" class="btn far fa-trash-alt actionDelete" title="Delete Message"></a>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td class="fontFourteen">1</td>
                                        <td class="fontFourteen"><a href="">Kundan Pandey</a></td>
                                        <td class="fontFourteen">Why do we use it?</td>
                                        <!--<td class="fontFourteen" style="max-width: 250px;">It is a long established fact that a reader will be distracted. </td>-->
                                        <td class=""><i class="statusDisapprove fontThirteen">Unread</i></td>
                                        <td class="fontFourteen">5 mins ago</td>
                                        <td>
                                            <a href="notificationDetail.php" class="btn far fa-eye actionEdit" title="See Message"></a>
                                            <a onclick="return confirm('Are you sure you want to delete this message?');" href="#" class="btn far fa-trash-alt actionDelete" title="Delete Message"></a>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td class="fontFourteen">1</td>
                                        <td class="fontFourteen"><a href="">Kundan Pandey</a></td>
                                        <td class="fontFourteen">Why do we use it?</td>
                                        <!--<td class="fontFourteen" style="max-width: 250px;">It is a long established fact that a reader will be distracted. </td>-->
                                        <td class=""><i class="statusDisapprove fontThirteen">Unread</i></td>
                                        <td class="fontFourteen">5 mins ago</td>
                                        <td>
                                            <a href="notificationDetail.php" class="btn far fa-eye actionEdit" title="See Message"></a>
                                            <a onclick="return confirm('Are you sure you want to delete this message?');" href="#" class="btn far fa-trash-alt actionDelete" title="Delete Message"></a>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td class="fontFourteen">1</td>
                                        <td class="fontFourteen"><a href="">Kundan Pandey</a></td>
                                        <td class="fontFourteen">Why do we use it?</td>
                                        <!--<td class="fontFourteen" style="max-width: 250px;">It is a long established fact that a reader will be distracted. </td>-->
                                        <td class=""><i class="statusDisapprove fontThirteen">Unread</i></td>
                                        <td class="fontFourteen">5 mins ago</td>
                                        <td>
                                            <a href="notificationDetail.php" class="btn far fa-eye actionEdit" title="See Message"></a>
                                            <a onclick="return confirm('Are you sure you want to delete this message?');" href="#" class="btn far fa-trash-alt actionDelete" title="Delete Message"></a>
                                        </td>
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
