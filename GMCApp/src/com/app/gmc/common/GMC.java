/**
 * 
 */
package com.app.gmc.common;

/**
 * @author Kumaran
 *
 */
public interface GMC
{
	
	public static final String _AMP = "&";
	public static final String _SLASH = "/";
	
	interface VALUES
	{
		public static final String BACKUP = "backup";
		public static final String RESTORE = "restore";
		public static final String ADD = "add";
		public static final String UPDATE = "update";
		public static final String DELETE = "delete";
		public static final String DELETEDATE = "deletedate";
		public static final String DELETEMONTH = "deletemonth";
		public static final String DELETEALL = "deleteall";
		public static final String CHANGE = "change";
		public static final String PRINT = "print";
		public static final String PRINTDATE = "printdate";
		public static final String PRINTMONTH = "printmonth";
		public static final String PRINTSUMM = "printsumm";
		public static final String PRINTSUMMMONTH = "printsummmonth";
		public static final String REPORT = "report";
		public static final String SUMMARY = "summary";
		public static final String SUMMARYMONTH = "summarymonth";
		public static final String MONTH = "month";
		public static final String RANGE = "range";
		public static final String SEARCH = "search";
		public static final String ALL = "all";
	}
	
	interface PARAMS
	{
		public static final String FILEPATH = "filepath";
		public static final String ACT = "act";
		public static final String FDATE = "fdate";
		public static final String TDATE = "tdate";
		public static final String FRMDATE = "frmdate";
		public static final String TODATE = "todate";
		public static final String PRINTDATE = "printDate";
		public static final String PRINTMONTH = "printMonth";
		public static final String REC = "rec";
		public static final String LST = "lst";
		public static final String BKNO = "bkno";
		public static final String BLNO = "blno";
		public static final String NF1 = "nf1";
		public static final String NF3 = "nf3";
		public static final String F8 = "f8";
		public static final String F10 = "f10";
		public static final String BOOKNO = "bookno";
		public static final String DBOOKNO = "dbookno";
		public static final String OLDBOOKNO = "oldbookno";
		public static final String BILLNO = "billno";
		public static final String OLDBILLNO = "oldbillno";
		public static final String SELDATE = "selDate";
		public static final String DELDATE = "delDate";
		public static final String DELMONTH = "delMonth";
		
		public static final String _FD = "&fd=";
		public static final String _SA = "&sa=";
		public static final String _TD = "&td=";
		public static final String _A = "&a=";
		public static final String _L = "&l=";
		public static final String _M = "&m=";
		public static final String _BL = "&bl=";
		public static final String _BK = "&bk=";
		public static final String _R = "&r=";
		public static final String _D = "&d=";
		public static final String _T = "&t=";
		public static final String _C = "c=";
		
		public static final String MYDATE = "MyDate";
		public static final String MYDATEUPDATE = "MyDateU";
		public static final String CMPSEL = "cmpsel";
		
		public static final String _USER = "?user=";
		public static final String _PASSWORD = "?password=";
		
	}
	
	interface JSP
	{
		public static final String ADD = "/add.jsp?";
		public static final String CHANGECMP = "/changecmp.jsp?";
		public static final String LISTING = "/listing.jsp?";
		public static final String MAIN = "/main.jsp?";
		public static final String PRINT = "/print.jsp?";
		public static final String REPORT = "/report.jsp?";
		public static final String SUMMARY = "/summarymon.jsp?";
		public static final String OLDSUMMARY = "/summary.jsp?";
	}
	
	interface ATTR
	{
		public static final String MSG = "msg";
	}
}


