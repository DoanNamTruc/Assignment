package com.trucdn.promotion.dtos;

import com.trucdn.promotion.common.VoucherStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class VoucherRequest {
    private long id;
    private long userId;
    private long promotionEventId;
    private VoucherStatus status;
    private String userContext;
}
