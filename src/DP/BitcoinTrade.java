package DP;

public class BitcoinTrade {

    //if you sell 𝑥 bit coins you receive only 0.95⋅𝑥⋅𝑟𝑡,
    // when you buy bit coins, you pay a fixed amount of €5 for the transaction.

    public static double optimalTrade(int n, double[] r) {
        double[] bitcoin = new double[n+1];
        double[] value = new double[n+1];

        bitcoin[0] = 0.1;
        value[0] = 0;

        for(int t = 1; t <= n; t++) {
            //prev or buy
            bitcoin[t] = Math.max(bitcoin[t-1], (value[t-1] - 5) / r[t]);
            //prev or sell
            value[t] = Math.max(value[t-1], 0.95 * bitcoin[t] * r[t]);
        }

        return value[n];
    }
}
