package pl.app.jpa.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * Mapowanie obiektowo-relacyjne między klasą Categories a tabelą categories
 */
@Entity(name = "Categories")
@Table(name = "categories")
public class Categories implements Serializable {

    /* ******************************************************* *
     *                                                         *
     *               Mapowane pola klasy                       *
     *                                                         *
     * ******************************************************* */

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private int idCategories;

    @Column(name = "name", length = 200, nullable = false, unique = true)
    private String name;

    /* ******************************************************* *
     *                                                         *
     *                Kolekcje dwukierunkowe                   *
     *                                                         *
     * ******************************************************* */

    //Categories -< Implementers
    @OneToMany(mappedBy = "categories_implementers")
    private Set<Implementers> myImplementers = new HashSet<>();

    //Categories -< ProductProposal
    @OneToMany(mappedBy = "categories_productProposal")
    private Set<ProductProposal> myProposal = new HashSet<>();

    //Categories -< Product
    @OneToMany(mappedBy = "productCategories")
    private Set<Product> myProduct = new HashSet<>();

    /* ******************************************************* *
     *                                                         *
     *                Konstruktory                             *
     *                                                         *
     * ******************************************************* */

    public Categories() {
    }

    public Categories(String name) {
        this.name = name;
    }


    /* ******************************************************* *
     *                                                         *
     *              Getters and setters                        *
     *                                                         *
     * ******************************************************* */

    public int getIdCategories() {
        return idCategories;
    }

    public void setIdCategories(int idCategories) {
        this.idCategories = idCategories;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return wszyscy Realizatorzy dla tej kategorii
     */
    public Set<Implementers> getMyImplementers() {
        return myImplementers;
    }

    public void setMyImplementers(Set<Implementers> myImplementers) {
        this.myImplementers = myImplementers;
    }

    /**
     * @return wszystkie obiekty (Propozycje produktu) dla tej kategorii
     */
    public Set<ProductProposal> getMyProposal() {
        return myProposal;
    }

    public void setMyProposal(Set<ProductProposal> myProposal) {
        this.myProposal = myProposal;
    }

    /**
     * @return wszystkie produkty tej kategorii
     */
    public Set<Product> getMyProduct() {
        return myProduct;
    }

    public void setMyProduct(Set<Product> myProduct) {
        this.myProduct = myProduct;
    }
}
