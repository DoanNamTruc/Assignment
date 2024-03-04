package com.trucdn.promotion.services;

import com.trucdn.promotion.common.external.UserNotifyMsg;
import com.trucdn.promotion.common.external.UserPromotionMsg;

public interface UserPromotionService {

    void preProcessEvent(UserNotifyMsg userData);

    void processEvent(UserPromotionMsg userData);
}
