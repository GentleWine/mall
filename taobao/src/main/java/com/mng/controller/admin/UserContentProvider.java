package com.mng.Controller.admin;

import com.mng.bean.response.UserListResponse;
import com.mng.Controller.account.AccountControllerBase;
import com.mng.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;

public class UserContentProvider extends AccountControllerBase {
    private int page = 1;
    private int limit = 2;

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public List<User> findUsersByPageAndLimit(int page, int limit) {
        Page<User> pages = userRepository.findAll(PageRequest.of(page - 1, limit));
        return pages.getContent();
    }

    public UserListResponse generateUserList() {
        List<User> allUser = findUsersByPageAndLimit(this.getPage(), this.getLimit());
        return UserListResponse.builder()
                .code(0)
                .msg("")
                .count(userRepository.count())
                .data(allUser)
                .build();
    }
}
