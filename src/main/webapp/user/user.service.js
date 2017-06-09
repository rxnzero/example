angular.module('user').factory('UserService',['$resource', function($resource) {
	return $resource(
			'rest/users/:id',
			{
				
			},
			{
				update:{method:'PUT'}
			},
			{
				stripTrailingSlashes:true
			});
}]);