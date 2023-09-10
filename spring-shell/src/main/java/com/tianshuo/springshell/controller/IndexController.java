package com.tianshuo.springshell.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

/**
 * 测试Controller是否是单例
 * 默认controller是单例
 * 设置Scope = request之后的，为多例
 * @author tianshuo
 */
//@Scope("request")
@Controller
public class IndexController {

    int a = 0;

    static int b = 0;
    @ResponseBody
    @RequestMapping("show")
    public Map<String,Integer> show() {
        HashMap<String, Integer> map = new HashMap<>();
        a++;
        b++;

        map.put("a", a);
        map.put("b", b);

        return map;
    }
}
