package com.zzti.fengyongge.mpandroidchart.linechart;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.widget.SeekBar;

import androidx.appcompat.app.AppCompatActivity;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.zzti.fengyongge.mpandroidchart.R;
import com.zzti.fengyongge.mpandroidchart.bean.LineChartBean;

import java.util.ArrayList;
import java.util.List;


public class LineChartActivity extends AppCompatActivity implements SeekBar.OnSeekBarChangeListener,
        OnChartValueSelectedListener {

    protected Typeface mTfRegular;
    protected Typeface mTfLight;
    LineChart mChart;

    List<LineChartBean> list = new ArrayList<>();
    String[] values =  {};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_line_chart);

        mChart = findViewById(R.id.chart1);

        mTfRegular = Typeface.createFromAsset(LineChartActivity.this.getAssets(), "OpenSans-Regular.ttf");
        mTfLight = Typeface.createFromAsset(LineChartActivity.this.getAssets(), "OpenSans-Light.ttf");

        initData();
        initChart(mChart,list,"200");
    }

    public void initData(){

        LineChartBean lineChartBean = new LineChartBean();
        lineChartBean.setxString("04-19");
        list.add(lineChartBean);

        LineChartBean lineChartBean1 = new LineChartBean();
        lineChartBean1.setxString("04-20");
        list.add(lineChartBean1);

        LineChartBean lineChartBean2 = new LineChartBean();
        lineChartBean2.setxString("04-21");
        list.add(lineChartBean2);

        LineChartBean lineChartBean3 = new LineChartBean();
        lineChartBean3.setxString("04-22");
        list.add(lineChartBean3);

        LineChartBean lineChartBean4 = new LineChartBean();
        lineChartBean4.setxString("04-23");
        list.add(lineChartBean4);

        LineChartBean lineChartBean5 = new LineChartBean();
        lineChartBean5.setxString("04-24");
        list.add(lineChartBean5);

        LineChartBean lineChartBean6 = new LineChartBean();
        lineChartBean6.setxString("04-25");
        list.add(lineChartBean6);
        LineChartBean lineChartBean7 = new LineChartBean();
        lineChartBean7.setxString("04-26");
        list.add(lineChartBean7);
        LineChartBean lineChartBean8 = new LineChartBean();
        lineChartBean8.setxString("04-27");
        list.add(lineChartBean8);
        LineChartBean lineChartBean9 = new LineChartBean();
        lineChartBean9.setxString("04-28");
        list.add(lineChartBean9);
        LineChartBean lineChartBean10 = new LineChartBean();
        lineChartBean10.setxString("04-29");
        list.add(lineChartBean10);
    }



    public void initChart(LineChart mChart, List<LineChartBean> list, String stringMax) {
        values = new String[list.size()];
        for (int i = 0; i < list.size(); i++) {
            values[i] = new String(list.get(i).getxString());
        }

//        mChart.setOnChartValueSelectedListener(this);
        // no description text
        mChart.getDescription().setEnabled(false);

        // enable touch gestures
        mChart.setTouchEnabled(true);

        mChart.setDragDecelerationFrictionCoef(0.9f);

        // enable scaling and dragging
        mChart.setDragEnabled(true);
        mChart.setScaleEnabled(false);
        mChart.setDrawGridBackground(false);
        mChart.setHighlightPerDragEnabled(true);

        // if disabled, scaling can be done on x- and y-axis separately
        mChart.setPinchZoom(true);

        // set an alternative background color
        mChart.setBackgroundColor(Color.WHITE);

        mChart.getXAxis().setAxisMinValue(0.0f);//设置X轴的最小值
        mChart.getAxisRight().setEnabled(false);//隐藏右边的y轴

        // add data
        setData(mChart,list, 10);

        mChart.animateX(2500);

//        //当值被选中的时候，执行操作显示一个Toast
//        mChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
//            @Override
//            public void onValueSelected(Entry e, Highlight h) {
//                Toast.makeText(LineChartActivity.this, String.valueOf(e.toString()), Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void onNothingSelected() {
//                // TODO Auto-generated method stub
//
//            }
//        });

        // get the legend (only possible after setting data)
        Legend l = mChart.getLegend();
        // modify the legend ...
        l.setForm(Legend.LegendForm.LINE);
        l.setTypeface(mTfLight);
        l.setTextSize(11f);
        l.setTextColor(Color.GRAY);
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        l.setDrawInside(false);

        //x坐标
        XAxis xAxis = mChart.getXAxis();
        xAxis.setTypeface(mTfLight);
        xAxis.setTextSize(11f);
        xAxis.setTextColor(Color.GRAY);
        xAxis.setDrawGridLines(false);//设置是否现实x网格
        xAxis.setDrawAxisLine(false);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        //自定义x轴显示
//        MyXFormatter formatter = new MyXFormatter(values);
//        xAxis.setLabelCount(7);//显示x个数
//        xAxis.setValueFormatter(formatter);

        //Y坐标左边
        YAxis leftAxis = mChart.getAxisLeft();
        leftAxis.setTypeface(mTfLight);
        leftAxis.setTextColor(ColorTemplate.getHoloBlue());
        leftAxis.setAxisMaximum(Float.parseFloat(stringMax));//设置最大显示多少
        leftAxis.setAxisMinimum(0f);
        leftAxis.setLabelCount(7);//显示y个数
        leftAxis.setDrawGridLines(true);
        leftAxis.setGranularityEnabled(true);

//        YAxis rightAxis = mChart.getAxisRight();
//        rightAxis.setTypeface(mTfLight);
//        rightAxis.setTextColor(Color.RED);
//        rightAxis.setAxisMaximum(0);
//        rightAxis.setAxisMinimum(0);
//        rightAxis.setDrawGridLines(false);
//        rightAxis.setDrawZeroLine(false);
//        rightAxis.setGranularityEnabled(false);


        // 设置MarkerView
//        MarkerView mv = new MyMarkerView(LineChartActivity.this, R.layout.custom_marker_view);
//        mChart.setMarker(mv);

    }

    private void setData(LineChart mChart,List<LineChartBean> list, float range) {

        ArrayList<Entry> yVals1 = new ArrayList<Entry>();

        for (int i = 0; i < list.size(); i++) {
            float mult = range / 2f;
            float val = (float) (Math.random() * mult) + 100;
            yVals1.add(new Entry(Float.parseFloat(String.valueOf(i)), val));

        }

        ArrayList<Entry> yVals2 = new ArrayList<Entry>();
        for (int i = 0; i < list.size(); i++) {
            float mult = range;
            float val = (float) (Math.random() * mult) + 150;
            yVals2.add(new Entry(Float.parseFloat(String.valueOf(i)), val));
        }

        ArrayList<Entry> yVals3 = new ArrayList<Entry>();
        for (int i = 0; i < list.size(); i++) {
            float mult = range;
            float val = (float) (Math.random() * mult) + 120;
            yVals3.add(new Entry(Float.parseFloat(String.valueOf(i)), val));
        }

        LineDataSet set1, set2, set3;

        if (mChart.getData() != null &&
                mChart.getData().getDataSetCount() > 0) {

            set1 = (LineDataSet) mChart.getData().getDataSetByIndex(0);
            set2 = (LineDataSet) mChart.getData().getDataSetByIndex(1);
            set3 = (LineDataSet) mChart.getData().getDataSetByIndex(2);
            set1.setValues(yVals1);
            set2.setValues(yVals2);
            set3.setValues(yVals3);

            mChart.getData().notifyDataChanged();
            mChart.notifyDataSetChanged();
        } else {
            // create a dataset and give it a type
            set1 = new LineDataSet(yVals1, "营业额");

            set1.setAxisDependency(YAxis.AxisDependency.LEFT);
            set1.setColor(ColorTemplate.getHoloBlue());
//            set1.setCircleColor(Color.BLUE);
            set1.setCircleColor(Color.parseColor("#528EFF"));
            set1.setLineWidth(2f);
            set1.setCircleRadius(3f);
            set1.setFillAlpha(65);
            set1.setFillColor(Color.BLUE);
            set1.setHighLightColor(Color.TRANSPARENT);//设置点击点的背景
            set1.setDrawCircleHole(true);
            //set1.setFillFormatter(new MyFillFormatter(0f));
            //set1.setDrawHorizontalHighlightIndicator(false);
            //set1.setVisible(false);
            //set1.setCircleHoleColor(Color.WHITE);

            // create a dataset and give it a type
            set2 = new LineDataSet(yVals2, "邀请会员");
//            set2.setAxisDependency(YAxis.AxisDependency.RIGHT);
            set2.setAxisDependency(YAxis.AxisDependency.LEFT);
            set2.setColor(Color.RED);
            set2.setCircleColor(Color.RED);
            set2.setLineWidth(2f);
            set2.setCircleRadius(3f);
            set2.setFillAlpha(65);
            set2.setFillColor(Color.RED);
            set2.setDrawCircleHole(false);
            set2.setHighLightColor(Color.rgb(244, 117, 117));
//            set2.setFillFormatter(new MyFillFormatter(900f));

            set3 = new LineDataSet(yVals3, "订单数");
//            set3.setAxisDependency(YAxis.AxisDependency.RIGHT);
            set3.setAxisDependency(YAxis.AxisDependency.LEFT);
            set3.setColor(Color.YELLOW);
            set3.setCircleColor(Color.YELLOW);
            set3.setLineWidth(2f);
            set3.setCircleRadius(3f);
            set3.setFillAlpha(65);
            set3.setFillColor(ColorTemplate.colorWithAlpha(Color.YELLOW, 200));
            set3.setDrawCircleHole(true);
            set3.setHighLightColor(Color.rgb(244, 117, 117));

//            // create a data object with the datasets
            LineData data = new LineData(set1,set2,set3);
            data.setValueTextColor(Color.GRAY);
            data.setValueTextSize(9f);

            // set data
            mChart.setData(data);
        }
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onValueSelected(Entry e, Highlight h) {

    }

    @Override
    public void onNothingSelected() {

    }
}
