<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
        <title>Table</title>

        <!-- Bootstrap -->
        <link rel="stylesheet" type="text/css" href="collapsetable/css/bootstrap.min.css">
        <!--<link href="..collapseTable/css/bootstrap.min.css" rel="stylesheet">-->
    </head>
    <body>
        <h1 class="text-center">Item</h1>

        <style>
            .treegrid-indent {
                width: 0px;
                height: 16px;
                display: inline-block;
                position: relative;
            }

            .treegrid-expander {
                width: 0px;
                height: 16px;
                display: inline-block;
                position: relative;
                left:-17px;
                cursor: pointer;
            }
        </style>

        <div class="container">
            <table id="tree-table" class="table table-hover table-bordered">
                <tbody>
                <th>Item Name</th>
                <th>Item Code</th>
                <th>Item Type</th>
                <th>Quantity</th>
                <th>Generation</th>
                <th>Description</th>
                <th></th>

                <tr data-id="1" data-parent="0" data-level="1">
                    <td data-column="name">Electronics</td>
                    <td>APL_ITEM_101</td>
                    <td>Raw Material</td>
                    <td>0</td>
                    <td>1</td>
                    <td>Additional info</td>
                    <td><a href="" class="btn btn-info">Edit</a><a href="" class="btn btn-danger" style="margin-left:2px">Delete</a></td>
                </tr>


                <tr data-id="2" data-parent="1" data-level="2">
                    <td data-column="name">Active Components</td>
                    <td>APL_ITEM_102</td>
                    <td>Raw Material</td>
                    <td>0</td>
                    <td>2</td>
                    <td>Additional info</td>
                    <td><a href="" class="btn btn-info">Edit</a><a href="" class="btn btn-danger" style="margin-left:2px">Delete</a></td>
                </tr>


                <tr data-id="3" data-parent="2" data-level="3">
                    <td data-column="name">Transistor</td>
                    <td>APL_ITEM_103</td>
                    <td>Raw Material</td>
                    <td>0</td>
                    <td>3</td>
                    <td>Additional info</td>
                    <td><a href="" class="btn btn-info">Edit</a><a href="" class="btn btn-danger" style="margin-left:2px">Delete</a></td>
                </tr>

                <tr data-id="4" data-parent="2" data-level="3">
                    <td data-column="name">Diode</td>
                    <td>APL_ITEM_104</td>
                    <td>Raw Material</td>
                    <td>0</td>
                    <td>3</td>
                    <td>Additional info</td>
                    <td><a href="" class="btn btn-info">Edit</a><a href="" class="btn btn-danger" style="margin-left:2px">Delete</a></td>
                </tr>

                <tr data-id="5" data-parent="1" data-level="2">
                    <td data-column="name">Passive Components</td>
                    <td>APL_ITEM_105</td>
                    <td>Raw Material</td>
                    <td>0</td>
                    <td>2</td>
                    <td>Additional info</td>
                    <td><a href="" class="btn btn-info">Edit</a><a href="" class="btn btn-danger" style="margin-left:2px">Delete</a></td>
                </tr>

                <tr data-id="6" data-parent="5" data-level="3">
                    <td data-column="name">Resistor</td>
                    <td>APL_ITEM_106</td>
                    <td>Raw Material</td>
                    <td>0</td>
                    <td>3</td>
                    <td>Additional info</td>
                    <td><a href="" class="btn btn-info">Edit</a><a href="" class="btn btn-danger" style="margin-left:2px">Delete</a></td>
                </tr>

                <tr data-id="7" data-parent="6" data-level="4">
                    <td data-column="name">SMD</td>
                    <td>APL_ITEM_107</td>
                    <td>Raw Material</td>
                    <td>0</td>
                    <td>4</td>
                    <td>Additional info</td>
                    <td><a href="" class="btn btn-info">Edit</a><a href="" class="btn btn-danger" style="margin-left:2px">Delete</a></td>
                </tr>

                <tr data-id="8" data-parent="7" data-level="5">
                    <td data-column="name">0805-R</td>
                    <td>APL_ITEM_108</td>
                    <td>Raw Material</td>
                    <td>0</td>
                    <td>5</td>
                    <td>Additional info</td>
                    <td><a href="" class="btn btn-info">Edit</a><a href="" class="btn btn-danger" style="margin-left:2px">Delete</a></td>
                </tr>

                <tr data-id="9" data-parent="8" data-level="6">
                    <td data-column="name">0.125W</td>
                    <td>APL_ITEM_109</td>
                    <td>Raw Material</td>
                    <td>0</td>
                    <td>6</td>
                    <td>Additional info</td>
                    <td><a href="" class="btn btn-info">Edit</a><a href="" class="btn btn-danger" style="margin-left:2px">Delete</a></td>
                </tr>

                <tr data-id="10" data-parent="9" data-level="7">
                    <td data-column="name">1k ohm</td>
                    <td>APL_ITEM_110</td>
                    <td>Raw Material</td>
                    <td>0</td>
                    <td>7</td>
                    <td>Additional info</td>
                    <td><a href="" class="btn btn-info">Edit</a><a href="" class="btn btn-danger" style="margin-left:2px">Delete</a></td>
                </tr>

                <tr data-id="11" data-parent="9" data-level="7">
                    <td data-column="name">33k ohm</td>
                    <td>APL_ITEM_111</td>
                    <td>Raw Material</td>
                    <td>0</td>
                    <td>7</td>
                    <td>Additional info</td>
                    <td><a href="" class="btn btn-info">Edit</a><a href="" class="btn btn-danger" style="margin-left:2px">Delete</a></td>
                </tr>

                <tr data-id="12" data-parent="7" data-level="5">
                    <td data-column="name">2512-R</td>
                    <td>APL_ITEM_112</td>
                    <td>Raw Material</td>
                    <td>0</td>
                    <td>5</td>
                    <td>Additional info</td>
                    <td><a href="" class="btn btn-info">Edit</a><a href="" class="btn btn-danger" style="margin-left:2px">Delete</a></td>
                </tr>

                <tr data-id="13" data-parent="6" data-level="4">
                    <td data-column="name">Through Hole</td>
                    <td>APL_ITEM_113</td>
                    <td>Raw Material</td>
                    <td>0</td>
                    <td>4</td>
                    <td>Additional info</td>
                    <td><a href="" class="btn btn-info">Edit</a><a href="" class="btn btn-danger" style="margin-left:2px">Delete</a></td>
                </tr>

                <tr data-id="14" data-parent="0" data-level="1">
                    <td data-column="name">Mechanical</td>
                    <td>APL_ITEM_114</td>
                    <td>Raw Material</td>
                    <td>0</td>
                    <td>1</td>
                    <td>Additional info</td>
                    <td><a href="" class="btn btn-info">Edit</a><a href="" class="btn btn-danger" style="margin-left:2px">Delete</a></td>
                </tr>
                </tbody>
            </table>
        </div>

        <!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
        <script src="collapsetable/js/jquery-3.1.1.min.js"></script>
        <!-- Include all compiled plugins (below), or include individual files as needed -->
        <script src="collapsetable/js/bootstrap.min.js"></script>


        <script src="collapsetable/js/javascript.js"></script>
    </body>
</html>