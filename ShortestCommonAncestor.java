import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.DirectedCycle;
import edu.princeton.cs.algs4.BreadthFirstDirectedPaths;
import edu.princeton.cs.algs4.Bag;

public class ShortestCommonAncestor {

    /// <summary>
    /// the digraph
    /// </summary>
    private Digraph G;

    /// <summary>
    /// the BSF
    /// </summary>
    private BreadthFirstDirectedPaths bfsV, bfsW;

    /// <summary>
    /// the constructor takes a rooted DAG as argument
    /// </summary>
    public ShortestCommonAncestor(Digraph G) {
        if (G == null) {
            throw new NullPointerException("G is null");
        }

        // need to check if the digraph is a rooted DAG
        DirectedCycle finder = new DirectedCycle(G);
        if (finder.hasCycle()) {
            throw new IllegalArgumentException("G is not a rooted DAG");
        }

        int count = 0;
        int hold = 0;
        for (int i = 0; i < G.V(); i++) {
            if (G.outdegree(i) == 0) {
                count++;
            }
            else {
                hold++;
            }
        }
        if (count != 1 || hold != G.V() - 1) {
            throw new IllegalArgumentException("G is not a rooted DAG");
        }

        this.G = G;
    }

    /// <summary>
    /// the length of shortest ancestral path between v and w; -1 if no such path
    /// </summary>
    /// <param name="v"> the first vertex </param>
    /// <param name="w"> the second vertex </param>
    /// <returns> the length of shortest ancestral path between v and w; -1 if no such path </returns>
    public int length(int v, int w) {
        // throw index out of bounds exception
        if (v < 0 || v >= G.V()) {
            throw new IndexOutOfBoundsException("v is out of bounds");
        }
        if (w < 0 || w >= G.V()) {
            throw new IndexOutOfBoundsException("w is out of bounds");
        }
        if (String.valueOf(v) == null || String.valueOf(w) == null) {
            throw new NullPointerException("v or w is null");
        }
            bfsV = new BreadthFirstDirectedPaths(G, v);
            bfsW = new BreadthFirstDirectedPaths(G, w);
            
            int length = Integer.MAX_VALUE;
            for (int i = 0; i < G.V(); i++) {
                if (bfsV.hasPathTo(i) && bfsW.hasPathTo(i)) {

                    int hold = bfsV.distTo(i) + bfsW.distTo(i);

                    if (hold < length || length == Integer.MAX_VALUE) {
                        length = hold;
                    }
                }
        }
        return length;

    }

    /// <summary>
    /// a shortest common ancestor of vertices v and w; -1 if no such path
    /// </summary>
    /// <param name="v"> the first vertex </param>
    /// <param name="w"> the second vertex </param>
    /// <returns> a shortest common ancestor of vertices v and w; -1 if no such path </returns>
    public int ancestor(int v, int w) {
        if (v < 0 || v >= G.V()) {
            throw new IndexOutOfBoundsException("v is out of bounds");
        }
        if (w < 0 || w >= G.V()) {
            throw new IndexOutOfBoundsException("w is out of bounds");
        }
        if (String.valueOf(v) == null || String.valueOf(w) == null) {
            throw new NullPointerException("v or w is null");
        }
        if (v == w) {
            return v;
        }
        // SeparateChainingHashST<Integer, Integer> vST = new SeparateChainingHashST<Integer, Integer>();
        // SeparateChainingHashST<Integer, Integer> wST = new SeparateChainingHashST<Integer, Integer>();
        // LinkedQueue<Integer> vQ = new LinkedQueue<Integer>();
        // LinkedQueue<Integer> wQ = new LinkedQueue<Integer>();
        // vST.put(v, 0);
        // wST.put(w, 0);
        // vQ.enqueue(v);
        // wQ.enqueue(w);

        // while (!vQ.isEmpty()) {
        //     int vParent = vQ.dequeue();
        //     Iterator<Integer> vIt = G.adj(vParent).iterator();
        //     while (vIt.hasNext()) {
        //         int vChild = vIt.next();
        //         if (!vST.contains(vChild)) {
        //             vST.put(vChild, vST.get(vParent) + 1);
        //             vQ.enqueue(vChild);
        //         }
        //     }
        // }

        // while (!wQ.isEmpty()) {
        //     int wParent = wQ.dequeue();
        //     Iterator<Integer> wIt = G.adj(wParent).iterator();
        //     while (wIt.hasNext()) {
        //         int wChild = wIt.next();
        //         if (!wST.contains(wChild)) {
        //             wST.put(wChild, wST.get(wParent) + 1);
        //             wQ.enqueue(wChild);
        //         }
        //     }
        // }

        bfsV = new BreadthFirstDirectedPaths(G, v);
        bfsW = new BreadthFirstDirectedPaths(G, w);

        int length = Integer.MAX_VALUE;
        int ancestor = -1;
        for (int i = 0; i < G.V(); i++) {
            if (bfsV.hasPathTo(i) && bfsW.hasPathTo(i)) {

                int hold = bfsV.distTo(i) + bfsW.distTo(i);

                if (length == Integer.MAX_VALUE || ancestor == -1) {
                    length = hold;
                    ancestor = i;
                }
                else if (hold < length) {
                    length = hold;
                    ancestor = i;
                }
            }
        }
        return ancestor;
    }


