package com.example.ps19319_levanchung_asm;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.ps19319_levanchung_asm.fragment.gioi_thieu.GioiThieuFragment;
import com.example.ps19319_levanchung_asm.fragment.khoan_chi.KhoanChiFragment;
import com.example.ps19319_levanchung_asm.fragment.khoan_thu.KhoanThuFragment;
import com.example.ps19319_levanchung_asm.fragment.thong_ke.ThongKeFragment;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {

    Toolbar toolbar;
    DrawerLayout drawerLayout;
    NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = findViewById(R.id.toolBar);
        drawerLayout = findViewById(R.id.drawerLayout);
        navigationView = findViewById(R.id.navigationView);

        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);
        actionBar.setDisplayHomeAsUpEnabled(true);
        setTitle("Quản lý thu chi | PS19319");

        Class fragmentClass = GioiThieuFragment.class;
        Fragment fragment = null;
        try {
            fragment = (Fragment) fragmentClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.frameLayout, fragment).commit();

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment;
                Class fragmentClass = null;
                switch (item.getItemId()) {
                    case R.id.menuKhoanThu:
                        fragmentClass = KhoanThuFragment.class;
                        break;
                    case R.id.menuKhoanChi:
                        fragmentClass = KhoanChiFragment.class;
                        break;
                    case R.id.menuThongKe:
                        fragmentClass = ThongKeFragment.class;
                        break;
                    case R.id.menuMaPin:
                        Intent intent = new Intent(MainActivity.this,NhapLaiMaPinActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.menuGioiThieu:
                        fragmentClass = GioiThieuFragment.class;
                        break;
                    case R.id.menuThoat:
                        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                        builder.setMessage("Bạn muốn thoát chương trình?");
                        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                finish();
                            }
                        });
                        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                        builder.show();
                        break;
                    default:
                        fragmentClass = KhoanThuFragment.class;
                        break;
                }

                try {
                    fragment = (Fragment) fragmentClass.newInstance();
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    fragmentManager.beginTransaction().replace(R.id.frameLayout, fragment).commit();

                    setTitle(item.getTitle());
                    drawerLayout.closeDrawer(GravityCompat.START);
                } catch (Exception exception) {
                }

                return false;
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            drawerLayout.openDrawer(GravityCompat.START);
        }
        return super.onOptionsItemSelected(item);
    }
}