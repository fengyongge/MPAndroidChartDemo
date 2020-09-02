package com.zzti.fengyongge.mpandroidchart;

import android.content.Context;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class GroupedBarChartActivity extends AppCompatActivity {

    BarChart barChart;
    Context context;
    int number = 5;
    float maxY = 60;
    List<String> xAxisValues = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grouped_barchart);

        context = GroupedBarChartActivity.this;
        barChart = (BarChart) findViewById(R.id.barchart);

        initChart();


    }

    void setXy(){
        xAxisValues.clear();
        xAxisValues.add("5分钟");
        xAxisValues.add("10分钟");
        xAxisValues.add("15分钟");
        xAxisValues.add("20分钟");
        xAxisValues.add("25分钟");
        xAxisValues.add("30分钟");
        xAxisValues.add("更多");

        //设置X轴在底部
        XAxis xAxis = barChart.getXAxis();
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


        YAxis rightYAxis = barChart.getAxisRight();
        YAxis leftYAxis = barChart.getAxisLeft();
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
        barChart.setDrawBarShadow(false);
        barChart.getDescription().setEnabled(false);
        barChart.setPinchZoom(false);
        barChart.setDrawGridBackground(false);
        barChart.getAxisRight().setEnabled(false);
        barChart.getLegend().setEnabled(false);

        setXy();

        float[] valOne = {10, 20, 30, 40, 50,49,40};
        float[] valTwo = {60, 50, 40, 30, 20,20,30};

        ArrayList<BarEntry> barOne = new ArrayList<>();
        ArrayList<BarEntry> barTwo = new ArrayList<>();

        for (int i = 0; i < valOne.length; i++) {
            barOne.add(new BarEntry(i+0.3f, valOne[i]));
            barTwo.add(new BarEntry(i+0.3f, valTwo[i]));
        }

        BarDataSet set1 = new BarDataSet(barOne, "barOne");
        set1.setColor(ContextCompat.getColor(GroupedBarChartActivity.this,R.color.common_color_blue));
        BarDataSet set2 = new BarDataSet(barTwo, "barTwo");
        set2.setColor(ContextCompat.getColor(GroupedBarChartActivity.this,R.color.common_color_green_a2));


        set1.setHighlightEnabled(false);
        set1.setDrawValues(false);
        set2.setHighlightEnabled(false);
        set2.setDrawValues(false);

        ArrayList<IBarDataSet> dataSets = new ArrayList<IBarDataSet>();
        dataSets.add(set1);
        dataSets.add(set2);

        //(barSpace+barWidth)*柱形图数量+groupSpace=1

        BarData data = new BarData(dataSets);
        float groupSpace = 0.6f;
        float barSpace = 0.04f;
        float barWidth = 0.15f;

        // (barSpace + barWidth) * 5 + groupSpace = 1
        // multiplied by 5 because there are 5 five bars
        // labels will be centered as long as the equation is satisfied
        data.setBarWidth(barWidth);
        barChart.setData(data);
        barChart.setScaleEnabled(false);
        barChart.groupBars(0f, groupSpace, barSpace);
        barChart.invalidate();
    }


}