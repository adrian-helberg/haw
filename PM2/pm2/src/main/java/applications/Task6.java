package applications;

import deque.ArrayDeque;
import deque.Deque;
import deque.ListDeque;

import java.io.*;

public class Task6 {
    public static void main(String[] args) {
        Deque originalArray = new ArrayDeque(4);
        Deque originalList = new ListDeque();

        originalArray.push("I");
        originalArray.push("am");
        originalArray.push("Array");
        originalArray.push("Deque!");

        originalList.push("I");
        originalList.push("am");
        originalList.push("List");
        originalList.push("Deque!");

        System.out.print("Original ArrayDeque: ");
        System.out.println(originalArray);
        System.out.print("Original ListDeque: ");
        System.out.println(originalList);

        System.out.println();
        System.out.println("--- SERIALIZING & DESERIALIZING ---");
        System.out.println();

        long time = System.currentTimeMillis();
        File arrayFile = new File(time + "_array-deque.txt");
        File listFile = new File(time + "_list-deque.txt");

        try (
            FileOutputStream arrayfos = new FileOutputStream(arrayFile);
            FileOutputStream listfos = new FileOutputStream(listFile);
            ObjectOutputStream arrayoos = new ObjectOutputStream(arrayfos);
            ObjectOutputStream listoos = new ObjectOutputStream(listfos);
        ) {

            arrayoos.writeObject(originalArray);
            listoos.writeObject(originalList);

        } catch (IOException e) {
            e.printStackTrace();
        }

        try (
                FileInputStream arrayfis = new FileInputStream(arrayFile);
                FileInputStream listfis = new FileInputStream(listFile);
                ObjectInputStream arrayois = new ObjectInputStream(arrayfis);
                ObjectInputStream listois = new ObjectInputStream(listfis);
        ) {

            Deque serializedArray = (Deque) arrayois.readObject();
            Deque serializedList = (Deque) listois.readObject();

            System.out.print("Serialized ArrayDeque: ");
            System.out.println(serializedArray);
            System.out.print("Serialized ListDeque: ");
            System.out.println(serializedList);

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
