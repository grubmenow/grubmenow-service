var gmnBrowse = angular.module('orderDetails', []);

gmnBrowse.controller('orderDetailsController', function ($scope, $http, $location) {
	
    $scope.getQSP = function() {
        var url = $location.absUrl();
        var hashes = url.slice(url.indexOf('?') + 1).split('&');
        for(var i = 0; i < hashes.length; i++)
        {
            hash = hashes[i].split('=');
            $scope[hash[0]] = hash[1];
        }
    }

    $scope.loadOrder = function() {
        $scope.searching = 1;
        
        var requestObj = new Object();
        requestObj.websiteAuthenticationToken = '10152843609422975';
        requestObj.orderId = $scope['orderId'];
        $scope.getCustomerOrderRequest = requestObj;

        
        $http.post("api/getCustomerOrder", JSON.stringify($scope.getCustomerOrderRequest)).success(function(data) {
            $scope.getCustomerOrderResponse = data;
            $scope.searching = 0;
        });
    };
    
    $scope.loadOrderFeedback = function() {
        $scope.searching = 1;
        
        var requestObj = new Object();
        requestObj.websiteAuthenticationToken = '10152843609422975';
        requestObj.orderId = $scope['orderId'];
        $scope.getCustomerOrderFeedbackRequest = requestObj;

        
        $http.post("api/getCustomerOrderFeedback", JSON.stringify($scope.getCustomerOrderFeedbackRequest)).success(function(data) {
            $scope.getCustomerOrderFeedbackResponse = data;
            $scope.searching = 0;
        });
    };

    
    $scope.submitFeedback = function(review) {
        var requestObj = new Object();
        requestObj.websiteAuthenticationToken = '10152843609422975';
        requestObj.orderId = $scope['orderId'];
        
        $scope.searching = 1;
        
        alert('Submitting..');
        
        $http.post("api/submitOrderFeedback", JSON.stringify($scope.review)).success(function(data) {
        	alert('feedback submitted');
        });
    };
    
    $scope.getQSP();
    $scope.loadOrder();
});