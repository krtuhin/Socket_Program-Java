package com.Manchester;

import java.util.*;

public class Manchester {
    public static void main(String args[]) {

        Scanner sc = new Scanner(System.in);
        ArrayList<Integer> data = new ArrayList<>();
        int bit;

        System.out.print("Enter Data        : ");
        String str = sc.next();

        System.out.print("Enter Counter Bit : ");
        bit = sc.nextInt();

        for (int i = 0; i < str.length(); i++) {
            data.add(Character.getNumericValue(str.charAt(i)));
        }

        System.out.print("\nManchester Encoding              : ");
        for (int i = 0; i < data.size(); i++) {
            if (data.get(i) == 0)
                System.out.print("01 ");
            if (data.get(i) == 1)
                System.out.print("10 ");
        }

        System.out.print("\nDifferential Manchester Encoding : ");
        for (int i = 0; i < data.size(); i++) {
            if (data.get(i) == 0) {
                if (bit == 0) {
                    System.out.print("10 ");
                    bit = 0;
                } else {
                    System.out.print("01 ");
                    bit = 1;
                }
            } else if (bit == 0) {
                System.out.print("01 ");
                bit = 1;
            } else {
                System.out.print("10 ");
                bit = 0;
            }
        }
        System.out.println();
    }
}
