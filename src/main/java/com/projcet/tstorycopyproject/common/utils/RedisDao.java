package com.projcet.tstorycopyproject.common.utils;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import java.time.Duration;

@Component
@RequiredArgsConstructor
public class RedisDao implements InitializingBean {
    private final StringRedisTemplate redisTemplate;
    private ValueOperations<String, String> valueOperations;
    @Override
    public void afterPropertiesSet() throws Exception {
        valueOperations = redisTemplate.opsForValue();
    }

    //Redis에 접근하기 위한 Spring의 Redis 템플릿 클래스
    public String getData(String key) {
        //지정된 키(key)에 해당하는 데이터를 Redis에서 가져오는 메서드
        return valueOperations.get(key);
    }
    public void setDataExpire(String key, String value, long duration) {
        //지정된 키(key)에 값을 저장하고, 지정된 시간(duration) 후에 데이터가 만료되도록 설정하는 메서드
        Duration expireDuration = Duration.ofSeconds(duration);
        valueOperations.set(key, value, expireDuration);
    }
    public void deleteData(String key) {
        //지정된 키(key)에 해당하는 데이터를 Redis에서 삭제하는 메서드
        redisTemplate.delete(key);
    }

    public boolean isExists(String key) {
        //지정된 키(key)에 해당하는 데이터가 Redis에 존재하는 지 확인하는 메서드
        return Boolean.TRUE.equals(redisTemplate.hasKey(key));
    }
}
