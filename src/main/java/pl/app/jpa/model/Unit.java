package pl.app.jpa.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * Mapowanie obiektowo-relacyjne między klasą Unit a tabelą unit
 */
@Entity(name = "Unit")
@Table(name = "unit", schema = "praca_inz_db")
public class Unit implements Serializable {

    /* ******************************************************* *
     *                                                         *
     *               Mapowane pola klasy                       *
     *                                                         *
     * ******************************************************* */

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    private int idUnit;

    @Column(name = "unit", length = 20, nullable = false, unique = true)
    private String unit;

    /* ******************************************************* *
     *                                                         *
     *                Kolekcje dwukierunkowe                   *
     *                                                         *
     * ******************************************************* */

    // Unit -< Product
    @OneToMany(mappedBy = "productUnit")
    private Set<Product> myProduct = new HashSet<>();

    /* ******************************************************* *
     *                                                         *
     *                Konstruktory                             *
     *                                                         *
     * ******************************************************* */

    public Unit() {
    }

    public Unit(String unit) {
        this.unit = unit;
    }

    /* ******************************************************* *
     *                                                         *
     *              Getters and setters                        *
     *                                                         *
     * ******************************************************* */

    public int getIdUnit() {
        return idUnit;
    }

    public void setIdUnit(int idUnit) {
        this.idUnit = idUnit;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    /**
     * @return wszystkie produkty dla tej jednostki
     */
    public Set<Product> getMyProduct() {
        return myProduct;
    }

    public void setMyProduct(Set<Product> myProduct) {
        this.myProduct = myProduct;
    }
}
