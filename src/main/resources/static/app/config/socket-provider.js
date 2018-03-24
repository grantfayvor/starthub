app.factory('socketProvider', function() {

    var socket = new SockJS('/gs-guide-websocket');

    var provider = {
        getSocket : function() {
            return socket;
        },
        getStompClient : function () {
            return Stomp.over(socket);
        },
        sendMessage: function(recipient, data, options) {
            provider.getStompClient().send(recipient, options, JSON.stringify(data));
        },
        subscribe : function (subscriber, publisher) {
            provider.connectToSocket(function (frame) {
                console.log('Connected: ' + frame);
                provider.getStompClient().subscribe(subscriber, publisher);
            });
        },
        connectToSocket : function (callback) {
            provider.getStompClient().connect({}, callback);
        }
    };
    return provider;
});