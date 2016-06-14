package leminhan.logintutorial;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.balysv.materialmenu.MaterialMenuDrawable;
import com.mikepenz.fontawesome_typeface_library.FontAwesome;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IProfile;

import leminhan.logintutorial.fragment.EbookFragment;
import leminhan.logintutorial.fragment.HomeFragment;
import leminhan.logintutorial.fragment.MovieFragment;
import leminhan.logintutorial.fragment.TroChoiFragment;
import leminhan.logintutorial.fragment.UngDungFragment;

public class MenuActivity extends AppCompatActivity {

    // View
    public static Toolbar myToolbar;
    private AccountHeader headerResult;
    FrameLayout flMainContainer;
    LinearLayout llActionBar;
    Drawer result;
    boolean doubleBackToExitPressedOnce;
    private SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Đọc file activity_main.xml để vẽ ra giao diện người dùng.
        setContentView(R.layout.activity_menu);
        getValues();
        initViews();
        initValues();
        initActions();
        initHeaderAccount();
        initMenuDrawer(savedInstanceState);
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        Fragment fragment = new HomeFragment();
        //Thay đổi fragment
        transaction.replace(R.id.flMainContainer, fragment);
        transaction.commit();
    }


    //__________GET VALUES___________//
    public void getValues() {

    }

    //__________INIT VIEWS___________//
    public void initViews() {

        //Tạo thanh Toolbar
        myToolbar = (Toolbar) findViewById(R.id.myToolbar);
        //Đặt thanh công cụ cho ứng dụng
        setSupportActionBar(myToolbar);
        //Tạo FrameLayout
        flMainContainer = (FrameLayout) findViewById(R.id.flMainContainer);
        //Tạo LinnearLayout chứa thanh Toolbar
        llActionBar = (LinearLayout) findViewById(R.id.llActionBar);
        //_______________________LOADING VIEW___________________________//
    }

    //__________INIT VALUES___________//
    public void initValues() {

    }

    //__________INIT ACTIONS___________//
    public void initActions() {

    }

    //__________INIT HEADER ACCOUNT___________//
    //Tạo account trong thanh menu
    public void initHeaderAccount() {
        headerResult = new AccountHeaderBuilder()
                .withActivity(this)
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

    public void initMenuDrawer(Bundle savedInstanceState) {
        result = new DrawerBuilder()
                .withActivity(this)
                .withToolbar(myToolbar)
                .withAccountHeader(headerResult) //set the AccountHeader we created earlier for the header
                .addDrawerItems(
                        //Nhóm 1
                        new PrimaryDrawerItem().withName("Trang Chủ").withIcon(FontAwesome.Icon.faw_home),
                        //Thanh ngang
                        new DividerDrawerItem(),
                        //here we use a customPrimaryDrawerItem we defined in our sample app
                        //this custom DrawerItem extends the PrimaryDrawerItem so it just overwrites some methods
                        // new CustomUrlPrimaryDrawerItem().withName(R.string.drawer_item_fragment_drawer).withDescription(R.string.drawer_item_fragment_drawer_desc).withIcon("https://avatars3.githubusercontent.com/u/1476232?v=3&s=460"),
                        //Nhóm 2
                        new SecondaryDrawerItem().withName("Ứng Dụng").withIcon(FontAwesome.Icon.faw_cubes),
                        new SecondaryDrawerItem().withName("Trò Chơi").withIcon(FontAwesome.Icon.faw_gamepad),
                        new SecondaryDrawerItem().withName("E-Book").withIcon(FontAwesome.Icon.faw_book),
                        new SecondaryDrawerItem().withName("Phim").withIcon(FontAwesome.Icon.faw_video_camera),
                        new SecondaryDrawerItem().withName("Hình Nền").withIcon(FontAwesome.Icon.faw_picture_o),
                        new SecondaryDrawerItem().withName("Nhạc Chuông").withIcon(FontAwesome.Icon.faw_music),
                        //Thanh ngang
                        new DividerDrawerItem(),
                        //Nhóm 3
                        new SecondaryDrawerItem().withName("Đánh Dấu").withIcon(FontAwesome.Icon.faw_star),
                        new SecondaryDrawerItem().withName("Lịch Sử Cài Đặt").withIcon(FontAwesome.Icon.faw_history),
                        new SecondaryDrawerItem().withName("Liên hệ").withIcon(FontAwesome.Icon.faw_phone)
                ) // add the items we want to use with our Drawer
                //Sự kiện nút home
                .withOnDrawerNavigationListener(new Drawer.OnDrawerNavigationListener() {
                    @Override
                    public boolean onNavigationClickListener(View clickedView) {
                        //this method is only called if the Arrow icon is shown. The hamburger is automatically managed by the MaterialDrawer
                        //if the back arrow is shown. close the activity
                        //return true if we have consumed the event
                        return true;
                    }
                })
                //Sự kiện đóng mở thanh menu
                .withOnDrawerListener(new Drawer.OnDrawerListener() {
                    @Override
                    public void onDrawerOpened(View drawerView) {
                        getSupportActionBar().show();
                    }

                    @Override
                    public void onDrawerClosed(View drawerView) {

                    }

                    @Override
                    public void onDrawerSlide(View drawerView, float slideOffset) {

                    }
                })
                //Thêm trường cho thanh menu
                .addStickyDrawerItems(
                        new SecondaryDrawerItem().withName("Cài Đặt").withIcon(FontAwesome.Icon.faw_cog).withIdentifier(10)
                )
                //Sự kiện click từng items cho trong thanh menu
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                        //Quản lý fragment
                        FragmentManager manager = getSupportFragmentManager();
                        FragmentTransaction transaction = manager.beginTransaction();
                        Fragment fragment = null;
                        switch (position) {
                            case 1:
                                fragment = new HomeFragment();
                                //Set màu cho text view trong thanh toolbar
                                ((TextView) myToolbar.findViewById(R.id.tvToolBarTitle)).setTextColor(Color.parseColor("#6f6f6f"));
                                break;
                            case 3:
                                fragment = new UngDungFragment();
                                //Set màu cho text view trong thanh toolbar
                                ((TextView) myToolbar.findViewById(R.id.tvToolBarTitle)).setTextColor(Color.parseColor("#6f6f6f"));
                                break;
                            case 4:
                                fragment = new TroChoiFragment();
                                //Set màu cho text view trong thanh toolbar
                                ((TextView) myToolbar.findViewById(R.id.tvToolBarTitle)).setTextColor(Color.parseColor("#6f6f6f"));
                                break;
                            case 5:
                                fragment = new EbookFragment();
                                //Set màu cho text view trong thanh toolbar
                                ((TextView) myToolbar.findViewById(R.id.tvToolBarTitle)).setTextColor(Color.parseColor("#6f6f6f"));
                                break;
                            case 6:
                                fragment = new MovieFragment();
                                //Set màu cho text view trong thanh toolbar
                                ((TextView) myToolbar.findViewById(R.id.tvToolBarTitle)).setTextColor(Color.parseColor("#6f6f6f"));
                                break;
                        }
                        //Cập nhật lại fragment
                        transaction.replace(R.id.flMainContainer, fragment);
                        transaction.commit();
                        return false;
                    }
                })
                .withSavedInstance(savedInstanceState)
                .build();
    }

    //Xử lý nút search trong thanh toolbar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.search_state, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchManager searchManager;
        searchManager = (SearchManager) MenuActivity.this.getSystemService(Context.SEARCH_SERVICE);
        searchView = null;
        if (searchItem != null) {
            searchView = (SearchView) searchItem.getActionView();
            searchView.setQueryHint("Tìm kiếm ....");
        }
        if (searchView != null) {
            searchView.setSearchableInfo(searchManager.getSearchableInfo(MenuActivity.this.getComponentName()));
            searchView.setQueryHint("Tìm kiếm ....");
            SearchView.OnQueryTextListener queryTextListener = new SearchView.OnQueryTextListener() {
                public boolean onQueryTextChange(String newText) {
                    return true;
                }

                public boolean onQueryTextSubmit(String query) {
                    // actionSearch(query);
                    searchView.setFocusable(false);
                    InputMethodManager imm =
                            (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(searchView.getWindowToken(), 0);
                    return true;
                }
            };
            searchView.setOnQueryTextListener(queryTextListener);
            searchView.setOnCloseListener(new SearchView.OnCloseListener() {
                @Override
                public boolean onClose() {
                    /*
                    Fragment fragment = new CategoryFragment();
                    Bundle bundle = new Bundle();
                    fragment.setArguments(bundle);
                    FragmentManager fragmentManager = getFragmentManager();
                    fragmentManager.beginTransaction().replace(R.id.containerView,fragment)
                            .commit();
                    */
                    return false;
                }
            });

        }
        return super.onCreateOptionsMenu(menu);
    }

    //Xử ký nút back hệ thống
    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
            getSupportFragmentManager().popBackStack();
        } else if (!doubleBackToExitPressedOnce) {
            this.doubleBackToExitPressedOnce = true;
            Toast.makeText(this, "Please click BACK again to exit.", Toast.LENGTH_SHORT).show();
            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    doubleBackToExitPressedOnce = false;
                }
            }, 2000);
        } else {
            //super.onBackPressed();
            finish();
            return;
        }
    }
    private MaterialMenuDrawable.IconState currentIconState;
    public boolean animateHomeIcon(MaterialMenuDrawable.IconState iconState) {
        if (currentIconState == iconState) return false;
        currentIconState = iconState;
        return true;
    }

    public void setHomeIcon(MaterialMenuDrawable.IconState iconState) {
        if (currentIconState == iconState) return;
        currentIconState = iconState;

    }
    public static MenuActivity of(Activity activity) {
        return (MenuActivity) activity;
    }
}