package ir.sajjadyosefi.tubeless.radyab.slavetubeless.classes.model.push;

import java.io.Serializable;
import java.util.List;

/**
 * Created by sajjad on 5/11/2018.
 */

public class GooglePushResponse implements Serializable {
    public float multicast_id;
    public int success;
    public int failure;
    public int canonical_ids;
    public List<Results> results;

    public List<Results> getResults() {
        return results;
    }

    public void setResults(List<Results> results) {
        this.results = results;
    }

    public float getMulticast_id() {
        return multicast_id;
    }

    public void setMulticast_id(float multicast_id) {
        this.multicast_id = multicast_id;
    }

    public int getSuccess() {
        return success;
    }

    public void setSuccess(int success) {
        this.success = success;
    }

    public int getFailure() {
        return failure;
    }

    public void setFailure(int failure) {
        this.failure = failure;
    }

    public int getCanonical_ids() {
        return canonical_ids;
    }

    public void setCanonical_ids(int canonical_ids) {
        this.canonical_ids = canonical_ids;
    }



    public class Results {
        public String error ;

        public String getError() {
            return error;
        }

        public void setError(String error) {
            this.error = error;
        }
    }
}
