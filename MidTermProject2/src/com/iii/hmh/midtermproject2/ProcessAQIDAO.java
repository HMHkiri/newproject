package com.iii.hmh.midtermproject2;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class ProcessAQIDAO
 */
@WebServlet("/AQI.do")
public class ProcessAQIDAO extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		//取得設定資料的編碼
		request.setCharacterEncoding("UTF-8");
		//建立儲存ErrorMessage的MAP(方便EL呼叫
		Map<String,String> errorMessage = new HashMap<String,String>();
		//設定rMessage識別字串存在request中
		request.setAttribute("errorMSG", errorMessage);

		//設定整數變數初始值
		int id=0,aqi=0,pm=0;
		//取得四種功能的識別字串(1新增2修改3刪除4查詢)
		String doaction = request.getParameter("doaction");
		//字串轉int方便比較
		int todoaction = Integer.parseInt(doaction);
		
		//Siteid驗證 不可為空(四個功能中)且要為數字
		String Siteid = request.getParameter("Siteid");
		if(Siteid==null||Siteid.trim().length()==0) {
			errorMessage.put("iderror","觀測站ID不能空白");
		}else if(!(ProcessAQIDAO.isNumeric(Siteid))){
			errorMessage.put("iderror","觀測站ID需為整數");
		}else {
			//通過則轉換成整數
			id = Integer.parseInt(Siteid);
		}//Siteid驗證結束
		
		//驗證觀測站名稱 取得觀測站名稱
		String SiteName = request.getParameter("SiteName");
		//在新增修改中需要驗證
		if(todoaction==1||todoaction==2) {
			if(SiteName==null||SiteName.trim().length()==0) {
				errorMessage.put("sitenameerror","觀測站名不可空白");
			}else if(SiteName.length()>3 && 1>=SiteName.length()) {
				errorMessage.put("sitenameerror","觀測站名稱為兩到三中文字");
			}
		for(int j = 0;j<=SiteName.length();j++) {
			if(!(SiteName.matches("[\\u4E00-\\u9FA5]+"))){
				errorMessage.put("sitenameerror","觀測站名稱請輸入中文");
			}
			
		}}//觀測站名稱驗證結束
		
		//取得城市名稱
		String Country =request.getParameter("Country");
		
		//驗證AQI 取得AQI值
		String AQI =request.getParameter("AQI");
		//在新增/修改中 AQI不能為零 且需為整數
		if(todoaction==1||todoaction==2) {
			if(AQI==null||AQI.trim().length()==0) {
				errorMessage.put("aqierror","AQI數值不可空白");
			}else if(!( ProcessAQIDAO.isNumeric(AQI) )) {
				errorMessage.put("aqierror","AQI需為整數");			
			}else {
				//皆通過則轉換成整數
				aqi = Integer.parseInt(AQI);
			}
		}
		
		//取得輸入之狀態
		String Status = request.getParameter("Status");
		
		//驗證pm2.5(新增及修改功能不可為零且需為整數
		String PMT =request.getParameter("pm25");
		if(todoaction==1||todoaction==2) {
			if(PMT==null||PMT.trim().length()==0 ) {
				errorMessage.put("pmerror","PM2.5不可為零");
			}else if(!(ProcessAQIDAO.isNumeric(PMT))) {
				errorMessage.put("pmerror","PM2.5需為整數");
			}else {
				pm =Integer.parseInt(PMT);
			}
		}
		//驗證發布時間
		String ptime = request.getParameter("ptime");
		java.sql.Timestamp date = null;
		if( ptime!=null&& ptime.trim().length()!=0) {
			try {
			//抓取轉換錯誤
			date = Timestamp.valueOf(ptime);
			ptime = ptime+".0";
			if(!(ptime.equalsIgnoreCase(date.toString()))) {
				errorMessage.put("timeerror","請輸入正確日期與時間");
			}
			}catch(IllegalArgumentException e) {
				errorMessage.put("timeerror","請輸入正確日期與時間");
			}
		}
		
		//處理所有錯誤 若有錯誤訊息則跳回原頁面
		if(errorMessage.size()!=0) {//errorMessage有東西表示有錯誤
			errorMessage.put("allerrors","請修正錯誤");
			//跳轉回原頁面
			RequestDispatcher rd = request.getRequestDispatcher("insertAQI.jsp");
	    	rd.forward(request, response);
			return;
		}
		
		AQIBean ab = new AQIBean(id,SiteName,Country,aqi,Status,pm,date);
		HttpSession session =request.getSession();
		session.setAttribute("AQIBean", ab);
		AQIDAO daoaqi = new AQIDAOjdbc();
		//判斷執行新增方法
		if(todoaction==1) {
			try {
				//呼叫新增方法回傳新增筆數
				int exam = daoaqi.insert(ab);
				//新增筆數為1新增成功 跳轉新增頁面
				if(exam==1) {
				session.setAttribute("AQIbean", ab);
				session.setAttribute("status", "新增資料");
				response.sendRedirect("success.jsp");
				}else {
					//新增失敗 傳遞失敗訊息至插入頁面
					request.setAttribute("statusfail", "新增資料失敗");
					RequestDispatcher rd = request.getRequestDispatcher("insertAQI.jsp");
					rd.forward(request, response);
				}
			} catch (SQLException e) {
				request.setAttribute("statusfail", "新增資料失敗");
				RequestDispatcher rd = request.getRequestDispatcher("insertAQI.jsp");
				rd.forward(request, response);
			}finally {
				try {
					daoaqi.closeConn();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			
		}else if(todoaction==2) { //判斷執行修改方法
			try {
				//回傳修改筆數
				int exam = daoaqi.update(ab);
				if(exam ==1) {
				session.setAttribute("AQIbean", ab);
				session.setAttribute("status", "修改資料");
				response.sendRedirect("success.jsp");
				}else {
					request.setAttribute("statusfail", "修改資料失敗");
					RequestDispatcher rd = request.getRequestDispatcher("insertAQI.jsp");
					rd.forward(request, response);
				}
			} catch (SQLException e) {

				e.printStackTrace();
			}finally {
				try {
					daoaqi.closeConn();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}else if(todoaction==3) {
			try {
				ab = daoaqi.findBySiteid(id);
				int exam = daoaqi.delete(id);
				if(exam==1) {
					session.setAttribute("AQIbean", ab);
					session.setAttribute("status", "刪除");
//					RequestDispatcher rd = request.getRequestDispatcher("success.jsp");
//					rd.forward(request, response);
					response.sendRedirect("success.jsp");
				}else {
					request.setAttribute("statusfail", "觀測站資料刪除失敗");
					RequestDispatcher rd = request.getRequestDispatcher("insertAQI.jsp");
					rd.forward(request, response);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}finally {
				try {
					daoaqi.closeConn();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}else if(todoaction==4) {
			try {
				AQIBean rsbean = daoaqi.findBySiteid(id);
				if(rsbean!=null) {
					session.setAttribute("AQIbean", rsbean);
					session.setAttribute("status", "查詢");
//				RequestDispatcher rd = request.getRequestDispatcher("success.jsp");
//				rd.forward(request, response);	
				response.sendRedirect("success.jsp");
				}else {
					request.setAttribute("statusfail", "查無此資料");
					RequestDispatcher rd = request.getRequestDispatcher("insertAQI.jsp");
					rd.forward(request, response);
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally {
				try {
					daoaqi.closeConn();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			
		}
		
	}
	public static boolean isNumeric(String str){
		   for (int i=0;i<str.length();i++){  
		      if (!Character.isDigit(str.charAt(i))){
		    	  return false;
		      }
		   }
		   return true;
		}
}


