package pers.mihao.toolset;

import org.junit.jupiter.api.Test;
import pers.mihao.toolset.common.vo.test_dto.Dog;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

public class JavaTest {
    @Test
    void name() {
        System.out.println(Math.random());
    }

    @Test
    void StringTest() {
        String a = "baidu%s.com";
        String b = String.format(a, "aa");
        System.out.println(b);
    }

    @Test
    void LocalDataTimeTest() throws InterruptedException {
        LocalDateTime localDataTime = LocalDateTime.now();
        Thread.sleep(1000);
        System.out.println(localDataTime.compareTo(LocalDateTime.now()));
    }

    @Test
    void name2() {
        for (int i = 0; i < 100; i++) {
            long index = Math.round(Math.random() * (10 - 1));
            System.out.println(index);
        }
    }

    @Test
    void name3() {
        List<Dog> dogList = Dog.getList();
    }
}
