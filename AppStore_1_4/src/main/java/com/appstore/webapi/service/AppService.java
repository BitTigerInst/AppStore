package com.appstore.webapi.service;

import java.util.List;

import com.appstore.entity.App;

public interface AppService {
	public App createApp(App obj);
	public App readApp(String appId);
	public App updateApp(App obj);
	public App deleteApp(App obj);
	
	public List<App> readAppByUser(String userId);
	public List<App> readAppbyCatalog(String catalogId);
	public List<App> readTopApps(int topN);
	
	public boolean isExist(App app);
	public List<App> readRecomApps(List<String> appIDs);
}
