import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.AbstractHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: wula
 * Date: 13-10-30
 * Time: 下午9:27
 * To change this template use File | Settings | File Templates.
 */
public class HttpClient4Test {
    public static void main(String[] args) throws IOException {
        String loginUrl="http:///smsservice/login/loginUser";
        HttpClient httpClient=new DefaultHttpClient();
        HttpPost httpPost=new HttpPost(loginUrl);
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("userId", ""));   //帐号
        params.add(new BasicNameValuePair("passWord", "")); //密码
        httpPost.setEntity(new UrlEncodedFormEntity(params));

        HttpResponse response = httpClient.execute(httpPost);
        HttpEntity entity = response.getEntity();
        String postResult = EntityUtils.toString(entity, "GBK");
        List<Cookie> cookies = ((AbstractHttpClient)httpClient).getCookieStore().getCookies();
        for(Cookie cookie: cookies)
            System.out.println(cookie);
        httpPost.releaseConnection();
        System.out.println(postResult);


        String memberpage = "http:///smsservice/sms/thirdPartImpl";
        httpPost = new HttpPost(memberpage);
        List<NameValuePair> params1 = new ArrayList<NameValuePair>();
        params1.add(new BasicNameValuePair("phoneNo", "18602861176,8618602861176"));  //手机号
        params1.add(new BasicNameValuePair("msg", "test"));      //短信内容
        httpPost.setEntity(new UrlEncodedFormEntity(params1));
        response = httpClient.execute(httpPost);

        HttpEntity entity1 = response.getEntity();
        String html = EntityUtils.toString(entity1, "GBK");
        System.out.println(html);
    }
}
