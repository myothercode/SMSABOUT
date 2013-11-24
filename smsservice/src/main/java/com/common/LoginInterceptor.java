package com.common;

import com.domainVO.SessionVo;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.PrintWriter;

/**
 * Created with IntelliJ IDEA.
 * User: wula
 * Date: 13-10-12
 * Time: 下午11:01
 * To change this template use File | Settings | File Templates.
 */
public class LoginInterceptor implements HandlerInterceptor {
    //@Autowired
    //private DataAccessService dataAccessService;

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        HttpSession s= httpServletRequest.getSession(false);
        if(s==null){
            PrintWriter p= httpServletResponse.getWriter();
            p.print("error!");
            return false;
        }
        Object sessionVo= s.getAttribute("system_session_sms_11111");
        if(sessionVo==null){
            PrintWriter p= httpServletResponse.getWriter();
            p.print("error!");
            return false;
        }
        SessionVo s1 = (SessionVo)s.getAttribute("system_session_sms_11111");
        if(s1.getId()==null || s1.getId()==0){
            PrintWriter p= httpServletResponse.getWriter();
            p.print("error!");
            return false;
        }

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
