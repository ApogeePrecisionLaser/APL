<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@include file="/CRM Dashboard/CRM_header.jsp" %>

<div class="content-wrapper" id="contentWrapper">
    <section class="content-header">
        <div class="container-fluid mt-2">
            <div class="row mb-2">
                <div class="col-sm-6">
                    <div class="d-flex">
                        <div class="mr-2 backBtnWrap">
                            <a href="DemandNoteController" class="btn btn-primary myNewLinkBtn">Back</a>
                        </div>
                        <div>
                            <h6 class="mt-2">Order No : ${order_no}</h6>
                        </div>
                    </div>  
                </div>
                <div class="col-sm-6">
                    <ol class="breadcrumb float-sm-right">
                        <li class="breadcrumb-item"><a href="CRMDashboardController">Dashboard</a></li>
                        <li class="breadcrumb-item active">Demand Note Detail
                        </li>
                    </ol>
                </div>
            </div>
        </div>
    </section>
    <section class="content mt-0">
        <div class="container-fluid">              
            <div class="row mt-0">
                <div class="col-md-12">
                    <div class="card card-primary card-outline">
                        <div class="card-body">
                            <form action="DemandNoteController" method="POST">
                                <div class="table-responsive tableScrollWrap" >
                                    <table class="table table-striped1 mainTable" id="mytable1" >
                                        <thead>
                                            <tr>
                                                <th>Sr. No.</th>
                                                <th>Product Img</th>
                                                <th>Product Name</th>
                                                <th>Model Name</th>
                                                <th>Quantity</th>
                                                <th>Approved Quantity</th>
                                                <th>Status</th>
                                            </tr>
                                        </thead>
                                        <tbody>

                                            <c:forEach var="beanType" items="${requestScope['detail']}"
                                                       varStatus="loopCounter">
                                                <tr>
                                                    <td>${loopCounter.count}</td>
                                                    <td>
                                                        <input type="hidden" name="image_path" id="image_path${loopCounter.count}" value="${beanType.image_path}">
                                                        <input type="hidden" name="image_name" id="image_name${loopCounter.count}" value="${beanType.image_name}">
                                                        <input type="hidden" name="count" id="count" value="${count}">
                                                        <input type="hidden" name="demand_note_id" id="demand_note_id${loopCounter.count}" value="${beanType.demand_note_id}">
                                                        <img class="orderImg img-fluid${loopCounter.count}" src="">
                                                    </td>
                                                    <td>${beanType.item_name}</td>
                                                    <td>${beanType.model}</td>
                                                    <td>${beanType.qty}
                                                        <input type="hidden" name="qty" id="qty${loopCounter.count}" value="${beanType.qty}">
                                                    </td>
                                                    <td>
                                                        <c:if test="${role=='Admin' || role=='Super Admin'}">
                                                            <c:choose>
                                                                <c:when test="${beanType.status!='Pending'}">
                                                                    <input type="text" name="approved_qty" disabled="" id="approved_qty${loopCounter.count}" value="${beanType.approved_qty}">
                                                                </c:when>
                                                                <c:otherwise>
                                                                    <input type="text" name="approved_qty" id="approved_qty${loopCounter.count}" >
                                                                </c:otherwise>
                                                            </c:choose>
                                                        </c:if>
                                                        <c:if test="${role=='Incharge'}">
                                                            <input type="text" name="approved_qty" disabled="" id="approved_qty${loopCounter.count}" value="${beanType.approved_qty}">
                                                        </c:if>
                                                    </td>

                                                    <c:if test="${role=='Incharge'}">
                                                        <c:choose>
                                                            <c:when test="${beanType.status=='Pending'}">
                                                                <td class="fontFourteen"><i class="statusPending">${beanType.status}</i> </td>
                                                            </c:when>
                                                            <c:when test="${beanType.status=='Approved'}">
                                                                <td class="fontFourteen"><i class="statusApprove">${beanType.status}</i> </td>
                                                            </c:when>
                                                            <c:when test="${beanType.status=='Denied'}">
                                                                <td class="fontFourteen"><i class="statusDisapprove">${beanType.status}</i> </td>
                                                            </c:when>
                                                            <c:when test="${beanType.status=='Convert To Quotation'}">
                                                                <td class="fontFourteen"><i class="statusPaymentDone">${beanType.status}</i> </td>
                                                            </c:when>
                                                            <c:otherwise>
                                                                <td class="fontFourteen"><i class="">${beanType.status}</i> </td>
                                                            </c:otherwise>
                                                        </c:choose> 
                                                    </c:if>

                                                    <c:if test="${role=='Admin' || role=='Super Admin'}">
                                                        <td>
                                                            <select class="btn btn-primary myNewLinkBtn px-1 ml-3 fontFourteen" style="width:100px" value="${beanType.status}"
                                                                    name="status${beanType.demand_note_id}" id="status${beanType.demand_note_id}" >
                                                                <c:if test="${beanType.status=='Approved'}">
                                                                    <option class="btn btn-primary actionEdit fontFourteen" value="${beanType.status}">Approved</option>
                                                                </c:if>
                                                                <c:if test="${beanType.status=='Denied'}">
                                                                    <option class="btn btn-primary actionDelete fontFourteen" value="${beanType.status}"> Denied</option>
                                                                </c:if>
                                                                <c:if test="${beanType.status=='Convert To Quotation'}">
                                                                    <option class="btn btn-primary  fontFourteen" value="${beanType.status}">Converted To Quotation</option>
                                                                </c:if>
                                                                <c:if test="${beanType.status=='Pending'}">
                                                                    <option class="btn btn-primary fontFourteen" >Select</option>
                                                                    <option class="btn btn-primary actionEdit fontFourteen" value="Approved">Approved</option>
                                                                    <option  class="btn btn-primary actionDelete fontFourteen" value="Denied">Denied</option>
                                                                </c:if>
                                                            </select>
                                                        </td>
                                                    </c:if>
                                                </tr>
                                            </c:forEach>

                                            <tr class="darkBlueBg">
                                                <td colspan="3"></td>
                                                <td class="totalValue text-white py-2">Total Qty</td>
                                                <td class="totalValue text-white py-2">${total_qty}</td>
                                                <td class="totalValue text-white py-2">${total_approved_qty}</td>
                                                <c:if test="${role=='Incharge'}">
                                                    <td></td>
                                                </c:if>
                                                <c:if test="${role=='Admin' || role=='Super Admin'}">
                                                    <td class="totalValue text-white py-2">
                                                        <!--${button}-->
                                                        <c:if test="${button=='Enable'}">
                                                            <input type="submit" name="task" onclick="checkStatus(this.value)" class="btn actionEdit fontFourteen " id="approved" value="Confirm">
                                                            <input type="submit" name="task" onclick="checkStatus(this.value)" class="btn actionDelete fontFourteen " id="denied" value="Denied All">
                                                        </c:if>
                                                        <c:if test="${button=='Disabled'}">
                                                            <input type="submit" name="task" disabled="" onclick="checkStatus(this.value)" class="btn actionEdit fontFourteen " id="approved" value="Confirm">
                                                            <input type="submit" name="task" disabled="" onclick="checkStatus(this.value)" class="btn actionDelete fontFourteen " id="denied" value="Denied All">
                                                        </c:if>
                                                    </td> 
                                                </c:if>
                                            </tr>  
                                        </tbody>
                                    </table>
                                </div>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </section>
