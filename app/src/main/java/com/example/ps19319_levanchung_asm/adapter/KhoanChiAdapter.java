package com.example.ps19319_levanchung_asm.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
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

public class KhoanChiAdapter extends RecyclerView.Adapter<KhoanChiAdapter.ViewHolder1> {

    ArrayList<GiaoDich> arrGiaoDich;
    ArrayList<PhanLoai> arrPhanLoai;
    Context context;
    DbHelper dbHelper;
    SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");

    TextInputEditText edtTieuDeDialogKhoanChi;
    TextInputEditText edtNgayDialogKhoanChi;
    TextInputEditText edtGiaDialogKhoanChi;
    TextInputLayout tilTieuDeDialogKhoanChi;
    TextInputLayout tilNgayDialogKhoanChi;
    TextInputLayout tilGiaDialogKhoanChi;

    public KhoanChiAdapter(ArrayList<GiaoDich> arrGiaoDich, Context context, DbHelper dbHelper) {
        this.arrGiaoDich = arrGiaoDich;
        this.context = context;
        this.dbHelper = dbHelper;
    }

    @NonNull
    @Override
    public ViewHolder1 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_khoan_chi_tab, parent, false);

        return new ViewHolder1(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder1 holder, @SuppressLint("RecyclerView") int position) {
        holder.tvTenKhoanChiTab.setText(arrGiaoDich.get(position).getTieuDe());
        String str = NumberFormat.getNumberInstance(Locale.US).format(arrGiaoDich.get(position).getTien());
        holder.tvGiaKhoanChiTab.setText("- "+ str + " đ");
        holder.tvNgayKhoanChiTab.setText(sdf.format(arrGiaoDich.get(position).getNgay()));
        arrPhanLoai = dbHelper.getAllPhanLoaiKC();
        for (int i = 0; i < arrPhanLoai.size(); i++) {
            if (arrPhanLoai.get(i).getMaLoai() == arrGiaoDich.get(position).getMaLoai()) {
                holder.tvMaLoaiKhoanChiTab.setText(arrPhanLoai.get(i).getTenLoai());
            }
        }
        holder.ivKhoanChiTab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popup = new PopupMenu(context, holder.ivKhoanChiTab);
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
                                DialogCapNhatKhoanChi(maGD,tieuDe,ngay,tien,maLoai);
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
        TextView tvTenKhoanChiTab,tvGiaKhoanChiTab,tvNgayKhoanChiTab,tvMaLoaiKhoanChiTab;
        ImageView ivKhoanChiTab;

        public ViewHolder1(@NonNull View itemView) {
            super(itemView);
            tvTenKhoanChiTab = itemView.findViewById(R.id.tvTenKhoanChiTab);
            tvGiaKhoanChiTab = itemView.findViewById(R.id.tvGiaKhoanChiTab);
            tvNgayKhoanChiTab = itemView.findViewById(R.id.tvNgayKhoanChiTab);
            tvMaLoaiKhoanChiTab = itemView.findViewById(R.id.tvMaLoaiKhoanChiTab);
            ivKhoanChiTab = itemView.findViewById(R.id.ivKhoanChiTab);
        }
    }

    private void DialogCapNhatKhoanChi(int maGD, String tieuDe, String ngay, float tien, int maLoai) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
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

        tvDialogKhoanChiTitle.setText("CẬP NHẬT KHOẢN CHI");
        edtTieuDeDialogKhoanChi.setText(tieuDe);
        edtNgayDialogKhoanChi.setText(ngay);
        edtGiaDialogKhoanChi.setText(String.format("%.0f",tien));

        ivDateTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get Current Date
                DatePickerDialog datePickerDialog = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        edtNgayDialogKhoanChi.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                    }
                }, Calendar.getInstance().get(Calendar.YEAR), Calendar.getInstance().get(Calendar.MONTH), Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
                datePickerDialog.show();
            }
        });

        arrPhanLoai = dbHelper.getAllPhanLoaiKC();
        ArrayAdapter adapter1 = new ArrayAdapter(context, android.R.layout.simple_list_item_1, arrPhanLoai);
        spnMaLoaiDialogKhoanChi.setAdapter(adapter1);
        for (int i = 0; i < arrPhanLoai.size(); i++) {
            if (arrPhanLoai.get(i).getMaLoai() == maLoai) {
                spnMaLoaiDialogKhoanChi.setSelection(i);
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
                    String _tieuDe = edtTieuDeDialogKhoanChi.getText().toString();
                    String _ngay = edtNgayDialogKhoanChi.getText().toString();
                    String _tien = edtGiaDialogKhoanChi.getText().toString();
                    String _maLoai = spnMaLoaiDialogKhoanChi.getSelectedItem().toString();
                    int maLoai = 0;
                    for (int j = 0; j < arrPhanLoai.size(); j++) {
                        if (_maLoai == (arrPhanLoai.get(j).getTenLoai())) {
                            maLoai = arrPhanLoai.get(j).getMaLoai();
                        }
                    }
                    dbHelper.capNhatKhoanThu(maGD,_tieuDe,_ngay,Float.parseFloat(_tien),maLoai);
                    Toast.makeText(context, "Cập nhật thành công!", Toast.LENGTH_SHORT).show();
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
                Toast.makeText(context, "Xoá thành công!", Toast.LENGTH_SHORT).show();
                loadDanhSach();
            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void loadDanhSach() {
        arrGiaoDich.clear();
        arrGiaoDich = dbHelper.getAllGiaoDichKC();
        notifyDataSetChanged();
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
}
