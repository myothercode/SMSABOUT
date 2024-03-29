package cn.server.Handler;

import cn.common.DBNUMINFO;
import cn.common.MyException;
import cn.panshihao.mm7send.SendMM7;
import cn.panshihao.mm7send.SendSMC;
import cn.server.bean.*;
import cn.server.dao.*;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;



public class ActionHandler {
	private static ActionHandler instance = null;
	
	public static synchronized ActionHandler getInstance(){

    	if(instance == null){
	
    		instance = new ActionHandler();
	
    	}
    	return instance;
    }

	public ActionHandler() {
		// TODO Auto-generated constructor stub
	}
	
	public String allHandler(String requestStr,
			HttpServletRequest request, HttpServletResponse response){
		JSONObject requestJSON = null;
		String responseStr = "";
		int cod = 0;
		try {
			requestJSON = new JSONObject(requestStr);
		} catch (JSONException e) {
			
			e.printStackTrace();
			
			return getErrorJson("请求字符串转化为JSON对象出现异常！！！").toString();
		}
		try {
			cod = requestJSON.getInt("cod");
		} catch (JSONException e) {
			
			e.printStackTrace();
			return getErrorJson("handler解析cod出现异常！！！").toString();
		}
		
		if(cod != 101){
			if(request.getSession().getAttribute("User") == null){
				return getErrorJson("用户未登录或登录超时，请重新登陆！").toString();
			}
		}
		
		try {
			switch (cod) {
			case 101:
				responseStr = userLogin(requestJSON, request, response);
				break;
			case 102:
				responseStr = userLogout(requestJSON, request, response);
				break;
			case 103:
				responseStr = editPassWord(requestJSON, request, response);
				break;
			case 104:
				responseStr = getUserList(requestJSON, request, response);
				break;
			case 105:
				responseStr = addUser(requestJSON);
				break;
			case 106:
				responseStr = deleteUser(requestJSON, request, response);
				break;
			case 107:
				responseStr = getSendTaskList(requestJSON, request, response);
				break;
			case 108:
				responseStr = newSendTask(requestJSON, request, response);
				break;
			case 109:
				responseStr = getFailNumberListByTaskID(requestJSON, request, response);
				break;
			case 110:
				responseStr = getContentByTaskID(requestJSON, request, response);
				break;
			case 111:
				responseStr = getSmcTaskList(requestJSON, request, response);
				break;
			case 112:
				responseStr = newSmcTask(requestJSON, request, response);
				break;
			case 113:
				responseStr = getFailSmcNumberListByTaskID(requestJSON, request, response);
				break;
			default:
				break;
			}
		} catch (Exception e) {
			
			e.printStackTrace();
			return getErrorJson(e.getMessage(), cod).toString();
		}
		return responseStr;
	}
	
	private static JSONObject getErrorJson(String errorMsg){
		JSONObject errorjson = new JSONObject();
		JSONObject pld = new JSONObject();
		try {
			errorjson.put("cod", 100);
			errorjson.put("res", false);
			pld.put("errorMsg", errorMsg);
			errorjson.put("pld", pld);
		} catch (JSONException e) {
			
			e.printStackTrace();
		}
		return errorjson;
	}
	private static JSONObject getErrorJson(String errorMsg, int cod){
		JSONObject errorjson = new JSONObject();
		JSONObject pld = new JSONObject();
		try {
			errorjson.put("cod", cod);
			errorjson.put("res", false);
			pld.put("errorMsg", errorMsg);
			errorjson.put("pld", pld);
		} catch (JSONException e) {
			
			e.printStackTrace();
		}
		return errorjson;
	}
	private static JSONObject getResponseJson(int cod,boolean res,JSONObject pld){
		JSONObject responseJson = new JSONObject();
		try {
			responseJson.put("cod", cod);
			responseJson.put("res", res);
			responseJson.put("pld", pld);
		} catch (JSONException e) {
			
			e.printStackTrace();
		}
		return responseJson;
	}
	
