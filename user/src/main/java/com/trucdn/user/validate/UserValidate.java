package com.trucdn.user.validate;

import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber;
import com.trucdn.user.dtos.AuthRequestDTO;
import com.trucdn.user.dtos.UserRequest;
import com.trucdn.user.exception.BadRequestException;
import org.apache.commons.validator.routines.EmailValidator;

public class UserValidate {

    public static void login(AuthRequestDTO req) {
        if (req.getLoginId().isEmpty()) {
            throw new BadRequestException("LoginId can't empty ");
        }
        if (req.getPassword().trim().isEmpty()) {
            throw new BadRequestException("Password can't empty ");
        }
    }

    public static void register(UserRequest req) {
        if (req.getEmail().trim().isEmpty() && req.getUsername().trim().isEmpty() && req.getPhoneNumber().trim().isEmpty()) {
            throw new BadRequestException("Email and Username and PhoneNumber can't not empty. please type one");
        }
        if (!req.getEmail().trim().isEmpty() && !EmailValidator.getInstance().isValid(req.getEmail())) {
             throw new BadRequestException("Email invalid " + req.getEmail());
        }
        if (req.getPassword().trim().isEmpty()) {
            throw new BadRequestException("Password can't empty ");
        }
        String swissNumberStr = "044 668 18 00";
        PhoneNumberUtil phoneUtil = PhoneNumberUtil.getInstance();
        try {
            Phonenumber.PhoneNumber swissNumberProto = phoneUtil.parse(swissNumberStr, "VN");
        } catch (NumberParseException e) {
            throw new BadRequestException("Phonenumber wrong format ");
        }
    }
}
