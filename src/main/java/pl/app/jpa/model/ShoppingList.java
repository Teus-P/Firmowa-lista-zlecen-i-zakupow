package pl.app.jpa.model;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

/**
 * Mapowanie obiektowo-relacyjne między klasą ShoppingList a tabelą shoppingList
 */
@Entity(name = "ShoppingList")
@Table(name = "shoppingList")
public class ShoppingList implements Serializable {

    /* ******************************************************* *
     *                                                         *
     *               Mapowane pola klasy                       *
     *                                                         *
     * ******************************************************* */

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    private int idShoppingList;

    @ManyToOne
    @JoinColumn(name = "idShoppingListStatus", nullable = false)
    private ShoppingListStatus shoppingListStatus_shoppingList;

    @ManyToOne
    @JoinColumn(name = "idImplementers", nullable = false)
    private Implementers implementers_shoppingList;

    @Column(name = "readyStatus", nullable = false)
    private boolean readyStatus;

    @Column(name = "beginDate")
    private LocalDateTime beginDate;

    @Column(name = "endDate")
    private LocalDateTime endDate;

    /* ******************************************************* *
     *                                                         *
     *                Kolekcje dwukierunkowe                   *
     *                                                         *
     * ******************************************************* */

    //ShoppingList -< OrderProduct
    @OneToMany(mappedBy = "orderProductInShoppingList")
    private Set<OrderProduct> myOrderProduct = new HashSet<>();


    /* ******************************************************* *
     *                                                         *
     *                Konstruktory                             *
     *                                                         *
     * ******************************************************* */

    public ShoppingList() {
    }

    public ShoppingList(boolean readyStatus, LocalDateTime beginDate, LocalDateTime endDate, ShoppingListStatus shoppingListStatus_shoppingList, Implementers implementers_shoppingList) {
        this.readyStatus = readyStatus;
        this.beginDate = beginDate;
        this.endDate = endDate;
        this.shoppingListStatus_shoppingList = shoppingListStatus_shoppingList;
        this.implementers_shoppingList = implementers_shoppingList;
    }

    /* ******************************************************* *
     *                                                         *
     *              Getters and setters                        *
     *                                                         *
     * ******************************************************* */

    public int getIdShoppingList() {
        return idShoppingList;
    }

    public void setIdShoppingList(int idShoppingList) {
        this.idShoppingList = idShoppingList;
    }

    public boolean isReadyStatus() {
        return readyStatus;
    }

    public void setReadyStatus(boolean readyStatus) {
        this.readyStatus = readyStatus;
    }

    public LocalDateTime getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(LocalDateTime beginDate) {
        this.beginDate = beginDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    public ShoppingListStatus getShoppingListStatus_shoppingList() {
        return shoppingListStatus_shoppingList;
    }

    public void setShoppingListStatus_shoppingList(ShoppingListStatus shoppingListStatus_shoppingList) {
        this.shoppingListStatus_shoppingList = shoppingListStatus_shoppingList;
    }

    public Implementers getImplementers_shoppingList() {
        return implementers_shoppingList;
    }

    public void setImplementers_shoppingList(Implementers implementers_shoppingList) {
        this.implementers_shoppingList = implementers_shoppingList;
    }

    /**
     * @return wyświetla wszystkie produkty w zamówieniu dla tego ShoppingList
     */
    public Set<OrderProduct> getMyOrderProduct() {
        return myOrderProduct;
    }

    public void setMyOrderProduct(Set<OrderProduct> myOrderProduct) {
        this.myOrderProduct = myOrderProduct;
    }
}
