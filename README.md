
## 流程1
1、vue（iframe） -> nginx -> grafana-proxy(springboot) -> grafana

通过服务访问grafana的方案。示例页面：


    nginx: 192.168.101.21:28300
    grafana-proxy: 192.168.101.21:48080
    grafana: 192.168.101.90:5300

    #nginx配置
```
    location /g/d-solo/ {
        proxy_set_header Host $proxy_host;
        proxy_set_header X-Real-IP $remote_addr;
        proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;
        proxy_set_header X-Forwarded-Proto $scheme;
        proxy_pass  http://192.168.101.21:48080/g/d-solo/;
        proxy_redirect off;
    }
    location /g/public/ {
        proxy_set_header Host $proxy_host;
        proxy_set_header X-Real-IP $remote_addr;
        proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;
        proxy_set_header X-Forwarded-Proto $scheme;
        proxy_pass  http://192.168.101.21:48080/g/public/;
        proxy_redirect off;
    }
    location /g/api/ {
        proxy_set_header Host $proxy_host;
        proxy_set_header X-Real-IP $remote_addr;
        proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;
        proxy_set_header X-Forwarded-Proto $scheme;
        proxy_pass  http://192.168.101.21:48080/g/api/;
        proxy_redirect off;
    }
        location /g/d/ {
        proxy_set_header Host $proxy_host;
        proxy_set_header X-Real-IP $remote_addr;
        proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;
        proxy_set_header X-Forwarded-Proto $scheme;
        proxy_pass  http://192.168.101.21:48080/g/d/;
        proxy_redirect off;
    }
```

## 流程2
2、vue -> nginx -> grafana

不通过服务，直接由nginx代理grafana的方案。示例页面：

http://192.168.101.21:28300/m/index.html

访问grafana的面板
http://192.168.101.21:28300/g/d-solo/9CWBz0bik/1-node-exporter-for-prometheus-dashboard-cn-v20201010?orgId=1&refresh=15s&panelId=156


    nginx: 192.168.101.21:28300
    grafana-proxy: 192.168.101.21:48080
    grafana: 192.168.101.90:5300

```
   location /g/ {
        proxy_set_header Host $proxy_host;
        proxy_set_header X-Real-IP $remote_addr;
        proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;
        proxy_set_header X-Forwarded-Proto $scheme;
        proxy_pass  http://192.168.101.90:53000/;
        proxy_redirect off;
   }
```

## Grafana配置
  
需要修改`/etc/grafana/grafana.ini`配置。
```text
    [server]
    #指定grafana使用子路径访问
    root_url = %(protocol)s://%(domain)s:%(http_port)s/g/
    serve_from_sub_path = true 

    [auth.anonymous]                                                                                                                                                                                 
    enabled = false
    #指定匿名用户归属组织及角色
    org_name = Main Org.
    org_role = Viewer
    hide_version = false  

    [auth.proxy]    
    #启动代理访问，使用X-AUTH-USER头使用用户                                                                                                                                                                              
    enabled = true                                                                                                                                                                                   
    header_name=X-AUTH-USER  
```

## POC

    整个页面嵌入，使用分享页面的方式：
    http://192.168.101.21:28300/m/allin.html
    分区块嵌入，使用embed的分享方式：
    http://192.168.101.21:28300/m/demo.html
