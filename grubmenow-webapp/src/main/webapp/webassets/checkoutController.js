angular.module('gmnControllers').controller('CheckoutCtrl', function ($scope, $http, $location) {
    
    $scope.placeOrder = function(paymentToken) {
        fbHelper.getFBTokenNameAndEmail(function(token, name, email){
    		if(!token) {
    			return;
    		}
    		var orderObject = {};
            orderObject.orderAvailabilityDay = $scope.finalOrder.availableDay;
        	orderObject.orderAmount = {};
        	orderObject.orderAmount.currency = "USD";
            orderObject.orderAmount.value = $scope.finalOrder.totalPrice;
        	orderObject.providerId = $scope.finalOrder.providerId;
        	orderObject.websiteAuthenticationToken = $scope.FB.accessToken;
        	orderObject.onlinePaymentToken = paymentToken;
        	orderObject.deliveryMethod = "CUSTOMER_PICKUP";
        	orderObject.paymentMethod = paymentToken ? "ONLINE_PAYMENT" : "CASH_ON_DELIVERY"
        	orderObject.orderItems = $scope.finalOrder.orderItems;
            orderObject.customerName = $scope.customer.customerName;
            orderObject.customerEmailId = $scope.customer.customerEmailId;
            orderObject.customerPhoneNumber = $scope.customer.customerPhoneNumber;
            var d = new Date();
            orderObject.timezoneOffsetMins = d.getTimezoneOffset();
        	var orderUrl = "api/placeOrder";
        	
        	
        	// restate the state variables
        	$scope.order.processing = 1;
        	$scope.order.error = 0;
        	
        	$http.post(orderUrl, JSON.stringify(orderObject)).success(function(data) {
        	    $scope.order.processing = 0;
        	    $scope.order.processed = 1;
        	    $scope.order.orderId = data.customerOrder.orderId;
            }).error(function(data, status, headers, config) {
            	$scope.order.error_message = "Sorry, we faced error placing your order. " + data.error_message +  ". Please try again.";
            	$scope.order.error = 1;
        	    $scope.order.processing = 0;
            });
    	});
    }
    
    $scope.getFinalOrder = function() {
    	$scope.finalOrder = JSON.parse(localStorage.getItem('gmn.finalOrder'));
	}
    
    $scope.handleFBResponse = function(token, name, email) {
        $scope.safeApply(function() {
            $scope.fbLoaded = 1;
            $scope.payment = {};
            
            // check for default payment method
            if($scope.finalOrder.provider.onlinePaymentAccepted) {
            	$scope.payment.mode = "card";
            } else if($scope.finalOrder.provider.cashOnDeliverPaymentAccepted) {
            	$scope.payment.mode = "cash";
            }
            
            if(!token) {
                $scope.FB.notRecognized = 1;
                $scope.customer.customerLoaded = 0;
                $scope.FB.name = null;
                $scope.FB.email = null;
                $scope.customer.customerName = null;
                $scope.customer.customerEmailId = null;
                $scope.customer.customerPhoneNumber = null;
            } else {
                $scope.FB.accessToken = token;
                $scope.FB.notRecognized = 0;
                $scope.FB.name = name;
                $scope.FB.email = email;
                $scope.populateCustomerAccountDetails();
            }
        });
    }
    

    $scope.populateCustomerAccountDetails = function() {
        var customerAccountDetailsAPIUrl = "api/getCustomerAccountDetails";
        var customerAccountDetailsReq = {};
        customerAccountDetailsReq.websiteAuthenticationToken = $scope.FB.accessToken;
        $http.post(customerAccountDetailsAPIUrl, JSON .stringify(customerAccountDetailsReq))
        .success(
                function(data) {
                    $scope.safeApply(function() {
                        var fullName = data.firstName;
                        if (data.lastName != null) {
                            fullName = fullName
                            + " "
                            + data.lastName;
                        }
                        $scope.customer.customerName = fullName;
                        $scope.customer.customerEmailId = data.emailId;
                        $scope.customer.customerPhoneNumber = data.phoneNumber;
                        $scope.customer.customerLoaded = 1;
                    });
                })
        .error(
                function(data) {
                    $scope.safeApply(function() {
                        // error from service, means the customer does not exist.
                        // Default with what fb returned
                        $scope.customer.customerName = $scope.FB.name;
                        $scope.customer.customerEmailId = $scope.FB.email;
                        $scope.customer.customerPhoneNumber = null;
                        $scope.customer.customerLoaded = 1;
                    });
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
            fbHelper.getFBTokenNameAndEmail(function(token, name, email){
                $scope.handleFBResponse(token, name, email);
            });
            
            fbHelper.addAuthChangeSubscription(function(token, name, email){
                $scope.handleFBResponse(token, name, email);
            }); 
        });
    }
    
    
    $scope.makePayment = function() {
    	var $form = $('#payment-form');
    	$scope.stripe.disableSubmit = 1;
    	$scope.order.processing = 1;
    	Stripe.card.createToken($form, $scope.stripeResponseHandler);
    }
    
    $scope.stripeResponseHandler = function(status, response) {
    	$scope.safeApply(function() {
    		$scope.stripe.disableSubmit = 0;
    		$scope.order.processing = 0;
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
    $scope.customer = {customerLoaded: 0, customerName: null, customerEmailId: null, customerPhoneNumber: null};
    $scope.stripe = {};
    $scope.order = {};
    $scope.initializeFB();
    Stripe.setPublishableKey('pk_test_CJPjqWuObYi705eii41Faeq7');
    $scope.getFinalOrder();
    $('#inputPayment').change(function(){
        $('html, body').animate({
            scrollTop: $("#inputPayment").offset().top
        }, 500);
    });
});

