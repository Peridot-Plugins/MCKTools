package me.mckoxu.mcktools.util;

import org.apache.commons.lang.Validate;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class RandomUtil {
    private static final Random RAND = new Random();

    public static int getRandom(int min, int max) throws IllegalArgumentException {
        Validate.isTrue(max > min, "Max can't be smaller than min!");
        return RAND.nextInt(max - min + 1) + min;
    }

    public static double getRandom(double min, double max) throws IllegalArgumentException {
        Validate.isTrue(max > min, "Max can't be smaller than min!");
        return min + (max - min) * RAND.nextDouble();
    }
}
