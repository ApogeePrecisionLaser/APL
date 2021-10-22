<%-- 
    Document   : organisation_type
    Created on : Dec 9, 2011, 2:46:00 PM
    Author     : Soft_Tech
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Data Entry: KeyPerson </title>
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
           
      
           $(function () {  
                 
              $("#org_office_name").autocomplete({
               
            source: function (request, response) {
                var officecode=document.getElementById("office_code").value;               
                var random = document.getElementById("org_office_name").value;      
        $.ajax({
                    url: "personCount.do",
                    dataType: "json",
                    data: {action1: "getOrgOfficeName",
                           action2: officecode,str:random},
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
                   var random = document.getElementById("searchOfficeCode") .value;            
                $.ajax({
                    url: "personCount.do",
                    dataType: "json",
                    data: {action1: "searchOrgOfficeCode",str:random},
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
                   var random = document.getElementById("searchOrg") .value;            
                $.ajax({
                    url: "personCount.do",
                    dataType: "json",
                    data: {action1: "searchOrg",str:random},
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
                    var random = document.getElementById("searchEmpCode") .value;             
                $.ajax({
                    url: "personCount.do",
                    dataType: "json",
                    data: {action1: "getOrgOfficeCode",str:random},
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
                 var random = document.getElementById("office_code") .value;
                $.ajax({
                    url: "personCount.do",
                    dataType: "json",
                    data: {action1: "getOrgOfficeCode",str:random},
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
                 var random = document.getElementById("city_name") .value;
                $.ajax({
                    url: "personCount.do",
                    dataType: "json",
                    data: {action1: "getCityName",str:random},
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
                var random = document.getElementById("designation") .value;
                  var org_office_name=document.getElementById("org_office_name").value;        
                $.ajax({
                    url: "personCount.do",
                    dataType: "json",
                    data: {action1: "getDesignation",str:random,action2:org_office_name},
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
                var random = document.getElementById("searchmobile") .value;
                $.ajax({
                    url: "personCount.do",
                    dataType: "json",
                    data: {action1: "getMobile",str:random},
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
                 var random = document.getElementById("searchDesignation") .value;
                $.ajax({
                    url: "personCount.do",
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
             
//                var code = document.getElementById("searchOfficeCode").value;
                 var random = document.getElementById("searchKeyPerson") .value;
                $.ajax({
                    url: "personCount.do",
                    dataType: "json",
                    data: {action1: "getSearchKeyPerson",
                          str:random},
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
                 var random = document.getElementById("searchfamily") .value;
                $.ajax({
                    url: "personCount.do",
                    dataType: "json",
                    data: {action1: "getfamilycode",
                          str:random},
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
   
   
   
     
  
 
   
   
   
            function makeEditable(id) {               
                document.getElementById("office_code").disabled = false;
                document.getElementById("org_office_name").disabled = false;
                document.getElementById("salutation").disabled = false;
                document.getElementById("employeeId").disabled = false;
                document.getElementById("key_person_name").disabled = false;
                document.getElementById("password").disabled = false;
                document.getElementById("blood").disabled = false;
                document.getElementById("emergency_name").disabled = false;
                document.getElementById("emergency_number").disabled = false;
                document.getElementById("designation").disabled = false;
                document.getElementById("city_name").disabled = false;
              
                document.getElementById("address_line1").disabled = false;
                document.getElementById("address_line2").disabled = false;
                document.getElementById("address_line3").disabled = false;
                
              //   document.getElementById("id_type").disabled = false;
             //     document.getElementById("id_no").disabled = false;
//                
                document.getElementById("mobile_no1").disabled = false;
                document.getElementById("mobile_no2").disabled = false;
                document.getElementById("landline_no1").disabled = false;
                document.getElementById("landline_no2").disabled = false;
                document.getElementById("email_id1").disabled = false;
                document.getElementById("email_id2").disabled = false;
                document.getElementById("latitude").disabled = false;
                document.getElementById("longitude").disabled = false;
                document.getElementById("design_name").disabled=false;
                document.getElementById("father_name").disabled=false;
                document.getElementById("date_of_birth").disabled=false;
                document.getElementById("id_proof").disabled=false;
                 document.getElementById("emerg").disabled = false;  
                   document.getElementById("delete").disabled = false;
                if(id == 'new') {
                    
                    $("#message").html("");
                    document.getElementById("key_person_id").value = "";
                    document.getElementById("edit").disabled = true;
                    document.getElementById("delete").disabled = true;
                    document.getElementById("save_As").disabled = true;
                     document.getElementById("get_cordinate").disabled = false;
                                      document.getElementById("id_type").disabled = false;
                                  document.getElementById("id_no").disabled = false;    
                document.getElementById("emerg").disabled = false;  
                 document.getElementById("genderm").disabled = false;
                 document.getElementById("genderf").disabled = false;
                    setDefaultColordOrgn(document.getElementById("noOfRowsTraversed").value, 25);
                    //document.getElementById("office_type").focus();
                }
                if(id == 'edit'){
                    document.getElementById("delete").disabled = false;
                    document.getElementById("save_As").disabled = false;
                     document.getElementById("get_cordinate").disabled = false;
                     document.getElementById("id_type").disabled = false;
                                  document.getElementById("id_no").disabled = false;   
                                   document.getElementById("emerg").disabled = false;   
                                    document.getElementById("genderf").disabled = false;
                 document.getElementById("genderm").disabled = false;
                    document.getElementById("relation").focus();
                }
                document.getElementById("save").disabled = false;
            }
            function setDefaultColordOrgn(noOfRowsTraversed, noOfColumns) {
                //alert(noOfColumns);
                for(var i = 0; i < noOfRowsTraversed; i++) {
                    for(var j = 1; j <= noOfColumns; j++) {
                        document.getElementById("t1c" + (i * noOfColumns + j)).bgColor = "";     // set the default color.
                    }
                }
            }

            function fillColumnKeyPerson(id) {
                   debugger;
                //  var noOfRowsTraversed = document.getElementById("noOfRowsTraversed").value;
                var noOfRowsTraversed = document.getElementById("noOfRowsTraversed").value;
                //                alert("get total count rows"+ noOfRowsTraversed);
                var noOfColumns = 35;
                var columnId = id;
                //                alert("Get  Column id of the Row "+ columnId);<%-- holds the id of the column being clicked, excluding the prefix t1c e.g. t1c3 (column 3 of table 1). --%>
                columnId = columnId.substring(3, id.length);    <%-- for e.g. suppose id is t1c3 we want characters after t1c i.e beginIndex = 3. --%>
                //                alert("After getting the column  " +columnId );
                var lowerLimit, higherLimit, rowNo = 0;
                var noOfRows;
                for(var i = 0; i < noOfRowsTraversed; i++) {
                    noOfRows = i;
                    lowerLimit = i * noOfColumns + 1;       // e.g. 11 = (1 * 10 + 1)
                    //                    alert("lower limit of row " +lowerLimit);
                    higherLimit = (i + 1) * noOfColumns;
                    //                    alert("higher limit of row "+higherLimit)// e.g. 20 = ((1 + 1) * 10)
                    rowNo++;
                    if((columnId >= lowerLimit) && (columnId <= higherLimit))
                        break;
                }
                //                alert("noOfRows: " + ++noOfRows);

                var lower= lowerLimit;
                // alert(lower);
                var upper= higherLimit;
                setDefaultColordOrgn(noOfRowsTraversed, noOfColumns);// set default color of rows (i.e. of multiple coloumns).
                //                alert("Total number of column in the set the valeu   "+  noOfColumns);
                var t1id = "t1c";       // particular column id of table 1 e.g. t1c3.
                //var t2id = "t2c";
                document.getElementById("key_person_id").value= document.getElementById("key_person_id"+rowNo).value;
                // document.getElementById("office_type").value = document.getElementById(t1id +(lower+1)).innerHTML
                document.getElementById("office_code").value = document.getElementById(t1id +(lower+2)).innerHTML
                //  document.getElementById("office_type").value = document.getElementById(t1id +(lower+3)).innerHTML
                document.getElementById("org_office_name").value = document.getElementById(t1id +(lower+4)).innerHTML
                document.getElementById("employeeId").value = $.trim(document.getElementById(t1id +(lower+5)).innerHTML);
                /*var role=  $.trim(document.getElementById(t1id +(lower+5)).innerHTML);
                $("#salutation option" ).each(function()
                {
                    if($(this).val() == role){
                        $(this).attr('selected', true);
                    }
                });*/
                document.getElementById("salutation").value = $.trim(document.getElementById(t1id +(lower+6)).innerHTML);
                document.getElementById("key_person_name").value = document.getElementById(t1id +(lower+7)).innerHTML;
                
                 document.getElementById("password").value = document.getElementById(t1id +(lower+8)).innerHTML;
                 
                document.getElementById("father_name").value = document.getElementById(t1id +(lower+9)).innerHTML;
                
               document.getElementById("blood").value = document.getElementById(t1id +(lower+10)).innerHTML;
                
                if(document.getElementById(t1id + (lower + 11)).innerHTML=='M')
                 {
                   document.getElementById("genderm").checked = document.getElementById(t1id + (lower + 11)).innerHTML;
                    document.getElementById("genderf").unchecked = document.getElementById(t1id + (lower + 11)).innerHTML;
               } 
               else
               {
                    document.getElementById("genderf").checked = document.getElementById(t1id + (lower + 11)).innerHTML;
                      document.getElementById("genderm").unchecked = document.getElementById(t1id + (lower + 11)).innerHTML;
               }
                  
                  
                  
                document.getElementById("date_of_birth").value = document.getElementById(t1id +(lower+12)).innerHTML;
                document.getElementById("designation").value = document.getElementById(t1id +(lower+13)).innerHTML;
                document.getElementById("address_line1").value = document.getElementById(t1id +(lower+14)).innerHTML;
                document.getElementById("address_line2").value = document.getElementById(t1id +(lower+15)).innerHTML;
                document.getElementById("address_line3").value = document.getElementById(t1id +(lower+16)).innerHTML;
                
                 document.getElementById("emergency_name").value = document.getElementById(t1id +(lower+17)).innerHTML;
                 document.getElementById("emergency_number").value = document.getElementById(t1id +(lower+18)).innerHTML;
                 
                document.getElementById("id_type").value = document.getElementById(t1id +(lower+19)).innerHTML;
                document.getElementById("id_no").value = document.getElementById(t1id +(lower+20)).innerHTML;
                
                document.getElementById("city_name").value = document.getElementById(t1id +(lower+21)).innerHTML;
               // document.getElementById("state_name").value = document.getElementById(t1id +(lower+15)).innerHTML;
                document.getElementById("mobile_no1").value = document.getElementById(t1id +(lower+22)).innerHTML;
                document.getElementById("mobile_no2").value = document.getElementById(t1id +(lower+23)).innerHTML;
                document.getElementById("landline_no1").value = document.getElementById(t1id +(lower+24)).innerHTML;
                document.getElementById("landline_no2").value = document.getElementById(t1id +(lower+25)).innerHTML;
                document.getElementById("email_id1").value = document.getElementById(t1id +(lower+26)).innerHTML;
                document.getElementById("email_id2").value = document.getElementById(t1id +(lower+27)).innerHTML;
                
                
                 document.getElementById("latitude").value=document.getElementById(t1id + (lower + 28)).innerHTML;
                document.getElementById("longitude").value=document.getElementById(t1id + (lower + 29)).innerHTML;
                document.getElementById("general_image_details_id").value = document.getElementById(t1id +(lower+30)).innerHTML;
               
                for(var i = 0; i <= 34; i++) {

                    document.getElementById(t1id + (lower + i)).bgColor = "#d0dafd";        // set the background color of clicked row to yellow.
                }
                document.getElementById("edit").disabled = false;
                if(!document.getElementById("save").disabled)   // if save button is already enabled, then make edit, and send button enabled too.
                {
                    document.getElementById("save_As").disabled = true;
                    document.getElementById("delete").disabled = false;
                }
                $("#message").html("");
            }
            function setStatus(id) {
                if(id == 'save'){
                    document.getElementById("clickedButton").value = "Save";
                }
                else if(id == 'save_As'){
                    document.getElementById("clickedButton").value = "Save AS New";
                }
                else document.getElementById("clickedButton").value = "Delete";;
            }
            function myLeftTrim(str) {
                var beginIndex = 0;
                for(var i = 0; i < str.length; i++) {
                    if(str.charAt(i) == ' ')
                        beginIndex++;
                    else break;
                }
                return str.substring(beginIndex, str.length);
            }
            function verify() {
                var result;
                if(document.getElementById("clickedButton").value == 'Save' || document.getElementById("clickedButton").value == 'Save AS New') {
                    var city_name = document.getElementById("city_name").value;
                   // var state_name = document.getElementById("state_name").value;
                                    
                    if(myLeftTrim(document.getElementById("office_code").value).length == 0) {
                        $("#message").html("<td colspan='8' bgcolor='coral'><b>Office Code is required...</b></td>");
                        document.getElementById("office_code").focus();
                        return false; // code to stop from submitting the form2.
                    }
                    if(myLeftTrim(document.getElementById("org_office_name").value).length == 0) {
                        $("#message").html("<td colspan='8' bgcolor='coral'><b>Organisation Office Name is required...</b></td>");
                        document.getElementById("org_office_name").focus();
                        return false; // code to stop from submitting the form2.
                    }
                    if(myLeftTrim(document.getElementById("employeeId").value).length == 0) {
                        $("#message").html("<td colspan='8' bgcolor='coral'><b>Person Id is required...</b></td>");
                        document.getElementById("employeeId").focus();
                        return false; // code to stop from submitting the form2.
                    }
                    if(myLeftTrim(document.getElementById("salutation").value)=='---Select---') {
                       $("#message").html("<td colspan='8' bgcolor='coral'><b>Salutation is required...</b></td>");
                        document.getElementById("salutation").focus();
                       return false; // code to stop from submitting the form2.
                    }
                    
                    if(myLeftTrim(document.getElementById("key_person_name").value).length == 0) {
                        $("#message").html("<td colspan='8' bgcolor='coral'><b>Key Person Name is required...</b></td>");
                        document.getElementById("key_person_name").focus();
                        return false; // code to stop from submitting the form2.
                    }
                    if(myLeftTrim(document.getElementById("designation").value).length == 0) {
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
                    if(myLeftTrim(city_name).length == 0) {
                        $("#message").html("<td colspan='8' bgcolor='coral'><b>City Name is required...</b></td>");
                        document.getElementById("city_name").focus();
                        return false; // code to stop from submitting the form2.
                    }
                    if(myLeftTrim(document.getElementById("address_line1").value).length == 0) {
                        $("#message").html("<td colspan='8' bgcolor='coral'><b>Address_line1 is required...</b></td>");
                        document.getElementById("address_line1").focus();
                        return false; // code to stop from submitting the form2.
                    }
                    if(myLeftTrim(document.getElementById("mobile_no1").value).length == 0) {
                        $("#message").html("<td colspan='8' bgcolor='coral'><b>Mobile_no1 is required...</b></td>");
                        document.getElementById("mobile_no1").focus();
                        return false; // code to stop from submitting the form2.2.
                    }if(myLeftTrim(document.getElementById("email_id1").value).length == 0) {
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
                    if(result == false)
                    {
                        // if result has value false do nothing, so result will remain contain value false.
                    }
                    else{
                        
                        result = true;
                    }
                    if(document.getElementById("clickedButton").value == 'Save AS New'){
                        result = confirm("Are you sure you want to save it as New record?")
                        return result;
                    }
                } else result = confirm("Are you sure you want to delete this record?")
                return result;
            }

            
            function displayOrgnList(id){
                var queryString;
                var organisation=document.getElementById("searchOrg").value;
                var searchKeyPerson =document.getElementById("searchKeyPerson").value;
                var searchmobile =document.getElementById("searchmobile").value;
                   var active = document.getElementById("activee").value;
                var searchfamily = document.getElementById("searchfamily").value;
                
              // alert(designation);
                if(id == "viewPdf")
                    queryString = "task1=PRINTRecordList&searchOrg="+organisation+"&searchKeyPerson="+searchKeyPerson+"&searchmobile="+searchmobile+"&active="+active+"&searchfamily="+searchfamily;
                else
                    queryString = "task1=PRINTExcelList&searchOrg="+organisation+"&searchKeyPerson="+searchKeyPerson+"&searchmobile="+searchmobile+"&active="+active+"&searchfamily="+searchfamily;
                //alert(queryString);
                var url = "personCount.do?" + queryString;
                popupwin = openPopUp(url, "Division List", 600, 900);
            }


            function openPopUp(url, window_name, popup_height, popup_width) {
                var popup_top_pos = (screen.availHeight / 2) - (popup_height / 2);
                var popup_left_pos = (screen.availWidth / 2) - (popup_width / 2);
                var window_features = "left=" + popup_left_pos + ", top=" + popup_top_pos + ", width=" + popup_width + ", height=" + popup_height + ", resizable=no, scrollbars=yes, status=no, dialog=yes, dependent=yes";
                return window.open(url, window_name, window_features);
            }
            if (!document.all) {
                document.captureEvents (Event.CLICK);
            }
            document.onclick = function() {
                if (popupwin != null && !popupwin.closed) {
                    popupwin.focus();
                }
            }
            function viewDemandNote(id, img){
                //alert(id);
                //var emp_code= document.getElementById("emp_code1"+id).value;
                var queryString = "task1=viewImage&kp_id="+id+"&type="+img;
                // alert(queryString);
                var url = "personCount.do?" + queryString;
                popupwin = openPopUp(url, "Show Image", 600, 900);
            }
            function  codeIsEmpty()
            {
                var office_code = document.getElementById("searchOfficeCode").value;

                if(myLeftTrim(office_code).length == 0) {
                    // document.getElementById("message").innerHTML = "<td colspan='8' bgcolor='coral'><b>Organisation Name is required...</b></td>";
                    $("#message").html( "<td colspan='8' bgcolor='coral'><b>Office Code is required...</b></td>");
                    document.getElementById("office_code").focus();

                }else{
                    $("#message").html("");
                }
            }
            function  EmpCodeIsEmpty()
            {
                var emp_code = document.getElementById("searchEmpCode").value;

                if(myLeftTrim(emp_code).length == 0) {
                    // document.getElementById("message").innerHTML = "<td colspan='8' bgcolor='coral'><b>Organisation Name is required...</b></td>";
                    $("#message").html( "<td colspan='8' bgcolor='coral'><b>Employee Id is required...</b></td>");
                    document.getElementById("emp_code").focus();

                }else{
                    $("#message").html("");
                }
            }
            function readURL(input) {
                document.getElementById("image_perview").style.visibility='visible';
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
            function changeClass(){
                var language=document.getElementById("language").value;
                if(language=='English'){
                    $( "#status_type" ).addClass('input').removeClass('new_input');
                    $( "#remark" ).addClass('input').removeClass('new_input');
                }
                else{
                    $( "#status_type" ).addClass('new_input').removeClass('input');
                    $( "#remark" ).addClass('new_input').removeClass('input');
                }
            }
            
            
               function openMapForCord() {
                var url="generalCont.do?task=GetCordinates4";//"getCordinate";
                popupwin = openPopUp(url, "",  600, 630);
            }
            
            
            function openPopUp(url, window_name, popup_height, popup_width) {
                var popup_top_pos = (screen.availHeight / 2) - (popup_height / 2);
                var popup_left_pos = (screen.availWidth / 2) - (popup_width / 2);
                var window_features = "left=" + popup_left_pos + ", top=" + popup_top_pos + ", width=" + popup_width + ", height=" + popup_height + ", resizable=yes, scrollbars=yes, location=0, menubar=no, status=no, dependent=yes";

                return window.open(url, window_name, window_features);
            }
            
            function emergency(id)
            {
                 var url="personCount.do?task1=showEmergency&keyperson_id="+id;
                popupwin = openPopUp(url, "",  600, 630);
            }
            
            
            
            
            function openMap(id) {
                //alert(vehicle_key_person_map_id);
                var url="personCount.do?task1=showMapWindow&keyperson_id="+id;
               
                popupwin = openPopUp(url, "",  580, 620);
           
    }
    
       function showpersonal(id) {
             
             $("#officeinfo"+id).hide();
              $("#personalinfo"+id).toggle();
           
    }
    function showoffice(id) {
               $("#personalinfo"+id).hide();
              $("#officeinfo"+id).toggle();
           
    }
         
        function showfilter() {
               $("#filters").toggle();
             
           
    } 
         

          function myFun(value)
{ 
    debugger;
        var req = null;
    if (req != null) req.abort();
       var random = document.getElementById("mobile_no1").value;
     //  alert(random);
  
     if(random.length>=10)
     {
         
    req = $.ajax({
        type: "POST",
        url: "personCount.do",
      data: {action1:"mobile_number",
             str:random},
        dataType: "json",
        success: function (response_data) {
  
                        console.log(response_data);
                        
                        
                        if(response_data.list[0] == ("Mobile no exist"))
                        {
                            alert(response_data.list[0]);
                            document.getElementById("mobile_no1").value="";
                        }
                        
                      //  response(response_data.list);
                    }, error: function (error) {
                        console.log(error.responseText);
                        
                        response(error.responseText);
                    }


    });
}

    }      
    
    
    
//    $(document).ready(function() {
//    $('#add').click(function(){ 
////        $('#emergency').append(' <tr> <td>Emergency Contact Name</td> <td>:</td> <td colspan="2"><input size="20" type="text" /></td> <td>Emergency Contact Numbe</td> <td>:</td> <td><input type="text" /></td> </tr>');
//            $('#emergency').append(' <tr> <td>Emergency Contact Name</td> <td>:</td> <td colspan="2"><input size="20" class="form-control" type="text" /></td> <td>Emergency Contact Numbe</td> <td>:</td> <td><input type="text"  class="form-control"/></td> </tr>');
//}); 
//});
    var i=0;
    $(document).ready(function() {
    var max_fields = 10;
    var wrapper = $(".container1");
    var add_button = $(".add_form_field");
    
    
    var x = 1;
    var em_name;
    $(add_button).click(function(e) {
        i++;
       
        e.preventDefault();
        if (x < max_fields) {
            x++;
            $(wrapper).append('<tr> <th class="heading1">Emergency Contact Name'+i+'</th> <td><input class="form-control" type="text" id="emergency_name'+i+'" name="emergency_name'+i+'" value="" ></td> <th class="heading1" >Emergency Contact Number'+i+'</th> <td><input class="form-control" type="text" id="emergency_number'+i+'" name="emergency_number'+i+'" value="" > </td> <tr><a href="#" class="delete">Delete</a></tr></tr>'); //add input box
        } else {
            alert('You Reached the limits')
        }
        document.getElementById("i").value=i;
    });
    
debugger;
    $(wrapper).on("click", ".delete", function(e) {
        e.preventDefault();
        $(this).parent('td').remove();
        x--;
    })
});
    
      function setDefaultValues(id){
            var result_type=   document.getElementById(id).checked;
            var default_mobile_no="9999999999";
            var default_email_id="abc@xyz.com";
            var default_address= "ABC";
            var latitude = "0.0";
            var longitude = "0.0";
            if(result_type){
                $("#mobile_no1").val(default_mobile_no);
                $("#email_id1").val(default_email_id);
                $("#address_line1").val(default_address);
                  $("#latitude").val(latitude);
                  $("#longitude").val(longitude);
            }else{
                $("#mobile_no1").val(" ");
                $("#email_id1").val("");
                $("#address_line1").val(" ");
                 $("#latitude").val("");
                   $("#longitude").val("");
            }

        }

    
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
           
           .boxeffext2{
               background-color: white;
               box-shadow:-2px 3px 40px 16px rgba(0,0,0,0.47);
             
               margin-top: 6%;
           }
           .btn-secondary{
               background-color: #E7475E;
               border-color: #E7475E;
               color:white !important;
               font-weight: 600;
               font-size: 15px;
               
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
           
           
           
           
           
           
           
           .maintr
           {
               
               padding-top: 50px;
           }
            
           .hidden{
       display:none;
    }
        </style>    
    </head>
    <body>
         <div class="container-fluid">
              <!--DWLayoutDefaultTable-->
            <%@include file="/layout/header.jsp" %>
                             <div class="container  topbox">
                                 <h1>KeyPerson</h1>
                             </div>
                             <button class="btn btn-secondary" onclick="showfilter()" style="float:right"> 
            <svg xmlns="http://www.w3.org/2000/svg" x="0px" y="0px"
width="140" height="140"
viewBox="0 0 172 172"
style=" fill:#000000;">
                 <text x="35" y="15" style="fill:white;font-size:20px;">Show Filters</text>
                
                <g fill="none" fill-rule="nonzero" stroke="none" stroke-width="1" stroke-linecap="butt" stroke-linejoin="miter" stroke-miterlimit="10" stroke-dasharray="" stroke-dashoffset="0" font-family="none" font-weight="none" font-size="none" text-anchor="none" style="mix-blend-mode: normal"><path d="M0,172v-172h172v172z" fill="none"></path><g fill="#ffffff"><path d="M103.91667,82.41667h-35.83333l-43,-50.16667h121.83333zM103.91667,136.16667l-35.83333,21.5v-75.25h35.83333zM148.70833,32.25h-125.41667c-2.86667,0 -5.375,-2.50833 -5.375,-5.375v0c0,-2.86667 2.50833,-5.375 5.375,-5.375h125.41667c2.86667,0 5.375,2.50833 5.375,5.375v0c0,2.86667 -2.50833,5.375 -5.375,5.375z"></path></g></g>
            </svg>
</button>
                                                <div class="container hidden" id="filters">
                                    <form name="form0" class="form-group row boxeffext" method="POST" action="personCount.do">
                                        <table  align="center"  class="table table-bordered table-striped" width="600">
                                            <tr>
                                             
                                                       <th>  Organisation Name</th>
                                                    <td><input class="form-control" type="text" id="searchOrg" name="searchOrg" value="${searchOrg}" size="20"></td>
                                                    <!--
                                                    <th> Employee Id </th>
                                                    <td>    <input class="form-control" type="text" id="searchEmpCode" name="searchEmpCode" value="${searchEmpCode}" size="20"></td>
                                                -->
                                           
                                           
                                                
                                            <th>  Person Name</th>
                                            <td><input class="form-control" type="text" id="searchKeyPerson" name="searchKeyPerson" value="${searchKeyPerson}"  size="20"></td>
<!--                                                <th>  Designation</th>
                                                <td><input class="form-control" type="text" id="searchDesignation" name="searchDesignation" value="${searchDesignation}" size="20">
                                                </td>-->
                                            <th>  Mobile Number</th>
                                                <td><input class="form-control" type="text" id="searchmobile" name="searchmobile" value="${searchmobile}" size="20">
                                                </td>
                                            </tr>
                                            <tr>
                                                  <th>Office Code</th>
                                                <td><input class="form-control" type="text" id="searchfamily" name="searchfamily" value="${searchfamily}" size="20">
                                                <td>
                                                    <input type="submit" class="btn btn-primary" name="task" id="search" value="Search"></td>
                                                <td>   <input type="submit" class="btn btn-success" name="task1" id="showAllRecords" value="Show All Records"></td>
                                                <td>    <input class="btn btn-danger" type="button" id="viewPdf" name="viewPdf" value="PDF" onclick="displayOrgnList(id)"></td>
                                                <td>   <input class="btn btn-warning" type="button" id="viewExcel" name="viewExcel" value="Excel" onclick="displayOrgnList(id)"></td>
                                                <input type="hidden" id="activee" name="active" value="${active}">
                                                <td>   <label id="org_msg">  </label></td>
                                            </tr>
                                        </table>
                                                
                                          <div class="panel-group container">
    <div class="panel panel-success">
<!--      <div class="panel">Panel with panel-default class</div>-->
      <div class="panel-body">
          <table align="center"  class="table table-bordered table-striped" width="600" class="table table-bordered table-responsive">
              <tr>  
<!--                  <td align="center"><input class="btn btn-success" type="submit"  name="task" id="active" value="ACTIVE RECORDS"></td>
                  <td align="center"><input class="btn btn-info" type="submit" name="task" id="inactive" value="INACTIVE RECORDS"></td>
                  <td align="center"><input class="btn btn-primary" type="submit" name="task" id="all" value="ALL RECORDS"></td>-->
             <td>  <select class="ui dropdown form-control" name="task2" id="hiera" >
                                                              <option>${ac}(CURRENTLY)</option>
                                                                  <option value="ACTIVE RECORDS">ACTIVE RECORDS</option>
                                                                  <option value="INACTIVE RECORDS">INACTIVE RECORDS</option>
                                                               <option value="ALL RECORDS">ALL RECORDS</option>
                                                                      
                                                         </select></td>  
                                                            <td><input type="submit" class="btn btn-primary" id="hiera" name="search_org" value="SEARCH RECORDS" onclick="setStatus(id)"></td>
      </tr>
          </table>
      </div>
    </div>  
                           </div> 
                                                
                                                
                                                
                                    </form>
                                                </div>
                              
                                       
                                                <DIV class="container">
                                    <form name="form1" method="POST" action="personCount.do" class="boxeffext2">
                                        
<!--                                            <table id="table1" width="600"  border="1"  align="center" class="table table-bordered table-striped boxeffext">-->
                                               
                                                <c:forEach var="key" items="${requestScope['keyList']}"  varStatus="loopCounter">
<!--                                                    <tr  class="${loopCounter.index % 2 == 0 ? 'even': 'odd'}" >
                                                        <td id="t1c${IDGenerator.uniqueID}" onclick="fillColumnKeyPerson(id)" align="center">
                                                            <input type="hidden" id="key_person_id${loopCounter.count}" value="${key.key_person_id}">${lowerLimit - noOfRowsTraversed + loopCounter.count}
                                                        </td>
                                                        <td id="t1c${IDGenerator.uniqueID}" class="new_input" onclick="fillColumnKeyPerson(id)" style="width: 400px" width="400px">${key.organisation_name}</td>
                                                        <td id="t1c${IDGenerator.uniqueID}" onclick="fillColumnKeyPerson(id)" style="width: 400px" width="400px">${key.org_office_code}</td>
                                                        <td id="t1c${IDGenerator.uniqueID}" onclick="fillColumnKeyPerson(id)" style="width: 400px" width="400px">${key.office_type}</td>
                                                        <td id="t1c${IDGenerator.uniqueID}" class="new_input" onclick="fillColumnKeyPerson(id)">${key.org_office_name}</td>
                                                        <td id="t1c${IDGenerator.uniqueID}" onclick="fillColumnKeyPerson(id)" >${key.emp_code}</td>
                                                        <td id="t1c${IDGenerator.uniqueID}" onclick="fillColumnKeyPerson(id)" style="display: none">${key.salutation}</td>
                                                        <td id="t1c${IDGenerator.uniqueID}" class="new_input" onclick="fillColumnKeyPerson(id)">${key.key_person_name}</td>
                                                        <td id="t1c${IDGenerator.uniqueID}" class="new_input" onclick="fillColumnKeyPerson(id)" style="display: none">${key.password}</td>
                                                        
                                                        <td id="t1c${IDGenerator.uniqueID}" class="new_input" onclick="fillColumnKeyPerson(id)">${key.father_name}</td>
                                                         <td id="t1c${IDGenerator.uniqueID}" class="new_input" onclick="fillColumnKeyPerson(id)">${key.blood}</td>
                                                           <td id="t1c${IDGenerator.uniqueID}" class="new_input" onclick="fillColumnKeyPerson(id)">${key.gender}</td>
                                                        <td id="t1c${IDGenerator.uniqueID}" class="" onclick="fillColumnKeyPerson(id)">${key.date_of_birth}</td>
                                                        <%-- <td id="t1c${IDGenerator.uniqueID}" ><img src="E:/Traffic/ImageUpload/DSP police_pic_0.jpg" height="90"width="90"></td>--%>
                                                        <td id="t1c${IDGenerator.uniqueID}" class="new_input" onclick="fillColumnKeyPerson(id)">${key.designation}</td>
                                                        <td id="t1c${IDGenerator.uniqueID}" class="new_input" onclick="fillColumnKeyPerson(id)">${key.address_line1}</td>
                                                        <td id="t1c${IDGenerator.uniqueID}" class="new_input" onclick="fillColumnKeyPerson(id)" style="display: none">${key.address_line2}</td>
                                                        <td id="t1c${IDGenerator.uniqueID}" class="new_input" onclick="fillColumnKeyPerson(id)" style="display: none">${key.address_line3}</td>
                                                        
                                                         <td id="t1c${IDGenerator.uniqueID}" class="new_input" onclick="fillColumnKeyPerson(id)">${key.emergency_name}</td>
                                                          <td id="t1c${IDGenerator.uniqueID}" class="new_input" onclick="fillColumnKeyPerson(id)">${key.emergency_number}</td>
                                                          
                                                         <td id="t1c${IDGenerator.uniqueID}" class="new_input" onclick="fillColumnKeyPerson(id)">${key.id_type}</td>
                                                       
                                                         <td id="t1c${IDGenerator.uniqueID}" class="new_input" onclick="fillColumnKeyPerson(id)">${key.id_no}</td>
                                                        
                                                        <td id="t1c${IDGenerator.uniqueID}" class="new_input" onclick="fillColumnKeyPerson(id)">${key.city_name}</td>
                                                        <%-- <td id="t1c${IDGenerator.uniqueID}" onclick="fillColumnKeyPerson(id)" style="display: none">${key.state_name}</td> --%>
                                                        <td id="t1c${IDGenerator.uniqueID}" onclick="fillColumnKeyPerson(id)">${key.mobile_no1}</td>
                                                        <td id="t1c${IDGenerator.uniqueID}" onclick="fillColumnKeyPerson(id)" style="display: none">${key.mobile_no2}</td>
                                                        <td id="t1c${IDGenerator.uniqueID}" onclick="fillColumnKeyPerson(id)" style="display: none">${key.landline_no1}</td>
                                                        <td id="t1c${IDGenerator.uniqueID}" onclick="fillColumnKeyPerson(id)" style="display: none">${key.landline_no2}</td>
                                                        <td id="t1c${IDGenerator.uniqueID}" onclick="fillColumnKeyPerson(id)">${key.email_id1}</td>
                                                        <td id="t1c${IDGenerator.uniqueID}" onclick="fillColumnKeyPerson(id)" style="display: none">${key.email_id2}</td>
                                                           <td id="t1c${IDGenerator.uniqueID}" onclick="fillColumnKeyPerson(id)" style="display: none">${key.latitude}</td>
                                                           <td id="t1c${IDGenerator.uniqueID}" onclick="fillColumnKeyPerson(id)" style="display: none">${key.longitude}</td>
                                                         <td>
                                            <input type="button" class="btn btn-info"  value ="View Map" id="map_container${loopCounter.count}" onclick="openMap('${key.key_person_id}');"/>
                                        </td>
                                        <td>
                                            <input type="button" class="btn btn-info"  value ="Emergency Contact List" id="emergency_container${loopCounter.count}" onclick="emergency('${key.key_person_id}');"/>
                                        </td>
                                                        <td id="t1c${IDGenerator.uniqueID}" onclick="fillColumnKeyPerson(id)" style="display: none">${key.general_image_details_id}</td>
                                                        <%--  <td id="t1c${IDGenerator.uniqueID}" onclick="fillColumnKeyPerson(id)" style="display: none">${key.image_name}</td>--%>

                                                        <td id="t1c${IDGenerator.uniqueID}" >
                                                            <input type="button" class="button" id="${loopCounter.count}" name="emp_code" value="Photo" onclick="viewDemandNote(${key.key_person_id}, 'ph')">
                                                            <input type="button" class="button" id="${loopCounter.count}" name="emp_code" value="ID" onclick="viewDemandNote(${key.key_person_id}, 'id')">
                                                        </td>
                                                         <td id="t1c${IDGenerator.uniqueID}" onclick="fillColumnKeyPerson(id)" >${key.familyName}</td>
                                                         <td id="t1c${IDGenerator.uniqueID}" onclick="fillColumnKeyPerson(id)" >${key.familyDesignation}</td>
                                                         <td id="t1c${IDGenerator.uniqueID}" onclick="fillColumnKeyPerson(id)" >${key.relation}</td>
                                                    </tr>
                                                    <input type="hidden"  name="emp_code" id="emp_code1${loopCounter.count}" value="${key.emp_code}">-->
                                                    <table id="table1" width="600"  border="1"  align="center" class="table table-bordered table-striped boxeffext">
                                                    <tr>
                                                      
                                                             <td id="t1c${IDGenerator.uniqueID}" class="new_input" onclick="fillColumnKeyPerson(id)">
                                                                Name:  <a class="btn btn-secondary" onclick="showpersonal('${key.key_person_id}')" title="click for personal info"> ${key.key_person_name}  </a>  
                                                             </td>
                                                             <td id="t1c${IDGenerator.uniqueID}" class="new_input" onclick="fillColumnKeyPerson(id)">
                                                             Office:  <a class="btn btn-secondary" onclick="showoffice('${key.key_person_id}')" title="click for Organisation info">    ${key.org_office_name} </a>   
                                                             </td>
                                                             <td id="t1c${IDGenerator.uniqueID}" onclick="fillColumnKeyPerson(id)" >
                                                            Family Office:  <a class="btn btn-secondary">   ${key.familyName} </a>   
                                                             </td>
                                                               <td id="t1c${IDGenerator.uniqueID}" onclick="fillColumnKeyPerson(id)" >
                                                              <a class="btn btn-secondary">  Emergency Contact List </a>   
                                                              
                                                             </td>
                                                             <td>
                                                                 <img src="https://img.icons8.com/wired/30/000000/edit.png" title="edit"/>
                                                              <img src="https://img.icons8.com/windows/30/000000/delete-forever.png" title="delete"/>
                                                             </td>
                                                        
                                                    </tr>
                                                    <tr id="personalinfo${key.key_person_id}" class="hidden">
                                                        <td id="t1c${IDGenerator.uniqueID}" class="new_input" onclick="fillColumnKeyPerson(id)">
                                                            <table>
                                                                <tr>
                                                                    <td>
                                                                        Blood Group:
                                                                    </td>
                                                                    <td>
                                                                        ${key.blood}
                                                                    </td>
                                                                </tr>
                                                                <tr>
                                                                    <td>
                                                                        Mobile Number:
                                                                    </td>
                                                                    <td>
                                                                        ${key.mobile_no1}
                                                                    </td>
                                                                </tr>
                                                                <tr>
                                                                    <td>
                                                                        Gender:
                                                                    </td>
                                                                    <td>
                                                                        ${key.gender}
                                                                    </td>
                                                                </tr>
                                                                <tr>
                                                                    <td>
                                                                        Date Of Birth:
                                                                    </td>
                                                                    <td>
                                                                        ${key.date_of_birth}
                                                                    </td>
                                                                </tr>
                                                            
                                                            </table>
                                                        
                                                             
                                                            
                                                        </td>
                                                        <td id="t1c${IDGenerator.uniqueID}" onclick="fillColumnKeyPerson(id)">
                                                            <table>
                                                                 <tr>
                                                                    <td>
                                                                        Father Name:
                                                                    </td>
                                                                    <td>
                                                                        ${key.father_name}
                                                                    </td>
                                                                </tr>
                                                                 <tr>
                                                                    <td>
                                                                         ID :
                                                                    </td>
                                                                    <td>
                                                                         ${key.id_type}
                                                                    </td>
                                                                </tr>
                                                                 <tr>
                                                                    <td>
                                                                         Id Number :
                                                                    </td>
                                                                    <td>
                                                                          ${key.id_no}
                                                                    </td>
                                                                </tr>
                                                            </table>
                                                        
                                                           
                                                        </td>
                                                        <td id="t1c${IDGenerator.uniqueID}" class="new_input" onclick="fillColumnKeyPerson(id)">
                                                             <table>
                                                                 <tr>
                                                                    <td>
                                                                         Address:
                                                                    </td>
                                                                    <td>
                                                                        ${key.address_line1}
                                                                    </td>
                                                                </tr>
                                                                 <tr>
                                                                    <td>
                                                                         City:
                                                                    </td>
                                                                    <td>
                                                                        ${key.city_name}
                                                                    </td>
                                                                </tr>
                                                                 <tr>
                                                                    <td>
                                                                        Latitude:
                                                                    </td>
                                                                    <td>
                                                                        ${key.latitude}
                                                                    </td>
                                                                </tr>
                                                                 <tr>
                                                                    <td>
                                                                        Longitude:
                                                                    </td>
                                                                    <td>
                                                                        ${key.longitude}
                                                                    </td>
                                                                </tr>
                                                            </table>
                                                           
                                                          
                                                             
                                                        </td>
                                                        <td id="t1c${IDGenerator.uniqueID}" class="" onclick="fillColumnKeyPerson(id)">
                                                            <table>
                                                                 <tr>
                                                                    <td>
                                                                        Mobile Number:
                                                                    </td>
                                                                    <td>
                                                                        ${key.mobile_no1}
                                                                    </td>
                                                                </tr>
                                                                 <tr>
                                                                    <td>
                                                                         Email :
                                                                    </td>
                                                                    <td>
                                                                        ${key.email_id1}
                                                                    </td>
                                                                </tr>
                                                            </table>
                                                          
                                                           
                                                        </td>
                                                    </tr>
                                                    <tr id="officeinfo${key.key_person_id}" class="hidden">
                                                        <td colspan="2">
                                                             <table>
                                                                 <tr>
                                                                    <td>
                                                                        Organisation:
                                                                    </td>
                                                                    <td>
                                                                        ${key.organisation_name}
                                                                    </td>
                                                                </tr>
                                                                 <tr>
                                                                    <td>
                                                                         Employee Id :
                                                                    </td>
                                                                    <td>
                                                                        ${key.emp_code}
                                                                    </td>
                                                                </tr>
                                                            </table>
                                                        </td>
                                                        <td colspan="2">
                                                             <table>
                                                                 <tr>
                                                                    <td>
                                                                        Office Code:
                                                                    </td>
                                                                    <td>
                                                                        ${key.org_office_code}
                                                                    </td>
                                                                </tr>
                                                                 <tr>
                                                                    <td>
                                                                         Office Type :
                                                                    </td>
                                                                    <td>
                                                                       ${key.office_type}
                                                                    </td>
                                                                </tr>
                                                                 <tr>
                                                                    <td>
                                                                         Office Name :
                                                                    </td>
                                                                    <td>
                                                                       ${key.org_office_name}
                                                                    </td>
                                                                </tr>
                                                            </table>
                                                        </td>
                                                    </tr>
                                                    
<!--                                                    <tr id="familyinfo${key.key_person_id}" class="hidden">
                                                    <table>
                                                        <tr>
                                                            <td>
                                                               Person Name
                                                            </td>
                                                            <td>
                                                               Mobile Number
                                                            </td>
                                                            <td>
                                                                Email id
                                                            </td>
                                                            <td>
                                                                Date Of Birth
                                                            </td>
                                                            <td>
                                                                Gender
                                                            </td>
                                                            
                                                            <td>
                                                                Father Name
                                                            </td>
                                                            <td>
                                                                Relation
                                                            </td>
                                                            <td>
                                                                Blood Group
                                                            </td>
                                                            <td>
                                                                Id
                                                            </td>
                                                            <td>
                                                                Id number
                                                            </td>
                                                        </tr>
                                                        <tr>
                                                             <td>
                                                               Vk
                                                            </td>
                                                            <td>
                                                               8945561223
                                                            </td>
                                                            <td>
                                                                test@gmail.com
                                                            </td>
                                                            <td>
                                                                26/12/1989
                                                            </td>
                                                            <td>
                                                                M
                                                            </td>
                                                            
                                                            <td>
                                                                Demo
                                                            </td>
                                                            <td>
                                                                Brother
                                                            </td>
                                                            <td>
                                                                A+
                                                            </td>
                                                            <td>
                                                                Aadhar
                                                            </td>
                                                            <td>
                                                                7845122356
                                                            </td>
                                                        </tr>
                                                    </table>
                                                          </tr>-->
                                                    
                                                
                                                    </table>
                                                </c:forEach>
                                                <table id="table1" width="600"  border="1"  align="center" class="table table-bordered table-striped boxeffext">
                                                <tr>
                                                    <td colspan="26" align="center">
                                                        <c:choose>
                                                            <c:when test="${showFirst eq 'false'}">
                                                                <input class="btn btn-primary" type='submit' name='buttonAction' value='First' disabled>
                                                                 <input type="hidden" id="activee" name="active" value="${active}">
                                                            </c:when>
                                                            <c:otherwise>
                                                                <input class="btn btn-primary" type='submit' name='buttonAction' value='First'>
                                                                 <input type="hidden" id="activee" name="active" value="${active}">
                                                            </c:otherwise>
                                                        </c:choose>
                                                        <c:choose>
                                                            <c:when test="${showPrevious == 'false'}">
                                                                <input class="btn btn-success" type='submit' name='buttonAction' value='Previous' disabled>
                                                                 <input type="hidden" id="activee" name="active" value="${active}">
                                                            </c:when>
                                                            <c:otherwise>
                                                                <input class="btn btn-success" type='submit' name='buttonAction' value='Previous'>
                                                                 <input type="hidden" id="activee" name="active" value="${active}">
                                                            </c:otherwise>
                                                        </c:choose>
                                                        <c:choose>
                                                            <c:when test="${showNext eq 'false'}">
                                                                <input class="btn btn-warning" type='submit' name='buttonAction' value='Next' disabled>
                                                                 <input type="hidden" id="activee" name="active" value="${active}">
                                                            </c:when>
                                                            <c:otherwise>
                                                                <input class="btn btn-warning" type='submit' name='buttonAction' value='Next'>
                                                                 <input type="hidden" id="activee" name="active" value="${active}">
                                                            </c:otherwise>
                                                        </c:choose>
                                                        <c:choose>
                                                            <c:when test="${showLast == 'false'}">
                                                                <input class="btn btn-info" type='submit' name='buttonAction' value='Last' disabled>
                                                                 <input type="hidden" id="activee" name="active" value="${active}">
                                                            </c:when>
                                                            <c:otherwise>
                                                                <input class="btn btn-info" type='submit' name='buttonAction' value='Last'>
                                                                 <input type="hidden" id="activee" name="active" value="${active}">
                                                            </c:otherwise>
                                                        </c:choose>
                                                    </td>
                                                </tr>
                                                <%-- These hidden fields "lowerLimit", and "noOfRowsTraversed" belong to form1 of table1. --%>
                                                <input type="hidden" name="lowerLimit" value="${lowerLimit}">
                                                <input type="hidden" id="noOfRowsTraversed" name="noOfRowsTraversed" value="${noOfRowsTraversed}">
                                                <input type="hidden"  name="searchOfficeCode" value="${searchOfficeCode}">
                                                <input type="hidden"  name="searchEmpCode" value="${searchEmpCode}">
                                                <input type="hidden"  name="searchKeyPerson" value="${searchKeyPerson}">
                                                <input type="hidden"  name="searchDesignation" value="${searchDesignation}">
                                                 <input type="hidden"  name="searchmobile" value="${searchmobile}">
                                                        <input type="hidden"  name="searchfamily" value="${searchfamily}">
                                            </table>
                                    </form>
                                            </DIV>
                     
                                            
                                   
                           

                    </DIV>
                
                 <%@include file="/layout/footer.jsp" %>
        
    </body>
</html>