<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@include file="/CRM Dashboard/CRM_header.jsp" %>

<div class="content-wrapper" id="contentWrapper">
    <section class="content-header">
        <div class="container-fluid mt-2">
            <div class="row mb-2">
                <div class="col-sm-6">
                    <div class="d-flex">
                        <div class="mr-2 backBtnWrap">
                            <a href="ApprovePurchaseOrdersController" class="btn btn-primary myNewLinkBtn">Back</a>
                        </div>
                        <div>
                            <h6 class="mt-2">Order No : ${order_no}</h6>
                        </div>
                    </div>  
                </div>
                <div class="col-sm-6">
                    <ol class="breadcrumb float-sm-right">
                        <li class="breadcrumb-item"><a href="CRMDashboardController">Dashboard</a></li>
                        <li class="breadcrumb-item active">Approve Purchase Order Detail
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
                            <form action="ApprovePurchaseOrdersController" method="POST">

                                <div class="table-responsive tableScrollWrap" >
                                    <table class="table table-striped1 mainTable" id="mytable1" >
                                        <thead>
                                            <tr>
                                                <th>Sr. No.</th>
                                                <th>Product Img</th>
                                                <th>Product Name</th>
                                                <th>Model Name</th>
                                                <th>Model/ Part No.</th>
                                                <th>Quantity</th>
                                                <th>Price (<small><i class="fas fa-rupee-sign curruncyIcon"></i></small>)</th>
                                                <th>Action</th>
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
                                                        <input type="hidden" name="purchase_order_id" id="purchase_order_id${loopCounter.count}" value="${beanType.purchase_order_id}">
                                                        <img class="orderImg img-fluid${loopCounter.count}" src="">
                                                    </td>
                                                    <td>${beanType.item_name}</td>
                                                    <td>${beanType.model}</td>
                                                    <td>${beanType.model_no}</td>
                                                    <td>${beanType.qty}</td>
                                                    <td class="font-weight-bold">${beanType.price}</td>    
                                                    <td>
                                                        <select class="btn btn-primary myNewLinkBtn px-1 ml-3 fontFourteen" style="width:100px" value="${beanType.status}"
                                                                name="status${beanType.purchase_order_id}" id="status${beanType.purchase_order_id}" >
                                                            <c:if test="${beanType.status=='Approved'}">
                                                                <option  class="btn btn-primary actionEdit fontFourteen" value="${beanType.status}">Approved</option>
                                                            </c:if>
                                                            <c:if test="${beanType.status=='Denied'}">
                                                                <option  class="btn btn-primary actionDelete fontFourteen" value="${beanType.status}"> Denied</option>
                                                            </c:if>
                                                            <c:if test="${beanType.status=='Pending'}">
                                                                <option  class="btn btn-primary fontFourteen" >Select</option>
                                                                <option  class="btn btn-primary actionEdit fontFourteen" value="Approved">Approved</option>
                                                                <option  class="btn btn-primary actionDelete fontFourteen" value="Denied">Denied</option>
                                                            </c:if>
                                                        </select>
                                                    </td>
                                                </tr>
                                            </c:forEach>

                                            <tr class="darkBlueBg">
                                                <td colspan="4"></td>
                                                <td class="totalValue text-white py-2">Total Qty</td>
                                                <td class="totalValue text-white py-2">${total_qty}</td>                    
                                                <td class="totalValue text-white py-2">${total_price}</td>                    
                                                <td class="totalValue text-white py-2">
                                                    <input type="submit" name="task" onclick="checkStatus(this.value)" class="btn actionEdit fontFourteen " id="approved" value="Confirm">
                                                    <input type="submit" name="task" onclick="checkStatus(this.value)" class="btn actionDelete fontFourteen " id="denied" value="Denied All">
                                                </td>                    
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
                var purchase_order_id = $('#purchase_order_id' + (k + 1)).val();
                var status = $('#status' + purchase_order_id).val();
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