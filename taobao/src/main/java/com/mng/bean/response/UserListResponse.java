package com.mng.bean.response;

import com.mng.entity.User;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class UserListResponse implements ListResponse<User> {
    private Integer code;
    private String msg;
    private Long count;
    private List<User> data;
}
