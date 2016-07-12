<html ng-app= "top">
<head>
	<script type="text/javascript" src="http://www.francescomalagrino.com/BootstrapPageGenerator/3/js/jquery-2.0.0.min.js"></script>
	<script type="text/javascript" src="http://www.francescomalagrino.com/BootstrapPageGenerator/3/js/jquery-ui"></script>
	<link href="http://www.francescomalagrino.com/BootstrapPageGenerator/3/css/bootstrap-combined.min.css" rel="stylesheet" media="screen">
	<script type="text/javascript" src="http://www.francescomalagrino.com/BootstrapPageGenerator/3/js/bootstrap.min.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/javascript/angular.min.js"></script>
	<script src="${pageContext.request.contextPath}/javascript/top.js"></script>
<style>
.container-fluid {
	width : 30%;
	margin-right: auto;
	margin-left: auto;
}

#carousel-762850{
	margin-top: 0px;
	margin-bottom: 0px;
}

.list-group-item {
	padding: 15px 15px;
}

.list-group-item-text {
	width : 100%;
}

.list-group-item-text td{
	width : 20%;
}

table img{
	width : 50%;
}

.show-app-detail{
	width : 100%;
}
.show-app-detail td{
	width : 25%;
	text-align: left;
}

.app-detail-img{
	margin-left : 25%;
	margin-top  : 10%;
	margin-bottom  : 10%;
}

.app-recom td{
	width : 20%;
}

.app-recom img{
	margin-left : 25%;
	margin-top  : 10%;
	margin-bottom  : 10%;
}



