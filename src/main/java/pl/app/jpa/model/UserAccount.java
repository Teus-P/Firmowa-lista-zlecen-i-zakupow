package pl.app.jpa.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * Mapowanie obiektowo-relacyjne między klasą UserAccount a tabelą userAccount
 */
@Entity(name = "UserAccount")
@Table(name = "userAccount",
        schema = "praca_inz_db",
        uniqueConstraints = {@UniqueConstraint(columnNames = {"login", "pesel"})})
public class UserAccount implements Serializable {

    /* ******************************************************* *
     *                                                         *
     *               Mapowane pola klasy                       *
     *                                                         *
     * ******************************************************* */

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    private int idUserAccount;

    @Column(name = "login", length = 25, nullable = false, unique = true)
    private String login;

    @Column(name = "password", length = 64, nullable = false)
    private String password;

    @Column(name = "firstName", length = 30, nullable = false)
    private String firstName;

    @Column(name = "lastName", length = 50, nullable = false)
    private String lastName;

    @Column(name = "pesel", length = 11, nullable = false, unique = true)
    private long pesel;

    @Column(name = "email", length = 150, nullable = false)
    private String email;

    @Column(name = "phoneNumber", length = 13, nullable = false)
    private String phoneNumber;

    /* ******************************************************* *
     *                                                         *
     *              Relacje wiele do wiele                     *
     *                                                         *
     * ******************************************************* */

    //Tabela UserAccountTypeCollection
    @ManyToMany
    @JoinTable(
            name = "UserAccountTypeCollection",
            joinColumns = {@JoinColumn(name = "idUserAccount", nullable = false)},
            inverseJoinColumns = {@JoinColumn(name = "idUserAccountType", nullable = false)}
    )
    Set<UserAccountType> userAccountTypes = new HashSet<>();

    /* ******************************************************* *
     *                                                         *
     *                Kolekcje dwukierunkowe                   *
     *                                                         *
     * ******************************************************* */

    //UserAccount -< SystemConfiguration
    @OneToMany(mappedBy = "userAccount_systemConfiguration")
    private Set<SystemConfiguration> mySystemConfiguration = new HashSet<>();

    // UserAccount >- ProductProposal
    @OneToMany(mappedBy = "userAccount_productProposal")
    private Set<ProductProposal> myProductProposal = new HashSet<>();

    // UserAccount -< Implementers
    @OneToMany(mappedBy = "userAccount_implementers")
    private Set<Implementers> myImplementers = new HashSet<>();

    // UserAccount -< Order (StoreKeeper)
    @OneToMany(mappedBy = "storeKeeper")
    private Set<UserOrder> myStoreKeepers = new HashSet<>();

    // UserAccount -< Order (Recipient)
    @OneToMany(mappedBy = "recipient")
    private Set<UserOrder> myRecipient = new HashSet<>();

    // UserAccount -< Order (UserAccount)
    @OneToMany(mappedBy = "userAccount_order")
    private Set<UserOrder> myUserOrders = new HashSet<>();

    /* ******************************************************* *
     *                                                         *
     *                Konstruktory                             *
     *                                                         *
     * ******************************************************* */

    public UserAccount() {
    }

    public UserAccount(String login, String password, String firstName, String lastName, long pesel, String email, String phoneNumber) {
        this.login = login;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.pesel = pesel;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }

    /* ******************************************************* *
     *                                                         *
     *              Getters and setters                        *
     *                                                         *
     * ******************************************************* */

    public int getIdUserAccount() {
        return idUserAccount;
    }

    public void setIdUserAccount(int idUserAccount) {
        this.idUserAccount = idUserAccount;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public long getPesel() {
        return pesel;
    }

    public void setPesel(long pesel) {
        this.pesel = pesel;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    /**
     * @return zwraca wszystkie konfiguracje systemu tego użytkownika
     */
    public Set<SystemConfiguration> getMySystemConfiguration() {
        return mySystemConfiguration;
    }

    public void setMySystemConfiguration(Set<SystemConfiguration> mySystemConfiguration) {
        this.mySystemConfiguration = mySystemConfiguration;
    }

    /**
     * @return zwraca wszystkie propozycje produktów tego użytkownika
     */
    public Set<ProductProposal> getMyProductProposal() {
        return myProductProposal;
    }

    public void setMyProductProposal(Set<ProductProposal> myProductProposal) {
        this.myProductProposal = myProductProposal;
    }

    public Set<UserAccountType> getUserAccountTypes() {
        return userAccountTypes;
    }

    public void setUserAccountTypes(Set<UserAccountType> userAccountTypes) {
        this.userAccountTypes = userAccountTypes;
    }

    /**
     * @return zwraca przypisane kategorie (Realizatorzy) dla tego użytkownika
     */
    public Set<Implementers> getMyImplementers() {
        return myImplementers;
    }

    public void setMyImplementers(Set<Implementers> myImplementers) {
        this.myImplementers = myImplementers;
    }

    /**
     * @return zwraca wszystkie zamówienia które dla tego użytkownika(o ile jest magazynierem) które zaakceptował (chyba XD)
     */
    public Set<UserOrder> getMyStoreKeepers() {
        return myStoreKeepers;
    }

    public void setMyStoreKeepers(Set<UserOrder> myStoreKeepers) {
        this.myStoreKeepers = myStoreKeepers;
    }

    /**
     * @return zwraca wszystkie zamówienia które dla tego użytkownika(o ile jest przyjmującym) które zaakceptował (chyba XD)
     */
    public Set<UserOrder> getMyRecipient() {
        return myRecipient;
    }

    public void setMyRecipient(Set<UserOrder> myRecipient) {
        this.myRecipient = myRecipient;
    }

    /**
     * @return zwraca wszystkie zamówienia dla tego użytkownika
     */
    public Set<UserOrder> getMyUserOrders() {
        return myUserOrders;
    }

    public void setMyUserOrders(Set<UserOrder> myUserOrders) {
        this.myUserOrders = myUserOrders;
    }
}
