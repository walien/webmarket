'use strict';

angular.module(webmarketUIModuleName)
    .controller('ItemEditorCtrl', function ($scope, $routeParams, $location, Item, Notification) {

        /////////////////
        // SCOPE INIT
        /////////////////

        $scope.item = {};

        //////////////////////////////
        // RETRIEVE ITEMS FROM SERVER
        //////////////////////////////

        var item = Item.get({id: $routeParams.id}, function () {
            $scope.item = item;
        });

        /////////////////////
        // USER ACTIONS
        /////////////////////

        /**
         * Manage file choosing
         * @param evt
         */
        $scope.chooseFile = function (evt) {

            var files = evt.target.files;
            for (var i = 0, f; f = files[i]; i++) {

                // Check the type of the selected file
                if (!f.type.match('image.*')) {
                    continue;
                }

                // Load the content of the image
                var reader = new FileReader();

                // Called when the data is loaded
                reader.onload = function (e) {
                    $scope.item.base64Image = e.target.result;
                    $scope.$apply();
                }
                reader.readAsDataURL(f)
            }
        }

        document.getElementById('itemImage').addEventListener('change', $scope.chooseFile, false);

        /**
         * Save item changes
         */
        $scope.saveItem = function () {
            var response = Item.save({id: $scope.item.id}, $scope.item, function () {
                if (response.status) {
                    Notification.info("Success", "Item data were changed successfuly !");
                    $scope.redirect('/admin/items');
                } else {
                    Notification.error("Error", "Error during item data changes...");
                }
            });
        };
    });