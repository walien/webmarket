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

package fr.webmarket.backend.filters;

import com.sun.jersey.spi.container.ContainerRequest;
import com.sun.jersey.spi.container.ContainerRequestFilter;
import fr.webmarket.backend.log.LoggerBundle;
import fr.webmarket.backend.rest.auth.AuthUtils;
import fr.webmarket.backend.rest.auth.ClientSessionManager;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

/**
 * User: walien
 * Date: 08/05/13
 * Time: 14:40
 */
public class AuthRequestFilter implements ContainerRequestFilter {

    @Override
    public ContainerRequest filter(ContainerRequest containerRequest) {

        // The session ID as query parameter
        String sessionID = containerRequest.getQueryParameters().get("sessionID").get(0);

        // POST requests require authentication
        if ("POST".equals(containerRequest.getMethod())) {
            // Add item ==> POST /items
            if ("items".equals(containerRequest.getPath())
                    && !ClientSessionManager.getInstance().checkSession(AuthUtils.parseSessionID(sessionID))) {
                LoggerBundle.getDefaultLogger().info("A request with path '{}' was refused. Bad sessionID.", containerRequest.getPath());
                rejectRequest();
            }
            // Update item ==> POST /items/:id
            if (containerRequest.getPath().matches("items/\\d+")
                    && !ClientSessionManager.getInstance().checkSession(AuthUtils.parseSessionID(sessionID))) {
                LoggerBundle.getDefaultLogger().info("A request with path '{}' was refused. Bad sessionID.", containerRequest.getPath());
                rejectRequest();
            }
        }

        // DELETE requests require authentication
        if ("DELETE".equals(containerRequest.getMethod())) {
            // Delete item ==> DELETE /items/:id
            if (containerRequest.getPath().matches("items/\\d+")
                    && !ClientSessionManager.getInstance().checkSession(AuthUtils.parseSessionID(sessionID))) {
                LoggerBundle.getDefaultLogger().info("A request with path '{}' was refused. Bad sessionID.", containerRequest.getPath());
                rejectRequest();
            }
        }

        return containerRequest;
    }

    private ContainerRequest rejectRequest() {
        throw new WebApplicationException(Response.Status.FORBIDDEN);
    }
}
