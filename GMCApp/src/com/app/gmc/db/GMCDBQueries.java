/**
 * 
 */
package com.app.gmc.db;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.app.gmc.common.GMCHelper;

/**
 * @author Kumaran
 *
 */
public class GMCDBQueries
{

	private Connection m_conn;

	GMCDBQueries(Connection con)
	{
		m_conn = con;
	}

	public boolean checkDB()
	{
		PreparedStatement stmt;
		try
		{

			stmt = m_conn.prepareStatement("select count(1) from " + GMCDBConstants.TABLE);

			ResultSet rs = stmt.executeQuery();

			while (rs.next())
			{
				return true;
			}

			// close statement and connection
			stmt.close();
		}
		catch (SQLException e)
		{
			e.printStackTrace();
			System.err.println("GMC: checkDB failed");
		}
		return false;
	}

	public List<GMCDBVo> selectAllRecords(String company, Date dt)
	{
		PreparedStatement stmt;
		int i = 1;
		ArrayList<GMCDBVo> list = new ArrayList<GMCDBVo>(0);
		try
		{

			stmt = m_conn.prepareStatement("select * from " + GMCDBConstants.TABLE
					+ " where COMPANY = ? AND DATE_OF_SALES = ? " + " ORDER BY BOOK_NUMBER,BILL_NUMBER");
			stmt.setString(i, company);
			stmt.setDate(++i, dt);

			ResultSet rs = stmt.executeQuery();

			while (rs.next())
			{
				GMCDBVo tempVo = new GMCDBVo();
				// get current row values
				tempVo.setCompany(rs.getString("COMPANY"));
				tempVo.setBookNumber(rs.getString("BOOK_NUMBER"));
				tempVo.setBillNumber(rs.getInt("BILL_NUMBER"));
				//tempVo.setSales125(rs.getDouble("12_5_I_SALES_AMOUNT"));
				//tempVo.setTax125(rs.getDouble("12_5_I_SALES_TAX"));
				//tempVo.setSales4(rs.getDouble("4_I_SALES_AMOUNT"));
				//tempVo.setTax4(rs.getDouble("4_I_SALES_TAX"));

				//CODE for 5% and 14.5%
				tempVo.setSales145(rs.getDouble("14_5_I_SALES_AMOUNT"));
				tempVo.setTax145(rs.getDouble("14_5_I_SALES_TAX"));
				tempVo.setSales5(rs.getDouble("5_I_SALES_AMOUNT"));
				tempVo.setTax5(rs.getDouble("5_I_SALES_TAX"));

				tempVo.setNtas(rs.getDouble("NTAS"));
				tempVo.setDt(rs.getDate("DATE_OF_SALES"));
				tempVo.setTotal(rs.getDouble("TOTAL"));
				list.add(tempVo);
			}

			// close statement and connection
			stmt.close();
		}
		catch (SQLException e)
		{
			e.printStackTrace();
			System.err.println("GMC: Retrieval of records failed");
		}
		return list;
	}

	public String findByBkNoBlNo(String company, String bkno, int blno)
	{
		String res = null;
		PreparedStatement stmt;
		int i = 1;
		try
		{

			stmt = m_conn.prepareStatement("select DATE_OF_SALES from " + GMCDBConstants.TABLE
					+ " where COMPANY = ? AND BOOK_NUMBER = ? and BILL_NUMBER =?");
			stmt.setString(i, company);
			stmt.setString(++i, bkno);
			stmt.setInt(++i, blno);

			ResultSet rs = stmt.executeQuery();

			while (rs.next())
			{
				res = GMCHelper.getInstance().convDate2String(rs.getDate("DATE_OF_SALES"));
			}

			// close statement and connection
			stmt.close();
		}
		catch (SQLException e)
		{
			e.printStackTrace();
			System.err.println("GMC: findByBkNoBlNo failed");
		}
		return res;
	}

