package haw.pm2.ultils;

import haw.pm2.structures.Complex;

public final class FormatUtils {

    public static String print(Complex c) {
        return c.getRe() + " + " + c.getIm() + "i";
    }
}