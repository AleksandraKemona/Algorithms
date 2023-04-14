// package kdtree;


import edu.princeton.cs.algs4.BST;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.SET;

// public class KdTree <Key extends Comparable<Key>, Value>{
public class KdTree {
    // private TreeSet<Point2D> kdTree;
    private Point2D nearestPoint;
    private Node root;

    private KdTree kdt;

    private int size = 0;


    // private BST bst = new BST<>();


    // construct an empty set of points
    public KdTree() {
    }



    private class Node {
        private double[] point;           // sorted by key
        private Node left, right;  // left and right subtrees


        public Node(double[] p) {
            this.point = p;
            this.left = null;
            this.right = null;
        }

        public void insertNode(double[] p) {
            insertIntoTree(root, p, 0);
        }

        public Node insertIntoTree(Node root, double[] p, int depth) {
            if (root == null) root = new Node(p);

            int coordinate = depth % 2;
            if (p[coordinate] > root.point[coordinate]) {
                root.right = insertIntoTree(root.right, p, depth + 1);
            } else if (p[coordinate] < root.point[coordinate]) {
                root.left = insertIntoTree(root.left, p, depth + 1);
            }
            size = size + 1;
            return root;
        }

    }

        // is the set empty?
        public boolean isEmpty() {
            return (size() != 0);
        }

        public int size() {
            return size;
        }

        // number of points in the set
        private int size(KdTree.Node x) {
            if (x == null) return 0;
            else return 1;
        }

        // add the point to the set (if it is not already in the set)
        public void insert(Point2D point) {
            double[] insertedPoint = new double[2];
            insertedPoint[0] = point.x();
            insertedPoint[1] = point.y();

            root.insertNode(insertedPoint);

        }

        // does the set contain point p?
        public boolean contains(Point2D p) {
            if (p == null) throw new IllegalArgumentException();
            // return kdTree.contains(p);
            return true;
        }

        // draw all points to standard draw
        public void draw() {
            while (root.left != null || root.left != null){

            }
            Point2D root2D = new Point2D(root.point[0], root.point[1]);
            root2D.draw();
            if (root.left != null){

            }
        }

        // all points that are inside the rectangle (or on the boundary)
        public Iterable<Point2D> range(RectHV rect) {
            if (rect == null) throw new IllegalArgumentException();
            SET<Point2D> inRangeSet = new SET<>();

            // for (Point2D point : kdTree) {
            //     if (rect.contains(point)){
            //         inRangeSet.add(point);
            //     }
            // }
            return inRangeSet;
        }

        // a nearest neighbor in the set to point p; null if the set is empty
        public Point2D nearest(Point2D p) {
            double shortestDistance = Double.POSITIVE_INFINITY;
            ;
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

        // private KdTree.Node put(KdTree.Node x, double key, double val) {
        //     if (x == null) return new KdTree.Node(key, val, 1);
        //     int cmp = compare(key, x.key);
        //     if (cmp < 0) x.left = put(x.left, key, val);
        //     else if (cmp > 0) x.right = put(x.right, key, val);
        //     else x.val = val;
        //     x.size = 1 + size(x.left) + size(x.right);
        //     return x;
        // }

        private int compare(double k, double nk) {
            if (k > nk) return 1;
            if (k == nk) return 0;
            else return -1;
        }


        // unit testing of the methods (optional)
        public static void main(String[] args) {

        }
    }

