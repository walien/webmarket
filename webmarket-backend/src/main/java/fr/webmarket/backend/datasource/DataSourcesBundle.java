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

package fr.webmarket.backend.datasource;

import fr.webmarket.backend.datasource.impl.MemoryDataSource;
import fr.webmarket.backend.datasource.impl.MongoDataSource;
import fr.webmarket.backend.datasource.providers.MemoryEntitySequenceProvider;
import fr.webmarket.backend.log.LoggerBundle;

public class DataSourcesBundle {

    private static DataSource dataSource;

    public static void initDataSource(String name) {
        if ("mongo".equalsIgnoreCase(name)) {
            dataSource = new MongoDataSource();
            LoggerBundle.getDefaultLogger().info("MongoDB will be used as datasource during this session.");
        } else if ("memory".equalsIgnoreCase(name)) {
            dataSource = new MemoryDataSource(new MemoryEntitySequenceProvider());
            LoggerBundle.getDefaultLogger().info("In-Memory DB will be used as datasource during this session.");
        } else {
            dataSource = new MemoryDataSource(new MemoryEntitySequenceProvider());
            LoggerBundle.getDefaultLogger().warn("No specific datasource provided : the in-memory data source " +
                    "will be used during this session !");
        }
    }

    public static DataSource getDataSource() {
        if (dataSource == null) {
            initDataSource(null);
        }
        return dataSource;
    }
}
