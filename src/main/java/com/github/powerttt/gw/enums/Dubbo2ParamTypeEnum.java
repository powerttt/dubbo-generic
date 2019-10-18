package com.github.powerttt.gw.enums;

/**
 * @Author tongning
 * @Date 2019/7/28 0028
 * function:<
 * <p>
 * >
 */
public enum  Dubbo2ParamTypeEnum {

    /**
     * String
     */
    String("java.lang.String"),
    Integer("java.lang.Integer"),
    Float("java.lang.Float"),
    Dubble("java.lang.Dubble"),
    Long("java.lang.Long"),
    ;
    private String javaType;

    private Object defaultValue;

    Dubbo2ParamTypeEnum(String javaType) {
        this.javaType = javaType;
        this.defaultValue = null;
    }

    public java.lang.String getJavaType() {
        return javaType;
    }

    public void setJavaType(java.lang.String javaType) {
        this.javaType = javaType;
    }

    public Object getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(Object defaultValue) {
        this.defaultValue = defaultValue;
    }
}
