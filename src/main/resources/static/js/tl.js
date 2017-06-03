{
    'use strict';

    var API_URL = '/api/tl';
    var SOCKET_API = '/tl-state';
    var pushRedLightButton;
    var switchServiceMode;
    var setTrafficLightColor;
    var setButtonsState;

    var sock = new SockJS(SOCKET_API);
    var stompClient = Stomp.over(sock);


    /**
     * Set current color of Traffic light
     * @param color - current color
     */
    setTrafficLightColor = function (color) {
        color = color.toLowerCase();
        $('.tl-body div').removeClass('active');
        $('.tl-light.' + color).addClass('active');
    }
    
    
    setButtonsState = function (redLightButtonState, serviceModeButtonState) {
        if (redLightButtonState) {
            $(".btn.redLightButton").addClass("active");
        } else {
            $(".btn.redLightButton").removeClass("active");
        }

        if (serviceModeButtonState) {
            $(".btn.serviceModButton").addClass("active");
        } else {
            $(".btn.serviceModButton").removeClass("active");
        }
    }

    /**
     * Sent message to API that user pushed button for RED light
     */
    pushRedLightButton = function () {
        $.post(API_URL + '/button', function () {
            console.log("red light button pushed");
        })
    };

    /**
     * Sent message to API that user pushed button switching "Service" mode
     */
    switchServiceMode = function () {
        $.post(API_URL + '/on-service', function () {
            console.log("service button pushed");
        })
    };

    /**
     * Connect to websocket and subscribe for messages from server
     */
    stompClient.connect({}, function() {
        stompClient.subscribe(SOCKET_API, function(message){
            var state = JSON.parse(message.body);
            setTrafficLightColor(state.currentLight);
            setButtonsState(state.buttonState, state.onServiceMode);
        });
    });


    $('.redLightButton').click(function () {
        pushRedLightButton();
    });

    $('.serviceModButton').click(function () {
        switchServiceMode();
    });
}