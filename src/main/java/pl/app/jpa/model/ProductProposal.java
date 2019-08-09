package pl.app.jpa.model;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Mapowanie obiektowo-relacyjne między klasą ProductProposal a tabelą productProposal
 */
@Entity(name = "ProductProposal")
@Table(name = "productProposal", schema = "praca_inz_db")
public class ProductProposal implements Serializable {

    /* ******************************************************* *
     *                                                         *
     *               Mapowane pola klasy                       *
     *                                                         *
     * ******************************************************* */

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private int idProductProposal;

    @ManyToOne
    @JoinColumn(name = "idUserAccount")
    private UserAccount userAccount_productProposal;

    @ManyToOne
    @JoinColumn(name = "idCategories")
    private Categories categories_productProposal;

    @Column(name = "name", length = 200, nullable = false)
    private String name;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "acceptedStatus", nullable = false)
    private boolean acceptedStatus;

    /* ******************************************************* *
     *                                                         *
     *                Konstruktory                             *
     *                                                         *
     * ******************************************************* */

    public ProductProposal() {
    }

    public ProductProposal(String name, String description, boolean acceptedStatus, UserAccount userAccount_productProposal, Categories categories_productProposal) {
        this.name = name;
        this.description = description;
        this.acceptedStatus = acceptedStatus;
        this.userAccount_productProposal = userAccount_productProposal;
        this.categories_productProposal = categories_productProposal;
    }

    /* ******************************************************* *
     *                                                         *
     *              Getters and setters                        *
     *                                                         *
     * ******************************************************* */

    public int getIdProductProposal() {
        return idProductProposal;
    }

    public void setIdProductProposal(int idProductProposal) {
        this.idProductProposal = idProductProposal;
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

    public boolean isAcceptedStatus() {
        return acceptedStatus;
    }

    public void setAcceptedStatus(boolean acceptedStatus) {
        this.acceptedStatus = acceptedStatus;
    }

    public UserAccount getUserAccount_productProposal() {
        return userAccount_productProposal;
    }

    public void setUserAccount_productProposal(UserAccount userAccount_productProposal) {
        this.userAccount_productProposal = userAccount_productProposal;
    }

    public Categories getCategories_productProposal() {
        return categories_productProposal;
    }

    public void setCategories_productProposal(Categories categories_productProposal) {
        this.categories_productProposal = categories_productProposal;
    }
}
