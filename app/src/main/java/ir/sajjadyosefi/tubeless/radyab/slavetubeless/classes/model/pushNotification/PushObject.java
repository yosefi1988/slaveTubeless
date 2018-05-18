package ir.sajjadyosefi.tubeless.radyab.slavetubeless.classes.model.pushNotification;

/**
 * Created by sajjad on 5/7/2018.
 */

public class PushObject {

    public PushData data ;
    public String to ;

    public PushObject() {
        data = new PushData();
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }


    public PushData getData() {
        return data;
    }

    public void setData(PushData data) {
        this.data = data;
    }


}
