<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 3.2//EN">
<%
	String cmptitle = request.getParameter("c");
	String date = request.getParameter("d");
	String msg = (String)request.getAttribute("msg");
	String company = "";
	if(cmptitle.equals(GMCCostants.GMC))
	{
		company = GMCCostants.XGMC;	
	}
	else 
	{
		company = GMCCostants.XSKC;	
	}
%>
<%@page import="com.app.gmc.common.GMCCostants"%> 
<html> 
<head> 
<meta http-equiv="Content-Type" content="text/html;charset=ISO-8859-1" >
<title><%= GMCCostants.TITLE %></title>
<style>
a.navwhite:link { text-decoration: none; color: #ffffff; font-family: Verdana, Arial, sans-serif; font-size: 10px; font-weight: bold; }
a.navwhite:visited { text-decoration: none; color: #ffffff; font-family: Verdana, Arial, sans-serif; font-size: 10px; font-weight: bold; }
a.navwhite:hover { text-decoration: underline; color: #ffffff; font-family: Verdana, Arial, sans-serif; font-size: 10px; font-weight: bold; }
a.navblack:link { text-decoration: none; color: #000000; font-family: Verdana, Arial, sans-serif; font-size: 10px; font-weight: bold; }
a.navblack:visited { text-decoration: none; color: #000000; font-family: Verdana, Arial, sans-serif; font-size: 10px; font-weight: bold; }
a.navblack:hover { text-decoration: underline; color: #000000; font-family: Verdana, Arial, sans-serif; font-size: 10px; font-weight: bold; }
</style>

<style>
<!-- 
h1 { font-family: Arial, sans-serif; font-size: 30px; color: #004080;}
h2 { font-family: Arial, sans-serif; font-size: 18px; color: #004080;}

body,p,b,i,em,dt,dd,dl,sl,caption,th,td,tr,u,blink,select,option,form,div,li { font-family: Arial, sans-serif; font-size: 12px; }

/* IE Specific */
body, textarea {
  scrollbar-3dlight-color: #808080;
  scrollbar-highlight-color: #808080;
  scrollbar-face-color: #004080;
  scrollbar-shadow-color: #808080;
  scrollbar-darkshadow-color: #805B32;
  scrollbar-arrow-color: #000000;
  scrollbar-track-color: #F8EFE2;
} 
/* END IE Specific */
-->
</style>


</head>
<body bgcolor="#ffffff" >
<p>
<table cellspacing="1" cellpadding="3" width="780" align="center" bgcolor="#004080" 
border="0">
  
  <tr>
    <td>
      <h1 align="center"><br><font color="#ffffff"><%= GMCCostants.TITLE %></font></h1>
      <p>&nbsp;</p></td></tr>
  <tr>
      <td bgcolor="#c0c0c0">
      <p align="center">
      &nbsp; <a class="navblack" href="#" 
      		onclick="document.frmhidden.action='ChangeCompanyAction';document.frmhidden.submit();"><%= GMCCostants.M_SETTINGS %></a>
      &nbsp; |&nbsp; <a class="navblack" href="#" 
			onclick="document.frmhidden.action='AddAction';document.frmhidden.submit();"><%= GMCCostants.M_ADD %></a>
      &nbsp; |&nbsp; <a class="navblack" href="#" 
      	onclick="document.frmhidden.action='ListingAction';document.frmhidden.submit();"><%= GMCCostants.M_LIST %></a>
      &nbsp; |&nbsp; <a class="navblack" href="#" 
      	onclick="document.frmhidden.action='ReportAction';document.frmhidden.submit();"><%= GMCCostants.M_REPORT %></a>
      &nbsp; |&nbsp; <a class="navblack" href="#" 
      	onclick="document.frmhidden.action='SummaryAction';document.frmhidden.submit();"><%= GMCCostants.M_SUMMARY %></a>
      </p></td></tr>
  <tr>
    <td bgcolor="#ffffff">
     <div align=center>
	    <h1>
	    <%= company %>
	    </h1>
	     <h2>
	    <%= date %>
		</h2>	    
     </div>
     </td></tr>
  <tr>
  <% if (msg != null && msg != "") {%>
  <tr>
  	<td>
  	 <center><font color="white" size="5"> <%= msg %> </font></center>
  	 </td>
  	</tr>
  <tr>
  <% } %>
  <td ></td></tr></table>
</body>
<form name="frmhidden" action="" method=post>
<input type="hidden" name="cmpsel" id="cmpsel" value= <%= cmptitle %>/>
<input type="hidden" name="MyDate" id="MyDate" value= <%= date %>/>
</form>
</html>