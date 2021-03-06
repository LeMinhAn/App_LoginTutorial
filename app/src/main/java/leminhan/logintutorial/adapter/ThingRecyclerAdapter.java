package leminhan.logintutorial.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import leminhan.logintutorial.R;
import leminhan.logintutorial.model.Thing;
import butterknife.InjectView;

public class ThingRecyclerAdapter extends BaseRecyclerAdapter<Thing> {

    @Override
    public ThingHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        return new ThingHolder(view);
    }

    public class ThingHolder extends BaseRecyclerAdapter<Thing>.ViewHolder {
        @InjectView(R.id.title)
        TextView titleTextView;

        public ThingHolder(View itemView) {
            super(itemView);
        }

        public void populate(Thing item) {
            titleTextView.setText(item.text);
        }
    }
}
