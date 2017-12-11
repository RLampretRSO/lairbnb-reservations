package si.fri.rso.rlamp.reservations.api.v1.resources;

import com.kumuluz.ee.rest.beans.QueryParameters;
import si.fri.rso.rlamp.lairbnb.reservations.models.Reservation;
import si.fri.rso.rlamp.lairbnb.reservations.services.ReservationService;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.List;

@RequestScoped
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Path("/reservations")
public class ReservationResource {

        @Context
        protected UriInfo uriInfo;
        @Inject
        private ReservationService reservBean;

        @GET
        @Path("/all")
        public Response getAllCustomers() {
            List<Reservation> customers = reservBean.getAllReservations();
            return Response.ok(customers).build();
        }

        @GET
        public Response getCustomers() {
            List<Reservation> customers = reservBean.getReservations(createQuery());
            return Response.ok(customers).build();
        }

        @GET
        @Path("/count")
        public Response getCount() {
            Long count = reservBean.getReservationCount(createQuery());
            return Response.ok(count).build();
        }

        @GET
        @Path("/{reservId}")
        public Response getCustomer(@PathParam("reservId") Integer reservId) {
            Reservation reserv = reservBean.getReservation(reservId);
            return reserv != null
                    ? Response.ok(reserv).build()
                    : Response.status(Response.Status.NOT_FOUND).build();
        }

        @POST
        public Response addNewReservation(Reservation reserv) {
            reservBean.createReservation(reserv);
            return Response.noContent().build();
        }

        @PUT
        @Path("/{reservId}")
        public Response updateReservation(@PathParam("reservId") Integer reservId, Reservation reserv) {
            reservBean.putReservation(reservId, reserv);
            return Response.noContent().build();
        }

        @DELETE
        @Path("/{reservId}")
        public Response deleteReservation(@PathParam("reservId") Integer reservId) {
            reservBean.deleteReservation(reservId);
            return Response.noContent().build();
        }

        /**
         * Helper method for parsing query parameters from uri.
         *
         * @return query parameters
         */
        private QueryParameters createQuery() {
            return QueryParameters.query(uriInfo.getRequestUri().getQuery()).defaultOffset(0).defaultLimit(10).build();
        }
}
