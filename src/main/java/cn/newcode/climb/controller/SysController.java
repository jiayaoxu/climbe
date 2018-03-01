package cn.newcode.climb.controller;

import cn.newcode.climb.po.Version;
import cn.newcode.climb.service.SysService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * \* Created with IntelliJ IDEA.
 * \* User: shine
 * \* Date: 2018/3/1 0001
 * \* Time: 19:43
 * \* Description:
 * \ 系统设置
 */
@Controller
@RequestMapping("/sys")
public class SysController {

    @Autowired
    private SysService sysService;

    @RequestMapping("/getVersion")
    public @ResponseBody Version getVersion(){
        Version v = null;
        try{
            v = sysService.getVersion();
        } catch (Exception e){
            e.printStackTrace();
        }
        return v;
    }

}