<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@include file="/CRM Dashboard/CRM_header.jsp" %>

<div class="content-wrapper" id="contentWrapper">
    <section class="content-header">
        <div class="container-fluid mt-2">
            <div class="row mb-2">
                <div class="col-sm-6">
                    <div class="d-flex">
                        <div>
                            <a href="PurchaseOrdersController" class="btn btn-primary myNewLinkBtn">Back</a>
                        </div>
                    </div>  
                </div>
                <div class="col-sm-6">
                    <ol class="breadcrumb float-sm-right">
                        <li class="breadcrumb-item"><a href="CRMDashboardController">Dashboard</a></li>
                        <li class="breadcrumb-item active">Order Detail</li>
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
                            <div class="table-responsive tableScrollWrap" >
                                <table class="table table-striped1 mainTable" id="mytable1" >
                                    <thead>
                                        <tr>
                                            <th>Sr. No.</th>
                                            <th>Product Img</th>
                                            <th>Product Name</th>
                                            <th>Model Name</th>
                                            <th>Model No</th>
                                            <th>Quantity</th>
                                            <th>Price</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <tr>
                                            <td>1</td>
                                            <td>
                                                <img class="orderImg" src="http://apogeeleveller.com/assets/images/gallery/Apogee1Transmitter.png">
                                            </td>
                                            <td>Transmitter</td>
                                            <td>RL-600</td>
                                            <td>Tr-2</td>
                                            <td>50</td>
                                            <td class="font-weight-bold">?1,80,000</td>                        
                                        </tr>
                                        <tr>
                                            <td>1</td>
                                            <td>
                                                <img class="orderImg" src="http://apogeeleveller.com/assets/images/gallery/Apogee1Transmitter.png">
                                            </td>
                                            <td>Transmitter</td>
                                            <td>RL-600</td>
                                            <td>Tr-2</td>
                                            <td>50</td>
                                            <td class="font-weight-bold">?1,80,000</td>                        
                                        </tr>
                                        <tr>
                                            <td>1</td>
                                            <td>
                                                <img class="orderImg" src="http://apogeeleveller.com/assets/images/gallery/Apogee1Transmitter.png">
                                            </td>
                                            <td>Transmitter</td>
                                            <td>RL-600</td>
                                            <td>Tr-2</td>
                                            <td>50</td>
                                            <td class="font-weight-bold">?1,80,000</td>                        
                                        </tr>   
                                        <tr class="darkBlueBg">
                                            <td colspan="5"></td>
                                            <td class="totalValue text-white py-2">Total Amount</td>
                                            <td class="totalValue text-white py-2">?1,80,000</td>                    
                                        </tr>   
                                    </tbody>
                                </table>
                            </div>
                        </div>
                    </div>
                </div>
            </div>

        </div>
</div>
</section>
</div>

<%@include file="/CRM Dashboard/CRM_footer.jsp" %>



