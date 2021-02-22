package DivideAndConquer;

import java.util.*;

public class Karatsuba {

    public static long karatsuba(long x, long y){
        // base case. if single number just multiply
        if(x < 10 || y < 10) return x * y;

        // length of the numbers. Using base 10 because numbers are base 10
        int length = Math.min(String.valueOf(x).length(), String.valueOf(y).length());
        int mid = length/ 2;

        // divide the number into two halves
        int base10 = (int) Math.pow(10,mid);

        //first half
        long x0 = x/base10;
        long y0 = y/base10;

        //second half
        long x1 = x% base10;
        long y1 = y %base10;

        // calls recursively
        long x0y0 =karatsuba(x0, y0);
        long x1y1 =karatsuba(x1, y1);
        long p = karatsuba(x0 +x1, y0 + y1);

        // using the formula. Using 10^ because numbers are base 10 else 2^ for binary
        // x1y1 + (x1y1 × 10 ^ (mid × 2)) + ((p - x1y1 - x0y0) × 10 ^ mid)
        // n / 2 = mid ---> 2 * mid = n because of loss when converting to int
        long formula = (long) (x1y1 + (x0y0 * Math.pow(10, (mid * 2))) + ((p - x0y0 - x1y1) * Math.pow(10, mid)));
        return formula;
    }

     public static void main(String[] args) {
        int number1 = -34;
        int number2 = 389;
        System.out.println(karatsuba(number1, number2));
    }
}