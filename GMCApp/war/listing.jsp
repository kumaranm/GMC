<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 3.2//EN">
<%
	String cmptitle = request.getParameter("c");
	String date = request.getParameter("d");
	String company = "";
	String ubk = request.getParameter("bk");
	String ubl = request.getParameter("bl");
	if(cmptitle.equals(GMCCostants.GMC))
	{
		company = GMCCostants.XGMC;	
	}
	else 
	{
		company = GMCCostants.XSKC;	
	}
	String res = request.getParameter("r");
	String act = request.getParameter("a");
	if(res == null) { res="";}
	if(act == null) { act="";} 
%> 

<%@page import="java.util.*,com.app.gmc.common.GMCCostants,com.app.gmc.common.GMCHelper, com.app.gmc.db.*"%>

<% 
	ArrayList<GMCDBVo> datas = (ArrayList<GMCDBVo>)GMCDBApp.getAllRecords(cmptitle, GMCHelper.getInstance().conv2SqlDate(date));
	Map<String, ArrayList<GMCDBVo>> dataMap = new LinkedHashMap<String, ArrayList<GMCDBVo>>();
	for(GMCDBVo vo: datas)
	{
		String bk = vo.getBookNumber();
		if (dataMap.containsKey(bk))
		{
			ArrayList<GMCDBVo> temp = dataMap.get(bk);
			temp.add(vo);
			dataMap.put(bk,temp);
		}
		else
		{
			ArrayList<GMCDBVo> temp = new ArrayList<GMCDBVo>(0);
			temp.add(vo);
			dataMap.put(bk,temp);
		}
	}
%>

<html>
<head>
<script type="text/javascript">
function selectAll(id) 
{
	if(id == null || id == '') { return false;}
	document.getElementById(id).select();
}

function resetValue()
{
	var e = event || evt; // for trans-browser compatibility
	var id = e.srcElement.id;
	if (document.getElementById(id).value == '' || document.getElementById(id).value == '.')
	{
		document.getElementById(id).value = 0;
	}
}

function onlyNumbers(evt)
{
	var e = event || evt; // for trans-browser compatibility
	var charCode = e.which || e.keyCode;

	//check if decimal point
	if ( charCode == 46) {
		var parts = e.srcElement.value;
		var s = parts.indexOf(".");
		//if it contains . return 
		if (s != -1 ) { return false;}
		//else  continue
	}
	//check if disallowed character
	if (charCode > 31 && (charCode < 48 || charCode > 57) && charCode != 46)
		return false;

	//check if number < 999999.99
	if(checkNumber(e.srcElement.value,charCode))
	{
		return checkAccuracy(e.srcElement);
	}
	
	return false;

}
function checkNumber(str,code)
{
	//if only decimal point is entered
	if(str ==".") {return true;}

	var no = str * 1;
	//if number contains 6 digits allow for 7
	if (no <= 99999){return true;}
	
	//if number contains 7 digits allow only if decimal
	if (no <= 1000000)
	{
		if(code == 46)
		{
			//if decimal pt and none exist allow entry
			var s = str.indexOf(".");
			if (s == -1 ) { return true;}
		}
		else
		{
			//allow numberd after decimal pt
			var s = str.indexOf(".");
			if (s != -1 ) { return true;}
		}
	}
	return false;
}


function checkAccuracy(el)
{
	var str = el.value;
	var s = str.indexOf(".");
	//if no dot exists no validation required
	if (s == -1 ) { return true;}
	
	var decimals = str.split(".");
	if (decimals.length == 2)
	{
		if (decimals[0].length == 7 && doGetCaretPosition(el) < 8)
		{
			return false;
		}
		if (decimals[1].length == 2)
		{
			return false;
		}
	}
	return true;
}
 /*
   **  Returns the caret (cursor) position of the specified text field.
   **  Return value range is 0-oField.length.
   */
   function doGetCaretPosition (oField) {

     // Initialize
     var iCaretPos = 0;
 
     // IE Support
     if (document.selection) { 

       // Set focus on the element
       oField.focus ();
  
       // To get cursor position, get empty selection range
       var oSel = document.selection.createRange ();
  
       // Move selection start to 0 position
       oSel.moveStart ('character', -oField.value.length);
  
       // The caret position is selection length
       iCaretPos = oSel.text.length;
     }

     // Firefox support
     else if (oField.selectionStart || oField.selectionStart == '0')
       iCaretPos = oField.selectionStart;

     // Return results
     return (iCaretPos);
   }
