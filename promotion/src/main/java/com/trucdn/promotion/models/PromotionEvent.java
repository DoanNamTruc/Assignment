package com.trucdn.promotion.models;

import com.trucdn.promotion.common.EventStatus;
import com.trucdn.promotion.common.constant.PromotionCode;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import java.util.Date;


@Entity
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "PROMOTION_EVENT")
public class PromotionEvent {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private long id;
    @Enumerated(EnumType.ORDINAL)
    private PromotionCode code;
    private String name;
    private Date startDate;
    private Date endDate;
    @Enumerated(EnumType.ORDINAL)
    private EventStatus status;
    private String content;
}
