// package kdtree;

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.SET;

import java.util.ArrayList;

// public class KdTree <Key extends Comparable<Key>, Value>{
public class KdTree {
    // private TreeSet<Point2D> kdTree;
    private Point2D nearestPoint;
    SET<Point2D> inRangeSet = new SET<>();


    // private static Node temporaryRoot;

    private KdTree kdt;

    private Node root;

    private Node nextParent = null;

    private int size;
    private int duplicateNumber = 0;
    // private ArrayList<double[]> listOfNodes = new ArrayList<>();


    // private BST bst = new BST<>();


    // construct an empty set of points
    public KdTree() {
        root = null;
    }


    private class Node {
        private double[] point;           // sorted by key
        private Node parent;
        private Node left, right;  // left and right subtrees
        private int size;

        private int depth;


        public Node(double[] p) {
            this.point = p;
            this.left = null;
            this.right = null;
            this.parent = null;
        }

        public Node(double[] p, Node par) {
            this.point = p;
            this.left = null;
            this.right = null;
            this.parent = par;
        }

        public Node(double[] p, int size) {
            this.point = p;
            this.size = size;
            // this.depth = dep;
        }
    }

    // is the set empty?
    public boolean isEmpty() {
        // if (root == null) {
        //     return true;
        // } else {
        return (size == 0);
        // }
    }

    public int size() {

        return size(root);
        // }
    }

    // number of points in the set
    // private int size(KdTree.Node x) {
    //     if (x == null) return 0;
    //     else return 1;
    // }

    // add the point to the set (if it is not already in the set)
    public void insert(Point2D point) {
        // System.out.println("-------------------------kolejny punkt " + point.x() + " " + point.y() + "----------------");
        // System.out.println();
        // size++;
        // System.out.println("Point " + point);
        double[] insertedPoint = new double[2];
        // System.out.println("inserted point " + insertedPoint[0] + insertedPoint[1]);
        // System.out.println(" X " + point.x());
        // System.out.println(" Y " + point.y());
        insertedPoint[0] = point.x();
        insertedPoint[1] = point.y();
        // System.out.println("inserted point after " + insertedPoint[0] + " " + insertedPoint[1]);
        size++;
        insertNode(insertedPoint);

    }

    private void insertNode(double[] p) {
        root = insertIntoTree(root, p, 0);
    }

    private double[] getPoint(Node n) {
        if (n != null) {
            return n.point;
        }
        return new double[0];
    }

    private Node insertIntoTree(Node r, double[] p, int depth) {

        int coordinate = depth % 2;
        if (r == null) {
            return new Node(p, 1);
        }
        if (areEqual(p, getPoint(r))) {
            // r.size = size(r.left) + size(r.right) - 1;
            return r;
        }
        int comp = compare(p[coordinate], r.point[coordinate]);
        if (comp < 0) r.left = insertIntoTree(r.left, p, depth + 1);
        else r.right = insertIntoTree(r.right, p, depth + 1);


        r.size = 1 + size(r.left) + size(r.right);

        return r;
    }

    private int size(Node x) {
        if (x == null) return 0;
        else return x.size;
    }


    private boolean areEqual(double[] p1, double[] p2) {
        return (p1[0] == p2[0] && p1[1] == p2[1]);
    }

    // does the tree contain point p?
    public boolean contains(Point2D p) {
        if (p == null) throw new IllegalArgumentException();
        double[] insertedPoint = new double[2];
        insertedPoint[0] = p.x();
        insertedPoint[1] = p.y();

        return get(insertedPoint);
    }

    private boolean get(double[] key) {
        return get(root, key, 0);
    }

    private boolean get(Node n, double[] key, int depth) {
        int coordinate = depth % 2;
        if (key == null) throw new IllegalArgumentException("calls get() with a null key");
        if (n == null) return false;
        if (areEqual(key, getPoint(n))) return true;

        int comp = compare(key[coordinate], n.point[coordinate]);
        if (comp < 0) return get(n.left, key, depth + 1);
            // else if (comp > 0) return get(n.right, key, depth + 1);
        else return get(n.right, key, depth + 1);

    }


    // draw all points to standard draw
    public void draw() {
        // while (root.left != null || root.right != null){
        //
        // }
        // Point2D root2D = new Point2D(root.point[0], root.point[1]);
        // root2D.draw();
        // if (root.left != null){
        //
        // }
    }

    // all points that are inside the rectangle (or on the boundary)
    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) throw new IllegalArgumentException();
        range(rect, root);

        return inRangeSet;
    }

    private void range(RectHV rect, Node temp) {

        double xchange = (rect.xmax() - rect.xmin()) / 2;
        double ychange = (rect.ymax() - rect.ymin()) / 2;
        double xmin = temp.point[0] - xchange;
        double xmax = temp.point[0] + xchange;
        double ymin = temp.point[1] - ychange;
        double ymax = temp.point[1] + ychange;
        RectHV tempRect = new RectHV(xmin, ymin, xmax, ymax);
        if (rect.contains(new Point2D(temp.point[0], temp.point[1]))) {
            inRangeSet.add(new Point2D(temp.point[0], temp.point[1]));
        }
        if (rect.intersects(tempRect)) {
            range(rect, temp.left);
            range(rect, temp.left);
        }
    }

    // a nearest neighbor in the set to point p; null if the set is empty
    public Point2D nearest(Point2D p) {
        double shortestDistance = Double.POSITIVE_INFINITY;

        // if (p == null) throw new IllegalArgumentException();
        // if (p == null) throw new IllegalArgumentException();
        // double xmin = p.x() - 0.05;
        // double xmax = p.x() + 0.05;
        // double ymin = p.y() - 0.05;
        // double ymax = p.y() + 0.05;
        // RectHV rangeForNeighbours = new RectHV(xmin, ymin, xmax, ymax);
        // Iterable<Point2D> neighbours = range(rangeForNeighbours);
        // double distance = rangeForNeighbours.distanceTo(p);
        // for (Point2D point : neighbours) {
        //     if (point.distanceTo(p) == distance) return point;
        // }
        // for (Point2D point : kdTree) {
        //     if (point.distanceTo(p) < shortestDistance){
        //         nearestPoint = point;
        //         shortestDistance = point.distanceTo(p);
        //     }
        // }

        return p;
    }

    private int compare(double k, double nk) {
        if (k > nk) return 1;
        if (k == nk) return 0;
        else return -1;
    }


    // unit testing of the methods (optional)
    public static void main(String[] args) {
        // Point2D pointTest = new Point2D(1.00, 1.0);
        // Point2D pointTest2 = new Point2D(1.0, 0.0);
        // Point2D pointTest3 = new Point2D(1.0, 0.0);
        // Point2D pointTest4 = new Point2D(1.0, 0.0);
        // KdTree kd = new KdTree();
        // // System.out.println("new KdTree");
        // // System.out.println("rozmiar drzewa" + kd.size);
        // kd.insert(pointTest);
        // // System.out.println("rozmiar drzewa" + kd.size);
        // kd.insert(pointTest2);
        // // System.out.println("rozmiar drzewa" + kd.size);
        // kd.insert(pointTest3);
        // // System.out.println("rozmiar drzewa" + kd.size);
        // // System.out.println("duplicate number " + duplicateNumber);
        // kd.insert(pointTest4);
        // // System.out.println("duplicate number " + duplicateNumber);
        // // System.out.println("rozmiar drzewa" + kd.size);
        // // System.out.println("size " + size());
        // // System.out.println("Root " + root);
    }
}

