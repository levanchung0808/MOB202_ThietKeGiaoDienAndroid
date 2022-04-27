package com.example.ps19319_levanchung_asm.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.ps19319_levanchung_asm.model.GiaoDich;
import com.example.ps19319_levanchung_asm.model.PhanLoai;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Currency;
import java.util.Date;

public class DbHelper extends SQLiteOpenHelper {

    SQLiteDatabase db = this.getReadableDatabase();
    SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");

    public DbHelper(Context context) {
        super(context, "PS19319_ASM_MOB202", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE PHANLOAI (MaLoai integer PRIMARY KEY AUTOINCREMENT, TenLoai text, TrangThai text)";
        db.execSQL(sql);

        sql = "INSERT INTO PHANLOAI('TenLoai','TrangThai') VALUES ('Lương','Thu')";
        db.execSQL(sql);
        sql = "INSERT INTO PHANLOAI('TenLoai','TrangThai') VALUES ('Thu hộ','Thu')";
        db.execSQL(sql);
        sql = "INSERT INTO PHANLOAI('TenLoai','TrangThai') VALUES ('Mua đồ ăn','Chi')";
        db.execSQL(sql);
        sql = "INSERT INTO PHANLOAI('TenLoai','TrangThai') VALUES ('Tiền nhà','Chi')";
        db.execSQL(sql);

        String sql1 = "CREATE TABLE GIAODICH(MaGD integer PRIMARY KEY AUTOINCREMENT, TieuDe text, Ngay date, Tien float, MaLoai integer REFERENCES PHANLOAI(MaLoai))";
        db.execSQL(sql1);

        //database test sort tháng
        sql1 = "INSERT INTO GIAODICH('TieuDe','Ngay','Tien','MaLoai') VALUES ('Tiền nhà 1-4-2021','01-04-2021',10,1)";
        db.execSQL(sql1);
        sql1 = "INSERT INTO GIAODICH('TieuDe','Ngay','Tien','MaLoai') VALUES ('Tiền nhà 10-4-2021','10-04-2021',100,1)";
        db.execSQL(sql1);
        sql1 = "INSERT INTO GIAODICH('TieuDe','Ngay','Tien','MaLoai') VALUES ('Tiền nhà 20-4-2021','20-04-2021',200,1)";
        db.execSQL(sql1);
        sql1 = "INSERT INTO GIAODICH('TieuDe','Ngay','Tien','MaLoai') VALUES ('Tiền nhà 30-4-2021','30-04-2021',300,1)";
        db.execSQL(sql1);

        sql1 = "INSERT INTO GIAODICH('TieuDe','Ngay','Tien','MaLoai') VALUES ('Tiền nhà 1-8-2021','01-08-2021',10,2)";
        db.execSQL(sql1);
        sql1 = "INSERT INTO GIAODICH('TieuDe','Ngay','Tien','MaLoai') VALUES ('Tiền nhà 10-8-2021','10-08-2021',100,2)";
        db.execSQL(sql1);
        sql1 = "INSERT INTO GIAODICH('TieuDe','Ngay','Tien','MaLoai') VALUES ('Tiền nhà 20-8-2021','20-08-2021',200,2)";
        db.execSQL(sql1);
        sql1 = "INSERT INTO GIAODICH('TieuDe','Ngay','Tien','MaLoai') VALUES ('Tiền nhà 30-8-2021','30-08-2021',300,2)";
        db.execSQL(sql1);


        sql1 = "INSERT INTO GIAODICH('TieuDe','Ngay','Tien','MaLoai') VALUES ('Tiền nhà 1-4-2021','01-04-2021',10,3)";
        db.execSQL(sql1);
        sql1 = "INSERT INTO GIAODICH('TieuDe','Ngay','Tien','MaLoai') VALUES ('Tiền nhà 10-4-2021','10-04-2021',100,3)";
        db.execSQL(sql1);
        sql1 = "INSERT INTO GIAODICH('TieuDe','Ngay','Tien','MaLoai') VALUES ('Tiền nhà 20-4-2021','20-04-2021',200,3)";
        db.execSQL(sql1);
        sql1 = "INSERT INTO GIAODICH('TieuDe','Ngay','Tien','MaLoai') VALUES ('Tiền nhà 30-4-2021','30-04-2021',300,3)";
        db.execSQL(sql1);

        sql1 = "INSERT INTO GIAODICH('TieuDe','Ngay','Tien','MaLoai') VALUES ('Tiền nhà 1-8-2021','01-08-2021',105,4)";
        db.execSQL(sql1);
        sql1 = "INSERT INTO GIAODICH('TieuDe','Ngay','Tien','MaLoai') VALUES ('Tiền nhà 10-8-2021','10-08-2021',1000,4)";
        db.execSQL(sql1);
        sql1 = "INSERT INTO GIAODICH('TieuDe','Ngay','Tien','MaLoai') VALUES ('Tiền nhà 20-8-2021','20-08-2021',2040,4)";
        db.execSQL(sql1);
        sql1 = "INSERT INTO GIAODICH('TieuDe','Ngay','Tien','MaLoai') VALUES ('Tiền nhà 30-8-2021','30-08-2021',3500,4)";
        db.execSQL(sql1);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS PHANLOAI");
        db.execSQL("DROP TABLE IF EXISTS GIAODICH");
        onCreate(db);
    }

    public ArrayList<PhanLoai> getAllPhanLoai() {
        Cursor cursor = db.rawQuery("SELECT * FROM PHANLOAI WHERE TrangThai = 'Thu'", null);
        ArrayList<PhanLoai> list = new ArrayList<>();
        if (cursor.getCount() != 0) {
            cursor.moveToFirst();
            do {
                list.add(new PhanLoai(cursor.getInt(0), cursor.getString(1), cursor.getString(2)));
            } while (cursor.moveToNext());
        }
        return list;
    }

    public ArrayList<GiaoDich> getAllGiaoDich() {
        Cursor cursor = db.rawQuery("SELECT * FROM GIAODICH WHERE MaLoai IN (SELECT MaLoai FROM PHANLOAI WHERE TrangThai = 'Thu')", null);
        ArrayList<GiaoDich> list = new ArrayList<>();
        if (cursor.getCount() != 0) {
            cursor.moveToFirst();
            do {
                try {
                    list.add(new GiaoDich(cursor.getInt(0),
                            cursor.getString(1),
                            sdf.parse(cursor.getString(2)),
                            cursor.getFloat(3),
                            cursor.getInt(4)));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            } while (cursor.moveToNext());
        }
        return list;
    }

    //lấy thông tin loại khoản thu
    public PhanLoai getThongTinLoaiKhoanThu(int maLoai) {
        Cursor cursor = db.rawQuery("SELECT * FROM PHANLOAI WHERE MaLoai =" + maLoai + "", null);
        return new PhanLoai(cursor.getInt(0), cursor.getString(1), cursor.getString(2));
    }

    public void taoLoaiKhoanThu(String tenLoai) {
        ContentValues values = new ContentValues();
        values.put("tenLoai", tenLoai);
        values.put("trangThai", "Thu");
        db.insert("PHANLOAI", null, values);
    }

    public void taoKhoanThu(String tieuDe, String ngay, float tien, int maLoai) {
        ContentValues values = new ContentValues();
        values.put("TieuDe", tieuDe);
        values.put("Ngay", ngay);
        values.put("Tien", tien);
        values.put("MaLoai", maLoai);
        db.insert("GIAODICH", null, values);
    }

    public void capNhatLoaiKhoanThu(int maLoai, String tenLoai) {
        ContentValues values = new ContentValues();
        values.put("tenLoai", tenLoai);
        values.put("trangThai", "Thu");
        db.update("PHANLOAI", values, "maloai=?", new String[]{String.valueOf(maLoai)});
    }

    public void capNhatKhoanThu(int MaGD, String tieuDe, String ngay, float tien, int maLoai) {
        ContentValues values = new ContentValues();
        values.put("TieuDe", tieuDe);
        values.put("Ngay", ngay);
        values.put("Tien", tien);
        values.put("MaLoai", maLoai);
        db.update("GIAODICH", values, "MaGD=?", new String[]{String.valueOf(MaGD)});
    }

    public void xoaLoaiKhoanThu(int maLoai) {
        db.delete("PHANLOAI", "maloai=?", new String[]{String.valueOf(maLoai)});
    }

    public void xoaKhoanThu(int MaGD) {
        db.delete("GIAODICH", "MaGD=?", new String[]{String.valueOf(MaGD)});
    }

    public void xoaMaLoai(int maLoai){
        db.delete("GIAODICH", "maLoai=?", new String[]{String.valueOf(maLoai)});
    }

//    KHOẢNG CHI

    public ArrayList<PhanLoai> getAllPhanLoaiKC() {
        Cursor cursor = db.rawQuery("SELECT * FROM PHANLOAI WHERE TrangThai = 'Chi'", null);
        ArrayList<PhanLoai> list = new ArrayList<>();
        if (cursor.getCount() != 0) {
            cursor.moveToFirst();
            do {
                list.add(new PhanLoai(cursor.getInt(0), cursor.getString(1), cursor.getString(2)));
            } while (cursor.moveToNext());
        }
        return list;
    }

    public ArrayList<GiaoDich> getAllGiaoDichKC() {
        Cursor cursor = db.rawQuery("SELECT * FROM GIAODICH WHERE MaLoai IN (SELECT MaLoai FROM PHANLOAI WHERE TrangThai = 'Chi')", null);
        ArrayList<GiaoDich> list = new ArrayList<>();
        if (cursor.getCount() != 0) {
            cursor.moveToFirst();
            do {
                try {
                    list.add(new GiaoDich(cursor.getInt(0),
                            cursor.getString(1),
                            sdf.parse(cursor.getString(2)),
                            cursor.getFloat(3),
                            cursor.getInt(4)));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            } while (cursor.moveToNext());
        }
        return list;
    }

    public void taoLoaiKhoanChi(String tenLoai) {
        ContentValues values = new ContentValues();
        values.put("tenLoai", tenLoai);
        values.put("trangThai", "Chi");
        db.insert("PHANLOAI", null, values);
    }

    public void capNhatLoaiKhoanChi(int maLoai, String tenLoai) {
        ContentValues values = new ContentValues();
        values.put("tenLoai", tenLoai);
        values.put("trangThai", "Chi");
        db.update("PHANLOAI", values, "maloai=?", new String[]{String.valueOf(maLoai)});
    }


    //    CHART
    //lấy thông tin tổng khoản thu/khoản chi (thông kê
    public float[] getThongTinThuChi() {
        int thu = 0, chi = 0;
        //select sum(tien)
        //from giaodich
        //where maloai in (select maloai from phanloai where thangthai = 'thu')
        Cursor cursorThu = db.rawQuery("SELECT SUM(TIEN) FROM GIAODICH WHERE MaLoai IN (SELECT MaLoai FROM PHANLOAI WHERE TrangThai = 'Thu')", null);
        if (cursorThu.getCount() != 0) {
            cursorThu.moveToFirst();
            thu = cursorThu.getInt(0);
        }

        //select sum(tien)
        //from giaodich
        //where maloai in (select maloai from phanloai where thangthai = 'chi')
        Cursor cursorChi = db.rawQuery("SELECT SUM(TIEN) FROM GIAODICH WHERE MaLoai IN (SELECT MaLoai FROM PHANLOAI WHERE TrangThai = 'Chi')", null);
        if (cursorChi.getCount() != 0) {
            cursorChi.moveToFirst();
            chi = cursorChi.getInt(0);
        }
        float[] ketQua = new float[]{thu, chi};
        return ketQua;
    }

    public float[] getThongTinThuChiTheoThang(String month) {
        int thu = 0, chi = 0;
        //select sum(tien)
        //from giaodich
        //where maloai in (select maloai from phanloai where thangthai = 'thu')
        Cursor cursorThu = db.rawQuery("SELECT SUM(TIEN) FROM giaodich WHERE substr(ngay,7)||substr(ngay,4,2) = '2021"+month+"' AND MaLoai IN (SELECT MaLoai FROM PHANLOAI WHERE TrangThai = 'Thu')", null);
        if (cursorThu.getCount() != 0) {
            cursorThu.moveToFirst();
            thu = cursorThu.getInt(0);
        }

        //select sum(tien)
        //from giaodich
        //where maloai in (select maloai from phanloai where thangthai = 'chi')
        Cursor cursorChi = db.rawQuery("SELECT SUM(TIEN) FROM giaodich WHERE substr(ngay,7)||substr(ngay,4,2) = '2021"+month+"' AND MaLoai IN (SELECT MaLoai FROM PHANLOAI WHERE TrangThai = 'Chi')", null);
        if (cursorChi.getCount() != 0) {
            cursorChi.moveToFirst();
            chi = cursorChi.getInt(0);
        }
        float[] ketQua = new float[]{thu, chi};
        return ketQua;
    }

}
