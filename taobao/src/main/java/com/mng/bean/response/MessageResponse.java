package com.mng.bean.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MessageResponse {
    private Boolean success;
    private String msg;

    public static MessageResponse success() {
        return builder().success(true).build();
    }

    public static MessageResponse fail() {
        return builder().success(false).build();
    }

    public static MessageResponse success(String message) {
        return builder().success(true).msg(message).build();
    }

    public static MessageResponse fail(String message) {
        return builder().success(false).msg(message).build();
    }

}
