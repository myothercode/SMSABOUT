package com.service.impl;

import com.domainVO.ActiveVo;
import com.domainVO.LoginVo;
import com.domainVO.SessionVo;
import com.domainVO.SmsBody;
import com.service.DataAccessService;
import org.springframework.beans.factory.annotation.Autowired;
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
        String sql="insert into sms_send_tb(serviceid,mobile_no,msg,reserve,flag,req_num,create_time,user_id) " +
                "values(?,?,?,'000000','0',null,null,?)";
        Object[] parm=new Object[]{smsBody.getServiceId(),smsBody.getPhoneNo(),smsBody.getMsg(),smsBody.getUserId()};
        //int[] types = new int[]{Types.INTEGER,Types.VARCHAR,Types.CHAR,Types.VARCHAR};
        jdbcTemplate.update(sql,parm);
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

    /*批量插入短信*/
    public void batchSendSMS(final List<SmsBody> smsBodyList){
        if(smsBodyList.isEmpty())return;
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
