package com.domainVO;

/**
 * Created with IntelliJ IDEA.
 * User: wula
 * Date: 13-10-14
 * Time: 下午6:04
 * To change this template use File | Settings | File Templates.
 */
public class SendSMSVo {
    private Long userId;
    private String createDate;
    private String phone;
    private String msg;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
