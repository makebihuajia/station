package stationform;

import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.Enumeration;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Text;
import com.google.appengine.api.datastore.Query.SortDirection;

/**
 * This is a class that manipulate the stationForm table
 * @author Yuxing
 *
 */
public class Station {
	long assID;
	Date date;
	String vendorStationID;
	String stationName;
	String latitude;
	String longitude;
	String installDate;
	String county;
	String vendorName;
	String vendorEmail;
	String vendorPhone;
	String vendorAddress;
	String growerName;
	String growerEmail;
	String growerPhone;
	String growerAddress;
	Date approveDate;
	String status = "pending";
	
	private static DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
	private static final Logger logger = Logger.getLogger(Controller.class.getCanonicalName());
	
	/**
	 * create the station table with input "HttpServletRequest request"
	 */
	public long createTable(HttpServletRequest request) {
		//the unique primary key is assID
		logger.log(Level.INFO, "create a station table");
		java.util.Date tempdate = new java.util.Date();
		this.assID = tempdate.getTime(); 
		
		Key stationFormKey = KeyFactory.createKey("Station", assID);
		this.date = new Date();
		this.vendorStationID = request.getParameter("id");
		this.stationName = request.getParameter("name");
		this.latitude = request.getParameter("latitude");
		this.longitude = request.getParameter("longitude");
		this.installDate = request.getParameter("DOInstall");
		this.county = request.getParameter("county");
		this.vendorName = request.getParameter("NOVendor");
		this.vendorEmail = request.getParameter("Email");
		this.vendorPhone = request.getParameter("Phone");
		this.vendorAddress = request.getParameter("Address");
		this.growerName = request.getParameter("Gname");
		this.growerEmail = request.getParameter("Gemail");
		this.growerPhone = request.getParameter("Gphone");
		this.growerAddress = request.getParameter("Gaddress");
		
		Entity oneStation = new Entity(stationFormKey);
		oneStation.setProperty("assID", assID);
		oneStation.setProperty("submitDate", date);
		oneStation.setProperty("vendorStationID", vendorStationID);
		oneStation.setProperty("stationName", stationName);
		oneStation.setProperty("latitude", latitude);
		oneStation.setProperty("longitude", longitude);
		oneStation.setProperty("installDate", installDate);
		oneStation.setProperty("county", county);
		oneStation.setProperty("vendorName", vendorName);
		oneStation.setProperty("vendorEmail", vendorEmail);
		oneStation.setProperty("vendorPhone", vendorPhone);
		oneStation.setProperty("vendorAddress", vendorAddress);
		oneStation.setProperty("growerName", growerName);
		oneStation.setProperty("growerEmail", growerEmail);
		oneStation.setProperty("growerPhone", growerPhone);
		oneStation.setProperty("growerAddress", growerAddress);
		oneStation.setProperty("approveDate", approveDate);
		oneStation.setProperty("status", status);
		datastore.put(oneStation);
		return assID;
	}
	
	//============================get specific date and status JSON data=============================//
	public String fetchStData(Date start_date, Date end_date, String status) {
		logger.log(Level.INFO, "get requested date range and status json data in station table");
		Query q = new Query("Station").addSort("assID", SortDirection.ASCENDING);
		PreparedQuery pq = datastore.prepare(q);
		ArrayList<Entity> entities = new ArrayList<Entity>();
		if(status.equals("submitted")) {
			for(Entity result : pq.asIterable()) {
				Date submit_date;
				try {
			         DateFormat formatter = new SimpleDateFormat("EEE MMM dd HH:mm:ss 'UTC' yyyy", Locale.US);
					// DateFormat formatter = new SimpleDateFormat("EEE MMM dd HH:mm:ss 'EDT' yyyy", Locale.US);
			         submit_date = (Date)formatter.parse(result.getProperty("submitDate").toString());
					if(submit_date.after(start_date) == true && submit_date.before(end_date) == true) {
						entities.add(result);
					}
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		}
		else if(status.equals("approved")) {
			for(Entity result : pq.asIterable()) {
				if(result.getProperty("approveDate") != null) {
					Date approve_date;
					try {
						 DateFormat formatter = new SimpleDateFormat("EEE MMM dd HH:mm:ss 'UTC' yyyy", Locale.US);
						// DateFormat formatter = new SimpleDateFormat("EEE MMM dd HH:mm:ss 'EDT' yyyy", Locale.US);
				         approve_date = (Date)formatter.parse(result.getProperty("submitDate").toString());						
				         if(approve_date.after(start_date) == true && approve_date.before(end_date) == true) {
							entities.add(result);
						}
					} catch (ParseException e) {
							// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}
		String result = DataBase.writeJSON(entities);
		return result;
	}
	
	//=======================get all data in ArrayList<Entity>====================//
	public ArrayList<String> getAllData() {
		logger.log(Level.INFO, "get all data in stationEntity table into an arraylist");
		Query q = new Query("Station").addSort("assID", SortDirection.ASCENDING);
		PreparedQuery pq = datastore.prepare(q);
		
		ArrayList<String> result = DataBase.writeArray(pq.asIterable());
		return result;
	}
	//===========================approve entity with assID=========================//
	public void approveStation(long assID) throws EntityNotFoundException{
		logger.log(Level.INFO, "approve an entity");
		this.approveDate = new Date();
		this.status = "approved";
		Key stationFormKey = KeyFactory.createKey("Station", assID);
		Entity oneStation = datastore.get(stationFormKey);
		
		if(oneStation.getProperty("approveDate") == null) {
			oneStation.setProperty("approveDate", this.approveDate);
			oneStation.setProperty("status", this.status);
			datastore.put(oneStation);
		}
		else {
			return;
		}
	}
	
	
	
	
	
}
