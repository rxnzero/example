angular.module('userDetail').component('userDetail',{
	templateUrl:'user/user-detail/user-detail.template.html',
	controller:['$routeParams','$location','UserService',UserDetailController]
});

function UserDetailController($routeParams,$location,UserService) {
	var self = this;
	self.user = $routeParams.id?UserService.get({id:$routeParams.id}):null;
	
	self.save = function() {
		UserService.save(self.user,function() {		
			$location.path('/list');
		});
	};
	
	self.update = function() {
		UserService.update(self.user, function() {
			$location.path('/list');
		});
	};
	
	self.delete = function() {
		UserService.delete({id:self.user.id}, function() {
			$location.path('/list');	
		});
	};
}