package com.appstore.webapi;

import com.appstore.webapi.service.AppService;

public class WebApi {
	
	private AppService appService;
	
	public AppService getAppService() {
		return appService;
	}
	public void setAppService(AppService appService) {
		this.appService = appService;
	}
}
