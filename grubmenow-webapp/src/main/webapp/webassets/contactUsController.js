angular.module('gmnControllers').controller('contactUsController', function ($scope, $http, $location) {
	
    $scope.getQSP = function() {
        var url = $location.absUrl();
        var hashes = url.slice(url.indexOf('?') + 1).split('&');
        for(var i = 0; i < hashes.length; i++)
        {
            hash = hashes[i].split('=');
            $scope[hash[0]] = hash[1];
        }
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

	$scope.submitContact = function()
    {
		$scope.remoteCallInProgress = 1;
    	request = {
    			feedbackType: "CONTACT",
    			feedbackMessage: $scope.feedback.generalFeedback, 
    			emailId: $scope.feedback.emailId,
    			zipcode: $scope.feedback.zipCode};
    	$http.post("api/submitGeneralFeedback", JSON.stringify(request))
    		.success(function(data) {
	        	$scope.showFeedbackForm = 0;
	    		$scope.showThankYouMessage = 1;
	    		$scope.remoteCallInProgress = 0;
    		})
    		.error(function(data) {
    			$scope.showFeedbackForm = 1;
    			$scope.remoteCallInProgress = 0;
    		})
    };
    
    $scope.feedback = {};
    $scope.showFeedbackForm = 1;
    $scope.showThankYouMessage = 0;
});