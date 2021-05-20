package com.mng.bean.request;

import lombok.Data;

@Data
public class UserListRequest {
    private Integer page;
    private Integer limit;
}
