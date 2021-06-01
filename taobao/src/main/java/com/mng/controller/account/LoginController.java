package com.mng.Controller.account;

import com.mng.bean.request.LoginRequest;
import com.mng.bean.response.LoginResponse;
import com.mng.data.UserType;
import com.mng.entity.User;
import com.mng.exception.authentication.LoginFailedException;
import com.mng.exception.authentication.LoginFailedException.Status;
import com.mng.repository.UserRepository;
import com.mng.util.CipherProcessor;
import com.mng.util.Log;
import com.mng.util.VerificationUtil;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.GeneralSecurityException;
import java.util.List;

@RestController
public class LoginController extends AccountControllerBase {

    public static LoginResponse resolveLogin(UserRepository userRepository, HttpServletRequest request, HttpServletResponse response, LoginRequest loginbody) {
        String phone = loginbody.getPhone();
        String password = loginbody.getPassword();
        try {
            List<User> usersList;
            if (VerificationUtil.anyIsEmpty(phone, password)) {
                throw new LoginFailedException(Status.FIELD_MISSING);
            } else if ((usersList = userRepository.findByPhone(phone)).isEmpty()) {
                throw new LoginFailedException(Status.ACCOUNT_NOT_FOUND);
            } else if (usersList.size() > 1) {
                throw new LoginFailedException(Status.DUPLICATE_USERS);
            } else if (!usersList.get(0).getPassword().equals(password)) {
                throw new LoginFailedException(Status.PASSWORD_INCORRECT);
            } else {
                String username = usersList.get(0).getUsername();
                String mail = usersList.get(0).getMail();
                String usertype = usersList.get(0).getUsertype();
                UserType enumUserType = UserType.getFromId(Integer.parseInt(usertype));
                request.getSession().setAttribute("phone", phone);
                request.getSession().setAttribute("mail", mail);
                request.getSession().setAttribute("username", username);
                request.getSession().setAttribute("usertype", enumUserType);
                request.getSession().setAttribute("usertypeId", enumUserType.getId());
                if (loginbody.getRememberMe()) {
                    try {
                        Cookie phoneCookie = new Cookie("phone", phone);
                        phoneCookie.setMaxAge(7 * 24 * 3600);
                        phoneCookie.setPath("/");
                        Cookie passwordCookie = new Cookie("password", CipherProcessor.getInstance().encrypt(password));
                        passwordCookie.setMaxAge(7 * 24 * 3600);
                        passwordCookie.setPath("/");
                        Cookie usertypeCookie = new Cookie("usertype", enumUserType.toString());
                        usertypeCookie.setMaxAge(7 * 24 * 3600);
                        usertypeCookie.setPath("/");
                        response.addCookie(phoneCookie);
                        response.addCookie(passwordCookie);
                        response.addCookie(usertypeCookie);
                    } catch (GeneralSecurityException e) {
                        Log.e(e);
                    }
                }
                return LoginResponse.builder()
                        .status(Status.SUCCESS)
                        .usertype(enumUserType.getId())
                        .build();
            }
        } catch (LoginFailedException e) {
            return LoginResponse.builder()
                    .status(e.getStatus())
                    .errorDescription(e.getMessage())
                    .build();
        }
    }

    @RequestMapping(value = "/user/login", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public LoginResponse login(HttpServletRequest request, HttpServletResponse response, @ModelAttribute("login") LoginRequest loginbody) {
        return resolveLogin(userRepository, request, response, loginbody);
    }
}

