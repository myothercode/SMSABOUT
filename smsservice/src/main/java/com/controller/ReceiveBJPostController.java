package com.controller;

import com.domainVO.AttVo;
import com.domainVO.ThirdPartRequestVO;
import com.service.ThirdPartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by chace.cai on 2014/6/30.
 */
@Controller("receiveBJPostController")
@RequestMapping("thirdPart")
public class ReceiveBJPostController extends BaseController{
   @Autowired
    private ThirdPartService thirdPartService;

    @RequestMapping("receiveMessage")
    public void receiveMessage(ThirdPartRequestVO thirdPartRequestVO,HttpServletResponse response) throws IOException {
        thirdPartService.setThirdPartMessage(thirdPartRequestVO)  ;
        ajaxString("x", response);
    }
}
