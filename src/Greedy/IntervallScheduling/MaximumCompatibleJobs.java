package Greedy.IntervallScheduling;

import java.util.*;

public class MaximumCompatibleJobs {

    // ONE RESOURCE, Max compatible job (non overlapping).

    /**
     *  You should implement this method.
     *  @param n the number of speakers
     *  @param presenterNames the names of the presenters p_1 through p_n. Note you should only use entries presenterNames[1] up to and including presenterNames[n].
     *  @param startTimes the start times of the presentations s_1 through s_n. Note you should only use entries startTimes[1] up to and including startTimes[n].
     *  @param endTimes the end times of the presentations e_1 through e_n. Note you should only use entries endTimes[1] up to and including endTimes[n].
     *  @return a largest possible set of presenters whose presentation we can attend.
     */
    public static Set<String> whatPresentations(int n, String[] presenterNames, int[] startTimes, int[] endTimes) {
        ArrayList<Job> jobs = new ArrayList<>();
        for(int i = 1; i <= n; i++) jobs.add(new Job(presenterNames[i], startTimes[i], endTimes[i]));

        Collections.sort(jobs, Comparator.comparingInt(j -> j.end));

        Set<String> names = new HashSet<>();

        int start = 0;
        for(int i = 0; i < jobs.size(); i++) {
            if (jobs.get(i).start >= start) {
                names.add(jobs.get(i).name);
                start = jobs.get(i).end;
            }
        }

        return names;

    }

    static class Job {
        String name;
        int start;
        int end;

        public Job(String n, int s, int e) {
            name = n;
            start = s;
            end = e;
        }
    }
}




