package com.projcet.tstorycopyproject.global.auth;

import com.projcet.tstorycopyproject.domain.user.errorcode.UserErrorCode;
import com.projcet.tstorycopyproject.global.auth.jwt.JwtTokenProvider;
import com.projcet.tstorycopyproject.global.entity.UserEntity;
import com.projcet.tstorycopyproject.global.exception.CustomException;
import com.projcet.tstorycopyproject.global.repository.UserRepository;
import com.projcet.tstorycopyproject.global.security.MyUserDetails;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
@Slf4j
@RequiredArgsConstructor
@Component
public class LoginArgumentResolver implements HandlerMethodArgumentResolver {
    private final JwtTokenProvider jwtTokenProvider;
    private final UserRepository userRepository;
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        log.info("supportsParameter 실행");
        boolean hasLoginAnnotation = parameter.hasParameterAnnotation(Login.class);
        boolean isUserEntityClass = parameter.getParameterType().equals(UserEntity.class);
        return hasLoginAnnotation && isUserEntityClass;
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        log.info("LoginArgumentResolver 동작");

        HttpServletRequest httpServletRequest = webRequest.getNativeRequest(HttpServletRequest.class);
        if (httpServletRequest != null){
            String token = jwtTokenProvider.resolveToken(httpServletRequest);
            log.info("resolveArgument token : " + token);

            if (token != null && !token.trim().isEmpty()){
                if (jwtTokenProvider.isValidateToken(token)){
                    MyUserDetails myUserDetails = (MyUserDetails) jwtTokenProvider.getUserDetailsFromToken(token);
                    Long userPk = myUserDetails.getMyPrincipal().getUserPk();
                    return userRepository.findById(userPk).orElseThrow();
                }
            }
            Login login = parameter.getParameterAnnotation(Login.class);
            if (login != null && !login.required()){
                return null;
            }
        }
        throw new CustomException(UserErrorCode.NOT_FOUND_USER);
    }
}
