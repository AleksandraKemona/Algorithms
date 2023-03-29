// package collinear;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

public class FastCollinearPoints {

    private final LineSegment[] segments;
    private Point[][] setsOfPoints;



    // finds all line segments containing 4 or more points
    public FastCollinearPoints(Point[] points) {
        if (points == null) {
            throw new IllegalArgumentException("Argument to the constructor can't be null");
        }
        checkForNull(points);
        ArrayList<LineSegment> collinearSegments = new ArrayList<>();
        Point[] duplicateArray = Arrays.copyOf(points, points.length);


        for (int p = 0; p < points.length-1; p++) {
            for (int q = p+1; q > 0; q--) {
                double slope1 = duplicateArray[p].slopeTo(duplicateArray[q]);
                double slope2 = duplicateArray[p].slopeTo(duplicateArray[q-1]);
                if(less(slope1, slope2)){
                    exch(duplicateArray, q, q-1);
                } else {
                    break;
                }
            }
        }
        for (int p = 0; p < points.length; p++) {
            int startingPoint = 0;
            int endingPoint = 1;
            for (int q = p+1; q < points.length; q++){
                double slope1 = duplicateArray[p].slopeTo(duplicateArray[q]);
                double slope2 = duplicateArray[p].slopeTo(duplicateArray[q-1]);
                if (slope1 == slope2 || slope2 == 0){
                    endingPoint++;
                } else {
                    collinearSegments.add(new LineSegment(duplicateArray[startingPoint], duplicateArray[endingPoint]));
                    startingPoint = endingPoint;
                    endingPoint = startingPoint + 1;
                }
            }
        }

        segments = collinearSegments.toArray(new LineSegment[collinearSegments.size()]);
    }

    private static boolean less(Comparable v, Comparable w)
    {  return v.compareTo(w) < 0;  }

    private static void exch(Comparable[] a, int i, int j)
    {  Comparable swap = a[i];
        a[i] = a[j];
        a[j] = swap;
    }



    // the number of line segments
    public int numberOfSegments() {
        return segments.length;
    }

    // the line segments
    public LineSegment[] segments()  {
        return Arrays.copyOf(segments, numberOfSegments());
    }
    private void checkForNull(Point[] points) {
        for (int r = 0; r < points.length -1; r++) {
            for (int s = r+1; s < points.length; s++) {
                if (points[r] == null || points[s] == null) {
                    throw new IllegalArgumentException("points can not be null");
                }
                if (points[r].compareTo(points[s]) == 0) {
                    throw new IllegalArgumentException("Duplicate points");
                }
            }
        }
    }


}
