<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@include file="../layout/header.jsp" %>
<script src="//code.jquery.com/jquery-1.10.2.js"></script>
<script src="//code.jquery.com/ui/1.10.4/jquery-ui.js"></script>

<style>
    .selected_row {
        font-weight: bolder;
        color: blue;
        border: 3px solid black;
    }
    table.dataTable {      
        border-collapse: collapse;
    }
    .not_in_stock{
        background-color: #d587c8;
        color:white;
    }
    .approved{
        background-color: #2e6da4;
        color:white; 
    }
    .delivered{
        background-color: #5cb85c;
        color:white; 
    }
    .denied{
        background-color: #dc3545;
        color:white; 
    }
    .pending{
        background-color: #ffc107;
        color:white; 
    }
    .PaymentPending{
        background-color: #dc3545;
        color:white; 
    }
    .PaymentSuccess{
        background-color: #5cb85c;
        color:white; 
    }
    .delivery_challan_generated{
        background-color: #df7d35;
        color:white; 
    }

</style>
<script>


    $(document).ready(function () {
        var final_indent_table_id = $('#stock_indent_final_indent_table_id').val();

        var delivery_indent_table_id = $('#delivery_indent_table_id').val();
        var delivery_indent_status = $('#delivery_indent_status').val();
        var final_task = $('#final_task').val();
        if (delivery_indent_table_id != "") {
            $('#status' + delivery_indent_table_id).val("Delivered");
        }

        if (final_task == "Generate Delivery Challan") {
          
            if ((final_indent_table_id != "") && (delivery_indent_table_id == "") && (delivery_indent_status != "Delivered")) {
                var url = "CheckOrderInventoryController?task=GenerateDeliveryChallan&final_indent_table_id=" + final_indent_table_id;
                popupwin = openPopUp(url, "", 600, 1030);
            }
        }
      

        $(document).on('keydown', '.myAutocompleteClass', function () {
            var id = this.id;
            var type;
            if (id.match("^status")) {
                type = "Status";
            } else if (id.match("^purpose")) {
                type = "Purpose";
            }

            var random = this.value;
            $('#' + id).autocomplete({
                source: function (request, response) {
                    $.ajax({
                        url: "CheckOrderInventoryController",
                        dataType: "json",
                        data: {
                            action1: "getParameter",
                            str: random,
                            type: type
                        },
                        success: function (data) {
                            console.log(data);
                            response(data.list);
                        },
                        error: function (error) {
                            console.log(error.responseText);
                            response(error.responseText);
                        }
                    });
                },
                select: function (events, ui) {
                    console.log(ui);
                    $(this).val(ui.item.label); // display the selected text
                    return false;
                }
            });
        });
    });

//function getdeliver(){
//      var url = "CheckOrderInventoryController?task=GetIndentItemsnew&indent_table_id=" + indent_table_id + "&indent_status=" + status;
//        popupwin = openPopUp(url, "", 600, 1030);
//}
    function openPopUpForItems(indent_table_id, status) {
      //  var st=document.getElementById('status').value;
    
        if(status==='Payment Success'){
            
             var url = "CheckOrderInventoryController?task=GetIndentItemsnew&indent_table_id=" + indent_table_id + "&indent_status=" + status;
        popupwin = openPopUp(url, "", 600, 1030);
        }else{
       //      alert("hiiiii-----"+st);     
              var url = "CheckOrderInventoryController?task=GetIndentItems&indent_table_id=" + indent_table_id + "&indent_status=" + status;
        popupwin = openPopUp(url, "", 600, 1030);
      }
      

    }

    function openPopUp(url, window_name, popup_height, popup_width) {
        var popup_top_pos = (screen.availHeight / 2) - (popup_height / 2);
        var popup_left_pos = (screen.availWidth / 2) - (popup_width / 2);
        var window_features = "left=" + popup_left_pos + ", top=" + popup_top_pos + ", width=" + popup_width + ", height=" + popup_height + ", resizable=yes, scrollbars=yes, location=0, menubar=no, status=no, dependent=yes";
        return window.open(url, window_name, window_features);
    }

    function searchIndentStatusWise(status) {
        var url = "CheckOrderInventoryController?action1=searchIndentStatusWise&status=" + status;
        window.open(url, "_self");
    }


</script>


<section>
    <div class="container-fluid page_heading sectionPadding35">
        <h1>Check Inventory</h1>
    </div>
</section>

<section>
    <div class="container organizationBox">
        <div class="headBox" style="background-color: #6D9FBD">
            <c:forEach var="beanType" items="${requestScope['status_list']}"
                       varStatus="loopCounter">
                <label style="color:white;margin-left: 20px">${beanType.status}</label>
                <input type="checkbox" name="search_status" id="search_status" value="${beanType.status}" 
                       onclick="searchIndentStatusWise('${beanType.status}')"> 

            </c:forEach>
            <label style="color:white;margin-left: 20px">All</label>
            <input type="checkbox" name="search_status" id="search_status" value="All" 
                   onclick="searchIndentStatusWise('All')"> 
        </div>
        
         <form name="form3" method="POST" action="CheckOrderInventoryController" >
            <div class="row mt-3 myTable">
                <input type="date" style="height:38px" placeholder="Search.." name="search_by_date">
                <button type="submit" class="btn normalBtn" name="submit_search">Search</button>
            </div>
        </form>
    </div>
