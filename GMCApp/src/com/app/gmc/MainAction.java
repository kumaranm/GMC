package com.app.gmc;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.app.gmc.common.GMC;
import com.app.gmc.common.GMCCostants;
import com.app.gmc.common.GMCHelper;
import com.app.gmc.db.GMCDBApp;
import com.app.gmc.db.GMCDBVo;

public class MainAction implements Action
{

	private GMCHelper helper = GMCHelper.getInstance();
	public String process(HttpServletRequest request, HttpServletResponse response) throws ServletException
	{
		String cmptitle = helper.getCompany(request);
		String date = helper.getDate(request);
		String act = helper.removeSlash(request.getParameter(GMC.PARAMS.ACT));
		String backupfile = helper.removeSlash(request.getParameter(GMC.PARAMS.FILEPATH));
		if (act != null && act.equals(GMC.VALUES.CHANGE))
		{
			request.setAttribute(GMC.ATTR.MSG, GMCCostants.CHANGES_APPLIED);
		}
		else if (act != null && act.equals(GMC.VALUES.DELETEALL))
		{
			boolean res = deleteAllRecords(request);
			if (res)
			{
				request.setAttribute(GMC.ATTR.MSG, GMCCostants.RECORDS_DELETED + date);
			}
			else
			{
				request.setAttribute(GMC.ATTR.MSG, GMCCostants.DELETION_FAILED + date + GMCCostants.TRY_AGAIN);
			}
		}
		else if(act!= null && act.equals(GMC.VALUES.BACKUP))
		{
			boolean res = backupDB(backupfile);
			request.setAttribute(GMC.ATTR.MSG, ""+res);
			return GMC.JSP.CHANGECMP + GMC.PARAMS._C + cmptitle + GMC.PARAMS._D + date;
		}
		/*else if(act!= null && act.equals(GMC.VALUES.RESTORE))
		{
			boolean res = restoreDB(backupfile);
			request.setAttribute(GMC.ATTR.MSG, ""+res);
			return GMC.JSP.CHANGECMP + GMC.PARAMS._C + cmptitle + GMC.PARAMS._D + date;
		}*/
		return GMC.JSP.MAIN + GMC.PARAMS._C + cmptitle + GMC.PARAMS._D + date;
	}

	/*private boolean restoreDB(String backupfile)
	{
		StringBuffer res = new StringBuffer();
		if (backupfile != null)
		{
			try
			{
				// Create file 
				FileReader fstream = new FileReader(backupfile+"GMC_Data_Backup_" + GMCHelper.getInstance().convDate2String(new Date())
						+ ".sql");
				BufferedReader br = new BufferedReader(fstream);
				String str;
			    while ((str = br.readLine()) != null) {
					res.append(str);
				}
				//Close the output stream
				br.close();
			}
			catch (Exception e)
			{  //Catch exception if any
				System.err.println("Error: " + e.getMessage());
			}
			return GMCDBApp.restoreDB();
		}
		return false;
	}*/

	private boolean deleteAllRecords(HttpServletRequest request)
	{
		GMCDBVo vo = new GMCDBVo();
		vo.setCompany(helper.getCompany(request));
		String date = helper.getDate(request);
		vo.setDt(helper.conv2SqlDate(date));
		return GMCDBApp.deleteAllRecordsByDate(vo);
	}
	
	private boolean backupDB(String backupfile)
	{
		String res = GMCDBApp.backupDB();
		if(backupfile == null) 
		{
			backupfile = "";
		}
		if (res != null)
		{
			try
			{
				// Create file 
				FileWriter fstream = new FileWriter(backupfile+"GMC_Data_Backup_" + GMCHelper.getInstance().convDate2String(new Date())
						+ ".sql");
				BufferedWriter out = new BufferedWriter(fstream);
				out.write(res);
				//Close the output stream
				out.close();
				return true;
			}
			catch (Exception e)
			{//Catch exception if any
				System.err.println("Error: " + e.getMessage());
			}
			return false;
		}
		return false;
	}
}