    /// <summary>
    /// all shortest paths from v
    /// </summary>
    /// <param name="subsetA"> the subset of vertices </param>
    /// <param name="subsetB"> the subset of vertices </param>
    /// <returns> the length of all shortest paths from v between the two subsets</returns>
    public int length(Iterable<Integer> subsetA, Iterable<Integer> subsetB) {
        if (subsetA == null || subsetB == null) {
            throw new NullPointerException("One or both of the subsets is null");
        }
        if (!subsetA.iterator().hasNext() || !subsetB.iterator().hasNext()) {
            throw new IllegalArgumentException("One or both of the subsets is empty");
        }

        // int length = Integer.MAX_VALUE;
        // int vFromSubsetA = -1;
        // int wFromSubsetB = -1;
        // int ancestor = -1;
        // for (int i : subsetA) {
        //     if (i < 0 || i >= G.V()) {
        //         throw new IndexOutOfBoundsException("i is out of bounds");
        //     }
        //     for (int j : subsetB) {
        //         if (j < 0 || j >= G.V()) {
        //             throw new IndexOutOfBoundsException("j is out of bounds");
        //         }
        //         int newLength = length(i, j);
        //         if (newLength < length) {
        //             length = newLength;
        //             ancestor = ancestor(i, j);
        //             vFromSubsetA = i;
        //             wFromSubsetB = j;
        //         }

        //     }
        // }

        // SeparateChainingHashST<Integer, Integer> vST = new SeparateChainingHashST<Integer, Integer>();
        // SeparateChainingHashST<Integer, Integer> wST = new SeparateChainingHashST<Integer, Integer>();

        // return vST.get(ancestor) + wST.get(ancestor);

        bfsV = new BreadthFirstDirectedPaths(G, subsetA);
        bfsW = new BreadthFirstDirectedPaths(G, subsetB);

        int length = Integer.MAX_VALUE;

        for (int i = 0; i < G.V(); i++) {
            if (bfsV.hasPathTo(i) && bfsW.hasPathTo(i)) {
                int hold = bfsV.distTo(i) + bfsW.distTo(i);
                if (hold < length || length == Integer.MAX_VALUE) {
                    length = hold;
                }
            }
        }
        return length;
    }

