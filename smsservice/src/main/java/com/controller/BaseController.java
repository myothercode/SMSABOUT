package com.controller;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by chace.cai on 2014/6/30.
 */
public class BaseController {
    public void ajaxString(String s,HttpServletResponse response) throws IOException {
        response.setContentType("text/html");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(s);
    }
}
