package com.atguigu.ecommerceuserprofile.app.controller;/**
 * Copyright (c) 2018-2028 尚硅谷 All Rights Reserved
 * <p>
 * Project: EcommerceUserProfile
 * Package: com.atguigu.ecommerceuserprofile.app.controller
 * Version: 1.0
 * <p>
 * Created by wushengran on 2020/6/12 12:02
 */

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @ClassName: PageController
 * @Description:
 * @Author: wushengran on 2020/6/12 12:02
 * @Version: 1.0
 */
@Controller
public class PageController {
    @RequestMapping("/")
    public String index(){
        return "index";
    }

    @RequestMapping("/tags")
    public String tags(){
        return "tags";
    }

}
