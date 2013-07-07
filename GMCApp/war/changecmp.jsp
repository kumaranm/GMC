<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 3.2//EN">
<%
	String cmptitle = request.getParameter("c");
	String date = request.getParameter("d");
	String msg = (String)request.getAttribute("msg");
	boolean isGMC = false; 
	if(cmptitle == null || cmptitle.equalsIgnoreCase(GMCCostants.GMC))
	{
		isGMC = true; 
	}
	if(msg == null) {msg="";}
%>
<%@page import="com.app.gmc.common.GMCCostants"%>
<html>
<head>  
<script language="JavaScript">
function apply(str)
{
	if(str == "deleteall") 	
	{
		if(!confirm("ATTENTION! All entries for selected company and date will be deleted. Continue? "))
		{
			return false;
		}
	}
	document.getElementById("act").value = str;
	document.frmcmp.submit();
}

function backupDB()
{
	if(document.getElementById("backupfile").value == "")
	{
		alert("Enter a backup file location to continue with backup");
		return false;
	}
	document.getElementById("act").value = "backup";
	document.getElementById("filepath").value = document.getElementById("backupfile").value;
	document.frmcmp.submit();
}

function restoreDB()
{
	alert("restore");
	document.getElementById("act").value = "restore";
	document.getElementById("filepath").value = document.getElementById("backupfile").value;
	document.frmcmp.submit();
}

<!--
var months = new Array("January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December");
var daysInMonth = new Array(31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31);
var days = new Array("S", "M", "T", "W", "T", "F", "S");

today = new getToday();	
var element_id;

function getDays(month, year) 
{
	// Test for leap year when February is selected.
	if (1 == month)
		return ((0 == year % 4) && (0 != (year % 100))) ||
			(0 == year % 400) ? 29 : 28;
	else
		return daysInMonth[month];
}

function getToday()
{
	// Generate today's date.
	this.now = new Date();
	this.year = this.now.getFullYear() ; // Returned year XXXX
	this.month = this.now.getMonth();
	this.day = this.now.getDate();
}

 
function newCalendar() 
{
	var parseYear = parseInt(document.all.year[document.all.year.selectedIndex].text);
 
	var newCal = new Date(parseYear , document.all.month.selectedIndex, 1);
	var day = -1;
	var startDay = newCal.getDay();
	var daily = 0; 

	today = new getToday(); // 1st call
	if ((today.year == newCal.getFullYear() ) &&   (today.month == newCal.getMonth()))
	   day = today.day;
	// Cache the calendar table's tBody section, dayList.
	var tableCal = document.all.calendar.tBodies.dayList;

	var intDaysInMonth =
	   getDays(newCal.getMonth(), newCal.getFullYear() );

	for (var intWeek = 0; intWeek < tableCal.rows.length;  intWeek++)
		   for (var intDay = 0;
			 intDay < tableCal.rows[intWeek].cells.length;
			 intDay++)
	 {
		  var cell = tableCal.rows[intWeek].cells[intDay];

		  // Start counting days.
		  if ((intDay == startDay) && (0 == daily))
			 daily = 1;

		  // Highlight the current day.
		  cell.style.color = (day == daily) ? "red" : "";
		  if(day == daily)
		  {
				document.all.todayday.innerText= "Today: " +  day + "-" + 
					(newCal.getMonth()+1) + "-" + newCal.getFullYear() ;
		  }
		  // Output the day number into the cell.
		  if ((daily > 0) && (daily <= intDaysInMonth))
			 cell.innerHTML = daily++;
		  else
			 cell.innerHTML = "";
	   }

}
	  
	 function getTodayDay()
	 {
			    document.all[element_id].value = today.day + "-" + (today.month+1) + 
					"-" + today.year; 
		        //document.all.calendar.style.visibility="hidden";
				document.all.calendar.style.display="none";
				document.all.year.selectedIndex =100;   
	            document.all.month.selectedIndex = today.month; 
	 }
 
        function getDate(event) 
		 {
            // This code executes when the user clicks on a day
            // in the calendar.
            var src="";
            var val;
            if(event.target)
            {
            	src = event.target.nodeName;
            	val=event.target.innerHTML;
            }
            else if(event.srcElement)
            {
            	src = event.srcElement.tagName;
            	val = event.srcElement.innerHTML;
            }
            
            if ("TD" == src)
               // Test whether day is valid.
               if ("" != val)
			   { 
				 var mn = document.all.month.selectedIndex+1;
    			 var Year = document.all.year [document.all.year.selectedIndex].text;
				 document.all[element_id].value=val+"-"+mn +"-"  +Year;
		         //document.all.calendar.style.visibility="hidden";
				 document.all.calendar.style.display="none";
			 }
		 }
 
