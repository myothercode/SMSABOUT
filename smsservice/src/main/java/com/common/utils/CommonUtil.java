package com.common.utils;


import org.apache.commons.codec.binary.Base64;
import org.springframework.web.multipart.MultipartFile;
import sun.misc.BASE64Decoder;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: wula
 * Date: 13-11-8
 * Time: ����11:00
 * To change this template use File | Settings | File Templates.
 */
public class CommonUtil {

    public static void main(String[] args) throws Exception {
        //deencoderString(strImg,"ddg.jpg");
        InputStream i=new FileInputStream(new File("D:\\test\\00.jpg")) ;
        byte [] b=new byte[i.available()];
        i.read(b);
        i.close();
        String s=CommonUtil.encoderBytes(b);
        System.out.println(s);
        deencoderString(s,"xxx.jpg");
    }


    /**��txt�������˳�������װ��map*/
    public static Map filterTxt(MultipartFile[] multipartFiles){
          Map map=new HashMap();
        for (MultipartFile multipartFile:multipartFiles){
            if (multipartFile.getOriginalFilename().endsWith(".jpg")||multipartFile.getOriginalFilename().endsWith(".gif")){
                map.put(multipartFile.getOriginalFilename().substring(0,multipartFile.getOriginalFilename().lastIndexOf(".")),multipartFile);
            }
        }

        return map;
    }

    /**��byte[]����base64����*/
    public static String encoderBytes(byte[] bytes){
        if (bytes==null||bytes.length==0)return "";

        return Base64.encodeBase64String(bytes) ;
        /*BASE64Encoder encoder=new BASE64Encoder();
        return encoder.encode(bytes);*/
    }

    /**��base64������ļ�ת����ȥ*/
    public static boolean deencoderString(String str,String fileName) throws Exception {
           if (str==null)return false;
        BASE64Decoder decoder = new BASE64Decoder();
        byte [] b = decoder.decodeBuffer(str);

        for(int i=0;i<b.length;++i)
        {
            if(b[i]<0)
            {//�����쳣����
                b[i]+=256;
            }
        }
        //����jpegͼƬ
        String imgFilePath = "d:\\test\\";//�����ɵ�ͼƬ
        File f = new File(imgFilePath)  ;
        if (!f.exists()){
          f.mkdirs();
        }

        OutputStream out = new FileOutputStream(imgFilePath+fileName);
        out.write(b);
        out.flush();
        out.close();
        return true;
    }
}
