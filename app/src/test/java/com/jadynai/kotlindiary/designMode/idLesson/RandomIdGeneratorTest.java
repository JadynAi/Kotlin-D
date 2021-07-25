package com.jadynai.kotlindiary.designMode.idLesson;

import org.junit.Test;
import org.mockito.Mockito;

import java.net.UnknownHostException;

/**
 * JadynAi since 2021/7/25
 */
public class RandomIdGeneratorTest {

    @Test
    public void generate() {
        String generate = IdGeneratorChangeBefore.generate();
        System.out.println(generate);
    }

    @Test
    public void getLastFieldOfHostName() throws UnknownHostException {
        RandomIdGenerator randomIdGenerator = Mockito.mock(RandomIdGenerator.class);
        String lastFieldOfHostName = randomIdGenerator.getLastFieldOfHostName();
        System.out.println(lastFieldOfHostName);
    }
    
}