package com.mng.bean.response;

import com.mng.exception.authentication.RegisterFailedException;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RegisterResponse {
    private RegisterFailedException.Status status;
    private Integer usertype;
    private String errorDescription;
}
