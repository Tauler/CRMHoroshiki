package ru.horoshiki.crm.site.model.entity;

import org.hibernate.annotations.*;
import ru.horoshiki.crm.site.model.enums.OrderConfirmType;
import ru.horoshiki.crm.site.model.enums.PaymentMethods;
import ru.horoshiki.crm.site.model.enums.Sex;

import javax.persistence.*;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;
import java.util.List;
import java.util.Set;

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

    @Column(name = "avatar_url")
    private String avatar;

    @Column(name = "birthday")
    private Date birthday;

    @Column(name = "sex")
    private Integer sex;

    @Column(name="order_confirm")
    private Boolean oderConfirm;

    @Column(name = "order_confirm_type")
    private Integer orderConfirmType;

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

    @OrderColumn
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user", cascade = CascadeType.ALL)
    private List<Phone> phones;

    @OrderColumn
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user", cascade = CascadeType.ALL)
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

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public void setOrderConfirmType(Integer orderConfirmType) {
        this.orderConfirmType = orderConfirmType;
    }

    public void setPaymentMethodDef(Integer paymentMethodDef) {
        this.paymentMethodDef = paymentMethodDef;
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
        return PaymentMethods.valueOf(this.paymentMethodDef);
    }

    public void setPaymentMethodDef(PaymentMethods paymentMethodDef) {
        this.paymentMethodDef = paymentMethodDef.getValue();
    }

    public Sex getSex(){
        return Sex.valueOf(this.sex);
    }

    public void setSex(Sex sex){
        this.sex = sex.getValue();
    }

    public OrderConfirmType getOrderConfirmType(){
        return  OrderConfirmType.valueOf(this.orderConfirmType);
    }

    public void setOrderConfirmType(OrderConfirmType orderConfirmType){
        this.orderConfirmType = orderConfirmType.getValue();
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

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public Boolean getOderConfirm() {
        return oderConfirm;
    }

    public void setOderConfirm(Boolean oderConfirm) {
        this.oderConfirm = oderConfirm;
    }
}
