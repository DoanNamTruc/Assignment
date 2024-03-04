package com.trucdn.promotion.subscriber;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.trucdn.promotion.common.external.UserNotifyMsg;
import com.trucdn.promotion.common.external.UserPromotionMsg;
import com.trucdn.promotion.dtos.eventContent.FirstLoginContent;
import com.trucdn.promotion.services.UserPromotionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Service;

@Service
public class UserQualifiedSubscriber implements MessageListener {
    Logger logger = LoggerFactory.getLogger(UserQualifiedSubscriber.class);
    private final ObjectMapper objectMapper = new ObjectMapper();
    private Gson gson = new GsonBuilder().setPrettyPrinting().create();

    @Autowired
    UserPromotionService userPromotionService;

    @Override
    public void onMessage(Message message, byte[] pattern) {
        try {
            logger.info("New message received: {}", message);
            UserPromotionMsg msg = gson.fromJson(objectMapper.readValue(message.getBody(), String.class), UserPromotionMsg.class);
            userPromotionService.processEvent(msg);
        } catch (Exception e) {
            logger.error("Error while parsing message");
        }
    }
}