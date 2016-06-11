package leminhan.logintutorial;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import leminhan.logintutorial.flowingdrawer_core.MenuFragment;


public class MyMenuFragment extends MenuFragment {

    private ImageView ivMenuUserProfilePhoto;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_menu, container,
                false);
        View header = ((NavigationView) view.findViewById(R.id.vNavigation)).getHeaderView(0);
        ivMenuUserProfilePhoto = (ImageView) header.findViewById(R.id.ivMenuUserProfilePhoto);
        setupHeader();
        return setupReveal(view);
    }

    private void setupHeader() {
        int avatarSize = getResources().getDimensionPixelSize(R.dimen.global_menu_avatar_size);
        String profilePhoto = getResources().getString(R.string.user_profile_photo);

        Picasso.with(getActivity())
                .load(profilePhoto)
                .placeholder(R.drawable.img_circle_placeholder)
                .resize(avatarSize, avatarSize)
                .centerCrop()
                .error(android.R.drawable.btn_star)
                .transform(new CircleTransformation())
                .into(ivMenuUserProfilePhoto);

    }
}
