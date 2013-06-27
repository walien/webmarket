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
import fr.webmarket.backend.model.Coupon;
import fr.webmarket.backend.model.ResponseWrapper;
import fr.webmarket.backend.model.UserRole;
import fr.webmarket.backend.utils.DigestUtils;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Path("/coupons")
public class CouponsREST {

    @GET
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    public List<Coupon> getAllCoupons() throws IOException {
        return new ArrayList<Coupon>(DataSourcesBundle.getDataSource().getCoupons().values());
    }

    @GET
    @Path("{coupon-id}")
    public Coupon getCoupon(@QueryParam("sessionID") String sessionID,
                            @PathParam("coupon-id") String couponID) {

        if (!ClientSessionManager.getInstance().checkSessionAndRights(DigestUtils.parseSessionID(sessionID),
                UserRole.ADMIN)) {
            return null;
        }

        return DataSourcesBundle.getDataSource().getCouponById(couponID);
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON + ";charset=utf-8")
    public ResponseWrapper addCoupon(@QueryParam("sessionID") String sessionID,
                                     Coupon coupon) {

        if (!ClientSessionManager.getInstance().checkSessionAndRights(DigestUtils.parseSessionID(sessionID),
                UserRole.ADMIN)) {
            return new ResponseWrapper().setStatus(false);
        }
        boolean result = DataSourcesBundle.getDataSource().addCoupon(coupon);

        return new ResponseWrapper().setStatus(result);
    }
}
