<%-- 
    Document   : emergencyContact
    Created on : 18 Apr, 2020, 1:32:46 PM
    Author     : DELL
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
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
    </head>
    <body>
        <DIV class="container">
                                    <form name="form1" method="POST" action="personCount.do" class="boxeffext">
                                        
                                            <table id="table1" width="600"  border="1"  align="center" class="table table-bordered table-striped boxeffext">
                                               <tr>
                                                    <th class="heading" >S.No.</th>
                                                    <th class="heading" >Emergency Name</th>
                                                    <th class="heading" >Emergency Number</th>
                                                    <th class="heading" >Key Person Name</th>
                                                    <th class="heading" >Emergency key person</th>
                                                    
                                                </tr>
                                                <c:forEach var="emer" items="${requestScope['emerList']}"  varStatus="loopCounter">
                                                  <tr  class="${loopCounter.index % 2 == 0 ? 'even': 'odd'}" >
                                                        <td  style="display:none">${emer.id}</td>
                                                            <td align="center">${lowerLimit - noOfRowsTraversed + loopCounter.count}</td>
                                                        <td>${emer.name}</td>
                                                        <td>${emer.number}</td>
                                                        <td>${emer.k_name}</td>
                                                        <td>${emer.k_emer_name}</td>
                                                        
                                                    </tr>
                                                 
                                                </c:forEach>
                                            </table>
                                    </form>
        </div>
    </body>
</html>
