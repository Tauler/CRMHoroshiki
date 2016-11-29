package ru.horoshiki.crm.site.model.entity;

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

    @Column(name = "login")
    private String login;

    @Column(name = "mail")
    private String mail;

    @Column(name = "blank")
    private Boolean blank = false;

    @Column(name = "password")
    private String password;

    @Column(name = "enabled")
    private Boolean enabled = false;

    @Column(name = "uuid_registration")
    private String uuidRegistration;

    //Датат создания пользователя
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

    public String getUuidRegistration() {
        return uuidRegistration;
    }

    public void setUuidRegistration(String uuidRegistration) {
        this.uuidRegistration = uuidRegistration;
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
}