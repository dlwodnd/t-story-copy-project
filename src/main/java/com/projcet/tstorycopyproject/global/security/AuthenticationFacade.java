package com.projcet.tstorycopyproject.global.security;

import com.projcet.tstorycopyproject.global.entity.UserEntity;
import com.projcet.tstorycopyproject.global.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
@Slf4j
@Component
@RequiredArgsConstructor
public class AuthenticationFacade {
    private final UserRepository userRepository;
    public MyUserDetails getLoginUser(){
        try {
            return (MyUserDetails) SecurityContextHolder
                    .getContext()
                    .getAuthentication()
                    .getPrincipal();
        }catch (Exception e){
            return null;
        }
    }
    public UserEntity getLoginUserEntity(){
        MyUserDetails myUserDetails = getLoginUser();
        return userRepository.findById(myUserDetails.getMyPrincipal().getUserPk()).orElse(null);
    }
}
