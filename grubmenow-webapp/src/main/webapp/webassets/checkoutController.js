angular.module('gmnBrowse').controller('CheckoutCtrl', function ($scope, $http, $location) {
    
    $scope.placeOrder = function() {
    	var orderObject = {};
    	orderObject.orderAmount = {};
    	orderObject.orderAmount.currency = "USD";
    	orderObject.orderAmount.value = Math.round($scope.finalOrder.totalPrice*100)/100;
    	orderObject.providerId = $scope.finalOrder.providerId;
    	orderObject.websiteAuthenticationToken = $scope.FB.accessToken;
    	orderObject.deliveryMethod = "CUSTOMER_PICKUP";
    	orderObject.paymentMethod = "CASH_ON_DELIVERY"
    	orderObject.orderItems = $scope.finalOrder.orderItems;
    	var orderUrl = "api/placeOrder";
    	$http.post(orderUrl, JSON.stringify(orderObject)).success(function(data) {
            console.log("Order Placed successfully");
        });
    }
    
    $scope.getFinalOrder = function() {
	$scope.finalOrder = JSON.parse(localStorage.getItem('gmn.finalOrder'));
	
    }
    
    $scope.initialize = function() {
    	window.fbAsyncInit = function() {
            FB.init({
                appId      : '107439809640',
                cookie     : true,  // enable cookies to allow the server to access the session
                xfbml      : true,  // parse social plugins on this page
                version    : 'v2.1' // use version 2.1
            });
          
            FB.getLoginStatus(function(response) {
            	if(response.status != "connected") {
            		return;
            	}
            	$scope.FB.accessToken = response.authResponse.accessToken;
                FB.api('/me', function(response) {
                    if (response.name) {
                        fbLoginName = response.name;
                        $scope.FB.name = response.name;
                    }
                });
            });
        };
    }
    
    $scope.getFinalOrder();
});