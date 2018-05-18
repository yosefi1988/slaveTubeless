package ir.sajjadyosefi.tubeless.radyab.slavetubeless.classes.model.response;

import java.io.Serializable;

import ir.sajjadyosefi.tubeless.radyab.slavetubeless.classes.model.basic.BasicObject;

/**
 * Created by sajjad on 2/27/2017.
 */
public class ServerStatus extends BasicObject {

    int Code ;
    String Message ;

    public int getCode() {
        return Code;
    }

    public void setCode(int code) {
        Code = code;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

}
