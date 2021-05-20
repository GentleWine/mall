package com.mng.bean.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SimpleResponse {
    private Boolean success;
    private String error;

    public static SimpleResponse success() {
        return builder().success(true).build();
    }

    public static SimpleResponse fail(Exception e) {
        return builder().success(false).error(e.getMessage()).build();
    }

    public static SimpleResponse fail(String errorMessage) {
        return builder().success(false).error(errorMessage).build();
    }
}
