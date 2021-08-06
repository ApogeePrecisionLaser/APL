
<%@include file="../layout/header.jsp" %>

    <section>
        <div class="container-fluid page_heading sectionPadding35">
            <h1>Organization Type</h1>
        </div>
    </section>



    <section class="marginTop30">
        <div class="container organizationBox">
            <div class="headBox">
                <h5 class="">Search Engine</h5>
            </div>
            <form action="#">
                <div class="row mt-3">
                    <div class="col-md-2">
                        <div class="form-group mb-md-0">
                            <label for="email">Organization Type</label>
                            <input type="email" Placeholder="Organization Type" class="form-control myInput searchInput1 w-100">
                        </div>
                    </div>
                    <div class="col-md-2">
                        <div class="form-group mb-md-0">
                            <label for="email">Generation</label>
                            <input type="email" Placeholder="Generation" class="form-control myInput searchInput1 w-100">
                        </div>
                    </div>
                    <div class="col-md-2">
                        <div class="form-group mb-md-0">
                            <label for="email">Org Type Hierarchy</label>
                            <input type="email" Placeholder="Org Type Hierarchy" class="form-control myInput searchInput1 w-100">
                        </div>
                    </div>
                    <div class="col-md-2">
                        <div class="form-group mb-md-0">
                            <label for="email">Status</label>
                            <select class="form-control selectInput mySelect">
                                <option selected disabled>Select One</option>
                                <option>Active Records</option>
                                <option>Inactive Records</option>
                                <option>All Records</option>
                            </select>
                        </div>
                    </div>
                    <div class="col-md-3">
                        <div class="formBtnWrap">
                            <button type="submit" class="btn formBtn">Search Record</button>
                        </div>
                    </div>
                </div>
            </form>
            <hr>
            <div class="row">
                <div class="col-md-12 text-center">
                    <a href="#" class="btn normalBtn">PDF</a>
                    <a href="#" class="btn normalBtn">Excel</a>
                </div>
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
                    <div class="table-responsive">
                        <table class="table table-striped table-bordered" id="mytable" style="width:100%">
                            <thead>
                                <tr>
                                    <th>S.No.</th>
                                    <th>Org Type Name</th>
                                    <th>Parent Org Type </th>
                                    <th>Is Super Child</th>
                                    <th>Description</th>
                                    <th>Generation</th>
                                </tr>
                            </thead>
                            <tbody>
                               <tr>
                                <td>1</td>
                                <td>Software Company</td>
                                <td>Software Company</td>
                                <td>Apogee</td>
                                <td>786564</td>
                                <td>It is a long established fact that a reader </td>
                              </tr>
                              <tr>
                                <td>2</td>
                                <td>Software Company</td>
                                <td>Software Company</td>
                                <td>Apogee</td>
                                <td>786564</td>
                                <td>It is a long established fact that a reader</td>
                              </tr>
                              <tr>
                                <td>3</td>
                                <td>ABSoftware Company</td>
                                <td>Software Company</td>
                                <td>Apogee</td>
                                <td>786564</td>
                                <td>It is a long established fact that a reader </td>
                              </tr>
                              <tr>
                                <td>4</td>
                                <td>Software Company</td>
                                <td>Software Company</td>
                                <td>Apogee</td>
                                <td>786564</td>
                                <td>It is a long established fact that a reader </td>
                              </tr>
                              <tr>
                                <td>5</td>
                                <td>Software Company</td>
                                <td>Software Company</td>
                                <td>Apogee</td>
                                <td>786564</td>
                                <td>It is a long established fact that a reader </td>
                              </tr>
                              <tr>
                                <td>6</td>
                                <td>Software Company</td>
                                <td>Software Company</td>
                                <td>Apogee</td>
                                <td>786564</td>
                                <td>It is a long established fact that a reader </td>
                              </tr>
                              <tr>
                                <td>3</td>
                                <td>ABSoftware Company</td>
                                <td>Software Company</td>
                                <td>Apogee</td>
                                <td>786564</td>
                                <td>It is a long established fact that a reader </td>
                              </tr>
                              <tr>
                                <td>3</td>
                                <td>ABSoftware Company</td>
                                <td>Software Company</td>
                                <td>Apogee</td>
                                <td>786564</td>
                                <td>It is a long established fact that a reader </td>
                              </tr>
                              <tr>
                                <td>4</td>
                                <td>Software Company</td>
                                <td>Software Company</td>
                                <td>Apogee</td>
                                <td>786564</td>
                                <td>It is a long established fact that a reader </td>
                              </tr>
                              <tr>
                                <td>5</td>
                                <td>Software Company</td>
                                <td>Software Company</td>
                                <td>Apogee</td>
                                <td>786564</td>
                                <td>It is a long established fact that a reader </td>
                              </tr>
                              <tr>
                                <td>6</td>
                                <td>Software Company</td>
                                <td>Software Company</td>
                                <td>Apogee</td>
                                <td>786564</td>
                                <td>It is a long established fact that a reader </td>
                              </tr>
                              <tr>
                                <td>3</td>
                                <td>ABSoftware Company</td>
                                <td>Software Company</td>
                                <td>Apogee</td>
                                <td>786564</td>
                                <td>It is a long established fact that a reader </td>
                              </tr>
                              <tr>
                                <td>3</td>
                                <td>ABSoftware Company</td>
                                <td>Software Company</td>
                                <td>Apogee</td>
                                <td>786564</td>
                                <td>It is a long established fact that a reader </td>
                              </tr>
                              <tr>
                                <td>4</td>
                                <td>Software Company</td>
                                <td>Software Company</td>
                                <td>Apogee</td>
                                <td>786564</td>
                                <td>It is a long established fact that a reader </td>
                              </tr>
                              <tr>
                                <td>5</td>
                                <td>Software Company</td>
                                <td>Software Company</td>
                                <td>Apogee</td>
                                <td>786564</td>
                                <td>It is a long established fact that a reader </td>
                              </tr>
                              <tr>
                                <td>6</td>
                                <td>Software Company</td>
                                <td>Software Company</td>
                                <td>Apogee</td>
                                <td>786564</td>
                                <td>It is a long established fact that a reader </td>
                              </tr>
                              <tr>
                                <td>3</td>
                                <td>ABSoftware Company</td>
                                <td>Software Company</td>
                                <td>Apogee</td>
                                <td>786564</td>
                                <td>It is a long established fact that a reader </td>
                              </tr>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>

        </div>
    </section>




    <section class="marginTop30">
        <div class="container organizationBox">
            <div class="headBox">
                <h5 class="">Data Entry</h5>
            </div>
            <form name="form" method="POST" action="OrganizationNameController" onsubmit="">
                <div class="row mt-3">
                    <div class="col-md-3">
                        <div class="">
                            <div class="form-group">
                                <label for="email">Organization Type Name<span class="text-danger">*</span></label>
                                <input type="text" class="form-control myInput" id="organization_type" name="organization_type">
