/**
 * 
 */
package com.app.gmc.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.app.gmc.common.GMC;

/**
 * @author Kumaran
 * 
 */
public final class GMCDBMain
{

	private static Connection m_con = null;

	private static final String USERNAME = "root";

	private static final String PASSWORD = "kumaran";

	private static final String DRIVER = "com.mysql.jdbc.Driver";

	private static final String URL = "jdbc:mysql://localhost/";

	private static final String DB = GMCDBConstants.DATABASE;

	private static void initializeConnection()
	{
		try
		{

			// Load the JDBC-ODBC bridge
			Class.forName(DRIVER);

			// specify the ODBC data source's URL
			String url = URL + DB + GMC.PARAMS._USER + USERNAME + GMC.PARAMS._PASSWORD + PASSWORD;

			// connect
			m_con = DriverManager.getConnection(url, USERNAME, PASSWORD);

		}
		catch (ClassNotFoundException cnfe)
		{
			System.err.println("GMCError: Driver class not found");
			cnfe.printStackTrace();
		}
		catch (SQLException se)
		{
			System.err.println("GMCError: Unable to get connection");
			se.printStackTrace();
		}
		catch (Exception ex)
		{
			System.err.println("GMCError: Exception while creating connection");
			ex.printStackTrace();
		}

	}

	public static Connection getConnection()
	{
		if (m_con == null)
		{
			initializeConnection();
		}
		return m_con;
	}
	
}