function updateTotal()
{
	var tot = document.getElementById("f10");
	var tmp =0;
	//var f1 = document.getElementById("f1").value;
	//var f2 = document.getElementById("f3").value;
	//CODE for 5% and 14.5%
	var nf1 = document.getElementById("nf1").value;
	var nf2 = document.getElementById("nf3").value;
	
	var f3 = document.getElementById("f8").value;
	
	//if (f1 == ".") {f1=0;}
	//if (f2 == ".") {f2=0;}
	if (f3 == ".") {f3=0;}
	//CODE for 5% and 14.5%
	if (nf1 == ".") {nf1=0;}
	if (nf2 == ".") {nf2=0;}
	
	//tmp = tmp + (f1 * 1) ;
	//tmp = tmp + (f2 * 1) ;
	tmp = tmp + (f3 * 1) ;
	//CODE for 5% and 14.5%
	tmp = tmp + (nf1 * 1) ;
	tmp = tmp + (nf2 * 1) ;
	
	tot.value = (tmp * 1).toFixed(2);
}

function showUpdateTable(book, bill)
{
	var blno = bill * 1;
	var bkno = book;
	<% 
		for (String key:dataMap.keySet()) {
		ArrayList<GMCDBVo> lst = dataMap.get(key);
		for (GMCDBVo vo : lst)
		{
	%>		
	if (blno == <%= vo.getBillNumber() %> && bkno == '<%= vo.getBookNumber() %>')
	{
		document.getElementById("bkno").value = '<%= vo.getBookNumber() %>';
		document.getElementById("blno").value = <%= vo.getBillNumber() %>;
		<%--document.getElementById("f1").value = <%= (vo.getSales125() + vo.getTax125())  %>;
		document.getElementById("f3").value = <%= (vo.getSales4() + vo.getTax4()) %>; --%>
		//CODE for 5% and 14.5%
		document.getElementById("nf1").value = <%= (vo.getSales145() + vo.getTax145())  %>;
		document.getElementById("nf3").value = <%= (vo.getSales5() + vo.getTax5()) %>;
		
		document.getElementById("f8").value = <%= vo.getNtas() %>;
		document.getElementById("f10").value = <%= vo.getTotal() %>;
	}	
	<% } }%>
	showRecords("delrec");
	document.getElementById("oldbookno").value = book;
	document.getElementById("oldbillno").value = bill;
	document.getElementById("nf1").focus();
	document.getElementById("nf1").select();
}


function showReport(seldate)
{ 
	document.getElementById("selDate").value = seldate;
	document.frmreport.submit();
}

