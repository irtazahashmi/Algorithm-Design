package NF;

public class ProjectSelectionTrains {

    // n tracks, exactly one day to cross
    // every track 1...n the government has requested at least ci- and at most ci+ trains.
    // every track i only certain train types j are suitable (dont know how many?)
    // For every train type 1...k we know the number of trains of that type the NS has available: tj

    // Do we have sufficient trains?

//   1. Create a source s with demand = -sum of ci- (requested trains)
//   2. Create a sink t with demand = sum of ci- (requested trains).
//   2. Create a node for every track ri and every train type xj.
//   3. Add an edge from s to every ri with lower bound ci- and capacity ci+ .
//   4. Add an edge from every xj to t with capacity = tj.
//   5. Add an edge from every ri to every xj that is suitable for with capacity ∞


// Find the max flow, return true iff v(f) = sum of ci-
}
