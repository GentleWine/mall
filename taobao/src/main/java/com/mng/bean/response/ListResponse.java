package com.mng.bean.response;

import java.util.List;

public interface ListResponse<T> {
    Integer getCode();

    String getMsg();

    Long getCount();

    List<T> getData();
}
