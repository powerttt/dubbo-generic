package com.github.powerttt.gw.controller;

import com.changyuan.commons.ResultBean;
import com.changyuan.commons.exception.UpExcetion;
import com.github.powerttt.gw.config.Dubbo2RouterProperties;
import com.github.powerttt.gw.entity.Dubbo2InterfaceProperties;
import com.github.powerttt.gw.entity.Dubbo2MethodProperties;
import com.github.powerttt.gw.entity.Dubbo2ParamProperties;
import com.github.powerttt.gw.entity.ServiceInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
public class DubboGenericController {

    @Autowired
    private Dubbo2RouterProperties dubbo2RouterProperties;

    @RequestMapping
    public Object dubboExec(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
//        try {
        return generateInterfaceInfo(httpServletRequest, httpServletResponse);
//        } catch (UpExcetion upExcetion) {
//            return new ResultBean().failure(upExcetion.getCode(), upExcetion.getMsg());
//        }
    }
//    private Object generate
    // TODO 添加请求方法验证
    private ServiceInfo generateInterfaceInfo(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws UpExcetion {
        String uri = httpServletRequest.getRequestURI();
        log.info("URI : {}", uri);
        String[] uris = uri.substring(1).split("/");
        // 请求格式有误
        if (uris.length < 2) {
            log.warn("URI ：{}  解析错误", uri);
//            throw new UpExcetion(40401, "URI解析错误");
        }
        String serviceName = uris[0];
        Dubbo2InterfaceProperties dubbo2InterfaceProperties = dubbo2RouterProperties.getRouters().get(serviceName);
        if (dubbo2InterfaceProperties == null) {
            log.warn("interfaceName ：{}  未找到此服务配置", serviceName);
//            throw new UpExcetion(40402, "未找到此服务配置");
        }
        String methodName = uris[1];
        if (StringUtils.isEmpty(methodName)) {
            log.warn("method ：{}  解析错误", uri);
        }
        assert dubbo2InterfaceProperties != null;
        List<Dubbo2ParamProperties> dubbo2ParamProperties = dubbo2InterfaceProperties.generateMethod(methodName);
        if (dubbo2ParamProperties == null) {
            if (dubbo2InterfaceProperties == null) {
                log.warn("method ：{}  未找到此服务方法", methodName);
//            throw new UpExcetion(40402, "未找到此服务配置");
            }
        }
        // 获取方法info
        ServiceInfo serviceInfo = new ServiceInfo();
        serviceInfo.setMethodName(serviceName);
        serviceInfo.setMethodName(methodName);

        List<String> paramTypeList = new ArrayList<>();
        List<Object> paramList = new ArrayList<>();

        // 处理请求类型
        for (int i = 0, uriJ = 2; i < dubbo2ParamProperties.size(); i++) {
            Dubbo2ParamProperties param = dubbo2ParamProperties.get(i);
            // RESTful
            if (param.getRestPath() != null) {
                log.info("RESTful : {}", uri);
                paramList.add(uris[uriJ]);
                uriJ++;
            } else {
                // set Value
                paramList.add(httpServletRequest.getParameter(param.getFiled()));
            }
            // set Type
            paramTypeList.add(param.getType());
        }
        serviceInfo.setParams(paramList);
        serviceInfo.setParamTypes(paramTypeList);
        return serviceInfo;
    }



}
