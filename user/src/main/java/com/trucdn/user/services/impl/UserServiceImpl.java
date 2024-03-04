package com.trucdn.user.services.impl;

import com.trucdn.user.dtos.UserLoginResponse;
import com.trucdn.user.dtos.UserRequest;
import com.trucdn.user.dtos.UserResponse;
import com.trucdn.user.models.UserInfo;
import com.trucdn.user.repositories.UserRepository;
import com.trucdn.user.exception.BadRequestException;
import com.trucdn.user.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    ModelMapper modelMapper = new ModelMapper();

    @Override
    public UserResponse saveUser(UserRequest userRequest) {
        UserInfo savedUser;
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String rawPassword = userRequest.getPassword();
        String encodedPassword = encoder.encode(rawPassword);
        UserInfo user = modelMapper.map(userRequest, UserInfo.class);
        user.setPassword(encodedPassword);

        List<UserInfo> existedUsers = userRepository.findUserByMultiParam(user.getEmail(), user.getUsername(), user.getPhoneNumber());
        if (!existedUsers.isEmpty()) {
            StringBuilder errorMsg = new StringBuilder();
            errorMsg.append("Parameter existed :");
            for(UserInfo existedUser : existedUsers) {
                if (user.getEmail() != null && user.getEmail().equals(existedUser.getEmail())) {
                    errorMsg.append("/n email: ").append(user.getEmail());
                }
                if (user.getUsername() != null && user.getUsername().equals(existedUser.getUsername())) {
                    errorMsg.append("/n username: ").append(user.getUsername());
                }
                if (user.getPhoneNumber() != null && user.getPhoneNumber().equals(existedUser.getPhoneNumber())) {
                    errorMsg.append("/n phoneNumber: ").append(user.getPhoneNumber());
                }
            }
            throw new BadRequestException(errorMsg.toString());
        } else {
            user.setCreatedAt(new Date());
            savedUser = userRepository.save(user);
        }
        return modelMapper.map(savedUser, UserResponse.class);
    }

    @Override
    public UserResponse getUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetail = (UserDetails) authentication.getPrincipal();
        String usernameFromAccessToken = userDetail.getUsername();
        UserInfo user = userRepository.findByUsername(usernameFromAccessToken);
        return modelMapper.map(user, UserResponse.class);
    }

    @Override
    @Transactional
    public UserLoginResponse updateLoginTime(String loginId) {
        List<UserInfo> existedUsers = userRepository.findUserByMultiParam(loginId, loginId, loginId);
        UserInfo user = existedUsers.get(0);
        UserLoginResponse loginResponse =  modelMapper.map(user, UserLoginResponse.class);
        if (user.getLatestLogin() == null) {
            loginResponse.setFirstLogin(true);
        }
        userRepository.updateLatestLogin(new Date(), user.getId());

        return loginResponse;
    }
}
