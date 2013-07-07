<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 3.2//EN">
<%
	String cmptitle = request.getParameter("c");
	String date = request.getParameter("d");
	String today = request.getParameter("t");
	String frmdate = request.getParameter("fd");
	String todate = request.getParameter("td");
	String ac = request.getParameter("a");
	String msg = (String)request.getAttribute("msg");
	boolean isAll = true;
	boolean isGMC = false; 
	if(cmptitle == null || cmptitle.equalsIgnoreCase(GMCCostants.GMC))
	{
		isGMC = true;
	}
	
	if (frmdate == null) {frmdate = today;}
	if (todate == null) {todate = today;}
	
	if(ac == null) {ac="";}
	if(msg == null) {msg="";}
%> 

<%@page import="java.util.*,com.app.gmc.common.GMCCostants,com.app.gmc.common.GMCHelper,com.app.gmc.db.*"%>

<% 
	Map<Date, Double> dtMap = new LinkedHashMap<Date, Double>(0);
	Map<String, Double> totals = new LinkedHashMap<String, Double>(0);
	
	if (ac.equals("all"))
	{
		dtMap = (LinkedHashMap<Date, Double>)GMCDBApp.getAllDates(cmptitle); 
		totals = (LinkedHashMap<String, Double>)GMCDBApp.getAllTotals(cmptitle);
	}
	else if (ac.equals("range"))
	{ 
		isAll = false;
		if (frmdate != null && todate != null)
		{
			Date start = GMCHelper.getInstance().conv2SqlDate(frmdate);
			Date end = GMCHelper.getInstance().conv2SqlDate(todate);
			dtMap = (LinkedHashMap<Date, Double>)GMCDBApp.getByDateRange(cmptitle, start,end);
			totals = (LinkedHashMap<String, Double>)GMCDBApp.getTotalsByDateRange(cmptitle, start,end);
		}
	} 
	if (!ac.equals("search") && !ac.equals("null") && dtMap.size() == 0)
	{
		msg = "No entries found";	
	}
%>
 
<%@page import="java.sql.Date"%>
<html>
<head>
<script language="JavaScript">
function apply(str)
{
	document.getElementById("act").value = str; 
	document.getElementById("frmdate").value = document.getElementById("MyDate1").value;
	document.getElementById("todate").value = document.getElementById("MyDate2").value;
	document.frmcmp.submit();
}

function showReport(seldate)
{ 
	document.getElementById("selDate").value = seldate;
	document.frmreport.submit();
}	
 
function deleteDate(seldate)
{
	if(!confirm("ATTENTION! All entries for "+seldate+" will be deleted. Continue? "))
	{
		return false;
	}
	document.getElementById("delDate").value = seldate;
	document.frmdelete.submit();
}

<!--
var months = new Array("January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December");
var daysInMonth = new Array(31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31);
var days = new Array("S", "M", "T", "W", "T", "F", "S");

today = new getToday();	
var element_id;
var el;
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
	var parseYear = parseInt(document.all.year  [document.all.year.selectedIndex].text);
 
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
    			 if (el == "1")
    			 {	document.getElementById("MyDate1").value=val+"-"+mn +"-"  +Year;}
    			 else 
				 { document.all[element_id].value=val+"-"+mn +"-"  +Year;}
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

