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
    <base href="<%=basePath%>">
    <title>Confirmation page</title>
    <meta http-equiv="pragma" content="no-cache">
 	<meta http-equiv="cache-control" content="no-cache">
 	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
 	<meta http-equiv="description" content="This is my page">
  </head>
	<body>
	
	<h2>Thank you! Your confirmation number is</h2>
	<%=request.getAttribute("confirm")%>
	
	<%
     UserService userService = UserServiceFactory.getUserService();
     if (!userService.isUserLoggedIn()) {
     	 response.sendRedirect(userService.createLoginURL(request.getRequestURI()));
     } 
   	 else {  	
   	 %>
      Now you can (<a href="<%=userService.createLogoutURL("/")%>">log out</a>)
      You can also (<a href="stationform">continue</a>)continue input another form
   	<%
     }
    %>
	
	
	</body>
	</html>