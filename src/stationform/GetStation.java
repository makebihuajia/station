package stationform;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.logging.Logger;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class GetStation extends HttpServlet{
	private static final Logger logger = Logger.getLogger(Controller.class.getCanonicalName());
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException{
		
		PrintWriter out = response.getWriter();
		String input_timestamp = request.getParameter("time_stamp");
		String input_pass = request.getParameter("password");
		String input_status = request.getParameter("status");
		String input_start_date = request.getParameter("start_date");
		String input_end_date = request.getParameter("end_date");
		String password = "password";
		boolean start_date_ok = false;
		boolean end_date_ok = false;
		GetStation gs = new GetStation();
		start_date_ok = gs.isValidDate(input_start_date);
		end_date_ok = gs.isValidDate(input_end_date);
		//if url request is not valid
		if((input_status.equals("approved")==false && input_status.equals("submitted")==false) || start_date_ok == false || end_date_ok == false) {
			out.println("Error Occurs:please input a valid api call url!");
		}
		else {	
			String before = input_timestamp + "-plus-" + password;
			String auth = "";
			try {
				MessageDigest md = MessageDigest.getInstance("MD5");
				md.update(before.getBytes());
				auth = new BigInteger(1,md.digest()).toString(16);
			
			} catch (NoSuchAlgorithmException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//if url request has authorization
			if(input_pass.equals(auth) == true) {
				try {
					//System.out.println("start_date is " + input_start_date);
					Date start_date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSSSSS").parse(input_start_date+" 00:00:00.000000");
					Date end_date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSSSSS").parse(input_end_date+" 23:59:59.0000000");
					Station st = new Station();
					String output = st.fetchStData(start_date, end_date, input_status);
					out.println(output);
					
					
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				
			}
			else {
				out.println("Error! You are not authorized!");
			}
		
		}
	}
	
	/*
	 * to check whether user has input a valid date format
	 */
	public boolean isValidDate(String inDate) {

	    if (inDate == null)
	      return false;

	    //set the format to use as a constructor argument
	    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	    
	    if (inDate.trim().length() != dateFormat.toPattern().length())
	      return false;
	    dateFormat.setLenient(false);
	    
	    try {
	      //parse the inDate parameter
	      dateFormat.parse(inDate.trim());
	    }
	    catch (ParseException pe) {
	      return false;
	    }
	    return true;
	}
	
}
