package com.jadynai.kotlindiary.designMode.idLesson;

import com.google.common.truth.Truth;

import org.junit.Test;
import org.mockito.Mockito;

import java.net.UnknownHostException;
import java.util.ArrayList;

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

    @Test
    public void testGenerate() {
        IdGenerator idGenerator = new RandomIdGenerator();
        ArrayList<String> historyIdList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            String id = idGenerator.generate();
            Truth.assertThat(historyIdList)
                    .doesNotContain(id);
            historyIdList.add(id);
        }
    }
}