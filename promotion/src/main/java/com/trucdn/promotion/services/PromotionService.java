package com.trucdn.promotion.services;

import com.trucdn.promotion.common.EventStatus;
import com.trucdn.promotion.dtos.EventRequest;
import com.trucdn.promotion.dtos.EventResponse;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public interface PromotionService {

    EventResponse saveEvent(EventRequest req);

    List<EventResponse> getAllEventActive();
    void updateStatus(Long id, EventStatus status);

    EventResponse get(Long id);
}
