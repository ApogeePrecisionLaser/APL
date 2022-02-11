

<!DOCTYPE html>

<html>
    <head>
        <meta name="viewport" content="width=device-width" />
        <title>Org Chart For Items</title>
        <style id="myStyles">
            html, body {
                margin: 0px;
                padding: 0px;
                width: 100%;
                height: 100%;
                font-family: Helvetica;
                overflow: hidden;
            }

            #tree {
                width: 100%;
                height: 100%;
            }

            /*            partial
                        [lcn='hr-team']>rect {
                            fill: #FFCA28;
                        }
            
                        [lcn='sales-team']>rect {
                            fill: #F57C00;
                        }
            
                        [lcn='top-management']>rect {
                            fill: #f2f2f2;
                        }
            
                        [lcn='top-management']>text,
                        .assistant>text {
                            fill: #aeaeae;
                        }
            
                        [lcn='top-management'] circle,
                        [lcn='assistant'] {
                            fill: #aeaeae;
                        }
            
                        .assistant>rect {
                            fill: #ffffff;
                        }
            
                        .assistant [data-ctrl-n-menu-id]>circle {
                            fill: #aeaeae;
                        }
            
                        .it-team>rect {
                            fill: #b4ffff;
                        }
            
                        .it-team>text {
                            fill: #039BE5;
                        }
            
                        .it-team>[data-ctrl-n-menu-id] line {
                            stroke: #039BE5;
                        }
            
                        .it-team>g>.ripple {
                            fill: #00efef;
                        }
            
                        .hr-team>rect {
                            fill: #fff5d8;
                        }
            
                        .hr-team>text {
                            fill: #ecaf00;
                        }
            
                        .hr-team>[data-ctrl-n-menu-id] line {
                            stroke: #ecaf00;
                        }
            
                        .hr-team>g>.ripple {
                            fill: #ecaf00;
                        }
            
                        .sales-team>rect {
                            fill: #ffeedd;
                        }
            
                        .sales-team>text {
                            fill: #F57C00;
                        }
            
                        .sales-team>[data-ctrl-n-menu-id] line {
                            stroke: #F57C00;
                        }
            
                        .sales-team>g>.ripple {
                            fill: #F57C00;
                        }*/

            .node.generation1 rect {
                fill: #F57C00;
                /*                width:160px;
                                height:50px;*/
            }
            .node.generation2 rect {
                fill: #039BE5;
            }

            .node.generation3 rect {
                fill: #F0BD20;
            }
            .node.generation4 rect {
                fill: #5050b5;
            }
            .node.generation5 rect {
                fill:  #5b8a4b;
            }
            .node.generation6 rect {
                fill: #c66595;
            }
            .node.generation7 rect {
                fill: #91604f;
            }
            .node.generation8 rect {
                fill: #004d4d;
            }
            .node.generation9 rect {
                fill: #808000;
            }
            .node.generation10 rect {
                fill: 	 #ffff4d;
            }


            /*            [data-n-id='1'] rect {
                            fill: #750000;
                        }*/
        </style>

    </head>
    <body>

