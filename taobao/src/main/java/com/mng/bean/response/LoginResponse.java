package com.mng.bean.response;

import com.mng.exception.authentication.LoginFailedException;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginResponse {
    private LoginFailedException.Status status;
    private Integer usertype;
    private String errorDescription;
}
