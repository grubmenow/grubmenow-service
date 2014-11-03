angular.module('gmnBrowse').controller('SearchFormCtrl', function ($scope, $http, $location, $anchorScroll) {
    $scope.location = {radius:5, availableDay:"Today"};
    $scope.searching = 0;
    $scope.showThankYouMessage = 0;
    $scope.feedback = {generalFeedback: '', newItems: '', emailId: ""};
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
                // auto scroll to the search results
                $location.hash("searchResults");
                $anchorScroll();
        	});
        }
    };
    
    $scope.openFeedbackForm = function() {
    	$scope.showFeedbackForm = 1;
    	$scope.showFoodItemSuggestionForm = 0;
        // auto scroll to the opened suggestion form
        $location.hash("generalFeedbackForm");
        $anchorScroll();
    };
    
    $scope.openSearchSuggestionForm = function() {
    	$scope.showFoodItemSuggestionForm = 1;
    	$scope.showFeedbackForm = 0;
        // auto scroll to the opened search suggestion form
        $location.hash("searchSuggestionForm");
        $anchorScroll();
    };
    
    $scope.submitFeedbackForm = function()
    {
    	request = {feedbackMessage: $scope.feedback.generalFeedback};
    	$http.post("api/submitGeneralFeedback", JSON.stringify(request)).success(function(data) {
    		$scope.showFoodItemSuggestionForm = 0;
        	$scope.showFeedbackForm = 0;
    		$scope.showThankYouMessage = 1;
            $location.hash("thankYouMessage");
            $anchorScroll();
    	})
    };
    
    $scope.submitFoodItemSuggestionForm = function()
    {
    	request = {foodItemSuggestions: $scope.feedback.newItems, zipCode: $scope.location.zipCode, emailId: $scope.feedback.emailId};
    	$http.post("api/submitFoodItemSuggestions", JSON.stringify(request)).success(function(data) {
    		$scope.showFoodItemSuggestionForm = 0;
        	$scope.showFeedbackForm = 0;
    		$scope.showThankYouMessage = 1;
            $location.hash("thankYouMessage");
            $anchorScroll();
    	})
    };
});
