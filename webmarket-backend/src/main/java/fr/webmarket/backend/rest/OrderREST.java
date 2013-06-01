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

package fr.webmarket.backend.rest;

import fr.webmarket.backend.auth.ClientSessionManager;
import fr.webmarket.backend.datasource.DataSourcesBundle;
import fr.webmarket.backend.model.Order;
import fr.webmarket.backend.model.ResponseWrapper;
import fr.webmarket.backend.model.UserRole;
import fr.webmarket.backend.utils.DigestUtils;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.Collections;
import java.util.List;

@Path("/orders")
public class OrderREST {

    @GET
    public List<Order> getAllOrders(@QueryParam("sessionID") String sessionID) {

        if (!ClientSessionManager.getInstance().checkSessionAndRights(DigestUtils.parseSessionID(sessionID),
                UserRole.ADMIN)) {
            return Collections.EMPTY_LIST;
        }
        return DataSourcesBundle.getDataSource().getOrders().values().asList();
    }

    @GET
    @Path("{order-id}")
    public Order getOrder(@QueryParam("sessionID") String sessionID,
                          @PathParam("order-id") String orderID) {

        if (!ClientSessionManager.getInstance().checkSessionAndRights(DigestUtils.parseSessionID(sessionID),
                UserRole.ADMIN)) {
            return null;
        }
        return DataSourcesBundle.getDataSource().getOrder(orderID);
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON + ";charset=utf-8")
    public ResponseWrapper doOrder(@QueryParam("sessionID") String sessionID,
                                   Order order) {

        if (!ClientSessionManager.getInstance().checkSessionAndRights(DigestUtils.parseSessionID(sessionID),
                UserRole.CUSTOMER)) {
            return new ResponseWrapper().setStatus(false);
        }
        return new ResponseWrapper().setStatus(DataSourcesBundle.getDataSource()
                .addOrder(order));
    }

}
