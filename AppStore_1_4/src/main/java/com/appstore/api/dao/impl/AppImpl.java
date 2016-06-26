package com.appstore.api.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.appstore.entity.App;
import com.appstore.api.dao.AppDAO;

public class AppImpl implements AppDAO{
	private SessionFactory sessionFactory;
	
	@Override
	/*
	 * Problem: App's coms and cata are both fetchType.EAGER, but when read, they are loaded.
	 * @see com.appstore.api.dao.AppDAO#createApp(com.appstore.entity.App)
	 */
	public App createApp(App appObj) {
		// TODO Auto-generated method stub
		Object obj = this.getSession().save(appObj);
		appObj = this.readApp(obj.toString());
		return appObj;
	}

	@Override
	public App deleteApp(App appObj) {
		// TODO Auto-generated method stub
		this.getSession().delete(appObj);
		return appObj;
	}

	@Override
	public App updateApp(App appObj) {
		// TODO Auto-generated method stub
		this.getSession().update(appObj);
		return null;
	}

	@Override
	public App readApp(String appID) {
		// TODO Auto-generated method stub
		App app = (App) this.getSession().get(App.class, appID);		
		return app;
	}

	@Override
	public List<App> readAppByCatalog(String catalogID) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<App> readAppByUser(String userID) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public boolean isAppExist(App app) {
		// TODO Auto-generated method stub
		String appid = app.getAppid();
		Query query = this.getSession().createQuery("select count(*) from App app where app.appid = :appid").setString("appid", appid);
		long count = (long) query.uniqueResult();
		return count == 1 ? true : false;
	}

	@Override
	public List<App> readTopApps(int topN) {
		// TODO Auto-generated method stub
		Query query = this.getSession().createQuery("from App app order by app.score desc").setMaxResults(topN);
		List<App> apps = (List<App>)query.list(); 
		return apps;
	}
	
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

    public Session getSession() {
        return this.sessionFactory.getCurrentSession();
    }

	@Override
	public List<App> readRecomApps(List<String> appIDs) {
		// TODO Auto-generated method stub
		if(appIDs == null)
			return new ArrayList<>();
		
		List<App> recomApps = new ArrayList<>(appIDs.size());
		for(String appid : appIDs){
			App app = this.readApp(appid);
			if(app != null)
				recomApps.add(app);
			else{
				System.out.println("appImpl 104 returned app with id: "+appid+" is null");
			}
		}
		return recomApps;
	}
}
