package ru.horoshiki.crm.site.model.dto;

import ru.horoshiki.crm.site.model.entity.User;
import ru.horoshiki.crm.site.model.enums.OrderConfirmType;
import ru.horoshiki.crm.site.model.enums.PaymentMethods;
import ru.horoshiki.crm.site.model.enums.Sex;

import java.util.Date;
import java.util.Set;

/**
 * Created by onyushkindv on 07.12.2016.
 */
public class UserSmallDTO {
    private Long id;
    private String login;
    private String name;
    private String avatar;
    private Date birthday;
    private Sex sex;
    private Boolean orderConfirm;
    private OrderConfirmType orderConfirmType;
    private Boolean notifications;
    private String mail;
    private Boolean enabled = false;
    private PaymentMethods paymentMethodDef;
    private Date createDate;
    private Set<PhoneSmallDTO> phones;
    private Set<AddressSmallDto> addresses;
    private AddressSmallDto defaultAddress;
    private PhoneSmallDTO defaultPhone;

    public static UserSmallDTO valueOf(User user){
        UserSmallDTO userSmallDTO = new UserSmallDTO();
        userSmallDTO.setId(user.getId());
        userSmallDTO.setLogin(user.getLogin());
        userSmallDTO.setAddresses(AddressSmallDto.valueOf(user.getAddresses()));
        userSmallDTO.setAvatar(user.getAvatar());
        userSmallDTO.setBirthday(user.getBirthday());
        userSmallDTO.setCreateDate(user.getCreateDate());
        userSmallDTO.setDefaultAddress(AddressSmallDto.valueOf(user.getDefaultAddress()));
        userSmallDTO.setDefaultPhone(PhoneSmallDTO.valueOf(user.getDefaultPhone()));
        userSmallDTO.setEnabled(user.getEnabled());
        userSmallDTO.setId(user.getId());
        userSmallDTO.setMail(user.getMail());
        userSmallDTO.setName(user.getName());
        userSmallDTO.setOrderConfirm(user.getOrderConfirm());
        userSmallDTO.setOrderConfirmType(user.getOrderConfirmType());
        userSmallDTO.setNotifications(user.isNotifications());
        userSmallDTO.setPaymentMethodDef(user.getPaymentMethodDef());
        userSmallDTO.setPhones(PhoneSmallDTO.valueOf(user.getPhones()));
        userSmallDTO.setSex(user.getSex());

        return userSmallDTO;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public Sex getSex() {
        return sex;
    }

    public void setSex(Sex sex) {
        this.sex = sex;
    }

    public Boolean getOrderConfirm() {
        return orderConfirm;
    }

    public Boolean getNotifications() {
        return notifications;
    }

    public void setNotifications(Boolean notifications) {
        this.notifications = notifications;
    }

    public void setOrderConfirm(Boolean orderConfirm) {
        this.orderConfirm = orderConfirm;
    }

    public OrderConfirmType getOrderConfirmType() {
        return orderConfirmType;
    }

    public void setOrderConfirmType(OrderConfirmType orderConfirmType) {
        this.orderConfirmType = orderConfirmType;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }


    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }


    public PaymentMethods getPaymentMethodDef() {
        return paymentMethodDef;
    }

    public void setPaymentMethodDef(PaymentMethods paymentMethodDef) {
        this.paymentMethodDef = paymentMethodDef;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Set<PhoneSmallDTO> getPhones() {
        return phones;
    }

    public void setPhones(Set<PhoneSmallDTO> phones) {
        this.phones = phones;
    }

    public Set<AddressSmallDto> getAddresses() {
        return addresses;
    }

    public void setAddresses(Set<AddressSmallDto> addresses) {
        this.addresses = addresses;
    }

    public AddressSmallDto getDefaultAddress() {
        return defaultAddress;
    }

    public void setDefaultAddress(AddressSmallDto defaultAddress) {
        this.defaultAddress = defaultAddress;
    }

    public PhoneSmallDTO getDefaultPhone() {
        return defaultPhone;
    }

    public void setDefaultPhone(PhoneSmallDTO defaultPhone) {
        this.defaultPhone = defaultPhone;
    }
}
