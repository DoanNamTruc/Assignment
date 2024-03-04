package com.trucdn.promotion.services.impl;

import com.trucdn.promotion.common.EventStatus;
import com.trucdn.promotion.dtos.EventRequest;
import com.trucdn.promotion.dtos.EventResponse;
import com.trucdn.promotion.models.PromotionEvent;
import com.trucdn.promotion.repositories.PromotionEventRepository;
import com.trucdn.promotion.services.PromotionService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class PromotionServiceImpl implements PromotionService {

    @Autowired
    PromotionEventRepository promotionRepository;
    ModelMapper modelMapper = new ModelMapper();
    public EventResponse saveEvent(EventRequest req) {
        PromotionEvent eventEntity = modelMapper.map(req, PromotionEvent.class);
        eventEntity = promotionRepository.save(eventEntity);
        return modelMapper.map(eventEntity, EventResponse.class);

    }

    @Override
    public EventResponse get(Long id) {
        Optional<PromotionEvent> event = promotionRepository.findById(id);
        return event.map(promotionEvent -> modelMapper.map(promotionEvent, EventResponse.class)).orElse(null);
    }

    @Override
    public List<EventResponse> getAllEventActive() {
        List<PromotionEvent> events = promotionRepository.findPromotionsByStatusAndStartDate(EventStatus.RUN);
        return events
                .stream()
                .map(user -> modelMapper.map(user, EventResponse.class))
                .toList();
    }

    @Override
    public void updateStatus(Long id, EventStatus status) {
        Optional<PromotionEvent> promotionEvent = promotionRepository.findById(id);
        PromotionEvent entity = promotionEvent.get();
        entity.setStatus(status);
        promotionRepository.save(entity);
    }
}
