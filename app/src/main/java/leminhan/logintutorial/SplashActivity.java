package leminhan.logintutorial;


import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

public class SplashActivity extends AppCompatActivity {
    private TextView txt_by;
    private Typeface tf_extralight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);
        txt_by = (TextView) findViewById(R.id.tv_by_splash);
        tf_extralight = Typeface.createFromAsset(getAssets(),
                "source-sans-pro.extralight.ttf");
        txt_by.setTypeface(tf_extralight);
        Thread timer=new Thread()
        {
            public void run() {
                try {
                    sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                finally
                {
                    Intent i=new Intent(SplashActivity.this,MainActivity.class);
                    finish();
                    startActivity(i);
                }
            }
        };
        timer.start();
    }

}
