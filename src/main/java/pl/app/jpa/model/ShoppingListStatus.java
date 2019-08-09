package pl.app.jpa.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * Mapowanie obiektowo-relacyjne między klasą ShoppingListStatus a tabelą shoppingListStatus
 */
@Entity(name = "ShoppingListStatus")
@Table(name = "shoppingListStatus", schema = "praca_inz_db")
public class ShoppingListStatus implements Serializable {

    /* ******************************************************* *
     *                                                         *
     *               Mapowane pola klasy                       *
     *                                                         *
     * ******************************************************* */

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    private int idShoppingListStatus;

    @Column(name = "name", length = 50, nullable = false, unique = true)
    private String name;

    @Column(name = "description", nullable = false)
    private String description;


    /* ******************************************************* *
     *                                                         *
     *                Kolekcje dwukierunkowe                   *
     *                                                         *
     * ******************************************************* */

    // ShoppingListStatus -< ShoppingList
    @OneToMany(mappedBy = "shoppingListStatus_shoppingList")
    private Set<ShoppingList> myShoppingList = new HashSet<>();


    /* ******************************************************* *
     *                                                         *
     *                Konstruktory                             *
     *                                                         *
     * ******************************************************* */

    public ShoppingListStatus() {
    }

    public ShoppingListStatus(String name, String description) {
        this.name = name;
        this.description = description;
    }


    /* ******************************************************* *
     *                                                         *
     *              Getters and setters                        *
     *                                                         *
     * ******************************************************* */

    public int getIdShoppingListStatus() {
        return idShoppingListStatus;
    }

    public void setIdShoppingListStatus(int idShoppingListStatus) {
        this.idShoppingListStatus = idShoppingListStatus;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return zwraca wszystki ShoppingList dla tego ShoppingListStatus
     */
    public Set<ShoppingList> getMyShoppingList() {
        return myShoppingList;
    }

    public void setMyShoppingList(Set<ShoppingList> myShoppingList) {
        this.myShoppingList = myShoppingList;
    }
}
