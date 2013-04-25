///////////////////////////:
// NOTIFICATION SERVICE
///////////////////////////:


angular.module(webmarketResourcesModuleName).
    factory('Notification', function () {

        var stack = {"dir1": "down", "dir2": "left", "push": "top"};
        var delay = 2000;

        return {
            info: function (title, text) {
                $.pnotify({
                    title: title,
                    text: text,
                    type: 'info',
                    delay: delay,
                    stack: stack
                });
            },
            error: function (title, text) {
                $.pnotify({
                    title: title,
                    text: text,
                    type: 'error',
                    delay: delay,
                    stack: stack
                });
            }
        }
    });