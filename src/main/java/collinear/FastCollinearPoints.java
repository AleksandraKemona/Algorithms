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
        ArrayList<SlopeAssignment> slopeAssignments = new ArrayList<>();
        SlopeAssignment[] slopesArray;
        for (int p = 0; p < points.length-1; p++) {
            for (int q = p+1; q < points.length; q++) {
                double slope = duplicateArray[p].slopeTo(duplicateArray[q]);
                slopeAssignments.add(new SlopeAssignment(duplicateArray[q], slope));
            }
            slopesArray = slopeAssignments.toArray(new SlopeAssignment[slopeAssignments.size()]);
            Arrays.sort(slopesArray);
            for (int i = 0; i < slopesArray.length; i++) {
                ArrayList<Point> segment = new ArrayList<>();
                double slopeI = slopesArray[i].slope;
                segment.add(slopesArray[i].point);
                for (int j = 0; j < slopesArray.length; j++) {
                    double slopeJ = slopesArray[j].slope;
                    if (slopeJ == slopeI) {
                        segment.add(slopesArray[j].point);
                    } else if (segment.size() >= 4) {
                        Point[] sortedSegment = segment.toArray(new Point[segment.size()+1]);
                        Arrays.sort(sortedSegment);
                        collinearSegments.add(new LineSegment(sortedSegment[0], sortedSegment[sortedSegment.length]));
                    }
                }

            }
        }

        segments = collinearSegments.toArray(new LineSegment[collinearSegments.size()]);
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

    private class SlopeAssignment implements Comparable<SlopeAssignment> {
        private final Point point;     // x-coordinate of this point
        private final double slope;
        public SlopeAssignment(Point point, double slope) {
        this.point = point;
        this.slope = slope;
        }

        @Override
        public int compareTo(SlopeAssignment slopeAssignment) {
            if ( slope <  slopeAssignment.slope) {
                return -1;
            } else if ((slope >  slopeAssignment.slope)) {
                return +1;
            } else {
                return 0;
            }
        }
        public Comparator<SlopeAssignment> slopeOrder() {
            return new Comparator<SlopeAssignment>() {
                @Override
                public int compare(SlopeAssignment o1, SlopeAssignment o2) {
                    double slopeTilt = o1.slope - o2.slope;
                    return (int) Math.signum(slopeTilt);
                }
            };
        }
    }
}
