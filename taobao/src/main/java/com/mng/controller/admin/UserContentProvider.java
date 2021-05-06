package com.mng.controller.admin;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mng.controller.account.AccountControllerBase;
import com.mng.entity.User;
import com.mng.util.JsonBuilder;
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

    public String generateUserListJson() {
        List<User> allUser = findUsersByPageAndLimit(this.getPage(), this.getLimit());
        return JsonBuilder.newObject()
                .put("code", 0)
                .put("msg", "")
                .put("count", userRepository.count())
                .put("data", new Gson().toJsonTree(allUser, new TypeToken<List<User>>() {}.getType()))
                .build();
    }
}
