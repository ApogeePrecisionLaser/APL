<!-- /.content-wrapper -->
<footer class="main-footer pl-3">
    <strong>Copyright &copy; 2021 <a href="#">Apogee Precision LLP</a>.</strong>
    All rights reserved.
    <div class="float-right d-none d-sm-inline-block">
        <!-- <b>Version</b> 3.1.0 -->
    </div>
</footer>




<!-- Control Sidebar -->
<aside class="control-sidebar control-sidebar-dark">
    <!-- Control sidebar content goes here -->
</aside>
<!-- /.control-sidebar -->

</div>
<!-- ./wrapper -->

<script src="CRM Dashboard/plugins/jquery/jquery.min.js"></script>
<!--jQuery UI 1.11.4--> 
<script src="CRM Dashboard/plugins/jquery-ui/jquery-ui.min.js"></script>
<!--Resolve conflict in jQuery UI tooltip with Bootstrap tooltip--> 
<script>
    $.widget.bridge('uibutton', $.ui.button)
</script>








<script src="CRM Dashboard/plugins/bootstrap/js/bootstrap.bundle.min.js"></script>
<!--<script src="CRM Dashboard/plugins/chart.js/Chart.min.js"></script>
<script src="CRM Dashboard/plugins/sparklines/sparkline.js"></script>
<script src="CRM Dashboard/plugins/jqvmap/jquery.vmap.min.js"></script>
<script src="CRM Dashboard/plugins/jqvmap/maps/jquery.vmap.usa.js"></script>
<script src="CRM Dashboard/plugins/jquery-knob/jquery.knob.min.js"></script>
<script src="CRM Dashboard/plugins/moment/moment.min.js"></script>
<script src="CRM Dashboard/plugins/daterangepicker/daterangepicker.js"></script>-->
<script src="CRM Dashboard/plugins/tempusdominus-bootstrap-4/js/tempusdominus-bootstrap-4.min.js"></script>
<!--<script src="CRM Dashboard/plugins/summernote/summernote-bs4.min.js"></script>-->
<script src="CRM Dashboard/plugins/overlayScrollbars/js/jquery.overlayScrollbars.min.js"></script>
<script src="CRM Dashboard/assets2/js/adminlte.js"></script>
<script src="CRM Dashboard/assets2/js/demo.js"></script>
<script src="CRM Dashboard/assets2/js/owl.carousel.js"></script>
<script type="text/javascript" src="//translate.google.com/translate_a/element.js?cb=googleTranslateElementInit"></script>

<!--<script src="CRM Dashboard/assets2/js/dashboard.js"></script>-->


<!--<script src="https://cdn.datatables.net/1.10.25/js/jquery.dataTables.min.js"></script>
<script src="https://cdn.datatables.net/buttons/1.7.1/js/dataTables.buttons.min.js"></script>-->

<script src="https://cdn.datatables.net/1.11.3/js/jquery.dataTables.min.js"></script>

<!--<script src = "https://code.jquery.com/jquery-1.10.2.js"></script>-->
<!--<script src = "https://code.jquery.com/ui/1.10.4/jquery-ui.js"></script>-->



</body>
</html>


<script type="text/javascript">
    function googleTranslateElementInit() {
      new google.translate.TranslateElement({pageLanguage: 'en'}, 'google_translate_element');
    }
</script>

<script>
    $(document).ready(function() {
    $('#mytable').DataTable();
} );
</script>

<!--<script>
    $(document).ready(function () {
        $('#mytable').DataTable({
            scrollY: '60vh',
            scrollCollapse: true,
            paging: true
        });
    });
</script>-->

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






