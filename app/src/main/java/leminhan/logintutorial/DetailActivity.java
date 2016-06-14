package leminhan.logintutorial;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.balysv.materialmenu.MaterialMenuDrawable;

public class DetailActivity extends AppCompatActivity {
    private TextView tv1, tv2;
    private OverScrollView overScrollView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        tv1 = (TextView) findViewById(R.id.detail_title);
        tv2 = (TextView) findViewById(R.id.detail_body);
        overScrollView = (OverScrollView) findViewById(R.id.overscroll_view);
        overScrollView.setOverScrollListener(new OverScrollView.OverScrollListener() {
            int translationThreshold = 100;

            @Override
            public boolean onOverScroll(int yDistance, boolean isReleased) {
                if (Math.abs(yDistance) > translationThreshold) { //passed threshold
                    if (isReleased) {
                        onBackPressed();
                        return true;
                    }
                }

                return false;
            }
        });
        String itemText = getIntent().getStringExtra("item_text");
        tv1.setText(itemText);
        initDetailBody();
    }

    private void initDetailBody() {
        tv2.setAlpha(0);
        new Handler().postDelayed(new Runnable() {
            public void run() {
                tv2.animate().alpha(1).start();
            }
        }, 500);
    }
}
