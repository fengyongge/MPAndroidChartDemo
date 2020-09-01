package com.zzti.fengyongge.mpandroidchart.combinedchart;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;

import androidx.core.content.ContextCompat;

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
public class CombinedChartManager {

    private CombinedChart mCombinedChart;
    private YAxis leftYAxis;
    private YAxis rightYAxis;
    private XAxis xAxis;
    private Context context;

    public CombinedChartManager(Context context, CombinedChart combinedChart) {
        this.mCombinedChart = combinedChart;
        leftYAxis = mCombinedChart.getAxisLeft();
        rightYAxis = mCombinedChart.getAxisRight();
        xAxis = mCombinedChart.getXAxis();
        this.context = context;
    }

    /**
     * 初始化Chart
     */
    private void initChart(int number,float maxY) {
        //不显示描述内容
        mCombinedChart.getDescription().setEnabled(false);
        //设置x轴倾斜
        mCombinedChart.getXAxis().setLabelRotationAngle(30);
        //禁止x轴y轴同时进行缩放
        mCombinedChart.setPinchZoom(false);
        // enable scaling and dragging
        mCombinedChart.setDragEnabled(false);
        mCombinedChart.setScaleEnabled(false);

        mCombinedChart.setDrawOrder(new CombinedChart.DrawOrder[]{
                CombinedChart.DrawOrder.BAR,
                CombinedChart.DrawOrder.LINE
        });
        mCombinedChart.setBackgroundColor(Color.WHITE);
        mCombinedChart.setDrawGridBackground(false);
        mCombinedChart.setDrawBarShadow(false);
        mCombinedChart.setHighlightFullBarEnabled(false);
        //显示边界
        mCombinedChart.setDrawBorders(false);
        //图例说明
        Legend legend = mCombinedChart.getLegend();
        legend.setEnabled(false);
        //点击圆点，提示自定义view
        MyMarkerView myMarkerView = new MyMarkerView(context);
        myMarkerView.setChartView(mCombinedChart);
        mCombinedChart.setMarker(myMarkerView);
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

    /**
     * 设置X轴坐标值
     *
     * @param xAxisValues x轴坐标集合
     */
    public void setXAxis(final List<String> xAxisValues) {
        //设置X轴在底部
        XAxis xAxis = mCombinedChart.getXAxis();
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
        mCombinedChart.invalidate();
    }


    /**
     * 得到折线图(多条)
     *
     * @param lineChartYs 折线Y轴值
//     * @param lineNames   折线图名字
     * @param lineColors  折线颜色
     * @return
     */
    private LineData getLineData(List<List<Float>> lineChartYs, List<Integer> lineColors) {
        LineData lineData = new LineData();


        for (int i = 0; i < lineChartYs.size(); i++) {
            ArrayList<Entry> yValues = new ArrayList<>();
            for (int j = 0; j < lineChartYs.get(i).size(); j++) {
                //x轴第一个坐标的y值默认在y轴上，为了居中所以偏移0.5
                yValues.add(new Entry(j+0.5f, lineChartYs.get(i).get(j)));
            }
            LineDataSet dataSet = new LineDataSet(yValues, "");
            lineData.addDataSet(initLineDataSet(dataSet,lineColors.get(i)));
        }
        return lineData;
    }


    LineDataSet initLineDataSet(LineDataSet lineDataSet, int color) {
        //是否显示坐标的值
        lineDataSet.setDrawValues(false);
        lineDataSet.setAxisDependency(YAxis.AxisDependency.LEFT);
        //设置折线图填充
        lineDataSet.setDrawFilled(false);
        lineDataSet.setFillAlpha(128);
        //设置折线背景
        //折线的颜色和宽度
        lineDataSet.setColor(color);
        lineDataSet.setLineWidth(1.5f);
        lineDataSet.setDrawCircleHole(true);
        //设置圆形
        lineDataSet.setCircleRadius(3f);
        lineDataSet.setCircleColor(ContextCompat.getColor(context,R.color.common_color_white));
        lineDataSet.setCircleHoleRadius(2f);
        lineDataSet.setCircleHoleColor(color);
        lineDataSet.setFillAlpha(65);
        //设置点击点的背景
        lineDataSet.setHighLightColor(Color.TRANSPARENT);

        return lineDataSet;
    }

    /**
     * 得到柱状图(单个)
     *
     * @param barChartY Y轴值
//     * @param barName   柱状图名字
//     * @param barColor  柱状图颜色
     * @return
     */

    private BarData getBarData(List<Float> barChartY) {
        BarData barData = new BarData();
        ArrayList<BarEntry> yValues = new ArrayList<>();
        for (int i = 0; i < barChartY.size(); i++) {
            yValues.add(new BarEntry(i, barChartY.get(i)));
        }
        BarDataSet barDataSet = new BarDataSet(yValues, "");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            barDataSet.setGradientColor(ContextCompat.getColor(context,R.color.common_color_blue_start_eb), ContextCompat.getColor(context,R.color.common_color_blue_end_eb));
        }
        barDataSet.setAxisDependency(YAxis.AxisDependency.LEFT);
        //展示文字
        barDataSet.setDrawValues(false);
        barData.addDataSet(barDataSet);

        //以下是为了解决 柱状图 左右两边只显示了一半的问题 根据实际情况 而定
        xAxis.setAxisMinimum(-0.5f);
        xAxis.setAxisMaximum((float) (barChartY.size()- 0.5));
        return barData;
    }


    /**
     * 得到柱状图(2条)
     *
     * @param barChartYs Y轴值
//     * @param barNames   柱状图名字
//     * @param barColors  柱状图颜色
     * @return
     */

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
        //需要显示柱状图的类别 数量
        int amount = barChartYs.size();
        float barSpace = (float) ((1 - 0.01) / 7);
        //柱状图组之间的间距，不能设置0
        float groupSpace = 0.0001f;
        //柱状图宽度
        // 1.00 即100% 按照百分百布局
        float barWidth = (float) (1-groupSpace / 7 );
        barData.setBarWidth(barWidth);
        //(起始点、柱状图组间距、柱状图之间间距)
        barData.groupBars(0, groupSpace, 0f);
        return barData;
    }


    /**
     * 显示混合图(柱状图+折线图)
     *
     * @param xAxisValues X轴坐标
     * @param barChartYs  柱状图Y轴值
     * @param lineChartYs 折线图Y轴值
//     * @param barNames    柱状图名字
//     * @param lineNames   折线图名字
//     * @param barColors   柱状图颜色
     * @param lineColors  折线图颜色
     */

    public void showCombinedChart(float maxY,
                                  List<String> xAxisValues, List<List<Float>> barChartYs, List<List<Float>> lineChartYs, List<Integer> lineColors) {
        initChart(5,maxY);
        setXAxis(xAxisValues);

        CombinedData combinedData = new CombinedData();
        combinedData.setData(getBarDataMutly(barChartYs));
        combinedData.setData(getLineData(lineChartYs,lineColors));

        mCombinedChart.setData(combinedData);
        mCombinedChart.invalidate();
    }


}