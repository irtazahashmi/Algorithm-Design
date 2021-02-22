package DivideAndConquer;

import java.util.*;

public class ClosestPairs {

    public static double closestPair(List<Point> points) {
        // Base case
        if (points.size() <= 3) return Util.bruteForce(points);

        // Sort by x coordinate
        Util.sortByX(points);

        // find median x coordinate
        double medianX;
        if (points.size() % 2 == 0) {
            int mid = points.size() / 2;
            medianX = (points.get(mid - 1).x + points.get(mid).x) / 2;
        } else {
            int mid = points.size() / 2;
            medianX = points.get((int) Math.ceil(mid)).x;
        }

        // divide into left and right
        List<Point> left = new ArrayList<>();
        List<Point> right = new ArrayList<>();
        for (Point p : points) {
            if (p.x < medianX) left.add(p);
            else if (p.x > medianX) right.add(p);
        }

        //recursive calls
        double leftMin = closestPair(left);
        double rightMin = closestPair(right);
        double min = Math.min(leftMin, rightMin);

        // get points within S from L
        List<Point> patchPoints = new ArrayList<>();
        for (Point p : points) {
            if (p.x >= medianX - min && p.x <= medianX + min)
                patchPoints.add(p);
        }

        // sort by y coordinate
        Util.sortByY(patchPoints);

        //find min distance by comparing to next 11 points
        double minDist = min;
        for (int i = 0; i < patchPoints.size(); i++) {
            Point p1 = patchPoints.get(i);
            for (int j = 0; j < 12; j++) {
                if (j < patchPoints.size()) {
                    Point p2 = patchPoints.get(j);
                    double dist = Util.distance(p1, p2);
                    if (dist < minDist && dist != 0.0)
                        minDist = dist;
                }
            }
        }

        return minDist;
    }
}




class Util {

    /**
     * Takes two points and computes the euclidean distance between the two points.
     *
     * @param point1 - first point.
     * @param point2 - second point.
     * @return euclidean distance between the two points.
     * @see <a href="https://en.wikipedia.org/wiki/Euclidean_distance">https://en.wikipedia.org/wiki/Euclidean_distance</a>
     */
    public static double distance(Point point1, Point point2) {
        return Math.sqrt(Math.pow(point1.x - point2.x, 2.0D) + Math.pow(point1.y - point2.y, 2.0D));
    }

    /**
     * Takes a list of points and sorts it on x (ascending).
     *
     * @param points - points that need to be sorted.
     */
    public static void sortByX(List<Point> points) {
        Collections.sort(points, Comparator.comparingDouble(point -> point.x));
    }

    /**
     * Takes a list of points and sorts it on y (ascending) .
     *
     * @param points - points that need to be sorted.
     */
    public static void sortByY(List<Point> points) {
        Collections.sort(points, Comparator.comparingDouble(point -> point.y));
    }

    /**
     * Takes a list of points and returns the distance between the closest pair.
     * This is done by brute forcing.
     *
     * @param points - list of points that need to be considered.
     * @return smallest pair-wise distance between points.
     */
    public static double bruteForce(List<Point> points) {
        int size = points.size();
        if (size <= 1)
            return Double.POSITIVE_INFINITY;
        double bestDist = Double.POSITIVE_INFINITY;
        for (int i = 0; i < size - 1; i++) {
            Point point1 = points.get(i);
            for (int j = i + 1; j < size; j++) {
                Point point2 = points.get(j);
                double distance = Util.distance(point1, point2);
                if (distance < bestDist)
                    bestDist = distance;
            }
        }
        return bestDist;
    }
}

class Point {
    public double x;
    public double y;

    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }
}