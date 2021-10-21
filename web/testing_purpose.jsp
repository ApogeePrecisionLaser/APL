<!DOCTYPE html>
<html lang="en">
    <body class="sb-nav-fixed">
        <div id="layoutSidenav">

            {% include "menu.html" %}

            <div id="layoutSidenav_content">
                <main>
                    <div class="container-fluid px-4">
                        <h2 class="mt-4" style="border-bottom:solid black 2px">Data Flow</h2>

                        <div class="row">
                            <div class="col-xl-12" style="margin-top:45px">
                                <div class="card mb-4">
                                    <div class="card-header">
                                        Configuration
                                    </div>
                                    <div class="card-body">

                                        <div class="row" style="margin-top:5px">
                                            <div class="col-lg-2">
                                                <label>Data Flow :</label>
                                            </div>
                                            <div class="col-lg-3">
                                                <select name="data_flow" id="data_flow">
                                                    {% if data_type == 'correction' %}
                                                    <option value="correction" selected>Correction</option>
                                                    <!--<option value="navigation">Navigation</option> -->
                                                    <option value="raw_data">Raw Data</option>
                                                    {% else %}
                                                    <option value="correction">Correction</option>
                                                    <!--<option value="navigation" selected>Navigation</option> -->
                                                    <option value="raw_data" selected>Raw Data</option>
                                                    {% endif %}
                                                </select>
                                            </div>
                                            <div class="col-lg-2">
                                                <select name="rtcm" id="rtcm" style="display:none">
                                                    {% if corr_type == 'rtcm3.0' %}
                                                    <option value="rtcm3.0" selected>RTCM3.0</option>
                                                    <option value="rtcm3.2">RTCM3.2</option>
                                                    {% else %}
                                                    <option value="rtcm3.0">RTCM3.0</option>
                                                    <option value="rtcm3.2" selected>RTCM3.2</option>
                                                    {% endif %}
                                                </select>
                                            </div>
                                        </div>


                                        <div class="row" style="margin-top:15px;display:none" id="rtcm3_2" name="rtcm3_2">

                                            <!-- Start 1st Row -->

                                            <div class="row" style="margin-top:15px">
                                                <div class="col-lg-4">
                                                    <div class="row">
                                                        <div class="col-md-5">
                                                            <div class="form-group">
                                                                <label for="email"><b>1005b:</b></label>
                                                                <select name="r1005b_2" id="r1005b_2" style="width:100%;">  
                                                                    {% if 1005b_2 == '1' %}                                          
                                                                    <option value="1" selected>ON</option>
                                                                    <option value="0">OFF</option>
                                                                    {% else %}
                                                                    <option value="1">ON</option>
                                                                    <option value="0" selected>OFF</option>
                                                                    {% endif %}
                                                                </select>
                                                            </div>												
                                                        </div> 
                                                        <div class="col-lg-5">
                                                            <div class="form-group">
                                                                <label for="email"><b>Duration:</b></label>
                                                                <select name="r1005b_1" id="r1005b_1" style="width:100%;">  
                                                                    {% if 1005b_1 == '1' %}   
                                                                    <option value="1" selected>1 sec</option>
                                                                    {% elif 1005b_1 == '5' %}
                                                                    <option value="5" selected>5 sec</option>
                                                                    {% elif 1005b_1 == '15' %}
                                                                    <option value="15" selected>15 sec</option>
                                                                    {% elif 1005b_1 == '30' %}
                                                                    <option value="30" selected>30 sec</option>
                                                                    {% endif %}                                           
                                                                    <option value="1">1 sec</option>
                                                                    <option value="5">5 sec</option>
                                                                    <option value="10">10 sec</option>
                                                                    <option value="15">15 sec</option>
                                                                    <option value="30">30 sec</option>
                                                                </select>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>


                                                <div class="col-lg-4">
                                                    <div class="row">
                                                        <div class="col-md-5">
                                                            <div class="form-group">
                                                                <label for="email"><b>1007b:</b></label>
                                                                <select name="r1007b_2" id="r1007b_2" style="width:100%;">  
                                                                    {% if 1007b_2 == '1' %}                                          
                                                                    <option value="1" selected>ON</option>
                                                                    <option value="0">OFF</option>
                                                                    {% else %}
                                                                    <option value="1">ON</option>
                                                                    <option value="0" selected>OFF</option>
                                                                    {% endif %}
                                                                </select>
                                                            </div>												
                                                        </div> 
                                                        <div class="col-lg-5">
                                                            <div class="form-group">
                                                                <label for="email"><b>Duration:</b></label>
                                                                <select name="r1007b_1" id="r1007b_1" style="width:100%;">  
                                                                    {% if 1007b_1 == '1' %}   
                                                                    <option value="1" selected>1 sec</option>
                                                                    {% elif 1007b_1 == '5' %}
                                                                    <option value="5" selected>5 sec</option>
                                                                    {% elif 1007b_1 == '15' %}
                                                                    <option value="15" selected>15 sec</option>
                                                                    {% elif 1007b_1 == '30' %}
                                                                    <option value="30" selected>30 sec</option>
                                                                    {% endif %}                                           
                                                                    <option value="1">1 sec</option>
                                                                    <option value="5">5 sec</option>
                                                                    <option value="10">10 sec</option>
                                                                    <option value="15">15 sec</option>
                                                                    <option value="30">30 sec</option>
                                                                </select>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>


                                                <div class="col-lg-4">
                                                    <div class="row">
                                                        <div class="col-md-5">
                                                            <div class="form-group">
                                                                <label for="email"><b>1033b:</b></label>
                                                                <select name="r1033b_2" id="r1033b_2" style="width:100%;">  
                                                                    {% if 1033b_2 == '1' %}                                          
                                                                    <option value="1" selected>ON</option>
                                                                    <option value="0">OFF</option>
                                                                    {% else %}
                                                                    <option value="1">ON</option>
                                                                    <option value="0" selected>OFF</option>
                                                                    {% endif %}
                                                                </select>
                                                            </div>												
                                                        </div> 
                                                        <div class="col-lg-5">
                                                            <div class="form-group">
                                                                <label for="email"><b>Duration:</b></label>
                                                                <select name="r1033b_1" id="r1033b_1" style="width:100%;">  
                                                                    {% if 1033b_1 == '1' %}   
                                                                    <option value="1" selected>1 sec</option>
                                                                    {% elif 1033b_1 == '5' %}
                                                                    <option value="5" selected>5 sec</option>
                                                                    {% elif 1033b_1 == '15' %}
                                                                    <option value="15" selected>15 sec</option>
                                                                    {% elif 1033b_1 == '30' %}
                                                                    <option value="30" selected>30 sec</option>
                                                                    {% endif %}                                           
                                                                    <option value="1">1 sec</option>
                                                                    <option value="5">5 sec</option>
                                                                    <option value="10">10 sec</option>
                                                                    <option value="15">15 sec</option>
                                                                    <option value="30">30 sec</option>
                                                                </select>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>

                                            </div>

                                            <!-- End 1st Row -->


                                            <!-- Start 2nd Row -->

                                            <div class="row" style="margin-top:15px">
                                                <div class="col-lg-4">
                                                    <div class="row">
                                                        <div class="col-md-5">
                                                            <div class="form-group">
                                                                <label for="email"><b>1074b:</b></label>
                                                                <select name="r1074b_2" id="r1074b_2" style="width:100%;">  
                                                                    {% if 1074b_2 == '1' %}                                          
                                                                    <option value="1" selected>ON</option>
                                                                    <option value="0">OFF</option>
                                                                    {% else %}
                                                                    <option value="1">ON</option>
                                                                    <option value="0" selected>OFF</option>
                                                                    {% endif %}
                                                                </select>
                                                            </div>												
                                                        </div> 
                                                        <div class="col-lg-5">
                                                            <div class="form-group">
                                                                <label for="email"><b>Duration:</b></label>
                                                                <select name="r1074b_1" id="r1074b_1" style="width:100%;">  
                                                                    {% if 1074b_1 == '1' %}   
                                                                    <option value="1" selected>1 sec</option>
                                                                    {% elif 1074b_1 == '5' %}
                                                                    <option value="5" selected>5 sec</option>
                                                                    {% elif 1074b_1 == '15' %}
                                                                    <option value="15" selected>15 sec</option>
                                                                    {% elif 1074b_1 == '30' %}
                                                                    <option value="30" selected>30 sec</option>
                                                                    {% endif %}                                           
                                                                    <option value="1">1 sec</option>
                                                                    <option value="5">5 sec</option>
                                                                    <option value="10">10 sec</option>
                                                                    <option value="15">15 sec</option>
                                                                    <option value="30">30 sec</option>
                                                                </select>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>


                                                <div class="col-lg-4">
                                                    <div class="row">
                                                        <div class="col-md-5">
                                                            <div class="form-group">
                                                                <label for="email"><b>1084b:</b></label>
                                                                <select name="r1084b_2" id="r1084b_2" style="width:100%;">  
                                                                    {% if 1084b_2 == '1' %}                                          
                                                                    <option value="1" selected>ON</option>
                                                                    <option value="0">OFF</option>
                                                                    {% else %}
                                                                    <option value="1">ON</option>
                                                                    <option value="0" selected>OFF</option>
                                                                    {% endif %}
                                                                </select>
                                                            </div>												
                                                        </div> 
                                                        <div class="col-lg-5">
                                                            <div class="form-group">
                                                                <label for="email"><b>Duration:</b></label>
                                                                <select name="r1084b_1" id="r1084b_1" style="width:100%;">  
                                                                    {% if 1084b_1 == '1' %}   
                                                                    <option value="1" selected>1 sec</option>
                                                                    {% elif 1084b_1 == '5' %}
                                                                    <option value="5" selected>5 sec</option>
                                                                    {% elif 1084b_1 == '15' %}
                                                                    <option value="15" selected>15 sec</option>
                                                                    {% elif 1084b_1 == '30' %}
                                                                    <option value="30" selected>30 sec</option>
                                                                    {% endif %}                                           
                                                                    <option value="1">1 sec</option>
                                                                    <option value="5">5 sec</option>
                                                                    <option value="10">10 sec</option>
                                                                    <option value="15">15 sec</option>
                                                                    <option value="30">30 sec</option>
                                                                </select>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>


                                                <div class="col-lg-4">
                                                    <div class="row">
                                                        <div class="col-md-5">
                                                            <div class="form-group">
                                                                <label for="email"><b>1124b:</b></label>
                                                                <select name="r1124b_2" id="r1124b_2" style="width:100%;">  
                                                                    {% if 1124b_2 == '1' %}                                          
                                                                    <option value="1" selected>ON</option>
                                                                    <option value="0">OFF</option>
                                                                    {% else %}
                                                                    <option value="1">ON</option>
                                                                    <option value="0" selected>OFF</option>
                                                                    {% endif %}
                                                                </select>
                                                            </div>												
                                                        </div> 
                                                        <div class="col-lg-5">
                                                            <div class="form-group">
                                                                <label for="email"><b>Duration:</b></label>
                                                                <select name="r1124b_1" id="r1124b_1" style="width:100%;">  
                                                                    {% if 1124b_1 == '1' %}   
                                                                    <option value="1" selected>1 sec</option>
                                                                    {% elif 1124b_1 == '5' %}
                                                                    <option value="5" selected>5 sec</option>
                                                                    {% elif 1124b_1 == '15' %}
                                                                    <option value="15" selected>15 sec</option>
                                                                    {% elif 1124b_1 == '30' %}
                                                                    <option value="30" selected>30 sec</option>
                                                                    {% endif %}                                           
                                                                    <option value="1">1 sec</option>
                                                                    <option value="5">5 sec</option>
                                                                    <option value="10">10 sec</option>
                                                                    <option value="15">15 sec</option>
                                                                    <option value="30">30 sec</option>
                                                                </select>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>

                                            </div>

                                            <!-- End 2nd Row -->

                                            <!-- Start 3rd Row -->


                                            <div class="row" style="margin-top:15px">
                                                <div class="col-lg-4">
                                                    <div class="row">
                                                        <div class="col-md-5">
                                                            <div class="form-group">
                                                                <label for="email"><b>1006b:</b></label>
                                                                <select name="r1006b_2" id="r1006b_2" style="width:100%;">  
                                                                    {% if 1006b_2 == '1' %}                                          
                                                                    <option value="1" selected>ON</option>
                                                                    <option value="0">OFF</option>
                                                                    {% else %}
                                                                    <option value="1">ON</option>
                                                                    <option value="0" selected>OFF</option>
                                                                    {% endif %}
                                                                </select>
                                                            </div>												
                                                        </div> 
                                                        <div class="col-lg-5">
                                                            <div class="form-group">
                                                                <label for="email"><b>Duration:</b></label>
                                                                <select name="r1006b_1" id="r1006b_1" style="width:100%;">  
                                                                    {% if 1006b_1 == '1' %}   
                                                                    <option value="1" selected>1 sec</option>
                                                                    {% elif 1006b_1 == '5' %}
                                                                    <option value="5" selected>5 sec</option>
                                                                    {% elif 1006b_1 == '15' %}
                                                                    <option value="15" selected>15 sec</option>
                                                                    {% elif 1006b_1 == '30' %}
                                                                    <option value="30" selected>30 sec</option>
                                                                    {% endif %}                                           
                                                                    <option value="1">1 sec</option>
                                                                    <option value="5">5 sec</option>
                                                                    <option value="10">10 sec</option>
                                                                    <option value="15">15 sec</option>
                                                                    <option value="30">30 sec</option>
                                                                </select>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>


                                                <div class="col-lg-4">
                                                    <div class="row">
                                                        <div class="col-md-5">
                                                            <div class="form-group">
                                                                <label for="email"><b>1019b:</b></label>
                                                                <select name="r1019b_2" id="r1019b_2" style="width:100%;">  
                                                                    {% if 1019b_2 == '1' %}                                          
                                                                    <option value="1" selected>ON</option>
                                                                    <option value="0">OFF</option>
                                                                    {% else %}
                                                                    <option value="1">ON</option>
                                                                    <option value="0" selected>OFF</option>
                                                                    {% endif %}
                                                                </select>
                                                            </div>												
                                                        </div> 
                                                        <div class="col-lg-5">
                                                            <div class="form-group">
                                                                <label for="email"><b>Duration:</b></label>
                                                                <select name="r1019b_1" id="r1019b_1" style="width:100%;">  
                                                                    {% if 1019b_1 == '1' %}   
                                                                    <option value="1" selected>1 sec</option>
                                                                    {% elif 1019b_1 == '5' %}
                                                                    <option value="5" selected>5 sec</option>
                                                                    {% elif 1019b_1 == '15' %}
                                                                    <option value="15" selected>15 sec</option>
                                                                    {% elif 1019b_1 == '30' %}
                                                                    <option value="30" selected>30 sec</option>
                                                                    {% endif %}                                           
                                                                    <option value="1">1 sec</option>
                                                                    <option value="5">5 sec</option>
                                                                    <option value="10">10 sec</option>
                                                                    <option value="15">15 sec</option>
                                                                    <option value="30">30 sec</option>
                                                                </select>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>


                                                <div class="col-lg-4">
                                                    <div class="row">
                                                        <div class="col-md-5">
                                                            <div class="form-group">
                                                                <label for="email"><b>1020b:</b></label>
                                                                <select name="r1020b_2" id="r1020b_2" style="width:100%;">  
                                                                    {% if 1020b_2 == '1' %}                                          
                                                                    <option value="1" selected>ON</option>
                                                                    <option value="0">OFF</option>
                                                                    {% else %}
                                                                    <option value="1">ON</option>
                                                                    <option value="0" selected>OFF</option>
                                                                    {% endif %}
                                                                </select>
                                                            </div>												
                                                        </div> 
                                                        <div class="col-lg-5">
                                                            <div class="form-group">
                                                                <label for="email"><b>Duration:</b></label>
                                                                <select name="r020b_1" id="r1020b_1" style="width:100%;">  
                                                                    {% if 1020b_1 == '1' %}   
                                                                    <option value="1" selected>1 sec</option>
                                                                    {% elif 1020b_1 == '5' %}
                                                                    <option value="5" selected>5 sec</option>
                                                                    {% elif 1020b_1 == '15' %}
                                                                    <option value="15" selected>15 sec</option>
                                                                    {% elif 1020b_1 == '30' %}
                                                                    <option value="30" selected>30 sec</option>
                                                                    {% endif %}                                           
                                                                    <option value="1">1 sec</option>
                                                                    <option value="5">5 sec</option>
                                                                    <option value="10">10 sec</option>
                                                                    <option value="15">15 sec</option>
                                                                    <option value="30">30 sec</option>
                                                                </select>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>

                                            </div>

                                            <!-- End 3rd Row -->

                                        </div>

                                        <!-- updated -->
                                        <div class="row" style="margin-top:15px;display:none" id="raw_data" name="raw_data">

                                            <div class="row" style="margin-top:15px">
                                                <div class="col-lg-4">
                                                    <div class="row">
                                                        <div class="col-md-5">
                                                            <div class="form-group">
                                                                <label for="email"><b>rawephemb:</b></label>
                                                                <select name="rawephemb_2" id="rawephemb_2" style="width:100%;">  
                                                                    {% if rawephemb_s == '1' %}                                          
                                                                    <option value="1" selected>ON</option>
                                                                    <option value="0">OFF</option>
                                                                    {% else %}
                                                                    <option value="1">ON</option>
                                                                    <option value="0" selected>OFF</option>
                                                                    {% endif %}
                                                                </select>
                                                            </div>												
                                                        </div> 
                                                        <div class="col-lg-5">
                                                            <div class="form-group">
                                                                <label for="email"><b>Duration:</b></label>
                                                                <select name="rawephemb_d" id="rawephemb_d" style="width:100%;">  
                                                                    {% if rawephemb_t == '1' %}   
                                                                    <option value="1" selected>1 sec</option>
                                                                    {% elif rawephemb_t == '5' %}
                                                                    <option value="5" selected>5 sec</option>
                                                                    {% elif rawephemb_t == '15' %}
                                                                    <option value="15" selected>15 sec</option>
                                                                    {% elif rawephemb_t == '30' %}
                                                                    <option value="30" selected>30 sec</option>
                                                                    {% endif %}                                           
                                                                    <option value="1">1 sec</option>
                                                                    <option value="5">5 sec</option>
                                                                    <option value="10">10 sec</option>
                                                                    <option value="15">15 sec</option>
                                                                    <option value="30">30 sec</option>
                                                                </select>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>



                                                <div class="col-lg-4">
                                                    <div class="row">
                                                        <div class="col-md-5">
                                                            <div class="form-group">
                                                                <label for="email"><b>glorawephemb:</b></label>
                                                                <select name="glorawephemb" id="glorawephemb" style="width:100%;">  
                                                                    {% if glorawephemb_s == '1' %}                                          
                                                                    <option value="1" selected>ON</option>
                                                                    <option value="0">OFF</option>
                                                                    {% else %}
                                                                    <option value="1">ON</option>
                                                                    <option value="0" selected>OFF</option>
                                                                    {% endif %}
                                                                </select>
                                                            </div>												
                                                        </div> 
                                                        <div class="col-lg-5">
                                                            <div class="form-group">
                                                                <label for="email"><b>Duration:</b></label>
                                                                <select name="glorawephemb_2" id="glorawephemb_2" style="width:100%;">                                              
                                                                    {% if glorawephemb_t == '1' %}   
                                                                    <option value="1" selected>1 sec</option>
                                                                    {% elif glorawephemb_t == '5' %}
                                                                    <option value="5" selected>5 sec</option>
                                                                    {% elif glorawepheb_t == '15' %}
                                                                    <option value="15" selected>15 sec</option>
                                                                    {% elif glorawephemb_t == '30' %}
                                                                    <option value="30" selected>30 sec</option>
                                                                    {% endif %}                                           
                                                                    <option value="1">1 sec</option>
                                                                    <option value="5">5 sec</option>
                                                                    <option value="10">10 sec</option>
                                                                    <option value="15">15 sec</option>
                                                                    <option value="30">30 sec</option>
                                                                </select>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                                <div class="col-lg-4">
                                                    <div class="row">
                                                        <div class="col-md-5">
                                                            <div class="form-group">
                                                                <label for="email"><b>bd2rawephemb:</b></label>
                                                                <select name="bd2rawephemb" id="bd2rawephemb" style="width:100%;"> 
                                                                    {% if bd2rawephemb_s == '1' %}                                          
                                                                    <option value="1" selected>ON</option>
                                                                    <option value="0">OFF</option>
                                                                    {% else %}
                                                                    <option value="1">ON</option>
                                                                    <option value="0" selected>OFF</option>
                                                                    {% endif %}
                                                                </select>
                                                            </div>												
                                                        </div> 
                                                        <div class="col-lg-5">
                                                            <div class="form-group">
                                                                <label for="email"><b>Duration:</b></label>
                                                                <select name="bd2rawephemb_d" id="bd2rawephemb_d" style="width:100%;">                                              
                                                                    {% if bd2rawephemb_t == '1' %}   
                                                                    <option value="1" selected>1 sec</option>
                                                                    {% elif bd2rawephemb_t == '5' %}
                                                                    <option value="5" selected>5 sec</option>
                                                                    {% elif bd2rawephemb_t == '15' %}
                                                                    <option value="15" selected>15 sec</option>
                                                                    {% elif bd2rawephemb_t == '30' %}
                                                                    <option value="30" selected>30 sec</option>
                                                                    {% endif %}                                           
                                                                    <option value="1">1 sec</option>
                                                                    <option value="5">5 sec</option>
                                                                    <option value="10">10 sec</option>
                                                                    <option value="15">15 sec</option>
                                                                    <option value="30">30 sec</option>
                                                                </select>
                                                            </div>
                                                        </div> 
                                                    </div>
                                                </div>									
                                            </div> 

                                            <div class="row" style="margin-top:15px">
                                                <div class="col-lg-4">
                                                    <div class="row">
                                                        <div class="col-md-5">
                                                            <div class="form-group">
                                                                <label for="email"><b>bd3ephemb:</b></label>
                                                                <select name="bd3ephemb" id="bd3ephemb" style="width:100%;">  
                                                                    {% if bd3ephemb_s == '1' %}                                          
                                                                    <option value="1" selected>ON</option>
                                                                    <option value="0">OFF</option>
                                                                    {% else %}
                                                                    <option value="1">ON</option>
                                                                    <option value="0" selected>OFF</option>
                                                                    {% endif %}
                                                                </select>
                                                            </div>												
                                                        </div> 
                                                        <div class="col-lg-5">
                                                            <div class="form-group">
                                                                <label for="email"><b>Duration:</b></label>
                                                                <select name="bd3ephemb_d" id="bd3ephemb_d" style="width:100%;">                                              
                                                                    {% if bd3ephemb_t == '1' %}   
                                                                    <option value="1" selected>1 sec</option>
                                                                    {% elif bd3ephemb_t == '5' %}
                                                                    <option value="5" selected>5 sec</option>
                                                                    {% elif bd3ephemb_t == '15' %}
                                                                    <option value="15" selected>15 sec</option>
                                                                    {% elif bd3ephemb_t == '30' %}
                                                                    <option value="30" selected>30 sec</option>
                                                                    {% endif %}                                           
                                                                    <option value="1">1 sec</option>
                                                                    <option value="5">5 sec</option>
                                                                    <option value="10">10 sec</option>
                                                                    <option value="15">15 sec</option>
                                                                    <option value="30">30 sec</option>
                                                                </select>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>



                                                <div class="col-lg-4">
                                                    <div class="row">
                                                        <div class="col-md-5">
                                                            <div class="form-group">
                                                                <label for="email"><b>galephemerisb:</b></label>
                                                                <select name="galephemerisb" id="galephemerisb" style="width:100%;">  
                                                                    {% if galephemerisb_s == '1' %}                                          
                                                                    <option value="1" selected>ON</option>
                                                                    <option value="0">OFF</option>
                                                                    {% else %}
                                                                    <option value="1">ON</option>
                                                                    <option value="0" selected>OFF</option>
                                                                    {% endif %}
                                                                </select>
                                                            </div>												
                                                        </div> 
                                                        <div class="col-lg-5">
                                                            <div class="form-group">
                                                                <label for="email"><b>Duration:</b></label>
                                                                <select name="galephemerisb_d" id="galephemerisb_d" style="width:100%;">                                              
                                                                    {% if galephemerisb_t == '1' %}   
                                                                    <option value="1" selected>1 sec</option>
                                                                    {% elif galephemerisb_t == '5' %}
                                                                    <option value="5" selected>5 sec</option>
                                                                    {% elif galephemerisb_t == '15' %}
                                                                    <option value="15" selected>15 sec</option>
                                                                    {% elif galephemerisb_t == '30' %}
                                                                    <option value="30" selected>30 sec</option>
                                                                    {% endif %}                                           
                                                                    <option value="1">1 sec</option>
                                                                    <option value="5">5 sec</option>
                                                                    <option value="10">10 sec</option>
                                                                    <option value="15">15 sec</option>
                                                                    <option value="30">30 sec</option>
                                                                </select>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                                <div class="col-lg-4">
                                                    <div class="row">
                                                        <div class="col-md-5">
                                                            <div class="form-group">
                                                                <label for="email"><b>rangecmpb:</b></label>
                                                                <select name="rangecmpb" id="rangecmpb" style="width:100%;"> 
                                                                    {% if rangecmpb_s == '1' %}                                          
                                                                    <option value="1" selected>ON</option>
                                                                    <option value="0">OFF</option>
                                                                    {% else %}
                                                                    <option value="1">ON</option>
                                                                    <option value="0" selected>OFF</option>
                                                                    {% endif %}
                                                                </select>
                                                            </div>												
                                                        </div> 
                                                        <div class="col-lg-5">
                                                            <div class="form-group">
                                                                <label for="email"><b>Duration:</b></label>
                                                                <select name="rangecmpb_d" id="rangecmpb_d" style="width:100%;">                                              
                                                                    {% if rangecmpb_t == '1' %}   
                                                                    <option value="1" selected>1 sec</option>
                                                                    {% elif rangecmpb_t == '5' %}
                                                                    <option value="5" selected>5 sec</option>
                                                                    {% elif rangecmpb_t == '15' %}
                                                                    <option value="15" selected>15 sec</option>
                                                                    {% elif rangecmpb_t == '30' %}
                                                                    <option value="30" selected>30 sec</option>
                                                                    {% endif %}                                           
                                                                    <option value="1">1 sec</option>
                                                                    <option value="5">5 sec</option>
                                                                    <option value="10">10 sec</option>
                                                                    <option value="15">15 sec</option>
                                                                    <option value="30">30 sec</option>
                                                                </select>
                                                            </div>
                                                        </div> 
                                                    </div>
                                                </div>									
                                            </div>

                                        </div>

                                        <!-- updated -->

                                        <div class="row" style="margin-top:15px">
                                            <div class="col-lg-2"></div>
                                            <div class="col-lg-1">
                                                <button class="btn btn-success" onclick="enterBtn()">Enter</button>
                                            </div>
                                            <div class="col-lg-1">
                                                <button class="btn btn-danger" type="button" name="submit_cancel" id="submit_cancel">Cancel</button>
                                            </div>

                                            <!--<div class="col-lg-1">
                                                <a href="{% url 'summary' %}" class="btn btn-info">Back</a>
                                            </div>-->

                                        </div>
                                        <input type="hidden" name="tcp_ip_no" id="tcp_ip_no" value={{result}}>


                                    </div>
                                </div>
                            </div>


                        </div>
                </main>
            </div>
        </div>
        <script src="http://ajax.googleapis.com/ajax/libs/jquery/1.7.1/jquery.min.js" type="text/javascript"></script>
        <script>
            $(function () {
                var data_flow_val = $('#data_flow').val();
                if (data_flow_val == 'correction') {
                    $('#rtcm').show();
                }

                var rtcm = $('#rtcm').val();
                if (rtcm == 'rtcm3.0') {
                    $('#rtcm3_2').hide();
                    $('#rtcm3_0').show();
                } else {
                    $('#rtcm3_2').show();
                    $('#rtcm3_0').hide();
                }

                $('#data_flow').change(function () {
                    var val = this.value;
                    if (val == 'raw_data') {
                        $('#rtcm').hide();
                        $('#rtcm3_2').hide();
                        $('#rtcm3_0').hide();
                        $('#raw_data').show();
                    } else {
                        $('#rtcm').show();
                        $('#raw_data').hide();
                    }

                });


                $("#rtcm").change(function () {
                    var val = this.value;
                    //alert(val);
                    if (val == 'rtcm3.0') {
                        $('#rtcm3_2').hide();
                        $('#rtcm3_0').show();
                    } else {
                        $('#rtcm3_2').show();
                        $('#rtcm3_0').hide();
                    }
                    var firstDropVal = $('#pick').val();
                });

            });


            function enterBtn() {
                var username = 'Vikranth';
                var command = 'data_config';
                var data_type = $('#data_flow').val(), corr_type = $('#rtcm').val();

                var r1005b_1 = $('#r1005b_1').val(), r1007b_1 = $('#r1007b_1').val(), r1033b_1 = $('#r1033b_1').val(), r1074b_1 = $('#r1074b_1').val();
                var r1084b_1 = $('#r1084b_1').val(), r1124b_1 = $('#r1124b_1').val(), r1006b_1 = $('#r1006b_1').val(), r1019b_1 = $('#r1019b_1').val(), r1020b_1 = $('#r1020b_1').val();

                var r1005b_2 = $('#r1005b_2').val(), r1007b_2 = $('#r1007b_2').val(), r1033b_2 = $('#r1033b_2').val(), r1074b_2 = $('#r1074b_2').val();
                var r1084b_2 = $('#r1084b_2').val(), r1124b_2 = $('#r1124b_2').val(), r1006b_2 = $('#r1006b_2').val(), r1019b_2 = $('#r1019b_2').val(), r1020b_2 = $('#r1020b_2').val();

                var rawephemb_2 = $('#rawephemb_2')
                , var glorawephemb_2 = $('#glorawephemb_2'), var bd2rawephemb_2 = $('#bd2rawephemb_2');
                var bd3ephemb_2 = $('#bd3ephemb_2')
                        , var galephemerisb_2 = $('#galephemerisb_2'), var rangecmpb_2 = $('#rangecmpb_2');

                var rawephemb_d = $('#rawephemb_d'), var glorawephemb_d = $('#glorawephemb_d'), var bd2rawephemb_d = $('#bd2rawephemb_d');
                        var var bd3ephemb_d = $('#bd3ephemb_d'), var galephemerisb_d = $('#galephemerisb_d'), var rangecmpb_d = $('#rangecmpb_d');


                //alert('r1020b_0---'+r1020b_0);

                if (data_type == 'raw_data') {

                    //alert(username);
                    $.ajax({
                        type: 'GET',
                        url: "{% url 'data_config' %}",
                        dataType: 'json',
                        data: {
                            'command': command,
                            'data_type': data_type,
                            'corr_type': corr_type,
                            'rawephemb_2': rawephemb_2,
                            'glorawephemb_2': glorawephemb_2,
                            'bd2rawephemb_2': bd2rawephemb_2,
                            'bd3ephemb_2': bd3ephemb_2,
                            'galephemerisb_2': galephemerisb_2,
                            'rangecmpb_2': rangecmpb_2,
                            'rawephemb_d': rawephemb_d,
                            'glorawephemb_d': glorawephemb_d,
                            'bd2rawephemb_d': bd2rawephemb_d,
                            'bd3ephemb_d': bd3ephemb_d,
                            'galephemerisb_d': galephemerisb_d,
                            'rangecmpb_d': rangecmpb_d

                        },
                        success: function (data) {
                            alert("Success");
                            location.reload();
                        },
                        dataType: 'html'
                    });

                } else {
                    $.ajax({
                        type: 'GET',
                        url: "{% url 'data_config' %}",
                        dataType: 'json',
                        data: {
                            'command': command,
                            'data_type': data_type,
                            'corr_type': corr_type,
                            'r1005b_2': r1005b_2,
                            'r1007b_2': r1007b_2,
                            'r1033b_2': r1033b_2,
                            'r1074b_2': r1074b_2,
                            'r1084b_2': r1084b_2,
                            'r1124b_2': r1124b_2,
                            'r1006b_2': r1006b_2,
                            'r1019b_2': r1019b_2,
                            'r1020b_2': r1020b_2,
                            'r1005b_1': r1005b_1,
                            'r1007b_1': r1007b_1,
                            'r1033b_1': r1033b_1,
                            'r1074b_1': r1074b_1,
                            'r1084b_1': r1084b_1,
                            'r1124b_1': r1124b_1,
                            'r1006b_1': r1006b_1,
                            'r1019b_1': r1019b_1,
                            'r1020b_1': r1020b_1

                        },
                        success: function (data) {
                            alert("Success");
                            location.reload();
                        },
                        dataType: 'html'
                    });
                }
            }
        </script>        
    </body>
</html>
