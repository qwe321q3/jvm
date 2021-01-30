package com.tianshuo.framework.protocol.http;

import com.tianshuo.framework.Invoke;
import com.tianshuo.framework.protocol.Protocol;
import org.apache.catalina.*;
import org.apache.catalina.connector.Connector;
import org.apache.catalina.core.StandardContext;
import org.apache.catalina.core.StandardEngine;
import org.apache.catalina.core.StandardHost;
import org.apache.catalina.startup.Tomcat;

/**
 * @ClassName : HttpServer
 * @Description : 启动服务 (启动netty，tomcat，或者jetty服务等)
 * @Author : tianshuo
 * @Date: 2021-01-29 14:31
 */
public class HttpServer implements Protocol {

    private int port;

    private String ip;
    public HttpServer() {

    }

    public HttpServer(String ip,int port) {
        this.port = port;
        this.ip = ip;
    }

    @Override
    public void startup() {

        Tomcat tomcat = new Tomcat();

        Server server = tomcat.getServer();
        Service service = server.findService("Tomcat");

        Connector connector = new Connector();
        connector.setPort(8000);

        Engine engine = new StandardEngine();
        engine.setDefaultHost("localhost");

        Host host = new StandardHost();
        host.setName("localhost");

        String contextPath = "";
        Context context = new StandardContext();
        context.setPath(contextPath);
        context.addLifecycleListener(new Tomcat.FixContextListener());

        host.addChild(context);
        engine.addChild(host);

        service.setContainer(engine);
        service.addConnector(connector);

        tomcat.addServlet(contextPath, "dispatcher", new DispatcherServlet());
        context.addServletMappingDecoded("/*", "dispatcher");


        try {
            tomcat.start();
            tomcat.getServer().await();
        } catch (LifecycleException e) {
            e.printStackTrace();
        }

    }


    @Override
    public Object send(Invoke invoke) {

        return new HttpClient().send(invoke);
    }
}

