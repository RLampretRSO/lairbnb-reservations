package si.fri.rso.rlamp.lairbnb.reservations.services;

import com.kumuluz.ee.rest.beans.QueryParameters;
import com.kumuluz.ee.rest.utils.JPAUtils;
import si.fri.rso.rlamp.lairbnb.reservations.models.Lair;
import si.fri.rso.rlamp.lairbnb.reservations.models.Reservation;
import si.fri.rso.rlamp.lairbnb.reservations.models.User;

import javax.enterprise.context.RequestScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@RequestScoped
public class ReservationService {

    @PersistenceContext
    private EntityManager em;

    public List<Reservation> getAllReservations() {
        List<Reservation> customers = em
                .createNamedQuery("Reservation.findAll", Reservation.class)
                .getResultList();

        return customers;
    }

    public List<Reservation> getReservations(QueryParameters query) {
        List<Reservation> customers = JPAUtils.queryEntities(em, Reservation.class, query);
        return customers;
    }

    public Long getReservationCount(QueryParameters query) {
        Long count = JPAUtils.queryEntitiesCount(em, Reservation.class, query);
        return count;
    }

    public Reservation getReservation(Integer reservId) {
        Reservation reserv = em.find(Reservation.class, reservId);

//        if (reserv != null) {
//            reserv.setHost(new User()); // TODO
//            reserv.setUser(new User()); // TODO
//            reserv.setLair(new Lair()); // TODO
//        }

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
}
