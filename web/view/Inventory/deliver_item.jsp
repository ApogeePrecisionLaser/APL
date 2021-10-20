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

</style>
<script>


    $(document).ready(function () {
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
                        url: "DeliverItemController",
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


    function openPopUpForItems(indent_table_id) {
        var url = "DeliverItemController?task=GetIndentItems&indent_table_id=" + indent_table_id;
        popupwin = openPopUp(url, "", 600, 1030);
    }

    function openPopUp(url, window_name, popup_height, popup_width) {
        var popup_top_pos = (screen.availHeight / 2) - (popup_height / 2);
        var popup_left_pos = (screen.availWidth / 2) - (popup_width / 2);
        var window_features = "left=" + popup_left_pos + ", top=" + popup_top_pos + ", width=" + popup_width + ", height=" + popup_height + ", resizable=yes, scrollbars=yes, location=0, menubar=no, status=no, dependent=yes";
        return window.open(url, window_name, window_features);
    }



</script>


<section>
    <div class="container-fluid page_heading sectionPadding35">
        <h1>Deliver Indent Item</h1>
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
                                <td style="display:none"></td>
                                <!--<td style="display:none"></td>-->
                                <th>Indent No.</th>
                                <th>Requested By</th>
                                <th>Requested To</th>
                                <th>Date Time</th>
                                <th>Description</th>
                                <th></th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="beanType" items="${requestScope['list']}"
                                       varStatus="loopCounter">
                                <tr  onclick="openPopUpForItems(${beanType.indent_table_id})">
                                    <td>${loopCounter.count }</td>
                                    <td style="display:none"><input type="hidden" name="indent_table_id" id="indent_table_id" value="${beanType.indent_table_id}"></td>
                                    <td id="${loopCounter.count }">${beanType.indent_no}</td>
                                    <td id="${loopCounter.count }">${beanType.requested_by}</td>
                                    <td id="${loopCounter.count }">${beanType.requested_to}</td>
                                    <td id="${loopCounter.count }">${beanType.date_time}</td>

                                    <td id="${loopCounter.count }">${beanType.description}</td>  

                                    <c:choose>
                                        <c:when test="${beanType.status =='Approved'}">
                                            <td id="${loopCounter.count }" class="approved"><b>Approved</b>
                                            </td>   
                                        </c:when>
                                        <c:when test="${beanType.status =='Delivered'}">
                                            <td id="${loopCounter.count }" class="delivered"><b>${beanType.status}</b>
                                            </td>
                                        </c:when> 
                                        <c:when test="${beanType.status =='Less Stock'}">
                                            <td id="${loopCounter.count }" class="not_in_stock" ><b>${beanType.status}</b>
                                            </td> 
                                        </c:when>
                                        <c:when test="${beanType.status =='Denied'}">
                                            <td id="${loopCounter.count }" class="denied"><b>${beanType.status}</b>
                                            </td>
                                        </c:when> 
                                        <c:otherwise>
                                            <td id="${loopCounter.count }"></td>  
                                        </c:otherwise>
                                    </c:choose>
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