</section>

<section class="marginTop30 ">
    <div class="container organizationBox">
        <div class="headBox">
            <h5 class="">Search List</h5>
        </div>

        <input type="hidden" name="stock_indent_final_status" id="stock_indent_final_status" value="">
        <input type="hidden" name="stock_indent_final_indent_table_id" id="stock_indent_final_indent_table_id" value="">
        <input type="hidden" name="stock_indent_final_message" id="stock_indent_final_message" value="">

        <input type="hidden" name="final_task" id="final_task" value="">

        <input type="hidden" name="delivery_indent_status" id="delivery_indent_status" value="">
        <input type="hidden" name="delivery_indent_table_id" id="delivery_indent_table_id" value="">
        <input type="hidden" name="delivery_indent_message" id="delivery_indent_message" value="">


        <div class="row mt-3 myTable">
            <div class="col-md-12">
                <div class="table-responsive verticleScroll">
                    <table class="table table-striped table-bordered" id="mytable" style="width:100%" data-page-length='6'>
                        <thead>
                            <tr>
                                <th>S.No.</th>
                                <td style="display:none"></td>
                                <th>Indent No.</th>
                                <th>Requested By</th>
                                <th>Requested To</th>
                                <th>Date Time</th>
<!--                               <th>Description</th>-->
                                <th></th>
                                <th></th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="beanType" items="${requestScope['list']}"
                                       varStatus="loopCounter">

                                <tr  onclick="openPopUpForItems(${beanType.indent_table_id}, '${beanType.status}')">
                                    <td>${loopCounter.count }</td>
                                    <td style="display:none"><input type="hidden" name="indent_table_id" id="indent_table_id" value="${beanType.indent_table_id}"></td>
                                    <td id="${loopCounter.count }">${beanType.indent_no}</td>
                                    <td id="${loopCounter.count }">${beanType.requested_by}</td>
                                    <td id="${loopCounter.count }">${beanType.requested_to}</td>
                                    <td id="${loopCounter.count }">${beanType.date_time}</td>
<!--                                    <td id="${loopCounter.count }">${beanType.description}</td>  -->

                                    <c:choose>
                                        <c:when test="${beanType.status =='Approved'}">
                                            <td id="${beanType.indent_table_id}" class="approved"><b>Approved</b>
                                            </td>   
                                        </c:when>
                                        <c:when test="${beanType.status =='Delivered'}">
                                            <td id="status${beanType.indent_table_id}" class="delivered"><b>${beanType.status}</b>
                                            </td>
                                        </c:when> 
                                        <c:when test="${beanType.status =='Payment Pending'}">
                                            <td id="status${beanType.indent_table_id}" class="PaymentPending"><b>${beanType.status}</b>
                                            </td>
                                        </c:when> 
                                        <c:when test="${beanType.status =='Payment Success'}">
                                            <td id="status${beanType.indent_table_id}" class="PaymentSuccess"><b>${beanType.status}</b>
                                            </td>
                                        </c:when> 
                                        <c:when test="${beanType.status =='Less Stock'}">
                                            <td id="${beanType.indent_table_id}" class="not_in_stock" ><b>${beanType.status}</b>
                                            </td> 
                                        </c:when>
                                        <c:when test="${beanType.status =='Denied'}">
                                            <td id="${beanType.indent_table_id}" class="denied"><b>${beanType.status}</b>
                                            </td>
                                        </c:when> 
                                        <c:when test="${beanType.status =='Delivery Challan Generated'}">
                                            <td id="${beanType.indent_table_id}" class="delivery_challan_generated"><b>${beanType.status}</b>
                                            </td>
                                        </c:when>

                                        <c:otherwise>
                                            <td id="${beanType.indent_table_id}"></td>  
                                        </c:otherwise>
                                    </c:choose>
                                 <!--   <c:choose>
                                        <c:when test="${(beanType.status == 'Payment Success')}">

                                            <td id="${loopCounter.count }"><input type="button" id="task" class="selected" name="item_status${beanType.indent_item_id}" value="Deliver Items" onclick="getdeliver()">
                                            </td>
                                        </c:when>

                                    </c:choose> -->
                                    <c:if test="${beanType.indent_table_id == final_indent_table_id}">
                                        <td id="${loopCounter.count }" class="delivered"><b>${final_status}</b>
                                        </td>
                                    </c:if>
                                      
                                </tr>

                            </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>
</section>



<%@include file="../layout/footer.jsp" %>

