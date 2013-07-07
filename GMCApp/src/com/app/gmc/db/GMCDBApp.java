/**
 * 
 */
package com.app.gmc.db;

import java.sql.Connection;
import java.sql.Date;
import java.util.List;
import java.util.Map;

import com.app.gmc.common.GMCHelper;

/**
 * Interface to the UI for all DB calls
 * @author Kumaran
 *
 */
public class GMCDBApp{
	
	private static final Connection con = GMCDBMain.getConnection();
	
	private static final GMCDBQueries db = new GMCDBQueries(con);
	
	public static boolean checkDB()
	{
		return db.checkDB();
	}
	
	public static String backupDB()
	{
		return db.backupDB();
	}
	
	public static String addRecord(GMCDBVo vo){
		return db.addRecord(vo);
	}
	
	public static String findByBkNoBlNo(String company, String bkno, int blno)
	{
		return db.findByBkNoBlNo(company, bkno, blno);
	}
	
	public static List<GMCDBVo> getAllRecords(String company, Date dt)
	{
		return db.selectAllRecords(company, dt);
	}
	
	public static boolean deleteRecord(GMCDBVo vo)
	{
		return db.deleteRecord(vo); 
	}
	
	public static boolean deleteRecordByBkNo(GMCDBVo vo)
	{
		return db.deleteRecordByBkNo(vo); 
	}
	
	public static boolean updateRecord(GMCDBVo vo)
	{
		return db.updateRecord(vo); 
	}
	
	public static String updateRecord(GMCDBVo vo, GMCDBVo newVo)
	{
		String r = db.addRecord(newVo);
		if(r.equalsIgnoreCase("true"))
		{
			return ""+db.deleteRecord(vo);
		}
		else if(r.equalsIgnoreCase("false"))
		{
			return "false";
		}
		else
		{
			return r;
		}
	}
	
	public static boolean updateRecord(GMCDBVo vo, String oldBkNo, int oldBlNo)
	{
		return db.updateRecord(vo, oldBkNo, oldBlNo); 
	}
	
	public static boolean deleteAllRecordsByDate(GMCDBVo vo)
	{
		return db.deleteAllRecordsByDate(vo); 
	}
	
	public static boolean deleteAllRecordsByMonth(GMCDBVo vo)
	{
		return db.deleteAllRecordsByMonth(vo); 
	}
	
	public static boolean deleteAllRecordsByMonthAndRange(GMCDBVo vo, Date dt1, Date dt2)
	{
		return db.deleteAllRecordsByMonthAndRange(vo, dt1, dt2); 
	}
	
	public static Map<Date, Double> getAllDates(String company)
	{
		return db.selectAllDates(company);
	}
	
	public static Map<Date, Double> getByDateRange(String company, Date dt1, Date dt2)
	{
		return db.selectByDateRange(company, dt1, dt2);
	}
	
	public static Map<String, Double> getAllTotals(String company)
	{
		return db.selectAllTotals(company);
	}
	
	public static Map<String, Double> getTotalsByDateRange(String company, Date dt1, Date dt2)
	{
		return db.selectTotalsByDateRange(company, dt1, dt2);
	}
	
	public static Map<String, SummaryVo> getAllByMonth(String company)
	{
		return db.selectAllTotalsByMonth(company);
	}
	
	public static Map<String, SummaryVo> getByMonthByDateRange(String company, Date dt1, Date dt2)
	{
		return db.selectTotalsByMonthByDateRange(company, dt1, dt2);
	}
	
	public static Map<String, SummaryVo> getAllByMonth(String company, String month)
	{
		return db.selectAllTotalsByMonth(company, GMCHelper.getInstance().convTextMonth2NoMonth(month));
	}
	
	public static Map<String, SummaryVo> getByMonthByDateRangeNMonth(String company, Date dt1, Date dt2, String month)
	{
		return db.selectTotalsByMonthByDateRangeNMonth(company, dt1, dt2, GMCHelper.getInstance().convTextMonth2NoMonth(month));
	}

	public static boolean restoreDB()
	{
		return db.restoreDB();
	}

}
