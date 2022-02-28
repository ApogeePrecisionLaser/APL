<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@include file="/CRM Dashboard/CRM_header.jsp" %>
<div class="content-wrapper" id="contentWrapper">
    <section class="content-header">
        <div class="container-fluid mt-1">
            <div class="row mb-2">
                <div class="col-sm-6">
                    <h1>Purchase</h1>
                </div>
                <div class="col-sm-6">
                    <ol class="breadcrumb float-sm-right">
                        <li class="breadcrumb-item"><a href="#">Home</a></li>
                        <li class="breadcrumb-item active">Purchase Add</li>
                    </ol>
                </div>
            </div>
        </div>
    </section>
    <section class="content mt-0">
        <div class="container-fluid">              
            <div class="row mt-0">
                <div class="col-md-12">
                    <div class="card card-primary card-outline rounded-0">
                        <div class="card-body rounded-0">
                            <div>
                                <form class="myForm1">
                                    <div class="row">
                                        <div class="col-md-3">
                                            <div class="form-group">
                                                <label for="inputName" class="fontFourteen">Invoice No:<sup class="text-danger">*</sup></label>
                                                <input type="text" class="form-control rounded-0">
                                            </div>
                                        </div>
                                        <div class="col-md-3">
                                            <div class="form-group">
                                                <label for="inputName" class="fontFourteen">Vendor Name:</label>
                                                <input type="text" class="form-control rounded-0">
                                            </div>
                                        </div>
                                        <div class="col-md-3">
                                            <div class="form-group">
                                                <label for="inputName" class="fontFourteen">Contact Person:<sup class="text-danger">*</sup></label>
                                                <input type="text" class="form-control rounded-0">
                                            </div>
                                        </div>
                                        <div class="col-md-3">
                                            <div class="form-group">
                                                <label for="inputName" class="fontFourteen">Mobile No:<sup class="text-danger">*</sup></label>
                                                <input type="text" class="form-control rounded-0">
                                            </div>
                                        </div>
                                        <div class="col-md-12">
                                            <div class="">
                                                <div class="text-right">
                                                    <input id='add-row' class='btn btn-primary rounded-0 btn-sm' type='button' value='Add Product' />
                                                </div>                        
                                                <div class="mt-3">
                                                    <div class="table-responsive">
                                                        <table id="test-table121121" class="table table-condensed w-100" style="min-width: 800px;">
                                                            <thead>
                                                                <tr>
                                                                    <th class="pl-0 fontFourteen">Product Name</th>
                                                                    <th class="pl-0 fontFourteen">Model Name</th>
                                                                    <th class="pl-0 fontFourteen">Model No</th>
                                                                    <th class="pl-0 fontFourteen">Quantity</th>
                                                                    <th class="pl-0 fontFourteen">Price</th>
                                                                    <th class="pl-0 fontFourteen"></th>
                                                                </tr>
                                                            </thead>
                                                            <tbody id="test-body">
                                                                <tr id="row0">
                                                                    <td class="pl-0">
                                                                        <input name='pr_name0' value='Receiver' type='text' class='form-control rounded-0' />
                                                                    </td>
                                                                    <td class="pl-0">
                                                                        <input name='pr_modelName0' value='APL002' type='text' class='form-control rounded-0 input-md' />
                                                                    </td>
                                                                    <td class="pl-0">
                                                                        <input name='pr_model0' value='RL-600' type='text' class='form-control rounded-0 input-md' />
                                                                    </td>
                                                                    <td class="pl-0">
                                                                        <input name='pr_qty0' value='150' type='text' class='form-control rounded-0 input-md' />
                                                                    </td>
                                                                    <td class="pl-0">
                                                                        <input name='pr_price0' value='2500' type='text' class='form-control rounded-0 input-md' />
                                                                    </td>
                                                                    <td class="pl-0">
                                                                        <button class='delete-row btn btn-sm rounded-0 btn-danger '><i class="far fa-trash-alt"></i></button>
                                                                    </td>
                                                                </tr>
                                                            </tbody>
                                                        </table>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="col-md-12">
                                            <div class="form-group mb-0 mt-0">
                                                <button class="btn myThemeBtn">Submit</button>
                                            </div>
                                        </div>
                                    </div>
                                </form> 
                            </div>
                        </div>
                    </div>
                </div>
            </div>


            <br><br><br><br><br><br><br><br><br><br><br><br>

        </div>
</div>
</section>
</div>


<%@include file="/CRM Dashboard/CRM_footer.jsp" %>


<script>
    var row = 1;
    $(document).on("click", "#add-row", function () {
        var new_row = '<tr id="row' + row + '"><td class="pl-0"><input name="pr_name' + row + '" type="text" class="form-control rounded-0" /></td><td class="pl-0"><input name="pr_modelName' + row + '" type="text" class="form-control rounded-0" /></td><td class="pl-0"><input name="pr_model' + row + '" type="text" class="form-control rounded-0" /></td><td class="pl-0"><input name="pr_qty' + row + '" type="text" class="form-control rounded-0" /></td><td class="pl-0"><input name="pr_price' + row + '" type="text" class="form-control rounded-0" /></td><td class="pl-0"><button class="delete-row btn btn-sm rounded-0 btn-danger"><i class="far fa-trash-alt"></i></button></td></tr>';
        // alert(new_row);
        $('#test-body').append(new_row);
        row++;
        return false;
    });
    $(document).on("click", ".delete-row", function () {
        if (row > 1) {
            $(this).closest('tr').remove();
            row--;
        }
        return false;
    });

</script>