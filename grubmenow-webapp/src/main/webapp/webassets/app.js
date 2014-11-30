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
		templateUrl: 'restaurantChooser.html',
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
    when('/contactus', {
        templateUrl: 'contactUs.html',
        controller: 'contactUsController'
    }).
    when('/orderDetails', {
        templateUrl: 'orderDetails.html',
        controller: 'orderDetailsController'
    }).
	otherwise({
		redirectTo: '/home'
	});
}]);