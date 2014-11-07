angular.module('gmnControllers').controller('InviteCtrl', function ($scope, $http, $location) {
    
    $scope.requestInvitation = function(invite) {
    	$http.post("api/invitationRequest", JSON.stringify(invite)).success(function(data) {
    		$scope.invitationRequested = true;
    	});
    }
    
    $scope.invite = {};
    $scope.invitationRequested = false;
});
