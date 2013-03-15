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
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.SortDirection;
import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

/**
 * 
 * @author Yuxing
 *If the user is administrator, this is the servlet to control the database, including editing the station is in the status of approving or pending, if approving, add the approving time to database
 */
@SuppressWarnings("serial")
public class AdminController extends HttpServlet {
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{
		UserService userService = UserServiceFactory.getUserService();
		User user = userService.getCurrentUser();
		
		if(user == null) {
			response.sendRedirect(userService.createLoginURL(request.getRequestURI()));
			
		}
		else {
			//first judge whether the user email is an administrator email
			String admin_email = user.getEmail();
			//if it is not administrator
			//if(admin_email != "liuyuxing1215@gmail.com") {
			if(admin_email.equals("test@example.com") == false) {
				PrintWriter out = response.getWriter();
				out.println("<html>");
				out.println("<body>");
				out.println("Sorry, you don't have authorization to access this part "+ "<br>");
				out.println("</body></html>");
			}
			//if administrator, arrange the data as ascending by assID, and put the data into json objects, send json objects to a display jsp page
			else {
				DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
				Query q = new Query("oneStation").addSort("assID", SortDirection.ASCENDING);
				PreparedQuery pq = datastore.prepare(q);
				
				PrintWriter out = response.getWriter();
				for(Entity result : pq.asIterable()) {
					String id = (String) result.getProperty("stationID");
					out.println(id + "  ");
					
				}
			}//else
		}//else
	}//doGet
}//AdminController
