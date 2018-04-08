package haw.pm2.ultils;

import haw.pm2.structures.Complex;

public final class FormatUtils {

    public static String toString(Complex c) {
        if (c.getIm() == 0) return c.getRe() + "+0i";
        if (c.getRe() == 0) return c.getIm() + "i";
        if (c.getIm() < 0) return c.getRe() + " - " + (-c.getIm()) + "i";
        return c.getRe() + " + " + c.getIm() + "i";
    }
}