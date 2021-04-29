package com.mng.controller.admin;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mng.controller.account.AccountControllerBase;
import com.mng.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    public String findUsers() {
        try {
            List<User> allUser = findUsersByPageAndLimit(this.getPage(), this.getLimit());
            Map<String, Object> result = new HashMap<>();
            result.put("code", 0);
            result.put("msg", "");
            result.put("count", userRepository.count());
            result.put("data", allUser);
            return new ObjectMapper().writeValueAsString(result);
        } catch (JsonProcessingException e) {
            return null;
        }
    }
}
