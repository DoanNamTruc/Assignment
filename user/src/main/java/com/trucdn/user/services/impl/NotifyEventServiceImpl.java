package com.trucdn.user.services.impl;

import com.nimbusds.jose.shaded.gson.Gson;
import com.nimbusds.jose.shaded.gson.GsonBuilder;
import com.trucdn.user.common.constant.RedisKeyConstant;
import com.trucdn.user.common.constant.UserMsgType;
import com.trucdn.user.common.external.UserNotifyMsg;
import com.trucdn.user.dtos.UserLoginResponse;
import com.trucdn.user.dtos.UserResponse;
import com.trucdn.user.services.NotifyEventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class NotifyEventServiceImpl implements NotifyEventService {

    @Autowired
    private RedisTemplate template;

    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();
    @Override
    public void userLoginEvent(UserLoginResponse userLogin) {
        String msg = gson.toJson(new UserNotifyMsg(userLogin.getId(), UserMsgType.LOGIN, userLogin));
        template.convertAndSend(RedisKeyConstant.USER_LOGIN_TOPIC_KEY, msg);
        System.out.println("sent notify userLoginEvent " + userLogin.getId());
    }

    @Override
    public void userRegisterEvent(UserResponse userResponse) {
//        String msg = gson.toJson(new UserNotifyMsg(userLogin.getId(), UserMsgType.LOGIN, userLogin));
//        template.convertAndSend(RedisKeyConstant.USER_CREATE_TOPIC_KEY, msg);
        System.out.println("sent notify userRegisterEvent " + userResponse.getId());
    }
}
