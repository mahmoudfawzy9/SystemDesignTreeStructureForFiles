package com.mahmoud.stc.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.mahmoud.stc.enums.Role;

import jakarta.persistence.*;


@Entity
@Table(name = "permissions")
public class Permission {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_email")
    private String userEmail;

    @Enumerated(EnumType.STRING)
    @Column(name = "permission_level")
    private Role permissionLevel;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "permission_group_id")
    @JsonBackReference(value = "permission-group")
    private PermissionGroup permissionGroup;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public Role getPermissionLevel() {
        return permissionLevel;
    }

    public void setPermissionLevel(Role permissionLevel) {
        this.permissionLevel = permissionLevel;
    }

    public PermissionGroup getPermissionGroup() {
        return permissionGroup;
    }

    public void setPermissionGroup(PermissionGroup permissionGroup) {
        this.permissionGroup = permissionGroup;
    }
}
