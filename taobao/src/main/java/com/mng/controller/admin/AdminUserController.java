package com.mng.controller.admin;

import com.mng.annotation.LoginRequired;
import com.mng.annotation.UserTypeOnly;
import com.mng.bean.request.EntityListRequest;
import com.mng.bean.request.UserAddRequest;
import com.mng.bean.request.UserEditRequest;
import com.mng.bean.request.UserRemoveRequest;
import com.mng.bean.response.MessageResponse;
import com.mng.bean.response.SimpleResponse;
import com.mng.bean.response.UserListResponse;
import com.mng.data.UserType;
import com.mng.entity.User;
import org.springframework.dao.DataAccessException;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;

/**
 * @author TCreopargh
 */
@RestController
@RequestMapping("/admin/users")
@LoginRequired
@UserTypeOnly(UserType.ADMIN)
public class AdminUserController extends UserContentProvider {
    @RequestMapping(value = "", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public UserListResponse generateUserTable(HttpServletRequest request, EntityListRequest body) {
        setLimit(body.getLimit());
        setPage(body.getPage());
        return generateUserList();
    }

    @ResponseBody
    @RequestMapping(value = "/edit-user", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public SimpleResponse editUser(HttpServletRequest request, @ModelAttribute("user") UserEditRequest body) {
        List<User> samePhoneUsers = userRepository.findByPhone(body.getPhone());
        if (!samePhoneUsers.isEmpty()) {
            for (User user1 : samePhoneUsers) {
                if (!user1.getUserid().equals(body.getId())) {
                    return SimpleResponse.fail("User with this phone number already exists!");
                }
            }
        }
        User user = userRepository.findById(body.getId()).orElse(null);
        if (user == null) {
            return SimpleResponse.fail("User Not Found!");
        }
        user.setMail(body.getMail());
        user.setUsername(body.getUsername());
        user.setUsertype(body.getUsertype());
        user.setPassword(body.getPassword());
        user.setPhone(body.getPhone());
        userRepository.save(user);
        return SimpleResponse.success();
    }

    @ResponseBody
    @RequestMapping(value = "/add-user", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public SimpleResponse addUser(HttpServletRequest request, @ModelAttribute("user") UserAddRequest body) {
        if (!userRepository.findByPhone(body.getPhone()).isEmpty()) {
            return SimpleResponse.fail("User with this phone number already exists!");
        }
        User user = new User();
        user.setMail(body.getMail());
        user.setUsername(body.getUsername());
        user.setUsertype(body.getUsertype());
        user.setPassword(body.getPassword());
        user.setPhone(body.getPhone());
        userRepository.saveAndFlush(user);
        return SimpleResponse.success();
    }

    @ResponseBody
    @RequestMapping(value = "/remove-users", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public MessageResponse removeUsers(HttpServletRequest request, @RequestBody List<User> users) {
        if (users == null) {
            return MessageResponse.fail("Request is null!");
        }
        if (users.isEmpty()) {
            return MessageResponse.fail("You didn't select any user!");
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
            return MessageResponse.success("Successfully deleted " + users.size() + " users!");
        } else {
            return MessageResponse.fail("Successfully deleted " + (users.size() - errorCount) + " users, " + errorCount + " users failed to delete.");
        }
    }

    @ResponseBody
    @RequestMapping(value = "/remove-user", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public SimpleResponse removeUser(HttpServletRequest request, @ModelAttribute("user") UserRemoveRequest body) {
        final Integer userId = body.getUserId();
        Optional<User> userToRemove = userRepository.findById(userId);
        if (userToRemove.isPresent()) {
            userRepository.delete(userToRemove.get());
            return SimpleResponse.success();
        }
        return SimpleResponse.fail("Requested user not found");
    }
}