    /// <summary>
    /// all shortest paths from v, and find the shortest ancestor from the data.
    /// </summary>
    /// <param name="subsetA"> the subset of vertices </param>
    /// <param name="subsetB"> the subset of vertices </param>
    /// <returns> the ancestor of all shortest paths from v between the two subsets</returns>
    public int ancestor(Iterable<Integer> subsetA, Iterable<Integer> subsetB) {
        if (subsetA == null || subsetB == null) {
            throw new NullPointerException("One or both of the subsets is null");
        }
        if (!subsetA.iterator().hasNext() || !subsetB.iterator().hasNext()) {
            throw new IllegalArgumentException("One or both of the subsets is empty");
        }
        bfsV = new BreadthFirstDirectedPaths(G, subsetA);
        bfsW = new BreadthFirstDirectedPaths(G, subsetB);

        int length = Integer.MAX_VALUE;
        int ancestor = -1;
        for (int i = 0; i < G.V(); i++) {
            if (bfsV.hasPathTo(i) && bfsW.hasPathTo(i)) {
                int hold = bfsV.distTo(i) + bfsW.distTo(i);
                if (ancestor == -1 || length == Integer.MAX_VALUE) {
                    length = hold;
                    ancestor = i;
                }
                else if (hold < length) {
                    length = hold;
                    ancestor = i;
                }
            }
        }
        return ancestor;
    }

    // do unit testing of this class
    public static void main(String[] args) {

        // Build unit tests
        if (args.length < 1) {
            manualUnitTest();
        }
        else {
            In in = new In(args[0]);
            Digraph G = new Digraph(in);
            ShortestCommonAncestor sca = new ShortestCommonAncestor(G);
            while (!StdIn.isEmpty()) {
                int v = StdIn.readInt();
                int w = StdIn.readInt();
                int length = sca.length(v, w);
                int ancestor = sca.ancestor(v, w);
                StdOut.printf("length = %d, ancestor = %d\n", length, ancestor);
            }
        }
    }

    // Unit test made by me
    public static void manualUnitTest() {
        // Basic tree test
        int numVertices = 12;// or whatever
        Digraph d1 = new Digraph(numVertices);
        //d1.addEdge(1, 0); // add a bunch of these, to form some tree-like shape, e.g.:
        /*
         *             0
         *          /      \
         *         1        2
         *        / \      / \
         *       3   4    5
         */
        d1.addEdge(6, 3);
        d1.addEdge(7, 3);
        d1.addEdge(3, 1);
        d1.addEdge(4, 1);
        d1.addEdge(5, 1);
        d1.addEdge(8, 5);
        d1.addEdge(9, 5);
        d1.addEdge(10, 9);
        d1.addEdge(11, 9);
        d1.addEdge(1, 0);
        d1.addEdge(2, 0);

        ShortestCommonAncestor sca = new ShortestCommonAncestor(d1);
        int w = 11; // fixme
        int x = 3; // fixme
        int y = 10; // fixme
        int z = 7; // fixme

        StdOut.println("Testing Case: 1");
        StdOut.println("length: " + sca.length(x, y));
        StdOut.println("ancestor: " + sca.ancestor(x, y));


        // testing sets with some iterable type
        // ({1,2},{3,4})
        Bag<Integer> b1 = new Bag<Integer>();
        Bag<Integer> b2 = new Bag<Integer>();

        b1.add(x);
        b1.add(y);
        b2.add(w);
        b2.add(z);

        StdOut.println("Testing Case: 2");
        StdOut.println("length: " + sca.length(b1, b2));
        StdOut.println("ancestor: " + sca.ancestor(b1, b2));

        StdOut.println("Testing Case: 3");
        StdOut.println("length: " + sca.length(x, y));
        StdOut.println("ancestor: " + sca.ancestor(x, y));
        StdOut.println("length: " + sca.length(b1, b2));
        StdOut.println("ancestor: " + sca.ancestor(b1, b2));
        StdOut.println("length: " + sca.length(x, z));
        StdOut.println("ancestor: " + sca.ancestor(x, z));

        Bag<Integer> b3 = new Bag<Integer>();
        Bag<Integer> b4 = new Bag<Integer>();
        b3.add(1);
        b3.add(2);
        b3.add(3);
        b4.add(4);
        b4.add(5);

        StdOut.println("Testing Case: 4");
        StdOut.println("length: " + sca.length(b3, b4));
        StdOut.println("ancestor: " + sca.ancestor(b3, b4));
    }
}