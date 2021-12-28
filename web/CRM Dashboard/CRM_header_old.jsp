<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <title>Admin Dashboard</title>
        <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Source+Sans+Pro:300,400,400i,700&display=fallback">
        <link rel="preconnect" href="https://fonts.googleapis.com">
        <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
        <link href="https://fonts.googleapis.com/css2?family=Lobster&display=swap" rel="stylesheet">
        <link rel="stylesheet" href="CRM Dashboard/plugins/fontawesome-free/css/all.min.css">
        <link rel="stylesheet" href="CRM Dashboard/plugins/tempusdominus-bootstrap-4/css/tempusdominus-bootstrap-4.min.css">
        <link rel="stylesheet" href="CRM Dashboard/plugins/icheck-bootstrap/icheck-bootstrap.min.css">
        <link rel="stylesheet" href="CRM Dashboard/assets2/css/adminlte.min.css">
        <link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/1.10.25/css/jquery.dataTables.min.css">
        <link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/buttons/1.7.1/css/buttons.dataTables.min.css">
        <link rel="stylesheet" href="CRM Dashboard/plugins/daterangepicker/daterangepicker.css">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/OwlCarousel2/2.3.4/assets/owl.carousel.min.css">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/OwlCarousel2/2.3.4/assets/owl.theme.default.css">
        <link rel="stylesheet" href="CRM Dashboard/assets2/css/myStyle.css">
    </head>
    <body class="hold-transition sidebar-mini layout-fixed" >
        <div class="wrapper">
            <nav class="main-header navbar navbar-expand navbar-white navbar-light">
                <ul class="navbar-nav">
                    <li class="nav-item">
                        <a class="nav-link" data-widget="pushmenu" href="#" role="button"><i class="fas fa-bars"></i></a>
                    </li>

                </ul>

                <ul class="navbar-nav ml-auto mr-3">
                    <!-- Notifications Dropdown Menu -->
                    <li class="nav-item dropdown">
                        <a class="nav-link" data-toggle="dropdown" href="#">
                            <i class="far fa-bell"></i>
                            <span class="badge badge-danger navbar-badge"> <%= session.getAttribute("total_notification")%></span>
                        </a>
                        <div class="dropdown-menu dropdown-menu-lg dropdown-menu-right">
                            <span class="dropdown-item dropdown-header"><%= session.getAttribute("total_notification")%> Notifications</span>
                            <div class="dropdown-divider"></div>

                            <%
                                if (session.getAttribute("user_role").equals("Admin")) {
                            %>
                            <a href="SalesEnquiryController?task=sales_enquiry_list" class="dropdown-item">
                                <i class="fas fa-envelope mr-2"></i> <%= session.getAttribute("sales_enquiries")%> Sales Enquiry
                                <span class="float-right text-muted text-sm">3 mins</span>
                            </a>
                            <div class="dropdown-divider"></div>
                            <a href="SalesEnquiryController?task=complaint_enquiry_list" class="dropdown-item">
                                <i class="fas fa-envelope mr-2"></i> <%= session.getAttribute("complaint_enquiries")%> Complaint Enquiry
                                <span class="float-right text-muted text-sm">12 hours</span>
                            </a>
                            <div class="dropdown-divider"></div>
                            <a href="notification.php" class="dropdown-item">
                                <i class="far fa-newspaper mr-2"></i> 3 News 
                                <span class="float-right text-muted text-sm">2 days</span>
                            </a>
                            <% }%>
                            <%
                                if (session.getAttribute("user_role").equals("Dealer")) {
                            %>
                            <a href="DealersOrderController?task=sales_enquiry_list" class="dropdown-item">
                                <i class="fas fa-envelope mr-2"></i> <%= session.getAttribute("sales_enquiries")%> Sales Enquiry
                                <span class="float-right text-muted text-sm">3 mins</span>
                            </a>
                            <div class="dropdown-divider"></div>
                            <a href="DealersOrderController?task=complaint_enquiry_list" class="dropdown-item">
                                <i class="fas fa-envelope mr-2"></i> <%= session.getAttribute("complaint_enquiries")%> Complaint Enquiry
                                <span class="float-right text-muted text-sm">12 hours</span>
                            </a>
                            <div class="dropdown-divider"></div>
                            <a href="notification.php" class="dropdown-item">
                                <i class="far fa-newspaper mr-2"></i> 3 News 
                                <span class="float-right text-muted text-sm">2 days</span>
                            </a>
                            <% }%>
                            <%
                                if (session.getAttribute("user_role").equals("Sales")) {
                            %>
                            <a href="ApproveOrdersController?task=sales_enquiry_list" class="dropdown-item">
                                <i class="fas fa-envelope mr-2"></i> <%= session.getAttribute("sales_enquiries")%> Sales Enquiry
                                <span class="float-right text-muted text-sm">3 mins</span>
                            </a>
                            <div class="dropdown-divider"></div>
                            <a href="ApproveOrdersController?task=complaint_enquiry_list" class="dropdown-item">
                                <i class="fas fa-envelope mr-2"></i> <%= session.getAttribute("complaint_enquiries")%> Complaint Enquiry
                                <span class="float-right text-muted text-sm">12 hours</span>
                            </a>
                            <div class="dropdown-divider"></div>
                            <a href="notification.php" class="dropdown-item">
                                <i class="far fa-newspaper mr-2"></i> 3 News 
                                <span class="float-right text-muted text-sm">2 days</span>
                            </a>
                            <% }%>

                            <div class="dropdown-divider"></div>
                        </div>
                    </li>

                </ul>


                <aside class="main-sidebar sidebar-dark-primary elevation-4" id="asideID">
                    <a href="CRMDashboardController" class="brand-link bg-white pl-3" style="margin-top: -6px;">
                        <img src="CRM Dashboard/assets2/img/product/logoIcon.png" class="brand-image img-circle elevation-3 mr-1 ml-0" style="margin-top:10px;">
                        <span class="brand-text font-weight-light1 font-weight-bold">
                            <img src="CRM Dashboard/assets2/img/product/logoText.png">
                        </span>
                    </a>
                    <div class="sidebarNavWrap">
                        <div class="user-panel mt-3 pb-3 mb-3 d-flex">
                            <%
                                if (session.getAttribute("user_role").equals("Admin") || session.getAttribute("user_role").equals("Dealer") || session.getAttribute("user_role").equals("Sales") || session.getAttribute("user_role").equals("Incharge")) {
                            %>
                            <div class="image">
                                <img src="CRM Dashboard/assets2/img/product/profileImg.png" class="img-circle usr_image elevation-2" alt="User Image">
                            </div>
                            <div class="info ml-3">
                                <%
                                    if (session.getAttribute("user_role").equals("Dealer")) {
                                %>
                                <a href="#" class="d-block"><%= session.getAttribute("logged_org_office")%></a>
                                <%}%>
                                <%
                                    if (session.getAttribute("user_role").equals("Sales")) {
                                %>
                                <a href="#" class="d-block"><%= session.getAttribute("logged_user_name")%></a>
                                <%}%>
                                <%
                                    if (session.getAttribute("user_role").equals("Admin")) {
                                %>
                                <a href="#" class="d-block">Admin</a>
                                <%}%>
                            </div>
                            <%}%>
                        </div>
                        <nav class="mt-2">

                            <%
                                if (session.getAttribute("user_role").equals("Admin") || session.getAttribute("user_role").equals("Dealer") || session.getAttribute("user_role").equals("Sales") || session.getAttribute("user_role").equals("Incharge")) {
                            %>
                            <%
                                if (session.getAttribute("user_role").equals("Dealer")) {
                            %>
                            <ul class="nav nav-pills nav-sidebar flex-column" data-widget="treeview" role="menu" data-accordion="true">
                                <li class="nav-item">
                                    <a href="CRMDashboardController" class="nav-link">
                                        <i class="nav-icon fas fa-tachometer-alt"></i>
                                        <p>Dashboard</p>
                                    </a>
                                </li>
                                <li class="nav-item">
                                    <a href="#" class="nav-link">
                                        <i class="nav-icon fas fa-layer-group"></i>
                                        <p>
                                            Order Management
                                            <i class="fas fa-angle-left right"></i>
                                        </p>
                                    </a>
                                    <ul class="nav nav-treeview">
                                        <li class="nav-item">
                                            <a href="DealersOrderController" class="nav-link">
                                                <i class="far fa-circle nav-icon"></i>
                                                <p>Make Order</p>
                                            </a>
                                        </li>
                                        <li class="nav-item">
                                            <a href="PendingOrdersController" class="nav-link">
                                                <i class="far fa-circle nav-icon"></i>
                                                <p>Pending Order</p>
                                            </a>
                                        </li>
                                        <li class="nav-item">
                                            <a href="OrdersHistoryController" class="nav-link">
                                                <i class="far fa-circle nav-icon"></i>
                                                <p>Order History</p>
                                            </a>
                                        </li>
                                    </ul>
                                </li>


                                <li class="nav-item">
                                    <a href="#" class="nav-link">
                                        <i class="nav-icon fas fa-envelope"></i>
                                        <p>
                                            Enquiry Management
                                            <i class="fas fa-angle-left right"></i>
                                        </p>
                                    </a>
                                    <ul class="nav nav-treeview">
                                        <li class="nav-item">
                                            <a href="DealersOrderController?task=sales_enquiry_list" class="nav-link">
                                                <i class="far fa-circle nav-icon"></i>
                                                <p>Sales Enquiry List</p>
                                            </a>
                                        </li>

                                        <li class="nav-item">
                                            <a href="DealersOrderController?task=complaint_enquiry_list" class="nav-link">
                                                <i class="far fa-circle nav-icon"></i>
                                                <p>Complaint Enquiry List</p>
                                            </a>
                                        </li>

                                    </ul>

                                </li>

                                <li class="nav-item">
                                    <a href="#" class="nav-link">
                                        <i class="nav-icon far fa-envelope"></i>
                                        <p>
                                            Message
                                            <i class="fas fa-angle-left right"></i>
                                        </p>
                                    </a>
                                    <ul class="nav nav-treeview">
                                        <!--                                        <li class="nav-item">
                                                                                    <a href="DealersEnquiryController" class="nav-link">
                                                                                        <i class="far fa-circle nav-icon"></i>
                                                                                        <p>For Sale</p>
                                                                                    </a>
                                                                                </li>
                                                                                <li class="nav-item">
                                                                                    <a href="DealersComplaintController" class="nav-link">
                                                                                        <i class="far fa-circle nav-icon"></i>
                                                                                        <p>For Complaint</p>
                                                                                    </a>
                                                                                </li>-->
                                        <li class="nav-item">
                                            <a href="NotificationController" class="nav-link">
                                                <i class="far fa-circle nav-icon"></i>
                                                <p>Notification</p>
                                            </a>
                                        </li>
                                    </ul>
                                </li>
                                <li class="nav-item">
                                    <a href="#" class="nav-link">
                                        <i class="nav-icon fas fa-user"></i>
                                        <p>
                                            My Account
                                            <i class="fas fa-angle-left right"></i>
                                        </p>
                                    </a>
                                    <ul class="nav nav-treeview">
                                        <li class="nav-item">
                                            <a href="ProfileController" class="nav-link">
                                                <i class="far fa-circle nav-icon"></i>
                                                <p>My Profile</p>
                                            </a>
                                        </li>
                                        <li class="nav-item">
                                            <a href="HelpController" class="nav-link">
                                                <i class="far fa-circle nav-icon"></i>
                                                <p>Help</p>
                                            </a>
                                        </li>
                                    </ul>
                                </li>
                                <li class="nav-item">
                                    <div class="neuBtn mt-2">
                                        <a href="LoginController?task=logout" class="nav-link text-danger">
                                            <!-- <i class="nav-icon fas fa-power-off"></i> -->
                                            <img src="CRM Dashboard/assets2/img/product/power-off1.png" width="20">
                                            &nbsp
                                            <p>Logout</p>
                                        </a>
                                    </div>                  
                                </li>
                            </ul>
                            <%}%>
                            <%}%>

                            <%
                                if (session.getAttribute("user_role").equals("Sales")) {
                            %>
                            <ul class="nav nav-pills nav-sidebar flex-column" data-widget="treeview" role="menu" data-accordion="true">
                                <li class="nav-item">
                                    <a href="CRMDashboardController" class="nav-link">
                                        <i class="nav-icon fas fa-tachometer-alt"></i>
                                        <p>Dashboard</p>
                                    </a>
                                </li>
                                <li class="nav-item">
                                    <a href="#" class="nav-link">
                                        <i class="nav-icon fas fa-layer-group"></i>
                                        <p>
                                            Order Management
                                            <i class="fas fa-angle-left right"></i>
                                        </p>
                                    </a>
                                    <ul class="nav nav-treeview">
                                        <li class="nav-item">
                                            <a href="ApproveOrdersController" class="nav-link">
                                                <i class="far fa-circle nav-icon"></i>
                                                <p>Approve Order</p>
                                            </a>
                                        </li>
                                    </ul>

                                </li>


                                <li class="nav-item">
                                    <a href="#" class="nav-link">
                                        <i class="nav-icon fas fa-envelope"></i>
                                        <p>
                                            Enquiry Management
                                            <i class="fas fa-angle-left right"></i>
                                        </p>
                                    </a>
                                    <ul class="nav nav-treeview">
                                        <li class="nav-item">
                                            <a href="ApproveOrdersController?task=sales_enquiry_list" class="nav-link">
                                                <i class="far fa-circle nav-icon"></i>
                                                <p>Sales Enquiry List</p>
                                            </a>
                                        </li>

                                        <li class="nav-item">
                                            <a href="ApproveOrdersController?task=complaint_enquiry_list" class="nav-link">
                                                <i class="far fa-circle nav-icon"></i>
                                                <p>Complaint Enquiry List</p>
                                            </a>
                                        </li>

                                    </ul>

                                </li>
                                <li class="nav-item">
                                    <div class="neuBtn mt-2">
                                        <a href="LoginController?task=logout" class="nav-link text-danger">
                                            <!-- <i class="nav-icon fas fa-power-off"></i> -->
                                            <img src="CRM Dashboard/assets2/img/product/power-off1.png" width="20">
                                            &nbsp
                                            <p>Logout</p>
                                        </a>
                                    </div>                  
                                </li>
                            </ul>
                            <%}%>


                            <%
                                if (session.getAttribute("user_role").equals("Admin")) {
                            %>

                            <ul class="nav nav-pills nav-sidebar flex-column" data-widget="treeview" role="menu" data-accordion="true">

                                <li class="nav-item">
                                    <a href="CRMDashboardController" class="nav-link">
                                        <i class="nav-icon fas fa-tachometer-alt"></i>
                                        <p>Dashboard</p>
                                    </a>
                                </li>

                                <li class="nav-item">
                                    <a href="#" class="nav-link">
                                        <i class="nav-icon fas fa-layer-group"></i>
                                        <p>
                                            Order Management
                                            <i class="fas fa-angle-left right"></i>
                                        </p>
                                    </a>
                                    <ul class="nav nav-treeview">
                                        <li class="nav-item">
                                            <a href="PendingOrdersController" class="nav-link">
                                                <i class="far fa-circle nav-icon"></i>
                                                <p>Pending Order</p>
                                            </a>
                                        </li>
                                        <li class="nav-item">
                                            <a href="OrdersHistoryController" class="nav-link">
                                                <i class="far fa-circle nav-icon"></i>
                                                <p>Order History</p>
                                            </a>
                                        </li>
                                    </ul>
                                </li>

                                <li class="nav-item">
                                    <a href="#" class="nav-link">
                                        <i class="nav-icon fas fa-users"></i>
                                        <p>
                                            Dealers Network
                                            <i class="fas fa-angle-left right"></i>
                                        </p>
                                    </a>
                                    <ul class="nav nav-treeview">
                                        <li class="nav-item">
                                            <a href="DealersController" class="nav-link">
                                                <i class="far fa-circle nav-icon"></i>
                                                <p>Dealers</p>
                                            </a>
                                        </li>
                                    </ul>
                                </li>


                                <li class="nav-item">
                                    <a href="#" class="nav-link">
                                        <i class="nav-icon fas fa-envelope"></i>
                                        <p>
                                            Enquiry Management
                                            <i class="fas fa-angle-left right"></i>
                                        </p>
                                    </a>
                                    <ul class="nav nav-treeview">
                                        <li class="nav-item">
                                            <a href="EnquirySourceController" class="nav-link">
                                                <i class="far fa-circle nav-icon"></i>
                                                <p>Enquiry Source</p>
                                            </a>
                                        </li>
                                        <li class="nav-item">
                                            <a href="MarketingVerticalController" class="nav-link">
                                                <i class="far fa-circle nav-icon"></i>
                                                <p>Marketing Vertical</p>
                                            </a>
                                        </li>
                                        <li class="nav-item">
                                            <a href="SalesEnquiryController" class="nav-link">
                                                <i class="far fa-circle nav-icon"></i>
                                                <p>Add Enquiry</p>
                                            </a>
                                        </li>
                                        <li class="nav-item">
                                            <a href="SalesEnquiryController?task=sales_enquiry_list" class="nav-link">
                                                <i class="far fa-circle nav-icon"></i>
                                                <p>Sales Enquiry List</p>
                                            </a>
                                        </li>

                                        <li class="nav-item">
                                            <a href="SalesEnquiryController?task=complaint_enquiry_list" class="nav-link">
                                                <i class="far fa-circle nav-icon"></i>
                                                <p>Complaint Enquiry List</p>
                                            </a>
                                        </li>

                                    </ul>

                                </li>
                                <li class="nav-item">
                                    <div class="neuBtn mt-2">
                                        <a href="LoginController?task=logout" class="nav-link text-danger">
                                            <!-- <i class="nav-icon fas fa-power-off"></i> -->
                                            <img src="CRM Dashboard/assets2/img/product/power-off1.png" width="20">
                                            &nbsp
                                            <p>Logout</p>
                                        </a>
                                    </div>                  
                                </li>
                            </ul>

                            <%}%>
                        </nav>
                    </div>
                    </div>
                </aside>
                <script src="http://ajax.googleapis.com/ajax/libs/jquery/1.7.1/jquery.min.js" type="text/javascript"></script>

                <script>
                    $('.usr_image').attr("src", "http://120.138.10.146:8080/APL/CRMDashboardController?task=viewImage");

                </script>