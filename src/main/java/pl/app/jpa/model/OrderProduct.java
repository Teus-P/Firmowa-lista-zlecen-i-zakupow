package pl.app.jpa.model;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Mapowanie obiektowo-relacyjne między klasą OrderProduct a tabelą orderProduct
 */
@Entity(name = "OrderProduct")
@Table(name = "orderProduct")
public class OrderProduct implements Serializable {

    /* ******************************************************* *
     *                                                         *
     *               Mapowane pola klasy                       *
     *                                                         *
     * ******************************************************* */

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    private int idOrderProduct;

    @ManyToOne
    @JoinColumn(name = "idOrder", nullable = false, unique = true)
    private UserOrder orderProductInUserOrder;

    @ManyToOne
    @JoinColumn(name = "idProduct", nullable = false, unique = true)
    private Product productInOrder;

    @ManyToOne
    @JoinColumn(name = "idShoppingList", nullable = false)
    private ShoppingList orderProductInShoppingList;

    @Column(name = "quantity", length = 5, nullable = false)
    private int quantity;

    @Column(name = "inStock", length = 5, nullable = false)
    private int inStock;

    @Column(name = "issuedQuantity", length = 5, nullable = false)
    private int issuedQuantity;


    /* ******************************************************* *
     *                                                         *
     *                Konstruktory                             *
     *                                                         *
     * ******************************************************* */

    public OrderProduct() {
    }

    public OrderProduct(UserOrder orderProductInUserOrder, Product productInOrder, ShoppingList orderProductInShoppingList, int quantity, int inStock, int issuedQuantity) {
        this.orderProductInUserOrder = orderProductInUserOrder;
        this.productInOrder = productInOrder;
        this.orderProductInShoppingList = orderProductInShoppingList;
        this.quantity = quantity;
        this.inStock = inStock;
        this.issuedQuantity = issuedQuantity;
    }

    /* ******************************************************* *
     *                                                         *
     *              Getters and setters                        *
     *                                                         *
     * ******************************************************* */

    public int getIdOrderProduct() {
        return idOrderProduct;
    }

    public void setIdOrderProduct(int idOrderProduct) {
        this.idOrderProduct = idOrderProduct;
    }

    public UserOrder getOrderProductInUserOrder() {
        return orderProductInUserOrder;
    }

    public void setOrderProductInUserOrder(UserOrder orderProductInUserOrder) {
        this.orderProductInUserOrder = orderProductInUserOrder;
    }

    public Product getProductInOrder() {
        return productInOrder;
    }

    public void setProductInOrder(Product productInOrder) {
        this.productInOrder = productInOrder;
    }

    public ShoppingList getOrderProductInShoppingList() {
        return orderProductInShoppingList;
    }

    public void setOrderProductInShoppingList(ShoppingList orderProductInShoppingList) {
        this.orderProductInShoppingList = orderProductInShoppingList;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getInStock() {
        return inStock;
    }

    public void setInStock(int inStock) {
        this.inStock = inStock;
    }

    public int getIssuedQuantity() {
        return issuedQuantity;
    }

    public void setIssuedQuantity(int issuedQuantity) {
        this.issuedQuantity = issuedQuantity;
    }
}
