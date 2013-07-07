<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 3.2//EN">
<%@page import="com.app.gmc.common.GMCHelper"%>
<%@page import="com.app.gmc.db.GMCDBApp"%>
<%
	String cmptitle = request.getParameter("c");
	String company = "";
	if(cmptitle.equals(GMCCostants.GMC))
	{
		company = GMCCostants.XGMC;	
	}
	else 
	{
		company = GMCCostants.XSKC;	
	}  
	String date = request.getParameter("d");
	String res = request.getParameter("r");
	String bk = request.getParameter("bk");
	String bl = request.getParameter("bl");
	if(res == null) { res="";}
	if(bk == null) { bk="";}
	if(bl == null ) { bl="1";}
	int obl = GMCHelper.getInstance().conv2Int(bl)-1;
	if(obl==0 )
	{
		obl=999;
	}
	int defval = 0;
%>
<%@page import="com.app.gmc.common.GMCCostants"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html;charset=ISO-8859-1" >
<title>Vithuvaravu</title>
<script>
function selectAll(id) 
{
	if(id == null || id == '') { return false;}
	document.getElementById(id).select();
}
function validateBillNo(evt)
{
	var e = event || evt; // for trans-browser compatibility
	var charCode = e.which || e.keyCode;
	//check if disallowed character
	if (charCode > 31 && (charCode < 48 || charCode > 57))
		return false;
	return true;
}

function showReport(seldate)
{ 
	document.getElementById("selDate").value = seldate;
	document.frmreport.submit();
}

function validateBookNo(evt)
{
	var e = event || evt; // for trans-browser compatibility
	var charCode = e.which || e.keyCode;
	
	//check if disallowed character
	if ((charCode >= 48 && charCode <= 57) 
		|| (charCode >= 65 && charCode <= 90)
		|| (charCode >= 97 && charCode <= 122)
		|| (charCode == 35 || charCode == 42)) 
		// #, *
		return true;
		
	return false;
}
function conv2Cap()
{
	var val = document.getElementById("bkno").value;
	document.getElementById("bkno").value = val.toUpperCase();
}

function resetValue(evt)
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
	var f3 = document.getElementById("f8").value;
	//CODE for 5% and 14.5%
	var nf1 = document.getElementById("nf1").value;
	var nf2 = document.getElementById("nf3").value;
	
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

