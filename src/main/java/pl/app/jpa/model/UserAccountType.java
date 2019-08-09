package pl.app.jpa.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * Mapowanie obiektowo-relacyjne między klasą UserAccountType a tabelą userAccountType
 */
@Entity(name = "UserAccountType")
@Table(name = "userAccountType", schema = "praca_inz_db")
public class UserAccountType implements Serializable {

    /* ******************************************************* *
     *                                                         *
     *               Mapowane pola klasy                       *
     *                                                         *
     * ******************************************************* */

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    private int idUserAccountType;

    @Column(name = "name", length = 200, nullable = false)
    private String name;

    @Column(name = "description", nullable = false)
    private String description;

    /* ******************************************************* *
     *                                                         *
     *                Kolekcje dwukierunkowe                   *
     *                                                         *
     * ******************************************************* */

    // UserAccountType -< UserAccount (UserAccountTypeCollection)
    @ManyToMany(mappedBy = "userAccountTypes")
    private Set<UserAccount> userAccounts = new HashSet<>();

    /* ******************************************************* *
     *                                                         *
     *                Konstruktory                             *
     *                                                         *
     * ******************************************************* */

    public UserAccountType() {
    }

    public UserAccountType(String name, String description) {
        this.name = name;
        this.description = description;
    }

    /* ******************************************************* *
     *                                                         *
     *              Getters and setters                        *
     *                                                         *
     * ******************************************************* */

    public int getIdUserAccountType() {
        return idUserAccountType;
    }

    public void setIdUserAccountType(int idUserAccountType) {
        this.idUserAccountType = idUserAccountType;
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
     * @return zwraca wszystkich użytkowników dla tego typu konta
     */
    public Set<UserAccount> getUserAccounts() {
        return userAccounts;
    }

    public void setUserAccounts(Set<UserAccount> userAccounts) {
        this.userAccounts = userAccounts;
    }
}
