var gmnBrowse = angular.module('gmnBrowse', []);

gmnBrowse.controller('ZipcodeCtrl', function ($scope, $http) {
    $scope.location = {radius:5, availableDay:"Today"};
    $scope.searching = 0;
    $scope.showThankYouMessage = 0;
    $scope.feedback = {generalFeedback: '', newItems: ''};
    $scope.showFeedbackForm = 0;
    $scope.showFoodItemSuggestionForm = 0;
    $( ".formElem" ).change(function (e) {
    	var id = e.target.id;
    	if(id != "inputZipcode" && $('#'+id).val() !=0) {
    		$('#'+id).removeClass('redBorder');
    	}
    });
    $('#inputZipcode').keyup(function(){
    	if($('#inputZipcode').val().length == 5 && !isNaN($('#inputZipcode').val())) {
    		$('#inputZipcode').removeClass('redBorder');
    	} else {
    		$('#inputZipcode').addClass('redBorder');
    	}
    });
    $scope.validateForm = function() {
    	$scope.badZipcode = $scope.searching = 0;
    	$scope.badRadius = $scope.badDay = 0;
        if(!$scope.location.zipCode || isNaN($scope.location.zipCode)) {
        	$scope.badZipcode = 1;
        }
        if($scope.location.radius == 0){
        	$scope.badRadius = 1;
        } 
        if($scope.location.availableDay == 0){
        	$scope.badDay = 1;
        }
    }
    
    $scope.update = function(location) {
        $scope.master = angular.copy(location);
        $scope.validateForm(); 
        if($scope.location.zipCode && $scope.location.radius != 0 && $scope.location.availableDay != 0) {
        	$scope.searching = 1;
        	$http.post("api/searchFoodItems", JSON.stringify($scope.master)).success(function(data) {
        		$scope.master.food = data;
            	$scope.searching = 0;
            	$scope.searchedOnce = 1;
        	});
        }
    };
    
    $scope.openFeedbackForm = function() {
    	$scope.showFeedbackForm = 1;
    	$scope.showFoodItemSuggestionForm = 0;
    };
    
    $scope.openSearchSuggestionForm = function() {
    	$scope.showFoodItemSuggestionForm = 1;
    	$scope.showFeedbackForm = 0;
    };
    
    $scope.submitFeedbackForm = function()
    {
    	request = {feedbackMessage: $scope.feedback.generalFeedback};
    	$http.post("api/submitGeneralFeedback", JSON.stringify(request)).success(function(data) {
    		$scope.showFoodItemSuggestionForm = 0;
        	$scope.showFeedbackForm = 0;
    		$scope.showThankYouMessage = 1;
    	})
    };
    
    $scope.submitFoodItemSuggestionForm = function()
    {
    	request = {foodItemSuggestions: $scope.feedback.newItems};
    	$http.post("api/submitFoodItemSuggestions", JSON.stringify(request)).success(function(data) {
    		$scope.showFoodItemSuggestionForm = 0;
        	$scope.showFeedbackForm = 0;
    		$scope.showThankYouMessage = 1;
    	})
    };
});


