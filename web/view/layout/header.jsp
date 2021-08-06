<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <meta charset="utf-8">
        <title>Data Entry: Organization Name Table</title>
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
        <!--<link rel="stylesheet" href="https://cdn.datatables.net/1.10.25/css/dataTables.bootstrap4.min.css">-->
        <!--<link href="https://cdn.jsdelivr.net/npm/select2@4.1.0-rc.0/dist/css/select2.min.css" rel="stylesheet" />-->
        <!--<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css">-->
        <link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/1.10.25/css/jquery.dataTables.min.css">
        <link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/buttons/1.7.1/css/buttons.dataTables.min.css">
        <link rel="stylesheet" type="text/css" href="assets/css/jquery-ui.css">
        <link rel="stylesheet" type="text/css" href="assets/css/style.css">
    </head>
    <body>
        <div class="mainMenuWrap" id="mainMenuWrap">
            <div class="mainMenuDataEntry ">
                <nav class="navbar navbar-expand-sm navbar-dark">
                    <div class="container px-0">
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
                                <li class="nav-item dropdown">
                                    <a class="nav-link dropdown-toggle" href="#" id="navbardrop" data-toggle="dropdown">
                                        Organization
                                    </a>
                                    <div class="dropdown-menu">
                                        <a class="dropdown-item" href="OrganizationNameController">Org. Name</a>
                                        <a class="dropdown-item" href="organization_type">Org. Type</a>
                                        <a class="dropdown-item" href="orgOfficeTypeCont.do" class="dropdown-item">Org. Office Type</a>
                                        <a class="dropdown-item" href="organisationCont.do" class="dropdown-item">Org. Office</a>
                                        <a class="dropdown-item" href="designationCont.do" class="dropdown-item">Designation</a>
                                        <a class="dropdown-item" href="personCount.do" class="dropdown-item">Org Person's Name</a>
                                        <a class="dropdown-item" href="OrganisationDesignationNewController" class="dropdown-item">Designation Organisation</a>
                                        <a class="dropdown-item" href="OrganisationTypeDesignationController" class="dropdown-item">Designation Organisation Type</a>
                                        <a class="dropdown-item" href="formdata" class="dropdown-item">FormData</a>
                                        <a class="dropdown-item" href="orgDetailEntryCont.do" class="dropdown-item">Org Detail Entry</a>
                                        <a class="dropdown-item" href="allinoneCont.do" class="dropdown-item">All in One</a>
                                        <a class="dropdown-item" href="keypersonnewCont.do" class="dropdown-item">KeyPerson New</a>
                                    </div>
                                </li>   
                                <li class="nav-item dropdown">
                                    <a class="nav-link dropdown-toggle" href="#" id="navbardrop" data-toggle="dropdown">
                                        Location
                                    </a>
                                    <div class="dropdown-menu">
                                        <a href="orgNameCont.do" class="dropdown-item">Org. Name</a>
                                        <a href="orgTypeCont.do" class="dropdown-item">Org. Type</a>
                                        <a href="orgOfficeTypeCont.do" class="dropdown-item">Org. Office Type</a>
                                        <a href="organisationCont.do" class="dropdown-item">Org. Office</a>
                                        <a href="designationCont.do" class="dropdown-item">Designation</a>
                                        <a href="personCount.do" class="dropdown-item">Org Person's Name</a>
                                        <a href="OrganisationDesignationNewController" class="dropdown-item">Designation Organisation</a>
                                        <a href="OrganisationTypeDesignationController" class="dropdown-item">Designation Organisation Type</a>
                                        <a href="formdata" class="dropdown-item">FormData</a>
                                        <a href="orgDetailEntryCont.do" class="dropdown-item">Org Detail Entry</a>
                                        <a href="allinoneCont.do" class="dropdown-item">All in One</a>
                                        <a href="keypersonnewCont.do" class="dropdown-item">KeyPerson New</a>
                                    </div>
                                </li>
                                <li class="nav-item dropdown">
                                    <a class="nav-link dropdown-toggle" href="#" id="navbardrop" data-toggle="dropdown">
                                        Shift
                                    </a>
                                    <div class="dropdown-menu">
                                        <a href="orgNameCont.do" class="dropdown-item">Org. Name</a>
                                        <a href="orgTypeCont.do" class="dropdown-item">Org. Type</a>
                                        <a href="orgOfficeTypeCont.do" class="dropdown-item">Org. Office Type</a>
                                        <a href="organisationCont.do" class="dropdown-item">Org. Office</a>
                                        <a href="designationCont.do" class="dropdown-item">Designation</a>
                                        <a href="personCount.do" class="dropdown-item">Org Person's Name</a>
                                        <a href="OrganisationDesignationNewController" class="dropdown-item">Designation Organisation</a>
                                        <a href="OrganisationTypeDesignationController" class="dropdown-item">Designation Organisation Type</a>
                                        <a href="formdata" class="dropdown-item">FormData</a>
                                        <a href="orgDetailEntryCont.do" class="dropdown-item">Org Detail Entry</a>
                                        <a href="allinoneCont.do" class="dropdown-item">All in One</a>
                                        <a href="keypersonnewCont.do" class="dropdown-item">KeyPerson New</a>
                                    </div>
                                </li> 
                                <li class="nav-item dropdown">
                                    <a class="nav-link dropdown-toggle" href="#" id="navbardrop" data-toggle="dropdown">
                                        E-Pass
                                    </a>
                                    <div class="dropdown-menu">
                                        <a href="orgNameCont.do" class="dropdown-item">Org. Name</a>
                                        <a href="orgTypeCont.do" class="dropdown-item">Org. Type</a>
                                        <a href="orgOfficeTypeCont.do" class="dropdown-item">Org. Office Type</a>
                                        <a href="organisationCont.do" class="dropdown-item">Org. Office</a>
                                        <a href="designationCont.do" class="dropdown-item">Designation</a>
                                        <a href="personCount.do" class="dropdown-item">Org Person's Name</a>
                                        <a href="OrganisationDesignationNewController" class="dropdown-item">Designation Organisation</a>
                                        <a href="OrganisationTypeDesignationController" class="dropdown-item">Designation Organisation Type</a>
                                        <a href="formdata" class="dropdown-item">FormData</a>
                                        <a href="orgDetailEntryCont.do" class="dropdown-item">Org Detail Entry</a>
                                        <a href="allinoneCont.do" class="dropdown-item">All in One</a>
                                        <a href="keypersonnewCont.do" class="dropdown-item">KeyPerson New</a>
                                    </div>
                                </li>
                                <li class="nav-item dropdown">
                                    <a class="nav-link dropdown-toggle" href="#" id="navbardrop" data-toggle="dropdown">
                                        Quarantine Mgnt
                                    </a>
                                    <div class="dropdown-menu">
                                        <a href="orgNameCont.do" class="dropdown-item">Org. Name</a>
                                        <a href="orgTypeCont.do" class="dropdown-item">Org. Type</a>
                                        <a href="orgOfficeTypeCont.do" class="dropdown-item">Org. Office Type</a>
                                        <a href="organisationCont.do" class="dropdown-item">Org. Office</a>
                                        <a href="designationCont.do" class="dropdown-item">Designation</a>
                                        <a href="personCount.do" class="dropdown-item">Org Person's Name</a>
                                        <a href="OrganisationDesignationNewController" class="dropdown-item">Designation Organisation</a>
                                        <a href="OrganisationTypeDesignationController" class="dropdown-item">Designation Organisation Type</a>
                                        <a href="formdata" class="dropdown-item">FormData</a>
                                        <a href="orgDetailEntryCont.do" class="dropdown-item">Org Detail Entry</a>
                                        <a href="allinoneCont.do" class="dropdown-item">All in One</a>
                                        <a href="keypersonnewCont.do" class="dropdown-item">KeyPerson New</a>
                                    </div>
                                </li>
                                <li class="nav-item dropdown">
                                    <a class="nav-link dropdown-toggle" href="#" id="navbardrop" data-toggle="dropdown">
                                        Document
                                    </a>
                                    <div class="dropdown-menu">
                                        <a href="orgNameCont.do" class="dropdown-item">Org. Name</a>
                                        <a href="orgTypeCont.do" class="dropdown-item">Org. Type</a>
                                        <a href="orgOfficeTypeCont.do" class="dropdown-item">Org. Office Type</a>
                                        <a href="organisationCont.do" class="dropdown-item">Org. Office</a>
                                        <a href="designationCont.do" class="dropdown-item">Designation</a>
                                        <a href="personCount.do" class="dropdown-item">Org Person's Name</a>
                                        <a href="OrganisationDesignationNewController" class="dropdown-item">Designation Organisation</a>
                                        <a href="OrganisationTypeDesignationController" class="dropdown-item">Designation Organisation Type</a>
                                        <a href="formdata" class="dropdown-item">FormData</a>
                                        <a href="orgDetailEntryCont.do" class="dropdown-item">Org Detail Entry</a>
                                        <a href="allinoneCont.do" class="dropdown-item">All in One</a>
                                        <a href="keypersonnewCont.do" class="dropdown-item">KeyPerson New</a>
                                    </div>
                                </li>
                               <li class="nav-item dropdown">
                                    <a class="nav-link dropdown-toggle" href="#" id="navbardrop" data-toggle="dropdown">
                                        Medical Survey
                                    </a>
                                    <div class="dropdown-menu">
                                        <a href="orgNameCont.do" class="dropdown-item">Org. Name</a>
                                        <a href="orgTypeCont.do" class="dropdown-item">Org. Type</a>
                                        <a href="orgOfficeTypeCont.do" class="dropdown-item">Org. Office Type</a>
                                        <a href="organisationCont.do" class="dropdown-item">Org. Office</a>
                                        <a href="designationCont.do" class="dropdown-item">Designation</a>
                                        <a href="personCount.do" class="dropdown-item">Org Person's Name</a>
                                        <a href="OrganisationDesignationNewController" class="dropdown-item">Designation Organisation</a>
                                        <a href="OrganisationTypeDesignationController" class="dropdown-item">Designation Organisation Type</a>
                                        <a href="formdata" class="dropdown-item">FormData</a>
                                        <a href="orgDetailEntryCont.do" class="dropdown-item">Org Detail Entry</a>
                                        <a href="allinoneCont.do" class="dropdown-item">All in One</a>
                                        <a href="keypersonnewCont.do" class="dropdown-item">KeyPerson New</a>
                                    </div>
                                </li>
                            </ul>

                            <div>
                                
                                <a href="#"> 
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