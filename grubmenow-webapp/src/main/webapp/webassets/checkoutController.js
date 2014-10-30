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
    
    $scope.initializeFB = function() {
    	// Load the SDK asynchronously
    	(function(d, s, id) {
    		var js, fjs = d.getElementsByTagName(s)[0];
    		if (d.getElementById(id)) return;
    		js = d.createElement(s); js.id = id;
    		js.src = "//connect.facebook.net/en_US/sdk.js";
    		fjs.parentNode.insertBefore(js, fjs);
    	}(document, 'script', 'facebook-jssdk'));
	  
        window.fbAsyncInit = function() {
            FB.init({
//                appId      : '85199433896',
                appId      : '591805167609392', 
                cookie     : true,  // enable cookies to allow the server to access the session
                xfbml      : true,  // parse social plugins on this page
                version    : 'v2.1' // use version 2.1
            });
          
            function getName(response) {
            	$scope.$apply(function() {
            		$scope.FB.accessToken = response.authResponse.accessToken;
            	});            	
            	FB.api('/me', function(response) {
                    if (response.name) {
                    	$scope.$apply(function() {
                    		$scope.FB.name = response.name;
                    	});                        
                    }
                });
            }	
            
            FB.getLoginStatus(function(response) {
            	if(response.status != "connected") {
            		$scope.$apply(function() {
                		$scope.FB.notRecognized = 1;
                	});
            		return;
            	}
            	getName(response);
            });
            
            FB.Event.subscribe('auth.authResponseChange', function(response) {
            	if(response.status == "connected") {
            		$scope.FB.notRecognized = 0;
            		getName(response);
            	}
            });
        };
    }
    
    
    $scope.makePayment = function() {
    	var $form = $('#payment-form');
    	$scope.stripe.disableSubmit = 1;
    	Stripe.card.createToken($form, $scope.stripeResponseHandler);
    }
    
    $scope.stripeResponseHandler = function(status, response) {
    	$scope.$apply(function() {
    		$scope.stripe.disableSubmit = 0;
    		var $form = $('#payment-form');

    		if (response.error) {
    			$scope.stripe.error = response.error;
    		} else {
    			// token contains id, last4, and card type
    			$scope.stripe.token = response.id;
    			$scope.stripe.error = {};
    		}
    	});
    }
    
    $scope.FB = {};
    $scope.stripe = {};
    $scope.initializeFB();
    Stripe.setPublishableKey('pk_test_CJPjqWuObYi705eii41Faeq7');
    $scope.getFinalOrder();
});
