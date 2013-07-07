package com.app.gmc;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.app.gmc.common.GMC;
import com.app.gmc.common.GMCHelper;
import com.app.gmc.db.GMCDBApp;
import com.app.gmc.db.GMCDBVo;

public class AddAction implements Action
{

	private GMCHelper helper = GMCHelper.getInstance();
	
	public String process(HttpServletRequest request, HttpServletResponse response) throws ServletException
	{

		String cmptitle = helper.getCompany(request);
		String date = helper.getDate(request);
		String act = helper.removeSlash(request.getParameter(GMC.PARAMS.ACT));
		if (act != null && act.equals(GMC.VALUES.ADD))
		{
			//add to DB
			String res = saveRecord(request);
			return GMC.JSP.ADD + GMC.PARAMS._C + cmptitle + 
					GMC.PARAMS._D + date + 
					GMC.PARAMS._R + res + 
					GMC.PARAMS._BK + request.getParameter(GMC.PARAMS.BKNO) + 
					GMC.PARAMS._BL + getNextBillNo(request.getParameter(GMC.PARAMS.BLNO));
		}

		return GMC.JSP.ADD + GMC.PARAMS._C + cmptitle + 
				GMC.PARAMS._D + date;
	}

	private int getNextBillNo(String blno)
	{
		int n = helper.conv2Int(blno) + 1;
		if (n > 999)
		{
			n = 1;
		}
		return n;
	}

	private String saveRecord(HttpServletRequest request)
	{
		GMCDBVo vo = new GMCDBVo();
		vo.setCompany(helper.getCompany(request));
		vo.setBookNumber(request.getParameter(GMC.PARAMS.BKNO));
		vo.setBillNumber(helper.conv2Int(request.getParameter(GMC.PARAMS.BLNO)));

		//CODE for 5% and 14.5%
		vo.setSales145(helper.conv2Double(request.getParameter(GMC.PARAMS.NF1)));
		vo.setSales5(helper.conv2Double(request.getParameter(GMC.PARAMS.NF3)));

		vo.setNtas(helper.conv2Double(request.getParameter(GMC.PARAMS.F8)));
		vo.setTotal(helper.conv2Double(request.getParameter(GMC.PARAMS.F10)));
		String date = helper.getDate(request);
		vo.setDt(helper.conv2SqlDate(date));
		helper.calculateTax(vo);
		return GMCDBApp.addRecord(vo);
	}
}
