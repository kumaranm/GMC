<%@page import="com.app.gmc.db.SummaryVo"%>
<%@page import="com.app.gmc.common.GMC"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.Map"%>
<%@page import="com.app.gmc.common.GMCHelper"%>
<%@page import="com.app.gmc.db.GMCDBApp"%>
<%@page import="com.app.gmc.db.GMCDBVo"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.app.gmc.common.GMCCostants"%>
<%@page import="com.app.gmc.PrintReport"%>
<%@page import="com.app.gmc.NewPrintReport"%>
<%@page import="com.lowagie.text.*"%>
<%@page import="com.lowagie.text.pdf.*"%>
<%@page import="java.io.ByteArrayOutputStream"%>
	
<%
		String cmptitle = request.getParameter("c");
		String date = request.getParameter("d");
		String action = request.getParameter("a");
		String subact = request.getParameter("sa");
		String fdate = request.getParameter("fd");
		String tdate = request.getParameter("td");
		String mon = request.getParameter("m");
		
		String company = "";
		if (cmptitle.equals(GMCCostants.GMC)) {
			company = GMCCostants.XGMC;
		} else {
			company = GMCCostants.XSKC;
		}

		if (action != null && action.equalsIgnoreCase(GMC.VALUES.REPORT)) {
			ArrayList<GMCDBVo> datas = (ArrayList<GMCDBVo>) GMCDBApp
					.getAllRecords(cmptitle, GMCHelper.getInstance()
							.conv2SqlDate(date));
			Document document = new Document(PageSize.A4);
			//document.setMargins(-125, -125, 3, 30);//L,R,T,B
			document.setMargins(-30, -30, 3, 30);//L,R,T,B
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			PdfWriter.getInstance(document, baos);
			document.open(); 
			document.newPage();
			document.add(PrintReport.getContent(company, datas, date));
			document = PrintReport.addMetaData(document, company, date);
			document.close();
			String fname = PrintReport.getFileName(cmptitle, date);
			response.setHeader("Content-Disposition",
					"attachment; filename=" + fname + ".pdf");
			response.setContentType("application/pdf");
			response.setContentLength(baos.size());
			ServletOutputStream out2 = response.getOutputStream();
			baos.writeTo(out2);
			out2.flush();
		} else if (action != null && action.equalsIgnoreCase(GMC.VALUES.SUMMARY)) {
			
			Map<String,Double> datas = null; 
			boolean all = false;
			if (subact.equalsIgnoreCase(GMC.VALUES.RANGE))
			{
				datas = (HashMap<String, Double>) GMCDBApp
				.getTotalsByDateRange(cmptitle, GMCHelper.getInstance()
						.conv2SqlDate(fdate), GMCHelper.getInstance()
						.conv2SqlDate(tdate));
			}else{
				all = true;
				datas = (HashMap<String, Double>) GMCDBApp
				.getAllTotals(cmptitle);
			} 
			
			
			Document document = new Document(PageSize.A4);
			//document.setMargins(-125, -125, 3, 30);//L,R,T,B
			document.setMargins(25, 25, 3, 30);//L,R,T,B
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			PdfWriter.getInstance(document, baos);
			document.open();
			document.newPage();
			document.add(PrintReport.getSummaryContent(company, datas, fdate, tdate, all ));
			document = PrintReport.addMetaData(document, company, fdate);
			document.close();
			String fname = PrintReport.getFileName(cmptitle, GMC.VALUES.SUMMARY);
			response.setHeader("Content-Disposition",
					"attachment; filename=" + fname + ".pdf");
			response.setContentType("application/pdf");
			response.setContentLength(baos.size());
			ServletOutputStream out2 = response.getOutputStream();
			baos.writeTo(out2);
			out2.flush();
		}
		 else if (action != null && action.equalsIgnoreCase(GMC.VALUES.SUMMARYMONTH)) {
			
			 Map<String, SummaryVo> datas = null; 
			boolean all = false;
			if (subact.equalsIgnoreCase(GMC.VALUES.RANGE))
			{
				datas = GMCDBApp
				.getByMonthByDateRange(cmptitle, GMCHelper.getInstance()
						.conv2SqlDate(fdate), GMCHelper.getInstance()
						.conv2SqlDate(tdate));
			}else{
				all = true;
				datas = GMCDBApp
				.getAllByMonth(cmptitle);
			} 
			
			
			Document document = new Document(PageSize.A4);
			//document.setMargins(-125, -125, 3, 30);//L,R,T,B
			document.setMargins(25, 25, 3, 30);//L,R,T,B
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			PdfWriter.getInstance(document, baos);
			document.open();
			document.newPage();
			document.add(PrintReport.getSummaryContentByMonth(company, datas, fdate, tdate, all ));
			document = PrintReport.addMetaData(document, company, fdate);
			document.close();
			String fname = PrintReport.getFileName(cmptitle, "Summary");
			response.setHeader("Content-Disposition",
					"attachment; filename=" + fname + ".pdf");
			response.setContentType("application/pdf");
			response.setContentLength(baos.size());
			ServletOutputStream out2 = response.getOutputStream();
			baos.writeTo(out2);
			out2.flush();
		}
		 else if (action != null && action.equalsIgnoreCase(GMC.VALUES.MONTH)) {
				
			 Map<String, SummaryVo> datas = null; 
			boolean all = false;
			if (subact.equalsIgnoreCase(GMC.VALUES.RANGE))
			{
				datas = GMCDBApp
				.getByMonthByDateRangeNMonth(cmptitle, GMCHelper.getInstance()
						.conv2SqlDate(fdate), GMCHelper.getInstance()
						.conv2SqlDate(tdate),mon);
			}else{
				all = true;
				datas = GMCDBApp
				.getAllByMonth(cmptitle,mon);
			} 
			
			
			Document document = new Document(PageSize.A4);
			//document.setMargins(-125, -125, 3, 30);//L,R,T,B
			document.setMargins(25, 25, 3, 30);//L,R,T,B
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			PdfWriter.getInstance(document, baos);
			document.open();
			document.newPage();
			document.add(PrintReport.getSummaryContentPerMonth(company, datas, fdate, tdate, all, mon));
			document = PrintReport.addMetaData(document, company, fdate);
			document.close();
			String fname = PrintReport.getFileName(cmptitle, "_"+mon +"_Summary");
			response.setHeader("Content-Disposition",
					"attachment; filename=" + fname + ".pdf");
			response.setContentType("application/pdf");
			response.setContentLength(baos.size());
			ServletOutputStream out2 = response.getOutputStream();
			baos.writeTo(out2);
			out2.flush();
		}
	%>