package leminhan.logintutorial.fragment;

import android.animation.Animator;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.widget.AbsListView;

import com.github.ksoichiro.android.observablescrollview.ObservableScrollView;
import com.github.ksoichiro.android.observablescrollview.ObservableScrollViewCallbacks;
import com.github.ksoichiro.android.observablescrollview.ScrollState;
import com.nineoldandroids.view.ViewHelper;

import leminhan.logintutorial.FeedAdapter;
import leminhan.logintutorial.GUIUtils;
import leminhan.logintutorial.MenuActivity;
import leminhan.logintutorial.R;

/**
 * Created by tobrother on 04/04/2016.
 */
//Fragment của trang Setting
public class HomeFragment extends Fragment implements ObservableScrollViewCallbacks {
    // View
    View view;
    private FloatingActionButton floatingActionButton;
    private int selectedTheme = 0;
    private int primaryColor = 0;
    private RecyclerView rvFeed;
    private SharedPreferences sharedpreferences;

    // ___________LOADING_____________ //
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setSharePreferences();
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_home, container, false);
        getData();
        initView();
        initValues();
        setupFeed();
        initActions();
        return view;
    }

    private void setSharePreferences() {
        sharedpreferences = getActivity().getSharedPreferences("test", Context.MODE_PRIVATE);
        getActivity().setTheme(sharedpreferences.getInt("theme", R.style.AppTheme));
    }

    public void getData() {
    }

    public void initView() {
        // getActivity().getActionBar().show();
        getActivity().findViewById(R.id.myToolbar).animate()
                .translationY(0)
                .setInterpolator(new AccelerateInterpolator())
                .setDuration(100)
                .start();


    }

    public void initValues() {
        floatingActionButton = (FloatingActionButton) view.findViewById(R.id.btn_floatingbutton);
        rvFeed = (RecyclerView) view.findViewById(R.id.rvFeed);
        rvFeed = (RecyclerView) view.findViewById(R.id.rvFeed);

    }

    public void initActions() {
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                selectedTheme = R.style.theme1;
                primaryColor = getResources().getColor(R.color.color_set_1_primary);


                int[] location = new int[2];
                rvFeed.setBackgroundColor(primaryColor);
                v.getLocationOnScreen(location);

                int cx = (location[0] + (v.getWidth() / 2));
                int cy = location[1] + (GUIUtils.getStatusBarHeight(getActivity()) / 2);

                SharedPreferences.Editor ed = sharedpreferences.edit();
                ed.putInt("x", cx);
                ed.putInt("y", cy);
                ed.putInt("theme", selectedTheme);
                ed.apply();

                hideNavigationStatus();
                GUIUtils.showRevealEffect(rvFeed, cx, cy, revealAnimationListener);
            }
        });
        rvFeed.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if (dy > 0 || dy < 0 && floatingActionButton.isShown())
                    floatingActionButton.hide();

            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {

                if (newState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE) {
                    floatingActionButton.show();
                }
                super.onScrollStateChanged(recyclerView, newState);
            }
        });


    }

    private void setupFeed() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity()) {
            @Override
            protected int getExtraLayoutSpace(RecyclerView.State state) {
                return 300;
            }
        };
        rvFeed.setLayoutManager(linearLayoutManager);

        FeedAdapter feedAdapter = new FeedAdapter(getActivity());
        rvFeed.setAdapter(feedAdapter);
        feedAdapter.updateItems();
    }

    private void hideNavigationStatus() {

        View decorView = getActivity().getWindow().getDecorView();

        int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
        decorView.setSystemUiVisibility(uiOptions);
    }

    Animator.AnimatorListener revealAnimationListener = new Animator.AnimatorListener() {

        @Override
        public void onAnimationStart(Animator animation) {
        }

        @Override
        public void onAnimationEnd(Animator animation) {

            Intent i = new Intent(getActivity(), MenuActivity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            startActivity(i);
            getActivity().overridePendingTransition(0, 0);
            getActivity().finish();
        }

        @Override
        public void onAnimationCancel(Animator animation) {
        }

        @Override
        public void onAnimationRepeat(Animator animation) {
        }
    };

    @Override
    public void onScrollChanged(int scrollY, boolean firstScroll, boolean dragging) {
        ViewHelper.setTranslationY(rvFeed, scrollY / 2);
    }

    @Override
    public void onDownMotionEvent() {

    }

    @Override
    public void onUpOrCancelMotionEvent(ScrollState scrollState) {
        if (((AppCompatActivity) getActivity()).getSupportActionBar() == null) {
            return;
        }
        if (scrollState == ScrollState.UP) {
            if (((AppCompatActivity) getActivity()).getSupportActionBar().isShowing()) {
                getActivity().findViewById(R.id.myToolbar).animate()
                        .translationY(-getActivity().findViewById(R.id.myToolbar).getBottom())
                        .setInterpolator(new AccelerateInterpolator())
                        .start();
                ((AppCompatActivity) getActivity()).getSupportActionBar().hide();
            }
        } else if (scrollState == ScrollState.DOWN) {
            if (!((AppCompatActivity) getActivity()).getSupportActionBar().isShowing()) {
                ((AppCompatActivity) getActivity()).getSupportActionBar().show();
                getActivity().findViewById(R.id.myToolbar).animate()
                        .translationY(0)
                        .setInterpolator(new AccelerateInterpolator())
                        .start();
            }
        }
    }
}


