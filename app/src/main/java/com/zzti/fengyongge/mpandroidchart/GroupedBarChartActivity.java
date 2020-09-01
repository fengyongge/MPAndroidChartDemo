package com.zzti.fengyongge.mpandroidchart;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.BarLineChartBase;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class GroupedBarChartActivity extends AppCompatActivity {

    BarChart mChart;
    Context context;
    int number = 5;
    float maxY = 60;
    List<String> xAxisValues = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grouped_barchart);

        context = GroupedBarChartActivity.this;
        mChart = (BarChart) findViewById(R.id.barchart);

        initChart();


    }

    void setXy(){
        xAxisValues.clear();
        for (int i = 1; i <=7; i++) {
            xAxisValues.add("Name"+i);
        }

//        // empty labels so that the names are spread evenly
//        final String[] labels = {"Name1", "Name2", "Name3", "Name4", "Name5","Name6", "Name7"};
//        XAxis xAxis = mChart.getXAxis();
//        xAxis.setCenterAxisLabels(true);
//        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
//        xAxis.setDrawGridLines(false);
//        xAxis.setGranularity(1f); // only intervals of 1 day
//        xAxis.setTextColor(Color.BLACK);
//        xAxis.setTextSize(12);
//        xAxis.setAxisLineColor(Color.WHITE);
//        xAxis.setAxisMinimum(1f);

        //设置X轴在底部
        XAxis xAxis = mChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        //展示x轴坐标
        xAxis.setDrawLabels(true);
        xAxis.setTextSize(9f);
        xAxis.setTextColor(ContextCompat.getColor(context,R.color.common_color_gray_99));
        //隐藏X轴水平线
        xAxis.setDrawAxisLine(false);
        //隐藏网格线，即竖线
        xAxis.setDrawGridLines(false);
        //设置x轴标签数，默认为6个,设置间隔
        xAxis.setLabelCount(7);
        xAxis.setGranularity(1f);
        xAxis.setAxisMaximum(7f);
        //将X轴的值显示在中央
        xAxis.setCenterAxisLabels(true);
        //x轴坐标处理显示(大于自定义的不显示)
        ValueFormatter xValueFormatter = new ValueFormatter() {
            @Override
            public String getAxisLabel(float value, AxisBase axis) {
                String xContent = "";
                //防止出线-1
                if((int)value>=0&&(int)value<7){
                    xContent =  xAxisValues.get((int)value);
                }
                return xContent;
            }
        };
        xAxis.setValueFormatter(xValueFormatter);


        YAxis rightYAxis = mChart.getAxisRight();
        YAxis leftYAxis = mChart.getAxisLeft();
        //Y轴设置
        rightYAxis.setEnabled(false);
        //是否绘制0
        leftYAxis.setDrawLabels(true);
        leftYAxis.setTextColor(ContextCompat.getColor(context, R.color.common_color_gray_99));
        //设置y轴颜色
        leftYAxis.setAxisLineColor(ContextCompat.getColor(context,R.color.common_color_gray_f1));
        //展示Y轴网格线./即横线
        leftYAxis.setDrawGridLines(true);
        leftYAxis.setGridColor(ContextCompat.getColor(context,R.color.common_color_gray_f1));
        leftYAxis.setGranularityEnabled(true);
        //设置y轴最大值和最小值，以及分割几份
        leftYAxis.setAxisMaximum(maxY);
        leftYAxis.setAxisMinimum(0f);
        leftYAxis.setLabelCount(number, true);
        //y轴坐标处理显示
        ValueFormatter yValueFormatter = new ValueFormatter(){
            @Override
            public String getAxisLabel(float value, AxisBase axis) {
                return new DecimalFormat("0").format(value);
            }
        };
        leftYAxis.setValueFormatter(yValueFormatter);
    }



    void initChart(){
        mChart.setDrawBarShadow(false);
        mChart.getDescription().setEnabled(false);
        mChart.setPinchZoom(false);
        mChart.setDrawGridBackground(false);
        mChart.getAxisRight().setEnabled(false);
        mChart.getLegend().setEnabled(false);

        setXy();


        float[] valOne = {10, 20, 30, 40, 50,49,40};
        float[] valTwo = {60, 50, 40, 30, 20,20,30};

        ArrayList<BarEntry> barOne = new ArrayList<>();
        ArrayList<BarEntry> barTwo = new ArrayList<>();

        for (int i = 0; i < valOne.length; i++) {
            barOne.add(new BarEntry(i, valOne[i]));
            barTwo.add(new BarEntry(i, valTwo[i]));
        }

        BarDataSet set1 = new BarDataSet(barOne, "barOne");
        set1.setColor(Color.BLUE);
        BarDataSet set2 = new BarDataSet(barTwo, "barTwo");
        set2.setColor(Color.MAGENTA);

        set1.setHighlightEnabled(false);
        set1.setDrawValues(false);
        set2.setHighlightEnabled(false);
        set2.setDrawValues(false);


        ArrayList<IBarDataSet> dataSets = new ArrayList<IBarDataSet>();
        dataSets.add(set1);
        dataSets.add(set2);

        //(barSpace+barWidth)*柱形图数量+groupSpace=1

        BarData data = new BarData(dataSets);
//        float groupSpace = 0.16f;
//        float barSpace = 0f;
//        float barWidth = 0.06f;

        //柱状图组之间的间距，不能设置0
        float groupSpace = 0.4f;
        //柱状图宽度
        // 1.00 即100% 按照百分百布局
        float barWidth = (float) ((1-groupSpace) / 14 );
        float barSpace = 0f;



        // (barSpace + barWidth) * 5 + groupSpace = 1
        // multiplied by 5 because there are 5 five bars
        // labels will be centered as long as the equation is satisfied
        data.setBarWidth(barWidth);
        // so that the entire chart is shown when scrolled from right to left
//        xAxis.setAxisMaximum(labels.length - 1.1f);
        mChart.setData(data);
        mChart.setScaleEnabled(false);
//        mChart.setVisibleXRangeMaximum(1f);
        mChart.groupBars(1f, groupSpace, barSpace);
        mChart.invalidate();
    }


}