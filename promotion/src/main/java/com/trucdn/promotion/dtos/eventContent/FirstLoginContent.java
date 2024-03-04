package com.trucdn.promotion.dtos.eventContent;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class FirstLoginContent {
    private int limit = 100;
    private double discountPercent = 30;
}
