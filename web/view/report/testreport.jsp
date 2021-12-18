<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@include file="../layout/header.jsp" %>

<script>
    $(document).ready(function () {
        $("#email").blur(function () {
            if (/(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|"(?:[\x01-\x08\x0b\x0c\x0e-\x1f\x21\x23-\x5b\x5d-\x7f]|\\[\x01-\x09\x0b\x0c\x0e-\x7f])*")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\x01-\x08\x0b\x0c\x0e-\x1f\x21-\x5a\x53-\x7f]|\\[\x01-\x09\x0b\x0c\x0e-\x7f])+)\])/.test($('#email').val()))
            {
                return true;
            }
            alert("Please Enter Valid Email Address!...");
            return (false)
        });
    });

//    function verify() {
//        if ((document.getElementById("email").value).trim().length == 0) {
//            $("#message").append('<div class="col-md-12 text-center"><label style="color:#a2a220"><b>Result: Please Enter Email Id!...</b></label></div>');
//            document.getElementById("email").focus();
//            return false;
//        }
//    }

    $(function () {
        $("#search_org_name").autocomplete({
            source: function (request, response) {
                var random = document.getElementById("search_org_name").value;
                $.ajax({
                    url: "TestController",
                    dataType: "json",
                    data: {action1: "getOrgName", str: random},
                    success: function (data) {
                        console.log(data);
                        response(data.list);
                    }, error: function (error) {
                        console.log(error.responseText);
                        response(error.responseText);
                    }
                });
            },
            select: function (events, ui) {
                console.log(ui);
                $('#search_org_name').val(ui.item.label); // display the selected text
                return false;
            }
        });

        $("#search_org_office_name").autocomplete({
            source: function (request, response) {
                var search_org_name = document.getElementById("search_org_name").value;
                var search_org_office_name = document.getElementById("search_org_office_name").value;
                $.ajax({
                    url: "TestController",
                    dataType: "json",
                    data: {action1: "getOrgOfficeName",
                        search_org_name: search_org_name, search_org_office_name: search_org_office_name},
                    success: function (data) {
                        console.log(data);
                        response(data.list);
                    }, error: function (error) {
                        console.log(error.responseText);
                        response(error.responseText);
                    }
                });
            },
            select: function (events, ui) {
                console.log(ui);
                $('#search_org_office_name').val(ui.item.label); // display the selected text
                return false;
            }
        });

        $("#search_org_office_type").autocomplete({
            source: function (request, response) {
                var search_org_name = document.getElementById("search_org_name").value;
                var search_org_office_name = document.getElementById("search_org_office_name").value;
                var search_org_office_type = document.getElementById("search_org_office_type").value;
                $.ajax({
                    url: "TestController",
                    dataType: "json",
                    data: {action1: "getOrgOfficeType",
                        search_org_name: search_org_name, search_org_office_name: search_org_office_name, search_org_office_type: search_org_office_type},
                    success: function (data) {
                        console.log(data);
                        response(data.list);
                    }, error: function (error) {
                        console.log(error.responseText);
                        response(error.responseText);
                    }
                });
            },
            select: function (events, ui) {
                console.log(ui);
                $('#search_org_office_type').val(ui.item.label); // display the selected text
                return false;
            }
        });

        $("#searchDesignation").autocomplete({
            source: function (request, response) {
                var search_org_name = document.getElementById("search_org_name").value;
                var search_org_office_name = document.getElementById("search_org_office_name").value;
                var search_org_office_type = document.getElementById("search_org_office_type").value;
                var searchDesignation = document.getElementById("searchDesignation").value;
                $.ajax({
                    url: "TestController",
                    dataType: "json",
                    data: {action1: "getDesignation",
                        search_org_name: search_org_name, search_org_office_name: search_org_office_name,
                        search_org_office_type: search_org_office_type, searchDesignation: searchDesignation},
                    success: function (data) {
                        console.log(data);
                        response(data.list);
                    }, error: function (error) {
                        console.log(error.responseText);
                        response(error.responseText);
                    }
                });
            },
            select: function (events, ui) {
                console.log(ui);
                $('#searchDesignation').val(ui.item.label); // display the selected text
                return false;
            }
        });

        $("#searchPerson").autocomplete({
            source: function (request, response) {
                var search_org_name = document.getElementById("search_org_name").value;
                var search_org_office_name = document.getElementById("search_org_office_name").value;
                var search_org_office_type = document.getElementById("search_org_office_type").value;
                var searchDesignation = document.getElementById("searchDesignation").value;
                var searchPerson = document.getElementById("searchPerson").value;
                $.ajax({
                    url: "TestController",
                    dataType: "json",
                    data: {action1: "getPerson",
                        search_org_name: search_org_name, search_org_office_name: search_org_office_name,
                        search_org_office_type: search_org_office_type, searchDesignation: searchDesignation, searchPerson: searchPerson},
                    success: function (data) {
                        console.log(data);
                        response(data.list);
                    }, error: function (error) {
                        console.log(error.responseText);
                        response(error.responseText);
                    }
                });
            },
            select: function (events, ui) {
                console.log(ui);
                $('#searchPerson').val(ui.item.label); // display the selected text
                return false;
            }
        });
    });


    function displayMapList() {
        var queryString;
        var search_org_name = document.getElementById("search_org_name").value;
        var search_org_office_name = document.getElementById("search_org_office_name").value;
        var search_org_office_type = document.getElementById("search_org_office_type").value;
        var searchDesignation = document.getElementById("searchDesignation").value;      

        queryString = "task=View Report&search_org_name=" + search_org_name + "&search_org_office_name=" + search_org_office_name + "&search_org_office_type=" + search_org_office_type + "&searchDesignation=" + searchDesignation ;
        //  queryString = "task=viewPdf";
        var url = "TestController?" + queryString;
        popupwin = openPopUp1(url, "Division List", 600, 1000);
    }

    function openPopUp1(url, window_name, popup_height, popup_width) {
        var popup_top_pos = (screen.availHeight / 2) - (popup_height / 2);
        var popup_left_pos = (screen.availWidth / 2) - (popup_width / 2);
        var window_features = "left=" + popup_left_pos + ", top=" + popup_top_pos + ", width=" + popup_width + ", height=" + popup_height + ", resizable=yes, scrollbars=yes, location=0, menubar=no, status=no, dependent=yes";

        return window.open(url, window_name, window_features);
    }
