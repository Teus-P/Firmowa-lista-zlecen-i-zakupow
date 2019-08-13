package pl.app.jpa.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * Mapowanie obiektowo-relacyjne między klasą Implementers a tabelą implementers
 */
@Entity(name = "Implementers")
@Table(name = "implementers")
public class Implementers implements Serializable {

    /* ******************************************************* *
     *                                                         *
     *               Mapowane pola klasy                       *
     *                                                         *
     * ******************************************************* */

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    private int idImplementers;

    @ManyToOne
    @JoinColumn(name = "idCategories", nullable = false)
    private Categories categories_implementers;

    @ManyToOne
    @JoinColumn(name = "idUserAccount", nullable = false)
    private UserAccount userAccount_implementers;

    /* ******************************************************* *
     *                                                         *
     *                Kolekcje dwukierunkowe                   *
     *                                                         *
     * ******************************************************* */

    // Implementers -< ShoppingList
    @OneToMany(mappedBy = "implementers_shoppingList")
    private Set<ShoppingList> myShoppingList = new HashSet<>();

    /* ******************************************************* *
     *                                                         *
     *                Konstruktory                             *
     *                                                         *
     * ******************************************************* */

    public Implementers() {
    }

    public Implementers(UserAccount userAccount_implementers, Categories categories_implementers) {
        this.userAccount_implementers = userAccount_implementers;
        this.categories_implementers = categories_implementers;
    }

    /* ******************************************************* *
     *                                                         *
     *              Getters and setters                        *
     *                                                         *
     * ******************************************************* */

    public int getIdImplementers() {
        return idImplementers;
    }

    public void setIdImplementers(int idImplementers) {
        this.idImplementers = idImplementers;
    }

    public UserAccount getUserAccount_implementers() {
        return userAccount_implementers;
    }

    public void setUserAccount_implementers(UserAccount userAccount_implementers) {
        this.userAccount_implementers = userAccount_implementers;
    }

    public Categories getCategories_implementers() {
        return categories_implementers;
    }

    public void setCategories_implementers(Categories categories_implementers) {
        this.categories_implementers = categories_implementers;
    }

    /**
     * @return zwraca wszystkie listy zakupów dla tego realizatora
     */
    public Set<ShoppingList> getMyShoppingList() {
        return myShoppingList;
    }

    public void setMyShoppingList(Set<ShoppingList> myShoppingList) {
        this.myShoppingList = myShoppingList;
    }
}
