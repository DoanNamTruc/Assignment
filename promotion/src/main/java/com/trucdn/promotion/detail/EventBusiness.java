package com.trucdn.promotion.detail;

import com.trucdn.promotion.common.external.UserPromotionMsg;
import com.trucdn.promotion.dtos.EventResponse;

public interface EventBusiness {

    void reProcess(Object param, EventResponse event);
    void process(Object param, EventResponse event);
}
