package com.zzti.fengyongge.mpandroidchart.combinedchart;

import android.content.Context;
import android.widget.TextView;

import com.github.mikephil.charting.components.MarkerView;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Highlight;
import com.zzti.fengyongge.mpandroidchart.R;

import java.text.DecimalFormat;

/**
 * describe
 * 自定义坐标点展示布局
 * @author fengyongge
 * @data  2020/8/3
 */
public class MyMarkerView extends MarkerView {
    private TextView tvContent;
    private DecimalFormat decimalFormat;
    /**
     * Constructor. Sets up the MarkerView with a custom layout resource.
     *
     * @param context
     * @param layoutResource the layout resource to use for the MarkerView
     */
    public MyMarkerView(Context context) {
        super(context, R.layout.workorder_markview);
        tvContent = findViewById(R.id.tvContent);
        decimalFormat = new DecimalFormat("0.00");
    }

    @Override
    public void refreshContent(Entry e, Highlight highlight) {
        tvContent.setText(decimalFormat.format(e.getY()));
        super.refreshContent(e, highlight);
    }
}
