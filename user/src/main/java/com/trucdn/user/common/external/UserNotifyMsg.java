package com.trucdn.user.common.external;

import com.trucdn.user.common.constant.UserMsgType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;


@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserNotifyMsg implements Serializable {

    private Long userId;
    private UserMsgType type;
    private Object content;
}
