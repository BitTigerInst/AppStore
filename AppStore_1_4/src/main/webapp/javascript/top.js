(function(){
	  var homeApp = angular.module('top', []);
	  
	  homeApp.controller('appList',['$http','$window',function($http,$window){
		  this.appList = [];//jobs;
		  this.showApp =false;
		  
		  this.appToShow = null;
		  this.appToRecom = null;
		  
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
		  };
		  
	  }]);//end-Controller-initContainer
	  
	})();//end-function
