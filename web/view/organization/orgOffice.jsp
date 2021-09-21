<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@include file="../layout/header.jsp" %>

<style>
    .selected_row {
        font-weight: bolder;
        color: blue;
        border: 3px solid black;
    }
    table.dataTable {      
        border-collapse: collapse;
    }
</style>

<script>
    $(function () {
        setTimeout(function () {
            $('#message').fadeOut('fast');
        }, 10000);

        $("#office_type").autocomplete({

            source: function (request, response) {
                var random = document.getElementById("office_type").value;
                $.ajax({
                    url: "OrgOfficeController",
                    dataType: "json",
                    data: {action1: "getOrgOfficeType", str: random},
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
                $('#office_type').val(ui.item.label); // display the selected text
                return false;
            }
        });
        $("#city_name").autocomplete({

            source: function (request, response) {
                var random = document.getElementById("city_name").value;
                $.ajax({
                    url: "OrgOfficeController",
                    dataType: "json",
                    data: {action1: "getCityName", str: random},
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
                $('#city_name').val(ui.item.label); // display the selected text
                return false;
            }
        });
        $(function () {
            $("#organisation_name").autocomplete({

                source: function (request, response) {
                    var random = document.getElementById("organisation_name").value;
                    $.ajax({
                        url: "OrgOfficeController",
                        dataType: "json",
                        data: {action1: "getOrgTypeName", str: random},
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
                    $('#organisation_name').val(ui.item.label); // display the selected text
                    return false;
                }
            });
            $("#searchmobile").autocomplete({

                source: function (request, response) {
                    var random = document.getElementById("searchmobile").value;
                    $.ajax({
                        url: "OrgOfficeController",
                        dataType: "json",
                        data: {action1: "getMobile", str: random},
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
                    $('#searchmobile').val(ui.item.label); // display the selected text
                    return false;
                }
            });
            $("#searchgeneration").autocomplete({

                source: function (request, response) {
                    var random = document.getElementById("searchgeneration").value;
                    $.ajax({
                        url: "OrgOfficeController",
                        dataType: "json",
                        data: {action1: "getsearchgeneration", str: random},
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
                    $('#searchgeneration').val(ui.item.label); // display the selected text
                    return false;
                }
            });
            $("#office_code_search").autocomplete({

                source: function (request, response) {
                    var org_name = document.getElementById("org_name").value;
                    var random = document.getElementById("office_code_search").value;
                    $.ajax({
                        url: "OrgOfficeController",
                        dataType: "json",
                        data: {action1: "getOfficeCodeName",
                            action2: org_name, str: random},
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
                    $('#office_code_search').val(ui.item.label); // display the selected text
                    return false;
                }
            });
            $("#office_name_search").autocomplete({

                source: function (request, response) {
                    var org_name = document.getElementById("org_name").value;
                    var office_code = document.getElementById("office_code_search").value;
                    var random = document.getElementById("office_name_search").value;
                    $.ajax({
                        url: "OrgOfficeController",
                        dataType: "json",
                        data: {action1: "getOfficeName",
                            action2: org_name,
                            action3: office_code, str: random},
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
                    $('#office_name_search').val(ui.item.label); // display the selected text
                    return false;
                }
            });
            $("#org_name").autocomplete({

                source: function (request, response) {
                    var random = document.getElementById("org_name").value;
                    $.ajax({
                        url: "OrgOfficeController",
                        dataType: "json",
                        data: {action1: "searchOrgTypeName", str: random},
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
                    $('#org_name').val(ui.item.label); // display the selected text
                    return false;
                }
            });
            $("#serialnumber").autocomplete({

                source: function (request, response) {

                    var code = document.getElementById("organisation_name").value;
                    var random = document.getElementById("serialnumber").value;
                    var generation = document.getElementById("generation").value;
                    var designation = document.getElementById("org_office_name").value;
                    $.ajax({
                        url: "OrgOfficeController",
                        dataType: "json",
                        data: {action1: "getParentOrgOffice",
                            action2: code, str: random,
                            action3: editable,
                            action4: generation,
                            action5: designation
                        },
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
                    $('#serialnumber').val(ui.item.label); // display the selected text
                    return false;
                }
            });
        });
        $("#searchhierarchy").autocomplete({

            source: function (request, response) {
                var org_name = document.getElementById("org_name").value;
                var office_code = document.getElementById("office_code_search").value;
                var random = document.getElementById("office_name_search").value;
                $.ajax({
                    url: "OrgOfficeController",
                    dataType: "json",
                    data: {action1: "getOfficeName",
                        action2: org_name,
                        action3: office_code, str: random},
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
                $('#searchhierarchy').val(ui.item.label); // display the selected text
                return false;
            }
        });
    });
    function fillColumn(id, count) {
        $('#org_office_id').val(id);
        $('#org_office_code').val($("#" + count + '2').html());
        $('#organisation_name').val($("#" + count + '3').html());
        $('#org_office_name').val($("#" + count + '4').html());
        $('#office_type').val($("#" + count + '5').html());
        $('#address_line1').val($("#" + count + '6').html());
        $('#address_line2').val($("#" + count + '7').html());
        $('#address_line3').val($("#" + count + '8').html());
        $('#city_name').val($("#" + count + '9').html());
        $('#email_id1').val($("#" + count + '10').html());
        $('#email_id2').val($("#" + count + '11').html());
        $('#mobile_no1').val($("#" + count + '12').html());
        $('#mobile_no2').val($("#" + count + '13').html());
        $('#landline_no1').val($("#" + count + '14').html());
        $('#landline_no2').val($("#" + count + '15').html());
        $('#landline_no3').val($("#" + count + '16').html());
        $('#latitude').val($("#" + count + '17').html());
        $('#longitude').val($("#" + count + '18').html());
        $('#serialnumber').val($("#" + count + '19').html());
        var super_child = $("#" + count + '20').html();
        if (super_child == 'Y') {
            $('#supery').attr('checked', true);
        } else {
            $('#supern').attr('checked', true);
        }

        $('#generation').val($("#" + count + '21').html());

        $('#edit').attr('disabled', false);
        $('#delete').attr('disabled', false);
    }





    var editable = false;
    function makeEditable(id) {
        document.getElementById("organisation_name").disabled = false;
        document.getElementById("org_office_name").disabled = false;
        document.getElementById("office_type").disabled = false;
        document.getElementById("org_office_code").disabled = false;
        document.getElementById("address_line1").disabled = false;
        document.getElementById("address_line2").disabled = false;
        document.getElementById("address_line3").disabled = false;
        // document.getElementById("state_name").disabled = false;
        document.getElementById("city_name").disabled = false;
        document.getElementById("email_id1").disabled = false;
        document.getElementById("email_id2").disabled = false;
        document.getElementById("mobile_no1").disabled = false;
        document.getElementById("mobile_no2").disabled = false;
        document.getElementById("landline_no1").disabled = false;
        document.getElementById("landline_no2").disabled = false;
        document.getElementById("landline_no3").disabled = false;
        document.getElementById("latitude").disabled = false;
        document.getElementById("longitude").disabled = false;
        document.getElementById("save").disabled = false;
        if (id == 'new') {
            editable = "false";
            // document.getElementById("message").innerHTML = "";      // Remove message
            $("#message").html("");
            document.getElementById("org_office_id").value = "";
            document.getElementById("serialnumber").disabled = false;
            document.getElementById("supern").disabled = false;
            document.getElementById("supery").disabled = false;
            document.getElementById("edit").disabled = true;
            document.getElementById("delete").disabled = true;
            // document.getElementById("save_As").disabled = true;
            document.getElementById("get_cordinate").disabled = false;
            setDefaultColordOrgn(document.getElementById("noOfRowsTraversed").value, 17);
            document.getElementById("organisation_name").focus();
        }
        if (id == 'edit') {
            editable = "true";
            // document.getElementById("save_As").disabled = false;
            document.getElementById("delete").disabled = false;
            document.getElementById("serialnumber").disabled = false;
            document.getElementById("supern").disabled = false;
            document.getElementById("supery").disabled = false;
            document.getElementById("get_cordinate").disabled = false;
        }

    }

    function setStatus(id) {
        if (id == 'save') {
            document.getElementById("clickedButton").value = "Save";
        } else if (id == 'save_As') {
            document.getElementById("clickedButton").value = "Save AS New";
        } else if (id == 'search_org') {
            var org_name = document.getElementById("org_name").value;
            document.getElementById("org_name1").value = org_name;
            document.getElementById("org_name2").value = org_name;
            document.getElementById("clickedButton").value = "SEARCH";
        } else if (id == 'clear_org') {
            document.getElementById("clickedButton").value = ' ';
            // document.getElementById("").innerHTML = "   ";
            $("#org_msg").html("");
            document.getElementById("org_name").value = " ";
            document.getElementById("office_code_search").value = " ";
            document.getElementById("office_name_search").value = " ";
            document.getElementById("org_name1").value = " ";
            document.getElementById("org_name2").value = " ";
        } else
            document.getElementById("clickedButton").value = "Delete";
    }
    function myLeftTrim(str) {
        var beginIndex = 0;
        for (var i = 0; i < str.length; i++) {
            if (str.charAt(i) == ' ')
                beginIndex++;
            else
                break;
        }
        return str.substring(beginIndex, str.length);
    }
    function verify() {
        var result;
        if (document.getElementById("clickedButton").value == 'Save' || document.getElementById("clickedButton").value == 'Save AS New') {
            var organisation_name = document.getElementById("organisation_name").value;
            var org_office_name = document.getElementById("org_office_name").value;
            var office_type = document.getElementById("office_type").value;
            var office_code = document.getElementById("org_office_code").value;
            var address_line1 = document.getElementById("address_line1").value;
            var city_name = document.getElementById("city_name").value;
            var email_id1 = document.getElementById("email_id1").value;
            var email_id2 = document.getElementById("email_id2").value;
            var landline_no1 = document.getElementById("landline_no1").value;
            if (myLeftTrim(organisation_name).length == 0) {
                $("#message").html("<td colspan='8' bgcolor='coral'><b>Organisation Name is required...</b></td>");
                document.getElementById("organisation_name").focus();
                return false;
            }
            if (myLeftTrim(office_code).length == 0) {
                $("#message").html("<td colspan='8' bgcolor='coral'><b>Office Code is required...</b></td>");
                document.getElementById("office_code").focus();
                return false;
            }
            if (myLeftTrim(org_office_name).length == 0) {
                $("#message").html("<td colspan='8' bgcolor='coral'><b>Organisation Office Name is required...</b></td>");
                document.getElementById("org_office_name").focus();
                return false;
            }
            if (myLeftTrim(office_type).length == 0) {
                $("#message").html("<td colspan='8' bgcolor='coral'><b>Org Office Type is required...</b></td>");
                document.getElementById("office_type").focus();
                return false;
            }

            if (myLeftTrim(city_name).length == 0) {
                $("#message").html("<td colspan='8' bgcolor='coral'><b>City Name is required...</b></td>");
                document.getElementById("city_name").focus();
                return false;
            }
            if (myLeftTrim(address_line1).length == 0) {
                $("#message").html("<td colspan='8' bgcolor='coral'><b>Address Line 1 is required...</b></td>");
                document.getElementById("address_line1").focus();
                return false;
            }
            var reg = /^([A-Za-z0-9_\-\.])+\@([A-Za-z0-9_\-\.])+\.([A-Za-z]{2,4})$/;
            if (reg.test(email_id1) == false) {
                $("#message").html("<td colspan='8' bgcolor='coral'><b>Please Enter Correct 1st Email ID...</b></td>");
                document.getElementById("email_id1").focus();
                return false; // code to stop from submitting the form2.
            }
            if (myLeftTrim(email_id2).length > 0) {
                if (reg.test(email_id2) == false) {
                    $("#message").html("<td colspan='8' bgcolor='coral'><b>Please Enter Correct 2nd Email ID...</b></td>");
                    document.getElementById("email_id2").focus();
                    return false; // code to stop from submitting the form2.
                }
            }
            if (myLeftTrim(landline_no1).length == 0) {
                $("#message").html("<td colspan='8' bgcolor='coral'><b>Landline No 1 is required...</b></td>");
                document.getElementById("landline_no1").focus();
                return false; // code to stop from submitting the form2.
            }
            if (result == false)
            {// if result has value false do nothing, so result will remain contain value false.
            } else {
                result = true;
            }
            if (document.getElementById("clickedButton").value == 'Save AS New') {
                result = confirm("Are you sure you want to save it as New record?")
                return result;
            }
        } else
            result = confirm("Are you sure you wantto delete this record?")
        return result;
    }


    function verifySearch() {
        var result;
        if (document.getElementById("clickedButton").value == 'SEARCH') {
            var org_name = document.getElementById("org_name").value;
            if (myLeftTrim(org_name).length == 0) {
                //document.getElementById("org_msg").innerHTML = "<b>Organization Name is required...</b>";
                $("#org_msg").html("<b>Organization Name is required...</b>");
                document.getElementById("org_name").focus();
                return false; // code to stop from submitting the form2.
            }
        }
    }



    function openPopUp(url, window_name, popup_height, popup_width) {
        var popup_top_pos = (screen.availHeight / 2) - (popup_height / 2);
        var popup_left_pos = (screen.availWidth / 2) - (popup_width / 2);
        var window_features = "left=" + popup_left_pos + ", top=" + popup_top_pos + ", width=" + popup_width + ", height=" + popup_height + ", resizable=no, scrollbars=yes, status=no, dialog=yes, dependent=yes";
        return window.open(url, window_name, window_features);
    }
    if (!document.all) {
        document.captureEvents(Event.CLICK);
    }
    document.onclick = function () {
        if (popupwin != null && !popupwin.closed) {
            popupwin.focus();
        }
    }

    function printAddress(id) {
        var queryString = "requester=PRINTAddress&org_office_id=" + id;
        var url = "organisationCont.do?" + queryString;
        popupwin = openPopUp(url, "Organisation", 600, 900);
    }
    function codeIsEmpty() {

        var office_code = document.getElementById("office_code").value;
        if (myLeftTrim(office_code).length == 0) {
            // document.getElementById("message").innerHTML = "<td colspan='8' bgcolor='coral'><b>Organisation Name is required...</b></td>";
            $("#message").html("<td colspan='8' bgcolor='coral'><b>Office Code is required...</b></td>");
            document.getElementById("office_code").focus();
        } else {
            $("#message").html("");
        }
    }


    function openMapForCord() {
        var url = "GeneralController?task=GetCordinates4"; //"getCordinate";
        popupwin = openPopUp(url, "", 600, 630);
    }



    function openPopUp(url, window_name, popup_height, popup_width) {
        var popup_top_pos = (screen.availHeight / 2) - (popup_height / 2);
        var popup_left_pos = (screen.availWidth / 2) - (popup_width / 2);
        var window_features = "left=" + popup_left_pos + ", top=" + popup_top_pos + ", width=" + popup_width + ", height=" + popup_height + ", resizable=yes, scrollbars=yes, location=0, menubar=no, status=no, dependent=yes";
        return window.open(url, window_name, window_features);
    }


    function openMap(id) {
        //alert(vehicle_key_person_map_id);
        var url = "OrgOfficeController?task=showMapWindow&org_office_id=" + id;
        popupwin = openPopUp(url, "", 580, 620);
    }


    function myFun(value)
    {
        debugger;
        var req = null;
        if (req != null)
            req.abort();
        var random = document.getElementById("mobile_no1").value;


        if (random.length >= 10)
        {

            req = $.ajax({
                type: "POST",
                url: "OrgOfficeController",
                data: {action1: "mobile_number",
                    str: random},
                dataType: "json",
                success: function (response_data) {

                    console.log(response_data);
                    alert(response_data.list[0]);
                    document.getElementById("mobile_no1").value = response_data.list[0];
                    //  response(response_data.list);
                }, error: function (error) {
                    console.log(error.responseText);
                    document.getElementById("mobile_no1").value = "";
                    response(error.responseText);
                }


            });
        }

    }

    function setDefaultValues(id) {
        var result_type = document.getElementById(id).checked;
        var default_mobile_no = "9999999999";
        var default_email_id = "abc@xyz.com";
        var default_address = "ABC";
        var default_address1 = "XYZ";
        var default_address2 = "UVW";
        var email_id1 = "xyz@abc.com";
        var landline_no1 = "000000000";
        var landline_no2 = "00000000";
        var landline_no3 = "000000000";
        //document.getElementById("supern").checked="N";
        var city = "jabalpur";
        var latitude = "0.0";
        var longitude = "0.0";
        if (result_type) {
            $("#mobile_no2").val(default_mobile_no);
            $("#email_id1").val(default_email_id);
            $("#address_line1").val(default_address);
            $("#latitude").val(latitude);
            $("#longitude").val(longitude);
            $("#address_line2").val(default_address1);
            $("#address_line3").val(default_address2);
            $("#email_id2").val(email_id1);
            $("#landline_no1").val(landline_no1);
            $("#landline_no2").val(landline_no2);
            $("#landline_no3").val(landline_no3);
            $("#city_name").val(city);
            document.getElementById("supern").checked = "N";
        } else {
            $("#email_id1").val("");
            $("#address_line1").val(" ");
            $("#latitude").val("");
            $("#longitude").val("");
            $("#address_line2").val("");
            $("#address_line3").val("");
            $("#email_id2").val("");
            $("#landline_no1").val("");
            $("#landline_no2").val("");
            $("#landline_no3").val("");
            $("#supern").val("");
            $("#city_name").val("");
        }

    }
    $(document).ready(function () {
        $('#mytable tbody').on(
                'click',
                'tr',
                function () {

                    if ($(this).hasClass('selected_row')) {
                        $(this).removeClass('selected_row');
                    } else {
                        $("#mytable").DataTable().$(
                                'tr.selected_row').removeClass(
                                'selected_row');
                        $(this).addClass('selected_row');
                    }
                });
    });


</script>




<section>
    <div class="container-fluid page_heading sectionPadding35">
        <h1>Organization Office</h1>
    </div>
</section>



<section class="marginTop30">
    <div class="container organizationBox">
        <div class="headBox">
            <h5 class="">Search Engine</h5>
        </div>
        <form name="form1" method="POST" action="OrgOfficeController" onsubmit="returnverifySearch();" >
            <div class="row mt-3">
                <div class="col-md-4 mb-md-2">
                    <div class="form-groupmb-md-0">
                        <label>Organization Name</label>
                        <input type="text" class="form-control myInput" id="org_name" name="org_name" value="${org_name}" size="20">
                    </div>
                </div>
                <div class="col-md-4 mb-md-2">
                    <div class="form-group mb-md-0">
                        <label>Office Code</label>
                        <input type="text"  class="form-control myInput" id="office_code_search" name="office_code_search" 
                               value="${office_code_search}" size="20">
                    </div>
                </div>
                <div class="col-md-4 mb-md-2">
                    <div class="form-group mb-md-0">
                        <label>Office Name</label>
                        <input type="text"  class="form-control myInput" id="office_name_search" name="office_name_search" 
                               value="${office_name_search}" size="20">
                    </div>
                </div>
                <div class="col-md-4">
                    <div class="form-group mb-md-0">
                        <label>Mobile Number</label>
                        <input type="text"  class="form-control myInput" id="searchmobile" name="searchmobile" 
                               value="${searchmobile}" size="20">
                    </div>
                </div>
                <div class="col-md-4">
                    <div class="form-group mb-md-0">
                        <label>Generation</label>
                        <input type="text"  class="form-control myInput" id="searchgeneration" name="searchgeneration"
                               value="${searchgeneration}" size="20">
                    </div>
                </div>
                <div class="col-md-4">
                    <div class="form-group mb-md-0">
                        <label>Org Office hierarchy</label>
                        <input type="text"  class="form-control myInput" id="searchhierarchy" name="searchhierarchy" value="${searchhierarchy}" size="20">
                    </div>
                </div>

            </div>

            <hr>
            <div class="row">
                <div class="col-md-12 text-center">
                    <input type="submit" class="btn normalBtn" id="search_org" name="search_org" value="SEARCH RECORDS" onclick="setStatus(id)">
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
                <div class="table-responsive">
                    <table class="table table-striped table-bordered" id="mytable" style="width:100%">
                        <thead>
                            <tr>   
                                <th>S.No.</th>
                                <th style="overflow:inherit">Office Type Code</th>
                                <th>Organization_Name</th>
                                <th>Office_Name</th>
                                <th>Office_Type</th>
                                <th>Address_1</th>
                                <th style="display: none">Address_2</th>
                                <th style="display: none">Address_3</th>
                                <th>City Name</th>
                                <th>First Email ID</th>
                                <th style="display: none">Second Email ID</th>
                                <th>Mobile No1</th>
                                <th style="display: none">Mobile No2</th>
                                <th>Landline No1</th>
                                <th style="display: none">Landline No2</th>
                                <th style="display: none">Landline No3</th>
                                <th style="display: none">Latitude</th>
                                <th style="display: none">Longitude</th>
                                <th>Parent Org Office</th>
                                <th>Is Super Child</th>
                                <th>Generation</th>
                                <th></th>
                            </tr>
                        </thead>
                        <tbody>   
                            <c:forEach var="organisation" items="${requestScope['organisationList']}" varStatus="loopCounter">
                                <tr
                                    onclick="fillColumn('${organisation.org_office_id}', '${loopCounter.count }');">
                                    <td>${loopCounter.count }</td>                          
                                    <td id="${loopCounter.count }2">${organisation.org_office_code}</td>
                                    <td id="${loopCounter.count }3">${organisation.organisation_name}</td>
                                    <td id="${loopCounter.count }4">${organisation.org_office_name}</td>
                                    <td id="${loopCounter.count }5">${organisation.office_type}</td>
                                    <td id="${loopCounter.count }6">${organisation.address_line1}</td>
                                    <td id="${loopCounter.count }7" style="display: none">${organisation.address_line2}</td>
                                    <td id="${loopCounter.count }8" style="display: none">${organisation.address_line3}</td>
                                    <td id="${loopCounter.count }9">${organisation.city_name}</td>
                                    <%-- <td id="t1c${IDGenerator.uniqueID}" class="new_input" onclick="fillColumns(id)">${organisation.state_name}</td>--%>
                                    <td id="${loopCounter.count }10">${organisation.email_id1}</td>
                                    <td id="${loopCounter.count }11" style="display: none">${organisation.email_id2}</td>
                                    <td id="${loopCounter.count }12">${organisation.mobile_no1}</td>
                                    <td id="${loopCounter.count }13" style="display: none">${organisation.mobile_no2}</td>
                                    <td id="${loopCounter.count }14">${organisation.landline_no1}</td>
                                    <td id="${loopCounter.count }15" style="display: none">${organisation.landline_no2}</td>
                                    <td id="${loopCounter.count }16" style="display: none">${organisation.landline_no3}</td>
                                    <td id="${loopCounter.count }17" style="display: none">${organisation.latitude}</td>
                                    <td id="${loopCounter.count }18" style="display: none">${organisation.longitude}</td>
                                    <td id="${loopCounter.count }19">${organisation.p_org}</td>
                                    <td id="${loopCounter.count }20">${organisation.superp}</td>
                                    <td id="${loopCounter.count }21">${organisation.generation}</td>

                                    <td>
                                        <input type="button" class="btn normalBtn"  value ="View Map" id="map_container${loopCounter.count}" onclick="openMap('${organisation.org_office_id}');"/>
                                    </td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>

    </div>
</section>




<section class="marginTop30">
    <div class="container organizationBox">
        <div class="headBox">
            <h5 class="">Data Entry</h5>
        </div>
        <form name="form2" method="POST" action="OrgOfficeController" onsubmit="return verify()">
            <div class="row mt-3">
                <div class="col-md-3">
                    <div class="">
                        <div class="form-group">
                            <label>Organization Name<span class="text-danger">*</span></label>                            
                            <input type="hidden" class="form-control myInput" id="org_office_id" name="org_office_id" value="" >
                            <input class="form-control myInput" type="text" id="organisation_name" name="organisation_name" value="${after_save_organisation}" disabled>
                        </div>
                    </div>
                </div>
                <div class="col-md-3">
                    <div class="">
                        <div class="form-group">
                            <label>Office Type<span class="text-danger">*</span></label>                            
                            <input  type="text" class="form-control myInput" id="office_type" name="office_type" value="${after_save_office_type}" onclick ="codeIsEmpty()" disabled>
                        </div>
                    </div>
                </div>
                <div class="col-md-3">
                    <div class="">
                        <div class="form-group">
                            <label>Office Code<span class="text-danger">*</span></label>                            
                            <input class="form-control myInput" type="text" id="org_office_code" name="org_office_code" value="${after_office_code}" disabled>
                        </div>
                    </div>
                </div>
                <div class="col-md-3">
                    <div class="">
                        <div class="form-group">
                            <label>Office Name<span class="text-danger">*</span></label>                            
                            <input type="text" class="form-control myInput" id="org_office_name" name="org_office_name" value="" disabled>
                        </div>
                    </div>
                </div>
            </div>
            <div class="row mt-3">
                <div class="col-md-3">
                    <div class="">
                        <div class="form-group">
                            <label>Address Line1<span class="text-danger">*</span></label>                            
                            <input class="form-control myInput"  type="text" id="address_line1" name="address_line1" value="" disabled>
                        </div>
                    </div>
                </div>
                <div class="col-md-3">
                    <div class="">
                        <div class="form-group">
                            <label>Address Line2<span class="text-danger">*</span></label>                            
                            <input class="form-control myInput" type="text" id="address_line2" name="address_line2" value="" disabled>
                        </div>
                    </div>
                </div>
                <div class="col-md-3">
                    <div class="">
                        <div class="form-group">
                            <label>Address Line3<span class="text-danger">*</span></label>                            
                            <input class="form-control myInput" type="text" id="address_line3" name="address_line3" value="" disabled>
                        </div>
                    </div>
                </div>
                <div class="col-md-3">
                    <div class="">
                        <div class="form-group">
                            <label>City Name<span class="text-danger">*</span></label>                            
                            <input class="form-control myInput" type="text" id="city_name" name="city_name" value="" disabled>
                        </div>
                    </div>
                </div>
            </div>
            <div class="row mt-3">
                <div class="col-md-3">
                    <div class="">
                        <div class="form-group">
                            <label>First EmailID<span class="text-danger">*</span></label>                            
                            <input class="form-control myInput" type="text" id="email_id1" name="email_id1" value="" disabled>
                        </div>
                    </div>
                </div>
                <div class="col-md-3">
                    <div class="">
                        <div class="form-group">
                            <label>Second EmailID<span class="text-danger">*</span></label>                            
                            <input class="form-control myInput" type="text" id="email_id2" name="email_id2" value="" disabled>
                        </div>
                    </div>
                </div>
                <div class="col-md-3">
                    <div class="">
                        <div class="form-group">
                            <label>Mobile No1<span class="text-danger">*</span></label>                            
                            <input class="form-control myInput" type="text" id="mobile_no1" name="mobile_no1" value="" onkeyup="myFun(id)" maxlength="10" disabled>
                        </div>
                    </div>
                </div>


                <div class="col-md-3">
                    <div class="">
                        <div class="form-group">
                            <label>Mobile No2<span class="text-danger">*</span></label>                            
                            <input class="form-control myInput" type="text" id="mobile_no2" name="mobile_no2" value="" maxlength="10" disabled>
                        </div>
                    </div>
                </div>
            </div>
            <div class="row mt-3">
                <div class="col-md-3">
                    <div class="">
                        <div class="form-group">
                            <label>Landline No1<span class="text-danger">*</span></label>                            
                            <input class="form-control myInput" type="text" id="landline_no1" name="landline_no1" value="" disabled>
                        </div>
                    </div>
                </div>
                <div class="col-md-3">
                    <div class="">
                        <div class="form-group">
                            <label>Landline No2<span class="text-danger">*</span></label>                            
                            <input class="form-control myInput" type="text" id="landline_no2" name="landline_no2" value="" disabled>
                        </div>
                    </div>
                </div>
                <div class="col-md-3">
                    <div class="">
                        <div class="form-group">
                            <label>Landline No3<span class="text-danger">*</span></label>                            
                            <input class="form-control myInput" type="text" id="landline_no3" name="landline_no3"  value="" disabled>
                        </div>
                    </div>
                </div>
            </div>

            <div class="row mt-3">
                <div class="col-md-3">
                    <div class="">
                        <div class="form-group">
                            <label>Latitude<span class="text-danger">*</span></label>                            
                            <input class="form-control myInput" type="text" id="latitude" name="latitude" value="" size="20" disabled>
                        </div>
                    </div>
                </div>
                <div class="col-md-3">
                    <div class="">
                        <div class="form-group">
                            <label>Longitude<span class="text-danger">*</span></label>   
                            <div class="d-flex">
                                <input class="form-control myInput rounded-0" type="text" id="longitude" name="longitude" value="" size="20" disabled>
                                <input class="btn normalBtn rounded-0 px-2" type="button" id="get_cordinate" value="Get Cordinate" onclick="openMapForCord()" disabled>
                            </div>

                        </div>
                    </div>
                </div>


                <div class="col-md-3">
                    <div class="">
                        <div class="form-group">
                            <label>Parent Org Office<span class="text-danger">*</span></label>                            
                            <input class="form-control myInput" type="text" id="serialnumber" name="serialnumber"  value="${serialnumber}" disabled>
                        </div>
                    </div>
                </div>
                <div class="col-md-3">
                    <div class="">
                        <div class="form-group mb-1">
                            <label class="" for="email">Is super child<span class="text-danger">*</span></label>
                        </div>
                        <div class="form-group form-check mb-0 d-inline mr-2 pl-0">
                            <label class="form-check-label">
                                <input type="radio" id="supery" name="super" value="Y" disabled> Yes
                            </label>
                        </div>
                        <div class="form-group form-check d-inline pl-0">
                            <label class="form-check-label">
                                <input type="radio" id="supern" name="super" value="N" disabled> No
                            </label>
                        </div>
                    </div>
                </div>
            </div>
            <div class="col-md-3">
                <div class="">
                    <div class="form-group">
                        <!--<label>Generation<span class="text-danger">*</span></label>-->
                        <input class="form-control myInput" type="text" id="generation" name="generation" value="" size="45" disabled hidden>
                    </div>
                </div>
            </div>
            <hr>
            <div class="row">
                <div id="message">
                    <c:if test="${not empty message}">
                        <div class="col-md-12 text-center">
                            <label style="color:${msgBgColor}"><b>Result: ${message}</b></label>
                        </div>
                    </c:if>
                </div>
                <div class="col-md-12 text-center"> 
                    <input type="button" class="btn normalBtn" name="edit" id="edit" value="Edit" onclick="makeEditable(id)" disabled>
                    <input type="submit" class="btn normalBtn" name="task" id="save" value="Save" onclick="setStatus(id)" disabled>
                    <input type="reset" class=" btn normalBtn" name="new" id="new" value="New" onclick="makeEditable(id)" >
                    <input type="submit" class="btn normalBtn" name="task" id="delete" value="Delete" onclick="setStatus(id)" disabled>
                </div>
            </div>

        </form>
    </div>
</section>



<%@include file="../layout/footer.jsp" %>

