package com.domainVO;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: wula
 * Date: 13-11-9
 * Time: 下午4:11
 * To change this template use File | Settings | File Templates.
 */
public class MM7VO {
    private String phone;
    private String title;//彩信标题
    private String yewuNum;//106289974显示的号码
    private String yewuCode;//产品代码

    private List<AttVo> attVoList;//附件list

    public List<AttVo> getAttVoList() {
        return attVoList;
    }

    public void setAttVoList(List<AttVo> attVoList) {
        this.attVoList = attVoList;
    }

    public String getYewuCode() {
        return yewuCode;
    }

    public void setYewuCode(String yewuCode) {
        this.yewuCode = yewuCode;
    }

    public String getYewuNum() {
        return yewuNum;
    }

    public void setYewuNum(String yewuNum) {
        this.yewuNum = yewuNum;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}

