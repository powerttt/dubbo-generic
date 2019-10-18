package com.github.powerttt.gw.config;

import com.github.powerttt.gw.entity.Dubbo2InterfaceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @Author tongning
 * @Date 2019/7/28 0028
 * function:<
 * <p>
 * >
 */
@Component
@ConfigurationProperties(prefix = "powertn.dubbo")
public class Dubbo2RouterProperties {

    private Map<String, Dubbo2InterfaceProperties> routers;

    public Map<String, Dubbo2InterfaceProperties> getRouters() {
        return routers;
    }

    public void setRouters(Map<String, Dubbo2InterfaceProperties> routers) {
        this.routers = routers;
    }
}
