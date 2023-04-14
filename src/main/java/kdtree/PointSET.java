// package kdtree;

// Implement the following API by using a red–black BST:
// You must use either SET or java.util.TreeSet; do not implement your own red–black BST.
// -------------------------------------
// Corner cases.  Throw an IllegalArgumentException if any argument is null.
// -------------------------------------
// Performance requirements.  Your implementation should support insert() and contains()
// in time proportional to the logarithm of the number of points in the set in the worst case;
// it should support nearest() and range() in time proportional to the number of points in the set.

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.SET;

public class PointSET {
    private SET<Point2D> brute;
    private Point2D nearestPoint;
    private double shortestDistance;


    // construct an empty set of points
    public PointSET() {
        this.brute = new SET<>();
    }

    // is the set empty?
    public boolean isEmpty() {
        return (size() == 0);
    }

    // number of points in the set
    public int size() {
        return brute.size();
    }

    // add the point to the set (if it is not already in the set)
    public void insert(Point2D p) {
        if (p == null) throw new IllegalArgumentException();
        brute.add(p);
    }

    // does the set contain point p?
    public boolean contains(Point2D p) {
        if (p == null) throw new IllegalArgumentException();
        return brute.contains(p);
    }

    // draw all points to standard draw
    public void draw() {
        for (Point2D point : brute) {
            point.draw();
        }
    }

    // all points that are inside the rectangle (or on the boundary)
    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) throw new IllegalArgumentException();
        SET<Point2D> inRangeSet = new SET<>();
        for (Point2D point : brute) {
            if (rect.contains(point)) {
                inRangeSet.add(point);
            }
        }
        if (inRangeSet.isEmpty()) {
            return null;
        } else {
            return inRangeSet;
        }

    }

    // a nearest neighbor in the set to point p; null if the set is empty
    public Point2D nearest(Point2D p) {
        if (p == null) throw new IllegalArgumentException();
        shortestDistance = Double.POSITIVE_INFINITY;
        nearestPoint = null;
        for (Point2D point : brute) {
            double distance = point.distanceSquaredTo(p);
            if (distance < shortestDistance) {
                shortestDistance = distance;
                nearestPoint = point;
            }
        }
        return nearestPoint;
    }

    // unit testing of the methods (optional)
    // public static void main(String[] args) {
    //     Point2D point1 = new Point2D(0.25, 0.75);
    //     Point2D point2 = new Point2D(0.75, 0.75);
    //     Point2D point3 = new Point2D(0.25, 0.25);
    //     Point2D point4 = new Point2D(0.5, 0.5);
    //     Point2D point5 = new Point2D(0.75, 1.0);
    //     Point2D point6 = new Point2D(0.5, 0.0);
    //     Point2D point7 = new Point2D(0.5, 0.0);
    //     Point2D point8 = new Point2D(1.0, 0.5);
    //     Point2D point9 = new Point2D(0.5, 0.25);
    //     Point2D point10 = new Point2D(0.0, 1.0);
    //
    //     Point2D p = new Point2D(0.5, 1.0);
    //
    //
    //     PointSET brute = new PointSET();
    //     brute.insert(point1);
    //     brute.insert(point2);
    //     brute.insert(point3);
    //     brute.insert(point4);
    //     brute.insert(point5);
    //     brute.insert(point6);
    //     brute.insert(point7);
    //     brute.insert(point8);
    //     brute.insert(point9);
    //     brute.insert(point10);
    //
    //     Point2D nearest = nearest(p);
    //
    //     System.out.println("Nearest point " + nearest);
    //
    //
    //     // // Point2D nearestPoint = new Point2D(p.x(), p.y());
    //     // double shortestDistance = Double.POSITIVE_INFINITY;
    //     // ;
    //     // // if (p == null) throw new IllegalArgumentException();
    //     // // if (p == null) throw new IllegalArgumentException();
    //     // double xmin = p.x() - 0.05;
    //     // double xmax = p.x() + 0.05;
    //     // double ymin = p.y() - 0.05;
    //     // double ymax = p.y() + 0.05;
    //     // RectHV rangeForNeighbours;
    //     // Iterable<Point2D> neighbours = null;
    //     // double distance = 0;
    //     // System.out.println("neighbours " + neighbours);
    //     //
    //     // while (neighbours == null) {
    //     //     System.out.println("neighbours == null");
    //     //     rangeForNeighbours = new RectHV(xmin, ymin, xmax, ymax);
    //     //     neighbours = range(rangeForNeighbours);
    //     //     distance = rangeForNeighbours.distanceSquaredTo(p);
    //     //     xmin = xmin - 0.05;
    //     //     xmax = xmax + 0.05;
    //     //     ymin = ymin - 0.05;
    //     //     ymax = ymax + 0.05;
    //     // }
    //     //
    //     // int counter = 1;
    //     // for (Point2D point : neighbours) {
    //     //     Point2D nearestPoint;
    //     //     System.out.println("Point: " + point + ", distance: " + point.distanceSquaredTo(p));
    //     //     if (point.distanceTo(p) == distance) {
    //     //         nearestPoint = point;
    //     //     }
    //     //     counter++;
    //     // }
    //
    //
    //     // System.out.println(nearestPoint);
    // }
}

