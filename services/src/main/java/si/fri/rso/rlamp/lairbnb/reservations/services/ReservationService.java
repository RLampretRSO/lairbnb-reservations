package si.fri.rso.rlamp.lairbnb.reservations.services;

import com.kumuluz.ee.discovery.annotations.DiscoverService;
import com.kumuluz.ee.rest.beans.QueryParameters;
import com.kumuluz.ee.rest.utils.JPAUtils;
import si.fri.rso.rlamp.lairbnb.reservations.models.Lair;
import si.fri.rso.rlamp.lairbnb.reservations.models.Reservation;
import si.fri.rso.rlamp.lairbnb.reservations.models.User;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.GenericType;
import java.util.List;
import java.util.Optional;

@RequestScoped
public class ReservationService {

    @PersistenceContext
    private EntityManager em;

    @Inject
    @DiscoverService(value = "lairbnb-users", version = "*", environment = "dev")
    private Optional<String> usersBaseUrl;

    @Inject
    @DiscoverService(value = "lairbnb-lairs", version = "*", environment = "dev")
    private Optional<String> lairsBaseUrl;

    private Client httpClient = ClientBuilder.newClient();;

    public List<Reservation> getAllReservations() {
        List<Reservation> reservations = em
                .createNamedQuery("Reservation.findAll", Reservation.class)
                .getResultList();

        return reservations;
    }

    public List<Reservation> getReservations(QueryParameters query) {
        List<Reservation> reservations = JPAUtils.queryEntities(em, Reservation.class, query);
        return reservations;
    }

    public Long getReservationCount(QueryParameters query) {
        Long count = JPAUtils.queryEntitiesCount(em, Reservation.class, query);
        return count;
    }

    public Reservation getReservation(Integer reservId) {
        Reservation reserv = em.find(Reservation.class, reservId);

        if (reserv != null) {
            reserv.setHost(this.getUser(reserv.getHostUserId()));
            reserv.setUser(this.getUser(reserv.getUserUserId()));
            reserv.setLair(this.getLair(reserv.getLairId()));
        }

        return reserv;
    }

    @Transactional
    public Reservation createReservation(Reservation reserv) {
        if (reserv == null) return null;
        em.persist(reserv);

        return reserv;
    }

    @Transactional
    public Reservation putReservation(Integer reservId, Reservation reserv) {
        if (reserv == null) return null;

        Reservation u = em.find(Reservation.class, reservId);
        if (u == null) return null;

        reserv.setId(u.getId());
        reserv = em.merge(reserv);

        return reserv;
    }

    @Transactional(Transactional.TxType.REQUIRED)
    public boolean deleteReservation(Integer reservId) {
        Reservation reserv = em.find(Reservation.class, reservId);
        if (reserv == null) return false;

        em.remove(reserv);

        return true;
    }

    public User getUser(Integer userId) {
        // Calling with filter rather than users/{id} since this call
        // will make additional calls to other service
        if (usersBaseUrl.isPresent()) {
            List<User> result = httpClient.target(usersBaseUrl.get() +
                    "/v1/users?filter=id:EQ:" + userId.toString())
                    .request().get(new GenericType<List<User>>() {
                    });

            if (result != null && !result.isEmpty()) {
                return result.get(0);
            }
        }
        return null;
    }

    public Lair getLair(Integer lairId) {
        // Calling with filter rather than lairs/{id} since this call
        // will make additional calls to other service

        if (lairsBaseUrl.isPresent()) {
            List<Lair> result = httpClient.target(lairsBaseUrl.get() +
                    "/v1/lairs?filter=id:EQ:" + lairId.toString())
                    .request().get(new GenericType<List<Lair>>() {
                    });

            if (result != null && !result.isEmpty()) {
                return result.get(0);
            }
        }
        return null;
    }
}
