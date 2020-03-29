package com.hong.order.util;

import java.security.SecureRandom;
import java.util.Random;

/**
 * @author wanghong
 * @date 2020/03/29 17:19
 **/
public class RandomNumberGenerator {
    private static Random random = new SecureRandom();
    private static final char[] CHARACTER_TABLE = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};

    private RandomNumberGenerator() {
    }

    public static String getRandomNumber(int length) {
        StringBuilder sb = new StringBuilder();

        do {
            long randomLong;
            for(randomLong = random.nextLong(); randomLong == -2147483648L || randomLong == -9223372036854775808L; randomLong = random.nextLong()) {
            }

            sb.append(Math.abs(randomLong));
        } while(sb.length() < length);

        return sb.substring(0, length);
    }

    public static String getRandomCharAndNumber(int length, boolean upperCaseSupported) {
        StringBuilder rsb = new StringBuilder();
        int i;
        if (upperCaseSupported) {
            for(i = 0; i < length; ++i) {
                rsb.append(CHARACTER_TABLE[random.nextInt(62)]);
            }
        } else {
            for(i = 0; i < length; ++i) {
                rsb.append(CHARACTER_TABLE[random.nextInt(36)]);
            }
        }

        return rsb.toString();
    }
}