	public String addRecord(GMCDBVo dataVo)
	{
		PreparedStatement stmt;
		int i = 1;
		try
		{
			stmt = m_conn.prepareStatement("insert into " + GMCDBConstants.TABLE + "( COMPANY, BOOK_NUMBER, BILL_NUMBER, "
					+ "14_5_I_SALES_AMOUNT ,14_5_I_SALES_TAX, 5_I_SALES_AMOUNT , 5_I_SALES_TAX ,"
					+ "NTAS ,TOTAL ,DATE_OF_SALES ,CREATION_DATE ,LAST_MODIFIED_DATE)"
					+ " values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");

			stmt.setString(i, dataVo.getCompany());
			stmt.setString(++i, dataVo.getBookNumber());
			stmt.setInt(++i, dataVo.getBillNumber());
			//stmt.setDouble(++i, dataVo.getSales125());
			//stmt.setDouble(++i, dataVo.getTax125());
			//stmt.setDouble(++i, dataVo.getSales4());
			//stmt.setDouble(++i, dataVo.getTax4());

			//CODE for 5% & 14.5%
			stmt.setDouble(++i, dataVo.getSales145());
			stmt.setDouble(++i, dataVo.getTax145());
			stmt.setDouble(++i, dataVo.getSales5());
			stmt.setDouble(++i, dataVo.getTax5());

			stmt.setDouble(++i, dataVo.getNtas());
			stmt.setDouble(++i, dataVo.getTotal());
			stmt.setDate(++i, dataVo.getDt());
			stmt.setDate(++i, new Date(Calendar.getInstance().getTimeInMillis()));
			stmt.setDate(++i, new Date(Calendar.getInstance().getTimeInMillis()));
			stmt.execute();

		}
		catch (SQLException e)
		{
			e.printStackTrace();
			System.err.println("GMC: Insert into table failed");
			String res = findByBkNoBlNo(dataVo.getCompany(), dataVo.getBookNumber(), dataVo.getBillNumber());
			if (res != null)
			{
				return res;
			}
			return "false";
		}
		return "true";
	}

	public boolean deleteRecord(final GMCDBVo dataVo)
	{
		PreparedStatement stmt;
		int i = 1;
		try
		{
			stmt = m_conn.prepareStatement("delete from " + GMCDBConstants.TABLE
					+ " where COMPANY = ? AND BOOK_NUMBER = ? AND BILL_NUMBER = ?");
			stmt.setString(i, dataVo.getCompany());
			stmt.setString(++i, dataVo.getBookNumber());
			stmt.setInt(++i, dataVo.getBillNumber());
			stmt.execute();
			return true;
		}
		catch (SQLException e)
		{
			e.printStackTrace();
			System.err.println("GMC: Delete from table failed");
			return false;
		}
	}

	public boolean deleteRecordByBkNo(final GMCDBVo dataVo)
	{
		PreparedStatement stmt;
		int i = 1;
		try
		{
			stmt = m_conn
					.prepareStatement("delete from " + GMCDBConstants.TABLE + " where COMPANY = ? AND BOOK_NUMBER = ? AND DATE_OF_SALES = ?");
			stmt.setString(i, dataVo.getCompany());
			stmt.setString(++i, dataVo.getBookNumber());
			stmt.setDate(++i, dataVo.getDt());
			stmt.execute();
			return true;
		}
		catch (SQLException e)
		{
			e.printStackTrace();
			System.err.println("GMC: Delete from table failed");
			return false;
		}
	}

	public boolean updateRecord(final GMCDBVo dataVo)
	{
		PreparedStatement stmt;
		int i = 1;
		try
		{ //qry changed
			stmt = m_conn.prepareStatement("update " + GMCDBConstants.TABLE + " set "
					+ "14_5_I_SALES_AMOUNT = ? ,14_5_I_SALES_TAX = ?, 5_I_SALES_AMOUNT = ? , 5_I_SALES_TAX = ? ,"
					+ "NTAS = ? ,TOTAL = ? ,DATE_OF_SALES = ? ,LAST_MODIFIED_DATE = ?"
					+ " where COMPANY = ? AND BOOK_NUMBER = ? AND BILL_NUMBER = ?");

			//stmt.setDouble(i, dataVo.getSales125());
			//stmt.setDouble(++i, dataVo.getTax125());
			//stmt.setDouble(++i, dataVo.getSales4());
			//stmt.setDouble(++i, dataVo.getTax4());

			//CODE for 5% and 14.5%
			stmt.setDouble(i, dataVo.getSales145());
			stmt.setDouble(++i, dataVo.getTax145());
			stmt.setDouble(++i, dataVo.getSales5());
			stmt.setDouble(++i, dataVo.getTax5());

			stmt.setDouble(++i, dataVo.getNtas());
			stmt.setDouble(++i, dataVo.getTotal());
			stmt.setDate(++i, dataVo.getDt());
			stmt.setDate(++i, new Date(Calendar.getInstance().getTimeInMillis()));

			stmt.setString(++i, dataVo.getCompany());
			stmt.setString(++i, dataVo.getBookNumber());
			stmt.setInt(++i, dataVo.getBillNumber());
			stmt.executeUpdate();
			return true;
		}
		catch (SQLException e)
		{
			e.printStackTrace();
			System.err.println("GMC: Update from table failed");
			return false;
		}
	}

