package com.example.ps19319_levanchung_asm.model;

import java.util.Date;

public class GiaoDich {
    private int maGD;
    private String tieuDe;
    private Date ngay;
    private float tien;
    private int maLoai;

    public GiaoDich(int maGD, String tieuDe, Date ngay, float tien , int maLoai) {
        this.maGD = maGD;
        this.tieuDe = tieuDe;
        this.ngay = ngay;
        this.tien = tien;
        this.maLoai = maLoai;
    }

    public int getMaGD() {
        return maGD;
    }

    public void setMaGD(int maGD) {
        this.maGD = maGD;
    }

    public String getTieuDe() {
        return tieuDe;
    }

    public void setTieuDe(String tieuDe) {
        this.tieuDe = tieuDe;
    }

    public Date getNgay() {
        return ngay;
    }

    public void setNgay(Date ngay) {
        this.ngay = ngay;
    }

    public float getTien() {
        return tien;
    }

    public void setTien(float tien) {
        this.tien = tien;
    }

    public int getMaLoai() {
        return maLoai;
    }

    public void setMaLoai(int maLoai) {
        this.maLoai = maLoai;
    }

}
