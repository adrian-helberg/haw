package haw.pm2.task1;

import haw.pm2.structures.Complex;
import haw.pm2.ultils.FormatUtils;
import haw.pm2.ultils.MathUtils;

/**
 * Main class App
 */
public class App {

    /**
     * Program entrance point
     *
     * @param args Arguments
     */
    public static void main(String[] args) {

        Complex immutableComplex1 = new ImmutableComplex(1.0, 2.0);
        Complex immutableComplex2 = new ImmutableComplex(3.0, 4.0);
        Complex addedImmutableComplex;

        Complex mutableComplex1 = new MutableComplex(1.0, 2.0);
        Complex mutableComplex2 = new MutableComplex(3.0, 4.0);

        System.out.println(FormatUtils.print(immutableComplex1));
        System.out.println(FormatUtils.print(immutableComplex2));
        System.out.println();
        System.out.println(FormatUtils.print(mutableComplex1));
        System.out.println(FormatUtils.print(mutableComplex2));
        System.out.println();

        addedImmutableComplex = MathUtils.add(immutableComplex1, immutableComplex2);
        System.out.println(FormatUtils.print(addedImmutableComplex));

        MathUtils.add(mutableComplex1, mutableComplex2);
        System.out.println(FormatUtils.print(mutableComplex1));
    }
}