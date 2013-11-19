package com.common.utils;

import com.domainVO.AttVo;
import com.domainVO.MM7VO;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * Created with IntelliJ IDEA.
 * User: wula
 * Date: 13-11-9
 * Time: ÏÂÎç3:49
 * To change this template use File | Settings | File Templates.
 */
public class MMSfactory {
    //private String serviceIP="119.6.251.96"; //10.143.9.128
    private String serviceIP="10.143.9.128";
    private int port=80;
    private MM7VO mm7VO;

    public MMSfactory(MM7VO mm7VO1){
        this.mm7VO=mm7VO1;
    }

    public String makeXMLhead(){
       String TransactionID = String.valueOf(System.currentTimeMillis()) ;
        StringBuffer body=new StringBuffer();

        body.append("--AAAA");
        body.append("\r\n");
        body.append("Content-Type:text/xml;charset=utf-8");
        body.append("\r\n");
        body.append("Content-Transfer-Encoding:8bit");
        body.append("\r\n");
        body.append("Content-ID:</tnn-200102/mm7-vasp>");
        body.append("\r\n");
        body.append("\r\n");

        body.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>");
        body.append("<env:Envelope xmlns:env=\"http://schemas.xmlsoap.org/soap/envelope/\">");
        body.append("<env:Header>");
        body.append("<mm7:TransactionID xmlns:mm7=\"http://www.3gpp.org/ftp/Specs/archive/23_series/23.140/schema/REL-6-MM7-1-0\" env:mustUnderstand=\"1\">");
        body.append(TransactionID);
        body.append("</mm7:TransactionID>");
        body.append("</env:Header>");
        body.append("<env:Body>");
        body.append("<SubmitReq xmlns=\"http://www.3gpp.org/ftp/Specs/archive/23_series/23.140/schema/REL-6-MM7-1-0\"><MM7Version>6.3.0</MM7Version><SenderIdentification>");
        body.append("<VASPID>71320</VASPID><VASID>10628997</VASID><SenderAddress>" + mm7VO.getYewuNum() + "</SenderAddress></SenderIdentification><Recipients><To><Number>");
        body.append(mm7VO.getPhone());
        body.append("</Number></To></Recipients><ServiceCode>");
        body.append(mm7VO.getYewuCode());
        body.append("</ServiceCode><LinkedID>");
        body.append("</LinkedID>");
        body.append("<MessageClass>Personal</MessageClass><DeliveryReport>true</DeliveryReport><ReadReply>true</ReadReply><Priority>Normal</Priority>");
        body.append("<Subject>");
        body.append(mm7VO.getTitle());
        body.append("</Subject>");
        body.append("<ChargedParty>0</ChargedParty><ChargedPartyID></ChargedPartyID><DistributionIndicator>true</DistributionIndicator></SubmitReq></env:Body></env:Envelope>");
        body.append("\r\n");
        body.append("\r\n");
        body.append(makeAttContent());
        body.append("--AAAA--");

        StringBuffer res=new StringBuffer();
        res.append("POST /MMSG/services/MM7AOMT HTTP/1.1\r\n");
        res.append("Host:10.143.9.128:80\r\n");
        res.append("Content-Type:multipart/related;boundary=\"AAAA\";type=\"text/xml\";start=\"</tnn-200102/mm7-vasp>\"\r\n");
        res.append("Content-Transfer-Encoding:8bit\r\n");
        res.append("SOAPAction:\"\"\r\n");
        res.append("MM7APIVersion:V1.5.7_200603\r\n");
        res.append("Connection:Close\r\n");
        res.append("Mime-Version:1.0\r\n");
        res.append("Content-Length:"+body.toString().length());
        res.append("\r\n");
        res.append("\r\n");
        res.append(body);
        return res.toString();
    }


private StringBuffer makeAttContent(){
    /*StringBuffer sb=new StringBuffer();
    sb.append("--AAAA");
    sb.append("\r\n");
    sb.append("Content-Type:text/plain;charset=UTF-8;name=xx.txt");
    sb.append("\r\n");
    sb.append("Content-Transfer-Encoding:base64");
    sb.append("\r\n");
    sb.append("Content-Location:xx.txt");
    sb.append("\r\n");
    sb.append("Content-ID:xx.txt");
    sb.append("\r\n");
    sb.append("\r\n");
    sb.append("ddd");
    sb.append("\r\n");
    sb.append("\r\n");*/


    StringBuffer sb=new StringBuffer();
    for (AttVo attVo : mm7VO.getAttVoList()){
        if (attVo.getAttbytes()!=null && attVo.getAttbytes().length>1){
            sb.append("--AAAA");
            sb.append("\r\n");
            sb.append("Content-Type:").append(attVo.getAtttype()).append(";").append("name=").append(attVo.getAttName());
            sb.append("\r\n");
            sb.append("Content-Transfer-Encoding:base64");
            sb.append("\r\n");
            sb.append("Content-Location:").append(attVo.getAttName());
            sb.append("\r\n");
            sb.append("Content-ID:").append(attVo.getAttName());
            sb.append("\r\n");
            sb.append("\r\n");
            sb.append(CommonUtil.encoderBytes(attVo.getAttbytes()));
            sb.append("\r\n");
            sb.append("\r\n");
        }

        if (attVo.getTxtBytes()!=null && attVo.getTxtBytes().length>1){
            sb.append("--AAAA");
            sb.append("\r\n");
            sb.append("Content-Type:text/plain;charset=UTF-8;").append("name=").append(attVo.getTxtName());
            sb.append("\r\n");
            sb.append("Content-Transfer-Encoding:base64");
            sb.append("\r\n");
            sb.append("Content-Location:").append(attVo.getTxtName());
            sb.append("\r\n");
            sb.append("Content-ID:").append(attVo.getTxtName());
            sb.append("\r\n");
            sb.append("\r\n");
            sb.append(CommonUtil.encoderBytes(attVo.getTxtBytes()));
            sb.append("\r\n");
            sb.append("\r\n");
        }




    }


    return sb;
}


public void send() {
    Socket socket=null;
    try {
        socket=new Socket(serviceIP,port);
        DataInputStream dis=new DataInputStream(socket.getInputStream());
        DataOutputStream dos=new DataOutputStream(socket.getOutputStream());

        /*PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
        out.print(makeXMLhead());
        out.flush();*/
        dos.write(makeXMLhead().getBytes("utf-8"));
        dos.flush();

        /*BufferedReader in = new BufferedReader(new InputStreamReader(processSocket.getInputStream()));
        while ((temp = in.readLine()) != null) {
            msg += temp;
        }*/

        System.out.println(makeXMLhead());
        String r="";
        while ((r=dis.readLine())  !=null){
            System.out.println(r);
        }




    }catch (Exception e){
       e.printStackTrace();
    }
    finally {
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
}