	public boolean updateRecord(final GMCDBVo dataVo, String oldBkNo, int oldBlNo)
	{
		PreparedStatement stmt;
		int i = 1;
		try
		{ //qry changed
			stmt = m_conn
					.prepareStatement("update "
							+ GMCDBConstants.TABLE
							+ " set "
							+ "BOOK_NUMBER = ? AND BILL_NUMBER = ?, 14_5_I_SALES_AMOUNT = ? ,14_5_I_SALES_TAX = ?, 5_I_SALES_AMOUNT = ? , 5_I_SALES_TAX = ? ,"
							+ "NTAS = ? ,TOTAL = ? ,DATE_OF_SALES = ? ,LAST_MODIFIED_DATE = ?"
							+ " where COMPANY = ? AND BOOK_NUMBER = ? AND BILL_NUMBER = ?");

			//stmt.setDouble(i, dataVo.getSales125());
			//stmt.setDouble(++i, dataVo.getTax125());
			//stmt.setDouble(++i, dataVo.getSales4());
			//stmt.setDouble(++i, dataVo.getTax4());
			stmt.setString(i, dataVo.getBookNumber());
			stmt.setInt(++i, dataVo.getBillNumber());

			//CODE for 5% and 14.5%
			stmt.setDouble(++i, dataVo.getSales145());
			stmt.setDouble(++i, dataVo.getTax145());
			stmt.setDouble(++i, dataVo.getSales5());
			stmt.setDouble(++i, dataVo.getTax5());

			stmt.setDouble(++i, dataVo.getNtas());
			stmt.setDouble(++i, dataVo.getTotal());
			stmt.setDate(++i, dataVo.getDt());
			stmt.setDate(++i, new Date(Calendar.getInstance().getTimeInMillis()));

			stmt.setString(++i, dataVo.getCompany());
			stmt.setString(++i, oldBkNo);
			stmt.setInt(++i, oldBlNo);
			stmt.executeUpdate();
			return true;
		}
		catch (SQLException e)
		{
			e.printStackTrace();
			System.err.println("GMC: Update from table failed");
			return false;
		}
	}

	public boolean deleteAllRecordsByDate(final GMCDBVo dataVo)
	{
		PreparedStatement stmt;
		int i = 1;
		try
		{
			stmt = m_conn.prepareStatement("delete from " + GMCDBConstants.TABLE
					+ " where COMPANY = ? AND DATE_OF_SALES = ? ");
			stmt.setString(i, dataVo.getCompany());
			stmt.setDate(++i, dataVo.getDt());
			stmt.execute();
			return true;
		}
		catch (SQLException e)
		{
			e.printStackTrace();
			System.err.println("GMC: deleteAllRecordsByDate from table failed");
			return false;
		}
	}

	public boolean deleteAllRecordsByMonth(final GMCDBVo dataVo)
	{
		PreparedStatement stmt;
		int i = 1;
		try
		{
			stmt = m_conn.prepareStatement("delete from " + GMCDBConstants.TABLE
					+ " where COMPANY = ? AND DATE_OF_SALES like ? ");
			stmt.setString(i, dataVo.getCompany());
			stmt.setString(++i, dataVo.getBookNumber() + "%");//date in format yyyy-mm
			stmt.execute();
			return true;
		}
		catch (SQLException e)
		{
			e.printStackTrace();
			System.err.println("GMC: deleteAllRecordsByMonth from table failed");
			return false;
		}
	}
	