function addSubmit()
{
	document.getElementById("msgtd").innerHTML = '';
	var bknumber = document.getElementById("bkno").value;
	var blnumber = document.getElementById("blno").value;
	var total = document.getElementById("f10").value;
	if (bknumber == "" || bknumber == 0 )
	{
		alert("Enter a valid Book Number");
		document.getElementById("bkno").focus();
		return false;
	}	
	if (blnumber == "" || blnumber == 0 )
	{
		alert("Enter a valid Bill Number");
		document.getElementById("blno").focus();
		return false;
	}	
	if (total == "" || total == 'NaN' )
	{
		alert("Invalid total");
		document.getElementById("f10").focus();
		return false;
	}	
	document.frmadd.submit();
}
function setFocus()
{
	if (document.getElementById("bkno").value == '') { document.getElementById("bkno").focus(); }
	else { document.getElementById("nf1").focus(); document.getElementById("nf1").select();}
}
</script>
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
<body bgcolor="#ffffff" onload="setFocus();">
<p></p>
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
      &nbsp; |&nbsp; <a class="navblack" href="#" style="color:white" ><%= GMCCostants.M_ADD %></a>
      &nbsp; |&nbsp; <a class="navblack" href="#"
      	onclick="document.frmhidden.action='ListingAction';document.frmhidden.submit();" ><%= GMCCostants.M_LIST %></a>
      &nbsp; |&nbsp; <a class="navblack" href="#" 
      	onclick="document.frmhidden.action='ReportAction';document.frmhidden.submit();"> <%= GMCCostants.M_REPORT %></a>
      &nbsp; |&nbsp; <a class="navblack" href="#" 
      	onclick="document.frmhidden.action='SummaryAction';document.frmhidden.submit();"><%= GMCCostants.M_SUMMARY %></a>
      </p></td></tr>
  <tr>
    <td bgcolor="#fffff2">
     <div align=center>
	    <h1>
	    <%= company %>
	    </h1>
	     <h2>
	    <%= date %>
		</h2>	    
     </div>
     </td></tr>
  <!-- add table -->
  <tr><td>
  <form name="frmadd" action="AddAction" method=post>
  <input type="hidden" name="cmpsel" id="cmpsel" value= <%=cmptitle%>/>
  <input type="hidden" name="MyDate" id="MyDate" value= <%= date %>/>
  <input type="hidden" name="act" id="act" value=add/>
  <table align="center" border=1 cellpadding=5 cellspacing=0 bgcolor="#ffffff" width=70% style="border-color:#004080;border-style:solid">
  <tr>
  	<td width=15% bgcolor="#004080" style="border:0"></td>
  	<th width=25% bgcolor="#3399ff" style="color:white;font-size:15"><%= GMCCostants.BOOK_NUMBER %> </th>
  	<td width=10%><input type=text size=10 name=bkno id=bkno maxlength="3" style="border:0" onkeyup="conv2Cap()" onKeyPress="return validateBookNo();" onfocus="selectAll('bkno')" value=<%= bk %>></td>
  	<td width=10% bgcolor="#004080"  style="border:0"> </td>
  </tr>
  <tr>
 	 <td width=15% bgcolor="#004080"  style="border:0"></td>
  	<th width=25% bgcolor="#3399ff" style="color:white;font-size:15"><%= GMCCostants.BILL_NUMBER %> </th>
  	<td width=10%><input type=text size=10 name=blno id=blno style="border:0"  onfocus="selectAll('blno')" onKeyPress="return validateBillNo();" maxlength="3"  value=<%= bl %>></td>
  	<td width=10% bgcolor="#004080"  style="border:0"> </td>
  </tr>
   <tr>
  	<td colspan=4 bgcolor="#004080"  style="border:0"></td>
  </tr>
  
  <!--  CODE for 5% and 14.5% -->
   <tr>
  	<td width=15% bgcolor="#004080"  style="border:0"></td>
  	<th width=25% bgcolor="#3399ff" style="color:white;font-size:15"><%= GMCCostants.NEWFIELD1 %> </th>
  	<td width=10%><input type=text size=10 name=nf1 id=nf1 style="border:0" value= <%= defval %> onKeyPress="return onlyNumbers();" onfocus="selectAll('nf1');" onkeyup="updateTotal()" onblur="resetValue();updateTotal()" maxlength="9" style="text-align:right;"></td>
  	<td width=10% bgcolor="#004080"  style="border:0"> </td>
  </tr>
  <tr>	
  	<td width=15% bgcolor="#004080"  style="border:0"></td>
  	<th width=25% bgcolor="#3399ff" style="color:white;font-size:15"><%= GMCCostants.NEWFIELD3 %></th> 
  	<td width=10% stye="border-right:0"><input type=text size=10 name=nf3 id=nf3 style="border:0" value= <%= defval %> onKeyPress="return onlyNumbers();"  onfocus="selectAll('nf3');" onkeyup="updateTotal()" onblur="resetValue();updateTotal()" maxlength="9" style="text-align:right;"></td>
  	<td width=10% bgcolor="#004080"  style="border:0"> </td>
  </tr>
  
  <tr>
  	<td width=15% bgcolor="#004080"  style="border:0"></td>
  	<th width=20% bgcolor="#3399ff" style="color:white;font-size:15"><%= GMCCostants.FIELD8 %> </th> 
  	<td width=10%><input type=text size=10 name=f8 id=f8 style="border:0" value= <%= defval %> onKeyPress="return onlyNumbers();" onfocus="selectAll('f8');" onkeyup="updateTotal()" onblur="resetValue();updateTotal();selectAll('addsbt')" maxlength="9" style="text-align:right;"></td>
  	<td width=10% bgcolor="#004080"  style="border:0"> </td>
  </tr>
  <tr>
  	<td width=15% bgcolor="#004080"  style="border:0"></td>
  	<th width=20% style="color:black;font-size:20" bgcolor=#99ccff><%= GMCCostants.TOTAL %> </th> 
  	<td width=10% colspan=1 bgcolor=#99ccff><input type=text size=12 name=f10 id=f10 readonly style="font-size=20;border:0;text-align:right;background-color:#99ccff" value= <%= defval %> tabindex=-1></td>
  	<td width=10% bgcolor="#004080"  style="border:0"> </td>
  </tr>
  <tr>
  	<td width=15% bgcolor="#004080"  style="border:0"></td>
  	<td colspan=2 align=center><input type=button id=addsbt name=addsbt onclick="addSubmit()" value=Save style="width:150px;height:40px;"></td>
  	<td width=10% bgcolor="#004080"  style="border:0"> </td>
  </tr>
 
  </table>
  </form>
  <!-- end -->
  </td></tr>
   <tr>
  	<td colspan=7 align=center id="msgtd">
  	<% if (res.equals("true")) {%>
  	<font color="white" size="5">Success ~ Entry Saved ~ <%= bk %> / <%= obl %></font>
  	<% } else if (res.equals("false")) { %>
  	<font color="red" size="5">Error ~ Entry Not Saved ~ Retry ~ <%= bk %> / <%= obl %></font>
  	<% } else if (!res.equals("")){ %>
  	<font color="yellow" size="5">Entry Exists ~ <%= bk %> / <%= obl %> ~ <a href="#" onclick="showReport('<%= res  %>')" style="background-color:white"><%= res %> </a> </font>
  	<% } %>
  	 </td>
  	</tr>
  <tr>
  <td ></td></tr></table>
</body>
<form name="frmhidden" action="" method=post>
	<input type="hidden" name="cmpsel" id="cmpsel" value= <%= cmptitle %>/>
	<input type="hidden" name="MyDate" id="MyDate" value= <%= date %>/>
</form>
<form name="frmreport" action="ReportAction" method=post>
	  <input type="hidden" name="cmpsel" id="cmpsel" value= <%=cmptitle%>/>
	  <input type="hidden" name="selDate" id="selDate" />
	  <input type="hidden" name="act" id="act" value='report'/>
	  <input type="hidden" name="MyDate" id="MyDate" value= <%= date %>/>
</form>
</html>