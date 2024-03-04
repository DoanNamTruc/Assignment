package com.trucdn.user.services;

import com.trucdn.user.dtos.UserLoginResponse;
import com.trucdn.user.dtos.UserRequest;
import com.trucdn.user.dtos.UserResponse;

public interface UserService {

    UserResponse saveUser(UserRequest userRequest);

    UserResponse getUser();

    UserLoginResponse updateLoginTime(String loginId);
}
