package com.mng.Controller.account;

import com.mng.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class AccountControllerBase {
    @Autowired
    protected UserRepository userRepository;
}
