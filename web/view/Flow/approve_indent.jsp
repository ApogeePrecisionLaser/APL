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
    .delivery_challan_generated{
        background-color: #df7d35;
        color:white; 
    }
</style>
<script>

    $(document).ready(function () {
        var final_indent_table_id = $('#indent_final_indent_table_id').val();
        var final_status = $('#indent_final_status').val();
        var final_message = $('#indent_final_message').val();


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
                        url: "ApproveIndentController",
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

    function openPopUpForItems(indent_table_id, indent_status) {
        var url = "ApproveIndentController?task=GetIndentItems&indent_table_id=" + indent_table_id + "&indent_status=" + indent_status;
        popupwin = openPopUp(url, "", 600, 1030);
    }

    function openPopUp(url, window_name, popup_height, popup_width) {
        var popup_top_pos = (screen.availHeight / 2) - (popup_height / 2);
        var popup_left_pos = (screen.availWidth / 2) - (popup_width / 2);
        var window_features = "left=" + popup_left_pos + ", top=" + popup_top_pos + ", width=" + popup_width + ", height=" + popup_height + ", resizable=yes, scrollbars=yes, location=0, menubar=no, status=no, dependent=yes";
        return window.open(url, window_name, window_features);
    }

    function searchIndentStatusWise(status) {
        var url = "ApproveIndentController?action1=searchIndentStatusWise&status=" + status;
        window.open(url, "_self");
    }
</script>


<section>
    <div class="container-fluid page_heading sectionPadding35">
        <h1>Approve Indent</h1>
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
                    <form>
                        <input type="hidden" name="final_status" id="indent_final_status" value="${final_status}">
                        <input type="hidden" name="final_indent_table_id" id="indent_final_indent_table_id" value="${final_indent_table_id}">
                        <input type="hidden" name="final_message" id="indent_final_message" value="${final_message}">
                        <table class="table table-striped table-bordered" id="mytable" style="width:100%" data-page-length='6'>
                            <thead>
                                <tr>
                                    <th>S.No.</th>
                                    <td style="display:none"></td>
                                    <th>Indent No.</th>
                                    <th>Requested By</th>
                                    <th>Date Time</th>
                                    <!--<th>Status</th>-->
                                    <th>Description</th>
                                    <th></th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach var="beanType" items="${requestScope['list']}"
                                           varStatus="loopCounter">
                                    <tr onclick="openPopUpForItems(${beanType.indent_table_id}, '${beanType.status}')">

                                        <td>${loopCounter.count }</td>
                                        <td style="display:none"><input type="hidden" name="indent_table_id" id="indent_table_id" value="${beanType.indent_table_id}"></td>
                                        <td id="${loopCounter.count }">${beanType.indent_no}</td>
                                        <td id="${loopCounter.count }">${beanType.requested_by}</td>
                                        <td id="${loopCounter.count }">${beanType.date_time}</td>
                                        <!--  <c:choose>
                                            <c:when test="${beanType.status =='Approved'}">
                                                <td id="${loopCounter.count }"><input type="text"  disabled="" name="status" placeholder="select status" class="myAutocompleteClass" id="status${beanType.indent_item_id}" value="${beanType.status}"></td>
                                            </c:when>
                                            <c:when test="${beanType.status =='Denied'}">
                                            <td id="${loopCounter.count }"><input type="text"  disabled="" name="status" placeholder="select status" class="myAutocompleteClass" id="status${beanType.indent_item_id}" value="${beanType.status}"></td>
                                            </c:when>
                                            <c:otherwise>
                                            <td id="${loopCounter.count }"><input type="text" name="status" class="myAutocompleteClass" placeholder="select status" id="status${beanType.indent_item_id}" value=""></td>
                                            </c:otherwise>
                                        </c:choose>-->

                                        <td id="${loopCounter.count }">${beanType.description}</td>  

                                        <c:choose>
                                            <c:when test="${beanType.status =='Pending'}">
                                                <td id="${loopCounter.count }" class="pending" ><b>Pending</b>
                                                </td> 

                                            </c:when>
                                            <c:when test="${beanType.status =='Approved'}">
                                                <td id="${loopCounter.count }" class="approved" ><b>${beanType.status}</b>
                                                </td> 
                                            </c:when> 

                                            <c:when test="${beanType.status =='Delivered'}">
                                                <td id="${loopCounter.count }" class="delivered" ><b>${beanType.status}</b>
                                                </td> 
                                            </c:when> 

                                            <c:when test="${beanType.status =='Denied'}">
                                                <!--<td id="${loopCounter.count }"><a style="pointer-events: none;background-color: #dc3545;color:white" href="" class="btn">Denied</a></td>-->  
                                                <td id="${loopCounter.count }" class="denied" ><b>${beanType.status}</b>
                                                </td> 
                                            </c:when>
                                            <c:when test="${beanType.status =='Delivery Challan Generated'}">
                                                <td id="${loopCounter.count }" class="delivery_challan_generated"><b>${beanType.status}</b>
                                                </td>
                                            </c:when>  
                                            <c:when test="${beanType.status =='Less Stock'}">
                                                <td id="${loopCounter.count }" class="not_in_stock"><b>${beanType.status}</b>
                                                </td>
                                            </c:when>
                                            <c:otherwise>
                                                <td id="${loopCounter.count }"></td>  
                                            </c:otherwise>
                                        </c:choose>
                                        <c:if test="${beanType.indent_table_id == final_indent_table_id}">
                                            <td id="${loopCounter.count }" class="delivery_challan_generated"><b>${final_status}</b>
                                            </td>
                                        </c:if>
                                    </tr>

                                </c:forEach>
                            </tbody>
                        </table>
                    </form>
                </div>
            </div>
        </div>
    </div>
</section>


<%@include file="../layout/footer.jsp" %>

