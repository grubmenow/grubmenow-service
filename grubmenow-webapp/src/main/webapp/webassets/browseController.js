var gmnBrowse = angular.module('gmnBrowse', []);

gmnBrowse.controller('ZipcodeCtrl', function ($scope, $http) {
    $scope.location = {radius:0, availableDay:0};
    $scope.searching = 0;
    $scope.showThankYouMessage = 0;
    $scope.feedback = {generalFeedback: '', newItems: ''};
    $scope.showFeedbackForm = 0;
    $scope.showFoodItemSuggestionForm = 0;
        
    $scope.update = function(location) {
        $scope.master = angular.copy(location);
        $scope.searching = 1;
        //var result = decodeURIComponent($.param($scope.master));
        //var ordersUrl = "orders.json?"+result;
        //$http.get(ordersUrl).success(function(data) {
        $http.post("api/searchFoodItems", JSON.stringify($scope.master)).success(function(data) {
            $scope.master.food = data;
            $scope.searching = 0;
            $scope.searchedOnce = 1;
        });
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
    
    $scope.getFinalOrder = function(index) {
        var order = {};
        order.items = [];
        order.orderItems = [];
        order.tax = $scope.getTotalPrice(index) * 0.095;
        order.totalPrice = $scope.getTotalPrice(index) + order.tax;
        order.providerId = $scope.restList.providerFoodItemOffers[index].provider.providerId;
        var i = 0;
        if($scope.restList.foodItem.foodItemQty > 0) {
        	order.items[i] = {
                "orderQty": $scope.restList.foodItem.foodItemQty,
                "orderName": $scope.restList.foodItem.foodItemName,
                "orderPrice": $scope.restList.foodItem.foodItemQty * $scope.restList.providerFoodItemOffers[index].foodItemOffer.price.value
            };
            order.orderItems[i] = {
                "quantity": $scope.restList.foodItem.foodItemQty,
                "foodItemOfferId": $scope.restList.providerFoodItemOffers[index].foodItemOffer.foodItemOfferId
            };
            i++;
        }
        
        var restId = $scope.restList.providerFoodItemOffers[index].provider.providerId;
        
        if ($scope.restMenu[restId]) {
            for(var j = 0; j < $scope.restMenu[restId].providerFoodItemOffers.length; j++) {
                var product = $scope.restMenu[restId].providerFoodItemOffers[j];
                if (!isNaN(parseInt(product.foodItem.foodItemQty))) {
                    order.items[i] = {
                        "orderQty": product.foodItem.foodItemQty,
                        "orderName": product.foodItem.foodItemName,
                        "orderPrice": product.foodItem.foodItemQty * product.foodItemOffer.price.value
                    };
                    order.orderItems[i] = {
                        "quantity": product.foodItem.foodItemQty,
                        "foodItemOfferId": product.foodItemOffer.foodItemOfferId
                    };
                    i++;
                }
            }
        }
        
        $scope.finalOrder = order;
        $scope.checkFBLoginState();
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
    
    $scope.checkFBLoginState = function() {
        if ($scope.FB.name) {
            $scope.loginState.name = $scope.FB.name;
            $scope.loginState.title = "Welcome, "+$scope.FB.name;
        } else {
            $scope.loginState.title = "Please login via Facebook to proceed";
        }
        
        $('#orderModal').modal('show'); 
    }
    
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
    
    $scope.restMenu = {};
    $scope.showRestMenu = {};
    $scope.loginState = {};
    $scope.FB = {};
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