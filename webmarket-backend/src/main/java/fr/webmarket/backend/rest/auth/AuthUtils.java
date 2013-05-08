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

package fr.webmarket.backend.rest.auth;

import fr.webmarket.backend.log.LoggerBundle;

import java.util.UUID;

public class AuthUtils {

    public static UUID parseSessionID(String sessionID) {

        if (sessionID == null) {
            return null;
        }

        try {
            return UUID.fromString(sessionID);
        } catch (Exception e) {
            LoggerBundle.getDefaultLogger().info(
                    "Wrong uuid from REST request : " + sessionID);
        }
        return null;
    }
}
