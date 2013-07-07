/**
 * 
 */
package com.app.gmc;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import com.app.gmc.common.GMCCostants;
import com.app.gmc.common.GMCHelper;
import com.app.gmc.db.GMCDBVo;
import com.app.gmc.db.SummaryVo;
import com.lowagie.text.Cell;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.PageSize;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

/**
 * @author Kumaran
 * 
 */
public class PrintReport
{

	private static final float[] widths = { 9, 7, 17, 17, 17, 15, 16, 19 };

	private static Font font11, font12, font14, font8;

	private static int RECORDS_PER_PAGE = 42;
	
	private static int RECORDS_PER_PAGE_SUMMARY_MONTH = 50; 

	public static String printReport()
	{
		String company = GMCCostants.GMC;
		String date = "13-7-2011";

		ArrayList<GMCDBVo> dataList = new ArrayList<GMCDBVo>(0);
		for (int i = 1; i <= RECORDS_PER_PAGE * 3; i++)
		{
			GMCDBVo vo = new GMCDBVo();
			vo.setBookNumber("21");
			vo.setBillNumber(i);

			if (i % 35 == 0)
			{
				dataList.add(vo);
				continue;
			}

			//vo.setSales125(12*i);
			//vo.setSales4(6*i);
			GMCHelper.getInstance().calculateTax(vo);

			if (i % 24 != 0)
			{
				vo.setNtas(10 * i);
			}
			vo.setTotal(vo.getSales145() + vo.getSales5() + vo.getNtas() + vo.getTax145() + vo.getTax5());
			dataList.add(vo);
		}

		// step 1: creation of a document-object
		Document document = new Document(PageSize.A4, -125, -125, 3, 30);
		File f = new File("Vithu_GMC" + "_" + date + ".pdf");
		FileOutputStream fs = null;
		try
		{
			// step 2:
			// we create a writer that listens to the document
			// and directs a PDF-stream to a file
			fs = new FileOutputStream(f);
			PdfWriter.getInstance(document, fs);

			// step 3: we open the document
			document.open();
			// step 4: we add a paragraph to the document
			document.add(getContent(company, dataList, date));

			// step 5: we close the document
			document.close();
			String path = f.getCanonicalPath();
			path = path.replace("\\", "/");
			return path;
		}
		catch (DocumentException de)
		{
			System.err.println(de.getMessage());
		}
		catch (IOException ioe)
		{
			System.err.println(ioe.getMessage());
		}

		return "";
	}

	public static void main(String[] args)
	{
		printReport();
	}

	public static String getFileName(String company, String dt)
	{
		String temp = GMCCostants.TITLE + "_" + company + "_" + dt;
		return temp;
	}

	public static Document addMetaData(final Document doc, String company, String dt)
	{
		doc.addTitle(GMCCostants.TITLE);
		doc.addAuthor(GMCCostants.AUTHOR);
		doc.addSubject(getFileName(company, dt));
		doc.addCreator("GMCApp");
		return doc;
	}

	public static Element getContent(String company, List<GMCDBVo> dataList, String dt) throws DocumentException
	{
		/*String dotMatrixFont = "Dot Matrix";
		FontFactory.register("c:/windows/fonts/DOTMATRX.ttf");

		font8 = FontFactory.getFont(dotMatrixFont, BaseFont.WINANSI, 8);
		//font11 = new Font(11);
		font11 = FontFactory.getFont(dotMatrixFont, BaseFont.WINANSI, 13);
		font12 = FontFactory.getFont(dotMatrixFont, BaseFont.WINANSI, 14);
		font14 = FontFactory.getFont(dotMatrixFont, BaseFont.WINANSI, 15);*/
		
		font8 = new Font(Font.COURIER, 6); 
		font11 = new Font(Font.TIMES_ROMAN, 12); 
		font12 = new Font(Font.TIMES_ROMAN, 13);
		font14 = new Font(Font.TIMES_ROMAN, 15);

		PdfPTable maintable = new PdfPTable(1);

		PdfPCell report = new PdfPCell();
		report.setColspan(widths.length);
		report.setBorder(PdfPCell.NO_BORDER);
		report.setHorizontalAlignment(Cell.ALIGN_CENTER);
		report.setVerticalAlignment(Cell.ALIGN_MIDDLE);
		report.addElement(getReport(dataList, company, dt));

		maintable.addCell(report);
		return maintable;
	}

