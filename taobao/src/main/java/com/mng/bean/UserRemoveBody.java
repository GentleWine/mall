package com.mng.bean;

import lombok.Data;

import javax.annotation.Nullable;

@Data
public class UserRemoveBody {
    @Nullable
    private int userId;
}
