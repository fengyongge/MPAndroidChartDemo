package com.zzti.fengyongge.mpandroidchart;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.zzti.fengyongge.mpandroidchart.combinedchart.CombinedChartActivity;
import com.zzti.fengyongge.mpandroidchart.linechart.LineChartActivity;
import com.zzti.fengyongge.mpandroidchart.piechart.PieChartActivity;


public class MainActivity extends AppCompatActivity implements View.OnClickListener{


    private TextView tv1,tv2,tv3,tv4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    void initView(){
        tv1 = findViewById(R.id.tv1);
        tv2 = findViewById(R.id.tv2);
        tv3 = findViewById(R.id.tv3);
        tv4 = findViewById(R.id.tv4);
        tv1.setOnClickListener(this);
        tv2.setOnClickListener(this);
        tv3.setOnClickListener(this);
        tv4.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv1:
                startActivity(new Intent(MainActivity.this, LineChartActivity.class));
                break;
            case R.id.tv2:
                startActivity(new Intent(MainActivity.this, CombinedChartActivity.class));
                break;
            case R.id.tv3:
                startActivity(new Intent(MainActivity.this, PieChartActivity.class));
                break;
            case R.id.tv4:
                startActivity(new Intent(MainActivity.this, GroupedBarChartActivity.class));
                break;
        }
    }
}
