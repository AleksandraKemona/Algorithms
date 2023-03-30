// package collinear;

// Write a program BruteCollinearPoints.java that examines 4 points at a time and checks
// whether they all lie on the same line segment, returning all such line segments.
// To check whether the 4 points p, q, r, and s are collinear, check whether
// the three slopes between p and q, between p and r, and between p and s are all equal.

import java.util.ArrayList;
import java.util.Arrays;

public class BruteCollinearPoints {

    private final LineSegment[] segments;


    // finds all line segments containing 4 points
    public BruteCollinearPoints(Point[] points) {
        checkForNull(points);

        ArrayList<LineSegment> collinearSegments = new ArrayList<>();
        Point[] duplicateArray = Arrays.copyOf(points, points.length);
        Arrays.sort(duplicateArray);

        for (int p = 0; p < points.length-3; p++) {
            for (int q = p+1; q < points.length-2; q++) {
                for (int r = q+1; r < points.length -1; r++) {
                    for (int s = r+1; s < points.length; s++) {

                        double slope1 = duplicateArray[p].slopeTo(duplicateArray[q]);
                        double slope2 = duplicateArray[p].slopeTo(duplicateArray[r]);
                        double slope3 = duplicateArray[p].slopeTo(duplicateArray[s]);
                        if (slope1 == slope2 && slope2 == slope3) {
                            collinearSegments.add(new LineSegment(duplicateArray[p], duplicateArray[s]));
                        }
                    }
                }
            }
        }
        segments = collinearSegments.toArray(new LineSegment[collinearSegments.size()]);
    }



   // Corner cases. Throw an IllegalArgumentException if the argument to the constructor is null,
    // if any point in the array is null, or if the argument to the constructor contains a repeated point.

    // the number of line segments
    public int numberOfSegments() {
        return segments.length;
    }

    // The method segments() should include each line segment containing 4 points exactly once.
    // If 4 points appear on a line segment in the order p→q→r→s, then you should include either
    // the line segment p→s or s→p (but not both) and you should not include subsegments such as p→r or q→r.
    // For simplicity, we will not supply any input to BruteCollinearPoints that has 5 or more collinear points.
    public LineSegment[] segments() {

        return Arrays.copyOf(segments, numberOfSegments());
    }

    private void checkForNull(Point[] points) {
        if (points == null) {
            throw new IllegalArgumentException("Argument to the constructor can't be null");
        }
        for (int i = 0; i < points.length; i++) {
            if (points[i] == null) {
                throw new IllegalArgumentException("points can not be null");
            }
        }
        for (int r = 0; r < points.length -1; r++) {
            for (int s = r+1; s < points.length; s++) {
                if (points[r].compareTo(points[s]) == 0) {
                    throw new IllegalArgumentException("Duplicate points");
                }
            }
        }
    }
}
