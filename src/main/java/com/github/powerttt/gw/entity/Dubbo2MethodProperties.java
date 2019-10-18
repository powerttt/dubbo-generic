package com.github.powerttt.gw.entity;


import java.util.List;

/**
 * @Author tongning
 * @Date 2019/7/28 0028
 * function:<
 * <p>
 * >
 */
public class Dubbo2MethodProperties {

    /**
     * 方法名
     */
    private String methodName;

    /**
     * 方法参数列表
     */
    private List<Dubbo2ParamProperties> paramList;

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public List<Dubbo2ParamProperties> getParamList() {
        return paramList;
    }

    public void setParamList(List<Dubbo2ParamProperties> paramList) {
        this.paramList = paramList;
    }
}