	private static PdfPTable getReport(List<GMCDBVo> dataList, String company, String dt) throws DocumentException
	{
		GMCHelper g = GMCHelper.getInstance();
		PdfPTable reporttable = new PdfPTable(widths.length);
		reporttable.setHeaderRows(4);
		reporttable.setSplitRows(false);
		reporttable.setComplete(true);
		reporttable.setTotalWidth(widths);
		reporttable.setWidths(widths);

		PdfPCell title = new PdfPCell();
		title.setFixedHeight(20);
		title.setBorder(PdfPCell.NO_BORDER);
		title.setColspan(widths.length);
		title.setHorizontalAlignment(Cell.ALIGN_CENTER);
		title.setVerticalAlignment(Cell.ALIGN_MIDDLE);
		title.setPhrase(createTitlePhrase(company));
		reporttable.addCell(title);

		PdfPCell date = new PdfPCell();
		date.setColspan(widths.length);
		date.setFixedHeight(20);
		date.setBorder(PdfPCell.NO_BORDER);
		date.setHorizontalAlignment(Cell.ALIGN_CENTER);
		date.setVerticalAlignment(Cell.ALIGN_MIDDLE);
		date.setPhrase(createDatePhrase(dt));
		reporttable.addCell(date);

		//		reporttable.addCell(createHeaderCell("Sl"));
		reporttable.addCell(createBkBlHeaderCell(GMCCostants.BOOK_NO));
		reporttable.addCell(createBkBlHeaderCell(GMCCostants.BILL_NO));
		reporttable.addCell(createHeaderCell(GMCCostants.NEWFIELD1));
		reporttable.addCell(createHeaderCell(GMCCostants.NEWFIELD11));
		reporttable.addCell(createHeaderCell(GMCCostants.NEWFIELD3));
		reporttable.addCell(createHeaderCell(GMCCostants.NEWFIELD13));
		reporttable.addCell(createHeaderCell(GMCCostants.FIELD8));
		reporttable.addCell(createHeaderCell(GMCCostants.TOTAL));

		addSeperatorRow(reporttable);

		int slno = 0;
		int page = 1;
		double grandTotal = 0;
		double sales145total = 0, tax145total = 0;
		double sales5total = 0, tax5total = 0;
		double ntastotal = 0;
		for (GMCDBVo vo : dataList)
		{
			slno = slno + 1;
			grandTotal = grandTotal + vo.getTotal();

			//individual totals
			sales145total = sales145total + vo.getSales145();
			tax145total = tax145total + vo.getTax145();
			sales5total = sales5total + vo.getSales5();
			tax5total = tax5total + vo.getTax5();
			ntastotal = ntastotal + vo.getNtas();
			//form table
			reporttable.addCell(createFieldCell(vo.getBookNumber()));
			reporttable.addCell(createFieldCell(String.valueOf(vo.getBillNumber())));
			if (vo.getTotal() == 0)
			{
				PdfPCell cancelled = new PdfPCell();
				cancelled.setColspan(widths.length - 3);
				cancelled.setBorder(PdfPCell.NO_BORDER);
				cancelled.setHorizontalAlignment(Cell.ALIGN_CENTER);
				cancelled.setVerticalAlignment(Cell.ALIGN_MIDDLE);
				cancelled.setPhrase(createContentPhrase(GMCCostants.CANCELLED));
				reporttable.addCell(cancelled);
			}
			else
			{
				reporttable.addCell(createContentCell(g.formatDouble(vo.getSales145())));
				reporttable.addCell(createContentCell(g.formatDouble(vo.getTax145())));
				reporttable.addCell(createContentCell(g.formatDouble(vo.getSales5())));
				reporttable.addCell(createContentCell(g.formatDouble(vo.getTax5())));
				reporttable.addCell(createContentCell(g.formatDouble(vo.getNtas())));
			}
			reporttable.addCell(createContentCell(g.formatDouble(vo.getTotal()), false));

			if (slno == RECORDS_PER_PAGE && dataList.size() > page * RECORDS_PER_PAGE)
			{
				slno = 0;
				page = page + 1;
				addSeperatorRow(reporttable);

				PdfPCell total = new PdfPCell();
				total.setBorder(Cell.NO_BORDER);
				total.setColspan(2);
				total.setHorizontalAlignment(1);
				total.setPhrase(createHeaderPhrase(GMCCostants.SUB_TOTAL));
				reporttable.addCell(total);
				reporttable.addCell(createTotalCell(g.formatDouble(sales145total)));
				reporttable.addCell(createTotalCell(g.formatDouble(tax145total)));
				reporttable.addCell(createTotalCell(g.formatDouble(sales5total)));
				reporttable.addCell(createTotalCell(g.formatDouble(tax5total)));
				reporttable.addCell(createTotalCell(g.formatDouble(ntastotal)));
				reporttable.addCell(createTotalCell(g.formatDouble(grandTotal)));
			}
		}

		if (slno != RECORDS_PER_PAGE || dataList.size() % RECORDS_PER_PAGE == 0)
		{
			addSeperatorRow(reporttable);

			PdfPCell total = new PdfPCell();
			total.setBorder(Cell.NO_BORDER);
			total.setColspan(2);
			total.setHorizontalAlignment(1);
			total.setPhrase(createTitlePhrase(GMCCostants.TOTAL));
			reporttable.addCell(total);
			reporttable.addCell(createTotalCell(g.formatDouble(sales145total)));
			reporttable.addCell(createTotalCell(g.formatDouble(tax145total)));
			reporttable.addCell(createTotalCell(g.formatDouble(sales5total)));
			reporttable.addCell(createTotalCell(g.formatDouble(tax5total)));
			reporttable.addCell(createTotalCell(g.formatDouble(ntastotal)));
			reporttable.addCell(createTotalCell(g.formatDouble(grandTotal)));
		}
		return reporttable;
	}

