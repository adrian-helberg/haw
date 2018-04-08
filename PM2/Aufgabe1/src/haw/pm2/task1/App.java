package haw.pm2.task1;

import haw.pm2.structures.Complex;
import haw.pm2.ultils.FormatUtils;

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
        Complex c1 = new ImmutableComplex(2, 3);
        Complex c2 = new ImmutableComplex(2, 3);

        Complex c3 = new MutableComplex(2, 3);
        Complex c4 = new MutableComplex(2, 3);


        String result = FormatUtils.toString(c1);
        String result2 = FormatUtils.toString(c3);

        System.out.println(result);
        System.out.println(result2);

        System.out.println(c1.cmp(c3) + "");
    }
}