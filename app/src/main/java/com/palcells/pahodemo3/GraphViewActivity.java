package com.palcells.pahodemo3;

import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;

import com.jjoe64.graphview.BarGraphView;
import com.jjoe64.graphview.CustomLabelFormatter;
import com.jjoe64.graphview.GraphViewDataInterface;
import com.jjoe64.graphview.GraphViewSeries;
import com.jjoe64.graphview.GraphViewSeries.GraphViewSeriesStyle;
import com.jjoe64.graphview.GraphViewStyle;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;


public class GraphViewActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph_view);
        Random rand = new Random();

		/*
		 * use Date as x axis label
		 */
        long now = new Date().getTime();
        int size =40;
        GraphViewData[] data = new GraphViewData[size];
        for (int i=0; i<size; i++) {
            data[i] = new GraphViewData(now+(i*60*60*1000), rand.nextInt(100)); // next day
        }
        GraphViewSeries exampleSeries = new GraphViewSeries("SOC", new GraphViewSeriesStyle(Color.argb(200, 164, 199, 57), 10), data);
        //exampleSeries.getStyle().color = Color.CYAN;

        BarGraphView graphView = new BarGraphView(
                    this
                    , "GraphViewDemo"
            );

        graphView.getGraphViewStyle().setGridColor(getResources().getColor(android.R.color.white));
        graphView.getGraphViewStyle().setHorizontalLabelsColor(getResources().getColor(android.R.color.white));
        graphView.getGraphViewStyle().setVerticalLabelsColor(getResources().getColor(android.R.color.white));
        graphView.addSeries(exampleSeries); // data
        graphView.getGraphViewStyle().setVerticalLabelsWidth(100);
        graphView.setManualYAxisBounds(100, 0);
        graphView.getGraphViewStyle().setNumVerticalLabels(20);
        graphView.getGraphViewStyle().setVerticalLabelsAlign(Paint.Align.CENTER);
        graphView.getGraphViewStyle().setGridStyle(GraphViewStyle.GridStyle.HORIZONTAL);


		/*
		 * date as label formatter
		 */
        final SimpleDateFormat dateFormat = new SimpleDateFormat("EEE h:mm a");
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
        LinearLayout layout = (LinearLayout) findViewById(R.id.graphview1);
        layout.addView(graphView);
    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.graph_view, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    class GraphViewData implements GraphViewDataInterface {
        private double  y;
        private double x;

        public GraphViewData(double x, double y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public double getX() {
            return this.x;
        }

        @Override
        public double getY() {
            return this.y;
        }
    }
}