function showRecords(str)
{
	document.getElementById("msgtd").innerHTML = '';
	document.getElementById("delrec").style.display = 'none';
	<%for (String key:dataMap.keySet())
	{
	%>
		document.getElementById('<%= key %>').style.display = 'none';
	<%	
	}
	%>
	document.getElementById(str).style.display = 'block';
}
function updateRecord()
{
	var total = document.getElementById("f10").value;
	if (total == "" || total == 'NaN' )
	{
		alert("Invalid total");
		document.getElementById("f10").focus();
		return false;
	}	
	document.frmupdate.submit();
}
function deleteRecord(bk,bl)
{
	if(!confirm("Delete entry? "+bk+"/"+bl))
	{
		return false;
	}
	document.getElementById("bookno").value = bk;
	document.getElementById("billno").value = bl;
	document.frmdelete.submit();
}
function deleteRecords(bk)
{
	var dt = document.getElementById("MyDate").value;
	if(!confirm("Delete all entries on "+ dt+"Book No "+bk+" ?"))
	{
		return false;
	}
	document.getElementById("dbookno").value = bk;
	document.frmdeleteall.submit();
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
      &nbsp; <a class="navblack" href="#" 
      		onclick="document.frmhidden.action='ChangeCompanyAction';document.frmhidden.submit();"><%= GMCCostants.M_SETTINGS %></a>
      &nbsp; |&nbsp; <a class="navblack" href="#" 
			onclick="document.frmhidden.action='AddAction';document.frmhidden.submit();"><%= GMCCostants.M_ADD %></a>
      &nbsp; |&nbsp; <a class="navblack" href="#" style="color:white"><%= GMCCostants.M_LIST %></a>
      &nbsp; |&nbsp; <a class="navblack" href="#" 
     	 onclick="document.frmhidden.action='ReportAction';document.frmhidden.submit();"><%= GMCCostants.M_REPORT %></a>
      &nbsp; |&nbsp; <a class="navblack" href="#"
      	onclick="document.frmhidden.action='SummaryAction';document.frmhidden.submit();" ><%= GMCCostants.M_SUMMARY %></a>
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
     <!-- START -->
     <tr> <td bgcolor="#ffffff"> 
     <!-- add table -->
	 <% if (dataMap.size() > 0)
		{
	 %>
	<table border=0 cellspacing=3 cellpadding=3 width=100%>
	 <!-- book numbers table -->
	
	 <tr><td align=left bgcolor="#fffDff" style="vertical-align: text-top">
		 <table cellspacing=1 cellpadding=1>
			 <tr><th style="font-size:20">BkNo</th></tr>
		 	<% 
		 		for (String key:dataMap.keySet())
		 		{
		 	%>
				<tr><td width="50px" align=top style="font-size:18"><a href="#" onclick="showRecords('<%= key %>')"> <%= key %> </a></td></tr>	 			
		 	<%		
		 		}
		 	%>
		 </table>
		 </td>
	 <!-- book numbers table -->
	 
	 <!-- bill numbers table -->
	 <td width=100% style="vertical-align: text-top">
	 <div id=delrec style="display:none">
	 <form name="frmupdate" action="ListingAction" method="post">
	  <input type="hidden" name="cmpsel" id="cmpsel" value= <%=cmptitle%>/>
	  <input type="hidden" name="MyDate" id="MyDate" value= <%= date %>/>
	  <input type="hidden" name="oldbookno" id="oldbookno" />
	  <input type="hidden" name="oldbillno" id="oldbillno" />
	  <input type="hidden" name="act" id="act" value=update/>
	 <table border=0 cellspacing=3 cellpadding=3 width="100%" >
	 	<tr bgcolor="#3399ff" style="color:#ffffff">
	 		<th width="3%"> <%= GMCCostants.SERIAL_NUMBER %> </th>
	 		<th width="4%"> <%= GMCCostants.DATE %> </th>
			<th width="4%"> <%= GMCCostants.BOOK_NUMBER %> </th>
			<th width="4%"> <%= GMCCostants.BILL_NUMBER %> </th>
			
			<th width="10%"> <%= GMCCostants.NEWFIELD1 %> </th>  <!-- 14.5% --> 
			<th width="10%"> <%= GMCCostants.NEWFIELD3 %> </th> <!-- 5% -->
			
			<th width="10%"> <%= GMCCostants.FIELD8 %> </th>
			<th width="10%"> <%= GMCCostants.TOTAL %> </th>
			<th width="10%"> ACTION </th>
		</tr>
		<tr>
			<td width="3%" align=center style="font-size:18"> 1 </td>
			<td>
				<div align=left>
					<input id=MyDateU name=MyDateU size=15 readonly value=<%= date %>> 
					<a href="JavaScript:;" onClick="toggleCalendar('MyDateU')">Calendar</a>
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
			<td width="4%" align=center> <input type=text size=6 name=bkno id=bkno value=0 style="text-align:center" maxlength="3"> </td>
			<td width="4%" align=center> <input type=text size=6 name=blno id=blno value=0 style="text-align:center" onKeyPress="return onlyNumbers();" maxlength="3"> </td>
			<td  align=right> <input type=text size=6 name=nf1 id=nf1 value=0 onKeyPress="return onlyNumbers();" onkeyup="updateTotal()" onfocus="selectAll('nf1');" onblur="resetValue();updateTotal()" maxlength="10" style="text-align:right;"> </td>
			<td  align=right> <input type=text size=6 name=nf3 id=nf3 value=0 onKeyPress="return onlyNumbers();" onkeyup="updateTotal()" onfocus="selectAll('nf3');" onblur="resetValue();updateTotal()" maxlength="10" style="text-align:right;"></td>
			
			<td  align=right> <input type=text size=6 name=f8 id=f8 value=0 onKeyPress="return onlyNumbers();" onkeyup="updateTotal()" onfocus="selectAll('f8');" onblur="resetValue();updateTotal();selectAll('updbtn')" maxlength="10" style="text-align:right;"> </td>
			<td  align=right> <input type=text size=9 name=f10 id=f10 value=0 readonly style="font-size:20;border:0;text-align:right;" tabindex=-1> </td>
			<td  align=right> <input type=button size=30 name=updbtn id=updbtn value=Update onclick="updateRecord()" style="width:150px;height:40px;"> </td>
		</tr>	
		</table>
	</form>	
	 </div>
     <%
	 		if (datas != null)
	 		{
	 			double grandTotal = 0;
	 			for (String key:dataMap.keySet())
		 		{
	 				ArrayList<GMCDBVo> temp = dataMap.get(key);
	 				if (temp != null) {
	 	%>
	 	
	  <div id=<%= key %>  style="display:none">
	  <table border=0 cellspacing=3 cellpadding=3 width="100%" >
	 	<tr bgcolor="#3399ff" style="color:#ffffff">
	 		<th width="5%"> <%= GMCCostants.SERIAL_NUMBER %> </th>
			<th width="5%"> <%= GMCCostants.BOOK_NUMBER %> </th>
			<th width="5%"> <%= GMCCostants.BILL_NUMBER %> </th>
			<th width="10%"> <%= GMCCostants.NEWFIELD1 %> </th> <!-- 14.5% -->
			<th width="10%"> <%= GMCCostants.NEWFIELD11 %> </th> <!-- 14.5% tax -->
			<th width="10%"> <%= GMCCostants.NEWFIELD3 %> </th> <!-- 5% -->
			<th width="10%"> <%= GMCCostants.NEWFIELD13 %> </th> <!-- 5% tax-->
			
			<th width="10%"> <%= GMCCostants.FIELD8 %> </th>
			<th width="10%"> <%= GMCCostants.TOTAL %> </th>
			<th width="10%"> ACTION </th>
		</tr>
	 	<%
	 		double tot = 0;
	 		double sales125total = 0, tax125total =0, sales145total = 0, tax145total =0;
			double sales4total = 0, tax4total =0, sales5total = 0, tax5total =0;
			double ntastotal=0;
	 		int slno=1;
	 		String clr = "";
	 		for (GMCDBVo vo:temp)
			{
	 			if (slno%2==0) { clr="#ccffff";} else { clr="#ffffff";}
	 			tot = tot + vo.getTotal();
	 			//individual totals
		 		//sales125total = sales125total + vo.getSales125();
		 		//tax125total = tax125total + vo.getTax125();
		 		//sales4total = sales4total + vo.getSales4();
		 		//tax4total = tax4total + vo.getTax4();
		 		//CODE for 5% and 14.5%
		 		sales145total = sales145total + vo.getSales145();
		 		tax145total = tax145total + vo.getTax145();
		 		sales5total = sales5total + vo.getSales5();
		 		tax5total = tax5total + vo.getTax5();
		 		
		 		ntastotal = ntastotal + vo.getNtas();
	 	%>
		<tr bgcolor="<%= clr %>">
			<td width="5%" align=center style="font-size:18"> <%= slno %> </td>
			<td width="5%" align=center> <input type=text size=6 readonly style="border: 0;text-align:center;background-color:<%= clr %>" value=<%= vo.getBookNumber() %> > </td>
			<td width="5%" align=center > <a href="#" onclick="showUpdateTable('<%= vo.getBookNumber() %>','<%= vo.getBillNumber() %>')" style="font-size:15"> <%= vo.getBillNumber() %> </a> </td>
			<td align=right> <input type=text size=7 readonly style="border:0;text-align:right;background-color:<%= clr %>" value=<%= GMCHelper.getInstance().formatDouble(vo.getSales145()) %> > </td>
			<td align=right> <input type=text size=7 readonly style="border:0;text-align:right;background-color:<%= clr %>" value=<%= GMCHelper.getInstance().formatDouble(vo.getTax145()) %> > </td>
			<td align=right> <input type=text size=7 readonly style="border:0;text-align:right;background-color:<%= clr %>" value=<%= GMCHelper.getInstance().formatDouble(vo.getSales5()) %> > </td>
			<td align=right> <input type=text size=7 readonly style="border:0;text-align:right;background-color:<%= clr %>" value=<%= GMCHelper.getInstance().formatDouble(vo.getTax5()) %> > </td>
			
			<td align=right> <input type=text size=7 readonly style="border:0;text-align:right;background-color:<%= clr %>" value=<%= GMCHelper.getInstance().formatDouble(vo.getNtas()) %> > </td>
			<td align=right> <input type=text size=9 readonly style="border:0;text-align:right;background-color:<%= clr %>" value=<%= GMCHelper.getInstance().formatDouble(vo.getTotal()) %> >  </td>
			<td align=right> <input type=button size=30 name=delbtn id=delbtn value=Delete 
				onclick="deleteRecord('<%= vo.getBookNumber() %>','<%= vo.getBillNumber() %>')" >  </td>
		</tr>	
	 	<%	
	 			++slno;
	 			}
	 		grandTotal = grandTotal + tot;
	 	%>
	 	<tr bgcolor="#99ccff"><th colspan=3 align=left style="font-size:20">TOTAL</th>
	 	<td colspan=1><input type=text size=8 readonly style="font-weight:bold;border:0;text-align:right;padding:0;background-color:#99ccff" value=<%= GMCHelper.getInstance().formatDouble(sales145total) %> ></td>
	 	<td colspan=1><input type=text size=8 readonly style="font-weight:bold;border:0;text-align:right;padding:0;background-color:#99ccff" value=<%= GMCHelper.getInstance().formatDouble(tax145total) %> ></td>
	 	<td colspan=1><input type=text size=8 readonly style="font-weight:bold;border:0;text-align:right;padding:0;background-color:#99ccff" value=<%= GMCHelper.getInstance().formatDouble(sales4total) %> ></td>
	 	<td colspan=1><input type=text size=8 readonly style="font-weight:bold;border:0;text-align:right;padding:0;background-color:#99ccff" value=<%= GMCHelper.getInstance().formatDouble(tax4total) %> ></td>
	 	
	 	<td colspan=1><input type=text size=8 readonly style="font-weight:bold;border:0;text-align:right;padding:0;background-color:#99ccff" value=<%= GMCHelper.getInstance().formatDouble(ntastotal) %> ></td>
	 	<td colspan=1><input type=text size=10 readonly style="font-weight:bold;border:0;text-align:right;padding:0;background-color:#99ccff" value=<%= GMCHelper.getInstance().formatDouble(tot) %> ></td>
	 	<td > <input type=button size=30 name=delbtn id=delbtn value="Delete All" 
				onclick="deleteRecords('<%= key %>')" >  </td> 
		</tr>		
	 	</table>
	 	</div > 
	 	<%
	 		  }
	 		}
	 		}
	 	%>
	 	
	 </td></tr>
	 <!-- bill numbers table -->
	 </table>
	<%
		}
	 %>
    <!-- END  -->
	</td></tr>
	<tr>
  	<td colspan=7 align=center id=msgtd>
  	<% if (act.equals("update")) 
  		{
  			if (res.equals("true")) 
  			{
  		%>
  		<font color="white" size="5">Success ~ Entry Updated ~ <%= ubk %> / <%= ubl %></font>
  	<% } else if (res.equals("false")) { %>
  	<font color="red" size="5">Error ~ Entry Not Updated </font>
  	 <% }  else if (!res.equals("")){%>
		<font color="yellow" size="5">Entry Exists ~ <%= ubk %> / <%= ubl %> ~ <a href="#" onclick="showReport('<%= res  %>')"style="background-color:white"><%= res %> </a> </font>  	 
  	 <% } }
  		else if (act.equals("delete")) 
		{
			if (res.equals("true")) 
			{
  	 %>
	  	 <font color="white" size="5">Success ~ Entry Deleted ~ <%= ubk %> / <%= ubl %> </font>
	  	<% } else if (res.equals("false")) {
	  	  %>
	  	 <font color="red" size="5">Error ~ Entry Not Deleted ~ <%= ubk %> / <%= ubl %> </font>
  	 <% } } 
	  	 else if (act.equals("deleteall")) 
		{
			if (res.equals("true")) 
			{
  	 	%>
	  	 <font color="white" size="5">Success ~ All entries Deleted for Book No <%= ubk %></font>
	  	<% } else if (res.equals("false")) { %>
	  	<font color="red" size="5">Error ~ Entries Not Deleted for Book No <%= ubk %></font>
	  	 <% } }%>
  	 </td>
  </tr>
	<tr>
  <td></td></tr></table>
</body>
<form name="frmdelete" action="ListingAction" method=post>
	  <input type="hidden" name="cmpsel" id="cmpsel" value= <%=cmptitle%>/>
	  <input type="hidden" name="MyDate" id="MyDate" value= <%= date %>/>
	  <input type="hidden" name="bookno" id="bookno" />
	  <input type="hidden" name="billno" id="billno" />
	  <input type="hidden" name="act" id="act" value=delete />
</form>
<form name="frmdeleteall" action="ListingAction" method=post>
	  <input type="hidden" name="cmpsel" id="cmpsel" value= <%=cmptitle%>/>
	  <input type="hidden" name="MyDate" id="MyDate" value= <%= date %>/>
	  <input type="hidden" name="dbookno" id="dbookno" />
	  <input type="hidden" name="act" id="act" value=deleteall />
</form>
<form name="frmhidden" action="" method=post>
	<input type="hidden" name="cmpsel" id="cmpsel" value= <%= cmptitle %>/>
	<input type="hidden" name="MyDate" id="MyDate" value= <%= date %>/>
</form>
<form name="frmreport" action="ReportAction" method=post>
	  <input type="hidden" name="cmpsel" id="cmpsel" value= <%=cmptitle%>/>
	  <input type="hidden" name="selDate" id="selDate" />
	  <input type="hidden" name="act" id="act" value='report'/>
	  <input type="hidden" name="MyDate" id="MyDate" value= <%=date %>/>
</form>
</html>