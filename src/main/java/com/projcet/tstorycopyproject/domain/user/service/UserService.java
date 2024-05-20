package com.projcet.tstorycopyproject.domain.user.service;

import com.projcet.tstorycopyproject.domain.user.errorcode.AuthorizedErrorCode;
import com.projcet.tstorycopyproject.domain.user.response.UserRefreshTokenRp;
import com.projcet.tstorycopyproject.global.auth.jwt.JwtProperties;
import com.projcet.tstorycopyproject.global.entity.UserEntity;
import com.projcet.tstorycopyproject.global.entity.jpa_enum.SocialEnum;
import com.projcet.tstorycopyproject.global.entity.jpa_enum.UserRoleEnum;
import com.projcet.tstorycopyproject.global.exception.CustomException;
import com.projcet.tstorycopyproject.global.repository.UserRepository;
import com.projcet.tstorycopyproject.global.auth.jwt.JwtTokenProvider;
import com.projcet.tstorycopyproject.global.security.MyPrincipal;
import com.projcet.tstorycopyproject.global.security.MyUserDetails;
import com.projcet.tstorycopyproject.global.utils.CookieUtils;
import com.projcet.tstorycopyproject.global.utils.MyFileUtils;
import com.projcet.tstorycopyproject.global.utils.RedisUtils;
import com.projcet.tstorycopyproject.domain.user.errorcode.UserErrorCode;
import com.projcet.tstorycopyproject.domain.user.request.UserLoginRq;
import com.projcet.tstorycopyproject.domain.user.request.UserSignUpRq;
import com.projcet.tstorycopyproject.domain.user.response.UserLoginRp;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {
    private final RedisUtils redisUtils;
    private final UserRepository userRepository;
    private final MyFileUtils myFileUtils;
    private final CookieUtils cookieUtils;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final JwtProperties JWTProperties;


    @Transactional
    public void userSignUp(UserSignUpRq dto, MultipartFile profileImg) {
        UserEntity userEntity = UserEntity.builder()
                .userEmail(dto.getEmailCertificationRp().getEmail())
                .userPw(passwordEncoder.encode(dto.getPassword()))
                .role(UserRoleEnum.USER)
                .socialType(SocialEnum.LOCAL)
                .userName(dto.getName())
                .nickname(dto.getNickname())
                .build();
        userRepository.save(userEntity);
        if (profileImg != null) {
            String target = "/user/" + userEntity.getUserPk();
            String fileNm = myFileUtils.transferTo(profileImg, target);
            userEntity.changeUserPic(fileNm);
        }
        redisUtils.deleteData(userEntity.getUserEmail());

    }

    @Transactional
    public UserLoginRp userLogin(HttpServletResponse response, UserLoginRq userLoginRq) {
        UserEntity userEntity = userRepository.findByUserEmail(userLoginRq.getEmail())
                .orElseThrow(() -> new CustomException(UserErrorCode.NOT_FOUND_USER));
        if (!passwordEncoder.matches(userLoginRq.getPassword(), userEntity.getUserPw())) {
            throw new CustomException(UserErrorCode.INVALID_PASSWORD);
        }
        MyPrincipal myPrincipal = MyPrincipal.builder()
                .userPk(userEntity.getUserPk())
                .role(userEntity.getRole().name())
                .build();
        String at = jwtTokenProvider.generateAccessToken(myPrincipal);
        String rt = jwtTokenProvider.generateRefreshToken(myPrincipal);
        int rtCookieMaxAge = (int) JWTProperties.getJwt().getRefreshTokenExpiry() / 1000;
        cookieUtils.deleteCookie(response, "rt");
        cookieUtils.setCookie(response, "rt", rt, rtCookieMaxAge);
        return UserLoginRp.builder()
                .userPk(userEntity.getUserPk())
                .email(userEntity.getUserEmail())
                .nickname(userEntity.getNickname())
                .accessToken(at)
                .build();
    }

    @Transactional
    public String changeProfilePic(MultipartFile profileImg,UserEntity userEntity) {
        String target = "/user/" + userEntity.getUserPk();
        myFileUtils.delAllFiles(target);
        String fileNm = myFileUtils.transferTo(profileImg, target);
        userEntity.changeUserPic(fileNm);
        return fileNm;
    }
    // 닉네임 중복체크
    public Boolean checkUserDuplicationNickname(String nickname) {
        return userRepository.findByNickname(nickname).isEmpty();
    }

    // 엑서스 토큰 재발급
    public UserRefreshTokenRp refreshAccessToken(HttpServletRequest request ) {

        Cookie userCookie = cookieUtils.getCookie(request, "rt").get();
        if (userCookie == null) {
            throw new CustomException(AuthorizedErrorCode.NOT_EXISTS_REFRESH_TOKEN);
        }
        String token = userCookie.getValue();
        if (!jwtTokenProvider.isValidateToken(token)) {
            throw new CustomException(AuthorizedErrorCode.REFRESH_TOKEN_IS_EXPIRATION);
        }
        MyUserDetails myUserDetails = (MyUserDetails) jwtTokenProvider.getUserDetailsFromToken(token);
        MyPrincipal myprincipal = myUserDetails.getMyPrincipal();
        String at = jwtTokenProvider.generateAccessToken(myprincipal);

        return new UserRefreshTokenRp(at);
    }

    public void userLogout(HttpServletResponse response) {
        cookieUtils.deleteCookie(response,"rt");
    }
}
