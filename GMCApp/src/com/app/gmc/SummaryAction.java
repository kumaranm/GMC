/**
 * 
 */
package com.app.gmc;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.app.gmc.common.GMC;
import com.app.gmc.common.GMCCostants;
import com.app.gmc.common.GMCHelper;
import com.app.gmc.db.GMCDBApp;
import com.app.gmc.db.GMCDBVo;

/**
 * @author Kumaran
 * 
 */
public class SummaryAction implements Action
{
	private GMCHelper helper = GMCHelper.getInstance();
	
	public String process(HttpServletRequest request, HttpServletResponse response) throws ServletException
	{
		String cmptitle = helper.getCompany(request);
		String date = helper.getDate(request);
		String today = helper.getDate(request,"");
		String deldate = helper.getDate(request, GMC.PARAMS.DELDATE);
		String delmonth = helper.getDate(request, GMC.PARAMS.DELMONTH);
		String frmdate = helper.getDate(request, GMC.PARAMS.FRMDATE);
		String todate = helper.getDate(request, GMC.PARAMS.TODATE);
		String lst = helper.removeSlash(request.getParameter(GMC.PARAMS.REC));
		String act = helper.removeSlash(request.getParameter(GMC.PARAMS.ACT));

		
		if (act != null && act.equals(GMC.VALUES.DELETEDATE))
		{
			boolean res = deleteAllRecordsByDate(request);
			if (res)
			{
				request.setAttribute(GMC.ATTR.MSG, GMCCostants.RECORDS_DELETED + deldate);
			}
			else
			{
				request.setAttribute(GMC.ATTR.MSG, GMCCostants.DELETION_FAILED + deldate + GMCCostants.TRY_AGAIN);
			}
			act = GMC.VALUES.SEARCH;
		}
		if (act != null && act.equals(GMC.VALUES.DELETEMONTH))
		{
			boolean isRange = false;
			if(lst != null && lst.equals(GMC.VALUES.RANGE))
			{
				isRange = true;
			}
			boolean res = deleteAllRecordsByMonth(request, isRange, frmdate, todate);
			if (res)
			{
				request.setAttribute(GMC.ATTR.MSG, GMCCostants.RECORDS_DELETED + delmonth);
			}
			else
			{
				request.setAttribute(GMC.ATTR.MSG, GMCCostants.DELETION_FAILED + delmonth + GMCCostants.TRY_AGAIN);
			}
			act = GMC.VALUES.SEARCH;
		}
		if (act != null && act.equals(GMC.VALUES.SEARCH) && lst != null && lst.equals(GMC.VALUES.ALL))
		{
			return GMC.JSP.SUMMARY + GMC.PARAMS._C + cmptitle + GMC.PARAMS._D + date + GMC.PARAMS._FD + frmdate
					+ GMC.PARAMS._TD + todate + GMC.PARAMS._A + GMC.VALUES.ALL;
		}
		else if (act != null && act.equals(GMC.VALUES.SEARCH) && lst != null && lst.equals(GMC.VALUES.RANGE))
		{
			if(helper.conv2SqlDate(frmdate).after(helper.conv2SqlDate(todate)))
			{
				request.setAttribute(GMC.ATTR.MSG, "Error!!! FROM date cannot be greater than TO date");
			}
			return GMC.JSP.SUMMARY + GMC.PARAMS._C + cmptitle + GMC.PARAMS._D + date + GMC.PARAMS._FD + frmdate
					+ GMC.PARAMS._TD + todate + GMC.PARAMS._A + GMC.VALUES.RANGE;
		}
		return GMC.JSP.SUMMARY + GMC.PARAMS._C + cmptitle + GMC.PARAMS._D + date + GMC.PARAMS._T + today + GMC.PARAMS._FD
				+ frmdate + GMC.PARAMS._TD + todate + GMC.PARAMS._A + act;
	}

	private boolean deleteAllRecordsByDate(HttpServletRequest request)
	{
		GMCDBVo vo = new GMCDBVo();
		vo.setCompany(helper.getCompany(request));
		String date = helper.getDate(request, GMC.PARAMS.DELDATE);
		vo.setDt(helper.conv2SqlDate(date));
		return GMCDBApp.deleteAllRecordsByDate(vo);
	}
	
	private boolean deleteAllRecordsByMonth(HttpServletRequest request, boolean isRange, String frmdate, String todate)
	{
		GMCDBVo vo = new GMCDBVo();
		vo.setCompany(helper.getCompany(request));
		String delmonth = helper.getDate(request, GMC.PARAMS.DELMONTH);
		String temp[] = delmonth.split(" ");
		String date = temp[1] + "-" + helper.getMonth(temp[0]);
		vo.setBookNumber(date);//month is set in format yyyy-mm and date is compared and deleted 
		if(!isRange)
		{
			return GMCDBApp.deleteAllRecordsByMonth(vo);
		}
		else
		{
			return GMCDBApp.deleteAllRecordsByMonthAndRange(vo, helper.conv2SqlDate(frmdate), helper.conv2SqlDate(todate));
		}
	}
}
