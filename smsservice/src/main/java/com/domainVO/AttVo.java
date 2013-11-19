package com.domainVO;

/**
 * Created with IntelliJ IDEA.
 * User: wula
 * Date: 13-11-18
 * Time: 下午10:11
 * To change this template use File | Settings | File Templates.
 */
public class AttVo{
    private byte [] attbytes;//图片附件数组
    private byte [] txtBytes;//文字附件数组
    private String atttype;//附件类型
    private String txttype;//文字附件类型
    private String attName;//附件名字
    private String txtName;

    public String getTxttype() {
        return txttype;
    }

    public void setTxttype(String txttype) {
        this.txttype = txttype;
    }

    public String getTxtName() {
        return txtName;
    }

    public void setTxtName(String txtName) {
        this.txtName = txtName;
    }

    public byte[] getAttbytes() {
        return attbytes;
    }

    public void setAttbytes(byte[] attbytes) {
        this.attbytes = attbytes;
    }

    public byte[] getTxtBytes() {
        return txtBytes;
    }

    public void setTxtBytes(byte[] txtBytes) {
        this.txtBytes = txtBytes;
    }

    public String getAtttype() {
        return atttype;
    }

    public void setAtttype(String atttype) {
        this.atttype = atttype;
    }

    public String getAttName() {
        return attName;
    }

    public void setAttName(String attName) {
        this.attName = attName;
    }
}