	public String addUser(JSONObject requestJSON)throws Exception{
		JSONObject prm = null;
		int cod = 0;
		UserDAO userDao = new UserDAO();
		try {
			cod = requestJSON.getInt("cod");
			prm = requestJSON.getJSONObject("prm");
			String userName = prm.getString("UserName");
			String passWord = prm.getString("PassWord");
			int grade = prm.getInt("grade");
			if(userDao.checkUserExist(userName)){
				return getErrorJson("用户名已存在", cod).toString();
			}else{
				userDao.adduser(userName, passWord, grade);
			}
		} catch (JSONException e) {
			
			e.printStackTrace();
			throw e;
		}
		
		return getResponseJson(cod, true, null).toString();
	}
	
	public String userLogin(JSONObject requestJSON,
			HttpServletRequest request, HttpServletResponse response)throws Exception{
		int cod = 0;
		JSONObject pld = null;
		try {
			UserDAO userDao = new UserDAO();
			cod = requestJSON.getInt("cod");
			JSONObject prm = requestJSON.getJSONObject("prm");
			String userName = prm.getString("UserName");
			String passWord = prm.getString("PassWord");
			User user = userDao.getUserInfoByUserName(userName);
			if( user == null){
				return getErrorJson("用户不存在", cod).toString();
			}else{
				if(0!=user.getPassword().compareTo(passWord)){
					return getErrorJson("密码错误", cod).toString();
				}
			}
			userDao.updateLoginTime(user.getId());
			request.getSession().setAttribute("User", user);
			pld = new JSONObject();
			pld.put("LastLoginTime", user.getLastLoginTime());
		} catch (Exception e) {
			
			e.printStackTrace();
			throw new MyException("用户登陆异常", e);
		}
		return getResponseJson(cod,true, pld).toString();
	}
	
	public String userLogout(JSONObject requestJSON,
			HttpServletRequest request, HttpServletResponse response)throws Exception{
		int cod = requestJSON.getInt("cod");
		request.getSession().removeAttribute("User");
		return getResponseJson(cod, true, null).toString();
	}
	
	public String deleteUser(JSONObject requestJSON,
			HttpServletRequest request, HttpServletResponse response)throws Exception{
		UserDAO userDao = new UserDAO();
		int cod = 0 ;
		try {
			cod = requestJSON.getInt("cod");
			JSONObject prm = requestJSON.getJSONObject("prm");
			int id = prm.getInt("UserID");
			userDao.removeUser(id);
		} catch (Exception e) {
			
			e.printStackTrace();
			throw new MyException("删除用户异常！", e);
		}
		return getResponseJson(cod, true, null).toString();
	}
	
	public String editPassWord(JSONObject requestJSON,
			HttpServletRequest request, HttpServletResponse response)throws Exception{
		int cod = 0;
		UserDAO userDao = new UserDAO();
		String newPass = "";
		User user = new User();
		try {
			cod = requestJSON.getInt("cod");
			JSONObject prm = requestJSON.getJSONObject("prm");
			String pass = prm.getString("PassWord");
			newPass = prm.getString("NewPassWord");
			String username = ((User)request.getSession().getAttribute("User")).getUsername();
			user = userDao.getUserInfoByUserName(username);
			if(0!=pass.compareTo(user.getPassword())){
				return getErrorJson("原密码错误", cod).toString();
			}
		} catch (Exception e) {
			
			e.printStackTrace();
			throw new MyException("修改密码异常！", e);
			
		}
		userDao.editPassWord(user.getId(), newPass);
		return getResponseJson(cod, true, null).toString();
	}
	
