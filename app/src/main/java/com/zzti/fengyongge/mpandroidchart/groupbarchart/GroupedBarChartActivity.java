package com.zzti.fengyongge.mpandroidchart.groupbarchart;

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
import com.zzti.fengyongge.mpandroidchart.R;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class GroupedBarChartActivity extends AppCompatActivity {

    BarChart barChart;
    BarChart barchartTemp;
    Context context;
    int number = 5;
    float maxY = 60;
    List<String> xAxisValues = new ArrayList<>();
    List<String> valOne = new ArrayList<>();
    List<String> valTwo = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grouped_barchart);

        context = GroupedBarChartActivity.this;
        barChart = (BarChart) findViewById(R.id.barchart);
        barchartTemp = (BarChart) findViewById(R.id.barchartTemp);

        //
        initData();
        initChart();
        initAxis();
        otherBackgroud(maxY,xAxisValues,valOne);
        setData();
    }



    void otherBackgroud(float maxY,List<String> xList,List<String> yList) {

        List<List<Float>> yBarDatas = new ArrayList<>();
        //2种直方图
        for (int i = 0; i < 2; i++) {
            List<Float> yData = new ArrayList<>();
            for (int j = 1; j <= yList.size(); j++) {
                yData.add( maxY);
            }
            yBarDatas.add(yData);
        }

        CombinedChartManagerOld combineChartManager = new CombinedChartManagerOld(GroupedBarChartActivity.this, barchartTemp);
        combineChartManager.showCombinedChart(maxY, xList, yBarDatas);

    }



    void initData(){
        xAxisValues.clear();
        xAxisValues.add("5分钟");
        xAxisValues.add("10分钟");
        xAxisValues.add("15分钟");
        xAxisValues.add("20分钟");
        xAxisValues.add("25分钟");
        xAxisValues.add("30分钟");
        xAxisValues.add("更多");
        //
        valOne.clear();
        valOne.add("10");
        valOne.add("20");
        valOne.add("30");
        valOne.add("40");
        valOne.add("50");
        valOne.add("49");
        valOne.add("40");
        //
        valTwo.clear();
        valTwo.add("60");
        valTwo.add("50");
        valTwo.add("30");
        valTwo.add("40");
        valTwo.add("20");
        valTwo.add("30");
        valTwo.add("30");
    }

    void initChart() {

        barChart.setDrawBarShadow(false);
        barChart.getDescription().setEnabled(false);
        barChart.setPinchZoom(false);
        barChart.setDrawGridBackground(false);
        barChart.getAxisRight().setEnabled(false);
        barChart.getLegend().setEnabled(false);
        barChart.setScaleEnabled(false);
    }


    void initAxis() {


        //设置X轴在底部
        XAxis xAxis = barChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        //展示x轴坐标
        xAxis.setDrawLabels(true);
        xAxis.setTextSize(9f);
        xAxis.setTextColor(ContextCompat.getColor(context, R.color.common_color_gray_99));
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
                if ((int) value >= 0 && (int) value < 7) {
                    xContent = xAxisValues.get((int) value);
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
        leftYAxis.setAxisLineColor(ContextCompat.getColor(context, R.color.common_color_gray_f1));
        //展示Y轴网格线./即横线
        leftYAxis.setDrawGridLines(true);
        leftYAxis.setGridColor(ContextCompat.getColor(context, R.color.common_color_gray_f1));
        leftYAxis.setGranularityEnabled(true);
        //设置y轴最大值和最小值，以及分割几份
        leftYAxis.setAxisMaximum(maxY);
        leftYAxis.setAxisMinimum(0f);
        leftYAxis.setLabelCount(number, true);
        //y轴坐标处理显示
        ValueFormatter yValueFormatter = new ValueFormatter() {
            @Override
            public String getAxisLabel(float value, AxisBase axis) {
                return new DecimalFormat("0").format(value);
            }
        };
        leftYAxis.setValueFormatter(yValueFormatter);
        barChart.invalidate();
    }


    void setData() {

        ArrayList<BarEntry> barOne = new ArrayList<>();
        ArrayList<BarEntry> barTwo = new ArrayList<>();

        for (int i = 0; i < valOne.size(); i++) {
            barOne.add(new BarEntry(i+0.3f , Float.parseFloat(valOne.get(i))));
            barTwo.add(new BarEntry(i+0.3f , Float.parseFloat(valTwo.get(i))));
        }

        BarDataSet set1 = new BarDataSet(barOne, "barOne");
        set1.setColor(ContextCompat.getColor(GroupedBarChartActivity.this, R.color.common_color_blue));
        BarDataSet set2 = new BarDataSet(barTwo, "barTwo");
        set2.setColor(ContextCompat.getColor(GroupedBarChartActivity.this, R.color.common_color_green_a2));


        set1.setHighlightEnabled(false);
        set1.setDrawValues(false);
        set2.setHighlightEnabled(false);
        set2.setDrawValues(false);

        ArrayList<IBarDataSet> dataSets = new ArrayList<IBarDataSet>();
        dataSets.add(set1);
        dataSets.add(set2);

        //(barSpace+barWidth)*柱形图数量+groupSpace=1
        BarData data = new BarData(dataSets);
        float groupSpace = 0.4f;
        float barSpace = 0.1f;
        float barWidth = 0.2f;

        data.setBarWidth(barWidth);
        barChart.setData(data);
        barChart.groupBars(0, groupSpace, barSpace);
        barChart.invalidate();
    }


}