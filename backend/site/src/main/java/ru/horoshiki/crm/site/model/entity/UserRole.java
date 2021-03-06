package ru.horoshiki.crm.site.model.entity;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by onyushkindv on 28.11.2016.
 */
@Entity
@Table(name = "USER_ROLES")
public class UserRole {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "USER_ROLES_SEQ")
    @SequenceGenerator(name = "USER_ROLES_SEQ", sequenceName = "USER_ROLES_SEQ", allocationSize = 1)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "roles")
    private String roles;

    @Column(name = "description")
    private String description;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRoles() {
        return roles;
    }

    public void setRoles(String roles) {
        this.roles = roles;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
