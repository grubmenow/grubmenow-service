angular.module('gmnControllers', []);

angular.module('gmnControllers').controller('InviteCtrl', function ($scope, $http, $location) {
    
    $scope.requestInvitation = function(invite) {
    	$http.post("api/invitationRequest", JSON.stringify(invite)).success(function(data) {
    		$scope.invitationRequested = true;
    	});
    }
    //Update the Nav state
    $( "li", "#gmnNav" ).each(function( index ) {
        $( this ).removeClass('active');
    });
    $('#invitationNav').addClass('active');
    
    $scope.invite = {};
    $scope.invitationRequested = false;
});
