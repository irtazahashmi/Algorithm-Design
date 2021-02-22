package Greedy.Knapsack;

import java.util.ArrayList;
import java.util.Collections;

public class FractionalKnapsack {

    public static double maximumValue(int[] weights, int[] values, int maxWeight) {
        ArrayList<Item> items = new ArrayList<>();
        for(int i = 0; i < weights.length; i++)
            items.add(new Item(weights[i], values[i], (double) values[i]/weights[i]));

        Collections.sort(items);

        double totalValue = 0;
        int currWeight = 0;

        for(int i = 0; i < items.size(); i++) {
            if (currWeight + items.get(i).weight > maxWeight) {
                int remainingWeight = maxWeight - currWeight;
                totalValue += ((((double) remainingWeight / items.get(i).weight) * items.get(i).value));
                break;
            } else {
                currWeight += items.get(i).weight;
                if (currWeight <= maxWeight) totalValue += items.get(i).value;
            }
        }

        return totalValue;
    }

    public static void main(String[] args) {
        int[] weights = new int[]{2, 3, 5, 7, 1, 4, 1};
        int[] values = new int[]{10, 5, 15, 7, 6, 18, 3};
        int maxWeight = 15;
        System.out.println(maximumValue(weights, values, maxWeight)); // 55.3
    }
}

class Item implements Comparable<Item>{
    int weight;
    int value;
    double ratio;

    public Item(int w, int v, double r) {
        weight = w;
        value = v;
        ratio = r;
    }


    @Override
    public int compareTo(Item o) {
        if (o.ratio - this.ratio > 0) return 1;
        if (o.ratio - this.ratio < 0) return -1;
        else return 0;
    }
}