	public static Element getSummaryContent(String company, Map<String, Double> dataMap, String fdt, String tdt, boolean all)
			throws DocumentException
	{
		// int TOTAL_COLS = 4;
		/*String dotMatrixFont = "Dot Matrix";
		FontFactory.register("c:/windows/fonts/DOTMATRX.ttf");

		font8 = FontFactory.getFont(dotMatrixFont, BaseFont.WINANSI, 8);
		//font11 = new Font(11);
		font11 = FontFactory.getFont(dotMatrixFont, BaseFont.WINANSI, 11);
		font12 = FontFactory.getFont(dotMatrixFont, BaseFont.WINANSI, 12);
		font14 = FontFactory.getFont(dotMatrixFont, BaseFont.WINANSI, 14);*/
		font8 = new Font(Font.COURIER, 6); 
		font11 = new Font(Font.TIMES_ROMAN, 12); 
		font12 = new Font(Font.TIMES_ROMAN, 13);
		font14 = new Font(Font.TIMES_ROMAN, 15);
		
		PdfPTable maintable = new PdfPTable(1);
		maintable.setWidthPercentage(75F);
		PdfPCell report = new PdfPCell();
		report.setColspan(4);
		report.setBorder(0);
		report.setHorizontalAlignment(1);
		report.setVerticalAlignment(5);
		GMCHelper g = GMCHelper.getInstance();
		PdfPTable reporttable = new PdfPTable(4);
		reporttable.setHeaderRows(2);
		reporttable.setSplitRows(false);
		reporttable.setComplete(true);
		reporttable.setTotalWidth(100F);
		reporttable.setWidths(new int[] {
				// 26, 25, 18, 31
				20, 25, 25, 30 });
		PdfPCell title = new PdfPCell();
		title.setFixedHeight(20F);
		title.setBorder(0);
		title.setColspan(4);
		title.setHorizontalAlignment(1);
		title.setVerticalAlignment(5);
		title.setPhrase(createTitlePhrase(company));
		reporttable.addCell(title);
		PdfPCell date = new PdfPCell();
		date.setColspan(4);
		date.setFixedHeight(20F);
		date.setBorder(0);
		date.setHorizontalAlignment(1);
		date.setVerticalAlignment(5);
		if (all)
			date.setPhrase(createDatePhrase(GMCCostants.SUMMARY_ALL_RECORDS));
		else
			date.setPhrase(createDatePhrase((new StringBuilder(GMCCostants.SUMMARY)).append(fdt).append("\t\tTo\t\t").append(tdt)
					.toString()));
		reporttable.addCell(date);
		reporttable.addCell(createBlankCell());
		reporttable.addCell(createBlankCell());
		reporttable.addCell(createBlankCell());
		reporttable.addCell(createBlankCell());
		reporttable.addCell(createBlankCell());
		reporttable.addCell(createHeaderCell(GMCCostants.NEWFIELD1));
		reporttable.addCell(createTotalCell(g.formatDouble(((Double) dataMap.get("S145")).doubleValue())));
		reporttable.addCell(createBlankCell());
		reporttable.addCell(createBlankCell());
		reporttable.addCell(createHeaderCell(GMCCostants.NEWFIELD11));
		reporttable.addCell(createTotalCell(g.formatDouble(((Double) dataMap.get("ST145")).doubleValue())));
		reporttable.addCell(createBlankCell());
		reporttable.addCell(createBlankCell());
		reporttable.addCell(createHeaderCell(GMCCostants.NEWFIELD3));
		reporttable.addCell(createTotalCell(g.formatDouble(((Double) dataMap.get("S5")).doubleValue())));
		reporttable.addCell(createBlankCell());
		reporttable.addCell(createBlankCell());
		reporttable.addCell(createHeaderCell(GMCCostants.NEWFIELD13));
		reporttable.addCell(createTotalCell(g.formatDouble(((Double) dataMap.get("ST5")).doubleValue())));
		reporttable.addCell(createBlankCell());
		reporttable.addCell(createBlankCell());

		//CODE for 5% and 14.5%
		/* reporttable.addCell(createHeaderCell("14.5% I Sales Amount"));
		 reporttable.addCell(createTotalCell(g.formatDouble(((Double)dataMap.get("S145")).doubleValue())));
		 reporttable.addCell(createBlankCell());
		 reporttable.addCell(createBlankCell());
		 reporttable.addCell(createHeaderCell("14.5% I Sales Tax"));
		 reporttable.addCell(createTotalCell(g.formatDouble(((Double)dataMap.get("ST145")).doubleValue())));
		 reporttable.addCell(createBlankCell());
		 reporttable.addCell(createBlankCell());
		 reporttable.addCell(createHeaderCell("5% I Sales Amount"));
		 reporttable.addCell(createTotalCell(g.formatDouble(((Double)dataMap.get("S5")).doubleValue())));
		 reporttable.addCell(createBlankCell());
		 reporttable.addCell(createBlankCell());
		 reporttable.addCell(createHeaderCell("5% I Sales Tax"));
		 reporttable.addCell(createTotalCell(g.formatDouble(((Double)dataMap.get("ST5")).doubleValue())));
		 reporttable.addCell(createBlankCell());
		 reporttable.addCell(createBlankCell());
		 */
		reporttable.addCell(createHeaderCell(GMCCostants.FIELD8));
		reporttable.addCell(createTotalCell(g.formatDouble(((Double) dataMap.get("NT")).doubleValue())));
		reporttable.addCell(createBlankCell());
		reporttable.addCell(createBlankCell());
		reporttable.addCell(createBlankCell());
		reporttable.addCell(createBlankCell());
		reporttable.addCell(createBlankCell());
		reporttable.addCell(createBlankCell());
		reporttable.addCell(createHeaderCell(GMCCostants.TOTAL_SALES));
		reporttable.addCell(createTotalCell(g.formatDouble(((Double) dataMap.get("TOT")).doubleValue())));
		reporttable.addCell(createBlankCell());
		report.addElement(reporttable);
		maintable.addCell(report);
		return maintable;
	}
	
	
	/**
	 * New Summary report print by month
	 * @param company
	 * @param dataMap
	 * @param fdt
	 * @param tdt
	 * @param all
	 * @return
	 * @throws DocumentException
	 */
	public static Element getSummaryContentByMonth(String company, Map<String, SummaryVo> dataMap, String fdt, String tdt, boolean all)
			throws DocumentException
	{
		float[] widths = new float[] { 11, 16, 14, 16, 14, 16, 19 };
		font8 = new Font(Font.COURIER, 6); 
		font11 = new Font(Font.TIMES_ROMAN, 10); 
		font12 = new Font(Font.TIMES_ROMAN, 11);
		font14 = new Font(Font.TIMES_ROMAN, 13);
		
		GMCHelper g = GMCHelper.getInstance();
		PdfPTable reporttable = new PdfPTable(widths.length);
		reporttable.setHeaderRows(4);
		reporttable.setSplitRows(false);
		reporttable.setComplete(true);
		reporttable.setTotalWidth(widths);
		reporttable.setWidths(widths);

		PdfPCell title = new PdfPCell();
		title.setFixedHeight(20);
		title.setBorder(PdfPCell.NO_BORDER);
		title.setColspan(widths.length);
		title.setHorizontalAlignment(Cell.ALIGN_CENTER);
		title.setVerticalAlignment(Cell.ALIGN_MIDDLE);
		title.setPhrase(createTitlePhrase(company));
		reporttable.addCell(title);

		PdfPCell date = new PdfPCell();
		date.setColspan(widths.length);
		date.setFixedHeight(20);
		date.setBorder(PdfPCell.NO_BORDER);
		date.setHorizontalAlignment(Cell.ALIGN_CENTER);
		date.setVerticalAlignment(Cell.ALIGN_MIDDLE);
		if (all)
			date.setPhrase(createDatePhrase(GMCCostants.SUMMARY_ALL_RECORDS));
		else
			date.setPhrase(createDatePhrase((new StringBuilder(GMCCostants.SUMMARY)).append(fdt).append("\t\tTo\t\t").append(tdt)
					.toString()));
		reporttable.addCell(date);

		reporttable.addCell(createHeaderCell(GMCCostants.MONTH));
		reporttable.addCell(createHeaderCell(GMCCostants.NEWFIELD1));
		reporttable.addCell(createHeaderCell(GMCCostants.NEWFIELD11));
		reporttable.addCell(createHeaderCell(GMCCostants.NEWFIELD3));
		reporttable.addCell(createHeaderCell(GMCCostants.NEWFIELD13));
		reporttable.addCell(createHeaderCell(GMCCostants.FIELD8));
		reporttable.addCell(createHeaderCell(GMCCostants.TOTAL));

		addSummarySeperatorRow(reporttable);

		int slno = 0;
		int page = 1;
		double grandTotal = 0;
		double sales145total = 0, tax145total = 0;
		double sales5total = 0, tax5total = 0;
		double ntastotal = 0;
		
		/*TEST CODE TO BE DELETED
		  for (String m: GMCCostants.MONTHS)
		{
			for (int i = 2000; i < 2010; i++)
			{
				SummaryVo vo = new SummaryVo();
				vo.setMonth(m+" "+i);
				vo.setTotal(9840393.09);
				dataMap.put(vo.getMonth(), vo);
			}
		}*/
		
		for (String month : dataMap.keySet())
		{
			slno = slno + 1;
			SummaryVo svo = dataMap.get(month);
			grandTotal = grandTotal + svo.getTotal();

			//individual totals
			sales145total = sales145total + svo.getSalesAmount145();
			tax145total = tax145total + svo.getSalesTax145();
			sales5total = sales5total + svo.getSalesAmount5();
			tax5total = tax5total + svo.getSalesTax5();
			ntastotal = ntastotal + svo.getSalesExempted();
			
			reporttable.addCell(createContentCell(month));
			reporttable.addCell(createContentCell(g.formatDouble(svo.getSalesAmount145())));
			reporttable.addCell(createContentCell(g.formatDouble(svo.getSalesTax145())));
			reporttable.addCell(createContentCell(g.formatDouble(svo.getSalesAmount5())));
			reporttable.addCell(createContentCell(g.formatDouble(svo.getSalesTax5())));
			reporttable.addCell(createContentCell(g.formatDouble(svo.getSalesExempted())));
			reporttable.addCell(createContentCell(g.formatDouble(svo.getTotal()), false));
			
			if (slno == RECORDS_PER_PAGE_SUMMARY_MONTH && dataMap.size() > page * RECORDS_PER_PAGE_SUMMARY_MONTH)
			{
				slno = 0;
				page = page + 1;
				addSummarySeperatorRow(reporttable);

				PdfPCell total = new PdfPCell();
				total.setBorder(Cell.NO_BORDER);
				total.setHorizontalAlignment(1);
				total.setPhrase(createHeaderPhrase(GMCCostants.SUB_TOTAL));
				reporttable.addCell(total);
				reporttable.addCell(createTotalCell(g.formatDouble(sales145total)));
				reporttable.addCell(createTotalCell(g.formatDouble(tax145total)));
				reporttable.addCell(createTotalCell(g.formatDouble(sales5total)));
				reporttable.addCell(createTotalCell(g.formatDouble(tax5total)));
				reporttable.addCell(createTotalCell(g.formatDouble(ntastotal)));
				reporttable.addCell(createTotalCell(g.formatDouble(grandTotal)));
			}
		}
		

		if (slno != RECORDS_PER_PAGE_SUMMARY_MONTH || dataMap.size() % RECORDS_PER_PAGE_SUMMARY_MONTH == 0)
		{
			addSummarySeperatorRow(reporttable);

			PdfPCell total = new PdfPCell();
			total.setBorder(Cell.NO_BORDER);
			total.setHorizontalAlignment(1);
			total.setPhrase(createHeaderPhrase(GMCCostants.TOTAL));
			reporttable.addCell(total);
			reporttable.addCell(createTotalCell(g.formatDouble(sales145total)));
			reporttable.addCell(createTotalCell(g.formatDouble(tax145total)));
			reporttable.addCell(createTotalCell(g.formatDouble(sales5total)));
			reporttable.addCell(createTotalCell(g.formatDouble(tax5total)));
			reporttable.addCell(createTotalCell(g.formatDouble(ntastotal)));
			reporttable.addCell(createTotalCell(g.formatDouble(grandTotal)));
		}
		return reporttable;
	}
	
	
	/**
	 * New Summary report print per month
	 * @param company
	 * @param dataMap
	 * @param fdt
	 * @param tdt
	 * @param all
	 * @return
	 * @throws DocumentException
	 */
	public static Element getSummaryContentPerMonth(String company, Map<String, SummaryVo> dataMap, String fdt, String tdt, boolean all, String month)
			throws DocumentException
	{
		float[] widths = new float[] { 11, 16, 14, 16, 14, 16, 19 };
		font8 = new Font(Font.COURIER, 6); 
		font11 = new Font(Font.TIMES_ROMAN, 10); 
		font12 = new Font(Font.TIMES_ROMAN, 11);
		font14 = new Font(Font.TIMES_ROMAN, 13);
		
		GMCHelper g = GMCHelper.getInstance();
		PdfPTable reporttable = new PdfPTable(widths.length);
		reporttable.setHeaderRows(4);
		reporttable.setSplitRows(false);
		reporttable.setComplete(true);
		reporttable.setTotalWidth(widths);
		reporttable.setWidths(widths);

		PdfPCell title = new PdfPCell();
		title.setFixedHeight(20);
		title.setBorder(PdfPCell.NO_BORDER);
		title.setColspan(widths.length);
		title.setHorizontalAlignment(Cell.ALIGN_CENTER);
		title.setVerticalAlignment(Cell.ALIGN_MIDDLE);
		title.setPhrase(createTitlePhrase(company));
		reporttable.addCell(title);

		PdfPCell date = new PdfPCell();
		date.setColspan(widths.length);
		date.setFixedHeight(20);
		date.setBorder(PdfPCell.NO_BORDER);
		date.setHorizontalAlignment(Cell.ALIGN_CENTER);
		date.setVerticalAlignment(Cell.ALIGN_MIDDLE);
		if (all)
			date.setPhrase(createDatePhrase(GMCCostants.SUMMARY_ALL_RECORDS + " for "+ month));
		else
			date.setPhrase(createDatePhrase((new StringBuilder(GMCCostants.SUMMARY)).append(fdt).append("\t\tTo\t\t").append(tdt).append(" for "+ month)
					.toString()));
		reporttable.addCell(date);

		reporttable.addCell(createBkBlHeaderCell(month));
		reporttable.addCell(createHeaderCell(GMCCostants.NEWFIELD1));
		reporttable.addCell(createHeaderCell(GMCCostants.NEWFIELD11));
		reporttable.addCell(createHeaderCell(GMCCostants.NEWFIELD3));
		reporttable.addCell(createHeaderCell(GMCCostants.NEWFIELD13));
		reporttable.addCell(createHeaderCell(GMCCostants.FIELD8));
		reporttable.addCell(createHeaderCell(GMCCostants.TOTAL));

		addSummarySeperatorRow(reporttable);

		int slno = 0;
//		int page = 1;
		double grandTotal = 0;
		double sales145total = 0, tax145total = 0;
		double sales5total = 0, tax5total = 0;
		double ntastotal = 0;
		
		/*TEST CODE TO BE DELETED
		  for (String m: GMCCostants.MONTHS)
		{
			for (int i = 2000; i < 2010; i++)
			{
				SummaryVo vo = new SummaryVo();
				vo.setMonth(m+" "+i);
				vo.setTotal(9840393.09);
				dataMap.put(vo.getMonth(), vo);
			}
		}*/
		
		/*if(dataMap == null || dataMap.size() < 1 || dataMap.get(month) == null ||  dataMap.get(month).getData().size() < 1)
		{
			return reporttable;
		}*/
		Collections.reverse(dataMap.get(month).getData());
		for (GMCDBVo vo : dataMap.get(month).getData())
		{
			slno = slno + 1;
			grandTotal = grandTotal + vo.getTotal();

			//individual totals
			sales145total = sales145total + vo.getSales145();
			tax145total = tax145total + vo.getTax145();
			sales5total = sales5total + vo.getSales5();
			tax5total = tax5total + vo.getTax5();
			ntastotal = ntastotal + vo.getNtas();
			
			reporttable.addCell(createFieldCell(g.getDay(vo.getDt())));
			reporttable.addCell(createContentCell(g.formatDouble(vo.getSales145())));
			reporttable.addCell(createContentCell(g.formatDouble(vo.getTax145())));
			reporttable.addCell(createContentCell(g.formatDouble(vo.getSales5())));
			reporttable.addCell(createContentCell(g.formatDouble(vo.getTax5())));
			reporttable.addCell(createContentCell(g.formatDouble(vo.getNtas())));
			reporttable.addCell(createContentCell(g.formatDouble(vo.getTotal()), false));
			
			/*if (slno == RECORDS_PER_PAGE_SUMMARY_MONTH && dataMap.size() > page * RECORDS_PER_PAGE_SUMMARY_MONTH)
			{
				slno = 0;
				page = page + 1;
				addSummarySeperatorRow(reporttable);

				PdfPCell total = new PdfPCell();
				total.setBorder(Cell.NO_BORDER);
				total.setHorizontalAlignment(1);
				total.setPhrase(createHeaderPhrase(GMCCostants.SUB_TOTAL));
				reporttable.addCell(total);
				reporttable.addCell(createTotalCell(g.formatDouble(sales145total)));
				reporttable.addCell(createTotalCell(g.formatDouble(tax145total)));
				reporttable.addCell(createTotalCell(g.formatDouble(sales5total)));
				reporttable.addCell(createTotalCell(g.formatDouble(tax5total)));
				reporttable.addCell(createTotalCell(g.formatDouble(ntastotal)));
				reporttable.addCell(createTotalCell(g.formatDouble(grandTotal)));
			}*/
		}
		

		//if (slno != RECORDS_PER_PAGE_SUMMARY_MONTH || dataMap.size() % RECORDS_PER_PAGE_SUMMARY_MONTH == 0)
		//{
			addSummarySeperatorRow(reporttable);

			PdfPCell total = new PdfPCell();
			total.setBorder(Cell.NO_BORDER);
			total.setHorizontalAlignment(1);
			total.setPhrase(createHeaderPhrase(GMCCostants.TOTAL));
			reporttable.addCell(total);
			reporttable.addCell(createTotalCell(g.formatDouble(sales145total)));
			reporttable.addCell(createTotalCell(g.formatDouble(tax145total)));
			reporttable.addCell(createTotalCell(g.formatDouble(sales5total)));
			reporttable.addCell(createTotalCell(g.formatDouble(tax5total)));
			reporttable.addCell(createTotalCell(g.formatDouble(ntastotal)));
			reporttable.addCell(createTotalCell(g.formatDouble(grandTotal)));
		//}
		return reporttable;
	}
	

