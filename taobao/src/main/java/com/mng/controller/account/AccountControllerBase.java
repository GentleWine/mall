package com.mng.controller.account;

import com.mng.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class AccountControllerBase {
    @Autowired
    protected UserRepository userRepository;
}
