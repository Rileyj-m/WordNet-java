/******************************************************************************
 *  Name: Riley Marsden     
 *
 *  Hours to complete assignment (optional): 15 hours
 *
 ******************************************************************************/

Programming Assignment 3: WordNet


/******************************************************************************
 *  Describe concisely the data structure(s) you used to store the 
 *  information in synsets.txt. Why did you make this choice?
 *****************************************************************************/
The data stucture that I used was a Seperate Chaining Hash symbol table. The symbol table was used because
it was easy to access the information after it was stored. Considering that it 
stores the data in the form of a tree, and the isNoun method needed to be logarithmic.
This was the clear choice considering that a .contains call would be logarithmic in the
average case. Not only that, but I'm not just using .contains in the isNoun class. If we are doing
this with a lot of data it's important to make sure the data structure is efficient. That's Why
I chose the symbol table. One thing that is important to consider is that I used two different 
ST for the nouns, and the ID's that are associated with the nouns and the root noun of the
synset. I could have used several different ways to store the data in the NounST, but I chose 
to use a bag to handle the duplicate values. Finally, with the ID's and root nouns stored in the
synsetST, I can then use the size of that to create the Digraph.


/******************************************************************************
 *  Describe concisely the data structure(s) you used to store the 
 *  information in hypernyms.txt. Why did you make this choice?
 *****************************************************************************/
The hypernyms.txt file information was stored in the digraph. The digraph was used because
that was the most efficient and logical way to create the relationship between the words effectively.
It was also a consideration because of how ShortestCommonAncestor was implemented.
Think of the hypernyms as the edges, and the synsets as the vertices.


/******************************************************************************
 *  Describe concisely your algorithm to compute the shortest common
 *  ancestor in ShortestCommonAncestor. For each method in the API, what
 *  is the order of growth of the worst-case running time as a function
 *  of the number of vertices V and the number of edges E in the digraph?
 *  For each method, what is the order of growth of the best-case running time?
 *
 *  If you use hashing, you may assume the uniform hashing assumption
 *  so that put() and get() take constant time.
 *
 *  Be careful! If you use something like a BreadthFirstDirectedPaths object, 
 *  don't forget to count the time needed to initialize the marked[],
 *  edgeTo[], and distTo[] arrays.
 *****************************************************************************/

Description:
It takes in two words and returns the shortest common ancestor of the two words.
it does this by first running BFS on the first word, and then running BFS on the second word. By doing this, it will find
the shortest path to the digraph vertex. Once it has computed such a path for both words, I then traversed the vertices in the
digraph and checked if both of the words had a path to that vertex. If they did, and the length of both of the paths was the 
shortest length to an ancestor, then that vertex is the shortest common ancestor. So I set the length to a holding variable, and update
the holding variable for the ancestor. If, in the future, they both have a path that is less than the holding lengh variable, then 
I update the holding variable to that new shortest length, and update the vertex that both words had the path to. In the end, Once
everything is traversed. I return the ancestor variable because it will be the shortest common ancestor.



                                              running time
method                               best case            worst case
------------------------------------------------------------------------
length(int v, int w)                  O(E + V)             O(E + V)

ancestor(int v, int w)                O(E + V)             O(E + V)

length(Iterable<Integer> v,
       Iterable<Integer> w)           O(E + V)             O(E + V)

ancestor(Iterable<Integer> v,
         Iterable<Integer> w)         O(E + V)             O(E + V)

/******************************************************************************
 *  Known bugs / limitations.
 *****************************************************************************/
As far as I know, there are no known bugs.

/******************************************************************************
 *  Describe whatever help (if any) that you received.
 *  Don't include readings, lectures, but do include any 
 *  help from people (including course staff, TAs,
 *  classmates, and friends) and attribute them by name.
 *****************************************************************************/
Derrick and I talked about some things concerning ShortestCommonAncestor and wordnet, we 
discussed the best way to go about it and how we could implement them.

I also talked to one of my bosses Alex about Wordnet Specifically, in regards to the way
we are getting the information from the file. So he helped me with that as well.

That is all the help that I recieived. 

/******************************************************************************
 *  Describe any serious problems you encountered.                    
 *****************************************************************************/
Honestly, the hardest part that I encountered was the unit testing for wordnet.
I really hard a hard time figuring out how to test the methods considering
that the files are so large. In the end I just tested the values that would
throw exceptions in the methods to make sure that everything was working as it should.
Then I just ran outcast and made changes as needed. By setting breakpoints and 
walking through the code.


/******************************************************************************
 *  List any other comments here. Feel free to provide any feedback   
 *  on how much you learned from doing the assignment, and whether    
 *  you enjoyed doing it.                                             
 *****************************************************************************/

For me, this was the most challenging assignement this year. It was really fun though
and i've learned so much. This assignment pushed me to learn more about the data structures we were taught in class,
and explore how they work. Overall, really fun and challenging. 