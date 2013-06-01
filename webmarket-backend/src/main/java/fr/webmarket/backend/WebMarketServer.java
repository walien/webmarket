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

package fr.webmarket.backend;

import fr.webmarket.backend.datasource.DataSourcesBundle;
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

        // Init the datasource (depending on the provided parameter "datasource", eg "-datasource = 'mongo'")
        boolean providedDS = false;
        for (String arg : args) {
            if (arg.startsWith("-datasource") && !providedDS) {
                String ds = arg.substring(arg.indexOf('=') + 1);
                DataSourcesBundle.initDataSource(ds);
                providedDS = true;
            }
        }
        if (!providedDS) {
            DataSourcesBundle.initDataSource(null);
        }

        // Instantiate the Jetty Web Server
        new JettyWebServer(WEB_XML_FILE, WEBAPP_RESOURCES,
                BIND_PORT, BIND_INTERFACE).startAndAwait();

    }
}
