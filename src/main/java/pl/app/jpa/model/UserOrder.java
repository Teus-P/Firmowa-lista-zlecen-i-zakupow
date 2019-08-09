package pl.app.jpa.model;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

/**
 * Mapowanie obiektowo-relacyjne między klasą UserOrder a tabelą userOrder
 */
@Entity(name = "UserOrder")
@Table(name = "userOrder", schema = "praca_inz_db")
public class UserOrder implements Serializable {

    /* ******************************************************* *
     *                                                         *
     *               Mapowane pola klasy                       *
     *                                                         *
     * ******************************************************* */

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    private int idOrder;

    @ManyToOne
    @JoinColumn(name = "idUserAccount", nullable = false)
    private UserAccount userAccount_order;

    @ManyToOne
    @JoinColumn(name = "idOrderStatus", nullable = false)
    private OrderStatus orderStatus_order;

    @ManyToOne
    @JoinColumn(name = "idStoreKeeper")
    private UserAccount storeKeeper;

    @ManyToOne
    @JoinColumn(name = "idRecipient")
    private UserAccount recipient;

    @Column(name = "date", nullable = false)
    private LocalDateTime date;

    @Column(name = "userInfo", nullable = false)
    private boolean userInfo;

    /* ******************************************************* *
     *                                                         *
     *                Kolekcje dwukierunkowe                   *
     *                                                         *
     * ******************************************************* */

    //UserOrder -< OrderProduct
    @OneToMany(mappedBy = "orderProductInUserOrder")
    private Set<OrderProduct> myOrderProduct = new HashSet<>();

    /* ******************************************************* *
     *                                                         *
     *                Konstruktory                             *
     *                                                         *
     * ******************************************************* */


    public UserOrder() {
    }

    public UserOrder(LocalDateTime date, boolean userInfo, UserAccount userAccount_order, UserAccount storeKeeper, UserAccount recipient, OrderStatus orderStatus_order) {
        this.date = date;
        this.userInfo = userInfo;
        this.userAccount_order = userAccount_order;
        this.storeKeeper = storeKeeper;
        this.recipient = recipient;
        this.orderStatus_order = orderStatus_order;
    }

    /* ******************************************************* *
     *                                                         *
     *              Getters and setters                        *
     *                                                         *
     * ******************************************************* */

    public int getIdOrder() {
        return idOrder;
    }

    public void setIdOrder(int idOrder) {
        this.idOrder = idOrder;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public boolean isUserInfo() {
        return userInfo;
    }

    public void setUserInfo(boolean userInfo) {
        this.userInfo = userInfo;
    }

    public UserAccount getUserAccount_order() {
        return userAccount_order;
    }

    public void setUserAccount_order(UserAccount userAccount_order) {
        this.userAccount_order = userAccount_order;
    }

    public UserAccount getStoreKeeper() {
        return storeKeeper;
    }

    public void setStoreKeeper(UserAccount storeKeeper) {
        this.storeKeeper = storeKeeper;
    }

    public UserAccount getRecipient() {
        return recipient;
    }

    public void setRecipient(UserAccount recipient) {
        this.recipient = recipient;
    }

    public OrderStatus getOrderStatus_order() {
        return orderStatus_order;
    }

    public void setOrderStatus_order(OrderStatus orderStatus_order) {
        this.orderStatus_order = orderStatus_order;
    }

    /**
     * @return wyświetla wszystkie produkty dla tego zamówienia
     */
    public Set<OrderProduct> getMyOrderProduct() {
        return myOrderProduct;
    }

    public void setMyOrderProduct(Set<OrderProduct> myOrderProduct) {
        this.myOrderProduct = myOrderProduct;
    }
}
