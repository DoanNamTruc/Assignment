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
public class UserLoginResponse extends UserResponse {
    private boolean isFirstLogin;
}
