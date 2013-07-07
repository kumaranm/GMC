package com.app.gmc;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class FrontController extends HttpServlet
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static final String PATH = "com.app.gmc.";

	public void init() throws ServletException
	{

	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{

		// load the action

		//		System.out.println("request.getPathInfo() = " + request.getPathInfo());
		String name = request.getPathInfo().substring(1);
		//		System.out.println("name = " + name);
		String viewName = "/error.jsp";
		try
		{
			name = PATH + name;
			//			System.out.println("name = " + name);
			Class c = getClass().getClassLoader().loadClass(name);
			Action action = (Action) c.newInstance();
			viewName = action.process(request, response);
			//			System.out.println("viewname = " + viewName);
		}
		catch (ClassNotFoundException e)
		{
			e.printStackTrace();
		}
		catch (InstantiationException e)
		{
			e.printStackTrace();
		}
		catch (IllegalAccessException e)
		{
			e.printStackTrace();
		}

		RequestDispatcher dispatcher = request.getRequestDispatcher(viewName);
		dispatcher.forward(request, response);

	}

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{

		doPost(request, response);

	}

}