</div>

<%@include file="/CRM Dashboard/CRM_footer.jsp" %>

<script>
    $(function () {
        var count = $('#count').val();
        for (var j = 0; j < count; j++) {
            var image_path = $('#image_path' + (j + 1)).val();
            var image_name = $('#image_name' + (j + 1)).val();
            //            alert(image_path);
            //            alert(image_name);
            var image = image_path + image_name;
            if (image != "") {
                image = image.replace(/\\/g, "/");
            }
            $('.img-fluid' + (j + 1)).attr("src", "http://" + IMAGE_URL + "/APL/DealersOrderController?getImage=" + image + "");

        }
    });

    function  checkStatus(btnstatus) {
        var count = $('#count').val();
        if (btnstatus == 'Confirm') {
            for (var k = 0; k < count; k++) {
                var demand_note_id = $('#demand_note_id' + (k + 1)).val();
                var qty = $('#qty' + (k + 1)).val();
                var approved_qty = $('#approved_qty' + (k + 1)).val();
                alert(qty);
                alert(approved_qty);
                if (approved_qty > qty) {
                    alert("Please Enter Valid qty ");
                    event.preventDefault();
                    return false;
                }
                var status = $('#status' + demand_note_id).val();
                //                alert(status);
                if (status == 'Select' || status == '') {
                    alert("Please Select Status ");
                    event.preventDefault();
                    return false;
                }
            }
        }
        return true;
    }

</script>