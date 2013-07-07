/**
 * 
 */
package com.app.gmc;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.app.gmc.common.GMCCostants;
import com.app.gmc.common.GMCHelper;
import com.app.gmc.db.GMCDBVo;
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
public class NewPrintReport {
	
//	private static final float[] widths = { 9, 6, 17, 17, 17, 15, 17, 19 };
	private static final float[] widths = { 6, 5, 10,9,10,9, 10,9, 10,9, 10, 12 };
	private static Font font11, font12, font14, font8;
	private static int RECORDS_PER_PAGE = 44; 
	
	public static String printReport()
	{
		String company = GMCCostants.GMC;
		String date = "13-7-2011";
		
		ArrayList<GMCDBVo> dataList = new ArrayList<GMCDBVo>(0);
		for (int i = 1; i <= RECORDS_PER_PAGE * 3 ; i++) {
			GMCDBVo vo = new GMCDBVo();
			vo.setBookNumber("W21");
			vo.setBillNumber(i);
			
			if (i % 35 == 0) 
			{
				dataList.add(vo);
				continue;
			}
			
			//vo.setSales125(12*i);
			//vo.setSales4(4*i);
			
			vo.setSales145(14*i);
			vo.setSales5(5*i);
			
			GMCHelper.getInstance().calculateTax(vo);
			
			if (i % 24 != 0) 
			{
				vo.setNtas(10*i);
			}
			vo.setTotal(/*vo.getSales125() + vo.getSales4()*/ + vo.getNtas() + /*vo.getTax125() + vo.getTax4()*/
					+ vo.getSales145() + vo.getSales5() + vo.getTax145() + vo.getTax5());
			dataList.add(vo);
		}
		
		
		// step 1: creation of a document-object
//		Document document = new Document(PageSize.A4, -125, -125, 3, 30);
//		Document document = new Document(PageSize.A4, -80, -70, 3, 30); //LRTB 
//		Document document = new Document(PageSize.A4, -40, -40, 3, 30); //LRTB 
		Document document = new Document(PageSize.A4, 0, 0, 3, 30); //LRTB 
		File f = new File("Vithu_GMC" + "_" + date + ".pdf");
		FileOutputStream fs = null;
		try {
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
		} catch (DocumentException de) {
			System.err.println(de.getMessage());
		} catch (IOException ioe) {
			System.err.println(ioe.getMessage());
		}

		return "";
	}
	
	public static void main(String[] args) {
		printReport();
	}
	
	public static String getFileName(String company, String dt) {
		String temp = GMCCostants.TITLE + "_" + company + "_"+ dt;
		return temp;
	}
	
	public static Document addMetaData(final Document doc, String company, String dt) {
		doc.addTitle(GMCCostants.TITLE);
		doc.addAuthor("Kumaran M");
		doc.addSubject(getFileName(company, dt));
		doc.addCreator("GMCApp");
		return doc;
	}

