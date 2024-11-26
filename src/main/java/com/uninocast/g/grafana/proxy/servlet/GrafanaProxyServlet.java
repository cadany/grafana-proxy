package com.uninocast.g.grafana.proxy.servlet;

import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.mitre.dsmiley.httpproxy.ProxyServlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;


public class GrafanaProxyServlet extends ProxyServlet {

    @Override
    protected HttpResponse doExecute(HttpServletRequest servletRequest, HttpServletResponse servletResponse,
                                     HttpRequest proxyRequest) throws IOException {
        //TODO: 判断用户是否登录，如已登录设置Header的Auth
        String currentUser = "";
        if(currentUser == null){
            return null;
        }
        proxyRequest.setHeader("X-AUTH-USER", "ad"); // ad为grafana的用户，需要在grafana.ini中配置
        return super.doExecute(servletRequest, servletResponse, proxyRequest);
    }
}
