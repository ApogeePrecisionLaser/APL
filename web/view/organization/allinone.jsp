<%--
    Document   : workType
    Created on : Feb 16, 2012, 6:18:45 PM
    Author     : Tarun
--%>


<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta charset="utf-8">
        <title>Data Entry: Organization Name Table</title>







        <link rel="stylesheet" href="//code.jquery.com/ui/1.10.4/themes/smoothness/jquery-ui.css">

        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">

        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css">
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js" integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q" crossorigin="anonymous"></script>

        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js" integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl" crossorigin="anonymous"></script>


        <script src="//code.jquery.com/jquery-1.10.2.js"></script>
        <script src="//code.jquery.com/ui/1.10.4/jquery-ui.js"></script>


        <script src="https://cdnjs.cloudflare.com/ajax/libs/moment.js/2.21.0/moment.min.js" type="text/javascript"></script>
        <link rel="stylesheet" href="time/bootstrap-datetimepicker.min.css">
        <script  type="text/javascript" src="time/moment.min.js"></script>
        <script  type="text/javascript" src="time/bootstrap-datetimepicker.min.js"></script>



        <script type="text/javascript" language="javascript">




//              $("#org_name").autocomplete({
//                    
//            source: function (request, response) {
//                debugger;
//                $.ajax({
//                    url: "orgNameCont.do",
//                    dataType: "json",
//                    data: {action1: "searchOrganisationName"},
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
//                $('#org_name').val(ui.item.label); // display the selected text
//                return false;
//            }
//        });



            $(function () {

                $("#organisation_type").autocomplete({

                    source: function (request, response) {

                        var random = document.getElementById("organisation_type").value;
                        $.ajax({
                            url: "orgNameCont.do",
                            dataType: "json",
                            data: {action1: "getOrganisationTypeName", str: random},
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
                        $('#organisation_type').val(ui.item.label); // display the selected text
                        return false;
                    }
                });

            });


            $(function () {

                $("#organisation_sub_type_name").autocomplete({

                    source: function (request, response) {
                        var org_type = document.getElementById("organisation_type").value;
                        var random = document.getElementById("organisation_sub_type_name").value;
                        $.ajax({
                            url: "orgNameCont.do",
                            dataType: "json",
                            data: {action1: "getOrganisationSubTypeName",
                                action2: org_type,
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
                        $('#organisation_sub_type_name').val(ui.item.label); // display the selected text
                        return false;
                    }


                });


                $("#org_name").autocomplete({

                    source: function (request, response) {

                        var random = document.getElementById("org_name").value;

                        $.ajax({
                            url: "orgNameCont.do",
                            dataType: "json",
                            data: {action1: "searchOrganisationName", str: random},
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
                        $("#orgshow").show();
                        $('#org_name').val(ui.item.label); // display the selected text
                        fillorg(ui.item.label);
                        return false;
                    }


                });

                $("#office_name_search").autocomplete({

                    source: function (request, response) {
                        var org_name = document.getElementById("org_name").value;
                        var office_code = null;
                        var random = document.getElementById("office_name_search").value;
                        $.ajax({
                            url: "organisationCont.do",
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
                        $("#officeshow").show();
                        $('#office_name_search').val(ui.item.label); // display the selected text
                        filloffice(ui.item.label);
                        return false;
                    }
                });


                $("#searchKeyPerson").autocomplete({

                    source: function (request, response) {

                        var org = document.getElementById("org_name").value;
                        var code = document.getElementById("office_name_search").value;
                        var random = document.getElementById("searchKeyPerson").value;
                        $.ajax({
                            url: "personCount.do",
                            dataType: "json",
                            data: {action1: "getSearchKeyPersonforall",
                                action2: code,
                                str: random,
                                org:org},
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
                         $("#personshow").show();
                        fillperson(ui.item.label);
                        return false;
                    }
                });


            });
//       $(function () {  
//                 
//              $("#organisation_name").autocomplete({
//                    
//            source: function (request, response) {
//                debugger;
//                $.ajax({
//                    url: "orgNameCont.do",
//                    dataType: "json",
//                    data: {action1: "getOrganisationName"},
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
//                $('#organisation_name').val(ui.item.label); // display the selected text
//                return false;
//            }
//        });

//        $('#btnDialog').click(function ()
//{
//    $(this).speedoPopup(
//    {
//        width:550,
//        height:265,
//        useFrame: TRUE,
//        href: '#divContentToPopup'
//    });
//});





            function makeEditable(id) {
                debugger;
                document.getElementById("organisation_name").disabled = false;
                document.getElementById("organisation_type").disabled = false;
                document.getElementById("code").disabled = false;
                document.getElementById("description").disabled = false;
                if (id === 'new') {
                    // document.getElementById("message").innerHTML = "";      // Remove message
                    $("#message").html("");
                    document.getElementById("organisation_id").value = "";
                    //document.getElementById("organisation_sub_type_id").value = "";
                    //document.getElementById("organisation_type_id").value = "";
                    document.getElementById("edit").disabled = true;
                    document.getElementById("delete").disabled = true;
                    document.getElementById("save_As").disabled = true;
                    document.getElementById("save").disabled = false;

                    setDefaultColor(document.getElementById("noOfRowsTraversed").value, 4);
                    document.getElementById("organisation_name").focus();
                    document.getElementById("organisation_type").focus();
                    document.getElementById("description").focus();
                    document.getElementById("code").focus();
                }
                if (id == 'edit') {
                    document.getElementById("save_As").disabled = true;
                    document.getElementById("delete").disabled = false;
                }
                document.getElementById("save").disabled = false;
            }
            function setDefaultColor(noOfRowsTraversed, noOfColumns) {
                for (var i = 0; i < noOfRowsTraversed; i++) {
                    for (var j = 1; j <= noOfColumns; j++) {
                        document.getElementById("t1c" + (i * noOfColumns + j)).bgColor = "";     // set the default color.
                    }
                }
            }
            function fillColumns(id) {
                debugger;

                var noOfRowsTraversed = document.getElementById("noOfRowsTraversed").value;
                var noOfColumns = 6;
                var columnId = id;
            <%-- holds the id of the column being clicked, excluding the prefix t1c e.g. t1c3 (column 3 of table 1). --%>
                columnId = columnId.substring(3, id.length);
            <%-- for e.g. suppose id is t1c3 we want characters after t1c i.e beginIndex = 3. --%>
                var lowerLimit, higherLimit, rowNo = 0;
                for (var i = 0; i < noOfRowsTraversed; i++)
                {
                    lowerLimit = i * noOfColumns + 1;       // e.g. 11 = (1 * 10 + 1)
                    higherLimit = (i + 1) * noOfColumns;    // e.g. 20 = ((1 + 1) * 10)
                    rowNo++;
                    if ((columnId >= lowerLimit) && (columnId <= higherLimit))
                        break;
                }

                // set default color of rows (i.e. of multiple coloumns).
                var t1id = "t1c";       // particular column id of table 1 e.g. t1c3.

                document.getElementById("organisation_id").value = document.getElementById("organisation_id" + rowNo).value;

                document.getElementById("organisation_type").value = document.getElementById(t1id + (lowerLimit + 1)).innerHTML;
                //document.getElementById("organisation_sub_type_name").value = document.getElementById(t1id + (lowerLimit + 2)).innerHTML;
                document.getElementById("organisation_name").value = document.getElementById(t1id + (lowerLimit + 3)).innerHTML;
                document.getElementById("code").value = document.getElementById(t1id + (lowerLimit + 4)).innerHTML;
                document.getElementById("description").value = document.getElementById(t1id + (lowerLimit + 5)).innerHTML;


                $("#message").html("");
            }
            ;

            function setStatus(id) {
                if (id === 'save') {
                    document.getElementById("clickedButton").value = "Save";
                } else if (id === 'save_As') {
                    document.getElementById("clickedButton").value = "Save AS New";
                } else if (id === 'search_org') {
                    var org_name = document.getElementById("org_name").value;
                    document.getElementById("org_name1").value = org_name;
                    document.getElementById("org_name2").value = org_name;
                    document.getElementById("clickedButton").value = "SEARCH";
                } else if (id === 'clear_org') {
                    document.getElementById("clickedButton").value = " ";
                    $("#org_msg").html("");
                    document.getElementById("org_name").value = " ";
                    document.getElementById("org_name1").value = " ";
                    document.getElementById("org_name2").value = " ";
                } else {
                    document.getElementById("clickedButton").value = "Delete";
                    ;
                }
            }
            function myLeftTrim(str) {
                var beginIndex = 0;
                for (var i = 0; i < str.length; i++) {
                    if (str.charAt(i) === ' ')
                        beginIndex++;
                    else
                        break;
                }
                return str.substring(beginIndex, str.length);
            }
            function verify() {
                var result;
                if (document.getElementById("clickedButton").value === 'Save' || document.getElementById("clickedButton").value === 'Save AS New') {
                    var organisation_name = document.getElementById("organisation_name").value;
                    if (myLeftTrim(organisation_name).length === 0) {
                        // document.getElementById("message").innerHTML = "<td colspan='5' bgcolor='coral'><b>Organisation Name is required...</b></td>";
                        $("#message").html("<td colspan='2' bgcolor='coral'><b>Organisation Name is required...</b></td>");
                        document.getElementById("organisation_name").focus();
                        return false; // code to stop from submitting the form2.
                    }
                    if (result === false) {
                        // if result has value false do nothing, so result will remain contain value false.
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
            function verifySearch() {
                var result;
                if (document.getElementById("clickedButton").value === 'SEARCH') {
                    var org_name = document.getElementById("org_name").value;
                    if (myLeftTrim(org_name).length === 0) {
                        document.getElementById("org_msg").innerHTML = "<b>Organization Name is required...</b>";
                        document.getElementById("org_name").focus();
                        return false; // code to stop from submitting the form2.
                    }
                }
            }


            function fillorg(name)
            {
                $.ajax({
                    url: "orgNameCont.do",
                    dataType: "json",
                    data: {action1: "getslectedorg", str: name},
                    success: function (data) {
                        debugger;
                        document.getElementById("organisation_id").value = data.id;
                        document.getElementById("organisation_type").value = data.orgtype;
                        document.getElementById("organisation_name").value = data.orgname;
                        document.getElementById("code").value = data.orgcode;
                        document.getElementById("description").value = data.desc;

                    }, error: function (error) {
                        debugger;
                        console.log(error.responseText);
                        response(error.responseText);
                    }
                });
            }

            function filloffice(name)
            {
                $.ajax({
                    url: "orgNameCont.do",
                    dataType: "json",
                    data: {action1: "getslectedoffice", str: name},
                    success: function (data) {
                        debugger;
                        document.getElementById("org_office_id").value = data.id;
                        document.getElementById("organisation_name2").value = data.orgname;
                        document.getElementById("office_type").value = data.offtype;
                        document.getElementById("org_office_code").value = data.offcode;
                        document.getElementById("org_office_name").value = data.offname;
                        document.getElementById("address_line1").value = data.address;
                        document.getElementById("city_name").value = data.city;
                        document.getElementById("email_id1").value = data.email;
                        document.getElementById("mobile_no1").value = data.mobile;
                        document.getElementById("landline_no1").value = data.landline;
                        document.getElementById("latitude").value = data.latitude;
                        document.getElementById("longitude").value = data.longitude;
                        document.getElementById("serialnumber").value = data.serial;
                        document.getElementById("generation").value = data.generation;

                    }, error: function (error) {
                        debugger;
                        console.log(error.responseText);
                        response(error.responseText);
                    }
                });
            }

            function fillperson(name)
            {
                $.ajax({
                    url: "orgNameCont.do",
                    dataType: "json",
                    data: {action1: "getslectedperson", str: name},
                    success: function (data) {
                        debugger;
                        document.getElementById("key_person_id").value = data.id;
                     
                        document.getElementById("org_office_name2").value = data.offname;
                        document.getElementById("employeeId").value = data.emp;
                        document.getElementById("key_person_name").value = data.name;
                        document.getElementById("password").value = data.password;
                        document.getElementById("father_name").value = data.fathername;
                        document.getElementById("blood").value = data.blood;
                        document.getElementById("date_of_birth").value = data.dob;
                        document.getElementById("designation").value = data.desig;
                        document.getElementById("emergency_name").value = data.emername;
                        document.getElementById("emergency_number").value = data.emernumber;
                        document.getElementById("id_type ").value = data.idtype;
                        document.getElementById("id_no").value = data.idnumber;
                        document.getElementById("gender").value = data.gender;
                        document.getElementById("address").value = data.address;
                        document.getElementById("city").value = data.city;
                        document.getElementById("mobile").value = data.mobile;
                        document.getElementById("landline").value = data.landline;
                        document.getElementById("email").value = data.email;
                        document.getElementById("famoffice").value = data.famoffice;
                        document.getElementById("famdesignation").value = data.famdesig;
                        document.getElementById("relation").value = data.relation;
                        document.getElementById("latitude2").value = data.lat2;
                        document.getElementById("longitude2").value = data.long2;

                    }, error: function (error) {
                        debugger;
                        console.log(error.responseText);
                        response(error.responseText);
                    }
                });
            }


            function displayOrgnList(id) {
                var queryString;
                var active = document.getElementById("activee").value;
                var org_name = document.getElementById("org_name").value;
                if (id === 'viewPdf')
                    queryString = "requester=PRINT" + "&active=" + active + "&org_name=" + org_name;
                else
                    queryString = "requester=PRINTXls";
                var url = "orgNameCont.do?" + queryString;
                popupwin = openPopUp(url, "Organisation", 600, 900);
            }

            function openPopUp(url, window_name, popup_height, popup_width) {
                var popup_top_pos = (screen.availHeight / 2) - (popup_height / 2);
                var popup_left_pos = (screen.availWidth / 2) - (popup_width / 2);
                var window_features = "left=" + popup_left_pos + ", top=" + popup_top_pos + ", width=" + popup_width + ", height=" + popup_height + ", resizable=no, scrollbars=yes, status=no, dialog=yes, dependent=yes";
                return window.open(url, window_name, window_features);
            }



            function selectorg(id) {
                var org = document.getElementById("org_name").value;
                if (org === "")
                {
                    alert("select a organisation first");
                } else
                {

                    $("#orgshow").hide();
                    document.getElementById("org_name").disabled = true;
                    document.getElementById("office_name_search").disabled = false;

                }
            }


            function  resetoffice(id){
                document.getElementById("searchKeyPerson").disabled = true;
                 document.getElementById("office_name_search").disabled = false;
                 $("#orgshow").hide();
                 $("#officeshow").hide();
                 $("#personshow").hide();
                 
                 document.getElementById("office_name_search").value="";
                 document.getElementById("searchKeyPerson").value="";
                
            }
            
            function resetorg(id){
                 document.getElementById("searchKeyPerson").disabled = true;
                 document.getElementById("office_name_search").disabled = true;
                 document.getElementById("org_name").disabled = false;
                 
                 $("#orgshow").hide();
                 $("#officeshow").hide();
                 $("#personshow").hide();
                 
                 document.getElementById("office_name_search").value="";
                 document.getElementById("searchKeyPerson").value="";
                 document.getElementById("org_name").value="";
                 
                 
            }

            function selectoffice(id) {
                var org = document.getElementById("office_name_search").value;
                if (org === "")
                {
                    alert("select a office first");
                } else
                {

                    $("#officeshow").hide();
                    document.getElementById("org_name").disabled = true;
                    document.getElementById("office_name_search").disabled = true;
                    document.getElementById("searchKeyPerson").disabled = false;
                }
            }

             function displayOrgnList(id) {
                var queryString;
                var office_name_search = document.getElementById("office_name_search").value;
                var org_name = document.getElementById("org_name").value;
                var searchKeyPerson = document.getElementById("searchKeyPerson").value;
                if (id === 'viewPdf')
                    queryString = "requester=PRINT" + "&office_name_search=" + office_name_search + "&org_name=" + org_name+"&searchKeyPerson="+searchKeyPerson;
                else
                    queryString = "requester=viewPdfOrg"+"&org_name="+org_name;
                var url = "allinoneCont.do?" + queryString;
                popupwin = openPopUp(url, "Organisation", 600, 900);
            }

 function displayKpList(id) {
                var queryString;
               
                var office_name_search = document.getElementById("office_name_search").value;
                var org_name = document.getElementById("org_name").value;
                var searchKeyPerson = document.getElementById("searchKeyPerson").value;
                if (id === 'viewPdf')
                    queryString = "requester=PRINTKp" + "&office_name_search=" + office_name_search + "&org_name=" + org_name+"&searchKeyPerson="+searchKeyPerson;
                else
                    queryString = "requester=PRINTXls";
                var url = "allinoneCont.do?" + queryString;
                popupwin = openPopUp(url, "Organisation", 600, 900);
            }





//            if (!document.all) {
//                document.captureEvents(Event.CLICK);
//            }
//            document.onclick = function () {
//                if (popupwin !== null && !popupwin.closed) {
//                    popupwin.focus();
//                }
//            }

//             function changeClass(){
//                        var language=document.getElementById("language").value;
//                        if(language=='English'){
//                            $( "#organisation_name" ).addClass('input').removeClass('new_input');
//                            $("#description" ).addClass('input').removeClass('new_input');
//                             $("#org_name" ).addClass('input').removeClass('new_input');
//                              }
//                        else{
//                            $( "#organisation_name" ).addClass('new_input').removeClass('input');
//                            $("#description" ).addClass('new_input').removeClass('input');
//                                $("#org_name" ).addClass('new_input').removeClass('input');
//                            }
//                    }
//                     function changeSearchClass(){
//                        var language=document.getElementById("language_type").value;
//                        if(language=='English'){
//                            $( "#org_name" ).addClass('input').removeClass('new_input');
//                              }
//                        else{
//                            $( "#org_name" ).addClass('new_input').removeClass('input');
//                                            }
//                    }
        </script>
        <style>
            body {
                background-color: #E7475E;
                /*                background-image: linear-gradient(to right, #E7475E , #EC9BA7);*/
            }
            .boxeffext{
                background-color: white;
                box-shadow:-2px 3px 40px 16px rgba(0,0,0,0.47);

                margin-top: 6%;
            }
            .topbox
            {
                margin-top: 10%;
                text-align: center;
                color:white;
                font-weight: 900;

            }
            .topbox { 
                color: white; 
                font: bold 52px "Arial Black", Gadget, sans-serif;
                text-shadow: 1px 1px #fe4902, 
                    2px 2px #fe4902, 
                    3px 3px #fe4902;
                -webkit-transition: all 0.12s ease-out;
                -moz-transition:    all 0.12s ease-out;
                -ms-transition:     all 0.12s ease-out;
                -o-transition:      all 0.12s ease-out;
            }
            .topbox:hover {
                position: relative; 
                top: -3px; 
                left: -3px; 
                text-shadow: 1px 1px #fe4902, 
                    2px 2px #fe4902, 
                    3px 3px #fe4902, 
                    4px 4px #fe4902, 
                    5px 5px #fe4902, 
                    6px 6px #fe4902;
            }



            .hidden{
                display:none;
            }



            .maintr
            {

                padding-top: 50px;
            }

        </style>        

    </head>
    <body>
        <div class="container-fluid">
            <!--DWLayoutDefaultTable-->
            <%@include file="/layout/header.jsp" %>




            <!--                            <tr><td>
                                                <table>
                                                    <tr>
                                                        <td align="center" class="header_table" width="800"></td>
                                                        <td>
                                                         
                                                        </td>
                                                    </tr>
                                                </table>
                                            </td></tr>-->

            <div class="container  topbox">
                <h1>All in One</h1>
            </div>



            <div class="container ">

                <form name="form1" method="POST" action="orgNameCont.do" onsubmit="return verifySearch();" class="boxeffext ">
                    <table  class="table  table-striped ">
                        <tr>

                            <th>Organization</th>
                            <td><input  type="text" class="form-control" id="org_name" name="org_name" value="${org_name}" size="35"></td>
                            <td><input type="button" class="btn btn-primary" id="search_org" name="search_org" value="Select" onclick="selectorg(id)"></td>
                            
                            <td><input type="button" class="btn btn-primary" id="reset1" name="reset1" value="Reset" onclick="resetorg(id)"></td>
                            
                             <td><input type="button" class="btn btn-info" id="viewPdfOrg" name="PRINT" value="PDF" onclick="displayOrgnList(id)"></td>
                             
                            <td><label id="org_msg">  </label></td>

                        </tr>
                    </table>


                </form>
            </div>

            <DIV class="container  hidden" id="orgshow">
                <form name="form2" method="POST" action="orgNameCont.do" onsubmit="return verify()" class="boxeffext">
                    <table class="table table-bordered table-striped "  id="table2" align="center" >
                        <tr id="message">
                            <c:if test="${not empty message}">
                                <td colspan="2" bgcolor="${msgBgColor}"><b>Result: ${message}</b></td>
                            </c:if>
                        </tr>


                        <tr>

                            <th class="heading1">Organisation Type<span class="required_field_class">*</span></th>
                            <td>
                                <input class="form-control" type="hidden" id="organisation_id" name="organisation_id" value="" >
                                <input class="form-control" type="text" id="organisation_type" name="organisation_type" size="60" value="" disabled >

                            </td>


                            <th class="heading1">Organisation Name<span class="required_field_class">*</span></th><td>

                                <input class="form-control" type="text" id="organisation_name" name="organisation_name" size="60" value="" disabled >
                            </td>
                        </tr>

                        <tr>

                            <th class="heading1">Organisation Code<span class="required_field_class">*</span></th><td>

                                <input class="form-control" type="text" id="code" name="code" size="60" value="" disabled>
                            </td>


                            <th class="heading1">Description</th><td><input class="form-control" type="text" id="description" name="description" size="60" value="" disabled ></td>
                        </tr>
                        <!--                        <tr>
                                                    <td align='center' colspan="4" >
                                                        <input type="button" class="btn btn-primary" name="edit" id="edit" value="Edit" onclick="makeEditable(id)" >
                                                        <input type="submit" class="btn btn-success" name="task" id="save" value="Save" onclick="setStatus(id)" disabled >
                                                        <input type="submit" class="btn btn-info" name="task" id="save_As" value="Save AS New" onclick="setStatus(id)" disabled>
                                                        <input type="reset" class="btn btn-danger" name="new" id="new" value="New" onclick="makeEditable(id)" >
                                                        <input type="submit" class="btn btn-primary" name="task" id="delete" value="Delete" onclick="setStatus(id)" >
                                                    </td>
                                                </tr>-->

                        <%-- These hidden fields "lowerLimit", "noOfRowsTraversed", and "clickedButton" belong to form2 of table2. --%>
                        <input type="hidden" name="lowerLimit" value="${lowerLimit}">
                        <input type="hidden" name="noOfRowsTraversed" value="${noOfRowsTraversed}">
                        <input type="hidden" id="clickedButton" value="">
                        <input type="hidden" id="org_name1" name="org_name" value="${org_name}">

                    </table>
                </form>
            </div>




            <div class="container ">

                <form name="form0" class="form-group row boxeffext" method="POST" action="organisationCont.do" >
                    <table  class="table  table-striped ">
                        <tr>
                            <th>  Office Name&nbsp;&nbsp;&nbsp;</th>
                            <td><input type="text"  class="form-control" id="office_name_search" name="office_name_search" value="${office_name_search}" size="20" disabled></td>
                            <td><input type="button" class="btn btn-primary" id="search_org" name="search_org" value="Select" onclick="selectoffice(id)"></td>
                            <td><input type="button" class="btn btn-primary" id="reset2" name="reset2" value="Reset" onclick="resetoffice(id)"></td>
                            <td><input type="button" class="btn btn-info" id="viewPdf" name="PRINT" value="PDF" onclick="displayOrgnList(id)"></td>
                            <td><label id="org_msg">  </label></td>
                        </tr>

                    </table>


                </form>
            </div>


            <div class="container hidden" id="officeshow">
                <form name="form2" method="POST" class="form-group row boxeffext" action="organisationCont.do" onsubmit="return verify()">
                    <table id="table2"  class="table table-bordered table-striped" border="0"  align="center" >
                        <tr id="message">
                            <c:if test="${not empty message}">
                                <td colspan="8" bgcolor="${msgBgColor}"><b>Result: ${message}</b></td>
                            </c:if>
                        </tr>


                        <tr>
                            <th class="heading1" >Organization Name</th>
                            <td>
                                <input type="hidden" id="org_office_id" name="org_office_id" value="" >
                                <input class="form-control" type="text" id="organisation_name2" name="organisation_name2" value="${after_save_organisation}" disabled>
                            </td>
                            <th class="heading1">Office Type</th>
                            <td><input  type="text" class="form-control" id="office_type" name="office_type" value="${after_save_office_type}" onclick ="codeIsEmpty()" disabled></td>

                            <th class="heading1" nowrap>Office Code</th>
                            <td><input class="form-control" type="text" id="org_office_code" name="org_office_code" value="${after_office_code}" disabled></td>

                        </tr>
                        <tr>
                            <th class="heading1" nowrap>Office Name</th>
                            <td><input type="text" class="form-control" id="org_office_name" name="org_office_name" value="" disabled></td>

                            <th class="heading1" nowrap>Address Line1</th>
                            <td><input class="form-control"  type="text" id="address_line1" name="address_line1" value="" disabled></td>

                            <th class="heading1">City Name</th>
                            <td><input class="form-control" type="text" id="city_name" name="city_name" value="" disabled></td>
                        </tr>

                        <tr>
                            <th class="heading1">First EmailID</th>
                            <td><input class="form-control" type="text" id="email_id1" name="email_id1" value="" disabled></td>

                            <th class="heading1">Mobile No1</th>
                            <td><input class="form-control" type="text" id="mobile_no1" name="mobile_no1" value="" onkeyup="myFun(id)" maxlength="10" disabled></td>

                            <th class="heading1">Landline No1</th>
                            <td><input class="form-control" type="text" id="landline_no1" name="landline_no1" value="" disabled></td>
                        </tr>
                        <tr>



                        </tr>
                        <tr>


                        </tr>

                        <tr>
                            <th class="heading1">Latitude</th><td><input class="form-control" type="text" id="latitude" name="latitude" value="" size="20" disabled></td>
                            <th class="heading1">Longitude</th><td><input  class="form-control" type="text" id="longitude" name="longitude" value="" size="20" disabled></td>
                            <th class="heading1" nowrap>Parent Org Office</th>
                            <td><input class="form-control" type="text" id="serialnumber" name="serialnumber"  value="${serialnumber}" disabled></td>
                        </tr>



                        <tr>


                        </tr>

                        <tr>
                            <td><input class="form-control" type="text" id="generation" name="generation" value="" size="45" disabled hidden></td>
                        </tr>

                        <%-- These hidden fields "lowerLimit", "noOfRowsTraversed", and "clickedButton" belong to form2 of table2. --%>
                        <input type="hidden" name="lowerLimit" value="${lowerLimit}">
                        <input type="hidden" name="noOfRowsTraversed" value="${noOfRowsTraversed}">
                        <input type="hidden" id="clickedButton" value="">
                        <input type="hidden" id="org_name" name="org_name" value="${org_name}">                                       
                        <input type="hidden" id="clickedButton" value="">
                    </table>
                </form>












            </div>          


            <div class="container" >

                <form name="form0" class="form-group row boxeffext" method="POST" action="personCount.do">
                    <table  class="table  table-striped ">
                        <tr>

                            <th>  Person Name</th>
                            <td><input class="form-control" type="text" id="searchKeyPerson" name="searchKeyPerson" value="${searchKeyPerson}"  size="20" disabled></td>
                            <td><input type="submit" class="btn btn-primary" id="search_org" name="search_org" value="Select" onclick="setStatus(id)"></td>
                             <td><input type="button" class="btn btn-info" id="viewPdf" name="PRINT" value="PDF" onclick="displayKpList(id)"></td>

                            <td><label id="org_msg">  </label></td>

                        </tr>
                    </table>


                </form>
            </div>

            <div class="container hidden" id="personshow">
                <form name="form2" method="POST" class="form-group row boxeffext" action="personCount.do" encType="multipart/form-data"  onsubmit="return verify()">
                    <table id="table2"  class="table table-bordered table-striped" border="0"  align="center" style="width: 100%">
                        <tr id="message">
                            <c:if test="${not empty message}">
                                <td colspan="2" bgcolor="${msgBgColor}"><b>Result: ${message}</b></td>
                            </c:if>
                        </tr>

                        <tr>


                        <input type="hidden" id="key_person_id" name="key_person_id" value="">
                        <input type="hidden" id="general_image_details_id" name="general_image_details_id" value="" size="28"   />
                        <input type="hidden" id="i" name="i" value=${i}>

                        
                        <th class="heading1" >Office Name</th>
                        <td><input class="form-control" type="text" id="org_office_name2" name="org_office_name2"  size="28"  disabled></td>
                        <th class="heading1" >Employee Id</th>
                        <td><input class="form-control" type="text" id="employeeId" name="employeeId" value=""  size="28" disabled></td>

                        </tr>
                        <tr>


                            <th class="heading1" >Person Name</th>
                            <td><input class="form-control" type="text" id="key_person_name" name="key_person_name" value=""  size="30" disabled></td>
                            <th class="heading1" >Password</th>
                            <td><input class="form-control" type="password" id="password" name="password" value=""  size="30" disabled></td>
                            <th class="heading1" >Father's Name</th>
                            <td><input class="form-control" type="text" id="father_name" name="father_name" value=""  size="30" disabled></td>
                        </tr>
                        <tr>

                            <th class="heading1" >Blood Group</th>
                            <td>  <select class="ui dropdown form-control" name="blood" id="blood" disabled>
                                    <option>---Select--- </option>
                                    <option value="A+">A+</option>
                                    <option value="A-">A-</option>
                                    <option value="B+">B+</option>
                                    <option value="B-">B-</option>
                                    <option value="O+">O+</option>
                                    <option value="O-">O-</option>
                                    <option value="AB+">AB+</option>
                                    <option value="AB-">AB-</option>
                                </select></td>

                            <th class="heading1" >Date Of Birth</th>
                            <td><input class="form-control" type="date" id="date_of_birth" name="date_of_birth" value=""  size="5" disabled></td>
                            <th class="heading1" >Designation</th>
                            <td><input class="form-control" type="text" id="designation" name="designation" value=""  size="30" disabled></td>
                            <!--                                                <th class="heading1" >Relation</th>
                                                                                <td><input class="form-control" type="text" id="relation" name="relation" value=""  size="30" disabled></td>-->
                        </tr>
                        <tr>


                        </tr>
                        <tr>


                        </tr>



                        <tr class="exp">

                            <th class="heading1" >Emergency Contact Name</th>
                            <td><input class="form-control" type="text" id="emergency_name" name="emergency_name" value="" size="28"  disabled></td> 
                            <th class="heading1" >Emergency Contact Number</th>
                            <td><input class="form-control" type="text" id="emergency_number" name="emergency_number" value="" size="28"  disabled>

                            </td> 



                        </tr>

                        </button>


                        </td>

                        </tr>





                        <tr>
                            <th class="heading1" >ID Type</th>
                            <td><input class="form-control" type="text" id="id_type " name="id_type " value=""  size="30"  disabled></td>

                            <th class="heading1" >ID No.</th>
                            <td><input class="form-control" type="text" id="id_no" name="id_no" value=""  size="30"  disabled></td>

                        </tr>

                        <tr>
                            <th class="heading1">Gender</th>
                            <td><input class="form-control" type="text" id="gender" name="gender" value=""  size="28"  disabled></td>
                            <th class="heading1" >Address Line1</th>
                            <td><input class="form-control" type="text" id="address" name="address" value=""  size="28"  disabled></td>
                            <th class="heading1" >City</th>
                            <td><input class="form-control" type="text" id="city" name="city"  size="25" value="" disabled></td>
                        </tr>

                        <tr>                                                    

                            <th class="heading1" >Mobile No. 1st</th>
                            <td><input class="form-control" type="text" id="mobile" name="mobile" value=""  size="30" onkeyup="myFun(id)" maxlength="10"  disabled></td>
                            <th class="heading1" >LandLine No. 1st</th>
                            <td><input class="form-control" type="text" id="landline" name="landline" value=""  size="25"  disabled></td>
                            <th class="heading1" >Email ID 1st</th>
                            <td><input class="form-control" type="text" id="email" name="email" value=""  size="30"  disabled></td>
                        </tr>
                        <tr>                                                    
                            <th class="heading1">Family Office</th><td><input class="form-control" type="text" id="famoffice" name="famoffice" value="" size="20" disabled></td>
                            <th class="heading1">Family Designation</th><td><input  class="form-control" type="text" id="famdesignation" name="famdesignation" value="" size="20" disabled></td>
                            <th class="heading1">Relation</th><td><input  class="form-control" type="text" id="relation" name="relation" value="" size="20" disabled></td>

                        </tr>


                        <tr>
                            <th class="heading1">Latitude</th><td><input class="form-control" type="text" id="latitude2" name="latitude2" value="" size="20" disabled></td>
                            <th class="heading1">Longitude</th><td><input  class="form-control" type="text" id="longitude2" name="longitude2" value="" size="20" disabled></td>
                        </tr>


                        <tr>
                        <input type="hidden" name="familyoffice" id="familyoffice" value="${noOfRowsTraversed}">
                        <input type="hidden" name="relation" id="relation" value="${noOfRowsTraversed}">
                        <input type="hidden" name="familydesignation" id="familydesignation" value="${noOfRowsTraversed}">
                        </tr>
                        <tr>
                            <td align='center' colspan="10">
                                <input type="button" class="btn btn-primary" name="edit" id="edit" value="Edit" onclick="makeEditable(id)" disabled>
                                <input type="submit" class="btn btn-success" name="task" id="save" value="Save" onclick="setStatus(id)" disabled>
                                <input type="submit" class="btn btn-info" name="task" id="save_As" value="Save AS New" onclick="setStatus(id)" disabled>
                                <input type="reset" class=" btn btn-warning" name="new" id="new" value="New" onclick="makeEditable(id)" >
                                <input type="submit" class="btn btn-success" name="task" id="delete" value="Delete" onclick="setStatus(id)" disabled>
                            </td>
                        </tr>
                        <%-- These hidden fields "lowerLimit", "noOfRowsTraversed", and "clickedButton" belong to form2 of table2. --%>
                        <input type="hidden" name="lowerLimit" value="${lowerLimit}">
                        <input type="hidden" name="noOfRowsTraversed" value="${noOfRowsTraversed}">

                        <input type="hidden" id="clickedButton" value="">
                        <input  type="hidden" id="searchOrgType" name="searchOrgType" value="${searchOrgType}" >
                    </table>
                </form>
            </div>




        </DIV>


        <%@include file="/layout/footer.jsp" %>
    </body>
</html>
