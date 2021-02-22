package NF;

public class ProjectSelectionOldNewServer {

    // After a new server has been installed, it appears that one application (indicated by number 1)
    // only runs on the old server. For each application i ∈ {2, 3, 4, . . . , n} we have some expected
    // benefit bi (due to the faster speed of the new server) of moving the application to the new server,
    // and for each pair of applications i and j we have some cost xij (due to the interface between the
    // old and the new server) of having i on the new server and j on the old server.


    //Describe how to use the algorithm of Ford-Fulkerson to decide which applications to move to the new
    // server such that the profit is optimized.


    //Let each application i be represented by a node i.

    //Additionally, define a source s. We will use node 1 as our sink, forcing it to stay (in B).

    // connect source to each application with capacity bi
    // connect application to sink(1) with capacity xi1

    // Define for each i an edge (s, i) with capacity bi, representing the cost of not moving if i ∈ B.

    // Define for each i and j two edges, (i,j) and (j,i) with capacities xij and xji, representing the cost of i and
    // j not being on the same system.

    // NetProfit = Total benefit - max flow
}