</script>


<section>
    <div class="container-fluid page_heading sectionPadding35">
        <h1>Dealer's Report</h1>
    </div>
</section>

<section class="marginTop30">
    <div class="container organizationBox">
        <div class="headBox">
            <h5 class="">Search Engine</h5>
        </div>
        <form name="form1" method="POST" action="TestController" onsubmit="return verifySearch();" >
            <div class="row mt-3">
                <div class="col-md-4">
                    <div class="form-group mb-md-0">
                        <input type="hidden" id="designation_id" name="designation_id" value="">
                        <label>Org. Name</label>
                        <input class="form-control myInput searchInput1 w-100" type="text" id="search_org_name" name="search_org_name" value="${org_name}" size="150" >
                    </div>
                </div>
                <div class="col-md-4">
                    <div class="form-group mb-md-0">
                        <label>Org. Office Name</label>
                        <input class="form-control myInput searchInput1 w-100" type="text" id="search_org_office_name" name="search_org_office_name"
                               value="${office_name}" size="150" >
                    </div>
                </div>
                <div class="col-md-4">
                    <div class="form-group mb-md-0">
                        <label>Org. Office Type</label>
                        <input class="form-control myInput searchInput1 w-100" type="text" id="search_org_office_type" name="search_org_office_type"
                               value="${search_org_office_type}" size="150" >
                    </div>
                </div>
            </div>
            <div class="row mt-3">
                <div class="col-md-4">
                    <div class="form-group mb-md-0">
                        <input type="hidden" id="designation_id" name="designation_id" value="">
                        <label>Designation</label>
                        <input class="form-control myInput searchInput1 w-100" type="text" id="searchDesignation" name="searchDesignation" 
                               value="${searchDesignation}" size="150" >
                    </div>
                </div>
                <div class="col-md-4">
                    <div class="form-group mb-md-0">
                        <label>Person Name</label>
                        <input class="form-control myInput searchInput1 w-100" type="text" id="searchPerson" name="searchPerson"
                               value="${searchPerson}" size="150" >
                    </div>
                </div>
            </div>
            <hr>
            <div class="row">
                <div class="col-md-12 text-center">
                    <input type="submit" class="btn normalBtn" id="searchRecords" name="searchRecords" value="SEARCH RECORDS" onclick="setStatus(id);">
                </div>
            </div>
            <hr>
            <div class="row mt-3">
                <div class="col-md-12">
                    <div class="form-group mb-md-0">
                        <input type="hidden" id="designation_id" name="designation_id" value="">
                        <label>Email</label>
                        <input class="form-control myInput searchInput1 w-100" type="text" id="email" name="email" value="" size="150"  >
                    </div>
                </div>
            </div>
            <hr>
            <div class="row">
                <div class="col-md-12 text-center">
                    <input type="button" class="btn normalBtn" id="genrateReport" name="genrateReport" value="View Report" onclick="displayMapList()">
                </div>
            </div>

            <div class="row">
                <div id="message">
                    <c:if test="${not empty message}">
                        <div class="col-md-12 text-center">
                            <label style="color:${msgBgColor}"><b>Result: ${message}</b></label>
                        </div>
                    </c:if>
                </div>
            </div>
        </form>
    </div>
