package ir.sajjadyosefi.tubeless.radyab.slavetubeless.classes.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;

/**
 * Created by sajjad on 5/10/2018.
 */

@DatabaseTable(tableName = "setting")
public class Setting extends BasicObject  implements Serializable {
    private static final long serialVersionUID = -222864131214757024L;


    @DatabaseField(id = true,columnName = "id")
    public int id;

    @DatabaseField(columnName = "masterPushNotificationToken")
    public String masterPushNotificationToken;
    @DatabaseField
    public String masterPhoneNamber;
    @DatabaseField
    public boolean isMasterTokenValid;

    @DatabaseField
    public String slavePushNotificationToken;
    @DatabaseField
    public String slavePhoneNumber;
    @DatabaseField
    public boolean isSlaveTokenValid;

    public Setting() { }

    public String getMasterPushNotificationToken() {
        return masterPushNotificationToken;
    }

    public void setMasterPushNotificationToken(String masterPushNotificationToken) {
        this.masterPushNotificationToken = masterPushNotificationToken;
    }

    public String getMasterPhoneNamber() {
        return masterPhoneNamber;
    }

    public void setMasterPhoneNamber(String masterPhoneNamber) {
        this.masterPhoneNamber = masterPhoneNamber;
    }

    public boolean isMasterTokenValid() {
        return isMasterTokenValid;
    }

    public void setMasterTokenValid(boolean masterTokenValid) {
        isMasterTokenValid = masterTokenValid;
    }

    public String getSlavePushNotificationToken() {
        return slavePushNotificationToken;
    }

    public void setSlavePushNotificationToken(String slavePushNotificationToken) {
        this.slavePushNotificationToken = slavePushNotificationToken;
    }

    public String getSlavePhoneNumber() {
        return slavePhoneNumber;
    }

    public void setSlavePhoneNumber(String slavePhoneNumber) {
        this.slavePhoneNumber = slavePhoneNumber;
    }

    public boolean isSlaveTokenValid() {
        return isSlaveTokenValid;
    }

    public void setSlaveTokenValid(boolean slaveTokenValid) {
        isSlaveTokenValid = slaveTokenValid;
    }

}
