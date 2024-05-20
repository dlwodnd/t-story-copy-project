package com.projcet.tstorycopyproject.global.auth.oauth2;


import com.projcet.tstorycopyproject.global.auth.oauth2.social_info.OAuth2UserInfo;
import com.projcet.tstorycopyproject.global.auth.oauth2.social_info.OAuth2UserInfoFactory;
import com.projcet.tstorycopyproject.global.entity.UserEntity;
import com.projcet.tstorycopyproject.global.entity.jpa_enum.SocialEnum;
import com.projcet.tstorycopyproject.global.repository.UserRepository;
import com.projcet.tstorycopyproject.global.security.MyPrincipal;
import com.projcet.tstorycopyproject.global.security.MyUserDetails;
import com.projcet.tstorycopyproject.global.security.UserInfo;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {
    private final UserRepository userRepository;
    private final OAuth2UserInfoFactory factory;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User user = super.loadUser(userRequest);
        try {
            return this.process(userRequest, user);
        } catch (AuthenticationException e) {
            throw e;
        } catch (Exception e) {
            throw new InternalAuthenticationServiceException(e.getMessage(), e.getCause());
        }
    }

    private OAuth2User process(OAuth2UserRequest userRequest, OAuth2User user) {
        SocialEnum socialType
                    = SocialEnum.valueOf(userRequest.getClientRegistration()
                                                            .getRegistrationId()
                                                            .toUpperCase()
                    );

        OAuth2UserInfo oAuth2UserInfo = factory.getOAuth2UserInfo(socialType, user.getAttributes());
        UserEntity userEntity = userRepository.findByUserEmailAndSocialType(oAuth2UserInfo.getEmail(),socialType)
                .orElse(UserEntity.builder()
                        .userPw(createUserPassWord())
                        .userEmail(oAuth2UserInfo.getEmail())
                        .userPic(oAuth2UserInfo.getImageUrl())
                        .userName(oAuth2UserInfo.getName())
                        .nickname(oAuth2UserInfo.getName())
                        .socialType(socialType)
                        .build());
        userRepository.save(userEntity);

        MyPrincipal myPrincipal = MyPrincipal.builder()
                .userPk(userEntity.getUserPk())
                .role(userEntity.getRole().name())
                .build();
         UserInfo userInfo = UserInfo.builder()
                .userPk(userEntity.getUserPk())
                .userPic(userEntity.getUserPic())
                .userEmail(userEntity.getUserEmail())
                .role(userEntity.getRole())
                .userPw(userEntity.getUserPw())
                .nickname(userEntity.getNickname())
                .socialType(userEntity.getSocialType())
                .build();
        return MyUserDetails.builder()
                .userInfo(userInfo)
                .myPrincipal(myPrincipal)
                .attributes(user.getAttributes())
                .build();
    }   private String createUserPassWord(){
        return RandomStringUtils.randomAlphanumeric(12);
    }

}
