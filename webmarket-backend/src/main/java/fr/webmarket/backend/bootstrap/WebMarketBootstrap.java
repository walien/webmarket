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

package fr.webmarket.backend.bootstrap;

import fr.webmarket.backend.configuration.ConfigurationBundle;
import fr.webmarket.backend.datasource.DataSourcesBundle;
import fr.webmarket.backend.log.LoggerBundle;

/**
 * User: walien
 * Date: 7/16/13
 * Time: 7:30 PM
 */
public class WebMarketBootstrap {

    public static final String WEBMARKET_CONF_ENV = "WEBMARKET_CONF";

    public static void doBootstrap() {

        // 0. Load configuration
        String webMarketConf = System.getenv(WEBMARKET_CONF_ENV);
        if (webMarketConf == null) {
            LoggerBundle.getDefaultLogger().warn("No configuration file found. Have you properly defined the " +
                    "environment variable '{}' ?", WEBMARKET_CONF_ENV);
        } else {
            LoggerBundle.getDefaultLogger().info("Configuration (file '{}') successfuly loaded.", webMarketConf);
            ConfigurationBundle.getInstance().loadFromFile(webMarketConf);
        }

        // 2. Configure persistence mode
        DataSourcesBundle.initDataSource(ConfigurationBundle.getInstance().getValue(ConfigurationBundle.PERSISTENCE_TYPE));

        // 3.
        // TODO
    }
}
