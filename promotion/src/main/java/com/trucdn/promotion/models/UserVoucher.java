package com.trucdn.promotion.models;

import com.trucdn.promotion.common.VoucherStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "USER_VOUCHER")
public class UserVoucher {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private long id;
    private long userId;
    private long promotionEventId;
    private VoucherStatus status;
    private String userContext;
}
