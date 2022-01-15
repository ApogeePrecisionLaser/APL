                     
<html>
    <head>
        <script src="https://cdn.anychart.com/releases/v8/js/anychart-base.min.js"></script>
        <script src="https://cdn.anychart.com/releases/v8/js/anychart-ui.min.js"></script>
        <script src="https://cdn.anychart.com/releases/v8/js/anychart-exports.min.js"></script>
        <link href="https://cdn.anychart.com/releases/v8/css/anychart-ui.min.css" type="text/css" rel="stylesheet">
        <link href="https://cdn.anychart.com/releases/v8/fonts/css/anychart-font.min.css" type="text/css" rel="stylesheet">
        <style type="text/css">

            html,
            body,
            #container {
                width: 100%;
                height: 100%;
                margin: 0;
                padding: 0;
            }

        </style>
    </head>
    <body>

        <input type="checkbox" name="source" id="indiamart">Indiamart
        <input type="checkbox" name="source" id="others">Others
        <div id="container"></div>

        <script>

            anychart.onDocumentReady(function () {
                // create pie chart with passed data
                var chart = anychart.pie([
                    ['Resolved', 50],
                    ['Unresolved', 30],
                    ['Assigned', 100],
                    ['Pending', 20]
                ]);

                // creates palette
                var palette = anychart.palettes.distinctColors();
                palette.items([
                    {color: '#7d9db6'},
                    {color: '#8db59d'},
                    {color: '#f38367'},
                    {color: '#b97792'}
                ]);

                // set chart radius
                chart
                        .radius('43%')
                        // create empty area in pie chart
                        .innerRadius('60%')
                        // set chart palette
                        .palette(palette);

                // set outline settings
                chart
                        .outline()
                        .width('3%')
                        .fill(function () {
                            return anychart.color.darken(this.sourceColor, 0.25);
                        });

                // format tooltip
                chart.tooltip().format('Percent Value: {%PercentValue}%');

                // create standalone label and set label settings
                var label = anychart.standalones.label();
                label
                        .enabled(true)
                        .text('Sales Enquiry (200)')
                        .width('100%')
                        .height('100%')
                        .adjustFontSize(true, true)
                        .minFontSize(10)
                        .maxFontSize(25)
                        .fontColor('#60727b')
                        .position('center')
                        .anchor('center')
                        .hAlign('center')
                        .vAlign('middle');

                // set label to center content of chart
                chart.center().content(label);

                // set container id for the chart
                chart.container('container');
                // initiate chart drawing
                chart.draw();
            });

        </script>
    </body>
</html>

