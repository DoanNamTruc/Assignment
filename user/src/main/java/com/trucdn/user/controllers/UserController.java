package com.trucdn.user.controllers;

import com.nimbusds.jose.shaded.gson.Gson;
import com.nimbusds.jose.shaded.gson.GsonBuilder;
import com.trucdn.user.common.constant.UserMsgType;
import com.trucdn.user.common.external.UserNotifyMsg;
import com.trucdn.user.dtos.*;
import com.trucdn.user.models.RefreshToken;
import com.trucdn.user.services.impl.JwtService;
import com.trucdn.user.services.NotifyEventService;
import com.trucdn.user.services.impl.RefreshTokenService;
import com.trucdn.user.services.UserService;
import com.trucdn.user.exception.BadRequestException;
import com.trucdn.user.validate.UserValidate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    NotifyEventService notifyEventService;

    @Autowired
    RefreshTokenService refreshTokenService;

    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    @PostMapping(value = "/create")
    public ResponseEntity<Object>  saveUser(@RequestBody UserRequest userRequest) {
            UserValidate.register(userRequest);
            UserResponse userResponse = userService.saveUser(userRequest);
            notifyEventService.userRegisterEvent(userResponse);
            return ResponseEntity.ok(userResponse);
    }

    @GetMapping("/profile")
    public ResponseEntity<UserResponse> getUserProfile() {
        try {
            UserResponse userResponse = userService.getUser();
            return ResponseEntity.ok().body(userResponse);
        } catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<JwtResponseDTO> AuthenticateAndGetToken(@RequestBody AuthRequestDTO authRequestDTO){
        UserValidate.login(authRequestDTO);
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                gson.toJson((LoginIdGroupDTO)authRequestDTO),
                authRequestDTO.getPassword()));
        UserLoginResponse response = userService.updateLoginTime(authRequestDTO);
        notifyEventService.userLoginEvent(response);
        if(authentication.isAuthenticated()){
            RefreshToken refreshToken = refreshTokenService.createRefreshToken(response.getId());
            return ResponseEntity.ok().body(JwtResponseDTO.builder()
                    .accessToken(jwtService.GenerateToken(response.getId().toString()))
                    .token(refreshToken.getToken()).build());
        } else {
            throw new UsernameNotFoundException("invalid user request..!!");
        }
    }

    @PostMapping("/refreshToken")
    public JwtResponseDTO refreshToken(@RequestBody RefreshTokenRequestDTO refreshTokenRequestDTO){
        return refreshTokenService.findByToken(refreshTokenRequestDTO.getToken())
                .map(refreshTokenService::verifyExpiration)
                .map(RefreshToken::getUserInfo)
                .map(userInfo -> {
                    String accessToken = jwtService.GenerateToken(userInfo.getUsername());
                    return JwtResponseDTO.builder()
                            .accessToken(accessToken)
                            .token(refreshTokenRequestDTO.getToken()).build();
                }).orElseThrow(() ->new RuntimeException("Refresh Token is not in DB..!!"));
    }
}