function toggleCalendar(elem_name, no)
{
	el = no;
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
<p/>
<table cellspacing="1" cellpadding="3" width="780" align="center" bgcolor="#004080" border="0">
  
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
      &nbsp; |&nbsp; <a class="navblack" href="#" style="color:white"><%= GMCCostants.M_SUMMARY %></a>
      </p></td></tr>
        <tr>
    <td bgcolor="#ffffff">
    <div align=center>
	    <form name="frmcmp" action = "SummaryAction" method="post">
		<input type="hidden" name="frmdate" id="frmdate"/>
		<input type="hidden" name="todate" id="todate"/>
	    <input type=hidden name="act" id="act"/>
	    <input type="hidden" name="MyDate" id="MyDate" value= <%= date %>/>
	    <br>
	    <center>
	     <table border=1 cellspacing =0 cellpadding=5 width= 50% align=center style="border-color:black;border-style:solid">
	    <tr> 
	    	<th width=10%>Company </th>
	    	<td width=40% colspan=2>
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
		 	<th width=10%>Listing </th>	
		 	<td width =25% nowrap>
		 	<% if (isAll) { %> 
					<input type=radio name=rec id=rec value=all checked onclick="document.getElementById('dtrg').style.display='none'" checked>All
					<input type=radio name=rec id=rec value=range onclick="document.getElementById('dtrg').style.display='block'">Range
				 <% } else { %>
				 	<input type=radio name=rec id=rec value=all checked onclick="document.getElementById('dtrg').style.display='none'" >All
					<input type=radio name=rec id=rec value=range onclick="document.getElementById('dtrg').style.display='block'" checked>Range
				 <% } %>
			</td>
			<% if (isAll) { %> 
		 		<td  id=dtrg style="display:none">
		 	<%   } else { %>
		 		<td id=dtrg style="display:block">
		 	<% } %>  
		 	<div align="left" width="100%">
					<div style="white-space:nowrap;">From: <input id=MyDate1 name=MyDate1 size=15 readonly value=<%= frmdate %>> 
					<a href="JavaScript:;" onClick="toggleCalendar('MyDate2', '1')">Calendar</a></div>
					<div style="white-space:nowrap;">To:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input id=MyDate2 name=MyDate2 size=15 readonly value=<%= todate %>> 
					<a href="JavaScript:;" onClick="toggleCalendar('MyDate2')">Calendar</a></div>
				<TABLE bgColor=#ffffff border=1 cellPadding=0 cellSpacing=3 id=calendar style="DISPLAY: none; POSITION: absolute; Z-INDEX: 4">
				  <TBODY>
				  <TR>
				    <TD colSpan=7 vAlign=middle>
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
				
				
				  <TBODY class=dates id=dayList onclick="getDate(event)" vAlign=middle>
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
			<input type="button" onclick="apply('search')" name="sch" id="sch" value= "Search" size=10 style="width:150px;height:40px;">
		</center>
		</form>
     </div>
     </td>
  </tr>
  
    <% if  (totals != null && totals.size() > 0 ) { %>
  <tr bgcolor="#ffffff">
  <td align=center>
  <!-- start dates table -->
  <table cellspacing=1 cellpadding=2>
  <%-- <tr> <td align=center bgcolor="#99ccff" style="font-size:18"> <%= GMCCostants.FIELD1 %> </td> <td align=right style="font-size:18" > <%= GMCHelper.getInstance().formatDouble(totals.get("S125")) %> </td></tr>
  <tr> <td align=center bgcolor="#99ccff" style="font-size:18"> <%= GMCCostants.FIELD11 %> </td> <td align=right style="font-size:18" > <%= GMCHelper.getInstance().formatDouble(totals.get("ST125")) %> </td></tr>
  <tr> <td align=center bgcolor="#99ccff" style="font-size:18"> <%= GMCCostants.FIELD3 %> </td> <td align=right style="font-size:18" > <%= GMCHelper.getInstance().formatDouble(totals.get("S4")) %> </td></tr>
  <tr> <td align=center bgcolor="#99ccff" style="font-size:18"> <%= GMCCostants.FIELD13 %> </td> <td align=right style="font-size:18" > <%= GMCHelper.getInstance().formatDouble(totals.get("ST4")) %> </td></tr>
 --%>
 <tr> <td align=center bgcolor="#99ccff" style="font-size:18"> <%= GMCCostants.NEWFIELD1 %> </td> <td align=right style="font-size:18" > <%= GMCHelper.getInstance().formatDouble(totals.get("S145")) %> </td></tr>
  <tr> <td align=center bgcolor="#99ccff" style="font-size:18"> <%= GMCCostants.NEWFIELD11 %> </td> <td align=right style="font-size:18" > <%= GMCHelper.getInstance().formatDouble(totals.get("ST145")) %> </td></tr>
  <tr> <td align=center bgcolor="#99ccff" style="font-size:18"> <%= GMCCostants.NEWFIELD3 %> </td> <td align=right style="font-size:18" > <%= GMCHelper.getInstance().formatDouble(totals.get("S5")) %> </td></tr>
  <tr> <td align=center bgcolor="#99ccff" style="font-size:18"> <%= GMCCostants.NEWFIELD13 %> </td> <td align=right style="font-size:18" > <%= GMCHelper.getInstance().formatDouble(totals.get("ST5")) %> </td></tr>
 
  <tr> <td align=center bgcolor="#99ccff" style="font-size:18"> <%= GMCCostants.FIELD8 %> </td> <td align=right style="font-size:18" > <%= GMCHelper.getInstance().formatDouble(totals.get("NT")) %> </td></tr>
  <tr> <td align=center bgcolor="#99ccff" style="font-size:18"> <%= GMCCostants.TOTAL %> </td> <td align=right style="font-size:18" > <%= GMCHelper.getInstance().formatDouble(totals.get("TOT")) %> </td></tr>
  
  <% if (totals.get("TOT") > 0) { %>
  <tr> <td colspan=2 align=center><input type=button id=print name=print value=Print 
	 		onClick="document.frmprint.submit();" style="width:150px;height:40px;"/></td></tr>
  <% } %>	 		
  </table>
  </td></tr>
  <% } %>
  
  <% if  (dtMap != null && dtMap.size() > 0 ) { %>
  <tr bgcolor="#ffffff">
  <td align=center>
  <!-- start dates table -->
  <table cellspacing=1 cellpadding=2>
  	<tr bgcolor="#3399ff"><th>Date</th><th>Amount</th><td> | </td><th>Date</th><th>Amount</th><td> | </td><th>Date</th><th>Amount</th><td> | </td></tr>
  		<%	int i = -1;
  			for (Date dt: dtMap.keySet())
  			{
  				++i;
  				if (i % 3 == 0 ) {
  		%>
  			<tr style="font-size:10">
  		<% }%>
  		<td> <a href="#" onclick="showReport('<%= GMCHelper.getInstance().convDate2String(dt)  %>')" style="font-size:15"><%= GMCHelper.getInstance().convDate2String(dt) %> </a></td>
  		<td bgcolor="#ccffcc" style="font-weight:bold"> <%= GMCHelper.getInstance().formatDouble(dtMap.get(dt)) %> </td>
  		<td> <input type=button name=del id=del value=D onclick="deleteDate('<%= GMCHelper.getInstance().convDate2String(dt) %>')"/> </td>
  		<% if (i % 3 == 2 ) { %>
  		</tr>
  		<% } 
  			}
  		%>
	</table>
  </td>
  </tr>
 <% } %>
 	 <% if (!msg.equals("") ){%>
  	<tr>
  	<td>
  	 <center><font color="white" size="5"> <%= msg %> </font></center>
  	 </td>
  	</tr>
  <% } %>
  <tr>
  	<td></td>
  </tr>
  
  </table>
  
  
  
</body>
<form name="frmreport" action="ReportAction" method=post>
	  <input type="hidden" name="cmpsel" id="cmpsel" value= <%=cmptitle%>/>
	  <input type="hidden" name="selDate" id="selDate" />
	  <input type="hidden" name="act" id="act" value='report'/>
	  <input type="hidden" name="frmdate" id="frmdate" value= <%= frmdate %>/>
	  <input type="hidden" name="todate" id="todate" value= <%= todate %>/>
	  <input type="hidden" name="lst" id="lst" value= <%= ac %>/>
	  <input type="hidden" name="MyDate" id="MyDate" value= <%= date %>/>
</form>
<form name="frmdelete" action="SummaryAction" method=post>
	  <input type="hidden" name="cmpsel" id="cmpsel" value= <%=cmptitle%>/>
	  <input type="hidden" name="delDate" id="delDate" />
	  <input type="hidden" name="act" id="act" value='deletedate'/>
	  <input type="hidden" name="frmdate" id="frmdate" value= <%= frmdate %>/>
	  <input type="hidden" name="todate" id="todate" value= <%= todate %>/>
	  <input type="hidden" name="rec" id="rec" value= <%= ac %>/>
	  <input type="hidden" name="MyDate" id="MyDate" value= <%= date %>/>
</form>
<form name="frmprint" action="PrintAction" method=post>
<input type="hidden" name="cmpsel" id="cmpsel" value= <%= cmptitle %>/>
<input type="hidden" name="fdate" id="fdate" value= <%= frmdate %>/>
<input type="hidden" name="tdate" id="tdate" value= <%= todate %>/>
<input type="hidden" name="lst" id="lst" value= <%= ac %>/>
<input type="hidden" name="act" id="act" value=printsumm />
<input type="hidden" name="MyDate" id="MyDate" value= <%= date %>/>
</form>
<form name="frmhidden" action="" method=post>
<input type="hidden" name="cmpsel" id="cmpsel" value= <%= cmptitle %>/>
<input type="hidden" name="MyDate" id="MyDate" value= <%= date %>/>
</form>
</html>