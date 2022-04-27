package com.example.ps19319_levanchung_asm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class NhapLaiMaPinActivity extends AppCompatActivity {

    TextView tvMatKhau, tvSo1, tvSo2, tvSo3, tvSo4, tvSo5, tvSo6, tvSo7, tvSo8, tvSo9, tvSo0;
    EditText edtMatKhau;
    ImageView ivCheck, ivClear;
    String mk="", matKhau;
    int soLanDangNhap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        tvMatKhau = findViewById(R.id.tvMatKhau);
        tvSo1 = findViewById(R.id.tvSo1);
        tvSo2 = findViewById(R.id.tvSo2);
        tvSo3 = findViewById(R.id.tvSo3);
        tvSo4 = findViewById(R.id.tvSo4);
        tvSo5 = findViewById(R.id.tvSo5);
        tvSo6 = findViewById(R.id.tvSo6);
        tvSo7 = findViewById(R.id.tvSo7);
        tvSo8 = findViewById(R.id.tvSo8);
        tvSo9 = findViewById(R.id.tvSo9);
        tvSo0 = findViewById(R.id.tvSo0);
        edtMatKhau = findViewById(R.id.edtMatKhau);
        ivCheck = findViewById(R.id.ivCheck);
        ivClear = findViewById(R.id.ivClear);

        tvMatKhau.setText("Nhập lại mật khẩu cũ");

        redPrefs();

        getText();

        ivClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(edtMatKhau.getText().toString().length() != 0){
                    mk = mk.substring(0,mk.length() - 1 );
                    edtMatKhau.setText(mk);
                }
            }
        });

        ivCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(edtMatKhau.getText().toString().equals(matKhau)){
                    Intent intent = new Intent(NhapLaiMaPinActivity.this,DoiMaPinActivity.class);
                    startActivity(intent);
                    finish();
                }else{
                    customToast("Mật khẩu cũ không hợp lệ!",false);
                }
            }
        });
    }

    private void savePrefs(String content, int soLanDangnhap){
        SharedPreferences preferences = getSharedPreferences("data",MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("matkhau",content);
        editor.putInt("soLanDangNhap",soLanDangnhap);
        editor.commit();
    }

    private void redPrefs(){
        SharedPreferences preferences = getSharedPreferences("data",MODE_PRIVATE);
        matKhau = preferences.getString("matkhau","");
        soLanDangNhap = preferences.getInt("soLanDangNhap",0);
    }

    private void getText(){
        tvSo1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mk += tvSo1.getText().toString();
                edtMatKhau.setText(mk);
            }
        });

        tvSo2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mk += tvSo2.getText().toString();
                edtMatKhau.setText(mk);
            }
        });

        tvSo3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mk += tvSo3.getText().toString();
                edtMatKhau.setText(mk);
            }
        });

        tvSo4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mk += tvSo4.getText().toString();
                edtMatKhau.setText(mk);
            }
        });

        tvSo5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mk += tvSo5.getText().toString();
                edtMatKhau.setText(mk);
            }
        });

        tvSo6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mk += tvSo6.getText().toString();
                edtMatKhau.setText(mk);
            }
        });

        tvSo7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mk += tvSo7.getText().toString();
                edtMatKhau.setText(mk);
            }
        });

        tvSo8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mk += tvSo8.getText().toString();
                edtMatKhau.setText(mk);
            }
        });

        tvSo9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mk += tvSo9.getText().toString();
                edtMatKhau.setText(mk);
            }
        });

        tvSo0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mk += tvSo0.getText().toString();
                edtMatKhau.setText(mk);
            }
        });
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
        Toast toast = new Toast(NhapLaiMaPinActivity.this);
        toast.setGravity(Gravity.BOTTOM,0,100);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(view);
        toast.show();
    }
}