package com.trucdn.promotion.controllers;

import com.trucdn.promotion.dtos.EventRequest;
import com.trucdn.promotion.dtos.EventResponse;
import com.trucdn.promotion.exception.BadRequestException;
import com.trucdn.promotion.services.PromotionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/promotion")
public class PromotionController {

    @Autowired
    PromotionService promotionService;


    @PostMapping(value = "/create")
    public ResponseEntity save(@RequestBody EventRequest req) {
        try {
            EventResponse response = promotionService.saveEvent(req);
            return ResponseEntity.ok(response);
        } catch (BadRequestException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/actives")
    public ResponseEntity getAllActive() {
        try {
            List<EventResponse> response = promotionService.getAllEventActive();
            return ResponseEntity.ok(response);
        } catch (BadRequestException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
