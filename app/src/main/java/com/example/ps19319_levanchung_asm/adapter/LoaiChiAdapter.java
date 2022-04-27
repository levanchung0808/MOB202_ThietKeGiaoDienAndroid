package com.example.ps19319_levanchung_asm.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
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

import java.util.ArrayList;

public class LoaiChiAdapter extends RecyclerView.Adapter<LoaiChiAdapter.ViewHolder> {

    ArrayList<PhanLoai> arrPhanLoai;
    ArrayList<GiaoDich> arrGiaoDich;
    Context context;
    DbHelper dbHelper;

    TextInputLayout tilDialogKhoanChi;
    TextInputEditText edtDialogKhoanChi;

    public LoaiChiAdapter(ArrayList<PhanLoai> arrPhanLoai, Context context, DbHelper dbHelper) {
        this.arrPhanLoai = arrPhanLoai;
        this.context = context;
        this.dbHelper = dbHelper;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_loai_chi_tab, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.tvTenLoaiChiTab.setText(arrPhanLoai.get(position).getTenLoai());
        holder.ivLoaiChiTab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popup = new PopupMenu(context, holder.ivLoaiChiTab);
                popup.inflate(R.menu.popup_menu_options);
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.edit:
                                DialogCapNhatLoaiChi(arrPhanLoai.get(position).getMaLoai(),arrPhanLoai.get(position).getTenLoai());
                                return true;
                            case R.id.delete:
                                DialogXoaLoaiChi(arrPhanLoai.get(position).getMaLoai());
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
        return arrPhanLoai.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvTenLoaiChiTab;
        ImageView ivLoaiChiTab;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTenLoaiChiTab = itemView.findViewById(R.id.tvTenLoaiChiTab);
            ivLoaiChiTab = itemView.findViewById(R.id.ivLoaiChiTab);
        }
    }

    private void DialogCapNhatLoaiChi(int maLoai, String tenLoai) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        View v = inflater.inflate(R.layout.dialog_khoan_chi, null);

        TextView tvDialogKhoanChiTitle = v.findViewById(R.id.tvDialogKhoanChiTitle);
        tilDialogKhoanChi = v.findViewById(R.id.tilDialogKhoanChi);
        edtDialogKhoanChi = v.findViewById(R.id.edtDialogKhoanChi);

        tvDialogKhoanChiTitle.setText("CẬP NHẬT LOẠI CHI");
        edtDialogKhoanChi.setText(tenLoai);

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
                    dbHelper.capNhatLoaiKhoanChi(maLoai, edtDialogKhoanChi.getText().toString());
                    Toast.makeText(context, "Cập nhật thành công!", Toast.LENGTH_SHORT).show();
                    loadDanhSach();
                    alertDialog.dismiss();
                }
            }
        });
    }

    private void DialogXoaLoaiChi(int maLoai) {
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

                arrGiaoDich = new ArrayList<>();
                arrGiaoDich = dbHelper.getAllGiaoDichKC();
                for(int j=0;j<arrGiaoDich.size();j++){
                    if(arrGiaoDich.get(j).getMaLoai() == maLoai){
                        dbHelper.xoaLoaiKhoanThu(maLoai);
                        dbHelper.xoaMaLoai(maLoai);
                        customToast("Xoá thành công!");
                        loadDanhSach();
                        return;
                    }
                }
            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void loadDanhSach() {
        arrPhanLoai.clear();
        arrPhanLoai = dbHelper.getAllPhanLoaiKC();
        notifyDataSetChanged();
    }

    private boolean validationForm(){
        if(isEmpty(tilDialogKhoanChi,edtDialogKhoanChi)){
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

    public void customToast(String thongBao){
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        View view = inflater.inflate(R.layout.custom_toast,null);
        TextView tv = view.findViewById(R.id.tvInfo);
        tv.setText(thongBao);
        //Tạo toast, gắn view, set thuộc tính và hiển thị Toast
        Toast toast = new Toast(context);
        toast.setGravity(Gravity.BOTTOM,0,100);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(view);
        toast.show();
    }
}
