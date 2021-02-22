package DivideAndConquer;

public class ExponentionBySquaringPower {
    public static int pow(int a, int b) {
        if (b < 0) return 1 / pow(a, b);
        if (b == 0) return 1;
        if (b == 1) return a;

        if (b % 2 == 0) return pow(a * a, b / 2);
        else return a * pow(a * a, (b - 1) / 2);
    }

    // ans % (10^9 + 7)
    long pow(long a, long b) {
        if (b < 0) return 1 / pow(a, b);

        long m = 1000000007;
        b = b % m;
        if (b == 0) return 1;
        if (b == 1) return a;

        if (b % 2 == 0) return pow((a * a) % m, b / 2) % m;
        else return ((a % m) * pow((a * a) % m , b / 2)) % m;
    }
}
