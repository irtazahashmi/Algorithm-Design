package DivideAndConquer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class GetParetoOptimal {
    /**
     *  You should implement this method.
     *  @param S the list of solutions to get the pareto optima from.
     *  @return the list of pareto optimal solutions.
     */
    public static List<Solution> getParetoOptima(List<Solution> S) {
        Collections.sort(S, (s1, s2) -> s1.numberOfHours - s2.numberOfHours);
        if (S.size() > 1) return getParetoOptima(S, 0, S.size() - 1);
        else return S;
    }

    public static List<Solution> getParetoOptima(List<Solution> S, int start, int end) {
        List<Solution> result = new ArrayList<>();
        if (start == end) result.add(S.get(start));
        else {
            int mid = (start + end) / 2;
            List<Solution> left = getParetoOptima(S, start, mid);
            List<Solution> right = getParetoOptima(S, mid + 1, end);
            // Get the last element of left list and see all solutions that are undominated by it in
            // right list
            Solution lastLeft = left.get(left.size() - 1);
            left.addAll(undominatedBy(lastLeft, right));
            result = left;
        }

        return result;
    }

    /**
     *  You should implement this method.
     *  @param sol a solution to compare the others to.
     *  @param S a set of solutions
     *  @return all solutions in S that are _not_ dominated by sol.
     */
    public static List<Solution> undominatedBy(Solution sol, List<Solution> S) {
        List<Solution> result = new ArrayList<>(S.size());
        for (Solution s : S) {
            if (!((sol.quality > s.quality && sol.numberOfHours <= s.numberOfHours) || (sol.numberOfHours < s.numberOfHours && sol.quality >= s.quality))) {
                result.add(s);
            }
        }
        return result;
    }

    static class Solution {

        int numberOfHours;

        int quality;

        public Solution(int numberOfHours, int quality) {
            this.numberOfHours = numberOfHours;
            this.quality = quality;
        }

        /*
                You should not need the equals and hashcode method below, we just use them in the test.
             */
        @Override
        public boolean equals(Object o) {
            if (this == o)
                return true;
            if (o == null || getClass() != o.getClass())
                return false;
            Solution that = (Solution) o;
            return numberOfHours == that.numberOfHours && quality == that.quality;
        }

        @Override
        public int hashCode() {
            return Objects.hash(numberOfHours, quality);
        }
    }
}