	public static Element getContent(String company, List<GMCDBVo> dataList, String dt) throws DocumentException {
		
//		FontFactory.register("c:/windows/fonts/DOTMATRI.ttf");
//		FontFactory.getFont("Dot Matrix", BaseFont.WINANSI, 8);
		font8 = new Font(Font.COURIER, 6); 
		font11 = new Font(Font.TIMES_ROMAN, 11); 
		font12 = new Font(Font.TIMES_ROMAN, 12);
		font14 = new Font(Font.TIMES_ROMAN, 14);
		
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

	private static PdfPTable getReport(List<GMCDBVo> dataList, String company, String dt) throws DocumentException {
		GMCHelper g = GMCHelper.getInstance();
		PdfPTable reporttable = new PdfPTable(widths.length);
		reporttable.setHeaderRows(4);
		reporttable.setSplitRows(false);
		reporttable.setComplete(true);
		reporttable.setTotalWidth(widths);
		reporttable.setWidthPercentage(100);
//		reporttable.setWidths(widths);
		
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
		reporttable.addCell(createBkBlHeaderCell("Bk No"));
		reporttable.addCell(createBkBlHeaderCell("Bl No"));
		/*reporttable.addCell(createHeaderCell(GMCCostants.FIELD1));
		reporttable.addCell(createHeaderCell(GMCCostants.FIELD11));
		reporttable.addCell(createHeaderCell(GMCCostants.FIELD3));
		reporttable.addCell(createHeaderCell(GMCCostants.FIELD13));*/
		//CODE for 5% and 14.5%
		reporttable.addCell(createHeaderCell(GMCCostants.NEWFIELD1));
		reporttable.addCell(createHeaderCell(GMCCostants.NEWFIELD11));
		reporttable.addCell(createHeaderCell(GMCCostants.NEWFIELD3));
		reporttable.addCell(createHeaderCell(GMCCostants.NEWFIELD13));
		
		reporttable.addCell(createHeaderCell("Exmptd Sales"));
		reporttable.addCell(createHeaderCell(GMCCostants.TOTAL));
		
		addSeperatorRow(reporttable);
		
		int slno = 0;
		int page = 1;
		double grandTotal = 0;
		double sales125total = 0, tax125total = 0, sales145total = 0, tax145total = 0;
		double sales4total = 0, tax4total = 0, sales5total = 0, tax5total = 0;
		double ntastotal = 0;
		for (GMCDBVo vo : dataList) {
			slno = slno + 1;
			grandTotal = grandTotal + vo.getTotal();
	 		
	 		//individual totals
	 		//sales125total = sales125total + vo.getSales125();
	 		//tax125total = tax125total + vo.getTax125();
	 		//sales4total = sales4total + vo.getSales4();
	 		//tax4total = tax4total + vo.getTax4();
	 		//CODE for 5% and 14.5%
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
	 			cancelled.setPhrase(createContentPhrase("- - - CANCELLED BILL - - -"));
	 			reporttable.addCell(cancelled);
	 		}
	 		else
	 		{
				//reporttable.addCell(createContentCell(g.formatDouble(vo.getSales125())));
				//reporttable.addCell(createContentCell(g.formatDouble(vo.getTax125())));
				//reporttable.addCell(createContentCell(g.formatDouble(vo.getSales4())));
				//reporttable.addCell(createContentCell(g.formatDouble(vo.getTax4())));
				//CODE for 5% and 14.5%
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
				total.setPhrase(createContentPhrase("SUB TOTAL"));
				reporttable.addCell(total);
				reporttable.addCell(createContentCell(g.formatDouble(sales125total)));
				reporttable.addCell(createContentCell(g.formatDouble(tax125total)));
				reporttable.addCell(createContentCell(g.formatDouble(sales4total)));
				reporttable.addCell(createContentCell(g.formatDouble(tax4total)));
				//CODE for 5% and 14.5%
				reporttable.addCell(createContentCell(g.formatDouble(sales145total)));
				reporttable.addCell(createContentCell(g.formatDouble(tax145total)));
				reporttable.addCell(createContentCell(g.formatDouble(sales5total)));
				reporttable.addCell(createContentCell(g.formatDouble(tax5total)));
				
				reporttable.addCell(createContentCell(g.formatDouble(ntastotal)));
				reporttable.addCell(createContentCell(g.formatDouble(grandTotal)));
			}
		}
		
		if (slno != RECORDS_PER_PAGE || dataList.size() % RECORDS_PER_PAGE == 0)
		{
			addSeperatorRow(reporttable);

			PdfPCell total = new PdfPCell();
			total.setBorder(Cell.NO_BORDER);
			total.setColspan(2);
			total.setHorizontalAlignment(1);
			total.setPhrase(createContentPhrase("TOTAL"));
			reporttable.addCell(total);
			reporttable.addCell(createContentCell(g.formatDouble(sales125total)));
			reporttable.addCell(createContentCell(g.formatDouble(tax125total)));
			reporttable.addCell(createContentCell(g.formatDouble(sales4total)));
			reporttable.addCell(createContentCell(g.formatDouble(tax4total)));
			//CODE for 5% and 14.5%
			reporttable.addCell(createContentCell(g.formatDouble(sales145total)));
			reporttable.addCell(createContentCell(g.formatDouble(tax145total)));
			reporttable.addCell(createContentCell(g.formatDouble(sales5total)));
			reporttable.addCell(createContentCell(g.formatDouble(tax5total)));
			
			reporttable.addCell(createContentCell(g.formatDouble(ntastotal)));
			reporttable.addCell(createContentCell(g.formatDouble(grandTotal)));
		}
		return reporttable;
	}
	
	public static Element getSummaryContent(String company, Map<String,Double> dataMap, String fdt, String tdt, boolean all)
    throws DocumentException
	{
	    //int TOTAL_COLS = 4;
	    font8 = new Font(8);
	    font11 = new Font(11);
	    font12 = new Font(12);
	    font14 = new Font(14);
	    PdfPTable maintable = new PdfPTable(1);
	    maintable.setWidthPercentage(100);
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
//		reporttable.setWidths(new int[] { 26, 25, 18, 31 });
		reporttable.setWidths(new int[] { 20, 30, 20, 30 });
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
	    if(all)
	        date.setPhrase(createDatePhrase("SUMMARY: ALL RECORDS"));
	    else
	        date.setPhrase(createDatePhrase((new StringBuilder("SUMMARY: ")).append(fdt).append("\t\tTo\t\t").append(tdt).toString()));
	    reporttable.addCell(date);
	    reporttable.addCell(createBlankCell());
	    reporttable.addCell(createBlankCell());
	    reporttable.addCell(createBlankCell());
	    reporttable.addCell(createBlankCell());
	    reporttable.addCell(createBlankCell());
	    reporttable.addCell(createHeaderCell("12.5% I Sales Amount"));
	    reporttable.addCell(createTotalCell(g.formatDouble(((Double)dataMap.get("S125")).doubleValue())));
	    reporttable.addCell(createBlankCell());
	    reporttable.addCell(createBlankCell());
	    reporttable.addCell(createHeaderCell("12.5% I Sales Tax"));
	    reporttable.addCell(createTotalCell(g.formatDouble(((Double)dataMap.get("ST125")).doubleValue())));
	    reporttable.addCell(createBlankCell());
	    reporttable.addCell(createBlankCell());
	    reporttable.addCell(createHeaderCell("4% I Sales Amount"));
	    reporttable.addCell(createTotalCell(g.formatDouble(((Double)dataMap.get("S4")).doubleValue())));
	    reporttable.addCell(createBlankCell());
	    reporttable.addCell(createBlankCell());
	    reporttable.addCell(createHeaderCell("4% I Sales Tax"));
	    reporttable.addCell(createTotalCell(g.formatDouble(((Double)dataMap.get("ST4")).doubleValue())));
	    reporttable.addCell(createBlankCell());
	    reporttable.addCell(createBlankCell());
	    
	    //CODE for 5% and 14.5%
	    reporttable.addCell(createHeaderCell("14.5% I Sales Amount"));
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
	    
	    reporttable.addCell(createHeaderCell("Exempted Sales"));
	    reporttable.addCell(createTotalCell(g.formatDouble(((Double)dataMap.get("NT")).doubleValue())));
	    reporttable.addCell(createBlankCell());
	    reporttable.addCell(createBlankCell());
	    reporttable.addCell(createBlankCell());
	    reporttable.addCell(createBlankCell());
	    reporttable.addCell(createBlankCell());
	    reporttable.addCell(createBlankCell());
	    reporttable.addCell(createHeaderCell("TOTAL SALES"));
	    reporttable.addCell(createTotalCell(g.formatDouble(((Double)dataMap.get("TOT")).doubleValue())));
	    reporttable.addCell(createBlankCell());
	    report.addElement(reporttable);
	    maintable.addCell(report);
	    return maintable;
	}
	