<!--                                <select class="form-control js-example-basic-single mySelect js-example-responsive" name="organization_type" style="height:70px;">
                                    <option value="AL">Alabama</option>
                                    <option value="WY">Wyoming</option>
                                </select>               -->
                            </div>
                        </div>
                    </div>
                    <div class="col-md-3">
                        <div class="">
                            <div class="form-group">
                                <label for="email">Parent Organization Type Name<span class="text-danger">*</span></label>
                                <input type="text" class="form-control myInput" id="organization_name" name="organization_name">
                            </div>
                        </div>
                    </div>
                    <div class="col-md-3">
                        <div class="">
                            <div class="form-group">
                                <label for="email">Is super child<span class="text-danger">*</span></label>
                                <input type="text" class="form-control myInput" id="organization_code" name="organization_code">
                            </div>
                        </div>
                    </div>
                    <div class="col-md-3">
                        <div class="">
                            <div class="form-group mb-1">
                                <label class="" for="email">Is super child<span class="text-danger">*</span></label>
                            </div>
                            <div class="form-group form-check mb-0 d-inline mr-2">
                                <label class="form-check-label">
                                    <input class="form-check-input" name="child" type="radio"> Yes
                                </label>
                            </div>
                            <div class="form-group form-check d-inline">
                                <label class="form-check-label">
                                  <input class="form-check-input" name="child" type="radio"> No
                                </label>
                            </div>
                        </div>
                    </div>
                    <div class="col-md-12">
                        <div class="">
                            <div class="form-group">
                                <label for="email">Type Description</label>
                                <textarea class="form-control myTextArea" id="description" name="description"></textarea>
                            </div>
                        </div>
                    </div>
                    <!-- <div class="col-md-4">
                        <div class="">
                            <div class="form-group">
                                <label for="email">Remark</label>
                                <input type="text" id="remark" name="remark" class="form-control myInput">
                            </div>
                        </div>
                    </div> -->

                </div>      
                <hr>
                <div class="row">

                        <div class="col-md-12 text-center">
                            <label style="text-success"><b>Result: ${message}</b></label>
                        </div>

                    <div class="col-md-12 text-center">
                        <a href="#" class="btn normalBtn">Edit</a>
                        <input class="btn normalBtn" type="submit" name="task" id="save" value="Save">
                        <a href="#" class="btn normalBtn">New</a>
                        <a href="#" class="btn normalBtn">Delete</a>
                    </div>
        </div>
    </section>



<%@include file="../layout/footer.jsp" %>

