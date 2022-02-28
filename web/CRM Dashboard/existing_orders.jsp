<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@include file="/CRM Dashboard/CRM_header.jsp" %>

<style>
    .salesManAppInputWrap{
        display: flex;
        white-space: nowrap;
    }
    .salesManAppInputWrap a{
        border-radius: 0;
    }
    .salesManAppInputWrap input{
        margin-right: 5px;
    }
</style>

<div class="content-wrapper position-relative" id="contentWrapper">
    <section class="content-header">
        <div class="container-fluid mt-2">
            <div class="row mb-2">
                <div class="col-sm-6">
                    <div class="d-flex">
                        <div>
                            <a href="PurchaseOrdersController?task=new order" class="btn btn-primary myNewLinkBtn">Create Order</a>
                        </div>
                        <div class="">
                            <div class="alert alert-success alert-dismissible myAlertBox">
                                <button type="button" class="close" data-dismiss="alert">&times;</button>
                                <strong>Success!</strong> New order create successfully.
                            </div>
                        </div>
                    </div>  
                </div>
                <div class="col-sm-6">
                    <ol class="breadcrumb float-sm-right">
                        <li class="breadcrumb-item"><a href="CRMDashboardController">Dashboard</a></li>
                        <li class="breadcrumb-item active">All Purchase Order</li>
                    </ol>
                </div>
            </div>
        </div><!-- /.container-fluid -->
    </section>
    <section class="content">
        <div class="container-fluid">              
            <div class="row mt-0">
                <div class="col-md-12">
                    <div class="card card-primary card-outline">            
                        <div class="card-body">
                            <div>
                                <div class="table-responsive tableScrollWrap" >
                                    <table class="table table-striped1 mainTable" id="mytable" >
                                        <thead>
                                            <tr>
                                                <th class="fontFourteen">Sr. No.</th>
                                                <th class="fontFourteen">Invoice No</th>
                                                <th class="fontFourteen">Order No</th>
                                                <th class="fontFourteen">Vendor Name</th>
                                                <th>Contact Person</th>
                                                <th>Mobile No</th>
                                                <th class="fontFourteen">Time</th>
                                                <th class="fontFourteen">Status</th>
                                                <th class="fontFourteen">Quotation</th>
                                                <th class="fontFourteen">Action</th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <tr>
                                                <td> 1 </td>
                                                <td class="fontFourteen">MAH0098657123</td>
                                                <td class="fontFourteen">APL111</td>
                                                <td class="fontFourteen">ABC Pvt Ltd</td>
                                                <td class="fontFourteen">Komal Saini</td>
                                                <td class="fontFourteen"><a href="tel:+91-8700328890">8700328890</a></td>                            
                                                <td class="fontFourteen">2 days ago... </td>
                                                <td class="fontFourteen"><i class="statusPending">Pending</i> </td>
                                                <td class="fontFourteen">1,20,000</td>
                                                <td>
                                                    <a href="PurchaseOrdersController?task=viewDetails" class="btn far fa-eye actionEdit" title="View Enquiry Detail"></a>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td> 2 </td>
                                                <td class="fontFourteen">MAH0098657123</td>
                                                <td class="fontFourteen">APL111</td>
                                                <td class="fontFourteen">ABC Pvt Ltd</td>
                                                <td class="fontFourteen">Komal Saini</td>
                                                <td class="fontFourteen"><a href="tel:+91-8700328890">8700328890</a></td>                            
                                                <td class="fontFourteen">2 days ago... </td>
                                                <td class="fontFourteen"><i class="statusDisapprove">Cancelled</i> </td>
                                                <td class="fontFourteen">1,20,000</td>
                                                <td>
                                                    <a href="PurchaseOrdersController?task=viewDetails" class="btn far fa-eye actionEdit" title="View Enquiry Detail"></a>
                                                </td>
                                            </tr>                        
                                            <tr>
                                                <td> 3 </td>
                                                <td class="fontFourteen">MAH0098657123</td>
                                                <td class="fontFourteen">APL111</td>
                                                <td class="fontFourteen">ABC Pvt Ltd</td>
                                                <td class="fontFourteen">Komal Saini</td>
                                                <td class="fontFourteen"><a href="tel:+91-8700328890">8700328890</a></td>                            
                                                <td class="fontFourteen">2 days ago... </td>
                                                <td class="fontFourteen"><i class="statusApprove">Complete</i> </td>
                                                <td class="fontFourteen">1,20,000</td>
                                                <td>
                                                    <a href="PurchaseOrdersController?task=viewDetails" class="btn far fa-eye actionEdit" title="View Enquiry Detail"></a>
                                                </td>
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