gmnBrowse.controller('RestuarantCtrl', function ($scope, $http, $location) {
    $scope.getQSP = function() {
        var url = $location.absUrl();
        var hashes = url.slice(url.indexOf('?') + 1).split('&');
        for(var i = 0; i < hashes.length; i++)
        {
            hash = hashes[i].split('=');
            $scope[hash[0]] = hash[1];
        }
    }

    $scope.getTotalItems = function(index) {
        var total = isNaN(parseInt($scope.restList.foodItem.foodItemQty)) ? 0 : parseInt($scope.restList.foodItem.foodItemQty);
        var restId = $scope.restList.providerFoodItemOffers[index].provider.providerId;
        if (!restId || !$scope.restMenu[restId]) return total;
        
        for(var i = 0; i < $scope.restMenu[restId].providerFoodItemOffers.length; i++) {
            var product = $scope.restMenu[restId].providerFoodItemOffers[i];
            total = product.foodItem.foodItemQty ? isNaN(parseInt(product.foodItem.foodItemQty)) ? total : total + parseInt(product.foodItem.foodItemQty) : total;
        }
        
        return total;
    }
    
    $scope.getTotalPrice = function(index) {
        var primaryQty = isNaN(parseInt($scope.restList.foodItem.foodItemQty)) ? 0 : parseInt($scope.restList.foodItem.foodItemQty);
        var total = primaryQty * $scope.restList.providerFoodItemOffers[index].foodItemOffer.price.value;
        total = isNaN(total) ? 0 : total;
        var restId = $scope.restList.providerFoodItemOffers[index].provider.providerId;
        if (!restId || !$scope.restMenu[restId]) return total;
        
        for(var i = 0; i < $scope.restMenu[restId].providerFoodItemOffers.length; i++) {
            var product = $scope.restMenu[restId].providerFoodItemOffers[i];
            total = product.foodItem.foodItemQty ? isNaN(parseInt(product.foodItem.foodItemQty)) ? total : total + (product.foodItemOffer.price.value * parseInt(product.foodItem.foodItemQty)) : total;            
        }
        
        return total;
    }
    

    $scope.placeOrder = function() {
		/**
		 * Create the order object to be sent to the server, and
		 * validate as much as possible without checking that
		 * the user is fb authenticated
		 */
		var orderObject = {};
		orderObject.orderAmount = {};
		orderObject.orderAmount.currency = "USD";
		orderObject.orderAmount.value = Math
				.round($scope.finalOrder.totalPrice * 100) / 100;
		orderObject.providerId = $scope.finalOrder.providerId;
		orderObject.deliveryMethod = "CUSTOMER_PICKUP";
		orderObject.paymentMethod = "CASH_ON_DELIVERY"
		orderObject.orderItems = $scope.finalOrder.orderItems;
		// Check fb authentication, and provide the callback
		// function and callback argument
		$scope.loginToFbAndInvokeCallback(1, false,
				$scope.placeOrderCallback, orderObject);
	}

	$scope.placeOrderCallback = function(isFBAuthenticated,
			username, fbAccessToken, orderObject) {
		if (isFBAuthenticated) {
			orderObject.websiteAuthenticationToken = fbAccessToken;
			var orderUrl = "api/placeOrder";
			$http
					.post(orderUrl, JSON.stringify(orderObject))
					.success(
							function(data) {
								alert("Order successful");
								console
										.log("Order Placed successfully");
							});
		} else {
			$scope.orderSummaryTitle = "Please login via Facebook to proceed";
		}

		// apply the change in angular js state. Does not happen
		// automatically since we are inside our own javascript
		// function
		// when this method is called
		$scope.safeApply(function() {
		});
	}

	$scope.loginToFbAndInvokeCallback = function(
			facebookLoginTryCount, isUsernameRequired,
			callbackFunction, callbackArgument) {
		if (!$scope.FB.init) {
			// if FB is not initialized, do not try to connect
			// to FB, rather just open the order modal without
			// login info
			console.log("FB API not initialized yet");
			$scope.loginState.loggedIn = false;
			$scope.loginState.name = null;
			callbackFunction(false, null, null,
					callbackArgument);
			return;
		}

		// first check if the user is logged in.
		FB
				.getLoginStatus(function(response) {
					console.log("facebook check login status response:" + response);
					if (response.status == 'connected') {
						console .log("facebook login: user connected");
						$scope.loginState.loggedIn = true;
						if (isUsernameRequired) {
							FB.api(
								'/me',
								function(meResponse) {
									console.log('Successful login for: ' + meResponse.name);
									$scope.loginState.name = meResponse.name;
									callbackFunction(
											true,
											meResponse.name,
											response.authResponse.accessToken,
											callbackArgument);
								});
						} else {
							// avoids unnecessary call by having the isUsernameRequired set to false
							callbackFunction(true, null, response.authResponse.accessToken, callbackArgument);
						}
					} else if (facebookLoginTryCount <= 0
							|| response.status === 'not_authorized') {
						$scope.loginState.loggedIn = false;
						$scope.loginState.name = null;
						// The person is logged into Facebook, but not your app.

						// if more facebook authentication tries available use them
						if (facebookLoginTryCount > 0)
						{
							FB.login(function(response) {
								$scope.loginToFbAndInvokeCallback(
										facebookLoginTryCount - 1,
										isUsernameRequired,
										callbackFunction,
										callbackArgument);
							},
							{
								scope : 'public_profile,email'
							});
						}
						else
						{
							console.log("User has not authorized the app");
							callbackFunction(false, null, null, callbackArgument);
						}
					} else {
						$scope.loginState.loggedIn = false;
						$scope.loginState.name = null;
						// The person is not logged into Facebook, so we're not sure if they are logged into this app or not.
						FB.login(function(response) {
							$scope.loginToFbAndInvokeCallback(
									facebookLoginTryCount - 1,
									isUsernameRequired,
									callbackFunction,
									callbackArgument);
						},
						{
							scope : 'public_profile,email'
						});
					}
				});
	};

	$scope.goToOrderSummary = function(isFBAuthenticated,
			username, fbAccessToken, providerIndex) {
		if (isFBAuthenticated) {
			$scope.orderSummaryTitle = "Welcome, " + username;
		} else {
			$scope.orderSummaryTitle = "Please login via Facebook to proceed";
		}

		// prepare the order
		$scope.prepareOrder(providerIndex);

		// apply the change in angular js state. Does not happen
		// automatically since we are inside our own javascript
		// function
		// when this method is called
		$scope.safeApply(function() {
			$('#orderModal').modal('show');
		});
	}

	$scope.getFinalOrder = function(providerIndex) {
		$scope.loginToFbAndInvokeCallback(1, true,
				$scope.goToOrderSummary, providerIndex);
	};

	$scope.prepareOrder = function(providerIndex) {
		var order = {};
		order.items = [];
		order.orderItems = [];
		order.tax = $scope.getTotalPrice(providerIndex) * 0.095;
		order.totalPrice = $scope.getTotalPrice(providerIndex)
				+ order.tax;
		order.providerId = $scope.restList.providerFoodItemOffers[providerIndex].provider.providerId;
		var i = 0;
		if ($scope.restList.foodItem.foodItemQty > 0) {
			order.items[i] = {
				"orderQty" : $scope.restList.foodItem.foodItemQty,
				"orderName" : $scope.restList.foodItem.foodItemName,
				"orderPrice" : $scope.restList.foodItem.foodItemQty
						* $scope.restList.providerFoodItemOffers[providerIndex].foodItemOffer.price.value
			};
			order.orderItems[i] = {
				"quantity" : $scope.restList.foodItem.foodItemQty,
				"foodItemOfferId" : $scope.restList.providerFoodItemOffers[providerIndex].foodItemOffer.foodItemOfferId
			};
			i++;
		}

		var restId = $scope.restList.providerFoodItemOffers[providerIndex].provider.providerId;

		if ($scope.restMenu[restId]) {
			for (var j = 0; j < $scope.restMenu[restId].providerFoodItemOffers.length; j++) {
				var product = $scope.restMenu[restId].providerFoodItemOffers[j];
				if (!isNaN(parseInt(product.foodItem.foodItemQty))) {
					order.items[i] = {
						"orderQty" : product.foodItem.foodItemQty,
						"orderName" : product.foodItem.foodItemName,
						"orderPrice" : product.foodItem.foodItemQty
								* product.foodItemOffer.price.value
					};
					order.orderItems[i] = {
						"quantity" : product.foodItem.foodItemQty,
						"foodItemOfferId" : product.foodItemOffer.foodItemOfferId
					};
					i++;
				}
			}
		}
		$scope.finalOrder = order;
	}
    
    $scope.showMenu = function(restId) {
        if ($scope.restMenu[restId]) {
            $scope.showRestMenu[restId] = 1;
        }
        var requestData = {"providerId": restId, "availableDay": $scope.availableDay};
        var restListUrl = "api/getProviderMenu";
        $http.post(restListUrl, JSON.stringify(requestData)).success(function(data) {
            $scope.restMenu[restId] = data;
            $scope.showRestMenu[restId] = 1;
        });
    }
    


    $scope.onCheckoutButtonMouseOver = function() {
		// console.log("On mouse hover start" + $scope.FB.init +
		if ($scope.FB.init && !$scope.FB.name) {
			$('#checkoutButton').fadeOut("fast", function() {
				$('#loginButton').fadeIn("fast");
			});
		}
		// console.log("On mouse hover end");
	}

	$scope.onCheckoutButtonMouseLeave = function() {
		// console.log("On mouse leave start" + $scope.FB.init +
		if ($scope.FB.init && !$scope.FB.name) {
			$('#loginButton').fadeOut("fast", function() {
				$('#checkoutButton').fadeIn("fast");
			});
		}
		// console.log("On mouse leave end");
	}
    
    $scope.initialize = function() {
    	console.log("inside controller initialize");
    	window.fbAsyncInit = function() {
    		console.log("inside fbAsyncInit method");
            FB.init({
                appId      : '591805167609392',
//                appId	   : '85199433896',
                cookie     : true,  // enable cookies to allow the server to access the session
                xfbml      : true,  // parse social plugins on this page
                version    : 'v2.1' // use version 2.1
            });
            
            FB.getLoginStatus(function(response) {
            	if(response.status != "connected") {
            		console.log("fb app not connected");
            		$scope.FB.init = true;
            		return;
            	}
            	$scope.FB.accessToken = response.authResponse.accessToken;
                FB.api('/me', function(response) {
                	console.log("fb app response of /me: " + response);
                    if (response.name) {
                        fbLoginName = response.name;
                        $scope.FB.name = response.name;
                        $scope.FB.init = true;
                    }
                });
            });
        };
        
        (function(d, s, id){
            var js, fjs = d.getElementsByTagName(s)[0];
            if (d.getElementById(id)) {return;}
            js = d.createElement(s); js.id = id;
            js.src = "//connect.facebook.net/en_US/sdk.js";
            fjs.parentNode.insertBefore(js, fjs);
          }(document, 'script', 'facebook-jssdk'));
    }
    
	// Does the $scope.$apply() in a safe manner such that
	// exceptions don't get thrown
	$scope.safeApply = function(fn) {
		var phase = this.$root.$$phase;
		if (phase == '$apply' || phase == '$digest') {
			if (fn && (typeof (fn) === 'function')) {
				fn();
			}
		} else {
			$scope.$apply(fn);
		}
	}

    $scope.restMenu = {};
    $scope.showRestMenu = {};
    $scope.loginState = {loggedIn: false, name: null};
    $scope.FB = {init: false};
    $scope.getQSP();
    $scope.initialize();
    var requestData = {"foodItemId": $scope.id, "availableDay": $scope.availableDay};
    var restListUrl = "api/getDetailPageResults";
    $http.post(restListUrl, JSON.stringify(requestData)).success(function(data) {
        $scope.restList = data;
        $scope.foodItem = data.foodItem;
        $scope.restList.foodItem.foodItemQty = 1;
    });
});

jQuery(function($){
    
});