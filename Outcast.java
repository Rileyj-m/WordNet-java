import java.io.IOException;

import edu.princeton.cs.algs4.In;

public class Outcast {

    /// <summary>
    /// The WordNet object.
    /// </summary>
    WordNet wordnet;

    /// <summary>
    /// constructor takes a WordNet object
    /// </summary>
    /// <param name="wordnet"></param>
    public Outcast(WordNet wordnet) {
        this.wordnet = wordnet;
    }

    /// <summary>
    /// given an array of WordNet nouns, return an outcast
    /// </summary>
    /// <param name="nouns">The nouns that we are searching for an outcast with</param>
    /// <returns>The outcast noun from the array of nouns</returns>
    public String outcast(String[] nouns) {
        int max = 0;
        int outcast_id = 0;
        for (int i = 0; i < nouns.length; i++) {
            int distance = 0;
            for (int j = 0; j < nouns.length; j++) {
                distance += wordnet.distance(nouns[i], nouns[j]);
            }
            if (distance > max || outcast_id == 0) {
                max = distance;
                outcast_id = i;
            }
        }

        return nouns[outcast_id];
    }

    // Unit Test client
    public static void main(String[] args) throws IOException { //throw because WordNet throws
        WordNet wordnet = new WordNet(args[0], args[1]);
        Outcast outcast = new Outcast(wordnet);
        for (int t = 2; t < args.length; t++) {
            In in = new In(args[t]);
            String[] nouns = in.readAllStrings();
            StdOut.println(args[t] + ": " + outcast.outcast(nouns));
        }
    }
}