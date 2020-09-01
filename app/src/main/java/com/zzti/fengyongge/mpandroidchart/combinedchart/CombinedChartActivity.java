
package com.zzti.fengyongge.mpandroidchart.combinedchart;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.github.mikephil.charting.charts.CombinedChart;
import com.zzti.fengyongge.mpandroidchart.R;

import java.util.ArrayList;
import java.util.List;

/**
 * describe
 * 组合图
 * @author fengyongge(fengyongge98@gmail.com)
 * @version V1.0
 * @date 2020/9/1
 */
public class CombinedChartActivity extends AppCompatActivity {

    private int combinedChartYMaxNum = 0;
    List<String> xList = new ArrayList<>();
    List<String> yList1 = new ArrayList<>();
    List<String> yList2 = new ArrayList<>();
    List<String> yList3 = new ArrayList<>();
    private CombinedChart combinedChart;
    private LinearLayout llCombinedCharEmpty;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_combined_chart);
        combinedChart = findViewById(R.id.combinedChart);
        llCombinedCharEmpty = findViewById(R.id.llCombinedCharEmpty);
        loadMore();
    }


    void loadMore() {
        llCombinedCharEmpty.setVisibility(View.GONE);
        combinedChart.setVisibility(View.VISIBLE);

        combinedChartYMaxNum = 0;
        xList.clear();
        yList1.clear();
        yList2.clear();
        yList3.clear();

        //x轴数据
        xList.add( "2020-08-26");
        xList.add( "2020-08-27");
        xList.add( "2020-08-28");
        xList.add( "2020-08-29");
        xList.add( "2020-08-30");
        xList.add( "2020-08-31");
        xList.add( "2020-09-01");

        //y轴
        for (int i = 0; i < 7 ; i++) {
            yList1.add(""+(float) (Math.random() * 700));
            yList2.add(""+(float) (Math.random() * 700));
            yList3.add(""+(float) (Math.random() * 700));
        }


        //获取y轴最大数据
        chooseChartMaxNum(yList1);
        chooseChartMaxNum(yList2);
        chooseChartMaxNum(yList3);

        //初始化
        initCombinedChart(combinedChartYMaxNum);
    }

    /**
     * 获取折线图中最大的数
     * @param list
     */
    void chooseChartMaxNum(List<String> list){
        for (int i = 0; i < list.size(); i++) {
            if (combinedChartYMaxNum < (int) Double.parseDouble(list.get(i))) {
                combinedChartYMaxNum = (int) Double.parseDouble(list.get(i));
            }
        }
    }


    void initCombinedChart(double maxNum) {

        //x轴数据
        List<String> xData = new ArrayList<>();
        for (int i = 0; i < xList.size(); i++) {
            xData.add(xList.get(i));
        }
        //y轴数据集合
        //1种直方图,直方图y轴数据

        List<List<Float>> yBarDatas = new ArrayList<>();
        //4种直方图
        for (int i = 0; i < 2; i++) {
            List<Float> yData = new ArrayList<>();
            for (int j = 1; j <= yList1.size(); j++) {
//                yData.add((float) (Math.random() * 100));
                yData.add(Float.parseFloat("" + maxNum));
            }
            yBarDatas.add(yData);
        }

        //3种折线图y轴数据

        List<List<Float>> yLineDatas = new ArrayList<>();

        //折线1
        List<Float> yData1 = new ArrayList<>();
        for (int i = 0; i < yList1.size(); i++) {
            yData1.add(Float.parseFloat(yList1.get(i)));
        }
        yLineDatas.add(yData1);

        //折线2
        List<Float> yData2 = new ArrayList<>();
        for (int i = 0; i < yList2.size(); i++) {
            yData2.add(Float.parseFloat(yList2.get(i)));
        }
        yLineDatas.add(yData2);

        //折线3
        List<Float> yData3 = new ArrayList<>();
        for (int i = 0; i < yList3.size(); i++) {
            yData3.add(Float.parseFloat(yList3.get(i)));
        }
        yLineDatas.add(yData3);


        //折线颜色集合
        List<Integer> lineChartColors = new ArrayList<>();
        lineChartColors.add(getResources().getColor(R.color.common_color_ED));
        lineChartColors.add(getResources().getColor(R.color.common_color_blue));
        lineChartColors.add(getResources().getColor(R.color.common_color_assist_brown));

        CombinedChartManager combineChartManager = new CombinedChartManager(CombinedChartActivity.this, combinedChart);
        combineChartManager.showCombinedChart(Float.parseFloat("" + maxNum), xData, yBarDatas, yLineDatas, lineChartColors);
    }
}