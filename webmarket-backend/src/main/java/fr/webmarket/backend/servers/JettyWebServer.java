/*
 * Copyright 2013 - Elian ORIOU
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package fr.webmarket.backend.servers;

import com.google.common.base.Strings;
import org.eclipse.jetty.security.DefaultIdentityService;
import org.eclipse.jetty.security.HashLoginService;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerCollection;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.nio.SelectChannelConnector;
import org.eclipse.jetty.util.thread.QueuedThreadPool;
import org.eclipse.jetty.util.thread.ThreadPool;
import org.eclipse.jetty.webapp.WebAppContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Remark : This class is largely inspired by a Jetty WebServer boostraping class which 
 * is part of the restx library (http://restx.io).
 */

public class JettyWebServer {

    private final Logger logger = LoggerFactory.getLogger(JettyWebServer.class);

    private Server server;
    private int port;
    private String bindInterface;
    private String appBase;
    private String webInfLocation;

    public JettyWebServer(String appBase, int aPort) {
        this(appBase, appBase, aPort, null);
    }

    public JettyWebServer(String webInfLocation, String appBase, int port, String bindInterface) {
        this.port = port;
        this.bindInterface = bindInterface;
        this.appBase = appBase;
        this.webInfLocation = webInfLocation;
    }

    public int getPort() {
        return port;
    }

    public String baseUrl() {
        return String.format("http://localhost:%s", port);
    }

    public void start() throws Exception {
        server = new Server();

        server.setThreadPool(createThreadPool());
        server.addConnector(createConnector());
        server.setHandler(createHandlers());
        server.setStopAtShutdown(true);

        server.start();
    }

    public void startAndAwait() throws Exception {
        start();
        server.join();
    }

    public void stop() throws Exception {
        server.stop();
    }

    private ThreadPool createThreadPool() {
        QueuedThreadPool threadPool = new QueuedThreadPool();
        threadPool.setMinThreads(1);
        threadPool.setMaxThreads(10);
        return threadPool;
    }

    private SelectChannelConnector createConnector() {
        SelectChannelConnector connector = new SelectChannelConnector();
        connector.setPort(port);
        connector.setHost(bindInterface);
        return connector;
    }

    private HandlerCollection createHandlers() {
        WebAppContext ctx = new WebAppContext();
        ctx.setContextPath("/");
        ctx.setWar(appBase);
        if(!Strings.isNullOrEmpty(webInfLocation)) {
            ctx.setDescriptor(webInfLocation);
        }
        // configure security to avoid err println "Null identity service, trying login service:"
        // but I've found no way to get rid of LoginService=xxx log on system err :(
        HashLoginService loginService = new HashLoginService();
        loginService.setIdentityService(new DefaultIdentityService());
        ctx.getSecurityHandler().setLoginService(loginService);
        ctx.getSecurityHandler().setIdentityService(loginService.getIdentityService());

        HandlerList contexts = new HandlerList();
        contexts.setHandlers(new Handler[]{ ctx });

        HandlerCollection result = new HandlerCollection();
        result.setHandlers(new Handler[]{contexts});

        return result;
    }
}
