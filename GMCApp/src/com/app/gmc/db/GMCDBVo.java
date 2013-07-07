/**
 * 
 */
package com.app.gmc.db;

import java.sql.Date;

/**
 * @author Kumaran
 * 
 */
public class GMCDBVo {

	private String company;
	private String bookNumber;
	private int billNumber;
//	private double sales125;
//	private double tax125;
//	private double sales4;
//	private double tax4;
	//CODE for 5% and 14.5%
	private double sales145;
	private double tax145;
	private double sales5;
	private double tax5;

	private double ntas;
	private Date dt;

	public double getSales145() {
		return sales145;
	}

	public void setSales145(double sales145) {
		this.sales145 = sales145;
	}

	public double getTax145() {
		return tax145;
	}

	public void setTax145(double tax145) {
		this.tax145 = tax145;
	}

	public double getSales5() {
		return sales5;
	}

	public void setSales5(double sales5) {
		this.sales5 = sales5;
	}

	public double getTax5() {
		return tax5;
	}

	public void setTax5(double tax5) {
		this.tax5 = tax5;
	}

	private double total;

	public double getTotal() {
		return total;
	}

	public void setTotal(double total) {
		this.total = total;
	}

	public int getBillNumber() {
		return billNumber;
	}

	public void setBillNumber(int billNumber) {
		this.billNumber = billNumber;
	}

	public String getBookNumber() {
		return bookNumber;
	}

	public void setBookNumber(String bookNumber) {
		this.bookNumber = bookNumber;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public Date getDt() {
		return dt;
	}

	public void setDt(Date dt) {
		this.dt = dt;
	}

	public double getNtas() {
		return ntas;
	}

	public void setNtas(double ntas) {
		this.ntas = ntas;
	}

	/*public double getSales125() {
		return sales125;
	}

	public void setSales125(double sales125) {
		this.sales125 = sales125;
	}

	public double getSales4() {
		return sales4;
	}

	public void setSales4(double sales4) {
		this.sales4 = sales4;
	}

	public double getTax125() {
		return tax125;
	}

	public void setTax125(double tax125) {
		this.tax125 = tax125;
	}

	public double getTax4() {
		return tax4;
	}

	public void setTax4(double tax4) {
		this.tax4 = tax4;
	}*/

	/*public String toString() {
		return company + "," + bookNumber + "," + sales125 + "," + tax125 + ","
				+ sales4 + "," + tax4 + "," + sales145 + "," + tax145 + ","
				+ sales5 + "," + tax5 + "," + total;
	}*/
}
