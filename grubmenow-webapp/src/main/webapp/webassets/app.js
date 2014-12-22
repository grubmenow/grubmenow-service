var gmnApp = angular.module('gmnApp', [
'ngRoute',
'gmnControllers'
]);

gmnApp.config(['$routeProvider', function($routeProvider) {
	$routeProvider.
	when('/invitation', {
		templateUrl: 'invitationPage.html',
		controller: 'InviteCtrl'
	}).
	when('/home', {
		templateUrl: 'searchForm.html',
		controller: 'SearchFormCtrl'
	}).
	when('/chefs', {
		templateUrl: 'restaurantChooser-new.html',
		controller: 'RestuarantCtrl'
	}).
	when('/checkout', {
		templateUrl: 'checkout.html',
		controller: 'CheckoutCtrl'
	}).
	when('/howitworks', {
        templateUrl: 'howitworks.html',
        controller: 'HIWCtrl'
    }).
	when('/faq', {
        templateUrl: 'faq.html',
        controller: 'FAQCtrl'
    }).
    	when('/cheffaq', {
        templateUrl: 'cheffaq.html',
        controller: 'FAQCtrl'
    }).
    when('/contactus', {
        templateUrl: 'contactUs.html',
        controller: 'ContactUsController'
    }).
    when('/orderDetails', {
        templateUrl: 'orderDetails.html',
        controller: 'orderDetailsController'
    }).
    when('/privacy', {
        templateUrl: 'privacy.html'
    }).
    when('/terms', {
        templateUrl: 'terms.html'
    }).
	otherwise({
		redirectTo: '/home'
	});
}]);
