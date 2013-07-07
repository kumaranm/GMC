package com.app.gmc;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface Action
{

	/**
	 * Peforms the processing associated with this action.
	 * 
	 * @param request
	 *            the HttpServletRequest instance
	 * @param response
	 *            the HttpServletResponse instance
	 * @return the name of the next view
	 */
	public abstract String process(HttpServletRequest request, HttpServletResponse response) throws ServletException;

}
