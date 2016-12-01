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

    @Column(name = "is_main", nullable = false)
    private boolean isMain = false;

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

    public boolean isMain() {
        return isMain;
    }

    public void setMain(boolean main) {
        isMain = main;
    }
}
