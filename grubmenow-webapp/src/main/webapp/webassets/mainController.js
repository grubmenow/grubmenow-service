var gmnControllers = angular.module('gmnControllers', ['ui.bootstrap']);

var gmnGetQSP = function(scope) {
    var url = window.location.href;
    if(url.indexOf('?') < 0) return scope;
    var hashes = url.slice(url.indexOf('?') + 1).split('&');
    for(var i = 0; i < hashes.length; i++)
    {
        hash = hashes[i].split('=');
        scope[hash[0]] = hash[1];
    }

    return scope;
}

angular.module('gmnControllers').controller('SearchFormCtrl', function ($scope, $http) {

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

    $scope.update = function() {
        $scope.master = $scope.location;
        $scope.validateForm(); 
        if($scope.location.zipCode && $scope.location.radius != 0 && $scope.location.availableDay != 0) {
            delete $scope.master['food'];
            $scope.noResults = 0;
            $scope.searching = 1;
            $('html, body').animate({
                scrollTop: $("#foodFinderMsg").offset().top
            }, $scope.animationTime);

            var request = {};
            request.zipCode = $scope.master.zipCode;
            request.radius = $scope.master.radius;
            request.availableDay = $scope.master.availableDay;
            var d = new Date()
            request.timezoneOffsetMins = d.getTimezoneOffset();

            $scope.resetAllFormStates();
            $http.post("api/searchFoodItems", JSON.stringify(request)).success(function(data) {
                $scope.master.food = data;
                if(!data || data.length == 0) $scope.noResults = 1;
                $scope.searching = 0;
                $scope.searchedOnce = 1;
                $('html, body').animate({
                    scrollTop: $("#searchResults").offset().top
                }, $scope.animationTime);
            });
        }
    };

    $scope.openFeedbackForm = function(formInfo) {
        $scope.showFeedbackForm = formInfo;
        $scope.showFoodItemSuggestionForm = !formInfo;
        $('html, body').animate({
            scrollTop: $("#feedbackFormContainer").offset().top
        }, $scope.animationTime);
    };


	$scope.submitFeedbackForm = function() {
		request = {
			feedbackType : "SEARCH_GENERAL_FEEDBACK",
			feedbackMessage : $scope.feedback.generalFeedback,
			zipCode : $scope.location.zipCode,
			emailId : $scope.feedback.emailId
		};
		$scope.submittingFeedback = 1;
		$http.post("api/submitGeneralFeedback",
				JSON.stringify(request)).success(
				function(data) {
					$scope.submittingFeedback = 0;
					$scope.showFoodItemSuggestionForm = 0;
					$scope.showFeedbackForm = 0;
					$scope.showThankYouMessage = 1;
				}).error(function(data) {
			$scope.submittingFeedback = 0;
		})
	};
    
    $scope.cancelFeedbackForms = function()
    {
        $scope.showFoodItemSuggestionForm = 0;
        $scope.showFeedbackForm = 0;
    }

	$scope.resetAllFormStates = function() {
		$scope.showThankYouMessage = 0;
		$scope.feedback = {
			generalFeedback : '',
			newItems : '',
			emailId : ""
		};
		$scope.showFeedbackForm = 0;
		$scope.showFoodItemSuggestionForm = 0;
		$scope.submittingFeedback = 0;
	}

    $scope.submitFoodItemSuggestionForm = function()
    {
        request = {foodItemSuggestions: $scope.feedback.newItems, zipCode: $scope.location.zipCode, emailId: $scope.feedback.emailId};
        $scope.submittingFeedback = 1;
        $http.post("api/submitFoodItemSuggestions", JSON.stringify(request)).success(function(data) {
            $scope.submittingFeedback = 0;
            $scope.showFoodItemSuggestionForm = 0;
            $scope.showFeedbackForm = 0;
            $scope.noResults = 0; // forget the search information since the user has taken an action over it. 
            $scope.showThankYouMessage = 1;
        })
        .error(function(data) {
			$scope.submittingFeedback = 0;
		})
    };

    //Update the Nav state
    $( "li", "#gmnNav" ).each(function( index ) {
        $( this ).removeClass('active');
    });
    $('#homeNav').addClass('active');
    $('#navbarCollapse').removeClass('in');

    $scope.location = {};
    $scope.location = gmnGetQSP($scope.location);
    if($scope.location.zipCode) {
        $scope.update();
    } else {
    	
    	var nowDate = new Date();
    	
    	// if we are above the cut off time, don't show the option for today
    	var cutoffTime = 16; // cut of time to order by today. Server and client side are stored separately.
    	
    	if(nowDate.getHours() >= cutoffTime) {
    	    $scope.isBeforeCutOffTime = 0;
    		$scope.location = {radius:5, availableDay:"Tomorrow"};
    	} else {
    		$scope.isBeforeCutOffTime = 1;
    		$scope.location = {radius:5, availableDay:"Today"};	
    	}
        
        $scope.searching = 0;
    }
    $scope.resetAllFormStates();
    $scope.animationTime = 500;
});

