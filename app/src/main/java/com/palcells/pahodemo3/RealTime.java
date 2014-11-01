package com.palcells.pahodemo3;

import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.widget.LinearLayout;

import com.jjoe64.graphview.CustomLabelFormatter;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GraphView.GraphViewData;
import com.jjoe64.graphview.GraphViewSeries;
import com.jjoe64.graphview.LineGraphView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;


public class RealTime extends ActionBarActivity {

    private final Handler mHandler = new Handler();
    private Runnable mTimer1;
    private Runnable mTimer2;
    private GraphView graphView;
    private GraphViewSeries exampleSeries;
    private GraphViewSeries exampleSeries2;
    private GraphViewSeries exampleSeries3;
    int  graph2LastXValue=20;
    private double getRandom() {
        double high = 3;
        double low = 0.5;
        return Math.random() * (high - low) + low;
    }

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_real_time);

        Random rand = new Random();

		/*
		 * use Date as x axis label
		 */
        long now = new Date().getTime();
        int size =20;
        GraphViewData[] data = new GraphViewData[size];
        for (int i=0; i<size; i++) {
            data[i] = new GraphViewData(now+(i*60*1000), rand.nextInt(100)); // next day
        }
        exampleSeries = new GraphViewSeries("Realtime", new GraphViewSeries.GraphViewSeriesStyle(getResources().getColor(android.R.color.holo_green_light), 3), data);
        //exampleSeries.getStyle().color = Color.CYAN;

        LineGraphView graphView = new LineGraphView(
                this
                , "RealtimeViewDemo"
        );
        // ((LineGraphView) graphView).setDrawBackground(true);
        //graphView.setBackgroundColor(Color.rgb(141, 182, 0));
        ((LineGraphView) graphView).setDrawBackground(true);
        graphView.setBackgroundColor(getResources().getColor(android.R.color.holo_green_light));
        graphView.getGraphViewStyle().setGridColor(getResources().getColor(android.R.color.white));
        graphView.getGraphViewStyle().setHorizontalLabelsColor(getResources().getColor(android.R.color.white));
        graphView.getGraphViewStyle().setVerticalLabelsColor(getResources().getColor(android.R.color.white));
        graphView.setBackgroundColor(Color.argb(200, 164, 199, 57));
        //graphView.setBackgroundColor(Color.BLACK);
        graphView.setViewPort(1, 60*1000*30);
        graphView.setScalable(true);
        graphView.addSeries(exampleSeries); // data
        graphView.getGraphViewStyle().setVerticalLabelsWidth(100);
        graphView.setManualYAxisBounds(100, 0);
        graphView.getGraphViewStyle().setNumVerticalLabels(20);
        graphView.getGraphViewStyle().setVerticalLabelsAlign(Paint.Align.CENTER);



        //graphView.addSeries(exampleSeries); // data

		/*
		 * date as label formatter
		 */
        final SimpleDateFormat dateFormat = new SimpleDateFormat("h:mm a");
        graphView.setCustomLabelFormatter(new CustomLabelFormatter() {
            @Override
            public String formatLabel(double value, boolean isValueX) {
                if (isValueX) {
                    Date d = new Date((long) value);
                    return dateFormat.format(d);
                }
                return null; // let graphview generate Y-axis label for us
            }
        });
        graphView.setScrollable(true);
// optional - activate scaling / zooming
        graphView.setScalable(true);
        //exampleSeries.getStyle().color = getResources().getColor(Color.rgb(69, 75, 10));
        LinearLayout layout = (LinearLayout) findViewById(R.id.graphviewRealtime);
        layout.addView(graphView);
    }

    @Override
    protected void onPause() {
        mHandler.removeCallbacks(mTimer1);
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mTimer1 = new Runnable() {
            @Override
            public void run() {
                long now = new Date().getTime();
                Random rand = new Random();
                graph2LastXValue= graph2LastXValue +1;
                    exampleSeries.appendData(new GraphViewData(now + (graph2LastXValue * 60 * 1000), rand.nextInt(100)), true, 60 * 1000 * 30);
                    mHandler.postDelayed(this, 10000);

            }
        };
        mHandler.postDelayed(mTimer1, 5000);


    }
}
