package com.example.ps19319_levanchung_asm.fragment.khoan_thu;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ps19319_levanchung_asm.LoginActivity;
import com.example.ps19319_levanchung_asm.R;
import com.example.ps19319_levanchung_asm.adapter.KhoanThuAdapter;
import com.example.ps19319_levanchung_asm.adapter.LoaiThuAdapter;
import com.example.ps19319_levanchung_asm.database.DbHelper;
import com.example.ps19319_levanchung_asm.model.GiaoDich;
import com.example.ps19319_levanchung_asm.model.PhanLoai;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.Calendar;

public class KhoanThuTabFragment extends Fragment {

    RecyclerView rvKhoanThuTab;
    FloatingActionButton fabKhoanThuTab;
    DbHelper dbHelper;
    KhoanThuAdapter adapter;
    ArrayList<GiaoDich> arrGiaoDich;
    ArrayList<PhanLoai> arrPhanLoai;

    TextInputEditText edtTieuDeDialogKhoanThu;
    TextInputEditText edtNgayDialogKhoanThu;
    TextInputEditText edtGiaDialogKhoanThu;
    TextInputLayout tilTieuDeDialogKhoanThu;
    TextInputLayout tilNgayDialogKhoanThu;
    TextInputLayout tilGiaDialogKhoanThu;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tab_khoan_thu, container, false);
        rvKhoanThuTab = view.findViewById(R.id.rvKhoanThuTab);
        fabKhoanThuTab = view.findViewById(R.id.fabKhoanThuTab);

        dbHelper = new DbHelper(getActivity());
        arrGiaoDich = new ArrayList<>();
        arrPhanLoai = new ArrayList<>();

        getDataKhoanThu();

        fabKhoanThuTab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogThemKhoanThu();
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
                dbHelper.xoaKhoanThu(arrGiaoDich.get(position).getMaGD());
                customToast("Xoá thành công!",true);
                getDataKhoanThu();
            }
        });
        itemTouchHelper.attachToRecyclerView(rvKhoanThuTab);

        return view;
    }

    private void getDataKhoanThu() {
        arrGiaoDich.clear();
        arrGiaoDich = dbHelper.getAllGiaoDich();
        adapter = new KhoanThuAdapter(arrGiaoDich, getActivity(), dbHelper);
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        rvKhoanThuTab.setLayoutManager(manager);
        rvKhoanThuTab.setAdapter(adapter);
        adapter.notifyDataSetChanged();

    }

    private void DialogThemKhoanThu() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getLayoutInflater();
        View v = inflater.inflate(R.layout.dialog_khoan_thu2, null);

        TextView tvDialogKhoanThuTitle = v.findViewById(R.id.tvDialogKhoanThuTitle);
        edtTieuDeDialogKhoanThu = v.findViewById(R.id.edtTieuDeDialogKhoanThu);
        edtNgayDialogKhoanThu = v.findViewById(R.id.edtNgayDialogKhoanThu);
        edtGiaDialogKhoanThu = v.findViewById(R.id.edtGiaDialogKhoanThu);
        tilTieuDeDialogKhoanThu = v.findViewById(R.id.tilTieuDeDialogKhoanThu);
        tilNgayDialogKhoanThu = v.findViewById(R.id.tilNgayDialogKhoanThu);
        tilGiaDialogKhoanThu = v.findViewById(R.id.tilGiaDialogKhoanThu);
        ImageView ivDateTime = v.findViewById(R.id.ivDateTime);
        Spinner spnMaLoaiDialogKhoanThu = v.findViewById(R.id.spnMaLoaiDialogKhoanThu);

        tvDialogKhoanThuTitle.setText("THÊM KHOẢN THU");

        ivDateTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get Current Date
                DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        String _day = String.format("%02d",dayOfMonth);
                        String _month = String.format("%02d",monthOfYear+1);
                        edtNgayDialogKhoanThu.setText(_day + "-" + _month + "-" + year);
                    }
                }, Calendar.getInstance().get(Calendar.YEAR), Calendar.getInstance().get(Calendar.MONTH), Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
                datePickerDialog.show();
            }
        });

        arrPhanLoai = dbHelper.getAllPhanLoai();
        ArrayAdapter adapter1 = new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1, arrPhanLoai);
        spnMaLoaiDialogKhoanThu.setAdapter(adapter1);

        builder.setView(v);
        builder.setNegativeButton("Huỷ", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
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
                    String tieuDe = edtTieuDeDialogKhoanThu.getText().toString();
                    String ngay = edtNgayDialogKhoanThu.getText().toString();
                    String gia = edtGiaDialogKhoanThu.getText().toString();
                    String maLoai = spnMaLoaiDialogKhoanThu.getSelectedItem().toString();
                    int _maLoai = 0;
                    for (int j = 0; j < arrPhanLoai.size(); j++) {
                        if (maLoai == (arrPhanLoai.get(j).getTenLoai())) {
                            _maLoai = arrPhanLoai.get(j).getMaLoai();
                        }
                    }
                    dbHelper.taoKhoanThu(tieuDe, ngay, Float.parseFloat(gia), _maLoai);
                    //CUSTOM Toast
                    customToast("Thêm thành công!",true);
                    getDataKhoanThu();
                    alertDialog.dismiss();
                }
            }
        });

        if (spnMaLoaiDialogKhoanThu.getCount()==0){
            customToast("Chưa có loại thu để thêm!",false);
            alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(false);
        }
    }

    public void customToast(String thongBao, boolean check){
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.custom_toast,null);
        ImageView ivHinh = view.findViewById(R.id.ivHinh);
        if(check == false){
            ivHinh.setImageResource(R.drawable.check2);
        }
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
        if(isEmpty(tilTieuDeDialogKhoanThu,edtTieuDeDialogKhoanThu) | isEmpty(tilNgayDialogKhoanThu,edtNgayDialogKhoanThu) | isEmpty(tilGiaDialogKhoanThu,edtGiaDialogKhoanThu)){
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

    @Override
    public void onResume() {
        getDataKhoanThu();
        super.onResume();
    }
}
