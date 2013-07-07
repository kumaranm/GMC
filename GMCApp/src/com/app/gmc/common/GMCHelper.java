/**
 * 
 */
package com.app.gmc.common;

import java.sql.Date;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.servlet.http.HttpServletRequest;

import com.app.gmc.db.GMCDBVo;

/**
 * @author Kumaran
 * 
 */
public class GMCHelper
{

	private static GMCHelper instance = new GMCHelper();
	
	public static GMCHelper getInstance()
	{
		return instance;
	}

	public String getCompany(HttpServletRequest request)
	{
		String cmptitle = request.getParameter(GMC.PARAMS.CMPSEL);

		if (cmptitle == null)
		{
			cmptitle = "";
		}
		cmptitle = cmptitle.replace(GMC._SLASH, "");
		if (cmptitle.equalsIgnoreCase(GMCCostants.SKC))
		{
			cmptitle = GMCCostants.SKC;
		}
		else
		{
			cmptitle = GMCCostants.GMC;
		}
		return cmptitle;
	}

	public String removeSlash(String str)
	{
		if (str != null)
		{
			str = str.replace(GMC._SLASH, "");
		}
		return str;
	}

	public double conv2Double(String str)
	{
		try
		{
			return Double.parseDouble(str);
		}
		catch (NumberFormatException nfe)
		{
			return 0;
		}
	}

	public int conv2Int(String str)
	{
		try
		{
			return Integer.parseInt(str);
		}
		catch (NumberFormatException nfe)
		{
			return 0;
		}
	}

	public String getDate(HttpServletRequest request)
	{
		SimpleDateFormat formatter = new SimpleDateFormat("d-M-yyyy");
		String date = request.getParameter(GMC.PARAMS.MYDATE);

		if (date == null)
		{
			date = "";
		}
		date = date.replace(GMC._SLASH, "");
		if (date == "")
		{
			date = formatter.format(Calendar.getInstance().getTime());
		}
		return date;
	}
	
	public String getUpdateDate(HttpServletRequest request)
	{
		SimpleDateFormat formatter = new SimpleDateFormat("d-M-yyyy");
		String date = request.getParameter(GMC.PARAMS.MYDATEUPDATE);
		
		if (date == null)
		{
			date = "";
		}
		date = date.replace(GMC._SLASH, "");
		if (date == "")
		{
			date = formatter.format(Calendar.getInstance().getTime());
		}
		return date;
	}

	public String getDate(HttpServletRequest request, String param)
	{
		SimpleDateFormat formatter = new SimpleDateFormat("d-M-yyyy");
		String date = request.getParameter(param);

		if (date == null || date.equals("null/"))
		{
			date = "";
		}
		date = date.replace(GMC._SLASH, "");
		if (date == "")
		{
			date = formatter.format(Calendar.getInstance().getTime());
		}
		return date;
	}

	public Date conv2SqlDate(String dt)
	{

		String date = "";
		String[] str = dt.split("-");
		if (str.length == 3)
		{
			str[0] = str[0].length() == 1 ? '0' + str[0] : str[0];
			str[1] = str[1].length() == 1 ? '0' + str[1] : str[1];

			date = str[2] + "-" + str[1] + "-" + str[0];
		}

		return Date.valueOf(date);
	}

	public String convDate2String(Date dt)
	{
		Calendar cal = Calendar.getInstance();
		cal.setTime(dt);
		String date = "" + cal.get(Calendar.DATE);
		String month = "" + (cal.get(Calendar.MONTH) + 1);
		String year = "" + cal.get(Calendar.YEAR);

		date = date.length() == 1 ? '0' + date : date;
		month = month.length() == 1 ? '0' + month : month;

		return date + "-" + month + "-" + year;
	}
	
	public String convDate2String(java.util.Date dt)
	{
		Calendar cal = Calendar.getInstance();
		cal.setTime(dt);
		String date = "" + cal.get(Calendar.DATE);
		String month = "" + (cal.get(Calendar.MONTH) + 1);
		String year = "" + cal.get(Calendar.YEAR);
		
		date = date.length() == 1 ? '0' + date : date;
		month = month.length() == 1 ? '0' + month : month;
		
		return date + "-" + month + "-" + year;
	}
	
	public String getDay(Date dt)
	{
		Calendar cal = Calendar.getInstance();
		cal.setTime(dt);
		String date = "" + cal.get(Calendar.DATE);
		//String month = "" + (cal.get(Calendar.MONTH) + 1);
		
		//date = date.length() == 1 ? '0' + date : date;
		//month = month.length() == 1 ? '0' + month : month;
		//String year = "" + cal.get(Calendar.YEAR);
		
		return date;
		//return date + "-" + month + "-"+ year.substring(2,4);
	}

	public String getMonth(Date dt)
	{
		Calendar cal = Calendar.getInstance();
		cal.setTime(dt);
		int month = cal.get(Calendar.MONTH);
		String mon = GMCCostants.MONTHS[month] +" "+ cal.get(Calendar.YEAR);
		return mon;
	}
	
	public String getMonth(String month)
	{
		for (int i=0; i< GMCCostants.MONTHS.length ; i++)
		{
			if(month.equalsIgnoreCase(GMCCostants.MONTHS[i]))
			{
				i++;
				return i > 9 ? ""+i : "0"+i; 
			}
		}
		return "00";
	}
	
	public String convTextMonth2NoMonth(String month)
	{
		String temp[] = month.split(" ");
		String date = temp[1] + "-" + getMonth(temp[0]);
		return date;
	}
	
	public String formatDouble(double d)
	{
		DecimalFormat formatter = new DecimalFormat("#,##,##,##0.00");
		try
		{

			return formatter.format(d);
		}
		catch (Exception e)
		{
			return String.valueOf(d);
		}
	}

	public void calculateTax(final GMCDBVo vo)
	{

		//CODE for 5% and 14.5%
		if (vo.getSales145() != 0)
		{
			double temp = (vo.getSales145() / 114.5) * 100;
			vo.setTax145(vo.getSales145() - temp);
			vo.setSales145(temp);
		}
		//CODE for 5% and 14.5%
		if (vo.getSales5() != 0)
		{
			double temp = (vo.getSales5() / 105) * 100;
			vo.setTax5(vo.getSales5() - temp);
			vo.setSales5(temp);
		}

	}
	
	public static void main(String[] args)
	{
		GMCHelper h = GMCHelper.getInstance();
		//Date temp = h.conv2SqlDate("1-1-2012");
		//System.out.println(temp);

		//String t = h.convDate2String(temp);
		//System.out.println(t);

		System.out.println(h.formatDouble(123456.78));
	}
}
