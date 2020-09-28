package mx.com.othings.edcore.Lib;

import android.app.Activity;

public class Service {

    private Activity activity;
    private SDB sdb;

    public Service( Activity activity ){
        this.activity = activity;
        sdb = new SDB(activity);
    }

    public mx.com.othings.edcore.Lib.Resources.OnlineResources Online(){
        return new mx.com.othings.edcore.Lib.Resources.OnlineResources(sdb);
    }
    public mx.com.othings.edcore.Lib.Resources.OfflineResources Offline(){
        return new mx.com.othings.edcore.Lib.Resources.OfflineResources();
    }
    public SDB sdb(){
        return new SDB(activity);
    }

}
