(function(){
	  var homeApp = angular.module('top', []);
	  
	  homeApp.controller('appList',['$http','$window',function($http,$window){
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
		  
		  this.selectApp = function(app){ 
			  container.appToShow = app;
			  container.showApp =true;
			  container.showList = false;

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
		  
		  this.goBackToMain = function(){
			  container.showApp = false;
			  container.showList = true;
			  container.addShow = false;
			  container.showupdate = false;
		  };



		  // upload the app 
		  this.submit = function(){
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
				  
				  container.selectApp(response.data);

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
			  	container.goBackToMain();
			  }
			);
		  };


		  this.update = function(){

		  	var request = {
		  		method: 'PUT',
		  		url: "/AppStore_1_4/app/" + app.appid,
		  		data: JSON.stringify(app),
		  		headers:{
		  			'Accept' : 'application/json',
		  			'Content-Type':'application/json',
		  			'dataType':'json'
		  		}
		  	};
		  	$http(request).then(
		  		function(response){
		  			console.log(response.status);
		  		},
		  		function(response){
		  			console.log(response.status);
		  		}
		  	);
		  };


		  this.delete = function(app){
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
		  			container.goBackToMain();
		  		}, function(response){
		  			alert("the app cannot be deleted");
		  		}
		  	);
		  };



		  this.addApp = function(){
		      container.addShow = true;
		      container.showList = false;
 
		  };

		  this.change = function(){
		  	container.showApp = false;
		  	container.showupdate = true;
		  }
		  
	  }]);//end-Controller-initContainer
	})();//end-function