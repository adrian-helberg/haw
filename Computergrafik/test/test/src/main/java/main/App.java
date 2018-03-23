package main;

import java.util.function.Function;

public class App {

    public Function intSeq = x -> {
        int i = 0;
        return () -> {
          i++;
          return i;
        };
    };

    public static void main(String[] args) {
        int nextInt = intSeq();

        System.out.println(nextInt);
    }

}
