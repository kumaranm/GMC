<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 3.2//EN">
<%@page import="com.app.gmc.db.GMCDBApp"%>
<% boolean db = GMCDBApp.checkDB();  %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html;charset=ISO-8859-1" >
<title>Vithuvaravu</title>
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
<body bgcolor="#ffffff">
<p></p>
<table cellspacing="1" cellpadding="3" width="780" align="center" bgcolor="#004080" 
border="0">
  
  <tr>
    <td>
      <h1 align="center"><br><font color="#ffffff">VITHUVARAVU</font></h1>
      <p>&nbsp;</p></td></tr>
  <tr>
      <td bgcolor="#c0c0c0">
      <%if(db) {%>
      <p align="center">
      &nbsp; <a class="navblack" href="servlet/ChangeCompanyAction" >SETTINGS</a>
      &nbsp; |&nbsp; <a class="navblack" href="servlet/AddAction" >ADD RECORDS</a>
      &nbsp; |&nbsp; <a class="navblack" href="servlet/ListingAction" >CORRECT</a>
      &nbsp; |&nbsp; <a class="navblack" href="servlet/ReportAction" >REPORT</a>
      &nbsp; |&nbsp; <a class="navblack" href="servlet/SummaryAction" >SUMMARY</a>
      </p>
      <%} else { %>
      <p align="center">
      &nbsp; <a class="navblack" href="" >SETTINGS</a>
      &nbsp; |&nbsp; <a class="navblack" href="" >ADD RECORDS</a>
      &nbsp; |&nbsp; <a class="navblack" href="" >CORRECT</a>
      &nbsp; |&nbsp; <a class="navblack" href="" >REPORT</a>
      &nbsp; |&nbsp; <a class="navblack" href="" >SUMMARY</a>
      </p>
      <%} %>
      </td></tr>
  <tr>
    <td bgcolor="#ffffff">
     </td></tr>
  <tr>
    <% if(GMCDBApp.checkDB()) {%>
     <td align=center style="font-size:25;color:lightgreen"> Database connection established.</td>
     <% } else { %>
     <td align=center style="font-size:25;color:red">Error!!! Database connection failed. Application will not work.</td>
     <% } %>
    </tr> 
      <tr><td ></td></tr>
 </table>
</body>

</html>