package com.mng.annotation;

import com.mng.data.UserType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @see LoginRequired Requires this annotation to work
 * Set response code to 403 if the user type of the logged in user is incorrect.
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface UserTypeOnly {
    UserType[] value();
}
