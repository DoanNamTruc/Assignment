package com.trucdn.user.dtos;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoginIdGroupDTO {
    private String phoneNumber= "";
    private String email= "";
    private String username = "";
}