function GetBodyOffsetX(el_name, shift)
{
	var x; 
	var y;
	x = 0;
	y = 0;

	var elem = document.all[el_name];
	do 
	{
		x += elem.offsetLeft;
		y += elem.offsetTop;
		if (elem.tagName == "BODY")
			break;
		elem = elem.offsetParent; 
	} while  (1 > 0);

	shift[0] = x;
	shift[1] = y;
	return  x;
}	 

function SetCalendarOnElement(el_name)
{
	if (el_name=="") 
	el_name = element_id;
	var shift = new Array(2);
	GetBodyOffsetX(el_name, shift);
	document.all.calendar.style.pixelLeft  = shift[0]; //  - document.all.calendar.offsetLeft;
	document.all.calendar.style.pixelTop = shift[1] + 25 ;
}
	  
 	  
	           
function ShowCalendar(elem_name)
{
		if (elem_name=="")
		elem_name = element_id;

		element_id	= elem_name; // element_id is global variable
		newCalendar();
		SetCalendarOnElement(element_id);
		//document.all.calendar.style.visibility = "visible";
		document.all.calendar.style.display="inline";
}

function HideCalendar()
{
	//document.all.calendar.style.visibility="hidden";
	document.all.calendar.style.display="none";
}

function toggleCalendar(elem_name)
{
	if(document.all.calendar.style.display=="none")
		ShowCalendar(elem_name);
	else 
		HideCalendar();
}
-->
</script>

