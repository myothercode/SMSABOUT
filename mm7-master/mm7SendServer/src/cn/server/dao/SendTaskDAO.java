package cn.server.dao;

import cn.common.JDBCHandler;
import cn.common.MyException;
import cn.server.bean.MyLimit;
import cn.server.bean.SendTask;
import cn.server.bean.Smctask;

import java.util.ArrayList;
import java.util.List;

public class SendTaskDAO {
	
	public int insertSendTask(final SendTask sendTask)throws Exception{
		return (Integer) new JDBCHandler() {
			
			@Override
			public Object doExecute() throws Exception {
				int id = 0;
				String sql1 = "INSERT INTO mm7_sendtask(`name`,createTime,toCount,customTo,state,successCount,failCount,subject)VALUES(?,NOW(),?,?,?,?,?,?);";
				String sql2 = "SELECT LAST_INSERT_ID();";
				try {
					stat = conn.prepareStatement(sql1);
					stat.setString(1, sendTask.getName());
					stat.setInt(2, sendTask.getToCount());
					stat.setString(3, sendTask.getCustomTo());
					stat.setInt(4, sendTask.getState());
					stat.setInt(5, 0);
					stat.setInt(6, 0);
					stat.setString(7, sendTask.getSubject());
					stat.executeUpdate();
					stat = conn.prepareStatement(sql2);
					rs = stat.executeQuery();
					while(rs.next()){
						id = rs.getInt(1);
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					throw new MyException("数据库插入彩信任务并返回ID异常",e);
				}
				return id;
			}
		}.execute();
	}
	
	public void updateState(final int id,final int state)throws Exception{
		new JDBCHandler() {
			
			@Override
			public Object doExecute() throws Exception {
				String sql = "UPDATE mm7_sendtask SET state=? WHERE sendTaskId = ?;";
				try {
					stat = conn.prepareStatement(sql);
					stat.setObject(1, state);
					stat.setObject(2,id);
					stat.execute();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					throw new MyException("数据库修改彩信任务状态出现异常", e);
				}
				return null;
			}
		}.execute();
	}
	public void updateTask(final int id,final int successCount,final int failCount,final int state)throws Exception{
		new JDBCHandler() {
			
			@Override
			public Object doExecute() throws Exception {
				String sql = "UPDATE mm7_sendtask SET state = ?, successCount=? ,failCount=?,completeTime=NOW() WHERE sendTaskId = ?;";
				try {
					stat = conn.prepareStatement(sql);
					stat.setObject(1, state);
					stat.setObject(2, successCount);
					stat.setObject(3, failCount);
					stat.setObject(4, id);
					stat.execute();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					throw new MyException("数据库彩信任务完成后更新异常", e);
				}
				return null;
			}
		}.execute();
	}
	
	@SuppressWarnings("unchecked")
	public List<SendTask> getSendTaskList(final MyLimit limit)throws Exception{
		return (List<SendTask>) new JDBCHandler() {
			
			@Override
			public Object doExecute() throws Exception {
				List<SendTask> list = new ArrayList<SendTask>();
				String sql = "SELECT * FROM mm7_sendtask "+limit.getSQL();
				try {
					stat = conn.prepareStatement(sql);
					rs = stat.executeQuery();
					while(rs.next()){
						SendTask task = new SendTask();
						task.setCompleteTime(rs.getDate("completeTime") != null ? rs.getDate("completeTime").toString() : "未结束");
						task.setCreateTime(rs.getDate("createTime").toString());
						task.setCustomTo(rs.getString("customTo"));
						task.setFailCount(rs.getInt("failCount"));
						task.setName(rs.getString("name"));
						task.setSendTaskId(rs.getInt("sendTaskId"));
						task.setState(rs.getInt("state"));
						task.setSuccessCount(rs.getInt("successCount"));
						task.setToCount(rs.getInt("toCount"));
						task.setSubject(rs.getString("subject"));
						list.add(task);
						
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					throw new MyException("数据库取得彩信任务列表异常", e);
				}
				return list;
			}
		}.execute();
	}
	
	public int getAllCount()throws Exception{
		return (Integer) new JDBCHandler() {
			
			@Override
			public Object doExecute() throws Exception {
				int count = 0;
				String sql = "SELECT COUNT(*) FROM mm7_sendtask;";
				try {
					stat = conn.prepareStatement(sql);
					rs = stat.executeQuery();
					while(rs.next()){
						count = rs.getInt(1);
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					throw new MyException("数据库取得彩信任务总数异常",e);
				}
				return count;
			}
		}.execute();
	}
	
	public int insertSmctask(final Smctask smctask)throws Exception{
		return (Integer)new JDBCHandler() {
			
			@Override
			public Object doExecute() throws Exception {
				int id = 0;
				String sql1 = "INSERT INTO mm7_smctask mm7_smctask （mm7_smctask.name,content,createTime,toCount,customTo,state,successCount,failCount,subject）VALUES (?,?,NOW(),?,?,?,?,?,?)";
				String sql2 = "SELECT LAST_INSERT_ID();";
				try {
					stat = conn.prepareStatement(sql1);
					stat.setObject(1, smctask.getName());
					stat.setObject(2, smctask.getContent());
					stat.setObject(3, smctask.getToCount());
					stat.setObject(4, smctask.getCustomTo());
					stat.setObject(5, smctask.getState());
					stat.setObject(6, 0);
					stat.setObject(7, 0);
					stat.setObject(8, smctask.getSubject());
					stat.execute();
					stat = conn.prepareStatement(sql2);
					rs = stat.executeQuery();
					while(rs.next()){
						id = rs.getInt(1);
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					throw new MyException("数据库新建短信任务异常", e);
				}
				return id;
			}
		}.execute();
		
	}
	
	@SuppressWarnings("unchecked")
	public List<Smctask> getSmctaskList(final MyLimit limit)throws Exception{
		return (List<Smctask>) new JDBCHandler() {
			
			@Override
			public Object doExecute() throws Exception {
				List<Smctask> list = new ArrayList<Smctask>();
				String sql = "SELECT * FROM mm7_smctask"+limit.getSQL();
				try {
					stat = conn.prepareStatement(sql);
					rs = stat.executeQuery();
					while(rs.next()){
						Smctask smctask = new Smctask();
						smctask.setId(rs.getInt("smcId"));
						smctask.setName(rs.getString("name"));
						smctask.setContent(rs.getString("content"));
						smctask.setCreateTime(rs.getDate("createTime").toString());
						smctask.setToCount(rs.getInt("toCount"));
						smctask.setCustomTo(rs.getString("customTo"));
						smctask.setState(rs.getInt("state"));
						smctask.setSuccessCount(rs.getInt("successCount"));
						smctask.setFailCount(rs.getInt("failCount"));
						smctask.setSubject(rs.getString("subject"));
						if(rs.getDate("completeTime") == null){
							smctask.setCompleteTime("未结束");
							
						}else{
							smctask.setCompleteTime(rs.getDate("completeTime").toString());
							
						}
						list.add(smctask);
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					throw new MyException("数据库取得短信任务列表异常", e);
				}
				return list;
			}
		}.execute();
	}
	
	public int getSmctaskCount()throws Exception{
		return (Integer) new JDBCHandler() {
			
			@Override
			public Object doExecute() throws Exception {
				int count = 0;
				String sql = "SELECT COUNT(*) FROM mm7_smctask;";
				try {
					stat = conn.prepareStatement(sql);
					rs = stat.executeQuery();
					while(rs.next()){
						count = rs.getInt(1);
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					throw new MyException("数据库取得短信信任务总数异常",e);
				}
				return count;
			}
		}.execute();
	}
	
	public void updateSmcTask(final int id,final int successCount,final int failCount,final int state)throws Exception{
		new JDBCHandler() {
			
			@Override
			public Object doExecute() throws Exception {
				String sql = "UPDATE mm7_smctask SET state = ?, successCount=? ,failCount=?,completeTime=NOW() WHERE smcId = ?;";
				try {
					stat = conn.prepareStatement(sql);
					stat.setObject(1, state);
					stat.setObject(2, successCount);
					stat.setObject(3, failCount);
					stat.setObject(4, id);
					stat.execute();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					throw new MyException("数据库短信任务完成后更新异常", e);
				}
				return null;
			}
		}.execute();
	}
	

}
