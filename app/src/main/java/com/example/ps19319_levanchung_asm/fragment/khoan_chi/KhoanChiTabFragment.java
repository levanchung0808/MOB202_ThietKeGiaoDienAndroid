package com.example.ps19319_levanchung_asm.fragment.khoan_chi;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
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

import com.example.ps19319_levanchung_asm.R;
import com.example.ps19319_levanchung_asm.adapter.KhoanChiAdapter;
import com.example.ps19319_levanchung_asm.adapter.KhoanThuAdapter;
import com.example.ps19319_levanchung_asm.database.DbHelper;
import com.example.ps19319_levanchung_asm.model.GiaoDich;
import com.example.ps19319_levanchung_asm.model.PhanLoai;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.Calendar;

public class KhoanChiTabFragment extends Fragment {

    RecyclerView rvKhoanChiTab;
    FloatingActionButton fabKhoanChiTab;
    DbHelper dbHelper;
    KhoanChiAdapter adapter;
    ArrayList<GiaoDich> arrGiaoDich;
    ArrayList<PhanLoai> arrPhanLoai;

    TextInputEditText edtTieuDeDialogKhoanChi;
    TextInputEditText edtNgayDialogKhoanChi;
    TextInputEditText edtGiaDialogKhoanChi;
    TextInputLayout tilTieuDeDialogKhoanChi;
    TextInputLayout tilNgayDialogKhoanChi;
    TextInputLayout tilGiaDialogKhoanChi;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tab_khoan_chi,container,false);
        rvKhoanChiTab = view.findViewById(R.id.rvKhoanChiTab);
        fabKhoanChiTab = view.findViewById(R.id.fabKhoanChiTab);

        dbHelper = new DbHelper(getActivity());
        arrGiaoDich = new ArrayList<>();

        getDataKhoanChi();

        fabKhoanChiTab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogThemKhoanChi();
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
                getDataKhoanChi();
            }
        });
        itemTouchHelper.attachToRecyclerView(rvKhoanChiTab);

        return view;
    }

    private void getDataKhoanChi() {
        arrGiaoDich.clear();
        arrGiaoDich = dbHelper.getAllGiaoDichKC();
        adapter = new KhoanChiAdapter(arrGiaoDich, getActivity(), dbHelper);
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        rvKhoanChiTab.setLayoutManager(manager);
        rvKhoanChiTab.setAdapter(adapter);
        adapter.notifyDataSetChanged();

    }

    private void DialogThemKhoanChi() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getLayoutInflater();
        View v = inflater.inflate(R.layout.dialog_khoan_chi2, null);

        TextView tvDialogKhoanChiTitle = v.findViewById(R.id.tvDialogKhoanChiTitle);
        edtTieuDeDialogKhoanChi = v.findViewById(R.id.edtTieuDeDialogKhoanChi);
        edtNgayDialogKhoanChi = v.findViewById(R.id.edtNgayDialogKhoanChi);
        edtGiaDialogKhoanChi = v.findViewById(R.id.edtGiaDialogKhoanChi);
        tilTieuDeDialogKhoanChi = v.findViewById(R.id.tilTieuDeDialogKhoanChi);
        tilNgayDialogKhoanChi = v.findViewById(R.id.tilNgayDialogKhoanChi);
        tilGiaDialogKhoanChi = v.findViewById(R.id.tilGiaDialogKhoanChi);
        ImageView ivDateTime = v.findViewById(R.id.ivDateTime);
        Spinner spnMaLoaiDialogKhoanChi = v.findViewById(R.id.spnMaLoaiDialogKhoanChi);

        tvDialogKhoanChiTitle.setText("THÊM KHOẢN CHI");

        ivDateTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get Current Date
                DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        String _day = String.format("%02d",dayOfMonth);
                        String _month = String.format("%02d",monthOfYear+1);
                        edtNgayDialogKhoanChi.setText(_day + "-" + _month + "-" + year);
                    }
                }, Calendar.getInstance().get(Calendar.YEAR), Calendar.getInstance().get(Calendar.MONTH), Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
                datePickerDialog.show();
            }
        });

        arrPhanLoai = dbHelper.getAllPhanLoaiKC();
        ArrayAdapter adapter1 = new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1, arrPhanLoai);
        spnMaLoaiDialogKhoanChi.setAdapter(adapter1);

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
                    String tieuDe = edtTieuDeDialogKhoanChi.getText().toString();
                    String ngay = edtNgayDialogKhoanChi.getText().toString();
                    String gia = edtGiaDialogKhoanChi.getText().toString();
                    String maLoai = spnMaLoaiDialogKhoanChi.getSelectedItem().toString();
                    int _maLoai = 0;
                    for (int j = 0; j < arrPhanLoai.size(); j++) {
                        if (maLoai == (arrPhanLoai.get(j).getTenLoai())) {
                            _maLoai = arrPhanLoai.get(j).getMaLoai();
                        }
                    }
                    dbHelper.taoKhoanThu(tieuDe,ngay,Float.parseFloat(gia),_maLoai);
                    customToast("Thêm thành công",true);
                    getDataKhoanChi();
                    alertDialog.dismiss();
                }
            }
        });

        if (spnMaLoaiDialogKhoanChi.getCount()==0){
            customToast("Chưa có loại chi để thêm!",false);
            alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(false);
        }
    }

    private boolean validationForm(){
        if(isEmpty(tilTieuDeDialogKhoanChi,edtTieuDeDialogKhoanChi) | isEmpty(tilNgayDialogKhoanChi,edtNgayDialogKhoanChi) | isEmpty(tilGiaDialogKhoanChi,edtGiaDialogKhoanChi)){
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

    public void customToast(String thongBao, boolean check){
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.custom_toast,null);
        ImageView ivHinh = view.findViewById(R.id.ivHinh);
        if(check == false){
            ivHinh.setImageResource(R.drawable.check2);
        }
        RelativeLayout relativeLayout = view.findViewById(R.id.layoutToast);
        TextView tv = view.findViewById(R.id.tvInfo);
        tv.setText(thongBao);
        //Tạo toast, gắn view, set thuộc tính và hiển thị Toast
        Toast toast = new Toast(getActivity());
        toast.setGravity(Gravity.BOTTOM,0,100);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(view);
        toast.show();
    }

    @Override
    public void onResume() {
        getDataKhoanChi();
        super.onResume();
    }
}
