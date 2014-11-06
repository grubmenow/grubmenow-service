angular.module('gmnControllers').controller('CheckoutCtrl', function ($scope, $http, $location) {
    
    $scope.placeOrder = function(token) {
        fbHelper.getFBTokenAndName(function(token, name){
    		if(!token) {
    			return;
    		}
    		var orderObject = {};
        	orderObject.orderAmount = {};
        	orderObject.orderAmount.currency = "USD";
            orderObject.orderAmount.value = $scope.finalOrder.totalPrice;
        	orderObject.providerId = $scope.finalOrder.providerId;
        	orderObject.websiteAuthenticationToken = $scope.FB.accessToken;
        	orderObject.onlinePaymentToken = token;
        	orderObject.deliveryMethod = "CUSTOMER_PICKUP";
        	orderObject.paymentMethod = token ? "ONLINE_PAYMENT" : "CASH_ON_DELIVERY"
        	orderObject.orderItems = $scope.finalOrder.orderItems;
        	var orderUrl = "api/placeOrder";
        	$http.post(orderUrl, JSON.stringify(orderObject)).success(function(data) {
                console.log("Order Placed successfully");
            });
    	});
    }
    
    $scope.getFinalOrder = function() {
    	$scope.finalOrder = JSON.parse(localStorage.getItem('gmn.finalOrder'));
	}
    
    $scope.handleFBResponse = function(token, name) {
        $scope.safeApply(function() {
            if(!token) {
                $scope.FB.notRecognized = 1;
                $scope.FB.name = null;
            } else {
                $scope.FB.accessToken = token;
                $scope.FB.notRecognized = 0;
                $scope.FB.name = name;
            }
        });
    }
    
    $scope.safeApply = function(fn) {
    	var phase = this.$root.$$phase;
    	if(phase == '$apply' || phase == '$digest') {
    		if(fn && (typeof(fn) === 'function')) {
    			fn();
    		}
    	} else {
    		this.$apply(fn);
    	}
    };
    	
    $scope.initializeFB = function() {
        fbHelper.initialize(function(initialized){
            fbHelper.getFBTokenAndName(function(token, name){
                $scope.handleFBResponse(token, name);
            });
            
            fbHelper.addAuthChangeSubscription(function(token, name){
                $scope.handleFBResponse(token, name);
            }); 
        });
    }
    
    
    $scope.makePayment = function() {
    	var $form = $('#payment-form');
    	$scope.stripe.disableSubmit = 1;
    	Stripe.card.createToken($form, $scope.stripeResponseHandler);
    }
    
    $scope.stripeResponseHandler = function(status, response) {
    	$scope.safeApply(function() {
    		$scope.stripe.disableSubmit = 0;
    		var $form = $('#payment-form');

    		if (response.error) {
    			$scope.stripe.error = response.error;
    		} else {
    			// token contains id, last4, and card type
    			$scope.stripe.token = response.id;
    			$scope.stripe.error = {};
    			$scope.placeOrder(response.id);
    		}
    	});
    }
    
    $scope.FB = {};
    $scope.stripe = {};
    $scope.initializeFB();
    Stripe.setPublishableKey('pk_test_CJPjqWuObYi705eii41Faeq7');
    $scope.getFinalOrder();
});
