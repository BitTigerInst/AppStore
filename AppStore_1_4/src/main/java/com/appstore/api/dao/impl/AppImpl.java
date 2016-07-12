package com.appstore.api.dao.impl;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.appstore.entity.App;
import com.appstore.api.dao.AppDAO;

public class AppImpl implements AppDAO{
	private SessionFactory sessionFactory;
	private Pattern pattern = Pattern.compile("D(\\d+)");
	private static Logger logger = Logger.getLogger(AppImpl.class);
	@Override
	/*
	 * Problem: App's coms and cata are both fetchType.EAGER, but when read, they are loaded.
	 * @see com.appstore.api.dao.AppDAO#createApp(com.appstore.entity.App)
	 */
	public App createApp(App appObj) {

		Session session = null;
		Transaction tx = null;

		try {
			session = this.getSessionFactory().openSession();
			tx = session.beginTransaction();
			tx.setTimeout(5);
			Query query = this.getSession()
					.createQuery("select Max(app.appid) from App as app where app.appid like :appid")
					.setString("appid", "D%");
			query.setLockMode("app", LockMode.WRITE);
			String maxId = (String) query.uniqueResult();
			Matcher m = pattern.matcher(maxId);
			if (m.find()) {
				String maxNumericInString = m.group(1);
				BigInteger maxNumericInInteger = new BigInteger(maxNumericInString);
				BigInteger nextNumbericInInteger = maxNumericInInteger.add(BigInteger.valueOf(1));
				String nextId = "D" + nextNumbericInInteger.toString();
				appObj.setAppid(nextId);
				Object obj = this.getSession().save(appObj);
				appObj = this.readApp(nextId);
			}
			tx.commit();
			return appObj;
		} catch (RuntimeException e) {
			try {
				if (tx != null) {
					tx.rollback();
				}
			} catch (RuntimeException rbe) {
				logger.error("Couldn��t roll back transaction", rbe);
			}
			throw e;
		} finally {
			if (session != null) {
				session.close();
			}
		}
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