	private static void addSeperatorRow(PdfPTable reporttable)
	{
		String sep1 = "- -";
		String sep2 = "- - - - - - -";
		for (int j = 0; j < 8; j++)
		{
			if (j < 2)
			{
				reporttable.addCell(createSeperatorCell(sep1, true));
				continue;
			}
			reporttable.addCell(createSeperatorCell(sep2, false));
		}
	}
	
	private static void addSummarySeperatorRow(PdfPTable reporttable)
	{
		String sep2 = "- - - - - -";
		for (int j = 0; j < 7; j++)
		{
			reporttable.addCell(createSeperatorCell(sep2, false));
		}
	}

	/*private static void addBlankRow(PdfPTable reporttable) {
		for (int j = 0; j < 8; j++) {
			reporttable.addCell(createBlankCell());
		}
	}*/

	private static PdfPCell createBlankCell()
	{
		PdfPCell cell = new PdfPCell();
		cell.setPadding(0);
		cell.setColspan(1);
		cell.setBorder(Cell.NO_BORDER);
		cell.setVerticalAlignment(Cell.ALIGN_MIDDLE);
		cell.setPhrase(createContentPhrase(" "));
		return cell;
	}

	private static Phrase createPhrase(String str, Font f)
	{
		return new Phrase(str, f);
	}

