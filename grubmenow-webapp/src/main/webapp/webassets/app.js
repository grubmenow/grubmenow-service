var gmnApp = angular.module('gmnApp', [
'ngRoute',
'gmnControllers'
]);

gmnApp.config(['$routeProvider', function($routeProvider) {
	$routeProvider.
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
	otherwise({
		redirectTo: '/home'
	});
}]);