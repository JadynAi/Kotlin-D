package com.jadynai.kotlindiary.designMode.idLesson;

import android.annotation.SuppressLint;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Random;

import androidx.annotation.VisibleForTesting;

/**
 * JadynAi since 2021/7/25
 */
class RandomIdGenerator implements LogIdGenerator {

    @SuppressLint("DefaultLocale")
    @Override
    public String generate() {
        String hostName = getLastFieldOfHostName();
        String randomAlphameric = generateRandomAlphameric(8);
        return String.format("%s-%d-%s", hostName, System.currentTimeMillis(),
                randomAlphameric);
    }

    @VisibleForTesting
    protected String getLastFieldOfHostName() {
        String hostName = null;
        try {
            hostName = InetAddress.getLocalHost().getHostName();
        } catch (UnknownHostException e) {
            e.printStackTrace();
            return "";
        }
        String[] tokens = hostName.split(".");
        if (tokens.length > 0) {
            hostName = tokens[tokens.length - 1];
        }
        return hostName;
    }

    private String generateRandomAlphameric(int length) {
        char[] randomChars = new char[length];
        int count = 0;
        Random random = new Random();
        int maxAscii = 'z';
        while (count < length) {
            int randomAscii = random.nextInt(maxAscii);
            boolean isDigit = randomAscii >= '0' && randomAscii <= '9';
            boolean isUppercase = randomAscii >= 'A' && randomAscii <= 'Z';
            boolean isLowercase = randomAscii >= 'a';
            if (isDigit || isUppercase || isLowercase) {
                randomChars[count] = (char) randomAscii;
                count++;
            }
        }
        return new String(randomChars);
    }
}
