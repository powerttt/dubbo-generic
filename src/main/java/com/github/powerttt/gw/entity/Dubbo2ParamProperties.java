package com.github.powerttt.gw.entity;

/**
 * @Author tongning
 * @Date 2019/7/28 0028
 * function:<
 * <p>
 * >
 */
public class Dubbo2ParamProperties {
    private String filed;
    private String type;
    private String restPath;

    public String getRestPath() {
        return restPath;
    }

    public void setRestPath(String restPath) {
        this.restPath = restPath;
    }

    public String getFiled() {
        return filed;
    }

    public void setFiled(String filed) {
        this.filed = filed;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
