package com.lsgf.utils;

import org.springframework.core.env.Environment;

import javax.annotation.Resource;


public class EnvironmentUtil {

    @Resource
    Environment environment;

    public EnvironmentUtil() {
    }

    // 局域网ip
    //没必要 删除掉
//    public String getIp(){
//        try {
//            return Inet4Address.getLocalHost().getHostAddress();
//        } catch (UnknownHostException e) {
//            throw new RuntimeException(e);
//        }
//    }

    // 端口号
    public String getPort() {
        return environment.getProperty("server.port");
    }

    // 项目发布名称
    public String getWebApp() {
        String webApp = environment.getProperty("server.servlet.context-path");
        if (webApp == null) {
            webApp = "";
        }
        return webApp;
    }

    public String getConfig(String key) {
        return environment.getProperty(key);
    }
}
