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
        <title>FormData </title>
     <link rel="stylesheet" href="//code.jquery.com/ui/1.10.4/themes/smoothness/jquery-ui.css">

<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">

<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js" integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q" crossorigin="anonymous"></script>

<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js" integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl" crossorigin="anonymous"></script>


 <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
   
   
   

<script  src="/JS/tarunvalidation.js" type="text/javascript"></script>
<script src="//code.jquery.com/jquery-1.10.2.js"></script>
<script src="//code.jquery.com/ui/1.10.4/jquery-ui.js"></script>


<script src="https://cdnjs.cloudflare.com/ajax/libs/moment.js/2.21.0/moment.min.js" type="text/javascript"></script>
<link rel="stylesheet" href="time/bootstrap-datetimepicker.min.css">
<script  type="text/javascript" src="time/moment.min.js"></script>
<script  type="text/javascript" src="time/bootstrap-datetimepicker.min.js"></script>
        <script type="text/javascript" language="javascript">
           
$(document).ready(function(){

var current_fs, next_fs, previous_fs; //fieldsets
var opacity;
var current = 1;
var steps = $("fieldset").length;



setProgressBar(current);


 

// function openMapForCord() {
//              debugger;
//                var url="generalCont.do?task=GetCordinates4";//"getCordinate";
//                popupwin = openPopUp(url, "",  600, 630);
//            };

 $("#get_cordinate").click(function(){
                debugger;
                var url="generalCont.do?task=GetCordinates4";//"getCordinate";
                popupwin = openPopUp(url, "",  600, 630);
     
 });

   function openPopUp(url, window_name, popup_height, popup_width) {
                var popup_top_pos = (screen.availHeight / 2) - (popup_height / 2);
                var popup_left_pos = (screen.availWidth / 2) - (popup_width / 2);
                var window_features = "left=" + popup_left_pos + ", top=" + popup_top_pos + ", width=" + popup_width + ", height=" + popup_height + ", resizable=yes, scrollbars=yes, location=0, menubar=no, status=no, dependent=yes";

                return window.open(url, window_name, window_features);
            }

  $("#unknownorg").hide();
  
   
//  $('#save').click(function(){
//      debugger;
//    var name=$("#key_person_name").val();
//        if(name === "")
//        {
//            $("#show_error").html("** username should not be empty.");
//            $("#show_error").css("color","red");
//            return false;
//        }
//        
//  });
          

  
    $('input[type="checkbox"]').click(function(){
          debugger;
        var inputValue = $(this).attr("value");
        if(inputValue === "orginfo")
        {
            $("#" + inputValue).toggle();
            $("#unknownorg").toggle();
        }
        else
        {
            $("#" + inputValue).toggle();
        }
    

    });


$( "#id_proof" ).change(function() {
    
       var kpimage=document.getElementById("design_name").value;
        var idimage=document.getElementById("id_proof").value;
           if(kpimage === idimage)
           {
               alert("Both images cannot be same");
               document.getElementById("id_proof").value="";
               
           }

});




$( "#nooffamilymembers" ).change(function() {
  
  var nofamily=document.getElementById("nooffamilymembers").value;
  var wrapper2 = $(".tabledata");
  wrapper2.html("");
  
  for(var f=0;f<nofamily; f++)
  {
     var txt = "";
     txt += '<tr><td>';
     txt += '<label class="fieldlabels">Name: *</label> ';
     txt += '<input type="text" name="relname'+f+'" id="relname'+f+'" placeholder="Name" />';
     txt += '</td><td>';
     txt += '<label class="fieldlabels">Contact Number.: *</label> ';
     txt += '<input type="text" name="phno'+f+'" id="phno'+f+'" placeholder="Contact No." /> ';
     txt += '</td><td>';
     
     txt += '<label class="fieldlabels">Email: *</label> ';
     txt += '<input type="text" name="relemail'+f+'" id="relemail'+f+'" placeholder="email." />';
     txt += '</td><td>';
      txt += '<label class="fieldlabels">Date Of Birth: *</label> ';
     txt += '<input type="date" name="reldob'+f+'" id="reldob'+f+'" placeholder="DOB." />';
     txt += '</td><td>';
     
      
      txt += '<label class="fieldlabels">Relation: *</label> ';
    
      txt += '<select class="dropdown-toggle form-control" id="relation'+f+'" name="relation'+f+'" value="">';
     txt += '<option>---Select--- </option>';
     txt += '<option style="text-align: center">Brother</option>';
     txt += '<option style="text-align: center">Sister</option>';
     txt += '<option style="text-align: center">Father</option>';
     txt += '<option style="text-align: center">Mother</option>';
     txt += '<option style="text-align: center">Son</option>';
     txt += '<option style="text-align: center">Daughter</option>';
     txt += '<option style="text-align: center">Husband</option>';
     txt += '<option style="text-align: center">Wife</option>';
     txt += ' </select>';
    
     
     
     
     txt += '</td></tr>';
     
    txt += '<tr><td>';
     txt += '<label class="fieldlabels">Father Name: *</label> ';
     txt += '<input type="text" name="relfathername'+f+'" id="relfathername'+f+'" placeholder="Father Name" />';
     txt += '</td><td>';
     
     txt += '<label class="fieldlabels">Blood Group *</label> ';
  
     txt += '<select class="dropdown-toggle form-control" id="bldgroup'+f+'" name="bldgroup'+f+'" value="">';
     txt += '<option>---Select--- </option>';
     txt += '<option style="text-align: center">A+</option>';
     txt += '<option style="text-align: center">A-</option>';
     txt += '<option style="text-align: center">B+</option>';
     txt += '<option style="text-align: center">B-</option>';
     txt += '<option style="text-align: center">AB+</option>';
     txt += '<option style="text-align: center">AB-</option>';
     txt += '<option style="text-align: center">O+</option>';
     txt += '<option style="text-align: center">O-</option>';
     txt += ' </select>';
     txt += '</td><td>';
     
     
     txt += '<label class="fieldlabels">Id Type.: *</label> ';
     txt += '<select class="dropdown-toggle form-control" id="relidtype'+f+'" name="relidtype'+f+'" value="">';
     txt += '<option>---Select--- </option>';
     txt += '<option style="text-align: center">Pan</option>';
     txt += '<option style="text-align: center">Aadhar</option>';
     txt += ' </select>';
     
        
     txt += '</td><td>';
     txt += '<label class="fieldlabels">Id Number.: *</label> ';
     txt += '<input type="text" name="relidnumber'+f+'" id="relidnumber'+f+'" placeholder="Id Number" /> ';
     txt += '</td><td>';
      txt += '<label class="fieldlabels">Gender: *</label> ';
     txt += '<select class="dropdown-toggle form-control" id="relgender'+f+'" name="relgender'+f+'" value="">';
     txt += '<option>---Select--- </option>';
     txt += '<option style="text-align: center">Male</option>';
     txt += '<option style="text-align: center">Female</option>';
     txt += ' </select>';
      txt += '</td></tr>';
     
     
                                               
        $(wrapper2).append(txt);   
    }
  
});

$(".next").click(function(){
//debugger;
// var name=$("#key_person_name").val();
//        if(name === "")
//        {
//            $("#show_error").html("** username should not be empty.");
//            $("#show_error").css("color","red");
//            return false;
//        }
        
        


current_fs = $(this).parent();
next_fs = $(this).parent().next();

//Add Class Active
$("#progressbar li").eq($("fieldset").index(next_fs)).addClass("active");

//show the next fieldset
next_fs.show();
//hide the current fieldset with style
current_fs.animate({opacity: 0}, {
step: function(now) {
// for making fielset appear animation
opacity = 1 - now;

current_fs.css({
'display': 'none',
'position': 'relative'
});
next_fs.css({'opacity': opacity});
},
duration: 500
});
setProgressBar(++current);
      
});

$(".previous").click(function(){

current_fs = $(this).parent();
previous_fs = $(this).parent().prev();

//Remove class active
$("#progressbar li").eq($("fieldset").index(current_fs)).removeClass("active");

//show the previous fieldset
previous_fs.show();

//hide the current fieldset with style
current_fs.animate({opacity: 0}, {
step: function(now) {
// for making fielset appear animation
opacity = 1 - now;

current_fs.css({
'display': 'none',
'position': 'relative'
});
previous_fs.css({'opacity': opacity});
},
duration: 500
});
setProgressBar(--current);
});

 $("#verifynumber").click(function()
                {
                    debugger;
                    alert("no number insaerted");
                });



function setProgressBar(curStep){
var percent = parseFloat(100 / steps) * curStep;
percent = percent.toFixed();
$(".progress-bar")
.css("width",percent+"%")
}





$(".submit").click(function(){
return false;
})


 

});
   

            
        </script>
                  <style>
           body {
               background-color: #E7475E;
/*                background-image: linear-gradient(to right, #E7475E , #EC9BA7);*/
           }


           
  * {
    margin: 0;
    padding: 0
}

