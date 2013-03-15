package stationform;

import java.io.IOException;
import java.io.PrintWriter;
import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.jsr107cache.CacheException;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

/**
 * 
 * @author Yuxing
 * This is the main controller for the station form register
 */
public class Controller extends HttpServlet{
	private static final Logger logger = Logger.getLogger(Controller.class.getCanonicalName());
	/*=================judge whether the user has log in, if log in redirect to the form page=========================*/
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		UserService userService = UserServiceFactory.getUserService();
		User user = userService.getCurrentUser();
		
		if(user == null) {
			response.sendRedirect(userService.createLoginURL(request.getRequestURI()));
			
		}
		else {
			response.setContentType("text/html; charset=gb2312");
			ServletContext sc = getServletContext();
			RequestDispatcher rd = null;
			rd = sc.getRequestDispatcher("/mainForm.html");
			rd.forward(request,  response);
		}
	}//doGet
	/*=======================When submit the form, will store the form data to database===============================*/
	protected synchronized void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		//use timestamp as the primary id
		response.setContentType("text/html");
		java.util.Date tempdate = new java.util.Date();
		long assID = tempdate.getTime(); 
		
		
		Key stationFormKey = KeyFactory.createKey("Station", assID);
		Date date = new Date();
		String stationID = request.getParameter("id");
		String stationName = request.getParameter("name");
		String stationLatitude = request.getParameter("latitude");
		String stationLongitude = request.getParameter("longitude");
		String stationDOInstall = request.getParameter("DOInstall");
		String stationCounty = request.getParameter("county");
		String stationNOVendor = request.getParameter("NOVendor");
		String vendorEmail = request.getParameter("Email");
		String vendorPhone = request.getParameter("Phone");
		String vendorAddress = request.getParameter("Address");
		String growName = request.getParameter("Gname");
		String growEmail = request.getParameter("Gemail");
		String growPhone = request.getParameter("Gphone");
		String growAddress = request.getParameter("Gaddress");
		
		Entity oneStation = new Entity("stationEntity", stationFormKey);
		oneStation.setProperty("assID", assID);
		oneStation.setProperty("submitDate", date);
		oneStation.setProperty("stationID", stationID);
		oneStation.setProperty("stationName", stationName);
		oneStation.setProperty("latitude", stationLatitude);
		oneStation.setProperty("longitude", stationLongitude);
		oneStation.setProperty("installDate", stationDOInstall);
		oneStation.setProperty("County", stationCounty);
		oneStation.setProperty("VendorName", stationNOVendor);
		oneStation.setProperty("vendorEmail", vendorEmail);
		oneStation.setProperty("vendorPhone", vendorPhone);
		oneStation.setProperty("vendorAddress", vendorAddress);
		oneStation.setProperty("growerName", growName);
		oneStation.setProperty("growerEmail", growEmail);
		oneStation.setProperty("growerPhone", growPhone);
		oneStation.setProperty("growerAddress", growAddress);
		
		
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		datastore.put(oneStation);
		
		//if successfully stored the data into database, redirect user to a confirmation page
		request.setAttribute("confirm", assID);
		request.getRequestDispatcher("/confirmation.jsp").forward(request, response);
	}//doPost

}//Controller
