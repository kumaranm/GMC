/**
 * 
 */
package com.app.gmc;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.app.gmc.common.GMC;
import com.app.gmc.common.GMCHelper;

/**
 * @author Kumaran
 * 
 */
public class ChangeCompanyAction implements Action
{
	private GMCHelper helper = GMCHelper.getInstance();
	public String process(HttpServletRequest request, HttpServletResponse response) throws ServletException
	{
		String cmptitle = helper.getCompany(request);
		String date = helper.getDate(request);
		return GMC.JSP.CHANGECMP + GMC.PARAMS._C + cmptitle + GMC.PARAMS._D + date;
	}
}
