package leminhan.logintutorial.fragment;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import leminhan.logintutorial.R;
import leminhan.logintutorial.ToolbarHidingOnScrollListener;


public class EbookFragment extends Fragment {
    View view;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_ebook, container, false);
        setupRecyclerView();
        return view;
    }
    private void setupRecyclerView() {
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recyclerview);

        // Let's use a grid with 2 columns.
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        populateRecyclerView(recyclerView);

        View tabBar = view.findViewById(R.id.fake_tab);
        View coloredBackgroundView = view.findViewById(R.id.colored_background_view);
        View toolbarContainer = view.findViewById(R.id.toolbar_container);
        View toolbar = view.findViewById(R.id.toolbar);

        recyclerView.setOnScrollListener(new ToolbarHidingOnScrollListener(toolbarContainer, toolbar, tabBar, coloredBackgroundView));
    }


    private void populateRecyclerView(RecyclerView recyclerView) {
        recyclerView.setAdapter(new RecyclerView.Adapter() {

            private final static int DUMMY_ITEM_COUNT = 30;

            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int position) {
                View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item, viewGroup, false);
                return new TextHolder(itemView);
            }

            @Override
            public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
                // We are too lazy for this by now ;-)
            }

            @Override
            public int getItemCount() {
                return DUMMY_ITEM_COUNT;
            }


            class TextHolder extends RecyclerView.ViewHolder {

                public TextHolder(View itemView) {
                    super(itemView);
                }
            }
        });
    }


}


