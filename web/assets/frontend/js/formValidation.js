// for FAQ'S Page
$(document).ready(function () {
    $('#questionForm').validate({ 
        rules: {
            name: {
                required: true
            },
            email: {
                required: true,
                email: true
            },
            mobile: {
                required: true,
                minlength: 10,
                maxlength: 10,
                number : true 
            },
            message: {
                required: true
            }
        }
    });

});


// for contact Page
$(document).ready(function () {
    $('#contactForm').validate({ 
        rules: {
            name: {
                required: true
            },
            email: {
                required: true,
                email: true
            },
            mobile: {
                required: true,
                minlength: 10,
                maxlength: 10,
                number : true 
            },
            city: {
                required: true
            },
            state: {
                required: true
            },
            country: {
                required: true
            },
            message: {
                required: true
            }
        }
    });
});


// homeQueryForm
$(document).ready(function () {
    $('#homeQueryForm').validate({ 
        rules: {
            name: {
                required: true
            },
            email: {
                required: true,
                email: true
            },
            mobile: {
                required: true,
                minlength: 10,
                maxlength: 10,
                number : true 
            },
            message: {
                required: true
            }
        }
    });

});


// for contact Page
$(document).ready(function () {
    $('#applyForm').validate({ 
        rules: {
            name: {
                required: true
            },
            email: {
                required: true,
                email: true
            },
            mobile: {
                required: true,
                minlength: 10,
                maxlength: 10,
                number : true 
            },
            resume: {
                required: true
            }
            
        }
    });
});
