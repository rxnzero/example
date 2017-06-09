angular.module('userList').component('userList', {
	templateUrl:'user/user-list/user-list.template.html',
	controller: ['$location','UserService',UserListController]
});

function UserListController($location,UserService) {
		var self = this;
		self.users = UserService.query();
		
		self.create = function() {
			$location.path('/create');
		};
		
		self.edit = function(id) {
			$location.path('/edit/' + id);
		};
}