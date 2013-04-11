package stationform;

import java.io.IOException;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

/**
 * 
 * @author Yuxing
 * This is the main controller for the station form register
 * This is also the initial page
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
			String admin_email = user.getEmail();
			//if it is a user
			if(admin_email.equals("liuyuxing1348@gmail.com") == false) {
				logger.log(Level.INFO, "this is a user");
				response.setContentType("text/html; charset=gb2312");
				ServletContext sc = getServletContext();
				RequestDispatcher rd = null;
				rd = sc.getRequestDispatcher("/mainForm.jsp");
				rd.forward(request,  response);
			}
			else {//if it is an administer
				logger.log(Level.INFO, "this is an admin");
				Station st = new Station();
				ArrayList<String> out_array = new ArrayList<String>();
				out_array = st.getAllData();
				
				request.setAttribute("out_array", out_array);
				request.getRequestDispatcher("/admin.jsp").forward(request, response);
				
			}
		}
	}//doGet
	
	/*=======================When submit the form, will store the form data to database===============================*/
	protected synchronized void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		
		String path = request.getPathInfo();
		if (path.equals("/submit")) {
		//use timestamp as the primary id
		response.setContentType("text/html");
		Station st = new Station();
		long assID = st.createTable(request);
		
		//if successfully stored the data into database, redirect user to a confirmation page
		request.setAttribute("confirm", assID);
		request.getRequestDispatcher("/confirmation.jsp").forward(request, response);
		}
		
		if(path.equals("/admin")) {
			String admin_arg_old = request.getParameter("id");
			long admin_arg_new = Long.parseLong(admin_arg_old);
			Station st = new Station();
			
				try {
					st.approveStation(admin_arg_new);
				} catch (EntityNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				
		}
		
	}//doPost

	
}//Controller
