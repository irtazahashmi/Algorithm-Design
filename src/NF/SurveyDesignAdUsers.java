package NF;

public class SurveyDesignAdUsers {

    // k distinct demographic groups G1, G2, . . . , Gk.
    // Such groups may overlap

    //The web portal has contracted with m different advertisers.
    // 1- Advertiser i only wants to show its ads to a subset Xi ⊆ {G1 , . . . , Gk } of the demographic groups
    // 2- Each minute, advertiser i wants its ads shown to at least ri users.

    //for each 1 ≤ i ≤ m, can at least ri of the n users, belonging to at least one group in Xi,
    // be shown an advertisement provided by advertiser i, whereby only one ad is shown to each user?

//    Introduce a node j for every user 1...n.
//    Introduce a node i for every advertiser 1 . . . m.
//    Connect s to each user node j with capacity 1.
//    Connect each advertiser node i to t with lower bound ri.

//    Connect each user node j to an advertiser node i if j is in a group i is interested in
//    (or alternatively, introduce group nodes, and connect each user to the groups it belongs t
//    o with capacity 1, and each group to the interested advertisers with capacity ∞).

//    Connect t to s with lower bound 􏰂i ri and capacity ∞.

}
