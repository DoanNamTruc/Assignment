package com.trucdn.user.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;


@Entity
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "USERS",
        indexes = {@Index(name = "phoneNumberIndex", columnList = "phoneNumber", unique = true),
                @Index(name = "usernameIndex", columnList = "username", unique = true),
                @Index(name = "emailIndex", columnList = "email", unique = true),})
public class UserInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @ManyToMany(fetch = FetchType.EAGER)
    private Set<UserRole> roles = new HashSet<>();
    private String fullName;
    private String phoneNumber;
    private String email;
    private String username;
    @JsonIgnore
    private String password;
    private Date birthday;
    private Date latestLogin;
    private Date createdAt;
}