html {
    height: 100%
}

p {
    color: grey
}

#heading {
    text-transform: uppercase;
    color: #E7475E;
    font-weight: normal
}

#msform {
    text-align: center;
    position: relative;
    margin-top: 20px
}

#msform fieldset {
    background: white;
    border: 0 none;
    border-radius: 0.5rem;
    box-sizing: border-box;
    width: 100%;
    margin: 0;
    padding-bottom: 20px;
    position: relative
}

.form-card {
    text-align: left
}

#msform fieldset:not(:first-of-type) {
    display: none
}

#msform input,
#msform textarea {
    padding: 8px 15px 8px 15px;
    border: 1px solid #ccc;
    border-radius: 0px;
    margin-bottom: 25px;
    margin-top: 2px;
    width: 100%;
    box-sizing: border-box;
    font-family: montserrat;
    color: #2C3E50;
    background-color: #ECEFF1;
    font-size: 16px;
    letter-spacing: 1px
}

#msform input:focus,
#msform textarea:focus {
    -moz-box-shadow: none !important;
    -webkit-box-shadow: none !important;
    box-shadow: none !important;
    border: 1px solid #E7475E;
    outline-width: 0
}

#msform .action-button {
    width: 100px;
    background: #E7475E;
    font-weight: bold;
    color: white;
    border: 0 none;
    border-radius: 0px;
    cursor: pointer;
    padding: 10px 5px;
    margin: 10px 0px 10px 5px;
    float: right
}

