import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import edu.princeton.cs.algs4.ST;
import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.Bag;


public class WordNet {

    /// <summary>
    /// ST for the nouns
    /// </summary>
    private ST<String, Bag<Integer>> nounST;

    /// <summary>
    /// ST for the sysnsetIDs
    /// </summary>
    private ST<Integer, String> synsetST;

    /// <summary>
    /// The digraph
    /// </summary>
    private Digraph G;

    /// <summary>
    /// The shortest common ancestor class
    /// </summary>
    private ShortestCommonAncestor sca;

    /// <summary>
    /// The constructor
    /// </summary>
    /// <param name="synsets">The synsets file</param>
    /// <param name="hypernyms">The hypernyms file</param>
    public WordNet(String synsets, String hypernyms) throws
                                                     IOException /* "throw" required for FileReader*/ {
        this.nounST = new ST<>();
        this.synsetST = new ST<>();
        if (synsets == null || hypernyms == null) {
            throw new IllegalArgumentException("null argument");
        }
        // Read in all synsets (and do something with them)
        BufferedReader input = new BufferedReader(new FileReader(synsets));
        String line = input.readLine();
        while (line != null) {
            String parts[] = line.split(",");
            int synId = Integer.parseInt(parts[0]);
            String synStr = parts[1];
            String[] synset = synStr.split(" ");

            //notice: the definitions are in parts[2];  we're ignoring those
            for (String s : synset) {
                Bag<Integer> bag = new Bag<Integer>();
                if (nounST.contains(s)) {
                    bag = nounST.get(s);
                }
                bag.add(synId);
                nounST.put(s, bag);
            }
            synsetST.put(synId, synStr);

            // Read next line from file and ..
            line = input.readLine();
        }
        input.close();
        G = new Digraph(synsetST.size());

        // Read in all hypernyms with some similar code
        input = new BufferedReader(new FileReader(hypernyms));
        line = input.readLine();
        while (line != null) {
            String parts[] = line.split(",");
            int synId = Integer.parseInt(parts[0]);
            for (int i = 0; i < parts.length; i++) {
                if (i == 0) {
                    continue;
                }
                G.addEdge(synId, Integer.parseInt(parts[i]));
            }
            line = input.readLine();
        }
        input.close();

        sca = new ShortestCommonAncestor(G);

    }

    /// <summary>
    /// Get's all the WordNet nouns
    /// </summary>
    public Iterable<String> nouns() {
        if (nounST == null) {
            throw new IllegalArgumentException("null argument");
        }
        return nounST;
    }

    /// <summary>
    /// checks if the noun is a WordNet noun
    /// </summary>
    /// <param name="">The noun to check</param>
    public boolean isNoun(String word) {
        if (word == null) {
            throw new IllegalArgumentException("null argument");
        }
        return nounST.contains(word);
    }

    /// <summary>
    /// a synset (second field of synsets.txt) that is a shortest common ancestor
    /// of noun1 and noun2 (defined below)
    /// </summary>
    /// <param name="noun1">The first noun</param>
    /// <param name="noun2">The second noun</param>
    /// <returns>The synset</returns>
    public String sca(String noun1, String noun2) {
        if (noun1 == null || noun2 == null) {
            throw new NullPointerException("null argument");
        }
        if (!isNoun(noun1) || !isNoun(noun2)) {
            throw new IllegalArgumentException("not a noun");
        }

        Bag<Integer> synId1 = nounST.get(noun1);
        Bag<Integer> synId2 = nounST.get(noun2);

        return synsetST.get(sca.ancestor(synId1, synId2));
    }

    /// <summary>
    /// distance between noun1 and noun2 (defined below)
    /// </summary>
    /// <param name="noun1">The first noun</param>
    /// <param name="noun2">The second noun</param>
    /// <returns>The distance</returns>
    public int distance(String noun1, String noun2) {
        if (noun1 == null || noun2 == null) {
            throw new NullPointerException("null argument");
        }
        if (!isNoun(noun1) || !isNoun(noun2)) {
            throw new IllegalArgumentException("not a noun");
        }

        Bag<Integer> synId1 = nounST.get(noun1);
        Bag<Integer> synId2 = nounST.get(noun2);

        int length = sca.length(synId1, synId2);

        return length;
    }


    // do unit testing of this class
    public static void main(String[] args) throws
                                           IOException { //"throw" because the constructor throws.
        WordNet wnet = new WordNet("synsets.txt", "hypernyms.txt");
        
        // pull horse from synsets.txt
        String noun = "horse";
        String noun2 = "bear";
        //String nullnoun = null;

        // check if a noun is a noun
        System.out.println("Is this noun indeed a wordnet noun? " + wnet.isNoun(noun));
        System.out.println("Is this noun2 indeed a wordnet noun? " + wnet.isNoun(noun2));

        // get the sca of two nouns
        System.out.println("The sca of " + noun + " and " + noun2 + " is " + wnet.sca(noun, noun2));

        // get the distance between two nouns

        System.out.println("The distance between " + noun + " and " + noun2 + " is " + wnet.distance(noun, noun2));

        // testing every case that it should throw an exception

        //String nonnoun = "asadfads";
        //String nonnoun2 = "asaffffdfdf";

        // try {
        //     wnet.sca(nonnoun, nonnoun2);
        // } catch (IllegalArgumentException e) {
        //     System.out.println("The sca of " + nonnoun + " and " + nonnoun2 + " is " + e.getMessage());
        // }

        // try {
        //     wnet.distance(nonnoun, nonnoun2);
        // } catch (IllegalArgumentException e) {
        //     System.out.println("The distance between " + nonnoun + " and " + nonnoun2 + " is " + e.getMessage());
        // }

        // try {
        //     wnet.isNoun(nullnoun);
        // } catch (IllegalArgumentException e) {
        //     System.out.println("The isNoun of " + nullnoun + " is " + e.getMessage());
        // }

        // try {
        //     wnet.distance(noun, nullnoun);
        // } catch (IllegalArgumentException e) {
        //     System.out.println("The distance between " + noun + " and " + nullnoun + " is " + e.getMessage());
        // }

        // try {
        //     wnet.sca(nullnoun, nullnoun);
        // } catch (IllegalArgumentException e) {
        //     System.out.println("The sca of " + nullnoun + " and " + noun + " is " + e.getMessage());
        // }

        // try {
        //     wnet.distance(nullnoun, nullnoun);
        // } catch (IllegalArgumentException e) {
        //     System.out.println("The distance between " + nullnoun + " and " + noun + " is " + e.getMessage());
        // }

        System.out.println("is apple a noun? " + wnet.isNoun("peach") + "\n");
        System.out.println("shortest Common Ancestor: " + wnet.sca("table", "table") + "\n");
        System.out.println("apple and orange distance: " + wnet.distance("peach", "orange") + "\n");

        System.out.println("is apple a noun? " + wnet.isNoun("apple") + "\n");

        System.out.println("shortest Common Ancestor: " + wnet.sca("apple", "orange") + "\n");
        System.out.println("apple and orange distance: " + wnet.distance("apple", "orange") + "\n");

        System.out.println("is apple a noun? " + wnet.isNoun("bear") + "\n");

        System.out.println("shortest Common Ancestor: " + wnet.sca("bear", "horse") + "\n");
        System.out.println("apple and orange distance: " + wnet.distance("bear", "horse") + "\n");

        System.out.println("is apple a noun? " + wnet.isNoun("horse") + "\n");

        System.out.println("shortest Common Ancestor: " + wnet.sca("horse", "bear") + "\n");
    }
}