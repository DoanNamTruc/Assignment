package com.trucdn.promotion.dtos;

import com.trucdn.promotion.common.EventStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class EventResponse {
    private long id;
    private String code;
    private String name;
    private Date startDate;
    private Date endDate;
    private EventStatus status;
    private String content;
}
