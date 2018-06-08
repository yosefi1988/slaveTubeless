package ir.sajjadyosefi.tubeless.radyab.slavetubeless.classes.model.pushNotification;

/**
 * Created by sajjad on 5/7/2018.
 */

public class PushObject {

    public PushDataString data ;
    public String to ;

    public PushObject() {
        data = new PushDataString();
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }


    public PushDataString getData() {
        return data;
    }

    public void setData(PushDataString data) {
        this.data = data;
    }


}
