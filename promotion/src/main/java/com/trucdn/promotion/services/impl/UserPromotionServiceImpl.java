package com.trucdn.promotion.services.impl;

import com.trucdn.promotion.common.constant.PromotionCode;
import com.trucdn.promotion.common.external.UserNotifyMsg;
import com.trucdn.promotion.common.external.UserPromotionMsg;
import com.trucdn.promotion.detail.EventBusiness;
import com.trucdn.promotion.detail.EventBusinessFactory;
import com.trucdn.promotion.dtos.EventResponse;
import com.trucdn.promotion.services.PromotionService;
import com.trucdn.promotion.services.UserPromotionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserPromotionServiceImpl implements UserPromotionService {

    @Autowired
    PromotionService promotionService;

    @Autowired
    ApplicationContext context;
    @Override
    public void preProcessEvent(UserNotifyMsg userData) {
        List<EventResponse> activeEvents = promotionService.getAllEventActive();
        for(EventResponse event : activeEvents) {
            EventBusiness eventBusiness = new EventBusinessFactory(PromotionCode.valueOf(event.getCode()), context);
            eventBusiness.reProcess(userData, event);
        }
    }

    @Override
    public void processEvent(UserPromotionMsg msg) {
        EventResponse event = promotionService.get(msg.getPromotionId());
        EventBusiness eventBusiness = new EventBusinessFactory(PromotionCode.valueOf(event.getCode()), context);
        eventBusiness.process(msg, event);
    }
}
