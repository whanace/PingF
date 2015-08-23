package xyz.pingtest.pingf;

import android.app.ActivityManager;
import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.android.gms.gcm.GoogleCloudMessaging;

import java.util.List;

/**
 * Created by S on 2015-08-23.
 */
public class GCMIntentService extends IntentService{

    private static final String TAG = "GCMIntentService";
    public static final int NOTIFICATION_ID = 1;

    public GCMIntentService(){
        super(TAG);
        Log.d(TAG, "GCMIntentService() called.");
    }

    @Override
    protected void onHandleIntent(Intent intent) {

        Log.d(TAG, "onHandleIntent() called.");
        Bundle extras = intent.getExtras();
        GoogleCloudMessaging gcm = GoogleCloudMessaging.getInstance(this);
        // The getMessageType() intent parameter must be the intent you received
        // in your BroadcastReceiver.
        String messageType = gcm.getMessageType(intent);

        if (!extras.isEmpty())
        { // has effect of unparcelling Bundle
         /*
          * Filter messages based on message type. Since it is likely that GCM
          * will be extended in the future with new message types, just ignore
          * any message types you're not interested in, or that you don't
          * recognize.
          */
            if (GoogleCloudMessaging.MESSAGE_TYPE_SEND_ERROR.equals(messageType))
            {
                sendNotification(null,"Send error: " + extras.toString());
            }
            else if (GoogleCloudMessaging.MESSAGE_TYPE_DELETED.equals(messageType))
            {
                sendNotification(null,"Deleted messages on server: " + extras.toString());
                // If it's a regular GCM message, do some work.
            }
            else if (GoogleCloudMessaging.MESSAGE_TYPE_MESSAGE.equals(messageType))
            {
                String msg = intent.getStringExtra("msg");
                String from = intent.getStringExtra("from");
                // Post notification of received message.
//            sendNotification("Received: " + extras.toString());
                sendNotification(from, msg);
                Log.d(TAG, "Received: " + extras.toString());

            }
        }

        // Release the wake lock provided by the WakefulBroadcastReceiver.
        GCMBroadcastReceiver.completeWakefulIntent(intent);

    }

    // Put the message into a notification and post it.
    // This is just one simple example of what you might choose to do with
    // a GCM message.
    private void sendNotification(String from, String msg)
    {
        Log.d(TAG, "sendNotification() called.");
        NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        Intent intent = new Intent(getApplicationContext(), SampleMain.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        intent.putExtra("from", from);
        intent.putExtra("msg", msg);

        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        //push 알림 부분
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this).setSmallIcon(R.drawable.user_default)//앱 아이콘
                .setContentTitle("GCM Notification")//앱 타이틀
                .setStyle(new NotificationCompat.BigTextStyle().bigText(msg))// 문구 및 글자크기
                .setAutoCancel(true)
                .setVibrate(new long[]{0, 500});//진동
        //.setContentText(msg)
        mBuilder.setContentIntent(contentIntent);
        mNotificationManager.notify(NOTIFICATION_ID, mBuilder.build());

        if(isRunningProcess(this))
        {
            startActivity(intent);
        }

    }

    /**
     * Process가 실행중인지 여부 확인.
     * @param context, packageName
     * @return true/false
     */
    public static boolean isRunningProcess(Context context)
    {
        Log.d(TAG, "isRuningProcess() called.");
        String packageName = "xyz.pingtest.pingf";

        ActivityManager am = (ActivityManager)context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> Info = am.getRunningTasks(1);
        ComponentName topActivity = Info.get(0).topActivity;
        String topactivityname = topActivity.getPackageName();
        Log.i(TAG, topactivityname);
        if(topactivityname.equals(packageName)) {
            Log.d(TAG, "isRunningProcess = TRUE");
            return true;
        }

        Log.d(TAG, "isRunningProcess = FALSE");
        return false;
    }
}
