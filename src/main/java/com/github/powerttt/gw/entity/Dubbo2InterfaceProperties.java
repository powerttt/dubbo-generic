package com.github.powerttt.gw.entity;

import java.util.List;
import java.util.Map;

/**
 * @Author tongning
 * @Date 2019/7/28 0028
 * function:<
 * <p>
 * >
 */
public class Dubbo2InterfaceProperties {
    /**
     * 服务名（包名）
     */
    private String serverName;

    /**
     * 服务方法 key-方法名称
     */
    private Map<String, List<Dubbo2ParamProperties>> methods;

    public String getServerName() {
        return serverName;
    }

    public void setServerName(String serverName) {
        this.serverName = serverName;
    }

    public Map<String, List<Dubbo2ParamProperties>> getMethods() {
        return methods;
    }

    public void setMethods(Map<String, List<Dubbo2ParamProperties>> methods) {
        this.methods = methods;
    }

    public List<Dubbo2ParamProperties> generateMethod(String method) {
        return method == null ? null : methods.get(method);
    }
}
