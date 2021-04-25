package com.mng.bean;

import lombok.Data;
import org.springframework.lang.Nullable;

@Data
public class UserRemoveBody {
    @Nullable
    private Integer userId;
    @Nullable
    private String username;
}
