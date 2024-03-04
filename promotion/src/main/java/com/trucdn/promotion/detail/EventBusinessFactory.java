package com.trucdn.promotion.detail;

import com.trucdn.promotion.common.constant.PromotionCode;
import com.trucdn.promotion.detail.impl.FistLoginEventBusiness;
import com.trucdn.promotion.dtos.EventResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

public class EventBusinessFactory implements EventBusiness {
    EventBusiness eventBusiness;

    Logger logger = LoggerFactory.getLogger(FistLoginEventBusiness.class);

    public EventBusinessFactory(PromotionCode promotionCode, ApplicationContext context) {
        switch (promotionCode) {
            case FIRST_LOGIN:
                eventBusiness = context.getBean(FistLoginEventBusiness.class);
                break;
            default:
        }
    }

    @Override
    public void reProcess(Object param, EventResponse event) {
        logger.info("Start reprocess event " + event.getName());
        eventBusiness.reProcess(param, event);
    }

    @Override
    public void process(Object param, EventResponse event) {
        logger.info("Start process  " + event.getName());
        eventBusiness.process(param, event);
    }
}
