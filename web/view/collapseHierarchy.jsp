<!DOCTYPE html>
<html>
    <head>
        <title>Bootstrap Treeview Example</title>
        <link href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.0/css/bootstrap.min.css" rel="stylesheet" type="text/css" />
        <link href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-treeview/1.2.0/bootstrap-treeview.min.css" rel="stylesheet" type="text/css" />
    </head>
    <body>
        <div class="col-sm-4">
            <div id="myTree"></div>
        </div>
        <script src="https://code.jquery.com/jquery-2.1.1.min.js" type="text/javascript"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-treeview/1.2.0/bootstrap-treeview.min.js" type="text/javascript"></script>
        <script type="text/javascript">
            $(document).ready(function () {
                var treeData = [
                    {
                        text: "Parent-Item-1",
                        nodes: [
                            {
                                text: "Child-Item-1",
                                nodes: [
                                    {
                                        text: "Grandchild-Item-1"
                                    },
                                    {
                                        text: "Grandchild-Item-2"
                                    }
                                ]
                            },
                            {
                                text: "Child-Item-2"
                            }
                        ]
                    },
                    {
                        text: "Parent-Item-2"
                    },
                    {
                        text: "Parent-Item-3",
                        nodes: [
                            {
                                text: "Child-Item-3",
                                nodes: [
                                    {
                                        text: "Grandchild-Item-1"
                                    }

                                ]
                            },
                            {
                                text: "Child-Item-2"
                            }
                        ]
                    },
                    {
                        text: "Parent-Item-4"
                    },
                    {
                        text: "Parent-Item-5"
                    }
                ];
                $('#myTree').treeview({
                    data: treeData
                });
            });
        </script>
    </body>
</html>