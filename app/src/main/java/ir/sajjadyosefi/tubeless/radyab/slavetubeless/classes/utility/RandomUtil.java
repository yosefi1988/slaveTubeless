package ir.sajjadyosefi.tubeless.radyab.slavetubeless.classes.utility;

import java.util.Random;

/**
 * Created by sajjad on 5/3/2018.
 */

public class RandomUtil {
    public long generateRandom(int length) {
        java.util.Random random = new java.util.Random();
        char[] digits = new char[length];
        digits[0] = (char) (random.nextInt(9) + '1');
        for (int i = 1; i < length; i++) {
            digits[i] = (char) (random.nextInt(10) + '0');
        }
        return Long.parseLong(new String(digits));
    }
}
