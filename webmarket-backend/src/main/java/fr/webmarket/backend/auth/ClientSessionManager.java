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

package fr.webmarket.backend.auth;

import com.google.common.collect.Maps;
import fr.webmarket.backend.model.User;
import fr.webmarket.backend.model.UserRole;

import java.util.Map;
import java.util.UUID;

public class ClientSessionManager {

    private static ClientSessionManager INSTANCE;
    private Map<User, UUID> sessions;

    private ClientSessionManager() {
        sessions = Maps.newConcurrentMap();
    }

    public static ClientSessionManager getInstance() {
        if (null == INSTANCE) {
            INSTANCE = new ClientSessionManager();
        }
        return INSTANCE;
    }

    public UUID getSessionID(User u) {
        return sessions.get(u);
    }

    public User getUserFromSession(UUID id) {
        if (!checkSession(id)) {
            return null;
        }
        for (Map.Entry<User, UUID> entry : sessions.entrySet()) {
            if (entry.getValue().equals(id)) {
                return entry.getKey();
            }
        }
        return null;
    }

    public UUID createSession(User u) {
        UUID newID = UUID.randomUUID();
        sessions.put(u, newID);
        return newID;
    }

    public boolean checkSession(UUID id) {
        return id != null && sessions.values().contains(id);
    }

    public boolean checkSessionAndRights(UUID id, UserRole neededRole) {
        User user = getUserFromSession(id);
        return user != null && user.getRole() == neededRole;
    }

    public UUID destroySession(User u) {
        return sessions.remove(u);
    }
}
