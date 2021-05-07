package com.mng.controller.admin;

import com.mng.annotation.LoginRequired;
import com.mng.annotation.UserTypeOnly;
import com.mng.bean.UserAddBody;
import com.mng.bean.UserEditBody;
import com.mng.bean.UserRemoveBody;
import com.mng.bean.UserTableRequestBody;
import com.mng.data.UserType;
import com.mng.entity.User;
import com.mng.util.JsonBuilder;
import org.springframework.dao.DataAccessException;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/admin")
@LoginRequired
@UserTypeOnly(UserType.ADMIN)
public class AdminController extends UserContentProvider {

    @RequestMapping(value = "/users", method = RequestMethod.POST)
    public String generateUserTable(HttpServletRequest request, UserTableRequestBody body) {
        setLimit(body.getLimit());
        setPage(body.getPage());
        String json = generateUserListJson();
        return json == null ? "{}" : json;
    }

    @RequestMapping(value = "/edit-user", method = RequestMethod.POST)
    public String editUser(HttpServletRequest request, @ModelAttribute("user") UserEditBody body) {
        List<User> samePhoneUsers = userRepository.findByPhone(body.getPhone());
        if (!samePhoneUsers.isEmpty()) {
            for (User user1 : samePhoneUsers) {
                if (user1.getUserid() != body.getId()) {
                    return JsonBuilder.newObject()
                            .put("success", false)
                            .put("reason", "User with this phone number already exists!")
                            .build();
                }
            }
        }
        User user = userRepository.findById(body.getId()).orElse(null);
        if (user == null) {
            return JsonBuilder.newObject()
                    .put("success", false)
                    .put("reason", "User Not Found!")
                    .build();
        }
        user.setMail(body.getMail());
        user.setUsername(body.getUsername());
        user.setUsertype(body.getUsertype());
        user.setPassword(body.getPassword());
        user.setPhone(body.getPhone());
        userRepository.save(user);
        return JsonBuilder.newObject()
                .put("success", true)
                .build();
    }

    @RequestMapping(value = "/add-user", method = RequestMethod.POST)
    public String addUser(HttpServletRequest request, @ModelAttribute("user") UserAddBody body) {
        if (!userRepository.findByPhone(body.getPhone()).isEmpty()) {
            return JsonBuilder.newObject()
                    .put("success", false)
                    .put("reason", "User with this phone number already exists!")
                    .build();
        }
        User user = new User();
        user.setMail(body.getMail());
        user.setUsername(body.getUsername());
        user.setUsertype(body.getUsertype());
        user.setPassword(body.getPassword());
        user.setPhone(body.getPhone());
        userRepository.saveAndFlush(user);
        return JsonBuilder.newObject()
                .put("success", true)
                .build();
    }

    @RequestMapping(value = "/remove-users", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public String batchDelete(HttpServletRequest request, @RequestBody List<User> users) {
        if (users == null) {
            return JsonBuilder.newObject()
                    .put("success", false)
                    .put("msg", "Request is null!")
                    .build();
        }
        if (users.isEmpty()) {
            return JsonBuilder.newObject()
                    .put("success", false)
                    .put("msg", "You didn't select any user!")
                    .build();
        }
        int errorCount = 0;
        for (User user : users) {
            try {
                userRepository.deleteById(user.getUserid());
            } catch (DataAccessException | IllegalArgumentException e) {
                errorCount++;
            }
        }
        if (errorCount == 0) {
            return JsonBuilder.newObject()
                    .put("success", true)
                    .put("msg", "Successly deleted " + users.size() + " users!")
                    .build();
        } else {
            return JsonBuilder.newObject()
                    .put("success", false)
                    .put("msg", "Successly deleted " + (users.size() - errorCount) + " users, " + errorCount + " users failed to delete.")
                    .build();
        }
    }

    @RequestMapping(value = "/remove-user", method = RequestMethod.POST)
    public String removeUser(HttpServletRequest request, @ModelAttribute("user") UserRemoveBody body) {
        final Integer userId = body.getUserId();
        final String jsonSuccess = JsonBuilder.newObject()
                .put("success", true)
                .build();
        final String jsonFail = JsonBuilder.newObject()
                .put("success", false)
                .build();
        Optional<User> userToRemove = userRepository.findById(userId);
        if (userToRemove.isPresent()) {
            userRepository.delete(userToRemove.get());
            return jsonSuccess;
        }
        return jsonFail;
    }
}