	private static Phrase createHeaderPhrase(String str)
	{

		return createPhrase(str, font11);
	}

	private static Phrase createContentPhrase(String str)
	{
		return createPhrase(str, font11);
	}

	private static Phrase createTitlePhrase(String str)
	{
		return createPhrase(str, font14);
	}

	private static Phrase createDatePhrase(String str)
	{
		return createPhrase(str, font12);
	}

	private static Phrase createTotalPhrase(String str)
	{
		return createPhrase(str, font11);
	}

	private static Phrase createSeperatorPhrase(String str)
	{
		return createPhrase(str, font8);
	}

	private static PdfPCell createFieldCell(String str)
	{
		PdfPCell cell = new PdfPCell();
		cell.setColspan(1);
		cell.setBorder(Cell.NO_BORDER);
		cell.setVerticalAlignment(Cell.ALIGN_MIDDLE);
		cell.setHorizontalAlignment(Cell.ALIGN_CENTER);
		cell.setPhrase(createContentPhrase(str));
		return cell;
	}

	private static PdfPCell createContentCell(String str)
	{
		return createContentCell(str, true);
	}

	private static PdfPCell createContentCell(String str, boolean setBlank)
	{
		PdfPCell cell = new PdfPCell();
		cell.setColspan(1);
		cell.setBorder(Cell.NO_BORDER);
		cell.setVerticalAlignment(Cell.ALIGN_MIDDLE);
		cell.setHorizontalAlignment(Cell.ALIGN_RIGHT);
		cell.setLeft(0);
		if (setBlank && str.equals("0.00"))
		{
			str = "";
		}
		cell.setPhrase(createContentPhrase(str));
		return cell;
	}

