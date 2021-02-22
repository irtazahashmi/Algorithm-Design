package NF;

public class SurveyDesignKExams {

    // n exam questions
    // each exam question i tests one of the m main learning objectives denoted by a pair (i, j)
    // the professor would like to generate the exams for the upcoming K exam opportunities
    // automatically by selecting different SUBSETS of the exam questions from the past

    //meet two extra criteria:
    // 1) each question can be used at most 2 times in all k exams together
    // 2) for each exam, each main learning objective must be tested by at least 3 questions


    // METHOD:
    // 1. for each exam k′, for each main learning objective j generate a node (j,k′) and connect
    // this to t with lower bound 3

    // 2. for each exam question i generate a node i and connect this from s with capacity 2, and
    // connect it to all nodes (j,k′) with capacity 1 if (i,j) was given.

    // 3. connect t to s with infinite capacity


    // for k = 2:
    // source  ->  questions ->  ObjectivesExam1 -> ObjectivesExam2 -> t

//  1. get rid of the lower bound by adding a demand of 3 to all nodes (j,k′)
//  and the total supply 3 · k · m to t, create a new source s′ and connect this to t
//  with capacity equal to the supply of t and create a new sink t′ which is connected
//  from all nodes (j, k) with capacity 3

//  2. If the maximum flow from s′ to t′ in this network (computed with FF) is equal to 3*k*m,
//  the professor can generate all k exams.
}
