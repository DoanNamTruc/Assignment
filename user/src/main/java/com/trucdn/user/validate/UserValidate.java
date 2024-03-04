package com.trucdn.user.validate;

import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber;
import com.trucdn.user.dtos.AuthRequestDTO;
import com.trucdn.user.dtos.UserRequest;
import com.trucdn.user.exception.BadRequestException;
import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class UserValidate {

    public static void login(AuthRequestDTO req) {
        req.setEmail(req.getEmail().trim());
        req.setUsername(req.getUsername().trim());
        req.setPhoneNumber(req.getPhoneNumber().trim());
        if (req.getEmail().trim().isEmpty() && req.getUsername().trim().isEmpty() && req.getPhoneNumber().trim().isEmpty()) {
            throw new BadRequestException("LoginId can't empty");
        }
        if (req.getPassword().trim().isEmpty()) {
            throw new BadRequestException("Password can't empty");
        }
    }

    public static void register(UserRequest req) {
        req.setEmail(req.getEmail().trim());
        req.setUsername(req.getUsername().trim());
        req.setPhoneNumber(req.getPhoneNumber().trim());
        if (req.getEmail().trim().isEmpty() && req.getUsername().trim().isEmpty() && req.getPhoneNumber().trim().isEmpty()) {
            throw new BadRequestException("Email and Username and PhoneNumber can't not empty. please type one");
        }
        if (!req.getEmail().trim().isEmpty() && !EmailValidator.getInstance().isValid(req.getEmail())) {
            throw new BadRequestException("Email invalid format" + req.getEmail());
        }
        if (req.getPassword().trim().isEmpty()) {
            throw new BadRequestException("Password can't empty");
        }
        String swissNumberStr = req.getPhoneNumber();
        PhoneNumberUtil phoneUtil = PhoneNumberUtil.getInstance();
        try {
            Phonenumber.PhoneNumber swissNumberProto = phoneUtil.parse(swissNumberStr, "VN");
        } catch (NumberParseException e) {
            throw new BadRequestException("PhoneNumber wrong format");
        }
    }
}
