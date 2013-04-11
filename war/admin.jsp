<%@page language="java" import="java.util.*" %>
<%@page import="com.google.appengine.api.users.User" %>
<%@page import="com.google.appengine.api.users.UserService" %>
<%@page import="com.google.appengine.api.users.UserServiceFactory" %>
<%@page import="javax.servlet.http.*"%>
<%@page import="java.io.PrintWriter"%>
<%
String path = request.getContextPath();
String basePath=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title>Admin Page</title>
   	<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.7.1/jquery.js" type="text/javascript"></script>
   	<script src="js/jquery-validate/jquery.validate.js" type="text/javascript"></script>
   
  </head>
  <body>
	
	 <%
     UserService userService = UserServiceFactory.getUserService();
     if (!userService.isUserLoggedIn()) {
     	 response.sendRedirect(userService.createLoginURL(request.getRequestURI()));
     } 
   	 else { 
   	 	if(userService.getCurrentUser().getEmail().equals("liuyuxing1348@gmail.com") == false) {
   	 	%>
   	 	Sorry, you are not an administrator! please  (<a href="<%=userService.createLogoutURL("/")%>">log out</a>) and relog in with a valid admin user name and password
   	 	<%
   	 	}
   	 	else {
   	 %>
      Welcome, Administrator <%= userService.getCurrentUser().getNickname()%>!
        (<a href="<%=userService.createLogoutURL("/")%>">log out</a>)
        
        <form id="adminform" name="adminform" action="stationform/admin" method="POST">
     <table align="left" border="1">
     
     <%
     ArrayList<String> data = new ArrayList<String>();
     if(request.getAttribute("out_array") == null) {
     	response.sendRedirect("/stationform");
     }
     else {
     data = (ArrayList<String>)request.getAttribute("out_array");
   	 if(data.size() == 0) {
   	 %>
   	 <tr>
     	<th>  id</th>
   	 	<th>  station_name</th>
   	 	<th>  vendor_station_id</th>
   	 	<th>  vendor_name</th>
   	 	<th>  vendor_address</th>
     	<th>  vendor_phone</th>
     	<th>  vendor_email</th>
     	<th>  grower_name</th>
     	<th>  grower_address</th>
     	<th>  grower_phone</th>
     	<th>  grower_email</th>
     	<th>  status</th>
     	<th>  submit_date</th>
     	<th>  install_date</th>
     	<th>  county</th>
     	<th>  approve_date</th>
     	<th>  longitude</th>
     	<th>  latitude</th>
     	<th>   </th>
     </tr>
   	 <%
   	 }
   	 else if(data.size() > 0) {
   	 	String[] header = data.get(0).split("%");
   	 	String[] col = new String[2];
   	 %>
   	 <tr>
   	 	<th>  id</th>
   	 	<th>  station_name</th>
   	 	<th>  vendor_station_id</th>
   	 	<th>  vendor_name</th>
   	 	<th>  vendor_address</th>
     	<th>  vendor_phone</th>
     	<th>  vendor_email</th>
     	<th>  grower_name</th>
     	<th>  grower_address</th>
     	<th>  grower_phone</th>
     	<th>  grower_email</th>
     	<th>  status</th>
     	<th>  submit_date</th>
     	<th>  install_date</th>
     	<th>  county</th>
     	<th>  approve_date</th>
     	<th>  longitude</th>
     	<th>  latitude</th>
     	<th>   </th>
   	 </tr>
   	 	<%
   	 }	
   	 int row = 0;
     for(int a = 0; a < data.size(); a++) {
 
     	row++;
     %>
     <tr id=<%=row%>>
     <%
    
	 	String[] entity = data.get(a).split("%");
	 	String[] item = new String[2];
	 	item[0] = "null";
	 	item[1] = "null";
	 	String[] item2 = new String[2];
	 	item2[0] = "null";
	 	item2[1] = "null";
	 	boolean flag = true;
	 	String approve_id = "";
	 	for(int k = 0; k < entity.length; k++) {
	 		item = entity[k].split("!");
	 		if(item[0].equals("assID")) {
	 			approve_id = item[item.length-1];
	 		}
	 		if(item[0].equals("status")) {
	 			if(item[item.length-1].equals("approved")) {
	 				flag = false;
	 			}
	 			else {
	 				flag = true;
	 			}
	 		}
	 		
	 		if(k == 0) {
	 			int m = 0;
	 			while(m < entity.length) {
	 				item2 = entity[m].split("!");
	 				if(item2[0].equals("assID")) {
	 				%>
	 				<td><%=item2[item2.length-1]%></td>
	 				<%
	 				break;
	 				}
	 				m++;
	 			}
	 		}
	 		else if(k == 1) {
	 			int m = 0;
	 			while(m < entity.length) {
	 				item2 = entity[m].split("!");
	 				if(item2[0].equals("stationName")) {
	 				%>
	 				<td><%=item2[item2.length-1]%></td>
	 				<%
	 				break;
	 				}
	 				m++;
	 			}
	 		}
	 		else if(k == 2) {
	 			int m = 0;
	 			while(m < entity.length) {
	 				item2 = entity[m].split("!");
	 				if(item2[0].equals("vendorStationID")) {
	 				%>
	 				<td><%=item2[item2.length-1]%></td>
	 				<%
	 				break;
	 				}
	 				m++;
	 			}
	 		}
	 		else if(k == 3) {
	 			int m = 0;
	 			while(m < entity.length) {
	 				item2 = entity[m].split("!");
	 				if(item2[0].equals("vendorName")) {
	 				%>
	 				<td><%=item2[item2.length-1]%></td>
	 				<%
	 				break;
	 				}
	 				m++;
	 			}
	 		}
	 		else if(k == 4) {
	 			int m = 0;
	 			while(m < entity.length) {
	 				item2 = entity[m].split("!");
	 				if(item2[0].equals("vendorAddress")) {
	 				%>
	 				<td><%=item2[item2.length-1]%></td>
	 				<%
	 				break;
	 				}
	 				m++;
	 			}
	 		}
	 		else if(k == 5) {
	 			int m = 0;
	 			while(m < entity.length) {
	 				item2 = entity[m].split("!");
	 				if(item2[0].equals("vendorPhone")) {
	 				%>
	 				<td><%=item2[item2.length-1]%></td>
	 				<%
	 				break;
	 				}
	 				m++;
	 			}
	 		}
	 		else if(k == 6) {
	 			int m = 0;
	 			while(m < entity.length) {
	 				item2 = entity[m].split("!");
	 				if(item2[0].equals("vendorEmail")) {
	 				%>
	 				<td><%=item2[item2.length-1]%></td>
	 				<%
	 				break;
	 				}
	 				m++;
	 			}
	 		}
	 		else if(k == 7) {
	 			int m = 0;
	 			while(m < entity.length) {
	 				item2 = entity[m].split("!");
	 				if(item2[0].equals("growerName")) {
	 				%>
	 				<td><%=item2[item2.length-1]%></td>
	 				<%
	 				break;
	 				}
	 				m++;
	 			}
	 		}
	 		else if(k == 8) {
	 			int m = 0;
	 			while(m < entity.length) {
	 				item2 = entity[m].split("!");
	 				if(item2[0].equals("growerAddress")) {
	 				%>
	 				<td><%=item2[item2.length-1]%></td>
	 				<%
	 				break;
	 				}
	 				m++;
	 			}
	 		}
	 		else if(k == 9) {
	 			int m = 0;
	 			while(m < entity.length) {
	 				item2 = entity[m].split("!");
	 				if(item2[0].equals("growerPhone")) {
	 				%>
	 				<td><%=item2[item2.length-1]%></td>
	 				<%
	 				break;
	 				}
	 				m++;
	 			}
	 		}
	 		else if(k == 10) {
	 			int m = 0;
	 			while(m < entity.length) {
	 				item2 = entity[m].split("!");
	 				if(item2[0].equals("growerEmail")) {
	 				%>
	 				<td><%=item2[item2.length-1]%></td>
	 				<%
	 				break;
	 				}
	 				m++;
	 			}
	 		}
	 		else if(k == 11) {
	 			int m = 0;
	 			while(m < entity.length) {
	 				item2 = entity[m].split("!");
	 				if(item2[0].equals("status")) {
	 				%>
	 				<td><%=item2[item2.length-1]%></td>
	 				<%
	 				break;
	 				}
	 				m++;
	 			}
	 		}
	 		else if(k == 12) {
	 			int m = 0;
	 			while(m < entity.length) {
	 				item2 = entity[m].split("!");
	 				if(item2[0].equals("submitDate")) {
	 				%>
	 				<td><%=item2[item2.length-1]%></td>
	 				<%
	 				break;
	 				}
	 				m++;
	 			}
	 		}
	 		else if(k == 13) {
	 			int m = 0;
	 			while(m < entity.length) {
	 				item2 = entity[m].split("!");
	 				if(item2[0].equals("installDate")) {
	 				%>
	 				<td><%=item2[item2.length-1]%></td>
	 				<%
	 				break;
	 				}
	 				m++;
	 			}
	 		}
	 		else if(k == 14) {
	 			int m = 0;
	 			while(m < entity.length) {
	 				item2 = entity[m].split("!");
	 				if(item2[0].equals("county")) {
	 				%>
	 				<td><%=item2[item2.length-1]%></td>
	 				<%
	 				break;
	 				}
	 				m++;
	 			}
	 		}
	 		
	 		else if(k == 15) {
	 			int m = 0;
	 			while(m < entity.length) {
	 				item2 = entity[m].split("!");
	 				if(item2[0].equals("approveDate")) {
	 				%>
	 				<td><%=item2[item2.length-1]%></td>
	 				<%
	 				break;
	 				}
	 				m++;
	 			}
	 		}
	 		else if(k == 16) {
	 			int m = 0;
	 			while(m < entity.length) {
	 				item2 = entity[m].split("!");
	 				if(item2[0].equals("longitude")) {
	 				%>
	 				<td><%=item2[item2.length-1]%></td>
	 				<%
	 				break;
	 				}
	 				m++;
	 			}
	 		}
	 		
	 		else if(k == 17) {
	 			int m = 0;
	 			while(m < entity.length) {
	 				item2 = entity[m].split("!");
	 				if(item2[0].equals("latitude")) {
	 				%>
	 				<td><%=item2[item2.length-1]%></td>
	 				<%
	 				break;
	 				}
	 				m++;
	 			}
	 		}
	 		
	 		
	 	}
	 %>
	
	<td> <input id=<%=approve_id%> name="<%=approve_id%>" type="button" value="Approve" onclick="approve(<%=approve_id%>)"></td>
	 </tr>
	 <%
	 if(flag == false) {
	 %>
	 	<script>
	 		document.getElementById(<%=approve_id%>).disabled=true;
	 	</script>
	 <%
	 }
	 }
	 }
   %>
   </table>
   </form>
        
     
   	<%
   		}
     }
    %>
     
     
   
   <script type="text/javascript">
   function approve(id) {
   $.ajax({
   		url: "/stationform/admin",
   		type: "POST",
   		data: {"id": id},
   		success: function() {
   		window.location.reload(true);
   		}
   });
  	
   }
   </script>
   
   </body>
   </html>