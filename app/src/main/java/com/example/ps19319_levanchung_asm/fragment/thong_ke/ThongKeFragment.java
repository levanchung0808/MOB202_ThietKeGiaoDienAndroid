package com.example.ps19319_levanchung_asm.fragment.thong_ke;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.ps19319_levanchung_asm.R;
import com.example.ps19319_levanchung_asm.database.DbHelper;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.MPPointF;

import java.util.ArrayList;

public class ThongKeFragment extends Fragment implements OnChartValueSelectedListener {

    DbHelper dbHelper;
    PieChart mChart;
    Spinner spnSort;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_thong_ke,container,false);
        spnSort = view.findViewById(R.id.spnSort);
        String[] arrMonth = {"01","02","03","04","05","06","07","08","09","10","11","12"};
        ArrayAdapter adapter = new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1,arrMonth);
        spnSort.setAdapter(adapter);

        dbHelper = new DbHelper(getActivity());

        mChart = view.findViewById(R.id.piechart);
        mChart.setRotationEnabled(true);
        mChart.setDescription(null);
        mChart.setTransparentCircleAlpha(0);
        mChart.setCenterText("Thống kê");
        mChart.setCenterTextSize(20);
        mChart.setDrawEntryLabels(true);
        mChart.setOnChartValueSelectedListener(this);

        spnSort.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mChart.notifyDataSetChanged();
                mChart.invalidate();
                addDataSet1(mChart,arrMonth[position]+"");
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        return view;
    }

    // Thực hiện công việc khi nhấn vào các giá trị trong chart
    //    Lấy giá trị: e.getY()
    //    Lấy vị trí: h.getX()
    //    Lấy vị trí trong Dataset: h.getDataSetIndex()
    @Override
    public void onValueSelected(Entry e, Highlight h) {
        Toast.makeText(getActivity(), "Value: "
                + e.getY()
                + h.getDataSetIndex(), Toast.LENGTH_SHORT).show();
    }

    // Thực hiện công việc khi không nhấn vào các giá trị trong chart
    @Override
    public void onNothingSelected() {

    }

    private void addDataSet1(PieChart pieChart, String month) {
        ArrayList<PieEntry> yEntrys = new ArrayList<>();
        float[] yData = dbHelper.getThongTinThuChiTheoThang(month);
        String[] xData = { "Khoản thu", "Khoản chi" };
        for (int i = 0; i < yData.length;i++){
            yEntrys.add(new PieEntry(yData[i],xData[i]));
        }
        PieDataSet pieDataSet = new PieDataSet(yEntrys,"Results:");
        pieDataSet.setSliceSpace(3);
        pieDataSet.setValueTextSize(12);
        pieDataSet.setSelectionShift(5f);
        pieDataSet.setValueTextColor(Color.WHITE);

        ArrayList<Integer> colors=new ArrayList<>();
        colors.add(Color.parseColor("#4CAF50"));
        colors.add(Color.RED);

        pieDataSet.setColors(colors);
        Legend legend = pieChart.getLegend();
        legend.setTextSize(14f);
        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        legend.setOrientation(Legend.LegendOrientation.VERTICAL);

        PieData pieData=new PieData(pieDataSet);
        pieChart.setData(pieData);
        pieChart.highlightValues(null);
        pieChart.invalidate();
    }

}
