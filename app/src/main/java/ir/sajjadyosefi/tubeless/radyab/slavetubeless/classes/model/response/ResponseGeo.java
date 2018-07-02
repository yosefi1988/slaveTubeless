package ir.sajjadyosefi.tubeless.radyab.slavetubeless.classes.model.response;

/**
 * Created by sajjad on 5/16/2018.
 */

public class ResponseGeo extends ServerResponse {

    public String geo;
    public int Type;


    public String getGeo() {
        return geo;
    }

    public void setGeo(String geo) {
        this.geo = geo;
    }


    public ResponseGeo( ) {
        this.setServerStatus(new ServerStatus());
    }

    public int getType() {
        return Type;
    }

    public void setType(int type) {
        Type = type;
    }
}
