package Greedy.Knapsack;

import java.util.ArrayList;
import java.util.Collections;

public class PhotocopyingBusiness {

    //Given duration time Ti and Weight Wi, minimize (sum of Wi*Fi) where Fi = finish time for i

    public static ArrayList<Integer> solve(int[] duration, int[] weight) {
        ArrayList<Photocopy> photocopies = new ArrayList<>();
        for(int i = 0; i < duration.length; i++)
            photocopies.add(new Photocopy(i + 1, duration[i], weight[i], (double) duration[i]/weight[i]));

        Collections.sort(photocopies);
        ArrayList<Integer> order = new ArrayList<>();
        for(Photocopy p : photocopies) order.add(p.index);
        return order;
    }

    public static void main(String[] args) {
        int[] d= {1, 3};
        int[] w = {3, 2};
        System.out.println(solve(d, w));
    }
}

class Photocopy implements Comparable<Photocopy>{
    int index;
    int duration;
    int weight;
    double ratio;

    public Photocopy(int i, int d, int w, double r) {
        index=i;
        duration=d;
        weight=w;
        ratio=r;
    }

    @Override
    public int compareTo(Photocopy o) {
        if (this.ratio-o.ratio > 0) return 1;
        else if (this.ratio-o.ratio<0)return -1;
        else return 0;
    }
}
