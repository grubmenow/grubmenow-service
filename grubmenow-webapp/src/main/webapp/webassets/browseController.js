var gmnBrowse = angular.module('gmnBrowse', []);

gmnBrowse.controller('ZipcodeCtrl', function ($scope, $http) {
    $scope.location = {radius:5, searching:0, today:true};

    $scope.update = function(location) {
        $scope.master = angular.copy(location);
        $scope.master.searching = 1;
        var result = decodeURIComponent($.param($scope.master));
        var ordersUrl = "orders.json?"+result;
        $http.get(ordersUrl).success(function(data) {
            $scope.master.food = data;
        });
    };
});


gmnBrowse.controller('RestuarantCtrl', function ($scope, $http, $location) {
    $scope.getId = function() {
        var url = $location.absUrl();
        var hashes = url.slice(url.indexOf('?') + 1).split('&');
        var qsp = {};
        for(var i = 0; i < hashes.length; i++)
        {
            hash = hashes[i].split('=');
            qsp[hash[0]] = hash[1];
        }
        return qsp.id;
    }

    $scope.getTotalItems = function(index) {
        var total = isNaN(parseInt($scope.restList[index].primaryFood.qty)) ? 0 : parseInt($scope.restList[index].primaryFood.qty);
        var restId = $scope.restList[index].restId;
        if (!$scope.restMenu[restId]) return total;
        
        for(var i = 0; i < $scope.restMenu[restId].length; i++) {
            var product = $scope.restMenu[restId][i];
            total = product.qty ? isNaN(parseInt(product.qty)) ? total : total + parseInt(product.qty) : total;
        }
        
        return total;
    }
    
    $scope.getTotalPrice = function(index) {
        var total = $scope.restList[index].primaryFood.qty * $scope.restList[index].primaryFood.price;
        total = isNaN(total) ? 0 : total;
        var restId = $scope.restList[index].restId;
        if (!$scope.restMenu[restId]) return total;
        
        for(var i = 0; i < $scope.restMenu[restId].length; i++) {
            var product = $scope.restMenu[restId][i];
            total = product.qty ? isNaN(parseInt(product.qty)) ? total : total + (product.price * parseInt(product.qty)) : total;            
        }
        
        return total;
    }
    
    $scope.getFinalOrder = function(index) {
        var order = {};
        order.items = [];
        order.totalPrice = $scope.getTotalPrice(index);
        var i = 0;
        if($scope.restList[index].primaryFood.qty > 0) {
            order.items[i++] = {
                "qty": $scope.restList[index].primaryFood.qty,
                "name": $scope.restList[index].primaryFood.name,
                "totalPrice": $scope.restList[index].primaryFood.qty * $scope.restList[index].primaryFood.price
            };
        }
        
        var restId = $scope.restList[index].restId;
        if (!$scope.restMenu[restId]) {
            $scope.finalOrder = order;
            return;
        }
        
        for(var j = 0; j < $scope.restMenu[restId].length; j++) {
            var product = $scope.restMenu[restId][j];
            if (!isNaN(parseInt(product.qty))) {
                order.items[i++] = {
                    "qty": product.qty,
                    "name": product.name,
                    "totalItemPrice": product.qty * product.price
                };    
            }
        }
        $scope.finalOrder = order;
    }
    
    $scope.showMenu = function(restId) {
        if ($scope.restMenu[restId]) {
            $scope.showRestMenu[restId] = 1;
        }
        var restMenuUrl = "restMenu.json?restId="+restId;
        $http.get(restMenuUrl).success(function(data) {
            $scope.restMenu[restId] = data;
            $scope.showRestMenu[restId] = 1;
        });
    }
    
    var foodId = $scope.getId();
    var restListUrl = "restList.json?foodId="+foodId;
    $http.get(restListUrl).success(function(data) {
        $scope.restList = data;
    });
    $scope.restMenu = {};
    $scope.showRestMenu = {};
});

jQuery(function($){
    
});