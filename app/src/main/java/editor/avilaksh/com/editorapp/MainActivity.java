package editor.avilaksh.com.editorapp;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.PopupMenu;
import android.view.Gravity;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {
    DrawerLayout drawerLayout;
    ImageView imageView;
    Context mContext;
    BottomNavigationView bottomNavigationView;
    ImageView optionImage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        drawerLayout = (DrawerLayout) findViewById(R.id.activity_main);
        imageView = (ImageView) findViewById(R.id.imageViewmenu);
        optionImage = (ImageView) findViewById(R.id.imageViewoption);
        optionImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopup(v);
            }
        });
        switchToFragment1();

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(Gravity.START);

            }
        });
        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomnavigationbar);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.home:
                        HomeFragment homeFragment = new HomeFragment();
                        switchToFragment1();


                        break;
                    case R.id.gallery:
                        GalleryFragment galleryFragment = new GalleryFragment();

                        switchToFragment2();
                        break;
                    case R.id.howtouse:
                        SettingFragment settingFragment = new SettingFragment();
                        switchToFragment3();
                        break;
                }
                return true;
            }
        });

    }

    public void switchToFragment1() {
        HomeFragment homeFragment = new HomeFragment();
        FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction().replace(R.id.container, homeFragment).commit();
    }

    public void switchToFragment2() {
        GalleryFragment galleryFragment = new GalleryFragment();
        FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction().replace(R.id.container, galleryFragment).commit();
    }

    public void switchToFragment3() {
        SettingFragment settingFragment = new SettingFragment();
        FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction().replace(R.id.container, settingFragment).commit();
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(Gravity.START)) {
            drawerLayout.closeDrawers();
        } else {
            super.onBackPressed();
        }


    }

    public void showPopup(View v) {
        PopupMenu popup = new PopupMenu(this, v);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.sidemenu, popup.getMenu());
        popup.show();
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.account:
                        // archive(item);
                        return true;
                    case R.id.logout:
//                        delete(item);
                        return true;
                    default:
                        return false;
                }
            }
        });

    }
}
