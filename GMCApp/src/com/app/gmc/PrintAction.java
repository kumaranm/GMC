package com.app.gmc;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.app.gmc.common.GMC;
import com.app.gmc.common.GMCHelper;

public class PrintAction implements Action
{

	private GMCHelper helper = GMCHelper.getInstance();
	
	public String process(HttpServletRequest request, HttpServletResponse response) throws ServletException
	{
		String cmptitle = helper.getCompany(request);
		String date = helper.getDate(request);
		String act = helper.removeSlash(request.getParameter(GMC.PARAMS.ACT));
		String frmdate = helper.getDate(request, GMC.PARAMS.FDATE);
		String todate = helper.getDate(request, GMC.PARAMS.TDATE);
		String subact = helper.removeSlash(request.getParameter(GMC.PARAMS.LST));
		String printdate = helper.getDate(request, GMC.PARAMS.PRINTDATE);
		String printmonth = helper.getDate(request, GMC.PARAMS.PRINTMONTH);
		if (act != null && act.equals(GMC.VALUES.PRINT))
		{
			return (new StringBuilder(GMC.JSP.PRINT + GMC.PARAMS._C)).append(cmptitle).append(GMC.PARAMS._D).append(date)
					.append(GMC.PARAMS._A + GMC.VALUES.REPORT).toString();
		}
		else if (act != null && act.equals(GMC.VALUES.PRINTSUMM))
		{
			return (new StringBuilder(GMC.JSP.PRINT + GMC.PARAMS._C)).append(cmptitle).append(GMC.PARAMS._FD)
					.append(frmdate).append(GMC.PARAMS._TD).append(todate)
					.append(GMC.PARAMS._A + GMC.VALUES.SUMMARY + GMC.PARAMS._SA).append(subact).toString();
		}
		else if (act != null && act.equals(GMC.VALUES.PRINTSUMMMONTH))
		{
			return (new StringBuilder(GMC.JSP.PRINT + GMC.PARAMS._C)).append(cmptitle).append(GMC.PARAMS._FD)
					.append(frmdate).append(GMC.PARAMS._TD).append(todate)
					.append(GMC.PARAMS._A + GMC.VALUES.SUMMARYMONTH + GMC.PARAMS._SA).append(subact).toString();
		}
		else if (act != null && act.equals(GMC.VALUES.PRINTMONTH))
		{
			return (new StringBuilder(GMC.JSP.PRINT + GMC.PARAMS._C)).append(cmptitle).append(GMC.PARAMS._FD)
					.append(frmdate).append(GMC.PARAMS._TD).append(todate)
					.append(GMC.PARAMS._A + GMC.VALUES.MONTH + GMC.PARAMS._SA).append(subact)
					.append(GMC.PARAMS._M + printmonth)
					.toString();
		}
		else if (act != null && act.equals(GMC.VALUES.PRINTDATE))
		{
			return (new StringBuilder(GMC.JSP.PRINT + GMC.PARAMS._C)).append(cmptitle).append(GMC.PARAMS._D).append(printdate).append(GMC.PARAMS._FD)
					.append(frmdate).append(GMC.PARAMS._TD).append(todate)
					.append(GMC.PARAMS._A + GMC.VALUES.REPORT).toString();
		}
		else
		{
			return (new StringBuilder(GMC.JSP.REPORT + GMC.PARAMS._C)).append(cmptitle).append(GMC.PARAMS._D).append(date).append(GMC.PARAMS._FD)
					.append(frmdate).append(GMC.PARAMS._TD).append(todate)
					.toString();
		}
		//		return "/report.jsp?c=" + cmptitle + "&d=" + date;
	}
}
