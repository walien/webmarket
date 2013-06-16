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

import fr.webmarket.backend.features.commercial.OrderingBusinessUnit;
import fr.webmarket.backend.model.Order;
import fr.webmarket.backend.model.ResponseWrapper;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;

@Path("/cart")
public class CartREST {

    @POST
    @Path("computeAmount")
    @Consumes("application/json")
    public ResponseWrapper getCartAmount(Order order) {
        double amount = OrderingBusinessUnit.computeTotalAmount(order);
        return new ResponseWrapper().setValue(amount);
    }
}
