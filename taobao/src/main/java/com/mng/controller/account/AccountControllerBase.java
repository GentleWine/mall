package com.mng.Controller.account;

import com.mng.repository.ShopRepository;
import com.mng.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class AccountControllerBase {
    @Autowired
    protected UserRepository userRepository;
    @Autowired
    protected ShopRepository shopRepository;
}
