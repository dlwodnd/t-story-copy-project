package com.projcet.tstorycopyproject.common.security;

import com.projcet.tstorycopyproject.common.entity.UserEntity;
import com.projcet.tstorycopyproject.common.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

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
