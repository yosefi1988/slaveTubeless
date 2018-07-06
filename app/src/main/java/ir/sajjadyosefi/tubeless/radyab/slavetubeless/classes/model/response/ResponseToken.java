package ir.sajjadyosefi.tubeless.radyab.slavetubeless.classes.model.response;

import ir.sajjadyosefi.tubeless.radyab.slavetubeless.classes.model.basic.ServerResponse;
import ir.sajjadyosefi.tubeless.radyab.slavetubeless.classes.model.basic.ServerStatus;

/**
 * Created by sajjad on 5/16/2018.
 */

public class ResponseToken extends ServerResponse {
    public String slavePushNotificationToken;
    public int Type;



    public String getSlavePushNotificationToken() {
        return slavePushNotificationToken;
    }

    public void setSlavePushNotificationToken(String slavePushNotificationToken) {
        this.slavePushNotificationToken = slavePushNotificationToken;
    }

    public ResponseToken( ) {
        this.setServerStatus(new ServerStatus());
    }

    public int getType() {
        return Type;
    }

    public void setType(int type) {
        Type = type;
    }
}
