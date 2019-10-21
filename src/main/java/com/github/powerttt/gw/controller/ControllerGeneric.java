package com.github.powerttt.gw.controller;

import com.github.powerttt.gw.config.Dubbo2RouterProperties;
import com.github.powerttt.gw.entity.Dubbo2InterfaceProperties;
import com.github.powerttt.gw.entity.Dubbo2ParamProperties;
import com.github.powerttt.gw.enums.Dubbo2ParamTypeEnum;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.ReferenceConfig;
import org.apache.dubbo.config.utils.ReferenceConfigCache;
import org.apache.dubbo.rpc.service.GenericException;
import org.apache.dubbo.rpc.service.GenericService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author tongning
 * @Date 2019/7/13 0013
 * function:<
 * <p>
 * >
 */
@RestController
public class ControllerGeneric {

    @Autowired
    private Dubbo2RouterProperties dubbo2RouterProperties;

    /**
     * RESTful接口
     * http://127.0.0.1:8080/generic/test/say/1 ->result接口
     *
     * @param httpServletRequest
     * @param version
     * @param interfaceName
     * @param method
     * @param restfulDate
     * @return
     */
    @RequestMapping("{version}/{interfaceName}/{method}/{restfulDate}")
    public Object dubboExec(HttpServletRequest httpServletRequest, @PathVariable String version, @PathVariable String interfaceName, @PathVariable String method, @PathVariable String restfulDate) {
        return exce(httpServletRequest, version, interfaceName, method, restfulDate);
    }

    /**
     * http://127.0.0.1:8080/generic/test?say=Dubbo ->有参数的接口
     * http://127.0.0.1:8080/generic/test1 ->无参数的接口
     *
     * @param httpServletRequest
     * @param interfaceName
     * @param method
     * @return
     */
    @RequestMapping("{version}/{interfaceName}/{method}")
    public Object dubboExec(HttpServletRequest httpServletRequest, @PathVariable String version, @PathVariable String interfaceName, @PathVariable String method) {
        return exce(httpServletRequest, version, interfaceName, method, null);
    }


    /**
     * 执行dubbo泛化
     *
     * @param httpServletRequest 请求
     * @param version            服务版本号
     * @param interfaceName      接口名称
     * @param method             方法名称
     * @param restfulDate        result风格接口
     * @return 返回内容
     */
    private Object exce(HttpServletRequest httpServletRequest, String version, String interfaceName, String method, String restfulDate) {
        // 获取服务配置信息
        Dubbo2InterfaceProperties dubbo2InterfaceProperties = dubbo2RouterProperties.getRouters().get(interfaceName);

        if (dubbo2InterfaceProperties == null) {
            return ("service not found");
        }

        // 该实例很重量，里面封装了所有与注册中心及服务提供方连接，请缓存
        ReferenceConfig<GenericService> referenceConfig = new ReferenceConfig<>();
        // 弱化接口名
        referenceConfig.setInterface(dubbo2InterfaceProperties.getServerName());
        // 设置版本号
        referenceConfig.setVersion(version);
        // 声明为泛化接口
        referenceConfig.setGeneric(true);

        ReferenceConfigCache cache = ReferenceConfigCache.getCache();
        GenericService genericService = cache.get(referenceConfig);

        if (genericService == null) {
            cache.destroy(referenceConfig);
            return ("服务不可用");
        }


        // 获取参数列表
        List<String> paramTypeList = new ArrayList<>();
        List<Object> params = new ArrayList<>();
        List<Dubbo2ParamProperties> dubbo2ParamProperties = dubbo2InterfaceProperties.getMethods().get(method);
        if (dubbo2ParamProperties == null) {
            return ("method not found");
        }
        Object result = null;

        // 参数为空的
        if (StringUtils.isEmpty(dubbo2ParamProperties.get(0).getFiled())) {
            try {
                return genericService.$invoke(method, null, null);
            } catch (GenericException e) {
                e.printStackTrace();
                // test
                return "调用错误";
            }
        }
        // 处理类型
        dubbo2ParamProperties.forEach(p -> {
            Dubbo2ParamTypeEnum type = Dubbo2ParamTypeEnum.valueOf(p.getType());
            // 参数类型
            paramTypeList.add(type.getJavaType());
            // 参数值
            params.add(p.getRestPath() == null ? httpServletRequest.getParameter(p.getFiled()) : restfulDate);
        });

        try {
            result = genericService.$invoke(method, paramTypeList.toArray(new String[0]), params.toArray(new Object[0]));
        } catch (GenericException e) {
            e.printStackTrace();
            // test
            return "调用错误";
        }
        return result;
    }
}
