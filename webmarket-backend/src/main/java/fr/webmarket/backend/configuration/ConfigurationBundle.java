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

package fr.webmarket.backend.configuration;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.Properties;

/**
 * User: walien
 * Date: 7/16/13
 * Time: 7:19 PM
 */
public class ConfigurationBundle {


    public static final String PERSISTENCE_TYPE = "persistence.type";
    private static ConfigurationBundle INSTANCE;
    private final Properties properties;

    private ConfigurationBundle() {
        properties = new Properties();
    }

    public static ConfigurationBundle getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new ConfigurationBundle();
        }
        return INSTANCE;
    }

    public ConfigurationBundle loadFromFile(String path) {

        try {
            Reader reader = new FileReader(new File(path));
            properties.load(reader);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return this;
    }

    public String getValue(String key) {
        return properties.getProperty(key);
    }

    public void setValue(String key, String value) {
        properties.setProperty(key, value);
    }

}
