package com.trucdn.promotion.subscriber;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.trucdn.promotion.common.external.UserNotifyMsg;
import com.trucdn.promotion.services.UserPromotionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Service;

@Service
public class UserNotifySubscriber implements MessageListener {
    Logger logger = LoggerFactory.getLogger(UserNotifySubscriber.class);
    private final ObjectMapper objectMapper = new ObjectMapper();
    private Gson gson = new GsonBuilder().setPrettyPrinting().create();

    @Autowired
    UserPromotionService userPromotionService;

    @Override
    public void onMessage(Message message, byte[] pattern) {
        try {
            logger.info("New message received: {}", message);
            UserNotifyMsg msg = gson.fromJson(objectMapper.readValue(message.getBody(), String.class), UserNotifyMsg.class);
            userPromotionService.preProcessEvent(msg);
        } catch (Exception e) {
            logger.error("Error while parsing message");
        }
    }
}