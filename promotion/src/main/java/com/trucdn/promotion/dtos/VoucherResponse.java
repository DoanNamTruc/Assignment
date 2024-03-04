package com.trucdn.promotion.dtos;

import com.trucdn.promotion.common.EventStatus;
import com.trucdn.promotion.common.VoucherStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class VoucherResponse {
    private long id;
    private long userId;
    private long promotionEventId;
    private VoucherStatus status;
    private String userContext;
}
