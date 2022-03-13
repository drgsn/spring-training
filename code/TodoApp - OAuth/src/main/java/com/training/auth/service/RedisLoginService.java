package com.training.auth.service;

import com.training.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class RedisLoginService {

    private final TokenStore redisTokenStore;
    private RedisTemplate<String, Object> redisTemplate;
    private ValueOperations valueOperations;

    @Autowired
    public RedisLoginService(@Lazy @Qualifier("tokenStore") TokenStore redisTokenStore,
                             @Lazy @Qualifier("redisTemplate") RedisTemplate<String, Object> redisTemplate) {
        this.redisTokenStore = redisTokenStore;
        this.redisTemplate = redisTemplate;
        this.valueOperations = redisTemplate.opsForValue();

    }

    /**
     * Checks the expiration of a Redis key.
     *
     * @param key the Redis key
     * @return if the provided key has expired
     */
    public boolean isExpired(String key) {
        return redisTemplate.getExpire(key) <= 0;
    }

    /**
     * Returns the expiration of a Redis key.
     *
     * @param key the Redis key
     * @return the expiration of the provided key in seconds
     */
    public int getExpire(String key) {
        return Math.toIntExact(redisTemplate.getExpire(key));
    }


    /**
     * Removes all the tokens associated with a certain users' device.
     *
     * @param user the user whose tokens to revoke
     * @return true if the process completed successfully
     */
    public boolean removeAccessTokens(User user) {
        Collection<OAuth2AccessToken> token_list = redisTokenStore
                .findTokensByClientIdAndUserName("browser", user.getUsername());
        for (OAuth2AccessToken aToken_list : token_list) {
            redisTokenStore.removeRefreshToken(aToken_list.getRefreshToken());
            redisTokenStore.removeAccessToken(aToken_list);
        }
        return true;
    }
}
