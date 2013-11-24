package com.domainVO;

/**
 * Created with IntelliJ IDEA.
 * User: wula
 * Date: 13-11-24
 * Time: ÉÏÎç11:03
 * To change this template use File | Settings | File Templates.
 */
public class MM7TableVO {
    private String phoneNo;
    private String firstPath;
    private String paths;
    private String serviceId;  //num
    private String serviceCode;//code
    private String userId;
    private Long idnum;

    public Long getIdnum() {
        return idnum;
    }

    public void setIdnum(Long idnum) {
        this.idnum = idnum;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getServiceCode() {
        return serviceCode;
    }

    public void setServiceCode(String serviceCode) {
        this.serviceCode = serviceCode;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getFirstPath() {
        return firstPath;
    }

    public void setFirstPath(String firstPath) {
        this.firstPath = firstPath;
    }

    public String getPaths() {
        return paths;
    }

    public void setPaths(String paths) {
        this.paths = paths;
    }

    public String getServiceId() {
        return serviceId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }
}
