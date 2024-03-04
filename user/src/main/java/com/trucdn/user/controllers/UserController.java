package com.trucdn.user.controllers;

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

    @PostMapping(value = "/create")
    public ResponseEntity saveUser(@RequestBody UserRequest userRequest) {
        try {
            UserValidate.register(userRequest);
            UserResponse userResponse = userService.saveUser(userRequest);
            notifyEventService.userRegisterEvent(userResponse);
            return ResponseEntity.ok(userResponse);
        } catch (BadRequestException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
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
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequestDTO.getLoginId(), authRequestDTO.getPassword()));
        UserLoginResponse response = userService.updateLoginTime(authRequestDTO.getLoginId());
        notifyEventService.userLoginEvent(response);
        if(authentication.isAuthenticated()){
            RefreshToken refreshToken = refreshTokenService.createRefreshToken(authRequestDTO.getLoginId());
            return ResponseEntity.ok().body(JwtResponseDTO.builder()
                    .accessToken(jwtService.GenerateToken(authRequestDTO.getLoginId()))
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
