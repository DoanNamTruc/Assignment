package com.trucdn.promotion.dtos;

import com.trucdn.promotion.common.EventStatus;
import com.trucdn.promotion.common.constant.PromotionCode;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class EventRequest {
    private String name;
    private PromotionCode code;
    private Date startDate;
    private Date endDate;
    private EventStatus status;
    private String content;
}
