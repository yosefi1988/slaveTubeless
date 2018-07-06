package ir.sajjadyosefi.tubeless.radyab.slavetubeless.classes.model.basic;

/**
 * Created by sajjad on 3/1/2017.
 */
public class ServerResponse extends BasicObject {
    public ServerStatus serverStatus;

    public ServerStatus getServerStatus() {
        return serverStatus;
    }

    public void setServerStatus(ServerStatus serverStatus) {
        this.serverStatus = serverStatus;
    }
}
