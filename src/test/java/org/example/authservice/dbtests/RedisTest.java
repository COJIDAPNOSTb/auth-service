package org.example.authservice.dbtests;



import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class RedisTest {

//    @Autowired
//    private StringRedisTemplate redisTemplate;
//
//    @Autowired
//    private RedisConnectionFactory redisConnectionFactory;
//
//    @Test
//    void testRedisConnection() {
//        try {
//
//            String isPingSuccessful = redisConnectionFactory.getConnection().ping();
//
//
//            assert isPingSuccessful != null;
//            assertThat(isPingSuccessful.equals("PONG")).isTrue();
//        } catch (Exception e) {
//            throw new RuntimeException("Не удалось подключиться к Redis", e);
//        }
//    }
//
//    @Test
//    void testRedisReadWrite() {
//        String key = "testKey";
//        String value = "testValue";
//
//        redisTemplate.opsForValue().set(key, value);
//        String result = redisTemplate.opsForValue().get(key);
//        assertThat(result).isEqualTo(value);
//    }
}