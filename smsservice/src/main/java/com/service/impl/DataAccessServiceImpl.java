package com.service.impl;

import com.domainVO.*;
import com.service.DataAccessService;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: wula
 * Date: 13-10-11
 * Time: 下午8:23
 * To change this template use File | Settings | File Templates.
 */
@Service("dataAccessService")
public class DataAccessServiceImpl implements DataAccessService {
    @Resource
    private JdbcTemplate jdbcTemplate;

    /*发送短信*/
    public void insertSend(SmsBody smsBody){
        int limit=deductSMS(smsBody.getUserId(),1);
        if(limit>0){
            String sql="insert into sms_send_tb(serviceid,mobile_no,msg,reserve,flag,req_num,create_time,user_id) " +
                    "values(?,?,?,'000000','0',null,null,?)";
            Object[] parm=new Object[]{smsBody.getServiceId(),smsBody.getPhoneNo(),smsBody.getMsg(),smsBody.getUserId()};
            //int[] types = new int[]{Types.INTEGER,Types.VARCHAR,Types.CHAR,Types.VARCHAR};
            jdbcTemplate.update(sql,parm);
        }

    }

    /*新增加一个活动*/
    public void insertActive(ActiveVo activeVo){
         String sql="insert into sms_hd_tb(hdid,msg,user_id) values(?,?,?)";
        Object[] parm=new Object[]{activeVo.getHdid(),activeVo.getMsg(),activeVo.getUser_id()};
        jdbcTemplate.update(sql,parm);
    }

    /*登录信息查询*/
    public SessionVo getLoginVo(LoginVo loginVo){
       String sql="select id,name,role from sms_user_tb where status=0 and id=? and password=?";
        Object[] parm=new Object[]{loginVo.getUserId(),loginVo.getPassWord()};
        List<SessionVo> list = jdbcTemplate.query(sql, parm, new SessionMapper());
        if(list==null||list.isEmpty())return null;
        return (SessionVo)list.get(0);
    }

    /**批量插入短信*/
    public int batchSendSMS(final List<SmsBody> smsBodyList){
        if(smsBodyList==null||smsBodyList.isEmpty()||smsBodyList.size()>20000)return 0;

        Double t = new Double(smsBodyList.get(0).getMsg().length()) /new Double(65) ;
        Double d=Math.ceil(t);
        int mlength=d.intValue();//消息条数
        if (mlength==0){mlength=1;}

        int limit=deductSMS(smsBodyList.get(0).getUserId(),mlength);
        if(limit>0){
            String sql="insert into sms_send_tb(serviceid,mobile_no,msg,reserve,flag,req_num,create_time,user_id) " +
                    "values(?,?,?,'000000','0',null,null,?)";
            jdbcTemplate.batchUpdate(sql,new BatchPreparedStatementSetter() {
                @Override
                public void setValues(PreparedStatement preparedStatement, int i) throws SQLException {
                    preparedStatement.getConnection().setAutoCommit(true);
                    SmsBody smsBody=(SmsBody)smsBodyList.get(i);
                    preparedStatement.setString(1,smsBody.getServiceId());
                    preparedStatement.setString(2,smsBody.getPhoneNo());
                    preparedStatement.setString(3,smsBody.getMsg());
                    preparedStatement.setLong(4,smsBody.getUserId());
                }
                @Override
                public int getBatchSize() {
                    return smsBodyList.size();
                }
            }) ;
            return smsBodyList.size();
        }else {
            return 0;
        }


    }

    /**批量插入彩信待发记录*/
    @Override
    public int batchSendMMS(final List<MM7TableVO> mm7TableVOList){
        if(mm7TableVOList==null||mm7TableVOList.isEmpty()||mm7TableVOList.size()>20000)return 0;

        String sql="insert into mms_send_tb(phoneNo,serviceid,flag,filePath,userId,firstPath,serviceCode) " +
                "values(?,?,'0',?,?,?,?)";
        jdbcTemplate.batchUpdate(sql,new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement preparedStatement, int i) throws SQLException {
                preparedStatement.getConnection().setAutoCommit(true);
                MM7TableVO mm7TableVO=mm7TableVOList.get(i);
                preparedStatement.setString(1,mm7TableVO.getPhoneNo());
                preparedStatement.setString(2,mm7TableVO.getServiceId());
                preparedStatement.setString(3,mm7TableVO.getPaths());
                preparedStatement.setString(4,mm7TableVO.getUserId());
                preparedStatement.setString(5,mm7TableVO.getFirstPath());
                preparedStatement.setString(6,mm7TableVO.getServiceCode());

            }


            @Override
            public int getBatchSize() {
                return mm7TableVOList.size();
            }
        });
        return mm7TableVOList.size();
    }


     /**根据组号发送短信*/
    public int insertSmsByGroup(SmsBody smsBody){
        int limit=deductSMS(smsBody.getUserId(),12000);
        if(limit>0){
            String sql="insert into sms_send_tb(serviceid,mobile_no,msg,reserve,flag,req_num,create_time,user_id) " +
                    "select ?,mobile_no,?,'000000','0',null,null,? from phoneNumbers where group_no=?";
            Object[] parm=new Object[]{smsBody.getServiceId(),smsBody.getMsg(),smsBody.getUserId(),smsBody.getPhoneNo()};
            return jdbcTemplate.update(sql,parm);
        }else return 0;
    }

    /**扣除短信余量*/
    private int deductSMS(Long id,int num){
          String sql="update sms_user_tb set totalnum=totalnum-?,usenum=usenum+? where id=? and totalnum-?>0";
          Object[] parm=new Object[]{num,num,id,num};
          return jdbcTemplate.update(sql,parm);
    }


}


class SessionMapper implements RowMapper{

    @Override
    public Object mapRow(ResultSet resultSet, int i) throws SQLException {
        SessionVo sessionVo = new SessionVo();
        sessionVo.setId(resultSet.getLong("id"));
        sessionVo.setName(resultSet.getString("name"));
        sessionVo.setRole(resultSet.getString("role"));
        return sessionVo;
    }
}
