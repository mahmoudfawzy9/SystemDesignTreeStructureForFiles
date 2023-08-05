package com.mahmoud.stc.entity;

import com.mahmoud.stc.enums.Role;

import com.mahmoud.stc.enums.UserStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
@EqualsAndHashCode(callSuper = false)
public class UserEntity extends BaseUserEntity {


    @Column(name = "user_name")
    private String userName;

    @Enumerated(EnumType.STRING)
    @Column(name = "permission_level")
    private List<Role> roles;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "mobile")
    private String mobile;
    
    @Column(name = "date_of_birth")
    private LocalDateTime dateOfBirth;


}
