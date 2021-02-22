package DP;

public class Problems {
	//1)
	// Saving and Checking account
	// If amount v not in your account -> cost = v * c
	// Can't have negative balance in checking account (currently 0)
	// Given expense pattern (t1, v1)...(tn, vn) -> from ti days from now, 
	// spend vi which will be removed from checking account
	// saving to checking account transfer -> cost = C

	//You want to find out how you can optimally transfer money from your 
	//savings account to your checking account, such that you have sufficient 
	//balance on your checking account for all the given expenses vi at time ti.

    // OPT(i) = min(      sum (tk - tj) * vk * c + C + OPT(j-1))
    //          1<=j<=i      j<=k<=i  

		//int[] mem = new int[n+1]
		//mem[0] = 0
   		// for i <- 1...n
			//int min <- Integer.MAX_VALUE
			//for j <- 1...i
				//int sum <- 0
				//for k = j... i
					// sum += (tk - tj) * vk * c

				//min = Math.min(sum + C + mem(j-1))

			//mem[i] = min
		// return mem[n]



	//2)
	//Install new coffee machines
	//Employees in the new building drink a total of C cups a day
	//Facility management still has a collection of n old coffee machines.
	//Known to produce ci cups of coffee on a given day and Σ (i = 1...n) ci > C
	//University does not install machines that together can produce more cups of 
	//coffee than is consumed (C in our case).

	//Determine the maximum number of cups of coffee a selection of the available machines 
	//can produce without going over the total consumption C

	//Call l(n, C) with l(i, j) 
		//1) if i = 0, l[i][j] = C - j
		//2) if ci >= j, l[i][j] = l[i-1][j]
		//3) max(l(i-1)(j), l(i-1)(j-ci))



    //3)
    //We’re looking at the price of a given stock over n consecutive days, numbered i = 1, 2,...n
    // For each day i, we have a price p(i) per share for the stock on that day.
    // When to buy stock (day i) and and a later day (j > i) when to sell it to maximize profit

    // mem[0] = 0
    //for i <- 1... n
        // mem[i] = max(0, (p(i) - p(i-1)) + mem[i-1])

    //return max of mem[n] where n = 1...n


//    int n = prices.length;
//    int[] dp = new int[n];
//    dp[n-1] = 0;
//        for (int i = n-1; i > 0; i--){
//        dp[i-1] = Math.max(prices[i] - prices[i -1], 0) + dp[i];
//    }
//        return dp[0];



    //4)
    //Given the data on robot arrivals x1...xn, and given the recharging function f(·), choose the
    //points in time at which you’re going to activate the EMP so as to destroy as many robots as possible.

    //mem[0] = 0;
    //mem[j] = max(mem[i] + min(xj, f(j-i))
          // 0 <= i < j


    //5)
    // {1,2...m} historical sites
    // {1,2...n} puzzles
    // f(i, j) is time spent at puzzle i at site j
    // Spend as much time as possible

    // FORWARD FORMULATION
    // if i > n, 0 (no more puzzles left)
    // if j > m, 0 (no more historical sites left)
    // else, max(mem[i][j+1], f(i)(j) + mem[i+1][j+1]
    // return mem[1][1]

    // BACKWARD FORMULATION
    // if i = 0, 0
    // if j = 0 & i > 0, -infinity
    // else, max(mem[i-1][j], f(i)(j) + mem[i-1][j-1])
    // chose max(mem[i][m]) because we dont know are used in the end
    //          1 <= 1 <= n


    //6)
    // Given the data on number of robots x1...xn on each sec
    // given the recharging function f (·)
    //choose the points in time at which you’re going to activate the EMP so as to destroy as many robots as possible.

    // mem[0] = 0
    //for i <- 1...n
        // maxRobots
        //for j <- 0...i
            // maxRobots = max(maxRobots, min(xi, f(i-j)) + mem[j])

        // mem[i] = maxRobots

    //return mem[n]


    //7)
    //Given the amounts of available data x1..xn for the next n days
    // given the profile of your system as expressed by s1...sn, amount of data that can be processed
    // (and starting from a freshly rebooted system on day 1)
    // choose the days on which you’re going to reboot so as to maximize the total amount of data you process.

    // mem[n][j] = min(xn, sj) for j <- 1...n
    // for i <- n-1...1
        // for j <- 1...i
            // mem[i][j] = max(mem[i+1][1], min(xi, sj) + mem[i+1][j+1])

    //return mem[1][1]

}