<style>
.today {COLOR: black; FONT-FAMILY: sans-serif; FONT-SIZE: 10pt; FONT-WEIGHT: bold}
.days {COLOR: navy; FONT-FAMILY: sans-serif; FONT-SIZE: 10pt; FONT-WEIGHT: bold; TEXT-ALIGN: center}
.dates {COLOR: black; FONT-FAMILY: sans-serif; FONT-SIZE: 10pt}
</style>
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
<body bgcolor="#ffffff" >
<p></p>
<table cellspacing="1" cellpadding="3" width="780" align="center" bgcolor="#004080" 
border="0">
  
  <tr>
    <td>
      <h1 align="center"><br><font color="#ffffff"><%= GMCCostants.TITLE %></font></h1>
      <p>&nbsp;</p></td></tr>
  <tr>
      <td bgcolor="#c0c0c0">
      <p align="center">
      &nbsp; <a class="navblack" href="#" style="color:white"><%= GMCCostants.M_SETTINGS %></a>
      &nbsp; |&nbsp; <a class="navblack" href="#" ><%= GMCCostants.M_ADD %></a>
      &nbsp; |&nbsp; <a class="navblack" href="#" ><%= GMCCostants.M_LIST %></a>
      &nbsp; |&nbsp; <a class="navblack" href="#" ><%= GMCCostants.M_REPORT %></a>
      &nbsp; |&nbsp; <a class="navblack" href="#" ><%= GMCCostants.M_SUMMARY %></a>
      </p></td></tr>
  <tr>
    <td bgcolor="#ffffff">
    <div align=center>
	    <form name="frmcmp" action = "MainAction" method="post">
	    <input type=hidden name="act" id="act"/>
	    <input type=hidden name="filepath" id="filepath"/>
	    <br>
	    <center>
	    <table border=1 cellspacing =0 cellpadding=5 width= 50% align=center style="border-color:black;border-style:solid">
	    <tr> 
	    	<th width=10%>Company </th>
	    	<td width=40%>
	    	<font size=3>
	    			<% if (isGMC) { %> 
					<input type="radio" id ="cmpsel" name = "cmpsel" value="GMC" checked> <%= GMCCostants.XGMC %>
					<br>
					<input type="radio" id ="cmpsel" name = "cmpsel" value="SKC" > <%= GMCCostants.XSKC %>
					<br>
				 <% } else { %>
				 	<input type="radio" id ="cmpsel" name = "cmpsel" value="GMC" > <%= GMCCostants.XGMC %>
					<br>
					<input type="radio" id ="cmpsel" name = "cmpsel" value="SKC" checked> <%= GMCCostants.XSKC %>
					<br>
				 <% } %>
				 </font>
		 	 </td>	
		 	 </tr>
		 <tr>
		 	<th width=10%>Date </th>	
		 	<td width=40%> 
		 	<div align=left>
					<input id=MyDate name=MyDate size=15 readonly value=<%= date %>> 
					<a href="JavaScript:;" onClick="toggleCalendar('MyDate')">Calendar</a>
				<TABLE bgColor=#ffffff border=1 cellPadding=0 cellSpacing=3 id=calendar style="DISPLAY: none; POSITION: absolute; Z-INDEX: 4">
				  <TBODY>
				  <TR>
				    <TD colSpan=7 vAlign=center>
					<!-- Month combo box -->
					<SELECT id=month onchange=newCalendar()> 
				    	<SCRIPT language=JavaScript>
						// Output months into the document.
						// Select current month.
						for (var intLoop = 0; intLoop < months.length; intLoop++)
							document.write("<OPTION " +	(today.month == intLoop ? "Selected" : "") + ">" + months[intLoop]);
						</SCRIPT>
					</SELECT> 
					<!-- Year combo box -->
					<SELECT id=year onchange=newCalendar()> 
				        <SCRIPT language=JavaScript>
						// Output years into the document.
						// Select current year.
						for (var intLoop = 2011; intLoop < 2040; intLoop++)
							document.write("<OPTION " + (today.year == intLoop ? "Selected" : "") + ">" + intLoop);
						</SCRIPT>
					</SELECT> 
				
					</TD>
				  </TR>
				
				
					
				  <TR class=days>
					<!-- Generate column for each day. -->
				    <SCRIPT language=JavaScript>
					// Output days.
					for (var intLoop = 0; intLoop < days.length; intLoop++)
						document.write("<TD>" + days[intLoop] + "</TD>");
					</SCRIPT>
				  </TR>
				
				
				  <TBODY class=dates id=dayList onclick="getDate(event)" vAlign=center>
				  <!-- Generate grid for individual days. -->
				  <SCRIPT language=JavaScript>
					for (var intWeeks = 0; intWeeks < 6; intWeeks++)
					{
						document.write("<TR>");
						for (var intDays = 0; intDays < days.length; intDays++)
							document.write("<TD></TD>");
						document.write("</TR>");
					}
				  </SCRIPT>
				
				  <!-- Generate today day. --></TBODY>
				  <TBODY>
				  <TR>
				    <TD class=today colSpan=5 id=todayday onclick=getTodayDay()></TD>
				    <TD align=right colSpan=2><A href="javascript:HideCalendar();"><SPAN style="COLOR: black; FONT-SIZE: 10px"><B>Hide</B></SPAN></A></TD>
				  </TR>
				  </TBODY>
				
				</TABLE>
		     </div>
		 	</td>
		 </tr>	 
	    </table>
	    </center>
	    <br>
		<center>
			<input type="button" onclick="apply('change')" name="app" id="app" value= "Apply" size=10 style="width:150px;height:40px;">
			<input type="button" onclick="apply('deleteall')" name="del" id="del" value= "Delete All" size=10 style="width:100px;height:30px;">
		</center>
		</form>
     </div>
     </td>
     </tr>
     <tr>
     <td bgcolor="#ffffff" align=center>
     <br>
     	<div style="font-size:15">Backup file path:<input type=text name=backupfile id=backupfile size="50" value="S:\GMCApp_Data_Backup\"/>
     	<input type="button" onclick="backupDB()" name="backup" id="backup" value= "Backup" size=10 style="width:125px;height:30px;"/>
     	<BR>
     	</div>
     </td>
     <tr>
    <% if(msg.equals("true")) {%>
     <td align=center style="font-size:25;color:lightgreen"> You are safe...Database backed up.</td>
     <% } else if(msg.equals("false")) { %>
     <td align=center style="font-size:25;color:red">Failure!!! Database backup failed.</td>
     <% } %>
    </tr>
  <tr>
  <td ></td></tr></table>
</body>
</html>