/**
 * Created with IntelliJ IDEA.
 * User: wula
 * Date: 13-10-26
 * Time: 下午4:53
 * To change this template use File | Settings | File Templates.
 */

import com.controller.SmsController;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import    org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:/META-INF/spring/applicationContext.xml", "classpath:/META-INF/spring/dispatcher-servlet.xml"})
public class JobControllerTest {
    @Autowired
    public RequestMappingHandlerAdapter handlerAdapter;
    @Autowired
    private SmsController smsController;
    private static MockHttpServletRequest request;
    private static MockHttpServletResponse response;

    @BeforeClass
    public static void before() {
        request = new MockHttpServletRequest();
        request.setCharacterEncoding("UTF-8");
        response = new MockHttpServletResponse();
    }

    @Test
    public void testList() {
        request.setRequestURI("/login/loginUser");
        request.setMethod(HttpMethod.POST.name());
        ModelAndView mv = null;
        try {
            mv = handlerAdapter.handle(request, response, new     HandlerMethod(smsController, "mainPage"));
        } catch (Exception e) {
            e.printStackTrace();
        }

      //  Assert.assertNotNull(mv);
      //  Assert.assertEquals(response.getStatus(), 200);
       // Assert.assertEquals(mv.getViewName(), "/job/job_list");
    }
}
