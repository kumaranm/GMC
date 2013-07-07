<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 3.2//EN">
<%
	String cmptitle = request.getParameter("c");
	String date = request.getParameter("d");
	String company = "";
	if(cmptitle.equals(GMCCostants.GMC))
	{
		company = GMCCostants.XGMC;	
	}
	else 
	{
		company = GMCCostants.XSKC;	
	} 
	String frmdate = request.getParameter("fd");
	String todate = request.getParameter("td");
	String lst = request.getParameter("l");
%>  

<%@page import="java.util.*,com.app.gmc.common.GMCCostants,com.app.gmc.common.GMCHelper, com.app.gmc.db.*"%>

<% 
	ArrayList<GMCDBVo> datas = (ArrayList<GMCDBVo>)GMCDBApp.getAllRecords(cmptitle, GMCHelper.getInstance().conv2SqlDate(date));
%> 
<html>
<head>
<script type="text/javascript">
function print()
{
}
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
      &nbsp; |&nbsp; <a class="navblack" href="#" 
      	onclick="document.frmhidden.action='ListingAction';document.frmhidden.submit();"><%= GMCCostants.M_LIST %></a>
      &nbsp; |&nbsp; <a class="navblack" href="#" style="color:white"><%= GMCCostants.M_REPORT %></a>
      &nbsp; |&nbsp; <a class="navblack" href="#" 
      	onclick="document.frmsummary.submit();"><%= GMCCostants.M_SUMMARY %></a>
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
     <tr> <td bgcolor="#ffffff" width=100%> 
     <!-- add table -->
	
     <%
	 		if (datas != null)
	 		{
	 			double grandTotal = 0;
	 			//double sales125total = 0, tax125total =0, sales4total = 0, tax4total =0 ;
	 			double sales145total = 0, tax145total =0, sales5total = 0, tax5total =0;
	 			double ntastotal=0;
	 			
	 			int slno=1;
	 	%>
	 	 <div id=all  style="display:block">
		  <table border=0 cellspacing=3 cellpadding=3 width="100%">
	 		<tr bgcolor="#3399ff" style="color:#ffffff">
		 		<th width="3%"> <%= GMCCostants.SERIAL_NUMBER %> </th>
				<th width="4%"> <%= GMCCostants.BOOK_NUMBER %> </th>
				<th width="4%"> <%= GMCCostants.BILL_NUMBER %> </th>
				<%-- <th width="10%"> <%= GMCCostants.FIELD1 %> </th>
				<th width="10%"> <%= GMCCostants.FIELD11 %> </th>
				<th width="10%"> <%= GMCCostants.FIELD3 %> </th>
				<th width="10%"> <%= GMCCostants.FIELD13 %> </th> --%>
				<!-- CODE for 5% and 14.5% -->
				<th width="10%"> <%= GMCCostants.NEWFIELD1 %> </th>
				<th width="10%"> <%= GMCCostants.NEWFIELD11 %> </th>
				<th width="10%"> <%= GMCCostants.NEWFIELD3 %> </th>
				<th width="10%"> <%= GMCCostants.NEWFIELD13 %> </th>
				
				<th width="10%"> <%= GMCCostants.FIELD8 %> </th>
				<th width="10%"> <%= GMCCostants.TOTAL %> </th>
			</tr>
	 	<%		
	 			for (GMCDBVo vo:datas)
		 		{
	 				if (vo != null) {
			 		String clr = "";
			 		if (slno%2==0) { clr="#ccffff";} else { clr="#ffffff";}
			 		grandTotal = grandTotal + vo.getTotal();
			 		
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
			<td width="3%" align=center> <%= slno %> </td>
			<td width="4%" align=center> <input type=text size=6 name=bkno id=bkno value=<%= vo.getBookNumber() %> readonly style="border: 0;text-align:center;background-color:<%= clr %>"> </td>
			<td width="4%" align=center> <input type=text size=6 name=blno id=blno value=<%= vo.getBillNumber() %> readonly style="border: 0;text-align:center;background-color:<%= clr %>"> </td>
		<%-- 	<td  align=right> <input type=text size=8 value=<%= GMCHelper.getInstance().formatDouble(vo.getSales125()) %> readonly style="border: 0;text-align:right;background-color:<%= clr %>"> </td>
			<td  align=right> <input type=text size=8 value=<%= GMCHelper.getInstance().formatDouble(vo.getTax125()) %> readonly style="border: 0;text-align:right;background-color:<%= clr %>"> </td>
			<td  align=right> <input type=text size=8 value=<%= GMCHelper.getInstance().formatDouble(vo.getSales4()) %> readonly style="border: 0;text-align:right;background-color:<%= clr %>"></td>
			<td  align=right> <input type=text size=8 value=<%= GMCHelper.getInstance().formatDouble(vo.getTax4()) %> readonly style="border: 0;text-align:right;background-color:<%= clr %>"></td>
		--%>	
			<td  align=right> <input type=text size=8 value=<%= GMCHelper.getInstance().formatDouble(vo.getSales145()) %> readonly style="border: 0;text-align:right;background-color:<%= clr %>"> </td>
			<td  align=right> <input type=text size=8 value=<%= GMCHelper.getInstance().formatDouble(vo.getTax145()) %> readonly style="border: 0;text-align:right;background-color:<%= clr %>"> </td>
			<td  align=right> <input type=text size=8 value=<%= GMCHelper.getInstance().formatDouble(vo.getSales5()) %> readonly style="border: 0;text-align:right;background-color:<%= clr %>"></td>
			<td  align=right> <input type=text size=8 value=<%= GMCHelper.getInstance().formatDouble(vo.getTax5()) %> readonly style="border: 0;text-align:right;background-color:<%= clr %>"></td>
			
			<td  align=right> <input type=text size=8 value=<%= GMCHelper.getInstance().formatDouble(vo.getNtas()) %> readonly style="border: 0;text-align:right;background-color:<%= clr %>"> </td>
			<td  align=right> <input type=text size=10 value=<%= GMCHelper.getInstance().formatDouble(vo.getTotal()) %> readonly style="border:0;text-align:right;background-color:<%= clr %>"> </td>
		</tr>	
	 	<%		
	 			++slno;
	 			}
		 		}
	 	%>
	 	<% if (datas.size() == 0) { %>
	 	<tr>
  			<td colspan=9>
	 	<center><font color="black" size="4"> No entries found </font></center>
		</td></tr> 	
	 		<% } %>
	 	<tr bgcolor="#99ccff"><th colspan=3 align=left style="font-size:20">GRAND TOTAL</th>
	<%-- 	<td ><input type=text size=8 readonly style="font-weight:bold;border:0;text-align:right;padding:0;background-color:#99ccff" value=<%= GMCHelper.getInstance().formatDouble(sales125total) %> ></td>
	 	<td ><input type=text size=8 readonly style="font-weight:bold;border:0;text-align:right;padding:0;background-color:#99ccff" value=<%= GMCHelper.getInstance().formatDouble(tax125total) %> ></td>
	 	<td ><input type=text size=8 readonly style="font-weight:bold;border:0;text-align:right;padding:0;background-color:#99ccff" value=<%= GMCHelper.getInstance().formatDouble(sales4total) %> ></td>
	 	<td ><input type=text size=8 readonly style="font-weight:bold;border:0;text-align:right;padding:0;background-color:#99ccff" value=<%= GMCHelper.getInstance().formatDouble(tax4total) %> ></td>
	 --%>	
	 	<td ><input type=text size=8 readonly style="font-weight:bold;border:0;text-align:right;padding:0;background-color:#99ccff" value=<%= GMCHelper.getInstance().formatDouble(sales145total) %> ></td>
	 	<td ><input type=text size=8 readonly style="font-weight:bold;border:0;text-align:right;padding:0;background-color:#99ccff" value=<%= GMCHelper.getInstance().formatDouble(tax145total) %> ></td>
	 	<td ><input type=text size=8 readonly style="font-weight:bold;border:0;text-align:right;padding:0;background-color:#99ccff" value=<%= GMCHelper.getInstance().formatDouble(sales5total) %> ></td>
	 	<td ><input type=text size=8 readonly style="font-weight:bold;border:0;text-align:right;padding:0;background-color:#99ccff" value=<%= GMCHelper.getInstance().formatDouble(tax5total) %> ></td>
	 	
	 	<td ><input type=text size=8 readonly style="font-weight:bold;border:0;text-align:right;padding:0;background-color:#99ccff" value=<%= GMCHelper.getInstance().formatDouble(ntastotal) %> ></td>
	 	<td ><input type=text size=10 readonly style="font-weight:bold;border:0;text-align:right;padding:0;background-color:#99ccff" value=<%= GMCHelper.getInstance().formatDouble(grandTotal) %> ></td></tr>
	 	</table>
	 	<% if (datas.size() > 0) { %>
	 	<center><input type=button id=print name=print value=Print 
	 		onClick="document.frmprint.submit();" style="width:150px;height:40px;"/></center>
	 		<% } %>
	 	</div >
	 	<%
	 		  }
	 	%>
	 	
    <!-- END  -->
	</td></tr> 
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
<form name="frmhidden" action="" method=post>
<input type="hidden" name="cmpsel" id="cmpsel" value= <%= cmptitle %>/>
<input type="hidden" name="MyDate" id="MyDate" value= <%= date %>/>
</form>
<form name="frmprint" action="PrintAction" method=post>
<input type="hidden" name="cmpsel" id="cmpsel" value= <%= cmptitle %>/>
<input type="hidden" name="MyDate" id="MyDate" value= <%= date %>/>
<input type="hidden" name="act" id="act" value=print />
</form>
<form name="frmsummary" action="SummaryAction" method=post>
	  <input type="hidden" name="cmpsel" id="cmpsel" value= <%=cmptitle%>/>
	  <input type="hidden" name="selDate" id="selDate" />
	  <input type="hidden" name="act" id="act" value= search/>
	  <input type="hidden" name="MyDate" id="MyDate" value= <%= date %>/>
	  <input type="hidden" name="frmdate" id="frmdate" value= <%= frmdate %>/>
	  <input type="hidden" name="todate" id="todate" value= <%= todate %>/>
	  <input type="hidden" name="rec" id="rec" value= <%= lst %>/>
</form>
</html>