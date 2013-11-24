package com.service.impl;

import com.domainVO.MM7TableVO;
import com.domainVO.QuenueVo;
import com.service.GetMMSFromDB;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import javax.annotation.Resource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * 从数据库取出任务，一次20条发送完毕后再取
 * Created with IntelliJ IDEA.
 * User: wula
 * Date: 13-11-24
 * Time: 下午4:26
 * To change this template use File | Settings | File Templates.
 */
//@Service("getMMSFromDB")
public class GetMMSFromDBImpl implements GetMMSFromDB {
    @Resource
    private JdbcTemplate jdbcTemplate;

    /**查询带发送的彩信列表，一次20条*/
    @Override
    public void queryMMSList() throws InterruptedException {

        if (QuenueVo.smsQueue.isEmpty()){
            String sql="select top 20 idnum ,phoneNo,serviceid,filePath,userId,firstPath,serviceCode from mms_send_tb " +
                    "where flag='0' and phoneNo is not null";
            List<MM7TableVO> mm7TableVOs=jdbcTemplate.query(sql,new DataMapperD());
            if (mm7TableVOs==null||mm7TableVOs.isEmpty())return;
            for (MM7TableVO mm7TableVO : mm7TableVOs){
                QuenueVo.smsQueue.put(mm7TableVO);
            }
        }

    }

    /**更新彩信记录状态*/
    @Override
    public void updateFlag(Long idnum){
        String sql="update mms_send_tb set flag=flag+1,senddate=getdate() where idnum=?";
        Object[] params = new Object[]{idnum};
        jdbcTemplate.update(sql,params);
    }

}
class DataMapperD implements RowMapper {
    public Object mapRow(ResultSet rs,int rowNum) throws SQLException {
        MM7TableVO sm=new MM7TableVO();
        sm.setPhoneNo(rs.getString("phoneNo"));
        sm.setServiceId(rs.getString("serviceid"));
        sm.setFirstPath(rs.getString("filePath"));
        sm.setUserId(rs.getString("userId"));
        sm.setFirstPath(rs.getString("firstPath"));
        sm.setServiceCode(rs.getString("serviceCode"));
        sm.setIdnum(rs.getLong("idnum"));

        return sm;
    }
}