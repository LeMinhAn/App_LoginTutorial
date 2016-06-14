package leminhan.logintutorial.fragment;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.balysv.materialmenu.MaterialMenuDrawable;

import java.util.ArrayList;
import java.util.List;

import leminhan.logintutorial.MenuActivity;
import leminhan.logintutorial.Navigator;
import leminhan.logintutorial.R;
import leminhan.logintutorial.adapter.BaseRecyclerAdapter;
import leminhan.logintutorial.adapter.ThingRecyclerAdapter;
import leminhan.logintutorial.model.Thing;


public class TroChoiFragment extends Fragment {
    View view;
    RecyclerView recyclerView;
    ThingRecyclerAdapter recyclerAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_trochoi, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler);
        initRecyclerView();
        return view;
    }

    private void initRecyclerView() {
        recyclerAdapter = new ThingRecyclerAdapter();
        recyclerAdapter.updateList(getThings());
        recyclerAdapter.setOnItemClickListener(new BaseRecyclerAdapter.OnItemClickListener<Thing>() {
            @Override
            public void onItemClick(View view, Thing item, boolean isLongClick) {
                if (isLongClick) {
                    MenuActivity.of(getActivity()).animateHomeIcon(MaterialMenuDrawable.IconState.X);
                } else {
                    Navigator.launchDetail(MenuActivity.of(getActivity()), view, item, recyclerView);
                }
            }
        });

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(recyclerAdapter);



        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(recyclerAdapter);
    }


    public static List<Thing> getThings() {
        ArrayList<Thing> list = new ArrayList<>();
        for (int l = 0; l < 100; l++) {
            list.add(new Thing("Thing " + l, null));
        }
        return list;
    }
}


