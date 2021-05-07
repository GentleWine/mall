package com.mng.bean;

import com.mng.entity.User;
import lombok.Data;

import java.util.List;

@Data
public class UserBatchDeleteBody {
    List<User> usersToDelete;
}