</style>
</head>
<body>
<div class="container-fluid">
	<div class="row-fluid">
		<div class="span12">
			<div class="carousel slide" id="carousel-762850" data-ride="carousel" >
				<ol class="carousel-indicators">
					<li class="active" data-slide-to="0" data-target="#carousel-762850">
					</li>
					<li data-slide-to="1" data-target="#carousel-762850">
					</li>
					<li data-slide-to="2" data-target="#carousel-762850">
					</li>
				</ol>
				<div class="carousel-inner">
					<div class="item active">
						<img alt="" src="${pageContext.request.contextPath}/img/3.jpg" />
						<div class="carousel-caption">
							<h4>
								App Name Here
							</h4>
							<p>
								App Description Here
							</p>
						</div>
					</div>
					<div class="item">
						<img alt="" src="${pageContext.request.contextPath}/img/4.jpg" />
						<div class="carousel-caption">
							<h4>
								App Name Here
							</h4>
							<p>
								App Description Here
							</p>
						</div>
					</div>
					<div class="item">
						<img alt="" src="${pageContext.request.contextPath}/img/5.jpg" />
						<div class="carousel-caption">
							<h4>
								App Name Here
							</h4>
							<p>
								App Description Here
							</p>
						</div>
					</div>
				</div> 
				<a class="left carousel-control" data-slide="prev" href="#carousel-762850">&lsaquo;</a> 
				<a class="right carousel-control" data-slide="next" href="#carousel-762850">&rsaquo;</a>
			</div>
		</div>
	</div>

	<!-- show all the app -->
	<div class="row-fluid" ng-controller = "appList as apps">
		<div class="span12">
			<div class="list-group" ng-show = "apps.showList">
				<div class = "list-group-item active">
					<i class="fa fa-bar-chart-o fa-fw"></i>Top 10 Apps
					<div class="pull-right">
                        
                            <button type="button" class="btn btn-primary" ng-click="addApp()">Add new APPs</button>
                        
                    </div>
				</div>

				<div class="list-group-item" ng-repeat = "app in apps.appList">
					<table class="list-group-item-text">
						<tr>
							<td id = "show_appid" rowspan = "2">{{app.appid}}</td>
							<td id = "show_img" rowspan = "2"><img alt="app_img" ng-src="{{app.thumbnail_url}}"/></td>
							<td id = "show_appname" colspan="2">{{app.title}}</td>
							<td id = "get_appdetail" rowspan="2"><button class="btn btn-primary" type="button" ng-click="selectApp(app)">GET</button> </td>
						</tr>
						<tr>
							<td id= "show_rate" colspan="2">rate:{{app.score}}/10</td>
						</tr>
					</table>
				</div>
			</div>

			<!-- add a new app -->
			<div class = "addApp" ng-show = "apps.addShow">
				<div class = list-group>
				<a href="#" class="list-group-item active" ng-click= "goBackToMain()">Go Back</a>
				</div>

				<table class="show-app-detail">
					<tr>
						<td rowspan = "2" colspan="1"><img class="app-detail-img" ng-src="{{apps.appToUp.thumbnail_url}}"/></td>
						

						<td rowspan = "1" colspan="1">{{apps.appToUp.title}}</td>
					</tr>

					<tr>
						<td rowspan = "1" colspan="1">{{apps.appToUp.developer}}</td>
					</tr>
					
				</table>
				
				<form ng-submit = "submit()">
					<h4> submit your app </h4>

					<fieldset class = "form-group">
 						<input type = "text" title = "name" ng-model = "apps.appToUp.title" placeholder="name" />
					</fieldset>

					<fieldset class = "form-group">
						<input type = "text" title = "url" ng-model = "apps.appToUp.url" placeholder="url"/>
					</fieldset>

					<fieldset class = "form-group"> 
						<input type = "text" title = "thumbnail_url" ng-model = "apps.appToUp.thumbnail_url" placeholder="thumbnail_url"/>
					</fieldset>

					<fieldset class = "form-group">
						<textarea  ng-model = "apps.appToUp.intro" placeholder=" please introduce your app " > </textarea>
					</fieldset>
					
					<fieldset class = "form-group">
						<input type = "text" title = "dev" ng-model = "apps.appToUp.developer" placeholder="developer"/> 
					</fieldset>

					<fieldset class="form-group">
                		<input type="submit" class="btn btn-primary pull-right" value="Submit"/>
              		</fieldset>
				</form>
			</div>


			<!-- update a status of app -->
			<div class = "update" ng-show = "apps.showupdate">
				<div class = list-group>
				<a href="#" class="list-group-item active" ng-click= "goBackToMain()">Go Back</a>
				</div>

				<form ng-submit = "update()">
					<h4> update your app </h4>
					<fieldset class = "form-group">
 						<input type = "text" title = "name" ng-model = "apps.appUpdate.title" placeholder="{{apps.appToShow.title}}" />
					</fieldset>

					<fieldset class = "form-group">
						<input type = "text" title = "url" ng-model = "apps.appUpdate.url" placeholder="{{apps.appToShow.url}}"/>
					</fieldset>

					<fieldset class = "form-group"> 
						<input type = "text" title = "thumbnail_url" ng-model = "apps.appUpdate.thumbnail_url" placeholder="{{apps.appToShow.thumbnail_url}}"/> 
					</fieldset>

					<fieldset class = "form-group">
						<textarea  ng-model = "apps.appUpdate.intro" placeholder="{{apps.appToShow.intro}}" > </textarea>
					</fieldset>
					
					<fieldset class = "form-group">
						<input type = "text" title = "dev" ng-model = "apps.appUpdate.developer" placeholder="{{appToShow.developer}}"/> 
					</fieldset>

					<fieldset class="form-group">
                		<input type="submit" class="btn btn-primary pull-right" value="Submit"/>
              		</fieldset>
				</form>
			</div>
			


			<!-- show one app -->
			<div class = "showApp" ng-show = "apps.showApp">
				<div class = list-group>
				<a href="#" class="list-group-item active" ng-click= "goBackToMain()">Go Back</a>

				<a href="#" class="list-group-item active" ng-click= "change()">update these app</a>
				
				<div class="list-group-item">
					<table class="show-app-detail">
						<tr>
							<td rowspan = "3" colspan="1"><img class="app-detail-img" ng-src="{{apps.appToShow.thumbnail_url}}"/></td>
							<td colspan = "1"> {{apps.appToShow.appid}}</td>
						</tr>
						
						<tr>
							<td rowspan = "1" colspan = "1">{{apps.appToShow.title}}</td>
						</tr>
						
						<tr>
							<td colspan = "1">rate: {{apps.appToShow.score}}/10</td>
						</tr>
					</table>
				</div>
				<div class="list-group-item">
					<p>Customers Also Like:</p>
					<table class="app-recom">
						<tr >
							<td ng-repeat="appToRecom in apps.appsToRecom"><img ng-src="{{appToRecom.thumbnail_url}}"></td>
						</tr>
						
						<tr>
							<td ng-repeat="appToRecom in apps.appsToRecom">{{appToRecom.title}}</td>
						</tr>
					</table>
				</div>
				
				<div class="list-group-item">
					<div> <h4>Description:</h4> </div>
					<div>
						<p>
							{{apps.appToShow.intro}}
						</p>
					</div>
				</div>

				<a href="#" class="list-group-item active" ng-click= "delete(apps.appToShow)">delete this app</a>
			</div>
			</div>

		</div>
	</div>
</div>
</body>
</html>