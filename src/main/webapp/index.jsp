<html ng-app="app">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Example Web Application</title>
<script type="text/javascript" src="angular-1.6.1/angular.js"></script>
<script type="text/javascript" src="angular-1.6.1/angular-route.js"></script>
<script type="text/javascript" src="angular-1.6.1/angular-resource.js"></script>
<script type="text/javascript" src="app/app.module.js"></script>
<script type="text/javascript" src="app/app.config.js"></script>
<script type="text/javascript" src="user/user.module.js"></script>
<script type="text/javascript" src="user/user-list/user-list.module.js"></script>
<script type="text/javascript"
	src="user/user-detail/user-detail.module.js"></script>
<script type="text/javascript" src="user/user.service.js"></script>
<script type="text/javascript"
	src="user/user-list/user-list.component.js"></script>
<script type="text/javascript"
	src="user/user-detail/user-detail.component.js"></script>
</head>
<body>
	<div ng-view></div>
</body>
</html>