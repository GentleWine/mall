package com.mng.Controller.admin;

import com.mng.annotation.LoginRequired;
import com.mng.annotation.UserTypeOnly;
import com.mng.data.UserType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author TCreopargh
 */
@RestController
@RequestMapping("/admin")
@LoginRequired
@UserTypeOnly(UserType.ADMIN)
public class AdminOrderController extends UserContentProvider {
    //TODO: Implement this
}
