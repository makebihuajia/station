package stationform;

import java.util.Collections;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.Enumeration;

import javax.servlet.ServletException;

import org.mortbay.util.ajax.JSON;

import net.sf.jsr107cache.Cache;
import net.sf.jsr107cache.CacheException;
import net.sf.jsr107cache.CacheManager;

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

public class DataBase {
	private String dbName;
	private static final Logger logger = Logger.getLogger(DataBase.class.getCanonicalName());
	public DataBase(String dbName) {
		this.dbName = dbName;
	}
	
	private static DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
	
	/**
	 * @param entity : entity to be persisted
	*/
	public static void persistEntity(Entity entity) {
		logger.log(Level.INFO, "saving entity");
		datastore.put(entity);
	}
	
	
	
	
	/**
	 * get all the element in the database
	 * @return
	 */
	
	public Hashtable<String,Hashtable<String,String>> fetchAll() {
		Hashtable<String, Hashtable<String,String>> records = new Hashtable<String, Hashtable<String,String>>();
		logger.log(Level.INFO, "retrieve all the station's data in database");
		Query q = new Query(this.dbName);
		PreparedQuery pq = datastore.prepare(q);
		for(Entity result : pq.asIterable()) {
			Text text = (Text)result.getProperty("info");
			String info = text.getValue();
			String id = (String) result.getKey().getName();
			Hashtable<String,String> recordArr = this.processStr2Arr(info);
			records.put(id, recordArr);
		}
		if(records.isEmpty()) {
			return null;
		}
		return records;
	}
	
	public Hashtable<String,String> processStr2Arr(String record) {
		Hashtable<String,String> recordArr = new Hashtable<String,String>();
		String[] pairs = record.split(",");
		for(String pair: pairs) {
			String[] keyVal = pair.split("=");
			if(keyVal.length == 2) {
				String key = keyVal[0];
				String val = keyVal[1];
				recordArr.put(key, val);
			}
		}
		return recordArr;
	}
}
