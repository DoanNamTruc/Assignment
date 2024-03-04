package com.trucdn.user.helpers;


import com.nimbusds.jose.shaded.gson.Gson;
import com.nimbusds.jose.shaded.gson.GsonBuilder;
import com.trucdn.user.dtos.LoginIdGroupDTO;
import com.trucdn.user.models.UserInfo;
import com.trucdn.user.repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;

@Component
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    private static final Logger logger = LoggerFactory.getLogger(UserDetailsServiceImpl.class);
    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        LoginIdGroupDTO loginIdDto = gson.fromJson(username, LoginIdGroupDTO.class);
        logger.debug("Entering in loadUserByUsername Method...");
        List<UserInfo> userList = userRepository.findUserByMultiParam(loginIdDto.getUsername(), loginIdDto.getEmail(), loginIdDto.getPhoneNumber());
        if(userList.isEmpty()){
            logger.error("Username not found: " + username);
            throw new UsernameNotFoundException("could not found user..!!");
        }
        logger.info("User Authenticated Successfully..!!!");
        return new CustomUserDetails(userList.get(0));
    }
}
