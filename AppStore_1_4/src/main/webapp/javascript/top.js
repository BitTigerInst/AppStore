(function(){
	  var homeApp = angular.module('top', []);
	  
	  homeApp.controller('appList',['$http','$window','$scope', function($http,$window,$scope){
		  this.appList = [];//jobs;
		  this.showApp = false;
		  this.addShow = false;
		  this.showList = true;
		  this.showupdate = false;
		  
		  this.appToShow = null;
		  this.appToRecom = null;
		  this.appToUp = null;
		  this.appUpdate = null;

		  var container = this;
		  
		 


		  $scope.getTop = function(){
		  	  container.showApp = false;
			  container.addShow = false;
			  container.showList = true;
			  container.showupdate = false;

			  container.appToShow = null;
			  container.appToRecom = null;
			  container.appToUp = null;
			  container.appUpdate = null;			  
		  	
		  	var req = {
				  method: 'GET',
				  url: "/AppStore_1_4/app/",
				  headers: {
				    'Content-Type': "application/json",
				    	'dataType': 'json'
				  }
				 };

		  $http(req).then(function(data) {
			  container.appList = data.data;
			  container.appsToShow = container.appList[0];
			  
			  console.log("reveive array successfully");
		    });
		  }


		   $scope.getTop();
		  
		  $scope.selectApp = function(app){ 
			  container.appToShow = app;
			  container.appUpdate = app;
			  container.showApp =true;
			  container.showList = false;
			  container.addShow = false;

			  console.log(JSON.stringify(app.top5AppsArray));
			  var reqest = {
					  method: 'POST',
					  url: "/AppStore_1_4/app/getRecom/similarapp/",
					  data: app.top5AppsArray,
					  headers: {
						'Accept': 'application/json',
					    'Content-Type': "application/json",
					    'dataType': 'json'
					  }
					 };
			  $http(reqest).then(
					  function(data) {
						
						  if(data.data.length>0){
							  container.appsToRecom = data.data;
						  }else{
							  container.appsToRecom = [];
						  }
							  
						  console.log("reveive array successfully");
					  },function(data){	    	  
						  alert("Note: no recommandation for this app");
					  }
			    );
		  };//end-seleCate
		  
		  $scope.goBackToMain = function(){
			  $scope.getTop();
		  };



		  // upload the app 
		  $scope.submit = function(){
		  	container.appToUp.appid = "";
		  	container.appToUp.top5App = "";
		  	container.appToUp.score = 0;

		  	console.log(JSON.stringify(container.appToUp));

		  	var request = {
		  		method: 'POST',
		  		url: "/AppStore_1_4/app/create/",
		  		data: JSON.stringify(container.appToUp),
		  		headers:{
		  			'Accept' : 'application/json',
		  			'Content-Type':'application/json',
		  			'dataType':'json'
		  		}
		  	};
		  	
		  	$http(request).then(
			  function(response){
			  	  console.log(response);
				  console.log("reveive array successfully");
				  
				  $scope.selectApp(response.data);
				  
				  container.appToUp = "";
				  //goBackToMain();
			  },function(response){
			  	console.log(response.status);
			  	if (response.status == 409 ) {
			  		alert("Note this application has been already in the database");

			  	}else{
			  		alert("something wrong with your applicaton");
			  	}
			  	container.appToUp = "";
			  	$scope.goBackToMain();
			  }
			);
		  };


		  $scope.update = function(){
		  	console.log(container.appUpdate);
		  	delete container.appUpdate.top5AppsArray;
		  	delete container.appUpdate.$$hashKey;


		  	var request = {
		  		method: 'PUT',
		  		url: "/AppStore_1_4/app/" + container.appUpdate.appid,
		  		data: JSON.stringify(container.appUpdate),
		  		headers:{
		  			'Accept' : 'application/json',
		  			'Content-Type':'application/json',
		  			'dataType':'json'
		  		}
		  	};
		  	$http(request).then(
		  		function(response){
		  			console.log(response.status);
		  			$scope.goBackToMain();
		  		},
		  		function(response){
		  			console.log(response.status);
		  			alert("something wrong");
		  		}
		  	);
		  };


		  $scope.delete = function(app){
		  	var request = {
		  		method: 'DELETE',
		  		url: "/AppStore_1_4/app/"+ app.appid,
		  		data : JSON.stringify(app),
		  		headers:{
		  			'Accept' : 'application/json',
		  			'Content-Type':'application/json',
		  			'dataType':'json'
		  		}
		  	}

		  	$http(request).then(
		  		function(response){
		  			console.log(response);
		  			console.log(response.status);
		  			alert("the app has been delete");
		  			
		  			$scope.goBackToMain();

		  		}, function(response){
		  			alert("the app cannot be deleted");
		  		}
		  	);
		  };

		  $scope.addApp = function(){
		      container.addShow = true;
		      container.showList = false;
 
		  };

		  $scope.change = function(){
		  	container.showApp = false;
		  	container.showupdate = true;
		  }
		  
	  }]);//end-Controller-initContainer
	})();//end-function