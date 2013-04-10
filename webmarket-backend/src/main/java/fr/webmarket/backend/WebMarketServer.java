package fr.webmarket.backend;

import fr.webmarket.backend.servers.JettyWebServer;

/**
 * User: walien
 * Date: 10/04/13
 * Time: 21:26
 */
public class WebMarketServer {


    private static final String WEB_XML_FILE = "webmarket-backend/src/main/webapp/WEB-INF/web.xml";
    private static final String WEBAPP_RESOURCES = "webmarket-web-ui/app";
    private static final String BIND_INTERFACE = "localhost";
    private static final int BIND_PORT = 8080;

    public static void main(String[] args) throws Exception {

        // Instantiate the Jetty Web Server
        new JettyWebServer(WEB_XML_FILE, WEBAPP_RESOURCES,
                BIND_PORT, BIND_INTERFACE).startAndAwait();

    }
}
