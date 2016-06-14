package leminhan.logintutorial;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IProfile;


public class MenuFragment extends leminhan.logintutorial.flowingdrawer_core.MenuFragment {

    private AccountHeader headerResult;
    FrameLayout flMainContainer;
    Context mContext;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.menu_fragment, container, false);
        mContext = getActivity();
        initHeaderAccount();

        flMainContainer = (FrameLayout) view.findViewById(R.id.id_menu);
        FragmentManager manager = getFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        Fragment fragment = new MenuFragment();
        //Thay đổi fragment
        transaction.replace(R.id.flMainContainer, fragment);
        transaction.commit();
        return view;
    }

    public void onOpenMenu() {
        Toast.makeText(getActivity(), "onOpenMenu", Toast.LENGTH_SHORT).show();
    }

    public void onCloseMenu() {
        Toast.makeText(getActivity(), "onCloseMenu", Toast.LENGTH_SHORT).show();
    }

    public void initHeaderAccount() {
        headerResult = new AccountHeaderBuilder()
                .withActivity(getActivity())
                //set background cho khung account
                .withHeaderBackground(R.drawable.header)
                //thêm một số trường cho khung account
                .addProfiles(
                        //Edit Profile
                        new ProfileDrawerItem().withName("Lê Minh Ân").withEmail("leminhan.cst@gmail.com").withIcon(getResources().getDrawable(R.drawable.icon_wallpaper))
                )
                //thay đổi account
                .withOnAccountHeaderListener(new AccountHeader.OnAccountHeaderListener() {
                    @Override
                    public boolean onProfileChanged(View view, IProfile profile, boolean currentProfile) {
                        return false;
                    }
                })
                .build();
    }


}
