package com.trucdn.user.dtos;

import com.trucdn.user.models.UserRole;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserRequest {

    private Long id;
    private Set<UserRole> roles;
    private String fullName= "";
    private String phoneNumber= "";
    private String email= "";
    private String username = "";
    private String password= "";
    private Date birthday;
    private Date latestLogin;
    private Date createdAt;
}
