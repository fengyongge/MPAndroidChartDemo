package com.zzti.fengyongge.mpandroidchart.groupbarchart;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;

import androidx.core.content.ContextCompat;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.CombinedChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.CombinedData;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.zzti.fengyongge.mpandroidchart.R;
import com.zzti.fengyongge.mpandroidchart.combinedchart.MyMarkerView;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * describe
 * 折线柱状图组合
 * @author fengyongge(fengyongge98@gmail.com)
 * @version V1.0
 * @date 2020/8/6
 */
public class CombinedChartManagerOld {

    private BarChart mCombinedChart;
    private YAxis leftYAxis;
    private YAxis rightYAxis;
    private XAxis xAxis;
    private Context context;

    public CombinedChartManagerOld(Context context, BarChart combinedChart) {
        this.mCombinedChart = combinedChart;
        leftYAxis = mCombinedChart.getAxisLeft();
        rightYAxis = mCombinedChart.getAxisRight();
        xAxis = mCombinedChart.getXAxis();
        this.context = context;
    }


    public void showCombinedChart(float maxY,
                                  List<String> xAxisValues, List<List<Float>> barChartYs) {
        initChart(5,maxY);
        initAxis(xAxisValues);
        mCombinedChart.setData(getBarDataMutly(barChartYs));
        mCombinedChart.invalidate();
    }

    /**
     * 初始化Chart
     */
    private void initChart(int number,float maxY) {
        mCombinedChart.setDrawBarShadow(false);
        mCombinedChart.getDescription().setEnabled(false);
        mCombinedChart.setPinchZoom(false);
        mCombinedChart.setDrawGridBackground(false);
        mCombinedChart.getAxisRight().setEnabled(false);
        mCombinedChart.getLegend().setEnabled(false);
        mCombinedChart.setScaleEnabled(false);
    }

    /**
     * 设置X轴坐标值
     *
     * @param xAxisValues x轴坐标集合
     */
    public void initAxis(final List<String> xAxisValues) {
        int number = 5;
        float maxY = 60;

        //设置X轴在底部
        XAxis xAxis = mCombinedChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        //展示x轴坐标
        xAxis.setDrawLabels(false);
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


        YAxis rightYAxis = mCombinedChart.getAxisRight();
        YAxis leftYAxis = mCombinedChart.getAxisLeft();
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
        ValueFormatter yValueFormatter = new ValueFormatter() {
            @Override
            public String getAxisLabel(float value, AxisBase axis) {
                return new DecimalFormat("0").format(value);
            }
        };
        leftYAxis.setValueFormatter(yValueFormatter);

        mCombinedChart.invalidate();
    }


    private BarData getBarDataMutly(List<List<Float>> barChartYs) {
        List<Integer> barColors = new ArrayList<>();
        barColors.add(ContextCompat.getColor(context,R.color.common_color_ED));
        barColors.add(ContextCompat.getColor(context,R.color.common_color_blue));

        List<IBarDataSet> lists = new ArrayList<>();
        for (int i = 0; i < barChartYs.size(); i++) {
            ArrayList<BarEntry> entries = new ArrayList<>();

            for (int j = 0; j < barChartYs.get(i).size(); j++) {
                entries.add(new BarEntry(j, barChartYs.get(i).get(j)));
            }

            BarDataSet barDataSet = new BarDataSet(entries, "");
            if(i ==0){
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    barDataSet.setGradientColor(ContextCompat.getColor(context,R.color.common_color_blue_start_eb), ContextCompat.getColor(context,R.color.common_color_blue_end_eb));
                }
            }else{
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    barDataSet.setGradientColor(ContextCompat.getColor(context,R.color.common_color_white_start_eb), ContextCompat.getColor(context,R.color.common_color_white_end_eb));
                }
            }
            barDataSet.setAxisDependency(YAxis.AxisDependency.LEFT);
            //设置点击柱之后的透明度
            barDataSet.setHighLightAlpha(0);
            //是否显示柱上面文字
            barDataSet.setDrawValues(false);
            lists.add(barDataSet);
        }

        BarData barData = new BarData(lists);

        //(barSpace+barWidth)*柱形图数量+groupSpace=1
        float groupSpace = 0.000001f;
        barData.setBarWidth(1);

        barData.groupBars(0, groupSpace, 0f);
        return barData;
    }


}