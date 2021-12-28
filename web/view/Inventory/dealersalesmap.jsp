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
                    url: "DealerSalemanMapController",
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
                    url: "DealerSalemanMapController",
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
            $("#salesmanname").autocomplete({

                source: function (request, response) {
                    var random = document.getElementById("salesmanname").value;
                    $.ajax({
                        url: "DealerSalemanMapController",
                        dataType: "json",
                        data: {action1: "getSalesDealer", str: random},
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
                    $('#salesmanname').val(ui.item.label); // display the selected text
                    return false;
                }
            });
            $("#dealername").autocomplete({

                source: function (request, response) {
                    var random = document.getElementById("dealername").value;
                    $.ajax({
                        url: "DealerSalemanMapController",
                        dataType: "json",
                        data: {action1: "getDealer", str: random},
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
                    $('#dealername').val(ui.item.label); // display the selected text
                    return false;
                }
            });


            $("#sales_search").autocomplete({

                source: function (request, response) {
                    var random = document.getElementById("sales_search").value;
                    $.ajax({
                        url: "DealerSalemanMapController",
                        dataType: "json",
                        data: {action1: "getSalesDealer", str: random},
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
                    $('#sales_search').val(ui.item.label); // display the selected text
                    return false;
                }
            });
            $("#searchdealername").autocomplete({

                source: function (request, response) {
                    var random = document.getElementById("searchdealername").value;

                    $.ajax({
                        url: "DealerSalemanMapController",
                        dataType: "json",
                        data: {action1: "getDealer", str: random},
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
                    $('#searchdealername').val(ui.item.label); // display the selected text
                    return false;
                }
            });
            $("#searchmobile").autocomplete({

                source: function (request, response) {
                    var random = document.getElementById("searchmobile").value;
                    $.ajax({
                        url: "DealerSalemanMapController",
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
                        url: "DealerSalemanMapController",
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
                        url: "DealerSalemanMapController",
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
                        url: "DealerSalemanMapController",
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
                        url: "DealerSalemanMapController",
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
                        url: "DealerSalemanMapController",
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
//        $("#searchhierarchy").autocomplete({
//
//            source: function (request, response) {
//                var org_name = document.getElementById("org_name").value;
//                var office_code = document.getElementById("office_code_search").value;
//                var random = document.getElementById("office_name_search").value;
//                $.ajax({
//                    url: "DealerSalemanMapController",
//                    dataType: "json",
//                    data: {action1: "getOfficeName",
//                        action2: org_name,
//                        action3: office_code, str: random},
//                    success: function (data) {
//
//                        console.log(data);
//                        response(data.list);
//                    }, error: function (error) {
//                        console.log(error.responseText);
//                        response(error.responseText);
//                    }
//                });
//            },
//            select: function (events, ui) {
//                console.log(ui);
//                $('#searchhierarchy').val(ui.item.label); // display the selected text
//                return false;
//            }
//        });

        $("#serialnumber").blur(function () {
            var p_org_office = $('#serialnumber').val();

            if (p_org_office == "") {
                $('#gst_div').show();
            } else {
                $('#gst_div').hide();
            }
        });
    });
    function fillColumn(id, count) {
        $('#org_office_id').val(id);
        $('#salesmanname').val($("#" + count + '2').html());
        $('#dealername').val($("#" + count + '3').html());
        $('#remark').val($("#" + count + '4').html());

        $('#edit').attr('disabled', false);
        $('#delete').attr('disabled', false);
    }





    var editable = false;
    function makeEditable(id) {
        document.getElementById("salesmanname").disabled = false;
        document.getElementById("dealername").disabled = false;

        document.getElementById("remark").disabled = false;
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
            // setDefaultColordOrgn(document.getElementById("noOfRowsTraversed").value, 17);
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
        //  alert("myLeftTrim"+str);
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
        //alert("verify");
        // $('#message').remove();
        if (document.getElementById("clickedButton").value === 'Save' || document.getElementById("clickedButton").value === 'Save AS New') {
            var organisation_name = document.getElementById("organisation_name").value;
            var office_type = document.getElementById("office_type").value;
            var org_office_code = document.getElementById("org_office_code").value;
            var org_office_name = document.getElementById("org_office_name").value;
            var address_line1 = document.getElementById("address_line1").value;
            var city_name = document.getElementById("city_name").value;
            var email_id1 = document.getElementById("email_id1").value;


            if (myLeftTrim(organisation_name).length === 0) {
                $("#message").html('<div class="col-md-12 text-center"><label style="color:red"><b>Org Name is required...</b></label></div>');
                document.getElementById("organisation_name").focus();
                return false;
            }
            if (myLeftTrim(office_type).length === 0) {
                // alert(office_type);
                $("#message").html('<div class="col-md-12 text-center"><label style="color:red"><b>Office Type  is required...</b></label></div>');
                document.getElementById("office_type").focus();
                return false;
            }

            if (myLeftTrim(org_office_code).length === 0) {
                $("#message").html('<div class="col-md-12 text-center"><label style="color:red"><b>Office Code  is required...</b></label></div>');
                document.getElementById("org_office_code").focus();
                return false;
            }

            if (myLeftTrim(org_office_name).length === 0) {
                $("#message").html('<div class="col-md-12 text-center"><label style="color:red"><b>Office Name  is required...</b></label></div>');
                document.getElementById("org_office_name").focus();
                return false;
            }
            if (myLeftTrim(address_line1).length === 0) {
                $("#message").html('<div class="col-md-12 text-center"><label style="color:red"><b>Address Line 1  is required...</b></label></div>');
                document.getElementById("address_line1").focus();
                return false;
            }

            if (myLeftTrim(city_name).length === 0) {
                $("#message").html('<div class="col-md-12 text-center"><label style="color:red"><b>City is required...</b></label></div>');
                document.getElementById("city_name").focus();
                return false;
            }
            if (myLeftTrim(email_id1).length === 0) {
                $("#message").html('<div class="col-md-12 text-center"><label style="color:red"><b>Email Id1 is required...</b></label></div>');
                document.getElementById("email_id1").focus();
                return false;
            }

            var reg = /^([A-Za-z0-9_\-\.])+\@([A-Za-z0-9_\-\.])+\.([A-Za-z]{2,4})$/;
            if (reg.test(email_id1) == false) {
                // alert(email_id1);
                $("#message").html('<div class="col-md-12 text-center"><label style="color:red"><b>Please Enter Correct Email Id...</b></label></div>');
                document.getElementById("email_id1").focus();
                return false;
            }

            if (result === false) {
            } else {
                result = true;
            }
            if (document.getElementById("clickedButton").value === 'Save AS New') {
                result = confirm("Are you sure you want to save it as New record?")
                return result;
            }
        } else {
            result = confirm("Are you sure you want to delete this record?");
        }
        return result;
    }




//    function verify() {
//        //alert("insert------");
//        var result;
//        if (document.getElementById("clickedButton").value == 'Save' || document.getElementById("clickedButton").value == 'Save AS New') {
//            var organisation_name = document.getElementById("organisation_name").value;
//            var org_office_name = document.getElementById("org_office_name").value;
//            var office_type = document.getElementById("office_type").value;
//            var office_code = document.getElementById("org_office_code").value;
//            var address_line1 = document.getElementById("address_line1").value;
//            var city_name = document.getElementById("city_name").value;
//            var email_id1 = document.getElementById("email_id1").value;
//            //  var email_id2 = document.getElementById("email_id2").value;
//            //  var landline_no1 = document.getElementById("landline_no1").value;
//
//            alert("address_line1---" + address_line1);
//
//            if (myLeftTrim(organisation_name).length == 0) {
//                $("#message").html('<div class="col-md-12 text-center"><label style="color:red"><b>Org Name is required...</b></label></div>');
//                document.getElementById("organisation_name").focus();
//                return false;
//            }
//            if (myLeftTrim(office_code).length == 0) {
//                $("#message").html('<div class="col-md-12 text-center"><label style="color:red"><b>Office Code is required...</b></label></div>');
//                document.getElementById("office_code").focus();
//                return false;
//            }
//            if (myLeftTrim(org_office_name).length == 0) {
//                $("#message").html('<div class="col-md-12 text-center"><label style="color:red"><b>Office Name is required...</b></label></div>');
//                document.getElementById("org_office_name").focus();
//                return false;
//            }
//            if (myLeftTrim(office_type).length == 0) {
//                $("#message").html('<div class="col-md-12 text-center"><label style="color:red"><b>Office Type is required...</b></label></div>');
//                document.getElementById("office_type").focus();
//                return false;
//            }
//
//            if (myLeftTrim(city_name).length == 0) {
//                $("#message").html('<div class="col-md-12 text-center"><label style="color:red"><b>City Name is required...</b></label></div>');
//                document.getElementById("city_name").focus();
//                return false;
//            }
//            if (myLeftTrim(address_line1).length == 0) {
//                alert(address_line1);
//                $("#message").html('<div class="col-md-12 text-center"><label style="color:red"><b>Address is required...</b></label></div>');
//                document.getElementById("address_line1").focus();
//                return false;
//            }
//
//            if (myLeftTrim(email_id1).length == 0) {
//                $("#message").html('<div class="col-md-12 text-center"><label style="color:red"><b>Email Id1 required...</b></label></div>');
//                document.getElementById("email_id1").focus();
//                return false;
//            }
//            var reg = /^([A-Za-z0-9_\-\.])+\@([A-Za-z0-9_\-\.])+\.([A-Za-z]{2,4})$/;
//            if (reg.test(email_id1) == false) {
//                alert(email_id1);
//                $("#message").html('<div class="col-md-12 text-center"><label style="color:red"><b>Please Enter Correct Email ID 1...</b></label></div>');
//                document.getElementById("email_id1").focus();
//                return false; // code to stop from submitting the form2.
//            }
//
//
//            if (result == false)
//            {// if result has value false do nothing, so result will remain contain value false.
//            } else {
//                result = true;
//            }
//            if (document.getElementById("clickedButton").value == 'Save AS New') {
//                result = confirm("Are you sure you want to save it as New record?")
//                return result;
//            }
//        } else
//            result = confirm("Are you sure you wantto delete this record?")
//        return result;
//    }


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
        var url = "DealerSalemanMapController?task=showMapWindow&org_office_id=" + id;
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
                url: "DealerSalemanMapController",
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
        <h1>Dealer SalesMan Mapping</h1>
    </div>
</section>



<section class="marginTop30">
    <div class="container organizationBox">
        <div class="headBox">
            <h5 class="">Search Engine</h5>
        </div>
        <form name="form1" method="POST" action="DealerSalemanMapController" onsubmit="return verifySearch();" >
            <div class="row mt-3">
                <div class="col-md-4 mb-md-2">
                    <div class="form-groupmb-md-0">
                        <label>Dealer Name</label>
                        <input type="text" class="form-control myInput" id="searchdealername" name="searchdealername" value="${searchdealername}" size="20">
                    </div>
                </div>
                <div class="col-md-4 mb-md-2">
                    <div class="form-group mb-md-0">
                        <label>SalesManName</label>
                        <input type="text"  class="form-control myInput" id="sales_search" name="sales_search" 
                               value="${sales_search}" size="20">
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
                <div class="table-responsive verticleScroll">
                    <table class="table table-striped table-bordered" id="mytable" style="width:100%" data-page-length='6'>
                        <thead>
                            <tr>   
                                <th>S.No.</th>


                                <th>Salesman Name</th>
                                <th>Dealer Name</th>
                                <th>Remark</th>


                            </tr>
                        </thead>
                        <tbody>   
                            <c:forEach var="organisation" items="${requestScope['organisationList']}" varStatus="loopCounter">
                                <tr
                                    onclick="fillColumn('${organisation.map_id}', '${loopCounter.count }');">
                                    <td>${loopCounter.count }</td>        
                                    <td id="${loopCounter.count }2">${organisation.salesmanname}</td>
                                    <td id="${loopCounter.count }3">${organisation.dealername}</td>

                                    <td id="${loopCounter.count }4">${organisation.remark}</td>


                                    <!--                                    <td>
                                                                            <input type="button" class="btn normalBtn"  value ="View Map" id="map_container${loopCounter.count}" onclick="openMap('${organisation.map_id}');"/>
                                                                        </td>-->
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
        <form name="form2" method="POST" action="DealerSalemanMapController" onsubmit="return verify()">
            <div class="row mt-3">
                <div class="col-md-9">
                    <div class="">
                        <div class="form-group">
                            <label>Select SalesMan  <span class="text-danger">*</span></label>                            
                            <input type="hidden" class="form-control myInput" id="org_office_id" name="org_office_id" value="" >
                            <input class="form-control myInput" type="text" id="salesmanname" name="salesmanname" value="${after_save_organisation}" disabled>
                        </div>
                    </div>
                </div>



            </div>
            <div class="row mt-3">
                <div class="col-md-9">
                    <div class="">
                        <div class="form-group">
                            <label>Select Dealer<span class="text-danger">*</span></label>                            
                            <input class="form-control myInput"  type="text" id="dealername" name="dealername" value="" disabled>
                        </div>
                    </div>
                </div>

            </div>


            <div class="row mt-3">
                <div class="col-md-9">
                    <div class="">
                        <div class="form-group">
                            <label>Remark<span class="text-danger">*</span></label>                            
                            <input class="form-control myInput"  type="text" id="remark" name="remark" value="" disabled>
                        </div>
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
        <input type="hidden" id="clickedButton" value="">
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

