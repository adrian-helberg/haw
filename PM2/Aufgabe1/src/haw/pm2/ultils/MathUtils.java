package haw.pm2.ultils;

import haw.pm2.structures.Complex;
import haw.pm2.task1.ImmutableComplex;
import haw.pm2.task1.MutableComplex;

public final class MathUtils {

    public static ImmutableComplex add(Complex c1, Complex c2) {
        return new ImmutableComplex(c1.getRe() + c2.getRe(), c1.getIm() + c2.getIm());
    }

    public static void add(MutableComplex c1, MutableComplex c2) {
        c1.setRe(c1.getRe() + c2.getRe());
        c1.setIm(c1.getIm() + c2.getIm());
    }
}
