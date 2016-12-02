package ru.horoshiki.crm.site.model.entity;

import ru.horoshiki.crm.site.model.enums.PaymentMethods;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * Created by onyushkindv on 28.11.2016.
 */

@Entity
@Table(name = "USERS")
public class User {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "USERS_SEQ")
    @SequenceGenerator(name = "USERS_SEQ", sequenceName = "USERS_SEQ", allocationSize = 1)
    private Long id;

    @Column(name = "login", unique = true)
    private String login;

    @Column(name = "name")
    private String name;

    @Column(name = "mail")
    private String mail;

    @Column(name = "blank")
    private Boolean blank = false;

    @Column(name = "password")
    private String password;

    @Column(name = "enabled")
    private Boolean enabled = false;

    @Column(name = "registration_key")
    private String registrationKey;

    @Column(name = "payment_method_def")
    private Integer paymentMethodDef;

    @Column(name="registration_key_gen_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date registrationKeyGenDate;

    //Дата создания пользователя
    @Column(name="create_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_role")
    private UserRole userRoles;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    private List<Phone> phones;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    private List<Address> addresses;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "def_address")
    private Address defaultAddress;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "def_phone")
    private Phone defaultPhone;

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public List<Phone> getPhones() {
        return phones;
    }

    public void setPhones(List<Phone> phones) {
        this.phones = phones;
    }

    public List<Address> getAddresses() {
        return addresses;
    }

    public void setAddresses(List<Address> addresses) {
        this.addresses = addresses;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public Boolean getBlank() {
        return blank;
    }

    public void setBlank(Boolean blank) {
        this.blank = blank;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public UserRole getUserRoles() {
        return userRoles;
    }

    public void setUserRoles(UserRole userRoles) {
        this.userRoles = userRoles;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRegistrationKey() {
        return registrationKey;
    }

    public void setRegistrationKey(String registrationKey) {
        this.registrationKey = registrationKey;
    }

    public PaymentMethods getPaymentMethodDef() {
        return PaymentMethods.valueOf(paymentMethodDef);
    }

    public void setPaymentMethodDef(PaymentMethods paymentMethodDef) {
        this.paymentMethodDef = paymentMethodDef.getValue();
    }

    public Date getRegistrationKeyGenDate() {
        return registrationKeyGenDate;
    }

    public void setRegistrationKeyGenDate(Date registrationKeyGenDate) {
        this.registrationKeyGenDate = registrationKeyGenDate;
    }

    public Address getDefaultAddress() {
        return defaultAddress;
    }

    public void setDefaultAddress(Address defaultAddress) {
        this.defaultAddress = defaultAddress;
    }

    public Phone getDefaultPhone() {
        return defaultPhone;
    }

    public void setDefaultPhone(Phone defaultPhone) {
        this.defaultPhone = defaultPhone;
    }
}
