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
       <meta charset="utf-8">
        <title>Data Entry:Org Office </title>
     
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
                    
                      $("#office_type").autocomplete({
                      
                source: function (request, response) {
               var random = document.getElementById("office_type").value;
                $.ajax({
                    url: "organisationCont.do",
                    dataType: "json",
                    data: {action1: "getOrgOfficeType",str:random},
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
                    url: "organisationCont.do",
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
        
        
        
        
          $("#searchhierarchy").autocomplete({
                    
                source: function (request, response) {
                 var random = document.getElementById("searchhierarchy").value;
                $.ajax({
                    url: "organisationCont.do",
                    dataType: "json",
                    data: {action1: "gethierarchysearch",str:random},
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
        
        
        
          
        
        
        
                 

        
      $(function () {     
    $("#organisation_name").autocomplete({
                    
            source: function (request, response) {
                  var random = document.getElementById("organisation_name").value;
                  
                $.ajax({
                    url: "organisationCont.do",
                    dataType: "json",
                    data: {action1: "getOrgTypeName",str:random},
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
                    url: "organisationCont.do",
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
  
        
         $("#searchgeneration").autocomplete({
                    
            source: function (request, response) {
                  var random = document.getElementById("searchgeneration").value;
                  
                $.ajax({
                    url: "organisationCont.do",
                    dataType: "json",
                    data: {action1: "getsearchgeneration",str:random},
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
                    url: "organisationCont.do",
                    dataType: "json",
                    data: {action1: "getOfficeCodeName",
                           action2: org_name,str:random},
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
                    url: "organisationCont.do",
                    dataType: "json",
                    data: {action1: "getOfficeName",
                           action2: org_name,
                           action3: office_code,str:random},
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
                    url: "organisationCont.do",
                    dataType: "json",
                    data: {action1: "searchOrgTypeName",str:random},
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
                    url: "organisationCont.do",
                    dataType: "json",
                    data: {action1: "getParentOrgOffice",
                           action2: code,str:random,
                           action3 :editable,       
                           action4: generation,
                           action5:designation
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
    
    
       
           
              var editable=false;
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

                if(id == 'new') {
                    editable="false";
                    // document.getElementById("message").innerHTML = "";      // Remove message
                    $("#message").html("");
                    document.getElementById("org_office_id").value = "";
                    document.getElementById("serialnumber").disabled = false;
              document.getElementById("supern").disabled = false;
                 document.getElementById("supery").disabled = false;
                    document.getElementById("edit").disabled = true;
                    document.getElementById("delete").disabled = true;
                    document.getElementById("save_As").disabled = true;
                        document.getElementById("get_cordinate").disabled = false;
                    setDefaultColordOrgn(document.getElementById("noOfRowsTraversed").value, 17);
                    document.getElementById("organisation_name").focus();
                }
                if(id == 'edit'){
                     editable="true";
                    document.getElementById("save_As").disabled = false;
                    document.getElementById("delete").disabled = false;
                    document.getElementById("serialnumber").disabled = false;
              document.getElementById("supern").disabled = false;
                 document.getElementById("supery").disabled = false;
                     document.getElementById("get_cordinate").disabled = false;
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
            function fillColumns(id) {
             
               
                //  var noOfRowsTraversed = document.getElementById("noOfRowsTraversed").value;
                var countrow =document.getElementById('table1').getElementsByTagName('tr').length;//// for getting the number of rows
                //  alert("no of Rows"+ countrow);
                var noOfRowsTraversed = countrow-2 ;
                //                alert("get total count rows"+ noOfRowsTraversed);
                var noOfColumns = 21;
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
                var upper= higherLimit;
                setDefaultColordOrgn(noOfRowsTraversed, noOfColumns);// set default color of rows (i.e. of multiple coloumns).
                //                alert("Total number of column in the set the valeu   "+  noOfColumns);
                var t1id = "t1c";       // particular column id of table 1 e.g. t1c3.
                //var t2id = "t2c";
              debugger; 
                document.getElementById("org_office_id").value= document.getElementById("org_office_id" + rowNo).value;
                document.getElementById("org_office_code").value = document.getElementById(t1id +(lower+1)).innerHTML;
              document.getElementById("organisation_name").value = document.getElementById(t1id +(lower+2)).innerHTML.replace("&amp;","&");
               document.getElementById("org_office_name").value = document.getElementById(t1id +(lower+3)).innerHTML.replace("&amp;","&");
               document.getElementById("office_type").value = document.getElementById(t1id +(lower+4)).innerHTML.replace("&amp;","&");
                document.getElementById("address_line1").value = document.getElementById(t1id +(lower+5)).innerHTML;
                document.getElementById("address_line2").value = document.getElementById(t1id +(lower+6)).innerHTML;
                document.getElementById("address_line3").value = document.getElementById(t1id +(lower+7)).innerHTML;
                document.getElementById("city_name").value = document.getElementById(t1id +(lower+8)).innerHTML;
              //  document.getElementById("state_name").value = document.getElementById(t1id +(lower+9)).innerHTML;
                document.getElementById("email_id1").value = document.getElementById(t1id +(lower+9)).innerHTML;
                document.getElementById("email_id2").value = document.getElementById(t1id +(lower+10)).innerHTML;
                document.getElementById("mobile_no1").value = document.getElementById(t1id +(lower+11)).innerHTML;
                document.getElementById("mobile_no2").value = document.getElementById(t1id +(lower+12)).innerHTML;
                document.getElementById("landline_no1").value = document.getElementById(t1id +(lower+13)).innerHTML;
                document.getElementById("landline_no2").value = document.getElementById(t1id +(lower+14)).innerHTML;
                document.getElementById("landline_no3").value = document.getElementById(t1id +(lower+15)).innerHTML;
                  document.getElementById("latitude").value = document.getElementById(t1id +(lower+16)).innerHTML;
                  document.getElementById("longitude").value = document.getElementById(t1id +(lower+17)).innerHTML;
                 document.getElementById("serialnumber").value = document.getElementById(t1id +(lower+18)).innerHTML;
               if(document.getElementById(t1id + (lower + 19)).innerHTML=='N')
                 {
                   document.getElementById("supern").checked = document.getElementById(t1id + (lower + 19)).innerHTML;
                    document.getElementById("supery").unchecked = document.getElementById(t1id + (lower + 19)).innerHTML;
               } 
               else
               {
                    document.getElementById("supery").checked = document.getElementById(t1id + (lower + 19)).innerHTML;
                      document.getElementById("supern").unchecked = document.getElementById(t1id + (lower + 19)).innerHTML;
               }
                 document.getElementById("generation").value = document.getElementById(t1id +(lower+20)).innerHTML;


                for(var i = 0; i <= 20; i++) {

                    document.getElementById(t1id + (lower + i)).bgColor = "#d0dafd";        // set the background color of clicked row to yellow.
                }
                document.getElementById("edit").disabled = false;
                if(!document.getElementById("save").disabled)   // if save button is already enabled, then make edit, and send button enabled too.
                // document.getElementById("send").disabled = false;
                {
                    document.getElementById("save_As").disabled = true;
                    document.getElementById("delete").disabled = false;

                }
                // document.getElementById("message").innerHTML = "";      // Remove message
                $("#message").html("");
            }


            function setStatus(id) {
                if(id == 'save'){
                    document.getElementById("clickedButton").value = "Save";
                }
                else if(id == 'save_As'){
                    document.getElementById("clickedButton").value = "Save AS New";
                }
                else if(id == 'search_org'){
                    var org_name=document.getElementById("org_name").value;
                    document.getElementById("org_name1").value =  org_name;
                    document.getElementById("org_name2").value =  org_name;
                    document.getElementById("clickedButton").value = "SEARCH";
                }
                else if(id == 'clear_org'){
                    document.getElementById("clickedButton").value = ' ';
                    // document.getElementById("").innerHTML = "   ";
                    $("#org_msg").html("");
                    document.getElementById("org_name").value =" ";
                    document.getElementById("office_code_search").value =" ";
                    document.getElementById("office_name_search").value =" ";
                    document.getElementById("org_name1").value =  " ";
                    document.getElementById("org_name2").value =  " ";
                }
                else document.getElementById("clickedButton").value = "Delete";
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
                    var organisation_name = document.getElementById("organisation_name").value;
                    var org_office_name = document.getElementById("org_office_name").value;
                    var office_type = document.getElementById("office_type").value;
                    var office_code = document.getElementById("org_office_code").value;
                    var address_line1 = document.getElementById("address_line1").value;
                   // var state_name = document.getElementById("state_name").value;
                    var city_name = document.getElementById("city_name").value;
                    var email_id1 = document.getElementById("email_id1").value;
                    var email_id2 = document.getElementById("email_id2").value;
                    var landline_no1 = document.getElementById("landline_no1").value;
                    

                    if(myLeftTrim(organisation_name).length == 0) {
                        // document.getElementById("message").innerHTML = "<td colspan='8' bgcolor='coral'><b>Organisation Name is required...</b></td>";
                        $("#message").html( "<td colspan='8' bgcolor='coral'><b>Organisation Name is required...</b></td>");
                        document.getElementById("organisation_name").focus();
                        return false;
                    }
                    if(myLeftTrim(office_code).length == 0) {
                        // document.getElementById("message").innerHTML = "<td colspan='8' bgcolor='coral'><b>Organisation Name is required...</b></td>";
                        $("#message").html( "<td colspan='8' bgcolor='coral'><b>Office Code is required...</b></td>");
                        document.getElementById("office_code").focus();
                        return false;
                    }
                    if(myLeftTrim(org_office_name).length == 0) {
                        // document.getElementById("message").innerHTML = "<td colspan='8' bgcolor='coral'><b>Organisation Office Name is required...</b></td>";
                        $("#message").html("<td colspan='8' bgcolor='coral'><b>Organisation Office Name is required...</b></td>");
                        document.getElementById("org_office_name").focus();
                        return false;
                    }
                    if(myLeftTrim(office_type).length == 0) {
                        // document.getElementById("message").innerHTML = "<td colspan='8' bgcolor='coral'><b>Org Office Type is required...</b></td>";
                        $("#message").html("<td colspan='8' bgcolor='coral'><b>Org Office Type is required...</b></td>");
                        document.getElementById("office_type").focus();
                        return false;
                    }
                   // if(myLeftTrim(state_name).length == 0) {
                        //document.getElementById("message").innerHTML = "<td colspan='8' bgcolor='coral'><b>State Name is required...</b></td>";
                       // $("#message").html("<td colspan='8' bgcolor='coral'><b>State Name is required...</b></td>");
                      //  document.getElementById("state_name").focus();
                     //   return false;// code to stop from submitting the form2.
                  //  }
                    if(myLeftTrim(city_name).length == 0) {
                        //document.getElementById("message").innerHTML = "<td colspan='8' bgcolor='coral'><b>City Name is required...</b></td>";
                        $("#message").html("<td colspan='8' bgcolor='coral'><b>City Name is required...</b></td>");
                        document.getElementById("city_name").focus();
                        return false;// code to stop from submitting the form2.
                    }
                    if(myLeftTrim(address_line1).length == 0) {
                        // document.getElementById("message").innerHTML = "<td colspan='8' bgcolor='coral'><b>Address Line 1 is required...</b></td>";
                        $("#message").html("<td colspan='8' bgcolor='coral'><b>Address Line 1 is required...</b></td>");
                        document.getElementById("address_line1").focus();
                        return false;
                    }
                    var reg = /^([A-Za-z0-9_\-\.])+\@([A-Za-z0-9_\-\.])+\.([A-Za-z]{2,4})$/;

                    if(reg.test(email_id1) == false) {
                        // document.getElementById("message").innerHTML = "<td colspan='8' bgcolor='coral'><b>Please Enter Correct 1st Email ID...</b></td>";
                        $("#message").html( "<td colspan='8' bgcolor='coral'><b>Please Enter Correct 1st Email ID...</b></td>");
                        document.getElementById("email_id1").focus();
                        return false;// code to stop from submitting the form2.
                    }
                    if(myLeftTrim(email_id2).length > 0) {
                        if(reg.test(email_id2) == false) {
                            //document.getElementById("message").innerHTML = "<td colspan='8' bgcolor='coral'><b>Please Enter Correct 2nd Email ID...</b></td>";
                            $("#message").html("<td colspan='8' bgcolor='coral'><b>Please Enter Correct 2nd Email ID...</b></td>");
                            document.getElementById("email_id2").focus();
                            return false;// code to stop from submitting the form2.
                        }
                    }
                    if(myLeftTrim(landline_no1).length == 0) {
                        // document.getElementById("message").innerHTML = "<td colspan='8' bgcolor='coral'><b>Landline No 1 is required...</b></td>";
                        $("#message").html( "<td colspan='8' bgcolor='coral'><b>Landline No 1 is required...</b></td>");
                        document.getElementById("landline_no1").focus();
                        return false;// code to stop from submitting the form2.
                    }
                    if(result == false)
                    {// if result has value false do nothing, so result will remain contain value false.
                    }
                    else{ result = true;
                    }
                    if(document.getElementById("clickedButton").value == 'Save AS New'){
                        result = confirm("Are you sure you want to save it as New record?")
                        return result;
                    }
                } else result = confirm("Are you sure you want to delete this record?")
                return result;
            }


            function verifySearch(){
                var result;
                if(document.getElementById("clickedButton").value == 'SEARCH') {
                    var org_name = document.getElementById("org_name").value;

                    if(myLeftTrim(org_name).length == 0) {
                        //document.getElementById("org_msg").innerHTML = "<b>Organization Name is required...</b>";
                        $("#org_msg").html("<b>Organization Name is required...</b>");
                        document.getElementById("org_name").focus();
                        return false; // code to stop from submitting the form2.
                    }
                }
            }

            function displayOrgnList(id){
                var queryString ;
                var organisation= document.getElementById("org_name").value;
                var office_code_search = document.getElementById("office_code_search").value;
                var office_name_search =  document.getElementById("office_name_search").value;
                var searchmobile =  document.getElementById("searchmobile").value;
                var searchgeneration =  document.getElementById("searchgeneration").value;
                  var active = document.getElementById("activee").value;
                  var searchhierarchy = document.getElementById("searchhierarchy").value;
                if(id == "viewPdf")
                    queryString = "requester=PRINT&org_name="+organisation+"&office_code_search="+office_code_search+"&office_name_search="+office_name_search+"&searchmobile="+searchmobile+"&searchgeneration="+searchgeneration+"&active="+active+"&searchhierarchy="+searchhierarchy;
                else
                    queryString = "requester=PrintExcel&organisation="+organisation;
                var url = "organisationCont.do?"+queryString;
                popupwin = openPopUp(url, "Organisation", 600, 900);
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

            function printAddress(id){
                var queryString = "requester=PRINTAddress&org_office_id="+id;
                var url = "organisationCont.do?"+queryString;
                popupwin = openPopUp(url, "Organisation", 600, 900);
            }
            function codeIsEmpty(){

                var office_code = document.getElementById("office_code").value;

                if(myLeftTrim(office_code).length == 0) {
                    // document.getElementById("message").innerHTML = "<td colspan='8' bgcolor='coral'><b>Organisation Name is required...</b></td>";
                    $("#message").html( "<td colspan='8' bgcolor='coral'><b>Office Code is required...</b></td>");
                    document.getElementById("office_code").focus();
                        
                }else{
                $("#message").html("");
                }
            }

            //            $(function() {
            //                var $el = $('#container').jScrollPane({
            //                }),
            //                pane = $el.data('jsp');
            //            });




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


 function openMap(id) {
                //alert(vehicle_key_person_map_id);
                var url="organisationCont.do?task=showMapWindow&org_office_id="+id;
               
                popupwin = openPopUp(url, "",  580, 620);
           
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
        url: "organisationCont.do",
      data: {action1:"mobile_number",
             str:random},
        dataType: "json",
        success: function (response_data) {
  
                        console.log(response_data);
                        
                        alert(response_data.list[0]);
                        document.getElementById("mobile_no1").value=response_data.list[0];
                      //  response(response_data.list);
                    }, error: function (error) {
                        console.log(error.responseText);
                         document.getElementById("mobile_no1").value="";
                        response(error.responseText);
                    }


    });
}

    }      

 function setDefaultValues(id){
            var result_type=   document.getElementById(id).checked;
            var default_mobile_no="9999999999";
           var default_email_id="abc@xyz.com";
            var default_address= "ABC";
            var default_address1= "XYZ";
            var default_address2= "UVW";
            var email_id1 = "xyz@abc.com";
            var landline_no1 = "000000000";
             var landline_no2 = "00000000";
            var landline_no3 = "000000000";
            //  document.getElementById("supern").checked="N";
              var city = "jabalpur";
            
            var latitude = "0.0";
            var longitude = "0.0";
            if(result_type){
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
                          document.getElementById("supern").checked="N";
                          
            }else{
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
         
                   
                                     <div class="container  topbox">
                                 <h1>Org. Office</h1>
                             </div>

            
                                                <div class="container">
                                    <form name="form0" class="form-group row boxeffext" method="POST" action="organisationCont.do" >
                                        <table  align="center"   width="900" class="table table-bordered table-striped">
                                            <tr>
                                                <th>  Organization Name&nbsp;&nbsp;&nbsp;&nbsp;</th>
                                                <td>  <input type="text" class="form-control" id="org_name" name="org_name" value="${org_name}" size="20"></td>
                                                <th> Office Code&nbsp;&nbsp;&nbsp;&nbsp;</th>
                                                    <td>    <input type="text"  class="form-control" id="office_code_search" name="office_code_search" value="${office_code_search}" size="20"></td>
                                                    <th>  Office Name&nbsp;&nbsp;&nbsp;</th>
                                                    <td><input type="text"  class="form-control" id="office_name_search" name="office_name_search" value="${office_name_search}" size="20"></td>
                                            </tr>
                                            <tr>   
                                                    
                                                    <th>  Mobile Number&nbsp;&nbsp;&nbsp;</th>
                                                    <td><input type="text"  class="form-control" id="searchmobile" name="searchmobile" value="${searchmobile}" size="20"></td>
                                                    <th>  Generation&nbsp;&nbsp;&nbsp;</th>
                                                    <td><input type="text"  class="form-control" id="searchgeneration" name="searchgeneration" value="${searchgeneration}" size="20"></td>
                                                    
                                      <th> Org Office hierarchy</th>
                                                    <td>    <input type="text"  class="form-control" id="searchhierarchy" name="searchhierarchy" value="${searchhierarchy}" size="20"></td>
                                            <tr>
                                                <td align="center"> <input type="submit" class="btn btn-primary" id="search_org" name="search_org" value="SEARCH" onclick="setStatus(id)"></td>
                                                <td align="center"> <input type="submit" class="btn btn-success" id="showAllRecords" name="task" value="Show All Records" onclick="setStatus(id)"></td>
                                                <td align="center"> <input type="button" class="btn btn-warning" id="viewPdf" name="viewPdf" value="PDF" onclick="displayOrgnList(id)"></td>
                                            </tr> 
                                                    <!--
                                                    <td>   <input type="button" class="btn btn-info" id="viewExcel" name="viewExcel" value="Excel" onclick="displayOrgnList(id)"></td>-->
                                            
                                            
                                            
                                            
                                            
                                            
                                            
                                            
                                            
                                            <input type="hidden" id="activee" name="active" value="${active}">
                                                    <td> <label id="org_msg">  </label></td>
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

             <td>  <select class="ui dropdown form-control" name="task" id="hiera" >
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
                                    <form name="form1" method="POST" action="organisationCont.do" class="boxeffext ">
                                       
                                            <table id="table1" width="600"  border="1"  align="center" class="table table-bordered table-striped boxeffext">
                                                <tr>
                                                    <th  class="heading">S.No.</th>
                                                    <th  class="heading" style=" overflow:inherit">Office Type Code</th>
                                                    <th  class="heading">Organization_Name</th>
                                                    <th class="heading">Office_Name</th>
                                                    <th class="heading">Office_Type</th>
                                                    <th class="heading">Address_1</th>
                                                    <th class="heading" style="display: none">Address_2</th>
                                                    <th class="heading" style="display: none">Address_3</th>
                                                    <th class="heading">City Name</th>
                                                    <th class="heading">First Email ID</th>
                                                    <th class="heading" style="display: none">Second Email ID</th>
                                                    <th class="heading">Mobile No1</th>
                                                    <th class="heading" style="display: none">Mobile No2</th>
                                                    <th class="heading">Landline No1</th>
                                                    <th class="heading" style="display: none">Landline No2</th>
                                                    <th class="heading" style="display: none">Landline No3</th>
                                                    <th class="heading" style="display: none">Latitude</th>
                                                     <th class="heading" style="display: none">Longitude</th>
                                                    
                                                    <th class="heading">Parent Org Office</th>
                                                    
                                                    <th class="heading">Is Super Child</th>
                                                     <th class="heading">Generation</th>
                                                </tr>
                                                <c:forEach var="organisation" items="${requestScope['organisationList']}" varStatus="loopCounter">
                                                    <tr  class="${loopCounter.index % 2 == 0 ? 'even': 'odd'}" >
                                                        <td id="t1c${IDGenerator.uniqueID}" onclick="fillColumns(id)" align="center">
                                                            <input type="hidden" id="org_office_id${loopCounter.count}" value="${organisation.org_office_id}">${lowerLimit - noOfRowsTraversed + loopCounter.count}
                                                        </td>
                                                        <td id="t1c${IDGenerator.uniqueID}" onclick="fillColumns(id)">${organisation.org_office_code}</td>
                                                        <td id="t1c${IDGenerator.uniqueID}" class="new_input" onclick="fillColumns(id)">${organisation.organisation_name}</td>
                                                        <td id="t1c${IDGenerator.uniqueID}" class="new_input" onclick="fillColumns(id)">${organisation.org_office_name}</td>
                                                        <td id="t1c${IDGenerator.uniqueID}" class="new_input" onclick="fillColumns(id)">${organisation.office_type}</td>
                                                        <td id="t1c${IDGenerator.uniqueID}" class="new_input" onclick="fillColumns(id)">${organisation.address_line1}</td>
                                                        <td id="t1c${IDGenerator.uniqueID}" class="new_input" onclick="fillColumns(id)" style="display: none">${organisation.address_line2}</td>
                                                        <td id="t1c${IDGenerator.uniqueID}" class="new_input" onclick="fillColumns(id)" style="display: none">${organisation.address_line3}</td>
                                                        <td id="t1c${IDGenerator.uniqueID}" class="new_input" onclick="fillColumns(id)">${organisation.city_name}</td>
                                                       <%-- <td id="t1c${IDGenerator.uniqueID}" class="new_input" onclick="fillColumns(id)">${organisation.state_name}</td>--%>
                                                        <td id="t1c${IDGenerator.uniqueID}" onclick="fillColumns(id)">${organisation.email_id1}</td>
                                                        <td id="t1c${IDGenerator.uniqueID}" onclick="fillColumns(id)" style="display: none">${organisation.email_id2}</td>
                                                        <td id="t1c${IDGenerator.uniqueID}" onclick="fillColumns(id)">${organisation.mobile_no1}</td>
                                                        <td id="t1c${IDGenerator.uniqueID}" onclick="fillColumns(id)" style="display: none">${organisation.mobile_no2}</td>
                                                        <td id="t1c${IDGenerator.uniqueID}" onclick="fillColumns(id)">${organisation.landline_no1}</td>
                                                        <td id="t1c${IDGenerator.uniqueID}" onclick="fillColumns(id)" style="display: none">${organisation.landline_no2}</td>
                                                        <td id="t1c${IDGenerator.uniqueID}" onclick="fillColumns(id)" style="display: none">${organisation.landline_no3}</td>
                                                        
                                                         <td id="t1c${IDGenerator.uniqueID}" onclick="fillColumns(id)" style="display: none">${organisation.latitude}</td>
                                                         <td id="t1c${IDGenerator.uniqueID}" onclick="fillColumns(id)" style="display: none">${organisation.longitude}</td>
                                                       
                                                        <td id="t1c${IDGenerator.uniqueID}" onclick="fillColumns(id)">${organisation.p_org}</td>
                                                        <td id="t1c${IDGenerator.uniqueID}" onclick="fillColumns(id)">${organisation.superp}</td>
                                                         <td id="t1c${IDGenerator.uniqueID}" onclick="fillColumns(id)">${organisation.generation}</td>
                                                         <td>
                                            <input type="button" class="btn btn-info"  value ="View Map" id="map_container${loopCounter.count}" onclick="openMap('${organisation.org_office_id}');"/>
                                        </td>
                                                    </tr>
                                                </c:forEach>
                                                <tr>
                                                    <td align='center' colspan="17">
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
                                               
                                                <input type="hidden" id="org_name" name="org_name" value="${org_name}">
                                                <input type="hidden" id="office_name_search" name="office_name_search" value="${office_name_search}">
                                                <input type="hidden" id="office_code_search" name="office_code_search" value="${office_code_search}">
                                            </table>
                                    </form>
                              </DIV>
                                            
                                    <div class="container">
                                        <form name="form2" method="POST" class="form-group row boxeffext" action="organisationCont.do" onsubmit="return verify()">
                                            <table id="table2"  class="table table-bordered table-striped" border="0"  align="center" >
                                                <tr id="message">
                                                    <c:if test="${not empty message}">
                                                        <td colspan="8" bgcolor="${msgBgColor}"><b>Result: ${message}</b></td>
                                                    </c:if>
                                                </tr>
                                                
                                                <th class="heading1">Data Entry</th>
                                                     <td>
                                                         <strong>Default Value</strong>&nbsp;&nbsp;<input type="checkbox" class="checkbox-inline" id="default" name="default" onclick="setDefaultValues(id)"></td>
                                                
                                               <tr>
                                                    <th class="heading1" >Organization Name</th>
                                                    <td>
                                                        <input type="hidden" id="org_office_id" name="org_office_id" value="" >
                                                        <input class="form-control" type="text" id="organisation_name" name="organisation_name" value="${after_save_organisation}" disabled>
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
                                                    <th class="heading1">Address Line2</th>
                                                    <td><input class="form-control" type="text" id="address_line2" name="address_line2" value="" disabled></td>
                                                   
                                                </tr>
                                                <tr>
                                                     <th class="heading1">Address Line3</th>
                                                    <td><input class="form-control" type="text" id="address_line3" name="address_line3" value="" disabled></td>
                                                  <!--   <th class="heading1">State Name</th>
                                                    <td><input class="input new_input" style="display: none" type="text" id="state_name" name="state_name" value="" disabled></td> -->

                                                    <th class="heading1">City Name</th>
                                                    <td><input class="form-control" type="text" id="city_name" name="city_name" value="" disabled></td>
                                                    
                                                </tr>
                                                <tr>
                                                    <th class="heading1">First EmailID</th>
                                                    <td><input class="form-control" type="text" id="email_id1" name="email_id1" value="" disabled></td>
                                                    <th class="heading1">Second EmailID</th>
                                                    <td><input class="form-control" type="text" id="email_id2" name="email_id2" value="" disabled></td>
                                                    <th class="heading1">Mobile No1</th>
                                                    <td><input class="form-control" type="text" id="mobile_no1" name="mobile_no1" value="" onkeyup="myFun(id)" maxlength="10" disabled></td>

                                                          </tr>
                                                          <tr>
                                                              <th class="heading1">Mobile No2</th>
                                                    <td><input class="form-control" type="text" id="mobile_no2" name="mobile_no2" value="" maxlength="10" disabled></td>
                                                    <th class="heading1">Landline No1</th>
                                                    <td><input class="form-control" type="text" id="landline_no1" name="landline_no1" value="" disabled></td>
                                                    <th class="heading1">Landline No2</th>
                                                    <td><input class="form-control" type="text" id="landline_no2" name="landline_no2" value="" disabled></td>
                                                          </tr>
                                                          <tr>
                                                               <th class="heading1" nowrap>Landline No3</th>
                                                    <td><input class="form-control" type="text" id="landline_no3" name="landline_no3"  value="" disabled></td>
                                          
                                                          </tr>
                                                          
                                                           <tr>
                                    <th class="heading1">Latitude</th><td><input class="form-control" type="text" id="latitude" name="latitude" value="" size="20" disabled></td>
                                    <th class="heading1">Longitude</th><td><input  class="form-control" type="text" id="longitude" name="longitude" value="" size="20" disabled><input style="margin-left:18px;" class="btn btn-info" type="button" id="get_cordinate" value="Get Cordinate" onclick="openMapForCord()" disabled></td>
                                </tr>
                                                          
                                                          
                                                          
                                                           <tr>
                                                               <th class="heading1" nowrap>Parent Org Office</th>
                                                    <td><input class="form-control" type="text" id="serialnumber" name="serialnumber"  value="${serialnumber}" disabled></td>
                                          
                                                          </tr>
                                                           <tr>
                                                               <th class="heading1" nowrap>Is Super Child</th>
                                              
                                                               <td>
<!--                                                        <input class="form-control" type="text" id="super" name="super" value="" size="45" disabled>-->
                                             <input type="radio" id="supery" name="super" value="Y" disabled>
                                                      <label for="Yes">Yes</label><br>
                                                      <input type="radio" id="supern" name="super" value="N" disabled>
                                                      <label for="No">No</label><br>
                                                       
                                                   </td>
                                                               

   
                                                          </tr>
                                                           <tr>
                                                 <td><input class="form-control" type="text" id="generation" name="generation" value="" size="45" disabled hidden></td>
                                                </tr>
                                                <tr>
                                                    <td align='center' colspan="8">
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
                                                <input type="hidden" id="org_name" name="org_name" value="${org_name}">                                       
                                                <input type="hidden" id="clickedButton" value="">
                                            </table>
                                        </form>
                                                
                                                
                                                
                                                
                                                
                                                
                                                
                                                
                                                
                                                
                                                
                                                
                                    </div>
                        

                    </DIV>
              
            <%@include file="/layout/footer.jsp" %>
       
    </body>
</html>