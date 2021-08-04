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
        <title>Data Entry: Organization Type </title>
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
                 
              $("#searchOrgType").autocomplete({
                    
            source: function (request, response) {
              var random = document.getElementById("searchOrgType").value;
                $.ajax({
                    url: "orgTypeCont.do",
                    dataType: "json",
                    data: {action1: "getOrganisationType",str:random},
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
                $('#searchOrgType').val(ui.item.label); // display the selected text
                return false;
            }
        });
        
        
         $("#hierarchysearch").autocomplete({
                    
            source: function (request, response) {
              var random = document.getElementById("hierarchysearch").value;
                $.ajax({
                    url: "orgTypeCont.do",
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
                $('#hierarchysearch').val(ui.item.label); // display the selected text
                return false;
            }
        });
        
        
        
        
        
        
        
         $("#searchgeneration").autocomplete({
                    
            source: function (request, response) {
              var random = document.getElementById("searchgeneration").value;
                $.ajax({
                    url: "orgTypeCont.do",
                    dataType: "json",
                    data: {action1: "getgeneration",str:random},
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
        
        
        
         $("#p_ot").autocomplete({
                    
            source: function (request, response) {
             
              var random = document.getElementById("p_ot").value;
              var o_t = document.getElementById("org_type_name").value;
              var generation = document.getElementById("generation").value;
              var edit = editable;
             
              if(document.getElementById("supern").value=='N')
              {
              var superr ="N";
          }
          else
          {
                 var superr = "Y";
              
          }
                $.ajax({
                    url: "orgTypeCont.do",
                    dataType: "json",
                    data: {action1: "getParentOrganisationType",str:random,action2:o_t,action3:superr,edit:edit,generation:generation},
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
                $('#p_ot').val(ui.item.label); // display the selected text
                return false;
            }
        });
        
    });
    
           var editable=false;   
            function makeEditable(id) {
                document.getElementById("org_type_name").disabled = false;
                document.getElementById("description").disabled = false;
                 document.getElementById("p_ot").disabled = false;
               
                 
                if(id == 'new') {
                      editable="false";
                    //document.getElementById("message").innerHTML = "";      // Remove message
                    $("#message").html("");
                    document.getElementById("organisation_type_id").value = "";
                    document.getElementById("edit").disabled = true;
                    document.getElementById("delete").disabled = true;
                    document.getElementById("save_As").disabled = true;
                    document.getElementById("supern").disabled = false;
                 document.getElementById("supery").disabled = false;
                    setDefaultColor(document.getElementById("noOfRowsTraversed").value, 3);
                    document.getElementById("org_type_name").focus();
                }
                if(id == 'edit'){
                     editable="true";
                    document.getElementById("save_As").disabled = false;
                    document.getElementById("delete").disabled = false;
                    document.getElementById("supern").disabled = false;
                 document.getElementById("supery").disabled = false;
                }
                document.getElementById("save").disabled = false;
            }
            function setDefaultColor(noOfRowsTraversed, noOfColumns) {
                for(var i = 0; i < noOfRowsTraversed; i++) {
                    for(var j = 1; j <= noOfColumns; j++) {
                        document.getElementById("t1c" + (i * noOfColumns + j)).bgColor = "";     // set the default color.
                    }
                }
            }
            function fillColumns(id) {
                var noOfRowsTraversed = document.getElementById("noOfRowsTraversed").value;
                var noOfColumns = 6;
                var columnId = id;                              <%-- holds the id of the column being clicked, excluding the prefix t1c e.g. t1c3 (column 3 of table 1). --%>
                columnId = columnId.substring(3, id.length);    <%-- for e.g. suppose id is t1c3 we want characters after t1c i.e beginIndex = 3. --%>
                var lowerLimit, higherLimit, rowNo = 0;
                for(var i = 0; i < noOfRowsTraversed; i++) {
                    lowerLimit = i * noOfColumns + 1;       // e.g. 11 = (1 * 10 + 1)
                    higherLimit = (i + 1) * noOfColumns;    // e.g. 20 = ((1 + 1) * 10)
                    rowNo++;
                    if((columnId >= lowerLimit) && (columnId <= higherLimit)) break;
                }
                setDefaultColor(noOfRowsTraversed, noOfColumns);        // set default color of rows (i.e. of multiple coloumns).
                var t1id = "t1c";       // particular column id of table 1 e.g. t1c3.

                document.getElementById("organisation_type_id").value= document.getElementById("organisation_type_id" + rowNo).value;
                document.getElementById("org_type_name").value = document.getElementById(t1id +(lowerLimit+1)).innerHTML;
                  document.getElementById("p_ot").value = document.getElementById(t1id +(lowerLimit+2)).innerHTML;
                    if(document.getElementById(t1id + (lowerLimit + 3)).innerHTML=='N')
                 {
                   document.getElementById("supern").checked = document.getElementById(t1id + (lowerLimit + 3)).innerHTML;
                    document.getElementById("supery").unchecked = document.getElementById(t1id + (lowerLimit + 3)).innerHTML;
               } 
               else
               {
                    document.getElementById("supery").checked = document.getElementById(t1id + (lowerLimit + 3)).innerHTML;
                      document.getElementById("supern").unchecked = document.getElementById(t1id + (lowerLimit + 3)).innerHTML;
               }
                document.getElementById("description").value = document.getElementById(t1id +(lowerLimit+4)).innerHTML;
                  document.getElementById("generation").value = document.getElementById(t1id +(lowerLimit+5)).innerHTML;
                for(var i = 0; i < noOfColumns; i++) {
                    document.getElementById(t1id + (lowerLimit + i)).bgColor = "#d0dafd";        // set the background color of clicked row to yellow.
                }
                document.getElementById("edit").disabled = false;
                if(!document.getElementById("save").disabled)   // if save button is already enabled, then make edit, and delete button enabled too.
                {
                    document.getElementById("save_As").disabled = true;
                    document.getElementById("delete").disabled = false;
                }
                //  document.getElementById("message").innerHTML = "";      // Remove message
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
                    var org_type_name = document.getElementById("org_type_name").value;
                    if(myLeftTrim(org_type_name).length == 0) {
                        // document.getElementById("message").innerHTML = "<td colspan='5' bgcolor='coral'><b>Organisation Type Name is required...</b></td>";
                        $("#message").html("<td colspan='5' bgcolor='coral'><b>Organisation Type Name is required...</b></td>");
                        document.getElementById("org_type_name").focus();
                        return false; // code to stop from submitting the form2.
                    }
                    if(result == false)    // if result has value false do nothing, so result will remain contain value false.
                    {
                       
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
              function displayOrgnList(id){
                var queryString;
                    var active = document.getElementById("activee").value;
                    var searchgeneration = document.getElementById("searchgeneration").value;
                var searchOrgType=document.getElementById("searchOrgType").value;
                var hierarchysearch = document.getElementById("hierarchysearch").value;
                if(id=='viewPdf')
                {
                queryString = "requester=PRINT&searchOrgType="+searchOrgType+"&active="+active+"&searchgeneration="+searchgeneration+"&hierarchysearch="+hierarchysearch;
           
        }
               else
               {queryString = "requester=PRINTXls&searchOrgType="+searchOrgType;
               }var url = "orgTypeCont.do?"+queryString;
                popupwin = openPopUp(url, "Organisation Type Report", 600, 900);
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
                                 <h1>Organisation Type</h1>
                             </div>
                             
                                                <div class="container">
                          
                                    <form name="form0" class="form-group row boxeffext" method="POST" action="orgTypeCont.do" >
                                        <table  align="center"  class="table table-bordered table-striped" width="600">
                                            <tr>
                                                <th>Org Type</th>
                                                <td><input class="form-control" type="text" id="searchOrgType" name="searchOrgType" value="${searchOrgType}" size="30" ></td>
                                             <th>Generation</th>
                                                <td><input class="form-control" type="text" id="searchgeneration" name="searchgeneration" value="${searchgeneration}" size="30" ></td>
                                             
                                                 <th>Org Type Hierarchy</th>
                                                <td><input class="form-control" type="text" id="hierarchysearch" name="hierarchysearch" value="${hierarchysearch}" size="30" ></td>
                                                
                                                
                                                <td><input class="btn btn-primary" type="submit" name="task" id="searchIn" value="Search"></td>
                                                <td><input class="btn btn-success" type="submit" name="task" id="showAllRecords" value="Show All Records"> </td>                                               
                                                <td> <input type="button" class="btn btn-warning" id="viewPdf" name="viewPdf" value="PDF" onclick="displayOrgnList(id)"></td>
                                                <td> <input type="button" class="btn btn-info" id="viewXls" name="viewXls" value="Excel" onclick="displayOrgnList(id)">
                                                      <input type="hidden" id="activee" name="active" value="${active}">
                                                </td>

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
                                    <form name="form1" method="POST" action="orgTypeCont.do" class="boxeffext ">
                                        
                                            <table id="table1" width="600"  border="1"  align="center" class="table table-bordered table-striped">
                                                <tr>
                                                    <th class="heading">S.No.</th>
                                                    <th class="heading">Organization Type Name</th>
                                                     <th  class="heading">Parent Organization Type Name</th>
                                                        <th  class="heading">Is Super Child</th>
                                                    <th  class="heading">Organization Description</th>
                                                     <th  class="heading">Generation</th>
                                                    
                                                </tr>
                                                <c:forEach var="orgType" items="${requestScope['orgTypeList']}"  varStatus="loopCounter">
                                                    <tr  class="${loopCounter.index % 2 == 0 ? 'even': 'odd'}" >
                                                        <td id="t1c${IDGenerator.uniqueID}" onclick="fillColumns(id)" align="center">
                                                            <input type="hidden" id="organisation_type_id${loopCounter.count}" value="${orgType.organisation_type_id}">${lowerLimit - noOfRowsTraversed + loopCounter.count}
                                                        </td>
                                                        <td id="t1c${IDGenerator.uniqueID}" class="new_input" onclick="fillColumns(id)" >${orgType.org_type_name}</td>
                                                          <td id="t1c${IDGenerator.uniqueID}" class="new_input" onclick="fillColumns(id)" >${orgType.p_ot}</td>
                                                           <td id="t1c${IDGenerator.uniqueID}" class="new_input" onclick="fillColumns(id)" >${orgType.supper}</td>
                                                        <td id="t1c${IDGenerator.uniqueID}" class="new_input" onclick="fillColumns(id)">${orgType.description}</td>
                                                           <td id="t1c${IDGenerator.uniqueID}" class="new_input" onclick="fillColumns(id)">${orgType.generation}</td>
                                                    </tr>
                                                </c:forEach>
                                                <tr>
                                                    <td align='center' colspan="6">
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
                                                <input  type="hidden" id="searchOrgType" name="searchOrgType" value="${searchOrgType}" >
                                            </table>
                                    </form>
                      </DIV>
                                            
                                    <div class="container">
                                        <form name="form2" method="POST" class="form-group row boxeffext" action="orgTypeCont.do" onsubmit="return verify()" >
                                            <table id="table2"  class="table table-bordered table-striped" border="0"  align="center" width="600">
                                                <tr id="message">
                                                    <c:if test="${not empty message}">
                                                        <td colspan="2" bgcolor="${msgBgColor}"><b>Result: ${message}</b></td>
                                                    </c:if>
                                                </tr>
                                                <tr>
                                                    <th class="heading1">Organization Type Name</th>
                                                    <td>
                                                        <input   class="form-control" type="hidden" id="organisation_type_id" name="organisation_type_id" value="" >
                                                        <input   class="form-control" type="text" id="org_type_name" name="org_type_name" value="" size="40" disabled>
                                                    </td>
                                                </tr>
                                                
                                                <tr>
                                                    <th class="heading1">Parent Organization Type Name</th><td><input  class="form-control" type="text" id="p_ot" name="p_ot" value="" size="40" disabled></td>
                                                </tr>
                                                
                                                <tr>
                                                    <th class="heading1">Is super child</th>
                                                    <td>
                                                      
<!--                                                        <input class="form-control" type="text" id="super" name="super" value="" size="45" disabled>-->
                                             <input type="radio" id="supery" name="super" value="Y" disabled>
                                                      <label for="Yes">Yes</label><br>
                                                      <input type="radio" id="supern" name="super" value="N" disabled>
                                                      <label for="No">No</label><br>
                                                       
                                                   </td>>
                                                   
                                                </tr>
                                                
                                                <tr>
                                                    <th class="heading1">Type Description</th><td><input  class="form-control" type="text" id="description" name="description" value="" size="40" disabled></td>
                                                </tr>
                                                     <tr>
                                                  <td><input  class="form-control" type="hidden" id="generation" name="generation" value="" size="40" disabled></td>
                                                </tr>
                                                
                                                
                                                <tr>
                                                    <td align='center' colspan="2">
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
                       

                    
           
                        
        
   
                                                </div>
                                                <%@include file="/layout/footer.jsp" %>
    </body>
</html>