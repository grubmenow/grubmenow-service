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
        $scope.showOrder = 0;
        
        $('.loadMessage').html("Please wait.. Loading Order");
        
        var requestObj = new Object();
        requestObj.websiteAuthenticationToken = '10152843609422975';
        requestObj.orderId = $scope['orderId'];
        $scope.getCustomerOrderRequest = requestObj;

        
        $http.post("api/getCustomerOrder", JSON.stringify($scope.getCustomerOrderRequest)).success(function(data) {
        	$('.loadMessage').hide();
            $scope.getCustomerOrderResponse = data;
            $scope.showOrder = 1;
        });
    };
    
    $scope.loadOrderFeedback = function() {
        $scope.showReview = 0;
        
        var requestObj = new Object();
        requestObj.websiteAuthenticationToken = '10152843609422975';
        requestObj.orderId = $scope['orderId'];
        $scope.getCustomerOrderFeedbackRequest = requestObj;

        
        $http.post("api/getCustomerOrderFeedback", JSON.stringify($scope.getCustomerOrderFeedbackRequest)).success(function(data) {
        	$scope.review = new Object() 
            $scope.review.feedback = data.feedback;
            $scope.review.rating = data.rating;
            
            $scope.showReview = 1;
        });
    };

    
    $scope.submitFeedback = function(review) {
        var requestObj = new Object();
        requestObj.websiteAuthenticationToken = '10152843609422975';
        requestObj.orderId = $scope['orderId'];
        requestObj.rating = review.rating;
        requestObj.feedback = review.feedback;
        
        $scope.searching = 1;
        
        $('.feedbackMessage').show();
        $('.feedbackMessage').html("Please wait.. Submitting feedback");
        
        $http.post("api/submitOrderFeedback", JSON.stringify(requestObj)).success(function(data) {
        	$('.feedbackMessage').html("Thanks, Feedback Submitted").fadeOut(2000);
        });
    };
    
    $scope.getQSP();
    $scope.loadOrderFeedback();
    $scope.loadOrder();
});