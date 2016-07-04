package com.appstore.api.dao;

import java.util.List;

import com.appstore.entity.App;

public interface AppDAO {
	public App createApp(App appObj);
	public App deleteApp(App appObj);
	public App updateApp(App appObj);
	public App readApp(String appID);
	
	public List<App> readAppByCatalog(String catalogID);
	public List<App> readAppByUser(String userID);
	public List<App> readTopApps(int topN);
	
	public boolean isAppExist(App app);
	public List<App> readRecomApps(List<String> appIDs);
}
