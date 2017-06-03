{
    'use strict';

    var API_URL = '/api/tl';
    var getCurrentColor;
    var pushRedLightButton;
    var switchServiceMode;


    getCurrentColor = function () {
        $.get(API_URL + '/light', function (data, status) {
            if (status === 'success') {
                $('.tl-body div').removeClass('active');
                $('.tl-light.' + data.toLowerCase()).addClass('active');
            }
        })
    };
    
    pushRedLightButton = function () {
        $.post(API_URL + '/button', function () {
            console.log("red light button pushed");
        })
    };

    switchServiceMode = function () {
        $.post(API_URL + '/on-service', function () {
            console.log("service button pushed");
        })
    };


    $('.redLightButton').click(function () {
        pushRedLightButton();
    });

    $('.serviceModButton').click(function () {
        switchServiceMode();
    });

    setInterval(function () {
        getCurrentColor();
    }, 1000);
}