<!--        <script src="https://balkan.app/js/OrgChart.js"></script>-->
        <script src="js/OrgChart.js"></script>
        <script src="http://ajax.googleapis.com/ajax/libs/jquery/1.7.1/jquery.min.js" type="text/javascript"></script>
        <div id="tree"></div>

        <input type="hidden" name="item_name" id="item_name" value="${item_name}">
        <script>
            window.onload = function () {
                OrgChart.templates.polina.plus = '<circle cx="15" cy="15" r="15" fill="#ffffff" stroke="#aeaeae" stroke-width="1"></circle>'
                        + '<text text-anchor="middle" style="font-size: 18px;cursor:pointer;" fill="#757575" x="15" y="22">{collapsed-children-count}</text>';
                OrgChart.templates.itTemplate = Object.assign({}, OrgChart.templates.ana);
                OrgChart.templates.itTemplate.nodeMenuButton = "";
                OrgChart.templates.itTemplate.nodeCircleMenuButton = {
                    radius: 18,
                    x: 250,
                    y: 60,
                    color: '#fff',
                    stroke: '#aeaeae'
                };
                OrgChart.templates.invisibleGroup.padding = [20, 0, 0, 0];
                var chart = new OrgChart(document.getElementById("tree"), {
                    layout: OrgChart.mixed,
//                    template: "base",
                    // enableSearch: false,
//                    template: "ana",
                    template: "polina",

                    enableDragDrop: true,
                    assistantSeparation: 170,
//                    nodeCircleMenu: {
//                        details: {
//                            icon: OrgChart.icon.details(24, 24, '#aeaeae'),
//                            text: "Details",
//                            color: "white"
//                        },
//                        edit: {
//                            icon: OrgChart.icon.edit(24, 24, '#aeaeae'),
//                            text: "Edit node",
//                            color: "white"
//                        },
//                        add: {
//                            icon: OrgChart.icon.add(24, 24, '#aeaeae'),
//                            text: "Add node",
//                            color: "white"
//                        },
//                        remove: {
//                            icon: OrgChart.icon.remove(24, 24, '#aeaeae'),
//                            text: "Remove node",
//                            color: '#fff',
//                        },
//                        addLink: {
//                            icon: OrgChart.icon.link(24, 24, '#aeaeae'),
//                            text: "Add C link(drag and drop)",
//                            color: '#fff',
//                            draggable: true
//                        }
//                    },
                    menu: {
                        pdfPreview: {
                            text: "Export to PDF",
                            icon: OrgChart.icon.pdf(24, 24, '#7A7A7A'),
                            onClick: preview
                        },
                        csv: {text: "Save as CSV"}
                    },
//                    nodeMenu: {
//                        details: {text: "Details"},
//                        edit: {text: "Edit"},
//                        add: {text: "Add"},
//                        remove: {text: "Remove"}
//                    },
                    align: OrgChart.ORIENTATION,
                    toolbar: {
                        fullScreen: true,
                        zoom: true,
                        fit: true,
                        expandAll: true
                    },
                    nodeBinding: {
                        field_0: "name",
                        field_1: "title",
                        img_0: "img"
                    },
//                    tags: {
//                        "top-management": {
//                            template: "invisibleGroup",
//                            subTreeConfig: {
//                                orientation: OrgChart.orientation.bottom,
//                                collapse: {
//                                    level: 1
//                                }
//                            }
//                        },
//                        "it-team": {
//                            subTreeConfig: {
//                                layout: OrgChart.mixed,
//                                collapse: {
//                                    level: 1
//                                }
//                            },
//                        },
//                        "hr-team": {
//                            subTreeConfig: {
//                                layout: OrgChart.treeRightOffset,
//                                collapse: {
//                                    level: 1
//                                }
//                            },
//                        },
//                        "sales-team": {
//                            subTreeConfig: {
//                                layout: OrgChart.mixed,
//                                collapse: {
//                                    level: 1
//                                }
//                            },
//                        },
//                        "seo-menu": {
//                            nodeMenu: {
//                                addSharholder: {text: "Add new sharholder", icon: OrgChart.icon.add(24, 24, "#7A7A7A"), onClick: addSharholder},
//                                addDepartment: {text: "Add new department", icon: OrgChart.icon.add(24, 24, "#7A7A7A"), onClick: addDepartment},
//                                addAssistant: {text: "Add new assitsant", icon: OrgChart.icon.add(24, 24, "#7A7A7A"), onClick: addAssistant},
//                                edit: {text: "Edit"},
//                                details: {text: "Details"},
//                            }
//                        },
//                        "menu-without-add": {
//                            nodeMenu: {
//                                details: {text: "Details"},
//                                edit: {text: "Edit"},
//                                remove: {text: "Remove"}
//                            }
//                        },
//                        "department": {
//                            template: "group",
//                            nodeMenu: {
//                                addManager: {text: "Add new manager", icon: OrgChart.icon.add(24, 24, "#7A7A7A"), onClick: addManager},
//                                remove: {text: "Remove department"},
//                                edit: {text: "Edit department"},
//                                nodePdfPreview: {text: "Export department to PDF", icon: OrgChart.icon.pdf(24, 24, "#7A7A7A"), onClick: nodePdfPreview}
//                            }
//                        },
//                        "it-team-member": {
//                            template: "itTemplate",
//                        }
//                    },
//                    clinks: [
//                        {from: 11, to: 100}
//                    ]
                });
                chart.nodeCircleMenuUI.on('click', function (sender, args) {
                    switch (args.menuItem.text) {
                        case "Details":
                            chart.editUI.show(args.nodeId, true);
                            break;
                        case "Add node":
                            {
                                var id = chart.generateId();
                                chart.addNode({id: id, pid: args.nodeId, tags: ["it-team-member"]});
                            }
                            break;
                        case "Edit node":
                            chart.editUI.show(args.nodeId);
                            break;
                        case "Remove node":
                            chart.removeNode(args.nodeId);
                            break;
                        default:
                    }
                    ;
                });
                chart.nodeCircleMenuUI.on('drop', function (sender, args) {
                    chart.addClink(args.from, args.to).draw(OrgChart.action.update);
                });
                chart.on("added", function (sender, id) {
                    sender.editUI.show(id);
                });
                chart.on('drop', function (sender, draggedNodeId, droppedNodeId) {
                    var draggedNode = sender.getNode(draggedNodeId);
                    var droppedNode = sender.getNode(droppedNodeId);
                    if (droppedNode.tags.indexOf("department") != -1 && draggedNode.tags.indexOf("department") == -1) {
                        var draggedNodeData = sender.get(draggedNode.id);
                        draggedNodeData.pid = null;
                        draggedNodeData.stpid = droppedNode.id;
                        sender.updateNode(draggedNodeData);
                        return false;
                    }
                });
                chart.editUI.on('field', function (sender, args) {
                    var isDeprtment = sender.node.tags.indexOf("department") != -1;
                    var deprtmentFileds = ["name"];
                    if (isDeprtment && deprtmentFileds.indexOf(args.name) == -1) {
                        return false;
                    }
                });
                
                chart.on('exportstart', function (sender, args) {
                    args.styles = document.getElementById('myStyles').outerHTML;
                });
                
                
                var item_name = [];
                var item_name_id = [];
                var parent_id = [];
                var generation = [];
                var org_chart_data;
                var json = "";
                var nodes;
                var item_names = $('#item_name').val();
                $.ajax({
                    url: "ItemNameController",
                    dataType: "json",
                    data: {action1: "getItems", item_name: item_names},
                    crossDomain:true,
                    success: function (data) {
                        org_chart_data = data.org_chart_data;
                        console.log(org_chart_data);
                        //for loop
                        for (var i = 0; i < org_chart_data.length; i++) {
                            item_name[i] = org_chart_data[i]["item_name"];
                            item_name_id[i] = org_chart_data[i]["item_name_id"];
                            parent_id[i] = org_chart_data[i]["parent_id"];
                            parent_id[i] = parseInt(parent_id[i]);
                            generation[i] = org_chart_data[i]["generation"];
                            json +=
                                    '{"id": ' + item_name_id[i] + ', "pid": ' + parent_id[i] + ', "name":"' + item_name[i] + '","tags":["generation' + generation[i] + '"], "title": "generation' + generation[i] + '"},';
                        }
                        json = json.slice(0, -1);

                        console.log(json);
                         nodes = JSON.parse('[' + json + ']');

//                        nodes = [
//                            {id: 1, pid: 0, name: "Electronics", title: "generation1",tags: ["generation1"], img: "https://static1.bigstockphoto.com/7/4/8/large1500/84712196.jpg"},
//                            {id: 2, pid: 1, name: "Active", title: "generation2", tags: ["generation2"],img: "https://static1.bigstockphoto.com/7/4/8/large1500/84712196.jpg"},
//                            {id: 3, pid: 1, name: "Passive", title: "generation2", tags: ["generation2"],img: "https://static1.bigstockphoto.com/7/4/8/large1500/84712196.jpg"},
//
//                            {id: 4, pid: 3, name: "Capacitor", title: "generation3",tags: ["generation3"], img: "https://static1.bigstockphoto.com/7/4/8/large1500/84712196.jpg"},
//                            {id: 5, pid: 4, name: "smd", title: "generation4",tags: ["generation4"], img: "https://static1.bigstockphoto.com/7/4/8/large1500/84712196.jpg"},
//                            {id: 6, pid: 5, name: "0805", title: "generation5",tags: ["generation5"], img: "https://static1.bigstockphoto.com/7/4/8/large1500/84712196.jpg"},
//                            {id: 7, pid: 6, name: "10uF,25V", title: "generation6",tags: ["generation6"], img: "https://static1.bigstockphoto.com/7/4/8/large1500/84712196.jpg"},
//                            {id: 8, pid: 5, name: "1206", title: "generation5", tags: ["generation5"],img: "https://static1.bigstockphoto.com/7/4/8/large1500/84712196.jpg"},
//                            {id: 9, pid: 4, name: "PTH", title: "generation4",tags: ["generation4"], img: "https://static1.bigstockphoto.com/7/4/8/large1500/84712196.jpg"},
//
//                            {id: 10, pid: 3, name: "Resistor", title: "generation3",tags: ["generation3"], img: "https://static1.bigstockphoto.com/7/4/8/large1500/84712196.jpg"},
//                            {id: 11, pid: 10, name: "smd", title: "generation4",tags: ["generation4"], img: "https://static1.bigstockphoto.com/7/4/8/large1500/84712196.jpg"},
//                            {id: 12, pid: 10, name: "PTH", title: "generation4", tags: ["generation4"],img: "https://static1.bigstockphoto.com/7/4/8/large1500/84712196.jpg"},
//
//                            {id: 13, pid: 3, name: "Connector", title: "generation3",tags: ["generation3"], img: "https://static1.bigstockphoto.com/7/4/8/large1500/84712196.jpg"},
//                            {id: 14, pid: 13, name: "smd", title: "generation4",tags: ["generation4"], img: "https://static1.bigstockphoto.com/7/4/8/large1500/84712196.jpg"},
//                            {id: 15, pid: 13, name: "PTH", title: "generation4", tags: ["generation4"],img: "https://static1.bigstockphoto.com/7/4/8/large1500/84712196.jpg"},
//                            {id: 16, pid: 13, name: "Relimate Connector", title: "generation4",tags: ["generation4"], img: "https://static1.bigstockphoto.com/7/4/8/large1500/84712196.jpg"}
//                        ];


                        for (var j = 0; j < nodes.length; j++) {
                            var node = nodes[j];

                            switch (node.title) {
                                case "generation1":
                                    node.tags = ["generation1"];
                                    break;
                                case "generation2":
                                    node.tags = ["generation2"];
                                    break;
                                case "generation3":
                                    node.tags = ["generation3"];
                                    break;
                                case "generation4":
                                    node.tags = ["generation4"];
                                    break;
                                case "generation5":
                                    node.tags = ["generation5"];
                                    break;
                                case "generation6":
                                    node.tags = ["generation6"];
                                    break;
                                case "generation7":
                                    node.tags = ["generation7"];
                                    break;
                                case "generation8":
                                    node.tags = ["generation8"];
                                    break;
                                case "generation9":
                                    node.tags = ["generation9"];
                                    break;
                                case "generation10":
                                    node.tags = ["generation10"];
                                    break;
                            }
                        }


                         chart.load(JSON.parse('[' + json + ']'));
                        
                        
//                        chart.load([
//                            {id: 1, pid: 0, name: "Electronics", title: "generation1",tags: ["generation1"], img: "https://static1.bigstockphoto.com/7/4/8/large1500/84712196.jpg"},
//                            {id: 2, pid: 1, name: "Active", title: "generation2", tags: ["generation2"],img: "https://static1.bigstockphoto.com/7/4/8/large1500/84712196.jpg"},
//                            {id: 3, pid: 1, name: "Passive", title: "generation2", tags: ["generation2"],img: "https://static1.bigstockphoto.com/7/4/8/large1500/84712196.jpg"},
//
//                            {id: 4, pid: 3, name: "Capacitor", title: "generation3",tags: ["generation3"], img: "https://static1.bigstockphoto.com/7/4/8/large1500/84712196.jpg"},
//                            {id: 5, pid: 4, name: "smd", title: "generation4",tags: ["generation4"], img: "https://static1.bigstockphoto.com/7/4/8/large1500/84712196.jpg"},
//                            {id: 6, pid: 5, name: "0805", title: "generation5",tags: ["generation5"], img: "https://static1.bigstockphoto.com/7/4/8/large1500/84712196.jpg"},
//                            {id: 7, pid: 6, name: "10uF,25V", title: "generation6",tags: ["generation6"], img: "https://static1.bigstockphoto.com/7/4/8/large1500/84712196.jpg"},
//                            {id: 8, pid: 5, name: "1206", title: "generation5", tags: ["generation5"],img: "https://static1.bigstockphoto.com/7/4/8/large1500/84712196.jpg"},
//                            {id: 9, pid: 4, name: "PTH", title: "generation4",tags: ["generation4"], img: "https://static1.bigstockphoto.com/7/4/8/large1500/84712196.jpg"},
//
//                            {id: 10, pid: 3, name: "Resistor", title: "generation3",tags: ["generation3"], img: "https://static1.bigstockphoto.com/7/4/8/large1500/84712196.jpg"},
//                            {id: 11, pid: 10, name: "smd", title: "generation4",tags: ["generation4"], img: "https://static1.bigstockphoto.com/7/4/8/large1500/84712196.jpg"},
//                            {id: 12, pid: 10, name: "PTH", title: "generation4", tags: ["generation4"],img: "https://static1.bigstockphoto.com/7/4/8/large1500/84712196.jpg"},
//
//                            {id: 13, pid: 3, name: "Connector", title: "generation3",tags: ["generation3"], img: "https://static1.bigstockphoto.com/7/4/8/large1500/84712196.jpg"},
//                            {id: 14, pid: 13, name: "smd", title: "generation4",tags: ["generation4"], img: "https://static1.bigstockphoto.com/7/4/8/large1500/84712196.jpg"},
//                            {id: 15, pid: 13, name: "PTH", title: "generation4", tags: ["generation4"],img: "https://static1.bigstockphoto.com/7/4/8/large1500/84712196.jpg"},
//                            {id: 16, pid: 13, name: "Relimate Connector", title: "generation4",tags: ["generation4"], img: "https://static1.bigstockphoto.com/7/4/8/large1500/84712196.jpg"}
//                        ]);
                    }
                });




                function preview() {
                    OrgChart.pdfPrevUI.show(chart, {
                        format: 'A4'
                    });
                }

                function nodePdfPreview(nodeId) {
                    OrgChart.pdfPrevUI.show(chart, {
                        format: 'A4',
                        nodeId: nodeId
                    });
                }
            };
        </script>
    </body>
</html>
