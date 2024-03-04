package com.trucdn.user.services;

import com.trucdn.user.dtos.UserLoginResponse;
import com.trucdn.user.dtos.UserResponse;

public interface NotifyEventService {

    void userLoginEvent(UserLoginResponse userLogin);

    void userRegisterEvent(UserResponse userResponse);
}
