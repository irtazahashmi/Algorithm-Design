package DP.ShortestPath;

public class AsynchronousShortestPath {

    //O(nm)

// Asynchronous-Shortest-Path(G,s,t)
// 	for all nodes v ∈ V do d[v] := ∞;
// 		d[t] := 0;
// 		activeList.insert(t);
// 		while activeList ≠ ∅ do
// 			remove any node w from activeList;
// 			for all edges (v,w) ∈ E do
// 				if cvw + d[w] < d[v] then
// 					d[v] := cvw + d[w];
// 					successor[v] := w;
// 					activeList.insert(v);

}


