angular.module('gmnControllers').controller('orderDetailsController', function ($scope, $http, $location) {
	
    $scope.getQSP = function() {
        var url = $location.absUrl();
        var hashes = url.slice(url.indexOf('?') + 1).split('&');
        for(var i = 0; i < hashes.length; i++)
        {
            hash = hashes[i].split('=');
            $scope[hash[0]] = hash[1];
        }
    }

    $scope.loadOrder = function(websiteAuthenticateToken) {
        $('.loadMessage').addClass('alert alert-info').show().html("Please wait, While we load your order");
        
        var requestObj = new Object();
        requestObj.websiteAuthenticationToken = websiteAuthenticateToken;
        requestObj.orderId = $scope['orderId'];
        $scope.getCustomerOrderRequest = requestObj;

        $http.post("api/getCustomerOrder", JSON.stringify($scope.getCustomerOrderRequest)).success(function(data) {
        	$('.loadMessage').hide();
            $scope.getCustomerOrderResponse = data;
            $scope.orderLoaded = 1;
        });
    };
    
    $scope.loadOrderFeedback = function(websiteAuthenticationToken) {
    	
    	$scope.rating_descriptions = ['N/A', 'Not Satisfactory', 'Could have been better', 'OK', 'Good', 'Delicious'];

    	var requestObj = new Object();
        requestObj.websiteAuthenticationToken = websiteAuthenticationToken;
        requestObj.orderId = $scope['orderId'];
        $scope.getCustomerOrderFeedbackRequest = requestObj;

        
        $http.post("api/getCustomerOrderFeedback", JSON.stringify($scope.getCustomerOrderFeedbackRequest)).success(function(data) {
        	$scope.review = new Object() 
            $scope.review.feedback = data.feedback;
            $scope.review.rating = data.rating;
            
            // show review only if user has not yet provided the review for this item
            if(data.rating == -1) {
            	$scope.allowReview = 1;
            } else {
            	$scope.review.rating_description = $scope.rating_descriptions[data.rating];
            	$scope.showReview = 1;
            }
        });
    };

    
    $scope.submitFeedback = function(review) {
        var requestObj = new Object();
        requestObj.websiteAuthenticationToken = $scope.FB.accessToken;
        requestObj.orderId = $scope['orderId'];
        requestObj.rating = review.rating;
        requestObj.feedback = review.feedback;
        
        $scope.searching = 1;
        
        $('.feedbackMessage').addClass('alert alert-info').show();
        $('.feedbackMessage').html("Please wait.. Submitting feedback");
        
        $http.post("api/submitOrderFeedback", JSON.stringify(requestObj)).success(function(data) {
        	$('.feedbackMessage').removeClass('alert-info').addClass('alert-success').html("Thanks, Feedback Submitted").fadeOut(2000);
        	$scope.review.rating_description = $scope.rating_descriptions[review.rating];
        	$scope.allowReview = 0;
        	$scope.showReview = 1;
        });
    };

    $scope.handleFBResponse = function(token, name, email) {
        $scope.safeApply(function() {
            $scope.FB.libraryLoaded = 1;
            if(!token) {
                $scope.FB.notRecognized = 1;
                $scope.FB.name = null;
                $scope.FB.email = null;
            } else {
                $scope.FB.accessToken = token;
                $scope.FB.notRecognized = 0;
                $scope.FB.name = name;
                $scope.FB.email = email;
                $scope.loadOrder(token);
                $scope.loadOrderFeedback(token);
            }
        });
    }
    
    $scope.formatEnum = function(enumString)
    {
    	return enumString.replace(/_/g, " ");
    }

    $scope.safeApply = function(fn) {
		var phase = this.$root.$$phase;
		if (phase == '$apply' || phase == '$digest') {
			if (fn && (typeof (fn) === 'function')) {
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

    $scope.showReview = 0;
    $scope.allowReview = 0;
    $scope.orderLoaded = 0;
    $scope.FB = {libraryLoaded: 0, notRecognized: 1};
    $scope.getQSP();
    $scope.initializeFB();
});