package com.example.ps19319_levanchung_asm.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.ps19319_levanchung_asm.fragment.khoan_thu.KhoanThuTabFragment;
import com.example.ps19319_levanchung_asm.fragment.khoan_thu.LoaiThuTabFragment;

public class ViewPagerKhoanThuAdaper extends FragmentStateAdapter {
    public ViewPagerKhoanThuAdaper(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 0:
                return new KhoanThuTabFragment();
            case 1:
                return new LoaiThuTabFragment();
            default:
                return new KhoanThuTabFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
