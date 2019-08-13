package pl.app.jpa.model;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalTime;

/**
 * Mapowanie obiektowo-relacyjne między klasą SystemConfiguration a tabelą systemConfiguration
 */
@Entity(name = "SystemConfiguration")
@Table(name = "systemConfiguration")
public class SystemConfiguration implements Serializable {

    /* ******************************************************* *
     *                                                         *
     *               Mapowane pola klasy                       *
     *                                                         *
     * ******************************************************* */

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    private int idSystemConfiguration;

    @ManyToOne
    @JoinColumn(name = "idUserAccount", nullable = false)
    private UserAccount userAccount_systemConfiguration;

    @Column(name = "timeToPickUpOrder", nullable = false)
    private LocalTime timeToPickUpOrder;

    @Column(name = "beginningTimeToGiveOrder", nullable = false)
    private LocalTime beginningTimeToGiveOrder;

    @Column(name = "endingTimeToGiveOrder", nullable = false)
    private LocalTime endingTimeToGiveOrder;

    /* ******************************************************* *
     *                                                         *
     *                Konstruktory                             *
     *                                                         *
     * ******************************************************* */

    public SystemConfiguration() {
    }

    public SystemConfiguration(LocalTime timeToPickUpOrder, LocalTime beginningTimeToGiveOrder, LocalTime endingTimeToGiveOrder, UserAccount userAccount_systemConfiguration) {
        this.timeToPickUpOrder = timeToPickUpOrder;
        this.beginningTimeToGiveOrder = beginningTimeToGiveOrder;
        this.endingTimeToGiveOrder = endingTimeToGiveOrder;
        this.userAccount_systemConfiguration = userAccount_systemConfiguration;
    }

    /* ******************************************************* *
     *                                                         *
     *              Getters and setters                        *
     *                                                         *
     * ******************************************************* */

    public int getIdSystemConfiguration() {
        return idSystemConfiguration;
    }

    public void setIdSystemConfiguration(int idSystemConfiguration) {
        this.idSystemConfiguration = idSystemConfiguration;
    }

    public LocalTime getTimeToPickUpOrder() {
        return timeToPickUpOrder;
    }

    public void setTimeToPickUpOrder(LocalTime timeToPickUpOrder) {
        this.timeToPickUpOrder = timeToPickUpOrder;
    }

    public LocalTime getBeginningTimeToGiveOrder() {
        return beginningTimeToGiveOrder;
    }

    public void setBeginningTimeToGiveOrder(LocalTime beginningTimeToGiveOrder) {
        this.beginningTimeToGiveOrder = beginningTimeToGiveOrder;
    }

    public LocalTime getEndingTimeToGiveOrder() {
        return endingTimeToGiveOrder;
    }

    public void setEndingTimeToGiveOrder(LocalTime endingTimeToGiveOrder) {
        this.endingTimeToGiveOrder = endingTimeToGiveOrder;
    }

    public UserAccount getUserAccount_systemConfiguration() {
        return userAccount_systemConfiguration;
    }

    public void setUserAccount_systemConfiguration(UserAccount userAccount_systemConfiguration) {
        this.userAccount_systemConfiguration = userAccount_systemConfiguration;
    }
}
