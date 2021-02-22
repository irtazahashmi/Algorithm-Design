package DivideAndConquer;

public class Majority {
    /**
     * You should implement this method.
     * @param n the number of patients
     * @param patients the patients at indices 1 through n, note that you should ignore index 0!
     * @return true iff more than n/2 of the patients have the same illnesses.
     */
    public static boolean patientCare(int n, Patient[] patients) {
        if (patients == null) return false;
        Patient majority =  patientCare(patients, 1, patients.length - 1);
        if (majority != null) return true;
        else return false;
    }

    public static Patient patientCare(Patient[] patients, int start, int end) {
        if (end - start == 0) return patients[start];
        if (end - start == 1) {
            if (patients[start].hasSameIllnesses(patients[end])) return patients[start];
        }

        int mid = (end + start) / 2;

        Patient m = patientCare(patients, start, mid);

        if (m != null) {
            int c = 0;
            for(int i = start; i <= end; i++) {
                if (m.hasSameIllnesses(patients[i])) c++;
            }
            if (c > (end - start + 1) / 2) return m; // if c > n/2 elements
            else return null;
        }

        Patient q = patientCare(patients, mid + 1, end);
        if (q != null) {
            int c = 0;
            for(int i = start; i <= end; i++) {
                if (q.hasSameIllnesses(patients[i])) c++;
            }
            if (c > (end - start + 1) / 2) return q;
            else return null;
        }

        return null;
    }
}

interface Patient {
    boolean hasSameIllnesses(Patient other);
}
