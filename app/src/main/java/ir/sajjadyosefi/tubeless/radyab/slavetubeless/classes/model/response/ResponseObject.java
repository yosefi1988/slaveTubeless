package ir.sajjadyosefi.tubeless.radyab.slavetubeless.classes.model.response;

import ir.sajjadyosefi.tubeless.radyab.slavetubeless.classes.model.basic.ServerResponse;

/**
 * Created by sajjad on 5/16/2018.
 */

public class ResponseObject extends ServerResponse {

    public String object;
    public int Type;

    public String getObject() {
        return object;
    }

    public void setObject(String object) {
        this.object = object;
    }

    public int getType() {
        return Type;
    }

    public void setType(int type) {
        Type = type;
    }
}