	private static PdfPCell createHeaderCell(String str)
	{
		PdfPCell cell = new PdfPCell();
		cell.setColspan(1);
		cell.setBorder(Cell.NO_BORDER);
		cell.setVerticalAlignment(Cell.ALIGN_MIDDLE);
		cell.setHorizontalAlignment(Cell.ALIGN_RIGHT);
		cell.setPhrase(createHeaderPhrase(str));
		return cell;
	}

	private static PdfPCell createBkBlHeaderCell(String str)
	{
		PdfPCell cell = new PdfPCell();
		cell.setColspan(1);
		cell.setBorder(Cell.NO_BORDER);
		cell.setVerticalAlignment(Cell.ALIGN_MIDDLE);
		cell.setHorizontalAlignment(Cell.ALIGN_CENTER);
		cell.setPhrase(createHeaderPhrase(str));
		return cell;
	}

	private static PdfPCell createTotalCell(String str)
	{
		PdfPCell cell = new PdfPCell();
		cell.setColspan(1);
		cell.setBorder(Cell.NO_BORDER);
		cell.setVerticalAlignment(Cell.ALIGN_MIDDLE);
		cell.setHorizontalAlignment(Cell.ALIGN_RIGHT);
		cell.setPhrase(createTotalPhrase(str));
		return cell;
	}

	private static PdfPCell createSeperatorCell(String str, boolean isCenter)
	{
		PdfPCell cell = new PdfPCell();
		cell.setPadding(0);
		cell.setColspan(1);
		cell.setBorder(Cell.NO_BORDER);
		cell.setVerticalAlignment(Cell.ALIGN_MIDDLE);
		if (isCenter)
		{
			cell.setHorizontalAlignment(Cell.ALIGN_CENTER);
		}
		else
		{
			cell.setHorizontalAlignment(Cell.ALIGN_RIGHT);
		}
		cell.setPhrase(createSeperatorPhrase(str));
		return cell;
	}
}
