package com.uninocast.g.grafana.proxy.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProxyController {

    @RequestMapping("/index")
    public String index(){
        return "2222";
    }
}