	public boolean deleteAllRecordsByMonthAndRange(final GMCDBVo dataVo, Date dt1, Date dt2)
	{
		PreparedStatement stmt;
		int i = 1;
		try
		{
			stmt = m_conn.prepareStatement("delete from " + GMCDBConstants.TABLE
					+ " where COMPANY = ? AND DATE_OF_SALES >= ? and  DATE_OF_SALES <= ? AND DATE_OF_SALES like ?");
			stmt.setString(i, dataVo.getCompany());
			stmt.setDate(++i, dt1);
			stmt.setDate(++i, dt2);
			stmt.setString(++i, dataVo.getBookNumber() + "%");//date in format yyyy-mm
			stmt.execute();
			return true;
		}
		catch (SQLException e)
		{
			e.printStackTrace();
			System.err.println("GMC: deleteAllRecordsByMonthAndRange from table failed");
			return false;
		}
	}

	public Map<Date, Double> selectAllDates(String company)
	{
		PreparedStatement stmt;
		int i = 1;
		Map<Date, Double> mp = new LinkedHashMap<Date, Double>(0);
		try
		{

			stmt = m_conn.prepareStatement("select DATE_OF_SALES, TOTAL from " + GMCDBConstants.TABLE
					+ " where COMPANY = ? " + " order by DATE_OF_SALES DESC");
			stmt.setString(i, company);

			ResultSet rs = stmt.executeQuery();

			while (rs.next())
			{
				Date dt = rs.getDate("DATE_OF_SALES");
				if (mp.containsKey(dt))
				{
					double tot = rs.getDouble("TOTAL");
					tot += mp.get(dt);
					mp.put(dt, tot);
				}
				else
				{
					mp.put(dt, rs.getDouble("TOTAL"));
				}
			}

			// close statement and connection
			stmt.close();
		}
		catch (SQLException e)
		{
			e.printStackTrace();
			System.err.println("GMC: Dates Retrieval of records failed ");
		}
		return mp;
	}

	public Map<Date, Double> selectByDateRange(String company, Date dt1, Date dt2)
	{
		PreparedStatement stmt;
		int i = 1;
		Map<Date, Double> mp = new LinkedHashMap<Date, Double>(0);
		try
		{

			stmt = m_conn.prepareStatement("select DATE_OF_SALES, TOTAL from " + GMCDBConstants.TABLE
					+ " where COMPANY = ? and " + "DATE_OF_SALES >= ? and  DATE_OF_SALES <= ? order by DATE_OF_SALES DESC");
			stmt.setString(i, company);
			stmt.setDate(++i, dt1);
			stmt.setDate(++i, dt2);

			ResultSet rs = stmt.executeQuery();

			while (rs.next())
			{
				Date dt = rs.getDate("DATE_OF_SALES");
				if (mp.containsKey(dt))
				{
					double tot = rs.getDouble("TOTAL");
					tot += mp.get(dt);
					mp.put(dt, tot);
				}
				else
				{
					mp.put(dt, rs.getDouble("TOTAL"));
				}
			}

			// close statement and connection
			stmt.close();
		}
		catch (SQLException e)
		{
			e.printStackTrace();
			System.err.println("GMC: Dates Retrieval of records failed ");
		}
		return mp;
	}

	public static void main(String[] args)
	{
		//		GMCDBAdd.testInsert(3);
		//GMCDBQueries.testSelectAll();
		GMCDBQueries q = new GMCDBQueries(GMCDBMain.getConnection());
		q.backupDB();
	}

	private static void testSelectAll()
	{
		GMCDBQueries add = new GMCDBQueries(GMCDBMain.getConnection());
		/*List<GMCDBVo> data = add.selectAllRecords(GMCDBConstants.GMC, Date.valueOf("2008-03-23"));
		for (GMCDBVo vo : data)
		{
			System.out.println(vo.toString());
		}*/
		//System.out.println("Retreived all records Successfully");
		Map<String, SummaryVo> selectAllTotalsByMonth = add.selectAllTotalsByMonth(GMCDBConstants.GMC);
		selectAllTotalsByMonth = add.selectTotalsByMonthByDateRange(GMCDBConstants.GMC, Date.valueOf("2012-03-13"),
				Date.valueOf("2012-03-14"));
		for (String mon : selectAllTotalsByMonth.keySet())
		{
			SummaryVo svo = selectAllTotalsByMonth.get(mon);
			System.out.println(mon + "-" + svo.getTotal() + "\n");
			for (GMCDBVo vo : svo.getData())
			{
				System.err.println(vo.getDt());
			}
		}
	}

