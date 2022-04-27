package com.example.ps19319_levanchung_asm.fragment.khoan_thu;

import android.app.Activity;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ps19319_levanchung_asm.R;
import com.example.ps19319_levanchung_asm.adapter.LoaiThuAdapter;
import com.example.ps19319_levanchung_asm.database.DbHelper;
import com.example.ps19319_levanchung_asm.model.PhanLoai;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;

public class LoaiThuTabFragment extends Fragment {

    RecyclerView rvLoaiThuTab;
    FloatingActionButton fabLoaiThuTab;
    DbHelper dbHelper;
    LoaiThuAdapter adapter;
    ArrayList<PhanLoai> arrLoaiThu;

    TextInputLayout tilDialogKhoanThu;
    TextInputEditText edtDialogKhoanThu;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tab_loai_thu,container,false);
        rvLoaiThuTab = view.findViewById(R.id.rvLoaiThuTab);
        fabLoaiThuTab = view.findViewById(R.id.fabLoaiThuTab);

        dbHelper = new DbHelper(getActivity());
        arrLoaiThu = new ArrayList<>();

        getDataLoaiThu();

        fabLoaiThuTab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogThemLoaiThu();
            }
        });

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();
                dbHelper.xoaLoaiKhoanThu(arrLoaiThu.get(position).getMaLoai());
                customToast("Xoá thành công!");
                getDataLoaiThu();
            }
        });
        itemTouchHelper.attachToRecyclerView(rvLoaiThuTab);

        return view;
    }

    private void getDataLoaiThu() {
        arrLoaiThu.clear();
        arrLoaiThu = dbHelper.getAllPhanLoai();
        adapter = new LoaiThuAdapter(arrLoaiThu, getActivity(), dbHelper);
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        rvLoaiThuTab.setLayoutManager(manager);
        rvLoaiThuTab.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    private void DialogThemLoaiThu() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getLayoutInflater();
        View v = inflater.inflate(R.layout.dialog_khoan_thu, null);

        TextView tvDialogKhoanThuTitle = v.findViewById(R.id.tvDialogKhoanThuTitle);
        tilDialogKhoanThu = v.findViewById(R.id.tilDialogKhoanThu);
        edtDialogKhoanThu = v.findViewById(R.id.edtDialogKhoanThu);

        tvDialogKhoanThuTitle.setText("THÊM LOẠI THU");

        builder.setView(v);

        builder.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });

        builder.setPositiveButton("Thêm", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
        alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validationForm()){
                    dbHelper.taoLoaiKhoanThu(edtDialogKhoanThu.getText().toString());
                    customToast("Thêm thành công!");
                    getDataLoaiThu();
                    alertDialog.dismiss();
                }
            }
        });
    }

    public void customToast(String thongBao){
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.custom_toast,null);
        TextView tv = view.findViewById(R.id.tvInfo);
        tv.setText(thongBao);
        //Tạo toast, gắn view, set thuộc tính và hiển thị Toast
        Toast toast = new Toast(getActivity());
        toast.setGravity(Gravity.BOTTOM,0,100);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(view);
        toast.show();
    }

    private boolean validationForm(){
        if(isEmpty(tilDialogKhoanThu,edtDialogKhoanThu)){
            return false;
        }
        return true;
    }

    private boolean isEmpty(TextInputLayout til, TextInputEditText edt){
        String str = edt.getText().toString();
        if(str.length() == 0){
            til.setError("Không được bỏ trống");
            return true;
        }
        til.setError(null);
        return false;
    }
}
