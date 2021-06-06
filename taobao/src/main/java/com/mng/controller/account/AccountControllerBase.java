package com.mng.controller.account;

import com.mng.repository.ShopRepository;
import com.mng.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class AccountControllerBase {
    @Autowired
    protected UserRepository userRepository;
    @Autowired
    protected ShopRepository shopRepository;
}
