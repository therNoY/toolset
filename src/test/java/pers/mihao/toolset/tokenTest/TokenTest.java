package pers.mihao.toolset.tokenTest;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import pers.mihao.toolset.common.util.JwtTokenHelper;

@SpringBootTest
@RunWith(SpringRunner.class)
public class TokenTest {

    @Autowired
    JwtTokenHelper jwtTokenHelper;

    @Test
    public void name() {
        String token = jwtTokenHelper.generateToken("123");
        System.out.println(token);
        jwtTokenHelper.validateToken(token);
    }
}
