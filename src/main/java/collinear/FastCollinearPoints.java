// package collinear;

import java.util.ArrayList;
import java.util.Arrays;

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
        Arrays.sort(duplicateArray);

// Może dodać listę slopów?
        ArrayList<Double> slopes = new ArrayList<Double>();
        for (int p = 0; p < points.length-1; p++) {
            for (int q = p+1; q > 0; q--) {
                double slope1 = duplicateArray[p].slopeTo(duplicateArray[q]);
                double slope2 = duplicateArray[p].slopeTo(duplicateArray[q-1]);
                if (less(slope1, slope2)) {
                    exch(duplicateArray, q, q-1);
                } else {
                    break;
                }
            }
            int q = 1;
            Point pP = duplicateArray[0];
            for (int i = 1; i < points.length-1; i++) {
                ArrayList<Point> slopesSet = new ArrayList<Point>();
                int r = q+1;
                Point pQ = duplicateArray[q];
                Point pR = duplicateArray[r];
                slopesSet.add(pP);
                int compared = pP.slopeOrder().compare(pQ, pR);
                slopesSet.add(pQ);
                q++;
                if (compared > 0) {
                    Point[] slopesArray = slopesSet.toArray(new Point[slopesSet.size()]);
                    if (slopesSet.size() >= 4 && !slopes.contains(pP.slopeTo(pQ))){
                        collinearSegments.add(new LineSegment(slopesArray[0], slopesArray[slopesSet.size()]));
                    }
                    slopes.add(pP.slopeTo(pQ));
                }
            }
        }
        segments = collinearSegments.toArray(new LineSegment[collinearSegments.size()]);
    }

    private static boolean less(Double v, Double w)
    {  return v.compareTo(w) < 0;  }

    private static void exch(Point[] a, int i, int j)
    {  Point swap = a[i];
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
