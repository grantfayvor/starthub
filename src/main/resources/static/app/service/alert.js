app.factory('AlertService', function () {

    var alert = function (message, autoClose) {
        $.niftyNoty({
            type: 'primary', //primary
            container: 'floating', //floating
            html: '<strong>' + message + '</strong>', //'<strong>Well done!</strong> You successfully read this important alert message.'
            closeBtn: 'true', //true
            floating: {
                position: "top-right",
                animationIn: 'bounceInDown', //bounceInDown
                animationOut: 'fadeOut' //fadeOut
            },
            focus: true,
            timer: autoClose ? 2500 : 0 // true ? 2500 : 0
        });
    };

    return {
        alertify : alert
    };
});