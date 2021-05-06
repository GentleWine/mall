package com.mng.util;

import lombok.experimental.UtilityClass;

@UtilityClass
public final class Constants {
    public static final String USERNAME_REGEX = "^[a-zA-Z0-9_]{3,18}$";
    public static final String EMAIL_REGEX = "^\\S+@\\S+\\.\\S+$";
    public static final String PHONE_REGEX = "^\\+?[0-9]{6,20}$";
}
