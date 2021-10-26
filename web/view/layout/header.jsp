<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <meta charset="utf-8">
        <title>APL</title>
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
        <script src="//code.jquery.com/jquery-1.10.2.js"></script>
        <script src="//code.jquery.com/ui/1.10.4/jquery-ui.js"></script>
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
        <!--<link rel="stylesheet" href="https://cdn.datatables.net/1.10.25/css/dataTables.bootstrap4.min.css">-->
        <!--<link href="https://cdn.jsdelivr.net/npm/select2@4.1.0-rc.0/dist/css/select2.min.css" rel="stylesheet" />-->
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css">
        <link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/1.10.25/css/jquery.dataTables.min.css">
        <link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/buttons/1.7.1/css/buttons.dataTables.min.css">
        <link rel="stylesheet" type="text/css" href="assets/css/jquery-ui.css">
        <link rel="stylesheet" type="text/css" href="assets/css/style.css">
    </head>
    <body>
        <div class="mainMenuWrap" id="mainMenuWrap">
            <div class="mainMenuDataEntry ">
                <nav class="navbar navbar-expand-sm navbar-dark">
                    <div class="container px-0 position-relative">
                        <a class="navbar-brand" href="dashboard">
                            <img src="assets/images/logo.png" width="100">
                        </a>
                        <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#collapsibleNavbar">
                            <span class="navbar-toggler-icon"></span>
                        </button>
                        <div class="collapse navbar-collapse" id="collapsibleNavbar">
                            <ul class="navbar-nav mx-auto">
                                <!--                                <li class="nav-item">
                                                                    <a class="nav-link" href="dashboard">Home</a>
                                                                </li>-->

                                <%
                                    if (session.getAttribute("user_role").equals("Super Admin")) {
                                %>

                                <li class="nav-item dropdown">
                                    <a class="nav-link dropdown-toggle" href="#" id="navbardrop" data-toggle="dropdown">
                                        Organization
                                    </a>
                                    <div class="dropdown-menu">
                                        <a class="dropdown-item" href="OrganisationTypeController">Org. Type</a>
                                        <a class="dropdown-item" href="OrganizationNameController">Org. Name</a>
                                        <a class="dropdown-item" href="OrgOfficeTypeController" class="dropdown-item">Org. Office Type</a>
                                        <a class="dropdown-item" href="OrgOfficeController" class="dropdown-item">Org. Office</a>
                                        <a class="dropdown-item" href="DesignationController" class="dropdown-item">Designation</a>
                                        <a class="dropdown-item" href="OrgOfficeDesignationMapController" class="dropdown-item">Org. Office Designation Map</a>
                                        <a class="dropdown-item" href="KeypersonController" class="dropdown-item">Org Person's Name</a>
                                        <a class="dropdown-item" href="generateSpreadSheetController" class="dropdown-item">Report</a>

                                    </div>
                                </li>  
                                <%}%>

                                <%
                                    if (session.getAttribute("user_role").equals("Super Admin") || session.getAttribute("user_role").equals("Employee")
                                            || session.getAttribute("user_role").equals("Incharge")) {
                                %>
                                <li class="nav-item dropdown">
                                    <a class="nav-link dropdown-toggle" href="#" id="navbardrop" data-toggle="dropdown">
                                        Inventory
                                    </a>
                                    <div class="dropdown-menu">
                                        <%
                                            if (session.getAttribute("user_role").equals("Super Admin")) {
                                        %>
                                        <a href="ItemTypeController" class="dropdown-item">Item Type</a>
                                        <a href="ItemNameController" class="dropdown-item">Item Name</a>
                                        <a href="ManufacturerController" class="dropdown-item">Manufacturer</a>
                                        <!--<a href="ManufacturerItemMapController" class="dropdown-item">Manufacturer Item Map</a>-->
                                        <a href="ModelNameController" class="dropdown-item">Manufacturer Item Model Map</a>
                                        <a href="InventoryBasicController" class="dropdown-item">Inventory Basic</a>
                                        <a href="InventoryController" class="dropdown-item">Inventory</a>
                                        <a href="ItemAuthorizationController" class="dropdown-item">Item Authorization</a>
                                        <%}%>
                                        <%
                                            if (session.getAttribute("user_role").equals("Employee") || session.getAttribute("user_role").equals("Super Admin")) {
                                        %>
                                        <a href="IndentController" class="dropdown-item">Request Indent</a>
                                        <%}%>
                                        <%
                                            if (session.getAttribute("user_role").equals("Super Admin")) {
                                        %>
                                        <a href="ApproveIndentController" class="dropdown-item">Approve Indent</a>
                                        <%}%>
                                        <%
                                            if (session.getAttribute("user_role").equals("Incharge") || session.getAttribute("user_role").equals("Super Admin")) {
                                        %>
                                        <a href="CheckInventoryController" class="dropdown-item">Check Inventory & Generate Delivery Challan</a>
                                        <%}%>
                                        <!--                                        <a href="DeliverItemController" class="dropdown-item">Delivery Chalan</a>-->
                                    </div> 
                                </li>
                                <%}%>

                                <li class="nav-item dropdown">
                                    <a class="nav-link dropdown-toggle" href="#" id="navbardrop" data-toggle="dropdown">
                                        Location
                                    </a>
                                    <div class="dropdown-menu">
                                        <a href="CityController" class="dropdown-item">City</a>
                                    </div>
                                </li>


                                <%
                                    if (session.getAttribute("user_role").equals("Super Admin")) {
                                %>

                                <li class="nav-item dropdown">
                                    <a class="nav-link dropdown-toggle" href="#" id="navbardrop" data-toggle="dropdown">
                                        Admin
                                    </a>
                                    <div class="dropdown-menu">
                                        <a href="AttendanceController" class="dropdown-item">View Attendance</a>     
                                        <a href="CalendarController" class="dropdown-item">View Calendar</a>
                                        <a href="UserPrivilegeController" class="dropdown-item">User Privilege</a>
                                    </div>
                                </li>                              


                                <%}
                                %>

                                <%
                                    if (session.getAttribute("user_role").equals("Super Admin") || session.getAttribute("user_role").equals("Dealer") || session.getAttribute("user_role").equals("Sales") || session.getAttribute("user_role").equals("Incharge")) {
                                %>
                                <li class="nav-item dropdown">
                                    <a class="nav-link dropdown-toggle" href="#" id="navbardrop" data-toggle="dropdown">
                                        Order
                                    </a>
                                    <div class="dropdown-menu">
                                        <%
                                            if (session.getAttribute("user_role").equals("Super Admin")) {
                                        %>
                                        <a href="DealerSalemanMapController" class="dropdown-item">Dealer-Salesman-Mapping</a> 
                                        <%}%>
                                        <%
                                            if (session.getAttribute("user_role").equals("Dealer")) {
                                        %>
                                        <a href="OrderController" class="dropdown-item">OrderForm</a>
                                        <%}%>

                                        <%
                                            if (session.getAttribute("user_role").equals("Sales")) {
                                        %>
                                        <a href="ApproveOrderController" class="dropdown-item">Approve Order</a>     
                                        <%}%>

                                        <%
                                            if (session.getAttribute("user_role").equals("Incharge")) {
                                        %>
                                        <a href="CheckOrderInventoryController" class="dropdown-item">Check Order Inventory</a>     
                                        <%}%>

                                    </div>
                                </li>
                                <%}%>


                                <%
                                    if (!session.getAttribute("user_role").equals("Dealer") && !session.getAttribute("user_role").equals("Sales")) {
                                %>

                                <li class="nav-item dropdown">
                                    <a class="nav-link dropdown-toggle" href="#" id="navbardrop" data-toggle="dropdown">
                                        Flow Screens
                                    </a>
                                    <div class="dropdown-menu">
                                        <a href="Flow?nxt=1" class="dropdown-item">Flow</a>                                             
                                    </div>
                                </li>

                                <%}
                                %>

                                <c:if test="${mode eq 'screens'}">

                                    <div class="prevNextWrap">
                                        <a class="dropdown-item" href="Flow?nxt=${iddd}">
                                            <i class="fa fa-angle-left"></i> &nbsp Prev
                                        </a>
                                        <a class="dropdown-item" href="Flow?nxt=${idd}">
                                            Next &nbsp <i class="fa fa-angle-right"></i>
                                        </a>                                   
                                    </div>

                                </c:if>

                                <!--                                <li class="nav-item dropdown">
                                                                    <a class="nav-link dropdown-toggle" href="#" id="navbardrop" data-toggle="dropdown">
                                                                        Admin
                                                                    </a>
                                                                    <div class="dropdown-menu">
                                                                        <a href="AttendanceController" class="dropdown-item">View Attendance</a>     
                                                                        <a href="CalendarController" class="dropdown-item">View Calendar</a>
                                                                        <a href="UserPrivilegeController" class="dropdown-item">User Privilege</a>
                                                                    </div>
                                                                </li>-->

                            </ul>

                            <div>

                                <a href="LoginController?task=logout"> 
                                    <img src="assets/images/logout.png" width="27">
                                </a>&nbsp 
                                <a href="home" class="btn btn-info"> 
                                    Go to Website
                                </a>
                            </div>


                        </div>
                    </div>
                </nav>
            </div>
        </div>
