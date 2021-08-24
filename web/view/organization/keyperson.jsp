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


        $("#org_office_name").autocomplete({

            source: function (request, response) {
                var officecode = document.getElementById("office_code").value;
                var random = document.getElementById("org_office_name").value;
                $.ajax({
                    url: "KeypersonController",
                    dataType: "json",
                    data: {action1: "getOrgOfficeName",
                        action2: officecode, str: random},
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
                $('#org_office_name').val(ui.item.label); // display the selected text
                return false;
            }
        });



        $("#searchOfficeCode").autocomplete({

            source: function (request, response) {
                var random = document.getElementById("searchOfficeCode").value;
                $.ajax({
                    url: "KeypersonController",
                    dataType: "json",
                    data: {action1: "searchOrgOfficeCode", str: random},
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
                $('#searchOfficeCode').val(ui.item.label); // display the selected text
                return false;
            }
        });


        $("#searchOrg").autocomplete({

            source: function (request, response) {
                var random = document.getElementById("searchOrg").value;
                $.ajax({
                    url: "KeypersonController",
                    dataType: "json",
                    data: {action1: "searchOrg", str: random},
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
                $('#searchOrg').val(ui.item.label); // display the selected text
                return false;
            }
        });



        $("#searchEmpCode").autocomplete({

            source: function (request, response) {
                var random = document.getElementById("searchEmpCode").value;
                $.ajax({
                    url: "KeypersonController",
                    dataType: "json",
                    data: {action1: "getOrgOfficeCode", str: random},
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
                $('#searchEmpCode').val(ui.item.label); // display the selected text
                return false;
            }
        });



        $("#office_code").autocomplete({

            source: function (request, response) {
                var random = document.getElementById("office_code").value;
                $.ajax({
                    url: "KeypersonController",
                    dataType: "json",
                    data: {action1: "getOrgOfficeCode", str: random},
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
                $('#office_code').val(ui.item.label); // display the selected text
                return false;
            }
        });

        $("#city_name").autocomplete({

            source: function (request, response) {
                var random = document.getElementById("city_name").value;
                $.ajax({
                    url: "KeypersonController",
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

        $("#designation").autocomplete({

            source: function (request, response) {
                var random = document.getElementById("designation").value;
                var org_office_name = document.getElementById("org_office_name").value;
                $.ajax({
                    url: "KeypersonController",
                    dataType: "json",
                    data: {action1: "getDesignation", str: random, action2: org_office_name},
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
                $('#designation').val(ui.item.label); // display the selected text
                return false;
            }
        });


        $("#searchmobile").autocomplete({

            source: function (request, response) {
                var random = document.getElementById("searchmobile").value;
                var code = document.getElementById("searchKeyPerson").value;
                $.ajax({
                    url: "KeypersonController",
                    dataType: "json",
                    data: {action1: "getMobile", str: random, action2: code},
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


        $("#searchDesignation").autocomplete({

            source: function (request, response) {
                var code = document.getElementById("searchOfficeCode").value;
                var key_person = document.getElementById("searchKeyPerson").value;
                var random = document.getElementById("searchDesignation").value;
                $.ajax({
                    url: "KeypersonController",
                    dataType: "json",
                    data: {action1: "getSearchDesignation",
                        action2: code,
                        action3: key_person,
                        str: random},
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


        $("#searchKeyPerson").autocomplete({

            source: function (request, response) {

                var code = document.getElementById("searchfamily").value;
                var random = document.getElementById("searchKeyPerson").value;
                $.ajax({
                    url: "KeypersonController",
                    dataType: "json",
                    data: {action1: "getSearchKeyPerson",
                        str: random,action2:code},
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
                $('#searchKeyPerson').val(ui.item.label); // display the selected text
                return false;
            }
        });



        $("#searchfamily").autocomplete({

            source: function (request, response) {

                //                var code = document.getElementById("searchOfficeCode").value;
                var random = document.getElementById("searchfamily").value;
                var code = document.getElementById("searchOrg").value;
                $.ajax({
                    url: "KeypersonController",
                    dataType: "json",
                    data: {action1: "getfamilycode",
                        str: random, action2: code},
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
                $('#searchfamily').val(ui.item.label); // display the selected text
                return false;
            }
        });

        //mobilevalidation

        //         $("#mobile_no1").keyup(function(){
        //                    
        //            source: function (request, response) {
        //           alert(1);
        //                 var random = document.getElementById("mobile_no1") .value;
        //                $.ajax({
        //                    url: "personCount.do",
        //                    dataType: "json",
        //                    data: {action1:"mobile_number",
        //                           str:random},
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
        //                $('#mobile_no1').val(ui.item.label); // display the selected text
        //                return false;
        //            }
        //        });

        //         $("#mobile_no1").keyup(function(){
        //             
        //               var random = document.getElementById("mobile_no1") .value;
        //    $("#mobile_no1").css("background-color", "pink");
        //  });

    });


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



    function makeEditable(id) {

        document.getElementById("save").disabled = false;
        document.getElementById("office_code").disabled = false;
        document.getElementById("org_office_name").disabled = false;
        document.getElementById("employeeId").disabled = false;
        document.getElementById("salutation").disabled = false;
        document.getElementById("key_person_name").disabled = false;
        document.getElementById("password").disabled = false;
        document.getElementById("father_name").disabled = false;
        document.getElementById("blood").disabled = false;
        document.getElementById("date_of_birth").disabled = false;
        document.getElementById("designation").disabled = false;
        document.getElementById("address_line1").disabled = false;
        document.getElementById("address_line2").disabled = false;
        document.getElementById("address_line3").disabled = false;
        document.getElementById("emergency_name").disabled = false;
        document.getElementById("emergency_number").disabled = false;
        document.getElementById("id_type").disabled = false;
        document.getElementById("id_no").disabled = false;
        document.getElementById("city_name").disabled = false;
        document.getElementById("mobile_no1").disabled = false;
        document.getElementById("mobile_no2").disabled = false;
        document.getElementById("landline_no1").disabled = false;
        document.getElementById("landline_no2").disabled = false;
        document.getElementById("email_id1").disabled = false;
        document.getElementById("email_id2").disabled = false;
        document.getElementById("latitude").disabled = false;
        document.getElementById("longitude").disabled = false;
        document.getElementById("design_name").disabled = false;
        document.getElementById("id_proof").disabled = false;
        document.getElementById("emerg").disabled = false;
        document.getElementById("delete").disabled = false;
        document.getElementById("genderm").disabled = false;
        document.getElementById("genderf").disabled = false;
        if (id == 'new') {

            $("#message").html("");
            document.getElementById("key_person_id").value = "";
            document.getElementById("edit").disabled = true;
            document.getElementById("delete").disabled = true;
            //  document.getElementById("save_As").disabled = true;
            document.getElementById("get_cordinate").disabled = false;

            document.getElementById("emerg").disabled = false;

            setDefaultColordOrgn(document.getElementById("noOfRowsTraversed").value, 25);
            //document.getElementById("office_type").focus();
        }
        if (id == 'edit') {
            document.getElementById("delete").disabled = false;
            // document.getElementById("save_As").disabled = false;
            document.getElementById("get_cordinate").disabled = false;
            document.getElementById("id_type").disabled = false;
            document.getElementById("id_no").disabled = false;
            document.getElementById("emerg").disabled = false;
            document.getElementById("genderf").disabled = false;
            document.getElementById("genderm").disabled = false;
            //                    document.getElementById("relation").focus();
        }

    }
    function setDefaultColordOrgn(noOfRowsTraversed, noOfColumns) {
        //alert(noOfColumns);
        for (var i = 0; i < noOfRowsTraversed; i++) {
            for (var j = 1; j <= noOfColumns; j++) {
                document.getElementById("t1c" + (i * noOfColumns + j)).bgColor = "";     // set the default color.
            }
        }
    }

    function fillColumn(id, count) {
        $('#key_person_id').val(id);

        $('#office_code').val($("#" + count + '3').html());
        $('#org_office_name').val($("#" + count + '5').html());
        $('#employeeId').val($("#" + count + '6').html());
        $('#salutation').val($("#" + count + '7').html());
        $('#key_person_name').val($("#" + count + '8').html());
        $('#password').val($("#" + count + '9').html());
        $('#father_name').val($("#" + count + '10').html());
        $('#blood').val($("#" + count + '11').html());
        var gender = $("#" + count + '12').html();
        if (gender == 'M') {
            $('#genderm').attr('checked', true);
        } else {
            $('#genderf').attr('checked', true);
        }

        $('#date_of_birth').val($("#" + count + '13').html());
        $('#designation').val($("#" + count + '14').html());
        $('#address_line1').val($("#" + count + '15').html());
        $('#address_line2').val($("#" + count + '16').html());
        $('#address_line3').val($("#" + count + '17').html());
        $('#emergency_name').val($("#" + count + '18').html());
        $('#emergency_number').val($("#" + count + '19').html());
        $('#id_type').val($("#" + count + '20').html());
        $('#id_no').val($("#" + count + '21').html());
        $('#city_name').val($("#" + count + '22').html());
        $('#mobile_no1').val($("#" + count + '23').html());
        $('#mobile_no2').val($("#" + count + '24').html());
        $('#landline_no1').val($("#" + count + '25').html());
        $('#landline_no2').val($("#" + count + '26').html());
        $('#email_id1').val($("#" + count + '27').html());
        $('#email_id2').val($("#" + count + '28').html());
        $('#latitude').val($("#" + count + '29').html());
        $('#longitude').val($("#" + count + '18').html());
        $('#serialnumber').val($("#" + count + '30').html());
        $('#general_image_details_id').val($("#" + count + '33').html());
        $('#familyoffice').val($("#" + count + '35').html());
        $('#familydesignation').val($("#" + count + '36').html());
        $('#relation').val($("#" + count + '37').html());
        //getImagePath(id);

        $('#edit').attr('disabled', false);
        $('#delete').attr('disabled', false);
    }


    function getImagePath(key_person_id) {
        $.ajax({
            url: "KeypersonController",
            dataType: "json",
            data: {action1: "getImagePath", str: key_person_id},
            success: function (data) {
                console.log(data);
                console.log(data.list);
                var image = data.list;
                for (var i = 0; i < image.length; i++) {
                    var path = image[i];
                    var url = "file:///C:\\ssadvt_repository\\ipms\\key_person\\" + path;
                    var image = new Image();
                    image.src = url;
                    $('#image_div').append(image);

                }
            }
        });
    }


    function setStatus(id) {
        if (id == 'save') {
            document.getElementById("clickedButton").value = "Save";
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
            var city_name = document.getElementById("city_name").value;
            // var state_name = document.getElementById("state_name").value;

            if (myLeftTrim(document.getElementById("office_code").value).length == 0) {
                $("#message").html("<td colspan='8' bgcolor='coral'><b>Office Code is required...</b></td>");
                document.getElementById("office_code").focus();
                return false; // code to stop from submitting the form2.
            }
            if (myLeftTrim(document.getElementById("org_office_name").value).length == 0) {
                $("#message").html("<td colspan='8' bgcolor='coral'><b>Organisation Office Name is required...</b></td>");
                document.getElementById("org_office_name").focus();
                return false; // code to stop from submitting the form2.
            }
            if (myLeftTrim(document.getElementById("employeeId").value).length == 0) {
                $("#message").html("<td colspan='8' bgcolor='coral'><b>Person Id is required...</b></td>");
                document.getElementById("employeeId").focus();
                return false; // code to stop from submitting the form2.
            }
            if (myLeftTrim(document.getElementById("salutation").value) == '---Select---') {
                $("#message").html("<td colspan='8' bgcolor='coral'><b>Salutation is required...</b></td>");
                document.getElementById("salutation").focus();
                return false; // code to stop from submitting the form2.
            }

            if (myLeftTrim(document.getElementById("key_person_name").value).length == 0) {
                $("#message").html("<td colspan='8' bgcolor='coral'><b>Key Person Name is required...</b></td>");
                document.getElementById("key_person_name").focus();
                return false; // code to stop from submitting the form2.
            }
            if (myLeftTrim(document.getElementById("designation").value).length == 0) {
                $("#message").html("<td colspan='8' bgcolor='coral'><b>Designation Name is required...</b></td>");
                document.getElementById("designation").focus();
                return false; // code to stop from submitting the form2.
            }
            // if(myLeftTrim(state_name).length == 0) {
            //   $("#message").html("<td colspan='8' bgcolor='coral'><b>State Name is required...</b></td>");
            // alert("State Name is required");
            // document.getElementById("state_name").focus();
            //  return false; // code to stop from submitting the form2.
            //  }
            if (myLeftTrim(city_name).length == 0) {
                $("#message").html("<td colspan='8' bgcolor='coral'><b>City Name is required...</b></td>");
                document.getElementById("city_name").focus();
                return false; // code to stop from submitting the form2.
            }
            if (myLeftTrim(document.getElementById("address_line1").value).length == 0) {
                $("#message").html("<td colspan='8' bgcolor='coral'><b>Address_line1 is required...</b></td>");
                document.getElementById("address_line1").focus();
                return false; // code to stop from submitting the form2.
            }
            if (myLeftTrim(document.getElementById("mobile_no1").value).length == 0) {
                $("#message").html("<td colspan='8' bgcolor='coral'><b>Mobile_no1 is required...</b></td>");
                document.getElementById("mobile_no1").focus();
                return false; // code to stop from submitting the form2.2.
            }
            if (myLeftTrim(document.getElementById("email_id1").value).length == 0) {
                $("#message").html("<td colspan='8' bgcolor='coral'><b>Email_id1 is required...</b></td>");
                document.getElementById("email_id1").focus();
                return false; // code to stop from submitting the form2.
            }
            /*if(myLeftTrim(document.getElementById("design_name").value).length == 0) {
             // alert(document.getElementById("design_name").value);
             $("#message").html("<td colspan='8' bgcolor='coral'><b>Photograph is mandatory...</b></td>");
             document.getElementById("design_name").focus();
             return false; // code to stop from submitting the form2.
             }*/
            if (result == false)
            {
                // if result has value false do nothing, so result will remain contain value false.
            } else {

                result = true;
            }
            if (document.getElementById("clickedButton").value == 'Save AS New') {
                result = confirm("Are you sure you want to save it as New record?")
                return result;
            }
        } else
            result = confirm("Are you sure you want to delete this record?")
        return result;
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
    function viewDemandNote(id, img) {
        //alert(id);
        //var emp_code= document.getElementById("emp_code1"+id).value;
        var queryString = "task1=viewImage&kp_id=" + id + "&type=" + img;
        // alert(queryString);
        var url = "KeypersonController?" + queryString;
        popupwin = openPopUp(url, "Show Image", 600, 900);
    }


    function readURL(input) {
        document.getElementById("image_perview").style.visibility = 'visible';
        if (input.files && input.files[0]) {
            var reader = new FileReader();

            reader.onload = function (e) {
                $('#blah')
                        .attr('src', e.target.result)
                        .width(150)
                        .height(200);
            };
            reader.readAsDataURL(input.files[0]);
        }
    }


    function openMapForCord() {
        var url = "GeneralController?task=GetCordinates4";//"getCordinate";
        popupwin = openPopUp(url, "", 600, 630);
    }


    function openPopUp(url, window_name, popup_height, popup_width) {
        var popup_top_pos = (screen.availHeight / 2) - (popup_height / 2);
        var popup_left_pos = (screen.availWidth / 2) - (popup_width / 2);
        var window_features = "left=" + popup_left_pos + ", top=" + popup_top_pos + ", width=" + popup_width + ", height=" + popup_height + ", resizable=yes, scrollbars=yes, location=0, menubar=no, status=no, dependent=yes";

        return window.open(url, window_name, window_features);
    }

    function emergency(id)
    {
        var url = "KeypersonController?task1=showEmergency&keyperson_id=" + id;
        popupwin = openPopUp(url, "", 600, 630);
    }


    function openMap(id) {
        //alert(vehicle_key_person_map_id);
        var url = "KeypersonController?task1=showMapWindow&keyperson_id=" + id;

        popupwin = openPopUp(url, "", 580, 620);
    }


    function myFun(value)
    {
        // debugger;
        var req = null;
        if (req != null)
            req.abort();
        var random = document.getElementById("mobile_no1").value;
        //  alert(random);

        if (random.length >= 10)
        {

            req = $.ajax({
                type: "POST",
                url: "KeypersonController",
                data: {action1: "mobile_number",
                    str: random},
                dataType: "json",
                success: function (response_data) {

                    console.log(response_data);


                    if (response_data.list[0] == ("Mobile no exist"))
                    {
                        // alert(response_data.list[0]);
                        document.getElementById("mobile_no1").value = "";
                    }

                    //  response(response_data.list);
                }, error: function (error) {
                    console.log(error.responseText);

                    response(error.responseText);
                }


            });
        }

    }

    var i = 0;
    $(document).ready(function () {

        var max_fields = 10;
        var wrapper = $(".container1");
        var add_button = $(".add_form_field");


        var x = 1;
        var em_name;
        $(add_button).click(function (e) {

            i++;

            e.preventDefault();
            if (x < max_fields) {
                x++;
                //$(wrapper).append('<tr> <th class="heading1">Emergency Contact Name' + i + '</th> <td><input class="form-control" type="text" id="emergency_name' + i + '" name="emergency_name' + i + '" value="" ></td> <th class="heading1" >Emergency Contact Number' + i + '</th> <td><input class="form-control" type="text" id="emergency_number' + i + '" name="emergency_number' + i + '" value="" > </td> <tr><a href="#" class="delete">Delete</a></tr></tr>'); //add input box
                $(wrapper).append('<div class="row mt-3" id="append_div"><div class="col-lg-4"><div class="form-group"><label>Emergency Contact Name' + i + '<span class="text-danger">*</span></label><input class="form-control myInput" type="text" id="emergency_name' + i + '" name="emergency_name' + i + '" value="" size="28"></div></div><div class="col-lg-6"><div class="form-group"><label>Emergency Contact Number' + i + '<span class="text-danger">*</span></label><input class="form-control myInput" type="text" id="emergency_number' + i + '" name="emergency_number' + i + '" value="" size="28"></div></div> <div class="col-md-2"><a href="#" class="delete btn btn-danger">x</a></div></div>'); //add input box

            } else {
                alert('You Reached the limits')
            }
            document.getElementById("i").value = i;
        });

        // debugger;
        $(wrapper).on("click", ".delete", function (e) {
            e.preventDefault();
//            $(this).parent('div').remove();
            $('#append_div').remove();
            x--;
        })
    });

    function setDefaultValues(id) {
        var result_type = document.getElementById(id).checked;

        var default_mobile_no1 = "0000000000";
        var email_id1 = "xyz@abc.com";
        var default_address = "ABC";
        var latitude = "0.0";
        var longitude = "0.0";
        var landline_no1 = "000000000";
        var landline_no2 = "00000000";
        var landline_no3 = "000000000";
        var default_address1 = "XYZ";
        var default_address2 = "UVW";
        var salutation = "Mr."
        var key_person_name = "default";
        var password = "1234";
        var father_name = "default";
        var blood = "B+"
        var date_of_birth = "01/01/2020";
        var id_type = "adhaar";
        var city = "jabalpur";
        var emergency_name = "emergency";
        var emergency_number = "1234567890";
        var id_no = "1234567890";

        if (result_type) {

            $("#mobile_no2").val(default_mobile_no1);
            $("#address_line1").val(default_address);
            $("#latitude").val(latitude);
            $("#longitude").val(longitude);
            $("#landline_no1").val(landline_no1);
            $("#email_id2").val(email_id1);
            $("#email_id1").val("xyz@gmail.com");
            $("#landline_no2").val(landline_no2);
            $("#landline_no3").val(landline_no3);
            $("#salutation").val(salutation);
            $("#address_line2").val(default_address1);
            $("#address_line3").val(default_address2);
            $("#key_person_name").val(key_person_name);
            $("#password").val(password);
            $("#father_name").val(father_name);
            $("#blood").val(blood);
            $("#date_of_birth").val(date_of_birth);
            $("#id_type").val(id_type);
            document.getElementById("genderm").checked = "M";
            $("#city_name").val(city);
            $("#id_no").val(id_no);
            $("#emergency_name").val(emergency_name);
            $("#emergency_number").val(emergency_number);

        } else {

            $("#mobile_no2").val("");
            $("#address_line1").val(" ");
            $("#latitude").val("");
            $("#longitude").val("");
            $("#landline_no1").val("");
            $("#email_id2").val("");
            $("#landline_no2").val("");
            $("#landline_no3").val("");
            $("#address_line2").val("");
            $("#address_line3").val("");
            $("#salutation").val("");
            $("#key_person_name").val("");
            $("#password").val("");
            $("#father_name").val("");
            $("#blood").val("");
            $("#date_of_birth").val("");
            $("#id_type").val("");
            $("#email_id1").val("");
            $("#city_name").val("");
            $("#id_no").val("");
            $("#emergency_name").val("");
            $("#emergency_number").val("");

        }

    }


</script>




<section>
    <div class="container-fluid page_heading sectionPadding35">
        <h1>KeyPerson</h1>
    </div>
</section>



<section class="marginTop30">
    <div class="container organizationBox">
        <div class="headBox">
            <h5 class="">Search Engine</h5>
        </div>
        <form name="form1" method="POST" action="KeypersonController" onsubmit="return verifySearch();" >
            <div class="row mt-3">
                <div class="col-md-3">
                    <div class="form-group mb-md-0">
                        <label>Organisation Name</label>
                        <input class="form-control myInput" type="text" id="searchOrg" name="searchOrg" value="${searchOrg}" size="20">
                    </div>
                </div>
                <div class="col-md-3">
                    <div class="form-group mb-md-0">
                        <label>Office Code</label>
                        <input class="form-control myInput" type="text" id="searchfamily" name="searchfamily" value="${searchfamily}" size="20">
                    </div>
                </div>
                <div class="col-md-3">
                    <div class="form-group mb-md-0">
                        <label>Person Name</label>
                        <input class="form-control myInput" type="text" id="searchKeyPerson" name="searchKeyPerson" value="${searchKeyPerson}"  size="20">
                    </div>
                </div>
                <div class="col-md-3">
                    <div class="form-group mb-md-0">
                        <label>Mobile Number</label>
                        <input class="form-control myInput" type="text" id="searchmobile" name="searchmobile" value="${searchmobile}" size="20">
                    </div>
                </div>


            </div>

            <hr>
            <div class="row">
                <div class="col-md-12 text-center">
                    <input type="submit" class="btn normalBtn" name="task" id="search" value="SEARCH RECORDS">

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
                                <th>Organization</th>
                                <th>Office Code</th>
                                <th>Office Type</th>
                                <th>Office Name</th>
                                <th>Employee Id</th>
                                <th style="display: none">Salutation</th>
                                <th>Person Name</th>
                                <th style="display: none">Password</th>
                                <th>Father's Name</th>
                                <th>Blood Group</th>
                                <th>Gender</th>
                                <th>Date Of Birth</th>
                                <th>Designation</th>
                                <th>Address Line1</th>
                                <th style="display: none">Address Line2</th>
                                <th style="display: none" >Address Line3</th>
                                <th>Emergency Contact Name</th>
                                <th>Emergency Contact Number</th>
                                <th>ID Type</th>
                                <th>ID No.</th>
                                <th>City</th>
                                <th>Mobile No. 1st</th>
                                <th style="display: none">Mobile No. 2nd</th>
                                <th style="display: none">LandLine No. 1st </th>
                                <th style="display: none">LandLine No. 2nd</th>
                                <th>Email ID 1st</th>
                                <th style="display: none">Email ID 2nd</th>
                                <th style="display: none">Latitude</th>
                                <th style="display: none">Longitude</th>
                                <th></th>
                                <th></th>
                                <th style="display: none">Image</th>
                                <th>Photo/ID</th>
                                <th>Family Office</th>
                                <th>Family Designation</th>
                                <th>Relation</th>
                            </tr>
                        </thead>



                        <tbody>                                     
                            <c:forEach var="key" items="${requestScope['keyList']}"  varStatus="loopCounter">
                                <tr  onclick="fillColumn('${key.key_person_id}', '${loopCounter.count }');">
                                    <td>${loopCounter.count }</td>
                                    <td id="${loopCounter.count }2">${key.organisation_name}</td>
                                    <td id="${loopCounter.count }3">${key.org_office_code}</td>
                                    <td id="${loopCounter.count }4">${key.office_type}</td>
                                    <td id="${loopCounter.count }5">${key.org_office_name}</td>
                                    <td id="${loopCounter.count }6">${key.emp_code}</td>
                                    <td id="${loopCounter.count }7" style="display: none">${key.salutation}</td>
                                    <td id="${loopCounter.count }8">${key.key_person_name}</td>
                                    <td id="${loopCounter.count }9" style="display: none">${key.password}</td>
                                    <td id="${loopCounter.count }10">${key.father_name}</td>
                                    <td id="${loopCounter.count }11">${key.blood}</td>
                                    <td id="${loopCounter.count }12">${key.gender}</td>
                                    <td id="${loopCounter.count }13">${key.date_of_birth}</td>
                                    <%-- <td id="t1c${IDGenerator.uniqueID}" ><img src="E:/Traffic/ImageUpload/DSP police_pic_0.jpg" height="90"width="90"></td>--%>
                                    <td id="${loopCounter.count }14">${key.designation}</td>
                                    <td id="${loopCounter.count }15">${key.address_line1}</td>
                                    <td id="${loopCounter.count }16" style="display: none">${key.address_line2}</td>
                                    <td id="${loopCounter.count }17" style="display: none">${key.address_line3}</td>
                                    <td id="${loopCounter.count }18">${key.emergency_name}</td>
                                    <td id="${loopCounter.count }19">${key.emergency_number}</td>
                                    <td id="${loopCounter.count }20">${key.id_type}</td>
                                    <td id="${loopCounter.count }21">${key.id_no}</td>
                                    <td id="${loopCounter.count }22">${key.city_name}</td>
                                    <%-- <td id="t1c${IDGenerator.uniqueID}" onclick="fillColumnKeyPerson(id)" style="display: none">${key.state_name}</td> --%>
                                    <td id="${loopCounter.count }23">${key.mobile_no1}</td>
                                    <td id="${loopCounter.count }24" style="display: none">${key.mobile_no2}</td>
                                    <td id="${loopCounter.count }25" style="display: none">${key.landline_no1}</td>
                                    <td id="${loopCounter.count }26" style="display: none">${key.landline_no2}</td>
                                    <td id="${loopCounter.count }27">${key.email_id1}</td>
                                    <td id="${loopCounter.count }28" style="display: none">${key.email_id2}</td>
                                    <td id="${loopCounter.count }29" style="display: none">${key.latitude}</td>
                                    <td id="${loopCounter.count }30" style="display: none">${key.longitude}</td>
                                    <td id="${loopCounter.count }31">
                                        <input type="btn normalBtn" class="btn btn-info"  value ="View Map" id="map_container${loopCounter.count}" onclick="openMap('${key.key_person_id}');"/>
                                    </td >
                                    <td id="${loopCounter.count }32">
                                        <input type="btn normalBtn" class="btn btn-info"  value ="Emergency Contact List" id="emergency_container${loopCounter.count}" onclick="emergency('${key.key_person_id}');"/>
                                    </td>
                                    <td id="${loopCounter.count }33" style="display: none">${key.general_image_details_id}</td>
                                    <%--  <td id="t1c${IDGenerator.uniqueID}" onclick="fillColumnKeyPerson(id)" style="display: none">${key.image_name}</td>--%>
                                    <td id="${loopCounter.count }34" >
                                        <input type="btn normalBtn" class="button" id="${loopCounter.count}" name="emp_code" value="Photo" onclick="viewDemandNote(${key.key_person_id}, 'ph')">
                                        <input type="btn normalBtn" class="button" id="${loopCounter.count}" name="emp_code" value="ID" onclick="viewDemandNote(${key.key_person_id}, 'id')">
                                    </td>
                                    <td id="${loopCounter.count }35" >${key.familyName}</td>
                                    <td id="${loopCounter.count }36" >${key.familyDesignation}</td>
                                    <td id="${loopCounter.count }37" >${key.relation}</td>
                                </tr>
                            <input type="hidden"  name="emp_code" id="emp_code1${loopCounter.count}" value="${key.emp_code}">
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
            <div>
                <strong class="text-white">Default Value </strong>&nbsp;&nbsp;<input type="checkbox" class="checkbox-inline" id="default" name="default" onclick="setDefaultValues(id)">
            </div>
        </div>
        <form name="form2" method="POST" action="KeypersonController" onsubmit="return verify()" enctype="multipart/form-data">

            <div class="row mt-3">
                <div>
                    <input type="hidden" id="key_person_id" name="key_person_id" value="">
                    <input type="hidden" id="general_image_details_id" name="general_image_details_id" value="" size="28"   />
                    <input type="hidden" id="i" name="i" value=${i}>
                </div>
                <div class="col-md-3">
                    <div class="">
                        <div class="form-group">
                            <label>Office Code<span class="text-danger">*</span></label>                            
                            <input class="form-control myInput" type="text" id="office_code" name="office_code"  size="20" disabled>
                        </div>
                    </div>
                </div>
                <div class="col-md-3">
                    <div class="">
                        <div class="form-group">
                            <label>Office Name<span class="text-danger">*</span></label>
                            <input class="form-control myInput" type="text" id="org_office_name" name="org_office_name"  size="28"  disabled>
                        </div>
                    </div>
                </div>
                <div class="col-md-3">
                    <div class="">
                        <div class="form-group">
                            <label>Employee Id<span class="text-danger">*</span></label>
                            <input class="form-control myInput" type="text" id="employeeId" name="employeeId" value=""  size="28" disabled>
                        </div>
                    </div>
                </div>
                <div class="col-md-3">
                    <div class="">
                        <div class="form-group">
                            <label>Salutation<span class="text-danger">*</span></label>
                            <select class="dropdown-toggle form-control mySelect" id="salutation" name="salutation" value=""  disabled>
                                <option>---Select--- </option>
                                <option style="text-align: center">Mr.</option>
                                <option style="text-align: center">Ms.</option>
                                <option style="text-align: center">Mrs.</option>
                            </select>
                        </div>
                    </div>
                </div>
            </div>
            <div class="row mt-3">
                <div class="col-md-3">
                    <div class="">
                        <div class="form-group">
                            <label>Person Name<span class="text-danger">*</span></label>
                            <input class="form-control myInput" type="text" id="key_person_name" name="key_person_name" value=""  size="30" disabled>
                        </div>
                    </div>
                </div>
                <div class="col-md-3">
                    <div class="">
                        <div class="form-group">
                            <label>Password<span class="text-danger">*</span></label>
                            <input class="form-control myInput" type="password" id="password" name="password" value=""  size="30" disabled>
                        </div>
                    </div>
                </div>
                <div class="col-md-3">
                    <div class="">
                        <div class="form-group">
                            <label>Father's Name<span class="text-danger">*</span></label>
                            <input class="form-control myInput" type="text" id="father_name" name="father_name" value=""  size="30" disabled>
                        </div>
                    </div>
                </div>
                <div class="col-md-3">
                    <div class="">
                        <div class="form-group">
                            <label>Blood Group<span class="text-danger">*</span></label>
                            <select class="ui dropdown mySelect form-control" name="blood" id="blood" disabled>
                                <option>---Select--- </option>
                                <option value="A+">A+</option>
                                <option value="A-">A-</option>
                                <option value="B+">B+</option>
                                <option value="B-">B-</option>
                                <option value="O+">O+</option>
                                <option value="O-">O-</option>
                                <option value="AB+">AB+</option>
                                <option value="AB-">AB-</option>
                            </select>
                        </div>
                    </div>
                </div>
            </div>
            <div class="row mt-3">
                <div class="col-md-3">
                    <div class="">
                        <div class="form-group">
                            <label>Date Of Birth<span class="text-danger">*</span></label>
                            <input class="form-control myInput" type="date" id="date_of_birth" name="date_of_birth" value=""  size="5" disabled>
                        </div>
                    </div>
                </div>
                <div class="col-md-3">
                    <div class="">
                        <div class="form-group">
                            <label>Designation<span class="text-danger">*</span></label>
                            <input class="form-control myInput" type="text" id="designation" name="designation" value=""  size="30" disabled>
                        </div>
                    </div>
                </div>
                <div class="col-md-3">
                    <div class="">
                        <div class="form-group">
                            <label>Address Line1<span class="text-danger">*</span></label>
                            <input class="form-control myInput" type="text" id="address_line1" name="address_line1" value=""  size="28"  disabled>
                        </div>
                    </div>
                </div>
                <div class="col-md-3">
                    <div class="">
                        <div class="form-group">
                            <label>Address Line2<span class="text-danger">*</span></label>
                            <input class="form-control myInput" type="text" id="address_line2" name="address_line2" value=""   size="25" disabled>
                        </div>
                    </div>
                </div>
            </div>

            <div class="row mt-3">
                <div class="col-md-3">
                    <div class="">
                        <div class="form-group">
                            <label>Address Line3<span class="text-danger">*</span></label>
                            <input class="form-control myInput" type="text" id="address_line3" name="address_line3" value=""  size="30"  disabled>
                        </div>
                    </div>
                </div>
                <div class="col-md-3">
                    <div class="">
                        <div class="form-group">
                            <label>Emergency Contact Name<span class="text-danger">*</span></label>
                            <input class="form-control myInput" type="text" id="emergency_name" name="emergency_name" value="" size="28"  disabled>
                        </div>
                    </div>
                </div>
                <div class="col-md-3">
                    <div class="">
                        <div class="form-group">
                            <label>Emergency Contact Number<span class="text-danger">*</span></label>
                            <input class="form-control myInput" type="text" id="emergency_number" name="emergency_number" value="" size="28"  disabled>
                        </div>
                    </div>
                </div>
            </div>
            <div class="row mt-3 container1">
                <div class="col-md-12">
                    <div class="form-group">
                        <button class="add_form_field btn btn-info" id="emerg">Add Emergency Name and Contact Fields &nbsp; 
                            <span style="font-size:16px; font-weight:bold;">+ </span>
                        </button>
                    </div>
                </div>
            </div>

            <div class="row mt-3">
                <div class="col-md-3">
                    <div class="">
                        <div class="form-group">
                            <label>ID Type<span class="text-danger">*</span></label>
                            <select class="ui dropdown form-control mySelect" name="id_type" id="id_type" disabled>
                                <option>---Select--- </option>
                                <c:forEach var="id_list"  items="${requestScope['id_list']}">
                                    <option value="${id_list}">
                                        <c:out value="${id_list}"/>
                                    </option>
                                </c:forEach>
                            </select>
                        </div>
                    </div>
                </div>
                <div class="col-md-3">
                    <div class="">
                        <div class="form-group">
                            <label>ID No.<span class="text-danger">*</span></label>
                            <input class="form-control myInput" type="text" id="id_no" name="id_no" value=""  size="30"  disabled>
                        </div>
                    </div>
                </div>

                <!--                <div class="col-md-3">
                                    <div class="">
                                        <div class="form-group mb-1">
                                            <label class="" for="email">Gender<span class="text-danger">*</span></label>
                                        </div>
                                        <div class="form-group form-check mb-0 d-inline mr-2 pl-0">
                                            <label class="form-check-label ">
                                                <input type="radio" id="genderm" name="gender" value="M" disabled> Male
                                            </label>
                                        </div>
                                        <div class="form-group form-check d-inline pl-0">
                                            <label class="form-check-label">
                                              <input type="radio" id="genderf" name="gender" value="F" disabled> Female
                                            </label>
                                        </div>
                                    </div>
                                </div>-->
                <div class="col-md-3">
                    <div class="">
                        <div class="form-group">
                            <label>City<span class="text-danger">*</span></label>
                            <input class="form-control myInput" type="text" id="city_name" name="city_name"  size="25" value="" disabled>
                        </div>
                    </div>
                </div>
            </div>
            <div class="row mt-3">
                <div class="col-md-3">
                    <div class="">
                        <div class="form-group">
                            <label>Mobile No. 1st<span class="text-danger">*</span></label>
                            <input class="form-control myInput" type="text" id="mobile_no1" name="mobile_no1"  size="25" value="" disabled>
                        </div>
                    </div>
                </div>
                <div class="col-md-3">
                    <div class="">
                        <div class="form-group">
                            <label>Mobile No. 2st<span class="text-danger">*</span></label>
                            <input class="form-control myInput" type="text" id="mobile_no2" name="mobile_no2"  size="25" value="" disabled>
                        </div>
                    </div>
                </div>
                <div class="col-md-3">
                    <div class="">
                        <div class="form-group">
                            <label>LandLine No. 1st<span class="text-danger">*</span></label>
                            <input class="form-control myInput" type="text" id="landline_no1" name="landline_no1"  size="25" value="" disabled>
                        </div>
                    </div>
                </div>
                <div class="col-md-3">
                    <div class="">
                        <div class="form-group">
                            <label>LandLine No. 2nd<span class="text-danger">*</span></label>
                            <input class="form-control myInput" type="text" id="landline_no2" name="landline_no2"  size="25" value="" disabled>
                        </div>
                    </div>
                </div>
            </div>
            <div class="row mt-3">
                <div class="col-md-3">
                    <div class="">
                        <div class="form-group">
                            <label>Email ID 1st<span class="text-danger">*</span></label>
                            <input class="form-control myInput" type="text" id="email_id1" name="email_id1"  size="25" value="" disabled>
                        </div>
                    </div>
                </div>
                <div class="col-md-3">
                    <div class="">
                        <div class="form-group">
                            <label>Email ID 2nd<span class="text-danger">*</span></label>
                            <input class="form-control myInput" type="text" id="email_id2" name="email_id2"  size="25" value="" disabled>
                        </div>
                    </div>
                </div>
                <div class="col-md-3">
                    <div class="">
                        <div class="form-group">
                            <label>Latitude<span class="text-danger">*</span></label>
                            <input class="form-control myInput" type="text" id="latitude" name="latitude"  size="25" value="" disabled>
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
            </div>


            <div class="row mt-3">
                <div class="col-md-3">
                    <div class="">
                        <div class="form-group">
                            <label>Select Photo<span class="text-danger">*</span></label>
                            <input class="form-control myInput" type="file" id="design_name" name="design_name"  size="30" value="" disabled onchange="readURL(this);"> 
                        </div>
                    </div>
                </div>
                <div class="col-md-3">
                    <div class="">
                        <div class="form-group">
                            <label>Select ID OR DL<span class="text-danger">*</span></label>
                            <input class="form-control myInput" type="file" id="id_proof" name="id_proof"  size="30" value="" disabled onchange="readURL(this);">
                        </div>
                    </div>
                </div>
                <div class="col-md-3">
                    <div class="">
                        <div class="form-group mb-1">
                            <label class="" for="email">Gender<span class="text-danger">*</span></label>
                        </div>
                        <div class="form-group form-check mb-0 d-inline mr-2 pl-0">
                            <label class="form-check-label ">
                                <input type="radio" id="genderm" name="gender" value="M" disabled> Male
                            </label>
                        </div>
                        <div class="form-group form-check d-inline pl-0">
                            <label class="form-check-label">
                                <input type="radio" id="genderf" name="gender" value="F" disabled> Female
                            </label>
                        </div>
                    </div>
                </div>

            </div>   
            <div class="row mt-3">
                <div class="col-md-3" id="image_div">

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


                <input type="hidden" name="familyoffice" id="familyoffice" value="10">
                <input type="hidden" name="relation" id="relation" value="10">
                <input type="hidden" name="familydesignation" id="familydesignation" value="10">
                <div class="col-md-12 text-center"> 
                    <input type="button" class="btn normalBtn" name="edit" id="edit" value="Edit" onclick="makeEditable(id)" disabled>
                    <input type="submit" class="btn normalBtn" name="task" id="save" value="Save" onclick="setStatus(id)" disabled>
                    <!--<input type="submit" class="btn normalBtn" name="task" id="save_As" value="Save AS New" onclick="setStatus(id)" disabled>-->
                    <input type="reset" class=" btn normalBtn" name="new" id="new" value="New" onclick="makeEditable(id)" >
                    <input type="submit" class="btn normalBtn" name="task" id="delete" value="Delete" onclick="setStatus(id)" disabled>
                </div>
            </div>
        </form>
    </div>
</section>



<%@include file="../layout/footer.jsp" %>

