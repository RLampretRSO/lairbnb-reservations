package si.fri.rso.rlamp.lairbnb.reservations.models;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "reservation")
@NamedQueries({
        @NamedQuery(name = "Reservation.findAll", query = "SELECT r FROM Reservation r")
})
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Basic(optional = false)
    @Column(name = "host_id")
    private Integer hostUserId;
    @Transient
    private User host;
    @Basic(optional = false)
    @Column(name = "user_id")
    private Integer userUserId;
    @Transient
    private User user;
    @Basic(optional = false)
    @Column(name = "lair_id")
    private Integer lairId;
    @Transient
    private Lair lair;
    @Basic(optional = false)
    @Temporal(TemporalType.DATE)
    @Column(name = "reservation_date")
    private Date date;
    @Basic(optional = false)
    @Column(name = "nights")
    private Integer nights;
    @Basic(optional = false)
    @Column(name = "price")
    private Float price;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getHostUserId() {
        return hostUserId;
    }

    public void setHostUserId(Integer hostUserId) {
        this.hostUserId = hostUserId;
    }

    public User getHost() {
        return host;
    }

    public void setHost(User host) {
        this.host = host;
    }

    public Integer getUserUserId() {
        return userUserId;
    }

    public void setUserUserId(Integer userUserId) {
        this.userUserId = userUserId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Integer getLairId() {
        return lairId;
    }

    public void setLairId(Integer lairId) {
        this.lairId = lairId;
    }

    public Lair getLair() {
        return lair;
    }

    public void setLair(Lair lair) {
        this.lair = lair;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Integer getNights() {
        return nights;
    }

    public void setNights(Integer nights) {
        this.nights = nights;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }
}