angular.module('gmnControllers').controller('RestuarantCtrl', function ($scope, $http, $location) {

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

    $scope.getTotalPriceInCents = function(index) {
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
        order.availableDay = $scope.availableDay;
        order.items = [];
        order.orderItems = [];
        order.tax = Math.floor($scope.getTotalPriceInCents(index) * 0.095);
        order.totalPrice = $scope.getTotalPriceInCents(index) + order.tax;
        order.provider = $scope.restList.providerFoodItemOffers[index].provider;
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
        order.restLocation = $scope.restList.providerFoodItemOffers[index].provider.providerAddress;
        
        var pickupDate = $scope.availableDay == 'Today' ? new Date() : new Date(new Date().getTime() + 24 * 60 * 60 * 1000);
        order.pickupTime = $scope.availableDay + " " + $scope.restList.providerFoodItemOffers[index].foodItemOffer.offerDay	
        					+ ", 6pm to 9pm ";
        order.chefPhone = $scope.restList.providerFoodItemOffers[index].provider.providerPhoneNumber;
        
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
        localStorage.setItem('gmn.finalOrder', JSON.stringify(order));
        window.location.href = "#/checkout";
    }

    $scope.backToSearch = function() {
        var url = "#/home?radius="+$scope.radius+"&availableDay="+$scope.availableDay+"&zipCode="+$scope.zipCode;
        window.location.href = url;
    }

    $scope.collapse = function(index) {
        if(!$scope.collapseState) $scope.collapseState = {};
        if(!$scope.collapseState.index) {
            $('#collapse'+index).collapse('hide');
            $scope.collapseState.index = 1;
        } else {
            $('#collapse'+index).collapse('show');
            $scope.collapseState.index = 0;
        }
        
    }   
    
    $scope.showMenu = function(restId, excludedFoodItem) {
        if ($scope.restMenu[restId]) {
            $scope.showRestMenu[restId] = 1;
            return;
        }
        var requestData = {"providerId": restId, "availableDay": $scope.availableDay};
        var date = new Date();
        requestData.timezoneOffsetMins = d.getTimezoneOffset();
        requestData.excludedFoodItem = excludedFoodItem;
        var restListUrl = "api/getProviderMenu";
        $http.post(restListUrl, JSON.stringify(requestData)).success(function(data) {
            $scope.restMenu[restId] = data;
            $scope.showRestMenu[restId] = 1;
        });
    }

    $scope.restMenu = {};
    $scope.showRestMenu = {};
    $scope = gmnGetQSP($scope);
    var requestData = {"foodItemId": $scope.id, "availableDay": $scope.availableDay, "radius": $scope.radius, "zipCode": $scope.zipCode};
    var d = new Date();
    requestData.timezoneOffsetMins = d.getTimezoneOffset();
    var restListUrl = "api/getDetailPageResults";
    $http.post(restListUrl, JSON.stringify(requestData)).success(function(data) {
        $scope.restList = data;
        $scope.foodItem = data.foodItem;
        $scope.restList.foodItem.foodItemQty = 1;
        $scope.chefsLoaded = 1;
    });
    
}).directive('providerRepeatDirective', function() {
	  return function(scope, element, attrs) {
		  scope.$watch('rest', function(){
			  $('span.stars').stars();
		  });
   };
});

