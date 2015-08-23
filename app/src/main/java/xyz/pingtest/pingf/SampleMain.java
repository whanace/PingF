package xyz.pingtest.pingf;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
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

public class SampleMain extends ActionBarActivity {
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
    }

    View.OnLongClickListener mOnLongClickListener = new View.OnLongClickListener() {
        @Override
        public boolean onLongClick(View v) {
            View icon = findViewById(v.getId()); // 뷰의 이미지를 동적으로 가져오는 것.
            String name = v.toString();//View id data test
            Toast.makeText(getApplicationContext(), " " + name, Toast.LENGTH_SHORT).show();
            ClipData data = ClipData.newPlainText("sssss", "aaaa");
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

                    if (v == findViewById(R.id.layout11))
                        Toast.makeText(getApplicationContext(), "targetLayout: " + v.getTag() +
                                " dragged :" + tag, Toast.LENGTH_SHORT).show();
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
            return true;
        }

        if (id == R.id.AddFriends) {
            setintent = new Intent(getApplication(), AddFriend.class);
            startActivityForResult(setintent, requestCode);
        }

        return super.onOptionsItemSelected(item);
    }
}
