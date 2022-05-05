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
                            <a href="DemandNoteController?task=new_demand" class="btn btn-primary myNewLinkBtn">Create New Demand Note</a>
                        </div>
                        <c:if test="${not empty message}">
                            <c:if test="${msgBgColor=='green'}">
                                <div class="alert alert-success alert-dismissible myAlertBox"  id="msg" >
                                    <button type="button" class="close" data-dismiss="alert">&times;</button>
                                    <strong>Success!</strong> ${message}

                                </div>
                            </c:if>
                            <c:if test="${msgBgColor=='red'}">
                                <div class="alert alert-danger alert-dismissible myAlertBox" id="msg" >
                                    <button type="button" class="close" data-dismiss="alert">&times;</button>
                                    <strong>OOps!</strong> ${message}
                                </div>
                            </c:if>
                        </c:if>
                    </div>  
                </div>
                <div class="col-sm-6">
                    <ol class="breadcrumb float-sm-right">
                        <c:choose>
                            <c:when test="${role=='Incharge' || role=='Super Admin'}">
                                <li class="breadcrumb-item"><a href="dashboard">Dashboard</a>
                                </li>
                            </c:when>
                            <c:otherwise>
                                <li class="breadcrumb-item"><a href="CRMDashboardController">Dashboard</a>
                                </li>
                            </c:otherwise>
                        </c:choose>
                        <li class="breadcrumb-item active">All Demand Notes</li>
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
                                                    <c:if test="${role=='Admin' || role=='Super Admin'}">
                                                    <th class="fontFourteen">Office Name</th>

                                                </c:if>
                                                <th class="fontFourteen">Order No</th>
                                                <th class="fontFourteen">Vendor Name</th>
                                                    <c:if test="${role=='Admin' || role=='Super Admin'}">
                                                    <th>Customer</th>
                                                    </c:if>
                                                <th>Vendor Mobile No</th>
                                                <th class="fontFourteen">Price (<small><i class="fas fa-rupee-sign curruncyIcon"></i></small>)</th>
                                                <th class="fontFourteen">Time</th>
                                                <th class="fontFourteen">Status</th>
                                                <!--<th class="fontFourteen">Quotation</th>-->
                                                <th class="fontFourteen">Action</th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            
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

<script>

</script>