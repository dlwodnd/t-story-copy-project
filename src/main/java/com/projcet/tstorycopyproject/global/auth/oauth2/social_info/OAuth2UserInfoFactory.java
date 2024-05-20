package com.projcet.tstorycopyproject.global.auth.oauth2.social_info;

import com.projcet.tstorycopyproject.global.entity.jpa_enum.SocialEnum;
import org.springframework.stereotype.Component;

import java.util.Map;


@Component
public class OAuth2UserInfoFactory {
    public OAuth2UserInfo getOAuth2UserInfo(SocialEnum socialType,
                                            Map<String, Object> attrs) {
        return switch(socialType) {
            case KAKAO -> new KakaoOAuth2UserInfo(attrs);
            case NAVER -> new NaverOAuth2UserInfo(attrs);
            default -> throw new IllegalArgumentException("Invalid Provider Type.");
        };
    }
}
