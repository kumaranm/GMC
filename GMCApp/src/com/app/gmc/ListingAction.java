package com.app.gmc;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.app.gmc.common.GMC;
import com.app.gmc.common.GMCHelper;
import com.app.gmc.db.GMCDBApp;
import com.app.gmc.db.GMCDBVo;

public class ListingAction implements Action
{

	private GMCHelper helper = GMCHelper.getInstance();

	public String process(HttpServletRequest request, HttpServletResponse response) throws ServletException
	{
		String cmptitle = helper.getCompany(request);
		String date = helper.getDate(request);
		String act = helper.removeSlash(request.getParameter(GMC.PARAMS.ACT));
		String oldBkNo = request.getParameter(GMC.PARAMS.OLDBOOKNO);
		String oldBlNo = request.getParameter(GMC.PARAMS.OLDBILLNO);
		String bkNo = request.getParameter(GMC.PARAMS.BOOKNO);
		String blNo = request.getParameter(GMC.PARAMS.BILLNO);
		String dBookNo = request.getParameter(GMC.PARAMS.DBOOKNO);
		if (act != null && act.equals(GMC.VALUES.UPDATE))
		{
			//update to DB
			String res = updateRecord(request);
			return GMC.JSP.LISTING + GMC.PARAMS._C + cmptitle + GMC.PARAMS._D + date + GMC.PARAMS._R + res + GMC.PARAMS._A
					+ GMC.VALUES.UPDATE + GMC.PARAMS._BK + oldBkNo + GMC.PARAMS._BL + oldBlNo;
		}
		else if (act != null && act.equals(GMC.VALUES.DELETE))
		{
			//delete records
			boolean res = deleteRecord(request);

			return GMC.JSP.LISTING + GMC.PARAMS._C + cmptitle + GMC.PARAMS._D + date + GMC.PARAMS._R + res + GMC.PARAMS._A
					+ GMC.VALUES.DELETE + GMC.PARAMS._BK + bkNo + GMC.PARAMS._BL + blNo;
		}
		else if (act != null && act.equals(GMC.VALUES.DELETEALL))
		{
			//delete records
			boolean res = deleteRecordByBkNo(request);
			return GMC.JSP.LISTING + GMC.PARAMS._C + cmptitle + GMC.PARAMS._D + date + GMC.PARAMS._R + res + GMC.PARAMS._A
					+ GMC.VALUES.DELETEALL + GMC.PARAMS._BK + dBookNo;
		}
		return GMC.JSP.LISTING + GMC.PARAMS._C + cmptitle + GMC.PARAMS._D + date;
	}

	private boolean deleteRecord(HttpServletRequest request)
	{
		GMCDBVo vo = new GMCDBVo();
		vo.setCompany(helper.getCompany(request));
		vo.setBookNumber(request.getParameter(GMC.PARAMS.BOOKNO));
		vo.setBillNumber(helper.conv2Int(request.getParameter(GMC.PARAMS.BILLNO)));
		return GMCDBApp.deleteRecord(vo);
	}

	private boolean deleteRecordByBkNo(HttpServletRequest request)
	{
		GMCDBVo vo = new GMCDBVo();
		vo.setCompany(helper.getCompany(request));
		vo.setBookNumber(request.getParameter(GMC.PARAMS.DBOOKNO));
		vo.setDt(helper.conv2SqlDate(helper.getDate(request)));
		return GMCDBApp.deleteRecordByBkNo(vo);
	}

	private String updateRecord(HttpServletRequest request)
	{
		GMCDBVo vo = new GMCDBVo();
		vo.setCompany(helper.getCompany(request));
		String oldBkNo = request.getParameter(GMC.PARAMS.OLDBOOKNO);
		String newBkNo = request.getParameter(GMC.PARAMS.BKNO);

		int oldBlNo = helper.conv2Int(request.getParameter(GMC.PARAMS.OLDBILLNO));
		int newBlNo = helper.conv2Int(request.getParameter(GMC.PARAMS.BLNO));

		vo.setBookNumber(request.getParameter(GMC.PARAMS.BKNO));
		vo.setBillNumber(helper.conv2Int(request.getParameter(GMC.PARAMS.BLNO)));

		//CODE for 5% and 14.5%
		vo.setSales145(helper.conv2Double(request.getParameter(GMC.PARAMS.NF1)));
		vo.setSales5(helper.conv2Double(request.getParameter(GMC.PARAMS.NF3)));

		vo.setNtas(helper.conv2Double(request.getParameter(GMC.PARAMS.F8)));
		vo.setTotal(helper.conv2Double(request.getParameter(GMC.PARAMS.F10)));

		String date = helper.getUpdateDate(request);
		vo.setDt(helper.conv2SqlDate(date));
		helper.calculateTax(vo);

		if (!oldBkNo.equalsIgnoreCase(newBkNo) || oldBlNo != newBlNo)
		{
			//update billno/bookno
			GMCDBVo delVo = new GMCDBVo();
			delVo.setCompany(vo.getCompany());
			delVo.setBookNumber(oldBkNo);
			delVo.setBillNumber(oldBlNo);
			return GMCDBApp.updateRecord(delVo, vo);
		}

		return "" + GMCDBApp.updateRecord(vo);
	}
}
