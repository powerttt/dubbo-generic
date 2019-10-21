package com.github.powerttt.gw.controller;

import com.changyuan.commons.ResultBean;
import com.changyuan.commons.exception.UpExcetion;
import com.github.powerttt.gw.config.Dubbo2RouterProperties;
import com.github.powerttt.gw.entity.Dubbo2InterfaceProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@RestController
public class DubboGenericController {

    @Autowired
    private Dubbo2RouterProperties dubbo2RouterProperties;

    @RequestMapping
    public Object dubboExec(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        try {
            return generateInterfaceInfo(httpServletRequest, httpServletResponse);
        } catch (UpExcetion upExcetion) {
            return new ResultBean().failure(upExcetion.getCode(), upExcetion.getMsg());
        }
    }


    private Dubbo2InterfaceProperties generateInterfaceInfo(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws UpExcetion {
        String uri = httpServletRequest.getRequestURI();
        log.info("URI : {}", uri);
        String[] uris = uri.substring(1).split("/");
        // 请求格式有误
        if (uris.length < 2) {
            log.warn("URI ：{}  解析错误", uri);
            throw new UpExcetion(40401, "URI解析错误");
        }
        String interfaceName = uris[0];
        Dubbo2InterfaceProperties dubbo2InterfaceProperties = dubbo2RouterProperties.getRouters().get(interfaceName);
        if (dubbo2InterfaceProperties == null) {
            log.warn("interfaceName ：{}  未找到此服务配置", interfaceName);
            throw new UpExcetion(40402, "未找到此服务配置");
        }



        return null;

    }


}
