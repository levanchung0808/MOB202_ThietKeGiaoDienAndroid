package com.example.ps19319_levanchung_asm.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
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
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.PopupMenu;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ps19319_levanchung_asm.R;
import com.example.ps19319_levanchung_asm.database.DbHelper;
import com.example.ps19319_levanchung_asm.model.GiaoDich;
import com.example.ps19319_levanchung_asm.model.PhanLoai;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class KhoanThuAdapter extends RecyclerView.Adapter<KhoanThuAdapter.ViewHolder1> {

    ArrayList<GiaoDich> arrGiaoDich;
    ArrayList<PhanLoai> arrPhanLoai;
    Context context;
    DbHelper dbHelper;
    SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");

    TextInputEditText edtTieuDeDialogKhoanThu;
    TextInputEditText edtNgayDialogKhoanThu;
    TextInputEditText edtGiaDialogKhoanThu;
    TextInputLayout tilTieuDeDialogKhoanThu;
    TextInputLayout tilNgayDialogKhoanThu;
    TextInputLayout tilGiaDialogKhoanThu;

    public KhoanThuAdapter(ArrayList<GiaoDich> arrGiaoDich, Context context, DbHelper dbHelper) {
        this.arrGiaoDich = arrGiaoDich;
        this.context = context;
        this.dbHelper = dbHelper;
    }

    @NonNull
    @Override
    public ViewHolder1 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_khoan_thu_tab, parent, false);

        return new ViewHolder1(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder1 holder, @SuppressLint("RecyclerView") int position) {
        holder.tvTenKhoanThuTab.setText(arrGiaoDich.get(position).getTieuDe());
        String str = NumberFormat.getNumberInstance(Locale.US).format(arrGiaoDich.get(position).getTien());
        holder.tvGiaKhoanThuTab.setText("+ " + str + " đ");
        holder.tvNgayKhoanThuTab.setText(sdf.format(arrGiaoDich.get(position).getNgay()));
        arrPhanLoai = dbHelper.getAllPhanLoai();
        for (int i = 0; i < arrPhanLoai.size(); i++) {
            if (arrPhanLoai.get(i).getMaLoai() == arrGiaoDich.get(position).getMaLoai()) {
                holder.tvMaLoaiKhoanThuTab.setText(arrPhanLoai.get(i).getTenLoai());
            }
        }
        holder.ivKhoanThuTab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popup = new PopupMenu(context, holder.ivKhoanThuTab);
                popup.inflate(R.menu.popup_menu_options);
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.edit:
                                int maGD = arrGiaoDich.get(position).getMaGD();
                                String tieuDe = arrGiaoDich.get(position).getTieuDe();
                                String ngay = sdf.format(arrGiaoDich.get(position).getNgay());
                                float tien = arrGiaoDich.get(position).getTien();
                                int maLoai = arrGiaoDich.get(position).getMaLoai();
                                DialogCapNhatKhoanThu(maGD, tieuDe, ngay, tien, maLoai);
                                return true;
                            case R.id.delete:
                                DialogXoaKhoanThu(arrGiaoDich.get(position).getMaGD());
                                return true;
                            default:
                                return false;
                        }
                    }
                });
                popup.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrGiaoDich.size();
    }

    class ViewHolder1 extends RecyclerView.ViewHolder {
        TextView tvTenKhoanThuTab, tvGiaKhoanThuTab, tvNgayKhoanThuTab, tvMaLoaiKhoanThuTab;
        ImageView ivKhoanThuTab;

        public ViewHolder1(@NonNull View itemView) {
            super(itemView);
            tvTenKhoanThuTab = itemView.findViewById(R.id.tvTenKhoanThuTab);
            tvGiaKhoanThuTab = itemView.findViewById(R.id.tvGiaKhoanThuTab);
            tvNgayKhoanThuTab = itemView.findViewById(R.id.tvNgayKhoanThuTab);
            tvMaLoaiKhoanThuTab = itemView.findViewById(R.id.tvMaLoaiKhoanThuTab);
            ivKhoanThuTab = itemView.findViewById(R.id.ivKhoanThuTab);
        }
    }

    private void DialogCapNhatKhoanThu(int maGD, String tieuDe, String ngay, float tien, int maLoai) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
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

        tvDialogKhoanThuTitle.setText("CẬP NHẬT KHOẢN THU");
        edtTieuDeDialogKhoanThu.setText(tieuDe);
        edtNgayDialogKhoanThu.setText(ngay);
        edtGiaDialogKhoanThu.setText(String.format("%.0f", tien));

        ivDateTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get Current Date
                DatePickerDialog datePickerDialog = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        edtNgayDialogKhoanThu.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                    }
                }, Calendar.getInstance().get(Calendar.YEAR), Calendar.getInstance().get(Calendar.MONTH), Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
                datePickerDialog.show();
            }
        });

        arrPhanLoai = dbHelper.getAllPhanLoai();
        ArrayAdapter adapter1 = new ArrayAdapter(context, android.R.layout.simple_list_item_1, arrPhanLoai);
        spnMaLoaiDialogKhoanThu.setAdapter(adapter1);
        for (int i = 0; i < arrPhanLoai.size(); i++) {
            if (arrPhanLoai.get(i).getMaLoai() == maLoai) {
                spnMaLoaiDialogKhoanThu.setSelection(i);
            }
        }

        builder.setView(v);
        builder.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });

        builder.setPositiveButton("Cập nhật", new DialogInterface.OnClickListener() {
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
                    String _tieuDe = edtTieuDeDialogKhoanThu.getText().toString();
                    String _ngay = edtNgayDialogKhoanThu.getText().toString();
                    String _tien = edtGiaDialogKhoanThu.getText().toString();
                    String _maLoai = spnMaLoaiDialogKhoanThu.getSelectedItem().toString();
                    int maLoai = 0;
                    for (int j = 0; j < arrPhanLoai.size(); j++) {
                        if (_maLoai == (arrPhanLoai.get(j).getTenLoai())) {
                            maLoai = arrPhanLoai.get(j).getMaLoai();
                        }
                    }
                    dbHelper.capNhatKhoanThu(maGD, _tieuDe, _ngay, Float.parseFloat(_tien), maLoai);
                    customToast("Cập nhật thành công");
                    loadDanhSach();
                    alertDialog.dismiss();
                }
            }
        });
    }

    private void DialogXoaKhoanThu(int maGD) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage("Bạn có chắc chắn muốn xoá?");

        builder.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });

        builder.setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dbHelper.xoaKhoanThu(maGD);
                customToast("Xoá thành công!");
                loadDanhSach();
            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void loadDanhSach() {
        arrGiaoDich.clear();
        arrGiaoDich = dbHelper.getAllGiaoDich();
        notifyDataSetChanged();
    }

    public void customToast(String thongBao){
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        View view = inflater.inflate(R.layout.custom_toast,null);
        RelativeLayout relativeLayout = view.findViewById(R.id.layoutToast);
        TextView tv = view.findViewById(R.id.tvInfo);
        tv.setText(thongBao);
        //Tạo toast, gắn view, set thuộc tính và hiển thị Toast
        Toast toast = new Toast(context);
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
}
