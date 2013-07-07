package com.app.gmc.db;

import java.util.ArrayList;
import java.util.List;

public class SummaryVo
{

	private String month;

	private List<GMCDBVo> data = new ArrayList<GMCDBVo>(0);
	
	private double salesAmount145;
	private double salesTax145;
	private double salesAmount5;
	private double salesTax5;
	private double salesExempted;
	private double total;

	public String getMonth()
	{
		return month;
	}

	public void setMonth(String month)
	{
		this.month = month;
	}

	public List<GMCDBVo> getData()
	{
		return data;
	}

	public void addData(GMCDBVo vo)
	{
		data.add(vo);
	}

	public double getTotal()
	{
		return total;
	}

	public void setTotal(double total)
	{
		this.total = total;
	}

	public double getSalesAmount145()
	{
		return salesAmount145;
	}

	public void setSalesAmount145(double salesAmount145)
	{
		this.salesAmount145 = salesAmount145;
	}

	public double getSalesTax145()
	{
		return salesTax145;
	}

	public void setSalesTax145(double salesTax145)
	{
		this.salesTax145 = salesTax145;
	}

	public double getSalesAmount5()
	{
		return salesAmount5;
	}

	public void setSalesAmount5(double salesAmount5)
	{
		this.salesAmount5 = salesAmount5;
	}

	public double getSalesTax5()
	{
		return salesTax5;
	}

	public void setSalesTax5(double salesTax5)
	{
		this.salesTax5 = salesTax5;
	}

	public double getSalesExempted()
	{
		return salesExempted;
	}

	public void setSalesExempted(double salesExempted)
	{
		this.salesExempted = salesExempted;
	}
	
}
