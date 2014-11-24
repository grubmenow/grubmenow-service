var gmnControllers = angular.module('gmnControllers', []);

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
            $scope.searching = 1;
            $('html, body').animate({
                scrollTop: $("#foodFinderMsg").offset().top
            }, 2000);
            $http.post("api/searchFoodItems", JSON.stringify($scope.master)).success(function(data) {
                $scope.master.food = data;
                $scope.searching = 0;
                $scope.searchedOnce = 1;
                $('html, body').animate({
                    scrollTop: $("#searchResults").offset().top
                }, 2000);
            });
            console.log('test');
        }
    };

    $scope.openFeedbackForm = function(formInfo) {
        $scope.showFeedbackForm = formInfo;
        $scope.showFoodItemSuggestionForm = !formInfo;
        $('html, body').animate({
            scrollTop: $("#feedbackFormContainer").offset().top
        }, 2000);
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

    $scope.cancelFeedbackForms = function()
    {
        $scope.showFoodItemSuggestionForm = 0;
        $scope.showFeedbackForm = 0;
    }

    $scope.submitFoodItemSuggestionForm = function()
    {
        request = {foodItemSuggestions: $scope.feedback.newItems, zipCode: $scope.location.zipCode, emailId: $scope.feedback.emailId};
        $http.post("api/submitFoodItemSuggestions", JSON.stringify(request)).success(function(data) {
            $scope.showFoodItemSuggestionForm = 0;
            $scope.showFeedbackForm = 0;
            $scope.showThankYouMessage = 1;
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
        $scope.location = {radius:5, availableDay:"Today"};
        $scope.searching = 0;
    }
    $scope.showThankYouMessage = 0;
    $scope.feedback = {generalFeedback: '', newItems: '', emailId: ""};
    $scope.showFeedbackForm = 0;
    $scope.showFoodItemSuggestionForm = 0;
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
        order.items = [];
        order.orderItems = [];
        order.tax = Math.floor($scope.getTotalPriceInCents(index) * 0.095);
        order.totalPrice = $scope.getTotalPriceInCents(index) + order.tax;
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
        order.pickupTime = pickupDate.toDateString() + " after 6pm. ";
        
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
        window.location.href = "index.html#/checkout";
    }

    $scope.backToSearch = function() {
        var url = "index.html#/home?radius="+$scope.radius+"&availableDay="+$scope.availableDay+"&zipCode="+$scope.zipCode;
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

    $scope.restMenu = {};
    $scope.showRestMenu = {};
    $scope = gmnGetQSP($scope);
    var requestData = {"foodItemId": $scope.id, "availableDay": $scope.availableDay};
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
			  $('.qtyBox').TouchSpin({
	                min: 0,
	                max: 10,
	                step: 1,
	                verticalbuttons: true,
	                verticalupclass: 'glyphicon glyphicon-plus',
	                verticaldownclass: 'glyphicon glyphicon-minus'
	            });
		  });
   };
});

