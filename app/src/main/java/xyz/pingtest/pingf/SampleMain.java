package xyz.pingtest.pingf;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import android.content.ClipData;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gms.gcm.GoogleCloudMessaging;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class SampleMain extends ActionBarActivity {

    public static final String GOOGLE_API_KEY = "AIzaSyDc3C1S72toBdiilZpRGsj5qspGeEk0qVo";
    public static final String PROJECT_ID = "179399445542";
    public static final String PROPERTY_REG_ID = "registration_id";
    private static final String PROPERTY_APP_VERSION = "appVersion";
    private final static int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
    public static final String TAG = "GCMActivity";
    //서버주소
    final static String REG_URL = "http://www.pingserver.xyz/setgcm";
    final static String SENDM_URL = "http://www.pingserver.xyz/send";
    //개인 아이디
    final static String MY_ID = "vu2";
    final static String MY_PW = "1111";

    GoogleCloudMessaging gcm;
    Handler handler = new Handler();
    String mGcmid;

    Intent setintent;
    int deviceHeight;
    int deviceWidth;
    int requestCode = 10;
    private LinearLayout layout11;
    private LinearLayout layout12;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sample_main);
        //디바이스 사이즈를 구합시다.
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        deviceWidth = metrics.widthPixels;
        deviceHeight = metrics.heightPixels;
        //레이아웃 사이즈 동적 변경을 위한 레이아웃 불러옴

        LinearLayout layout_friendslistpage1, layout_friendslistpage1_1, layout_friendslistpage1_2, layout_friendslistpage1_3;
        //매핑해주고

        layout_friendslistpage1 = (LinearLayout) findViewById(R.id.layout_friendslistpage);
        layout_friendslistpage1_1 = (LinearLayout) findViewById(R.id.layout_friendslistpage_1);
        layout_friendslistpage1_2 = (LinearLayout) findViewById(R.id.layout_friendslistpage_2);
        layout_friendslistpage1_3 = (LinearLayout) findViewById(R.id.layout_friendslistpage_3);

        layout_friendslistpage1_1.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, deviceHeight / 6 + 30));
        layout_friendslistpage1_2.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, deviceHeight / 6 + 30));
        layout_friendslistpage1_3.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, deviceHeight / 6 + 30));
        System.out.println("deviceWidth---------" + deviceWidth);
        layout11 = (LinearLayout)findViewById(R.id.layout11);
        layout12 = (LinearLayout)findViewById(R.id.layout12);

        layout11.setOnDragListener(new mDragListener());
        layout12.setOnDragListener(new mDragListener());

        Button icon1 = (Button) findViewById(R.id.icon20);
        Button icon2 = (Button) findViewById(R.id.icon21);
        Button icon3 = (Button) findViewById(R.id.icon22);
        Button icon4 = (Button) findViewById(R.id.icon23);
        Button icon5 = (Button) findViewById(R.id.icon24);
        Button icon6 = (Button) findViewById(R.id.icon25);
        Button icon7 = (Button) findViewById(R.id.icon26);
        Button icon8 = (Button) findViewById(R.id.icon27);

        Button btn = (Button) findViewById(R.id.UserPlus);
        btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "aa", Toast.LENGTH_SHORT).show();

        /*      LinearLayout rel = (LinearLayout) View.inflate(
                        SampleMain.this, R.layout.layout, null);
                //생성 규칙
                LinearLayout linear = (LinearLayout) findViewById(R.id.layout_friendslistpage_2);
                if (linear.getChildCount() < 3) {
                    linear.addView(rel);
                } else if (linear.getChildCount() < 6) {
                    linear = (LinearLayout) findViewById(R.id.layout_friendslistpage_3);
                    linear.addView(rel);
                }
        */    }
        });

        /*icon1.setOnTouchListener(mOnTouchListener);
        icon2.setOnTouchListener(mOnTouchListener);
        icon3.setOnTouchListener(mOnTouchListener);
        icon4.setOnTouchListener(mOnTouchListener);
        icon5.setOnTouchListener(mOnTouchListener);
        icon6.setOnTouchListener(mOnTouchListener);
        icon7.setOnTouchListener(mOnTouchListener);
        icon8.setOnTouchListener(mOnTouchListener);
        */
        icon1.setOnLongClickListener(mOnLongClickListener); //터치이벤트는 스크롤을 움직일수 없어서 롱클릭으로 바꿈
        icon2.setOnLongClickListener(mOnLongClickListener);
        icon3.setOnLongClickListener(mOnLongClickListener);
        icon4.setOnLongClickListener(mOnLongClickListener);
        icon5.setOnLongClickListener(mOnLongClickListener);
        icon6.setOnLongClickListener(mOnLongClickListener);
        icon7.setOnLongClickListener(mOnLongClickListener);
        icon8.setOnLongClickListener(mOnLongClickListener);


        /*GCM 부분 */

        //폰에 저장되어있는 GCM_ID를 불러옴
        mGcmid = getRegistrationId(getApplicationContext());
        if(mGcmid.isEmpty()) {
            println("등록이 필요 합니다.");
        } else
        {
            println("이미 등록 되어 있습니다..");
        }



        // 인텐트를 전달받는 경우
        Intent intent = getIntent();
        if (intent != null) {
            Log.i(TAG, "getIntent()");
            processIntent(intent);
        }
    }

    View.OnLongClickListener mOnLongClickListener = new View.OnLongClickListener() {
        @Override
        public boolean onLongClick(View v) {
            View icon = findViewById(v.getId()); // 뷰의 이미지를 동적으로 가져오는 것.
            String name = v.toString();//View id data test
            Toast.makeText(getApplicationContext(),v.getId()+"----", Toast.LENGTH_SHORT).show();
            ClipData data = ClipData.newPlainText("setting", "1");
            switch((int)v.getAlpha()) {
                case 1:
                    data = ClipData.newPlainText("setting", "a");
                    break;
                case 2:
                    data = ClipData.newPlainText("setting", "b");
                    break;
                case 3:
                    data = ClipData.newPlainText("setting", "c");
                    break;
                case 4:
                    data = ClipData.newPlainText("setting", "d");
                    break;
                case 5:
                    data = ClipData.newPlainText("setting", "e");
                    break;
            }

            View.DragShadowBuilder shadow = new View.DragShadowBuilder(icon);
            v.startDrag(data, shadow, null, 0);
            return false;
        }
    };
    View.OnTouchListener mOnTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View p_v, MotionEvent p_event) {
            switch (p_event.getAction()) {
                case MotionEvent.ACTION_DOWN: { // 놓았을때
                   /*
                   String id = String.valueOf(p_v.getId());//View id data test
                   Toast.makeText(getApplicationContext(), id, Toast.LENGTH_SHORT).show();
                   */
                    View icon = findViewById(p_v.getId());
                    String name = p_v.toString();//View id data test
                    Toast.makeText(getApplicationContext(), " " + name, Toast.LENGTH_SHORT).show();
                    ClipData data = ClipData.newPlainText("sssss", "aaaa");
                    View.DragShadowBuilder shadow = new View.DragShadowBuilder(icon);
                    p_v.startDrag(data, shadow, null, 0);
                }

                case MotionEvent.ACTION_MOVE: {
                    break;
                }
            }
            return true;
        }
    };

    class LinearInflate {


    }
    class mDragListener implements View.OnDragListener {
        @Override
        public boolean onDrag(View v, DragEvent event) {
            final int action = event.getAction();
            switch (action) {
                case DragEvent.ACTION_DRAG_STARTED:
                    return true;
                case DragEvent.ACTION_DRAG_EXITED:
                    break;
                case DragEvent.ACTION_DRAG_ENTERED:
                    return true;
                case DragEvent.ACTION_DROP: {
                    ClipData dragData = event.getClipData();
                    final String tag = dragData.getItemAt(0).getText().toString();
<<<<<<< HEAD
                    /*switch(tag) {
                        case a:
                            break;
                        case b:
                            break;
                    }*/
                    if (v == findViewById(R.id.layout11))
=======

                    if (v == findViewById(R.id.layout11)) {
>>>>>>> 37254cdc9f3e5f2b125930d4c4b67d2c51d058b7
                        Toast.makeText(getApplicationContext(), "targetLayout: " + v.getTag() +
                                " dragged :" + tag, Toast.LENGTH_SHORT).show();
                        sendMessage(tag);
                    }
                    else if (v == findViewById(R.id.layout12))
                        Toast.makeText(getApplicationContext(), "targetLayout: " + v.getTag() +
                                " dragged :" + tag, Toast.LENGTH_SHORT).show();
                    return true;
                }
                case DragEvent.ACTION_DRAG_ENDED: {
                    // drag가 끝났을때
                    return (true);
                }
                default:
                    break;
            }
            return true;
        }
    }


    //Toast 뿌리기
    private void println(String msg) {
        final String output = msg;
        handler.post(new Runnable() {
            public void run() {
                Log.d(TAG, output);
                Toast.makeText(getApplicationContext(), output, Toast.LENGTH_LONG).show();
            }
        });
    }

    //폰에 저장되어 있는 GCM_ID 가져온다. 없으면 ""반환
    private String getRegistrationId(Context context){
        final SharedPreferences prefs = getGCMPreferences(context);
        Log.i(TAG, "REG_ID : " + PROPERTY_REG_ID);
        String registrationId = prefs.getString(PROPERTY_REG_ID,"");
        Log.i(TAG, "R : " + registrationId);
        if(registrationId.isEmpty()){
            Log.i(TAG,"Registration not found");
            return "";
        }

        int registeredVersion = prefs.getInt(PROPERTY_APP_VERSION, Integer.MIN_VALUE);
        int currentVersion = getAppVersion(context);
        if(registeredVersion != currentVersion){
            Log.i(TAG, "App version changed");
            return "";
        }

        return registrationId;
    }
    private SharedPreferences getGCMPreferences(Context context)
    {
        return getSharedPreferences(SampleMain.class.getSimpleName(), Context.MODE_PRIVATE);
    }
    /**
     * @return {@code PackageManager}의 애플리케이션의 버전 코드.
     */
    //앱 버전을 받아온다
    private static int getAppVersion(Context context) {
        try {
            PackageInfo packageInfo = context.getPackageManager()
                    .getPackageInfo(context.getPackageName(), 0);
            return packageInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            // 일어나지 않아야 합니다
            throw new RuntimeException("Could not get package name: " + e);
        }
    }

    //구글에 단말 등록
    private void registerInBackground(){
        new AsyncTask<Void, Void ,Void>(){
            @Override
            protected Void doInBackground(Void... params) {

                try{
                    if(gcm == null){
                        gcm = GoogleCloudMessaging.getInstance(getApplicationContext());
                    }
                    mGcmid = gcm.register(PROJECT_ID);
                    println("푸시 서비스를 위해 단말을 등록했습니다.");
                    println("등록 ID : " + mGcmid);

                    //서버로 보내기
                    sendRegistrationIdToBackend();

                    //id 보관
                    storeRegistrationId(getApplicationContext(), mGcmid);

                }catch (IOException e){ e.printStackTrace();};
                return null;
            }
        }.execute(null,null,null);
    }

    //자체 서버로 전송(Http get방식)
    private void sendRegistrationIdToBackend(){
        try {
            String parameter = "?" + URLEncoder.encode("user_id", "UTF-8") + "=" + URLEncoder.encode(MY_ID, "UTF-8") + "&"
                    + URLEncoder.encode("user_pw", "UTF-8") + "=" + URLEncoder.encode(MY_PW, "UTF-8") + "&"
                    + URLEncoder.encode("user_gcm", "UTF-8") + "=" + URLEncoder.encode(mGcmid, "UTF-8");

            URL url = new URL(REG_URL + parameter);
            Log.i(TAG, url+"");

            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setConnectTimeout(3000);
            con.setReadTimeout(3000);

            con.connect();

            //con.getResponseCode() 해야 실행이 되는거 같음
            if (con.getResponseCode() == HttpURLConnection.HTTP_OK) {
                Log.i(TAG, "Register OK");
            }
        }
        catch (Exception e){ e.printStackTrace();}
    }

    //앱 내부에 저장
    private void storeRegistrationId(Context context, String regId){
        final SharedPreferences prefs = getGCMPreferences(context);
        int appVersion = getAppVersion(context);
        Log.i(TAG, "Saving regId on app version" + appVersion);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(PROPERTY_REG_ID, regId);
        editor.putInt(PROPERTY_APP_VERSION, appVersion);
        editor.commit();
    }

    /**
     * 수신자로부터 전달받은 Intent 처리
     *
     * @param intent
     */
    private void processIntent(Intent intent) {
        String from = intent.getStringExtra("from");
        if (from == null) {
            Log.d(TAG, "from is null.");
            return;
        }
        String recvData = intent.getStringExtra("msg");
        String[] splitData = recvData.split("/");
        String fromId = splitData[0];
        String msg = splitData[1];

        println("[" + fromId + "]로부터 수신한 데이터 : " + msg);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        Log.d(TAG, "onNewIntent() called.");

        processIntent(intent);
        super.onNewIntent(intent);
    }

    //매시지 보내기
    private void sendMessage(String msg){
        new AsyncTask<String, Void, Void>(){

            @Override
            protected Void doInBackground(String... params) {

                try{
                    String m="";//받는사람
                    //어차피 한개 들어오지만.. ASYNC를 쓰기위해..
                    for(String s : params)
                        m = s;


                    String parameter = "?" + URLEncoder.encode("id_from","UTF-8") + "=" + URLEncoder.encode(MY_ID, "UTF-8") +
                            "&" + URLEncoder.encode("id_to", "UTF-8") + "=" + URLEncoder.encode("g3","UTF-8") +
                            "&" + URLEncoder.encode("msg", "UTF-8") + "=" + URLEncoder.encode(m,"UTF-8") +
                            "&" + URLEncoder.encode("user_pw", "UTF-8") + "=" + URLEncoder.encode(MY_PW,"UTF-8");

                    URL url = new URL(SENDM_URL + parameter);
                    Log.i(TAG, "URL=" + url);

                    HttpURLConnection con =(HttpURLConnection) url.openConnection();
                    con.setConnectTimeout(3000);
                    con.setReadTimeout(3000);

                    con.connect();

                    if(con.getResponseCode() == HttpURLConnection.HTTP_OK)
                    {
                        Log.i(TAG, "Send Message OK");
                    }

                } catch (Exception e){e.printStackTrace();}


                return null;
            }
        }.execute(msg);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_sample_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            registerInBackground();//구글에 폰 등록 후 GCM_ID받아옴
            /*
            if(mGcmid.isEmpty()) {

            } else
            {
                println("이미 등록 되어 있습니다..");
            }
            */
            return true;
        }

        if (id == R.id.AddFriends) {
            setintent = new Intent(getApplication(), AddFriend.class);
            startActivityForResult(setintent, requestCode);
        }

        return super.onOptionsItemSelected(item);
    }
}
