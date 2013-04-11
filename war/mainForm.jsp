<%@page language="java" import="java.util.*" %>
<%@page import="com.google.appengine.api.users.User" %>
<%@page import="com.google.appengine.api.users.UserService" %>
<%@page import="com.google.appengine.api.users.UserServiceFactory" %>
<%@page import="javax.servlet.http.*"%>
<%
String path = request.getContextPath();
String basePath=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title>Farm Weather Station Form</title>
   	<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.7.1/jquery.js" type="text/javascript"></script>
   	<script src="js/jquery-validate/jquery.validate.js" type="text/javascript"></script>
   
  </head>
  <body>
	
	 <%
     UserService userService = UserServiceFactory.getUserService();
     if (!userService.isUserLoggedIn()) {
     	 response.sendRedirect(userService.createLoginURL(request.getRequestURI()));
   	 %>
      
     <% 
     } 
   	 else { 
   	 %>
      Welcome, User <%= userService.getCurrentUser().getNickname()%>!
        (<a href="<%=userService.createLogoutURL("/")%>">log out</a>)
     
   <%
     }
   %>


	<div id="largeWrapper">
		<div id="largeContentWrap">
			<div id="mainIndex">
			
			<!--***************form content************************-->
				<form id="station_form" action="stationform/submit" method="POST">
					<h2 style="margin-bottom: 15px">Station Register Tool</h2>
					<fieldset>
						<div class="mainContactForm welcome">
						Please take a moment to complete the form below and submit the form to get registered
						</div>
					</fieldset>
					<span class="required">* indicates required field</span>
					
			<!--***************station information********************-->
					<fieldset>
    				<table class="mainStationContent">
    					<tr>
    						<td align="right">*Station ID:</td> 
   							<td><input type="text" name="id"></td>
    					</tr>
    					<tr>
     						<td align="right">*Station Name:</td>
     						<td><input type="text" name="name"></td>
    					</tr>
    					<tr>
     						<td align="right">*Latitude:</td>  
     						<td><input type="text" name="latitude">
     						<label for="latitude">Florida range is 25.000000N to 31.000000N, 27.542642 means 27.542642North</label>
     						</td>
    					</tr>
    					<tr>
     						<td align="right">*Longitude:</td>
     						<td><input type="text" name="longitude">
     						<label for="longitude">Florida range is 80.000000W to 88.000000W, 84.542642 means 84.542642West</label>
     						</td>
    					</tr>
    					<tr>
     						<td align="right">*Date Of Installation:</td>
     						<td><input type="text" name="DOInstall">
     						<label for="DOInstall">DD/MM/YYYY</label>
     						</td>
    					</tr>
    					<tr>
							<td align="right">*County:</td>
							<td><select name="county">
									<option value="">- County -</option>
									<option value="ALACHUA">ALACHUA</option>
									<option value="BAKER">BAKER</option>
									<option value="BAY">BAY</option>
									<option value="BRADFORD">BRADFORD</option>
									<option value="BREVARD">BREVARD</option>
									<option value="BROWARD">BROWARD</option>
									<option value="CALHOUN">CALHOUN</option>
									<option value="CHARLOTTE">CHARLOTTE</option>
									<option value="CITRUS">CITRUS</option>
									<option value="CLAY">CLAY</option>
									<option value="COLLIER">COLLIER</option>
									<option value="COLUMBIA">COLUMBIA</option>
									<option value="DESOTO">DESOTO</option>
									<option value="DIXIE">DIXIE</option>
									<option value="DUVAL">DUVAL</option>
									<option value="ESCAMBIA">ESCAMBIA</option>
									<option value="FLAGLER">FLAGLER</option>
									<option value="FRANKLIN">FRANKLIN</option>
									<option value="GADSDEN">GADSDEN</option>
									<option value="GILCHRIST">GILCHRIST</option>
									<option value="GLADES">GLADES</option>
									<option value="GULF">GULF</option>
									<option value="HAMILTON">HAMILTON</option>
									<option value="HARDEE">HARDEE</option>
									<option value="HENDRY">HENDRY</option>
									<option value="HERNANDO">HERNANDO</option>
									<option value="HIGHLANDS">HIGHLANDS</option>
									<option value="HILLSBOROUGH">HILLSBOROUGH</option>
									<option value="HOLMES">HOLMES</option>
									<option value="INDIAN RIVER">INDIAN RIVER</option>
									<option value="JACKSON">JACKSON</option>
									<option value="JEFFERSON">JEFFERSON</option>
									<option value="LAFAYETTE">LAFAYETTE</option>
									<option value="LAKE">LAKE</option>
									<option value="LEE">LEE</option>
									<option value="LEON">LEON</option>
									<option value="LEVY">LEVY</option>
									<option value="LIBERTY">LIBERTY</option>
									<option value="MADISON">MADISON</option>
									<option value="MANATEE">MANATEE</option>
									<option value="MARION">MARION</option>
									<option value="MARTIN">MARTIN</option>
									<option value="MIAMI-DADE">MIAMI-DADE</option>
									<option value="MONROE">MONROE</option>
									<option value="NASSAU">NASSAU</option>
									<option value="OKALOOSA">OKALOOSA</option>
									<option value="OKEECHOBEE">OKEECHOBEE</option>
									<option value="ORANGE">ORANGE</option>
									<option value="OSCEOLA">OSCEOLA</option>
									<option value="PALM BEACH">PALM BEACH</option>
									<option value="PASCO">PASCO</option>
									<option value="PINELLAS">PINELLAS</option>
									<option value="POLK">POLK</option>
									<option value="PUTNAM">PUTNAM</option>
									<option value="SANTA ROSA">SANTA ROSA</option>
									<option value="SARASOTA">SARASOTA</option>
									<option value="SEMINOLE">SEMINOLE</option>
									<option value="ST. JOHNS">ST. JOHNS</option>
									<option value="ST. LUCIE">ST. LUCIE</option>
									<option value="SUMTER">SUMTER</option>
									<option value="SUWANNEE">SUWANNEE</option>
									<option value="TAYLOR">TAYLOR</option>
									<option value="UNION">UNION</option>
									<option value="VOLUSIA">VOLUSIA</option>
									<option value="WAKULLA">WAKULLA</option>
									<option value="WALTON">WALTON</option>
									<option value="WASHINGTON">WASHINGTON</option>
									
							</select>
							</td>
						</tr>
						</table>
						</fieldset>
						
				<!--********************Vendor information***************-->
						<fieldset>
						<table class=""mainVendorContent>
    					<tr>
							<td align="right">*Name Of Vendor:</td>
							<td><select name="NOVendor">
									<option value="">- Name of Vendor -</option>
									<option value="Rainwise">Rainwise</option>
									<option value="Ag-tronix">Ag-tronix</option>
									<option value="McCrometer">McCrometer</option>
									<option value="Locher_Environment">Locher Environment</option>
									<option value="TWC_Distributors_Inc">TWC Distributors Inc</option>
							</select>
							</td>
						</tr>
    					
    					<tr>
     						<td align="right">*Email Of Vendor:</td>  
     						<td><input type="text" name="Email"></td>
    					</tr>
    					<tr>
    						 <td align="right">*PhoneNumber Of Vendor:</td>  
    						 <td><input type="text" name="Phone"></td>
    					</tr>
    					<tr>
     						<td align="right">*Address Of Vendor:</td>  
     						<td><input type="text" name="Address"></td>
    					</tr>
    					</table>
    					</fieldset>
    			<!--********************Grower information****************-->
    					<fieldset>
    					<table class="mainGrowerContent">
    					<tr>
     						<td align="right">*Name Of Grower:</td>  
     						<td><input type="text" name="Gname"></td>
    					</tr>
    					<tr>
     						<td align="right">*Email Of Grower:</td>  
     						<td><input type="text" name="Gemail"></td>
   					 	</tr>
     					<tr>
     						<td align="right">*PhoneNumber Of Grower:</td>  
     						<td><input type="text" name="Gphone"></td>
    					</tr>
     					<tr>
    						 <td align="right">*Address Of Grower:</td>  
     						<td><input type="text" name="Gaddress"></td>
    					</tr>
    					
    				</table>
 					</fieldset>
 					
 					<input id="submit_form" name="submit_form" type="submit" value="Submit">
    			
    			</form>
    			<!--***************************End Of Form***************************-->
    			</div>
    		</div>
    	</div>
    	
    	<!--************************************form validation function***************************-->
    	<script type="text/javascript">
    	
    	$(document).ready(function() {
    	
    		$.validator.addMethod("phoneUS", function(phone_number, element){
    			phone_number = phone_number.replace(/\s+/g, "");
    			return this.optional(element) || phone_number.length > 9 &&
					phone_number.match(/^(1-?)?(\([2-9]\d{2}\)|[2-9]\d{2})-?[2-9]\d{2}-?\d{4}$/);
					}, "Please specify a valid phone number");
					
			jQuery.validator.addMethod("negativeNumber", function(value, element) {
    			return this.optional(element) || (+value < 0) || (+value > 0);
					}, "Please make sure your input format likes 108.4536 or -108.4536");
					
			$.validator.addMethod("USDate", function(value, element) {
        		return value.match(/^\d\d?\/\d\d?\/\d\d\d\d$/);
    				}, "Please enter a date in the format dd/mm/yyyy.");
    				
			$.validator.addMethod("longitude", function(value, element) {
				return value.match(/\d{2}\.\d{6}/);
			}, "please make sure your input format like 84.354225");
			
			$.validator.addMethod("latitude", function(value, element) {
				return value.match(/\d{2}\.\d{6}/);
			}, "please make sure your input format like 28.354225");
			
			$.validator.addMethod("special", function(value, element) {
				return value.match(/^[^\'\"\$\%\!\|\=\{\}\[\]\\\<\>\n\r]*$/);
			}, "please don't input special characters such as ! or % ");
			
			
    		$("#station_form").validate({
    			rules: {
    				id: {required:true, maxlength:20, special:true},
    				name: {required:true, maxlength:128, special:true},
    				latitude: {required:true, range:[25.000000, 31.000000], latitude:true, maxlength:9},
    				longitude: {required:true, range:[80.000000, 88.000000], longitude:true, maxlength:9},
    				DOInstall: {required:true, USDate:true, maxlength:11, special:true},
    				NOVendor: {required:true, maxlength:20},
    				county: {required:true, maxlength:20},
    				Email: {
    					required: true,
    					email: true,
    					maxlength: 128,
    					special:true
    				},
    				Phone: {required:true, phoneUS:true},
    				Address: {required:true, maxlength:128, special:true},
    				Gname: {required:true, maxlength:128, special:true},
    				Gemail:  {
    					required: true,
    					email: true,
    					maxlength:128,
    					special:true
    				},
    				Gphone: {required:true, phoneUS:true},
    				Gaddress: {required:true, maxlength:128, special:true}
    			}
    			
    		});
    	});
    	
    </script>
    
    	</body>
    </html>
