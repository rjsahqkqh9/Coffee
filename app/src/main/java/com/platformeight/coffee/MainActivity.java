package com.platformeight.coffee;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.platformeight.coffee.dummy.DummyContent;

import java.util.Arrays;
import java.util.List;

import static com.platformeight.coffee.MyApplication.app_state;
import static com.platformeight.coffee.MyApplication.user;

public class MainActivity extends AppCompatActivity implements ShopListFragment.OnListFragmentInteractionListener {
    private static final String TAG = "MainActivity";
    private FragmentManager fragmentManager = getSupportFragmentManager();
    private ShopListFragment fragmentshop =  new ShopListFragment();
    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializeData();
        initializeView();

    }

    private void initializeData() {
        fragmentshop = ShopListFragment.newInstance(app_state);
    }

    private void initializeView() {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.main_frame, fragmentshop).commitAllowingStateLoss();
        bottomNavigationView = findViewById(R.id.navigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(new ItemSelectedListener());
        bottomNavigationView.inflateMenu(R.menu.menu_bottom);

    }

    @Override
    public void onListFragmentInteraction(DummyContent.DummyItem item) {

    }

    class ItemSelectedListener implements BottomNavigationView.OnNavigationItemSelectedListener{
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            switch(menuItem.getItemId())
            {
                case R.id.homeItem:
                    transaction.replace(R.id.main_frame, fragmentshop).commitAllowingStateLoss();
                    break;
                case R.id.listItem1:

                    break;
                case R.id.profileItem:

                    break;
            }
            return true;
        }
        void nodata(){
            if (user.getNo()<1){
                Toast.makeText(MainActivity.this, "회원 정보가 없습니다.", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