</section>

<section class="marginTop30 ">
    <div class="container organizationBox">
        <div class="headBox">
            <h5 class="">Search List</h5>
        </div>
        <div class="row mt-3 myTable">
            <div class="col-md-12">
                <div class="table-responsive verticleScroll" >
                    <table class="table table-striped table-bordered" id="mytable" style="width:100%" data-page-length='6'>
                        <thead>
                            <tr>                                
                                <th>S.No.</th>
                                <th>Org. Name</th>
                                <th>Org. Code</th>
                                <th>Org. Office Name</th>
                                <th>Org. Office Type</th>
                                <th>Org. Office Code</th>
                                <th>Designation</th>
                                <th>Designation Code</th>
                                <th>Office Address</th>
                                <th>Office Email</th>
                                <th>Office Mobile</th>
                                <th>Person Name</th>
                                <th>Person Address Line 1</th>
                                <th>Person Address Line 2</th>
                                <th>Person Address Line 3</th>
                                <th>Person Mobile Number</th>
                                <th>Person Email</th>
                                <th>Person Code</th>
                                <th>Person Father's Name</th>
                                <th>Person DOB</th>
                                <th>Emergency Contact Name</th>
                                <th>Emergency Contact Number</th>
                            </tr>
                        </thead>
                        <tbody>                                
                            <c:forEach var="beanType" items="${requestScope['list']}" varStatus="loopCounter">
                                <tr
                                    onclick="fillColumn();">
                                    <td>${loopCounter.count }</td>
                                    <td>${beanType.organisation_name}</td>
                                    <td>${beanType.organisation_code}</td>
                                    <td>${beanType.org_office_name}</td>
                                    <td>${beanType.org_office_type}</td>
                                    <td>${beanType.org_office_code}</td>
                                    <td>${beanType.designation}</td>
                                    <td>${beanType.designation_code}</td>
                                    <td>${beanType.off_address_line1}</td>
                                    <td>${beanType.off_email_id1}</td>
                                    <td>${beanType.off_mobile_no1}</td>
                                    <td>${beanType.key_person_name}</td>
                                    <td>${beanType.kp_address_line1}</td>
                                    <td>${beanType.kp_address_line2}</td>
                                    <td>${beanType.kp_address_line3}</td>
                                    <td>${beanType.kp_mobile_no1}</td>
                                    <td>${beanType.kp_email_id1}</td>
                                    <td>${beanType.emp_code}</td>
                                    <td>${beanType.kp_father_name}</td>
                                    <td>${beanType.kp_date_of_birth}</td>
                                    <td>${beanType.emergency_contact_name}</td>
                                    <td>${beanType.emergency_contact_mobile}</td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>

    </div>
</section>
