angular.module('app').config(['$locationProvider','$routeProvider',
	function config($locationProvider,$routeProvider) {
		$routeProvider.when('/list', {
			template:'<user-list></user-list>'
		}).when('/edit/:id', {
			template:'<user-detail></user-detail>'
		}).when('/create',{
			template:'<user-detail></user-detail>'
		}).otherwise('/list');
}]);