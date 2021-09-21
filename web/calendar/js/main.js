(function ($) {

    var url = "http://localhost:8080/APL/webAPI/aplService/getCalendarData";

    var myData = "9758128792";
    var jsonData;
    var holi_date;
    var holi_month = "0";
    var holi_day;
    var holi_year;
    var holiJSON = "";
    var occassion = "";

    $.ajax({
        type: "POST",
        url: url,
        data: myData,
        dataType: "text",
        contentType: "application/json; charset=utf-8",
        success: function (result) {
            console.log(result);
            jsonData = JSON.parse(result);
            //alert("size --"+jsonData["holiday_data"].length);
            //alert("holiday data --"+jsonData["holiday_data"]);
            holiJSON = jsonData["holiday_data"];
            //holi_date = jsonData["holiday_data"][0]["holiday_date"];            
            //alert("holiJSON length --" + holiJSON.length);

//            for (var hh = 0; hh < holiJSON.length; hh ++ ) {
//
//                holi_date = holiJSON[hh]["holiday_date"];
//
//                var split_holi_date = holi_date.split("-");
//                holi_year = split_holi_date[0];
//                holi_month = split_holi_date[1];
//                holi_day = split_holi_date[2];
//                alert("day -" + holi_day + " month -" + holi_month + " year -" + holi_year);
//            }

            //holi_month
            holi_day = "02";
            holi_month = "10";
            holi_year = "2021";

            holiJSON = jsonData["holiday_data"];
            console.log(holiJSON);
        },
        error: function (xhr, status, error) {
            alert("Result: " + status + " " + error + " " + xhr.status + " " + xhr.statusText)
        }
    });




    "use strict";

    document.addEventListener('DOMContentLoaded', function () {
        var today = new Date(),
                year = today.getFullYear(),
                month = today.getMonth(),
                monthTag = ["January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"],
                day = today.getDate(),
                days = document.getElementsByTagName('td'),
                selectedDay,
                setDate,
                daysLen = days.length;
// options should like '2014-01-01'
        function Calendar(selector, options) {
            this.options = options;
            this.draw();
        }

        Calendar.prototype.draw = function () {
            this.getCookie('selected_day');
            this.getOptions();
            this.drawDays();
            var that = this,
                    reset = document.getElementById('reset'),
                    pre = document.getElementsByClassName('pre-button'),
                    next = document.getElementsByClassName('next-button');

            pre[0].addEventListener('click', function () {
                that.preMonth();
            });
            next[0].addEventListener('click', function () {
                that.nextMonth();
            });
            reset.addEventListener('click', function () {
                that.reset();
            });
            while (daysLen--) {
                days[daysLen].addEventListener('click', function () {
                    that.clickDay(this);
                });
            }
        };

        Calendar.prototype.drawHeader = function (e, hol) {
            var headDay = document.getElementsByClassName('head-day'),
                    headMonth = document.getElementsByClassName('head-month');

            e ? headDay[0].innerHTML = e : headDay[0].innerHTML = day;
            headMonth[0].innerHTML = monthTag[month] + " - " + year;

        };

        Calendar.prototype.drawOcassion = function (e, hol, hol_name) {//alert("holi name --"+hol_name);
            var headDay = document.getElementsByClassName('head-day'),
                    headMonth = document.getElementsByClassName('head-month');

            e ? headDay[0].innerHTML = e : headDay[0].innerHTML = day;
            headMonth[0].innerHTML = monthTag[month] + " - " + year;


//            alert("val of e day ====" + e);
//            alert("val of e month ====" + month);
            var int_month = (parseInt(holi_month)) - 1;
            var int_day = (parseInt(holi_day));


//            for (var hh = 0; hh < holiJSON.length; hh++) {
//                debugger;
//                var holi_name = holiJSON[hh]["holiday_name"];                
//                holi_date = holiJSON[hh]["holiday_date"];
//                var split_holi_date = holi_date.split("-");
//                holi_year = split_holi_date[0];
//                holi_month = split_holi_date[1];
//                holi_day = split_holi_date[2];
//                //alert("day -" + holi_day + " month -" + holi_month + " year -" + holi_year);
//
//
//                var int_month = (parseInt(holi_month)) - 1;
//                //alert("int month ---- "+int_month);
//                var int_day = (parseInt(holi_day));
//                if (e == int_day && month == int_month) {//alert("holiday name -"+holi_name); 
//                    var occassion=holi_name.toString();
//                    //alert("holiday name -"+hho);                                       
//                    var headOccasion = document.getElementsByClassName('head-occasion');
//                    headOccasion[0].innerHTML = occassion;
//
//                } else {
//                    var headOccasion = document.getElementsByClassName('head-occasion');
//                    headOccasion[0].innerHTML = "";
//                }
//
//            }

//alert("hol length -"+hol.length);
            if (hol.length > 0) {
                debugger;

                if (e == int_day && month == int_month) {//alert("holi name under --"+hol_name);
                    var headOccasion = document.getElementsByClassName('head-occasion');
                    //headOccasion[0].innerHTML = "Gandhi Jayanti";
                    headOccasion[0].innerHTML = hol_name;

                } else {//alert("holi name else --"+hol_name);
                    var headOccasion = document.getElementsByClassName('head-occasion');
                    headOccasion[0].innerHTML = "";
                }
            }


//            if (hol.length > 0) {
//
//                var day_arr;
//
//                for (var h = 0; h < holiJSON.length; h++)
//                    var datee = holiJSON[h]["holiday_date"];
//                    //datee = datee.toString().substr(0, datee.indexOf(' '));
//                    datee = datee;
//                    //alert("deatee -- "+datee);
//
//
//            }




        };

        Calendar.prototype.drawDays = function () {
            //debugger;
            var startDay = new Date(year, month, 1).getDay(),
                    nDays = new Date(year, month + 1, 0).getDate(),
                    n = startDay;
            for (var k = 0; k < 42; k++) {
                days[k].innerHTML = '';
                days[k].id = '';
                days[k].className = '';
            }

            for (var i = 1; i <= nDays; i++) {//debugger;
                days[n].innerHTML = i;
                n++;
            }
            //alert("month ---"+month);
            for (var j = 0; j < 42; j++) {
                //debugger;
                if (days[j].innerHTML === "") {

                    days[j].id = "disabled";

                } else if (j === day + startDay - 1) {
                    if ((this.options && (month === setDate.getMonth()) && (year === setDate.getFullYear())) || (!this.options && (month === today.getMonth()) && (year === today.getFullYear()))) {
                        this.drawHeader(day);
                        //alert("startDay of august ---" + startDay);
                        days[j].id = "today";
                        //days[10 - 1].id = "today";
                    }

                    // Start Edit 
                    //debugger;
                    if (month == '9') {
                        //days[11 + startDay - 1].id = "holiday";
                        //days[14+startDay-1].id = "holiday";

                        //this.drawHeader(day,"holiday");
//                        var headOccasion = document.getElementsByClassName('head-occasion');
//                        headOccasion[0].innerHTML = "hello its birthday";

                    }


                    for (var hh = 0; hh < holiJSON.length; hh++) {
                        holi_date = holiJSON[hh]["holiday_date"];
                        var split_holi_date = holi_date.split("-");
                        holi_year = split_holi_date[0];
                        holi_month = split_holi_date[1];
                        holi_day = split_holi_date[2];
                        //alert("day -" + holi_day + " month -" + holi_month + " year -" + holi_year);


                        var int_month = (parseInt(holi_month)) - 1;
                        //alert("int month ---- "+int_month);
                        var int_day = (parseInt(holi_day));
                        if (month == int_month) {
                            //debugger;//alert("day-"+int_day+" month-"+month+" int month-"+int_month);
                            days[int_day + startDay - 1].id = "holiday";
                        }

                    }

//                    for (var n = 0; n < 3; n++) {
//                        var int_month = (parseInt(holi_month)) - 1;
//                        //alert("int month ---- "+int_month);
//                        var int_day = (parseInt(holi_day));
//                        if (month == int_month) {
//                            days[int_day + n + startDay - 1].id = "holiday";
//                        }
//
//                    }


                    // End Edit

                }

                if (selectedDay) {
                    if ((j === selectedDay.getDate() + startDay - 1) && (month === selectedDay.getMonth()) && (year === selectedDay.getFullYear())) {
                        days[j].className = "selected";
                        this.drawHeader(selectedDay.getDate());
                    }
                }
            }
        };

        Calendar.prototype.clickDay = function (o) {
            var selected = document.getElementsByClassName("selected"),
                    len = selected.length;
            if (len !== 0) {
                selected[0].className = "";
            }
            o.className = "selected";
            selectedDay = new Date(year, month, o.innerHTML);
//            alert("inner html ---"+o.innerHTML);
//            alert("inner year ---"+year);
//            alert("inner month ---"+month);
//            alert("selectedDay ---"+selectedDay);
            //this.drawHeader(o.innerHTML, "hol");


            for (var hh = 0; hh < holiJSON.length; hh++) {
                //debugger;
                var holi_name = holiJSON[hh]["holiday_name"];
                holi_date = holiJSON[hh]["holiday_date"];
                var split_holi_date = holi_date.split("-");
                holi_year = split_holi_date[0];
                holi_month = split_holi_date[1];
                holi_day = split_holi_date[2];
                //alert("day -" + holi_day + " month -" + holi_month + " year -" + holi_year);


                var int_month = (parseInt(holi_month)) - 1;
                //alert("int month ---- "+int_month);
                var int_day = (parseInt(holi_day));
                if (o.innerHTML == int_day && month == int_month) {//alert("holiday name -"+holi_name); 
                    occassion = holi_name.toString();
                    //alert("occasion --" + occassion);
                    this.drawOcassion(o.innerHTML, "hol", occassion);
                    hh=holiJSON.length;
                } else {
                    occassion = "";
                    this.drawOcassion(o.innerHTML, "hol", occassion);
                    
                }

            }
            
//            if(occassion==""){
//                this.drawOcassion(o.innerHTML, "hol", occassion);
//            }

//            alert("occasion --"+occassion);            
//            this.drawOcassion(o.innerHTML, "hol");
            //alert("occassion -"+occassion);
            this.setCookie('selected_day', 1);

        };

        Calendar.prototype.preMonth = function () {
            if (month < 1) {
                month = 11;
                year = year - 1;
            } else {
                month = month - 1;
            }
            this.drawHeader(1);
            this.drawDays();
        };

        Calendar.prototype.nextMonth = function () {
            if (month >= 11) {
                month = 0;
                year = year + 1;
            } else {
                month = month + 1;
            }
            this.drawHeader(1);
            this.drawDays();
        };

        Calendar.prototype.getOptions = function () {
            if (this.options) {
                var sets = this.options.split('-');
                setDate = new Date(sets[0], sets[1] - 1, sets[2]);
                day = setDate.getDate();
                year = setDate.getFullYear();
                month = setDate.getMonth();
            }
        };

        Calendar.prototype.reset = function () {
            month = today.getMonth();
            year = today.getFullYear();
            day = today.getDate();
            this.options = undefined;
            this.drawDays();
        };

        Calendar.prototype.setCookie = function (name, expiredays) {
            if (expiredays) {
                var date = new Date();
                date.setTime(date.getTime() + (expiredays * 24 * 60 * 60 * 1000));
                var expires = "; expires=" + date.toGMTString();
            } else {
                var expires = "";
            }
            document.cookie = name + "=" + selectedDay + expires + "; path=/";
        };

        Calendar.prototype.getCookie = function (name) {
            if (document.cookie.length) {
                var arrCookie = document.cookie.split(';'),
                        nameEQ = name + "=";
                for (var i = 0, cLen = arrCookie.length; i < cLen; i++) {
                    var c = arrCookie[i];
                    while (c.charAt(0) == ' ') {
                        c = c.substring(1, c.length);

                    }
                    if (c.indexOf(nameEQ) === 0) {
                        selectedDay = new Date(c.substring(nameEQ.length, c.length));
                    }
                }
            }
        };
        var calendar = new Calendar();


    }, false);

})(jQuery);
