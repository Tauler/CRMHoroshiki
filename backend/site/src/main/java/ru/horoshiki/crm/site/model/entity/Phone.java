package ru.horoshiki.crm.site.model.entity;

import javax.persistence.*;

/**
 * Created by onyushkindv on 29.11.2016.
 */
@Entity
@Table(name = "PHONES")
public class Phone {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PHONES_SEQ")
    @SequenceGenerator(name = "PHONES_SEQ", sequenceName = "PHONES_SEQ", allocationSize = 1)
    private Long id;

    @Column(name = "phone")
    private String phone;

    @Column(name = "new_phone")
    private String newPhone;

    @Column(name = "confirm_code")
    private String confirmCode;

    @ManyToOne(fetch = FetchType.LAZY)
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getNewPhone() {
        return newPhone;
    }

    public void setNewPhone(String newPhone) {
        this.newPhone = newPhone;
    }

    public String getConfirmCode() {
        return confirmCode;
    }

    public void setConfirmCode(String confirmCode) {
        this.confirmCode = confirmCode;
    }
}