#msform .action-button:hover,
#msform .action-button:focus {
    background-color: #311B92
}

#msform .action-button-previous {
    width: 100px;
    background: #616161;
    font-weight: bold;
    color: white;
    border: 0 none;
    border-radius: 0px;
    cursor: pointer;
    padding: 10px 5px;
    margin: 10px 5px 10px 0px;
    float: right
}

#msform .action-button-previous:hover,
#msform .action-button-previous:focus {
    background-color: #000000
}

.card {
    z-index: 0;
    border: none;
    position: relative
}

.fs-title {
    font-size: 25px;
    color: #E7475E;
    margin-bottom: 15px;
    font-weight: normal;
    text-align: left
}

.purple-text {
    color: #E7475E;
    font-weight: normal
}

.steps {
    font-size: 25px;
    color: gray;
    margin-bottom: 10px;
    font-weight: normal;
    text-align: right
}

.fieldlabels {
    color: gray;
    text-align: left
}

#progressbar {
    margin-bottom: 30px;
    overflow: hidden;
    color: lightgrey
}

#progressbar .active {
    color: #E7475E
}

#progressbar li {
    list-style-type: none;
    font-size: 15px;
    width: 16.6%;
    float: left;
    position: relative;
    font-weight: 400
}

#progressbar #account:before {
    font-family: FontAwesome;
    content: "\f13e"
}
#progressbar #family:before {
    font-family: FontAwesome;
    content: "\f13e"
}
#progressbar #employment:before {
    font-family: FontAwesome;
    content: "\f13e"
}

#progressbar #emergency:before {
    font-family: FontAwesome;
    content: "\f007"
}

#progressbar #payment:before {
    font-family: FontAwesome;
    content: "\f030"
}

#progressbar #confirm:before {
    font-family: FontAwesome;
    content: "\f00c"
}

#progressbar li:before {
    width: 50px;
    height: 50px;
    line-height: 45px;
    display: block;
    font-size: 20px;
    color: #ffffff;
    background: lightgray;
    border-radius: 50%;
    margin: 0 auto 10px auto;
    padding: 2px
}

#progressbar li:after {
    content: '';
    width: 100%;
    height: 2px;
    background: lightgray;
    position: absolute;
    left: 0;
    top: 25px;
    z-index: -1
}