	private static void addSeperatorRow(PdfPTable reporttable) {
		String sep5 =  "- -";
		String sep8 =  "----------";
		String sep9 =  "----------";
		String sep10 = "-----------";
		String sep11 = "-------------";
		for (int j = 0; j < widths.length; j++) {
			if (j == 0 || j == 1) {
				reporttable.addCell(createSeperatorCell(sep5,true));
				continue;
			}
			else if (j == 2 || j == 4 || j == 6 || j == 8 || j == 10)
			{
				reporttable.addCell(createSeperatorCell(sep10,false));
				continue;
			}
			else if (j == 3 || j == 5 || j ==9 || j == 7)
			{
				reporttable.addCell(createSeperatorCell(sep9,false));
				continue;
			}
			/*else if () 
			{
				reporttable.addCell(createSeperatorCell(sep8,false));
				continue;
			}*/
			else if (j == 11)
			{
				reporttable.addCell(createSeperatorCell(sep11,false));
				continue;
			}
			//{ 5, 5, 10,9,10,8, 10,9, 10,8, 9, 10 };	
//			reporttable.addCell(createSeperatorCell(sep2,false));
		}
	}
	
	private static void addBlankRow(PdfPTable reporttable) {
		for (int j = 0; j < 8; j++) {
			reporttable.addCell(createBlankCell());
		}
	}

	private static PdfPCell createBlankCell() 
	{
		
		//used
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
		//used
		return createPhrase(str, font11);
	}
	
	private static Phrase createContentPhrase(String str)
	{
		//used
		return createPhrase(str, font11);
	}
	
	private static Phrase createTitlePhrase(String str)
	{
		//used
		return createPhrase(str, font14);
	}
	
	private static Phrase createDatePhrase(String str)
	{
		//used
		return createPhrase(str, font12);
	}
	
	private static Phrase createTotalPhrase(String str)
	{
		//used
		return createPhrase(str, font11);
	}
	
	private static Phrase createSeperatorPhrase(String str)
	{
		return createPhrase(str, font11);
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
		cell.setNoWrap(true);
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
		//used
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
		cell.setNoWrap(false);
		cell.setBorder(Cell.NO_BORDER);
		cell.setVerticalAlignment(Cell.ALIGN_MIDDLE);
		cell.setHorizontalAlignment(Cell.ALIGN_CENTER);
		cell.setPhrase(createHeaderPhrase(str));
		return cell;
	}
	
	private static PdfPCell createTotalCell(String str)
	{
		//used
		PdfPCell cell = new PdfPCell();
		cell.setColspan(1);
		cell.setNoWrap(true);
		cell.setBorder(Cell.NO_BORDER);
		cell.setVerticalAlignment(Cell.ALIGN_MIDDLE);
		cell.setHorizontalAlignment(Cell.ALIGN_RIGHT);
		cell.setPhrase(createTotalPhrase(str));
		return cell;
	}
	
	
	private static PdfPCell createSeperatorCell(String str, boolean isCenter)
	{
		PdfPCell cell = new PdfPCell();
//		cell.setPadding(0);
		cell.setColspan(1);
		cell.setBorder(Cell.NO_BORDER);
		cell.setVerticalAlignment(Cell.ALIGN_MIDDLE);
		if (isCenter) {
			cell.setHorizontalAlignment(Cell.ALIGN_CENTER);
		} else {
			cell.setHorizontalAlignment(Cell.ALIGN_RIGHT);
		}
		cell.setPhrase(createSeperatorPhrase(str));
		return cell;
	}
}
