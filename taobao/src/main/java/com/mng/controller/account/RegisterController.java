package com.mng.controller.account;

import com.mng.bean.RegisterBody;
import com.mng.data.UserType;
import com.mng.entity.User;
import com.mng.exception.authentication.RegisterFailedException;
import com.mng.exception.authentication.RegisterFailedException.Status;
import com.mng.util.Constants;
import com.mng.util.JsonBuilder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RegisterController extends AccountControllerBase {

    @RequestMapping(value = "/user/register", method = RequestMethod.POST)
    public String register(@ModelAttribute("register") RegisterBody requestbody) {
        String phone = requestbody.getPhone();
        String password = requestbody.getPassword();
        String confirm = requestbody.getConfirm();
        String usertype = requestbody.getUsertype();
        String username = requestbody.getUsername();
        String mail = requestbody.getMail();
        boolean agreed = requestbody.isAgreementAgreed();

        try {
            if ("".equals(phone) || "".equals(password) || "".equals(confirm) ||
                    "".equals(usertype) || "".equals(username) || "".equals(mail)) {
                throw new RegisterFailedException(Status.FIELD_MISSING);
            } else if (!agreed) {
                throw new RegisterFailedException(Status.AGREEMENT_NOT_AGREED);
            } else if (!password.equals(confirm)) {
                throw new RegisterFailedException(Status.PASSWORD_CONFIRM_MISMATCH);
            } else if (!userRepository.findByPhone(phone).isEmpty()) {
                throw new RegisterFailedException(Status.USER_ALREADY_EXISTS);
            } else {
                UserType type;
                try {
                    type = UserType.getFromId(Integer.parseInt(usertype));
                } catch (IllegalArgumentException e) {
                    throw new RegisterFailedException(Status.INVALID_USER_TYPE);
                }
                if (!username.matches(Constants.USERNAME_REGEX)) {
                    throw new RegisterFailedException(Status.INVALID_USERNAME);
                }
                if (password.length() < 6) {
                    throw new RegisterFailedException(Status.PASSWORD_TOO_SHORT);
                }
                if (!mail.matches(Constants.EMAIL_REGEX)) {
                    throw new RegisterFailedException(Status.INVALID_EMAIL);
                }
                if (!phone.matches(Constants.PHONE_REGEX)) {
                    throw new RegisterFailedException(Status.INVALID_PHONE);
                }
                User user = new User();
                user.setPhone(phone);
                user.setPassword(password);
                user.setUsertype(String.valueOf(type != null ? type.ordinal() : 0));
                user.setUsername(username);
                user.setMail(mail);
                userRepository.save(user);
                return JsonBuilder.newObject()
                        .put("status", Status.SUCCESS)
                        .build();
            }
        } catch (RegisterFailedException e) {
            return JsonBuilder.newObject()
                    .put("status", e.getStatus())
                    .put("error_description", e.getMessage())
                    .build();
        }
    }
}
