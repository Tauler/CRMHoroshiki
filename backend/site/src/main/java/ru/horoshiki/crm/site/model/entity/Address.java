package ru.horoshiki.crm.site.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

/**
 * Created by onyushkindv on 29.11.2016.
 */

@Entity
@Table(name = "ADDRESSES")
public class Address {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ADDRESSES_SEQ")
    @SequenceGenerator(name = "ADDRESSES_SEQ", sequenceName = "ADDRESSES_SEQ", allocationSize = 1)
    private Long id;

    //адрес
    @Column(name = "address")
    private String address;

    //код домофона
    @Column(name = "intercom")
    private String intercom;

    // этаж
    @Column(name = "storey")
    private Integer storey;

    // подъезд
    @Column(name = "access")
    private Integer access;

    // квартира
    @Column(name = "apartment")
    private Integer apartment;

    //комментарий
    @Column(name = "comment")
    private String comment;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinColumn(name = "user_id")
    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getIntercom() {
        return intercom;
    }

    public void setIntercom(String intercom) {
        this.intercom = intercom;
    }

    public Integer getStorey() {
        return storey;
    }

    public void setStorey(Integer storey) {
        this.storey = storey;
    }

    public Integer getAccess() {
        return access;
    }

    public void setAccess(Integer access) {
        this.access = access;
    }

    public Integer getApartment() {
        return apartment;
    }

    public void setApartment(Integer apartment) {
        this.apartment = apartment;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