	/*private static void testInsert(int bk)
	{
		GMCDBQueries add = new GMCDBQueries(GMCDBMain.getConnection());

		GMCDBVo vo = new GMCDBVo();
		vo.setCompany(GMCDBConstants.GMC);
		vo.setBookNumber("B" + bk);
		vo.setBillNumber(225);
		vo.setDt(new Date(Calendar.getInstance().getTimeInMillis()));

		boolean r = add.addRecord(vo);
		if (r)
		{
			System.out.println("Insert succedded");
		}
		else
		{
			System.out.println("Insert Failed");

		}
	}*/

	public Map<String, Double> selectAllTotals(String company)
	{
		int i = 1;
		Map<String, Double> mp = new LinkedHashMap<String, Double>(0);
		try
		{
			//qry changed
			PreparedStatement stmt = m_conn
					.prepareStatement("select SUM(14_5_I_SALES_AMOUNT) S145, SUM(14_5_I_SALES_TAX) ST145, "
							+ "SUM(5_I_SALES_AMOUNT) S5, SUM(5_I_SALES_TAX) ST5, " + "SUM(NTAS) NT, SUM(TOTAL) TOT "
							+ "from GMC_SKC_VITHU_BILL where COMPANY = ? ");
			stmt.setString(i, company);
			for (ResultSet rs = stmt.executeQuery(); rs.next(); mp.put("TOT", Double.valueOf(rs.getDouble("TOT"))))
			{
				/*mp.put("S125", Double.valueOf(rs.getDouble("S125")));
				mp.put("ST125", Double.valueOf(rs.getDouble("ST125")));
				mp.put("S4", Double.valueOf(rs.getDouble("S4")));
				mp.put("ST4", Double.valueOf(rs.getDouble("ST4")));*/
				//CODE for 5% and 14.5%
				mp.put("S145", Double.valueOf(rs.getDouble("S145")));
				mp.put("ST145", Double.valueOf(rs.getDouble("ST145")));
				mp.put("S5", Double.valueOf(rs.getDouble("S5")));
				mp.put("ST5", Double.valueOf(rs.getDouble("ST5")));

				mp.put("NT", Double.valueOf(rs.getDouble("NT")));
			}

			stmt.close();
		}
		catch (SQLException e)
		{
			e.printStackTrace();
			System.err.println("GMC: Total Retrieval of records failed ");
		}
		return mp;
	}

	public Map<String, Double> selectTotalsByDateRange(String company, Date dt1, Date dt2)
	{
		int i = 1;
		Map<String, Double> mp = new LinkedHashMap<String, Double>(0);
		try
		{
			PreparedStatement stmt = m_conn
					.prepareStatement("select SUM(14_5_I_SALES_AMOUNT) S145, SUM(14_5_I_SALES_TAX) ST145, "
							+ "SUM(5_I_SALES_AMOUNT) S5, SUM(5_I_SALES_TAX) ST5, " + "SUM(NTAS) NT, SUM(TOTAL) TOT "
							+ "from GMC_SKC_VITHU_BILL where COMPANY = ? and DATE_OF_SALES >= ? and  DATE_OF_SALES <= ?");
			stmt.setString(i, company);
			stmt.setDate(++i, dt1);
			stmt.setDate(++i, dt2);
			for (ResultSet rs = stmt.executeQuery(); rs.next(); mp.put("TOT", Double.valueOf(rs.getDouble("TOT"))))
			{
				/*mp.put("S125", Double.valueOf(rs.getDouble("S125")));
				mp.put("ST125", Double.valueOf(rs.getDouble("ST125")));
				mp.put("S4", Double.valueOf(rs.getDouble("S4")));
				mp.put("ST4", Double.valueOf(rs.getDouble("ST4")));*/

				//CODE for 5% and 14.5%
				mp.put("S145", Double.valueOf(rs.getDouble("S145")));
				mp.put("ST145", Double.valueOf(rs.getDouble("ST145")));
				mp.put("S5", Double.valueOf(rs.getDouble("S5")));
				mp.put("ST5", Double.valueOf(rs.getDouble("ST5")));

				mp.put("NT", Double.valueOf(rs.getDouble("NT")));
			}

			stmt.close();
		}
		catch (SQLException e)
		{
			e.printStackTrace();
			System.err.println("GMC: Total Retrieval of records failed ");
		}
		return mp;
	}

