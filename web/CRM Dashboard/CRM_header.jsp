<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <title>CRM Dashboard</title>
        <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Source+Sans+Pro:300,400,400i,700&display=fallback">
        <link rel="stylesheet" href="CRM Dashboard/plugins/fontawesome-free/css/all.min.css">
        <link rel="stylesheet" href="https://code.ionicframework.com/ionicons/2.0.1/css/ionicons.min.css">
        <link rel="stylesheet" href="CRM Dashboard/plugins/tempusdominus-bootstrap-4/css/tempusdominus-bootstrap-4.min.css">
        <link rel="stylesheet" href="CRM Dashboard/plugins/icheck-bootstrap/icheck-bootstrap.min.css">
        <link rel="stylesheet" href="CRM Dashboard/plugins/jqvmap/jqvmap.min.css">
        <link rel="stylesheet" href="CRM Dashboard/assets2/css/adminlte.min.css">
        <!--        <link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/1.10.25/css/jquery.dataTables.min.css">
                <link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/buttons/1.7.1/css/buttons.dataTables.min.css">-->
        <link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/1.11.3/css/jquery.dataTables.min.css">
        <link rel="stylesheet" href="CRM Dashboard/plugins/overlayScrollbars/css/OverlayScrollbars.min.css">
        <link rel="stylesheet" href="CRM Dashboard/plugins/daterangepicker/daterangepicker.css">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/OwlCarousel2/2.3.4/assets/owl.carousel.min.css">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/OwlCarousel2/2.3.4/assets/owl.theme.default.css">
        <link href = "https://code.jquery.com/ui/1.10.4/themes/ui-lightness/jquery-ui.css" rel = "stylesheet">
        <link rel="stylesheet" href="CRM Dashboard/assets2/css/myStyle.css">
        <link rel="stylesheet" type="text/css" href="CRM Dashboard/assets2/css/mobileResponsive.css">
    </head>
    <body class="hold-transition sidebar-mini layout-fixed">
        <div class="wrapper">

            <!-- Preloader -->
            <!--            <div class="preloader flex-column justify-content-center align-items-center">
                            <img class="animation__shake" src="CRM Dashboard/assets2/img/product/loaderLogoIcon.png" height="60" width="60">
                        </div>-->

            <!-- Navbar -->
            <nav class="main-header navbar navbar-expand navbar-white navbar-light">
                <!-- Left navbar links -->
                <ul class="navbar-nav">
                    <li class="nav-item">
                        <a class="nav-link" data-widget="pushmenu" href="#" role="button"><i class="fas fa-bars"></i></a>
                    </li>      
                </ul>

                <ul class="navbar-nav ml-auto">
                    <li class="nav-item">
                        <div class="languageConvertorWrap">
                            <div id="google_translate_element"></div>
                        </div>
                    </li>
                    <!--                    <li class="nav-item">
                                            <a class="nav-link" data-widget="navbar-search" href="#" role="button">
                                                <i class="fas fa-search"></i>
                                            </a>
                                            <div class="navbar-search-block">
                                                <form class="form-inline">
                                                    <div class="input-group input-group-sm">
                                                        <input class="form-control form-control-navbar" type="search" placeholder="Search" aria-label="Search">
                                                        <div class="input-group-append">
                                                            <button class="btn btn-navbar" type="submit">
                                                                <i class="fas fa-search"></i>
                                                            </button>
                                                            <button class="btn btn-navbar" type="button" data-widget="navbar-search">
                                                                <i class="fas fa-times"></i>
                                                            </button>
                                                        </div>
                                                    </div>
                                                </form>
                                            </div>
                                        </li>-->


                    <!-- Notifications Dropdown Menu -->
                    <li class="nav-item dropdown">
                        <a class="nav-link" data-toggle="dropdown" href="#">
                            <i class="far fa-bell"></i>
                            <span class="badge badge-warning navbar-badge"><%= session.getAttribute("total_notification")%></span>
                        </a>
                        <div class="dropdown-menu dropdown-menu-lg dropdown-menu-right">
                            <span class="dropdown-item dropdown-header"><%= session.getAttribute("total_notification")%> Notifications</span>
                            <div class="dropdown-divider"></div>


                            <%
                                if (session.getAttribute("user_role").equals("Admin")) {
                            %>
                            <a href="SalesEnquiryController?task=sales_enquiry_list" class="dropdown-item">
                                <i class="fas fa-envelope mr-2"></i> <%= session.getAttribute("sales_enquiries")%> Sales Enquiry
                                <span class="float-right text-muted text-sm">${last_time_of_enquiry}</span>
                            </a>
                            <div class="dropdown-divider"></div>
                            <a href="SalesEnquiryController?task=complaint_enquiry_list" class="dropdown-item">
                                <i class="fas fa-envelope mr-2"></i> <%= session.getAttribute("complaint_enquiries")%> Complaint Enquiry
                                <span class="float-right text-muted text-sm">${last_time_of_complaint}</span>
                            </a>
                            <!--                            <div class="dropdown-divider"></div>
                                                        <a href="notification.php" class="dropdown-item">
                                                            <i class="far fa-file mr-2"></i> 0 News 
                                                            <span class="float-right text-muted text-sm">2 days</span>
                                                        </a>-->
                            <% }%>
                            <%
                                if (session.getAttribute("user_role").equals("Dealer")) {
                            %>
                            <a href="DealersOrderController?task=sales_enquiry_list" class="dropdown-item">
                                <i class="fas fa-envelope mr-2"></i> <%= session.getAttribute("sales_enquiries")%> Sales Enquiry
                                <span class="float-right text-muted text-sm">${last_time_of_enquiry}</span>
                            </a>
                            <div class="dropdown-divider"></div>
                            <a href="DealersOrderController?task=complaint_enquiry_list" class="dropdown-item">
                                <i class="fas fa-envelope mr-2"></i> <%= session.getAttribute("complaint_enquiries")%> Complaint Enquiry
                                <span class="float-right text-muted text-sm">${last_time_of_complaint}</span>
                            </a>
                            <div class="dropdown-divider"></div>
                            <a href="notification.php" class="dropdown-item">
                                <i class="far fa-newspaper mr-2"></i> 0 News 
                                <span class="float-right text-muted text-sm"></span>
                            </a>
                            <% }%>
                            <%
                                if (session.getAttribute("user_role").equals("Sales")) {
                            %>
                            <a href="ApproveOrdersController?task=sales_enquiry_list" class="dropdown-item">
                                <i class="fas fa-envelope mr-2"></i> <%= session.getAttribute("sales_enquiries")%> Sales Enquiry
                                <span class="float-right text-muted text-sm">${last_time_of_enquiry}</span>
                            </a>
                            <div class="dropdown-divider"></div>
                            <a href="ApproveOrdersController?task=complaint_enquiry_list" class="dropdown-item">
                                <i class="fas fa-envelope mr-2"></i> <%= session.getAttribute("complaint_enquiries")%> Complaint Enquiry
                                <span class="float-right text-muted text-sm">${last_time_of_complaint}</span>
                            </a>
                            <!--                            <div class="dropdown-divider"></div>
                                                        <a href="notification.php" class="dropdown-item">
                                                            <i class="far fa-newspaper mr-2"></i> 0 News 
                                                            <span class="float-right text-muted text-sm">2 days</span>
                                                        </a>-->
                            <% }%>




                        </div>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" data-widget="fullscreen" href="#" role="button">
                            <i class="fas fa-expand-arrows-alt"></i>
                        </a>
                    </li>
                    <li class="nav-item mr-3">
                        <a class="nav-link lineHeightOneTwo pl-1" href="LoginController?task=logout">
                            <!-- <i class="fas fa-th-large">sda</i> -->
                            <img src="CRM Dashboard/assets2/img/product/power-off.png" width="20">
                        </a>
                    </li>
                </ul>
            </nav>
            <!-- /.navbar -->

            <!-- Main Sidebar Container -->
            <aside class="main-sidebar sidebar-dark-primary elevation-4">
                <!-- Brand Logo -->
                <a href="CRMDashboardController" class="brand-link">
                    <img src="CRM Dashboard/assets2/img/product/logoIcon.png" alt="AdminLTE Logo" class="brand-image img-circle elevation-3" style="opacity: .8;margin-top: 6px;">
                    <span class="brand-text font-weight-light1">Apogee</span>
                </a>

                <!-- Sidebar -->
                <div class="sidebar sidebarNavWrap">
                    <div class="user-panel mb-2 d-flex">
                        <%
                            if (session.getAttribute("user_role").equals("Admin") || session.getAttribute("user_role").equals("Dealer") || session.getAttribute("user_role").equals("Sales") || session.getAttribute("user_role").equals("Incharge")) {
                        %>
                        <div class="image">
                            <img src="CRM Dashboard/assets2/img/product/profileImg.png" class="img-circle elevation-2 usr_image" alt="User Image">
                        </div>
                        <div class="info">
                            <%
                                if (session.getAttribute("user_role").equals("Dealer")) {
                            %>
                            <a href="CRMDashboardController" class="d-block"><%= session.getAttribute("logged_org_office")%></a>
                            <%}%>
                            <%
                                if (session.getAttribute("user_role").equals("Sales")) {
                            %>
                            <a href="CRMDashboardController" class="d-block"><%= session.getAttribute("logged_user_name")%></a>
                            <%}%>
                            <%
                                if (session.getAttribute("user_role").equals("Admin")) {
                            %>
                            <!--<a href="CRMDashboardController" class="d-block">Admin</a>-->
                            <a href="CRMDashboardController" class="d-block"><%= session.getAttribute("logged_user_name")%></a>
                            <%}%>
                        </div>
                        <%}%>
                    </div>

                    <!-- Sidebar Menu -->
                    <nav class="mt-2">
                        <%
                            if (session.getAttribute("user_role").equals("Admin") || session.getAttribute("user_role").equals("Dealer") || session.getAttribute("user_role").equals("Sales") || session.getAttribute("user_role").equals("Incharge")) {
                        %>
                        <%
                            if (session.getAttribute("user_role").equals("Dealer")) {
                        %>



                        <ul class="nav nav-pills nav-sidebar flex-column" data-widget="treeview" role="menu" data-accordion="false">
                            <li class="nav-item">
                                <a href="CRMDashboardController" class="nav-link">
                                    <i class="nav-icon fas fa-th"></i>
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
                                        <sup class="px-1 text-white bg-danger rounded-sm fontTen"><%= session.getAttribute("total_notification")%></sup>
                                    </p>
                                </a>
                                <ul class="nav nav-treeview">
                                    <li class="nav-item">
                                        <a href="DealersOrderController?task=sales_enquiry_list" class="nav-link">
                                            <i class="far fa-circle nav-icon"></i>
                                            <p>Sales Enquiry List<sup class="px-1 text-white bg-danger rounded-sm fontTen"><%= session.getAttribute("sales_enquiries")%></sup></p>
                                        </a>
                                    </li>

                                    <li class="nav-item">
                                        <a href="DealersOrderController?task=complaint_enquiry_list" class="nav-link">
                                            <i class="far fa-circle nav-icon"></i>
                                            <p>Complaint Enquiry List<sup class="px-1 text-white bg-danger rounded-sm fontTen"><%= session.getAttribute("complaint_enquiries")%></sup></p>
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
                                <a href="LoginController?task=logout" class="nav-link lineHeightOneTwo">
                                    <img src="CRM Dashboard/assets2/img/product/power-off.png" width="20"> &nbsp
                                    <p>Logout</p>
                                </a>
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
                                    <i class="nav-icon fas fa-th"></i>
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
                                        <sup class="px-1 text-white bg-danger rounded-sm fontTen"><%= session.getAttribute("total_notification")%></sup>
                                    </p>
                                </a>
                                <ul class="nav nav-treeview">
                                    <li class="nav-item">
                                        <a href="ApproveOrdersController?task=sales_enquiry_list" class="nav-link">
                                            <i class="far fa-circle nav-icon"></i>
                                            <p>Sales Enquiry List <sup class="px-1 text-white bg-danger rounded-sm fontTen"><%= session.getAttribute("sales_enquiries")%></sup></p>
                                        </a>
                                    </li>

                                    <li class="nav-item">
                                        <a href="ApproveOrdersController?task=complaint_enquiry_list" class="nav-link">
                                            <i class="far fa-circle nav-icon"></i>
                                            <p>Complaint Enquiry List <sup class="px-1 text-white bg-danger rounded-sm fontTen"><%= session.getAttribute("complaint_enquiries")%></sup></p>
                                        </a>
                                    </li>

                                </ul>

                            </li>
                            <li class="nav-item">
                                <a href="LoginController?task=logout" class="nav-link lineHeightOneTwo">
                                    <img src="CRM Dashboard/assets2/img/product/power-off.png" width="20"> &nbsp
                                    <p>Logout</p>
                                </a>
                            </li>
                        </ul>
                        <%}%>


                        <%
                            if (session.getAttribute("user_role").equals("Admin")) {
                        %>

                        <ul class="nav nav-pills nav-sidebar flex-column" data-widget="treeview" role="menu" data-accordion="true">

                            <li class="nav-item">
                                <a href="CRMDashboardController" class="nav-link">
                                    <i class="nav-icon fas fa-th"></i>
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
                                    <li class="nav-item">
                                        <a href="DealersController?task=AddDealer" class="nav-link">
                                            <i class="far fa-circle nav-icon"></i>
                                            <p>Add Dealer</p>
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

                                        <sup class="px-1 text-white bg-danger rounded-sm fontTen"><%= session.getAttribute("total_notification")%></sup>

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
                                            <p>Sales Enquiry List  <sup class="px-1 text-white bg-danger rounded-sm fontTen"><%= session.getAttribute("sales_enquiries")%></sup></p>
                                        </a>
                                    </li>

                                    <li class="nav-item">
                                        <a href="SalesEnquiryController?task=complaint_enquiry_list" class="nav-link">
                                            <i class="far fa-circle nav-icon"></i>
                                            <p>Complaint Enquiry List <sup class="px-1 text-white bg-danger rounded-sm fontTen"><%= session.getAttribute("complaint_enquiries")%></sup></p>
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

                                    <li class="nav-item">
                                        <a href="SupportMessagesController" class="nav-link">
                                            <i class="far fa-circle nav-icon"></i>
                                            <p>Support Messages</p>
                                        </a>
                                    </li>
                                </ul>
                            </li>
                            <li class="nav-item">
                                <a href="LoginController?task=logout" class="nav-link lineHeightOneTwo">
                                    <img src="CRM Dashboard/assets2/img/product/power-off.png" width="20"> &nbsp
                                    <p>Logout</p>
                                </a>
                            </li>
                        </ul>

                        <%}%>
                    </nav>
                    <!-- /.sidebar-menu -->
                </div>
                <!-- /.sidebar -->
            </aside>



            <script src="http://ajax.googleapis.com/ajax/libs/jquery/1.7.1/jquery.min.js" type="text/javascript"></script>
            <script src="CRM Dashboard/assets2/js/myJS.js" type="text/javascript"></script>

            <script>
                $('.usr_image').attr("src", "http://" + IMAGE_URL + "/APL/CRMDashboardController?task=viewImage&type=ph");
            </script>
