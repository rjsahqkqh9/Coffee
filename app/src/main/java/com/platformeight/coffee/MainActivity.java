/*
 *  Create by platform eight on 2020. 6. 4.
 *  Copyright (c) 2019. platform eight. All rights reserved.
 */

package com.platformeight.coffee;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.platformeight.coffee.mylocation.LocationTask;
import com.platformeight.coffee.servertask.ServerHandle;
import com.platformeight.coffee.service.FCMService;
import com.platformeight.coffee.ui.login.LoginActivity;

import java.util.concurrent.ExecutionException;

import static com.platformeight.coffee.Constant.login_state;
import static com.platformeight.coffee.Constant.result_login;
import static com.platformeight.coffee.MyApplication.mLoginForm;
import static com.platformeight.coffee.MyApplication.user;

public class MainActivity extends AppCompatActivity implements ItemFragment.OnListFragmentInteractionListener{
    private static final String TAG = "main";
    private ToolBarCustom toolBar;
    private DrawerLayout mDrawerLayout;
    private NavigationView navigationView;
    private TextView nav_name;
    private TextView tv_name;
    private TextView nav_point;
    private Context context = this;
    private ItemFragment item;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;
    float DEFAULT_ZOOM = 15;
    private Location mLastKnownLocation;

    private boolean mPermissionDenied = false;
    private boolean mLocationPermissionGranted = false;

    private FusedLocationProviderClient mFusedLocationProviderClient;
    private FragmentManager fragmentManager;
    private FragmentTransaction transaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //test developer login
        new ServerHandle().login("kyg1084", "rlawjdgns");

