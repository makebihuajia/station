package stationform;

import java.util.ArrayList;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;

public class DataBase {
	private String dbName;
	private static final Logger logger = Logger.getLogger(DataBase.class.getCanonicalName());
	public DataBase(String dbName) {
		this.dbName = dbName;
	}
	
	private static DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
	
	
	/*
	/**
	 * List the entities in JSON format
	 * 
	 * @param entities
	 *          entities to return as JSON strings
	 */
	
	public static String writeJSON(Iterable<Entity> entities) {
		logger.log(Level.INFO, "creating JSON format object");
	
		StringBuilder sb = new StringBuilder();
		int i = 0;
		sb.append("{\"data\": [");
		for (Entity result : entities) {
			Map<String, Object> properties = result.getProperties();
			sb.append("{");
			//sb.append("\"Key\" : \"" + result.getKey().getName() + "\",");

			for (String key : properties.keySet()) {
				String key_show = "";
				if(key.equals("vendorName"))
					key_show = "vendor_name";
				else if(key.equals("growerAddress"))
					key_show="grower_address";
				else if(key.equals("submitDate"))
					key_show="submitted_date";
				else if(key.equals("growerEmail"))
					key_show="grower_email";
				else if(key.equals("assID"))
					key_show="id";
				else if(key.equals("vendorAddress"))
					key_show="vendor_address";
				else if(key.equals("vendorPhone"))
					key_show="vendor_phone";
				else if(key.equals("vendorEmail"))
					key_show="vendor_email";
				else if(key.equals("vendorStationID"))
					key_show="vendor_station_id";
				else if(key.equals("installDate"))
					key_show="date_of_installation";
				else if(key.equals("growerPhone"))
					key_show="grower_phone";
				else if(key.equals("approveDate"))
					key_show="approve_date";
				else if(key.equals("growerName"))
					key_show="grower_name";
				else if(key.equals("stationName"))
					key_show="station_name";
				else if(key.equals("status") || key.equals("longitude") || key.equals("latitude") || key.equals("county"))
					key_show = key;
				sb.append("\"" + key_show + "\" : \"" + properties.get(key) + "\",");
			}
			sb.deleteCharAt(sb.lastIndexOf(","));
			sb.append("},");
			i++;
		}
		if (i > 0) {
			sb.deleteCharAt(sb.lastIndexOf(","));
		}
		sb.append("]}");
		return sb.toString();
	}
	
	/*
	 * List the entities in ArrayList
	 */
	public static ArrayList<String> writeArray(Iterable<Entity> entities) {
		logger.log(Level.INFO, "create Array");
		ArrayList<String> get_array = new ArrayList<String>();
	
		
		for (Entity result : entities) {
			StringBuilder sb = new StringBuilder();
			Map<String, Object> properties = result.getProperties();

			for (String key : properties.keySet()) {
				sb.append(key + "!" + properties.get(key) + "%");
			}
			get_array.add(sb.toString());
		}
		
		return get_array;
	}
	
	
	
}