#progressbar li.active:before,
#progressbar li.active:after {
    background: #E7475E
}

.progress {
    height: 20px
}

.progress-bar {
    background-color: #E7475E
}

.fit-image {
    width: 100%;
    object-fit: cover
}
            
        </style>    
    </head>
    <script>
        var i = 0;
        
        function myFun(value)
            {
                debugger;
                var req = null;
                if (req !== null)
                    req.abort();
                var random = document.getElementById("mobile_no1").value;
                //  alert(random);

                if (random.length >= 10)
                {

                    req = $.ajax({
                        type: "POST",
                        url: "personCount.do",
                        data: {action1: "mobile_number",
                            str: random},
                        dataType: "json",
                        success: function (response_data) {

                            console.log(response_data);


                            if (response_data.list[0] === ("Mobile no exist"))
                            {
                                alert(response_data.list[0]);
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
            $(document).ready(function () {
                var max_fields = 10;
                var wrapper = $(".container12");
                var add_button = $(".add_form_field2");


                var x = 1;
                var em_name;
                $("#emerg").click(function (e) {
                    i++;
                     debugger;
                    e.preventDefault();
                    if (x < max_fields) {
                        x++;
                        $(wrapper).append('<tr> <th class="heading1">Emergency Contact Name' + i + '</th> <td><input class="form-control" type="text" id="emergency_name' + i + '" name="emergency_name' + i + '" value="" ></td> <th class="heading1" >Emergency Contact Number' + i + '</th> <td><input class="form-control" type="text" id="emergency_number' + i + '" name="emergency_number' + i + '" value="" > </td> <tr><a href="#" class="delete">Delete</a></tr></tr>'); //add input box
                    } else {
                        alert('You Reached the limits');
                    }
                    document.getElementById("i").value = i;
                });

                debugger;
                $(wrapper).on("click", ".delete", function (e) {
                    e.preventDefault();
                    $(this).parent('td').remove();
                    x--;
                });
                
                 $("#unofficecity").autocomplete({

                    source: function (request, response) {
                        var random = document.getElementById("city_name").value;
                        $.ajax({
                            url: "personCount.do",
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
                        $('#unofficecity').val(ui.item.label); // display the selected text
                        return false;
                    }
                });
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

            
                 $("#city_name").autocomplete({

                    source: function (request, response) {
                        var random = document.getElementById("city_name").value;
                        $.ajax({
                            url: "personCount.do",
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
                            url: "personCount.do",
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
                
                 $("#undesignation").autocomplete({

                    source: function (request, response) {
                        var random = document.getElementById("undesignation").value;
                        var org_office_name = document.getElementById("organisation_type").value;
                        $.ajax({
                            url: "personCount.do",
                            dataType: "json",
                            data: {action1: "getunknownDesignation", str: random, action2: org_office_name},
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
                        $('#undesignation').val(ui.item.label); // display the selected text
                        return false;
                    }
                });
                
                
                 $("#office_code").autocomplete({

                    source: function (request, response) {
                        var random = document.getElementById("office_code").value;
                        $.ajax({
                            url: "personCount.do",
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
                
                
                
//                $("#serialnumber").autocomplete({
//                    
//                source: function (request, response) {
//                var code = document.getElementById("unorgname").value;
//                var random = document.getElementById("serialnumber").value;
//                var generation = document.getElementById("generation").value;
//                var designation = document.getElementById("org_office_name").value;
//                
//                $.ajax({
//                    url: "organisationCont.do",
//                    dataType: "json",
//                    data: {action1: "getParentOrgOffice",
//                           action2: code,str:random,
//                           action3 :editable,       
//                           action4: generation,
//                           action5:designation
//                           },
//                           
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
//                $('#serialnumber').val(ui.item.label); // display the selected text
//                return false;
//            }
//        });  

                $("#org_office_name").autocomplete({

                    source: function (request, response) {
                        var officecode = document.getElementById("office_code").value;
                        var random = document.getElementById("org_office_name").value;
                        $.ajax({
                            url: "personCount.do",
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
               
            });

        
        
    </script>
 



    <body>
         <div class="container-fluid">
            <%@include file="../layout/header.jsp" %>
                           
                               
                                 
  <div class="container-fluid">
    <div class="row justify-content-center">
        <div class="col-11 col-sm-10 col-md-9 col-lg-8 col-xl-6 text-center p-0 mt-3 mb-2">
            <div class="card px-0 pt-4 pb-0 mt-3 mb-3">
                <h2 id="heading">Sign Up Your User Account</h2>
                <p>Fill all form field to go to next step</p>
                <form id="msform" method="POST" action="formdataCont.do" encType="multipart/form-data" >
                    <!-- progressbar -->
                    <ul id="progressbar">
                        <li class="active" id="account"><strong>Personal</strong></li>
                        <li id="family"><strong>Family</strong></li>
                        <li id="employment"><strong>Employment</strong></li>
                        <li id="emergency"><strong>Emergency</strong></li>
                        <li id="payment"><strong>Image</strong></li>
                        <li id="confirm"><strong>Finish</strong></li>
                    </ul>
                    <div class="progress">
                        <div class="progress-bar progress-bar-striped progress-bar-animated" role="progressbar" aria-valuemin="0" aria-valuemax="100"></div>
                    </div> <br>
                    <div class="container">
                    <!-- fieldsets -->
                    
                    
                    <fieldset>
                        <div class="form-card">
                            <div class="row">
                                <div class="col-7">
                                    <h2 class="fs-title">Personal Information :</h2>
                                </div>
                                <div class="col-5">
                                    <h2 class="steps">Step 1 - 6</h2>
                                </div>
                            </div>
                            <input type="hidden" id="key_person_id" name="key_person_id" value="">
                                    <table class="table table-responsive">
                                         <tr>
                                             <td>
                                                     <label class="fieldlabels">Data Entry:</label>
                                                  
                                                        &nbsp;&nbsp; <strong>Default Value</strong>&nbsp;&nbsp;<input type="checkbox" class="checkbox-inline" id="default" name="default" onclick="setDefaultValues(id)"></td>
                                                     
                                                </tr>
                                        <tr>
                                            <td>
                                                <label class="fieldlabels">Username: *</label> <input type="text" class="form-control" id="key_person_name" name="key_person_name" placeholder="UserName"    />
                                                <div  id="usercheck"></div>
                                             
                                            </td>
                                            <td>
                                                <label class="fieldlabels">Email: *</label> <input id="email_id1" name="email_id1" type="email" placeholder="Email Id" autocomplete="off" />
                                                <div  id="emailcheck"></div>
                                            </td>
                                            
                                        </tr>
                                        <tr>
                                            <td>
                                                <label class="fieldlabels">Mobile Number *  
<!--                                                    <input type="button" class="btn btn-warning btn-sm" id="verifynumber" value="Verify"/>   -->
                                                </label> 
                                                <input type="text" name="mobile_no1" id="mobile_no1" placeholder="Mobile Number" onkeyup="myFun(id)" maxlength="10"/>
                                                <div  id="mobilecheck"></div>
                                            </td>
                                            <td>
                                                <label class="fieldlabels">Landline Number: *</label> <input type="text" id="landline_no1" name="landline_no1" placeholder="Landline Number" />
                                            </td>
                                            
                                        </tr>
                                        <tr>
                                            <td>
                                                <label class="fieldlabels">Password: *</label> <input type="password" id="password" name="password" placeholder="Password" />
                                                <div  id="passcheck"></div>
                                            </td>
                                            <td>
                                                <label class="fieldlabels">Confirm Password: *</label> <input type="password"  id="conpassword" name="cpwd" placeholder="Confirm Password" />
                                                <div  id="conpasscheck"></div>
                                            </td>
                                            
                                        </tr>
                                        <tr>
                                            <td>
                                                 <label class="fieldlabels">Father's Name *</label> <input type="text" id="father_name" name="father_name" placeholder="FatherName" />
                                                 <div  id="fathercheck"></div>
                                            </td>
                                            <td>
                                                <label class="fieldlabels">Date Of Birth *</label> <input type="date" id="date_of_birth" name="date_of_birth" placeholder="DOB" />
                                            </td>
                                            
                                        </tr>
                                        <tr>
                                            <td>
                                                <label class="fieldlabels">ID Type : *</label> 
                                                
                                                <select class="ui dropdown form-control" name="id_type" id="id_type" >
                                                                  <option>---Select--- </option>
                                                                  <option value="adhaar">Adhaar</option>
                                                                  <option value="Pan">Pan</option>
                                                                  
                                                </select>
                                            </td>
                                            <td>
                                                <label class="fieldlabels">ID Serial Number: *</label> <input type="text" id="id_no" name="id_no" placeholder="Id Number" />
                                            </td>
                                            
                                        </tr>
                                        <tr>
                                            <td>
                                                <label class="fieldlabels">Blood Group *</label> 
                                                <select class="ui dropdown form-control" name="blood" id="blood" >
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
                                            </td>
                                            <td>
                                                <table>
                                                    <tr>
                                                        <td colspan="4">
                                                            <label class="fieldlabels">Gender *</label> 
                                                        </td>
                                                        
                                                    </tr>
                                                    <tr>
                                                        <td>
                                                            <label for="Yes">Male</label>
                                                        </td>
                                                        <td>
                                                            <input type="radio" id="genderm" name="gender" value="M" >
                                                        </td>
                                                        <td>
                                                            <label for="No">Female</label>
                                                        </td>
                                                        <td>
                                                            <input type="radio" id="genderf" name="gender" value="F" >
                                                        </td>
                                                    </tr>
                                                  
                                                    
                                                </table>
                                                  
                                                       
                                                 
                                                       
                                            </td>
                                            
                                        </tr>
                                        <tr>
                                            <td colspan="2">
                                                <label class="fieldlabels">Address: *</label> <input type="text" id="address_line1" name="address_line1" placeholder="Address Line" />
                                            </td>
                                         
                                            
                                        </tr>
                                         <tr>
                                            <td>
                                                <label class="fieldlabels">PinCode *</label> <input type="text" id="pincode" name="pincode" placeholder="Pin Code" />
                                            </td>
                                            <td>
                                                <label class="fieldlabels">City: *</label> <input type="text" id="city_name" name="city_name" placeholder="City" />
                                            </td>
                                            
                                        </tr>
                                        <tr>
                                            <td>
                                                <label class="fieldlabels">Latitude: *</label> <input type="text" name="latitude" id="latitude" placeholder="Lat" />
                                            </td>
                                            <td>
                                                <label class="fieldlabels">Longitude: *</label> <input type="text" name="longitude" id="longitude" placeholder="Long" />
                                                <input  class="btn btn-info" type="button" id="get_cordinate" value="Get Cordinate" style="width:40%" >
                                            </td>
                                            
                                        </tr>
                                       
                                     
                                    </table>
                            
                            
     
                            
                        </div> <input type="button" name="next" class="next action-button" value="Next" />
                    </fieldset>
                    
                    
                    
                    
                    <fieldset>
                        <div class="form-card">
                            <div class="row">
                                <div class="col-7">
                                    <h2 class="fs-title">Family Information:</h2>
                                </div>
                                <div class="col-5">
                                    <h2 class="steps">Step 2 - 6</h2>
                                </div>
                            </div> 
                            <label class="fieldlabels">No of Family Members: *</label>
                            <input type="text" name="nooffamilymembers" id="nooffamilymembers" placeholder="Insert in numbers only" /> 
                            
                            <table class="table table-responsive">
                                
                                <div class="tabledata">
                                    
                                    
                                </div>
<!--                                <tr>
                                    <td>
                                        <label class="fieldlabels">Name: *</label> 
                                         <input type="text" name="name" id="name" placeholder="Name" /> 
                                    </td> 
                                    <td>
                                        <label class="fieldlabels">Contact Number.: *</label> 
                                        <input type="text" name="phno" id="phno" placeholder="Contact No." /> 
                                    </td>
                                    <td>
                                        <label class="fieldlabels">Relation: *</label> 
                                        <input type="text" name="relation" id="relation" placeholder="Alternate Contact No." />
                                    </td>
                                    <td>
                                        <label class="fieldlabels">Emergency Contact: *</label>
                                        <input type="checkbox" name="emergency" id="emergency" />
                                        
                                        
                                    </td>
                                </tr>-->
                                                              
                            </table>
                            
                            
                            
                            
                            
                            
                        </div> 
                        <input type="button" name="next" class="next action-button" value="Next" /> 
                        <input type="button" name="previous" class="previous action-button-previous" value="Previous" />
                    </fieldset>
                    <fieldset>
                        <div class="form-card">
                            <div class="row">
                                <div class="col-7">
                                    <h2 class="fs-title">Employment Information:</h2>
                                </div>
                                <div class="col-5">
                                    <h2 class="steps">Step 3 - 6</h2>
                                </div>
                            </div> 
                          
                            
                            
                            <div class="form-check">
                             <input type="checkbox" class="form-check-input" id="employed"  name="emplyoed" value="showemployment">
                             <label class="form-check-label" for="employed">UnEmployed:</label>
                             </div>
                            <br>
                            <div id="showemployment">
                            <div class="form-check">
                             <input type="checkbox" class="form-check-input" name="notregistered" id="notregistered" value="orginfo">
                             <label class="form-check-label" for="notregistered">Organisation Not registered:</label>
                             </div>
                            <br>
                            <br>
                            <div id="orginfo">
                            <table class="table table-responsive">
                                <tr>
                                    <td>
                                        <label class="fieldlabels">Office Code: *</label> 
                                        <input type="text" name="office_code" id="office_code" placeholder="Office Code" />
                                    </td>
                                    <td>
                                        <label class="fieldlabels">Office Name: *</label> 
                                        <input type="text" name="org_office_name" id="org_office_name" placeholder="Office Name" />
                                    </td>
                                </tr>
                                 <tr>
                                    <td>
                                        <label class="fieldlabels">Employee Id: *</label> 
                                        <input type="text" name="employeeId" id="employeeId"placeholder="Employee Id" />
                                    </td>
                                    <td>
                                        <label class="fieldlabels">Designation: *</label> 
                                        <input type="text" name="designation" id="designation" placeholder="Designation" />
                                    </td>
                                </tr>
                                 
                                
                            </table>
                            </div>
                            <div id="unknownorg" >
                                <table class="table table-responsive">
                                 <tr>
                                     <td colspan="2">
                                        <label class="fieldlabels">Help Us In Getting Your Organisation Registered *</label> 
                                        
                                    </td>
                                   
                                </tr>
                                <tr>
                                    <td>
                                        <label class="fieldlabels">Organisation Type: *</label> 
                                        <input type="text" id="organisation_type" name="organisation_type" placeholder="Organisation Type" />
                                    </td>
                                    <td>
                                        <label class="fieldlabels">Organisation Name: *</label> 
                                        <input type="text" name="unorgname" id="unorgname" placeholder="Organisation Name" />
                                    </td>
                                    <td>
                                        <label class="fieldlabels">Organisation Code: *</label> 
                                        <input type="text" name="unorgcode" id="unorgcode" placeholder="Organisation Code" />
                                    </td>
                                </tr>
                                 <tr>
                                    <td>
                                        <label class="fieldlabels">Office Type: *</label> 
                                        <input type="text" id="office_type" name="office_type" placeholder="Office Type" />
                                    </td>
                                    <td>
                                        <label class="fieldlabels">Office Name: *</label> 
                                        <input type="text" name="unofficename" id="unofficename" placeholder="Office Name" />
                                    </td>
                                    <td>
                                        <label class="fieldlabels">Office Code: *</label> 
                                        <input type="text" name="unofficecode" id="unofficecode" placeholder="Office Code" />
                                    </td>
                                </tr>
                                <tr>
                                    <td>
                                        <label class="fieldlabels">Office Address: *</label> 
                                        <input type="text" id="unofficeadddress" name="unofficeadddress" placeholder="Office Address" />
                                    </td>
                                    <td>
                                        <label class="fieldlabels">Office City: *</label> 
                                        <input type="text" name="unofficecity" id="unofficecity" placeholder="Office City" />
                                    </td>
<!--                                    <td>
                                        <label class="fieldlabels">Parent Org Office: *</label> 
                                        <input type="text" id="serialnumber" name="serialnumber" placeholder=" Parent Office" />
                                    </td>-->
                                </tr>
                                 <tr>
                                    <td>
                                        <label class="fieldlabels">Designation: *</label> 
                                        <input type="text" id="undesignation" name="undesignation" placeholder="Designation" />
                                    </td>
                                    <td>
                                        <label class="fieldlabels">EmployeeId: *</label> 
                                        <input type="text" name="unempid" id="unempid" placeholder="Employee Id" />
                                    </td>
<!--                                    <td>
                                        <label class="fieldlabels">Parent Org Office: *</label> 
                                        <input type="text" id="serialnumber" name="serialnumber" placeholder=" Parent Office" />
                                    </td>-->
                                </tr>
                                 <tr>
                                    <td>
                                        <label class="fieldlabels">Office  Email Id: *</label> 
                                        <input type="text" name="unoffemail" id="unorgemail" placeholder="Office  Email" />
                                    </td>
                                    <td>
                                        <label class="fieldlabels">Office Contact Number: *</label> 
                                        <input type="text" name="unofficemobile" id="unofficemobile" placeholder="Office Number" />
                                    </td>
                                    <td>
                                        <label class="fieldlabels">Office Landline: *</label> 
                                        <input type="text" name="unofficelandline" id="unofficelandline" placeholder="Office Landline" />
                                    </td>
                                </tr>
                                 
                                
                            </table>
                            </div>
                            </div>
                            
                        </div> <input type="button" name="next" class="next action-button" value="Next" /> <input type="button" name="previous" class="previous action-button-previous" value="Previous" />
                    </fieldset>
                    <fieldset>
                        <div class="form-card">
                            <div class="row">
                                <div class="col-7">
                                    <h2 class="fs-title">Emergency Information:</h2>
                                </div>
                                <div class="col-5">
                                    <h2 class="steps">Step 4 - 6</h2>
                                </div>
                            </div>
                            
                            <table>
                                  <tr class="exp">
                                      <input type="hidden" id="noofemergencycontact" name="i" value=${i}>
                                      <th class="heading1" >Emergency Contact Name</th>
                                      <td><input class="form-control" type="text" id="emergency_name" name="emergency_name" value="" size="28" ></td> 
                                       <th class="heading1" >Emergency Contact Number</th>
                                      <td>
                                          <input class="form-control" type="text" id="emergency_number" name="emergency_number" value="" size="28" >
                                          
                                      </td> 

                                  </tr>
                        <tr>
                            <td class="container12" colspan="6">
                                <button class="add_form_field2 btn btn-info" id="emerg">Add Emergency Name and Contact Fields &nbsp; 
                                    <span style="font-size:16px; font-weight:bold;">+ </span>
                                </button>


                            </td>

                        </tr>
                                
                            </table>
                            
                            
                            
                         
                         
                        
                        </div> <input type="button" name="next" class="next action-button" value="Next" /> <input type="button" name="previous" class="previous action-button-previous" value="Previous" />
                    </fieldset>
                    <fieldset>
                        <div class="form-card">
                            <div class="row">
                                <div class="col-7">
                                    <h2 class="fs-title">Image Upload:</h2>
                                </div>
                                <div class="col-5">
                                    <h2 class="steps">Step 5 - 6</h2>
                                </div>
                            </div> 
                            <label class="fieldlabels">Upload Your Photo:</label> 
                            <input type="file" id="design_name" name="design_name" accept="image/*"> 
                            <label class="fieldlabels">Upload Id Photo Photo:</label> 
                            <input type="file" id="id_proof" name="id_proof" accept="image/*">
                        </div> <input type="submit"  class="next action-button" name="task" id="submitbtn" value="Save" /> <input type="button" name="previous" class="previous action-button-previous" value="Previous" />
                    </fieldset>
                    <fieldset>
                        <div class="form-card">
                            <div class="row">
                                <div class="col-7">
                                    <h2 class="fs-title">Finish:</h2>
                                </div>
                                <div class="col-5">
                                    <h2 class="steps">Step 6 - 6</h2>
                                </div>
                            </div> <br><br>
                            <h2 class="purple-text text-center"><strong>SUCCESS !</strong></h2> <br>
                            <div class="row justify-content-center">
                                <div class="col-3"> <img src="https://i.imgur.com/GwStPmg.png" class="fit-image"> </div>
                            </div> <br><br>
                            <div class="row justify-content-center">
                                <div class="col-7 text-center">
                                    <h5 class="purple-text text-center">You Have Successfully Signed Up</h5>
                                </div>
                            </div>
                        </div>
                    </fieldset>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>                         
                                 
                                 
                                 
                                 
                                 
                           

                   
                
                 <%@include file="../layout/footer.jsp" %>
         </DIV>
    </body>
</html>
