package com.trucdn.promotion.services.impl;

import com.google.gson.Gson;

import com.google.gson.GsonBuilder;
import com.trucdn.promotion.common.constant.UserMsgType;
import com.trucdn.promotion.common.external.UserNotifyMsg;
import com.trucdn.promotion.dtos.EventResponse;
import com.trucdn.promotion.models.PromotionEvent;
import com.trucdn.promotion.services.NotifyEventService;
import com.trucdn.promotion.services.PromotionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotifyEventServiceImpl implements NotifyEventService {

    @Autowired
    private RedisTemplate template;

    @Autowired
    private PromotionService promotionService;
    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    @Override
    public void parseEvent(Long userId) {
        List<EventResponse> promotionList = promotionService.getAllEventActive();
        System.out.println("sent notify user " + userId);
    }
}
