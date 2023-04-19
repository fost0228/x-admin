package com.lantu;

import com.lantu.common.utils.JwtUtil;
import com.lantu.sys.entity.User;
import io.jsonwebtoken.Claims;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author jiangH
 * @create 18-04-2023 11:58 PM
 */
@SpringBootTest
public class JwtUtilTest {
    @Autowired
    private JwtUtil jwtUtil;

    @Test
    public void testCreateJwt() {
        User user = new User();
        user.setUsername("zhangsan");
        user.setPhone("4031586666");
        String token = jwtUtil.createToken(user);
        System.out.println(token);

    }

    @Test
    public void testParseJwt() {
        String token = "eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiI5ODFjMjlmYS0wOTJiLTQwYzAtODc0ZS1jNzhiN2JjYzRlNmEiLCJzdWIiOiJ7XCJwaG9uZVwiOlwiNDAzMTU4NjY2NlwiLFwidXNlcm5hbWVcIjpcInpoYW5nc2FuXCJ9IiwiaXNzIjoic3lzdGVtIiwiaWF0IjoxNjgxODg0MDcxLCJleHAiOjE2ODE4ODU4NzF9.o3tLMQQ4Ts98pP6098zAAEHLgpOWw902K2N4NXQoOSc";
        Claims claims = jwtUtil.parseToken(token);
        System.out.println(claims);
    }

    @Test
    public void testParseJwt2() {
        String token = "eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiI5ODFjMjlmYS0wOTJiLTQwYzAtODc0ZS1jNzhiN2JjYzRlNmEiLCJzdWIiOiJ7XCJwaG9uZVwiOlwiNDAzMTU4NjY2NlwiLFwidXNlcm5hbWVcIjpcInpoYW5nc2FuXCJ9IiwiaXNzIjoic3lzdGVtIiwiaWF0IjoxNjgxODg0MDcxLCJleHAiOjE2ODE4ODU4NzF9.o3tLMQQ4Ts98pP6098zAAEHLgpOWw902K2N4NXQoOSc";
        User user = jwtUtil.parseToken(token, User.class);
        System.out.println(user);
    }
}
