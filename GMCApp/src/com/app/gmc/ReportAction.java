package com.app.gmc;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.app.gmc.common.GMC;
import com.app.gmc.common.GMCHelper;

public class ReportAction implements Action
{
	
	private GMCHelper helper = GMCHelper.getInstance();

	public String process(HttpServletRequest request, HttpServletResponse response) throws ServletException
	{
		String cmptitle = helper.getCompany(request);
		String date = helper.getDate(request);
		String act = helper.removeSlash(request.getParameter(GMC.PARAMS.ACT));
		String lst = helper.removeSlash(request.getParameter(GMC.PARAMS.LST));
		String frmdate = helper.getDate(request, GMC.PARAMS.FRMDATE);
		String todate = helper.getDate(request, GMC.PARAMS.TODATE);
		if (act != null && act.equals(GMC.VALUES.REPORT))
		{
			String dt = helper.getDate(request, GMC.PARAMS.SELDATE);
			return GMC.JSP.REPORT + GMC.PARAMS._C + cmptitle + GMC.PARAMS._D + dt + GMC.PARAMS._L + lst
					+ GMC.PARAMS._FD + frmdate + GMC.PARAMS._TD + todate;
		}
		else if (act != null && act.equals(GMC.VALUES.PRINT))
		{
			return GMC.JSP.PRINT + GMC.PARAMS._C + cmptitle + GMC.PARAMS._D + date;
		}
		return GMC.JSP.REPORT + GMC.PARAMS._C + cmptitle + GMC.PARAMS._D + date;
	}
}
