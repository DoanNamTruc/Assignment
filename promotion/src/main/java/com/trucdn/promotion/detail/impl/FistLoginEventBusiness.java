package com.trucdn.promotion.detail.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.internal.LinkedTreeMap;
import com.trucdn.promotion.common.EventStatus;
import com.trucdn.promotion.common.VoucherStatus;
import com.trucdn.promotion.common.constant.RedisKeyConstant;
import com.trucdn.promotion.common.constant.UserMsgType;
import com.trucdn.promotion.common.external.UserNotifyMsg;
import com.trucdn.promotion.common.external.UserPromotionMsg;
import com.trucdn.promotion.detail.EventBusiness;
import com.trucdn.promotion.dtos.EventResponse;
import com.trucdn.promotion.dtos.VoucherRequest;
import com.trucdn.promotion.dtos.eventContent.FirstLoginContent;
import com.trucdn.promotion.dtos.eventContent.UserLoginResponse;
import com.trucdn.promotion.services.PromotionService;
import com.trucdn.promotion.services.UserVoucherService;
import com.trucdn.promotion.subscriber.UserNotifySubscriber;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class FistLoginEventBusiness implements EventBusiness {

    @Autowired
    private UserVoucherService userVoucherService;

    @Autowired
    private PromotionService promotionService;
    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();
    private final ObjectMapper objectMapper = new ObjectMapper();
    @Autowired
    private RedisTemplate template;

    Logger logger = LoggerFactory.getLogger(FistLoginEventBusiness.class);

    @Override
    public void reProcess(Object param, EventResponse event) {
        UserNotifyMsg msg = (UserNotifyMsg)param;

        boolean isFirstLogin = false;
        FirstLoginContent content;
        try {
            content = gson.fromJson(event.getContent(), FirstLoginContent.class);
            LinkedTreeMap<String, Object> map = (LinkedTreeMap<String, Object>)msg.getContent();
            isFirstLogin = (boolean) map.get("isFirstLogin");
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return;
        }
        if (!isFirstLogin) {
            return;
        }
        long limit = content.getLimit();
        String redisKey = "event.UserFistLogin" + event.getId();
        Long index = template.opsForValue().increment(redisKey);
        if (index > limit) {
            updateEventStatus(event.getId(), EventStatus.END);
        } else {
            logger.info("user id :" + msg.getUserId() +" login with order index = " + index);
            sentUserQualifiedMsg(index, msg, event);
        }
    }

    private void sentUserQualifiedMsg(Long index, UserNotifyMsg userNotifyMsg, EventResponse event) {
        String msg = gson.toJson(new UserPromotionMsg(userNotifyMsg.getUserId(), event.getId(), index));
        template.convertAndSend(RedisKeyConstant.PROMOTION_USER_QUALIFIED_TOPIC_KEY, msg);
    }

    private void updateEventStatus(Long id, EventStatus status) {
        promotionService.updateStatus(id, status);
    }

    @Override
    public void process(Object param, EventResponse event) {
        UserPromotionMsg msg = (UserPromotionMsg)param;
        VoucherRequest request = new VoucherRequest();
        request.setUserId(msg.getUserId());
        request.setPromotionEventId(event.getId());
        request.setStatus(VoucherStatus.NEW);
        request.setUserContext(msg.getContent().toString());
        userVoucherService.saveVoucher(request);
    }
}
