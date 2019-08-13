package pl.app.jpa.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * Mapowanie obiektowo-relacyjne między klasą Product a tabelą product
 */
@Entity(name = "Product")
@Table(name = "product")
public class Product implements Serializable {

    /* ******************************************************* *
     *                                                         *
     *               Mapowane pola klasy                       *
     *                                                         *
     * ******************************************************* */

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    private int idProduct;

    @ManyToOne
    @JoinColumn(name = "idCategories", nullable = false)
    private Categories productCategories;

    @ManyToOne
    @JoinColumn(name = "idUnit", nullable = false)
    private Unit productUnit;

    @Column(name = "name", length = 200, nullable = false, unique = true)
    private String name;

    @Column(name = "quantity", nullable = false)
    private int quantity;

    @Column(name = "deleted")
    private boolean deleted;

    /* ******************************************************* *
     *                                                         *
     *                Kolekcje dwukierunkowe                   *
     *                                                         *
     * ******************************************************* */

    // Product -< OrderProduct
    @OneToMany(mappedBy = "productInOrder")
    private Set<OrderProduct> myOrderProduct = new HashSet<>();

    /* ******************************************************* *
     *                                                         *
     *                Konstruktory                             *
     *                                                         *
     * ******************************************************* */

    public Product() {
    }

    public Product(Categories productCategories, Unit productUnit, String name, int quantity, boolean deleted) {
        this.productCategories = productCategories;
        this.productUnit = productUnit;
        this.name = name;
        this.quantity = quantity;
        this.deleted = deleted;
    }

    /* ******************************************************* *
     *                                                         *
     *              Getters and setters                        *
     *                                                         *
     * ******************************************************* */

    public int getIdProduct() {
        return idProduct;
    }

    public void setIdProduct(int idProduct) {
        this.idProduct = idProduct;
    }

    public Categories getProductCategories() {
        return productCategories;
    }

    public void setProductCategories(Categories productCategories) {
        this.productCategories = productCategories;
    }

    public Unit getProductUnit() {
        return productUnit;
    }

    public void setProductUnit(Unit productUnit) {
        this.productUnit = productUnit;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    /**
     * @return wyświetla wszystkie OrderProduct dla tego produktu
     */
    public Set<OrderProduct> getMyOrderProduct() {
        return myOrderProduct;
    }

    public void setMyOrderProduct(Set<OrderProduct> myOrderProduct) {
        this.myOrderProduct = myOrderProduct;
    }
}
