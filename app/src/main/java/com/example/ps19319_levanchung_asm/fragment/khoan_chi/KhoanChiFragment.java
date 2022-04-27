package com.example.ps19319_levanchung_asm.fragment.khoan_chi;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.example.ps19319_levanchung_asm.R;
import com.example.ps19319_levanchung_asm.adapter.ViewPagerKhoanChiAdaper;
import com.example.ps19319_levanchung_asm.adapter.ViewPagerKhoanThuAdaper;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class KhoanChiFragment extends Fragment {

    ViewPager2 viewPagerKhoanChi;
    TabLayout tabLayoutKhoanChi;
    ViewPagerKhoanChiAdaper adaper;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_khoan_chi,container,false);

        viewPagerKhoanChi = view.findViewById(R.id.viewPagerKhoanChi);
        tabLayoutKhoanChi = view.findViewById(R.id.tabLayoutKhoanChi);

        adaper = new ViewPagerKhoanChiAdaper(getActivity());

        viewPagerKhoanChi.setAdapter(adaper);

        new TabLayoutMediator(tabLayoutKhoanChi, viewPagerKhoanChi, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                switch (position){
                    case 0:
                        tab.setText("Khoản chi");
                        break;
                    case 1:
                        tab.setText("Loại chi");
                        break;
                }
            }
        }).attach();

        return view;
    }
}
