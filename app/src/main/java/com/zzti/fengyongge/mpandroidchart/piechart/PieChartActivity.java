package com.zzti.fengyongge.mpandroidchart.piechart;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.zzti.fengyongge.mpandroidchart.R;
import com.zzti.fengyongge.mpandroidchart.bean.PieBean;

import java.util.ArrayList;
import java.util.List;

public class PieChartActivity extends AppCompatActivity {

    private PieChart pieChart;
    //饼图占比
    private int pieMaxNum = 0;
    private int pieSumNum = 0;
    private RecyclerView pieRecyclerView;
    private PieAdapter pieAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pie_chart);

        pieChart = findViewById(R.id.pieChartView);
        pieRecyclerView = findViewById(R.id.pieRecyclerView);
        pieRecyclerView.setLayoutManager(new LinearLayoutManager(PieChartActivity.this));
        pieAdapter = new PieAdapter();
        pieRecyclerView.setAdapter(pieAdapter);

        initPieChartData();
    }


    List<PieBean> dataList = new ArrayList<>();
    void initPieChartData() {
        dataList.clear();
        PieBean pieBean1 = new PieBean();
        pieBean1.setLevel1Id("1");
        pieBean1.setOrderNum("3");
        pieBean1.setTypeName("报事报修");
        dataList.add(pieBean1);
        PieBean pieBean2 = new PieBean();
        pieBean2.setLevel1Id("2");
        pieBean2.setOrderNum("6");
        pieBean2.setTypeName("问题投诉");
        dataList.add(pieBean2);
        PieBean pieBean3= new PieBean();
        pieBean3.setLevel1Id("3");
        pieBean3.setOrderNum("9");
        pieBean3.setTypeName("信息咨询");
        dataList.add(pieBean3);

        PieBean pieBean4= new PieBean();
        pieBean4.setLevel1Id("4");
        pieBean4.setOrderNum("27");
        pieBean4.setTypeName("紧急工单");
        dataList.add(pieBean4);


        //
        for (int i = 0; i < dataList.size(); i++) {
            pieSumNum += (int) Float.parseFloat(dataList.get(i).getOrderNum());
        }
        for (int i = 0; i < dataList.size(); i++) {
            if (pieMaxNum < (int) Float.parseFloat(dataList.get(i).getOrderNum())) {
                pieMaxNum = (int) Float.parseFloat(dataList.get(i).getOrderNum());
            }
        }
        pieAdapter.setNewData(dataList);
        initPieChart(pieSumNum + "单", dataList);
    }

    void initPieChart(String centerTitle, List<PieBean> dataList) {
        //不展示右下角的说明
        pieChart.getDescription().setEnabled(false);
        //设置pieChart图表上下左右的偏移，类似于外边距
        pieChart.setExtraOffsets(0, 0, 0, 0);
        //设置pieChart图表转动阻力摩擦系数[0,1]
        pieChart.setDragDecelerationFrictionCoef(0.95f);
        //是否显示中间的内部圆环，设置这个下面的才有意义
        pieChart.setDrawHoleEnabled(true);
        //设置内部圆半径，内部圆的颜色
        pieChart.setHoleRadius(70f);
        pieChart.setHoleColor(Color.WHITE);
        //设置PieChart内部透明圆与内部圆间距(31f-28f)透明度[0~255]数值越小越透明
        pieChart.setTransparentCircleAlpha(0);
        //设置内部圆外的半透明的圆半径，半透明圆的颜色
        pieChart.setTransparentCircleRadius(0f);
        pieChart.setTransparentCircleColor(Color.WHITE);
        //是否绘制PieChart内部中心文字
        pieChart.setDrawCenterText(true);
        pieChart.setCenterText(centerTitle);
        //没有数据默认值
        pieChart.setNoDataText("暂无数据");
        //初始旋转角度
        pieChart.setRotationAngle(0);
        //设置手动旋转
        pieChart.setRotationEnabled(false);
        //设置凸起
        pieChart.setHighlightPerTapEnabled(true);
        //自带比例图说明
        Legend l = pieChart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        l.setOrientation(Legend.LegendOrientation.VERTICAL);
        l.setDrawInside(false);
        //比例块字体大小，通过字号隐藏
        l.setFormSize(0);
        //设置与饼图的距离
        l.setXEntrySpace(7f);
        l.setYEntrySpace(0f);
        l.setYOffset(0f);
        //设置数据以及颜色
        ArrayList<Integer> colors = new ArrayList<>();
        colors.add(getResources().getColor(R.color.common_color_blue_f9));
        colors.add(getResources().getColor(R.color.common_color_red_3b));
        colors.add(getResources().getColor(R.color.common_color_yellow_43));
        colors.add(getResources().getColor(R.color.common_color_green_a2));
        ArrayList<PieEntry> entries = new ArrayList<>();
        for (int i = 0; i < dataList.size(); i++) {
            entries.add(new PieEntry(Float.parseFloat(dataList.get(i).getOrderNum()), "", ""));
        }
        PieDataSet dataSet = new PieDataSet(entries, "");
        dataSet.setColors(colors);
        PieData data = new PieData(dataSet);
        //设置显示比例
        pieChart.setUsePercentValues(true);
        //设置扇形上面展示的百分比，通过设置字体字号，隐藏文字
        data.setValueFormatter(new PercentFormatter(pieChart));
        data.setValueTextSize(0f);
        data.setValueTextColor(Color.WHITE);
        pieChart.setData(data);
        // undo all highlights
        pieChart.highlightValues(null);
        pieChart.invalidate();
    }


    /**
     * 饼图占比adapter
     */
    public class PieAdapter extends BaseQuickAdapter<PieBean, BaseViewHolder> {

        public PieAdapter() {
            super(R.layout.workorder_fragment_report_pie_item);
        }

        @Override
        protected void convert(BaseViewHolder helper, PieBean item) {

            View view = helper.getView(R.id.view);
            TextView tvTypeName = helper.getView(R.id.tvTypeName);
            TextView tvPieNum = helper.getView(R.id.tvPieNum);
            TextView tvNumPercent = helper.getView(R.id.tvNumPercent);

            tvTypeName.setText(item.getTypeName());
            tvPieNum.setText((int) (Float.parseFloat(item.getOrderNum()))+ "单");
            int progress = (int) (Float.parseFloat(item.getOrderNum()) * 1.0f / pieSumNum * 100);
            tvNumPercent.setText(progress + "%");
            if (item.getLevel1Id().equals("1")) {
                view.setBackgroundResource(R.color.common_color_blue_f9);
            } else if (item.getLevel1Id().equals("2")) {
                view.setBackgroundResource(R.color.common_color_red_3b);
            } else if (item.getLevel1Id().equals("3")) {
                view.setBackgroundResource(R.color.common_color_yellow_43);
            } else if (item.getLevel1Id().equals("4")) {
                view.setBackgroundResource(R.color.common_color_green_a2);
            }

        }
    }
}