package com.appstore.webapi.service.impl;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.appstore.api.API;
import com.appstore.entity.App;
import com.appstore.webapi.service.AppService;

public class AppServiceImpl implements AppService{

	private API api;
	
	@Override
	@Transactional
	public App createApp(App obj) {
		// TODO Auto-generated method stub
		return this.api.getAppDao().createApp(obj);
	}

	@Override
	@Transactional
	public App readApp(String appId) {
		// TODO Auto-generated method stub
		return this.api.getAppDao().readApp(appId);
	}

	@Override
	@Transactional
	public App updateApp(App obj) {
		// TODO Auto-generated method stub
		return this.api.getAppDao().updateApp(obj);
	}

	@Override
	@Transactional
	public App deleteApp(App obj) {
		// TODO Auto-generated method stub
		return this.api.getAppDao().deleteApp(obj);
	}

	@Override
	@Transactional
	public List<App> readAppByUser(String userId) {
		// TODO Auto-generated method stub
		return this.api.getAppDao().readAppByUser(userId);
	}

	@Override
	@Transactional
	public List<App> readAppbyCatalog(String catalogId) {
		// TODO Auto-generated method stub
		return this.api.getAppDao().readAppByCatalog(catalogId);
	}

	@Override
	@Transactional
	public List<App> readTopApps(int topN) {
		// TODO Auto-generated method stub
		return this.api.getAppDao().readTopApps(topN);
	}
	
	public API getApi() {
		return api;
	}

	public void setApi(API api) {
		this.api = api;
	}

	@Override
	@Transactional
	public boolean isExist(App app) {
		// TODO Auto-generated method stub
		return this.api.getAppDao().isAppExist(app);
		
	}

	@Override
	@Transactional
	public List<App> readRecomApps(List<String> appIDs) {
		// TODO Auto-generated method stub
		return this.api.getAppDao().readRecomApps(appIDs);
	}

}
