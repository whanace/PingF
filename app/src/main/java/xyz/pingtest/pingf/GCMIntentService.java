package xyz.pingtest.pingf;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

/**
 * Created by S on 2015-08-23.
 */
public class GCMIntentService extends IntentService{

    private static final String TAG = "GCMIntentService";

    public GCMIntentService(){
        super(TAG);
        Log.d(TAG, "GCMIntentService() called.");
    }

    @Override
    protected void onHandleIntent(Intent intent) {

    }
}