	public Map<String, SummaryVo> selectAllTotalsByMonth(String company)
	{
		return selectTotalsByMonthByDateRange(company, null, null);
	}

	public Map<String, SummaryVo> selectAllTotalsByMonth(String company, String month)
	{
		return selectTotalsByMonthByDateRangeNMonth(company, null, null, month);
	}

	public Map<String, SummaryVo> selectTotalsByMonthByDateRange(String company, Date dt1, Date dt2)
	{
		return selectTotalsByMonthByDateRangeNMonth(company, dt1, dt2, null);
	}

	public Map<String, SummaryVo> selectTotalsByMonthByDateRangeNMonth(String company, Date dt1, Date dt2, String selMonth)
	{
		PreparedStatement stmt;
		int i = 1;
		Map<String, SummaryVo> mp = new LinkedHashMap<String, SummaryVo>(0);
		try
		{
			if (selMonth == null)
			{
				if (dt1 != null && dt2 != null)
				{
					stmt = m_conn
							.prepareStatement("SELECT DATE_OF_SALES, sum(5_I_SALES_TAX) ST5, sum(5_I_SALES_AMOUNT) S5, "
									+ "sum(14_5_I_SALES_TAX) ST145, "
									+ "sum(14_5_I_SALES_AMOUNT) S145,sum(NTAS ) NT, sum(TOTAL) TOT from "
									+ GMCDBConstants.TABLE
									+ " where COMPANY = ? and DATE_OF_SALES >= ? and  DATE_OF_SALES <= ? group by DATE_OF_SALES order by DATE_OF_SALES DESC");
					stmt.setString(i, company);
					stmt.setDate(++i, dt1);
					stmt.setDate(++i, dt2);
				}
				else
				{
					stmt = m_conn
							.prepareStatement("SELECT DATE_OF_SALES, sum(5_I_SALES_TAX) ST5, sum(5_I_SALES_AMOUNT) S5, "
									+ "sum(14_5_I_SALES_TAX) ST145, "
									+ "sum(14_5_I_SALES_AMOUNT) S145,sum(NTAS ) NT, sum(TOTAL) TOT from "
									+ GMCDBConstants.TABLE
									+ " where COMPANY = ? group by DATE_OF_SALES order by DATE_OF_SALES DESC");
					stmt.setString(i, company);
				}
			}
			else
			{
				if (dt1 != null && dt2 != null)
				{
					stmt = m_conn
							.prepareStatement("SELECT DATE_OF_SALES, sum(5_I_SALES_TAX) ST5, sum(5_I_SALES_AMOUNT) S5, "
									+ "sum(14_5_I_SALES_TAX) ST145, "
									+ "sum(14_5_I_SALES_AMOUNT) S145,sum(NTAS ) NT, sum(TOTAL) TOT from "
									+ GMCDBConstants.TABLE
									+ " where COMPANY = ? and DATE_OF_SALES like ? and DATE_OF_SALES >= ? and  DATE_OF_SALES <= ? group by DATE_OF_SALES order by DATE_OF_SALES DESC");
					stmt.setString(i, company);
					stmt.setString(++i, selMonth + "%");//date in format yyyy-mm
					stmt.setDate(++i, dt1);
					stmt.setDate(++i, dt2);
				}
				else
				{
					stmt = m_conn
							.prepareStatement("SELECT DATE_OF_SALES, sum(5_I_SALES_TAX) ST5, sum(5_I_SALES_AMOUNT) S5, "
									+ "sum(14_5_I_SALES_TAX) ST145, "
									+ "sum(14_5_I_SALES_AMOUNT) S145,sum(NTAS ) NT, sum(TOTAL) TOT from "
									+ GMCDBConstants.TABLE
									+ " where COMPANY = ? and DATE_OF_SALES like ? group by DATE_OF_SALES order by DATE_OF_SALES DESC");
					stmt.setString(i, company);
					stmt.setString(++i, selMonth + "%");//date in format yyyy-mm
				}
			}

			ResultSet rs = stmt.executeQuery();
			while (rs.next())
			{
				Date dt = rs.getDate("DATE_OF_SALES");
				String month = GMCHelper.getInstance().getMonth(dt);
				if (mp.containsKey(month))
				{
					SummaryVo vo = mp.get(month);
					GMCDBVo gvo = new GMCDBVo();
					gvo.setDt(dt);
					gvo.setSales145(rs.getDouble("S145"));
					gvo.setTax145(rs.getDouble("ST145"));
					gvo.setSales5(rs.getDouble("S5"));
					gvo.setTax5(rs.getDouble("ST5"));
					gvo.setNtas(rs.getDouble("NT"));
					gvo.setTotal(rs.getDouble("TOT"));
					vo.setSalesAmount145(vo.getSalesAmount145() + gvo.getSales145());
					vo.setSalesTax145(vo.getSalesTax145() + gvo.getTax145());
					vo.setSalesAmount5(vo.getSalesAmount5() + gvo.getSales5());
					vo.setSalesTax5(vo.getSalesTax5() + gvo.getTax5());
					vo.setSalesExempted(vo.getSalesExempted() + gvo.getNtas());
					vo.setTotal(vo.getTotal() + gvo.getTotal());
					vo.addData(gvo);
					mp.put(month, vo);
				}
				else
				{
					SummaryVo vo = new SummaryVo();
					vo.setMonth(month);
					GMCDBVo gvo = new GMCDBVo();
					gvo.setDt(dt);
					gvo.setSales145(rs.getDouble("S145"));
					gvo.setTax145(rs.getDouble("ST145"));
					gvo.setSales5(rs.getDouble("S5"));
					gvo.setTax5(rs.getDouble("ST5"));
					gvo.setNtas(rs.getDouble("NT"));
					gvo.setTotal(rs.getDouble("TOT"));
					vo.addData(gvo);
					vo.setTotal(gvo.getTotal());
					vo.setSalesAmount145(gvo.getSales145());
					vo.setSalesTax145(gvo.getTax145());
					vo.setSalesAmount5(gvo.getSales5());
					vo.setSalesTax5(gvo.getTax5());
					vo.setSalesExempted(gvo.getNtas());
					mp.put(month, vo);
				}
			}
			stmt.close();
		}
		catch (SQLException e)
		{
			e.printStackTrace();
			System.err.println("GMC: Dates Retrieval of records failed ");
		}
		return mp;
	}

