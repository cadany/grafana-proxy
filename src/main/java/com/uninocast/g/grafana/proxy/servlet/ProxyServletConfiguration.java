package com.uninocast.g.grafana.proxy.servlet;

import com.google.common.collect.ImmutableMap;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Servlet;
import java.util.Map;

@Configuration
public class ProxyServletConfiguration {

    private String servlet_url = "/g/*";

//    private String target_url = "http://172.16.1.170:3000/grafana";
    //grafana地址
    private String target_url = "http://192.168.101.90:53000/g/";

    @Bean
    public ServletRegistrationBean proxyServletRegistration() {
        ServletRegistrationBean registrationBean = new ServletRegistrationBean(
               new GrafanaProxyServlet(),
                servlet_url);
        //设置网址以及参数
        Map<String, String> params = ImmutableMap.of("targetUri", target_url, "log", "true");
        registrationBean.setInitParameters(params);
        return registrationBean;
    }

}
