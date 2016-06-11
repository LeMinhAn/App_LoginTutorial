package leminhan.logintutorial;

import android.animation.Animator;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import leminhan.logintutorial.flowingdrawer_core.FlowingView;
import leminhan.logintutorial.flowingdrawer_core.LeftDrawerLayout;

public class HomeActivity extends FragmentActivity {
    private RecyclerView rvFeed;
    private LeftDrawerLayout mLeftDrawerLayout;
    private SharedPreferences sharedpreferences;
    private FloatingActionButton floatingActionButton;
    private Context mContext;
    private int selectedTheme = 0;
    private int primaryColor = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setSharePreferences();

        setContentView(R.layout.activity_home);

        mContext = this;

        setupToolbar();
        setValues();
        getAciton();
        setFragment();
        setupFeed();
    }

    private void setSharePreferences() {
        sharedpreferences = getSharedPreferences("test", Context.MODE_PRIVATE);
        setTheme(sharedpreferences.getInt("theme", R.style.AppTheme));
    }

    protected void setupToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_menu_white);
        toolbar.setTitle(R.string.app_name);
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mLeftDrawerLayout.toggle();
            }
        });
    }

    private void setFragment() {
        FragmentManager fm = getSupportFragmentManager();
        MyMenuFragment mMenuFragment = (MyMenuFragment) fm.findFragmentById(R.id.id_container_menu);
        FlowingView mFlowingView = (FlowingView) findViewById(R.id.sv);
        if (mMenuFragment == null) {
            fm.beginTransaction().add(R.id.id_container_menu, mMenuFragment = new MyMenuFragment()).commit();
        }
        mLeftDrawerLayout.setFluidView(mFlowingView);
        mLeftDrawerLayout.setMenuFragment(mMenuFragment);
    }


    private void setValues() {
        mLeftDrawerLayout = (LeftDrawerLayout) findViewById(R.id.id_drawerlayout);
        rvFeed = (RecyclerView) findViewById(R.id.rvFeed);
        floatingActionButton = (FloatingActionButton) findViewById(R.id.btn_floatingbutton);
    }

    private void getAciton() {
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                selectedTheme = R.style.theme1;
                primaryColor = getResources().getColor(R.color.color_set_1_primary);


                int[] location = new int[2];
                rvFeed.setBackgroundColor(primaryColor);
                v.getLocationOnScreen(location);

                int cx = (location[0] + (v.getWidth() / 2));
                int cy = location[1] + (GUIUtils.getStatusBarHeight(mContext) / 2);

                SharedPreferences.Editor ed = sharedpreferences.edit();
                ed.putInt("x", cx);
                ed.putInt("y", cy);
                ed.putInt("theme", selectedTheme);
                ed.apply();

                hideNavigationStatus();
                GUIUtils.showRevealEffect(rvFeed, cx, cy, revealAnimationListener);
            }
        });
    }



    private void setupFeed() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this) {
            @Override
            protected int getExtraLayoutSpace(RecyclerView.State state) {
                return 300;
            }
        };
        rvFeed.setLayoutManager(linearLayoutManager);

        FeedAdapter feedAdapter = new FeedAdapter(this);
        rvFeed.setAdapter(feedAdapter);
        feedAdapter.updateItems();
    }

    @Override
    public void onBackPressed() {
        if (mLeftDrawerLayout.isShownMenu()) {
            mLeftDrawerLayout.closeDrawer();
        } else {
            super.onBackPressed();
        }
    }

    private void hideNavigationStatus() {

        View decorView = getWindow().getDecorView();

        int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
        decorView.setSystemUiVisibility(uiOptions);
    }

    Animator.AnimatorListener revealAnimationListener = new Animator.AnimatorListener() {

        @Override
        public void onAnimationStart(Animator animation) {
        }

        @Override
        public void onAnimationEnd(Animator animation) {

            Intent i = new Intent(HomeActivity.this, HomeActivity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            startActivity(i);
            overridePendingTransition(0, 0);
            finish();
        }

        @Override
        public void onAnimationCancel(Animator animation) {
        }

        @Override
        public void onAnimationRepeat(Animator animation) {
        }
    };
}
