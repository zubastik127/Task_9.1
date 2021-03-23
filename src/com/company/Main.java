package com.company;
import java.util.Arrays;

public class Main {

    public static void main(String[] args) {

        MyCollection<Integer> myCollection = new MyCollection<>();
        myCollection.add(1);
        myCollection.add(2);
        myCollection.add(3);
        myCollection.add(2);
        myCollection.add(4);

        for (Integer integer : myCollection) {
            System.out.print(integer + " ");
        }

        System.out.println();

        Integer[] arr = new Integer[10];
        System.out.println(Arrays.toString(myCollection.toArray(arr)));

        System.out.println(myCollection.remove(3));

        System.out.println(Arrays.toString(myCollection.toArray()));
    }
}
