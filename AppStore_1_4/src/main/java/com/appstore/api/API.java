package com.appstore.api;

import com.appstore.api.dao.AppDAO;

public class API {

	public AppDAO appDao;
	
	public void setAppDao(AppDAO appDao) {
		this.appDao = appDao;
	}
	
	public AppDAO getAppDao() {
		return appDao;
	}
}
