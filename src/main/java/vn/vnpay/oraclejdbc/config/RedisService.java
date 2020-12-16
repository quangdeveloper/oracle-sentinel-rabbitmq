package vn.vnpay.oraclejdbc.config;

import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import vn.vnpay.oraclejdbc.util.GsonUtil;

@Service
public class RedisService {

    private final Logger logger = LoggerFactory.getLogger(RedisService.class);

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    public void setKey(String key, Object value) {
        try {
            redisTemplate.opsForValue().set(key, GsonUtil.toJson(value));
            logger.info("Set key {} successful. Value is: {}", key, value);
        } catch (Exception exception) {
            logger.error("Set key {} failed: {} because: {}", key, value, exception.getMessage());
        }
    }

    public Object getKey(String key) {
        if (Boolean.TRUE.equals(redisTemplate.hasKey(key))) {
            return redisTemplate.opsForValue().get(key);
        } else {
            logger.warn("Key {}  not exists!!!!", key);
            return null;
        }
    }

    public Boolean hasKey(String key) {
        return redisTemplate.hasKey(key);
    }
}