	public String backupDB()
	{
		StringBuffer result = new StringBuffer();
		String columnNameQuote = "";
		String schema = GMCDBConstants.DATABASE;
		String table = GMCDBConstants.TABLE;
		try
		{
			DatabaseMetaData metaData = m_conn.getMetaData();
			ResultSet rs = metaData.getTables(null, schema, table, null);
			if (!rs.next())
			{
				System.err
						.println("Unable to find any tables matching: catalog=" + " schema=" + schema + " tables=" + table);
				rs.close();
				return null;
			}
			else
			{
				do
				{
					String tableName = rs.getString("TABLE_NAME");
					String tableType = rs.getString("TABLE_TYPE");
					if ("TABLE".equalsIgnoreCase(tableType))
					{
						result.append("\n\n-- " + tableName);
						result.append("\nCREATE TABLE " + tableName + " (\n");
						ResultSet tableMetaData = metaData.getColumns(null, null, tableName, "%");
						boolean firstLine = true;
						while (tableMetaData.next())
						{
							if (firstLine)
							{
								firstLine = false;
							}
							else
							{
								// If we're not the first line, then finish the previous line with a comma
								result.append(",\n");
							}
							String columnName = tableMetaData.getString("COLUMN_NAME");
							String columnType = tableMetaData.getString("TYPE_NAME");
							// WARNING: this may give daft answers for some types on some databases (eg JDBC-ODBC link)
							int columnSize = tableMetaData.getInt("COLUMN_SIZE");
							String nullable = tableMetaData.getString("IS_NULLABLE");
							String nullString = "NULL";
							if ("NO".equalsIgnoreCase(nullable))
							{
								nullString = "NOT NULL";
							}
							result.append("    " + columnNameQuote + columnName + columnNameQuote + " " + columnType + " ("
									+ columnSize + ")" + " " + nullString);
						}
						tableMetaData.close();

						// Now we need to put the primary key constraint
						try
						{
							ResultSet primaryKeys = metaData.getPrimaryKeys(null, schema, tableName);
							// What we might get:
							// TABLE_CAT String => table catalog (may be null)
							// TABLE_SCHEM String => table schema (may be null)
							// TABLE_NAME String => table name
							// COLUMN_NAME String => column name
							// KEY_SEQ short => sequence number within primary key
							// PK_NAME String => primary key name (may be null)
							String primaryKeyName = null;
							StringBuffer primaryKeyColumns = new StringBuffer();
							while (primaryKeys.next())
							{
								String thisKeyName = primaryKeys.getString("PK_NAME");
								if ((thisKeyName != null && primaryKeyName == null)
										|| (thisKeyName == null && primaryKeyName != null)
										|| (thisKeyName != null && !thisKeyName.equals(primaryKeyName))
										|| (primaryKeyName != null && !primaryKeyName.equals(thisKeyName)))
								{
									// the keynames aren't the same, so output all that we have so far (if anything)
									// and start a new primary key entry
									if (primaryKeyColumns.length() > 0)
									{
										// There's something to output
										result.append(",\n    PRIMARY KEY ");
										if (primaryKeyName != null)
										{
											result.append(primaryKeyName);
										}
										result.append("(" + primaryKeyColumns.toString() + ")");
									}
									// Start again with the new name
									primaryKeyColumns = new StringBuffer();
									primaryKeyName = thisKeyName;
								}
								// Now append the column
								if (primaryKeyColumns.length() > 0)
								{
									primaryKeyColumns.append(", ");
								}
								primaryKeyColumns.append(primaryKeys.getString("COLUMN_NAME"));
							}
							if (primaryKeyColumns.length() > 0)
							{
								// There's something to output
								result.append(",\n    PRIMARY KEY ");
								if (primaryKeyName != null)
								{
									result.append(primaryKeyName);
								}
								result.append(" (" + primaryKeyColumns.toString() + ")");
							}
						}
						catch (SQLException e)
						{
							// NB you will get this exception with the JDBC-ODBC link because it says
							// [Microsoft][ODBC Driver Manager] Driver does not support this function
							System.err.println("Unable to get primary keys for table " + tableName + " because " + e);
						}

						result.append("\n);\n");

						// Right, we have a table, so we can go and dump it
						dumpTable(result, tableName);
					}
				} while (rs.next());
				rs.close();
			}
			return result.toString();
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/** dump this particular table to the string buffer */
	private void dumpTable(StringBuffer result, String tableName)
	{
		try
		{
			// First we output the create table stuff
			PreparedStatement stmt = m_conn.prepareStatement("SELECT * FROM " + tableName);
			ResultSet rs = stmt.executeQuery();
			ResultSetMetaData metaData = rs.getMetaData();
			int columnCount = metaData.getColumnCount();

			// Now we can output the actual data
			result.append("\n\n-- Data for " + tableName + "\n");
			while (rs.next())
			{
				result.append("INSERT INTO " + tableName + " VALUES (");
				for (int i = 0; i < columnCount; i++)
				{
					if (i > 0)
					{
						result.append(", ");
					}
					Object value = rs.getObject(i + 1);
					if (value == null)
					{
						result.append("NULL");
					}
					else
					{
						String outputValue = value.toString();
						outputValue = outputValue.replaceAll("'", "\\'");
						result.append("'" + outputValue + "'");
					}
				}
				result.append(");\n");
			}
			rs.close();
			stmt.close();
		}
		catch (SQLException e)
		{
			System.err.println("Unable to dump table " + tableName + " because: " + e);
		}
	}

	public boolean restoreDB()
	{
		// TODO Auto-generated method stub
		return false;
	}

}