	public String getUserList(JSONObject requestJSON,
			HttpServletRequest request, HttpServletResponse response)throws Exception{
		int cod = 0;
		List<User> list = null;
		UserDAO userDao = new UserDAO();
		JSONObject pld = new JSONObject();
		JSONArray ja = new JSONArray();
		cod = requestJSON.getInt("cod");
		list = userDao.getUserList();
		for (int i = 0; i < list.size(); i++) {
			User user = list.get(i);
			JSONObject temp = new JSONObject();
			temp.put("UserID", user.getId());
			temp.put("UserName", user.getUsername());
			temp.put("grade", user.getGrade());
			temp.put("LastLoginTime", user.getLastLoginTime());
			ja.put(temp);
		}
		pld.put("UserList", ja);
		return getResponseJson(cod, true, pld).toString();
	}
	
	public String newSendTask(JSONObject requestJSON,
			HttpServletRequest request, HttpServletResponse response)throws Exception{
		
		System.out.println("json -> "+requestJSON.toString());
		
		
		boolean bool = false;
		int cod = 0;
		int numberCount = 0;
		int taskID = 0;
		int successCount = 0;
		int failCount = 0;
		
		List<String> customNumberList = new ArrayList<String>();
		String customNumberstr = "";
		List<Content> contentList = new ArrayList<Content>();
		JSONObject prm = new JSONObject();
		SendTask sendTask = new SendTask();
		SendMM7 sendMM7 = null;
		SendTaskDAO sendTaskDao = new SendTaskDAO();
		DBNumber dbNumberDao = new DBNumber();
		ContentDAO contentDAO = new ContentDAO();
		numberCount = dbNumberDao.getAllCount();
		int allPageNum = 0;
		
		if(numberCount/DBNUMINFO.MAX_SEND_MM7_NUM == 0 || numberCount == DBNUMINFO.MAX_SEND_MM7_NUM){
			allPageNum = 1;
		}
		if(numberCount%DBNUMINFO.MAX_SEND_MM7_NUM == 0 && numberCount > DBNUMINFO.MAX_SEND_MM7_NUM){
			allPageNum = numberCount/DBNUMINFO.MAX_SEND_MM7_NUM;
		}
		if(numberCount%DBNUMINFO.MAX_SEND_MM7_NUM != 0 && numberCount > DBNUMINFO.MAX_SEND_MM7_NUM){
			allPageNum = numberCount/DBNUMINFO.MAX_SEND_MM7_NUM + 1;
		}
		
		//numberList = dbNumberDao.getToNumberList(new MyLimit(pageNum, numberCount));
		try {
			cod = requestJSON.getInt("cod");
			prm = requestJSON.getJSONObject("prm");
			String name = prm.getString("name");
			String subject = prm.getString("subject");
			
			JSONArray customnumber = prm.getJSONArray("CustomNumber");
			JSONArray contentArray = prm.getJSONArray("content");
			
			for (int i = 0; i < customnumber.length(); i++) {
				customNumberList.add(customnumber.getString(i));
			}
			
			
			
			
			StringBuilder sb = new StringBuilder(); 
			if(customNumberList != null  && customNumberList.size()>0){
				for (int i = 0; i < customNumberList.size(); i++) {  
			        if (i < customNumberList.size() - 1) {  
			            sb.append(customNumberList.get(i) + ",");  
			        } else {  
			            sb.append(customNumberList.get(i));  
			        }  
			    }  
			}
			customNumberstr = sb.toString();
			sendTask.setName(name);
			sendTask.setSubject(subject);
			sendTask.setToCount(numberCount+customnumber.length());
			sendTask.setCustomTo(customNumberstr);
			sendTask.setState(1);
			
			taskID = sendTaskDao.insertSendTask(sendTask);//新建任务
			
			for (int i = 0; i < contentArray.length(); i++) {
				JSONObject temp = contentArray.getJSONObject(i);
				Content content = new Content();
				if(temp.getInt("Type") ==1){
					content.setContentByte(temp.getString("Text"));
				}else if(temp.getInt("Type") ==2){
					content.setContentByte(temp.getString("FilePath"));
				}
				content.setContentType(temp.getInt("Type"));
				content.setSort(temp.getInt("sort"));
				content.setSendTaskId(taskID);
				contentDAO.insertContent(content);//保存内容
				contentList.add(content);
			}
			
			//分次发送
			for (int i = 1; i <= allPageNum; i++) {
				List<String> toNumbers = dbNumberDao.getToNumberList(new MyLimit(i, DBNUMINFO.MAX_SEND_MM7_NUM));
				sendMM7 = new SendMM7(toNumbers, contentList, subject);
				if(sendMM7.send(request.getSession().getServletContext())==true){
					bool = true;
					successCount = successCount+toNumbers.size();
				}else{
					failCount = failCount+toNumbers.size();
				}
			}
			//发送定制号码
			/*sendMM7 = new SendMM7(customNumberList, contentList, subject);
			if(sendMM7.send(request.getSession().getServletContext())==true){
				bool = true;
				successCount = successCount+customNumberList.size();
			}else{
				failCount = failCount+customNumberList.size();
			}*/
			
			
			if(bool){
				sendTaskDao.updateTask(taskID, successCount, failCount, 2);
			}else{
				sendTaskDao.updateTask(taskID, successCount, failCount, 3);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new MyException("新建彩信任务异常", e);
		}
		
		return getResponseJson(cod, bool, null).toString();
	}
	
	public String getSendTaskList(JSONObject requestJSON,
			HttpServletRequest request, HttpServletResponse response)throws Exception{
		int cod = 0;
		int count = 0;
		List<SendTask> list = new ArrayList<SendTask>();
		SendTaskDAO sendTaskDao = new SendTaskDAO();
		MyLimit limit = null;
		JSONObject prm = new JSONObject();
		JSONObject pld;
		try {
			cod = requestJSON.getInt("cod");
			prm = requestJSON.getJSONObject("prm");
			limit = new MyLimit(prm.getInt("PageNum"),prm.getInt("CountLimit"));
			list = sendTaskDao.getSendTaskList(limit);
			count = sendTaskDao.getAllCount();
			pld = new JSONObject();
			JSONArray ja = new JSONArray();
			for (int i = 0; i < list.size(); i++) {
				JSONObject temp = new JSONObject();
				SendTask sendtask = list.get(i);
				temp.put("sendTaskId", sendtask.getSendTaskId());
				temp.put("name", sendtask.getName());
				temp.put("createTime", sendtask.getCreateTime());
				temp.put("toCount", sendtask.getToCount());
				temp.put("state", sendtask.getState());
				temp.put("successCount", sendtask.getSuccessCount());
				temp.put("failCount", sendtask.getFailCount());
				temp.put("completeTime", sendtask.getCompleteTime());
				temp.put("subject", sendtask.getSubject());
				String customNumber = sendtask.getCustomTo();
				String[] strArray = null;   
			    strArray = customNumber.split(",");
			    JSONArray tempja = new JSONArray();
			    for (int j = 0; j < strArray.length; j++) {
			    	tempja.put(strArray[j]);
				}
			    temp.put("CustomNumber", tempja);
			    ja.put(temp);
			}
			 
			 pld.put("CountNum", count);
			 pld.put("TaskList", ja);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new MyException("取得任务列表出现异常",e);
		}
		return getResponseJson(cod, true, pld).toString();
	}
	
	public String getFailNumberListByTaskID(JSONObject requestJSON,
			HttpServletRequest request, HttpServletResponse response)throws Exception{
		int cod = 0;
		int id = 0;
		int count = 0;
		List<Failnumber> list = new ArrayList<Failnumber>();
		PhoneNumDAO dao = new PhoneNumDAO();
		JSONObject prm = new JSONObject();
		JSONObject pld;
		try {
			cod = requestJSON.getInt("cod");
			prm = requestJSON.getJSONObject("prm");
			MyLimit limit = new MyLimit(prm.getInt("PageNum"),prm.getInt("CountLimit"));
			id = prm.getInt("ID");
			
			list = dao.getFailPhoneNumList(id, limit);
			count = dao.getAllCount();
			pld = new JSONObject();
			JSONArray ja = new JSONArray();
			for (int i = 0; i < list.size(); i++) {
				ja.put(list.get(i).getNumber());
			}
			pld.put("CountNum", count);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new MyException("取得失败号码出现异常", e);
		}
		return getResponseJson(cod, true, pld).toString();
	}
	
	public String getContentByTaskID(JSONObject requestJSON,
			HttpServletRequest request, HttpServletResponse response)throws Exception{
		int cod = 0;
		int id = 0;
		List<Content> list = new ArrayList<Content>();
		JSONObject prm = new JSONObject();
		ContentDAO dao = new ContentDAO();
		JSONObject pld = null;
		try {
			cod = requestJSON.getInt("cod");
			prm = requestJSON.getJSONObject("prm");
			id = prm.getInt("ID");
			
			list = dao.getContentListBySendTaskId(id);
			
			pld = new JSONObject();
			JSONArray ja = new JSONArray();
			for (int i = 0; i < list.size(); i++) {
				Content content = list.get(i);
				JSONObject temp = new JSONObject();
				temp.put("ID", content.getContentId());
				temp.put("Type", content.getContentType());
				temp.put("Text", content.getContentByte());
				temp.put("content", content.getContentByte());
				temp.put("sort", content.getSort());
				ja.put(temp);
			}
			pld.put("ID", id);
			pld.put("content", ja);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new MyException("取得彩信内容出现异常", e);
		}
		return getResponseJson(cod, true, pld).toString();
		
		
	}
	

	public String newSmcTask(JSONObject requestJSON,
			HttpServletRequest request, HttpServletResponse response)throws Exception{
		
		boolean bool = false;
		int cod = 0;
		int numberCount = 0;
		int taskID = 0;
		int successCount = 0;
		int failCount = 0;
		
		List<String> customNumberList = new ArrayList<String>();
		String customNumberstr = "";
		
		List<Content> contentList = new ArrayList<Content>();
		JSONObject prm = new JSONObject();
		Smctask sendTask = new Smctask();
		SendSMC sendSMC = null;
		SendTaskDAO sendTaskDao = new SendTaskDAO();
		DBNumber dbNumberDao = new DBNumber();
		numberCount = dbNumberDao.getAllCount();
		int allPageNum = 0;
		
		if(numberCount/DBNUMINFO.MAX_SEND_SMC_NUM == 0 || numberCount == DBNUMINFO.MAX_SEND_SMC_NUM){
			allPageNum = 1;
		}
		if(numberCount%DBNUMINFO.MAX_SEND_SMC_NUM == 0 && numberCount > DBNUMINFO.MAX_SEND_SMC_NUM){
			allPageNum = numberCount/DBNUMINFO.MAX_SEND_SMC_NUM;
		}
		if(numberCount%DBNUMINFO.MAX_SEND_SMC_NUM != 0 && numberCount > DBNUMINFO.MAX_SEND_SMC_NUM){
			allPageNum = numberCount/DBNUMINFO.MAX_SEND_SMC_NUM + 1;
		}
		
		try {
			cod = requestJSON.getInt("cod");
			prm = requestJSON.getJSONObject("prm");
			String name = prm.getString("name");
			String subject = prm.getString("subject");
			
			JSONArray customnumber = prm.getJSONArray("CustomNumber");
			String content = prm.getString("content");
			
			for (int i = 0; i < customnumber.length(); i++) {
				customNumberList.add(customnumber.getString(i));
			}
			
			
			StringBuilder sb = new StringBuilder(); 
			if(customNumberList != null  && customNumberList.size()>0){
				for (int i = 0; i < customNumberList.size(); i++) {  
			        if (i < customNumberList.size() - 1) {  
			            sb.append(customNumberList.get(i) + ",");  
			        } else {  
			            sb.append(customNumberList.get(i));  
			        }  
			    }  
			}
			customNumberstr = sb.toString();
			
			sendTask.setName(name);
			sendTask.setContent(content);
			sendTask.setSubject(subject);
			sendTask.setCustomTo(customNumberstr);
			sendTask.setState(1);
			sendTask.setToCount(numberCount+customNumberList.size());
			
			taskID = sendTaskDao.insertSmctask(sendTask);
			
			//分次发送
			for (int i = 1; i <= allPageNum; i++) {
				List<String> toNumbers = dbNumberDao.getToNumberList(new MyLimit(i, DBNUMINFO.MAX_SEND_MM7_NUM));
				sendSMC = new SendSMC(toNumbers, subject, content);
				if(sendSMC.Submit() == true){
					bool = true;
					successCount = successCount+toNumbers.size();
				}else{
					failCount = failCount+toNumbers.size();
				}
			}
			//发送定制号码
			sendSMC = new SendSMC(customNumberList, subject, content);
			if(sendSMC.Submit() == true){
				bool = true;
				successCount = successCount+customNumberList.size();
			}else{
				failCount = failCount+customNumberList.size();
			}
			
			
			if(bool){
				sendTaskDao.updateSmcTask(taskID, successCount, failCount, 2);
			}else{
				sendTaskDao.updateSmcTask(taskID, successCount, failCount, 3);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new MyException("发送短信任务异常", e);
		}
		
		return getResponseJson(cod, bool, null).toString();
	}
	
	public String getSmcTaskList(JSONObject requestJSON,
			HttpServletRequest request, HttpServletResponse response)throws Exception{
		int cod = 0;
		int count = 0;
		MyLimit limit = null;
		List <Smctask> list = new ArrayList<Smctask>();
		JSONObject prm = new JSONObject();
		JSONObject pld = new JSONObject();
		SendTaskDAO dao = new SendTaskDAO();
		PhoneNumDAO phoneNum = new PhoneNumDAO();
		
		try {
			cod = requestJSON.getInt("cod");
			prm = requestJSON.getJSONObject("prm");
			limit = new MyLimit(prm.getInt("PageNum"), prm.getInt("CountLimit"));
			list = dao.getSmctaskList(limit);
			count = dao.getSmctaskCount();
			JSONArray ja = new JSONArray();
			for (int i = 0; i < list.size(); i++) {
				Smctask smctask = list.get(i);
				JSONObject temp = new JSONObject();
				temp.put("sendTaskId", smctask.getId());
				temp.put("name", smctask.getName());
				temp.put("createTime", smctask.getCreateTime());
				temp.put("toCount", smctask.getToCount());
				temp.put("state", smctask.getState());
				temp.put("successCount", smctask.getSuccessCount());
				temp.put("failCount", smctask.getFailCount());
				temp.put("completeTime", smctask.getCompleteTime());
				temp.put("content", smctask.getContent());
				temp.put("subject", smctask.getSubject());
				String customNumber = smctask.getCustomTo();
				String[] strArray = null;   
			    strArray = customNumber.split(",");
			    JSONArray tempja = new JSONArray();
			    for (int j = 0; j < strArray.length; j++) {
			    	tempja.put(strArray[i]);
				}
			    temp.put("CustomNumber", tempja);
			    ja.put(temp);
			}
			pld.put("SmcTaskList", ja);
			pld.put("CountNum", count);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new MyException("取得短信列表出现异常", e);
		}
		
		return getResponseJson(cod, true, pld).toString();
	}
	public String getFailSmcNumberListByTaskID(JSONObject requestJSON,
			HttpServletRequest request, HttpServletResponse response)throws Exception{
		int cod = 0;
		JSONObject prm = new JSONObject();
		SendTaskDAO sendTask = new SendTaskDAO();
		PhoneNumDAO phoneNum = new PhoneNumDAO();
		
		cod = requestJSON.getInt("cod");
		prm = requestJSON.getJSONObject("prm");
		
		return null;
	}
	
}
