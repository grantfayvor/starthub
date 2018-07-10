app.factory('socketProvider', function () {

    var socket = new SockJS('/starthub-ws');

    var provider = {
        getSocket: function () {
            return socket;
        },
        getStompClient: function () {
            return Stomp.over(socket);
        },
        sendMessage: function (recipient, data, options) {
            this.getStompClient().send(recipient, options, data);
        },
        executeRequest: function (subscriber, onMessageReceived, recipient, data, options) {
            if (Array.prototype.slice.call(arguments).length > 2) {
                options = recipient;
                data = onMessageReceived;
                recipient = subscriber;
                subscriber = null;
                onMessageReceived = null;
                this.connectToSocket(function (frame) {
                    console.log('Connected: ' + frame);
                    provider.getStompClient().subscribe(subscriber, onMessageReceived);
                    provider.getStompClient().send(recipient, options, data);
                });
            } else {
                this.subscribe(subscriber, onMessageReceived);
            }
        },
        subscribe: function (subscriber, options, onMessageReceived) {
            if (Array.prototype.slice.call(arguments) == 2) {
                onMessageReceived = options;
                options = {};
            }
            this.connectToSocket(function (frame) {
                console.log('Connected: ' + frame);
                provider.getStompClient().subscribe(subscriber, onMessageReceived, options);
            });
        },
        unsubscribe: function(id, options) {
            this.getStompClient().unsubscribe(id, options);
        },
        connectToSocket: function (callback) {
            this.getStompClient().connect({}, callback);
        },
        disconnectSocket: function () {
            this.getStompClient().disconnect();
            socket = new SockJS('/starthub-ws');
        }
    };
    return provider;
});