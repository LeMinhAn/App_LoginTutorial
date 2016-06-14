package leminhan.logintutorial;


import android.content.Intent;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v4.view.ViewCompat;
import android.view.View;

import leminhan.logintutorial.model.Thing;

public class Navigator {

    public static int ANIM_DURATION = 350;

    public static void launchDetail(MenuActivity fromActivity, View fromView, Thing item, View backgroundView) {
        ViewCompat.setTransitionName(fromView, "detail_element");
        ActivityOptionsCompat options =TransitionHelper.makeOptionsCompat(
                        fromActivity,
                        Pair.create(fromView, "detail_element")

                );
        Intent intent = new Intent(fromActivity, DetailActivity.class);
        intent.putExtra("item_text", item.text);

        if (backgroundView != null) BitmapUtil.storeBitmapInIntent(BitmapUtil.createBitmap(backgroundView), intent);

        ActivityCompat.startActivity(fromActivity, intent, options.toBundle());

        fromActivity.overridePendingTransition(R.anim.slide_up, R.anim.scale_down);
    }

}