        if ( user.getNo() > 0 ) mLoginForm = false;
        initialView();
        initialData();
    }
    private void initialData() {
        MyApplication.Main = this;
        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        fragmentManager = getSupportFragmentManager();
        Bundle bundle = new Bundle(1);
        //TODO: 앱시작시 위치 값
        toolBar.setTitle("서울시청");
        bundle.putString("location", "서울시청");
        bundle.putDouble("laty", 126.9783881);
        bundle.putDouble("latx", 37.5666102);
        item = new ItemFragment();
        item.setArguments(bundle);
        transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.Item_list, item).commitAllowingStateLoss();

    }

    private void initialView() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolBar = new ToolBarCustom(getSupportActionBar());
        toolBar.setTitleText((TextView) findViewById(R.id.toolbar_title), R.string.app_name_kor);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        nav_name = navigationView.getHeaderView(0).findViewById(R.id.textView_Name);
        nav_point = navigationView.getHeaderView(0).findViewById(R.id.textView_Mi);
        tv_name = findViewById(R.id.main_name);
        changeLogin();
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                //menuItem.setChecked(true);
                mDrawerLayout.closeDrawers();
                int id = menuItem.getItemId();
                String title = menuItem.getTitle().toString();
                Intent intent=null;
                switch (id){
                    case R.id.account:
                        Toast.makeText(context, title + ": 계정 정보를 확인합니다.", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.order_List:
                        Toast.makeText(context, title + ": 주문 정보를 확인합니다.", Toast.LENGTH_SHORT).show();
                        intent = new Intent(context, MyOrdersActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.setting:
                        Toast.makeText(context, title + ": 설정 정보를 확인합니다.", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.logout:
                        //Toast.makeText(context, title + ": 로그아웃 시도중", Toast.LENGTH_SHORT).show();
                        if (mLoginForm) { //로그인 및 회원가입
                            intent = new Intent(context, LoginActivity.class);
                            startActivityForResult(intent, result_login);
                        } else { //로그아웃시도
                            mLoginForm = true;
                            changeLogin();
                            user = new MemberData();
                            Toast.makeText(context, "로그아웃", Toast.LENGTH_SHORT).show();
                        }
                        break;
                }
                return false;
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //return super.onCreateOptionsMenu(menu);
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public void onListFragmentInteraction(ShopData data) { //점포 클릭시 적용
        Intent intent = new Intent(this, ShopActivity.class);
        intent.putExtra(Constant.shopdata, data);
        startActivity(intent);
        //Toast.makeText(this, "event "+item.content, Toast.LENGTH_SHORT).show();
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_location:
                //Toast.makeText(context, "내주변 버튼 클릭됨", Toast.LENGTH_LONG).show();
                //this.item.setLocation("대구", "128.583052,35.798838");
                enableMyLocation();
                return true;
            case android.R.id.home:
                //super.onBackPressed();
                //Toast.makeText(this, "서랍열기 ", Toast.LENGTH_SHORT).show();
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;

        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * location 권환 확인
     */
    private void enableMyLocation() {
        // [START maps_check_location_permission]
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mLocationPermissionGranted = true;
            getDeviceLocation();
        } else {
            // Permission to access the location is missing. Show rationale and request permission
            PermissionUtils.requestPermission(this, LOCATION_PERMISSION_REQUEST_CODE,
                    Manifest.permission.ACCESS_FINE_LOCATION, true);
        }
        // [END maps_check_location_permission]
    }
    private void getDeviceLocation() {
        /*
         * Get the best and most recent location of the device, which may be null in rare
         * cases when a location is not available.
         */
        try {
            if (mLocationPermissionGranted) {
                Task locationResult = mFusedLocationProviderClient.getLastLocation();
                locationResult.addOnCompleteListener(this, new OnCompleteListener() {
                    @Override
                    public void onComplete(@NonNull Task task) {
                        if (task.isSuccessful()) {
                            mLastKnownLocation = (Location) task.getResult();
                            Log.d("mylocation", "Current location task:\n" + task.getResult());
                            myLocation(new LatLng(mLastKnownLocation.getLatitude(), mLastKnownLocation.getLongitude()));
                            //toolBar.setTitleText((TextView) findViewById(R.id.toolbar_title), "타이틀");
                        } else {
                            Log.d(TAG, "Current location is null. Using defaults.");
                            Log.e(TAG, "Exception: %s", task.getException());
                        }
                    }
                });
            }
        } catch(SecurityException e)  {
            Log.e("Exception: %s", e.getMessage());
        }
    }
    private void myLocation(LatLng latLng){
        LocationTask nt = new LocationTask(latLng);
        //LocationTask nt = new LocationTask(mDefaultLocation);
        try {
            String str = nt.execute().get();
            Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
            toolBar.setTitleText((TextView) findViewById(R.id.toolbar_title), str);
            Log.d(TAG, "myLocation: lat:"+ latLng.latitude+ ", long: "+ latLng.longitude);
            //this.item.setLocation("행정동",128.583052,35.798838);
            this.item.setLocation(str, latLng);
            //JSONObject jsonObj = new JSONObject(str);
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    public void changeLogin(){
        if (mLoginForm){ //로그인되었는지 확인
            navigationView.getMenu().findItem(R.id.logout).setTitle(getString(R.string.menu_login));
            navigationView.getMenu().findItem(R.id.account).setEnabled(false);
            navigationView.getMenu().findItem(R.id.order_List).setEnabled(false);
            mLoginForm = true;
            nav_name.setText(getString(R.string.nav_header_title));
            nav_point.setText(getString(R.string.nav_header_subtitle));
            tv_name.setText(getString(R.string.nav_header_title));
        } else { //로그인성공
            navigationView.getMenu().findItem(R.id.logout).setTitle(getString(R.string.menu_logout));
            navigationView.getMenu().findItem(R.id.account).setEnabled(true);
            navigationView.getMenu().findItem(R.id.order_List).setEnabled(true);
            mLoginForm = false;
            nav_name.setText(user.getName());
            tv_name.setText(user.getName()+"님 환영합니다.");
            nav_point.setText(""+user.getPoint());

        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == result_login && resultCode == RESULT_OK) { //로그인 결과
            if (data.hasExtra(login_state)) {
                mLoginForm = !data.getBooleanExtra(login_state,false);
                Log.d(TAG, "onActivityResult: "+mLoginForm);
                changeLogin();
                getToken("coffee_members");
            }

        }
    }
    private void getToken(String table) {
        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                        if (!task.isSuccessful()) {
                            Log.w(TAG, "getInstanceId failed", task.getException());
                            return;
                        }

                        // Get new Instance ID token
                        String token = task.getResult().getToken();

                        // Log and toast
                        String msg = getString(R.string.msg_token_fmt, token);
                        Log.d(TAG, msg);
                        Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
                        //msg = new ServerHandle().setToken(user.getNo(),table,token);
                        //Log.d(TAG, msg);
                    }
                });
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (PermissionUtils.isPermissionGranted(permissions, grantResults, Manifest.permission.ACCESS_FINE_LOCATION)) {
                // Enable the my location layer if the permission has been granted.
                enableMyLocation();
            } else {
                // Permission was denied. Display an error message
                // [START_EXCLUDE]
                // Display the missing permission error dialog when the fragments resume.
                mPermissionDenied = true;
                // [END_EXCLUDE]
            }
        }
    }
    @Override
    protected void onResumeFragments() {
        super.onResumeFragments();
        if (mPermissionDenied) {
            // Permission was not granted, display error dialog.
            showMissingPermissionError();
            mPermissionDenied = false;
        }
    }
    /**
     * Displays a dialog with error message explaining that the location permission is missing.
     */
    private void showMissingPermissionError() {
        PermissionUtils.PermissionDeniedDialog
                .newInstance(true).show(getSupportFragmentManager(), "dialog");
    }
}
