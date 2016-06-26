package com.appstore.controller;


import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.util.UriComponentsBuilder;

import com.appstore.entity.App;
import com.appstore.webapi.WebApi;

@Controller
public class AppController {
	
	@Resource(name = "webapi")
	private WebApi api;	
	
	//-------------------Retrieve Single App--------------------------------------------------------
	@RequestMapping(value = "/app/{appid}", method = RequestMethod.GET)
	public ResponseEntity<App> getApp(@PathVariable("appid") String appid) {
		System.out.println("Fetching app with id " + appid);
		App app = this.api.getAppService().readApp(appid);
		if (app == null) {
			System.out.println("app with id " + appid + " not found");
			return new ResponseEntity<App>(HttpStatus.NOT_FOUND);
		}
		HttpHeaders headers = new HttpHeaders();
		return new ResponseEntity<App>(app,headers, HttpStatus.OK);
	}
	
	//-------------------Retrieve Top 10 Apps--------------------------------------------------------
	@RequestMapping(value = "/app/", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<App>> getTopApps() {
		int topN = 10;
		List<App> apps = this.api.getAppService().readTopApps(topN);
		if (apps == null) {
			System.out.println(" no apps found");
			return new ResponseEntity<List<App>>(HttpStatus.NOT_FOUND);
		}
		System.out.println("/****************************************************/"+"\r\n"+
		                   "                    Retrievw top 10 Apps              "+"\r\n"+
				           "/****************************************************/");
		HttpHeaders headers = new HttpHeaders();
		return new ResponseEntity<List<App>>(apps, headers, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/app/getRecom/similarapp/", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<App>> getRecomApps(@RequestBody List<String> appIDs) {
		List<App> apps = this.api.getAppService().readRecomApps(appIDs);
		if (apps == null||apps.size()==0) {
			System.out.println("AppController 65: no recommandation apps found");
			return new ResponseEntity<List<App>>(HttpStatus.NOT_FOUND);
		}
		System.out.println("/****************************************************/"+"\r\n"+
		                   "                    Retrievw 5 Recom Apps              "+"\r\n"+
				           "/****************************************************/");
		System.out.println(apps.size()+" "+(apps.size()==0?null:apps.get(0).getAppid())+" "+appIDs.get(0)+" "+appIDs.size());
		HttpHeaders headers = new HttpHeaders();
		return new ResponseEntity<List<App>>(apps, headers, HttpStatus.OK);
	}
	
	//-------------------Create a App--------------------------------------------------------
	
		@RequestMapping(value = "/app/", method = RequestMethod.POST)
		public ResponseEntity<App> createApp(@RequestBody App app, UriComponentsBuilder ucBuilder) {
			System.out.println("Creating App " + app.getTitle());

			if (this.api.getAppService().isExist(app)) {
				System.out.println("A app with name " + app.getTitle() + " already exist");
				return new ResponseEntity<App>(HttpStatus.CONFLICT);
			}

			app = this.api.getAppService().createApp(app);
			
			HttpHeaders headers = new HttpHeaders();
			headers.setLocation(ucBuilder.path("/app/{appid}").buildAndExpand(app.getAppid()).toUri());
			
			ObjectMapper mapper=new ObjectMapper();
			try {
				String jsonString=mapper.writeValueAsString(app);
				System.out.print(jsonString);
			} catch (JsonGenerationException e) {
				e.printStackTrace();
			} catch (JsonMappingException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			return new ResponseEntity<App>(app, headers, HttpStatus.CREATED);
		}

		
		//------------------- Update a App --------------------------------------------------------
		
		@RequestMapping(value = "/app/{appid}", method = RequestMethod.PUT)
		public ResponseEntity<App> updateApp(@PathVariable("appid") String appid, @RequestBody App app) {
			System.out.println("Updating app " + appid);
			
			App currentApp = this.api.getAppService().readApp(appid);
			
			if (currentApp==null) {
				System.out.println("App with id " + appid + " not found");
				return new ResponseEntity<App>(HttpStatus.NOT_FOUND);
			}else if(appid.equals(app.getAppid()))
				return new ResponseEntity<App>(HttpStatus.CONFLICT);
			
			this.api.getAppService().updateApp(app);
			return new ResponseEntity<App>(currentApp, HttpStatus.OK);
		}

		//------------------- Delete a App --------------------------------------------------------
		
		@RequestMapping(value = "/app/{appid}", method = RequestMethod.DELETE)
		public ResponseEntity<App> deleteApp(@RequestBody App app) {//@RequestBody App app --> 400 bad request, instead please use @PathVariable("appid") String appid
			//App app = new App();
			//app.setAppid(appid);
			System.out.println("\r\n"+app.getAppid());
			String appid = app.getAppid();
			System.out.println("Fetching & Deleting App with appid " + app.getAppid());
//
			app = this.api.getAppService().deleteApp(app);
			if (app == null) {
				System.out.println("Unable to delete. App with id " + appid + " not found");
				return new ResponseEntity<App>(HttpStatus.NOT_FOUND);
			}

			return new ResponseEntity<App>(HttpStatus.NO_CONTENT);
		}
	 
}
