package main;

import java.lang.reflect.Method;
import java.util.function.Supplier;

public class App {

    public static String getStringProperty(Object object, String methodname) {
        String value = null;
        try {
            Method getter = object.getClass().getMethod(methodname, new Class[0]);
            value = (String) getter.invoke(object, new Object[0]);
        } catch (Exception e) {}

        return value;
    }

    public static void main(String[] args) {

        Person p1 = new Person("Hans", "Peter");

        System.out.println("Vorname von " + p1 + " ist " + getStringProperty(p1, "getVorname"));

    }
}

class Person {
    private String vorname;
    private String nachname;

    public Person(String v, String n) {
        vorname = v;
        nachname = n;
    }

    public String getVorname() {
        return vorname;
    }

    public String getNachname() {
        return nachname;
    }
}