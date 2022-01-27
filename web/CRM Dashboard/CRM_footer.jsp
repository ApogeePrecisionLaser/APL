
<!-- /.content-wrapper -->
<footer class="main-footer pl-3 position-relative">

    <div class="backBtnAllPage d-block d-md-none d-lg-none d-xl-none">
        <a href="#" onclick="history.back()"><img src="CRM Dashboard/assets2/img/product/backIcon.png" width="25"></a>
    </div>
    <strong>Copyright &copy; 2021 <a href="http://apogeeprecision.com/" target="_blank">Apogee Precision LLP</a>.</strong>
    All rights reserved.
    <div class="float-right d-none d-sm-inline-block">
        <!-- <b>Version</b> 3.1.0 -->
    </div>
</footer>


<!-- Control Sidebar -->
<aside class="control-sidebar control-sidebar-dark">
    <!-- Control sidebar content goes here -->
</aside>
</div>


<script src="CRM Dashboard/plugins/jquery/jquery.min.js"></script>
<script src="CRM Dashboard/plugins/jquery-ui/jquery-ui.min.js"></script>
<script src="CRM Dashboard/plugins/bootstrap/js/bootstrap.bundle.min.js"></script>
<script src="CRM Dashboard/assets2/js/adminlte.min.js"></script>
<script src="CRM Dashboard/plugins/flot/jquery.flot.js"></script>
<!--<script src="CRM Dashboard/plugins/flot/plugins/jquery.flot.resize.js"></script>
<script src="CRM Dashboard/plugins/flot/plugins/jquery.flot.pie.js"></script>-->
<script src="CRM Dashboard/assets2/js/demo.js"></script>
<script src="CRM Dashboard/assets2/js/owl.carousel.js"></script>
<script src="CRM Dashboard/plugins/chart.js/Chart.min.js"></script>
<script type="text/javascript" src="//translate.google.com/translate_a/element.js?cb=googleTranslateElementInit"></script>
</body>
</html>

<script type="text/javascript">
            function googleTranslateElementInit() {
                new google.translate.TranslateElement({pageLanguage: 'en'}, 'google_translate_element');
            }
</script>

<!-- <script src="https://code.jquery.com/jquery-3.5.1.js"></script> -->
<script src="https://cdn.datatables.net/1.11.3/js/jquery.dataTables.min.js"></script>

<script>
            $(document).ready(function () {
                $('#mytable').DataTable();
            });
</script>

<script>
    $('.productSlider').owlCarousel({
        loop: true,
        margin: 10,
        dots: false,
        responsiveClass: true,
        navText: ["<i class='fa fa-chevron-left'></i>", "<i class='fa fa-chevron-right'></i>"],
        responsive: {
            0: {
                items: 1,
                nav: true
            },
            600: {
                items: 3,
                nav: false
            },
            1000: {
                items: 4,
                nav: true,
                loop: false
            }
        }


    })
</script>




